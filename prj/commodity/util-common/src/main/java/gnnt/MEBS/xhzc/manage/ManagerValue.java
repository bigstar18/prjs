package gnnt.MEBS.xhzc.manage;

import java.sql.Timestamp;

public class ManagerValue
{
  public String userID;
  public String name;
  public String password;
  public Timestamp createtime;
  public int userlevel;
  public String UserAcl;

  public Timestamp getCreatetime()
  {
    return this.createtime;
  }

  public String getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUserID() {
    return this.userID;
  }

  public int getUserlevel() {
    return this.userlevel;
  }

  public String getUserAcl() {
    return this.UserAcl;
  }

  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public void setUserlevel(int userlevel) {
    this.userlevel = userlevel;
  }

  public void setUserAcl(String UserAcl) {
    this.UserAcl = UserAcl;
  }
}