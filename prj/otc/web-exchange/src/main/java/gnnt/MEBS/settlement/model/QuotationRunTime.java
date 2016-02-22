package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;

public class QuotationRunTime
  extends Clone
{
  private String commodityId;
  private String m_FirmId;
  private double curPrice_B;
  private double curPrice_S;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getM_FirmId()
  {
    return this.m_FirmId;
  }
  
  public void setM_FirmId(String m_FirmId)
  {
    this.m_FirmId = m_FirmId;
  }
  
  public double getCurPrice_B()
  {
    return this.curPrice_B;
  }
  
  public void setCurPrice_B(double curPrice_B)
  {
    this.curPrice_B = curPrice_B;
  }
  
  public double getCurPrice_S()
  {
    return this.curPrice_S;
  }
  
  public void setCurPrice_S(double curPrice_S)
  {
    this.curPrice_S = curPrice_S;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
