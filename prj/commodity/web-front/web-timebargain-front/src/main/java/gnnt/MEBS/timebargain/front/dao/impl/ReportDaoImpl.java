/*     */ package gnnt.MEBS.timebargain.front.dao.impl;

/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;

/*     */ import org.springframework.stereotype.Repository;

/*     */
/*     */ import gnnt.MEBS.common.front.dao.StandardDao;
/*     */ import gnnt.MEBS.timebargain.front.dao.ReportDao;

/*     */
/*     */ @Repository("reportDao")
/*     */ public class ReportDaoImpl extends StandardDao/*     */ implements ReportDao
/*     */ {
	/*     */ public List<Map<Object, Object>> firmFundsQuery(Map<String, String> params)
	/*     */ {
		/* 37 */ StringBuffer sb = new StringBuffer();
		/* 38 */ sb/* 39 */ .append(/* 40 */ "select distinct a.*, (b.ClearMargin-b.ClearAssure) ClearMargin,b.ClearFL,b.ClearSettleMargin,")
				/* 41 */ .append(
						/* 42 */ "(b.RuntimeMargin-b.RuntimeAssure) RuntimeMargin,RuntimeFL,RuntimeSettleMargin,MinClearDeposit,b.MaxOverdraft, ")
				/* 43 */ .append(
						/* 44 */ "nvl((select sum(floatingloss) from t_h_Firmholdsum where firmid = b.firmid and cleardate = a.B_Date),0) PL ")
				/* 45 */ .append(/* 46 */ "from F_FirmBalance a,t_h_firm b where a.firmID(+)=b.firmID and a.B_Date=b.cleardate ")
				/* 47 */ .append(" and a.firmid='").append((String) params.get("firmid")).append(/* 48 */ "' and a.b_date=to_date('")
				/* 49 */ .append((String) params.get("b_date")).append("','yyyy-MM-dd')");
		/*     */
		/* 51 */ logger.debug("资金结算报表查询sql1: " + sb.toString());
		/* 52 */ List fundList1 = queryBySql(sb.toString());
		/*     */
		/* 55 */ StringBuffer sb2 = new StringBuffer();
		/* 56 */ sb2
				/* 57 */ .append(
						/* 58 */ "select nvl(sum(case when f.fieldsign>0 then t.value else -t.value end),0) value from (select * from f_clientledger where firmId='")
				/* 59 */ .append((String) params.get("firmid"))/* 60 */ .append("' and b_date=to_date('")
				/* 61 */ .append((String) params.get("b_date"))/* 62 */ .append("','yyyy-MM-dd')) t,")
				/* 63 */ .append(/* 64 */ "f_ledgerfield f where f.code=t.code(+) and f.moduleid not in (22,15) order by f.moduleid,f.ordernum ");
		/*     */
		/* 66 */ this.logger.debug("资金结算报表查询sql2: " + sb2.toString());
		/* 67 */ List fundList2 = queryBySql(sb2.toString());
		/* 68 */ if ((fundList1 != null) && (fundList1.size() > 0)) {
			/* 69 */ ((Map) fundList1.get(0)).putAll((Map) fundList2.get(0));
			/*     */ }
		/* 71 */ return fundList1;
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> getFundFields(Map<String, String> params)
	/*     */ {
		/* 81 */ StringBuffer sb = new StringBuffer();
		/* 82 */ sb/* 83 */ .append(/* 84 */ "select nvl(b.Value,0) Value,a.Name,a.FieldSign From F_LedgerField a,")
				/* 85 */ .append("(select * from F_ClientLedger ")/* 86 */ .append("where firmId='")/* 87 */ .append((String) params.get("firmid"))
				/* 88 */ .append("' and b_date=to_date('")/* 89 */ .append((String) params.get("b_date"))/* 90 */ .append("','yyyy-MM-dd')")
				/* 91 */ .append(/* 92 */ " ) b where  b.Code(+)=a.Code and a.moduleid in (22,15) order by moduleid,ordernum ");
		/* 93 */ this.logger.debug("资金结算表中字段查询sql: " + sb.toString());
		/* 94 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> tradesQuery(Map<String, String> params)
	/*     */ {
		/* 105 */ StringBuffer sb = new StringBuffer();
		/* 106 */ sb.append("select * from (select distinct rownum num, a.CUSTOMERID, a.A_TRADENO, a.PRICE, a.QUANTITY, a.TRADEFEE,")
				/* 107 */ .append("a.tradeTime, b.OrderTime ,d.CommodityID, ")
				/* 108 */ .append("(case when a.BS_Flag=1 then '买进' when a.BS_Flag=2 then '卖出' else '' end) || ")
				/* 109 */ .append(" (case when a.OrderType=1 then '订货' when a.OrderType=2 then '转让' else '' end) BSOrderType ")
				/* 110 */ .append("from T_H_Trade a,T_H_Orders b,T_H_Commodity d ")
				/* 111 */ .append(
						"where a.ClearDate = b.ClearDate(+) and a.ClearDate=d.ClearDate and a.A_OrderNo = b.A_OrderNo(+) and a.CommodityID=d.CommodityID  ")
				/* 112 */ .append("and a.firmID='" + (String) params.get("a.firmID") + "' ")
				.append("and a.ClearDate >= to_date('" + (String) params.get("startDate") + "', 'yyyy-MM-dd') ")
				/* 113 */ .append("and a.ClearDate <= to_date('" + (String) params.get("endDate") + "', 'yyyy-MM-dd') ");
		/*     */
		/* 116 */ sb.append(" order by a.A_TradeNo) ");
		/* 117 */ sb.append("where num between " + (String) params.get("startCount") + " and " + (String) params.get("endCount"));
		/* 118 */ this.logger.debug("sql: " + sb.toString());
		/* 119 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public int tradesQueryCount(Map<String, String> params)
	/*     */ {
		/* 133 */ StringBuffer sb = new StringBuffer();
		/* 134 */ sb.append("select * from (select distinct rownum num, a.CUSTOMERID, a.A_TRADENO, a.PRICE, a.QUANTITY, a.TRADEFEE,")
				/* 135 */ .append("a.tradeTime, b.OrderTime ,d.CommodityID, ")
				/* 136 */ .append("(case when a.BS_Flag=1 then '买进' when a.BS_Flag=2 then '卖出' else '' end) || ")
				/* 137 */ .append(" (case when a.OrderType=1 then '订货' when a.OrderType=2 then '转让' else '' end) BSOrderType ")
				/* 138 */ .append("from T_H_Trade a,T_H_Orders b,T_H_Commodity d ")
				/* 139 */ .append(
						"where a.ClearDate = b.ClearDate(+) and a.ClearDate=d.ClearDate and a.A_OrderNo = b.A_OrderNo(+) and a.CommodityID=d.CommodityID  ")
				/* 140 */ .append("and a.firmID='" + (String) params.get("a.firmID") + "' ")
				.append("and a.ClearDate >= to_date('" + (String) params.get("startDate") + "', 'yyyy-MM-dd') ")
				/* 141 */ .append("and a.ClearDate <= to_date('" + (String) params.get("endDate") + "', 'yyyy-MM-dd') ");
		/*     */
		/* 144 */ sb.append(" order by a.A_TradeNo) ");
		/* 145 */ this.logger.debug("sql: " + sb.toString());
		/*     */
		/* 147 */ return queryBySql(sb.toString()).size();
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> tradesQuerySum(Map<String, String> params)
	/*     */ {
		/* 161 */ StringBuffer sb = new StringBuffer();
		/* 162 */ sb
				.append("select nvl(sum(QUANTITY),0) quantitySum, nvl(sum(TRADEFEE),0) tradefeeSum from (select distinct rownum num, a.CUSTOMERID, a.A_TRADENO, a.PRICE, a.QUANTITY, a.TRADEFEE,")
				/* 163 */ .append("a.tradeTime, b.OrderTime ,d.CommodityID, ")
				/* 164 */ .append("(case when a.BS_Flag=1 then '买进' when a.BS_Flag=2 then '卖出' else '' end) || ")
				/* 165 */ .append(" (case when a.OrderType=1 then '订货' when a.OrderType=2 then '转让' else '' end) BSOrderType ")
				/* 166 */ .append("from T_H_Trade a,T_H_Orders b,T_H_Commodity d ")
				/* 167 */ .append(
						"where a.ClearDate = b.ClearDate(+) and a.ClearDate=d.ClearDate and a.A_OrderNo = b.A_OrderNo(+) and a.CommodityID=d.CommodityID  ")
				/* 168 */ .append("and a.firmID='" + (String) params.get("a.firmID") + "' ")
				.append("and a.ClearDate >= to_date('" + (String) params.get("startDate") + "', 'yyyy-MM-dd') ")
				/* 169 */ .append("and a.ClearDate <= to_date('" + (String) params.get("endDate") + "', 'yyyy-MM-dd') ");
		/*     */
		/* 172 */ sb.append(" order by a.A_TradeNo) ");
		/* 173 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> firmHoldSumQuery(Map<String, String> params)
	/*     */ {
		/* 181 */ StringBuffer sb = new StringBuffer();
		/* 182 */ sb.append("select * from (select  rownum num, a.*,a.HoldQty+a.GageQty HoldGageQty,b.Price LastPrice, ")
				/* 183 */ .append("case when a.BS_Flag=1 then '买' when a.BS_Flag=2 then '卖' else '' end as sBS_Flag ")
				/* 184 */ .append("from T_H_CustomerHoldSum a,T_H_Quotation b ")
				/* 185 */ .append("where a.ClearDate=b.ClearDate and a.CommodityID=b.CommodityID ")
				/* 186 */ .append("and a.firmID='" + (String) params.get("a.firmID") + "' ")
				.append("and a.ClearDate=to_date('" + (String) params.get("a.ClearDate") + "', 'yyyy-MM-dd') ");
		/*     */
		/* 188 */ sb.append(" order by a.CustomerID,b.CommodityID,a.BS_Flag )");
		/* 189 */ sb.append("where num between " + (String) params.get("startCount") + " and " + (String) params.get("endCount"));
		/* 190 */ this.logger.debug("sql: " + sb.toString());
		/* 191 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> transferProfitAndLossQuery(Map<String, String> params)
	/*     */ {
		/* 200 */ StringBuffer sb = new StringBuffer();
		/* 201 */ sb.append("select * from (select  rownum num, a.*, ")
				/* 202 */ .append("(case when a.BS_Flag=1 then '买进' when a.BS_Flag=2 then '卖出' else '' end) || ")
				/* 203 */ .append(" (case when a.OrderType=1 then '订货' when a.OrderType=2 then '转让' else '' end) BSOrderType ")
				/* 204 */ .append("from T_H_Trade a,T_H_Commodity b ")
				/* 205 */ .append("where a.OrderType=2 and a.ClearDate=b.ClearDate and a.CommodityID=b.CommodityID ")
				/* 206 */ .append("and a.firmID='" + (String) params.get("a.firmID") + "' ")
				.append("and a.ClearDate=to_date('" + (String) params.get("a.ClearDate") + "','yyyy-MM-dd')");
		/* 207 */ sb.append(" order by a.A_TradeNo)");
		/* 208 */ sb.append("where num between " + (String) params.get("startCount") + " and " + (String) params.get("endCount"));
		/* 209 */ this.logger.debug("sql: " + sb.toString());
		/* 210 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> tradeSettleQuery(Map<String, String> params)
	/*     */ {
		/* 219 */ StringBuffer sb = new StringBuffer();
		/* 220 */ sb
				.append("select * from (select  rownum num, to_char(shp.settleprocessdate,'YYYY-MM-DD') settleprocessdate,shp.firmid,shp.commodityid, case when shp.bs_flag=1 then '买' when shp.bs_flag=2 then '卖' else '' end as bs_flag, (shp.HoldQty + shp.GageQty) settleQty, shp.a_HoldNo,shp.openQty,shp.settleMargin,shp.payout, shp.settleFee,shp.settle_PL,shp.SettleAddedTax,shp.Price, shp.HappenMatchTax, shp.SettlePrice from T_SettleHoldPosition shp where shp.firmID='"
						+
						/* 227 */ (String) params.get("shp.firmID") + "' " +
						/* 228 */ "and shp.settleprocessdate=to_date('" + (String) params.get("shp.settleprocessdate") + "','yyyy-MM-dd'))");
		/* 229 */ sb.append("where num between " + (String) params.get("startCount") + " and " + (String) params.get("endCount"));
		/* 230 */ this.logger.debug("sql: " + sb.toString());
		/* 231 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> validGageBillQuery(Map<String, String> params)
	/*     */ {
		/* 239 */ StringBuffer sb = new StringBuffer();
		/*     */
		/* 241 */ System.out.println((String) params.get("a.commodityID"));
		/*     */
		/* 243 */ if (((params.get("a.commodityID") == null ? 1 : 0) | (params.get("a.commodityID") == "" ? 1 : 0)) != 0) {
			/* 244 */ sb.append(" select * from (select  rownum num, a.* from T_ValidGageBill a where firmid = '" +
					/* 245 */ (String) params.get("a.firmID") + "' order by a.commodityid ) ");
			/* 246 */ sb.append("where num between " + (String) params.get("startCount") + " and " + (String) params.get("endCount"));
			/*     */ } else {
			/* 248 */ sb.append(" select * from (select  rownum num, a.* from T_ValidGageBill a where firmid = '" +
					/* 249 */ (String) params.get("a.firmID") + "' and a.commodityID='" + (String) params.get("a.commodityID")
					+ "' order by a.commodityid ) ");
			/* 250 */ sb.append("where num between " + (String) params.get("startCount") + " and " + (String) params.get("endCount"));
			/*     */ }
		/*     */
		/* 253 */ this.logger.debug("sql: " + sb.toString());
		/*     */
		/* 255 */ System.out.println("================");
		/* 256 */ System.out.println(sb.toString());
		/*     */
		/* 258 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> fundDetailQuery(Map<String, String> params)
	/*     */ {
		/* 265 */ StringBuffer sb = new StringBuffer();
		/* 266 */ sb.append("select a.settleFee,a.settlePL,a.t_goodmoney,a.settleMargain,a.penal,tradefee ")
				/* 267 */ .append("from (select max(firmid) firmid,")
				/* 268 */ .append("sum(case when oprcode = '15010' then amount else 0 end) settleFee,")
				/* 269 */ .append(
						"(sum(case when oprcode = '15011' then amount else 0 end) - sum(case when oprcode = '15012' then amount else 0 end)) settlePL,")
				/* 270 */ .append(
						"(sum(case when oprcode = '15009' then amount else 0 end) - sum(case when oprcode = '15008' then amount else 0 end)) t_goodmoney,")
				/* 271 */ .append(
						"(sum(case when oprcode = '15014' then amount else 0 end) - sum(case when oprcode = '15013' then amount else 0 end)) settleMargain,")
				/* 272 */ .append(
						"(sum(case when oprcode = '15017' then amount else 0 end) - sum(case when oprcode = '15018' then amount else 0 end)) penal,")
				/* 273 */ .append("sum(case when oprcode = '15001' then amount else 0 end) tradefee ")
				/* 274 */ .append("from f_fundflow where firmid='" + (String) params.get("a.firmID") + "') a ")/* 275 */ .append("where 1=1  ")
				/* 276 */ .append("and a.firmID='" + (String) params.get("a.firmID") + "' ");
		/* 277 */ this.logger.debug("sql: " + sb.toString());
		/* 278 */ return queryBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List firm_info(String firmID, String traderID, String firmName)
	/*     */ {
		/* 287 */ List firmList = new ArrayList();
		/* 288 */ StringBuffer sb = new StringBuffer();
		/* 289 */ sb.append("select a.lastbalance,a.balance,")
				/* 290 */ .append("nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('11001','11003')),0) inAmount,")
				/* 291 */ .append("nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('11002','11004')),0) outAmount,")
				/* 292 */ .append("nvl((select sum(close_pl) from t_trade where firmid=b.firmid and close_pl is not null),0) close_pl,")
				/* 293 */ .append("nvl((select sum(tradefee) from t_trade where firmid=b.firmid),0) tradefee,")
				/* 294 */ .append("b.ClearMargin,b.clearfl,b.runtimemargin,b.runtimefl,b.ClearAssure,b.MaxOverdraft,b.RuntimeSettleMargin,")
				/* 295 */ .append("nvl((select sum(frozenfunds - unfrozenfunds) from t_orders where firmid = b.firmid),0) orderFrozen,")
				/* 296 */ .append(
						"(a.frozenfunds-nvl(c.frozenfunds,0)) otherFrozen,(a.balance - a.frozenfunds) usefulFund,b.virtualfunds,nvl((select sum(floatingloss) from t_Firmholdsum where firmid = b.firmid),0) PL,b.runtimeassure ")
				/* 297 */ .append("from F_FIRMFUNDS a,T_Firm b, (select firmid,frozenfunds from f_Frozenfunds where moduleid='15' and firmID='"
						+ firmID + "') c ")
				/* 298 */ .append("where a.firmid = b.firmid and a.firmid = c.firmid(+) and b.firmID = '" + firmID + "'");
		/* 299 */ List lst = queryBySql(sb.toString());
		/* 300 */ Map map1 = (Map) lst.get(0);
		/*     */
		/* 303 */ String ids = null;
		/*     */
		/* 305 */ String sql = "select OperateCode from T_Trader where TraderID = '" + traderID + "'";
		/* 306 */ List list = queryBySql(sql);
		/* 307 */ if ((list != null) && (list.size() > 0))
		/*     */ {
			/* 309 */ Map map = (Map) list.get(0);
			/* 310 */ String id = (String) map.get("OperateCode");
			/* 311 */ if (id != null)
			/*     */ {
				/* 313 */ ids = id;
				/*     */ }
				/*     */ else/* 316 */ ids = getCustomersOfMFirm(firmID);
			/*     */ }
			/*     */ else
		/*     */ {
			/* 320 */ ids = getCustomersOfMFirm(firmID);
			/*     */ }
		/*     */
		/* 323 */ map1.put("FirmID", firmID);
		/* 324 */ map1.put("Name", firmName);
		/* 325 */ map1.put("OperateCode", ids);
		/* 326 */ firmList.add(map1);
		/*     */
		/* 328 */ return firmList;
		/*     */ }

	/*     */
	/*     */ private String getCustomersOfMFirm(String firmID)
	/*     */ {
		/* 333 */ List idList = queryBySql("select Code from T_Customer where FirmID='" + firmID + "'");
		/* 334 */ StringBuffer idSB = new StringBuffer();
		/* 335 */ for (Iterator localIterator = idList.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			/*     */
			/* 337 */ Map idmap = (Map) o;
			/* 338 */ idSB.append((String) idmap.get("Code")).append(",");
			/*     */ }
		/* 340 */ String ids = idSB.toString();
		/* 341 */ if ((ids != null) && (!ids.equals("")))
		/*     */ {
			/* 343 */ ids = ids.substring(0, ids.length() - 1);
			/*     */ }
		/* 345 */ return ids;
		/*     */ }
	/*     */ }