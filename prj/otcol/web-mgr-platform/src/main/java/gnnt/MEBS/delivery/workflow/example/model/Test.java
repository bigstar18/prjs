package gnnt.MEBS.delivery.workflow.example.model;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;

public class Test
  extends WorkFlowClone
{
  private long id;
  private String name;
  private String addrestes;
  private int status;
  
  public int getCurrentStatus()
  {
    return this.status;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getAddrestes()
  {
    return this.addrestes;
  }
  
  public void setAddrestes(String paramString)
  {
    this.addrestes = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}
