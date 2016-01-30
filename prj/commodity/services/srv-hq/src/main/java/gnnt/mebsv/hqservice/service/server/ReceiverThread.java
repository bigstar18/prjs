package gnnt.mebsv.hqservice.service.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.ClientSocket;
import gnnt.mebsv.hqservice.service.HQService;
import gnnt.mebsv.hqservice.service.ResponderX;
import gnnt.mebsv.hqservice.service.ServiceManagerX;
import gnnt.mebsv.hqservice.service.IO.CodeGeneration;
import gnnt.mebsv.hqservice.service.communication.SocketTimeOutThread;

public class ReceiverThread extends ResponderX {
	private Log log = LogFactory.getLog(ReceiverThread.class);
	String version;
	String defaultClientVersion = "F3.1.9.0000";
	static QuotationServer quotation = null;
	public static CodeGeneration flashThread = null;
	static HQService hqService;
	PushServer pushServer;
	long tradeTime = 0L;
	boolean flag;
	static String DY_ST_POOL = "1";

	public void initResponder() {
		synchronized (ReceiverThread.class) {
			try {
				if (quotation == null)
					quotation = QuotationServer.getInstance();
				if (flashThread == null) {
					flashThread = new CodeGeneration();
					flashThread.setName("flashThread " + new Date());
					flashThread.init(quotation);
					flashThread.setDaemon(true);
					flashThread.start();
				}
				if (hqService == null) {
					hqService = new HQService();
					hqService.init();
				}
				this.pushServer = new PushServer(hqService, null);
				this.version = new Configuration().getSection("MEBS.HQVersion").getProperty("Version");
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
	}

	public void run() {
		SocketTimeOutThread localSocketTimeOutThread = new SocketTimeOutThread();
		localSocketTimeOutThread.setName("socketTimeOutThread " + new Date());
		localSocketTimeOutThread.setDaemon(true);
		localSocketTimeOutThread.start();
		ServiceManagerX localServiceManagerX = getManager();
		Date localDate = new Date();
		byte[] arrayOfByte1 = new byte[1];
		arrayOfByte1[0] = 0;
		this.tradeTime = hqService.getTradeTime();
		while (true) {
			ClientSocket localClientSocket;
			Socket localSocket;
			DataOutputStream localDataOutputStream;
			if (System.currentTimeMillis() - localDate.getTime() > 20000L) {
				for (int i = this.clientPool.size() - 1; i >= 0; i--) {
					localClientSocket = (ClientSocket) this.clientPool.elementAt(i);
					localSocket = localClientSocket.socket;
					try {
						localDataOutputStream = new DataOutputStream(new BufferedOutputStream(localSocket.getOutputStream()));
						localDataOutputStream.write(arrayOfByte1);
						localDataOutputStream.flush();
					} catch (IOException localIOException1) {
						try {
							localSocket.close();
						} catch (IOException localIOException2) {
						}
						localServiceManagerX.removeClient(this.clientPool, localClientSocket);
					}
				}
				localDate = new Date();
			}
			if ((this.clientPool.size() <= 0) && (localServiceManagerX.removeThread(this))) {
				localSocketTimeOutThread.end();
				return;
			}
			this.flag = quotation.isClear;
			for (int i = this.clientPool.size() - 1; i >= 0; i--) {
				localClientSocket = (ClientSocket) this.clientPool.elementAt(i);
				localSocket = localClientSocket.socket;
				try {
					localSocketTimeOutThread.setSocket(localSocket);
					localDataOutputStream = new DataOutputStream(new BufferedOutputStream(localSocket.getOutputStream()));
					DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(localSocket.getInputStream()));
					if (this.tradeTime != hqService.getTradeTime()) {
						byte[] arrayOfByte2 = new byte[0];
						arrayOfByte2 = hqService.getTradeTimes(null);
						flushData(localSocket, arrayOfByte2);
						this.tradeTime = hqService.getTradeTime();
					}
					if ((this.flag) && (quotation.isServiceClear) && (quotation.clearMarketList.size() > 0)) {
						this.log.debug("进入交易日切换！！");
						for (int j = 0; j < quotation.clearMarketList.size(); j++) {
							this.log.debug("进入交易日切换！！，切换的市场为" + (String) quotation.clearMarketList.get(j));
							String localObject = (String) quotation.clearMarketList.get(j);
							localDataOutputStream.writeByte(14);
							localDataOutputStream.writeUTF((String) localObject);
							localDataOutputStream.flush();
							byte[] arrayOfByte4 = new byte[0];
							arrayOfByte4 = hqService.getDate((String) localObject);
							flushData(localSocket, arrayOfByte4);
							arrayOfByte4 = hqService.getTradeTimes((String) localObject);
							flushData(localSocket, arrayOfByte4);
							arrayOfByte4 = hqService.getMinLineInterval();
							flushData(localSocket, arrayOfByte4);
						}
						localClientSocket.quoteListTime = 0L;
						localClientSocket.vpSize.clear();
					}
					if (localClientSocket.iStatus == 99) {
						System.out.println("用户第一次登陆");
						byte[] arrayOfByte3 = new byte[0];
						arrayOfByte3 = hqService.getDate();
						flushData(localSocket, arrayOfByte3);
						arrayOfByte3 = hqService.getTradeTimes(null);
						System.out.println("交易节时间---------------" + arrayOfByte3.length + "  ------" + arrayOfByte3);
						flushData(localSocket, arrayOfByte3);
						arrayOfByte3 = hqService.getMinLineInterval();
						flushData(localSocket, arrayOfByte3);
						arrayOfByte3 = hqService.getAddrDictionary();
						flushData(localSocket, arrayOfByte3);
						arrayOfByte3 = hqService.getIndustryDictionary();
						flushData(localSocket, arrayOfByte3);
						localClientSocket.iStatus = -1;
					}
					while (localDataInputStream.available() > 0) {
						int k = localDataInputStream.readByte();
						byte[] localObject = new byte[0];
						localClientSocket.result = hqService.validateClientVersion(localClientSocket.clientVersion);
						switch (k) {
						case 1:
							localObject = hqService.logon();
							flushData(localSocket, (byte[]) localObject);
							break;
						case 2:
							System.out.println("进入版本验证");
							localClientSocket.clientVersion = localDataInputStream.readUTF();
							this.version = new Configuration().getSection("MEBS.HQVersion").getProperty("Version");
							localDataOutputStream.writeByte(2);
							localDataOutputStream.writeUTF(this.version);
							localDataOutputStream.flush();
							break;
						case 3:
							System.out.println("进入取码表");
							int m = localDataInputStream.readInt();
							int n = localDataInputStream.readInt();
							localObject = flashThread.getProductInfoList(m, n);
							flushData(localSocket, (byte[]) localObject);
							break;
						case 4:
							System.out.println("进入个股行情");
							this.log.debug("自选商品行情");
							System.out.println("个股行情数据.....请求");
							localClientSocket.codeList.clear();
							byte b1 = localDataInputStream.readByte();
							int i1 = localDataInputStream.readInt();
							for (int i2 = 0; i2 < i1; i2++) {
								String[] arrayOfString1 = new String[3];
								arrayOfString1[0] = localDataInputStream.readUTF();
								arrayOfString1[1] = localDataInputStream.readUTF();
								arrayOfString1[2] = localDataInputStream.readUTF();
								localClientSocket.codeList.put(Integer.valueOf(i2), arrayOfString1);
							}
							localObject = hqService.getCurStatusByCodes(localClientSocket.codeList, b1);
							System.out.println("个股行情数据数量" + localObject.length);
							flushData(localSocket, (byte[]) localObject);
							System.out.println("推送个股行情数据完毕");
							localClientSocket.isAll = b1;
							localClientSocket.iStatus = -1;
							break;
						case 5:
							System.out.println("取报价排名");
							byte b2 = localDataInputStream.readByte();
							int i3 = localDataInputStream.readInt();
							int i4 = localDataInputStream.readInt();
							localObject = hqService.getSortData(localDataInputStream.readByte(), b2, i3, i4);
							flushData(localSocket, (byte[]) localObject);
							break;
						case 6:
							this.log.debug("取分钟数据");
							System.out.println("取分钟数据");
							int i5 = localDataInputStream.readByte();
							int i6 = localDataInputStream.readByte();
							if (i6 == 1)
								localClientSocket.codeList.clear();
							for (byte b3 = 0; b3 < i5; b3++) {
								String[] arrayOfString2 = new String[3];
								arrayOfString2[0] = localDataInputStream.readUTF();
								arrayOfString2[2] = localDataInputStream.readUTF();
								byte b4 = localDataInputStream.readByte();
								System.out.println("当前商品位置" + arrayOfString2[2] + b4 + arrayOfString2[0]);
								localClientSocket.codeList.put(Byte.valueOf(b4), arrayOfString2);
							}
							byte b3 = localDataInputStream.readByte();
							int i7 = localDataInputStream.readInt();
							int i8 = localDataInputStream.readInt();
							localClientSocket.minLastDate = i7;
							localClientSocket.minLastTime = i8;
							localClientSocket.billDataPage = 1;
							localObject = hqService.getMinData(localClientSocket, b3, i7, i8);
							flushData(localSocket, (byte[]) localObject);
							localClientSocket.iStatus = -1;
							break;
						case 7:
							this.log.debug("大数据量请求分笔发送版本取分笔数据");
							System.out.println("大数据量请求分笔发送版本取分笔数据");
							String str1 = localDataInputStream.readUTF();
							String str2 = localDataInputStream.readUTF();
							byte b5 = localDataInputStream.readByte();
							long l1 = localDataInputStream.readLong();
							long l2 = localDataInputStream.readLong();
							String str3 = localDataInputStream.readUTF();
							localClientSocket.strCode = str2;
							localClientSocket.strMarket = str1;
							if ((b5 == 0) || (b5 == 2)) {
								localClientSocket.codeList.clear();
								localClientSocket.billDataPage = 2;
								localClientSocket.codeList.put(Integer.valueOf(0), new String[] { str2, "", str1 });
							} else if (b5 == 1) {
								localClientSocket.billDataPage = 1;
							}
							localObject = hqService.getBillData(str1, str2, b5, l1, l2, localClientSocket, str3);
							System.out.println("成交明细数据" + localObject.length);
							flushData(localSocket, (byte[]) localObject);
							localClientSocket.iStatus = -1;
							break;
						case 8:
							System.out.println("进入取交易节");
							localObject = hqService.getTradeTimes(null);
							flushData(localSocket, (byte[]) localObject);
							break;
						case 9:
							System.out.println("进入取交易日时间");
							localObject = hqService.getDate();
							flushData(localSocket, (byte[]) localObject);
							break;
						case 10:
							System.out.println("进入取市场综合排名");
							localClientSocket.iStatus = -1;
							int i9 = localDataInputStream.readInt();
							localObject = hqService.getMarketSortData(i9);
							flushData(localSocket, (byte[]) localObject);
							break;
						case 11:
							localClientSocket.iStatus = -1;
							System.out.println("进入取指定时间之后的行情");
							int i10 = localDataInputStream.readInt();
							flushData(localSocket, (byte[]) localObject);
							break;
						case 12:
							System.out.println("取分时走势时间间隔");
							localObject = hqService.getMinLineInterval();
							flushData(localSocket, (byte[]) localObject);
							break;
						case 13:
							localClientSocket.iStatus = localDataInputStream.readInt();
							System.out.println("客户端当前页为" + localClientSocket.iStatus);
							break;
						default:
							this.log.debug("error cmd :" + k);
						}
					}
					this.pushServer.pushHQ(localClientSocket, localDataOutputStream);
					localSocketTimeOutThread.removeSocket(localSocket);
				} catch (Exception localException) {
					localException.printStackTrace();
					try {
						localSocket.close();
					} catch (IOException localIOException3) {
					}
					localServiceManagerX.removeClient(this.clientPool, localClientSocket);
				}
			}
			try {
				sleep(100L);
			} catch (InterruptedException localInterruptedException) {
			}
		}
	}

	public void flushData(Socket paramSocket, byte[] paramArrayOfByte) {
		if ((paramArrayOfByte.length > 0) && (!DY_ST_POOL.equals("0")) && (DY_ST_POOL.equals("1")))
			try {
				if (paramArrayOfByte.length > 0) {
					DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(paramSocket.getOutputStream()));
					localDataOutputStream.write(paramArrayOfByte);
					localDataOutputStream.flush();
				}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
	}
}