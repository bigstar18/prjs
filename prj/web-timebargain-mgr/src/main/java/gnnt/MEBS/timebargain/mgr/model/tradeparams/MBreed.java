package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class MBreed
{

  @ClassDiscription(name="商品代码", description="")
  private String breedID;

  @ClassDiscription(name="商品名称", description="")
  private String breedName;

  @ClassDiscription(name="商品分类ID", description="")
  private String categoryID;

  @ClassDiscription(name="单位", description="")
  private String unit;

  public String getBreedID()
  {
    return this.breedID;
  }

  public void setBreedID(String breedID) {
    this.breedID = breedID;
  }

  public String getBreedName() {
    return this.breedName;
  }

  public void setBreedName(String breedName) {
    this.breedName = breedName;
  }

  public String getCategoryID() {
    return this.categoryID;
  }

  public void setCategoryID(String categoryID) {
    this.categoryID = categoryID;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String toString()
  {
    return "{'breedID':'" + this.breedID + "', 'breedName':'" + this.breedName + 
      "', 'categoryID':'" + this.categoryID + "', 'unit':'" + this.unit + "'}";
  }
}