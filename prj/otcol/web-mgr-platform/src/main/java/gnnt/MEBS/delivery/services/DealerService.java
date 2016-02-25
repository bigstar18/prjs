package gnnt.MEBS.delivery.services;

import gnnt.MEBS.delivery.dao.DealerDao;
import gnnt.MEBS.delivery.model.Dealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_dealerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class DealerService
{
  @Autowired
  @Qualifier("w_dealerDao")
  private DealerDao dealerDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Dealer getDealerById(String paramString)
  {
    return this.dealerDao.getDealerById(paramString);
  }
}
