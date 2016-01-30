package gnnt.MEBS.common.core.dao.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDAOJdbc
  extends JdbcDaoSupport
{
  protected final Log log = LogFactory.getLog(getClass());
}
