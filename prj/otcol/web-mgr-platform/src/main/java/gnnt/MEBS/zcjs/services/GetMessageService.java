package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.GetMessageDao;
import gnnt.MEBS.zcjs.model.Broadcast;
import gnnt.MEBS.zcjs.model.TradeMsgFeedback;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_getMessageService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class GetMessageService
{
  @Autowired
  @Qualifier("z_getMessageDao")
  private GetMessageDao getMessageDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<TradeMsgFeedback> getMessageTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.getMessageDao.getMessageList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Broadcast> getBroadcastMessageTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.getMessageDao.getBroadcastMessageList(paramQueryConditions, paramPageInfo);
  }
  
  public void updateRead(long paramLong)
  {
    this.getMessageDao.updateRead(paramLong);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<TradeMsgFeedback> getTradeMsgFeedbackList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.getMessageDao.getTradeMsgFeedbackList(paramQueryConditions, paramPageInfo);
  }
  
  public List<Map<String, Object>> getAllTraderId()
  {
    return this.getMessageDao.getAllTraderId();
  }
}
