package gnnt.MEBS.finance.unit;

import java.math.BigDecimal;

public class VoucherEntry
{
  private Long entryId;
  private Long voucherNo;
  private String entrySummary;
  private String accountCode;
  private String accountName;
  private BigDecimal debitAmount;
  private BigDecimal creditAmount;
  
  public BigDecimal getCreditAmount()
  {
    return this.creditAmount;
  }
  
  public void setCreditAmount(BigDecimal paramBigDecimal)
  {
    this.creditAmount = paramBigDecimal;
  }
  
  public BigDecimal getDebitAmount()
  {
    return this.debitAmount;
  }
  
  public void setDebitAmount(BigDecimal paramBigDecimal)
  {
    this.debitAmount = paramBigDecimal;
  }
  
  public Long getEntryId()
  {
    return this.entryId;
  }
  
  public void setEntryId(Long paramLong)
  {
    this.entryId = paramLong;
  }
  
  public String getEntrySummary()
  {
    return this.entrySummary;
  }
  
  public void setEntrySummary(String paramString)
  {
    this.entrySummary = paramString;
  }
  
  public Long getVoucherNo()
  {
    return this.voucherNo;
  }
  
  public void setVoucherNo(Long paramLong)
  {
    this.voucherNo = paramLong;
  }
  
  public String getAccountCode()
  {
    return this.accountCode;
  }
  
  public void setAccountCode(String paramString)
  {
    this.accountCode = paramString;
  }
  
  public String getAccountName()
  {
    return this.accountName;
  }
  
  public void setAccountName(String paramString)
  {
    this.accountName = paramString;
  }
}
