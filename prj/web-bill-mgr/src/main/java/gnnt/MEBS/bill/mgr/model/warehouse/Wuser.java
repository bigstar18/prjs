package gnnt.MEBS.bill.mgr.model.warehouse;

import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Set;

public class Wuser
  extends StandardModel
{
  private static final long serialVersionUID = -1185871472800004202L;
  @ClassDiscription(name="用户代码", description="")
  private String userId;
  @ClassDiscription(name="用户名称", description="")
  private String name;
  @ClassDiscription(name="用户密码", description="")
  private transient String password;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseID;
  @ClassDiscription(name="用户描述", description="")
  private String description;
  @ClassDiscription(name="获取用户使用的皮肤风格", description="")
  private String skin = "default";
  @ClassDiscription(name="key盘中的代码", description="")
  private String keyCode;
  @ClassDiscription(name="用户本身拥有的权限集合", description="")
  private Set<Right> rightSet;
  @ClassDiscription(name="用户类型", description="用户类型 DEFAULT_SUPER_ADMIN：默认超级管理员 DEFAULT_ADMIN：级管理员 ADMIN: 普通管理员 , 默认超级管理员不可删除 ")
  private String type;
  @ClassDiscription(name="是否禁用状态", description="是否禁用状态  N：可用 Y:禁用")
  private String isForbid = "N";
  @ClassDiscription(name="用户登录成功后的Ip地址", description="")
  private String ipAddress;
  @ClassDiscription(name="用户sessionID", description="登录成功后由AU返回可以唯一标示用户身份")
  private long sessionId;
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getWarehouseID()
  {
    return this.warehouseID;
  }
  
  public void setWarehouseID(String paramString)
  {
    this.warehouseID = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getIsForbid()
  {
    return this.isForbid;
  }
  
  public void setIsForbid(String paramString)
  {
    this.isForbid = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getSkin()
  {
    return this.skin;
  }
  
  public void setSkin(String paramString)
  {
    this.skin = paramString;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String paramString)
  {
    this.keyCode = paramString;
  }
  
  public String getIpAddress()
  {
    return this.ipAddress;
  }
  
  public void setIpAddress(String paramString)
  {
    this.ipAddress = paramString;
  }
  
  public long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long paramLong)
  {
    this.sessionId = paramLong;
  }
  
  public Set<Right> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<Right> paramSet)
  {
    this.rightSet = paramSet;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (((paramObject instanceof Wuser)) && (paramObject != null))
    {
      Wuser localWuser = (Wuser)paramObject;
      if (!getUserId().equals(localWuser.getUserId())) {
        bool = false;
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("userId", this.userId);
  }
}
