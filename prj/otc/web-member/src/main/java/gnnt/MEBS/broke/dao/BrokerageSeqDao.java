package gnnt.MEBS.broke.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BrokerageSeqDao
  extends JdbcDaoSupport
{
  public Long brokerageSeq()
  {
    String sqlString = "select  SEQ_BROKERNO.nextval from dual";
    Long returnLong = Long.valueOf(getJdbcTemplate().queryForLong(sqlString));
    return returnLong;
  }
}
