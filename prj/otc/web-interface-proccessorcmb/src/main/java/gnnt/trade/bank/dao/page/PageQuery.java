package gnnt.trade.bank.dao.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageQuery
{
  private static int getStartOfAnyPage(int pageNo, int pageSize)
  {
    int startIndex = (pageNo - 1) * pageSize + 1;
    if (startIndex < 1) {
      startIndex = 1;
    }
    return startIndex;
  }
  
  private static String intiQuerySQL(String sql, int startIndex, int pageSize)
  {
    StringBuffer querySQL = new StringBuffer();
    
    querySQL.append(
      "select * from (select my_table.*,rownum as my_rownum from(")
      .append(sql).append(") my_table where rownum<")
      .append(startIndex + pageSize).append(") where my_rownum>=")
      .append(startIndex);
    
    return querySQL.toString();
  }
  
  public static ResultSet executeQuery(Connection conn, PreparedStatement pst, ResultSet rs, String sql, int[] pageinfo)
    throws SQLException
  {
    try
    {
      if ((pageinfo == null) || (pageinfo.length < 4))
      {
        pst = conn.prepareStatement(sql);
        return pst.executeQuery();
      }
      if (pageinfo[2] <= 0) {
        pageinfo[2] = 10;
      }
      String countSQL = "select count(*) from ( " + sql + ") ";
      pst = conn.prepareStatement(countSQL);
      rs = pst.executeQuery();
      if (rs.next())
      {
        pageinfo[0] = rs.getInt(1);
        pageinfo[3] = (pageinfo[0] % pageinfo[2] > 0 ? pageinfo[0] / pageinfo[2] + 1 : pageinfo[0] / pageinfo[2]);
        if (pageinfo[1] > pageinfo[3]) {
          pageinfo[1] = pageinfo[3];
        }
      }
      else
      {
        pageinfo[0] = 0;
      }
      if (pageinfo[0] < 1) {
        return rs;
      }
      if (pageinfo[1] <= 0) {
        pageinfo[1] = 1;
      }
      rs.close();
      pst.close();
      
      int startIndex = getStartOfAnyPage(pageinfo[1], pageinfo[2]);
      sql = intiQuerySQL(sql, startIndex, pageinfo[2]);
      

      pst = conn.prepareStatement(sql);
      pst.setFetchSize(pageinfo[2]);
      rs = pst.executeQuery();
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
      throw sqle;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException(e.toString());
    }
    return rs;
  }
}
