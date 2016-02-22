package gnnt.MEBS.commodity.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class DelayTradeProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(DelayTradeProDao.class);
  
  public void updateSlipPointPro(String pType)
  {
    UpdateSlipPointPro func = new UpdateSlipPointPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("P_TYPE", pType);
    Map results = func.execute(inputs);
  }
  
  private class UpdateSlipPointPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_T_slipPoint_rt";
    
    public UpdateSlipPointPro(DataSource ds)
    {
      super("SP_T_slipPoint_rt");
      setFunction(false);
      declareParameter(new SqlParameter("P_TYPE", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
