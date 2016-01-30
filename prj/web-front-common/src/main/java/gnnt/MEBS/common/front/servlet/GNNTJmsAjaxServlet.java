package gnnt.MEBS.common.front.servlet;

import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.activemq.web.AjaxServlet;

public class GNNTJmsAjaxServlet
  extends AjaxServlet
{
  private static final long serialVersionUID = 2538638263012152495L;
  
  public void init()
    throws ServletException
  {
    resetBrokerURL();
    super.init();
  }
  
  private void resetBrokerURL()
  {
    String str1 = "org.apache.activemq.brokerURL";
    String str2 = "org.apache.activemq.connectionFactory";
    ServletContext localServletContext = getServletContext();
    if (localServletContext.getAttribute(str2) == null) {
      try
      {
        Field localField = localServletContext.getClass().getDeclaredField("context");
        localField.setAccessible(true);
        Object localObject = localField.get(localServletContext);
        Method localMethod = localObject.getClass().getDeclaredMethod("setInitParameter", new Class[] { String.class, String.class });
        localMethod.invoke(localObject, new Object[] { str1, getBrokerURLFromDB(localServletContext, str1) });
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    System.out.println(localServletContext.getInitParameter(str1));
  }
  
  private String getBrokerURLFromDB(ServletContext paramServletContext, String paramString)
  {
    String str = paramServletContext.getInitParameter(paramString);
    StandardService localStandardService = (StandardService)ApplicationContextInit.getBean("com_standardService");
    List localList = localStandardService.getListBySql("select INFOVALUE from C_MARKETINFO where INFONAME='JMSBrokerURL'");
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if ((localMap != null) && (localMap.get("INFOVALUE") != null)) {
        str = localMap.get("INFOVALUE").toString();
      }
    }
    return str;
  }
}
