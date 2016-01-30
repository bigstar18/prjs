package gnnt.MEBS.timebargain.broker.dao.report;

import gnnt.MEBS.common.broker.dao.StandardDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("commReportDao")
public class CommReportDao extends StandardDao
{
  public List getCommodityList(String paramString)
  {
    String str = "select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID " + paramString;
    return queryBySql(str);
  }

  public List getBrokerageList(String paramString)
  {
    String str = "select brokerageId from br_brokerage t  where brokerId= '" + paramString + "' order by brokerageId asc";
    return queryBySql(str);
  }
}