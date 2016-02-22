package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.SpecialMemberVODao;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberVOService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberVOService
  extends BaseService<SpecialMember>
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberVOService.class);
  @Autowired
  @Qualifier("specialMemberVODao")
  private SpecialMemberVODao specialMemberVODao;
  
  public BaseDao getDao()
  {
    return this.specialMemberVODao;
  }
}
