package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.TradeAuth;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("tradeAuthDao")
public class TradeAuthDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TradeAuthDao.class);
  
  public Class getEntityClass()
  {
    return new TradeAuth().getClass();
  }
  
  public List<TradeAuth> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new TradeAuth(primary.commodityId,commodity.name,primary.firmId,primary.m_B_Open,primary.m_B_Close,primary.l_B_Open,primary.l_B_CloseLose,primary.l_B_CloseProfit,primary.m_S_Open,primary.m_S_Close,primary.l_S_Open,primary.l_S_CloseLose,primary.l_S_CloseProfit,primary.cancel_L_Open,primary.cancel_StopLoss,primary.cancel_StopProfit,primary.display) from TradeAuth as primary,Commodity as commodity where primary.commodityId=commodity.id";
    


    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return queryByHQL(hql, names, values, pageInfo, null);
  }
}
