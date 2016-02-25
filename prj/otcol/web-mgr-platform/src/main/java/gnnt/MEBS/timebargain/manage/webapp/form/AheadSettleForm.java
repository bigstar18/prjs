package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AheadSettleForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private long applyID;
  private String commodityID;
  private String customerID_S;
  private String customerID_B;
  private double price;
  private long quantity;
  private long gageQty;
  private int applyType;
  private int status;
  private Date createTime;
  private String creator;
  private String remark1;
  private Date modifyTime;
  private String modifier;
  private String remark2;
  
  public long getApplyID()
  {
    return this.applyID;
  }
  
  public void setApplyID(long paramLong)
  {
    this.applyID = paramLong;
  }
  
  public int getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(int paramInt)
  {
    this.applyType = paramInt;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
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
  
  public long getGageQty()
  {
    return this.gageQty;
  }
  
  public void setGageQty(long paramLong)
  {
    this.gageQty = paramLong;
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
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public long getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(long paramLong)
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
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
