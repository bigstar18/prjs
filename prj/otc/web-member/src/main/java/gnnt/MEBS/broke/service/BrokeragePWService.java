package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.BrokerageDao;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokeragePWService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokeragePWService
  extends BaseService<Brokerage>
{
  private final transient Log logger = LogFactory.getLog(BrokeragePWService.class);
  @Autowired
  @Qualifier("brokerageDao")
  private BrokerageDao brokerageDao;
  
  public BaseDao getDao()
  {
    return this.brokerageDao;
  }
  
  public int update(Brokerage obj)
  {
    this.logger.debug("enter update");
    int num = 0;
    Brokerage brokerage = (Brokerage)copyObject(obj);
    brokerage.setPassword(MD5.getMD5(obj.getId(), obj.getPassword()));
    getDao().update(brokerage);
    num = 3;
    return num;
  }
}
