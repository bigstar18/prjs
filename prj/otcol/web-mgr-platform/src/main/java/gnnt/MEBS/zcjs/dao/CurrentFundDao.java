package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.CurrentFund;
import java.util.List;

public class CurrentFundDao
  extends DaoHelperImpl
{
  public List<CurrentFund> getList(QueryConditions paramQueryConditions, String paramString)
  {
    String str = "select * from (select a.firmid firmid,a.balance balance,a.frozenfunds totalfrozenfunds,a.lastbalance lastbalance,a.lastwarranty lastwarranty,a.settlemargin settlemargin, a.lastsettlemargin lastsettlemargin, b.moduleid  moduleid, nvl( b.frozenfunds,0) frozenfunds from f_firmfunds a, (select * from f_frozenfunds b where b.moduleid = " + paramString + ") b where a.firmid = b.firmid(+))";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new CurrentFund()));
  }
}
