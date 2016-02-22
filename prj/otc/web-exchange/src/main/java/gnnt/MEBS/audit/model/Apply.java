package gnnt.MEBS.audit.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.util.Set;

public class Apply
  extends Clone
{
  private Long id;
  private String applyType;
  private Integer status;
  private String content;
  private String note;
  private Set<AuditStatus> auditStatusSet;
  private String statusDiscribe;
  
  @ClassDiscription(name="状态中文描述")
  public String getStatusDiscribe()
  {
    return this.statusDiscribe;
  }
  
  public void setStatusDiscribe(String statusDiscribe)
  {
    this.statusDiscribe = statusDiscribe;
  }
  
  @ClassDiscription(name="所包含的具体的状态改变")
  public Set<AuditStatus> getAuditStatusSet()
  {
    return this.auditStatusSet;
  }
  
  public void setAuditStatusSet(Set<AuditStatus> auditStatusSet)
  {
    this.auditStatusSet = auditStatusSet;
  }
  
  @ClassDiscription(name="id")
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  @ClassDiscription(name="申请类型")
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(String applyType)
  {
    this.applyType = applyType;
  }
  
  @ClassDiscription(name="申请状态")
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  @ClassDiscription(name="申请的具体内容")
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
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
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
