package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class AgencyForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049815L;
  private String type;
  private String marketID;
  private String customerID;
  private int marketStatus;
  private String recoverTime;
  private String commodityID;
  private String marginRate_B;
  private String marginRate_S;
  private String marginAssure_B;
  private String marginAssure_S;
  private String marginAlgr;
  
  public String getMarginAlgr()
  {
    return this.marginAlgr;
  }
  
  public void setMarginAlgr(String paramString)
  {
    this.marginAlgr = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public String getMarginAssure_B()
  {
    return this.marginAssure_B;
  }
  
  public void setMarginAssure_B(String paramString)
  {
    this.marginAssure_B = paramString;
  }
  
  public String getMarginAssure_S()
  {
    return this.marginAssure_S;
  }
  
  public void setMarginAssure_S(String paramString)
  {
    this.marginAssure_S = paramString;
  }
  
  public String getMarginRate_B()
  {
    return this.marginRate_B;
  }
  
  public void setMarginRate_B(String paramString)
  {
    this.marginRate_B = paramString;
  }
  
  public String getMarginRate_S()
  {
    return this.marginRate_S;
  }
  
  public void setMarginRate_S(String paramString)
  {
    this.marginRate_S = paramString;
  }
  
  public String getRecoverTime()
  {
    return this.recoverTime;
  }
  
  public void setRecoverTime(String paramString)
  {
    this.recoverTime = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getMarketID()
  {
    return this.marketID;
  }
  
  public void setMarketID(String paramString)
  {
    this.marketID = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public int getMarketStatus()
  {
    return this.marketStatus;
  }
  
  public void setMarketStatus(int paramInt)
  {
    this.marketStatus = paramInt;
  }
}
