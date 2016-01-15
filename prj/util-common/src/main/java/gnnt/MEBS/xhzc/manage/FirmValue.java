package gnnt.MEBS.xhzc.manage;

import java.sql.Timestamp;

public class FirmValue
{
  public String firmID;
  public String userID;
  public String password;
  public int firmStatus;
  public Timestamp createtime;
  public int type;
  public String parentID;
  public String groupID;
  public Timestamp modifytime;

  public Timestamp getCreatetime()
  {
    return this.createtime;
  }

  public String getFirmID() {
    return this.firmID;
  }

  public int getFirmStatus() {
    return this.firmStatus;
  }

  public String getGroupID() {
    return this.groupID;
  }

  public Timestamp getModifytime() {
    return this.modifytime;
  }

  public String getParentID() {
    return this.parentID;
  }

  public String getPassword() {
    return this.password;
  }

  public int getType() {
    return this.type;
  }

  public String getUserID() {
    return this.userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setParentID(String parentID) {
    this.parentID = parentID;
  }

  public void setModifytime(Timestamp modifytime) {
    this.modifytime = modifytime;
  }

  public void setGroupID(String groupID) {
    this.groupID = groupID;
  }

  public void setFirmStatus(int firmStatus) {
    this.firmStatus = firmStatus;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }
}