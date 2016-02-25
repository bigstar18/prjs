package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import java.util.List;

public class FirmHoldDao
  extends DaoHelperImpl
{
  public List<?> firmHoldList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "select f.firmId firmId,f.name firmName,fh.commodityID commodityId,fh.bs_Flag bs_Flag,fh.evenprice evenprice,fh.holdMargin holdMargin,nvl(fh.FloatingLoss,0) floatingLoss,nvl((fh.holdQty+fh.gageQty),0) commodityQty from t_FirmHoldSum fh,m_Firm f where fh.firmId=f.firmId and fh.firmId in (select firmId from M_B_FirmAndBroker where brokerid='" + paramString + "')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    List localList = queryBySQL(str, arrayOfObject, paramPageInfo);
    return localList;
  }
}
