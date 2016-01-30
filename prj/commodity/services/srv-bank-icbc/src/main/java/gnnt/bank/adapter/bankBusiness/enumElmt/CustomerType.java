package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum CustomerType
{
  PERSONAL(0), 
  ORGANIZE(1), 
  AGENT(2);

  private int value;

  public int getValue() { return this.value; }

  private CustomerType(int value) {
    this.value = value;
  }
}