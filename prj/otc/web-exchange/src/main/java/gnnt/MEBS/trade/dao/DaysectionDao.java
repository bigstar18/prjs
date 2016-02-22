package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.DaySection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("daysectionDao")
public class DaysectionDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(DaysectionDao.class);
  
  public Class getEntityClass()
  {
    return new DaySection().getClass();
  }
}
