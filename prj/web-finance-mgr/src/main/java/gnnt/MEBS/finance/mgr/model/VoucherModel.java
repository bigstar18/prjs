package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class VoucherModel extends StandardModel
{
  private static final long serialVersionUID = 5784146401111672026L;

  @ClassDiscription(name="模板代码", description="")
  private String code;

  @ClassDiscription(name="模板名称", description="")
  private String name;

  @ClassDiscription(name="摘要号", description="")
  private String summaryNo;

  @ClassDiscription(name="借方科目代码", description="")
  private String debitCode;

  @ClassDiscription(name="贷方科目代码", description="")
  private String creditCode;

  @ClassDiscription(name="需要合同号", description="")
  private String needcontractNo;

  @ClassDiscription(name="备注", description="")
  private String note;

  public String getCode()
  {
    return this.code;
  }

  public String getName()
  {
    return this.name;
  }

  public String getSummaryNo()
  {
    return this.summaryNo;
  }

  public String getDebitCode()
  {
    return this.debitCode;
  }

  public String getCreditCode()
  {
    return this.creditCode;
  }

  public String getNeedcontractNo()
  {
    return this.needcontractNo;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }

  public void setDebitCode(String paramString)
  {
    this.debitCode = paramString;
  }

  public void setCreditCode(String paramString)
  {
    this.creditCode = paramString;
  }

  public void setNeedcontractNo(String paramString)
  {
    this.needcontractNo = paramString;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "code", this.code);
  }
}