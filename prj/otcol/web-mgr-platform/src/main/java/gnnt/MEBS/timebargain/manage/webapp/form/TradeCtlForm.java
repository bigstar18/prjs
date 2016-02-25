package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class TradeCtlForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049818L;
  private String id;
  private String groupID;
  private String customerID;
  private String customerName;
  private String marketCode;
  private String uni_Cmdty_Code;
  private String bS_Flag;
  private String orderType;
  private String status;
  private String isQryHis;
  private String beginDate;
  private String endDate;
  private String FirmID;
  private String FirmName;
  private String GageQty;
  private String dh;
  private String crud = "";
  
  public String getBeginDate()
  {
    return this.beginDate;
  }
  
  public void setBeginDate(String paramString)
  {
    this.beginDate = paramString;
  }
  
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
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String paramString)
  {
    this.endDate = paramString;
  }
  
  public String getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.groupID = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getIsQryHis()
  {
    return this.isQryHis;
  }
  
  public void setIsQryHis(String paramString)
  {
    this.isQryHis = paramString;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String paramString)
  {
    this.customerName = paramString;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getBS_Flag()
  {
    return this.bS_Flag;
  }
  
  public void setBS_Flag(String paramString)
  {
    this.bS_Flag = paramString;
  }
  
  public String getOrderType()
  {
    return this.orderType;
  }
  
  public void setOrderType(String paramString)
  {
    this.orderType = paramString;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public String getFirmName()
  {
    return this.FirmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.FirmName = paramString;
  }
  
  public String getGageQty()
  {
    return this.GageQty;
  }
  
  public void setGageQty(String paramString)
  {
    this.GageQty = paramString;
  }
  
  public String getDh()
  {
    return this.dh;
  }
  
  public void setDh(String paramString)
  {
    this.dh = paramString;
  }
}
