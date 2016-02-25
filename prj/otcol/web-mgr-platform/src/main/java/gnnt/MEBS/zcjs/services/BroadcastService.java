package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.BroadcastDao;
import gnnt.MEBS.zcjs.model.Broadcast;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_broadcastService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BroadcastService
{
  @Autowired
  @Qualifier("z_broadcastDao")
  private BroadcastDao broadcastDao;
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.broadcastDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Broadcast> getObjectList(QueryConditions paramQueryConditions)
  {
    return this.broadcastDao.getObjectList(paramQueryConditions);
  }
  
  public Broadcast getObjectById(long paramLong)
  {
    return this.broadcastDao.getObject(paramLong);
  }
  
  public void update(Broadcast paramBroadcast)
  {
    this.broadcastDao.update(paramBroadcast);
  }
  
  public void add(Broadcast paramBroadcast)
  {
    this.broadcastDao.add(paramBroadcast);
  }
  
  public void delete(Broadcast paramBroadcast)
  {
    this.broadcastDao.delete(paramBroadcast);
  }
}
