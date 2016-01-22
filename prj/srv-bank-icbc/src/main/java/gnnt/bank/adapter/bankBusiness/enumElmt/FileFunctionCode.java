package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum FileFunctionCode
{
  CODE0000(0), 
  CODE0001(1), 
  CODE0002(2);

  private int value;

  public int getValue() { return this.value; }

  private FileFunctionCode(int value) {
    this.value = value;
  }
}