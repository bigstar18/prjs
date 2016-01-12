package gnnt.MEBS.timebargain.mgr.model.apply;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Apply
{

  @ClassDiscription(name="id", description="")
  protected long id;

  @ClassDiscription(name="xml的id", description="")
  protected long xmlId;

  @ClassDiscription(name="申请类型", description="")
  protected int applyType;

  @ClassDiscription(name="状态", description="")
  protected int status;

  @ClassDiscription(name="申请人", description="")
  protected String proposer;

  @ClassDiscription(name="申请时间", description="")
  protected Date applytime;

  @ClassDiscription(name="审核人", description="")
  protected String approver;

  @ClassDiscription(name="审核时间", description="")
  protected Date approvetime;

  @ClassDiscription(name="申请内容，以xml格式存储", description="")
  protected String content;

  @ClassDiscription(name="备注", description="")
  protected String note;

  public String getNote()
  {
    return this.note;
  }
  public void setNote(String note) {
    this.note = note;
  }
  public long getXmlId() {
    return this.xmlId;
  }
  public void setXmlId(long xmlId) {
    this.xmlId = xmlId;
  }
  public Date getApplytime() {
    return this.applytime;
  }
  public void setApplytime(Date applytime) {
    this.applytime = applytime;
  }
  public int getApplyType() {
    return this.applyType;
  }
  public void setApplyType(int applyType) {
    this.applyType = applyType;
  }
  public String getApprover() {
    return this.approver;
  }
  public void setApprover(String approver) {
    this.approver = approver;
  }
  public Date getApprovetime() {
    return this.approvetime;
  }
  public void setApprovetime(Date approvetime) {
    this.approvetime = approvetime;
  }

  public String getContent() {
    generationContent();
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public long getId() {
    return this.id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getProposer() {
    return this.proposer;
  }
  public void setProposer(String proposer) {
    this.proposer = proposer;
  }
  public int getStatus() {
    return this.status;
  }
  public void setStatus(int status) {
    this.status = status;
  }

  public void generationContent()
  {
  }

  public String toQury()
  {
    return "select id, applytype, status, proposer, applytime, approver, approvetime,note from t_apply t";
  }

  public String toFilter()
  {
    String filter = "";
    if (this.id != 0L)
    {
      filter = filter + " and id=" + this.id;
    }
    if (this.applyType != 0)
    {
      filter = filter + " and applyType=" + this.applyType;
    }
    if (this.status != 0)
    {
      filter = filter + " and status=" + this.status;
    }
    if ((this.proposer != null) && (!"".equals(this.proposer)))
    {
      filter = filter + " and proposer='" + this.proposer + "'";
    }
    if ((this.approver != null) && (!"".equals(this.approver)))
    {
      filter = filter + " and approver='" + this.approver + "'";
    }
    if ((this.note != null) && (!"".equals(this.note)))
    {
      filter = filter + " and note='" + this.note + "'";
    }
    return filter;
  }

  public String toOrder()
  {
    String order = " order by applytime desc";
    return order;
  }
}