package gnnt.MEBS.timebargain.tradeweb.model;

import gnnt.MEBS.timebargain.tradeweb.util.Arith;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.CIOUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Response
{
  private static final Log log = LogFactory.getLog(Response.class);
  private long retCode;
  private String message;
  private short cmd;
  
  protected void setCMD(short cmd)
  {
    this.cmd = cmd;
  }
  
  public short getCMD()
  {
    return this.cmd;
  }
  
  public long getRetCode()
  {
    return this.retCode;
  }
  
  public void setRetCode(long retCode)
  {
    this.retCode = retCode;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public static void RenderBinary(HttpServletResponse response, Response responseVO)
  {
    ByteArrayOutputStream array = null;
    DataOutputStream os = null;
    try
    {
      array = new ByteArrayOutputStream();
      os = new DataOutputStream(array);
      
      CIOUtil.writeShort(os, responseVO.getCMD());
      CIOUtil.writeLong(os, responseVO.getRetCode());
      CIOUtil.writeUTF(os, responseVO.getMessage());
      switch (responseVO.getCMD())
      {
      case 99: 
        break;
      case 1: 
        List list = ((ResponseCDQ)responseVO).getLst();
        if ((list == null) || (list.size() == 0))
        {
          CIOUtil.writeByte(os, (byte)0);
        }
        else
        {
          CIOUtil.writeByte(os, (byte)list.size());
          for (int i = 0; i < list.size(); i++)
          {
            Map map = (Map)list.get(i);
            double highPrice = ((BigDecimal)map.get("HighPrice"))
              .doubleValue();
            double lowPrice = ((BigDecimal)map.get("LowPrice"))
              .doubleValue();
            double curPrice = ((BigDecimal)map.get("CurPrice"))
              .doubleValue();
            double quotePoint_B = 
              ((BigDecimal)map.get("QuotePoint_B_RMB")).doubleValue();
            double quotePoint_S = 
              ((BigDecimal)map.get("QuotePoint_S_RMB")).doubleValue();
            quotePoint_B += curPrice;
            quotePoint_S = curPrice - quotePoint_S;
            int scale = Arith.getScale(
              ((BigDecimal)map.get("MinPriceMove")).doubleValue());
            CIOUtil.writeUTF(os, (String)map.get("COMMODITYID"));
            CIOUtil.writeDouble(os, Arith.format(highPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(lowPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(curPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(quotePoint_B, 
              scale));
            CIOUtil.writeDouble(os, Arith.format(quotePoint_S, 
              scale));
            CIOUtil.writeUTF(os, map.get("CreateTime") == null ? "" : 
              DateUtil.getDateTime(
              (Date)map.get("CreateTime")));
          }
        }
        break;
      case 2: 
        ResponseSTQ responseSTQ = (ResponseSTQ)responseVO;
        CIOUtil.writeUTF(os, responseSTQ.getCurTime());
        CIOUtil.writeUTF(os, responseSTQ.getCurDate());
        CIOUtil.writeLong(os, responseSTQ.getCurrentTimeMillis());
        CIOUtil.writeLong(os, responseSTQ.getLastID());
        CIOUtil.writeByte(os, responseSTQ.getNewTrade());
        CIOUtil.writeLong(os, responseSTQ.getTradeTotalCount());
        CIOUtil.writeUTF(os, responseSTQ.getTradeDate());
        if (responseSTQ.getNewTrade() == 0)
        {
          CIOUtil.writeInt(os, 0);
        }
        else
        {
          List<Trade> tradeList = responseSTQ.getLst();
          if ((tradeList == null) || (tradeList.size() == 0))
          {
            CIOUtil.writeInt(os, 0);
          }
          else
          {
            CIOUtil.writeInt(os, tradeList.size());
            for (int i = 0; i < tradeList.size(); i++)
            {
              Trade trade = (Trade)tradeList.get(i);
              CIOUtil.writeLong(os, trade.getOrderNo().longValue());
              CIOUtil.writeUTF(os, trade.getCommodityID());
              CIOUtil.writeLong(os, trade.getQuantity().longValue());
              CIOUtil.writeShort(os, trade.getOrderType().shortValue());
              CIOUtil.writeShort(os, trade.getBS_Flag().shortValue());
              CIOUtil.writeShort(os, trade.getTradeType()
                .shortValue());
            }
          }
        }
        CIOUtil.writeByte(os, (byte)responseSTQ.getSysStatus());
        break;
      case 3: 
        List msdqList = ((ResponseMCDQ)responseVO).getLst();
        if ((msdqList == null) || (msdqList.size() == 0))
        {
          CIOUtil.writeByte(os, (byte)0);
        }
        else
        {
          CIOUtil.writeByte(os, (byte)msdqList.size());
          for (int i = 0; i < msdqList.size(); i++)
          {
            Map map = (Map)msdqList.get(i);
            double highPrice = ((BigDecimal)map.get("HighPrice"))
              .doubleValue();
            double lowPrice = ((BigDecimal)map.get("LowPrice"))
              .doubleValue();
            double curPrice = ((BigDecimal)map.get("CurPrice"))
              .doubleValue();
            double quotePoint_B = 
              ((BigDecimal)map.get("QuotePoint_B_RMB")).doubleValue();
            double quotePoint_S = 
              ((BigDecimal)map.get("QuotePoint_S_RMB")).doubleValue();
            double mQuotePoint_B = 
              ((BigDecimal)map.get("M_QuotePoint_B_RMB")).doubleValue();
            double mQuotePoint_S = 
              ((BigDecimal)map.get("M_QuotePoint_S_RMB")).doubleValue();
            
            int scale = Arith.getScale(
              ((BigDecimal)map.get("MinPriceMove")).doubleValue());
            CIOUtil.writeUTF(os, (String)map.get("COMMODITYID"));
            CIOUtil.writeDouble(os, Arith.format(highPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(lowPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(curPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(curPrice, scale));
            CIOUtil.writeDouble(os, Arith.format(mQuotePoint_B, 
              scale));
            CIOUtil.writeDouble(os, Arith.format(mQuotePoint_S, 
              scale));
            CIOUtil.writeDouble(os, Arith.format(quotePoint_B, 
              scale));
            CIOUtil.writeDouble(os, Arith.format(quotePoint_S, 
              scale));
            CIOUtil.writeUTF(os, map.get("CreateTime") == null ? "" : 
              DateUtil.getDateTime(
              (Date)map.get("CreateTime")));
          }
        }
        break;
      }
      os.flush();
      response.getOutputStream().write(array.toByteArray());
    }
    catch (IOException e)
    {
      log.error("RenderBinary occur IOException,IOException info=" + 
        e.getMessage());
      e.printStackTrace();
      if (os != null) {
        try
        {
          os.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          log.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          log.warn("array.close occur IOException:" + e.getMessage());
        }
      }
    }
    catch (Exception e)
    {
      log.error("RenderBinary occur Exception,Exception info=" + 
        e.getMessage());
      e.printStackTrace();
      if (os != null) {
        try
        {
          os.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          log.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          log.warn("array.close occur IOException:" + e.getMessage());
        }
      }
    }
    finally
    {
      if (os != null) {
        try
        {
          os.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          log.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          log.warn("array.close occur IOException:" + e.getMessage());
        }
      }
    }
  }
}
