package gnnt.MEBS.common.manage.dao.jdbc;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.model.Apply;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class ApplyDAOJdbc
  extends JdbcDaoSupport
  implements ApplyDAO
{
  private LobHandler lobHandler;
  private DataFieldMaxValueIncrementer incre;
  public static Object LOCK = new Object();
  
  public LobHandler getLobHandler()
  {
    return this.lobHandler;
  }
  
  public void setLobHandler(LobHandler paramLobHandler)
  {
    this.lobHandler = paramLobHandler;
  }
  
  public void insertApply(final Apply paramApply)
  {
    this.logger.debug("enter insertApply ....");
    synchronized (LOCK)
    {
      String str = "select nvl(max(id),0)+1 id from c_xmltemplate";
      long l1 = getJdbcTemplate().queryForLong(str);
      this.logger.debug("xmlId:" + l1);
      paramApply.setXmlId(l1);
      str = "insert into c_xmltemplate values(?,?)";
      this.logger.debug("c:" + paramApply.getContent());
      getJdbcTemplate().execute(str, new AbstractLobCreatingPreparedStatementCallback(this.lobHandler)
      {
        protected void setValues(PreparedStatement paramAnonymousPreparedStatement, LobCreator paramAnonymousLobCreator)
          throws SQLException
        {
          paramAnonymousPreparedStatement.setLong(1, paramApply.getXmlId());
          paramAnonymousLobCreator.setClobAsString(paramAnonymousPreparedStatement, 2, paramApply.getContent());
        }
      });
      str = "select nvl(max(id),0)+1 id from c_apply";
      long l2 = getJdbcTemplate().queryForLong(str);
      str = "insert into c_apply (id,applyType,status,proposer,applytime,content) values (?,?,?,?,sysdate,sys.xmlType.createXML((select clob from c_xmltemplate where id=" + l1 + ")))";
      Object[] arrayOfObject = { Long.valueOf(l2), Integer.valueOf(paramApply.getApplyType()), Integer.valueOf(paramApply.getStatus()), paramApply.getProposer() };
      this.logger.debug("sql: " + str);
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
      str = "delete from c_xmltemplate where id=" + l1;
      getJdbcTemplate().update(str);
    }
  }
  
  public Apply getApplyById(Apply paramApply, boolean paramBoolean)
  {
    long l = paramApply.getId();
    String str1 = paramBoolean ? " for update" : "";
    String str2 = "select id, applytype, status, proposer, applytime, approver, approvetime, note from c_apply t where id=" + l + " " + str1;
    this.logger.debug(str2);
    List localList1 = getJdbcTemplate().queryForList(str2);
    if (localList1.size() > 0)
    {
      Map localMap = (Map)localList1.get(0);
      paramApply.setId(((BigDecimal)localMap.get("ID")).longValue());
      paramApply.setApplyType(((BigDecimal)localMap.get("APPLYTYPE")).intValue());
      paramApply.setStatus(((BigDecimal)localMap.get("STATUS")).intValue());
      paramApply.setProposer((String)localMap.get("PROPOSER"));
      paramApply.setApplytime((Date)localMap.get("APPLYTIME"));
      paramApply.setApprover((String)localMap.get("approver"));
      paramApply.setNote((String)localMap.get("note"));
      paramApply.setApprovetime((Date)localMap.get("APPROVETIME"));
      str2 = "select t.content.getclobval() content from c_apply t where id=" + l;
      List localList2 = getJdbcTemplate().query(str2, new Object[0], new RowMapper()
      {
        public Object mapRow(ResultSet paramAnonymousResultSet, int paramAnonymousInt)
          throws SQLException
        {
          String str = ApplyDAOJdbc.this.lobHandler.getClobAsString(paramAnonymousResultSet, 1);
          return str;
        }
      });
      paramApply.setContent((String)localList2.get(0));
    }
    return paramApply;
  }
  
  public List getApplys(Apply paramApply)
  {
    List localList = null;
    if (paramApply != null)
    {
      String str = paramApply.toQury() + " where 1=1 " + paramApply.toFilter() + paramApply.toOrder();
      this.logger.debug(str);
      localList = getJdbcTemplate().queryForList(str);
    }
    return localList;
  }
  
  public void updateApply(Apply paramApply)
  {
    String str = "update c_apply t set status=" + paramApply.getStatus() + ",t.approver='" + paramApply.getApprover() + "'," + "t.approvetime=sysdate,t.note='" + paramApply.getNote() + "' where id=" + paramApply.getId();
    getJdbcTemplate().update(str);
  }
}
