package gnnt.MEBS.account.dao;

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

public class FirmProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(FirmProDao.class);
  
  public int firmMod(String firmId, int cardType, String card, String firmName)
  {
    FirmMod func = new FirmMod(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_firmid", firmId);
    inputs.put("p_cardtype", cardType);
    inputs.put("p_card", card);
    inputs.put("p_firmname", firmName);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class FirmMod
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_F_B_FIRMMOD";
    
    public FirmMod(DataSource ds)
    {
      super("FN_F_B_FIRMMOD");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_firmid", 12));
      declareParameter(new SqlParameter("p_cardtype", 12));
      declareParameter(new SqlParameter("p_card", 12));
      declareParameter(new SqlParameter("p_firmname", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
