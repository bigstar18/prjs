package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository("QuotePointJDBCDao")
public class QuotePointJDBCDao
  extends DaoHelper
{
  private final transient Log logger = LogFactory.getLog(QuotePointJDBCDao.class);
  
  public int quotePointRunTime(String commodityId, double clearPrice)
  {
    quotePointRunTime func = new quotePointRunTime(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_CommodityID", commodityId);
    inputs.put("p_ClearPrice", Double.valueOf(clearPrice));
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class quotePointRunTime
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_UpdateClearPrice";
    
    public quotePointRunTime(DataSource ds)
    {
      super("FN_T_UpdateClearPrice");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_CommodityID", 12));
      declareParameter(new SqlParameter("p_ClearPrice", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
