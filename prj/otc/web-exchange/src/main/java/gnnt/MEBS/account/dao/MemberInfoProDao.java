package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberInfo;
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

public class MemberInfoProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(MemberInfoProDao.class);
  
  public void addMemberInfoPro(String memberNo)
  {
    AddMemberInfoPro func = new AddMemberInfoPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_memberNo", memberNo);
    Map results = func.execute(inputs);
  }
  
  private class AddMemberInfoPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "sp_M_member";
    
    public AddMemberInfoPro(DataSource ds)
    {
      super("sp_M_member");
      setFunction(false);
      declareParameter(new SqlParameter("p_memberNo", 12));
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
    private static final String SFUNC_NAME = "FN_M_MemberToStatus";
    
    public StatusChange(DataSource ds)
    {
      super("FN_M_MemberToStatus");
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
  
  public int statusChange(MemberInfo memberInfo)
  {
    StatusChange func = new StatusChange(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_FirmID", memberInfo.getId());
    inputs.put("p_ToStatus", memberInfo.getStatus());
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  public int forceClose(String customerNo, String operator)
  {
    ForceClose func = new ForceClose(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_firmid", customerNo);
    inputs.put("p_operator", operator);
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class ForceClose
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_ForceClose";
    
    public ForceClose(DataSource ds)
    {
      super("FN_T_ForceClose");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_firmid", 12));
      declareParameter(new SqlParameter("p_operator", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void commodityAdd(String commodityId, String commodityIdIn)
  {
    CommodityAdd func = new CommodityAdd(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_CommodityId", commodityId);
    inputs.put("p_CommodityIdIn", commodityIdIn);
    func.execute(inputs);
  }
  
  private class CommodityAdd
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_COMMODITY_ADD";
    
    public CommodityAdd(DataSource ds)
    {
      super("SP_COMMODITY_ADD");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_CommodityId", 12));
      declareParameter(new SqlParameter("p_CommodityIdIn", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      super.execute(map);
      return null;
    }
  }
  
  public void commodityDelete(String commodityId)
  {
    CommodityDelete func = new CommodityDelete(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_CommodityId", commodityId);
    func.execute(inputs);
  }
  
  private class CommodityDelete
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_COMMODITY_DELETE";
    
    public CommodityDelete(DataSource ds)
    {
      super("SP_COMMODITY_DELETE");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_CommodityId", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      super.execute(map);
      return null;
    }
  }
  
  private class MemberAddToPwd
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_MemberAddToPwd";
    
    public MemberAddToPwd(DataSource ds)
    {
      super("SP_M_MemberAddToPwd");
      setFunction(false);
      


      declareParameter(new SqlParameter("p_M_memberID", 12));
      declareParameter(new SqlParameter("p_password", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void memberAddToPwd(MemberInfo memberInfo)
  {
    MemberAddToPwd func = new MemberAddToPwd(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_memberID", memberInfo.getId());
    inputs.put("p_password", memberInfo.getPassword());
    Map results = func.execute(inputs);
  }
}
