package gnnt.MEBS.integrated.mgr.dao;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import java.util.Date;
import org.springframework.stereotype.Repository;

@Repository("fundDao")
public class FundDao
  extends StandardDao
{
  public double inmoneymodel(String paramString, double paramDouble)
    throws Exception
  {
    String str1 = "11003";
    String str2 = new Date().getTime() + "";
    String str3 = "100201";
    Object[] arrayOfObject = { paramString, str1, Double.valueOf(paramDouble), str2, str3 };
    Object localObject = executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,null,null)}", arrayOfObject);
    double d = new Double(localObject.toString()).doubleValue();
    return d;
  }
}
