package gnnt.MEBS.billWarehoursInterface.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.billWarehoursInterface.VO.RequestVO;
import gnnt.MEBS.billWarehoursInterface.encrypt.DES3EncryptIMP;
import gnnt.MEBS.billWarehoursInterface.encrypt.IEnCrypt;
import gnnt.MEBS.billWarehoursInterface.operation.IInvokeCore;
import gnnt.MEBS.billWarehoursInterface.util.DateUtil;
import gnnt.MEBS.billWarehoursInterface.util.GnntBeanFactory;

public class Receiver extends Thread {
	protected final transient Log logger = LogFactory.getLog(Receiver.class);
	private Socket socket;
	private SocketTimeOutThread socketTimeOutThread;
	private IEnCrypt encrypt;
	private DataInputStream input;
	private DataOutputStream output;

	public void setEncrypt(IEnCrypt encrypt) {
		this.encrypt = encrypt;
	}

	public Receiver(Socket socket) {
		this.encrypt = ((DES3EncryptIMP) GnntBeanFactory.getBean("encrypt"));
		this.socket = socket;
		try {
			socket.setSoTimeout(20000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			this.socketTimeOutThread = new SocketTimeOutThread();
			this.socketTimeOutThread.setDaemon(true);
			this.socketTimeOutThread.start();
			this.input = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
			this.output = new DataOutputStream(this.socket.getOutputStream());

			String msg = decryptRecvMessage();
			if (msg != null) {
				RequestVO requestVO;
				IInvokeCore realMardridCore;
				return;
			}
		} catch (IOException e) {
			this.logger.error("processMsg occur Error,Error info=" + e.getMessage());
			e.printStackTrace();
		} finally {
			this.socketTimeOutThread.shutdown();
			closeSocket();
		}
	}

	String decryptRecvMessage() throws IOException {
		String strMsg = recvMessage();
		if (strMsg == null) {
			return null;
		}
		this.logger.debug("recv:" + strMsg);
		String msg = this.encrypt.decrypt(strMsg);
		this.logger.debug("decrypt recv:" + msg);
		return msg;
	}

	boolean encryptSendMessage(String strMsg) throws IOException {
		this.logger.debug("send:" + strMsg);
		strMsg = this.encrypt.encrypt(strMsg);
		this.logger.debug("encrypt send:" + strMsg);
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
			this.socketTimeOutThread.setTime();

			byte tempByte = 0;
			while ((tempByte = this.input.readByte()) != 0) {
				outputArray.write(tempByte);
			}
			this.socketTimeOutThread.initTime();

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
		if (this.socket != null) {
			if (!this.socket.isClosed()) {
				try {
					this.socket.close();
				} catch (IOException e1) {
					this.logger.warn("socket.close occur Error,Error info=" + e1.getMessage());
					e1.printStackTrace();
				}
			}
			this.socket = null;
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

	class SocketTimeOutThread extends Thread {
		boolean isRun = true;
		Date setDate = null;

		SocketTimeOutThread() {
		}

		void setTime() {
			this.setDate = new Date();
		}

		void initTime() {
			this.setDate = null;
		}

		public void run() {
			label124: while (this.isRun) {
				try {
					if ((Receiver.this.socket != null) && (this.setDate != null)) {
						if (System.currentTimeMillis() - this.setDate.getTime() <= 30000L) {
							break label124;
						}
						Receiver.this.logger.warn("SocketTimeOutThread checked timeout close socket");
						Receiver.this.closeSocket();
					}
				} catch (Exception e) {
					Receiver.this.logger.error("SocketTimeOutThread checked timeout occur Error:" + e.getMessage());
					e.printStackTrace();
					try {
						sleep(1000L);
					} catch (InterruptedException localInterruptedException) {
					}
				} finally {
					try {
						sleep(1000L);
					} catch (InterruptedException localInterruptedException1) {
					}
				}
			}
		}

		public void shutdown() {
			this.isRun = false;
			try {
				interrupt();
			} catch (Exception localException) {
			}
		}
	}
}
