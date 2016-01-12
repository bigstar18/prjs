package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class TariffSimple
  implements Comparable
{

  @ClassDiscription(name="", description="")
  private String tariffID;

  @ClassDiscription(name="", description="")
  private String tariffName;

  @ClassDiscription(name="", description="")
  private Date createTime;

  @ClassDiscription(name="", description="")
  private String createUser;

  public String getTariffID()
  {
    return this.tariffID;
  }

  public void setTariffID(String tariffID) {
    this.tariffID = tariffID;
  }

  public String getTariffName() {
    return this.tariffName;
  }

  public void setTariffName(String tariffName) {
    this.tariffName = tariffName;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getCreateUser() {
    return this.createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (
      this.tariffID == null ? 0 : this.tariffID.hashCode());
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TariffSimple other = (TariffSimple)obj;
    if (this.tariffID == null) {
      if (other.tariffID != null)
        return false;
    } else if (!this.tariffID.equals(other.tariffID))
      return false;
    return true;
  }

  public int compareTo(Object o) {
    TariffSimple target = (TariffSimple)o;
    return this.tariffID.compareTo(target.getTariffID());
  }

  public TariffSimple() {
  }

  public TariffSimple(String tariffID, String tariffName, Date createTime, String createUser) {
    this.tariffID = tariffID;
    this.tariffName = tariffName;
    this.createTime = createTime;
    this.createUser = createUser;
  }
}