package gnnt.MEBS.xhzc.manage;

public class GroupValue
{
  public String groupID;
  public String groupName;
  public String description;

  public String getDescription()
  {
    return this.description;
  }

  public String getGroupID() {
    return this.groupID;
  }

  public String getGroupName() {
    return this.groupName;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setGroupID(String groupID) {
    this.groupID = groupID;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}