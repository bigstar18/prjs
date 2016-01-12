package gnnt.MEBS.timebargain.mgr.model.settleProps;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CategoryProperty extends StandardModel
{
  private static final long serialVersionUID = 1L;

  @ClassDiscription(name="属性ID", description="")
  private Long propertyId;

  @ClassDiscription(name="与商品关联 对应商品ID", description="")
  private Category category;

  @ClassDiscription(name="属性名称", description="")
  private String propertyName;

  @ClassDiscription(name="是否有字典值", description="")
  private String hasValueDict;

  @ClassDiscription(name="是否用于检索", description="")
  private String searchable;

  @ClassDiscription(name="排序号", description="")
  private Integer sortNo;

  @ClassDiscription(name="是不是必填项", description="")
  private String isNecessary;

  @ClassDiscription(name="支持输入的格式", description=" 0:字符串  1：数字")
  private Integer fieldType;

  @ClassDiscription(name="是否用于匹配仓单时检查", description="")
  private String stockCheck;

  public String getIsNecessary()
  {
    return this.isNecessary;
  }

  public void setIsNecessary(String isNecessary)
  {
    this.isNecessary = isNecessary;
  }

  public Integer getFieldType()
  {
    return this.fieldType;
  }

  public void setFieldType(Integer fieldType)
  {
    this.fieldType = fieldType;
  }

  public String getStockCheck()
  {
    return this.stockCheck;
  }

  public void setStockCheck(String stockCheck)
  {
    this.stockCheck = stockCheck;
  }

  public Long getPropertyId()
  {
    return this.propertyId;
  }

  public void setPropertyId(Long propertyId)
  {
    this.propertyId = propertyId;
  }

  public Category getCategory()
  {
    return this.category;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public String getPropertyName()
  {
    return this.propertyName;
  }

  public void setPropertyName(String propertyName)
  {
    this.propertyName = propertyName;
  }

  public String getHasValueDict()
  {
    return this.hasValueDict;
  }

  public void setHasValueDict(String hasValueDict)
  {
    this.hasValueDict = hasValueDict;
  }

  public String getSearchable()
  {
    return this.searchable;
  }

  public void setSearchable(String searchable)
  {
    this.searchable = searchable;
  }

  public Integer getSortNo()
  {
    return this.sortNo;
  }

  public void setSortNo(Integer sortNo)
  {
    this.sortNo = sortNo;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "propertyId", this.propertyId);
  }
}