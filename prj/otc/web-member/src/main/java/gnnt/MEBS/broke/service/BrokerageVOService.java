package gnnt.MEBS.broke.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.dao.BrokerageVODao;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.BrokerageVO;
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

@Service("brokerageVOService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerageVOService
  extends BaseService<Brokerage>
{
  private final transient Log logger = LogFactory.getLog(BrokerageVOService.class);
  @Autowired
  @Qualifier("brokerageVODao")
  private BrokerageVODao brokerageVODao;
  
  public BaseDao getDao()
  {
    return this.brokerageVODao;
  }
  
  public List<BrokerageVO> getListForSimple(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.brokerageVODao.getListForSimple(conditions, pageInfo);
  }
}
