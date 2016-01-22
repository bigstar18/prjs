package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmMessageVo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmid;
  public String firmName;
  public String firmStatus;
  public String Password;

  public String getFirmid()
  {
    return this.firmid;
  }
  public void setFirmid(String firmid) {
    this.firmid = firmid;
  }
  public String getFirmName() {
    return this.firmName;
  }
  public void setFirmName(String firmName) {
    this.firmName = firmName;
  }
  public String getFirmStatus() {
    return this.firmStatus;
  }
  public void setFirmStatus(String firmStatus) {
    this.firmStatus = firmStatus;
  }
  public String getPassword() {
    return this.Password;
  }
  public void setPassword(String password) {
    this.Password = password;
  }

  public String toString()
  {
    String str = "交易员ID=[" + this.firmid + "],交易员名称=[" + this.firmName + "]" + "交易员状态=[" + this.firmStatus + "]";
    return str;
  }
}