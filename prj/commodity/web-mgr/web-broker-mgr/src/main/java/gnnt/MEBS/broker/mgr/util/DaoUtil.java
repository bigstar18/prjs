package gnnt.MEBS.broker.mgr.util;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import java.util.List;
import org.apache.commons.logging.Log;

public class DaoUtil extends StandardDao
{
  public List getListBySql(String paramString)
    throws Exception
  {
    this.logger.debug("????????????????????" + paramString);
    List localList = getListBySql(paramString);
    this.logger.debug("List===========" + localList.size());
    return localList;
  }
}