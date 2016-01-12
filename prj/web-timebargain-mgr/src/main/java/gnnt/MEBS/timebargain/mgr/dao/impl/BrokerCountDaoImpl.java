package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.BrokerCountDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("brokerCountDao")
public class BrokerCountDaoImpl extends StandardDao
  implements BrokerCountDao
{
  public List brokerFundsTable(String brokerID, String brokerName)
  {
    String sql = "select q.*, nvl((q.balance + q.clearassure + q.clearmargin + q.clearfl + q.PL + q.close_PL - q.TradeFee),0) quanyi from (select br1.brokerid, br1.name, sum(a.VirtualFunds) VirtualFunds, sum(a.runtimefl) runtimefl, sum(a.clearfl) clearfl, sum(a.runtimemargin) runtimemargin, sum(a.clearmargin) clearmargin, sum(a.runtimeassure) runtimeassure, sum(a.clearassure) clearassure, sum(a.runtimesettlemargin) runtimesettlemargin, sum(a.clearsettlemargin) clearsettlemargin, sum(f.balance) balance, sum(f.frozenfunds) frozenfunds, sum(f.lastbalance) lastbalance, sum(nvl(tt.TradeFee, 0)) TradeFee, sum(nvl(t_Firmholdsum.PL, 0)) PL, sum(nvl(nvl(balance, 0) + nvl((-1) * FrozenFunds, 0), 0)) nowLeaveBalance, sum(nvl(d.close_PL, 0)) close_PL, sum(a.MaxOverdraft) MaxOverdraft from br_broker br1, br_firmandbroker br2, T_Firm a, F_FirmFunds f, M_firm m, (select sum(t.TradeFee) TradeFee, t.firmID firmID from T_Trade t group by firmID) tt, (select firmID, nvl(sum(Close_PL), 0) close_PL from T_Trade group by firmID) d, (select firmID, nvl(sum(floatingloss), 0) PL from t_Firmholdsum group by firmID) t_Firmholdsum where br1.brokerid = br2.brokerid and br2.firmid = a.firmid and br2.firmid = f.firmid and br2.firmid = m.firmid and br2.firmid = tt.FirmID(+) and br2.firmid = d.firmID(+) and br2.firmid = t_Firmholdsum.firmID(+)";

    if ((brokerID != null) && 
      (!"".equals(brokerID.trim()))) {
      sql = sql + "and br1.brokerid = '" + brokerID + "'";
    }
    if ((brokerName != null) && 
      (!"".equals(brokerName.trim()))) {
      sql = sql + "and br1.name = '" + brokerName + "'";
    }

    sql = sql + "group by br1.brokerid,br1.name order by nowLeaveBalance ASC) q ";
    return queryBySql(sql);
  }

  public List brokerIndentTable(String brokerID, String commodityId, String flag)
  {
    String sql = " select z.*, (case when z1.hp > 0 then round(z1.hp1 / z1.hp, 2) else 0 end) hp, nvl(z2.lastholeqty, 0) lastholeqty, nvl((z.HoldQty - z2.lastholeqty),0) holdce from (select a.CommodityID, a.BS_Flag, sum(a.HoldQty) HoldQty, sum(a.HoldFunds) HoldFunds, sum(a.FloatingLoss) FloatingLoss, sum(a.HoldMargin) HoldMargin, sum(a.HoldAssure) HoldAssure, sum(a.gageqty) GageQty, m.BrokerID BrokerID, m.name name, sum(nvl((a.HoldQty + a.GageQty), 0)) HoldQtyGageQty, to_char(sysdate, 'yyyy-MM-dd') ClearDate from T_FirmHoldSum a, Br_Broker m, Br_FirmAndBroker t where t.firmID = a.firmID and t.BrokerID = m.BrokerID group by m.BrokerID, m.name, a.CommodityID, a.BS_Flag order by m.BrokerID) z, (select to_char(sysdate, 'yyyy-MM-dd') ClearDate, m.BrokerID, a.commodityid, a.BS_Flag, nvl(sum(a.HoldQty * a.Price), 0) hp1, nvl(sum(a.HoldQty), 0) hp from T_HoldPosition a, Br_FirmAndBroker m where a.firmID = m.firmID group by m.BrokerID, a.commodityid, a.BS_Flag) z1, (select m.BrokerID, a.commodityid, a.BS_Flag, nvl(sum(a.HoldQty), 0) lastholeqty from T_H_FIRMHOLDSUM a, Br_FirmAndBroker m where a.firmID = m.firmID and a.cleardate = (select max(cleardate) from T_H_FIRMHOLDSUM) ";

    sql = sql + "group by m.BrokerID, a.commodityid, a.BS_Flag) z2 where z.BrokerID = z1.BrokerID(+) and z.commodityid = z1.CommodityID(+) and z.BS_Flag = z1.BS_Flag(+) and z.ClearDate = z1.ClearDate(+) and z.BrokerID = z2.BrokerID(+) and z.commodityid = z2.CommodityID(+) and z.BS_Flag = z2.BS_Flag(+) ";

    if ((!"".equals(brokerID)) && (brokerID != null)) {
      sql = sql + "and z.BrokerID = '" + brokerID + "' ";
    }

    if ((!"".equals(commodityId)) && (commodityId != null)) {
      sql = sql + "and z.CommodityID = '" + commodityId + "' ";
    }

    if ((!"".equals(flag)) && (flag != null)) {
      sql = sql + "and z.BS_Flag = '" + flag + "' ";
    }

    return queryBySql(sql);
  }

  public List historyBrokerIndentTable(String brokerID, String commodityId, String flag, String clearDate)
  {
    String sql = " select z.*, (case when z1.hp > 0 then round(z1.hp1 / z1.hp, 2) else 0 end) hp, nvl(z2.lastholeqty, 0) lastholeqty, nvl((z.HoldQty - z2.lastholeqty),0) holdce from (select a.CommodityID, a.BS_Flag, sum(a.HoldQty) HoldQty, sum(a.HoldFunds) HoldFunds, sum(a.FloatingLoss) FloatingLoss, sum(a.HoldMargin) HoldMargin, sum(a.HoldAssure) HoldAssure, sum(a.gageqty) GageQty, m.BrokerID BrokerID, m.name name, sum(nvl((a.HoldQty + a.GageQty), 0)) HoldQtyGageQty, a.ClearDate from T_H_FIRMHOLDSUM a, Br_Broker m, Br_FirmAndBroker t where t.firmID = a.firmID and t.BrokerID = m.BrokerID and a.ClearDate = to_date('" + 
      clearDate + "', 'yyyy-MM-dd') " + 
      "group by m.BrokerID, m.name, a.CommodityID, a.BS_Flag, a.ClearDate " + 
      "order by m.BrokerID) z, " + 
      "(select a.ClearDate, " + 
      "m.BrokerID, " + 
      "a.commodityid, " + 
      "a.BS_Flag, " + 
      "nvl(sum(a.HoldQty * a.Price), 0) hp1, " + 
      "nvl(sum(a.HoldQty), 0) hp " + 
      "from T_H_HOLDPOSITION a, Br_FirmAndBroker m " + 
      "where a.firmID = m.firmID " + 
      "and a.ClearDate = to_date('" + clearDate + "', 'yyyy-MM-dd') " + 
      "group by a.ClearDate, m.BrokerID, a.commodityid, a.BS_Flag) z1, " + 
      "(select m.BrokerID, " + 
      "a.commodityid, " + 
      "a.BS_Flag, " + 
      "nvl(sum(a.HoldQty), 0) lastholeqty " + 
      "from T_H_FIRMHOLDSUM a, Br_FirmAndBroker m " + 
      "where a.firmID = m.firmID " + 
      "and a.cleardate = (select max(cleardate) from T_H_FIRMHOLDSUM " + 
      "where cleardate < to_date('" + clearDate + "', 'yyyy-MM-dd')) ";

    sql = sql + "group by m.BrokerID, a.commodityid, a.BS_Flag) z2 where z.BrokerID = z1.BrokerID(+) and z.commodityid = z1.CommodityID(+) and z.BS_Flag = z1.BS_Flag(+) and z.ClearDate = z1.ClearDate(+) and z.BrokerID = z2.BrokerID(+) and z.commodityid = z2.CommodityID(+) and z.BS_Flag = z2.BS_Flag(+) ";

    if ((!"".equals(brokerID)) && (brokerID != null)) {
      sql = sql + "and z.BrokerID = '" + brokerID + "' ";
    }

    if ((!"".equals(commodityId)) && (commodityId != null)) {
      sql = sql + "and z.CommodityID = '" + commodityId + "' ";
    }

    if ((!"".equals(flag)) && (flag != null)) {
      sql = sql + "and z.BS_Flag = '" + flag + "' ";
    }

    if ((!"".equals(clearDate)) && (clearDate != null)) {
      sql = sql + "and z.ClearDate = to_date('" + clearDate + "', 'yyyy-MM-dd') ";
    }

    return queryBySql(sql);
  }

  public List brokerTradeTable(String brokerID, String brokerName, String flag, String tradeType, String commodityId, String orderType)
  {
    String sql = "select a.brokerid, a.name, t.commodityid, t.BS_Flag, t.OrderType, t.TradeType, nvl(sum(t.quantity), 0) quantity, nvl(sum(t.Close_PL), 0) Close_PL, nvl(sum(t.TradeFee), 0) TradeFee, nvl(sum(t.CloseAddedTax), 0) CloseAddedTax from br_broker a, br_firmandbroker b, t_trade  t where a.brokerid=b.brokerid and b.firmid=t.firmid(+) and t.quantity>0 ";

    if ((!"".equals(brokerID)) && (brokerID != null)) {
      sql = sql + "and a.brokerid = '" + brokerID + "' ";
    }

    if ((!"".equals(brokerName)) && (brokerName != null)) {
      sql = sql + "and a.name = '" + brokerName + "' ";
    }

    if ((!"".equals(flag)) && (flag != null)) {
      sql = sql + "and t.BS_Flag = '" + flag + "' ";
    }

    if ((!"".equals(tradeType)) && (tradeType != null)) {
      sql = sql + "and t.TradeType = '" + tradeType + "' ";
    }

    if ((!"".equals(commodityId)) && (commodityId != null)) {
      sql = sql + "and t.commodityid = '" + commodityId + "' ";
    }

    if ((!"".equals(orderType)) && (orderType != null)) {
      sql = sql + "and t.OrderType = '" + orderType + "' ";
    }

    sql = sql + "group by a.brokerid,a.name,t.commodityid,t.BS_Flag,t.OrderType,t.TradeType order by a.brokerid ";
    return queryBySql(sql);
  }

  public List historyBrokerTradeTable(String brokerID, String brokerName, String flag, String tradeType, String commodityId, String orderType, String beginDate, String endDate)
  {
    String sql = "select a.brokerid, a.name, t.commodityid, t.BS_Flag, t.OrderType, t.TradeType, nvl(sum(t.quantity), 0) quantity, nvl(sum(t.Close_PL), 0) Close_PL, nvl(sum(t.TradeFee), 0) TradeFee, nvl(sum(t.CloseAddedTax), 0) CloseAddedTax from br_broker a, br_firmandbroker b, T_H_TRADE  t where a.brokerid=b.brokerid and b.firmid=t.firmid(+) and t.quantity>0 ";

    if ((!"".equals(brokerID)) && (brokerID != null)) {
      sql = sql + "and a.brokerid = '" + brokerID + "' ";
    }

    if ((!"".equals(brokerName)) && (brokerName != null)) {
      sql = sql + "and a.name = '" + brokerName + "' ";
    }

    if ((!"".equals(flag)) && (flag != null)) {
      sql = sql + "and t.BS_Flag = '" + flag + "' ";
    }

    if ((!"".equals(tradeType)) && (tradeType != null)) {
      sql = sql + "and t.TradeType = '" + tradeType + "' ";
    }

    if ((!"".equals(commodityId)) && (commodityId != null)) {
      sql = sql + "and t.commodityid = '" + commodityId + "' ";
    }

    if ((!"".equals(orderType)) && (orderType != null)) {
      sql = sql + "and t.OrderType = '" + orderType + "' ";
    }

    if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
      sql = sql + "and t.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
        "and t.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
    }
    sql = sql + "group by a.brokerid,a.name,t.commodityid,t.BS_Flag,t.OrderType,t.TradeType order by a.brokerid ";
    return queryBySql(sql);
  }
}