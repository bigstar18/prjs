package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.member.broker.model.ModuleConfiguration;
import java.util.List;

public class ModuleConfigurationDao
  extends DaoHelperImpl
{
  public ModuleConfiguration getObject(String paramString)
  {
    ModuleConfiguration localModuleConfiguration = null;
    String str = "select * from M_b_ModuleConfiguration where moduleId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new ModuleConfiguration()));
    if (localList.size() > 0) {
      localModuleConfiguration = (ModuleConfiguration)localList.get(0);
    }
    return localModuleConfiguration;
  }
}
