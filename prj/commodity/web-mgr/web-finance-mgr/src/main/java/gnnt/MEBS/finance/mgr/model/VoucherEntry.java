package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.math.BigDecimal;

public class VoucherEntry extends StandardModel
{
  private static final long serialVersionUID = 5784146401111672026L;

  @ClassDiscription(name="凭证分录ID", description="")
  private Long entryId;

  @ClassDiscription(name="所属凭证号", description="")
  private Long voucherNo;

  @ClassDiscription(name="分录摘要", description="")
  private String entrySummary;

  @ClassDiscription(name="科目代码", description="")
  private String accountCode;

  @ClassDiscription(name="借方金额", description="")
  private BigDecimal debitAmount;

  @ClassDiscription(name="贷方金额", description="")
  private BigDecimal creditAmount;

  public Long getEntryId()
  {
    return this.entryId;
  }

  public void setEntryId(Long paramLong)
  {
    this.entryId = paramLong;
  }

  public Long getVoucherNo()
  {
    return this.voucherNo;
  }

  public void setVoucherNo(Long paramLong)
  {
    this.voucherNo = paramLong;
  }

  public String getEntrySummary()
  {
    return this.entrySummary;
  }

  public void setEntrySummary(String paramString)
  {
    this.entrySummary = paramString;
  }

  public String getAccountCode()
  {
    return this.accountCode;
  }

  public void setAccountCode(String paramString)
  {
    this.accountCode = paramString;
  }

  public BigDecimal getDebitAmount()
  {
    return this.debitAmount;
  }

  public void setDebitAmount(BigDecimal paramBigDecimal)
  {
    this.debitAmount = paramBigDecimal;
  }

  public BigDecimal getCreditAmount()
  {
    return this.creditAmount;
  }

  public void setCreditAmount(BigDecimal paramBigDecimal)
  {
    this.creditAmount = paramBigDecimal;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "entryId", this.entryId);
  }
}