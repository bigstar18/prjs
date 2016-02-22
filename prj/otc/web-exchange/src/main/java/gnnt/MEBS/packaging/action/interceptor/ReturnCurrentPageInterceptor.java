package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.query.hibernate.OrderField;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ReturnCurrentPageInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ReturnCurrentPageInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    if (request.getParameter("return") != null)
    {
      this.logger.debug("enter list赋值");
      HttpSession session = request.getSession();
      PageInfo pageInfo = (PageInfo)session.getAttribute("pi");
      Map oldParams = (Map)session.getAttribute("op");
      if (oldParams != null) {
        for (Object o : oldParams.keySet())
        {
          this.logger.debug("--:" + o.toString() + ";" + oldParams.get(o).toString());
          request.setAttribute("_" + o.toString(), oldParams.get(o).toString());
        }
      }
      if (pageInfo != null)
      {
        this.logger.debug("pageSize:" + pageInfo.getPageSize());
        request.setAttribute(ActionConstant.PAGESIZE, pageInfo.getPageSize());
        this.logger.debug("pageNo:" + pageInfo.getPageNo());
        request.setAttribute(ActionConstant.PAGENO, pageInfo.getPageNo());
        
        List list = pageInfo.getOrderFields();
        if ((list != null) && (list.size() > 0))
        {
          String[] orderField = new String[list.size()];
          String[] orderType = new String[list.size()];
          for (int i = 0; i < list.size(); i++)
          {
            OrderField order = (OrderField)list.get(i);
            orderField[i] = order.getOrderField();
            orderType[i] = order.isOrderDesc();
          }
          this.logger.debug("orderField:" + orderField.length);
          request.setAttribute(ActionConstant.ORDERFIELD, orderField);
          this.logger.debug("orderType:" + orderType.length);
          request.setAttribute(ActionConstant.ORDERDESC, orderType);
        }
      }
    }
    String result = invocation.invoke();
    if (request.getAttribute("noReturn") == null)
    {
      this.logger.debug("enter list收值");
      if (request.getAttribute(ActionConstant.PAGEINFO) != null)
      {
        PageInfo pageInfo = (PageInfo)request.getAttribute(ActionConstant.PAGEINFO);
        request.getSession().setAttribute("pi", pageInfo);
      }
      if (request.getAttribute(ActionConstant.OLDPARAMS) != null)
      {
        Map oldParams = (Map)request.getAttribute(ActionConstant.OLDPARAMS);
        request.getSession().setAttribute("op", oldParams);
      }
    }
    return result;
  }
}
