package gnnt.MEBS.integrated.mgr.model.commodity;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CategoryProperty
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="属性ID", description="")
  private Long propertyId;
  @ClassDiscription(name="与商品关联 对应商品ID", description="")
  private Category category;
  @ClassDiscription(name="属性名称", description="")
  private String propertyName;
  @ClassDiscription(name="是否有字典值", description="Y：有值字典  N：无值字典")
  private String hasValueDict;
  @ClassDiscription(name="是否用于检索", description="Y：用于列表搜索 N：不用于搜索")
  private String searchable;
  @ClassDiscription(name="排序号", description="")
  private Integer sortNo;
  @ClassDiscription(name="是不是必填项", description="Y：必填项；N：选填项")
  private String isNecessary;
  @ClassDiscription(name="支持输入的格式 0", description="0:字符串  1：数字")
  private Integer fieldType;
  @ClassDiscription(name="是否用于匹配仓单时检查", description="Y：检查  N：不检查")
  private String stockCheck;
  @ClassDiscription(name="属性分类编号", description="")
  private Long propertyTypeID;
  @ClassDiscription(name="属性分类", description="")
  private PropertyType propertyType;
  
  public String getIsNecessary()
  {
    return this.isNecessary;
  }
  
  public void setIsNecessary(String paramString)
  {
    this.isNecessary = paramString;
  }
  
  public Integer getFieldType()
  {
    return this.fieldType;
  }
  
  public void setFieldType(Integer paramInteger)
  {
    this.fieldType = paramInteger;
  }
  
  public String getStockCheck()
  {
    return this.stockCheck;
  }
  
  public void setStockCheck(String paramString)
  {
    this.stockCheck = paramString;
  }
  
  public Long getPropertyTypeID()
  {
    return this.propertyTypeID;
  }
  
  public void setPropertyTypeID(Long paramLong)
  {
    this.propertyTypeID = paramLong;
  }
  
  public PropertyType getPropertyType()
  {
    return this.propertyType;
  }
  
  public void setPropertyType(PropertyType paramPropertyType)
  {
    this.propertyType = paramPropertyType;
  }
  
  public Long getPropertyId()
  {
    return this.propertyId;
  }
  
  public void setPropertyId(Long paramLong)
  {
    this.propertyId = paramLong;
  }
  
  public Category getCategory()
  {
    return this.category;
  }
  
  public void setCategory(Category paramCategory)
  {
    this.category = paramCategory;
  }
  
  public String getPropertyName()
  {
    return this.propertyName;
  }
  
  public void setPropertyName(String paramString)
  {
    this.propertyName = paramString;
  }
  
  public String getHasValueDict()
  {
    return this.hasValueDict;
  }
  
  public void setHasValueDict(String paramString)
  {
    this.hasValueDict = paramString;
  }
  
  public String getSearchable()
  {
    return this.searchable;
  }
  
  public void setSearchable(String paramString)
  {
    this.searchable = paramString;
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
    return new StandardModel.PrimaryInfo("propertyId", this.propertyId);
  }
}
