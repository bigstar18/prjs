package gnnt.MEBS.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Util
{
  public static String DB_TYPE = null;

  public static String convertDBStr(String dbString)
  {
    if (DB_TYPE == null) {
      getDB_TYPE();
    }

    if (DB_TYPE.equals("Oracle")) {
      return dbString;
    }
    if (DB_TYPE.equals("MSSQL")) {
      return ISOToGBK(dbString);
    }
    if (DB_TYPE.equals("Sybase")) {
      return dbString;
    }

    throw new RuntimeException("无效的数据库类型，convertDBStr()转换失败。");
  }

  public static String ISOToGBK(String isoString)
  {
    String target = null;
    if (isoString == null) {
      return null;
    }
    try
    {
      byte[] t = isoString.getBytes("ISO-8859-1");
      target = new String(t, "GB2312");
    }
    catch (UnsupportedEncodingException e) {
      System.out.println("error:ISOToGBK() fail !");
    }
    return target;
  }

  private static void getDB_TYPE() {
    String dbType = null;
    try {
      Properties p = new Configuration().getSection("MEBS.Database");
      dbType = p.getProperty("DBType");
      if (dbType == null)
        throw new RuntimeException("无法从MEBS.xml中读到当前数据库类型，getDB_TYPE()失败。");
    }
    catch (Exception e)
    {
      throw new RuntimeException("无法读到当前数据库类型，getDB_TYPE()失败。");
    }
    DB_TYPE = dbType;
  }
}