package gnnt.MEBS.xhzc.manage;

import java.sql.Timestamp;

public class UserLogValue
{
  public int logID;
  public String firmID;
  public String traderID;
  public int onoff;
  public Timestamp logtime;
  public int logtype;

  public String getFirmID()
  {
    return this.firmID;
  }

  public int getLogID() {
    return this.logID;
  }

  public Timestamp getLogtime() {
    return this.logtime;
  }

  public int getLogtype() {
    return this.logtype;
  }

  public int getOnoff() {
    return this.onoff;
  }

  public String getTraderID() {
    return this.traderID;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public void setLogID(int logID) {
    this.logID = logID;
  }

  public void setLogtime(Timestamp logtime) {
    this.logtime = logtime;
  }

  public void setLogtype(int logtype) {
    this.logtype = logtype;
  }

  public void setOnoff(int onoff) {
    this.onoff = onoff;
  }

  public void setTraderID(String traderID) {
    this.traderID = traderID;
  }
}