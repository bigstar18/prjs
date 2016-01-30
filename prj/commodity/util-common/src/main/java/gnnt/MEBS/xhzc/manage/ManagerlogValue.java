package gnnt.MEBS.xhzc.manage;

import java.sql.Timestamp;

public class ManagerlogValue
{
  public int logID;
  public String operatorID;
  public Timestamp operateTime;
  public String commond;
  public String firmID;
  public String content;

  public String getCommond()
  {
    return this.commond;
  }

  public String getContent() {
    return this.content;
  }

  public String getFirmID() {
    return this.firmID;
  }

  public int getLogID() {
    return this.logID;
  }

  public Timestamp getOperateTime() {
    return this.operateTime;
  }

  public String getOperatorID() {
    return this.operatorID;
  }

  public void setCommond(String commond) {
    this.commond = commond;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public void setLogID(int logID) {
    this.logID = logID;
  }

  public void setOperateTime(Timestamp operateTime) {
    this.operateTime = operateTime;
  }

  public void setOperatorID(String operatorID) {
    this.operatorID = operatorID;
  }
}