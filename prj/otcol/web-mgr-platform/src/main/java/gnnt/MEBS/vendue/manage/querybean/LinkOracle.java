package gnnt.MEBS.vendue.manage.querybean;

import gnnt.MEBS.vendue.manage.bean.DBBean;
import gnnt.MEBS.vendue.manage.servlet.Right;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LinkOracle
{
  public String JNDI = null;
  
  public boolean judgeChild(String paramString)
  {
    ResultSet localResultSet = null;
    DBBean localDBBean = new DBBean(this.JNDI);
    boolean bool1 = false;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select count(id) as n from v_accessright where parentid=" + paramString + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if ((localResultSet.next()) && (localResultSet.getInt("n") > 0)) {
        bool1 = true;
      }
      localResultSet.close();
      localDBBean.close();
      bool2 = bool1;
      return bool2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (SQLException localSQLException3) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public ArrayList queryChildTree(String paramString)
  {
    ResultSet localResultSet = null;
    DBBean localDBBean = new DBBean(this.JNDI);
    ArrayList localArrayList = new ArrayList();
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select * from v_accessright where parentid=" + paramString + " order by id asc");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new Right();
        ((Right)localObject1).setMarknum(localResultSet.getLong("marknum"));
        ((Right)localObject1).setId(localResultSet.getString("id"));
        ((Right)localObject1).setParentId(localResultSet.getString("parentid"));
        ((Right)localObject1).setDescription(localResultSet.getString("description"));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (SQLException localSQLException3) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
}
