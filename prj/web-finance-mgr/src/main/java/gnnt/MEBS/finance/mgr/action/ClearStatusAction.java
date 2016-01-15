package gnnt.MEBS.finance.mgr.action;

import gnnt.MEBS.common.communicate.IBalanceRMI;
import gnnt.MEBS.common.communicate.model.BalanceVO;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.rmi.Naming;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("clearStatusAction")
@Scope("request")
public class ClearStatusAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568112367465621L;

  @Resource(name="clearStatus_statusMap")
  private Map<String, String> clearStatus_statusMap;

  public Map<String, String> getClearStatus_statusMap()
  {
    return this.clearStatus_statusMap;
  }

  public void setClearStatus_statusMap(Map<String, String> paramMap)
  {
    this.clearStatus_statusMap = paramMap;
  }

  public String financeBalance()
  {
    Date localDate = null;
    String str1 = "";
    String str2 = "select * from c_trademodule  where isbalancecheck = 'Y'";
    List localList = getService().getListBySql(str2);
    IBalanceRMI localIBalanceRMI = null;
    int i = 1;
    if ((localList.size() > 0) && (localList != null))
      for (int j = 0; j < localList.size(); j++)
      {
        String str3 = ((Map)localList.get(j)).get("HOSTIP").toString();
        String str4 = ((Map)localList.get(j)).get("PORT").toString();
        try
        {
          localIBalanceRMI = (IBalanceRMI)Naming.lookup("rmi://" + str3 + ":" + str4 + "/balanceRMI");
          BalanceVO localBalanceVO = localIBalanceRMI.checkBalance();
          if (!localBalanceVO.isBalanceStatus())
            str1 = str1 + ((Map)localList.get(j)).get("CNNAME").toString() + " : " + localBalanceVO.getMessage() + "； ";
          if (localDate != null)
          {
            if (localBalanceVO.getTradeDate() != null)
              localDate = getMaxDate(localDate, localBalanceVO.getTradeDate());
          }
          else
            localDate = localBalanceVO.getTradeDate();
        }
        catch (Exception localException1)
        {
          i = 0;
          localException1.printStackTrace();
        }
      }
    Object localObject1 = null;
    int k = -1;
    Object localObject2;
    if ((i == 1) && (str1.equals("")))
    {
      if (localDate != null)
        new Timestamp(localDate.getTime());
      localObject2 = new Object[] { localObject1 };
      try
      {
        k = Integer.parseInt(getService().executeProcedure("{?=call FN_F_Balance(?) }", (Object[])localObject2).toString());
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    if (k == 1)
    {
      if ((localList.size() > 0) && (localList != null))
        for (int m = 0; m < localList.size(); m++)
        {
          localObject2 = ((Map)localList.get(m)).get("HOSTIP").toString();
          String str5 = ((Map)localList.get(m)).get("PORT").toString();
          try
          {
            localIBalanceRMI = (IBalanceRMI)Naming.lookup("rmi://" + (String)localObject2 + ":" + str5 + "/balanceRMI");
            localIBalanceRMI.noticeSubsystemStatus();
          }
          catch (Exception localException3)
          {
            localException3.printStackTrace();
          }
        }
      getService().executeUpdateBySql("update f_systemstatus t set t.isclear=1");
      addReturnValue(1, 211101L);
    }
    else
    {
      addReturnValue(3, 231101L);
      if (!str1.equals(""))
        addReturnValue(0, 211199L, new Object[] { str1 });
    }
    return "success";
  }

  public Date getMaxDate(Date paramDate1, Date paramDate2)
  {
    return paramDate1.compareTo(paramDate2) > 0 ? paramDate1 : paramDate2;
  }
}