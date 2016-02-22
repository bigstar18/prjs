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

public class OrganizationProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(OrganizationProDao.class);
  
  public int organizationChangePro(String oldNo, String newNo)
  {
    OrganizationChangePro func = new OrganizationChangePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_oldOrganization", oldNo);
    inputs.put("p_newOrganization", newNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class OrganizationChangePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_organizationTransfer";
    
    public OrganizationChangePro(DataSource ds)
    {
      super("FN_M_B_organizationTransfer");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_oldOrganization", 12));
      declareParameter(new SqlParameter("p_newOrganization", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int organizationDeletePro(String organizationNo)
  {
    OrganizationDeletePro func = new OrganizationDeletePro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_organization", organizationNo);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("result")).intValue();
    return result;
  }
  
  private class OrganizationDeletePro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_B_checkOrganizationDelete";
    
    public OrganizationDeletePro(DataSource ds)
    {
      super("FN_M_B_checkOrganizationDelete");
      setFunction(true);
      declareParameter(new SqlOutParameter("result", 2));
      declareParameter(new SqlParameter("p_organization", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
