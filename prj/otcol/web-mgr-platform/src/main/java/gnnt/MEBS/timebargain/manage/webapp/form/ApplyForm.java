package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ApplyForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private String applyID;
  private String uni_Cmdty_Code;
  private String firmID_S;
  private String customerID_S;
  private String billID;
  private String quantity;
  private String applyType;
  private String status;
  private String createTime;
  private String creator;
  private String remark1;
  private String modifyTime;
  private String modifier;
  private String remark2;
  private String firmID_B;
  private String customerID_B;
  private String price;
  private String crud = "";
  private String type = "";
  private String commodityID;
  private String billType;
  
  public String getBillType()
  {
    return this.billType;
  }
  
  public void setBillType(String paramString)
  {
    this.billType = paramString;
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
  
  public String getApplyID()
  {
    return this.applyID;
  }
  
  public void setApplyID(String paramString)
  {
    this.applyID = paramString;
  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(String paramString)
  {
    this.applyType = paramString;
  }
  
  public String getBillID()
  {
    return this.billID;
  }
  
  public void setBillID(String paramString)
  {
    this.billID = paramString;
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
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setPrice(String paramString)
  {
    this.price = paramString;
  }
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(String paramString)
  {
    this.quantity = paramString;
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
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
}
