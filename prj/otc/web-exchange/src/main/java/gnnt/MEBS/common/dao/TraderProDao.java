package gnnt.MEBS.common.dao;

import gnnt.MEBS.account.dao.TraderDao;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class TraderProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(TraderDao.class);
  
  private class TraderAdd
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_TraderAdd";
    
    public TraderAdd(DataSource ds)
    {
      super("SP_M_TraderAdd");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_Firmid", 12));
      declareParameter(new SqlParameter("p_traderId", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void traderAdd(String userId, String memberNo)
  {
    TraderAdd func = new TraderAdd(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_Firmid", memberNo);
    inputs.put("p_traderId", userId);
    Map results = func.execute(inputs);
  }
  
  private class TraderUpdate
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_TraderUpdate";
    
    public TraderUpdate(DataSource ds)
    {
      super("SP_M_TraderUpdate");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_TraderID", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void traderUpdate(String userId)
  {
    TraderUpdate func = new TraderUpdate(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_TraderID", userId);
    Map results = func.execute(inputs);
  }
}
