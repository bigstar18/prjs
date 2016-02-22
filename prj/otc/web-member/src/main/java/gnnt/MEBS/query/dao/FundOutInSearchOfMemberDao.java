package gnnt.MEBS.query.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.query.model.FundOutInSearchOfMember;
import org.springframework.stereotype.Repository;

@Repository("fundOutInSearchOfMemberDao")
public class FundOutInSearchOfMemberDao
  extends BaseDao<FundOutInSearchOfMember>
{
  public Class getEntityClass()
  {
    return new FundOutInSearchOfMember().getClass();
  }
}
