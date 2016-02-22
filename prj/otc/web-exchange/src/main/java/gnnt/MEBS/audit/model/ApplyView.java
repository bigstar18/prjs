package gnnt.MEBS.audit.model;

import gnnt.MEBS.base.model.Clone;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ApplyView
  extends Clone
{
  private Long id;
  private String applyType;
  private Integer status;
  private String content;
  private String note;
  private String statusDiscribe;
  private String proposer;
  private Timestamp modTime;
  private String auditProposer;
  private Timestamp auditModtime;
  
  public String getAuditProposer()
  {
    return this.auditProposer;
  }
  
  public void setAuditProposer(String auditProposer)
  {
    this.auditProposer = auditProposer;
  }
  
  public Timestamp getAuditModtime()
  {
    return this.auditModtime;
  }
  
  public void setAuditModtime(Timestamp auditModtime)
  {
    this.auditModtime = auditModtime;
  }
  
  public Timestamp getModTime()
  {
    return this.modTime;
  }
  
  public String getModTimeString()
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    return simpleDateFormat.format(this.modTime);
  }
  
  public void setModTime(Timestamp modTime)
  {
    this.modTime = modTime;
  }
  
  public String getStatusDiscribe()
  {
    return this.statusDiscribe;
  }
  
  public void setStatusDiscribe(String statusDiscribe)
  {
    this.statusDiscribe = statusDiscribe;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(String applyType)
  {
    this.applyType = applyType;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
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
  
  public String getProposer()
  {
    return this.proposer;
  }
  
  public void setProposer(String proposer)
  {
    this.proposer = proposer;
  }
}
