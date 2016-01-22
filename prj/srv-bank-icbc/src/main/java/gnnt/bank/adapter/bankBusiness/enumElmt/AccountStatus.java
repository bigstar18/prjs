package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum AccountStatus
{
  NORMAL(0), 
  FROZEN(1), 
  LOSS(2);

  private int value;

  public int getValue() { return this.value; }

  private AccountStatus(int value) {
    this.value = value;
  }
}