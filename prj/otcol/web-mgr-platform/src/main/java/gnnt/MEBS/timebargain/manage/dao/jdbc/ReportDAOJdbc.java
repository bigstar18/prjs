package gnnt.MEBS.timebargain.manage.dao.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import gnnt.MEBS.timebargain.manage.dao.ReportDAO;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.util.DateUtil;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;

public class ReportDAOJdbc extends BaseDAOJdbc implements ReportDAO {
	private Log log = LogFactory.getLog(ReportDAOJdbc.class);

	public List getAlarm(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer(
				"select a.CustomerID,a.Balance,b.CustomerName from T_H_firmFunds a,T_Customer b where a.CustomerID=b.CustomerID and a.ClearDate=trunc(sysdate) ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" order by a.CustomerID");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getHisOrders(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select sysdate,a.A_OrderNo,a.FirmID,a.Price,a.Quantity,a.OrderTime,a.WithdrawTime,a.BS_Flag,a.OrderType,case when a.BS_Flag=1 then '买' when a.BS_Flag=2 then '卖' else '' end as sBS_Flag,")
				.append("case when a.OrderType=1 then '订立' when a.OrderType=2 then '转让' when a.OrderType=3 then '强转' when a.OrderType=4 then '撤单' else '' end as sOrderType,")
				.append("m.Name firmName,a.commodityid from T_H_Orders a, M_firm m ").append("where a.firmID = m.firmID ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" order by a.FirmID");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getHisHoldDetail(StatQuery paramStatQuery) {
		return getHoldDetail(paramStatQuery, "T_HisHoldPosition");
	}

	public Map getAccountUpdate(QueryConditions paramQueryConditions) {
		HashMap localHashMap = new HashMap();
		String str1 = DateUtil.getDate((Date) paramQueryConditions.getConditionValue("a.ClearDate"));
		String str2 = "select nvl(sum(InitFunds),0) InitFunds,nvl(sum(InFunds),0) InFunds,nvl(sum(OutFunds),0) OutFunds,nvl(sum(ClearMargin),0) ClearMargin,nvl(sum(ClearAssure),0) ClearAssure,nvl(sum(ClearFL),0) ClearFL,nvl(sum(ClearSettleMargin),0) ClearSettleMargin,nvl(sum(RuntimeMargin),0) RuntimeMarginnvl(sum(RuntimeAssure),0) RuntimeAssure,nvl(sum(RuntimeFL),0) RuntimeFL,nvl(sum(RuntimeSettleMargin),0) RuntimeSettleMarginnvl(sum(TradeFee),0) TradeFee,nvl(sum(SettleFee),0) SettleFee,nvl(sum(Close_PL),0) Close_PL,nvl(sum(Settle_PL),0) Settle_PL,nvl(sum(SettleMargin),0) SettleMargin,nvl(sum(Sales),0) Sales,nvl(sum(OtherItem),0) OtherItem,nvl(sum(Balance),0) Balance,nvl(sum(ShouldAddFunds),0) ShouldAddFunds,nvl(sum(FirmRights),0) FirmRights from T_Bal_FirmFundsView a where a.ClearDate = to_date('"
				+ str1 + "', 'yyyy-MM-dd')";
		Object[] arrayOfObject = null;
		this.log.debug("sqlN: " + str2);
		List localList = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap1 = (Map) localList.get(0);
			if (localMap1.get("FirmID") != null) {
				localHashMap.put("firmID", localMap1.get("FirmID").toString());
			}
			if (localMap1.get("InitFunds") != null) {
				localHashMap.put("initFunds", new Double(((BigDecimal) localMap1.get("InitFunds")).doubleValue()));
			}
			if (localMap1.get("InFunds") != null) {
				localHashMap.put("inFunds", new Double(((BigDecimal) localMap1.get("InFunds")).doubleValue()));
			}
			if (localMap1.get("OutFunds") != null) {
				localHashMap.put("outFunds", new Double(((BigDecimal) localMap1.get("OutFunds")).doubleValue()));
			}
			if (localMap1.get("ClearMargin") != null) {
				localHashMap.put("clearMargin", new Double(((BigDecimal) localMap1.get("ClearMargin")).doubleValue()));
			}
			if (localMap1.get("ClearAssure") != null) {
				localHashMap.put("clearAssure", new Double(((BigDecimal) localMap1.get("ClearAssure")).doubleValue()));
			}
			if (localMap1.get("ClearFL") != null) {
				localHashMap.put("clearFL", new Double(((BigDecimal) localMap1.get("ClearFL")).doubleValue()));
			}
			if (localMap1.get("ClearSettleMargin") != null) {
				localHashMap.put("clearSettleMargin", new Double(((BigDecimal) localMap1.get("ClearSettleMargin")).doubleValue()));
			}
			if (localMap1.get("RuntimeMargin") != null) {
				localHashMap.put("runtimeMargin", new Double(((BigDecimal) localMap1.get("RuntimeMargin")).doubleValue()));
			}
			if (localMap1.get("RuntimeAssure") != null) {
				localHashMap.put("runtimeAssure", new Double(((BigDecimal) localMap1.get("RuntimeAssure")).doubleValue()));
			}
			if (localMap1.get("RuntimeFL") != null) {
				localHashMap.put("runtimeFL", new Double(((BigDecimal) localMap1.get("RuntimeFL")).doubleValue()));
			}
			if (localMap1.get("RuntimeSettleMargin") != null) {
				localHashMap.put("runtimeSettleMargin", new Double(((BigDecimal) localMap1.get("RuntimeSettleMargin")).doubleValue()));
			}
			if (localMap1.get("TradeFee") != null) {
				localHashMap.put("tradeFee", new Double(((BigDecimal) localMap1.get("TradeFee")).doubleValue()));
			}
			if (localMap1.get("SettleFee") != null) {
				localHashMap.put("settleFee", new Double(((BigDecimal) localMap1.get("SettleFee")).doubleValue()));
			}
			if (localMap1.get("Close_PL") != null) {
				localHashMap.put("close_PL", new Double(((BigDecimal) localMap1.get("Close_PL")).doubleValue()));
			}
			if (localMap1.get("Settle_PL") != null) {
				localHashMap.put("settle_PL", new Double(((BigDecimal) localMap1.get("Settle_PL")).doubleValue()));
			}
			if (localMap1.get("SettleMargin") != null) {
				localHashMap.put("settleMargin", new Double(((BigDecimal) localMap1.get("SettleMargin")).doubleValue()));
			}
			if (localMap1.get("Sales") != null) {
				localHashMap.put("sales", new Double(((BigDecimal) localMap1.get("Sales")).doubleValue()));
			}
			if (localMap1.get("OtherItem") != null) {
				localHashMap.put("otherItem", new Double(((BigDecimal) localMap1.get("OtherItem")).doubleValue()));
			}
			if (localMap1.get("Balance") != null) {
				localHashMap.put("balance", new Double(((BigDecimal) localMap1.get("Balance")).doubleValue()));
			}
			if (localMap1.get("ShouldAddFunds") != null) {
				localHashMap.put("shouldAddFunds", new Double(((BigDecimal) localMap1.get("ShouldAddFunds")).doubleValue()));
			}
			if (localMap1.get("FirmRights") != null) {
				localHashMap.put("firmRights", new Double(((BigDecimal) localMap1.get("FirmRights")).doubleValue()));
			}
		}
		str2 = "select nvl(sum(Quantity),0) zcj from T_H_Trade a ";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str2 = str2 + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		localList = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap2 = (Map) localList.get(0);
			localHashMap.put("zcj", new Long(((BigDecimal) localMap2.get("zcj")).longValue()));
		}
		str2 = "select nvl(sum(a.HoldQty),0) mjccs,nvl(sum(a.HoldMargin),0) mjbzj,nvl(sum(a.FloatingLoss),0) mjfk from T_H_FirmHoldSum a where a.BS_Flag=1";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int j = 0; j < arrayOfObject.length; j++) {
				this.log.debug("params[" + j + "]: " + arrayOfObject[j]);
			}
		}
		localList = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap3 = (Map) localList.get(0);
			localHashMap.put("mjccs", new Long(((BigDecimal) localMap3.get("mjccs")).longValue()));
			localHashMap.put("mjzyzj",
					new Double(Arith.add(((BigDecimal) localMap3.get("mjbzj")).doubleValue(), ((BigDecimal) localMap3.get("mjfk")).doubleValue())));
		}
		str2 = "select nvl(sum(a.HoldQty),0) mcccs,nvl(sum(a.HoldMargin),0) mcbzj,nvl(sum(a.FloatingLoss),0) mcfk from T_H_FirmHoldSum a where BS_Flag=2";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int k = 0; k < arrayOfObject.length; k++) {
				this.log.debug("params[" + k + "]: " + arrayOfObject[k]);
			}
		}
		localList = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap4 = (Map) localList.get(0);
			localHashMap.put("mcccs", new Long(((BigDecimal) localMap4.get("mcccs")).longValue()));
			localHashMap.put("mczyzj",
					new Double(Arith.add(((BigDecimal) localMap4.get("mcbzj")).doubleValue(), ((BigDecimal) localMap4.get("mcfk")).doubleValue())));
		}
		str2 = "select nvl(sum(InFunds),0) InFunds,nvl(sum(OutFunds),0) OutFunds,nvl(sum(TradeFee),0) TradeFee,nvl(sum(Close_PL),0) Close_PL,nvl(sum(ClearAssure),0) ClearAssure,nvl(sum(Balance),0) Balance from T_bal_firmfundsview a ";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str2 = str2 + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int m = 0; m < arrayOfObject.length; m++) {
				this.log.debug("params[" + m + "]: " + arrayOfObject[m]);
			}
		}
		localList = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap5 = (Map) localList.get(0);
			localHashMap.put("InFunds", new Double(((BigDecimal) localMap5.get("InFunds")).doubleValue()));
			localHashMap.put("OutFunds", new Double(((BigDecimal) localMap5.get("OutFunds")).doubleValue()));
			localHashMap.put("TradeFee", new Double(((BigDecimal) localMap5.get("TradeFee")).doubleValue()));
			localHashMap.put("Close_PL", new Double(((BigDecimal) localMap5.get("Close_PL")).doubleValue()));
			localHashMap.put("ClearAssure", new Double(((BigDecimal) localMap5.get("ClearAssure")).doubleValue()));
			localHashMap.put("Balance", new Double(((BigDecimal) localMap5.get("Balance")).doubleValue()));
		}
		localHashMap.put("ClearDate", paramQueryConditions.getConditionValue("a.ClearDate"));
		return localHashMap;
	}

	public Map getAccountUpdate(QueryConditions paramQueryConditions, String paramString) {
		HashMap localHashMap = new HashMap();
		String str1 = paramString;
		String str2 = "select nvl(sum(InitFunds),0) InitFunds,nvl(sum(InFunds),0) InFunds,nvl(sum(OutFunds),0) OutFunds,nvl(sum(ClearMargin),0) ClearMargin,nvl(sum(ClearAssure),0) ClearAssure,nvl(sum(ClearFL),0) ClearFL,nvl(sum(ClearSettleMargin),0) ClearSettleMargin,nvl(sum(RuntimeMargin),0) RuntimeMargin,nvl(sum(RuntimeAssure),0) RuntimeAssure,nvl(sum(RuntimeFL),0) RuntimeFL,nvl(sum(RuntimeSettleMargin),0) RuntimeSettleMargin,nvl(sum(TradeFee),0) TradeFee,nvl(sum(SettleFee),0) SettleFee,nvl(sum(Close_PL),0) Close_PL,nvl(sum(Settle_PL),0) Settle_PL,nvl(sum(SettleMargin),0) SettleMargin,nvl(sum(Sales),0) Sales,nvl(sum(OtherItem),0) OtherItem,nvl(sum(Balance),0) Balance,nvl(sum(ShouldAddFunds),0) ShouldAddFunds,nvl(sum(FirmRights),0) FirmRights from T_Bal_FirmFundsView a where a.ClearDate = to_date('"
				+ str1 + "', 'yyyy-MM-dd')";
		Object[] arrayOfObject = null;
		this.log.debug("sql: " + str2);
		List localList1 = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localMap1 = (Map) localList1.get(0);
			if (localMap1.get("FirmID") != null) {
				localHashMap.put("firmID", localMap1.get("FirmID").toString());
			}
			if (localMap1.get("InitFunds") != null) {
				localHashMap.put("initFunds", new Double(((BigDecimal) localMap1.get("InitFunds")).doubleValue()));
			}
			if (localMap1.get("InFunds") != null) {
				localHashMap.put("inFunds", new Double(((BigDecimal) localMap1.get("InFunds")).doubleValue()));
			}
			if (localMap1.get("OutFunds") != null) {
				localHashMap.put("outFunds", new Double(((BigDecimal) localMap1.get("OutFunds")).doubleValue()));
			}
			if (localMap1.get("ClearMargin") != null) {
				localHashMap.put("clearMargin", new Double(((BigDecimal) localMap1.get("ClearMargin")).doubleValue()));
			}
			if (localMap1.get("ClearAssure") != null) {
				localHashMap.put("clearAssure", new Double(((BigDecimal) localMap1.get("ClearAssure")).doubleValue()));
			}
			if (localMap1.get("ClearFL") != null) {
				localHashMap.put("clearFL", new Double(((BigDecimal) localMap1.get("ClearFL")).doubleValue()));
			}
			if (localMap1.get("ClearSettleMargin") != null) {
				localHashMap.put("clearSettleMargin", new Double(((BigDecimal) localMap1.get("ClearSettleMargin")).doubleValue()));
			}
			if (localMap1.get("RuntimeMargin") != null) {
				localHashMap.put("runtimeMargin", new Double(((BigDecimal) localMap1.get("RuntimeMargin")).doubleValue()));
			}
			if (localMap1.get("RuntimeAssure") != null) {
				localHashMap.put("runtimeAssure", new Double(((BigDecimal) localMap1.get("RuntimeAssure")).doubleValue()));
			}
			if (localMap1.get("RuntimeFL") != null) {
				localHashMap.put("runtimeFL", new Double(((BigDecimal) localMap1.get("RuntimeFL")).doubleValue()));
			}
			if (localMap1.get("RuntimeSettleMargin") != null) {
				localHashMap.put("runtimeSettleMargin", new Double(((BigDecimal) localMap1.get("RuntimeSettleMargin")).doubleValue()));
			}
			if (localMap1.get("TradeFee") != null) {
				localHashMap.put("tradeFee", new Double(((BigDecimal) localMap1.get("TradeFee")).doubleValue()));
			}
			if (localMap1.get("SettleFee") != null) {
				localHashMap.put("settleFee", new Double(((BigDecimal) localMap1.get("SettleFee")).doubleValue()));
			}
			if (localMap1.get("Close_PL") != null) {
				localHashMap.put("close_PL", new Double(((BigDecimal) localMap1.get("Close_PL")).doubleValue()));
			}
			if (localMap1.get("Settle_PL") != null) {
				localHashMap.put("settle_PL", new Double(((BigDecimal) localMap1.get("Settle_PL")).doubleValue()));
			}
			if (localMap1.get("SettleMargin") != null) {
				localHashMap.put("settleMargin", new Double(((BigDecimal) localMap1.get("SettleMargin")).doubleValue()));
			}
			if (localMap1.get("Sales") != null) {
				localHashMap.put("sales", new Double(((BigDecimal) localMap1.get("Sales")).doubleValue()));
			}
			if (localMap1.get("OtherItem") != null) {
				localHashMap.put("otherItem", new Double(((BigDecimal) localMap1.get("OtherItem")).doubleValue()));
			}
			if (localMap1.get("Balance") != null) {
				localHashMap.put("balance", new Double(((BigDecimal) localMap1.get("Balance")).doubleValue()));
			}
			if (localMap1.get("ShouldAddFunds") != null) {
				localHashMap.put("shouldAddFunds", new Double(((BigDecimal) localMap1.get("ShouldAddFunds")).doubleValue()));
			}
			if (localMap1.get("FirmRights") != null) {
				localHashMap.put("firmRights", new Double(((BigDecimal) localMap1.get("FirmRights")).doubleValue()));
			}
		}
		str2 = "select nvl(sum(Quantity),0) zcj from T_H_Trade a ";
		arrayOfObject = null;
		if ((paramString != null) && (!"".equals(paramString))) {
			str2 = str2 + " where a.clearDate=to_date('" + paramString + "','yyyy-MM-dd')";
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		localList1 = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localObject1 = (Map) localList1.get(0);
			localHashMap.put("zcj", new Long(((BigDecimal) ((Map) localObject1).get("zcj")).longValue()));
		}
		str2 = "select nvl(sum(a.HoldQty),0) mjccs,nvl(sum(a.HoldMargin),0) mjbzj from T_H_FirmHoldSum a where a.BS_Flag=1  ";
		Object localObject1 = "select nvl(sum(a.FloatingLoss),0) mjfk from T_H_FirmHoldSum a where a.BS_Flag=1 and  a.FloatingLoss<0";
		arrayOfObject = null;
		if ((paramString != null) && (!"".equals(paramString))) {
			str2 = str2 + " and a.clearDate=to_date('" + paramString + "','yyyy-MM-dd')";
			localObject1 = (String) localObject1 + " and a.clearDate=to_date('" + paramString + "','yyyy-MM-dd')";
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int j = 0; j < arrayOfObject.length; j++) {
				this.log.debug("params[" + j + "]: " + arrayOfObject[j]);
			}
		}
		localList1 = getJdbcTemplate().queryForList(str2, arrayOfObject);
		List localList2 = getJdbcTemplate().queryForList((String) localObject1, arrayOfObject);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localObject2 = (Map) localList1.get(0);
			localHashMap.put("mjccs", new Long(((BigDecimal) ((Map) localObject2).get("mjccs")).longValue()));
			Map localMap2 = (Map) localList2.get(0);
			double d1 = -1.0D * ((BigDecimal) localMap2.get("mjfk")).doubleValue();
			localHashMap.put("mjzyzj", new Double(((BigDecimal) ((Map) localObject2).get("mjbzj")).doubleValue() + d1));
		}
		str2 = "select nvl(sum(a.HoldQty),0) mcccs,nvl(sum(a.HoldMargin),0) mcbzj from T_H_FirmHoldSum a where BS_Flag=2 ";
		Object localObject2 = "select nvl(sum(a.FloatingLoss),0) mcfk from T_H_FirmHoldSum a where a.BS_Flag=2 and  a.FloatingLoss<0";
		arrayOfObject = null;
		if ((paramString != null) && (!"".equals(paramString))) {
			str2 = str2 + " and a.clearDate=to_date('" + paramString + "','yyyy-MM-dd')";
			localObject2 = (String) localObject2 + " and a.clearDate=to_date('" + paramString + "','yyyy-MM-dd')";
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int k = 0; k < arrayOfObject.length; k++) {
				this.log.debug("params[" + k + "]: " + arrayOfObject[k]);
			}
		}
		localList1 = getJdbcTemplate().queryForList(str2, arrayOfObject);
		List localList3 = getJdbcTemplate().queryForList((String) localObject2, arrayOfObject);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localMap3 = (Map) localList1.get(0);
			localHashMap.put("mcccs", new Long(((BigDecimal) localMap3.get("mcccs")).longValue()));
			Map localMap5 = (Map) localList3.get(0);
			double d2 = -1.0D * ((BigDecimal) localMap5.get("mcfk")).doubleValue();
			localHashMap.put("mczyzj", new Double(((BigDecimal) localMap3.get("mcbzj")).doubleValue() + d2));
		}
		str2 = "select nvl(sum(InFunds),0) InFunds,nvl(sum(OutFunds),0) OutFunds,nvl(sum(TradeFee),0) TradeFee,nvl(sum(Close_PL),0) Close_PL,nvl(sum(RuntimeAssure),0) ClearAssure,nvl(sum(Balance),0) Balance,nvl(sum(SettleMargin),0) SettleMargin,nvl(sum(SettleFee),0) SettleFee,nvl(sum(Settle_PL),0) Settle_PL from T_bal_firmfundsview a ";
		arrayOfObject = null;
		if ((paramString != null) && (!"".equals(paramString))) {
			str2 = str2 + " where a.clearDate=to_date('" + paramString + "','yyyy-MM-dd')";
		}
		this.log.debug("sql: " + str2);
		if (arrayOfObject != null) {
			for (int m = 0; m < arrayOfObject.length; m++) {
				this.log.debug("params[" + m + "]: " + arrayOfObject[m]);
			}
		}
		localList1 = getJdbcTemplate().queryForList(str2, arrayOfObject);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localMap4 = (Map) localList1.get(0);
			localHashMap.put("InFunds", new Double(((BigDecimal) localMap4.get("InFunds")).doubleValue()));
			localHashMap.put("OutFunds", new Double(((BigDecimal) localMap4.get("OutFunds")).doubleValue()));
			localHashMap.put("TradeFee", new Double(((BigDecimal) localMap4.get("TradeFee")).doubleValue()));
			localHashMap.put("Close_PL", new Double(((BigDecimal) localMap4.get("Close_PL")).doubleValue()));
			localHashMap.put("ClearAssure", new Double(((BigDecimal) localMap4.get("ClearAssure")).doubleValue()));
			localHashMap.put("Balance", new Double(((BigDecimal) localMap4.get("Balance")).doubleValue()));
			localHashMap.put("SettleMargin", new Double(((BigDecimal) localMap4.get("SettleMargin")).doubleValue()));
			localHashMap.put("SettleFee", new Double(((BigDecimal) localMap4.get("SettleFee")).doubleValue()));
			localHashMap.put("Settle_PL", new Double(((BigDecimal) localMap4.get("Settle_PL")).doubleValue()));
		}
		localHashMap.put("ClearDate", paramQueryConditions.getConditionValue("a.ClearDate"));
		return localHashMap;
	}

	public List getAccountList(QueryConditions paramQueryConditions) {
		String str = "select to_char(a.ClearDate,'yyyy-MM-dd') ClearDate,nvl(sum(InitFunds),0) brjy,nvl(sum(Balance),0) Balance,nvl(sum(InFunds),0) InFunds,nvl(sum(OutFunds),0) OutFunds,nvl(sum(TradeFee),0) TradeFee,nvl(sum(Close_PL),0) Close_PL,nvl(sum(SettleMargin),0) SettleMargin,nvl(sum(SettleFee),0) SettleFee,nvl(sum(Settle_PL),0) Settle_PL from T_bal_firmfundsview a  where 1=1";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str);
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		str = str + " group by a.ClearDate order by a.ClearDate desc";
		return getJdbcTemplate().queryForList(str, arrayOfObject);
	}

	public Map getAccount(QueryConditions paramQueryConditions) {
		HashMap localHashMap = new HashMap();
		String str = "select nvl(sum(ClearMargin),0) zbzj,nvl(sum(Balance),0) brjy from F_H_FIRMFUNDS a ";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str);
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap1 = (Map) localList.get(0);
			localHashMap.put("zbzj", new Double(((BigDecimal) localMap1.get("zbzj")).doubleValue()));
			localHashMap.put("brjy", new Double(((BigDecimal) localMap1.get("brjy")).doubleValue()));
		}
		str = "select nvl(sum(Quantity),0) zcj,nvl(sum(TradeFee),0) zsxf,nvl(sum(Close_PL),0) drzyk  from T_H_Trade a ";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str);
		if (arrayOfObject != null) {
			for (int j = 0; j < arrayOfObject.length; j++) {
				this.log.debug("params[" + j + "]: " + arrayOfObject[j]);
			}
		}
		localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap2 = (Map) localList.get(0);
			localHashMap.put("zcj", new Long(((BigDecimal) localMap2.get("zcj")).longValue()));
			localHashMap.put("zsxf", new Double(((BigDecimal) localMap2.get("zsxf")).doubleValue()));
			localHashMap.put("drzyk", new Double(((BigDecimal) localMap2.get("drzyk")).doubleValue()));
		}
		str = "select nvl(sum(a.HoldQty),0) mjccs,nvl(sum(a.HoldMargin),0) mjbzj,nvl(sum(a.FloatingLoss),0) mjfk from T_H_CustomerHoldSum a where a.BS_Flag=1";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str);
		if (arrayOfObject != null) {
			for (int k = 0; k < arrayOfObject.length; k++) {
				this.log.debug("params[" + k + "]: " + arrayOfObject[k]);
			}
		}
		localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap3 = (Map) localList.get(0);
			localHashMap.put("mjccs", new Long(((BigDecimal) localMap3.get("mjccs")).longValue()));
			localHashMap.put("mjzyzj",
					new Double(Arith.add(((BigDecimal) localMap3.get("mjbzj")).doubleValue(), ((BigDecimal) localMap3.get("mjfk")).doubleValue())));
		}
		str = "select nvl(sum(a.HoldQty),0) mcccs,nvl(sum(a.HoldMargin),0) mcbzj,nvl(sum(a.FloatingLoss),0) mcfk from T_H_CustomerHoldSum a where BS_Flag=2";
		arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		this.log.debug("sql: " + str);
		if (arrayOfObject != null) {
			for (int m = 0; m < arrayOfObject.length; m++) {
				this.log.debug("params[" + m + "]: " + arrayOfObject[m]);
			}
		}
		localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap4 = (Map) localList.get(0);
			localHashMap.put("mcccs", new Long(((BigDecimal) localMap4.get("mcccs")).longValue()));
			localHashMap.put("mczyzj",
					new Double(Arith.add(((BigDecimal) localMap4.get("mcbzj")).doubleValue(), ((BigDecimal) localMap4.get("mcfk")).doubleValue())));
		}
		localHashMap.put("ClearDate", paramQueryConditions.getConditionValue("a.ClearDate"));
		return localHashMap;
	}

	public List getBreed(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select a.BuyQuantity,a.BuyEvenPrice,b.SellQuantity,b.SellEvenPrice,a.Commodityid  from ")
				.append("(select ClearDate,Commodityid,nvl(sum(Quantity),0) BuyQuantity, nvl(sum(Price * Quantity) / sum(Quantity),0) BuyEvenPrice from T_H_Trade where BS_Flag = 1 and Quantity <> 0 group by ClearDate,Commodityid) a,")
				.append("(select ClearDate,Commodityid,nvl(sum(Quantity),0) SellQuantity, nvl(sum(Price * Quantity) / sum(Quantity),0) SellEvenPrice from T_H_Trade where BS_Flag = 2 and Quantity <> 0 group by ClearDate,Commodityid) b,")
				.append("(select distinct ClearDate,Commodityid from T_H_Trade  group by ClearDate,Commodityid) e ")
				.append("where e.ClearDate=a.ClearDate(+) and e.ClearDate=b.ClearDate(+) and e.Commodityid=a.Commodityid(+) and e.Commodityid=b.Commodityid(+) ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" order by a.Commodityid");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getFeeMonth(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select a.CustomerID,max(b.CustomerName) CustomerName,sum(a.TradeFee) TradeFee,sysdate ")
				.append("from T_H_Trade a,T_Customer b where a.CustomerID = b.CustomerID ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" group by a.CustomerID order by a.CustomerID");
		this.log.debug("getFeeMonth sql: " + localStringBuffer.toString());
		String str = localStringBuffer.toString();
		str = str.replaceAll("a.ClearDate", "to_char(a.ClearDate,'yyyyMM')");
		localStringBuffer = new StringBuffer(str);
		this.log.debug("getFeeMonth1 sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getFeeMonth1(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select a.FirmID,max(c.Name) FirmName,sum(a.TradeFee) TradeFee,sysdate ")
				.append("from T_H_Trade a,M_firm c where a.firmID = c.firmID ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" group by a.FirmID order by a.FirmID");
		this.log.debug("getFeeMonth sql: " + localStringBuffer.toString());
		this.log.debug("getFeeMonth1 sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getCmdtyHold(StatQuery paramStatQuery) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select distinct(a.commodityId) commodityId,d.BuyQuantity,d.BuyEvenPrice,b.SellQuantity,b.SellEvenPrice from ")
				.append("(select ClearDate,commodityId,nvl(sum(HoldQty),0) BuyQuantity, nvl(sum(EvenPrice * HoldQty) / sum(HoldQty),0) BuyEvenPrice from T_H_FirmHoldSum where BS_Flag = 1 and HoldQty <> 0 group by ClearDate,commodityId) d,")
				.append("(select ClearDate,commodityId,nvl(sum(HoldQty),0) SellQuantity, nvl(sum(EvenPrice * HoldQty) / sum(HoldQty),0) SellEvenPrice from T_H_FirmHoldSum where BS_Flag = 2 and HoldQty <> 0 group by ClearDate,commodityId) b,")
				.append("T_H_FirmHoldSum a ").append("where a.ClearDate=d.ClearDate(+) and a.ClearDate=b.ClearDate(+) ")
				.append(" and a.commodityId=d.commodityId(+) and a.commodityId=b.commodityId(+) ");
		ArrayList localArrayList = new ArrayList();
		if (paramStatQuery != null) {
			if ((paramStatQuery.getCommodityID() != null) && (!"".equals(paramStatQuery.getCommodityID()))) {
				localStringBuffer.append(" and a.commodityID like ?");
				localArrayList.add(paramStatQuery.getCommodityID());
			}
			if (paramStatQuery.getBeginDate() != null) {
				localStringBuffer.append(" and a.ClearDate = ?");
				localArrayList.add(paramStatQuery.getBeginDate());
			}
		}
		Object[] arrayOfObject = null;
		localStringBuffer.append(" order by commodityId");
		if ((localArrayList != null) && (localArrayList.size() > 0)) {
			arrayOfObject = localArrayList.toArray();
		}
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getReportByTypeView(QueryConditions paramQueryConditions, String paramString1, String paramString2) {
		ArrayList localArrayList = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer();
		Object[] arrayOfObject = null;
		Date localDate = (Date) paramQueryConditions.getConditionValue("a.ClearDate");
		String str1 = (String) paramQueryConditions.getConditionValue("a.FirmID", ">=");
		String str2 = (String) paramQueryConditions.getConditionValue("a.FirmID", "<=");
		String str3 = (String) paramQueryConditions.getConditionValue("c.GroupID");
		Object localObject1 = null;
		Object localObject2 = null;
		localStringBuffer.append(
				"select FirmID,max(FirmName) FirmName,sum(Balance),sum(InitFunds),sum(InFunds),sum(OutFunds),sum(Close_PL),sum(ClearFL+ClearMargin),sum(RuntimeMargin+RuntimeFL),sum(TradeFee) from t_bal_firmfundsview a ");
		if (!"".equals(paramString2)) {
			localStringBuffer.append(" where a.FirmID in (" + paramString2 + ")");
			localStringBuffer.append(" and a.ClearDate =? ");
		} else {
			localStringBuffer.append(" where a.ClearDate =? ");
		}
		localStringBuffer.append(" group by a.FirmID order by a.FirmID ");
		arrayOfObject = new Object[] { localDate };
		this.log.debug("sql: " + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		this.log.debug("===>custList.size:" + localList.size());
		return localList;
	}

	public String getReportByTypeM_CustomerID(String paramString) {
		String str = "";
		ArrayList localArrayList = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer();
		Object[] arrayOfObject = null;
		localStringBuffer.append("select M_CustomerID from T_CM_CustomerMap a where customerID='" + paramString + "'");
		this.log.debug("sql: " + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		if (localList.size() > 0) {
			Map localMap = (Map) localList.get(0);
			str = (String) localMap.get("M_CustomerID");
		}
		this.log.debug("===>custList.size:" + localList.size());
		return str;
	}

	public List getReportByTypeTrade(QueryConditions paramQueryConditions, String paramString1, String paramString2) {
		Object localObject = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer();
		Object[] arrayOfObject = null;
		Date localDate = (Date) paramQueryConditions.getConditionValue("a.ClearDate");
		String str1 = (String) paramQueryConditions.getConditionValue("a.FirmID", ">=");
		String str2 = (String) paramQueryConditions.getConditionValue("a.FirmID", "<=");
		String str3 = (String) paramQueryConditions.getConditionValue("c.GroupID");
		String str4 = null;
		String str5 = null;
		localStringBuffer = new StringBuffer();
		localStringBuffer.append(
				"select a.A_TradeNo, a.CommodityID, nvl(to_char(b.OrderTime,'HH24miss'),'&nbsp;') OrderTime, to_char(a.TradeTime,'HH24miss') TradeTime, a.Price aPrice, a.Quantity,nvl(a.HoldPrice,0) bPrice,nvl(to_char(a.HoldTime,'HHmmss'),'无') HoldTime,Close_PL,(case when a.OrderType=1 then '订立' when a.OrderType=2 then '转让' when a.OrderType=3 then '强转' else '撤单' end )OrderType,case when a.BS_Flag=1 then '买' when a.BS_Flag=2 then '卖' else '' end BS_Flag,f.BreedName ");
		localStringBuffer.append("from T_H_Trade a, T_H_Orders b, T_Commodity d,T_A_Breed f  where  ")
				.append("  a.A_OrderNo = b.A_OrderNo(+) and a.ClearDate = b.ClearDate(+) and a.CommodityID=d.CommodityID  and d.BreedID=f.BreedID")
				.append("  and a.FirmID = ? ");
		if ((paramString1 == null) || (paramString1.equals(""))) {
			arrayOfObject = new Object[] { paramString2 };
		} else if (paramString1.equals("day")) {
			str4 = DateUtil.formatDate(localDate, "yyyy-MM-dd");
			localStringBuffer.append("and to_char(a.ClearDate,'yyyy-MM-dd')=? ");
			arrayOfObject = new Object[] { paramString2, str4 };
		} else if (paramString1.equals("week")) {
			str4 = DateUtil.getFirstWeekDay(localDate);
			str5 = DateUtil.getLastWeekDay(localDate);
			localStringBuffer.append("and a.ClearDate >= to_date(?, 'yyyy-MM-dd') and a.ClearDate <= to_date(?, 'yyyy-MM-dd') ");
			arrayOfObject = new Object[] { paramString2, str4, str5 };
		} else if (paramString1.equals("month")) {
			str4 = DateUtil.formatDate(localDate, "yyyy-MM");
			localStringBuffer.append("and to_char(a.ClearDate,'yyyy-MM')=? ");
			arrayOfObject = new Object[] { paramString2, str4 };
		}
		localStringBuffer.append("order by a.ClearDate,a.A_TradeNo ");
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getReportByTypeHold(QueryConditions paramQueryConditions, String paramString1, String paramString2) {
		Object localObject = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer();
		Object[] arrayOfObject = null;
		Date localDate = (Date) paramQueryConditions.getConditionValue("a.ClearDate");
		String str1 = (String) paramQueryConditions.getConditionValue("a.FirmID", ">=");
		String str2 = (String) paramQueryConditions.getConditionValue("a.FirmID", "<=");
		String str3 = (String) paramQueryConditions.getConditionValue("c.GroupID");
		String str4 = null;
		String str5 = null;
		localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select  a.CommodityID,case when a.BS_Flag=1 then '买' when a.BS_Flag=2 then '卖' else '' end BS_Flag,a.EvenPrice,b.Price,a.HoldQty,")
				.append("nvl((a.HoldMargin+(case when a.FloatingLoss>=0 then 0 else -a.FloatingLoss end)),0) ShouldFee,f.BreedName ")
				.append("from T_H_FirmHoldSum a,T_H_Quotation b,T_Commodity d,T_A_breed f ")
				.append("where  a.CommodityID = b.CommodityID and a.ClearDate = b.ClearDate and a.CommodityID=d.CommodityID and d.BreedID=f.BreedID ")
				.append("and a.FirmID = ? ");
		if ((paramString1 == null) || (paramString1.equals(""))) {
			arrayOfObject = new Object[] { paramString2 };
		} else if (paramString1.equals("day")) {
			str4 = DateUtil.formatDate(localDate, "yyyy-MM-dd");
			localStringBuffer.append("and to_char(a.ClearDate,'yyyy-MM-dd')=? ");
			arrayOfObject = new Object[] { paramString2, str4 };
		} else if (paramString1.equals("week")) {
			str4 = DateUtil.getFirstWeekDay(localDate);
			str5 = DateUtil.getLastWeekDay(localDate);
			localStringBuffer.append(
					"and to_char(a.ClearDate,'yyyyMMdd') in (select max(to_char(ClearDate,'yyyyMMdd')) from T_H_FirmHoldSum where ClearDate >= to_date(?, 'yyyy-MM-dd') and ClearDate <= to_date(?, 'yyyy-MM-dd')) ");
			arrayOfObject = new Object[] { paramString2, str4, str5 };
		} else if (paramString1.equals("month")) {
			str4 = DateUtil.formatDate(localDate, "yyyy-MM");
			localStringBuffer.append(
					"and to_char(a.ClearDate,'yyyyMMdd') in (select max(to_char(ClearDate,'yyyyMMdd')) from T_H_FirmHoldSum where to_char(ClearDate,'yyyy-MM')=?)  ");
			arrayOfObject = new Object[] { paramString2, str4 };
		}
		localStringBuffer.append("order by a.ClearDate ");
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getReportByTypeMoney(QueryConditions paramQueryConditions, String paramString1, String paramString2) {
		Object localObject1 = new ArrayList();
		Object localObject2 = new ArrayList();
		Object localObject3 = new ArrayList();
		ArrayList localArrayList = new ArrayList();
		StringBuffer localStringBuffer1 = new StringBuffer();
		Object[] arrayOfObject = null;
		Date localDate = (Date) paramQueryConditions.getConditionValue("a.ClearDate");
		String str1 = (String) paramQueryConditions.getConditionValue("a.FirmID", ">=");
		String str2 = (String) paramQueryConditions.getConditionValue("a.FirmID", "<=");
		String str3 = (String) paramQueryConditions.getConditionValue("c.GroupID");
		String str4 = null;
		String str5 = null;
		localStringBuffer1 = new StringBuffer();
		localStringBuffer1.append("select InitFunds,ClearMargin+ClearFL a1,ClearAssure from T_bal_firmfundsview a ");
		localStringBuffer1.append("where a.FirmID = ? ");
		StringBuffer localStringBuffer2 = new StringBuffer();
		localStringBuffer2.append(
				"select sum(InFunds) InFunds,sum(OutFunds) OutFunds,sum(Close_PL) Close_PL,sum(TradeFee) TradeFee from T_bal_firmfundsview a ");
		localStringBuffer2.append("where a.FirmID = ? ");
		StringBuffer localStringBuffer3 = new StringBuffer();
		localStringBuffer3.append(
				"select Balance,RuntimeFL+RuntimeMargin a2,RuntimeAssure,SettleMargin,SettleFee,Settle_PL,Sales,ClearSettleMargin,RuntimeSettleMargin,FirmRights,OtherItem from T_bal_firmfundsview a ");
		localStringBuffer3.append("where a.FirmID = ? ");
		if ((paramString1 == null) || (paramString1.equals(""))) {
			arrayOfObject = new Object[] { paramString2 };
		} else if (paramString1.equals("day")) {
			str4 = DateUtil.formatDate(localDate, "yyyy-MM-dd");
			localStringBuffer1.append("and to_char(a.ClearDate,'yyyy-MM-dd')=? ");
			localStringBuffer2.append("and to_char(a.ClearDate,'yyyy-MM-dd')=? ");
			localStringBuffer3.append("and to_char(a.ClearDate,'yyyy-MM-dd')=? ");
			arrayOfObject = new Object[] { paramString2, str4 };
		} else if (paramString1.equals("week")) {
			str4 = DateUtil.getFirstWeekDay(localDate);
			str5 = DateUtil.getLastWeekDay(localDate);
			localStringBuffer1.append(
					"and to_char(a.ClearDate,'yyyyMMdd') in (select min(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where ClearDate >= to_date(?, 'yyyy-MM-dd') and ClearDate <= to_date(?, 'yyyy-MM-dd')) ");
			localStringBuffer2.append("and a.ClearDate >= to_date(?, 'yyyy-MM-dd') and a.ClearDate <= to_date(?, 'yyyy-MM-dd') ");
			localStringBuffer3.append(
					"and to_char(a.ClearDate,'yyyyMMdd') in (select max(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where ClearDate >= to_date(?, 'yyyy-MM-dd') and ClearDate <= to_date(?, 'yyyy-MM-dd')) ");
			arrayOfObject = new Object[] { paramString2, str4, str5 };
		} else if (paramString1.equals("month")) {
			str4 = DateUtil.formatDate(localDate, "yyyy-MM");
			localStringBuffer1.append(
					"and to_char(a.ClearDate,'yyyyMMdd') in (select min(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where to_char(ClearDate,'yyyy-MM')=?)  ");
			localStringBuffer2.append("and to_char(ClearDate,'yyyy-MM')=?  ");
			localStringBuffer3.append(
					"and to_char(a.ClearDate,'yyyyMMdd') in (select max(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where to_char(ClearDate,'yyyy-MM')=?)  ");
			arrayOfObject = new Object[] { paramString2, str4 };
		}
		localStringBuffer1.append("order by a.ClearDate ");
		this.log.debug("sb sql: " + localStringBuffer1.toString());
		this.log.debug("sb1 sql: " + localStringBuffer2.toString());
		this.log.debug("sb2 sql: " + localStringBuffer3.toString());
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		localObject1 = getJdbcTemplate().queryForList(localStringBuffer1.toString(), arrayOfObject);
		localObject2 = getJdbcTemplate().queryForList(localStringBuffer2.toString(), arrayOfObject);
		localObject3 = getJdbcTemplate().queryForList(localStringBuffer3.toString(), arrayOfObject);
		HashMap localHashMap = new HashMap();
		if (((List) localObject1).size() > 0) {
			localHashMap.put("InitFund", (BigDecimal) ((Map) ((List) localObject1).get(0)).get("InitFunds") != null
					? (BigDecimal) ((Map) ((List) localObject1).get(0)).get("InitFunds") : new BigDecimal("0.00"));
			localHashMap.put("ClearFee", (BigDecimal) ((Map) ((List) localObject1).get(0)).get("a1") != null
					? (BigDecimal) ((Map) ((List) localObject1).get(0)).get("a1") : new BigDecimal("0.00"));
			localHashMap.put("ClearAssure", (BigDecimal) ((Map) ((List) localObject1).get(0)).get("ClearAssure") != null
					? (BigDecimal) ((Map) ((List) localObject1).get(0)).get("ClearAssure") : new BigDecimal("0.00"));
		} else {
			localHashMap.put("InitFund", new BigDecimal("0.00"));
			localHashMap.put("ClearFee", new BigDecimal("0.00"));
			localHashMap.put("ClearAssure", new BigDecimal("0.00"));
		}
		if (((List) localObject2).size() > 0) {
			localHashMap.put("InFund", (BigDecimal) ((Map) ((List) localObject2).get(0)).get("InFunds") != null
					? (BigDecimal) ((Map) ((List) localObject2).get(0)).get("InFunds") : new BigDecimal("0.00"));
			localHashMap.put("OutFund", (BigDecimal) ((Map) ((List) localObject2).get(0)).get("OutFunds") != null
					? (BigDecimal) ((Map) ((List) localObject2).get(0)).get("OutFunds") : new BigDecimal("0.00"));
			localHashMap.put("Close_PL", (BigDecimal) ((Map) ((List) localObject2).get(0)).get("Close_PL") != null
					? (BigDecimal) ((Map) ((List) localObject2).get(0)).get("Close_PL") : new BigDecimal("0.00"));
			localHashMap.put("TradeFee", (BigDecimal) ((Map) ((List) localObject2).get(0)).get("TradeFee") != null
					? (BigDecimal) ((Map) ((List) localObject2).get(0)).get("TradeFee") : new BigDecimal("0.00"));
		} else {
			localHashMap.put("InFund", new BigDecimal("0.00"));
			localHashMap.put("OutFund", new BigDecimal("0.00"));
			localHashMap.put("Close_PL", new BigDecimal("0.00"));
			localHashMap.put("TradeFee", new BigDecimal("0.00"));
		}
		if (((List) localObject3).size() > 0) {
			localHashMap.put("RuntimeFee", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("a2") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("a2") : new BigDecimal("0.00"));
			localHashMap.put("LastFund", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("Balance") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("Balance") : new BigDecimal("0.00"));
			localHashMap.put("RuntimeAssure", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("RuntimeAssure") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("RuntimeAssure") : new BigDecimal("0.00"));
			localHashMap.put("SettleMargin", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("SettleMargin") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("SettleMargin") : new BigDecimal("0.00"));
			localHashMap.put("SettleFee", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("SettleFee") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("SettleFee") : new BigDecimal("0.00"));
			localHashMap.put("Settle_PL", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("Settle_PL") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("Settle_PL") : new BigDecimal("0.00"));
			localHashMap.put("Sales", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("Sales") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("Sales") : new BigDecimal("0.00"));
			localHashMap.put("ClearSettleMargin", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("ClearSettleMargin") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("ClearSettleMargin") : new BigDecimal("0.00"));
			localHashMap.put("RuntimeSettleMargin", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("RuntimeSettleMargin") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("RuntimeSettleMargin") : new BigDecimal("0.00"));
			localHashMap.put("FirmRights", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("FirmRights") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("FirmRights") : new BigDecimal("0.00"));
			localHashMap.put("OtherItem", (BigDecimal) ((Map) ((List) localObject3).get(0)).get("OtherItem") != null
					? (BigDecimal) ((Map) ((List) localObject3).get(0)).get("OtherItem") : new BigDecimal("0.00"));
		} else {
			localHashMap.put("RuntimeFee", new BigDecimal("0.00"));
			localHashMap.put("LastFund", new BigDecimal("0.00"));
			localHashMap.put("RuntimeAssure", new BigDecimal("0.00"));
			localHashMap.put("SettleMargin", new BigDecimal("0.00"));
			localHashMap.put("SettleFee", new BigDecimal("0.00"));
			localHashMap.put("Settle_PL", new BigDecimal("0.00"));
			localHashMap.put("Sales", new BigDecimal("0.00"));
			localHashMap.put("ClearSettleMargin", new BigDecimal("0.00"));
			localHashMap.put("RuntimeSettleMargin", new BigDecimal("0.00"));
			localHashMap.put("FirmRights", new BigDecimal("0.00"));
			localHashMap.put("OtherItem", new BigDecimal("0.00"));
		}
		localArrayList.add(localHashMap);
		return localArrayList;
	}

	private List getHoldDetail(StatQuery paramStatQuery, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select a.* from T_H_HoldPosition a where 1=1 ");
		ArrayList localArrayList = new ArrayList();
		if (paramStatQuery != null) {
			if ((paramStatQuery.getFirmID() != null) && (!"".equals(paramStatQuery.getFirmID()))) {
				localStringBuffer.append(" and a.firmID = ?");
				localArrayList.add(paramStatQuery.getFirmID());
			}
			if (paramStatQuery.getBeginDate() != null) {
				localStringBuffer.append(" and a.ClearDate = ?");
				localArrayList.add(paramStatQuery.getBeginDate());
			}
		}
		Object[] arrayOfObject = null;
		String str = "";
		localStringBuffer.append(" order by a.FirmID");
		if ((localArrayList != null) && (localArrayList.size() > 0)) {
			arrayOfObject = localArrayList.toArray();
		}
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		if (localList.size() == 0) {
			localList = getHoldDetail1(paramStatQuery, paramString, str);
		}
		return localList;
	}

	private List getHoldDetail1(StatQuery paramStatQuery, String paramString1, String paramString2) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select a.* from T_H_HoldPosition a where 1=1 ");
		ArrayList localArrayList = new ArrayList();
		if (paramStatQuery != null) {
			if ((paramStatQuery.getFirmID() != null) && (!"".equals(paramStatQuery.getFirmID()))) {
				localStringBuffer.append(" and a.firmID = ?");
				localArrayList.add(paramStatQuery.getFirmID());
			}
			if (paramStatQuery.getBeginDate() != null) {
				localStringBuffer.append(" and a.ClearDate = ?");
				localArrayList.add(paramStatQuery.getBeginDate());
			}
		}
		Object[] arrayOfObject = null;
		localStringBuffer.append(" order by a.firmID");
		if ((localArrayList != null) && (localArrayList.size() > 0)) {
			arrayOfObject = localArrayList.toArray();
		}
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getHoldDetailOther(StatQuery paramStatQuery, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select sysdate,a.FirmID,a.BS_Flag,a.price,a.holdQty,")
				.append("case when a.BS_Flag=1 then '买' when a.BS_Flag=2 then '卖' else '' end as sBS_Flag,")
				.append("abs(a.FloatingLoss) as loss,a.CommodityId  ").append("from ").append("T_H_HoldPosition a ");
		localStringBuffer.append("where 1=1 ");
		if ((paramString != null) && (!"".equals(paramString))) {
			localStringBuffer.append(" and a.FirmID  in(" + paramString + ")");
		}
		ArrayList localArrayList = new ArrayList();
		if (paramStatQuery != null) {
			if ((paramStatQuery.getFirmID() != null) && (!"".equals(paramStatQuery.getFirmID()))) {
				localStringBuffer.append(" and a.firmID = ?");
				localArrayList.add(paramStatQuery.getFirmID());
			}
			if (paramStatQuery.getBeginDate() != null) {
				localStringBuffer.append(" and a.clearDate = ?");
				localArrayList.add(paramStatQuery.getBeginDate());
			}
		}
		Object[] arrayOfObject = null;
		String str = "";
		localStringBuffer.append(" order by a.FirmID");
		if ((localArrayList != null) && (localArrayList.size() > 0)) {
			arrayOfObject = localArrayList.toArray();
		}
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		try {
			return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		} catch (Exception localException) {
			throw new RuntimeException("查询出错！");
		}
	}

	public List getHoldDetailOther1(StatQuery paramStatQuery, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select sysdate,a.FirmID ").append("from ").append("T_H_HoldPosition a ");
		localStringBuffer.append("where 1=1 ");
		if (!"".equals(paramString)) {
			localStringBuffer.append(" and a.FirmID in(" + paramString + ")");
		}
		ArrayList localArrayList = new ArrayList();
		if (paramStatQuery != null) {
			if ((paramStatQuery.getFirmID() != null) && (!"".equals(paramStatQuery.getFirmID()))) {
				localStringBuffer.append(" and a.firmID = ?");
				localArrayList.add(paramStatQuery.getFirmID());
			}
			if (paramStatQuery.getBeginDate() != null) {
				localStringBuffer.append(" and a.clearDate = ?");
				localArrayList.add(paramStatQuery.getBeginDate());
			}
		}
		Object[] arrayOfObject = null;
		localStringBuffer.append(" group by a.FirmID order by a.FirmID");
		if ((localArrayList != null) && (localArrayList.size() > 0)) {
			arrayOfObject = localArrayList.toArray();
		}
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	private static void printMap(Map paramMap) {
		Iterator localIterator = paramMap.entrySet().iterator();
		while (localIterator.hasNext()) {
		}
	}

	public List getClose_PL(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer("");
		localStringBuffer.append("select a.*, ").append("(case when a.BS_Flag=1 then '买进' when a.BS_Flag=2 then '卖出' else '' end) || ")
				.append(" (case when a.OrderType=1 then '订立' when a.OrderType=2 then '转让' else '' end) BSOrderType ").append("from T_H_Trade a ")
				.append(" ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" where ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" order by a.ClearDate,a.CustomerID");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getTrades(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer("");
		localStringBuffer.append("select a.*,b.OrderTime, ").append("(case when a.BS_Flag=1 then '买进' when a.BS_Flag=2 then '卖出' else '' end) || ")
				.append(" (case when a.OrderType=1 then '订立' when a.OrderType=2 then '转让' else '' end) BSOrderType ")
				.append("from T_H_Trade a,T_H_Orders b ").append("where a.ClearDate = b.ClearDate and a.A_OrderNo = b.A_OrderNo  ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" order by a.ClearDate,a.CustomerID");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getFunds(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer("");
		localStringBuffer.append("select a.*,b.TradeFee,b.Close_PL ,c.MinClearDeposit,")
				.append("(case when (c.MinClearDeposit-c.Balance) > 0 then (c.MinClearDeposit-c.Balance) else 0 end) shouldAddFund ")
				.append("from F_H_firmfunds a,T_FundTradePropsView c, ")
				.append("(select ClearDate,CustomerID,sum(TradeFee) TradeFee,sum(Close_PL) Close_PL from T_h_trade group by ClearDate,CustomerID) b ")
				.append("where a.ClearDate = b.ClearDate(+) and a.CustomerID=b.CustomerID(+) and a.CustomerID=c.CustomerID(+) ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(" order by a.ClearDate,a.CustomerID");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public Date getMaxClearDateByTable(String paramString) {
		if ((paramString == null) || (paramString.equals(""))) {
			return null;
		}
		String str = "select max(ClearDate) from " + paramString;
		this.log.debug("sql: " + str);
		return (Date) getJdbcTemplate().queryForObject(str, Date.class);
	}

	public List getSortFund(QueryConditions paramQueryConditions) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select a.ClearDate,a.CustomerID,a.Balance,a.RuntimeMargin,nvl((case when b.floatProfit< 0 then 0 else b.floatProfit end),0) floatProfit,(a.Balance+a.RuntimeMargin-a.RuntimeAssure+nvl((case when b.floatProfit< 0 then 0 else b.floatProfit end),0)) allFunds,a.CustomerName  ")
				.append("from T_Bal_CustomerFundsView a, ")
				.append("(select a.cleardate,a.customerid,nvl(sum(a.FloatingLoss),0) floatProfit from T_H_CustomerHoldSum a group by a.cleardate, a.customerid) b  ")
				.append("where a.ClearDate = b.ClearDate(+) and a.CustomerID=b.CustomerID(+)  ");
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
		}
		localStringBuffer.append(
				" order by (a.Balance+a.RuntimeMargin-a.RuntimeAssure+nvl((case when b.floatProfit< 0 then 0 else b.floatProfit end),0)) desc ");
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getWeek1Detail(QueryConditions paramQueryConditions, String paramString) {
		StringBuffer localStringBuffer1 = new StringBuffer();
		StringBuffer localStringBuffer2 = new StringBuffer();
		StringBuffer localStringBuffer3 = new StringBuffer();
		localStringBuffer1.append(
				"select FirmID,FirmName,InitFunds,ClearMargin+ClearFL a1,ClearAssure,SettleMargin,SettleFee,Settle_PL  from T_bal_firmfundsview a ");
		localStringBuffer2.append(
				"select FirmID,sum(InFunds) InFunds,sum(OutFunds) OutFunds,sum(Close_PL) Close_PL,sum(TradeFee) TradeFee from T_bal_firmfundsview a ");
		localStringBuffer3.append("select FirmID,Balance,RuntimeFL+RuntimeMargin a2,RuntimeAssure from T_bal_firmfundsview a ");
		Date localDate = (Date) paramQueryConditions.getConditionValue("a.ClearDate");
		String str1 = null;
		String str2 = null;
		Object[] arrayOfObject = null;
		if ((paramString != null) && (!paramString.equals(""))) {
			if (paramString.equals("day")) {
				str1 = DateUtil.formatDate(localDate, "yyyy-MM-dd");
				localStringBuffer1.append("where to_char(a.ClearDate,'yyyy-MM-dd')='" + str1 + "'");
				localStringBuffer2.append("where to_char(a.ClearDate,'yyyy-MM-dd')='" + str1 + "' group by a.FirmID");
				localStringBuffer3.append("where to_char(a.ClearDate,'yyyy-MM-dd')='" + str1 + "'");
			} else if (paramString.equals("week")) {
				str1 = DateUtil.getFirstWeekDay(localDate);
				str2 = DateUtil.getLastWeekDay(localDate);
				localStringBuffer1
						.append("where to_char(a.ClearDate,'yyyyMMdd') in (select min(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where ClearDate >= to_date('"
								+ str1 + "', 'yyyy-MM-dd') and ClearDate <= to_date('" + str2 + "', 'yyyy-MM-dd')) ");
				localStringBuffer2.append("where a.ClearDate >= to_date('" + str1 + "', 'yyyy-MM-dd') and a.ClearDate <= to_date('" + str2
						+ "', 'yyyy-MM-dd') group by a.FirmID");
				localStringBuffer3
						.append("where to_char(a.ClearDate,'yyyyMMdd') in (select max(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where ClearDate >= to_date('"
								+ str1 + "', 'yyyy-MM-dd') and ClearDate <= to_date('" + str2 + "', 'yyyy-MM-dd')) ");
			} else if (paramString.equals("month")) {
				str1 = DateUtil.formatDate(localDate, "yyyy-MM");
				localStringBuffer1
						.append("where to_char(a.ClearDate,'yyyyMMdd') in (select min(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where to_char(ClearDate,'yyyy-MM')='"
								+ str1 + "') ");
				localStringBuffer2.append("where to_char(ClearDate,'yyyy-MM')='" + str1 + "' group by a.FirmID ");
				localStringBuffer3
						.append("where to_char(a.ClearDate,'yyyyMMdd') in (select max(to_char(ClearDate,'yyyyMMdd')) from T_bal_firmfundsview where to_char(ClearDate,'yyyy-MM')='"
								+ str1 + "')  ");
			}
		}
		String str3 = "select b.FirmID,b.FirmName,nvl(b.SettleMargin,0) SettleMargin,nvl(b.SettleFee,0) SettleFee,nvl(b.Settle_PL,0) Settle_PL,nvl(b.InitFunds,0) InitFunds,nvl(b.a1,0) ClearFee,nvl(b.ClearAssure,0) ClearAssure,nvl(c.InFunds,0) InFunds,nvl(c.OutFunds,0) OutFunds,nvl(c.Close_PL,0) Close_PL,nvl(c.TradeFee,0) TradeFee,nvl(d.Balance,0) LastFund,nvl(d.a2,0) RuntimeFee,nvl(d.RuntimeAssure,0) RuntimeAssure from ("
				+ localStringBuffer1.toString() + ") b,(" + localStringBuffer2.toString() + ") c," + "(" + localStringBuffer3.toString()
				+ ") d where b.FirmID=c.FirmID and c.FirmID=d.FirmID  ";
		String str4 = (String) paramQueryConditions.getConditionValue("a.GroupID");
		String str5 = (String) paramQueryConditions.getConditionValue("a.FirmID");
		String str6 = (String) paramQueryConditions.getConditionValue("a.FirmName");
		localStringBuffer1.append(" order by a.FirmID");
		this.log.debug("sql: " + str3);
		arrayOfObject = new Object[0];
		return getJdbcTemplate().queryForList(str3);
	}

	/**
	 * @deprecated
	 */
	private class ComputeFundStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "ComputeFund";

		public ComputeFundStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "ComputeFund");
			declareParameter(new SqlParameter("p_ReportType", 12));
			declareParameter(new SqlParameter("p_BeginDate", 12));
			declareParameter(new SqlParameter("p_EndDate", 12));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlOutParameter("p_InitFund", 8));
			declareParameter(new SqlOutParameter("p_InFund", 8));
			declareParameter(new SqlOutParameter("p_OutFund", 8));
			declareParameter(new SqlOutParameter("p_ClearFee", 8));
			declareParameter(new SqlOutParameter("p_RuntimeFee", 8));
			declareParameter(new SqlOutParameter("p_Close_PL", 8));
			declareParameter(new SqlOutParameter("p_TradeFee", 8));
			declareParameter(new SqlOutParameter("p_LastFund", 8));
			declareParameter(new SqlOutParameter("p_ret", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
