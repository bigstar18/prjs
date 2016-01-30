package gnnt.MEBS.timebargain.mgr.service.systemMana;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogonManager
{
  public static Map<String, Object> getRMIConfig(String moduleId, DataSource ds)
  {
    Map map = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    Connection conn = null;
    Log logger = LogFactory.getLog(LogonManager.class);
    try {
      String sql = "";
      conn = ds.getConnection();
      sql = "select hostip,rmi_port from m_trademodule where moduleId='" + moduleId + "'";
      logger.debug(sql);
      state = conn.prepareStatement(sql);

      rs = state.executeQuery();
      if (rs.next())
      {
        map = new HashMap();
        map.put("host", rs.getString(1));
        logger.debug("ip:" + map.get("host"));
        map.put("port", Integer.valueOf(rs.getInt(2)));
        logger.debug("port:" + map.get("port"));
      }
      rs.close();
      rs = null;
      state.close();
      state = null;
    } catch (Exception e) {
      logger.error("getRMIConfig error!", e);
      try
      {
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
        logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
        logger.error(ex);
      }
    }
    return map;
  }
}