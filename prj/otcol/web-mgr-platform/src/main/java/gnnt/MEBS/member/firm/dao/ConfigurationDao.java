package gnnt.MEBS.member.firm.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.member.firm.unit.Configuration;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurationDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(ConfigurationDao.class);
  
  public Configuration getObject(String paramString)
  {
    String str = "select * from M_Configuration where key=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("---------sql:-------- " + str);
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Configuration()));
    if ((localList != null) && (localList.size() > 0)) {
      return (Configuration)localList.get(0);
    }
    return null;
  }
}
