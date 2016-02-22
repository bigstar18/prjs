package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.TCExchagerate;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("tcExchagerateDao")
public class TCExchagerateDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TCExchagerateDao.class);
  
  public Class getEntityClass()
  {
    return new TCExchagerate().getClass();
  }
  
  public List<TCExchagerate> getList(String commodityId)
  {
    String hql = "from gnnt.MEBS.trade.model.TCExchagerate tc where tc.commodityId = '" + commodityId + "'";
    
    List<TCExchagerate> list = queryByHQL(hql, null, null, null, null);
    
    return list;
  }
  
  public List getList()
  {
    String hql = "select distinct new gnnt.MEBS.trade.model.Commodity(tc.inCommodityId) from gnnt.MEBS.trade.model.TCExchagerate tc";
    
    List list = queryByHQL(hql, null, null, null, null);
    
    return list;
  }
}
