package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
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

public class CustomerInfoProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(CustomerInfoProDao.class);
  
  public void addCustomerInfoPro(String firmId)
  {
    AddCustomerInfoPro func = new AddCustomerInfoPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_customerNo", firmId);
    Map results = func.execute(inputs);
  }
  
  private class AddCustomerInfoPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_Customer";
    
    public AddCustomerInfoPro(DataSource ds)
    {
      super("SP_M_Customer");
      setFunction(false);
      declareParameter(new SqlParameter("p_customerNo", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int checkCustomerDivert(Customer customer)
  {
    CheckCustomerDivert func = new CheckCustomerDivert(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_customerId", customer.getId());
    inputs.put("p_memberId", customer.getMemberNoChange());
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class CheckCustomerDivert
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_checkCustomerDivert";
    
    public CheckCustomerDivert(DataSource ds)
    {
      super("FN_M_checkCustomerDivert");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_customerId", 12));
      declareParameter(new SqlParameter("p_memberId", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int customerDivert(Customer customer)
  {
    CustomerDivert func = new CustomerDivert(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_firmid", customer.getId());
    inputs.put("m_firmid", customer.getMemberNoChange());
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class CustomerDivert
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_CustomerDivert";
    
    public CustomerDivert(DataSource ds)
    {
      super("FN_M_CustomerDivert");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_firmid", 12));
      declareParameter(new SqlParameter("m_firmid", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private class StatusChange
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_M_CustomerToStatus";
    
    public StatusChange(DataSource ds)
    {
      super("FN_M_CustomerToStatus");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_M_FirmID", 12));
      declareParameter(new SqlParameter("p_ToStatus", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int statusChange(Customer customer)
  {
    StatusChange func = new StatusChange(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_FirmID", customer.getId());
    inputs.put("p_ToStatus", customer.getCustomerStatus().getStatus());
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class CustomerAddToPwd
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_CustomerAddToPwd";
    
    public CustomerAddToPwd(DataSource ds)
    {
      super("SP_M_CustomerAddToPwd");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_M_customerID", 12));
      declareParameter(new SqlParameter("p_phonePwd", 12));
      declareParameter(new SqlParameter("p_password", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void customerAddToPwd(Customer customer)
  {
    CustomerAddToPwd func = new CustomerAddToPwd(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_customerID", customer.getId());
    inputs.put("p_phonePwd", customer.getPhonePWD());
    inputs.put("p_password", customer.getPassword());
    Map results = func.execute(inputs);
  }
}
