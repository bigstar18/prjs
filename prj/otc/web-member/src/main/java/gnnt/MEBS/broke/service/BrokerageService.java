package gnnt.MEBS.broke.service;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.broke.dao.BrokerageDao;
import gnnt.MEBS.broke.dao.BrokerageProDao;
import gnnt.MEBS.broke.dao.BrokerageSeqDao;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.config.constant.PasswordConstant;
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

@Service("brokerageService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerageService
  extends BaseService<Brokerage>
{
  private final transient Log logger = LogFactory.getLog(BrokerageService.class);
  @Autowired
  @Qualifier("brokerageDao")
  private BrokerageDao brokerageDao;
  @Autowired
  @Qualifier("brokerageProDao")
  private BrokerageProDao brokerageProDao;
  @Autowired
  @Qualifier("brokerageSeqDao")
  private BrokerageSeqDao brokerageSeqDao;
  
  public BaseDao getDao()
  {
    return this.brokerageDao;
  }
  
  public int add(Brokerage obj)
  {
    this.logger.debug("enter add");
    int num = 0;
    Brokerage brokerage = (Brokerage)obj.clone();
    brokerage.setPassword(MD5.getMD5(brokerage.getId(), PasswordConstant.PASSWORD));
    getDao().add(brokerage);
    num = 2;
    return num;
  }
  
  public int delete(Brokerage obj)
  {
    int num = this.brokerageProDao.brokerageDeletePro(obj.getBrokerageNo());
    if (num > 0)
    {
      getDao().delete(obj);
      num = 4;
    }
    return num;
  }
  
  public Long getBrokerageSeq()
  {
    return this.brokerageSeqDao.brokerageSeq();
  }
  
  public int update(Brokerage obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Clone objFor = copyObject(obj);
    ((Brokerage)objFor).setOrganizationSet(obj.getOrganizationSet());
    this.brokerageDao.update(objFor);
    num = this.brokerageProDao.brokerageUpdatePro(obj.getBrokerageNo(), obj.getOrganizationNO());
    num = 3;
    return num;
  }
}
