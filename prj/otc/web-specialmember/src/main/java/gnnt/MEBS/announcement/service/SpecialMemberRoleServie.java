package gnnt.MEBS.announcement.service;

import gnnt.MEBS.announcement.dao.SpecialMemberRoleDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberRoleServie")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberRoleServie
  extends BaseService
{
  @Autowired
  @Qualifier("specialMemberRoleDao")
  private SpecialMemberRoleDao specialMemberRoleDao;
  
  public BaseDao getDao()
  {
    return this.specialMemberRoleDao;
  }
}
