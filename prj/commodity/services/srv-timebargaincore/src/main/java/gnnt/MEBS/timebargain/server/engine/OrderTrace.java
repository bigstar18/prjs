package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.util.SysData;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.model.Order;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OrderTrace
{
  private final transient Log logger = LogFactory.getLog(OrderTrace.class);
  private static OrderTrace writer;
  private boolean traceEnable = false;
  private FileOutputStream fout;
  private DataOutputStream dout;
  
  private OrderTrace()
    throws IOException
  {
    String str = DAOBeanFactory.getConfig("orderTrace").trim();
    if ("1".equals(str))
    {
      this.traceEnable = true;
      Date localDate = new Date();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      File localFile = new File("order_" + localSimpleDateFormat.format(localDate));
      if (!localFile.exists()) {
        localFile.createNewFile();
      }
      this.logger.info("Trace Path:" + localFile.getAbsolutePath());
      this.fout = new FileOutputStream(localFile, true);
      this.dout = new DataOutputStream(this.fout);
    }
  }
  
  public void close()
  {
    try
    {
      this.dout.close();
      this.fout.close();
    }
    catch (Exception localException) {}
  }
  
  static OrderTrace getInstance()
  {
    if (writer == null) {
      try
      {
        writer = new OrderTrace();
      }
      catch (Exception localException)
      {
        System.out.println("Create Order Writer Fail!");
        localException.printStackTrace();
      }
    }
    return writer;
  }
  
  public void writeOrder(Order paramOrder)
  {
    if ((!this.traceEnable) || (paramOrder == null)) {
      return;
    }
    if (this.dout != null) {
      try
      {
        String str = order2trace(paramOrder);
        this.dout.write(toByteArray(str.length()));
        int i = 130;
        this.dout.write(toByteArray(i));
        int j = new Long(System.currentTimeMillis() / 1000L).intValue();
        this.dout.write(toByteArray(j));
        this.dout.write(str.getBytes());
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public void writeCancelOrder(Long paramLong)
  {
    if ((!this.traceEnable) || (paramLong == null)) {
      return;
    }
    if (this.dout != null) {
      try
      {
        String str = cancel2trace(paramLong.longValue());
        int i = str.length();
        this.dout.write(toByteArray(i));
        int j = 168;
        this.dout.write(toByteArray(j));
        int k = new Long(System.currentTimeMillis() / 1000L).intValue();
        this.dout.write(toByteArray(k));
        this.dout.write(str.getBytes());
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  static byte[] toByteArray(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = new Integer(paramInt & 0xFF).byteValue();
    arrayOfByte[1] = new Integer(paramInt >> 8 & 0xFF).byteValue();
    arrayOfByte[2] = new Integer(paramInt >> 16 & 0xFF).byteValue();
    arrayOfByte[3] = new Integer(paramInt >> 24 & 0xFF).byteValue();
    return arrayOfByte;
  }
  
  private static String order2trace(Order paramOrder)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    AddTraceString(localStringBuffer, paramOrder.getTraderID(), 7);
    AddTraceString(localStringBuffer, paramOrder.getCustomerID(), 8);
    AddTraceString(localStringBuffer, paramOrder.getBuyOrSell().shortValue() == 1 ? "B" : "S", 1);
    AddTraceString(localStringBuffer, paramOrder.getCommodityID(), 8);
    AddTraceString(localStringBuffer, "" + paramOrder.getPrice().intValue(), 6);
    AddTraceString(localStringBuffer, paramOrder.getQuantity().toString(), 5);
    AddTraceString(localStringBuffer, paramOrder.getOrderType().shortValue() == 1 ? "O" : "L", 1);
    AddTraceString(localStringBuffer, "0", 6);
    AddTraceString(localStringBuffer, "", 7);
    return localStringBuffer.toString();
  }
  
  private static String cancel2trace(long paramLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    List localList = localDaoHelper.queryBySQL("select traderid,bs_flag ,CustomerID,CommodityID from t_orders where a_orderno=" + paramLong);
    if (localList.size() > 0)
    {
      Map localMap = (Map)localList.get(0);
      AddTraceString(localStringBuffer, (String)localMap.get("TRADERID"), 7);
      AddTraceString(localStringBuffer, "1", 1);
      AddTraceString(localStringBuffer, ((BigDecimal)localMap.get("BS_FLAG")).intValue() == 1 ? "B" : "S", 1);
      AddTraceString(localStringBuffer, "" + paramLong, 6);
      AddTraceString(localStringBuffer, (String)localMap.get("CUSTOMERID"), 8);
      AddTraceString(localStringBuffer, (String)localMap.get("COMMODITYID"), 8);
      AddTraceString(localStringBuffer, (String)localMap.get("TRADERID"), 7);
      AddTraceString(localStringBuffer, "", 5);
    }
    return localStringBuffer.toString();
  }
  
  private static void AddTraceString(StringBuffer paramStringBuffer, String paramString, int paramInt)
  {
    if (paramString.length() > paramInt) {
      paramString = paramString.substring(0, paramInt - 1);
    }
    paramStringBuffer.append(paramString);
    for (int i = 0; i < paramInt - paramString.length(); i++) {
      paramStringBuffer.append(' ');
    }
  }
  
  public static List<Order> readTrace()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream("trace4read");
      DataInputStream localDataInputStream = new DataInputStream(localFileInputStream);
      int i = localDataInputStream.available();
      int j = i / 61;
      System.out.println("***** order num:" + j);
      for (int k = 0; k < j; k++)
      {
        localDataInputStream.skipBytes(12);
        byte[] arrayOfByte = new byte[49];
        localDataInputStream.read(arrayOfByte);
        Order localOrder = new Order();
        int m = 0;
        int n = m + 7;
        localOrder.setTraderID(ParseBytesString(arrayOfByte, m, n));
        m = n;
        n = m + 8;
        localOrder.setCustomerID(ParseBytesString(arrayOfByte, m, n));
        m = n;
        n = m + 1;
        localOrder.setBuyOrSell(Short.valueOf((short)(ParseBytesString(arrayOfByte, m, n).equals("S") ? 2 : 1)));
        m = n;
        n = m + 8;
        localOrder.setCommodityID(ParseBytesString(arrayOfByte, m, n));
        m = n;
        n = m + 6;
        localOrder.setPrice(Double.valueOf(ParseBytesDouble(arrayOfByte, m, n)));
        m = n;
        n = m + 5;
        localOrder.setQuantity(Long.valueOf(ParseBytesLong(arrayOfByte, m, n)));
        m = n;
        n = m + 1;
        localOrder.setOrderType(Short.valueOf((short)(ParseBytesString(arrayOfByte, m, n).equals("L") ? 2 : 1)));
        localOrder.setCloseFlag(Short.valueOf((short)0));
        localOrder.setFirmID(localOrder.getCustomerID().substring(0, 4));
        localOrder.setStatus(Short.valueOf((short)1));
        localOrder.setOrderNo(new Long(k + 1));
        localArrayList.add(localOrder);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localArrayList;
  }
  
  private static String ParseBytesString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2 - paramInt1];
    for (int i = paramInt1; i < paramInt2; i++) {
      arrayOfByte[(i - paramInt1)] = paramArrayOfByte[i];
    }
    return new String(arrayOfByte).trim();
  }
  
  private static long ParseBytesLong(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l = Long.parseLong(ParseBytesString(paramArrayOfByte, paramInt1, paramInt2));
    return l;
  }
  
  private static double ParseBytesDouble(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    double d;
    try
    {
      d = Double.parseDouble(ParseBytesString(paramArrayOfByte, paramInt1, paramInt2));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      d = 0.0D;
    }
    return d;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    readTrace();
  }
}
