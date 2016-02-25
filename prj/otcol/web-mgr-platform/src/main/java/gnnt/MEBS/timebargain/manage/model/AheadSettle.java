package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.jdbc.core.RowMapper;

public class AheadSettle
  extends BaseObject
  implements Serializable, RowMapper
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
  
  public void setApplyID(long paramLong)
  {
    this.applyID = paramLong;
  }
  
  public void setApplyType(int paramInt)
  {
    this.applyType = paramInt;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public void setCreator(String paramString)
  {
    this.creator = paramString;
  }
  
  public void setCustomerID_B(String paramString)
  {
    this.customerID_B = paramString;
  }
  
  public void setCustomerID_S(String paramString)
  {
    this.customerID_S = paramString;
  }
  
  public void setGageQty(long paramLong)
  {
    this.gageQty = paramLong;
  }
  
  public void setModifier(String paramString)
  {
    this.modifier = paramString;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public void setQuantity(long paramLong)
  {
    this.quantity = paramLong;
  }
  
  public void setRemark1(String paramString)
  {
    this.remark1 = paramString;
  }
  
  public void setRemark2(String paramString)
  {
    this.remark2 = paramString;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public long getApplyID()
  {
    return this.applyID;
  }
  
  public int getApplyType()
  {
    return this.applyType;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public String getCreator()
  {
    return this.creator;
  }
  
  public String getCustomerID_B()
  {
    return this.customerID_B;
  }
  
  public String getCustomerID_S()
  {
    return this.customerID_S;
  }
  
  public long getGageQty()
  {
    return this.gageQty;
  }
  
  public String getModifier()
  {
    return this.modifier;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public long getQuantity()
  {
    return this.quantity;
  }
  
  public String getRemark1()
  {
    return this.remark1;
  }
  
  public String getRemark2()
  {
    return this.remark2;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Trader)) {
      return false;
    }
    AheadSettle localAheadSettle = (AheadSettle)paramObject;
    return this.applyID + "" != null ? (this.applyID + "").equals(localAheadSettle.applyID + "") : localAheadSettle.applyID + "" == null;
  }
  
  public int hashCode()
  {
    return this.applyID + "" != null ? (this.applyID + "").hashCode() : 0;
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
  
  private AheadSettle rsToApplyGage(ResultSet paramResultSet)
    throws SQLException
  {
    AheadSettle localAheadSettle = new AheadSettle();
    localAheadSettle.setApplyID(paramResultSet.getLong("APPLYID"));
    localAheadSettle.setCommodityID(paramResultSet.getString("COMMODITYID"));
    localAheadSettle.setCustomerID_S(paramResultSet.getString("CUSTOMERID_S"));
    localAheadSettle.setCustomerID_B(paramResultSet.getString("CUSTOMERID_B"));
    localAheadSettle.setPrice(paramResultSet.getDouble("PRICE"));
    localAheadSettle.setQuantity(paramResultSet.getLong("QUANTITY"));
    localAheadSettle.setGageQty(paramResultSet.getLong("GAGEQTY"));
    localAheadSettle.setApplyType(paramResultSet.getInt("APPLYTYPE"));
    localAheadSettle.setStatus(paramResultSet.getInt("STATUS"));
    localAheadSettle.setCreateTime(paramResultSet.getDate("CREATETIME"));
    localAheadSettle.setCreator(paramResultSet.getString("CREATOR"));
    localAheadSettle.setRemark1(paramResultSet.getString("REMARK1"));
    localAheadSettle.setModifyTime(paramResultSet.getDate("MODIFYTIME"));
    localAheadSettle.setModifier(paramResultSet.getString("MODIFIER"));
    localAheadSettle.setRemark2(paramResultSet.getString("REMARK2"));
    return localAheadSettle;
  }
}
