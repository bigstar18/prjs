package gnnt.MEBS.common.filter;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuLoggingFilter
  implements Filter
{
  private final transient Log logger = LogFactory.getLog(MenuLoggingFilter.class);
  
  public void destroy() {}
  
  public void init(FilterConfig filterCfg)
    throws ServletException
  {}
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    chain.doFilter(request, response);
    if (ThreadStore.get(ThreadStoreConstant.RIGHT) != null)
    {
      Right right = (Right)ThreadStore.get(ThreadStoreConstant.RIGHT);
      String desc = "点击菜单" + right.getName();
      addGolbalLog(desc, null, "", AclCtrl.getLogonID(request), request);
    }
  }
  
  public void addGolbalLog(String desc, Object obj, String operate, String operator, ServletRequest request)
  {
    OperateLog operateLog = new OperateLog();
    operateLog.setObj(obj);
    operateLog.setOperateContent(desc);
    operateLog.setOperateDate(new Date());
    operateLog.setOperateType(operate);
    operateLog.setOperator(operator);
    operateLog.setOperateLogType(3005);
    operateLog.setOperateIp(request.getRemoteAddr());
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    operateLog.setMark((String)((HttpServletRequest)request).getSession().getAttribute(ActionConstant.REGISTERID));
    OperateLogService operateLogService = (OperateLogService)SpringContextHelper.getBean("globalLogService");
    operateLogService.add(operateLog);
  }
}
