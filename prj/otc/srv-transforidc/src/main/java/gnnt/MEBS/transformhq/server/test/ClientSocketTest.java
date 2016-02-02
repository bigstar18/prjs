package gnnt.MEBS.transformhq.server.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClientSocketTest implements Runnable {
	private static Log log = LogFactory.getLog(ClientSocketTest.class);
	private Socket socket;
	DataInputStream input;

	public ClientSocketTest() {
		startUp();
	}

	public void startUp() {
		try {
			this.socket = new Socket("192.168.1.105", 16891);
			this.socket.setSoTimeout(5000);
			BufferedInputStream br = new BufferedInputStream(this.socket.getInputStream());
			this.input = new DataInputStream(br);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		for (;;) {
			if ((this.socket != null) && (!this.socket.isClosed())) {
				receive(this.input);
			} else {
				startUp();
			}
		}
	}

	private String receive(DataInputStream input) {
		String msg = "";
		ByteArrayOutputStream array = null;
		DataOutputStream outputArray = null;
		try {
			array = new ByteArrayOutputStream();
			outputArray = new DataOutputStream(array);
			byte b = input.readByte();
			while ((b = input.readByte()) != 0) {
				outputArray.write(b);
			}
			outputArray.flush();
			byte[] buf = array.toByteArray();
			msg = new String(buf, "GBK");
			log.info("receive msg :" + msg);
		} catch (IOException e) {
			log.error("receive  occur Error,Error info=" + e.getMessage());
			e.printStackTrace();
			msg = null;
			if (outputArray != null) {
				try {
					outputArray.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					log.warn("outputArray.close occur IOException:" + e1.getMessage());
				}
			}
			if (array != null) {
				try {
					array.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					log.warn("array.close occur IOException:" + e1.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("receive occur Error,Error info=" + e.getMessage());
			e.printStackTrace();
			msg = null;
			if (outputArray != null) {
				try {
					outputArray.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					log.warn("outputArray.close occur IOException:" + e1.getMessage());
				}
			}
			if (array != null) {
				try {
					array.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					log.warn("array.close occur IOException:" + e1.getMessage());
				}
			}
		} finally {
			if (outputArray != null) {
				try {
					outputArray.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.warn("outputArray.close occur IOException:" + e.getMessage());
				}
			}
			if (array != null) {
				try {
					array.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.warn("array.close occur IOException:" + e.getMessage());
				}
			}
		}
		return msg;
	}

	private void process(String msg) {
		String[] strs = msg.split("\\|", -1);
		for (int i = 0; i < strs.length; i++) {
			log.warn("输出数据为：" + strs[i]);
		}
	}

	public static void main(String[] args) {
		new ClientSocketTest().run();
	}
}
