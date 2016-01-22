package gnnt.bank.adapter.bankBusiness.enumElmt;

public enum TradeType
{
  DEBTOUT("00"), 
  DEBTIN("01"), 
  COMMISSIONOUT("02"), 
  DUTYOUT("03");

  private String value;

  public String getValue() { return this.value; }

  private TradeType(String value) {
    this.value = value;
  }
}