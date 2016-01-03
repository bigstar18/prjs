package gnnt.mebsv.hqservice.dao.rmidao.impl;

import gnnt.mebsv.hqservice.dao.rmidao.HQRMIDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQRMIDAOJdbc extends HQRMIDAO
{
  private final transient Log log = LogFactory.getLog(HQRMIDAOJdbc.class);

  public Map getHQRMI(String paramString)
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    HashMap localHashMap = new HashMap();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select HOSTIP,\tPORT from C_TRADEMODULE ").append("  where moduleID=? ");
    try
    {
      this.conn = getConn();
      if (paramString == null)
        paramString = "15";
      localPreparedStatement = this.conn.prepareStatement(localStringBuffer.toString());
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localHashMap.put("host", localResultSet.getString(1));
        localHashMap.put("port", localResultSet.getString(2));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error(localException);
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, this.conn);
    }
    return localHashMap;
  }
}