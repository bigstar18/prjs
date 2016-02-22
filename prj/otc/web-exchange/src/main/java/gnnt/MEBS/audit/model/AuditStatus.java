package gnnt.MEBS.audit.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.util.Date;

public class AuditStatus
  extends Clone
{
  private Long id;
  private Long applyId;
  private Integer status;
  private String proposer;
  private Date modTime;
  private String statusDiscribe;
  
  @ClassDiscription(name="id")
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  @ClassDiscription(name="修改时间")
  public Date getModTime()
  {
    return this.modTime;
  }
  
  public void setModTime(Date modTime)
  {
    this.modTime = modTime;
  }
  
  @ClassDiscription(name="所对应的申请记录")
  public Long getApplyId()
  {
    return this.applyId;
  }
  
  public void setApplyId(Long applyId)
  {
    this.applyId = applyId;
  }
  
  @ClassDiscription(name="状态的中文描述")
  public String getStatusDiscribe()
  {
    return this.statusDiscribe;
  }
  
  public void setStatusDiscribe(String statusDiscribe)
  {
    this.statusDiscribe = statusDiscribe;
  }
  
  @ClassDiscription(name="申批状态")
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  @ClassDiscription(name="审批人")
  public String getProposer()
  {
    return this.proposer;
  }
  
  public void setProposer(String proposer)
  {
    this.proposer = proposer;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
