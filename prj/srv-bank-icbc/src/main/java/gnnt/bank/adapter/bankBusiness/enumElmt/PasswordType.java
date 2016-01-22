package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum PasswordType
{
  QRY(0), 
  TAKEOUT(1), 
  TRANSFER(2), 
  TRADE(4);

  private int value;

  public int getValue() { return this.value; }

  private PasswordType(int value) {
    this.value = value;
  }
}