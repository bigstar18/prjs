package gnnt.MEBS.test.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.test.model.StuTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("stuTestDao")
public class StuTestDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(StuTestDao.class);
  
  public Class getEntityClass()
  {
    return new StuTest().getClass();
  }
}
