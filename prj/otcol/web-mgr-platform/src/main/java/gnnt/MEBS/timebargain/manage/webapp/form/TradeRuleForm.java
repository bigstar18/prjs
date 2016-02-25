package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class TradeRuleForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private String marketCode;
  private String groupID;
  private String customerID;
  private String FeeDiscountRate;
  private String MarginDiscountRate;
  private String crud = "";
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getFeeDiscountRate()
  {
    return this.FeeDiscountRate;
  }
  
  public void setFeeDiscountRate(String paramString)
  {
    this.FeeDiscountRate = paramString;
  }
  
  public String getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.groupID = paramString;
  }
  
  public String getMarginDiscountRate()
  {
    return this.MarginDiscountRate;
  }
  
  public void setMarginDiscountRate(String paramString)
  {
    this.MarginDiscountRate = paramString;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }
}
