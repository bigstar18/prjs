package gnnt.MEBS.test.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.test.dao.StuTestDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("stuTestService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class StuTestService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(StuTestService.class);
  @Autowired
  @Qualifier("stuTestDao")
  private StuTestDao stuTestDao;
  
  public BaseDao getDao()
  {
    return this.stuTestDao;
  }
}
