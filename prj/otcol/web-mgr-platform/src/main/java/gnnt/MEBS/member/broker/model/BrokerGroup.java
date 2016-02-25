package gnnt.MEBS.member.broker.model;

public class BrokerGroup
  extends Cloneable
{
  private long groupId;
  private String groupName;
  private String brokerId;
  
  public long getGroupId()
  {
    return this.groupId;
  }
  
  public void setGroupId(long paramLong)
  {
    this.groupId = paramLong;
  }
  
  public String getGroupName()
  {
    return this.groupName;
  }
  
  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
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
