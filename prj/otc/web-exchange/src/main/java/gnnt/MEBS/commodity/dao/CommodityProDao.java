package gnnt.MEBS.commodity.dao;

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

public class CommodityProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(CommodityProDao.class);
  
  public int updateIn(String commodityId)
  {
    CommodityInFundsPro func = new CommodityInFundsPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_CommodityID", commodityId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  private class CommodityInFundsPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_CommodityIn";
    
    public CommodityInFundsPro(DataSource ds)
    {
      super("FN_T_CommodityIn");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_CommodityID", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int updateOut(String commodityId)
  {
    CommodityOutFundsPro func = new CommodityOutFundsPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_CommodityID", commodityId);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("Return")).intValue();
    return result;
  }
  
  private class CommodityOutFundsPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_CommodityOut";
    
    public CommodityOutFundsPro(DataSource ds)
    {
      super("FN_T_CommodityOut");
      setFunction(true);
      declareParameter(new SqlOutParameter("Return", 2));
      declareParameter(new SqlParameter("p_CommodityID", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
