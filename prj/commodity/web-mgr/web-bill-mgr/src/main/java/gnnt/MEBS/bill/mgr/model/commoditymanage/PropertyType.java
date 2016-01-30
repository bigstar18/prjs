package gnnt.MEBS.bill.mgr.model.commoditymanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class PropertyType
  extends StandardModel
{
  private static final long serialVersionUID = 5746570481774934197L;
  @ClassDiscription(name="类型编号", description="")
  private Long propertyTypeID;
  @ClassDiscription(name="类型名称", description="")
  private String name;
  @ClassDiscription(name="状态", description="0 可见 1 不可见")
  private Integer status;
  @ClassDiscription(name="排序编号", description="")
  private Integer sortNo;
  
  public Long getPropertyTypeID()
  {
    return this.propertyTypeID;
  }
  
  public void setPropertyTypeID(Long paramLong)
  {
    this.propertyTypeID = paramLong;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public Integer getSortNo()
  {
    return this.sortNo;
  }
  
  public void setSortNo(Integer paramInteger)
  {
    this.sortNo = paramInteger;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("propertyTypeID", this.propertyTypeID);
  }
}
