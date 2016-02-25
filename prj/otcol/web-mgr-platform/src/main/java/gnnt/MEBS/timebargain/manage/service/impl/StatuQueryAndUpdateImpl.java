package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.StatuQueryAndUpdateDAO;
import gnnt.MEBS.timebargain.manage.service.StatuQueryAndUpdate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;

public class StatuQueryAndUpdateImpl
  extends BaseManager
  implements StatuQueryAndUpdate
{
  private StatuQueryAndUpdateDAO dao;
  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
  
  public void setStatuQueryAndUpdateDAO(StatuQueryAndUpdateDAO paramStatuQueryAndUpdateDAO)
  {
    this.dao = paramStatuQueryAndUpdateDAO;
  }
  
  public boolean getStatus()
  {
    this.log.debug("Entering 'getStatus' method");
    List localList = this.dao.getStatus();
    boolean bool = true;
    if (localList.size() != 0)
    {
      Map localMap = (Map)localList.get(0);
      this.log.debug("(map.get('STATUS'):" + localMap.get("STATUS"));
      if ("Y".equals(localMap.get("STATUS")))
      {
        bool = true;
      }
      else
      {
        Timestamp localTimestamp1 = (Timestamp)localMap.get("SETTINGDATE");
        Timestamp localTimestamp2 = new Timestamp(System.currentTimeMillis());
        this.log.debug("now:" + localTimestamp2);
        this.log.debug("dateInDB:" + localTimestamp1);
        if (!SDF.format(localTimestamp2).equals(SDF.format(localTimestamp1))) {
          bool = true;
        } else {
          bool = false;
        }
      }
    }
    this.log.debug("checkResult:" + bool);
    return bool;
  }
  
  public void updateStatus(String paramString)
    throws Exception
  {
    this.log.debug("Entering 'updateStatus' method");
    this.log.debug("state:" + paramString);
    this.dao.updateStatus(paramString);
  }
}
