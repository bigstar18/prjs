package gnnt.MEBS.finance.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("accountAction")
@Scope("request")
public class AccountAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568112367465621L;

  @Resource(name="account_dCFlagMap")
  private Map<String, String> account_dCFlagMap;

  @Resource(name="account_isInitMap")
  private Map<String, String> account_isInitMap;

  public Map<String, String> getAccount_dCFlagMap()
  {
    return this.account_dCFlagMap;
  }

  public void setAccount_dCFlagMap(Map<String, String> paramMap)
  {
    this.account_dCFlagMap = paramMap;
  }

  public Map<String, String> getAccount_isInitMap()
  {
    return this.account_isInitMap;
  }

  public void setAccount_isInitMap(Map<String, String> paramMap)
  {
    this.account_isInitMap = paramMap;
  }

  public String deleteAccount()
  {
    this.logger.debug("----------deleteAccount   批量删除科目----------------");
    String[] arrayOfString = this.request.getParameterValues("ids");
    String str1 = "";
    for (int i = 0; i < arrayOfString.length; i++)
      if (i != arrayOfString.length - 1)
        str1 = str1 + "'" + arrayOfString[i] + "'" + ",";
      else
        str1 = str1 + "'" + arrayOfString[i] + "'";
    String str2 = "select code from f_account where isinit ='Y' and code in (" + str1 + ")";
    List localList = getService().getListBySql(str2);
    String str3 = "";
    if (localList.size() == 0)
    {
      try
      {
        delete();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    else
    {
      for (int j = 0; j < localList.size(); j++)
      {
        Map localMap = (Map)localList.get(j);
        if (j != localList.size() - 1)
          str3 = str3 + localMap.get("CODE").toString() + ",";
        else
          str3 = str3 + localMap.get("CODE").toString();
      }
      addReturnValue(1, 211105L, new Object[] { str3 });
    }
    return "success";
  }
}