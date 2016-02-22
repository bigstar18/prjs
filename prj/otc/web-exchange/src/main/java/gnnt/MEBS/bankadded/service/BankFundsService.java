package gnnt.MEBS.bankadded.service;

import gnnt.MEBS.bankadded.dao.BankFundsDao;
import gnnt.MEBS.bankadded.model.BankFunds;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bankFundsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BankFundsService
  extends BaseService<BankFunds>
{
  private final transient Log logger = LogFactory.getLog(BankFundsService.class);
  @Autowired
  @Qualifier("bankFundsDao")
  private BankFundsDao bankFundsDao;
  
  public BaseDao getDao()
  {
    return this.bankFundsDao;
  }
}
