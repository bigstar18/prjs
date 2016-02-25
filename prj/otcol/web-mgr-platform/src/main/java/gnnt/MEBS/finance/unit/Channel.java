package gnnt.MEBS.finance.unit;

public class Channel
{
  private String code;
  private String name;
  private String summaryNo;
  private String debitCode;
  private String creditCode;
  private String needContractNo;
  private String note;
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getCreditCode()
  {
    return this.creditCode;
  }
  
  public void setCreditCode(String paramString)
  {
    this.creditCode = paramString;
  }
  
  public String getDebitCode()
  {
    return this.debitCode;
  }
  
  public void setDebitCode(String paramString)
  {
    this.debitCode = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getNeedContractNo()
  {
    return this.needContractNo;
  }
  
  public void setNeedContractNo(String paramString)
  {
    this.needContractNo = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public String getSummaryNo()
  {
    return this.summaryNo;
  }
  
  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }
}
