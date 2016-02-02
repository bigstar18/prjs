package gnnt.MEBS.base.test;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.util.SysData;
import gnnt.MEBS.base.util.Utils;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test
{
  public static void main(String[] args)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select * from currentdata where (code='SYS' or code in (select commodity_id from commodity where status='N')) and time>? order by time";
    Date lastTime = Utils.parseDate(new SimpleDateFormat("yyyyMMdd HHmmss"), "20080416 235900");
    Object[] params = {
      lastTime };
    
    int[] types = {
      93 };
    
    List list = dao.queryBySQL(sql, params, types, null);
    System.out.println("---:" + list.size());
  }
}
