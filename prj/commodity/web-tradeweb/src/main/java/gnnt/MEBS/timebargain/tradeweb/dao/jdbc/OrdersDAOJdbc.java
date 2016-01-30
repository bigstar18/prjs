package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.tradeweb.dao.OrdersDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class OrdersDAOJdbc extends BaseDAOJdbc implements OrdersDAO {
	private Log log = LogFactory.getLog(OrdersDAOJdbc.class);

	public List commodity_query(Orders orders) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.BreedTradeMode,(a.MarginRate_B+a.MarginAssure_B) MarginRate_B,(a.MarginRate_S+a.MarginAssure_S) MarginRate_S")

		.append(" from T_Commodity a ,T_A_Breed b").append(" where a.BreedID = b.BreedID AND a.MarketDate<=sysdate and a.Status<>1 ");

		Object[] params = null;
		List lst = new ArrayList();
		if (!StringUtils.isEmpty(orders.getCommodityID())) {
			sb.append("and a.CommodityID=? ");
			lst.add(orders.getCommodityID());
		}
		sb.append(" order by a.CommodityID ");
		params = lst.toArray();
		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	public List holding_query(Orders orders, Privilege prvlg, SortCondition sc) {
		String sql = "select FloatingProfitSubTax,FloatingLossComputeType,GageMode from T_A_Market ";

		List list = getJdbcTemplate().queryForList(sql);
		Map map = (Map) list.get(0);

		int flag = 0;

		int type = ((BigDecimal) map.get("FloatingLossComputeType")).intValue();

		int gageMode = ((BigDecimal) map.get("GageMode")).intValue();

		String sql2 = "select Status from T_SystemStatus";
		int status = getJdbcTemplate().queryForInt(sql2);
		StringBuffer sb = new StringBuffer();
		String condition = "";
		if (type == 4) {
			if ((status != 3) && (status != 10)) {
				condition = "qc.LastPrice";
			} else {
				condition = "qc.Price";
			}
		} else {
			condition = "qc.Price";
		}
		String condition2 = "";
		if (gageMode == 0) {
			String conditionGageFunds = " decode(s.gageqty,0,0, ( select nvl(sum(price*gageqty),0) from t_holdposition t where t.customerid=cmdty.customerid and t.commodityid = cmdty.commodityid and t.bs_flag = 2 and gageqty>0 ) )";
			condition2 = " case when s.holdQty>0 then (s.holdFunds-(" + conditionGageFunds
					+ ")*qc.ContractFactor)/(s.holdQty*qc.ContractFactor) else 0 end ";
		} else if (gageMode == 1) {
			condition2 = "s.EvenPrice";
		}
		sb.append(
				"select cmdty.commodityid commodityid,cmdty.customerid customerid,b.holdqty buyQty,b.evenprice buyEvenPrice,(s.holdqty+s.gageqty) sellQty,s.gageqty gageqty,s.evenprice sellEvenPrice,")
				.append("nvl(b.holdmargin,0)+nvl(s.holdmargin,0) holdmargin, ").append("nvl(b.holdqty-b.frozenQty,0) buyAvailQty,")
				.append("nvl(s.holdqty-s.frozenQty,0) sellAvailQty,")
				.append("((case when (qc.MarginRate_B = -1 or (qc.MarginAlgr=1 and qc.MarginRate_B >= 1)) then 0  else nvl(FN_T_ComputeFPSubTax(b.EvenPrice,")
				.append(condition).append(",b.HoldQty, qc.ContractFactor, 1,qc.addedtax,").append(flag).append("), 0) end )")
				.append("+(case when(qc.MarginRate_S = -1 or (qc.MarginAlgr=1 and qc.MarginRate_S >= 1)) then 0 else nvl(FN_T_ComputeFPSubTax( "
						+ condition2 + " , ")
				.append(condition);

		String conditionSellHoldQty = null;
		if (gageMode == 0) {
			conditionSellHoldQty = "s.HoldQty";
		} else if (gageMode == 1) {
			conditionSellHoldQty = "s.HoldQty+s.gageqty";
		}
		sb.append("," + conditionSellHoldQty + ", qc.ContractFactor, 2,qc.addedtax,").append(flag).append("), 0)end )) bsPL ").append(", ")
				.append("(nvl(FN_T_ComputeFPSubTax(b.EvenPrice,decode(qc.CurPrice,0,qc.lastprice,qc.CurPrice),b.HoldQty, qc.ContractFactor, 1,qc.addedtax,")
				.append(flag).append("), 0) ")
				.append("+nvl(FN_T_ComputeFPSubTax( " + condition2 + " ,decode(qc.CurPrice,0,qc.lastprice,qc.CurPrice)," + conditionSellHoldQty
						+ ", qc.ContractFactor, 2,qc.addedtax,")
				.append(flag).append("), 0)) lastestPL ").append(" From ")
				.append("(select distinct firmid,customerid,commodityid from t_customerholdsum where firmid=?) cmdty,")
				.append("(select commodityid,customerid,holdqty,FrozenQty,holdmargin,evenprice from t_customerholdsum where firmid=? and bs_flag=1) b,")
				.append("(select commodityid,customerid,holdqty,gageqty,FrozenQty,holdmargin,evenprice,holdfunds from t_customerholdsum where firmid=? and bs_flag=2) s,")
				.append("(select q.commodityid,q.price,q.CurPrice,c.lastprice,c.contractfactor,c.addedtax,c.MarginAlgr MarginAlgr,c.MarginRate_B MarginRate_B,c.MarginRate_S MarginRate_S from T_Quotation q, T_Commodity c where q.commodityid=c.commodityid) qc ")
				.append("where cmdty.commodityid=qc.commodityid and cmdty.commodityid=b.commodityid(+) and cmdty.customerid=b.customerid(+) and ")
				.append("cmdty.commodityid=s.commodityid(+) and cmdty.customerid=s.customerid(+) and cmdty.firmid=?");
		Object[] params = null;

		List lst = new ArrayList();
		String firmID = prvlg.getFirmId();
		for (int i = 0; i < 4; i++) {
			lst.add(firmID);
		}
		if (!StringUtils.isEmpty(orders.getCommodityID())) {
			sb.append("and cmdty.CommodityID=? ");
			lst.add(orders.getCommodityID());
		}
		params = lst.toArray();

		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	public List tradequery(long lastTradeNo) {
		String sql = "select a.*,b.TraderID from T_Trade a, (select TraderID,a_orderno  from  t_orders ) b Where a.A_TradeNo>? and  a.A_OrderNo=b.A_OrderNo order by a.A_TradeNo";

		List linked = new LinkedList();
		Object[] params = null;
		params = new Object[] { Long.valueOf(lastTradeNo) };
		linked = getJdbcTemplate().query(sql, params, new TradeRowMapper());
		return linked;
	}

	public List getFirmList() {
		String sql = "Select FirmID from T_Firm ";
		return getJdbcTemplate().queryForList(sql);
	}

	public List firm_info(Privilege prvg) {
		List firmList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.lastbalance,a.balance,")
				.append("nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('11001','11003')),0) inAmount,")
				.append("nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('11002','11004')),0) outAmount,")
				.append("nvl((select sum(close_pl) from t_trade where firmid=b.firmid and close_pl is not null),0) close_pl,")
				.append("nvl((select sum(tradefee) from t_trade where firmid=b.firmid),0) tradefee,")
				.append("b.ClearMargin,b.clearfl,b.runtimemargin,b.runtimefl,b.ClearAssure,b.MaxOverdraft,b.RuntimeSettleMargin,")
				.append("nvl((select sum(frozenfunds - unfrozenfunds) from t_orders where firmid = b.firmid),0) orderFrozen,")
				.append("(a.frozenfunds-nvl(c.frozenfunds,0)) otherFrozen,(a.balance - a.frozenfunds) usefulFund,b.virtualfunds,nvl((select sum(floatingloss) from t_Firmholdsum where firmid = b.firmid),0) PL ")
				.append("from F_FIRMFUNDS a,T_Firm b, (select firmid,frozenfunds from f_Frozenfunds where moduleid='15' and firmID=?) c ")
				.append("where a.firmid = b.firmid and a.firmid = c.firmid(+) and b.firmID = ? ");
		Object[] params = null;
		params = new Object[] { prvg.getFirmId(), prvg.getFirmId() };

		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		Map map1 = getJdbcTemplate().queryForMap(sb.toString(), params);

		String ids = null;

		String sql = "select OperateCode from T_Trader where TraderID = ?";
		List list = getJdbcTemplate().queryForList(sql, new Object[] { prvg.getTraderID() });
		if ((list != null) && (list.size() > 0)) {
			Map map = (Map) list.get(0);
			String id = (String) map.get("OperateCode");
			if (id != null) {
				ids = id;
			} else {
				ids = prvg.getIds();
			}
		} else {
			ids = prvg.getIds();
		}
		map1.put("FirmID", prvg.getFirmId());
		map1.put("Name", prvg.getFirmName());

		map1.put("OperateCode", ids);
		firmList.add(map1);

		return firmList;
	}

	public List my_weekorder_query(Orders orders, Privilege prvlg, SortCondition sc) {
		return order_query(orders, null, prvlg, sc);
	}

	public List my_order_query(Orders orders, Privilege prvlg, SortCondition sc) {
		return order_query(orders, " and a.Status in(1,2) ", prvlg, sc);
	}

	public List my_order_query(Orders orders, Privilege prvlg) {
		StringBuffer sb = new StringBuffer();
		Object[] params = null;
		sb.append("select a.*,(a.Quantity-a.TradeQty) notTradeQty ").append("from T_Orders a ").append("where OrderType <> 4 and a.Status in(1,2)");
		String type = prvlg.getTraderStatus();
		if (orders.getA_OrderNo() != null) {
			sb.append("and A_OrderNo=? ");
			params = new Object[] { orders.getA_OrderNo() };
		} else {
			List lst = new ArrayList();
			if (!StringUtils.isEmpty(orders.getTraderID())) {
				if ("A".equals(type)) {
					sb.append(" and firmid =?");
					lst.add(prvlg.getFirmId());
				} else {
					sb.append(" and TraderID=?");
					lst.add(prvlg.getTraderID());
				}
			}
			if (!StringUtils.isEmpty(orders.getCommodityID())) {
				sb.append("and CommodityID=? ");
				lst.add(orders.getCommodityID());
			}
			if (orders.getBS_Flag() != null) {
				sb.append("and BS_Flag=? ");
				lst.add(orders.getBS_Flag());
			}
			params = lst.toArray();
		}
		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	private List order_query(Orders orders, String tjStr, Privilege prvlg, SortCondition sc) {
		StringBuffer sb = new StringBuffer();
		Object[] params = null;

		String startNum = sc.getStartNu();

		String reccnt = sc.getReccnt();

		String sortfLD = sc.getSortfLD();

		String isdesc = sc.getIsdesc() == 0 ? "asc" : "desc";
		if ((!"0".equals(startNum)) && (!"".equals(startNum))) {
			this.log.debug("==> 分页SQL,startNum=" + startNum);
			sb.append("select b.rownum r,b.* from ( select a.*,(a.Quantity-a.TradeQty) notTradeQty ").append("from T_Orders a ")
					.append("where OrderType <> 4 ");
			String type = prvlg.getTraderStatus();
			if (!StringUtils.isEmpty(tjStr)) {
				sb.append(tjStr).append(" ");
			}
			if (orders.getA_OrderNo() != null) {
				sb.append("and A_OrderNo=? ");
				params = new Object[] { orders.getA_OrderNo() };
			} else {
				List lst = new ArrayList();
				if (!StringUtils.isEmpty(orders.getTraderID())) {
					if ("A".equals(type)) {
						sb.append(" and firmid =?");
						lst.add(prvlg.getFirmId());
					} else {
						sb.append(" and TraderID=?");
						lst.add(prvlg.getTraderID());
					}
				}
				if (!StringUtils.isEmpty(orders.getCommodityID())) {
					sb.append("and CommodityID=? ");
					lst.add(orders.getCommodityID());
				}
				if (orders.getBS_Flag() != null) {
					sb.append("and BS_Flag=? ");
					lst.add(orders.getBS_Flag());
				}
				if (!"queryAll".equalsIgnoreCase(orders.getUpdateTime())) {
					sb.append(" and UpdateTime>? ");
					lst.add(new Timestamp(Long.parseLong(orders.getUpdateTime())));
				}
				params = lst.toArray();
			}
			sb.append(" order by ").append(" " + sortfLD).append(" " + isdesc + ") b where rownum<=").append(reccnt).append(" and r>")
					.append(startNum);
		} else {
			this.log.debug("==> 不分页SQL");
			sb.append("select a.*,(a.Quantity-a.TradeQty) notTradeQty ").append("from T_Orders a ").append("where OrderType <> 4 ");
			String type = prvlg.getTraderStatus();
			if (!StringUtils.isEmpty(tjStr)) {
				sb.append(tjStr).append(" ");
			}
			if (orders.getA_OrderNo() != null) {
				sb.append("and A_OrderNo=? ");
				params = new Object[] { orders.getA_OrderNo() };
			} else {
				List lst = new ArrayList();
				if (!StringUtils.isEmpty(orders.getTraderID())) {
					if ("A".equals(type)) {
						sb.append(" and firmid =?");
						lst.add(prvlg.getFirmId());
					} else {
						sb.append(" and TraderID=?");
						lst.add(prvlg.getTraderID());
					}
				}
				if (!StringUtils.isEmpty(orders.getCommodityID())) {
					sb.append("and CommodityID=? ");
					lst.add(orders.getCommodityID());
				}
				if (orders.getBS_Flag() != null) {
					sb.append("and BS_Flag=? ");
					lst.add(orders.getBS_Flag());
				}
				if (!"queryAll".equalsIgnoreCase(orders.getUpdateTime())) {
					sb.append(" and UpdateTime>? ").append(" order by A_OrderNo");
					lst.add(new Timestamp(Long.parseLong(orders.getUpdateTime())));
				}
				params = lst.toArray();
			}
		}
		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	public List market_query(Market market) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from T_A_Market where Status=1 ");
		Object[] params = null;
		List lst = new ArrayList();
		if (!StringUtils.isEmpty(market.getMarketCode())) {
			sb.append("and MarketCode=? ");
			lst.add(market.getMarketCode());
		}
		params = lst.toArray();
		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	public List commodity_data_query(Orders orders) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.CommodityID,a.Name,a.SettleDate,a.ContractFactor,a.MinPriceMove,a.SpreadUpLmt,a.SpreadDownLmt,b.* ")
				.append("from T_Commodity a,T_Quotation b ").append("where a.CommodityID=b.CommodityID ");

		Object[] params = null;
		if ((orders != null) && (!StringUtils.isEmpty(orders.getCommodityID()))) {
			sb.append("and a.CommodityID=? ");
			params = new Object[] { orders.getCommodityID() };
		}
		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	public List getBroadcasts() {
		String sql = "select * from T_Broadcast order by id";

		return getJdbcTemplate().queryForList(sql);
	}

	public Privilege getTradePrivilege(TraderLogonInfo info) {
		Privilege privilege = new Privilege();
		String traderID = info.getTraderId();
		String firmID = info.getFirmId();
		privilege.setTraderID(traderID);
		privilege.setFirmId(firmID);
		privilege.setTraderName(info.getTraderName());
		privilege.setFirmName(info.getFirmName());
		privilege.setTraderStatus(info.getType());
		privilege.setSessionID(Long.valueOf(info.getSessionID()));

		List idList = getJdbcTemplate().queryForList("select Code from T_Customer where FirmID=?", new Object[] { firmID });
		StringBuffer idSB = new StringBuffer();
		for (Object o : idList) {
			Map idmap = (Map) o;
			idSB.append((String) idmap.get("Code")).append(",");
		}
		String ids = idSB.toString();
		if ((ids != null) && (!ids.equals(""))) {
			ids = ids.substring(0, ids.length() - 1);
		}
		privilege.setIds(ids);

		String margain_sql = "select CommodityID, MarginAlgr,MarginRate_B,MarginRate_S,MarginAssure_B,MarginAssure_S from T_A_FirmMargin where FirmID=?";

		List mar_List = getJdbcTemplate().queryForList(margain_sql, new Object[] { firmID });
		if ((mar_List != null) && (mar_List.size() > 0)) {
			Map firm_MarginRate = new HashMap();
			for (int i = 0; i < mar_List.size(); i++) {
				Map map = (Map) mar_List.get(i);
				Map value_Map = new HashMap();
				value_Map.put("MarginAlgr", (BigDecimal) map.get("MarginAlgr"));
				value_Map.put("MarginRate_B", (BigDecimal) map.get("MarginRate_B"));
				value_Map.put("MarginRate_S", (BigDecimal) map.get("MarginRate_S"));
				value_Map.put("MarginAssure_B", (BigDecimal) map.get("MarginAssure_B"));
				value_Map.put("MarginAssure_S", (BigDecimal) map.get("MarginAssure_S"));
				firm_MarginRate.put((String) map.get("CommodityID"), value_Map);
			}
			privilege.setFirm_MarginRate(firm_MarginRate);
		}
		privilege.setFirm_tariffId(
				(String) getJdbcTemplate().queryForObject("select TariffID from t_firm where FirmID=?", new Object[] { firmID }, String.class));

		String fee_sql = "select CommodityID,FeeAlgr, FeeRate_B,FeeRate_S,todayclosefeerate_b,todayclosefeerate_s, historyclosefeerate_b, historyclosefeerate_s,forceclosefeerate_b,forceclosefeerate_s from T_A_FirmFee  where firmID = ?";

		List fee_List = getJdbcTemplate().queryForList(fee_sql, new Object[] { firmID });
		if ((fee_List != null) && (fee_List.size() > 0)) {
			Map firm_FeeRate = new HashMap();
			for (int i = 0; i < fee_List.size(); i++) {
				Map map = (Map) fee_List.get(i);
				Map value_Map = new HashMap();
				value_Map.put("FeeAlgr", (BigDecimal) map.get("FeeAlgr"));
				value_Map.put("FeeRate_B", (BigDecimal) map.get("FeeRate_B"));
				value_Map.put("FeeRate_S", (BigDecimal) map.get("FeeRate_S"));

				value_Map.put("todayclosefeerate_b", (BigDecimal) map.get("todayclosefeerate_b"));
				value_Map.put("todayclosefeerate_s", (BigDecimal) map.get("todayclosefeerate_s"));
				value_Map.put("historyclosefeerate_b", (BigDecimal) map.get("historyclosefeerate_b"));
				value_Map.put("historyclosefeerate_s", (BigDecimal) map.get("historyclosefeerate_s"));
				value_Map.put("forceclosefeerate_b", (BigDecimal) map.get("forceclosefeerate_b"));
				value_Map.put("forceclosefeerate_s", (BigDecimal) map.get("forceclosefeerate_s"));

				firm_FeeRate.put((String) map.get("CommodityID"), value_Map);
			}
			privilege.setFirm_FeeRate(firm_FeeRate);
		}
		String settleFee_sql = "select CommodityID,settlefeealgr,settlefeerate_b,settlefeerate_s from t_a_firmsettlefee  where firmID = ?";

		List settleFee_List = getJdbcTemplate().queryForList(settleFee_sql, new Object[] { firmID });
		if ((settleFee_List != null) && (settleFee_List.size() > 0)) {
			Map firm_settleFeeRate = new HashMap();
			for (int i = 0; i < settleFee_List.size(); i++) {
				Map map = (Map) settleFee_List.get(i);
				Map value_Map = new HashMap();

				value_Map.put("settlefeealgr", (BigDecimal) map.get("settlefeealgr"));
				value_Map.put("settlefeerate_b", (BigDecimal) map.get("settlefeerate_b"));
				value_Map.put("settlefeerate_s", (BigDecimal) map.get("settlefeerate_s"));

				firm_settleFeeRate.put((String) map.get("CommodityID"), value_Map);
			}
			privilege.setFirm_SettleFeeRate(firm_settleFeeRate);
		}
		String sql = "Select PrivilegeCode_B from T_A_TradePrivilege where typeID=? and type=3";
		this.log.debug("tradePrivilegeSQL:" + sql);
		this.log.debug("Param1: TraderID= " + traderID);
		Object[] param = { traderID };
		List list = getJdbcTemplate().queryForList(sql, param);
		if ((list != null) && (list.size() > 0)) {
			List arr = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				arr.add(Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_B")).intValue()));
			}
			privilege.setTraderPrivilege(arr);
		}
		sql = "Select KindID,PrivilegeCode_B,PrivilegeCode_S from T_A_TradePrivilege where TypeID=? and Type=3 and Kind=1";
		this.log.debug("Select Trader Breed Privilege SQL:" + sql);
		this.log.debug("Param1: TraderID= " + traderID);
		list = getJdbcTemplate().queryForList(sql, param);
		if ((list != null) && (list.size() > 0)) {
			Map tBreedPrivilege_B = new HashMap();
			Map tBreedPrivilege_S = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String KindID = (String) map.get("KindID");
				if (map.get("PrivilegeCode_B") != null) {
					tBreedPrivilege_B.put(KindID, Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_B")).intValue()));
				} else {
					tBreedPrivilege_B.put(KindID, Integer.valueOf(101));
				}
				if (map.get("PrivilegeCode_S") != null) {
					tBreedPrivilege_S.put(KindID, Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_S")).intValue()));
				} else {
					tBreedPrivilege_S.put(KindID, Integer.valueOf(201));
				}
			}
			privilege.setTBreedPrivilege_B(tBreedPrivilege_B);
			privilege.setTBreedPrivilege_S(tBreedPrivilege_S);
		}
		sql = "Select BreedID,OrderPrivilege From T_A_Breed";
		this.log.debug("Breed Privilege SQL: " + sql);
		list = getJdbcTemplate().queryForList(sql);
		if ((list != null) && (list.size() > 0)) {
			Map breedPrivilege = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				Integer BreedID = Integer.valueOf(((BigDecimal) map.get("BreedID")).intValue());
				this.log.debug("OrderPrivilege:  " + map.get("OrderPrivilege"));
				breedPrivilege.put(BreedID, Integer.valueOf(((BigDecimal) map.get("OrderPrivilege")).intValue()));
			}
			privilege.setBreedPrivilege(breedPrivilege);
		}
		sql = "Select CommodityID,OrderPrivilege_B,OrderPrivilege_S,BreedID From T_Commodity ";
		this.log.debug("CommodityPrivilegeSQL: " + sql);
		list = getJdbcTemplate().queryForList(sql);
		if ((list != null) && (list.size() > 0)) {
			Map commPrivilege_B = new HashMap();
			Map commPrivilege_S = new HashMap();
			Map commMap = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String commodityID = (String) map.get("CommodityID");
				Integer BreedID = Integer.valueOf(((BigDecimal) map.get("BreedID")).intValue());
				commPrivilege_B.put(commodityID, Integer.valueOf(((BigDecimal) map.get("OrderPrivilege_B")).intValue()));
				commPrivilege_S.put(commodityID, Integer.valueOf(((BigDecimal) map.get("OrderPrivilege_S")).intValue()));
				commMap.put(commodityID, BreedID);
			}
			privilege.setCommPrivilege_B(commPrivilege_B);
			privilege.setCommPrivilege_S(commPrivilege_S);
			privilege.setCommMap(commMap);
		}
		sql = "Select Kind,KindID,PrivilegeCode_B,PrivilegeCode_S From T_A_TradePrivilege Where typeID=? and type=1";

		this.log.debug("FirmPrivilegeSQL:" + sql);

		this.log.debug("Param2: FirmID= " + firmID);
		Object[] param2 = { firmID };
		list = getJdbcTemplate().queryForList(sql, param2);
		if ((list != null) && (list.size() > 0)) {
			Map fBreedPrivilege_B = new HashMap();
			Map fBreedPrivilege_S = new HashMap();
			Map fCommPrivilege_B = new HashMap();
			Map fCommPrivilege_S = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				int kind = ((BigDecimal) map.get("Kind")).intValue();
				if (kind == 1) {
					String KindID = (String) map.get("KindID");

					fBreedPrivilege_B.put(KindID, Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_B")).intValue()));
					fBreedPrivilege_S.put(KindID, Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_S")).intValue()));
				} else if (kind == 2) {
					String KindID = (String) map.get("KindID");
					fCommPrivilege_B.put(KindID, Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_B")).intValue()));
					fCommPrivilege_S.put(KindID, Integer.valueOf(((BigDecimal) map.get("PrivilegeCode_S")).intValue()));
				}
			}
			privilege.setFBreedPrivilege_B(fBreedPrivilege_B);
			privilege.setFBreedPrivilege_S(fBreedPrivilege_S);
			privilege.setFCommPrivilege_B(fCommPrivilege_B);
			privilege.setFCommPrivilege_S(fCommPrivilege_S);
		}
		sql = "select TypeID,PrivilegeCode_B,PrivilegeCode_S,KindID,Kind From t_a_Tradeprivilege a,t_customer b Where a.typeid = b.customerid(+) and a.type=2 and b.firmid=?";

		Object[] param4 = { firmID };
		List list2 = getJdbcTemplate().queryForList(sql, param4);
		if ((list2 != null) && (list2.size() > 0)) {
			Map cusBreedPrivilege_B = new HashMap();
			Map cusBreedPrivilege_S = new HashMap();
			Map cusCommPrivilege_B = new HashMap();
			Map cusCommPrivilege_S = new HashMap();

			Map cus_B_brd_Map = new HashMap();
			Map cus_S_brd_Map = new HashMap();
			Map cus_B_cty_Map = new HashMap();
			Map cus_S_cty_Map = new HashMap();
			for (int j = 0; j < list2.size(); j++) {
				Map cusMap = (Map) list2.get(j);
				int kind = ((BigDecimal) cusMap.get("Kind")).intValue();
				if (kind == 1) {
					String KindID = (String) cusMap.get("KindID");
					BigDecimal cusPri_B = (BigDecimal) cusMap.get("PrivilegeCode_B");
					if (cusPri_B != null) {
						cus_B_brd_Map.put(KindID, Integer.valueOf(cusPri_B.intValue()));
					} else {
						cus_B_brd_Map.put(KindID, Integer.valueOf(101));
					}
					BigDecimal cusPri_S = (BigDecimal) cusMap.get("PrivilegeCode_S");
					if (cusPri_S != null) {
						cus_S_brd_Map.put(KindID, Integer.valueOf(cusPri_S.intValue()));
					} else {
						cus_S_brd_Map.put(KindID, Integer.valueOf(201));
					}
					String customerID = (String) cusMap.get("TypeID");
					cusBreedPrivilege_B.put(customerID, cus_B_brd_Map);
					cusBreedPrivilege_S.put(customerID, cus_S_brd_Map);
				} else if (kind == 2) {
					String KindID = (String) cusMap.get("KindID");
					BigDecimal cusPri_B = (BigDecimal) cusMap.get("PrivilegeCode_B");
					if (cusPri_B != null) {
						cus_B_cty_Map.put(KindID, Integer.valueOf(cusPri_B.intValue()));
					} else {
						cus_B_cty_Map.put(KindID, Integer.valueOf(101));
					}
					BigDecimal cusPri_S = (BigDecimal) cusMap.get("PrivilegeCode_S");
					if (cusPri_S != null) {
						cus_S_cty_Map.put(KindID, Integer.valueOf(cusPri_S.intValue()));
					} else {
						cus_S_cty_Map.put(KindID, Integer.valueOf(201));
					}
					String customerID = (String) cusMap.get("TypeID");
					cusCommPrivilege_B.put(customerID, cus_B_cty_Map);
					cusCommPrivilege_S.put(customerID, cus_S_cty_Map);
				}
			}
			privilege.setCusBreedPrivilege_B(cusBreedPrivilege_B);
			privilege.setCusBreedPrivilege_S(cusBreedPrivilege_S);
			privilege.setCusCommPrivilege_B(cusCommPrivilege_B);
			privilege.setCusCommPrivilege_S(cusCommPrivilege_S);
		}
		sql = " select PrivilegeCode_B,PrivilegeCode_S,Kind,KindID  From t_a_SettlePrivilege a  Where Type=1 and TypeID=?";

		param2 = new Object[] { firmID };
		list = getJdbcTemplate().queryForList(sql, param2);
		if ((list != null) && (list.size() > 0)) {
			Map commPrivilegeB = new HashMap();
			Map commPrivilegeS = new HashMap();
			Map breedPrivilegeB = new HashMap();
			Map breedPrivilegeS = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				Map privilegeMap = (Map) list.get(i);
				BigDecimal kind = (BigDecimal) privilegeMap.get("Kind");
				String commOrBreed = (String) privilegeMap.get("KindID");
				BigDecimal privilegeCode_B = (BigDecimal) privilegeMap.get("PrivilegeCode_B");
				BigDecimal privilegeCode_S = (BigDecimal) privilegeMap.get("PrivilegeCode_S");
				if (kind != null) {
					if (kind.intValue() == 1) {
						if (privilegeCode_B != null) {
							breedPrivilegeB.put(commOrBreed, Integer.valueOf(privilegeCode_B.intValue()));
						} else {
							breedPrivilegeB.put(commOrBreed, Integer.valueOf(104));
						}
						if (privilegeCode_S != null) {
							breedPrivilegeS.put(commOrBreed, Integer.valueOf(privilegeCode_S.intValue()));
						} else {
							breedPrivilegeS.put(commOrBreed, Integer.valueOf(104));
						}
					} else if (kind.intValue() == 2) {
						if (privilegeCode_B != null) {
							commPrivilegeB.put(commOrBreed, Integer.valueOf(privilegeCode_B.intValue()));
						} else {
							commPrivilegeB.put(commOrBreed, Integer.valueOf(104));
						}
						if (privilegeCode_S != null) {
							commPrivilegeS.put(commOrBreed, Integer.valueOf(privilegeCode_S.intValue()));
						} else {
							commPrivilegeS.put(commOrBreed, Integer.valueOf(104));
						}
					}
				}
			}
			privilege.setfBreedSettlePrivilege_B(breedPrivilegeB);
			privilege.setfBreedSettlePrivilege_S(breedPrivilegeS);
			privilege.setfCommSettlePrivilege_B(commPrivilegeB);
			privilege.setfCommSettlePrivilege_S(commPrivilegeS);
		}
		sql = " select PrivilegeCode_B,PrivilegeCode_S,Kind,KindID  From t_a_SettlePrivilege a  Where Type=3 and typeID=?";

		param2 = new Object[] { traderID };
		list = getJdbcTemplate().queryForList(sql, param2);
		if ((list != null) && (list.size() > 0)) {
			Map commPrivilegeB = new HashMap();
			Map commPrivilegeS = new HashMap();
			Map breedPrivilegeB = new HashMap();
			Map breedPrivilegeS = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				Map privilegeMap = (Map) list.get(i);
				BigDecimal kind = (BigDecimal) privilegeMap.get("Kind");
				String commOrBreed = (String) privilegeMap.get("KindID");
				BigDecimal privilegeCode_B = (BigDecimal) privilegeMap.get("PrivilegeCode_B");
				BigDecimal privilegeCode_S = (BigDecimal) privilegeMap.get("PrivilegeCode_S");
				if (kind != null) {
					if (kind.intValue() == 1) {
						if (privilegeCode_B != null) {
							breedPrivilegeB.put(commOrBreed, Integer.valueOf(privilegeCode_B.intValue()));
						} else {
							breedPrivilegeB.put(commOrBreed, Integer.valueOf(104));
						}
						if (privilegeCode_S != null) {
							breedPrivilegeS.put(commOrBreed, Integer.valueOf(privilegeCode_S.intValue()));
						} else {
							breedPrivilegeS.put(commOrBreed, Integer.valueOf(104));
						}
					} else if (kind.intValue() == 2) {
						if (privilegeCode_B != null) {
							commPrivilegeB.put(commOrBreed, Integer.valueOf(privilegeCode_B.intValue()));
						} else {
							commPrivilegeB.put(commOrBreed, Integer.valueOf(104));
						}
						if (privilegeCode_S != null) {
							commPrivilegeS.put(commOrBreed, Integer.valueOf(privilegeCode_S.intValue()));
						} else {
							commPrivilegeS.put(commOrBreed, Integer.valueOf(104));
						}
					}
				}
			}
			privilege.settBreedSettlePrivilege_B(breedPrivilegeB);
			privilege.settBreedSettlePrivilege_S(breedPrivilegeS);
			privilege.settCommSettlePrivilege_B(commPrivilegeB);
			privilege.settCommSettlePrivilege_S(commPrivilegeS);
		}
		sql = "select TypeID,PrivilegeCode_B,PrivilegeCode_S,KindID,Kind From t_a_SettlePrivilege a,t_customer b Where a.typeid = b.customerid(+) and a.type=2 and b.firmid=?";

		param2 = new Object[] { firmID };
		list2 = getJdbcTemplate().queryForList(sql, param2);
		Map cusCommPrivilege_S;
		if ((list2 != null) && (list2.size() > 0)) {
			Map cusBreedPrivilege_B = new HashMap();
			Map cusBreedPrivilege_S = new HashMap();
			Map cusCommPrivilege_B = new HashMap();
			cusCommPrivilege_S = new HashMap();

			Map cus_B_brd_Map = new HashMap();
			Map cus_S_brd_Map = new HashMap();
			Map cus_B_cty_Map = new HashMap();
			Map cus_S_cty_Map = new HashMap();
			for (int j = 0; j < list2.size(); j++) {
				Map cusMap = (Map) list2.get(j);
				int kind = ((BigDecimal) cusMap.get("Kind")).intValue();
				if (kind == 1) {
					String KindID = (String) cusMap.get("KindID");
					BigDecimal cusPri_B = (BigDecimal) cusMap.get("PrivilegeCode_B");
					if (cusPri_B != null) {
						cus_B_brd_Map.put(KindID, Integer.valueOf(cusPri_B.intValue()));
					} else {
						cus_B_brd_Map.put(KindID, Integer.valueOf(104));
					}
					BigDecimal cusPri_S = (BigDecimal) cusMap.get("PrivilegeCode_S");
					if (cusPri_S != null) {
						cus_S_brd_Map.put(KindID, Integer.valueOf(cusPri_S.intValue()));
					} else {
						cus_S_brd_Map.put(KindID, Integer.valueOf(104));
					}
					String customerID = (String) cusMap.get("TypeID");
					cusBreedPrivilege_B.put(customerID, cus_B_brd_Map);
					cusBreedPrivilege_S.put(customerID, cus_S_brd_Map);
				} else if (kind == 2) {
					String KindID = (String) cusMap.get("KindID");
					BigDecimal cusPri_B = (BigDecimal) cusMap.get("PrivilegeCode_B");
					if (cusPri_B != null) {
						cus_B_cty_Map.put(KindID, Integer.valueOf(cusPri_B.intValue()));
					} else {
						cus_B_cty_Map.put(KindID, Integer.valueOf(104));
					}
					BigDecimal cusPri_S = (BigDecimal) cusMap.get("PrivilegeCode_S");
					if (cusPri_S != null) {
						cus_S_cty_Map.put(KindID, Integer.valueOf(cusPri_S.intValue()));
					} else {
						cus_S_cty_Map.put(KindID, Integer.valueOf(104));
					}
					String customerID = (String) cusMap.get("TypeID");
					cusCommPrivilege_B.put(customerID, cus_B_cty_Map);
					cusCommPrivilege_S.put(customerID, cus_S_cty_Map);
				}
			}
			privilege.setCusBreedSettlePrivilege_B(cusBreedPrivilege_B);
			privilege.setCusBreedSettlePrivilege_S(cusBreedPrivilege_S);
			privilege.setCusCommSettlePrivilege_B(cusCommPrivilege_B);
			privilege.setCusCommSettlePrivilege_S(cusCommPrivilege_S);
		}
		sql = "select a.CommodityID,b.BreedTradeMode from T_Commodity a,T_A_Breed b where a.BreedID=b.BreedID AND b.BreedTradeMode in (1,2,3,4)";
		Map directMap = new HashMap();
		List directList = getJdbcTemplate().queryForList(sql);
		for (Object o : directList) {
			Map m = (Map) o;
			directMap.put(m.get("CommodityID"), m.get("BreedTradeMode"));
		}
		privilege.setDirectBreeds(directMap);

		privilege.setDirectFirmBreeds(directfirmbreed_query(firmID));

		return privilege;
	}

	public Date getDBTime() {
		String sql = "select sysdate from dual";

		this.log.debug("sql" + sql);

		return (Date) getJdbcTemplate().queryForObject(sql, Date.class);
	}

	private class TradeRowMapper implements RowMapper {
		private TradeRowMapper() {
		}

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Trade trade = new Trade();
			trade.setA_OrderNo(Long.valueOf(rs.getLong("A_OrderNo")));
			trade.setA_TradeNo(Long.valueOf(rs.getLong("A_TradeNo")));
			trade.setA_TradeNo_Closed(Long.valueOf(rs.getLong("A_TradeNo_Closed")));
			trade.setBS_Flag(Short.valueOf(rs.getShort("BS_Flag")));
			trade.setClose_PL(Double.valueOf(rs.getDouble("Close_PL")));
			trade.setCommodityID(rs.getString("CommodityID"));
			trade.setCustomerID(rs.getString("CustomerID"));
			trade.setFirmID(rs.getString("FirmID"));
			trade.setHoldPrice(Double.valueOf(rs.getDouble("HoldPrice")));
			trade.setM_TradeNo(Long.valueOf(rs.getLong("M_TradeNo")));
			trade.setOrderType(Short.valueOf(rs.getShort("OrderType")));
			trade.setPrice(Double.valueOf(rs.getDouble("Price")));
			trade.setQuantity(Long.valueOf(rs.getLong("Quantity")));
			trade.setTradeFee(Double.valueOf(rs.getDouble("TradeFee")));
			trade.setTradeTime(rs.getTimestamp("TradeTime"));
			trade.setTradeType(Integer.valueOf(rs.getInt("TradeType")));

			trade.setTraderID(rs.getString("TraderID"));
			return trade;
		}
	}

	public List directfirmbreed_query(String firmID) {
		String sql = "select BreedID from T_E_DirectFirmBreed where FirmID=?";
		Object[] params = { firmID };
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		return getJdbcTemplate().queryForList(sql, params);
	}

	public List holdpositionbyprice(Orders orders, Privilege prvlg, SortCondition sc) {
		StringBuffer sb = new StringBuffer();

		sb.append(
				"select to_char(h.atcleardate,'YYYY-MM-DD') holddate,h.CustomerID,h.CommodityID,h.BS_Flag,h.Price,to_char(h.deadline,'YYYY-MM-DD') deadline,h.remainday,sum(h.HoldQty+h.GageQty) QTY,sum(h.GageQty) GageQty,sum(h.HoldMargin) HoldMargin from T_HoldPosition h where h.HoldQty+h.GageQty>0 and h.FirmID=? ");
		Object[] params = null;

		List lst = new ArrayList();
		String firmID = prvlg.getFirmId();
		lst.add(firmID);
		if (!StringUtils.isEmpty(orders.getCommodityID())) {
			sb.append(" and h.CommodityID=? ");
			lst.add(orders.getCommodityID());
		}
		params = lst.toArray();

		this.log.debug("sql: " + sb.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				this.log.debug("params[" + i + "]: " + params[i]);
			}
		}
		sb.append(" group by h.atcleardate, h.CustomerID,h.CommodityID,h.BS_Flag,h.Price,h.deadline,h.remainday ");
		sb.append(" order by h.remainday,h.atcleardate,h.Price");

		return getJdbcTemplate().queryForList(sb.toString(), params);
	}

	public Map tariff() {
		String sql = "select distinct tariffid from t_a_tariff";
		List<String> list = getJdbcTemplate().queryForList(sql, String.class);
		Map tariff = new HashMap();
		for (String tariffId : list) {
			String fee_sql = "select t.commodityid CommodityID, t.feealgr FeeAlgr, t.feerate_b FeeRate_B, t.feerate_s FeeRate_S, t.todayclosefeerate_b todayclosefeerate_b, t.todayclosefeerate_s todayclosefeerate_s, t.historyclosefeerate_b historyclosefeerate_b, t.historyclosefeerate_s historyclosefeerate_s, t.forceclosefeerate_b  forceclosefeerate_b, t.forceclosefeerate_s forceclosefeerate_s, c.settlefeealgr settlefeealgr, c.settlefeerate_b settlefeerate_b, c.settlefeerate_s settlefeerate_s from T_A_Tariff t, t_Commodity c where t.CommodityID = c.commodityid and TariffID = ? ";

			List fee_List = getJdbcTemplate().queryForList(fee_sql, new Object[] { tariffId });
			if ((fee_List != null) && (fee_List.size() > 0)) {
				Map firm_FeeRate = new HashMap();
				for (int i = 0; i < fee_List.size(); i++) {
					Map map = (Map) fee_List.get(i);
					Map value_Map = new HashMap();
					value_Map.put("FeeAlgr", (BigDecimal) map.get("FeeAlgr"));
					value_Map.put("FeeRate_B", (BigDecimal) map.get("FeeRate_B"));
					value_Map.put("FeeRate_S", (BigDecimal) map.get("FeeRate_S"));

					value_Map.put("todayclosefeerate_b", (BigDecimal) map.get("todayclosefeerate_b"));
					value_Map.put("todayclosefeerate_s", (BigDecimal) map.get("todayclosefeerate_s"));
					value_Map.put("historyclosefeerate_b", (BigDecimal) map.get("historyclosefeerate_b"));
					value_Map.put("historyclosefeerate_s", (BigDecimal) map.get("historyclosefeerate_s"));
					value_Map.put("forceclosefeerate_b", (BigDecimal) map.get("forceclosefeerate_b"));
					value_Map.put("forceclosefeerate_s", (BigDecimal) map.get("forceclosefeerate_s"));

					value_Map.put("settlefeealgr", (BigDecimal) map.get("settlefeealgr"));
					value_Map.put("settlefeerate_b", (BigDecimal) map.get("settlefeerate_b"));
					value_Map.put("settlefeerate_s", (BigDecimal) map.get("settlefeerate_s"));

					firm_FeeRate.put((String) map.get("CommodityID"), value_Map);
				}
				tariff.put(tariffId, firm_FeeRate);
			}
		}
		return tariff;
	}
}
