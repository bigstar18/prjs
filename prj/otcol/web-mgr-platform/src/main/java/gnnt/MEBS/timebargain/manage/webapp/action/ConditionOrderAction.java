package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.service.ConditionOrderManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.ECSideUtils;
import org.ecside.util.RequestUtils;

public class ConditionOrderAction
  extends BaseAction
{
  public ActionForward topConditionOrder(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topConditionOrder' method");
    }
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topConditionOrder");
  }
  
  public ActionForward listConditionOrder(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listConditionOrder' method");
    }
    ConditionOrderManager localConditionOrderManager = (ConditionOrderManager)getBean("conditionOrderManager");
    QueryConditions localQueryConditions1 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    try
    {
      if (j < 0) {
        j = localConditionOrderManager.getConditionOrderCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap);
      localQueryConditions2.addCondition("orderby", "=", str);
      List localList = localConditionOrderManager.getConditionOrder(localQueryConditions2, k, m);
      if (j < 0) {
        j = localConditionOrderManager.getConditionOrderCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      localQueryConditions2.addCondition("orderby", "=", str);
      paramHttpServletRequest.setAttribute("BS_FLAG", ConditionOrderDictionary.BS_FLAG);
      paramHttpServletRequest.setAttribute("ORDERTYPE", ConditionOrderDictionary.ORDERTYPE);
      paramHttpServletRequest.setAttribute("CONDITIONOPERATION", ConditionOrderDictionary.CONDITIONOPERATION);
      paramHttpServletRequest.setAttribute("orderList", localList);
    }
    catch (Exception localException)
    {
      System.out.println("------------------出现异常----------------------");
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listConditionOrder");
  }
}
