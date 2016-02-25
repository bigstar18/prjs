package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.jdbc.core.RowMapper;

public class ApplyGage
  extends BaseObject
  implements Serializable, RowMapper
{
  private static final long serialVersionUID = 1L;
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
  
  public long getApplyId()
  {
    return this.applyId;
  }
  
  public void setApplyId(long paramLong)
  {
    this.applyId = paramLong;
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
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Trader)) {
      return false;
    }
    ApplyGage localApplyGage = (ApplyGage)paramObject;
    return this.applyId + "" != null ? (this.applyId + "").equals(localApplyGage.applyId + "") : localApplyGage.applyId + "" == null;
  }
  
  public int hashCode()
  {
    return this.applyId + "" != null ? (this.applyId + "").hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Object mapRow(ResultSet paramResultSet, int paramInt)
    throws SQLException
  {
    return rsToApplyGage(paramResultSet);
  }
  
  private ApplyGage rsToApplyGage(ResultSet paramResultSet)
    throws SQLException
  {
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setApplyId(paramResultSet.getLong("APPLYID"));
    localApplyGage.setCommodityID(paramResultSet.getString("COMMODITYID"));
    localApplyGage.setFirmID(paramResultSet.getString("FIRMID"));
    localApplyGage.setCustomerID(paramResultSet.getString("CUSTOMERID"));
    localApplyGage.setQuantity(paramResultSet.getLong("QUANTITY"));
    localApplyGage.setApplyType(paramResultSet.getInt("APPLYTYPE"));
    localApplyGage.setStatus(paramResultSet.getInt("STATUS"));
    localApplyGage.setCreateTime(paramResultSet.getDate("CREATETIME"));
    localApplyGage.setCreator(paramResultSet.getString("CREATOR"));
    localApplyGage.setRemark1(paramResultSet.getString("REMARK1"));
    localApplyGage.setModifyTime(paramResultSet.getDate("MODIFYTIME"));
    localApplyGage.setModifier(paramResultSet.getString("MODIFIER"));
    localApplyGage.setRemark2(paramResultSet.getString("REMARK2"));
    return localApplyGage;
  }
}
