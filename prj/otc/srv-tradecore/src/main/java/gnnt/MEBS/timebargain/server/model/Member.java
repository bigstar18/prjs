package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Member
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private String m_FirmID;
  private Map<String, QuotePoint_RT> quotePointMap;
  private Map<String, Quotation_RT> quotationMap;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getM_FirmID()
  {
    return this.m_FirmID;
  }
  
  public void setM_FirmID(String firmID)
  {
    this.m_FirmID = firmID;
  }
  
  public Map<String, QuotePoint_RT> getQuotePointMap()
  {
    return this.quotePointMap;
  }
  
  public void setQuotePointMap(Map<String, QuotePoint_RT> quotePointMap)
  {
    this.quotePointMap = quotePointMap;
  }
  
  public Map<String, Quotation_RT> getQuotationMap()
  {
    return this.quotationMap;
  }
  
  public void setQuotationMap(Map<String, Quotation_RT> quotationMap)
  {
    this.quotationMap = quotationMap;
  }
}
