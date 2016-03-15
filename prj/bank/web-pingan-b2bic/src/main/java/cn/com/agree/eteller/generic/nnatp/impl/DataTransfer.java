package cn.com.agree.eteller.generic.nnatp.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataTransfer {
	private OutputStream _outputStream;
	private InputStream _inputStream;
	private Socket _sock;
	private byte[] recvDatas = new byte[40960];
	private int dataLen = 0;
	private int _natpVersion = 1;
	private String _tradeCode;
	private String _businessType;
	private String _agentNo;
	private String _areaNo;
	private String _bankNo;
	private String _tellerNo;
	private int _protoclVersion;
	private String _fileServerName;
	private String _templateCode;
	private String _reserve;

	public DataTransfer(Socket sock) {
		this._sock = sock;
		try {
			setInputStream(this._sock.getInputStream());
			setOutputStream(this._sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] natpRecv() throws IOException {
		DataInputStream din = new DataInputStream(getInputStream());
		int totalLength = din.readInt();
		byte[] allDatas = new byte[totalLength];
		readFully(din, allDatas);
		if (Crc32_Agree.crc32_2(allDatas, 0, totalLength - 4) != getCrc(allDatas)) {
			throw new IOException("crc校验不符");
		}
		byte protoclVersion = allDatas[0];
		setProtoclVersion(protoclVersion);
		if (protoclVersion == 1) {
			byte[] datas = natpRecvData(allDatas, din);
			return handleDatas(datas);
		}
		if (protoclVersion == 2) {
			return natpRecvFile(allDatas, din);
		}
		throw new IOException("无此协议版本号" + protoclVersion);
	}

	public void natpSendDatas(byte[] datas) throws IOException {
		int offset;
		if (getNatpVersion() == 16) {
			offset = 71;
		} else {
			offset = 43;
		}
		DataOutputStream dout = new DataOutputStream(getOutputStream());
		DataInputStream din = new DataInputStream(getInputStream());
		byte continueFlag = 0;
		short packNum = 1;
		int sendLength = datas.length + offset;
		int index = datas.length;
		if (sendLength > 4092) {
			sendLength = 4092;
			continueFlag = 1;
			index = sendLength - offset;
		}
		dout.writeInt(sendLength);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(bout);

		dataOut.write(1);
		dataOut.write(0);
		dataOut.write(continueFlag);
		dataOut.write(0);
		dataOut.writeShort(packNum);
		if (getNatpVersion() == 16) {
			dataOut.write(16);
			dataOut.write(StringTool.fill(getTradeCode(), ' ', 20, false).getBytes("GBK"));
			dataOut.write(StringTool.fill(getTemplateCode(), ' ', 20, false).getBytes("GBK"));
			dataOut.write(StringTool.fill(getReserve(), ' ', 20, false).getBytes("GBK"));
		} else {
			dataOut.write(1);
			dataOut.write(StringTool.fill(getTradeCode(), ' ', 5, false).getBytes());
			dataOut.write(StringTool.fill(getBusinessType(), ' ', 3, false).getBytes());
			dataOut.write(StringTool.fill(getAgentNo(), ' ', 9, false).getBytes());
			dataOut.write(StringTool.fill(getAreaNo(), ' ', 5, false).getBytes());
			dataOut.write(StringTool.fill(getBankNo(), ' ', 5, false).getBytes());
			dataOut.write(StringTool.fill(getTellerNo(), ' ', 5, false).getBytes());
		}
		dataOut.write(datas, 0, index);
		byte[] pureDatas = bout.toByteArray();
		dout.write(pureDatas);
		dout.writeInt(Crc32_Agree.crc32_2(pureDatas, 0, pureDatas.length));
		dout.flush();
		while (continueFlag == 1) {
			recvProtocolPack(din);
			if (datas.length <= index + 4096 - 14) {
				continueFlag = 0;
				sendLength = datas.length - index;
			} else {
				continueFlag = 1;
				sendLength = 4082;
			}
			bout.reset();
			dataOut.write(1);
			dataOut.write(0);
			dataOut.write(continueFlag);
			dataOut.write(0);
			dataOut.writeShort(packNum = (short) (packNum + 1));
			dataOut.write(datas, index, sendLength);
			pureDatas = bout.toByteArray();
			dout.writeInt(sendLength + 10);
			dout.write(pureDatas);
			dout.writeInt(Crc32_Agree.crc32_2(pureDatas, 0, pureDatas.length));
			dout.flush();
			index += sendLength;
		}
	}

	private void recvProtocolPack(DataInputStream din) throws IOException {
		int len = din.readInt();
		byte[] protocol = new byte[len];
		din.read(protocol);
	}

	private byte[] handleDatas(byte[] datas) throws IOException {
		int natpVersion = datas[0];
		if ((natpVersion == 1) || (natpVersion == 2)) {
			byte[] tradeCode = { datas[1], datas[2], datas[3], datas[4], datas[5] };

			byte[] businessType = { datas[6], datas[7], datas[8] };

			byte[] agentNo = { datas[9], datas[10], datas[11], datas[12], datas[13], datas[14], datas[15], datas[16], datas[17] };

			byte[] areaNo = { datas[18], datas[19], datas[20], datas[21], datas[22] };

			byte[] bankNo = { datas[23], datas[24], datas[25], datas[26], datas[27] };

			byte[] tellerNo = { datas[28], datas[29], datas[30], datas[31], datas[32] };
			setTradeCode(new String(tradeCode).trim());
			setAgentNo(new String(agentNo).trim());
			setBusinessType(new String(businessType).trim());
			setAreaNo(new String(areaNo).trim());
			setBankNo(new String(bankNo).trim());
			setTellerNo(new String(tellerNo).trim());
			byte[] realDatas = new byte[datas.length - 33];
			System.arraycopy(datas, 33, realDatas, 0, realDatas.length);
			return realDatas;
		}
		if (natpVersion == 3) {
			byte[] realDatas = new byte[datas.length - 1];
			System.arraycopy(datas, 1, realDatas, 0, realDatas.length);
			return realDatas;
		}
		if (natpVersion == 16) {
			byte[] tradeCode = new byte[20];
			System.arraycopy(datas, 1, tradeCode, 0, 20);

			byte[] templateCode = new byte[20];
			System.arraycopy(datas, 21, templateCode, 0, 20);

			byte[] reserve = new byte[20];
			System.arraycopy(datas, 41, reserve, 0, 20);

			setTradeCode(new String(tradeCode).trim());
			setTemplateCode(new String(templateCode).trim());
			setReserve(new String(reserve).trim());

			byte[] realDatas = new byte[datas.length - 61];
			System.arraycopy(datas, 61, realDatas, 0, realDatas.length);
			return realDatas;
		}
		throw new IOException("无此NATP版本类型 " + natpVersion);
	}

	private void readFully(InputStream din, byte[] rec) throws IOException {
		int readed = din.read(rec);
		while (readed < rec.length) {
			try {
				int next = din.read(rec, readed, rec.length - readed);
				readed += next;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private byte[] natpRecvFile(byte[] allDatas, DataInputStream din) throws UnknownHostException, IOException {
		byte[] hostName = new byte[15];
		System.arraycopy(allDatas, 7, hostName, 0, hostName.length);
		String host = new String(hostName).trim();
		setFileServerName(host);
		return allDatas;
	}

	/* Error */
	public void fileTransfer(byte[] firstDatas, InputStream _hostInput, OutputStream _hostOutput) throws IOException {
		// Byte code:
		// 0: new 76 java/io/DataInputStream
		// 3: dup
		// 4: aload_2
		// 5: invokespecial 79 java/io/DataInputStream:<init> (Ljava/io/InputStream;)V
		// 8: astore 4
		// 10: new 145 java/io/DataOutputStream
		// 13: dup
		// 14: aload_3
		// 15: invokespecial 148 java/io/DataOutputStream:<init> (Ljava/io/OutputStream;)V
		// 18: astore 5
		// 20: new 145 java/io/DataOutputStream
		// 23: dup
		// 24: aload_0
		// 25: invokevirtual 147 cn/com/agree/eteller/generic/nnatp/impl/DataTransfer:getOutputStream ()Ljava/io/OutputStream;
		// 28: invokespecial 148 java/io/DataOutputStream:<init> (Ljava/io/OutputStream;)V
		// 31: astore 6
		// 33: new 76 java/io/DataInputStream
		// 36: dup
		// 37: aload_0
		// 38: invokevirtual 78 cn/com/agree/eteller/generic/nnatp/impl/DataTransfer:getInputStream ()Ljava/io/InputStream;
		// 41: invokespecial 79 java/io/DataInputStream:<init> (Ljava/io/InputStream;)V
		// 44: astore 7
		// 46: aload 5
		// 48: aload_1
		// 49: arraylength
		// 50: invokevirtual 150 java/io/DataOutputStream:writeInt (I)V
		// 53: aload 5
		// 55: aload_1
		// 56: invokevirtual 179 java/io/DataOutputStream:write ([B)V
		// 59: aload 5
		// 61: invokevirtual 210 java/io/DataOutputStream:flush ()V
		// 64: aload 4
		// 66: invokevirtual 81 java/io/DataInputStream:readInt ()I
		// 69: istore 8
		// 71: iload 8
		// 73: newarray byte
		// 75: astore 9
		// 77: aload_0
		// 78: aload 4
		// 80: aload 9
		// 82: invokespecial 85 cn/com/agree/eteller/generic/nnatp/impl/DataTransfer:readFully (Ljava/io/InputStream;[B)V
		// 85: aload 6
		// 87: iload 8
		// 89: invokevirtual 150 java/io/DataOutputStream:writeInt (I)V
		// 92: aload 6
		// 94: aload 9
		// 96: invokevirtual 179 java/io/DataOutputStream:write ([B)V
		// 99: aload 6
		// 101: invokevirtual 210 java/io/DataOutputStream:flush ()V
		// 104: aload 7
		// 106: invokevirtual 81 java/io/DataInputStream:readInt ()I
		// 109: istore 8
		// 111: iload 8
		// 113: newarray byte
		// 115: astore 9
		// 117: aload_0
		// 118: aload 7
		// 120: aload 9
		// 122: invokespecial 85 cn/com/agree/eteller/generic/nnatp/impl/DataTransfer:readFully (Ljava/io/InputStream;[B)V
		// 125: aload 5
		// 127: iload 8
		// 129: invokevirtual 150 java/io/DataOutputStream:writeInt (I)V
		// 132: aload 5
		// 134: aload 9
		// 136: invokevirtual 179 java/io/DataOutputStream:write ([B)V
		// 139: aload 5
		// 141: invokevirtual 210 java/io/DataOutputStream:flush ()V
		// 144: goto -80 -> 64
		// 147: astore 8
		// 149: aload 8
		// 151: invokevirtual 302 java/io/IOException:toString ()Ljava/lang/String;
		// 154: ldc_w 303
		// 157: invokevirtual 305 java/lang/String:equals (Ljava/lang/Object;)Z
		// 160: ifeq +4 -> 164
		// 163: return
		// 164: aload 8
		// 166: athrow
		// Line number table:
		// Java source line #275 -> byte code offset #0
		// Java source line #274 -> byte code offset #8
		// Java source line #277 -> byte code offset #10
		// Java source line #276 -> byte code offset #18
		// Java source line #278 -> byte code offset #20
		// Java source line #280 -> byte code offset #33
		// Java source line #279 -> byte code offset #44
		// Java source line #281 -> byte code offset #46
		// Java source line #282 -> byte code offset #53
		// Java source line #283 -> byte code offset #59
		// Java source line #286 -> byte code offset #64
		// Java source line #287 -> byte code offset #71
		// Java source line #288 -> byte code offset #77
		// Java source line #289 -> byte code offset #85
		// Java source line #290 -> byte code offset #92
		// Java source line #291 -> byte code offset #99
		// Java source line #292 -> byte code offset #104
		// Java source line #293 -> byte code offset #111
		// Java source line #294 -> byte code offset #117
		// Java source line #295 -> byte code offset #125
		// Java source line #296 -> byte code offset #132
		// Java source line #297 -> byte code offset #139
		// Java source line #298 -> byte code offset #147
		// Java source line #299 -> byte code offset #149
		// Java source line #300 -> byte code offset #163
		// Java source line #302 -> byte code offset #164
		// Local variable table:
		// start length slot name signature
		// 0 167 0 this DataTransfer
		// 0 167 1 firstDatas byte[]
		// 0 167 2 _hostInput InputStream
		// 0 167 3 _hostOutput OutputStream
		// 8 71 4 hostInput DataInputStream
		// 18 122 5 hostOuput DataOutputStream
		// 31 69 6 clientOutput DataOutputStream
		// 44 75 7 clientInput DataInputStream
		// 69 59 8 len int
		// 147 18 8 e IOException
		// 75 60 9 recv byte[]
		// Exception table:
		// from to target type
		// 64 144 147 java/io/IOException
	}

	private byte[] natpRecvData(byte[] allDatas, DataInputStream din) throws IOException {
		int packType = allDatas[1];
		int continueFlag = allDatas[2];
		int compressFlag = allDatas[3];
		short packNum = bytesToShort(allDatas, 4);
		appendBytes(allDatas, 6, allDatas.length - 10);
		DataOutputStream dout = new DataOutputStream(getOutputStream());
		while (continueFlag == 1) {
			natpSendProtocol(dout, (short) (packNum + 1));
			int len = din.readInt();
			byte[] datas = new byte[len];
			readFully(din, datas);
			packType = datas[1];
			continueFlag = datas[2];
			compressFlag = datas[3];
			packNum = bytesToShort(datas, 4);
			if (Crc32_Agree.crc32_2(datas, 0, datas.length - 4) != getCrc(datas)) {
				throw new IOException("crc校验不符");
			}
			appendBytes(datas, 6, datas.length - 10);
		}
		return getAllDatas();
	}

	private void natpSendProtocol(DataOutputStream dout, short packNum) throws IOException {
		byte[] datas = new byte[6];
		datas[0] = 1;
		datas[1] = 1;
		datas[2] = 0;
		datas[3] = 0;
		datas[4] = ((byte) (packNum >>> 8 & 0xFF));
		datas[5] = ((byte) (packNum >>> 0 & 0xFF));
		dout.writeInt(10);
		dout.write(datas);
		dout.writeInt(Crc32_Agree.crc32_2(datas, 0, datas.length));
		dout.flush();
	}

	private void appendBytes(byte[] allDatas, int i, int j) {
		System.arraycopy(allDatas, i, this.recvDatas, this.dataLen, j);
		this.dataLen += j;
	}

	private byte[] getAllDatas() {
		byte[] ret = new byte[this.dataLen];
		System.arraycopy(this.recvDatas, 0, ret, 0, this.dataLen);
		for (int i = 0; i < this.dataLen; i++) {
			this.recvDatas[i] = 0;
		}
		this.dataLen = 0;
		return ret;
	}

	private short bytesToShort(byte[] allDatas, int i) {
		int i1 = allDatas[i] & 0xFF;
		int i2 = allDatas[(i + 1)] & 0xFF;
		return (short) ((i1 << 8) + (i2 << 0));
	}

	private int getCrc(byte[] allDatas) throws IOException {
		if (allDatas.length < 4) {
			throw new IOException("crc校验时数据长度太小");
		}
		int i1 = allDatas[(allDatas.length - 4)] & 0xFF;
		int i2 = allDatas[(allDatas.length - 3)] & 0xFF;
		int i3 = allDatas[(allDatas.length - 2)] & 0xFF;
		int i4 = allDatas[(allDatas.length - 1)] & 0xFF;
		return (i1 << 24) + (i2 << 16) + (i3 << 8) + (i4 << 0);
	}

	public void setOutputStream(OutputStream stream) {
		this._outputStream = stream;
	}

	public void setInputStream(InputStream stream) {
		this._inputStream = stream;
	}

	public InputStream getInputStream() {
		return this._inputStream;
	}

	public OutputStream getOutputStream() {
		return this._outputStream;
	}

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(7007);
		DataTransfer transfer = new DataTransfer();
		for (;;) {
			Socket socket = server.accept();
			transfer.setInputStream(socket.getInputStream());
			transfer.setOutputStream(socket.getOutputStream());
			byte[] recv = transfer.natpRecv();
			if (transfer.getProtoclVersion() != 2) {
				transfer.natpSendDatas(recv);
			}
		}
	}

	public DataTransfer() {
	}

	public String getAgentNo() {
		return this._agentNo;
	}

	public String getAreaNo() {
		return this._areaNo;
	}

	public String getBankNo() {
		return this._bankNo;
	}

	public String getBusinessType() {
		return this._businessType;
	}

	public String getTellerNo() {
		return this._tellerNo;
	}

	public String getTradeCode() {
		return this._tradeCode;
	}

	public void setAgentNo(String string) {
		this._agentNo = string;
	}

	public void setAreaNo(String string) {
		this._areaNo = string;
	}

	public void setBankNo(String string) {
		this._bankNo = string;
	}

	public void setBusinessType(String string) {
		this._businessType = string;
	}

	public void setTellerNo(String string) {
		this._tellerNo = string;
	}

	public void setTradeCode(String string) {
		this._tradeCode = string;
	}

	public int getProtoclVersion() {
		return this._protoclVersion;
	}

	public void setProtoclVersion(int i) {
		this._protoclVersion = i;
	}

	public String getFileServerName() {
		return this._fileServerName;
	}

	public void setFileServerName(String string) {
		this._fileServerName = string;
	}

	public String getReserve() {
		return this._reserve;
	}

	public void setReserve(String _reserve) {
		this._reserve = _reserve;
	}

	public String getTemplateCode() {
		return this._templateCode;
	}

	public void setTemplateCode(String code) {
		this._templateCode = code;
	}

	public int getNatpVersion() {
		return this._natpVersion;
	}

	public void setNatpVersion(int version) {
		this._natpVersion = version;
	}
}
