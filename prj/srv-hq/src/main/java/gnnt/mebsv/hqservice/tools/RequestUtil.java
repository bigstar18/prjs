package gnnt.mebsv.hqservice.tools;

import gnnt.mebsv.hqservice.model.HQVO.CMDBillVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDByTimeVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDMarketSortVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDMinVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDProductInfoVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDQuoteVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDSetCurPage;
import gnnt.mebsv.hqservice.model.HQVO.CMDSortVO;
import gnnt.mebsv.hqservice.model.HQVO.CMDVO;
import gnnt.mebsv.hqservice.model.HQVO.ProductInfoListVO;
import gnnt.mebsv.hqservice.model.MinDataVO;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class RequestUtil
{
  public static final byte CMD_TEST_CONNECT = 0;
  public static final byte CMD_LOGIN = 1;
  public static final byte CMD_VERSION = 2;
  public static final byte CMD_CODELIST = 3;
  public static final byte CMD_QUOTE = 4;
  public static final byte CMD_CLASS_SORT = 5;
  public static final byte CMD_MIN_DATA = 6;
  public static final byte CMD_BILL_DATA = 7;
  public static final byte CMD_TRADETIME = 8;
  public static final byte CMD_DATE = 9;
  public static final byte CMD_MARKET_SORT = 10;
  public static final byte CMD_BYCONDITION = 11;
  public static final byte CMD_MIN_LINE_INTERVAL = 12;
  public static final byte CMD_SETCURPAGE = 13;
  public static final byte CMD_CLEAR = 14;
  public static final byte CMD_INDUSTRYDIC = 15;
  public static final byte CMD_ADDRDIC = 16;
  public static final byte CMD_MrketInfo = 17;
  public static final byte CMD_SYSTEMMESSAGE = 18;
  public static final byte CMD_USERSET = 99;

  public static void sendRequest(CMDVO paramCMDVO, Socket paramSocket)
    throws IOException
  {
    DataOutputStream localDataOutputStream1 = new DataOutputStream(paramSocket.getOutputStream());
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream2 = new DataOutputStream(localByteArrayOutputStream);
    switch (paramCMDVO.getCmd())
    {
    case 12:
      localDataOutputStream2.writeByte(12);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 3:
      CMDProductInfoVO localCMDProductInfoVO = (CMDProductInfoVO)paramCMDVO;
      localDataOutputStream2.writeByte(3);
      localDataOutputStream2.writeInt(localCMDProductInfoVO.date);
      localDataOutputStream2.writeInt(localCMDProductInfoVO.time);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 5:
      CMDSortVO localCMDSortVO = (CMDSortVO)paramCMDVO;
      localDataOutputStream2.writeByte(5);
      localDataOutputStream2.writeByte(localCMDSortVO.sortBy);
      localDataOutputStream2.writeByte(localCMDSortVO.isDescend);
      localDataOutputStream2.writeInt(localCMDSortVO.start);
      localDataOutputStream2.writeInt(localCMDSortVO.end);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 4:
      CMDQuoteVO localCMDQuoteVO = (CMDQuoteVO)paramCMDVO;
      localDataOutputStream2.writeByte(4);
      localDataOutputStream2.writeByte(localCMDQuoteVO.isAll);
      localDataOutputStream2.writeInt(localCMDQuoteVO.codeList.length);
      for (int i = 0; i < localCMDQuoteVO.codeList.length; i++)
      {
        localDataOutputStream2.writeUTF(localCMDQuoteVO.codeList[i][0]);
        localDataOutputStream2.writeUTF(localCMDQuoteVO.codeList[i][1]);
        localDataOutputStream2.writeUTF(localCMDQuoteVO.codeList[i][2]);
      }
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 7:
      CMDBillVO localCMDBillVO = (CMDBillVO)paramCMDVO;
      localDataOutputStream2.writeByte(7);
      localDataOutputStream2.writeUTF(localCMDBillVO.marketID);
      localDataOutputStream2.writeUTF(localCMDBillVO.code);
      localDataOutputStream2.writeByte(localCMDBillVO.type);
      localDataOutputStream2.writeInt(localCMDBillVO.date);
      localDataOutputStream2.writeInt(localCMDBillVO.time);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 6:
      CMDMinVO localCMDMinVO = (CMDMinVO)paramCMDVO;
      localDataOutputStream2.writeByte(6);
      localDataOutputStream2.writeUTF(localCMDMinVO.marketID);
      localDataOutputStream2.writeUTF(localCMDMinVO.code);
      localDataOutputStream2.writeByte(localCMDMinVO.type);
      localDataOutputStream2.writeInt(localCMDMinVO.date);
      localDataOutputStream2.writeInt(localCMDMinVO.time);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 10:
      CMDMarketSortVO localCMDMarketSortVO = (CMDMarketSortVO)paramCMDVO;
      localDataOutputStream2.writeByte(10);
      localDataOutputStream2.writeInt(localCMDMarketSortVO.num);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 9:
      localDataOutputStream2.writeByte(9);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 8:
      localDataOutputStream2.writeByte(8);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 11:
      CMDByTimeVO localCMDByTimeVO = (CMDByTimeVO)paramCMDVO;
      localDataOutputStream2.writeByte(11);
      localDataOutputStream2.writeInt(localCMDByTimeVO.time);
      localDataOutputStream2.flush();
      localDataOutputStream1.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    case 13:
      CMDSetCurPage localCMDSetCurPage = (CMDSetCurPage)paramCMDVO;
      localDataOutputStream2.writeByte(13);
      localDataOutputStream2.writeInt(localCMDSetCurPage.curPage);
      localDataOutputStream2.flush();
      localDataOutputStream2.close();
      break;
    case 2:
      localDataOutputStream2.writeByte(2);
      localDataOutputStream2.flush();
      localDataOutputStream2.write(localByteArrayOutputStream.toByteArray());
      localDataOutputStream1.flush();
      localDataOutputStream2.close();
      break;
    }
  }

  private static byte[] getRepoent(String paramString)
    throws MalformedURLException, IOException
  {
    URL localURL = null;
    localURL = new URL(paramString);
    URLConnection localURLConnection = localURL.openConnection();
    localURLConnection.connect();
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(localURLConnection.getInputStream());
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (localBufferedInputStream.read(arrayOfByte) > 0)
      localByteArrayOutputStream.write(arrayOfByte);
    localBufferedInputStream.close();
    return localByteArrayOutputStream.toByteArray();
  }

  public static ProductInfoListVO getProductInfoList(String paramString)
    throws MalformedURLException, IOException
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(getRepoent(paramString));
    GZIPInputStream localGZIPInputStream = new GZIPInputStream(localByteArrayInputStream);
    DataInputStream localDataInputStream = new DataInputStream(localGZIPInputStream);
    localDataInputStream.readByte();
    ProductInfoListVO localProductInfoListVO = CMDProductInfoVO.getObj(localDataInputStream);
    localDataInputStream.close();
    localGZIPInputStream.close();
    return localProductInfoListVO;
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      Socket localSocket = new Socket("127.0.0.1", 888);
      CMDMinVO localCMDMinVO = new CMDMinVO();
      localCMDMinVO.code = "G0509";
      sendRequest(localCMDMinVO, localSocket);
      DataInputStream localDataInputStream = new DataInputStream(localSocket.getInputStream());
      System.out.println(localDataInputStream.readByte());
      System.out.println(localDataInputStream.readUTF());
      System.out.println(localDataInputStream.readByte());
      System.out.println(localDataInputStream.readInt());
      MinDataVO[] arrayOfMinDataVO = CMDMinVO.getObj(localDataInputStream);
      for (int i = 0; i < arrayOfMinDataVO.length; i++)
        System.out.println(arrayOfMinDataVO[i]);
      System.out.println("2");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}