package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum BusinessCode
{
  CODE20001(20001), 
  CODE20002(20002), 
  CODE20003(20003), 
  CODE20004(20004), 

  CODE21001(21001), 
  CODE21002(21002), 
  CODE21003(21003), 
  CODE21004(21004), 
  CODE21005(21005), 
  CODE21006(21006), 

  CODE22001(22001), 
  CODE22002(22002), 
  CODE22003(22003), 
  CODE22004(22004), 
  CODE22005(22005), 
  CODE22006(22006), 

  CODE23001(23001), 
  CODE23002(23002), 
  CODE23003(23003), 

  CODE21013(21013);

  private int value;

  public int getValue() { return this.value; }

  private BusinessCode(int value) {
    this.value = value;
  }
}