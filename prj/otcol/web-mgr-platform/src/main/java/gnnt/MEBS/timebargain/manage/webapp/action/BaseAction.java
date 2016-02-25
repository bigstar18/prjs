package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.service.xtgl.UserManager;
import gnnt.MEBS.timebargain.manage.util.ConvertUtil;
import gnnt.MEBS.timebargain.manage.util.CurrencyConverter;
import gnnt.MEBS.timebargain.manage.util.DateConverter;
import gnnt.MEBS.timebargain.manage.util.DateUtil;
import gnnt.MEBS.timebargain.manage.util.SysData;
import gnnt.MEBS.timebargain.manage.util.TimestampConverter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

public class BaseAction
  extends DispatchAction
{
  protected final Log log = LogFactory.getLog(getClass());
  public static final String SECURE = "secure";
  private static Long defaultLong = null;
  
  public Object getBean(String paramString)
  {
    return SysData.getBean(paramString);
  }
  
  protected Object convert(Object paramObject)
    throws Exception
  {
    return ConvertUtil.convert(paramObject);
  }
  
  protected Object convertLists(Object paramObject)
    throws Exception
  {
    return ConvertUtil.convertLists(paramObject);
  }
  
  public ActionMessages getMessages(HttpServletRequest paramHttpServletRequest)
  {
    ActionMessages localActionMessages = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (paramHttpServletRequest.getAttribute("org.apache.struts.action.ACTION_MESSAGE") != null)
    {
      localActionMessages = (ActionMessages)paramHttpServletRequest.getAttribute("org.apache.struts.action.ACTION_MESSAGE");
      saveMessages(paramHttpServletRequest, localActionMessages);
    }
    else if (localHttpSession.getAttribute("org.apache.struts.action.ACTION_MESSAGE") != null)
    {
      localActionMessages = (ActionMessages)localHttpSession.getAttribute("org.apache.struts.action.ACTION_MESSAGE");
      saveMessages(paramHttpServletRequest, localActionMessages);
      localHttpSession.removeAttribute("org.apache.struts.action.ACTION_MESSAGE");
    }
    else
    {
      localActionMessages = new ActionMessages();
    }
    return localActionMessages;
  }
  
  private String getActionMethodWithMapping(HttpServletRequest paramHttpServletRequest, ActionMapping paramActionMapping)
  {
    return getActionMethod(paramHttpServletRequest, paramActionMapping.getParameter());
  }
  
  protected String getActionMethod(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    String str1 = null;
    str1 = paramHttpServletRequest.getParameter(paramString);
    if (str1 != null)
    {
      str1 = str1.trim();
      return str1.replace(str1.charAt(0), Character.toLowerCase(str1.charAt(0)));
    }
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      String str2 = (String)localEnumeration.nextElement();
      if (str2.startsWith(paramString + "."))
      {
        if (this.log.isDebugEnabled()) {
          this.log.debug("calling method: " + str2);
        }
        String[] arrayOfString = StringUtils.split(str2, ".");
        str1 = arrayOfString[1];
        break;
      }
    }
    return str1;
  }
  
  public ActionForward execute(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (isCancelled(paramHttpServletRequest)) {
      try
      {
        getMethod("cancel");
        return dispatchMethod(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse, "cancel");
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        this.log.warn("No 'cancel' method found, returning null");
        return cancelled(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
      }
    }
    String str = getActionMethodWithMapping(paramHttpServletRequest, paramActionMapping);
    if (str != null) {
      return dispatchMethod(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse, str);
    }
    String[] arrayOfString = { "edit", "save", "search", "view" };
    for (int i = 0; i < arrayOfString.length; i++) {
      if (paramHttpServletRequest.getServletPath().indexOf(arrayOfString[i]) > -1) {
        return dispatchMethod(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse, arrayOfString[i]);
      }
    }
    return super.execute(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  protected ActionForm getActionForm(ActionMapping paramActionMapping, HttpServletRequest paramHttpServletRequest)
  {
    ActionForm localActionForm = null;
    if (paramActionMapping.getAttribute() != null) {
      if ("request".equals(paramActionMapping.getScope()))
      {
        localActionForm = (ActionForm)paramHttpServletRequest.getAttribute(paramActionMapping.getAttribute());
      }
      else
      {
        HttpSession localHttpSession = paramHttpServletRequest.getSession();
        localActionForm = (ActionForm)localHttpSession.getAttribute(paramActionMapping.getAttribute());
      }
    }
    return localActionForm;
  }
  
  public Map getConfiguration()
  {
    HashMap localHashMap = (HashMap)getServlet().getServletContext().getAttribute("appConfig");
    if (localHashMap == null) {
      return new HashMap();
    }
    return localHashMap;
  }
  
  protected void removeFormBean(ActionMapping paramActionMapping, HttpServletRequest paramHttpServletRequest)
  {
    if (paramActionMapping.getAttribute() != null) {
      if ("request".equals(paramActionMapping.getScope()))
      {
        paramHttpServletRequest.removeAttribute(paramActionMapping.getAttribute());
      }
      else
      {
        HttpSession localHttpSession = paramHttpServletRequest.getSession();
        localHttpSession.removeAttribute(paramActionMapping.getAttribute());
      }
    }
  }
  
  protected void updateFormBean(ActionMapping paramActionMapping, HttpServletRequest paramHttpServletRequest, ActionForm paramActionForm)
  {
    if (paramActionMapping.getAttribute() != null)
    {
      System.out.print(paramActionMapping.getAttribute());
      if ("request".equals(paramActionMapping.getScope()))
      {
        paramHttpServletRequest.setAttribute(paramActionMapping.getAttribute(), paramActionForm);
      }
      else
      {
        HttpSession localHttpSession = paramHttpServletRequest.getSession();
        localHttpSession.setAttribute(paramActionMapping.getAttribute(), paramActionForm);
      }
    }
  }
  
  protected String getFirstDayOfMonth()
  {
    Calendar localCalendar = Calendar.getInstance();
    Date localDate = localCalendar.getTime();
    localDate = DateUtil.GoDate(localDate, -localCalendar.get(5) + 1);
    return DateUtil.formatDate(localDate, "yyyy-MM-dd");
  }
  
  protected String getEndDayOfMonth()
  {
    Calendar localCalendar = Calendar.getInstance();
    Date localDate = localCalendar.getTime();
    localDate = DateUtil.GoDate(localDate, -localCalendar.get(5) + 1);
    localDate = DateUtil.GoDate(DateUtil.GoMonth(localDate, 1), -1);
    return DateUtil.formatDate(localDate, "yyyy-MM-dd");
  }
  
  protected String getLastDay()
  {
    Date localDate = DateUtil.GoDate(new Date(), -1);
    return DateUtil.formatDate(localDate, "yyyy-MM-dd");
  }
  
  protected String getThisDay()
  {
    Date localDate = DateUtil.GoDate(new Date(), 0);
    return DateUtil.formatDate(localDate, "yyyy-MM-dd");
  }
  
  protected void rendText(HttpServletResponse paramHttpServletResponse, String paramString)
    throws IOException
  {
    paramHttpServletResponse.setCharacterEncoding("GBK");
    paramHttpServletResponse.getWriter().write(paramString);
  }
  
  protected void addSysLog(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2)
  {
    UserManager localUserManager = (UserManager)getBean("userManager");
    localUserManager.addSysLog(AclCtrl.getLogonID(paramHttpServletRequest), paramString1, paramString2);
  }
  
  protected void addSysLog(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    addSysLog(paramHttpServletRequest, "01", paramString);
  }
  
  static
  {
    ConvertUtils.register(new CurrencyConverter(), Double.class);
    ConvertUtils.register(new DateConverter(), Date.class);
    ConvertUtils.register(new DateConverter(), String.class);
    ConvertUtils.register(new LongConverter(defaultLong), Long.class);
    ConvertUtils.register(new DoubleConverter(defaultLong), Double.class);
    ConvertUtils.register(new IntegerConverter(defaultLong), Integer.class);
    ConvertUtils.register(new ShortConverter(defaultLong), Short.class);
    ConvertUtils.register(new TimestampConverter(), Timestamp.class);
  }
}
