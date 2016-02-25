package gnnt.MEBS.common.manage.dao.jdbc;

import gnnt.MEBS.common.manage.dao.VirtualFundDAO;
import gnnt.MEBS.common.manage.model.Apply_T_VirtualMoney;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class VirtualFundDAOJdbc
  extends JdbcDaoSupport
  implements VirtualFundDAO
{
  public int CheckFirmAndVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney)
  {
    String str = "select t.VirtualFunds VirtualFunds from T_Firm t where t.firmID = '" + paramApply_T_VirtualMoney.getFirmId() + "' for update";
    int i = 0;
    List localList = getJdbcTemplate().queryForList(str);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if (localMap.get("VirtualFunds") != null)
      {
        double d = Double.parseDouble(localMap.get("VirtualFunds").toString());
        if (d + paramApply_T_VirtualMoney.getMoney() < 0.0D) {
          i = 2;
        }
      }
    }
    else
    {
      i = 1;
    }
    return i;
  }
  
  public void updateVirtualFund(Apply_T_VirtualMoney paramApply_T_VirtualMoney)
  {
    String str = "update T_Firm set VirtualFunds= (VirtualFunds + ?) where firmID = ?";
    Object[] arrayOfObject = { Double.valueOf(paramApply_T_VirtualMoney.getMoney()), paramApply_T_VirtualMoney.getFirmId() };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}
