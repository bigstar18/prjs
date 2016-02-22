package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberRightDao;
import gnnt.MEBS.account.model.MemberRight;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberRightService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberRightService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MemberRightService.class);
  @Autowired
  @Qualifier("memberRightDao")
  private MemberRightDao rightDao;
  
  public BaseDao getDao()
  {
    return this.rightDao;
  }
  
  public MemberRight getTreeRight()
  {
    MemberRight right = null;
    right = this.rightDao.loadTreeRight(0L, -2, 0);
    return right;
  }
}
