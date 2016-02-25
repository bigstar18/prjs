package gnnt.MEBS.common.manage.model;

import java.util.Date;

public class Apply
{
  protected long id;
  protected long xmlId;
  protected int applyType;
  protected int status;
  protected String proposer;
  protected Date applytime;
  protected String approver;
  protected Date approvetime;
  protected String content;
  protected String note;
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public long getXmlId()
  {
    return this.xmlId;
  }
  
  public void setXmlId(long paramLong)
  {
    this.xmlId = paramLong;
  }
  
  public Date getApplytime()
  {
    return this.applytime;
  }
  
  public void setApplytime(Date paramDate)
  {
    this.applytime = paramDate;
  }
  
  public int getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(int paramInt)
  {
    this.applyType = paramInt;
  }
  
  public String getApprover()
  {
    return this.approver;
  }
  
  public void setApprover(String paramString)
  {
    this.approver = paramString;
  }
  
  public Date getApprovetime()
  {
    return this.approvetime;
  }
  
  public void setApprovetime(Date paramDate)
  {
    this.approvetime = paramDate;
  }
  
  public String getContent()
  {
    generationContent();
    return this.content;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getProposer()
  {
    return this.proposer;
  }
  
  public void setProposer(String paramString)
  {
    this.proposer = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public void generationContent() {}
  
  public String toQury()
  {
    return "select id, applytype, status, proposer, applytime, approver, approvetime,note from c_apply t";
  }
  
  public String toFilter()
  {
    String str = "";
    if (this.id != 0L) {
      str = str + " and id=" + this.id;
    }
    if (this.applyType != 0) {
      str = str + " and applyType=" + this.applyType;
    }
    if (this.status != 0) {
      str = str + " and status=" + this.status;
    }
    if ((this.proposer != null) && (!"".equals(this.proposer))) {
      str = str + " and proposer='" + this.proposer + "'";
    }
    if ((this.approver != null) && (!"".equals(this.approver))) {
      str = str + " and approver='" + this.approver + "'";
    }
    if ((this.note != null) && (!"".equals(this.note))) {
      str = str + " and note='" + this.note + "'";
    }
    return str;
  }
  
  public String toOrder()
  {
    String str = " order by applytime desc";
    return str;
  }
}
