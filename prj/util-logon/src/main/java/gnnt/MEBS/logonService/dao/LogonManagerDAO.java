package gnnt.MEBS.logonService.dao;

import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.po.ModuleAndAUPO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class LogonManagerDAO extends BaseDAOJdbc
{
  public LogonConfigPO getLogonConfigByID(int paramInt)
  {
    String str = "select * from L_AUConfig where configID=?";
    Object[] arrayOfObject = { Integer.valueOf(paramInt) };
    this.logger.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++)
      this.logger.debug("params[" + i + "]" + arrayOfObject[i]);
    List localList = getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new LogonConfigPO()));
    if ((localList != null) && (localList.size() > 0))
      return (LogonConfigPO)localList.get(0);
    return null;
  }

  public List<LogonConfigPO> getLogonConfigList(String paramString)
  {
    String str = "select * from L_AUConfig where Sysname=? and HostIP is not null";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++)
      this.logger.debug("params[" + i + "]" + arrayOfObject[i]);
    return getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new LogonConfigPO()));
  }

  public List<ModuleAndAUPO> getModuleAndAUList(String paramString)
  {
    String str = "select m.* from L_AUConfig a,L_ModuleAndAU m where m.ConfigID=a.ConfigID and a.Sysname=? order by m.ModuleID,m.ConfigID";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++)
      this.logger.debug("params[" + i + "]" + arrayOfObject[i]);
    return getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new ModuleAndAUPO()));
  }

  public List<ModuleAndAUPO> getModuleAndAUList(int paramInt, String paramString)
  {
    String str = "select m.* from L_AUConfig a,L_ModuleAndAU m where m.ConfigID=a.ConfigID and a.Sysname=? and m.ModuleID=? order by m.ConfigID";
    Object[] arrayOfObject = { paramString, Integer.valueOf(paramInt) };
    this.logger.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++)
      this.logger.debug("params[" + i + "]" + arrayOfObject[i]);
    return getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new ModuleAndAUPO()));
  }
}