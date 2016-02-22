package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QuotePoint_RT
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654059813L;
  public String commodityID;
  public String m_FirmID;
  public double quotePoint_B;
  public double quotePoint_S;
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getM_FirmID()
  {
    return this.m_FirmID;
  }
  
  public void setM_FirmID(String firmID)
  {
    this.m_FirmID = firmID;
  }
  
  public double getQuotePoint_B()
  {
    return this.quotePoint_B;
  }
  
  public void setQuotePoint_B(double quotePoint_B)
  {
    this.quotePoint_B = quotePoint_B;
  }
  
  public double getQuotePoint_S()
  {
    return this.quotePoint_S;
  }
  
  public void setQuotePoint_S(double quotePoint_S)
  {
    this.quotePoint_S = quotePoint_S;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
