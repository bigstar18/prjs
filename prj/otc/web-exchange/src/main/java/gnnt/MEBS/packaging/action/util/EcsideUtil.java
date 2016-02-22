package gnnt.MEBS.packaging.action.util;

import gnnt.MEBS.base.query.hibernate.Condition;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.query.hibernate.QueryHelper;
import gnnt.MEBS.base.util.ThreadStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.core.ECSideContext;
import org.ecside.core.bean.BaseBean;
import org.ecside.table.limit.FilterSet;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtils;

public class EcsideUtil
  extends BaseBean
{
  private static final transient Log logger = LogFactory.getLog(EcsideUtil.class);
  
  public static Map<String, Object> getQurey(HttpServletRequest request, String defaultField, boolean isOrderDesc)
  {
    Map<String, Object> map = new HashMap();
    getQc(request, map);
    getPageInfo(request, defaultField, isOrderDesc, map);
    return map;
  }
  
  public static void getQc(HttpServletRequest request, Map map)
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
    Limit limit = RequestUtils.getLimit(request);
    FilterSet filterSet = limit.getFilterSet();
    Map filterPropertyMap = filterSet.getPropertyValueMap();
    if ((filterPropertyMap != null) && (filterPropertyMap.keySet().size() > 0))
    {
      if (qc == null) {
        qc = new QueryConditions();
      }
      QueryConditions qcForEcside = QueryHelper.getQueryConditionsForMap(filterPropertyMap);
      List<Condition> list = qcForEcside.getConditionList();
      logger.debug("qc list:" + list.size());
      for (Condition condition : list) {
        qc.addCondition(condition);
      }
    }
    map.put("queryConditions", qc);
  }
  
  public static void getPageInfo(HttpServletRequest request, String defaultField, boolean isOrderDesc, Map map)
  {
    Limit limit = RequestUtils.getLimit(request);
    
    Sort sort = limit.getSort();
    Map sortValueMap = sort.getSortValueMap();
    PageInfo pageInfo = null;
    

    int pageSize = RequestUtils.getCurrentRowsDisplayed(request);
    logger.debug("pageSize:" + pageSize);
    int pageNo = RequestUtils.getPageNo(request);
    logger.debug("pageNo:" + pageNo);
    if (pageNo <= 0) {
      pageNo = 1;
    }
    if (pageSize <= 0) {
      pageSize = ECSideContext.DEFAULT_PAGE_SIZE;
    }
    String isAll = request.getParameter("eti_p");
    logger.debug("ec_totalrows:" + request.getParameter("ec_totalrows"));
    logger.debug("eti_p:" + isAll);
    if ((isAll != null) && (!"".equals(isAll)))
    {
      if ("false".equals(isAll)) {
        if ((request.getParameter("ec_totalrows") != null) && (!"".equals(request.getParameter("ec_totalrows")))) {
          pageSize = Integer.parseInt(request.getParameter("ec_totalrows"));
        }
      }
      ThreadStore.put("exportAll", "Y");
    }
    pageInfo = new PageInfo();
    pageInfo.setPageSize(pageSize);
    pageInfo.setPageNo(pageNo);
    if ((sortValueMap != null) && (sortValueMap.keySet().size() > 0))
    {
      for (Object o : sortValueMap.keySet())
      {
        String expressionString = (String)o;
        String[] expressionArray = QueryHelper.splitExpression(expressionString);
        String orderField = expressionArray[0];
        String order = (String)sortValueMap.get(expressionString);
        boolean orderDesc = true;
        if (order.toUpperCase().equals("ASC")) {
          orderDesc = false;
        }
        pageInfo.addOrderField(orderField, orderDesc);
      }
    }
    else if (ThreadStore.get("orderString") == null)
    {
      pageInfo.addOrderField(defaultField, isOrderDesc);
    }
    else
    {
      String orderString = (String)ThreadStore.get("orderString");
      pageInfo.setOrderString(orderString);
    }
    map.put("pageInfo", pageInfo);
  }
  
  public static void setRowAttributes(HttpServletRequest request, int totalRows)
  {
    Limit limit = RequestUtils.getLimit(request);
    limit.setRowAttributes(totalRows, ECSideContext.DEFAULT_PAGE_SIZE);
  }
}
