package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.BrokerageDivertDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerageDivertService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerageDivertService
  extends BaseService
{
  @Autowired
  @Qualifier("brokerageDivertDao")
  private BrokerageDivertDao brokerageDivertDao;
  
  public BaseDao getDao()
  {
    return this.brokerageDivertDao;
  }
  
  public int checkBrokerageDivert(String brokerageNo, String memberid)
  {
    return this.brokerageDivertDao.checkBrokerageDivert(brokerageNo, memberid);
  }
  
  public void add(String brokerageNo, String memberid)
  {
    this.brokerageDivertDao.brokerageDivert(brokerageNo, memberid);
  }
}
