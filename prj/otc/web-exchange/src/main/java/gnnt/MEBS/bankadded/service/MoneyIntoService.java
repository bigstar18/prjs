package gnnt.MEBS.bankadded.service;

import gnnt.MEBS.bankadded.dao.BanksDao;
import gnnt.MEBS.bankadded.dao.MoneyIntoDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("moneyIntoService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MoneyIntoService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MoneyIntoService.class);
  @Autowired
  @Qualifier("moneyIntoDao")
  private MoneyIntoDao moneyIntoDao;
  @Autowired
  @Qualifier("banksDao")
  private BanksDao banksDao;
  
  public BaseDao getDao()
  {
    return this.banksDao;
  }
  
  public int add(Map obj, int type)
  {
    int num = 0;
    num = this.moneyIntoDao.addMoneyInto(obj, type);
    return num;
  }
  
  public int outMoney(Map obj, int type)
  {
    int num = 1;
    num = this.moneyIntoDao.outMoney(obj, type);
    return num;
  }
  
  public List getBankId(String firmid)
  {
    return this.moneyIntoDao.getBankId(firmid);
  }
}
