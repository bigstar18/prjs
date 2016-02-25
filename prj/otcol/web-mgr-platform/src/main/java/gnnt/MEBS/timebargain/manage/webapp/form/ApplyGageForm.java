package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ApplyGageForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private long applyId;
  private String commodityID;
  private String firmID;
  private String customerID;
  private long quantity;
  private int applyType;
  private int status;
  private Date createTime;
  private String creator;
  private String remark1;
  private Date modifyTime;
  private String modifier;
  private String remark2;
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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
  
  public int getApplyType()
  {
    return this.applyType;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public long getQuantity()
  {
    return this.quantity;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public long getApplyId()
  {
    return this.applyId;
  }
  
  public void setApplyId(long paramLong)
  {
    this.applyId = paramLong;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public void setApplyType(int paramInt)
  {
    this.applyType = paramInt;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public void setQuantity(long paramLong)
  {
    this.quantity = paramLong;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}
