package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum CashExCode
{
  CODE0(0), 
  CODE1(1);

  private int value;

  public int getValue() { return this.value; }

  private CashExCode(int value) {
    this.value = value;
  }
}