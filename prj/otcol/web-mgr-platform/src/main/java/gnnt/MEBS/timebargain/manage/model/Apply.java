package gnnt.MEBS.timebargain.manage.model;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Apply
{
  private static final long serialVersionUID = 3690197650654049927L;
  private Long applyID;
  private String uni_Cmdty_Code;
  private String firmID_S;
  private String customerID_S;
  private String billID;
  private Long quantity;
  private Short applyType;
  private Short status;
  private Date createTime;
  private String creator;
  private String remark1;
  private Date modifyTime;
  private String modifier;
  private String remark2;
  private String firmID_B;
  private String customerID_B;
  private Double price;
  private String crud = "";
  private String commodityID;
  private Short billType;
  private Short validID;
  
  public Short getBillType()
  {
    return this.billType;
  }
  
  public void setBillType(Short paramShort)
  {
    this.billType = paramShort;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Long getApplyID()
  {
    return this.applyID;
  }
  
  public void setApplyID(Long paramLong)
  {
    this.applyID = paramLong;
  }
  
  public Short getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(Short paramShort)
  {
    this.applyType = paramShort;
  }
  
  public String getBillID()
  {
    return this.billID;
  }
  
  public void setBillID(String paramString)
  {
    this.billID = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getCreator()
  {
    return this.creator;
  }
  
  public void setCreator(String paramString)
  {
    this.creator = paramString;
  }
  
  public String getCustomerID_B()
  {
    return this.customerID_B;
  }
  
  public void setCustomerID_B(String paramString)
  {
    this.customerID_B = paramString;
  }
  
  public String getCustomerID_S()
  {
    return this.customerID_S;
  }
  
  public void setCustomerID_S(String paramString)
  {
    this.customerID_S = paramString;
  }
  
  public String getFirmID_B()
  {
    return this.firmID_B;
  }
  
  public void setFirmID_B(String paramString)
  {
    this.firmID_B = paramString;
  }
  
  public String getFirmID_S()
  {
    return this.firmID_S;
  }
  
  public void setFirmID_S(String paramString)
  {
    this.firmID_S = paramString;
  }
  
  public String getModifier()
  {
    return this.modifier;
  }
  
  public void setModifier(String paramString)
  {
    this.modifier = paramString;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public Long getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Long paramLong)
  {
    this.quantity = paramLong;
  }
  
  public String getRemark1()
  {
    return this.remark1;
  }
  
  public void setRemark1(String paramString)
  {
    this.remark1 = paramString;
  }
  
  public String getRemark2()
  {
    return this.remark2;
  }
  
  public void setRemark2(String paramString)
  {
    this.remark2 = paramString;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.status = paramShort;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public Short getValidID()
  {
    return this.validID;
  }
  
  public void setValidID(Short paramShort)
  {
    this.validID = paramShort;
  }
}
