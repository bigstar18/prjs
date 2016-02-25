package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class ReportForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049819L;
  private String beginCustomerID;
  private String endCustomerID;
  private String stopDot;
  private String beginCmdty;
  private String endCmdty;
  private String beginDate;
  private String endDate;
  private String crud = "";
  private String firmID;
  private String firmName;
  private String commodityID;
  private String clearDate;
  
  public String getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(String paramString)
  {
    this.clearDate = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.firmName = paramString;
  }
  
  public String getBeginCmdty()
  {
    return this.beginCmdty;
  }
  
  public void setBeginCmdty(String paramString)
  {
    this.beginCmdty = paramString;
  }
  
  public String getBeginCustomerID()
  {
    return this.beginCustomerID;
  }
  
  public void setBeginCustomerID(String paramString)
  {
    this.beginCustomerID = paramString;
  }
  
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
  
  public String getEndCmdty()
  {
    return this.endCmdty;
  }
  
  public void setEndCmdty(String paramString)
  {
    this.endCmdty = paramString;
  }
  
  public String getEndCustomerID()
  {
    return this.endCustomerID;
  }
  
  public void setEndCustomerID(String paramString)
  {
    this.endCustomerID = paramString;
  }
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String paramString)
  {
    this.endDate = paramString;
  }
  
  public String getStopDot()
  {
    return this.stopDot;
  }
  
  public void setStopDot(String paramString)
  {
    this.stopDot = paramString;
  }
}
