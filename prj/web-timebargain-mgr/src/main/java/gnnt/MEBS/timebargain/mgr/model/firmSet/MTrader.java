package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class MTrader extends StandardModel
{
  private static final long serialVersionUID = 6726025292493598814L;

  @ClassDiscription(name="交易员ID", description="")
  private String traderID;

  @ClassDiscription(name="交易员ID", description="")
  private String name;

  @ClassDiscription(name="交易员密码", description="")
  private String password;

  @ClassDiscription(name="是否强制修改密码", description="0：否，1：是")
  private Integer forceChangePwd;

  @ClassDiscription(name="状态", description="N：正常 Normal，D：禁用 Disable")
  private String status;

  @ClassDiscription(name="交易员类型", description="A：管理员，N：一般交易员")
  private String type;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="最后一次修改时间")
  private Date modifyTime;

  @ClassDiscription(name="Key码", description="")
  private String keyCode;

  @ClassDiscription(name="Key码", description="Y：启用，N：不启用")
  private String enableKey;

  @ClassDiscription(name="信任的key", description="客户端登录成功后，在本地和服务端记录一个信任Key，不限制重试次数。")
  private String trustKey;

  @ClassDiscription(name="上次登录IP", description="")
  private String lastIP;

  @ClassDiscription(name="上次登录时间", description="")
  private Date lastTime;

  @ClassDiscription(name="皮肤", description="")
  private String skin;
  private TFirm firm;

  public String getTraderID()
  {
    return this.traderID;
  }

  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public Integer getForceChangePwd()
  {
    return this.forceChangePwd;
  }

  public void setForceChangePwd(Integer forceChangePwd)
  {
    this.forceChangePwd = forceChangePwd;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public String getKeyCode()
  {
    return this.keyCode;
  }

  public void setKeyCode(String keyCode)
  {
    this.keyCode = keyCode;
  }

  public String getEnableKey()
  {
    return this.enableKey;
  }

  public void setEnableKey(String enableKey)
  {
    this.enableKey = enableKey;
  }

  public String getTrustKey()
  {
    return this.trustKey;
  }

  public void setTrustKey(String trustKey)
  {
    this.trustKey = trustKey;
  }

  public String getLastIP()
  {
    return this.lastIP;
  }

  public void setLastIP(String lastIP)
  {
    this.lastIP = lastIP;
  }

  public Date getLastTime()
  {
    return this.lastTime;
  }

  public void setLastTime(Date lastTime)
  {
    this.lastTime = lastTime;
  }

  public String getSkin()
  {
    return this.skin;
  }

  public void setSkin(String skin)
  {
    this.skin = skin;
  }

  public TFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(TFirm firm)
  {
    this.firm = firm;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "traderID", this.traderID);
  }
}