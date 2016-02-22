package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class FirmUpdate
  extends Clone
{
  private static final long serialVersionUID = 1L;
  public Date modifyTime;
  public String firmId;
  public int holdNo;
  public int holdQty;
  public int oldFlag;
  public int newFlag;
  public String host;
  public String ip;
  public String note;
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public int getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(int holdNo)
  {
    this.holdNo = holdNo;
  }
  
  public int getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(int holdQty)
  {
    this.holdQty = holdQty;
  }
  
  public int getOldFlag()
  {
    return this.oldFlag;
  }
  
  public void setOldFlag(int oldFlag)
  {
    this.oldFlag = oldFlag;
  }
  
  public int getNewFlag()
  {
    return this.newFlag;
  }
  
  public void setNewFlag(int newFlag)
  {
    this.newFlag = newFlag;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public void setHost(String host)
  {
    this.host = host;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public void setPrimary(String primary)
  {
    this.firmId = primary;
  }
  
  public String getId()
  {
    return this.firmId;
  }
}
