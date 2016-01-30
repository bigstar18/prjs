package gnnt.MEBS.checkLogon.dao.broker;

import gnnt.MEBS.checkLogon.dao.BaseDAOJdbc;
import gnnt.MEBS.checkLogon.dao.ObjectRowMapper;
import gnnt.MEBS.checkLogon.po.broker.BrokerAgePO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class BrokerAgeCheckDAO extends BaseDAOJdbc
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

  public BrokerAgePO getBrokerAgePOByID(String brokerAgeID)
  {
    String sql = "select * from BR_Brokerage b where b.brokerAgeID =?";
    Object[] params = { brokerAgeID };

    this.logger.debug("sql:" + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]:" + params[i]);
    }

    List list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new BrokerAgePO()));

    if ((list != null) && (list.size() > 0)) {
      return (BrokerAgePO)list.get(0);
    }

    return null;
  }

  public void changePassowrd(String brokerAgeID, String password)
  {
    String sql = "update BR_Brokerage set password=? where brokerAgeID=? ";
    Object[] params = { password, brokerAgeID };
    int[] dataTypes = { 12, 12 };

    this.logger.debug("sql:" + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]:" + params[i]);
    }

    getJdbcTemplate().update(sql, params, dataTypes);
  }
}