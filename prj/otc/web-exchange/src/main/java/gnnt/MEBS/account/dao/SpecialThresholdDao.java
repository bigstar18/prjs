package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.SpecialThreshold;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialThresholdDao")
public class SpecialThresholdDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialThresholdDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialThreshold().getClass();
  }
  
  public List<SpecialThreshold> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new SpecialThreshold(primary.firmId,specialMember.name,primary.minRiskFund,primary.warnTh,primary.holdWarnTh,primary.frozenTh) from SpecialThreshold as primary,SpecialMember as specialMember where primary.firmId=specialMember.id and specialMember.id in (select s_memberNo from SpecialMemberStatus specialMemberStatus where specialMemberStatus.status in ('N','F'))";
    


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
