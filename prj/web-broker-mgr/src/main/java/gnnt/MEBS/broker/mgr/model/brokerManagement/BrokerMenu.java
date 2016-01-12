package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.LogCatalog;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.HashSet;
import java.util.Set;

public class BrokerMenu extends StandardModel
{
  private static final long serialVersionUID = 5079532922255904177L;

  @ClassDiscription(name="权限代码", description="")
  private Long id;

  @ClassDiscription(name="权限名称", description="")
  private String name;

  @ClassDiscription(name="权限使用的图标", description="")
  private String icon;

  @ClassDiscription(name="对应的权限路径", description="")
  private String url;

  @ClassDiscription(name="对应的资源路径", description="")
  private String visiturl;

  @ClassDiscription(name="所属模块号", description="")
  private Integer moduleId;

  @ClassDiscription(name="是否可见", description="是否可见 0：可见、 其他：不可见")
  private Integer visible;

  @ClassDiscription(name="序号", description="用于属于同一类型的菜单排序")
  private Integer seq;

  @ClassDiscription(name="权限类型", description="权限类型 -3：只检查session不检查权限的url -2：无需判断权限的URL  -1： 父菜单类型 0：子菜单类型  1：页面内增删改查权限")
  private Integer type;
  private Long onlyMember;

  @ClassDiscription(name="当前权限所拥有的子权限集合", description="")
  private Set<BrokerMenu> childRightSet = new HashSet();

  @ClassDiscription(name="拥有此权限的角色集合", description="")
  private Set<Broker> brokerSet;

  @ClassDiscription(name="父权限", description="")
  private BrokerMenu parentRight;

  @ClassDiscription(name="日志对应的分类", description="")
  private LogCatalog logCatalog;

  @ClassDiscription(name="是否自动写日志", description="是否自动写日志 Y：写日志 N：不写日志")
  private String isWriteLog;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getIcon()
  {
    return this.icon;
  }

  public void setIcon(String paramString)
  {
    this.icon = paramString;
  }

  public String getUrl()
  {
    return this.url;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public String getVisiturl()
  {
    return this.visiturl;
  }

  public void setVisiturl(String paramString)
  {
    this.visiturl = paramString;
  }

  public Integer getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }

  public Integer getVisible()
  {
    return this.visible;
  }

  public void setVisible(Integer paramInteger)
  {
    this.visible = paramInteger;
  }

  public Integer getSeq()
  {
    return this.seq;
  }

  public void setSeq(Integer paramInteger)
  {
    this.seq = paramInteger;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer paramInteger)
  {
    this.type = paramInteger;
  }

  public Long getOnlyMember()
  {
    return this.onlyMember;
  }

  public void setOnlyMember(Long paramLong)
  {
    this.onlyMember = paramLong;
  }

  public Set<BrokerMenu> getChildRightSet()
  {
    return this.childRightSet;
  }

  public void setChildRightSet(Set<BrokerMenu> paramSet)
  {
    this.childRightSet = paramSet;
  }

  public Set<Broker> getBrokerSet()
  {
    return this.brokerSet;
  }

  public void setBrokerSet(Set<Broker> paramSet)
  {
    this.brokerSet = paramSet;
  }

  public BrokerMenu getParentRight()
  {
    return this.parentRight;
  }

  public void setParentRight(BrokerMenu paramBrokerMenu)
  {
    this.parentRight = paramBrokerMenu;
  }

  public LogCatalog getLogCatalog()
  {
    return this.logCatalog;
  }

  public void setLogCatalog(LogCatalog paramLogCatalog)
  {
    this.logCatalog = paramLogCatalog;
  }

  public String getIsWriteLog()
  {
    return this.isWriteLog;
  }

  public void setIsWriteLog(String paramString)
  {
    this.isWriteLog = paramString;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    BrokerMenu localBrokerMenu1 = this;
    if ((paramObject instanceof BrokerMenu))
    {
      BrokerMenu localBrokerMenu2 = (BrokerMenu)paramObject;
      if ((localBrokerMenu1.getId() != localBrokerMenu2.getId()) || (!localBrokerMenu1.getUrl().equals(localBrokerMenu2.getUrl())))
        bool = false;
    }
    else
    {
      bool = false;
    }
    return bool;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}