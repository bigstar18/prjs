package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberInfoDao")
public class MemberInfoDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberInfoDao.class);
  
  public Class getEntityClass()
  {
    return new MemberInfo().getClass();
  }
  
  public List<MemberInfo> memberInfoListFromSpecial(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new MemberInfo(memberInfo.id, memberInfo.name) from MemberRelation as memberRelation,MemberInfo as memberInfo where memberRelation.memberNo=memberInfo.id ";
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
