package gnnt.MEBS.bankadded.dao;

import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository("moneyIntoDao")
public class MoneyIntoDao
  extends DaoHelper
{
  private final transient Log logger = LogFactory.getLog(MoneyIntoDao.class);
  
  private class AddMoneyInto
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_F_Fund_In_Manual";
    
    public AddMoneyInto(DataSource ds)
    {
      super("FN_F_Fund_In_Manual");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_FirmID", 12));
      declareParameter(new SqlParameter("p_Amount", 2));
      declareParameter(new SqlParameter("p_BankCode", 12));
      declareParameter(new SqlParameter("p_contractno", 12));
      declareParameter(new SqlParameter("p_type", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int addMoneyInto(Map obj, int type)
  {
    AddMoneyInto func = new AddMoneyInto(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_FirmID", obj.get("firmId"));
    inputs.put("p_Amount", Double.valueOf(obj.get("money").toString()));
    inputs.put("p_BankCode", obj.get("bankId"));
    inputs.put("p_contractno", null);
    inputs.put("p_type", Integer.valueOf(type));
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  private class OutMoney
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_F_Fund_Out_Manual";
    
    public OutMoney(DataSource ds)
    {
      super("FN_F_Fund_Out_Manual");
      setFunction(true);
      


      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_FirmID", 12));
      declareParameter(new SqlParameter("p_Amount", 2));
      declareParameter(new SqlParameter("p_BankCode", 12));
      declareParameter(new SqlParameter("p_contractno", 12));
      declareParameter(new SqlParameter("p_type", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int outMoney(Map obj, int type)
  {
    OutMoney func = new OutMoney(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_FirmID", obj.get("firmId"));
    inputs.put("p_Amount", Double.valueOf(obj.get("money").toString()));
    inputs.put("p_BankCode", obj.get("bankId"));
    inputs.put("p_contractno", null);
    inputs.put("p_type", Integer.valueOf(type));
    Map results = func.execute(inputs);
    int result = ((BigDecimal)results.get("ret")).intValue();
    return result;
  }
  
  public List getBankId(String firmid)
  {
    String sql = "select * from ( select a.BANKID,b.bankName from  F_B_FIRMIDANDACCOUNT a,f_b_banks b  where a.bankid = b.bankid(+) and  a.firmid='" + 
      firmid + "'" + 
      ")";
    return queryBySQL(sql);
  }
}
