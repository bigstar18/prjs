package gnnt.MEBS.base.query.jdbc;

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
    Map keys = WebUtils.getParametersStartingWith(request, ActionConstant.GNNT_);
    Map keysForAttribute = WebUtils.getAttributeStartingWith(request, ActionConstant.GNNT_);
    keys.putAll(keysForAttribute);
    qc = getQueryConditionsForMap(keys);
    
    return qc;
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
    
    String pageSizeForString = request.getParameter("pageSize");
    if (pageSizeForString == null) {
      pageSizeForString = (String)request.getAttribute("pageSize");
    }
    int pageSize = Utils.parseInt(pageSizeForString);
    

    String pageNoForString = request.getParameter("pageNo");
    if (pageNoForString == null) {
      pageNoForString = (String)request.getAttribute("pageNo");
    }
    int pageNo = Utils.parseInt(pageNoForString, 1);
    if (pageSize > 0)
    {
      pageInfo = new PageInfo();
      
      pageInfo.setPageSize(pageSize);
      pageInfo.setPageNo(pageNo);
      

      String[] orderFields = request.getParameterValues("orderField");
      if (orderFields == null)
      {
        orderFields = (String[])request.getAttribute("orderField");
        logger.debug("orderFields:" + orderFields);
      }
      String[] orderTypes = request.getParameterValues("orderDesc");
      if (orderTypes == null) {
        orderTypes = (String[])request.getAttribute("orderDesc");
      }
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
        String orderField = request.getParameter("orderField");
        String orderType = request.getParameter("orderDesc");
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
  
  public static String getTargetView(HttpServletRequest request, String defaultTarget)
  {
    String target = request.getParameter("targetView");
    if ((target == null) || (target.length() == 0)) {
      target = defaultTarget;
    }
    return target;
  }
  
  public static QueryConditions getQueryConditionsForMap(Map keys)
  {
    QueryConditions qc = null;
    if ((keys != null) && (keys.size() > 0))
    {
      logger.debug("parameter size:" + keys.size());
      
      Pattern pOperatorDatatype = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");
      
      Pattern pOperator = Pattern.compile("(.+?)\\[(.+)]");
      for (Iterator it = keys.keySet().iterator(); it.hasNext();)
      {
        String key = (String)it.next();
        Object value = keys.get(key).toString().trim();
        logger.debug("parameter:" + key);
        Matcher m = pOperatorDatatype.matcher(key);
        String type;
        String param;
        String op;
        String type;
        if (m.matches())
        {
          String param = m.group(1);
          String op = m.group(2);
          type = m.group(3);
        }
        else
        {
          m = pOperator.matcher(key);
          String type;
          if (m.matches())
          {
            String param = m.group(1);
            String op = m.group(2);
            type = null;
          }
          else
          {
            param = null;
            op = null;
            type = null;
          }
        }
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
          qc.addCondition(param, op, value, type);
        }
      }
    }
    return qc;
  }
  
  public static QueryConditions getQueryConditionsFromRequestAttribute(HttpServletRequest request, String prefix)
  {
    Map map = WebUtils.getAttributeStartingWith(request, prefix);
    QueryConditions qc = getQueryConditionsForMap(map);
    return qc;
  }
  
  public static Map getMapFromRequest(HttpServletRequest request, String beginString)
  {
    logger.debug("enter getQueryConditionsFromReq...");
    
    Map keys = WebUtils.getParametersStartingWith(request, beginString);
    Map keysForAttribute = WebUtils.getAttributeStartingWith(request, beginString);
    keys.putAll(keysForAttribute);
    
    return keys;
  }
}
