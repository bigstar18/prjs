package gnnt.MEBS.common.dao;

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

public class UserProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(UserProDao.class);
  
  private class UserRelatedRole
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "Fn_M_UserRelatedRole";
    
    public UserRelatedRole(DataSource ds)
    {
      super("Fn_M_UserRelatedRole");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_UserId", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int userRelatedRole(String userId)
  {
    UserRelatedRole func = new UserRelatedRole(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_UserId", userId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
}
