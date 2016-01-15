package gnnt.MEBS.xhzc.manage;

import java.sql.Timestamp;

public class TradeTimeValue
{
  public int orderID;
  public int type;
  public String startTime;
  public int spanTime;
  public int status;
  public int runType;
  public Timestamp modifytime;

  public Timestamp getModifytime()
  {
    return this.modifytime;
  }

  public int getOrderID() {
    return this.orderID;
  }

  public int getRunType() {
    return this.runType;
  }

  public int getSpanTime() {
    return this.spanTime;
  }

  public String getStartTime() {
    return this.startTime;
  }

  public int getStatus() {
    return this.status;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public void setRunType(int runType) {
    this.runType = runType;
  }

  public void setSpanTime(int spanTime) {
    this.spanTime = spanTime;
  }

  public void setOrderID(int orderID) {
    this.orderID = orderID;
  }

  public void setModifytime(Timestamp modifytime) {
    this.modifytime = modifytime;
  }
}