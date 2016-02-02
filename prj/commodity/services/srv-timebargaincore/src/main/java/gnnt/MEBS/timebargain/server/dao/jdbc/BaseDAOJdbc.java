package gnnt.MEBS.timebargain.server.dao.jdbc;

import gnnt.MEBS.timebargain.server.dao.DAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDAOJdbc
  extends JdbcDaoSupport
  implements DAO
{
  protected final Log log = LogFactory.getLog(getClass());
}
