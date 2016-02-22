package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum BankAccountType
{
  BANKBOOK(0), 
  DEPOSITCARD(1), 
  MULCURRENCYCARD(2);

  private int value;

  public int getValue() { return this.value; }

  private BankAccountType(int value) {
    this.value = value;
  }
}