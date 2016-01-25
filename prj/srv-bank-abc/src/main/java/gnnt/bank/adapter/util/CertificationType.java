package gnnt.bank.adapter.util;

public enum CertificationType
{
  IDCARD(10), 
  POSSCARD(11), 
  OFFICERCARD(12), 
  SOLDIERCARD(13), 
  RETURNCARD(14), 
  EGISTEREDCARD(15), 
  BUSINESSLICENCE(16), 
  LEGALPERSONCODE(17), 
  OTHER(20);

  private int value;

  public int getValue() { return this.value; }

  private CertificationType(int value) {
    this.value = value;
  }
}