package gnnt.MEBS.monitor.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class MonitorProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(MonitorProDao.class);
  
  private class Updateprice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_UPDATEPRICETRY";
    
    public Updateprice(DataSource ds)
    {
      super("SP_UPDATEPRICETRY");
      setFunction(false);
      declareParameter(new SqlParameter("p_commodityid", 12));
      declareParameter(new SqlParameter("p_price", 8));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class LogKickonline
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_C_GLBLOG";
    
    public LogKickonline(DataSource ds)
    {
      super("SP_C_GLBLOG");
      setFunction(false);
      declareParameter(new SqlParameter("p_operator", 12));
      declareParameter(new SqlParameter("p_operatetype", 12));
      declareParameter(new SqlParameter("p_operateip", 12));
      declareParameter(new SqlParameter("p_operatecontent", 12));
      declareParameter(new SqlParameter("p_operateresult", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int updateprice(String commodity, double price)
  {
    Updateprice func = new Updateprice(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_commodityid", commodity);
    inputs.put("p_price", Double.valueOf(price));
    Map results = func.execute(inputs);
    return 0;
  }
  
  public int logkick(String operator, String operator_type, String operatorIp, String operatorContent, int operatorResult)
  {
    LogKickonline func = new LogKickonline(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_operator", operator);
    inputs.put("p_operatetype", operator_type);
    inputs.put("p_operateip", operatorIp);
    inputs.put("p_operatecontent", operatorContent);
    inputs.put("p_operateresult", Integer.valueOf(operatorResult));
    Map results = func.execute(inputs);
    return 0;
  }
}
