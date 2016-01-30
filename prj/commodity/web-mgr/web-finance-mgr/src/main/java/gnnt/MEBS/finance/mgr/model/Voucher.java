package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Voucher extends StandardModel
{
  private static final long serialVersionUID = 8317236233983103549L;

  @ClassDiscription(name="凭证编号(序列自增长)", description="")
  private Long voucherNo;

  @ClassDiscription(name="结算日期", description="")
  private Date b_date;

  @ClassDiscription(name="", description="")
  private String summaryNo;

  @ClassDiscription(name="凭证摘要", description="")
  private String summary;

  @ClassDiscription(name="附加说明", description="")
  private String note;

  @ClassDiscription(name="录入员", description="")
  private String inputUser;

  @ClassDiscription(name="录入时间", description="")
  private Date inputTime;

  @ClassDiscription(name="审核员", description="")
  private String auditor;

  @ClassDiscription(name="审核时间", description="")
  private Date auditTime;

  @ClassDiscription(name="审核意见", description="")
  private String auditNote;

  @ClassDiscription(name="凭证状态 ", description="editing 编辑中 auditing 待审核 audited 已审核 accounted 已记账")
  private String status;

  @ClassDiscription(name="成交合同号", description="")
  private String contractNo;

  @ClassDiscription(name="资金流水id", description="")
  private Long fundFlowID;
  private Set<VoucherEntry> voucherEntries = new HashSet();

  public Set<VoucherEntry> getVoucherEntries()
  {
    return this.voucherEntries;
  }

  public void setVoucherEntries(Set<VoucherEntry> paramSet)
  {
    this.voucherEntries = paramSet;
  }

  public Long getVoucherNo()
  {
    return this.voucherNo;
  }

  public void setVoucherNo(Long paramLong)
  {
    this.voucherNo = paramLong;
  }

  public Date getB_date()
  {
    return this.b_date;
  }

  public void setB_date(Date paramDate)
  {
    this.b_date = paramDate;
  }

  public String getSummaryNo()
  {
    return this.summaryNo;
  }

  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }

  public String getSummary()
  {
    return this.summary;
  }

  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public String getInputUser()
  {
    return this.inputUser;
  }

  public void setInputUser(String paramString)
  {
    this.inputUser = paramString;
  }

  public Date getInputTime()
  {
    return this.inputTime;
  }

  public void setInputTime(Date paramDate)
  {
    this.inputTime = paramDate;
  }

  public String getAuditor()
  {
    return this.auditor;
  }

  public void setAuditor(String paramString)
  {
    this.auditor = paramString;
  }

  public Date getAuditTime()
  {
    return this.auditTime;
  }

  public void setAuditTime(Date paramDate)
  {
    this.auditTime = paramDate;
  }

  public String getAuditNote()
  {
    return this.auditNote;
  }

  public void setAuditNote(String paramString)
  {
    this.auditNote = paramString;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String paramString)
  {
    this.status = paramString;
  }

  public String getContractNo()
  {
    return this.contractNo;
  }

  public void setContractNo(String paramString)
  {
    this.contractNo = paramString;
  }

  public Long getFundFlowID()
  {
    return this.fundFlowID;
  }

  public void setFundFlowID(Long paramLong)
  {
    this.fundFlowID = paramLong;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "voucherNo", this.voucherNo);
  }
}