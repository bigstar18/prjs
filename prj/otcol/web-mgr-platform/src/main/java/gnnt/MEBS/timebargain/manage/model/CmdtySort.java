package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CmdtySort
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049822L;
  private Long sortID;
  private String sortName;
  private Long maxHoldQty;
  private Long groupID;
  private String customerID;
  private Date modifyTime;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof CmdtySort)) {
      return false;
    }
    CmdtySort localCmdtySort = (CmdtySort)paramObject;
    return this.sortID != null ? this.sortID.equals(localCmdtySort.sortID) : localCmdtySort.sortID == null;
  }
  
  public int hashCode()
  {
    return this.sortID != null ? this.sortID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public Long getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(Long paramLong)
  {
    this.groupID = paramLong;
  }
  
  public Long getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(Long paramLong)
  {
    this.maxHoldQty = paramLong;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public Long getSortID()
  {
    return this.sortID;
  }
  
  public void setSortID(Long paramLong)
  {
    this.sortID = paramLong;
  }
  
  public String getSortName()
  {
    return this.sortName;
  }
  
  public void setSortName(String paramString)
  {
    this.sortName = paramString;
  }
}
