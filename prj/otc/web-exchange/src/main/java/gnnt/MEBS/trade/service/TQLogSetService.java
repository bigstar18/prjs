package gnnt.MEBS.trade.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.trade.dao.TQLogSetDao;
import gnnt.MEBS.trade.model.TQLogSet;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("tqLogSetService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TQLogSetService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(TQLogSetService.class);
  @Autowired
  @Qualifier("tqLogSetDao")
  private TQLogSetDao tqLogSetDao;
  
  public BaseDao getDao()
  {
    return this.tqLogSetDao;
  }
  
  public int add(TQLogSet obj)
  {
    this.logger.debug("enter add");
    int num = 0;
    if (obj != null)
    {
      this.tqLogSetDao.add(obj);
      num = 2;
    }
    return num;
  }
  
  public int update(TQLogSet obj)
  {
    this.logger.debug("enter update");
    int num = 0;
    if (obj != null)
    {
      this.tqLogSetDao.update(obj);
      num = 3;
    }
    return num;
  }
  
  public TQLogSet get(String commodityId)
  {
    TQLogSet obj = null;
    
    List<TQLogSet> list = this.tqLogSetDao.getList(commodityId);
    if ((list != null) && (list.size() > 0)) {
      obj = (TQLogSet)list.get(0);
    }
    return obj;
  }
}
