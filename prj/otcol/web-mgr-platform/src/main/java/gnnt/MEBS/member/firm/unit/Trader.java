package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Trader
  extends Clone
{
  private String traderId;
  private String name;
  private String password;
  private String status;
  private String firmId;
  private Date createTime;
  private Date modifyTime;
  private String keyCode;
  private String enableKey;
  private List<TraderModule> traderModule;
  private String type;
  
  public List<TraderModule> getTraderModule()
  {
    return this.traderModule;
  }
  
  public void addTraderModule(List<TraderModule> paramList)
  {
    this.traderModule = paramList;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Timestamp paramTimestamp)
  {
    this.createTime = paramTimestamp;
  }
  
  public String getEnableKey()
  {
    return this.enableKey;
  }
  
  public void setEnableKey(String paramString)
  {
    this.enableKey = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String paramString)
  {
    this.keyCode = paramString;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Timestamp paramTimestamp)
  {
    this.modifyTime = paramTimestamp;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
}
