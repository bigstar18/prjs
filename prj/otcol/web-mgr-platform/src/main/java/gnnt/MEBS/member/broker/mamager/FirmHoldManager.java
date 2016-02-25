package gnnt.MEBS.member.broker.mamager;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.dao.DaoHelper;
import gnnt.MEBS.finance.base.util.SysData;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FirmHoldManager
{
  private static final transient Log logger = LogFactory.getLog(FirmHoldManager.class);
  
  public static List<?> firmHoldList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "select f.firmId firmId,f.name firmName,fh.commodityID commodityId,fh.bs_Flag bs_Flag,fh.evenprice evenprice,fh.holdMargin holdMargin,nvl(fh.FloatingLoss,0) floatingLoss,nvl((fh.holdQty+fh.gageQty),0) commodityQty from t_FirmHoldSum fh,m_Firm f where fh.firmId=f.firmId and fh.firmId in (select firmId from M_B_FirmAndBroker where brokerid='" + paramString + "')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    logger.debug("sql:" + str);
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    List localList = localDaoHelper.queryBySQL(str, arrayOfObject, paramPageInfo);
    return localList;
  }
}
