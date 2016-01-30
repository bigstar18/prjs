package gnnt.MEBS.common.broker.model;

import java.util.Date;

public class Audit extends StandardModel
{
  private static final long serialVersionUID = 7301167367396938476L;
  private Long id;
  private Apply apply;
  private Integer status;
  private String auditUser;
  private Date modTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }

  public Apply getApply()
  {
    return this.apply;
  }

  public void setApply(Apply paramApply)
  {
    this.apply = paramApply;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }

  public String getAuditUser()
  {
    return this.auditUser;
  }

  public void setAuditUser(String paramString)
  {
    this.auditUser = paramString;
  }

  public Date getModTime()
  {
    return this.modTime;
  }

  public void setModTime(Date paramDate)
  {
    this.modTime = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}