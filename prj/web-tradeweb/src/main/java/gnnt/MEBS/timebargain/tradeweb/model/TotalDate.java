package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TotalDate
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 5792711521142361739L;
  private long totalNum;
  private long totalQty;
  private double totalLiqpl;
  private double totalComm;
  private long totalUnTradeQty;
  private String responseName;
  
  public String getResponseName()
  {
    return this.responseName;
  }
  
  public void setResponseName(String paramString)
  {
    this.responseName = paramString;
  }
  
  public long getTotalNum()
  {
    return this.totalNum;
  }
  
  public void setTotalNum(long paramLong)
  {
    this.totalNum = paramLong;
  }
  
  public long getTotalQty()
  {
    return this.totalQty;
  }
  
  public void setTotalQty(long paramLong)
  {
    this.totalQty = paramLong;
  }
  
  public double getTotalLiqpl()
  {
    return this.totalLiqpl;
  }
  
  public void setTotalLiqpl(double paramDouble)
  {
    this.totalLiqpl = paramDouble;
  }
  
  public double getTotalComm()
  {
    return this.totalComm;
  }
  
  public void setTotalComm(double paramDouble)
  {
    this.totalComm = paramDouble;
  }
  
  public long getTotalUnTradeQty()
  {
    return this.totalUnTradeQty;
  }
  
  public void setTotalUnTradeQty(long paramLong)
  {
    this.totalUnTradeQty = paramLong;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public int hashCode()
  {
    return 1;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Trade)) {
      return false;
    }
    Trade localTrade = (Trade)paramObject;
    return true;
  }
}
