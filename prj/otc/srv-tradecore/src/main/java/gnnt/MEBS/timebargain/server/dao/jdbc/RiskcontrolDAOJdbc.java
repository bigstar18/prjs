package gnnt.MEBS.timebargain.server.dao.jdbc;

import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class RiskcontrolDAOJdbc
  extends BaseDAOJdbc
  implements RiskcontrolDAO
{
  private Log log = LogFactory.getLog(getClass());
  
  public void setLossProfit(Long holdingID, Double stopLoss, Double stopProfit)
  {
    if ((stopLoss.doubleValue() != 0.0D) && (stopProfit.doubleValue() != 0.0D))
    {
      String sql = "update T_HoldPosition set StopLossPrice=?,StopProfitPrice=? where HoldNo=? ";
      Object[] params = {
        stopLoss, 
        stopProfit, 
        holdingID };
      
      int[] types = {
        8, 
        8, 
        -5 };
      
      getJdbcTemplate().update(sql, params, types);
    }
    else if (stopLoss.doubleValue() != 0.0D)
    {
      String sql = "update T_HoldPosition set StopLossPrice=? where HoldNo=? ";
      Object[] params = {
        stopLoss, 
        holdingID };
      
      int[] types = {
        8, 
        -5 };
      
      getJdbcTemplate().update(sql, params, types);
    }
    else if (stopProfit.doubleValue() != 0.0D)
    {
      String sql = "update T_HoldPosition set StopProfitPrice=? where HoldNo=? ";
      Object[] params = {
        stopProfit, 
        holdingID };
      
      int[] types = {
        8, 
        -5 };
      
      getJdbcTemplate().update(sql, params, types);
    }
  }
  
  public void withdrawLossProfit(Long holdingID, Short type)
  {
    if (type.shortValue() == 3)
    {
      String sql = "update T_HoldPosition set StopLossPrice=0,StopProfitPrice=0 where HoldNo=? ";
      Object[] params = {
        holdingID };
      
      int[] types = {
        -5 };
      
      getJdbcTemplate().update(sql, params, types);
    }
    else if (type.shortValue() == 1)
    {
      String sql = "update T_HoldPosition set StopLossPrice=0 where HoldNo=? ";
      Object[] params = {
        holdingID };
      
      int[] types = {
        -5 };
      
      getJdbcTemplate().update(sql, params, types);
    }
    else if (type.shortValue() == 2)
    {
      String sql = "update T_HoldPosition set StopProfitPrice=0 where HoldNo=? ";
      Object[] params = {
        holdingID };
      
      int[] types = {
        -5 };
      
      getJdbcTemplate().update(sql, params, types);
    }
  }
  
  public void updateQuotation(Quotation quotation)
  {
    String sql = "update T_Quotation set CurPrice=?,OpenPrice=?,HighPrice=?,LowPrice=?,ClosePrice=?,CreateTime=? where CommodityID = ? ";
    Object[] params = {
      quotation.getCurPrice(), 
      quotation.getOpenPrice(), 
      quotation.getHighPrice(), 
      quotation.getLowPrice(), 
      quotation.getClosePrice(), 
      quotation.getUpdateTime(), 
      quotation.getCommodityID() };
    
    int[] types = {
      8, 
      8, 
      8, 
      8, 
      8, 
      93, 
      12 };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void updateQuotation_RT(Quotation_RT quotation_RT)
  {
    String sql = "update T_C_Quotation_RT set CurPrice_B=?,CurPrice_S=?,ClearPrice_B=?,ClearPrice_S=?,Y_ClearPrice_B=?,Y_ClearPrice_S=?,UpdateTime=? where CommodityID = ? and M_FirmID=? ";
    Object[] params = {
      Double.valueOf(quotation_RT.getCurPrice_B()), 
      Double.valueOf(quotation_RT.getCurPrice_S()), 
      Double.valueOf(quotation_RT.getClearPrice_B()), 
      Double.valueOf(quotation_RT.getClearPrice_S()), 
      Double.valueOf(quotation_RT.getY_ClearPrice_B()), 
      Double.valueOf(quotation_RT.getY_ClearPrice_S()), 
      quotation_RT.getUpdateTime(), 
      quotation_RT.getCommodityID(), 
      quotation_RT.getM_FirmID() };
    
    int[] types = {
      8, 
      8, 
      8, 
      8, 
      8, 
      8, 
      93, 
      12, 
      12 };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void hqUpdate(String commodityID, double curPrice)
  {
    SPTHQUpdateProcedure sfunc = new SPTHQUpdateProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_commodityid", commodityID);
    inputs.put("p_price", Double.valueOf(curPrice));
    
    sfunc.execute(inputs);
  }
  
  private class SPTHQUpdateProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_T_HQ_Update";
    
    public SPTHQUpdateProcedure(DataSource ds)
    {
      super("SP_T_HQ_Update");
      declareParameter(new SqlParameter("p_commodityid", 12));
      declareParameter(new SqlParameter("p_price", 8));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public Timestamp floatingComputer(String m_FirmID, Timestamp lastWorkTime)
  {
    UpdateFloatingLossStoredProcedure sfunc = new UpdateFloatingLossStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_m_firmid", m_FirmID);
    inputs.put("p_LastTime", lastWorkTime);
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    
    return (Timestamp)results.get("ret");
  }
  
  private class UpdateFloatingLossStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_UpdateFloatingLoss";
    
    public UpdateFloatingLossStoredProcedure(DataSource ds)
    {
      super("FN_T_UpdateFloatingLoss");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 93));
      declareParameter(new SqlParameter("p_m_firmid", 12));
      declareParameter(new SqlParameter("p_LastTime", 93));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int stopPL(String commodityID)
  {
    StopPLStoredProcedure sfunc = new StopPLStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_commodityid", commodityID);
    
    Map results = sfunc.execute(inputs);
    
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class StopPLStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_StopPL";
    
    public StopPLStoredProcedure(DataSource ds)
    {
      super("FN_T_StopPL");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_commodityid", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int updateRiskRateAll()
  {
    UpdateRiskRateAllStoredProcedure sfunc = new UpdateRiskRateAllStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    Map results = sfunc.execute(inputs);
    
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class UpdateRiskRateAllStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_R_UpdateRiskRateAll";
    
    public UpdateRiskRateAllStoredProcedure(DataSource ds)
    {
      super("FN_R_UpdateRiskRateAll");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int updateRiskRateHigh()
  {
    UpdateRiskRateHighStoredProcedure sfunc = new UpdateRiskRateHighStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    Map results = sfunc.execute(inputs);
    
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class UpdateRiskRateHighStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_R_UpdateRiskRateHigh";
    
    public UpdateRiskRateHighStoredProcedure(DataSource ds)
    {
      super("FN_R_UpdateRiskRateHigh");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
