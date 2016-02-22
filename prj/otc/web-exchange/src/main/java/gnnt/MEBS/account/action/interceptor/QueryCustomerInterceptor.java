package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryCustomerInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryCustomerInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter queryCustomerInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    String memberIds = request.getParameter(ActionConstant.GNNT_ + "memberInfoIds");
    String organizationIds = request.getParameter(ActionConstant.GNNT_ + "organizationIds");
    String brokerageIds = request.getParameter(ActionConstant.GNNT_ + "brokerageIds");
    String sqlString = "";
    sqlString = sqlString + "select t.customerNo from gnnt.MEBS.account.model.CustomerVO t1,gnnt.MEBS.broke.model.CustomerMappingBroker t where t.customerNo = t1.customerNo ";
    String filterString = "";
    int count = 998;
    if ((memberIds != null) && (!"".equals(memberIds)))
    {
      this.logger.debug("memberIds:" + memberIds);
      String[] mems = memberIds.split(",");
      String filter = "";
      String memberIds1 = memberIds;
      if (mems != null)
      {
        int i = mems.length / count;
        for (int j = 0; j < i; j++)
        {
          String subString = findSubString(memberIds1, ",", count);
          if (!"".equals(subString))
          {
            filter = filter + "t1.memberNo in ( " + subString + " ) or ";
            memberIds1 = memberIds1.replace(subString + ",", "");
          }
        }
        filter = filter + "t1.memberNo in ( " + memberIds1 + " )";
      }
      else
      {
        filter = "t1.memberNo in ( " + memberIds + " )";
      }
      filterString = filterString + "((" + filter + ") and t1.organizationNo is null and t1.brokerageNo is null ) ";
    }
    this.logger.debug("filterString:" + filterString);
    if ((organizationIds != null) && (!"".equals(organizationIds)))
    {
      if (!"".equals(filterString)) {
        filterString = filterString + " or ";
      }
      String[] orgs = organizationIds.split(",");
      String filter = "";
      String organizationIds1 = organizationIds;
      if (orgs != null)
      {
        int i = orgs.length / count;
        for (int j = 0; j < i; j++)
        {
          String subString = findSubString(organizationIds1, ",", count);
          if (!"".equals(subString))
          {
            filter = filter + "t.organizationNo in ( " + subString + " ) or ";
            organizationIds1 = organizationIds1.replace(subString + ",", "");
          }
        }
        filter = filter + "t.organizationNo in ( " + organizationIds1 + " )";
      }
      else
      {
        filter = "t.organizationNo in ( " + organizationIds + " )";
      }
      this.logger.debug("organizationIds:" + organizationIds);
      filterString = filterString + " ((" + filter + " ) and t.brokerageNo is null) ";
    }
    if ((brokerageIds != null) && (!"".equals(brokerageIds)))
    {
      if (!"".equals(filterString)) {
        filterString = filterString + " or ";
      }
      String[] bros = brokerageIds.split(",");
      String filter = "";
      String brokerageIds1 = brokerageIds;
      if (bros != null)
      {
        int i = bros.length / count;
        for (int j = 0; j < i; j++)
        {
          String subString = findSubString(brokerageIds1, ",", count);
          if (!"".equals(subString))
          {
            filter = filter + "t.brokerageNo in ( " + subString + " ) or ";
            brokerageIds1 = brokerageIds1.replace(subString + ",", "");
          }
        }
        filter = filter + "t.brokerageNo in ( " + brokerageIds1 + " )";
      }
      else
      {
        filter = "t.brokerageNo in ( " + brokerageIds + " )";
      }
      this.logger.debug("brokerageIds:" + brokerageIds);
      filterString = filterString + " (" + filter + " )";
    }
    if (!"".equals(filterString))
    {
      filterString = " and (" + filterString + ")";
      sqlString = "( " + sqlString + filterString + ")";
      request.setAttribute(ActionConstant.GNNT_ + "primary.customerNo[in]", sqlString);
    }
    this.logger.debug("sqlString:" + sqlString);
    String result = invocation.invoke();
    return result;
  }
  
  public String findSubString(String str, String letter, int num)
  {
    int i = 0;
    int m = 0;
    char c = new String(letter).charAt(0);
    char[] ch = str.toCharArray();
    for (int j = 0; j < ch.length; j++) {
      if (ch[j] == c)
      {
        i++;
        if (i == num)
        {
          m = j;
          break;
        }
      }
    }
    return str.substring(0, m);
  }
}
