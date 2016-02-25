package gnnt.MEBS.vendue.server.beans.dtobeans;

import gnnt.MEBS.vendue.server.beans.busbeans.CountTime;
import java.sql.Timestamp;

public class QuotationValue
  implements Comparable
{
  private Long tradePartition;
  private Long id;
  private Long submitId;
  private Long section;
  private String code;
  private Double price;
  private Long validAmount;
  private String userID;
  private Timestamp lastTime;
  private Timestamp countDownStart;
  private Long countDownTime;
  private CountTime countTime;
  private CurCommodity curCommodity;
  private Commodity commodity;
  private StringBuffer xmlStringBuffer;
  private StringBuffer xmlStringBuffer1;
  private StringBuffer xmlStringBuffer2;
  
  public StringBuffer getXmlStringBuffer1()
  {
    return this.xmlStringBuffer1;
  }
  
  public void setXmlStringBuffer1(StringBuffer paramStringBuffer)
  {
    this.xmlStringBuffer1 = paramStringBuffer;
  }
  
  public StringBuffer getXmlStringBuffer2()
  {
    return this.xmlStringBuffer2;
  }
  
  public void setXmlStringBuffer2(StringBuffer paramStringBuffer)
  {
    this.xmlStringBuffer2 = paramStringBuffer;
  }
  
  public CountTime getCountTime()
  {
    return this.countTime;
  }
  
  public void setCountTime(CountTime paramCountTime)
  {
    this.countTime = paramCountTime;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public Timestamp getCountDownStart()
  {
    return this.countDownStart;
  }
  
  public void setCountDownStart(Timestamp paramTimestamp)
  {
    this.countDownStart = paramTimestamp;
  }
  
  public Long getCountDownTime()
  {
    return this.countDownTime;
  }
  
  public void setCountDownTime(Long paramLong)
  {
    this.countDownTime = paramLong;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Timestamp getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(Timestamp paramTimestamp)
  {
    this.lastTime = paramTimestamp;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public Long getSection()
  {
    return this.section;
  }
  
  public void setSection(Long paramLong)
  {
    this.section = paramLong;
  }
  
  public Long getSubmitId()
  {
    return this.submitId;
  }
  
  public void setSubmitId(Long paramLong)
  {
    this.submitId = paramLong;
  }
  
  public Long getTradePartition()
  {
    return this.tradePartition;
  }
  
  public void setTradePartition(Long paramLong)
  {
    this.tradePartition = paramLong;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public Long getValidAmount()
  {
    return this.validAmount;
  }
  
  public void setValidAmount(Long paramLong)
  {
    this.validAmount = paramLong;
  }
  
  public StringBuffer getXmlStringBuffer()
  {
    return this.xmlStringBuffer;
  }
  
  public void setXmlStringBuffer(StringBuffer paramStringBuffer)
  {
    this.xmlStringBuffer = paramStringBuffer;
  }
  
  public Commodity getCommodity()
  {
    return this.commodity;
  }
  
  public void setCommodity(Commodity paramCommodity)
  {
    this.commodity = paramCommodity;
  }
  
  public CurCommodity getCurCommodity()
  {
    return this.curCommodity;
  }
  
  public void setCurCommodity(CurCommodity paramCurCommodity)
  {
    this.curCommodity = paramCurCommodity;
  }
  
  public int compareTo(Object paramObject)
  {
    if ((paramObject instanceof QuotationValue))
    {
      QuotationValue localQuotationValue = (QuotationValue)paramObject;
      return this.id.compareTo(localQuotationValue.id);
    }
    return 1;
  }
}
