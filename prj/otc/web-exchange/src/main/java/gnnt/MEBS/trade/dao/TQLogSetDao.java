package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.TQLogSet;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("tqLogSetDao")
public class TQLogSetDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TQLogSetDao.class);
  
  public Class getEntityClass()
  {
    return new TQLogSet().getClass();
  }
  
  public List<TQLogSet> getList(String commodityId)
  {
    String hql = "from gnnt.MEBS.trade.model.TQLogSet s where s.commodityId = '" + commodityId + "'";
    
    List<TQLogSet> list = queryByHQL(hql, null, null, null, null);
    
    return list;
  }
}
