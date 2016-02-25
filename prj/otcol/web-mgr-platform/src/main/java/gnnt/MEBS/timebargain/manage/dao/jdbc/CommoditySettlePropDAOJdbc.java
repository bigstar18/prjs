package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.CommoditySettlePropDAO;
import gnnt.MEBS.timebargain.manage.model.CommoditySettleProp;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class CommoditySettlePropDAOJdbc
  extends BaseDAOJdbc
  implements CommoditySettlePropDAO
{
  public void commoditySettlepropDelete(CommoditySettleProp paramCommoditySettleProp)
  {
    String str = "delete from t_a_commoditysettleprop where commodityid=? and sectionid=?";
    Object[] arrayOfObject = { paramCommoditySettleProp.getCommodityId(), Integer.valueOf(paramCommoditySettleProp.getSectionId()) };
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public List commoditySettlepropList(QueryConditions paramQueryConditions)
  {
    String str = "select * from t_a_commoditysettleprop";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public int commoditySettlePropAdd(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    int i = 0;
    String str = "delete from t_a_commoditysettleprop";
    getJdbcTemplate().update(str);
    try
    {
      int j;
      if ((paramArrayOfString1 != null) && (paramArrayOfString1.length > 0)) {
        for (j = 0; j < paramArrayOfString1.length; j++)
        {
          str = "insert into t_a_commoditysettleprop (commodityid, sectionid, modifytime) values ('" + paramArrayOfString1[j] + "', " + 1 + ", sysdate)";
          getJdbcTemplate().update(str);
        }
      }
      if ((paramArrayOfString2 != null) && (paramArrayOfString2.length > 0)) {
        for (j = 0; j < paramArrayOfString2.length; j++)
        {
          str = "insert into t_a_commoditysettleprop (commodityid, sectionid, modifytime) values ('" + paramArrayOfString2[j] + "', " + 2 + ", sysdate)";
          getJdbcTemplate().update(str);
        }
      }
      i = 1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = 2;
    }
    return i;
  }
}
