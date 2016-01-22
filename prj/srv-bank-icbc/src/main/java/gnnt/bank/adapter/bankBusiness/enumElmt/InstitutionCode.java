package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum InstitutionCode
{
  B_Beijing("1021000"), 
  B_Tianjin("1021100");

  private String value;

  public String getValue() {
    return this.value;
  }
  private InstitutionCode(String value) {
    this.value = value;
  }
}