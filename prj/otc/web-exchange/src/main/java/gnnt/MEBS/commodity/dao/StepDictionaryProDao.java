package gnnt.MEBS.commodity.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class StepDictionaryProDao
  extends JdbcDaoSupport
{
  private final transient Log logger = LogFactory.getLog(StepDictionaryProDao.class);
  
  public void addDelayDaysPro(long stepNo)
  {
    AddDelayDaysPro func = new AddDelayDaysPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("stepNo", Long.valueOf(stepNo));
    Map results = func.execute(inputs);
  }
  
  private class AddDelayDaysPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_T_TepDictionaryDelayDay";
    
    public AddDelayDaysPro(DataSource ds)
    {
      super("SP_T_TepDictionaryDelayDay");
      setFunction(false);
      declareParameter(new SqlParameter("stepNo", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void addMemberFundsPro(long stepNo)
  {
    AddMemberFundsPro func = new AddMemberFundsPro(getDataSource());
    Map inputs = new HashMap();
    inputs.put("stepNo", Long.valueOf(stepNo));
    Map results = func.execute(inputs);
  }
  
  private class AddMemberFundsPro
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_T_TepDictionaryMemberFuncs";
    
    public AddMemberFundsPro(DataSource ds)
    {
      super("SP_T_TepDictionaryMemberFuncs");
      setFunction(false);
      declareParameter(new SqlParameter("stepNo", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
