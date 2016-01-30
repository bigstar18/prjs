package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.CommodityIdDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("commodityDao")
public class CommodityIdDaoImpl extends StandardDao
  implements CommodityIdDao
{
  public List commodityIdList(String isDesc)
  {
    String sql = "select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID " + isDesc;
    return queryBySql(sql);
  }

  public List firmIdList(String isDesc)
  {
    String sql = "select firmId from t_firm order by firmId " + isDesc;
    return queryBySql(sql);
  }
}