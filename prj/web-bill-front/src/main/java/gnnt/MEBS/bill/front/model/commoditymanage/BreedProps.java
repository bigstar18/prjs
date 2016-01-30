package gnnt.MEBS.bill.front.model.commoditymanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class BreedProps
  extends StandardModel
{
  private static final long serialVersionUID = -6599508852049902941L;
  @ClassDiscription(name="属性值", description="")
  private String propertyValue;
  @ClassDiscription(name="品名", description="所属品名")
  private Breed breed;
  @ClassDiscription(name="属性", description="所属分类")
  private CategoryProperty categoryProperty;
  @ClassDiscription(name="排序号", description="")
  private Long sortNo;
  
  public Breed getBreed()
  {
    return this.breed;
  }
  
  public void setBreed(Breed paramBreed)
  {
    this.breed = paramBreed;
  }
  
  public CategoryProperty getCategoryProperty()
  {
    return this.categoryProperty;
  }
  
  public void setCategoryProperty(CategoryProperty paramCategoryProperty)
  {
    this.categoryProperty = paramCategoryProperty;
  }
  
  public String getPropertyValue()
  {
    return this.propertyValue;
  }
  
  public void setPropertyValue(String paramString)
  {
    this.propertyValue = paramString;
  }
  
  public Long getSortNo()
  {
    return this.sortNo;
  }
  
  public void setSortNo(Long paramLong)
  {
    this.sortNo = paramLong;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
