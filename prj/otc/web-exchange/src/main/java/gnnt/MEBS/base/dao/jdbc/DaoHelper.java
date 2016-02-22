package gnnt.MEBS.base.dao.jdbc;

import gnnt.MEBS.base.query.jdbc.OrderField;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.util.ThreadStore;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class DaoHelper
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(DaoHelper.class);
  
  @Resource(name="dataSource")
  public void setSuperDataSource(DataSource dataSource)
  {
    super.setDataSource(dataSource);
  }
  
  public List queryBySQL(String sql)
  {
    return queryBySQL(sql, null);
  }
  
  public List queryBySQL(String sql, Object[] params, PageInfo pageInfo)
  {
    return queryBySQL(sql, params, null, pageInfo, null);
  }
  
  public List queryBySQL(String sql, PageInfo pageInfo)
  {
    return queryBySQL(sql, null, null, pageInfo, null);
  }
  
  public List queryBySQL(String sql, Object[] params, int[] dataTypes, PageInfo pageInfo)
  {
    return queryBySQL(sql, params, null, pageInfo, null);
  }
  
  public List queryBySQL(String sql, Object[] params, PageInfo pageInfo, RowMapper rowMapper)
  {
    return queryBySQL(sql, params, null, pageInfo, rowMapper);
  }
  
  public List queryBySQL(String sql, Object[] params, int[] dataTypes, PageInfo pageInfo, RowMapper rowMapper)
  {
    if (params != null) {
      for (int i = 0; i < params.length; i++)
      {
        Object obj = params[i];
        if ((obj instanceof Date)) {
          params[i] = new Timestamp(((Date)obj).getTime());
        }
        this.logger.debug("param[" + i + "]:" + params[i]);
      }
    }
    this.logger.debug("Query Start!");
    int totalRecords = 0;
    if (pageInfo != null)
    {
      if (params != null) {
        totalRecords = 
          getJdbcTemplate().queryForInt(
          "select count(*) cnt from (" + sql + ") t", 
          params);
      } else {
        totalRecords = 
          getJdbcTemplate().queryForInt(
          "select count(*) cnt from (" + sql + ") t");
      }
      this.logger.debug("select count(*) cnt from (" + sql + ") t");
    }
    if (pageInfo != null)
    {
      boolean isPageQuery = pageInfo.getPageSize() > 0;
      

      int not_shown_records = totalRecords - 
        (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
      if ((pageInfo.getOrderFields() != null) && (pageInfo.getOrderString() == null))
      {
        List orderFields = pageInfo.getOrderFields();
        


        int startCount = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize() + 1;
        int endCount = pageInfo.getPageNo() * pageInfo.getPageSize();
        if (isPageQuery)
        {
          String order = "";
          for (int i = 0; i < orderFields.size(); i++)
          {
            OrderField of = (OrderField)orderFields.get(i);
            
            String fieldName = of.getOrderField();
            
            boolean isDesc = of.isOrderDesc();
            if (isDesc) {
              order = order + fieldName + " desc";
            } else {
              order = order + fieldName + " asc";
            }
            if (i != orderFields.size() - 1) {
              order = order + ",";
            }
          }
          String pageSql = "select * from (select rownum num,primary.* from ( select * from (" + sql + ") primary order by " + order + ") primary ) " + 
            "where num between " + startCount + " and " + endCount;
          sql = pageSql;
        }
        else
        {
          StringBuffer orderStmt = new StringBuffer();
          for (int i = 0; i < orderFields.size(); i++)
          {
            OrderField of = (OrderField)orderFields.get(i);
            
            String orderField = of.getOrderField();
            
            boolean isDesc = of.isOrderDesc();
            if (isDesc) {
              orderStmt = 
                orderStmt.append(orderField).append(" desc");
            } else {
              orderStmt = 
                orderStmt.append(orderField).append(" asc");
            }
            if (i != orderFields.size() - 1) {
              orderStmt.append(",");
            }
          }
          sql = sql + " order by " + orderStmt.toString();
        }
      }
      else if (pageInfo.getOrderString() != null)
      {
        sql = sql + " order by " + pageInfo.getOrderString();
      }
    }
    List resultList = null;
    if ((pageInfo != null) && (totalRecords == 0))
    {
      resultList = new ArrayList();
    }
    else
    {
      if (params != null)
      {
        if (rowMapper != null)
        {
          if (dataTypes != null)
          {
            if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
            {
              resultList = getJdbcTemplate().query(sql, params, dataTypes, rowMapper);
            }
            else
            {
              SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql, params, dataTypes);
              ThreadStore.put("sqlRowSet", sqlRowSet);
            }
          }
          else if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
          {
            resultList = getJdbcTemplate().query(sql, params, rowMapper);
          }
          else
          {
            SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql, params, dataTypes);
            ThreadStore.put("sqlRowSet", sqlRowSet);
          }
        }
        else if (dataTypes != null)
        {
          if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
          {
            resultList = getJdbcTemplate().queryForList(sql, params, dataTypes);
          }
          else
          {
            SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql, params, dataTypes);
            ThreadStore.put("sqlRowSet", sqlRowSet);
          }
        }
        else if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
        {
          resultList = getJdbcTemplate().queryForList(sql, params);
        }
        else
        {
          SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql, params);
          ThreadStore.put("sqlRowSet", sqlRowSet);
        }
      }
      else if (rowMapper != null)
      {
        if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
        {
          resultList = getJdbcTemplate().query(sql, rowMapper);
        }
        else
        {
          SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql, params);
          ThreadStore.put("sqlRowSet", sqlRowSet);
          ThreadStore.put("reloadExport", null);
        }
      }
      else if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
      {
        resultList = getJdbcTemplate().queryForList(sql);
      }
      else
      {
        SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql);
        ThreadStore.put("sqlRowSet", sqlRowSet);
        ThreadStore.put("reloadExport", null);
      }
      this.logger.debug(sql);
    }
    if (pageInfo != null)
    {
      int pageSize = pageInfo.getPageSize();
      int pages;
      int pages;
      if (pageSize > 0)
      {
        int pages;
        if (totalRecords % pageSize == 0)
        {
          pages = totalRecords / pageSize;
        }
        else
        {
          int pages;
          if (totalRecords < pageSize) {
            pages = 1;
          } else {
            pages = totalRecords / pageSize + 1;
          }
        }
      }
      else
      {
        pages = 1;
      }
      pageInfo.setTotalRecords(totalRecords);
      pageInfo.setTotalPages(pages);
    }
    return resultList;
  }
  
  public void updateBySQL(String sql)
  {
    this.logger.debug(sql);
    if (sql != null) {
      getJdbcTemplate().update(sql);
    }
  }
  
  public void updateBySQL(String sql, Object[] params, int[] dataTypes)
  {
    this.logger.debug(sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }
    if (sql != null) {
      getJdbcTemplate().update(sql, params, dataTypes);
    }
  }
  
  public int updateBySQL(String sql, Object[] params)
  {
    this.logger.debug(sql);
    int k = 0;
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }
    if (sql != null) {
      k = getJdbcTemplate().update(sql, params);
    }
    return k;
  }
  
  public int queryForInt(String sql, Object[] params)
  {
    if (params != null) {
      for (int i = 0; i < params.length; i++)
      {
        Object obj = params[i];
        if ((obj instanceof Date)) {
          params[i] = new Timestamp(((Date)obj).getTime());
        }
        this.logger.debug("param[" + i + "]:" + params[i]);
      }
    }
    this.logger.debug(sql);
    return getJdbcTemplate().queryForInt(sql, params);
  }
  
  public int queryForInt(String sql)
  {
    this.logger.debug(sql);
    return getJdbcTemplate().queryForInt(sql);
  }
  
  public long queryForLong(String sql)
  {
    this.logger.debug(sql);
    return getJdbcTemplate().queryForLong(sql);
  }
  
  public Object queryForObject(String sql, Object[] params, Class objClass)
  {
    this.logger.debug(sql);
    Object obj = null;
    try
    {
      obj = getJdbcTemplate().queryForObject(sql, params, objClass);
    }
    catch (Exception e)
    {
      this.logger.error(e);
    }
    return obj;
  }
  
  public Object queryForObject(String sql, Class objClass)
  {
    this.logger.debug(sql);
    Object obj = null;
    try
    {
      obj = getJdbcTemplate().queryForObject(sql, objClass);
    }
    catch (Exception e)
    {
      this.logger.error(e);
    }
    return obj;
  }
  
  public Object queryForObject(String sql, Object[] params, RowMapper rowMapper)
  {
    this.logger.debug(sql);
    Object obj = null;
    try
    {
      obj = getJdbcTemplate().queryForObject(sql, params, rowMapper);
    }
    catch (Exception e)
    {
      this.logger.error(e);
    }
    return obj;
  }
  
  public List query(String sql, Object[] args, int[] argTypes, RowMapper rowMapper)
  {
    this.logger.debug(sql);
    List list = null;
    try
    {
      list = getJdbcTemplate().query(sql, args, argTypes, rowMapper);
    }
    catch (Exception e)
    {
      this.logger.error(e);
    }
    return list;
  }
  
  public void executeStoredSubprogram(String storedSubprogram)
  {
    if (storedSubprogram == null) {
      return;
    }
    CallableStatementCallback cb = new CallableStatementCallback()
    {
      public Object doInCallableStatement(CallableStatement cs)
        throws SQLException
      {
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
    }
    catch (SQLException se)
    {
      this.logger.error("Call Stored Procedure Failed!" + se.getMessage());
    }
    return -1;
  }
}
