package gnnt.MEBS.checkLogon.dao.broker;

import gnnt.MEBS.checkLogon.dao.BaseDAOJdbc;
import gnnt.MEBS.checkLogon.dao.ObjectRowMapper;
import gnnt.MEBS.checkLogon.po.broker.BrokerPO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class BrokerCheckDAO extends BaseDAOJdbc
{
  public void addGlobalLog(String operator, String operatorIP, int operatorType, String operatorContent, int operatorResult)
  {
    String sql = "insert into c_globallog_all(id,operator,operatetime,operatetype,operateip,operatecontent,operateresult)  values(SEQ_C_GLOBALLOG.Nextval,?, sysdate,?,?,?,?)";

    Object[] params = { operator, Integer.valueOf(operatorType), operatorIP, 
      operatorContent, Integer.valueOf(operatorResult) };
    int[] types = { 12, 4, 12, 
      12, 4 };

    this.logger.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }

    getJdbcTemplate().update(sql, params, types);
  }

  public BrokerPO getBrokerPOByID(String brokerID)
  {
    String sql = "select * from br_broker b where b.brokerid =?";
    Object[] params = { brokerID };

    this.logger.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }

    List list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new BrokerPO()));
    if ((list != null) && (list.size() > 0)) {
      return (BrokerPO)list.get(0);
    }
    return null;
  }

  public void changePassowrd(String brokerID, String password)
  {
    String sql = "update br_broker set password=? where brokerId=? ";
    Object[] params = { password, brokerID };
    int[] dataTypes = { 12, 12 };

    this.logger.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }

    getJdbcTemplate().update(sql, params, dataTypes);
  }
}