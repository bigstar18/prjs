package gnnt.MEBS.timebargain.mgr.model.settleProps;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class BreedProps extends StandardModel
{
  private static final long serialVersionUID = -6599508852049902941L;

  @ClassDiscription(name="属性值", description="")
  private String propertyValue;

  @ClassDiscription(name="品种", description="")
  private BreedP breed;

  @ClassDiscription(name="属性", description="")
  private CategoryProperty categoryProperty;

  @ClassDiscription(name="排序号", description="")
  private Long sortNo;

  public BreedP getBreed()
  {
    return this.breed;
  }

  public void setBreed(BreedP breed) {
    this.breed = breed;
  }

  public CategoryProperty getCategoryProperty()
  {
    return this.categoryProperty;
  }

  public void setCategoryProperty(CategoryProperty categoryProperty) {
    this.categoryProperty = categoryProperty;
  }

  public String getPropertyValue()
  {
    return this.propertyValue;
  }

  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
  }

  public Long getSortNo()
  {
    return this.sortNo;
  }

  public void setSortNo(Long sortNo) {
    this.sortNo = sortNo;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}