package cn.com.agree.eteller.generic.nnatp.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.agree.eteller.generic.nnatp.INatp;

public class CommunicationNatp implements INatp {
	private static final Logger logger = Logger.getLogger(CommunicationNatp.class);
	private static final String ENCODING = "GBK";
	private DataTransfer dataTransfer;
	private ByteBuffer buf;
	private List nameList;
	private List valueList;
	private StringBuffer sendLog = new StringBuffer();
	private StringBuffer receiveLog = new StringBuffer();

	public CommunicationNatp() {
		this.dataTransfer = new DataTransfer();
		this.buf = ByteBuffer.allocate(4096);
		this.nameList = new ArrayList();
		this.valueList = new ArrayList();
	}

	public int getRecordCount(String fieldName) {
		if (fieldName == null) {
			return -1;
		}
		int count = 0;
		for (int i = 0; i < this.nameList.size(); i++) {
			if (fieldName.equals(this.nameList.get(i))) {
				count++;
			}
		}
		return count;
	}

	public int init(int natpVersion, String transCode, String templateCode, String reservedCode) {
		this.dataTransfer.setNatpVersion(natpVersion);
		this.dataTransfer.setTradeCode(transCode);
		this.dataTransfer.setTemplateCode(templateCode);
		this.dataTransfer.setReserve(reservedCode);
		return 0;
	}

	public void pack(String fieldName, String value) throws Exception {
		if ((fieldName == null) || (value == null)) {
			throw new Exception("打包数据不能为null");
		}
		this.buf.putShort((short) fieldName.getBytes().length);
		this.buf.put(fieldName.getBytes());
		this.buf.putShort((short) value.getBytes().length);
		this.buf.put(value.getBytes());
		this.sendLog.append("\t\t");
		this.sendLog.append(fieldName).append(":").append(value).append("\n");
	}

	public void pack(String[] fieldNames, String[] values) throws Exception {
		if ((fieldNames == null) || (values == null)) {
			throw new Exception("打包数据不能为null");
		}
		if (fieldNames.length != values.length) {
			throw new Exception("字段数量不匹配");
		}
		for (int i = 0; i < fieldNames.length; i++) {
			this.buf.putShort((short) fieldNames[i].getBytes().length);
			this.buf.put(fieldNames[i].getBytes());
			this.buf.putShort((short) values[i].getBytes().length);
			this.buf.put(values[i].getBytes());
			this.sendLog.append("\t\t");
			this.sendLog.append(fieldNames[i]).append(":").append(values[i]).append("\n");
		}
	}

	public String unpack(String fieldName, int iPos) throws Exception {
		if (fieldName == null) {
			throw new Exception("字段名称不能为null");
		}
		int count = 0;
		int i = 0;
		for (i = 0; i < this.nameList.size(); i++) {
			if (fieldName.equals(this.nameList.get(i))) {
				count++;
				if (count >= iPos) {
					break;
				}
			}
		}
		if (i == this.nameList.size()) {
			return null;
		}
		return (String) this.valueList.get(i);
	}

	public String[] unpack(String fieldName) throws Exception {
		if (fieldName == null) {
			throw new Exception("字段名称不能为null");
		}
		List findList = new ArrayList();
		for (int i = 0; i < this.nameList.size(); i++) {
			if (fieldName.equals(this.nameList.get(i))) {
				findList.add(this.valueList.get(i));
			}
		}
		return (String[]) findList.toArray(new String[0]);
	}

	public void exchange(String serverName) throws Exception {
		Socket socket = null;
		try {
			socket = SocketFactory.createSocket(serverName);
			this.dataTransfer.setOutputStream(socket.getOutputStream());
			this.dataTransfer.setInputStream(socket.getInputStream());
			byte[] sendData = new byte[this.buf.position()];
			for (int i = 0; i < sendData.length; i++) {
				sendData[i] = this.buf.get(i);
			}
			logger.info("\n\t" + TimeFactory.getCurrentTime() + "发送出的信息：" + "\n" + "\t\t" + "NatpVersion:" + this.dataTransfer.getNatpVersion() + "\n"
					+ "\t\t" + "TradeCode:" + this.dataTransfer.getTradeCode() + "\n" + "\t\t" + "TemplateCode:" + this.dataTransfer.getTemplateCode()
					+ "\n" + "\t\t" + "Reserve:" + this.dataTransfer.getReserve() + "\n" + this.sendLog);
			this.sendLog.setLength(0);
			this.nameList.clear();
			this.valueList.clear();
			this.dataTransfer.natpSendDatas(sendData);
			byte[] retData = this.dataTransfer.natpRecv();
			ByteArrayInputStream bin = new ByteArrayInputStream(retData);
			DataInputStream din = new DataInputStream(bin);
			while (din.available() > 0) {
				int nLen = din.readShort();
				byte[] byName = new byte[nLen];
				din.read(byName);
				String sName = new String(byName, "GBK");
				nLen = din.readShort();
				byte[] byValue = new byte[nLen];
				din.read(byValue);
				String sValue = new String(byValue, "GBK");
				this.nameList.add(sName);
				this.valueList.add(sValue);
				this.receiveLog.append("\t\t");
				this.receiveLog.append(sName).append(":").append(sValue).append("\n");
			}
			logger.info("\n\t" + TimeFactory.getCurrentTime() + "接收的信息：" + "\n" + this.receiveLog);
			this.receiveLog.setLength(0);
			din.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

	public List getNameList() {
		return this.nameList;
	}

	public void setNameList(List nameList) {
		this.nameList = nameList;
	}

	public List getValueList() {
		return this.valueList;
	}

	public void setValueList(List valueList) {
		this.valueList = valueList;
	}
}
