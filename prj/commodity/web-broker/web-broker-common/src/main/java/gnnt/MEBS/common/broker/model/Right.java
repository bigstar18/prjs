package gnnt.MEBS.common.broker.model;

import java.util.HashSet;
import java.util.Set;

public class Right extends StandardModel
{
  private static final long serialVersionUID = -6128452830189088494L;
  private Long id;
  private String name;
  private String icon;
  private String url;
  private String visiturl;
  private Integer moduleId;
  private Integer visible;
  private Integer seq;
  private Integer type;
  private Set<Right> childRightSet = new HashSet();
  private Set<Broker> brokerSet;
  private Right parentRight;
  private LogCatalog logCatalog;
  private String isWriteLog;
  private String onlyMember;

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

  public Right getParentRight()
  {
    return this.parentRight;
  }

  public void setParentRight(Right paramRight)
  {
    this.parentRight = paramRight;
  }

  public Set<Right> getChildRightSet()
  {
    return this.childRightSet;
  }

  public void setChildRightSet(Set<Right> paramSet)
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

  public String getOnlyMember()
  {
    return this.onlyMember;
  }

  public void setOnlyMember(String paramString)
  {
    this.onlyMember = paramString;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    Right localRight1 = this;
    if ((paramObject instanceof Right))
    {
      Right localRight2 = (Right)paramObject;
      if ((localRight1.getId() != localRight2.getId()) || (!localRight1.getUrl().equals(localRight2.getUrl())))
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
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}