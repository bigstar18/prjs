package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberThresholdDao;
import gnnt.MEBS.account.model.MemberThreshold;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberThresholdService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberThresholdService
  extends BaseService<MemberThreshold>
{
  private final transient Log logger = LogFactory.getLog(MemberThresholdService.class);
  @Autowired
  @Qualifier("memberThresholdDao")
  private MemberThresholdDao memThresholdDao;
  
  public BaseDao getDao()
  {
    return this.memThresholdDao;
  }
  
  public MemberThreshold get(MemberThreshold memberThreshold)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.memberNo", "=", memberThreshold.getMemberNo());
    List list = getDao().getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      memberThreshold = new MemberThreshold();
      memberThreshold = (MemberThreshold)list.get(0);
    }
    return memberThreshold;
  }
}
