package gnnt.MEBS.finance.unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Voucher
{
  private Long voucherNo;
  private Date voucherDate;
  private String summaryNo;
  private String Summary;
  private String note;
  private String inputUser;
  private Date inputTime;
  private String auditor;
  private Date auditTime;
  private String auditNote;
  private String status;
  private String contractNo;
  private double fundFlowID;
  private List voucherEntrys = new ArrayList();
  
  public String getAuditNote()
  {
    return this.auditNote;
  }
  
  public void setAuditNote(String paramString)
  {
    this.auditNote = paramString;
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
  
  public String getContractNo()
  {
    return this.contractNo;
  }
  
  public void setContractNo(String paramString)
  {
    this.contractNo = paramString;
  }
  
  public double getFundFlowID()
  {
    return this.fundFlowID;
  }
  
  public void setFundFlowID(double paramDouble)
  {
    this.fundFlowID = paramDouble;
  }
  
  public Date getInputTime()
  {
    return this.inputTime;
  }
  
  public void setInputTime(Date paramDate)
  {
    this.inputTime = paramDate;
  }
  
  public String getInputUser()
  {
    return this.inputUser;
  }
  
  public void setInputUser(String paramString)
  {
    this.inputUser = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getSummary()
  {
    return this.Summary;
  }
  
  public void setSummary(String paramString)
  {
    this.Summary = paramString;
  }
  
  public String getSummaryNo()
  {
    return this.summaryNo;
  }
  
  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }
  
  public Date getVoucherDate()
  {
    return this.voucherDate;
  }
  
  public void setVoucherDate(Date paramDate)
  {
    this.voucherDate = paramDate;
  }
  
  public List getVoucherEntrys()
  {
    return this.voucherEntrys;
  }
  
  public void setVoucherEntrys(List paramList)
  {
    this.voucherEntrys = paramList;
  }
  
  public Long getVoucherNo()
  {
    return this.voucherNo;
  }
  
  public void setVoucherNo(Long paramLong)
  {
    this.voucherNo = paramLong;
  }
}
