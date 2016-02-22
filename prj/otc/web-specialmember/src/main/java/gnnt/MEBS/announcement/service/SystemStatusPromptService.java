package gnnt.MEBS.announcement.service;

import gnnt.MEBS.announcement.dao.SystemStatusPromptDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("systemStatusPromptService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SystemStatusPromptService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(SystemStatusPromptService.class);
  @Autowired
  @Qualifier("systemStatusPromptDao")
  private SystemStatusPromptDao systemStatusPromptDao;
  
  public BaseDao getDao()
  {
    return this.systemStatusPromptDao;
  }
}
