package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.TradeMsgFeedbackDao;
import gnnt.MEBS.zcjs.model.TradeMsgFeedback;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeMsgFeedbackService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeMsgFeedbackService
{
  @Autowired
  @Qualifier("z_tradeMsgFeedbackDao")
  private TradeMsgFeedbackDao tradeMsgFeedbackDao;
  
  public void add(TradeMsgFeedback paramTradeMsgFeedback)
  {
    paramTradeMsgFeedback.setTradeMsgFeedbackId(this.tradeMsgFeedbackDao.getId());
    this.tradeMsgFeedbackDao.add(paramTradeMsgFeedback);
  }
  
  public List<TradeMsgFeedback> getObjectList(QueryConditions paramQueryConditions)
  {
    return this.tradeMsgFeedbackDao.getObject(paramQueryConditions);
  }
  
  public void allDelete()
  {
    this.tradeMsgFeedbackDao.allDelete();
  }
}
