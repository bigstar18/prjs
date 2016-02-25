package gnnt.MEBS.member.broker.model;

public class BrokerRight
  extends Cloneable
{
  private Integer rightId;
  private String brokerId;
  
  public Integer getRightId()
  {
    return this.rightId;
  }
  
  public void setRightId(Integer paramInteger)
  {
    this.rightId = paramInteger;
  }
  
  public String getBrokerId()
  {
    return this.brokerId;
  }
  
  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }
}
