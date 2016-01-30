package gnnt.MEBS.logonService.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDAOJdbc extends JdbcDaoSupport
{
  protected volatile Log logger = LogFactory.getLog(getClass());
}