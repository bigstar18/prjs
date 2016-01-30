package gnnt.MEBS.billWarehoursInterface.client.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.billWarehoursInterface.VO.RequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.ResponseVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterResponseVO;
import gnnt.MEBS.billWarehoursInterface.client.IClient;
import gnnt.MEBS.billWarehoursInterface.encrypt.IEnCrypt;
import gnnt.MEBS.billWarehoursInterface.exception.WarehoursException;
import gnnt.MEBS.billWarehoursInterface.util.DateUtil;
import gnnt.MEBS.billWarehoursInterface.util.GnntProperties;

public class Client implements IClient {
	protected final transient Log logger = LogFactory.getLog(Client.class);
	private Socket serverSocket;
	private String serverAddr = null;
	private int serverPort;
	private DataInputStream input;
	private DataOutputStream output;
	private IEnCrypt encrypt;

	public void setEncrypt(IEnCrypt encrypt) {
		this.encrypt = encrypt;
	}

	public Client() {
		this(GnntProperties.getInstance().getProperty("warehours.ip"), Integer.parseInt(GnntProperties.getInstance().getProperty("warehours.port")));
	}

	public Client(String serverAddr, int serverPort) {
		if ((serverPort < 0) || (serverPort > 65535)) {
			throw new IllegalArgumentException("port out of range:" + serverPort);
		}
		if ((serverAddr == null) || (serverAddr.length() == 0)) {
			throw new IllegalArgumentException("serverAddr can't be null");
		}
		this.serverAddr = serverAddr;
		this.serverPort = serverPort;
	}

	public Client(InetAddress localHost, int port) {
		this(localHost.getHostAddress(), port);
	}

	public ResponseVO communicate(RequestVO requestVO) {
		if (requestVO == null) {
			throw new WarehoursException("请求包为空");
		}

		switch (requestVO.getProtocolName().ordinal()) {
		case 2:
			break;
		default:
			throw new WarehoursException("仓库系统不支持" + requestVO.getProtocolName() + "协议");
		}

		ResponseVO responseVO = null;
		if (connectServer()) {
			try {
				encryptSendMessage(requestVO.getXMLFromVO());
				String msg = decryptRecvMessage();
				if (msg != null) {
					switch (requestVO.getProtocolName().ordinal()) {
					case 2:
						responseVO = new UnRegisterResponseVO();
						responseVO = responseVO.getResponseVOFromXml(msg);
					}
				}

				this.logger.debug("msg=" + msg);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.error("通讯异常，异常信息" + e.toString());
				throw new WarehoursException("通讯异常，异常信息" + e.getMessage());
			} finally {
				closeSocket();
			}
		} else
			throw new WarehoursException("连接服务器失败！");

		return responseVO;
	}

	private boolean connectServer() {
		this.logger.info("connectServer............");
		boolean result = false;
		try {
			this.serverSocket = new Socket(this.serverAddr, this.serverPort);
			try {
				this.serverSocket.setSoTimeout(30000);
			} catch (SocketException e) {
				e.printStackTrace();
				this.logger.error("设置超时时间失败:" + e);
			}

			this.input = new DataInputStream(new BufferedInputStream(this.serverSocket.getInputStream()));

			this.output = new DataOutputStream(this.serverSocket.getOutputStream());

			result = true;

			this.logger.info("connectServer success");
		} catch (UnknownHostException e) {
			this.logger.warn("connectServer UnknownHostException ");
			e.printStackTrace();
		} catch (IOException e) {
			this.logger.warn("connectServer IOException ");
			e.printStackTrace();
		}
		if (!result) {
			this.logger.error("尝试连接服务器失败,地址：" + this.serverAddr + ":" + this.serverPort);
		}

		return result;
	}

	String decryptRecvMessage() throws IOException {
		String strMsg = recvMessage();
		if (strMsg == null) {
			return null;
		}

		String msg = this.encrypt.decrypt(strMsg);
		this.logger.debug("decrypt recv:" + msg);
		return msg;
	}

	boolean encryptSendMessage(String strMsg) throws IOException {
		this.logger.debug(" send:" + strMsg);
		strMsg = this.encrypt.encrypt(strMsg);
		this.logger.debug("encrypt Send" + strMsg);
		sendMessage(strMsg);
		return true;
	}

	void sendMessage(String strMsg) throws IOException {
		this.output.write(strMsg.getBytes());
		this.output.write(0);
		this.output.flush();
	}

	private String recvMessage() {
		String msg = null;
		ByteArrayOutputStream array = null;
		DataOutputStream outputArray = null;
		try {
			array = new ByteArrayOutputStream();
			outputArray = new DataOutputStream(array);

			byte tempByte = 0;
			while ((tempByte = this.input.readByte()) != 0) {
				outputArray.write(tempByte);
			}
			outputArray.flush();
			byte[] buf = array.toByteArray();
			msg = new String(buf, "GBK");
		} catch (IOException e) {
			this.logger.error("receive  occur Error,Error info=" + e.getMessage());
			e.printStackTrace();
			msg = null;
			closeSocket();
			if (outputArray != null) {
				try {
					outputArray.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					this.logger.warn("outputArray.close occur IOException:" + e1.getMessage());
				}
			}
			if (array != null) {
				try {
					array.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					this.logger.warn("array.close occur IOException:" + e1.getMessage());
				}
			}
		} catch (Exception e) {
			this.logger.error("receive occur Error,Error info=" + e.getMessage());
			e.printStackTrace();
			msg = null;
			closeSocket();
			if (outputArray != null) {
				try {
					outputArray.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					this.logger.warn("outputArray.close occur IOException:" + e1.getMessage());
				}
			}
			if (array != null) {
				try {
					array.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					this.logger.warn("array.close occur IOException:" + e1.getMessage());
				}
			}
		} finally {
			if (outputArray != null) {
				try {
					outputArray.close();
				} catch (IOException e) {
					e.printStackTrace();
					this.logger.warn("outputArray.close occur IOException:" + e.getMessage());
				}
			}
			if (array != null) {
				try {
					array.close();
				} catch (IOException e) {
					e.printStackTrace();
					this.logger.warn("array.close occur IOException:" + e.getMessage());
				}
			}
		}
		return msg;
	}

	private void closeSocket() {
		this.logger.info("close socket, close date:" + DateUtil.getCurDateTime());
		if (this.serverSocket != null) {
			if (!this.serverSocket.isClosed()) {
				try {
					this.serverSocket.close();
				} catch (IOException e1) {
					this.logger.warn("serverSocket.close occur Error,Error info=" + e1.getMessage());
					e1.printStackTrace();
				}
			}
			this.serverSocket = null;
		}

		if (this.input != null) {
			try {
				this.input.close();
			} catch (IOException e1) {
				this.logger.warn("input.close() occur Error,Error info=" + e1.getMessage());
				e1.printStackTrace();
			}
			this.input = null;
		}

		if (this.output != null) {
			try {
				this.output.close();
			} catch (IOException e1) {
				this.logger.warn("output.close() occur Error,Error info=" + e1.getMessage());
				e1.printStackTrace();
			}
			this.output = null;
		}
	}

	public static void main(String[] args) throws Exception {
		IClient client = new Client();
		client.communicate(new UnRegisterRequestVO());
	}
}