package gnnt.MEBS.settlement.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.settlement.model.QuotePointRunTime;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("quotePointRunTimeDao")
public class QuotePointRunTimeDao
  extends BaseDao<QuotePointRunTime>
{
  private final transient Log logger = LogFactory.getLog(QuotePointRunTimeDao.class);
  
  public Class getEntityClass()
  {
    return new QuotePointRunTime().getClass();
  }
  
  public List<QuotePointRunTime> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new QuotePointRunTime(primary.commodityId, commodity.name,primary.m_FirmId,firm.firmName,firm.firmType,primary.quotePoint_B,primary.quotePoint_S,primary.quotePoint_B_RMB,primary.quotePoint_S_RMB) from QuotePointRunTime as primary,Commodity as commodity,Firm firm  where primary.commodityId=commodity.id and firm.firmId=primary.m_FirmId";
    
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
  
  public List<QuotePointRunTime> getSpemcialMemberNo(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new QuotePointRunTime(t.m_FirmId) from QuotePointRunTime t, SpecialMember m where t.m_FirmId = m.s_memberNo";
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
