package gnnt.MEBS.common.broker.model;

import java.util.Date;

public class Apply extends StandardModel
{
  private static final long serialVersionUID = -5508529233023594826L;
  private Long id;
  private String operateType;
  private String applyType;
  private String applyUser;
  private String content;
  private String entityClass;
  private Integer status;
  private String discribe;
  private Date createTime;
  private Date modTime;
  private String note;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }

  public String getOperateType()
  {
    return this.operateType;
  }

  public void setOperateType(String paramString)
  {
    this.operateType = paramString;
  }

  public String getApplyType()
  {
    return this.applyType;
  }

  public void setApplyType(String paramString)
  {
    this.applyType = paramString;
  }

  public String getApplyUser()
  {
    return this.applyUser;
  }

  public void setApplyUser(String paramString)
  {
    this.applyUser = paramString;
  }

  public String getContent()
  {
    return this.content;
  }

  public void setContent(String paramString)
  {
    this.content = paramString;
  }

  public String getEntityClass()
  {
    return this.entityClass;
  }

  public void setEntityClass(String paramString)
  {
    this.entityClass = paramString;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }

  public String getDiscribe()
  {
    return this.discribe;
  }

  public void setDiscribe(String paramString)
  {
    this.discribe = paramString;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }

  public Date getModTime()
  {
    return this.modTime;
  }

  public void setModTime(Date paramDate)
  {
    this.modTime = paramDate;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}