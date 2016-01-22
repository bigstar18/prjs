package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum SystemType
{
  BF(8);

  private int value;

  public int getValue() { return this.value; }

  private SystemType(int value) {
    this.value = value;
  }
}