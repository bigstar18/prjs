package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.TraderDao;
import gnnt.MEBS.account.model.Trader;
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

@Service("traderPWService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TraderPWService
  extends BaseService<Trader>
{
  private final transient Log logger = LogFactory.getLog(TraderPWService.class);
  @Autowired
  @Qualifier("traderDao")
  private TraderDao traderDao;
  
  public BaseDao getDao()
  {
    return this.traderDao;
  }
  
  public int update(Trader obj)
  {
    int num = 0;
    Trader objFor = (Trader)copyObject(obj);
    objFor.setPassword(MD5.getMD5(obj.getId(), obj.getPassword()));
    this.logger.debug("phonepwd:" + objFor.getPassword());
    getDao().update(objFor);
    num = 1;
    return num;
  }
}
