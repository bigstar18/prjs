package gnnt.MEBS.delivery.command.model;

import gnnt.MEBS.delivery.model.settle.SettleMatch;

public class SettleAddObject
{
  private SettleMatch settleMatch;
  private String operator;
  private String settleMatchOldId;
  
  public String getSettleMatchOldId()
  {
    return this.settleMatchOldId;
  }
  
  public void setSettleMatchOldId(String paramString)
  {
    this.settleMatchOldId = paramString;
  }
  
  public SettleMatch getSettleMatch()
  {
    return this.settleMatch;
  }
  
  public void setSettleMatch(SettleMatch paramSettleMatch)
  {
    this.settleMatch = paramSettleMatch;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String paramString)
  {
    this.operator = paramString;
  }
}
