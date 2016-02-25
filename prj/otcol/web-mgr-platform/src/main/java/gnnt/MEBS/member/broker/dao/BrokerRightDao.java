package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.model.BrokerMenu;
import gnnt.MEBS.member.broker.model.BrokerRight;
import java.util.List;

public class BrokerRightDao
  extends DaoHelperImpl
{
  public List<BrokerMenu> getBrokerMenu(QueryConditions paramQueryConditions)
  {
    String str = "select * from m_b_brokermenu where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + " order by menuid";
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new BrokerMenu()));
  }
  
  public List<BrokerRight> getBrokerRight(QueryConditions paramQueryConditions)
  {
    String str = "select * from m_b_broker_right where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new BrokerRight()));
  }
  
  public int getBrokerRightCount(String paramString)
  {
    String str = "select count(*) from m_b_broker_right where brokerid = ? ";
    Object[] arrayOfObject = { paramString };
    return queryForInt(str, arrayOfObject);
  }
  
  public void deleteBrokerRight(String paramString)
  {
    String str = "delete from m_b_broker_right where brokerid = ?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void addBrokerRight(String paramString, Integer paramInteger)
  {
    String str = "insert into m_b_broker_right (brokerid,rightid) values(?,?)";
    Object[] arrayOfObject = { paramString, paramInteger };
    int[] arrayOfInt = { 12, 4 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
