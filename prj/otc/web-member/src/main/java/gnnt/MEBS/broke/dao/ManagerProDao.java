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

public class ManagerProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(ManagerProDao.class);
  
  public int managerChangePro(String oldNo, String newNo)
  {
    ManagerChangePro func = new ManagerChangePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_oldManager", oldNo);
    inputs.put("p_newOrganization", newNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class ManagerChangePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_managerTransfer";
    
    public ManagerChangePro(DataSource ds)
    {
      super("FN_M_B_managerTransfer");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_oldManager", 12));
      declareParameter(new SqlParameter("p_newOrganization", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int managerDeletePro(String managerNo)
  {
    ManagerDeletePro func = new ManagerDeletePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_manager", managerNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class ManagerDeletePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_checkManagerDelete";
    
    public ManagerDeletePro(DataSource ds)
    {
      super("FN_M_B_checkManagerDelete");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_manager", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
