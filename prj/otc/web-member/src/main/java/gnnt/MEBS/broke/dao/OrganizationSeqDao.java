package gnnt.MEBS.broke.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class OrganizationSeqDao
  extends JdbcDaoSupport
{
  public Long getOrganizationSeq()
  {
    String sqlString = "select  SEQ_ORGANIZATIONON.nextval from dual";
    Long returnLong = Long.valueOf(getJdbcTemplate().queryForLong(sqlString));
    return returnLong;
  }
}
