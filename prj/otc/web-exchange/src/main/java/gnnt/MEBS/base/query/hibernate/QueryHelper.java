package gnnt.MEBS.base.query.hibernate;

import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryHelper
{
  private static final transient Log logger = LogFactory.getLog(QueryHelper.class);
  
  public static QueryConditions getQueryConditionsFromRequest(HttpServletRequest request)
  {
    logger.debug("enter getQueryConditionsFromReq...");
    
    QueryConditions qc = null;
    Map keys = getMapFromRequest(request, ActionConstant.GNNT_);
    qc = getQueryConditionsForMap(keys);
    return qc;
  }
  
  public static QueryConditions getQueryConditionsForMap(Map keys)
  {
    QueryConditions qc = null;
    if ((keys != null) && (keys.size() > 0))
    {
      logger.debug("parameter size:" + keys.size());
      for (Iterator it = keys.keySet().iterator(); it.hasNext();)
      {
        String key = (String)it.next();
        Object value = keys.get(key).toString().trim();
        logger.debug("parameter:" + key);
        String[] expressionArray = splitExpression(key);
        String param = expressionArray[0];
        String op = expressionArray[1];
        String type = expressionArray[2];
        if ((param != null) && (value != null) && (((String)value).length() > 0))
        {
          if (qc == null) {
            qc = new QueryConditions();
          }
          if (type != null) {
            try
            {
              value = Utils.convert(value, type);
            }
            catch (Exception be)
            {
              logger.error("Utils.convert() failed!");
            }
          }
          logger.debug("parameter:" + param);
          logger.debug("operator:" + op);
          logger.debug("datatype:" + type);
          logger.debug("value:" + value);
          logger.debug("value calss:" + value.getClass().getName());
          qc.addCondition(param, op, value, type);
        }
      }
    }
    return qc;
  }
  
  public static String[] splitExpression(String expressionString)
  {
    String[] expressionArray = new String[3];
    Pattern pOperatorDatatype = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");
    
    Pattern pOperator = Pattern.compile("(.+?)\\[(.+)]");
    Matcher m = pOperatorDatatype.matcher(expressionString);
    if (m.matches())
    {
      expressionArray[0] = m.group(1);
      expressionArray[1] = m.group(2);
      expressionArray[2] = m.group(3);
    }
    else
    {
      m = pOperator.matcher(expressionString);
      if (m.matches())
      {
        expressionArray[0] = m.group(1);
        expressionArray[1] = m.group(2);
        expressionArray[2] = "string";
      }
      else
      {
        expressionArray[0] = null;
        expressionArray[1] = null;
        expressionArray[2] = null;
      }
    }
    return expressionArray;
  }
  
  public static QueryConditions createQueryConditionsFromRequest(HttpServletRequest request)
  {
    QueryConditions qc = getQueryConditionsFromRequest(request);
    if (qc == null) {
      qc = new QueryConditions();
    }
    return qc;
  }
  
  public static PageInfo getPageInfoFromRequest(HttpServletRequest request)
  {
    logger.debug("enter getPageInfoFromRequest...");
    PageInfo pageInfo = null;
    int pageSize = Utils.parseInt(request.getParameter(ActionConstant.PAGESIZE));
    int pageNo = Utils.parseInt(request.getParameter(ActionConstant.PAGENO), 1);
    if (pageSize > 0)
    {
      pageInfo = new PageInfo();
      pageInfo.setPageSize(pageSize);
      pageInfo.setPageNo(pageNo);
      
      String[] orderFields = request.getParameterValues(ActionConstant.ORDERFIELD);
      String[] orderTypes = request.getParameterValues(ActionConstant.ORDERDESC);
      if ((orderFields != null) && (orderTypes != null))
      {
        for (int i = 0; i < orderFields.length; i++)
        {
          logger.debug("orderField " + i + "=" + orderFields[i]);
          logger.debug("orderDesc " + i + "=" + orderTypes[i]);
          String orderField = orderFields[i];
          boolean orderDesc = Utils.parseBoolean(orderTypes[i]);
          pageInfo.addOrderField(orderField, orderDesc);
        }
      }
      else
      {
        String orderField = request.getParameter(ActionConstant.ORDERFIELD);
        String orderType = request.getParameter(ActionConstant.ORDERDESC);
        logger.debug("orderField =" + orderField);
        logger.debug("orderDesc =" + orderType);
        if ((orderField != null) && (orderField.length() > 0))
        {
          boolean orderDesc = Utils.parseBoolean(orderType);
          pageInfo.addOrderField(orderField, orderDesc);
        }
        else
        {
          return null;
        }
      }
      logger.debug("pageInfo: pageNo = " + pageInfo.getPageNo() + ", pageSize = " + pageInfo.getPageSize());
    }
    return pageInfo;
  }
  
  public static String getTargetView(HttpServletRequest request, String defaultTarget)
  {
    String target = request.getParameter("targetView");
    if ((target == null) || (target.length() == 0)) {
      target = defaultTarget;
    }
    return target;
  }
  
  public static Map getMapFromRequest(HttpServletRequest request, String beginStr)
  {
    Map keys = WebUtils.getParametersStartingWith(request, beginStr);
    Map keysForAttribute = WebUtils.getAttributeStartingWith(request, beginStr);
    keys.putAll(keysForAttribute);
    return keys;
  }
}
