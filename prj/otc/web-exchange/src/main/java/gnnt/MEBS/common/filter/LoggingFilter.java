package gnnt.MEBS.common.filter;

import com.opensymphony.xwork2.util.ValueStack;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.ExecuteObject;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.globalLog.util.Compare;
import gnnt.MEBS.packaging.action.util.LogHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingFilter
  implements Filter
{
  private final transient Log logger = LogFactory.getLog(LoggingFilter.class);
  private FilterConfig filterConfig = null;
  private List<String> noKeywordList = null;
  private List<String> methodList = null;
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    String contextPath = ((HttpServletRequest)request).getContextPath();
    String requestUri = ((HttpServletRequest)request).getRequestURI();
    String value;
    for (String parameter : this.methodList)
    {
      value = ((HttpServletRequest)request).getParameter(parameter);
      if (value != null) {
        requestUri = requestUri + "?" + parameter + "=" + value;
      }
    }
    requestUri = requestUri.replaceFirst(contextPath, "");
    boolean sign = true;
    for (String key : this.noKeywordList) {
      if ((requestUri + ",").indexOf(key + ",") > 0) {
        sign = false;
      }
    }
    if (!sign)
    {
      chain.doFilter(request, response);
    }
    else
    {
      String key = requestUri + LogConstant.MARK + System.currentTimeMillis();
      request.setAttribute(LogConstant.OLDOBJECTGETKEY, key);
      HttpSession session = ((HttpServletRequest)request).getSession();
      chain.doFilter(request, response);
      

      RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest)request);
      Object obj = requestWrapper.getAttribute(ActionConstant.OBJ);
      if (requestUri.contains(LogConstant.FORWARDUPDATE))
      {
        this.logger.debug(LogConstant.FORWARDUPDATE + " url:" + requestUri);
        try
        {
          Map map = (Map)session.getAttribute(LogConstant.OLDOBJECTMAP);
          if (map != null)
          {
            this.logger.debug("update map");
          }
          else
          {
            this.logger.debug("new map");
            map = Collections.synchronizedMap(new LogHashMap());
          }
          this.logger.debug("obj:" + obj);
          map.put(key, obj);
          this.logger.debug(LogConstant.FORWARDUPDATE);
          session.setAttribute(LogConstant.OLDOBJECTMAP, map);
          this.logger.debug(Integer.valueOf(map.size()));
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      else if (requestUri.contains(LogConstant.UPDATE))
      {
        this.logger.debug(LogConstant.UPDATE + " url:" + requestUri);
        this.logger.debug("ThreadStore.get(\"isLog\")" + ThreadStore.get(ThreadStoreConstant.ISLOG));
        if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && 
          (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
        {
          String operate = (String)ThreadStore.get(ThreadStoreConstant.OPERATE);
          this.logger.debug("operate:" + operate);
          this.logger.debug(request.getAttribute(ActionConstant.RESULTVAULE));
          if (((request.getAttribute(ActionConstant.RESULTVAULE) != null) && (((Integer)request.getAttribute(ActionConstant.RESULTVAULE)).intValue() > 0)) || (
            (((HttpServletRequest)request).getSession().getAttribute(ActionConstant.RESULTVAULE) != null) && (((Integer)((HttpServletRequest)request).getSession().getAttribute(ActionConstant.RESULTVAULE)).intValue() > 0)))
          {
            String oldObjKey = request.getParameter(LogConstant.OLDOBJECTGETKEY);
            
            this.logger.debug("oldObjKey:" + oldObjKey);
            
            this.logger.debug("obj:" + obj);
            Map map = (Map)session.getAttribute(LogConstant.OLDOBJECTMAP);
            if (map == null) {
              map = new HashMap();
            }
            Object oldObject = map.get(oldObjKey);
            try
            {
              ExecuteObject executeObject = Compare.getDifferent(oldObject, obj);
              this.logger.debug("executeObject.getPropertyList().size():" + executeObject.getPropertyList().size());
              if (executeObject.getPropertyList().size() > 0)
              {
                String description = Compare.translate(executeObject);
                this.logger.debug("description:" + description);
                addGolbalLog(description, obj, operate, AclCtrl.getLogonID(request), request);
              }
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
            map.remove(oldObjKey);
          }
        }
      }
      else if (requestUri.contains(LogConstant.ADD))
      {
        this.logger.debug(LogConstant.ADD + " url:" + requestUri);
        if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && 
          (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
        {
          this.logger.debug(LogConstant.ADD + " url:" + requestUri);
          String operate = (String)ThreadStore.get(ThreadStoreConstant.OPERATE);
          this.logger.debug("operate:" + operate);
          if (((Integer)request.getAttribute(ActionConstant.RESULTVAULE)).intValue() > 0)
          {
            this.logger.debug("obj:" + obj);
            try
            {
              ExecuteObject executeObject = Compare.getDifferent(null, obj);
              this.logger.debug("executeObject.getPropertyList().size():" + executeObject.getPropertyList().size());
              String description = Compare.translate(executeObject);
              this.logger.debug("description:" + description);
              addGolbalLog(description, obj, operate, AclCtrl.getLogonID(request), request);
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
  
  public void destroy()
  {
    this.methodList = null;
    this.noKeywordList = null;
  }
  
  public void init(FilterConfig filterCfg)
    throws ServletException
  {
    this.filterConfig = filterCfg;
    String noKeyword = this.filterConfig.getInitParameter("NOKEYWORDS");
    String[] noKeywords = noKeyword.split(",");
    this.noKeywordList = new ArrayList();
    for (int i = 0; i < noKeywords.length; i++) {
      this.noKeywordList.add(noKeywords[i]);
    }
    String method = this.filterConfig.getInitParameter("METHODS");
    String[] methods = method.split(",");
    this.methodList = new ArrayList();
    for (int i = 0; i < methods.length; i++) {
      this.methodList.add(methods[i]);
    }
  }
  
  private class RequestWrapper
    extends HttpServletRequestWrapper
  {
    public RequestWrapper(HttpServletRequest request)
    {
      super();
    }
    
    private final transient Log logger = LogFactory.getLog(RequestWrapper.class);
    
    public Object getAttribute(String s)
    {
      this.logger.debug("使用中" + s);
      

      Object attribute = super.getAttribute(s);
      if (attribute == null)
      {
        ValueStack vs = (ValueStack)super.getAttribute("struts.valueStack");
        if (vs != null) {
          attribute = vs.findValue(s);
        }
      }
      return attribute;
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
    operateLog.setOperateIp(request.getRemoteAddr());
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    operateLog.setMark((String)((HttpServletRequest)request).getSession().getAttribute(ActionConstant.REGISTERID));
    OperateLogService operateLogService = (OperateLogService)SpringContextHelper.getBean("globalLogService");
    operateLogService.add(operateLog);
  }
}
