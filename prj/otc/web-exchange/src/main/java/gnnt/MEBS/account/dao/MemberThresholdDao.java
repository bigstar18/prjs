package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberThreshold;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberThresholdDao")
public class MemberThresholdDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberThresholdDao.class);
  
  public Class getEntityClass()
  {
    return new MemberThreshold().getClass();
  }
  
  public List<MemberThreshold> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new MemberThreshold(primary.memberNo,memberInfo.name,primary.minRiskFund,primary.warnTh,primary.frozenTh,primary.m_SelfTradeRate,primary.netHoldWarnTh,primary.cu_F_WarnTh,primary.m_CU_BalanceSum) from MemberThreshold as primary,MemberInfo as memberInfo where primary.memberNo=memberInfo.id and memberInfo.id in (select id from CompMember as compMember where compMember.status in ('N','F'))";
    

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
