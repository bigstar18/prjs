package gnnt.MEBS.audit.businessService;

import gnnt.MEBS.base.copy.MapToObject;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.SpringContextHelper;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BusinessServiceImpl
  implements BusinessService
{
  private final transient Log logger = LogFactory.getLog(BusinessServiceImpl.class);
  private String serrviceName;
  private String methodName;
  private String classFullName;
  
  public void setSerrviceName(String serrviceName)
  {
    this.serrviceName = serrviceName;
  }
  
  public void setMethodName(String methodName)
  {
    this.methodName = methodName;
  }
  
  public void setClassFullName(String classFullName)
  {
    this.classFullName = classFullName;
  }
  
  public int business(Map<String, String> businessObjectMap)
  {
    int returnValue = 0;
    Object object = SpringContextHelper.getBean(this.serrviceName);
    Method method = null;
    try
    {
      Class classType = object.getClass();
      Method[] methods = classType.getMethods();
      if ((methods != null) && (methods.length > 0)) {
        for (Method met : methods) {
          if ((met.getName().equals(this.methodName)) && (met.getParameterTypes().length == 1))
          {
            method = met;
            break;
          }
        }
      }
      Clone clone = getObject();
      MapToObject.bindData(businessObjectMap, clone);
      this.logger.debug("method:" + method.getName());
      this.logger.debug("clone:" + clone.getClass().getName());
      this.logger.debug("object:" + object.getClass().getName());
      Object o = method.invoke(object, new Object[] { clone });
      returnValue = ((Integer)o).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      returnValue = -1;
    }
    return returnValue;
  }
  
  private Clone getObject()
  {
    Clone clone = null;
    Class classType = null;
    try
    {
      classType = Class.forName(this.classFullName);
      clone = (Clone)classType.newInstance();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return clone;
  }
}
