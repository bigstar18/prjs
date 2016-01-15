package gnnt.MEBS.xhzc.manage;

public class CommodityPropertiesValue
{
  public int ID;
  public String name;
  public int parentID = -1;
  public String description;

  public String getDescription()
  {
    return this.description;
  }

  public String getName() {
    return this.name;
  }

  public int getParentID() {
    return this.parentID;
  }

  public int getID() {
    return this.ID;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParentID(int parentID) {
    this.parentID = parentID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }
}