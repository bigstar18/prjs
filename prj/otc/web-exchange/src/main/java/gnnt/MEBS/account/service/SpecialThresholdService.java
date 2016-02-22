package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.SpecialThresholdDao;
import gnnt.MEBS.account.model.SpecialThreshold;
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

@Service("specialThresholdService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialThresholdService
  extends BaseService<SpecialThreshold>
{
  private final transient Log logger = LogFactory.getLog(SpecialThresholdService.class);
  @Autowired
  @Qualifier("specialThresholdDao")
  private SpecialThresholdDao specialThresholdDao;
  
  public BaseDao getDao()
  {
    return this.specialThresholdDao;
  }
  
  public SpecialThreshold get(SpecialThreshold specialThreshold)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", specialThreshold.getFirmId());
    List list = getDao().getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      specialThreshold = new SpecialThreshold();
      specialThreshold = (SpecialThreshold)list.get(0);
    }
    return specialThreshold;
  }
}
