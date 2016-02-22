package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.SpecialMember;
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

public class SpecialMemberInfoProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberInfoProDao.class);
  
  public void addMemberInfoPro(String specialMemberNo)
  {
    AddMemberInfoPro func = new AddMemberInfoPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_specialMemberNo", specialMemberNo);
    Map results = func.execute(inputs);
  }
  
  private class AddMemberInfoPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "sp_M_SpecialMember";
    
    public AddMemberInfoPro(DataSource ds)
    {
      super("sp_M_SpecialMember");
      setFunction(false);
      declareParameter(new SqlParameter("p_specialMemberNo", 12));
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
    private static final String SFUNC_NAME = "FN_M_SMemberToStatus";
    
    public StatusChange(DataSource ds)
    {
      super("FN_M_SMemberToStatus");
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
  
  public int statusChange(SpecialMember specialMember)
  {
    StatusChange func = new StatusChange(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_FirmID", specialMember.getId());
    inputs.put("p_ToStatus", specialMember.getStatus());
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class MemberAddToPwd
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_M_SMemberAddToPwd";
    
    public MemberAddToPwd(DataSource ds)
    {
      super("SP_M_SMemberAddToPwd");
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
  
  public void memberAddToPwd(SpecialMember memberInfo)
  {
    MemberAddToPwd func = new MemberAddToPwd(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_M_memberID", memberInfo.getId());
    inputs.put("p_password", memberInfo.getPassword());
    Map results = func.execute(inputs);
  }
}
