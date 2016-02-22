package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import java.sql.Timestamp;

public class TQLog
  extends Clone
{
  private String commodityId;
  private Timestamp actionTime;
  private Double quoprice;
  private String operator;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public Double getQuoprice()
  {
    return this.quoprice;
  }
  
  public void setQuoprice(Double quoprice)
  {
    this.quoprice = quoprice;
  }
  
  public Timestamp getActionTime()
  {
    return this.actionTime;
  }
  
  public void setActionTime(Timestamp actionTime)
  {
    this.actionTime = actionTime;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
