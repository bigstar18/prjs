package gnnt.MEBS.query.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.query.dao.FundOutInSearchOfMemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("fundOutInSearchOfMemberService")
public class FundOutInSearchOfMemberService
  extends BaseService
{
  @Autowired
  @Qualifier("fundOutInSearchOfMemberDao")
  private FundOutInSearchOfMemberDao fundOutInSearchOfMemberDao;
  
  public BaseDao getDao()
  {
    return this.fundOutInSearchOfMemberDao;
  }
}
