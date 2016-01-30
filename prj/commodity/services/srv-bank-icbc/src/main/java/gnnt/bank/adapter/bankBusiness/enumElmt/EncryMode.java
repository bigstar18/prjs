package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum EncryMode
{
  NONE("00"), 
  DES("01"), 
  THREEDES("02");

  private String value;

  public String getValue() { return this.value; }

  private EncryMode(String value) {
    this.value = value;
  }
}