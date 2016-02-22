package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.MemberHoldSumGroupDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberHoldSumGroupService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberHoldSumGroupService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MemberHoldSumGroupService.class);
  @Autowired
  @Qualifier("memberHoldSumGroupDao")
  private MemberHoldSumGroupDao memberHoldSumGroupDao;
  
  public BaseDao getDao()
  {
    return this.memberHoldSumGroupDao;
  }
}
