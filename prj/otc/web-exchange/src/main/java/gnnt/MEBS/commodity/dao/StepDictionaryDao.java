package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.StepDictionary;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("stepDictionaryDao")
public class StepDictionaryDao
  extends BaseDao<StepDictionary>
{
  private final transient Log logger = LogFactory.getLog(StepDictionaryDao.class);
  
  public Class getEntityClass()
  {
    return new StepDictionary().getClass();
  }
  
  public List<StepDictionary> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "from StepDictionary as primary where 1=1";
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
  
  public int getTotal()
  {
    String hql = "select count(primary.stepNo) from StepDictionary as primary where primary.ladderCode='MemberFunds'";
    return totalRow(hql, null, null, null);
  }
  
  public int getDelayTotal()
  {
    String hql = "select count(primary.stepNo) from StepDictionary as primary where primary.ladderCode='DelayDays'";
    return totalRow(hql, null, null, null);
  }
}
