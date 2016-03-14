package gnnt.MEBS.timebargain.manage.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

import gnnt.MEBS.timebargain.manage.dao.CommodityDAO;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.TradeTime;

public class CommodityDAOJdbc extends BaseDAOJdbc implements CommodityDAO {
	private Log log = LogFactory.getLog(CommodityDAOJdbc.class);

	public Commodity getCommodityByIdCURorHIS(Commodity paramCommodity) {
		String str1 = "";
		String str2 = paramCommodity.getOper();
		this.log.debug("oper: " + str2);
		if ("cur".equals(str2)) {
			str1 = "select * from T_COMMODITY where CommodityID=?";
		} else if ("his".equals(str2)) {
			str1 = "select * from T_SETTLECOMMODITY where CommodityID=?";
		} else if ("querycur".equals(str2)) {
			str1 = "select * from T_COMMODITY where CommodityID=?";
		} else if ("queryhis".equals(str2)) {
			str1 = "select * from T_SETTLECOMMODITY where CommodityID=?";
		}
		Object[] arrayOfObject = { paramCommodity.getCommodityID() };
		this.log.debug("sql: " + str1);
		this.log.debug("CommodityID:" + arrayOfObject[0]);
		Commodity localCommodity = new Commodity();
		List localList1 = getJdbcTemplate().queryForList(str1, arrayOfObject);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localObject1 = (Map) localList1.get(0);
			localCommodity.setCommodityID((String) ((Map) localObject1).get("CommodityID"));
			localCommodity.setCommodityID((String) ((Map) localObject1).get("CommodityID"));
			localCommodity.setName((String) ((Map) localObject1).get("Name"));
			localCommodity.setSortID(Long.valueOf(Long.parseLong(((Map) localObject1).get("SortID").toString())));
			if (((Map) localObject1).get("MaxHoldPositionDay") != null) {
				localCommodity.setMaxHoldPositionDay(Integer.valueOf(Integer.parseInt(((Map) localObject1).get("MaxHoldPositionDay").toString())));
			} else {
				localCommodity.setMaxHoldPositionDay(null);
			}
			localCommodity.setHoldDaysLimit(Integer.parseInt(((Map) localObject1).get("HoldDaysLimit").toString()));
			localCommodity.setStatus(Short.valueOf(Short.parseShort(((Map) localObject1).get("Status").toString())));
			localCommodity.setContractFactor(Double.valueOf(Double.parseDouble(((Map) localObject1).get("ContractFactor").toString())));
			localCommodity.setMinPriceMove(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MinPriceMove").toString())));
			localCommodity.setBreedID(Long.valueOf(Long.parseLong(((Map) localObject1).get("BreedID").toString())));
			localCommodity.setSpreadAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("SpreadAlgr").toString())));
			localCommodity.setSpreadUpLmt(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SpreadUpLmt").toString())));
			localCommodity.setSpreadDownLmt(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SpreadDownLmt").toString())));
			localCommodity.setFeeAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("FeeAlgr").toString())));
			localCommodity.setFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("FeeRate_B").toString())));
			localCommodity.setFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("FeeRate_S").toString())));
			localCommodity.setMarginAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("MarginAlgr").toString())));
			localCommodity.setMarginRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginRate_B").toString())));
			localCommodity.setMarginRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginRate_S").toString())));
			if (((Map) localObject1).get("MarketDate") != null) {
				localCommodity.setMarketDate((Date) ((Map) localObject1).get("MarketDate"));
			}
			if (((Map) localObject1).get("SettleDate1") != null) {
				localCommodity.setSettleDate1((Date) ((Map) localObject1).get("SettleDate1"));
			}
			if (((Map) localObject1).get("MarginItem1") != null) {
				localCommodity.setMarginItem1(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem1").toString())));
			}
			if (((Map) localObject1).get("SettleDate2") != null) {
				localCommodity.setSettleDate2((Date) ((Map) localObject1).get("SettleDate2"));
			}
			if (((Map) localObject1).get("MarginItem2") != null) {
				localCommodity.setMarginItem2(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem2").toString())));
			}
			if (((Map) localObject1).get("SettleDate3") != null) {
				localCommodity.setSettleDate3((Date) ((Map) localObject1).get("SettleDate3"));
			}
			if (((Map) localObject1).get("MarginItem3") != null) {
				localCommodity.setMarginItem3(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem3").toString())));
			}
			if (((Map) localObject1).get("SettleDate4") != null) {
				localCommodity.setSettleDate4((Date) ((Map) localObject1).get("SettleDate4"));
			}
			if (((Map) localObject1).get("MarginItem4") != null) {
				localCommodity.setMarginItem4(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem4").toString())));
			}
			if (((Map) localObject1).get("SettleDate5") != null) {
				localCommodity.setSettleDate5((Date) ((Map) localObject1).get("SettleDate5"));
			}
			if (((Map) localObject1).get("MarginItem5") != null) {
				localCommodity.setMarginItem5(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem5").toString())));
			}
			localCommodity.setSettleDate((Date) ((Map) localObject1).get("SettleDate"));
			localCommodity.setLastPrice(Double.valueOf(Double.parseDouble(((Map) localObject1).get("LastPrice").toString())));
			if (((Map) localObject1).get("MarginItem1_S") != null) {
				localCommodity.setMarginItem1_S(new Double(((Map) localObject1).get("MarginItem1_S").toString()));
			}
			if (((Map) localObject1).get("MarginItem2_S") != null) {
				localCommodity.setMarginItem2_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem2_S").toString())));
			}
			if (((Map) localObject1).get("MarginItem3_S") != null) {
				localCommodity.setMarginItem3_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem3_S").toString())));
			}
			if (((Map) localObject1).get("MarginItem4_S") != null) {
				localCommodity.setMarginItem4_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem4_S").toString())));
			}
			if (((Map) localObject1).get("MarginItem5_S") != null) {
				localCommodity.setMarginItem5_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItem5_S").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure1") != null) {
				localCommodity.setMarginItemAssure1(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure1").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure2") != null) {
				localCommodity.setMarginItemAssure2(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure2").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure3") != null) {
				localCommodity.setMarginItemAssure3(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure3").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure4") != null) {
				localCommodity.setMarginItemAssure4(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure4").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure5") != null) {
				localCommodity.setMarginItemAssure5(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure5").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure1_S") != null) {
				localCommodity.setMarginItemAssure1_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure1_S").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure2_S") != null) {
				localCommodity.setMarginItemAssure2_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure2_S").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure3_S") != null) {
				localCommodity.setMarginItemAssure3_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure3_S").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure4_S") != null) {
				localCommodity.setMarginItemAssure4_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure4_S").toString())));
			}
			if (((Map) localObject1).get("MarginItemAssure5_S") != null) {
				localCommodity.setMarginItemAssure5_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MarginItemAssure5_S").toString())));
			}
			localCommodity.setTodayCloseFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("TodayCloseFeeRate_B").toString())));
			localCommodity.setTodayCloseFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("TodayCloseFeeRate_S").toString())));
			localCommodity.setHistoryCloseFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("HistoryCloseFeeRate_B").toString())));
			localCommodity.setHistoryCloseFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("HistoryCloseFeeRate_S").toString())));
			if (((Map) localObject1).get("LimitCmdtyQty") != null) {
				localCommodity.setLimitCmdtyQty(Long.valueOf(Long.parseLong(((Map) localObject1).get("LimitCmdtyQty").toString())));
			}
			localCommodity.setSettleFeeAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("SettleFeeAlgr").toString())));
			localCommodity.setSettleFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SettleFeeRate_B").toString())));
			localCommodity.setSettleFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SettleFeeRate_S").toString())));
			localCommodity.setForceCloseFeeAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("ForceCloseFeeAlgr").toString())));
			localCommodity.setForceCloseFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("ForceCloseFeeRate_B").toString())));
			localCommodity.setForceCloseFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("ForceCloseFeeRate_S").toString())));
			localCommodity.setSettleMarginRate_B(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SettleMarginRate_B").toString())));
			localCommodity.setSettleMarginRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SettleMarginRate_S").toString())));
			localCommodity.setAddedTax(Double.valueOf(Double.parseDouble(((Map) localObject1).get("AddedTax").toString())));
			localCommodity.setMarginPriceType(Short.valueOf(Short.parseShort(((Map) localObject1).get("MarginPriceType").toString())));
			localCommodity.setLowestSettleFee(Double.valueOf(Double.parseDouble(((Map) localObject1).get("LowestSettleFee").toString())));
			localCommodity.setFirmCleanQty(Long.valueOf(Long.parseLong(((Map) localObject1).get("FirmCleanQty").toString())));
			localCommodity.setSettleMarginAlgr_B(Short.valueOf(Short.parseShort(((Map) localObject1).get("SettleMarginAlgr_B").toString())));
			localCommodity.setSettleMarginAlgr_S(Short.valueOf(Short.parseShort(((Map) localObject1).get("SettleMarginAlgr_S").toString())));
			localCommodity.setPayoutAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("PayoutAlgr").toString())));
			localCommodity.setPayoutRate(Double.valueOf(Double.parseDouble(((Map) localObject1).get("PayoutRate").toString())));
			localCommodity.setFirmMaxHoldQty(Long.valueOf(Long.parseLong(((Map) localObject1).get("FirmMaxHoldQty").toString())));
			localCommodity.setAddedTaxFactor(Double.valueOf(Double.parseDouble(((Map) localObject1).get("AddedTaxFactor").toString())));
			localCommodity.setOrderPrivilege_B(Short.valueOf(Short.parseShort(((Map) localObject1).get("OrderPrivilege_B").toString())));
			localCommodity.setOrderPrivilege_S(Short.valueOf(Short.parseShort(((Map) localObject1).get("OrderPrivilege_S").toString())));
			localCommodity.setSettlePriceType(Short.valueOf(Short.parseShort(((Map) localObject1).get("SettlePriceType").toString())));
			if (((Map) localObject1).get("BeforeDays") != null) {
				localCommodity.setBeforeDays(Long.valueOf(Long.parseLong(((Map) localObject1).get("BeforeDays").toString())));
			}
			if (((Map) localObject1).get("SpecSettlePrice") != null) {
				localCommodity.setSpecSettlePrice(Double.valueOf(Double.parseDouble(((Map) localObject1).get("SpecSettlePrice").toString())));
			}
			localCommodity.setSettleMarginType(Integer.parseInt(((Map) localObject1).get("aheadSettlepriceType").toString()));
			localCommodity.setSettleMarginType(Integer.parseInt(((Map) localObject1).get("SettleMarginType").toString()));
			if (((Map) localObject1).get("BeforeDays_M") != null) {
				localCommodity.setBeforeDays_M(Integer.parseInt(((Map) localObject1).get("BeforeDays_M").toString()));
			}
			if (((Map) localObject1).get("FirmMaxHoldQtyAlgr") != null) {
				localCommodity.setFirmMaxHoldQtyAlgr(Short.valueOf(Short.parseShort(((Map) localObject1).get("FirmMaxHoldQtyAlgr").toString())));
			}
			if (((Map) localObject1).get("StartPercentQty") != null) {
				localCommodity.setStartPercentQty(Long.valueOf(Long.parseLong(((Map) localObject1).get("StartPercentQty").toString())));
			}
			if (((Map) localObject1).get("MaxPercentLimit") != null) {
				localCommodity.setMaxPercentLimit(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MaxPercentLimit").toString())));
			}
			localCommodity.setOneMaxHoldQty(Long.valueOf(Long.parseLong(((Map) localObject1).get("OneMaxHoldQty").toString())));
			localCommodity.setMinQuantityMove(Integer.parseInt(((Map) localObject1).get("MinQuantityMove").toString()));
			localCommodity.setMinSettleMoveQty(Integer.parseInt(((Map) localObject1).get("MinSettleMoveQty").toString()));
			localCommodity.setMinSettleQty(Integer.parseInt(((Map) localObject1).get("MinSettleQty").toString()));
			localCommodity.setDelayRecoupRate(Double.valueOf(Double.parseDouble(((Map) localObject1).get("DelayRecoupRate").toString())));
			localCommodity.setDelayRecoupRate_S(Double.valueOf(Double.parseDouble(((Map) localObject1).get("DelayRecoupRate_S").toString())));
			localCommodity.setSettleWay(Short.valueOf(Short.parseShort(((Map) localObject1).get("SettleWay").toString())));
			localCommodity.setDelayFeeWay(Short.valueOf(Short.parseShort(((Map) localObject1).get("DelayFeeWay").toString())));
			localCommodity.setStoreRecoupRate(Double.valueOf(Double.parseDouble(((Map) localObject1).get("StoreRecoupRate").toString())));
			localCommodity.setDelaySettlePriceType(Integer.parseInt(((Map) localObject1).get("DelaySettlePriceType").toString()));
			localCommodity.setAheadSettlePriceType(Integer.parseInt(((Map) localObject1).get("aheadSettlePriceType").toString()));
			if (((Map) localObject1).get("MaxFeeRate") != null) {
				localCommodity.setMaxFeeRate(Double.valueOf(Double.parseDouble(((Map) localObject1).get("MaxFeeRate").toString())));
			}
			String localObject2 = ((Map) localObject1).get("holdDaysLimit").toString();
			localCommodity.setHoldDaysLimit(Integer.parseInt((String) localObject2));
			if ("1".equals(localObject2)) {
				localCommodity.setMaxHoldPositionDay(Integer.valueOf(Integer.parseInt(((Map) localObject1).get("MaxHoldPositionDay").toString())));
			}
		}
		Object localObject1 = "select t.sectionID sectionID,t.Name name from T_A_COMMODITYTRADEPROP ctp,T_COMMODITY c,T_A_TRADETIME t where c.CommodityID=ctp.CommodityID and t.SectionID=ctp.SectionID and c.CommodityID=?";
		String[] localObject2 = { paramCommodity.getCommodityID() };
		List localList2 = getJdbcTemplate().queryForList((String) localObject1, (Object[]) localObject2);
		HashSet localHashSet = new HashSet();
		for (int i = 0; i < localList2.size(); i++) {
			Map localMap = (Map) localList2.get(i);
			Integer localInteger = Integer.valueOf(Integer.parseInt(localMap.get("sectionID").toString()));
			String str3 = (String) localMap.get("name");
			TradeTime localTradeTime = new TradeTime();
			localTradeTime.setSectionID(localInteger);
			localTradeTime.setName(str3);
			localHashSet.add(localTradeTime);
		}
		localCommodity.setTradeTime(localHashSet);
		return localCommodity;
	}

	public Commodity getCommodityById(String paramString) {
		String str1 = "select * from T_COMMODITY where CommodityID=?";
		Object[] arrayOfObject1 = { paramString };
		this.log.debug("sql: " + str1);
		this.log.debug("CommodityID:" + arrayOfObject1[0]);
		Commodity localCommodity = new Commodity();
		List localList1 = getJdbcTemplate().queryForList(str1, arrayOfObject1);
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localObject = (Map) localList1.get(0);
			localCommodity.setCommodityID((String) ((Map) localObject).get("CommodityID"));
			localCommodity.setCommodityID((String) ((Map) localObject).get("CommodityID"));
			localCommodity.setName((String) ((Map) localObject).get("Name"));
			localCommodity.setSortID(Long.valueOf(Long.parseLong(((Map) localObject).get("SortID").toString())));
			localCommodity.setStatus(Short.valueOf(Short.parseShort(((Map) localObject).get("Status").toString())));
			localCommodity.setContractFactor(Double.valueOf(Double.parseDouble(((Map) localObject).get("ContractFactor").toString())));
			localCommodity.setMinPriceMove(Double.valueOf(Double.parseDouble(((Map) localObject).get("MinPriceMove").toString())));
			localCommodity.setBreedID(Long.valueOf(Long.parseLong(((Map) localObject).get("BreedID").toString())));
			localCommodity.setSpreadAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("SpreadAlgr").toString())));
			localCommodity.setSpreadUpLmt(Double.valueOf(Double.parseDouble(((Map) localObject).get("SpreadUpLmt").toString())));
			localCommodity.setSpreadDownLmt(Double.valueOf(Double.parseDouble(((Map) localObject).get("SpreadDownLmt").toString())));
			localCommodity.setFeeAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("FeeAlgr").toString())));
			localCommodity.setFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("FeeRate_B").toString())));
			localCommodity.setFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("FeeRate_S").toString())));
			localCommodity.setMarginAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("MarginAlgr").toString())));
			localCommodity.setMarginRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginRate_B").toString())));
			localCommodity.setMarginRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginRate_S").toString())));
			if (((Map) localObject).get("MarketDate") != null) {
				localCommodity.setMarketDate((Date) ((Map) localObject).get("MarketDate"));
			}
			if (((Map) localObject).get("SettleDate1") != null) {
				localCommodity.setSettleDate1((Date) ((Map) localObject).get("SettleDate1"));
			}
			if (((Map) localObject).get("MarginItem1") != null) {
				localCommodity.setMarginItem1(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem1").toString())));
			}
			if (((Map) localObject).get("SettleDate2") != null) {
				localCommodity.setSettleDate2((Date) ((Map) localObject).get("SettleDate2"));
			}
			if (((Map) localObject).get("MarginItem2") != null) {
				localCommodity.setMarginItem2(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem2").toString())));
			}
			if (((Map) localObject).get("SettleDate3") != null) {
				localCommodity.setSettleDate3((Date) ((Map) localObject).get("SettleDate3"));
			}
			if (((Map) localObject).get("MarginItem3") != null) {
				localCommodity.setMarginItem3(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem3").toString())));
			}
			if (((Map) localObject).get("SettleDate4") != null) {
				localCommodity.setSettleDate4((Date) ((Map) localObject).get("SettleDate4"));
			}
			if (((Map) localObject).get("MarginItem4") != null) {
				localCommodity.setMarginItem4(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem4").toString())));
			}
			if (((Map) localObject).get("SettleDate5") != null) {
				localCommodity.setSettleDate5((Date) ((Map) localObject).get("SettleDate5"));
			}
			if (((Map) localObject).get("MarginItem5") != null) {
				localCommodity.setMarginItem5(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem5").toString())));
			}
			localCommodity.setSettleDate((Date) ((Map) localObject).get("SettleDate"));
			localCommodity.setLastPrice(Double.valueOf(Double.parseDouble(((Map) localObject).get("LastPrice").toString())));
			if (((Map) localObject).get("MarginItem1_S") != null) {
				localCommodity.setMarginItem1_S(new Double(((Map) localObject).get("MarginItem1_S").toString()));
			}
			if (((Map) localObject).get("MarginItem2_S") != null) {
				localCommodity.setMarginItem2_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem2_S").toString())));
			}
			if (((Map) localObject).get("MarginItem3_S") != null) {
				localCommodity.setMarginItem3_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem3_S").toString())));
			}
			if (((Map) localObject).get("MarginItem4_S") != null) {
				localCommodity.setMarginItem4_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem4_S").toString())));
			}
			if (((Map) localObject).get("MarginItem5_S") != null) {
				localCommodity.setMarginItem5_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItem5_S").toString())));
			}
			localCommodity.setMarginAssure_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginAssure_B").toString())));
			localCommodity.setMarginAssure_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginAssure_S").toString())));
			if (((Map) localObject).get("MarginItemAssure1") != null) {
				localCommodity.setMarginItemAssure1(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure1").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure2") != null) {
				localCommodity.setMarginItemAssure2(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure2").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure3") != null) {
				localCommodity.setMarginItemAssure3(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure3").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure4") != null) {
				localCommodity.setMarginItemAssure4(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure4").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure5") != null) {
				localCommodity.setMarginItemAssure5(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure5").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure1_S") != null) {
				localCommodity.setMarginItemAssure1_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure1_S").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure2_S") != null) {
				localCommodity.setMarginItemAssure2_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure2_S").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure3_S") != null) {
				localCommodity.setMarginItemAssure3_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure3_S").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure4_S") != null) {
				localCommodity.setMarginItemAssure4_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure4_S").toString())));
			}
			if (((Map) localObject).get("MarginItemAssure5_S") != null) {
				localCommodity.setMarginItemAssure5_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("MarginItemAssure5_S").toString())));
			}
			localCommodity.setTodayCloseFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("TodayCloseFeeRate_B").toString())));
			localCommodity.setTodayCloseFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("TodayCloseFeeRate_S").toString())));
			localCommodity.setHistoryCloseFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("HistoryCloseFeeRate_B").toString())));
			localCommodity.setHistoryCloseFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("HistoryCloseFeeRate_S").toString())));
			if (((Map) localObject).get("LimitCmdtyQty") != null) {
				localCommodity.setLimitCmdtyQty(Long.valueOf(Long.parseLong(((Map) localObject).get("LimitCmdtyQty").toString())));
			}
			localCommodity.setSettleFeeAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("SettleFeeAlgr").toString())));
			localCommodity.setSettleFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("SettleFeeRate_B").toString())));
			localCommodity.setSettleFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("SettleFeeRate_S").toString())));
			localCommodity.setForceCloseFeeAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("ForceCloseFeeAlgr").toString())));
			localCommodity.setForceCloseFeeRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("ForceCloseFeeRate_B").toString())));
			localCommodity.setForceCloseFeeRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("ForceCloseFeeRate_S").toString())));
			localCommodity.setSettleMarginRate_B(Double.valueOf(Double.parseDouble(((Map) localObject).get("SettleMarginRate_B").toString())));
			localCommodity.setSettleMarginRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("SettleMarginRate_S").toString())));
			localCommodity.setAddedTax(Double.valueOf(Double.parseDouble(((Map) localObject).get("AddedTax").toString())));
			localCommodity.setMarginPriceType(Short.valueOf(Short.parseShort(((Map) localObject).get("MarginPriceType").toString())));
			localCommodity.setLowestSettleFee(Double.valueOf(Double.parseDouble(((Map) localObject).get("LowestSettleFee").toString())));
			localCommodity.setFirmCleanQty(Long.valueOf(Long.parseLong(((Map) localObject).get("FirmCleanQty").toString())));
			localCommodity.setSettleMarginAlgr_B(Short.valueOf(Short.parseShort(((Map) localObject).get("SettleMarginAlgr_B").toString())));
			localCommodity.setSettleMarginAlgr_S(Short.valueOf(Short.parseShort(((Map) localObject).get("SettleMarginAlgr_S").toString())));
			localCommodity.setPayoutAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("PayoutAlgr").toString())));
			localCommodity.setPayoutRate(Double.valueOf(Double.parseDouble(((Map) localObject).get("PayoutRate").toString())));
			localCommodity.setFirmMaxHoldQty(Long.valueOf(Long.parseLong(((Map) localObject).get("FirmMaxHoldQty").toString())));
			localCommodity.setAddedTaxFactor(Double.valueOf(Double.parseDouble(((Map) localObject).get("AddedTaxFactor").toString())));
			localCommodity.setSettlePriceType(Short.valueOf(Short.parseShort(((Map) localObject).get("SettlePriceType").toString())));
			if (((Map) localObject).get("BeforeDays") != null) {
				localCommodity.setBeforeDays(Long.valueOf(Long.parseLong(((Map) localObject).get("BeforeDays").toString())));
			}
			if (((Map) localObject).get("SpecSettlePrice") != null) {
				localCommodity.setSpecSettlePrice(Double.valueOf(Double.parseDouble(((Map) localObject).get("SpecSettlePrice").toString())));
			}
			localCommodity.setAheadSettlePriceType(Integer.parseInt(((Map) localObject).get("aheadSettlepriceType").toString()));
			localCommodity.setSettleMarginType(Integer.parseInt(((Map) localObject).get("SettleMarginType").toString()));
			if (((Map) localObject).get("BeforeDays_M") != null) {
				localCommodity.setBeforeDays_M(Integer.parseInt(((Map) localObject).get("BeforeDays_M").toString()));
			}
			if (((Map) localObject).get("FirmMaxHoldQtyAlgr") != null) {
				localCommodity.setFirmMaxHoldQtyAlgr(Short.valueOf(Short.parseShort(((Map) localObject).get("FirmMaxHoldQtyAlgr").toString())));
			}
			if (((Map) localObject).get("StartPercentQty") != null) {
				localCommodity.setStartPercentQty(Long.valueOf(Long.parseLong(((Map) localObject).get("StartPercentQty").toString())));
			}
			if (((Map) localObject).get("MaxPercentLimit") != null) {
				localCommodity.setMaxPercentLimit(Double.valueOf(Double.parseDouble(((Map) localObject).get("MaxPercentLimit").toString())));
			}
			localCommodity.setOneMaxHoldQty(Long.valueOf(Long.parseLong(((Map) localObject).get("OneMaxHoldQty").toString())));
			localCommodity.setMinQuantityMove(Integer.parseInt(((Map) localObject).get("MinQuantityMove").toString()));
			localCommodity.setMinSettleMoveQty(Integer.parseInt(((Map) localObject).get("MinSettleMoveQty").toString()));
			localCommodity.setMinSettleQty(Integer.parseInt(((Map) localObject).get("MinSettleQty").toString()));
			localCommodity.setDelayRecoupRate(Double.valueOf(Double.parseDouble(((Map) localObject).get("DelayRecoupRate").toString())));
			localCommodity.setDelayRecoupRate_S(Double.valueOf(Double.parseDouble(((Map) localObject).get("DelayRecoupRate_S").toString())));
			localCommodity.setSettleWay(Short.valueOf(Short.parseShort(((Map) localObject).get("SettleWay").toString())));
			localCommodity.setDelayFeeWay(Short.valueOf(Short.parseShort(((Map) localObject).get("DelayFeeWay").toString())));
			localCommodity.setStoreRecoupRate(Double.valueOf(Double.parseDouble(((Map) localObject).get("StoreRecoupRate").toString())));
			localCommodity.setDelaySettlePriceType(Integer.parseInt(((Map) localObject).get("DelaySettlePriceType").toString()));
			if (((Map) localObject).get("MaxFeeRate") != null) {
				localCommodity.setMaxFeeRate(Double.valueOf(Double.parseDouble(((Map) localObject).get("MaxFeeRate").toString())));
			}
		}
		Object localObject = "select t.sectionID sectionID,t.Name name from T_A_COMMODITYTRADEPROP ctp,T_COMMODITY c,T_A_TRADETIME t where c.CommodityID=ctp.CommodityID and t.SectionID=ctp.SectionID and c.CommodityID=?";
		Object[] arrayOfObject2 = { paramString };
		List localList2 = getJdbcTemplate().queryForList((String) localObject, arrayOfObject2);
		HashSet localHashSet = new HashSet();
		for (int i = 0; i < localList2.size(); i++) {
			Map localMap = (Map) localList2.get(i);
			Integer localInteger = Integer.valueOf(Integer.parseInt(localMap.get("sectionID").toString()));
			String str2 = (String) localMap.get("name");
			TradeTime localTradeTime = new TradeTime();
			localTradeTime.setSectionID(localInteger);
			localTradeTime.setName(str2);
			localHashSet.add(localTradeTime);
		}
		localCommodity.setTradeTime(localHashSet);
		return localCommodity;
	}

	public Commodity getCommodityIDById(String paramString) {
		Assert.hasText(paramString);
		String str = "select * from T_COMMODITY where CommodityID=?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		this.log.debug("CommodityID:" + arrayOfObject[0]);
		Commodity localCommodity = null;
		try {
			localCommodity = (Commodity) getJdbcTemplate().queryForObject(str, arrayOfObject, new CommodityRowMapper());
			return localCommodity;
		} catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException) {
			throw new RuntimeException("商品代码[" + paramString + "]不存在！");
		}
	}

	public List getCommoditys(Commodity paramCommodity) {
		return getCommoditys(paramCommodity, null);
	}

	public List getCommoditys(Commodity paramCommodity, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select a.commodityID,a.name,a.status,a.marketdate,a.settledate,decode(a.MarginAlgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.MarginAlgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s,a.breedid,c.BreedName,d.SortName ")
				.append("from T_COMMODITY a,T_A_BREED c,T_A_CMDTYSORT d where a.BreedID=c.BreedID(+) and a.SortID=d.SortID(+) ");
		Object[] arrayOfObject = null;
		ArrayList localArrayList = new ArrayList();
		if ((paramCommodity != null) && (paramCommodity.getBreedID() != null)) {
			localStringBuffer.append("and a.BreedID=? ");
			localArrayList.add(paramCommodity.getBreedID());
		}
		arrayOfObject = localArrayList.toArray();
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
			return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString());
	}

	public List getCommoditysHis(Commodity paramCommodity, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select a.commodityID,a.name,a.status,a.marketdate,a.settledate,decode(a.MarginAlgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.MarginAlgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s,a.breedid,c.BreedName,d.SortName ")
				.append("from T_SETTLECOMMODITY a,T_A_BREED c,T_A_CMDTYSORT d where a.BreedID=c.BreedID(+) and a.SortID=d.SortID(+) ");
		Object[] arrayOfObject = null;
		ArrayList localArrayList = new ArrayList();
		if ((paramCommodity != null) && (paramCommodity.getBreedID() != null)) {
			localStringBuffer.append("and a.BreedID=? ");
			localArrayList.add(paramCommodity.getBreedID());
		}
		arrayOfObject = localArrayList.toArray();
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
			return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString());
	}

	public void insertCommodity(Commodity paramCommodity) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("insert into T_COMMODITY(CommodityID,Name,MaxHoldPositionDay,SortID,Status,ContractFactor,MinPriceMove,BreedID,SpreadAlgr,SpreadUpLmt,SpreadDownLmt,")
				.append("FeeAlgr,FeeRate_B,FeeRate_S,MarginAlgr,MarketDate,SettleDate,SettleDate1,SettleDate2,SettleDate3,SettleDate4,")
				.append("MarginItem1,MarginItem2,MarginItem3,MarginItem4,LastPrice,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B,HistoryCloseFeeRate_S,LimitCmdtyQty,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,SettleMarginRate_B,SettleMarginRate_S,AddedTax,MarginPriceType,LowestSettleFee,FirmCleanQty,SettleMarginAlgr_B,SettleMarginAlgr_S,PayoutAlgr,PayoutRate,FirmMaxHoldQty,AddedTaxFactor,SettleDate5,MarginItem5,MarginItem5_S,MarginItemAssure5,MarginItemAssure5_S,OrderPrivilege_B,OrderPrivilege_S,SettlePriceType,BeforeDays,SpecSettlePrice,FirmMaxHoldQtyAlgr,StartPercentQty,MaxPercentLimit,OneMaxHoldQty,MinQuantityMove,MinSettleMoveQty,MinSettleQty,DelayRecoupRate,DelayRecoupRate_S,SettleWay,DelayFeeWay,MaxFeeRate,StoreRecoupRate,DelaySettlePriceType,SettleMarginType,BeforeDays_M,aheadSettlepriceType,HoldDaysLimit)")
				.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		int[] arrayOfInt = { 12, 12, -5, -5, 5, 8, 8, -5, 5, 8, 8, 5, 8, 8, 5, 91, 91, 91, 91, 91, 91, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
				8, 8, 8, 8, 8, -5, 5, 8, 8, 5, 8, 8, 8, 8, 8, 5, 8, -5, 5, 5, 5, 8, -5, 8, 91, 8, 8, 8, 8, 5, 5, 5, -5, 8, 5, -5, 8, -5, 5, 5, 5, 8,
				8, 5, 5, 8, 8, 8, 4, 4, 4, 4 };
		Object[] arrayOfObject1 = { paramCommodity.getCommodityID(), paramCommodity.getName(), paramCommodity.getMaxHoldPositionDay(),
				paramCommodity.getSortID(), paramCommodity.getStatus() == null ? new Short((short) -1) : paramCommodity.getStatus(),
				paramCommodity.getContractFactor() == null ? new Double(0.0D) : paramCommodity.getContractFactor(),
				paramCommodity.getMinPriceMove() == null ? new Double(0.0D) : paramCommodity.getMinPriceMove(), paramCommodity.getBreedID(),
				paramCommodity.getSpreadAlgr(), paramCommodity.getSpreadUpLmt() == null ? new Double(0.0D) : paramCommodity.getSpreadUpLmt(),
				paramCommodity.getSpreadDownLmt() == null ? new Double(0.0D) : paramCommodity.getSpreadDownLmt(), paramCommodity.getFeeAlgr(),
				paramCommodity.getFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getFeeRate_B(),
				paramCommodity.getFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getFeeRate_S(), paramCommodity.getMarginAlgr(),
				paramCommodity.getMarketDate(), paramCommodity.getSettleDate(), paramCommodity.getSettleDate1(), paramCommodity.getSettleDate2(),
				paramCommodity.getSettleDate3(), paramCommodity.getSettleDate4(),
				paramCommodity.getMarginItem1() == null ? new Double(0.0D) : paramCommodity.getMarginItem1(),
				paramCommodity.getMarginItem2() == null ? new Double(0.0D) : paramCommodity.getMarginItem2(),
				paramCommodity.getMarginItem3() == null ? new Double(0.0D) : paramCommodity.getMarginItem3(),
				paramCommodity.getMarginItem4() == null ? new Double(0.0D) : paramCommodity.getMarginItem4(), paramCommodity.getLastPrice(),
				paramCommodity.getMarginItem1_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem1_S(),
				paramCommodity.getMarginItem2_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem2_S(),
				paramCommodity.getMarginItem3_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem3_S(),
				paramCommodity.getMarginItem4_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem4_S(),
				paramCommodity.getMarginItemAssure1() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure1(),
				paramCommodity.getMarginItemAssure2() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure2(),
				paramCommodity.getMarginItemAssure3() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure3(),
				paramCommodity.getMarginItemAssure4() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure4(),
				paramCommodity.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure1_S(),
				paramCommodity.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure2_S(),
				paramCommodity.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure3_S(),
				paramCommodity.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure4_S(),
				paramCommodity.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getTodayCloseFeeRate_B(),
				paramCommodity.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getTodayCloseFeeRate_S(),
				paramCommodity.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getHistoryCloseFeeRate_B(),
				paramCommodity.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getHistoryCloseFeeRate_S(),
				paramCommodity.getLimitCmdtyQty() == null ? new Long(0L) : paramCommodity.getLimitCmdtyQty(), paramCommodity.getSettleFeeAlgr(),
				paramCommodity.getSettleFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getSettleFeeRate_B(),
				paramCommodity.getSettleFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getSettleFeeRate_S(),
				paramCommodity.getForceCloseFeeAlgr(),
				paramCommodity.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getForceCloseFeeRate_B(),
				paramCommodity.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getForceCloseFeeRate_S(),
				paramCommodity.getSettleMarginRate_B() == null ? new Double(0.0D) : paramCommodity.getSettleMarginRate_B(),
				paramCommodity.getSettleMarginRate_S() == null ? new Double(0.0D) : paramCommodity.getSettleMarginRate_S(),
				paramCommodity.getAddedTax() == null ? new Double(0.0D) : paramCommodity.getAddedTax(), paramCommodity.getMarginPriceType(),
				paramCommodity.getLowestSettleFee() == null ? new Double(0.0D) : paramCommodity.getLowestSettleFee(),
				paramCommodity.getFirmCleanQty() == null ? new Long(0L) : paramCommodity.getFirmCleanQty(), paramCommodity.getSettleMarginAlgr_B(),
				paramCommodity.getSettleMarginAlgr_S(), paramCommodity.getPayoutAlgr(),
				paramCommodity.getPayoutRate() == null ? new Double(0.0D) : paramCommodity.getPayoutRate(),
				paramCommodity.getFirmMaxHoldQty() == null ? new Long(0L) : paramCommodity.getFirmMaxHoldQty(),
				paramCommodity.getAddedTaxFactor() == null ? new Double(0.0D) : paramCommodity.getAddedTaxFactor(), paramCommodity.getSettleDate5(),
				paramCommodity.getMarginItem5() == null ? new Double(0.0D) : paramCommodity.getMarginItem5(),
				paramCommodity.getMarginItem5_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem5_S(),
				paramCommodity.getMarginItemAssure5() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure5(),
				paramCommodity.getMarginItemAssure5_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure5_S(),
				paramCommodity.getOrderPrivilege_B(), paramCommodity.getOrderPrivilege_S(), paramCommodity.getSettlePriceType(),
				paramCommodity.getBeforeDays() == null ? new Long(0L) : paramCommodity.getBeforeDays(),
				paramCommodity.getSpecSettlePrice() == null ? new Double(0.0D) : paramCommodity.getSpecSettlePrice(),
				paramCommodity.getFirmMaxHoldQtyAlgr(),
				paramCommodity.getStartPercentQty() == null ? new Long(0L) : paramCommodity.getStartPercentQty(),
				paramCommodity.getMaxPercentLimit() == null ? new Double(0.0D) : paramCommodity.getMaxPercentLimit(),
				paramCommodity.getOneMaxHoldQty() == null ? new Long(0L) : paramCommodity.getOneMaxHoldQty(),
				Integer.valueOf(paramCommodity.getMinQuantityMove()), Integer.valueOf(paramCommodity.getMinSettleMoveQty()),
				Integer.valueOf(paramCommodity.getMinSettleQty()),
				paramCommodity.getDelayRecoupRate() == null ? new Double(0.0D) : paramCommodity.getDelayRecoupRate(),
				paramCommodity.getDelayRecoupRate_S() == null ? new Double(0.0D) : paramCommodity.getDelayRecoupRate_S(),
				paramCommodity.getSettleWay(), paramCommodity.getDelayFeeWay(),
				paramCommodity.getMaxFeeRate() == null ? new Double(0.0D) : paramCommodity.getMaxFeeRate(),
				paramCommodity.getStoreRecoupRate() == null ? new Double(0.0D) : paramCommodity.getStoreRecoupRate(),
				Integer.valueOf(Integer.valueOf(paramCommodity.getDelaySettlePriceType()) == null ? new Integer(0).intValue()
						: paramCommodity.getDelaySettlePriceType()),
				Integer.valueOf(paramCommodity.getSettleMarginType()), Integer.valueOf(paramCommodity.getBeforeDays_M()),
				Integer.valueOf(paramCommodity.getAheadSettlePriceType()), Integer.valueOf(paramCommodity.getHoldDaysLimit()) };
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject1.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
		}
		try {
			getJdbcTemplate().update(localStringBuffer.toString(), arrayOfObject1, arrayOfInt);
		} catch (Exception localException1) {
			throw new RuntimeException("此商品已存在！");
		}
		String str = "insert into T_A_COMMODITYTRADEPROP values (?,?,sysdate)";
		Object[] arrayOfObject2 = { paramCommodity.getCommodityID(), "" };
		Set localSet = paramCommodity.getTradeTime();
		Iterator localIterator = localSet.iterator();
		try {
			while (localIterator.hasNext()) {
				TradeTime localTradeTime = (TradeTime) localIterator.next();
				arrayOfObject2[1] = localTradeTime.getSectionID();
				this.log.debug("paramProp[1]: " + arrayOfObject2[1]);
				getJdbcTemplate().update(str, arrayOfObject2);
			}
		} catch (Exception localException2) {
			throw new RuntimeException("此商品已存在！");
		}
		CommodityStoredProcedure localCommodityStoredProcedure = new CommodityStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_CommodityID", paramCommodity.getCommodityID());
		Map localMap = localCommodityStoredProcedure.execute(localHashMap);
	}

	public void updateCommodity(Commodity paramCommodity) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("update T_COMMODITY set Name=?,MaxHoldPositionDay=?,SortID=?,Status=?,ContractFactor=?,MinPriceMove=?,BreedID=?,SpreadAlgr=?,SpreadUpLmt=?,SpreadDownLmt=?,")
				.append("FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,MarginAlgr=?,MarketDate=?,SettleDate=?,SettleDate1=?,SettleDate2=?,SettleDate3=?,SettleDate4=?,")
				.append("MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,LastPrice=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?, LimitCmdtyQty=?, SettleFeeAlgr=?, SettleFeeRate_B=?, SettleFeeRate_S=?, ForceCloseFeeAlgr=?, ForceCloseFeeRate_B=?, ForceCloseFeeRate_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,AddedTax=?,MarginPriceType=?,LowestSettleFee=?,FirmCleanQty=?,SettleMarginAlgr_B=?,SettleMarginAlgr_S=?,PayoutAlgr=?,PayoutRate=?,FirmMaxHoldQty=?,AddedTaxFactor=?,SettleDate5=?,MarginItem5=?,MarginItem5_S=?,MarginItemAssure5=?,MarginItemAssure5_S=?,OrderPrivilege_B=?,OrderPrivilege_S=?,SettlePriceType=?,BeforeDays=?,SpecSettlePrice=?,FirmMaxHoldQtyAlgr=?,StartPercentQty=?,MaxPercentLimit=?,OneMaxHoldQty=?,MinQuantityMove=?,MinSettleMoveQty=?,MinSettleQty=?,DelayRecoupRate=?,DelayRecoupRate_S=?,SettleWay=?,DelayFeeWay=?,MaxFeeRate=?,StoreRecoupRate=?,DelaySettlePriceType=?,SettleMarginType=?,BeforeDays_M=?,aheadSettlepriceType=?,holdDaysLimit=?   where CommodityID=?");
		int[] arrayOfInt = { 12, -5, -5, 5, 8, 8, -5, 5, 8, 8, 5, 8, 8, 5, 91, 91, 91, 91, 91, 91, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
				8, 8, 8, 8, -5, 5, 8, 8, 5, 8, 8, 8, 8, 8, 5, 8, -5, 5, 5, 5, 8, -5, 8, 91, 8, 8, 8, 8, 5, 5, 5, -5, 8, 5, -5, 8, -5, 5, 5, 5, 8, 8,
				5, 5, 8, 8, 4, 4, 4, 4, 4, 12 };
		Object[] arrayOfObject1 = { paramCommodity.getName(), paramCommodity.getHoldDaysLimit() == 1 ? paramCommodity.getMaxHoldPositionDay() : null,
				paramCommodity.getSortID(), paramCommodity.getStatus() == null ? new Short((short) -1) : paramCommodity.getStatus(),
				paramCommodity.getContractFactor() == null ? new Double(0.0D) : paramCommodity.getContractFactor(),
				paramCommodity.getMinPriceMove() == null ? new Double(0.0D) : paramCommodity.getMinPriceMove(), paramCommodity.getBreedID(),
				paramCommodity.getSpreadAlgr(), paramCommodity.getSpreadUpLmt() == null ? new Double(0.0D) : paramCommodity.getSpreadUpLmt(),
				paramCommodity.getSpreadDownLmt() == null ? new Double(0.0D) : paramCommodity.getSpreadDownLmt(), paramCommodity.getFeeAlgr(),
				paramCommodity.getFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getFeeRate_B(),
				paramCommodity.getFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getFeeRate_S(), paramCommodity.getMarginAlgr(),
				paramCommodity.getMarketDate(), paramCommodity.getSettleDate(), paramCommodity.getSettleDate1(), paramCommodity.getSettleDate2(),
				paramCommodity.getSettleDate3(), paramCommodity.getSettleDate4(),
				paramCommodity.getMarginItem1() == null ? new Double(0.0D) : paramCommodity.getMarginItem1(),
				paramCommodity.getMarginItem2() == null ? new Double(0.0D) : paramCommodity.getMarginItem2(),
				paramCommodity.getMarginItem3() == null ? new Double(0.0D) : paramCommodity.getMarginItem3(),
				paramCommodity.getMarginItem4() == null ? new Double(0.0D) : paramCommodity.getMarginItem4(), paramCommodity.getLastPrice(),
				paramCommodity.getMarginItem1_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem1_S(),
				paramCommodity.getMarginItem2_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem2_S(),
				paramCommodity.getMarginItem3_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem3_S(),
				paramCommodity.getMarginItem4_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem4_S(),
				paramCommodity.getMarginItemAssure1() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure1(),
				paramCommodity.getMarginItemAssure2() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure2(),
				paramCommodity.getMarginItemAssure3() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure3(),
				paramCommodity.getMarginItemAssure4() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure4(),
				paramCommodity.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure1_S(),
				paramCommodity.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure2_S(),
				paramCommodity.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure3_S(),
				paramCommodity.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure4_S(),
				paramCommodity.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getTodayCloseFeeRate_B(),
				paramCommodity.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getTodayCloseFeeRate_S(),
				paramCommodity.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getHistoryCloseFeeRate_B(),
				paramCommodity.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getHistoryCloseFeeRate_S(),
				paramCommodity.getLimitCmdtyQty() == null ? new Long(0L) : paramCommodity.getLimitCmdtyQty(), paramCommodity.getSettleFeeAlgr(),
				paramCommodity.getSettleFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getSettleFeeRate_B(),
				paramCommodity.getSettleFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getSettleFeeRate_S(),
				paramCommodity.getForceCloseFeeAlgr(),
				paramCommodity.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramCommodity.getForceCloseFeeRate_B(),
				paramCommodity.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramCommodity.getForceCloseFeeRate_S(),
				paramCommodity.getSettleMarginRate_B() == null ? new Double(0.0D) : paramCommodity.getSettleMarginRate_B(),
				paramCommodity.getSettleMarginRate_S() == null ? new Double(0.0D) : paramCommodity.getSettleMarginRate_S(),
				paramCommodity.getAddedTax() == null ? new Double(0.0D) : paramCommodity.getAddedTax(), paramCommodity.getMarginPriceType(),
				paramCommodity.getLowestSettleFee() == null ? new Double(0.0D) : paramCommodity.getLowestSettleFee(),
				paramCommodity.getFirmCleanQty() == null ? new Long(0L) : paramCommodity.getFirmCleanQty(), paramCommodity.getSettleMarginAlgr_B(),
				paramCommodity.getSettleMarginAlgr_S(), paramCommodity.getPayoutAlgr(),
				paramCommodity.getPayoutRate() == null ? new Double(0.0D) : paramCommodity.getPayoutRate(),
				paramCommodity.getFirmMaxHoldQty() == null ? new Long(0L) : paramCommodity.getFirmMaxHoldQty(),
				paramCommodity.getAddedTaxFactor() == null ? new Double(0.0D) : paramCommodity.getAddedTaxFactor(), paramCommodity.getSettleDate5(),
				paramCommodity.getMarginItem5() == null ? new Double(0.0D) : paramCommodity.getMarginItem5(),
				paramCommodity.getMarginItem5_S() == null ? new Double(0.0D) : paramCommodity.getMarginItem5_S(),
				paramCommodity.getMarginItemAssure5() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure5(),
				paramCommodity.getMarginItemAssure5_S() == null ? new Double(0.0D) : paramCommodity.getMarginItemAssure5_S(),
				paramCommodity.getOrderPrivilege_B(), paramCommodity.getOrderPrivilege_S(), paramCommodity.getSettlePriceType(),
				paramCommodity.getBeforeDays() == null ? new Long(0L) : paramCommodity.getBeforeDays(),
				paramCommodity.getSpecSettlePrice() == null ? new Double(0.0D) : paramCommodity.getSpecSettlePrice(),
				paramCommodity.getFirmMaxHoldQtyAlgr(),
				paramCommodity.getStartPercentQty() == null ? new Long(0L) : paramCommodity.getStartPercentQty(),
				paramCommodity.getMaxPercentLimit() == null ? new Double(0.0D) : paramCommodity.getMaxPercentLimit(),
				paramCommodity.getOneMaxHoldQty() == null ? new Long(0L) : paramCommodity.getOneMaxHoldQty(),
				Integer.valueOf(paramCommodity.getMinQuantityMove()), Integer.valueOf(paramCommodity.getMinSettleMoveQty()),
				Integer.valueOf(paramCommodity.getMinSettleQty()),
				paramCommodity.getDelayRecoupRate() == null ? new Double(0.0D) : paramCommodity.getDelayRecoupRate(),
				paramCommodity.getDelayRecoupRate_S() == null ? new Double(0.0D) : paramCommodity.getDelayRecoupRate_S(),
				paramCommodity.getSettleWay(), paramCommodity.getDelayFeeWay(), paramCommodity.getMaxFeeRate(),
				paramCommodity.getStoreRecoupRate() == null ? new Double(0.0D) : paramCommodity.getStoreRecoupRate(),
				Integer.valueOf(Integer.valueOf(paramCommodity.getDelaySettlePriceType()) == null ? new Integer(0).intValue()
						: paramCommodity.getDelaySettlePriceType()),
				Integer.valueOf(paramCommodity.getSettleMarginType()), Integer.valueOf(paramCommodity.getBeforeDays_M()),
				Integer.valueOf(paramCommodity.getAheadSettlePriceType()), Integer.valueOf(paramCommodity.getHoldDaysLimit()),
				paramCommodity.getCommodityID() };
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject1.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
		}
		try {
			getJdbcTemplate().update(localStringBuffer.toString(), arrayOfObject1, arrayOfInt);
		} catch (Exception localException) {
			throw new RuntimeException("修改失败！");
		}
		String str1 = "delete from T_A_COMMODITYTRADEPROP c where c.CommodityID=?";
		Object[] arrayOfObject2 = { paramCommodity.getCommodityID() };
		getJdbcTemplate().update(str1, arrayOfObject2);
		String str2 = "insert into T_A_COMMODITYTRADEPROP values (?,?,sysdate)";
		Object[] arrayOfObject3 = { paramCommodity.getCommodityID(), "" };
		Set localSet = paramCommodity.getTradeTime();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			TradeTime localTradeTime = (TradeTime) localIterator.next();
			arrayOfObject3[1] = localTradeTime.getSectionID();
			this.log.debug("paramProp[1]: " + arrayOfObject3[1]);
			getJdbcTemplate().update(str2, arrayOfObject3);
		}
	}

	public void deleteCommodityById(String paramString) {
		Assert.hasText(paramString);
		Object[] arrayOfObject1 = { paramString };
		String str1 = "select count(*) from T_FIRMHOLDSUM where CommodityID=?";
		if (getJdbcTemplate().queryForInt(str1, arrayOfObject1) > 0) {
			throw new RuntimeException("有此商品的持仓信息，不能删除！");
		}
		str1 = "select count(*) from T_Trade where CommodityID = ?";
		if (getJdbcTemplate().queryForInt(str1, arrayOfObject1) > 0) {
			throw new RuntimeException("有此商品的成交信息，不能删除！");
		}
		str1 = "delete from T_COMMODITY where CommodityID=?";
		this.log.debug("sql: " + str1);
		this.logger.debug("CommodityID: " + arrayOfObject1[0]);
		getJdbcTemplate().update(str1, arrayOfObject1);
		String str2 = "delete from T_A_COMMODITYTRADEPROP c where c.CommodityID=?";
		Object[] arrayOfObject2 = { paramString };
		getJdbcTemplate().update(str2, arrayOfObject2);
	}

	public void updateCommodityStatus(Commodity paramCommodity) {
		String str = "update T_COMMODITY set status=? where CommodityID=?";
		Object[] arrayOfObject = { paramCommodity.getStatus(), paramCommodity.getCommodityID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public List getCurCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select * from (")
				.append("select  rownum r,a.name,a.breedID,a.commodityid,a.status,a.marketDate,a.settleDate,decode(a.marginalgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.marginalgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s ")
				.append("from T_COMMODITY a where 1=1 ");
		Object[] arrayOfObject = null;
		ArrayList localArrayList = new ArrayList();
		if (paramCommodity != null) {
			if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear())) && (paramCommodity.getMonth() != null)
					&& (!"".equals(paramCommodity.getMonth()))) {
				localStringBuffer.append("and to_char(settleDate,'yyyyMM')=? ");
				localArrayList.add(paramCommodity.getYear().trim() + paramCommodity.getMonth().trim());
			} else if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear()))) {
				localStringBuffer.append("and to_char(settleDate,'yyyy')=? ");
				localArrayList.add(paramCommodity.getYear().trim());
			} else if ((paramCommodity.getMonth() != null) && (!"".equals(paramCommodity.getMonth()))) {
				localStringBuffer.append("and to_char(settleDate,'MM')=? ");
				localArrayList.add(paramCommodity.getMonth().trim());
			}
		}
		localStringBuffer.append(" order by a.commodityID ").append(") where r between " + paramInt1 + " and " + paramInt2 + " ");
		arrayOfObject = localArrayList.toArray();
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
			return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString());
	}

	public List getHisCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select * from (")
				.append("select rownum r,a.name,a.breedID,a.commodityid,a.status,a.marketDate,a.settleDate,decode(a.marginalgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.marginalgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s ")
				.append("from T_SETTLECOMMODITY a, t_Systemstatus b where 1=1 ")
				.append("and to_char(settleDate,'yyyyMMdd') < to_char(b.tradedate,'yyyyMMdd') ");
		Object[] arrayOfObject = null;
		ArrayList localArrayList = new ArrayList();
		if (paramCommodity != null) {
			if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear())) && (paramCommodity.getMonth() != null)
					&& (!"".equals(paramCommodity.getMonth()))) {
				localStringBuffer.append("and to_char(settleDate,'yyyyMM')=? ");
				localArrayList.add(paramCommodity.getYear().trim() + paramCommodity.getMonth().trim());
			} else if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear()))) {
				localStringBuffer.append("and to_char(settleDate,'yyyy')=? ");
				localArrayList.add(paramCommodity.getYear().trim());
			} else if ((paramCommodity.getMonth() != null) && (!"".equals(paramCommodity.getMonth()))) {
				localStringBuffer.append("and to_char(settleDate,'MM')=? ");
				localArrayList.add(paramCommodity.getMonth().trim());
			}
		}
		localStringBuffer.append(") where r between " + paramInt1 + " and " + paramInt2 + " ");
		arrayOfObject = localArrayList.toArray();
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
			return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString());
	}

	public String getSystemStatus() {
		String str1 = "select status from t_systemstatus t";
		Object localObject = getJdbcTemplate().queryForObject(str1, Object.class);
		String str2 = "";
		if (localObject != null) {
			str2 = localObject.toString();
		}
		return str2;
	}

	public void updateCommodityMargin(Commodity paramCommodity) {
		String str = "update T_commodity set MarginRate_B=?, MarginRate_S=?, MarginAssure_B=?, MarginAssure_S=? where commodityID = ?";
		Object[] arrayOfObject = { paramCommodity.getMarginRate_B(), paramCommodity.getMarginRate_S(), paramCommodity.getMarginAssure_B(),
				paramCommodity.getMarginAssure_S(), paramCommodity.getCommodityID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("修改失败！");
		}
	}

	public void updateLastPrice(Commodity paramCommodity) {
		UpdateLastPriceProcedure localUpdateLastPriceProcedure = new UpdateLastPriceProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		this.logger.debug("p_CommodityID:" + paramCommodity.getCommodityID());
		localHashMap.put("p_CommodityID", paramCommodity.getCommodityID());
		localHashMap.put("p_Price", paramCommodity.getLastPrice());
		Map localMap = localUpdateLastPriceProcedure.execute(localHashMap);
	}

	public List getCommodityList() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select commodityid from T_COMMODITY a where 1=1 ");
		return getJdbcTemplate().queryForList(localStringBuffer.toString());
	}

	public int queryCommodityCount() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select count(*)  cmdtyCount ").append("from T_COMMODITY a where 1=1 ");
		localStringBuffer.append(" order by a.commodityID");
		this.log.debug("sql: " + localStringBuffer.toString());
		Map localMap = (Map) getJdbcTemplate().queryForList(localStringBuffer.toString()).get(0);
		String str = String.valueOf(localMap.get("cmdtyCount"));
		return Integer.parseInt(str);
	}

	public int queryHisCommodityCount() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select count(*) cmdtyCount ").append("from T_SETTLECOMMODITY a, t_Systemstatus b where 1=1 ")
				.append("and to_char(settleDate,'yyyyMMdd') < to_char(b.tradedate,'yyyyMMdd') ");
		Map localMap = (Map) getJdbcTemplate().queryForList(localStringBuffer.toString()).get(0);
		String str = String.valueOf(localMap.get("cmdtyCount"));
		return Integer.parseInt(str);
	}

	private class UpdateLastPriceProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_UpdateLastPrice";

		public UpdateLastPriceProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_UpdateLastPrice");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_Price", 2));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	class CommodityRowMapper implements RowMapper {
		CommodityRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToCommodity(paramResultSet);
		}

		private Commodity rsToCommodity(ResultSet paramResultSet) throws SQLException {
			Commodity localCommodity = new Commodity();
			localCommodity.setCommodityID(paramResultSet.getString("CommodityID"));
			localCommodity.setMarketCode(paramResultSet.getString("MarketCode"));
			localCommodity.setName(paramResultSet.getString("Name"));
			localCommodity.setSortID(new Long(paramResultSet.getLong("SortID")));
			localCommodity.setStatus(new Short(paramResultSet.getShort("Status")));
			localCommodity.setContractFactor(new Double(paramResultSet.getDouble("ContractFactor")));
			localCommodity.setMinPriceMove(new Double(paramResultSet.getDouble("MinPriceMove")));
			localCommodity.setBreedID(new Long(paramResultSet.getLong("BreedID")));
			localCommodity.setSpreadAlgr(new Short(paramResultSet.getShort("SpreadAlgr")));
			localCommodity.setSpreadUpLmt(new Double(paramResultSet.getDouble("SpreadUpLmt")));
			localCommodity.setSpreadDownLmt(new Double(paramResultSet.getDouble("SpreadDownLmt")));
			localCommodity.setFeeAlgr(new Short(paramResultSet.getShort("FeeAlgr")));
			localCommodity.setFeeRate_B(new Double(paramResultSet.getDouble("FeeRate_B")));
			localCommodity.setFeeRate_S(new Double(paramResultSet.getDouble("FeeRate_S")));
			localCommodity.setMarginAlgr(new Short(paramResultSet.getShort("MarginAlgr")));
			localCommodity.setMarginRate_B(new Double(paramResultSet.getDouble("MarginRate_B")));
			localCommodity.setMarginRate_S(new Double(paramResultSet.getDouble("MarginRate_S")));
			localCommodity.setMarketDate(paramResultSet.getTimestamp("MarketDate"));
			localCommodity.setSettleDate(paramResultSet.getTimestamp("SettleDate"));
			localCommodity.setSettleDate1(paramResultSet.getTimestamp("SettleDate1"));
			localCommodity.setSettleDate2(paramResultSet.getTimestamp("SettleDate2"));
			localCommodity.setSettleDate3(paramResultSet.getTimestamp("SettleDate3"));
			localCommodity.setSettleDate4(paramResultSet.getTimestamp("SettleDate4"));
			localCommodity.setFeeItem1(new Double(paramResultSet.getDouble("FeeItem1")));
			localCommodity.setFeeItem2(new Double(paramResultSet.getDouble("FeeItem2")));
			localCommodity.setMarginItem1(new Double(paramResultSet.getDouble("MarginItem1")));
			localCommodity.setMarginItem2(new Double(paramResultSet.getDouble("MarginItem2")));
			localCommodity.setMarginItem3(new Double(paramResultSet.getDouble("MarginItem3")));
			localCommodity.setMarginItem4(new Double(paramResultSet.getDouble("SettleMarginRate_B")));
			localCommodity.setStartTime(paramResultSet.getString("StartTime"));
			localCommodity.setEndTime(paramResultSet.getString("EndTime"));
			localCommodity.setLastPrice(new Double(paramResultSet.getDouble("LastPrice")));
			localCommodity.setMarketFeeAlgr(new Short(paramResultSet.getShort("MarketFeeAlgr")));
			localCommodity.setMarketFeeRate_B(new Double(paramResultSet.getDouble("MarketFeeRate_B")));
			localCommodity.setMarketFeeRate_S(new Double(paramResultSet.getDouble("MarketFeeRate_S")));
			localCommodity.setMarginItem1_S(new Double(paramResultSet.getDouble("MarginItem1_S")));
			localCommodity.setMarginItem2_S(new Double(paramResultSet.getDouble("MarginItem2_S")));
			localCommodity.setMarginItem3_S(new Double(paramResultSet.getDouble("MarginItem3_S")));
			localCommodity.setMarginItem4_S(new Double(paramResultSet.getDouble("SettleMarginRate_S")));
			localCommodity.setMarginAssure_B(new Double(paramResultSet.getDouble("marginAssure_B")));
			localCommodity.setMarginAssure_S(new Double(paramResultSet.getDouble("marginAssure_S")));
			localCommodity.setMarginItemAssure1(new Double(paramResultSet.getDouble("marginItemAssure1")));
			localCommodity.setMarginItemAssure1_S(new Double(paramResultSet.getDouble("marginItemAssure1_S")));
			localCommodity.setMarginItemAssure2(new Double(paramResultSet.getDouble("marginItemAssure2")));
			localCommodity.setMarginItemAssure2_S(new Double(paramResultSet.getDouble("marginItemAssure2_S")));
			localCommodity.setMarginItemAssure3(new Double(paramResultSet.getDouble("marginItemAssure3")));
			localCommodity.setMarginItemAssure3_S(new Double(paramResultSet.getDouble("marginItemAssure3_S")));
			localCommodity.setMarginItemAssure4(new Double(paramResultSet.getDouble("marginItemAssure4")));
			localCommodity.setMarginItemAssure4_S(new Double(paramResultSet.getDouble("marginItemAssure4_S")));
			localCommodity.setTodayCloseFeeRate_B(new Double(paramResultSet.getDouble("TodayCloseFeeRate_B")));
			localCommodity.setTodayCloseFeeRate_S(new Double(paramResultSet.getDouble("TodayCloseFeeRate_S")));
			localCommodity.setHistoryCloseFeeRate_B(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_B")));
			localCommodity.setHistoryCloseFeeRate_S(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_S")));
			localCommodity.setSettleFeeAlgr(new Short(paramResultSet.getShort("SettleFeeAlgr")));
			localCommodity.setSettleFeeRate_B(new Double(paramResultSet.getDouble("SettleFeeRate_B")));
			localCommodity.setSettleFeeRate_S(new Double(paramResultSet.getDouble("SettleFeeRate_S")));
			localCommodity.setForceCloseFeeAlgr(new Short(paramResultSet.getShort("ForceCloseFeeAlgr")));
			localCommodity.setForceCloseFeeRate_B(new Double(paramResultSet.getDouble("ForceCloseFeeRate_B")));
			localCommodity.setForceCloseFeeRate_S(new Double(paramResultSet.getDouble("ForceCloseFeeRate_S")));
			localCommodity.setLimitCmdtyQty(new Long(paramResultSet.getLong("LimitCmdtyQty")));
			localCommodity.setSettleMarginRate_B(new Double(paramResultSet.getDouble("SettleMarginRate_B")));
			localCommodity.setSettleMarginRate_S(new Double(paramResultSet.getDouble("SettleMarginRate_S")));
			localCommodity.setAddedTax(new Double(paramResultSet.getDouble("AddedTax")));
			localCommodity.setMarginPriceType(new Short(paramResultSet.getShort("MarginPriceType")));
			localCommodity.setLowestSettleFee(new Double(paramResultSet.getDouble("LowestSettleFee")));
			localCommodity.setFirmCleanQty(new Long(paramResultSet.getLong("FirmCleanQty")));
			localCommodity.setSettleMarginAlgr_B(new Short(paramResultSet.getShort("SettleMarginAlgr_B")));
			localCommodity.setSettleMarginAlgr_S(new Short(paramResultSet.getShort("SettleMarginAlgr_S")));
			return localCommodity;
		}
	}

	private class CommodityStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "SP_F_SynchCommodity";

		public CommodityStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "SP_F_SynchCommodity");
			declareParameter(new SqlParameter("p_CommodityID", 12));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
