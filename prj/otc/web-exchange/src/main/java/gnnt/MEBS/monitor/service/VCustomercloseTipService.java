package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomerclosTipeDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomercloseTipService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class VCustomercloseTipService
{
  @Autowired
  @Qualifier("vCustomerclosTipeDao")
  private VCustomerclosTipeDao vCustomercloseTipDao;
  
  public BaseDao getDao()
  {
    return this.vCustomercloseTipDao;
  }
}
