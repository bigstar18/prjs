package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomerfundDaoSimple;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomerfundServiceSimple")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class VCustomerfundServiceSimple
  extends BaseService
{
  @Autowired
  @Qualifier("vCustomerfundDaoSimple")
  private VCustomerfundDaoSimple vCustomerfundDaoSimple;
  
  public BaseDao getDao()
  {
    return this.vCustomerfundDaoSimple;
  }
}
