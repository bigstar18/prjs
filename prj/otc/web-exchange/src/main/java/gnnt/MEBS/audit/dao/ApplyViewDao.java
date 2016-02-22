package gnnt.MEBS.audit.dao;

import gnnt.MEBS.audit.model.ApplyView;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("applyViewDao")
public class ApplyViewDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(ApplyViewDao.class);
  
  public Class getEntityClass()
  {
    return new ApplyView().getClass();
  }
}
