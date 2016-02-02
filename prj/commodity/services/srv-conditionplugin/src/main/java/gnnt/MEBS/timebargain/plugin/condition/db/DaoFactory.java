package gnnt.MEBS.timebargain.plugin.condition.db;

import gnnt.MEBS.timebargain.plugin.condition.Config;
import java.lang.reflect.Constructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaoFactory
{
  private static DaoFactory df;
  private ConditionDao cDao;
  static Log log = LogFactory.getLog(DaoFactory.class);
  
  public static DaoFactory getInstance()
  {
    if (df == null) {
      df = new DaoFactory();
    }
    return df;
  }
  
  public ConditionDao getConditionDao(Config paramConfig)
  {
    if (this.cDao == null) {
      try
      {
        if (paramConfig == null) {
          paramConfig = new Config();
        }
        String str = paramConfig.DAO_NAME;
        if (str != null)
        {
          Class localClass = Class.forName(str);
          Class[] arrayOfClass = { Config.class };
          Constructor localConstructor = localClass.getConstructor(arrayOfClass);
          Object[] arrayOfObject = { paramConfig };
          this.cDao = ((ConditionDao)localConstructor.newInstance(arrayOfObject));
          log.debug("Using " + str + ":" + this.cDao + " for Application...");
        }
        else
        {
          log.info("解析文件失败");
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return this.cDao;
      }
    }
    return this.cDao;
  }
}
