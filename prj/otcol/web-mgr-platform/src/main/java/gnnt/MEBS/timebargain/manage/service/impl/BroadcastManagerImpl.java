package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.BroadcastDAO;
import gnnt.MEBS.timebargain.manage.model.Broadcast;
import gnnt.MEBS.timebargain.manage.service.BroadcastManager;
import java.util.List;

public class BroadcastManagerImpl
  extends BaseManager
  implements BroadcastManager
{
  private BroadcastDAO dao;
  
  public void setBroadcastDAO(BroadcastDAO paramBroadcastDAO)
  {
    this.dao = paramBroadcastDAO;
  }
  
  public Broadcast getBroadcastById(Long paramLong)
  {
    return this.dao.getBroadcastById(paramLong);
  }
  
  public List getBroadcasts(Broadcast paramBroadcast)
  {
    return this.dao.getBroadcasts(paramBroadcast);
  }
  
  public void insertBroadcast(Broadcast paramBroadcast)
  {
    this.dao.insertBroadcast(paramBroadcast);
  }
  
  public void updateBroadcast(Broadcast paramBroadcast)
  {
    this.dao.updateBroadcast(paramBroadcast);
  }
  
  public void deleteBroadcastById(Long paramLong)
  {
    this.dao.deleteBroadcastById(paramLong);
  }
  
  public List getHisBroadcast(String paramString)
  {
    return this.dao.getHisBroadcast(paramString);
  }
  
  public void insertHisBroadcast(Broadcast paramBroadcast)
  {
    this.dao.insertHisBroadcast(paramBroadcast);
  }
  
  public List getBroadcastWait()
  {
    return this.dao.getBroadcastWait();
  }
  
  public Broadcast getBroadcastWaitById(Long paramLong)
  {
    return this.dao.getBroadcastWaitById(paramLong);
  }
  
  public void updateBroadcastWait(Broadcast paramBroadcast)
  {
    this.dao.updateBroadcastWait(paramBroadcast);
  }
  
  public void deleteBroadcastWaitById(Long paramLong)
  {
    this.dao.deleteBroadcastWaitById(paramLong);
  }
  
  public List getBroadcastAready()
  {
    return this.dao.getBroadcastAready();
  }
  
  public void deleteBroadcastAreadyById(Long paramLong)
  {
    this.dao.deleteBroadcastAreadyById(paramLong);
  }
  
  public void hisBroadcast()
  {
    this.dao.hisBroadcast();
  }
  
  public Broadcast getHisBroadcastByKye(Broadcast paramBroadcast)
  {
    return this.dao.getHisBroadcastByKye(paramBroadcast);
  }
}
