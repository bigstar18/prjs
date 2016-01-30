package gnnt.MEBS.integrated.mgr.action.usermanage;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.ErrorLoginLog;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("errorLoginLogAction")
@Scope("request")
public class ErrorLoginLogAction
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  
  public String getList()
    throws Exception
  {
    List localList1 = getService().getListBySql("select * from l_dictionary l where l.key='FrontMaxErrorLogonTimes'");
    int i = Tools.strToInt(((Map)localList1.get(0)).get("VALUE") + "", 64536);
    List localList2 = getService().getListBySql("select trunc(sysdate) CRTDATE from dual");
    Date localDate = (Date)((Map)localList2.get(0)).get("CRTDATE");
    PageRequest localPageRequest = getPageRequest(this.request);
    QueryConditions localQueryConditions = getQueryConditionsFromRequest(this.request);
    localQueryConditions.addCondition("primary.counts", ">=", Integer.valueOf(i));
    localQueryConditions.addCondition("primary.loginDate", "=", localDate);
    localPageRequest.setFilters(localQueryConditions);
    localPageRequest.setSortColumns(" order by primary.traderId");
    listByLimit(localPageRequest);
    return "success";
  }
  
  public String getDetail()
    throws Exception
  {
    String str = this.request.getParameter("traderId");
    PageRequest localPageRequest = new PageRequest(" and primary.traderId='" + str + "'");
    localPageRequest.setSortColumns(" order by primary.traderId");
    Page localPage = getService().getPage(localPageRequest, new ErrorLoginLog());
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
  
  public String deleteTraders()
  {
    String[] arrayOfString1 = this.request.getParameterValues("ids");
    String str1 = "";
    for (String str2 : arrayOfString1)
    {
      if (str1.trim().length() > 0) {
        str1 = str1 + ",";
      }
      str1 = str1 + "'" + str2 + "'";
    }
    getService().executeUpdateBySql("delete from m_errorloginlog where traderId in (" + str1 + ")");
    addReturnValue(1, 110603L, new Object[] { str1.replaceAll("'", "") });
    writeOperateLog(1031, "对代码为：" + str1 + "的交易员解锁", 1, "");
    return "success";
  }
  
  public String deleteAllActive()
  {
    getService().executeUpdateBySql("delete from m_errorloginlog ");
    addReturnValue(1, 110604L);
    writeOperateLog(1031, "清空前台所有登陆异常记录", 1, "");
    return "success";
  }
}
