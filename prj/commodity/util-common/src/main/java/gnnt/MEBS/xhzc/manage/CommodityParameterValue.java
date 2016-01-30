package gnnt.MEBS.xhzc.manage;

public class CommodityParameterValue
{
  public String ID;
  public int classID;
  public String name;
  public String relatedID;
  public String description;

  public String getDescription()
  {
    return this.description;
  }

  public int getClassID() {
    return this.classID;
  }

  public String getName() {
    return this.name;
  }

  public String getRelatedID() {
    return this.relatedID;
  }

  public String getID() {
    return this.ID;
  }

  public void setClassID(int classID) {
    this.classID = classID;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRelatedID(String relatedID) {
    this.relatedID = relatedID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }
}