package gnnt.MEBS.bill.core.reflect;

import gnnt.MEBS.bill.core.po.Clone;
import gnnt.MEBS.bill.core.util.Tool;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResultSetToBeanBySetMethod
  implements IResultSetToBean
{
  private Log logger = LogFactory.getLog(ResultSetToBeanBySetMethod.class);
  
  public Clone resultSetToBean(Clone paramClone, ResultSet paramResultSet)
  {
    Class localClass = paramClone.getClass();
    Clone localClone = (Clone)paramClone.clone();
    try
    {
      Method[] arrayOfMethod1 = localClass.getDeclaredMethods();
      for (Method localMethod : arrayOfMethod1) {
        if (localMethod.getName().substring(0, 3).equalsIgnoreCase("set"))
        {
          Object localObject = paramResultSet.getObject(localMethod.getName().substring(3));
          if (paramClone != null) {
            try
            {
              localMethod.invoke(localClone, new Object[] { localObject });
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
              this.logger.error("Class:" + localClass.getName() + " in methodName=" + localMethod.getName() + "invoke error!");
            }
          }
        }
      }
    }
    catch (Exception localException1)
    {
      this.logger.error(Tool.getExceptionTrace(localException1));
    }
    return localClone;
  }
}
