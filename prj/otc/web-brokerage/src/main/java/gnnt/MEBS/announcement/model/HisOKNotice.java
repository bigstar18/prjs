package gnnt.MEBS.announcement.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class HisOKNotice
  extends Clone
{
  private Long id;
  private Long noticeId;
  private String recipient;
  private String recipientType;
  private String status;
  private Date clearDate;
  private String noticeTarget;
  private String isIncludeSub;
  
  public String getNoticeTarget()
  {
    return this.noticeTarget;
  }
  
  public void setNoticeTarget(String noticeTarget)
  {
    this.noticeTarget = noticeTarget;
  }
  
  public String getIsIncludeSub()
  {
    return this.isIncludeSub;
  }
  
  public void setIsIncludeSub(String isIncludeSub)
  {
    this.isIncludeSub = isIncludeSub;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Long getNoticeId()
  {
    return this.noticeId;
  }
  
  public void setNoticeId(Long noticeId)
  {
    this.noticeId = noticeId;
  }
  
  public String getRecipient()
  {
    return this.recipient;
  }
  
  public void setRecipient(String recipient)
  {
    this.recipient = recipient;
  }
  
  public String getRecipientType()
  {
    return this.recipientType;
  }
  
  public void setRecipientType(String recipientType)
  {
    this.recipientType = recipientType;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
