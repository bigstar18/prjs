package gnnt.MEBS.broke.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class BrokerageProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(BrokerageProDao.class);
  
  public int brokerageChangePro(String oldNo, String newNo)
  {
    BrokerageChangePro func = new BrokerageChangePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_oldBrokerage", oldNo);
    inputs.put("p_newBrokerage", newNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class BrokerageChangePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_b_customerTransfer";
    
    public BrokerageChangePro(DataSource ds)
    {
      super("FN_M_B_b_customerTransfer");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_oldBrokerage", 12));
      declareParameter(new SqlParameter("p_newBrokerage", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int brokerageDeletePro(String managerNo)
  {
    BrokerageDeletePro func = new BrokerageDeletePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_brokerage", managerNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class BrokerageDeletePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_checkBrokerageDelete";
    
    public BrokerageDeletePro(DataSource ds)
    {
      super("FN_M_B_checkBrokerageDelete");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_brokerage", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int brokerageUpdatePro(String brokerage, String organizationNo)
  {
    BrokerageUpdatePro func = new BrokerageUpdatePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_brokerage", brokerage);
    inputs.put("p_organizationNo", organizationNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class BrokerageUpdatePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_brokerageUpdate";
    
    public BrokerageUpdatePro(DataSource ds)
    {
      super("FN_M_B_brokerageUpdate");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_brokerage", 12));
      declareParameter(new SqlParameter("p_organizationNo", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
