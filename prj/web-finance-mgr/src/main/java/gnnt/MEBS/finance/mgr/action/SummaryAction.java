package gnnt.MEBS.finance.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.finance.mgr.model.LedgerField;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("summaryAction")
@Scope("request")
public class SummaryAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568140167465621L;

  @Resource(name="summary_fundDCFlagMap")
  private Map<String, String> summary_fundDCFlagMap;

  @Resource(name="summary_appendAccountMap")
  private Map<String, String> summary_appendAccountMap;

  @Resource(name="summary_isInitMap")
  private Map<String, String> summary_isInitMap;

  public Map<String, String> getSummary_fundDCFlagMap()
  {
    return this.summary_fundDCFlagMap;
  }

  public Map<String, String> getSummary_appendAccountMap()
  {
    return this.summary_appendAccountMap;
  }

  public Map<String, String> getSummary_isInitMap()
  {
    return this.summary_isInitMap;
  }

  public void setSummary_fundDCFlagMap(Map<String, String> paramMap)
  {
    this.summary_fundDCFlagMap = paramMap;
  }

  public void setSummary_appendAccountMap(Map<String, String> paramMap)
  {
    this.summary_appendAccountMap = paramMap;
  }

  public void setSummary_isInitMap(Map<String, String> paramMap)
  {
    this.summary_isInitMap = paramMap;
  }

  public String getListByLimit()
  {
    this.logger.debug("----------getListByLimit  查询摘要----------------");
    PageRequest localPageRequest = new PageRequest("");
    Page localPage = getService().getPage(localPageRequest, new LedgerField());
    List localList = getService().getListBySql("select cast(moduleId as varchar(2)) as moduleId,name from v_c_trademodule");
    this.request.setAttribute("ledgerFieldPage", localPage);
    this.request.setAttribute("tradeMoudelList", localList);
    String str = "";
    try
    {
      str = listByLimit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  public String addSummaryForward()
  {
    this.logger.debug("----------addSummaryForward   添加摘要跳转----------------");
    PageRequest localPageRequest = new PageRequest("");
    Page localPage = getService().getPage(localPageRequest, new LedgerField());
    this.request.setAttribute("ledgerFieldPage", localPage);
    return forwardAdd();
  }

  public String updateSummaryForward()
    throws Exception
  {
    this.logger.debug("----------updateSummaryForward   修改摘要跳转----------------");
    PageRequest localPageRequest = new PageRequest("");
    Page localPage = getService().getPage(localPageRequest, new LedgerField());
    this.request.setAttribute("ledgerFieldPage", localPage);
    return viewById();
  }

  public String deleteSummary()
  {
    this.logger.debug("----------deleteSummary   批量删除摘要----------------");
    String[] arrayOfString = this.request.getParameterValues("ids");
    String str1 = "";
    for (int i = 0; i < arrayOfString.length; i++)
      if (i != arrayOfString.length - 1)
        str1 = str1 + "'" + arrayOfString[i] + "'" + ",";
      else
        str1 = str1 + "'" + arrayOfString[i] + "'";
    String str2 = "";
    String str3 = "select cast(summaryNo as varchar(5)) as summaryNo from f_summary where isinit ='Y' and summaryNo in (" + str1 + ")";
    List localList = getService().getListBySql(str3);
    Object localObject;
    if (localList.size() == 0)
    {
      String str4 = "select cast(summaryNo as varchar(5)) as summaryNo from F_Voucher where summaryNo in (" + str1 + ") group by summaryNo";
      localObject = getService().getListBySql(str4);
      if ((localObject == null) || (((List)localObject).size() == 0))
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
        for (int k = 0; k < ((List)localObject).size(); k++)
        {
          Map localMap = (Map)((List)localObject).get(k);
          if (k != ((List)localObject).size() - 1)
            str2 = str2 + localMap.get("SUMMARYNO").toString() + ",";
          else
            str2 = str2 + localMap.get("SUMMARYNO").toString();
        }
        addReturnValue(1, 211106L, new Object[] { str2 });
      }
    }
    else
    {
      for (int j = 0; j < localList.size(); j++)
      {
        localObject = (Map)localList.get(j);
        if (j != localList.size() - 1)
          str2 = str2 + ((Map)localObject).get("SUMMARYNO").toString() + ",";
        else
          str2 = str2 + ((Map)localObject).get("SUMMARYNO").toString();
      }
      addReturnValue(1, 211104L, new Object[] { str2 });
    }
    return "success";
  }
}