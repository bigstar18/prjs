package cn.com.agree.eteller.generic.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import org.apache.commons.io.FileUtils;

public class DBGen
{
  static void initDB()
    throws Exception
  {
    String sql = FileUtils.readFileToString(new File("./sql/create_table.sql"));
    Connection conn = H2BDUtil.getConnection();
    conn.createStatement().execute(sql);
    conn.close();
  }
  
  public static void main(String[] args)
    throws Exception
  {}
}
