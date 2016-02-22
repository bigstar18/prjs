package gnnt.MEBS.announcement.service;

import gnnt.MEBS.announcement.dao.MemberRoleDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberRoleServie")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberRoleServie
  extends BaseService
{
  @Autowired
  @Qualifier("memberRoleDao")
  private MemberRoleDao memberRoleDao;
  
  public BaseDao getDao()
  {
    return this.memberRoleDao;
  }
}
