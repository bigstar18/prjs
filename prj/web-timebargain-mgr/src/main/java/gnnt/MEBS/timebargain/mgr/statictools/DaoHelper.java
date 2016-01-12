package gnnt.MEBS.timebargain.mgr.statictools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("com_ajaxHelper")
public class DaoHelper extends JdbcDaoSupport
{
  @Resource(name="jdbcTemplate")
  public void setSuperJdbcTemplate(JdbcTemplate jdbcTemplate)
  {
    super.setJdbcTemplate(jdbcTemplate);
  }

  public List queryBySQL(String sql) {
    return getJdbcTemplate().queryForList(sql);
  }

  public void updateBySQL(String sql)
  {
    this.logger.debug(sql);
    if (sql != null)
      getJdbcTemplate().update(sql);
  }

  public void updateBySQL(String sql, Object[] params, int[] dataTypes)
  {
    this.logger.debug(sql);
    for (int i = 0; i < params.length; i++)
      this.logger.debug("params[" + i + "]: " + params[i]);
    if (sql != null)
      getJdbcTemplate().update(sql, params, dataTypes);
  }

  public int updateBySQL(String sql, Object[] params)
  {
    this.logger.debug(sql);
    int k = 0;
    for (int i = 0; i < params.length; i++)
      this.logger.debug("params[" + i + "]: " + params[i]);
    if (sql != null) {
      k = getJdbcTemplate().update(sql, params);
    }
    return k;
  }

  public int queryForInt(String sql, Object[] params)
  {
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        Object obj = params[i];
        if ((obj instanceof Date))
          params[i] = new Timestamp(((Date)obj).getTime());
        this.logger.debug("param[" + i + "]:" + params[i]);
      }
    }
    this.logger.debug(sql);
    return getJdbcTemplate().queryForInt(sql, params);
  }

  public int queryForInt(String sql) {
    this.logger.debug(sql);
    return getJdbcTemplate().queryForInt(sql);
  }

  public long queryForLong(String sql) {
    this.logger.debug(sql);
    return getJdbcTemplate().queryForLong(sql);
  }

  public Object queryForObject(String sql, Object[] params, Class objClass) {
    this.logger.debug(sql);
    Object obj = null;
    try {
      obj = getJdbcTemplate().queryForObject(sql, params, objClass);
    } catch (Exception e) {
      this.logger.error(e);
    }
    return obj;
  }

  public Object queryForObject(String sql, Class objClass) {
    this.logger.debug(sql);
    Object obj = null;
    try {
      obj = getJdbcTemplate().queryForObject(sql, objClass);
    } catch (Exception e) {
      this.logger.error(e);
    }
    return obj;
  }

  public Object queryForObject(String sql, Object[] params, RowMapper rowMapper) {
    this.logger.debug(sql);
    Object obj = null;
    try {
      obj = getJdbcTemplate().queryForObject(sql, params, rowMapper);
    } catch (Exception e) {
      this.logger.error(e);
    }
    return obj;
  }

  public List query(String sql, Object[] args, int[] argTypes, RowMapper rowMapper) {
    this.logger.debug(sql);
    List list = null;
    try {
      list = getJdbcTemplate().query(sql, args, argTypes, rowMapper);
    } catch (Exception e) {
      this.logger.error(e);
    }
    return list;
  }

  public void executeStoredSubprogram(String storedSubprogram)
  {
    if (storedSubprogram == null)
      return;
    CallableStatementCallback cb = new CallableStatementCallback() {
      public Object doInCallableStatement(CallableStatement cs) throws SQLException {
        cs.execute();
        return null;
      }
    };
    String sql = "{call " + storedSubprogram + " }";
    this.logger.debug(sql);
    getJdbcTemplate().execute(sql, cb);
  }

  public int callStoredProcedure(String sp_call_str)
  {
    try
    {
      Connection conn = getJdbcTemplate().getDataSource().getConnection();
      CallableStatement cstmt = conn.prepareCall("{?=call " + sp_call_str + "}");
      cstmt.registerOutParameter(1, 4);
      cstmt.execute();
      return cstmt.getInt(1);
    } catch (SQLException se) {
      this.logger.error("Call Stored Procedure Failed!" + se.getMessage());
    }return -1;
  }
}