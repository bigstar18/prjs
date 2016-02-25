package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class StatQuery
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private Integer id;
  private Integer groupID;
  private String customerID;
  private String customerName;
  private String marketCode;
  private String uni_Cmdty_Code;
  private Short bS_Flag;
  private Short orderType;
  private Short status;
  private Boolean isQryHis;
  private Date beginDate;
  private Date endDate;
  private String firmID;
  private String firmName;
  private String name;
  private String commodityID;
  private Long holdQty;
  private Long settleQty;
  private Long fellbackQty;
  private Long gageQty;
  private Double price;
  private String bCustomerID;
  private Long bHoldQty;
  private Long bGageQty;
  private String sCustomerID;
  private Long sHoldQty;
  private Long sGageQty;
  private String userID;
  private Short logType;
  private String didingType;
  private String quantity;
  private String year;
  private String month;
  private String billID;
  private Double billFund;
  private String commodityName;
  private String createTime;
  private String creator;
  private String modifyTime;
  private String modifier;
  private String settleProcessDate;
  private Short settleType;
  private String day;
  
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String paramString)
  {
    this.day = paramString;
  }
  
  public Short getSettleType()
  {
    return this.settleType;
  }
  
  public void setSettleType(Short paramShort)
  {
    this.settleType = paramShort;
  }
  
  public String getSettleProcessDate()
  {
    return this.settleProcessDate;
  }
  
  public void setSettleProcessDate(String paramString)
  {
    this.settleProcessDate = paramString;
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
  
  public String getBCustomerID()
  {
    return this.bCustomerID;
  }
  
  public void setBCustomerID(String paramString)
  {
    this.bCustomerID = paramString;
  }
  
  public Long getBGageQty()
  {
    return this.bGageQty;
  }
  
  public void setBGageQty(Long paramLong)
  {
    this.bGageQty = paramLong;
  }
  
  public Long getBHoldQty()
  {
    return this.bHoldQty;
  }
  
  public void setBHoldQty(Long paramLong)
  {
    this.bHoldQty = paramLong;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public String getSCustomerID()
  {
    return this.sCustomerID;
  }
  
  public void setSCustomerID(String paramString)
  {
    this.sCustomerID = paramString;
  }
  
  public Long getSGageQty()
  {
    return this.sGageQty;
  }
  
  public void setSGageQty(Long paramLong)
  {
    this.sGageQty = paramLong;
  }
  
  public Long getSHoldQty()
  {
    return this.sHoldQty;
  }
  
  public void setSHoldQty(Long paramLong)
  {
    this.sHoldQty = paramLong;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public Long getFellbackQty()
  {
    return this.fellbackQty;
  }
  
  public void setFellbackQty(Long paramLong)
  {
    this.fellbackQty = paramLong;
  }
  
  public Long getGageQty()
  {
    return this.gageQty;
  }
  
  public void setGageQty(Long paramLong)
  {
    this.gageQty = paramLong;
  }
  
  public Long getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(Long paramLong)
  {
    this.holdQty = paramLong;
  }
  
  public Long getSettleQty()
  {
    return this.settleQty;
  }
  
  public void setSettleQty(Long paramLong)
  {
    this.settleQty = paramLong;
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
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof StatQuery)) {
      return false;
    }
    StatQuery localStatQuery = (StatQuery)paramObject;
    return this.id != null ? this.id.equals(localStatQuery.id) : localStatQuery.id == null;
  }
  
  public int hashCode()
  {
    return this.id != null ? this.id.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Date getBeginDate()
  {
    return this.beginDate;
  }
  
  public void setBeginDate(Date paramDate)
  {
    this.beginDate = paramDate;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public Date getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(Date paramDate)
  {
    this.endDate = paramDate;
  }
  
  public Integer getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(Integer paramInteger)
  {
    this.groupID = paramInteger;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer paramInteger)
  {
    this.id = paramInteger;
  }
  
  public Boolean getIsQryHis()
  {
    return this.isQryHis;
  }
  
  public void setIsQryHis(Boolean paramBoolean)
  {
    this.isQryHis = paramBoolean;
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
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.status = paramShort;
  }
  
  public Short getBS_Flag()
  {
    return this.bS_Flag;
  }
  
  public void setBS_Flag(Short paramShort)
  {
    this.bS_Flag = paramShort;
  }
  
  public Short getOrderType()
  {
    return this.orderType;
  }
  
  public void setOrderType(Short paramShort)
  {
    this.orderType = paramShort;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public Short getLogType()
  {
    return this.logType;
  }
  
  public void setLogType(Short paramShort)
  {
    this.logType = paramShort;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public Double getBillFund()
  {
    return this.billFund;
  }
  
  public void setBillFund(Double paramDouble)
  {
    this.billFund = paramDouble;
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
  
  public String getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(String paramString)
  {
    this.createTime = paramString;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
}
