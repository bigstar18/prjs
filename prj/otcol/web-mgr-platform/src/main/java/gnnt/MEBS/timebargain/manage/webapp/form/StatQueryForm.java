package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class StatQueryForm
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
  private String firmID;
  private String firmName;
  private String crud = "";
  private String name;
  private String commodityID;
  private String holdQty;
  private String settleQty;
  private String fellbackQty;
  private String gageQty;
  private String price;
  private String bCustomerID;
  private String bHoldQty;
  private String bGageQty;
  private String sCustomerID;
  private String sHoldQty;
  private String sGageQty;
  private String userID;
  private String logType;
  private String didingType;
  private String quantity;
  private String year;
  private String month;
  private String billID;
  private String billFund;
  private String commodityName;
  private String createTime;
  private String creator;
  private String modifyTime;
  private String modifier;
  private String settleProcessDate;
  private String settleType;
  private String day;
  
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String paramString)
  {
    this.day = paramString;
  }
  
  public String getSettleType()
  {
    return this.settleType;
  }
  
  public void setSettleType(String paramString)
  {
    this.settleType = paramString;
  }
  
  public String getSettleProcessDate()
  {
    return this.settleProcessDate;
  }
  
  public void setSettleProcessDate(String paramString)
  {
    this.settleProcessDate = paramString;
  }
  
  public String getBillFund()
  {
    return this.billFund;
  }
  
  public void setBillFund(String paramString)
  {
    this.billFund = paramString;
  }
  
  public String getBillID()
  {
    return this.billID;
  }
  
  public void setBillID(String paramString)
  {
    this.billID = paramString;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String paramString)
  {
    this.commodityName = paramString;
  }
  
  public String getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(String paramString)
  {
    this.createTime = paramString;
  }
  
  public String getCreator()
  {
    return this.creator;
  }
  
  public void setCreator(String paramString)
  {
    this.creator = paramString;
  }
  
  public String getModifier()
  {
    return this.modifier;
  }
  
  public void setModifier(String paramString)
  {
    this.modifier = paramString;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
  
  public String getMonth()
  {
    return this.month;
  }
  
  public void setMonth(String paramString)
  {
    this.month = paramString;
  }
  
  public String getYear()
  {
    return this.year;
  }
  
  public void setYear(String paramString)
  {
    this.year = paramString;
  }
  
  public String getbCustomerID()
  {
    return this.bCustomerID;
  }
  
  public void setbCustomerID(String paramString)
  {
    this.bCustomerID = paramString;
  }
  
  public String getbGageQty()
  {
    return this.bGageQty;
  }
  
  public void setbGageQty(String paramString)
  {
    this.bGageQty = paramString;
  }
  
  public String getbHoldQty()
  {
    return this.bHoldQty;
  }
  
  public void setbHoldQty(String paramString)
  {
    this.bHoldQty = paramString;
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setPrice(String paramString)
  {
    this.price = paramString;
  }
  
  public String getsCustomerID()
  {
    return this.sCustomerID;
  }
  
  public void setsCustomerID(String paramString)
  {
    this.sCustomerID = paramString;
  }
  
  public String getsGageQty()
  {
    return this.sGageQty;
  }
  
  public void setsGageQty(String paramString)
  {
    this.sGageQty = paramString;
  }
  
  public String getsHoldQty()
  {
    return this.sHoldQty;
  }
  
  public void setsHoldQty(String paramString)
  {
    this.sHoldQty = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public String getFellbackQty()
  {
    return this.fellbackQty;
  }
  
  public void setFellbackQty(String paramString)
  {
    this.fellbackQty = paramString;
  }
  
  public String getGageQty()
  {
    return this.gageQty;
  }
  
  public void setGageQty(String paramString)
  {
    this.gageQty = paramString;
  }
  
  public String getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(String paramString)
  {
    this.holdQty = paramString;
  }
  
  public String getSettleQty()
  {
    return this.settleQty;
  }
  
  public void setSettleQty(String paramString)
  {
    this.settleQty = paramString;
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
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getDidingType()
  {
    return this.didingType;
  }
  
  public void setDidingType(String paramString)
  {
    this.didingType = paramString;
  }
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(String paramString)
  {
    this.quantity = paramString;
  }
  
  public String getLogType()
  {
    return this.logType;
  }
  
  public void setLogType(String paramString)
  {
    this.logType = paramString;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
}
