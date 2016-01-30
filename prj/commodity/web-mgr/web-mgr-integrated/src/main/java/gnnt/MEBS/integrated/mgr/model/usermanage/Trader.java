package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.Set;

public class Trader
  extends StandardModel
{
  private static final long serialVersionUID = 3959252196100594144L;
  @ClassDiscription(name="交易员Id ", description="")
  private String traderId;
  @ClassDiscription(name="所属交易商", description="")
  private MFirm mfirm;
  @ClassDiscription(name="交易员姓名", description="")
  private String name;
  @ClassDiscription(name="用户名", description="")
  private String userId;
  @ClassDiscription(name="交易员密码", description="")
  private String password;
  @ClassDiscription(name="是否修改密码 ", description="")
  private Integer forceChangePwd = Integer.valueOf(1);
  @ClassDiscription(name="交易员状态", description="N：正常 Normal D：禁用 Disable")
  private String status;
  @ClassDiscription(name="交易员类型", description=" A：管理员  N：一般交易员")
  private String type;
  @ClassDiscription(name=" 创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;
  @ClassDiscription(name="交易员Key值", description="")
  private String keyCode;
  @ClassDiscription(name="是否启用Key", description="")
  private String enableKey;
  @ClassDiscription(name="信任Key", description="")
  private String trustKey;
  @ClassDiscription(name="上次登录的IP", description="")
  private String lastIP;
  @ClassDiscription(name="上次登录时间", description="")
  private Date lastTime;
  @ClassDiscription(name="交易员sessionID", description="")
  private long sessionId;
  @ClassDiscription(name="交易员模块列表", description="")
  private Set<TraderModule> traderModuleSet;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
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
  
  public Integer getForceChangePwd()
  {
    return this.forceChangePwd;
  }
  
  public void setForceChangePwd(Integer paramInteger)
  {
    this.forceChangePwd = paramInteger;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String paramString)
  {
    this.keyCode = paramString;
  }
  
  public String getEnableKey()
  {
    return this.enableKey;
  }
  
  public void setEnableKey(String paramString)
  {
    this.enableKey = paramString;
  }
  
  public String getTrustKey()
  {
    return this.trustKey;
  }
  
  public void setTrustKey(String paramString)
  {
    this.trustKey = paramString;
  }
  
  public String getLastIP()
  {
    return this.lastIP;
  }
  
  public void setLastIP(String paramString)
  {
    this.lastIP = paramString;
  }
  
  public Date getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(Date paramDate)
  {
    this.lastTime = paramDate;
  }
  
  public long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long paramLong)
  {
    this.sessionId = paramLong;
  }
  
  public MFirm getMfirm()
  {
    return this.mfirm;
  }
  
  public void setMfirm(MFirm paramMFirm)
  {
    this.mfirm = paramMFirm;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public Set<TraderModule> getTraderModuleSet()
  {
    return this.traderModuleSet;
  }
  
  public void setTraderModuleSet(Set<TraderModule> paramSet)
  {
    this.traderModuleSet = paramSet;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("traderId", this.traderId);
  }
}
