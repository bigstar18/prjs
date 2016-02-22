package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum FileBusinessCode
{
  OTHER("0000"), 
  DETAIL("0001"), 
  SUM("0002");

  private String value;

  public String getValue() { return this.value; }

  private FileBusinessCode(String value) {
    this.value = value;
  }
}