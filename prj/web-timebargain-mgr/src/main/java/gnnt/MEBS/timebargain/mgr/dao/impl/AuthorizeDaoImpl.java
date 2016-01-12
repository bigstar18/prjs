package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.AuthorizeDao;
import gnnt.MEBS.timebargain.mgr.model.authorize.OverdueHoldPosition;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("authorizeDao")
public class AuthorizeDaoImpl extends StandardDao
  implements AuthorizeDao
{
  public List holdPositionsList(String firmIds)
  {
    String sql = "select * from (select ag.*, (s.holdqty - s.FrozenQty) maycloseQty from (select a.firmid, a.customerid, a.commodityid, a.bs_flag, sum(a.holdqty) holdqty, sum(a.openqty) openqty, sum(a.gageqty) gageqty from T_HoldPosition a where 1 = 1 and remainday = 0 and a.deadline is not null group by a.firmid, a.customerid, a.commodityid, a.bs_flag) ag, t_customerholdsum s where ag.commodityid = s.commodityid and ag.customerid = s.customerid and ag.bs_flag = s.bs_flag and ag.holdqty > 0 and (s.holdqty - s.FrozenQty) > 0) where 1=1 and firmid in (" + 
      firmIds + ") " + 
      "order by firmid, commodityid, bs_flag";

    List list = queryBySql(sql);
    if ((list != null) && (list.size() > 0))
    {
      for (int i = 0; i < list.size(); i++) {
        Map map = (Map)list.get(i);
        String firmID = map.get("FIRMID").toString();
        String customerID = map.get("CUSTOMERID").toString();
        String commodityID = map.get("COMMODITYID").toString();
        String bs_flag = map.get("BS_FLAG").toString();

        long holdqty = Long.parseLong(map.get("HOLDQTY").toString());

        long specHoldqty = getSpecHoldqty(firmID, customerID, commodityID, bs_flag);
        long notradeqty = getNotradeqty(firmID, customerID, commodityID, bs_flag);

        holdqty = holdqty - specHoldqty - notradeqty;

        map.put("HOLDQTY", Long.valueOf(holdqty));
      }
    }

    return list;
  }

  public long getSpecHoldqty(String firmID, String customerID, String commodityID, String bs_flag)
  {
    long result = 0L;

    String sql = "select nvl(specHoldqty, 0) specHoldqty from (select h.firmid,h.customerid, h.commodityid, h.bs_flag,nvl(sum(s.frozenqty),0) specHoldqty from t_holdposition h join T_SpecFrozenHold s on h.a_holdno = s.a_holdno where h.remainday = 0 and h.deadline is not null and h.firmid = '" + 
      firmID + "' " + "and h.customerid = '" + 
      customerID + "' " + "and h.commodityid = '" + commodityID + 
      "' " + "and h.bs_flag = '" + bs_flag + "'" + 
      "group by h.firmid,h.customerid, h.commodityid, h.bs_flag)";

    List list = queryBySql(sql);
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      result = Long.parseLong(map.get("SPECHOLDQTY").toString());
    }

    return result;
  }

  public long getNotradeqty(String firmID, String customerID, String commodityID, String bs_flag)
  {
    long result = 0L;

    String sql = "select nvl(notradeqty, 0) notradeqty from (select o.firmid,o.customerid,o.commodityid,o.bs_flag,(nvl(sum(o.quantity),0) - nvl(sum(o.tradeqty),0)) notradeqty from t_orders o join t_commodity c on o.commodityid = c.commodityid where o.closeflag = 2 and  o.status in (1,2) and o.firmid = '" + 
      firmID + "' " + "and o.customerid = '" + 
      customerID + "' " + "and o.commodityid = '" + commodityID + 
      "' " + "and o.bs_flag != '" + bs_flag + "'" + 
      "group by o.firmid,o.customerid,o.commodityid,o.bs_flag)";

    List list = queryBySql(sql);
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      result = Long.parseLong(map.get("NOTRADEQTY").toString());
    }

    return result;
  }

  public long getSpecHoldqty(OverdueHoldPosition obj)
  {
    long result = 0L;

    String sql = "select nvl(specHoldqty, 0) specHoldqty from (select h.firmid,h.customerid, h.commodityid, h.bs_flag,nvl(sum(s.frozenqty),0) specHoldqty from t_holdposition h join T_SpecFrozenHold s on h.a_holdno = s.a_holdno where h.remainday = 0 and h.deadline is not null and h.firmid = '" + 
      obj.getFirmID() + "' " + "and h.customerid = '" + 
      obj.getCustomerID() + "' " + "and h.commodityid = '" + obj.getCommodityID() + 
      "' " + "and h.bs_flag = '" + obj.getBSFlag() + "'" + 
      "group by h.firmid,h.customerid, h.commodityid, h.bs_flag)";

    List list = queryBySql(sql);
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      result = Long.parseLong(map.get("SPECHOLDQTY").toString());
    }

    return result;
  }

  public long getNotradeqty(OverdueHoldPosition obj)
  {
    long result = 0L;

    String sql = "select nvl(notradeqty, 0) notradeqty from (select o.firmid,o.customerid,o.commodityid,o.bs_flag,(nvl(sum(o.quantity),0) - nvl(sum(o.tradeqty),0)) notradeqty from t_orders o join t_commodity c on o.commodityid = c.commodityid where o.closeflag = 2 and  o.status in (1,2) and o.firmid = '" + 
      obj.getFirmID() + "' " + "and o.customerid = '" + 
      obj.getCustomerID() + "' " + "and o.commodityid = '" + obj.getCommodityID() + 
      "' " + "and o.bs_flag != '" + obj.getBSFlag() + "'" + 
      "group by o.firmid,o.customerid,o.commodityid,o.bs_flag)";

    List list = queryBySql(sql);
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      result = Long.parseLong(map.get("NOTRADEQTY").toString());
    }

    return result;
  }
}