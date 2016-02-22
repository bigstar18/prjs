package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.SpecialMemberRightDao;
import gnnt.MEBS.account.model.SpecialMemberRight;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberRightService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberRightService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberRightService.class);
  @Autowired
  @Qualifier("specialMemberRightDao")
  private SpecialMemberRightDao rightDao;
  
  public BaseDao getDao()
  {
    return this.rightDao;
  }
  
  public SpecialMemberRight getTreeRight()
  {
    SpecialMemberRight right = null;
    right = this.rightDao.loadTreeRight(0L, -2, 0);
    return right;
  }
}
