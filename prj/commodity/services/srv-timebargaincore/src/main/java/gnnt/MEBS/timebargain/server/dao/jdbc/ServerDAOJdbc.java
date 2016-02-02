package gnnt.MEBS.timebargain.server.dao.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Customer;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.NotTradeDay;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Tariff;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.util.Arith;

public class ServerDAOJdbc extends BaseDAOJdbc implements ServerDAO {
	private Log log = LogFactory.getLog(getClass());

	public Map getFirmMap() {
		HashMap localHashMap = new HashMap();
		String str1 = "select FirmID,Status,MaxHoldQty,MinClearDeposit,MaxOverdraft,(select TariffID from M_Firm where FirmID=T_Firm.FirmID) as TariffID from T_Firm where Status <> 2 ";
		this.log.debug("sql:" + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			Firm localFirm = new Firm();
			String str2 = (String) localMap.get("FirmID");
			localFirm.setFirmID(str2);
			localFirm.setStatus(((BigDecimal) localMap.get("Status")).shortValue());
			localFirm.setMaxHoldQty(((BigDecimal) localMap.get("MaxHoldQty")).longValue());
			localFirm.setMinClearDeposit(((BigDecimal) localMap.get("MinClearDeposit")).doubleValue());
			localFirm.setMaxOverdraft(((BigDecimal) localMap.get("MaxOverdraft")).doubleValue());
			localFirm.setTariffID((String) localMap.get("TariffID"));
			localHashMap.put(str2, localFirm);
		}
		return localHashMap;
	}

	public Map getTraderMap() {
		HashMap localHashMap = new HashMap();
		String str1 = "select a.TraderID,a.FirmID,b.OperateCode from M_Trader a,T_Trader b where a.TraderID=b.TraderID(+)";
		this.log.debug("sql:" + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			Trader localTrader = new Trader();
			String str2 = (String) localMap.get("TraderID");
			localTrader.setTraderID(str2);
			String str3 = (String) localMap.get("FirmID");
			localTrader.setFirmID(str3);
			String str4 = (String) localMap.get("OperateCode");
			if (!StringUtils.isEmpty(str4)) {
				String[] arrayOfString = str4.split(",");
				ArrayList localArrayList = new ArrayList();
				for (int j = 0; j < arrayOfString.length; j++) {
					localArrayList.add(str3 + arrayOfString[j]);
				}
				localTrader.setOperateCustomerList(localArrayList);
			}
			localHashMap.put(str2, localTrader);
		}
		return localHashMap;
	}

	public Map getConsignerMap() {
		HashMap localHashMap = new HashMap();
		String str1 = "select AgentTraderID consignerID,Name,Type,Status,OperateFirm from M_AgentTrader";
		this.log.debug("sql:" + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			Consigner localConsigner = new Consigner();
			String str2 = (String) localMap.get("ConsignerID");
			this.log.debug("consignerID:" + str2);
			localConsigner.setConsignerID(str2);
			localConsigner.setName((String) localMap.get("Name"));
			localConsigner.setType(((BigDecimal) localMap.get("Type")).shortValue());
			localConsigner.setStatus(((BigDecimal) localMap.get("Status")).shortValue());
			String str3 = (String) localMap.get("OperateFirm");
			if (!StringUtils.isEmpty(str3)) {
				String[] arrayOfString = str3.split(",");
				ArrayList localArrayList = new ArrayList();
				for (int j = 0; j < arrayOfString.length; j++) {
					localArrayList.add(arrayOfString[j]);
				}
				localConsigner.setOperateFirmList(localArrayList);
			}
			localHashMap.put(str2, localConsigner);
		}
		return localHashMap;
	}

	public Map getCustomerMap() {
		HashMap localHashMap = new HashMap();
		String str1 = "select * from T_Customer";
		this.log.debug("sql:" + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			Customer localCustomer = new Customer();
			String str2 = (String) localMap.get("CustomerID");
			localCustomer.setCustomerID(str2);
			localCustomer.setName((String) localMap.get("Name"));
			localCustomer.setCode((String) localMap.get("Code"));
			localCustomer.setStatus(((BigDecimal) localMap.get("Status")).shortValue());
			localCustomer.setFirmID((String) localMap.get("FirmID"));
			localHashMap.put(str2, localCustomer);
		}
		return localHashMap;
	}

	public Map getFirmMarginMap(String paramString) {
		HashMap localHashMap = new HashMap();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(
				"select CommodityID,MarginAlgr,MarginRate_B,MarginRate_S,MarginAssure_B,MarginAssure_S from T_A_FirmMargin where FirmID = ? ");
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql:" + localStringBuffer.toString());
		this.log.debug("firmID:" + arrayOfObject[0]);
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		for (int i = 0; i < localList.size(); i++) {
			Commodity localCommodity = new Commodity();
			Map localMap = (Map) localList.get(i);
			String str = (String) localMap.get("CommodityID");
			localCommodity.setCommodityID(str);
			localCommodity.setMarginAlgr(((BigDecimal) localMap.get("MarginAlgr")).shortValue());
			localCommodity.setMarginRate_B(((BigDecimal) localMap.get("MarginRate_B")).doubleValue());
			localCommodity.setMarginRate_S(((BigDecimal) localMap.get("MarginRate_S")).doubleValue());
			localCommodity.setMarginAssure_B(((BigDecimal) localMap.get("MarginAssure_B")).doubleValue());
			localCommodity.setMarginAssure_S(((BigDecimal) localMap.get("MarginAssure_S")).doubleValue());
			localHashMap.put(str, localCommodity);
		}
		return localHashMap;
	}

	public Map getFirmFeeMap(String paramString) {
		HashMap localHashMap = new HashMap();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select CommodityID,FeeAlgr,FeeRate_B,FeeRate_S from T_A_FirmFee where FirmID = ? ");
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql:" + localStringBuffer.toString());
		this.log.debug("firmID:" + arrayOfObject[0]);
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		for (int i = 0; i < localList.size(); i++) {
			Commodity localCommodity = new Commodity();
			Map localMap = (Map) localList.get(i);
			String str = (String) localMap.get("CommodityID");
			localCommodity.setCommodityID(str);
			localCommodity.setFeeAlgr(((BigDecimal) localMap.get("FeeAlgr")).shortValue());
			localCommodity.setFeeRate_B(((BigDecimal) localMap.get("FeeRate_B")).doubleValue());
			localCommodity.setFeeRate_S(((BigDecimal) localMap.get("FeeRate_S")).doubleValue());
			localHashMap.put(str, localCommodity);
		}
		return localHashMap;
	}

	public Map getTariffMap() {
		HashMap localHashMap = new HashMap();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select TariffID,CommodityID,FeeAlgr,FeeRate_B,FeeRate_S from T_A_Tariff ");
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
		for (int i = 0; i < localList.size(); i++) {
			Tariff localTariff = new Tariff();
			Map localMap = (Map) localList.get(i);
			String str1 = (String) localMap.get("TariffID");
			localTariff.setTariffID(str1);
			String str2 = (String) localMap.get("CommodityID");
			localTariff.setCommodityID(str2);
			localTariff.setFeeAlgr(((BigDecimal) localMap.get("FeeAlgr")).shortValue());
			localTariff.setFeeRate_B(((BigDecimal) localMap.get("FeeRate_B")).doubleValue());
			localTariff.setFeeRate_S(((BigDecimal) localMap.get("FeeRate_S")).doubleValue());
			localHashMap.put(str1 + "_" + str2, localTariff);
		}
		return localHashMap;
	}

	public List getCommodityList() {
		return getCommodityListByID(null);
	}

	public List getCommodityListByID(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer("select a.* from T_Commodity a where a.Status <> 1 ");
		if ((paramString != null) && (!paramString.equals(""))) {
			localStringBuffer.append(" and a.CommodityID='").append(paramString).append("'");
		}
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
		return getCommodityList(localList);
	}

	public List getCommodityList(Integer paramInteger) {
		StringBuffer localStringBuffer = new StringBuffer(
				"select a.* from T_Commodity a ,T_A_CommodityTradeProp b where a.Status <> 1 and a.CommodityID=b.CommodityID(+) ");
		Object[] arrayOfObject = null;
		ArrayList localArrayList = new ArrayList();
		if (paramInteger != null) {
			localStringBuffer.append(" and b.SectionID=?");
			localArrayList.add(paramInteger);
		}
		arrayOfObject = localArrayList.toArray();
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		return getCommodityList(localList);
	}

	private List getCommodityList(List paramList) {
		ArrayList localArrayList = new ArrayList();
		for (int i = 0; i < paramList.size(); i++) {
			Commodity localCommodity = new Commodity();
			Map localMap = (Map) paramList.get(i);
			localCommodity.setCommodityID((String) localMap.get("CommodityID"));
			localCommodity.setName((String) localMap.get("Name"));
			localCommodity.setStatus(((BigDecimal) localMap.get("Status")).shortValue());
			localCommodity.setBreedID(((BigDecimal) localMap.get("BreedID")).longValue());
			localCommodity.setSortID(((BigDecimal) localMap.get("SortID")).longValue());
			localCommodity.setContractFactor(((BigDecimal) localMap.get("ContractFactor")).doubleValue());
			localCommodity.setMinPriceMove(((BigDecimal) localMap.get("MinPriceMove")).doubleValue());
			localCommodity.setLastPrice(((BigDecimal) localMap.get("LastPrice")).doubleValue());
			localCommodity.setSpreadAlgr(((BigDecimal) localMap.get("SpreadAlgr")).shortValue());
			double d1 = ((BigDecimal) localMap.get("SpreadUpLmt")).doubleValue();
			double d2 = ((BigDecimal) localMap.get("SpreadDownLmt")).doubleValue();
			if (localCommodity.getSpreadAlgr() == 1) {
				d1 = Math.round(Arith.mul(localCommodity.getLastPrice(), d1) / localCommodity.getMinPriceMove()) * localCommodity.getMinPriceMove();
				d1 = Arith.add(localCommodity.getLastPrice(), d1);
				d2 = Math.round(Arith.mul(localCommodity.getLastPrice(), d2) / localCommodity.getMinPriceMove()) * localCommodity.getMinPriceMove();
				d2 = Arith.sub(localCommodity.getLastPrice(), d2);
			} else if (localCommodity.getSpreadAlgr() == 2) {
				d1 = Arith.add(localCommodity.getLastPrice(), d1);
				d2 = Arith.sub(localCommodity.getLastPrice(), d2);
			}
			localCommodity.setSpreadUpLmt(d1);
			localCommodity.setSpreadDownLmt(d2);
			localCommodity.setFeeAlgr(((BigDecimal) localMap.get("FeeAlgr")).shortValue());
			localCommodity.setFeeRate_B(((BigDecimal) localMap.get("FeeRate_B")).doubleValue());
			localCommodity.setFeeRate_S(((BigDecimal) localMap.get("FeeRate_S")).doubleValue());
			localCommodity.setMarginAlgr(((BigDecimal) localMap.get("MarginAlgr")).shortValue());
			localCommodity.setMarginRate_B(((BigDecimal) localMap.get("MarginRate_B")).doubleValue());
			localCommodity.setMarginRate_S(((BigDecimal) localMap.get("MarginRate_S")).doubleValue());
			localCommodity.setMarketDate((Date) localMap.get("MarketDate"));
			localCommodity.setSettleDate((Date) localMap.get("SettleDate"));
			localCommodity.setMarginPriceType(((BigDecimal) localMap.get("MarginPriceType")).shortValue());
			localCommodity.setFirmMaxHoldQty(((BigDecimal) localMap.get("FirmMaxHoldQty")).longValue());
			localCommodity.setFirmMaxHoldQtyAlgr(((BigDecimal) localMap.get("FirmMaxHoldQtyAlgr")).shortValue());
			localCommodity.setStartPercentQty(((BigDecimal) localMap.get("StartPercentQty")).longValue());
			localCommodity.setMaxPercentLimit(((BigDecimal) localMap.get("MaxPercentLimit")).doubleValue());
			localCommodity.setOneMaxHoldQty(((BigDecimal) localMap.get("OneMaxHoldQty")).longValue());
			localCommodity.setMinQuantityMove(((BigDecimal) localMap.get("MinQuantityMove")).intValue());
			localCommodity.setMinSettleMoveQty(((BigDecimal) localMap.get("MinSettleMoveQty")).intValue());
			localCommodity.setMinSettleQty(((BigDecimal) localMap.get("MinSettleQty")).longValue());
			localArrayList.add(localCommodity);
		}
		return localArrayList;
	}

	public List getQuotationList() {
		return getQuotationList(null);
	}

	public List getQuotationList(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer("select * from T_Quotation where 1=1 ");
		Object[] arrayOfObject = null;
		ArrayList localArrayList = new ArrayList();
		if (paramString != null) {
			localStringBuffer.append(" and CommodityID=?");
			localArrayList.add(paramString);
		}
		arrayOfObject = localArrayList.toArray();
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		return dbList2QuotationList(localList);
	}

	public List queryQuotationByTime(Date paramDate) {
		StringBuffer localStringBuffer = new StringBuffer("select * from T_Quotation ");
		Object[] arrayOfObject = null;
		if (paramDate != null) {
			localStringBuffer.append(" where CreateTime>?");
			arrayOfObject = new Object[] { paramDate };
		}
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		return dbList2QuotationList(localList);
	}

	private List dbList2QuotationList(List paramList) {
		if (paramList == null) {
			return null;
		}
		ArrayList localArrayList = new ArrayList();
		for (int i = 0; i < paramList.size(); i++) {
			Quotation localQuotation = new Quotation();
			Map localMap = (Map) paramList.get(i);
			localQuotation.setCommodityID((String) localMap.get("CommodityID"));
			localQuotation.setYesterBalancePrice(Double.valueOf(((BigDecimal) localMap.get("YesterBalancePrice")).doubleValue()));
			localQuotation.setClosePrice(Double.valueOf(((BigDecimal) localMap.get("ClosePrice")).doubleValue()));
			localQuotation.setOpenPrice(Double.valueOf(((BigDecimal) localMap.get("OpenPrice")).doubleValue()));
			localQuotation.setHighPrice(Double.valueOf(((BigDecimal) localMap.get("HighPrice")).doubleValue()));
			localQuotation.setLowPrice(Double.valueOf(((BigDecimal) localMap.get("LowPrice")).doubleValue()));
			localQuotation.setCurPrice(Double.valueOf(((BigDecimal) localMap.get("CurPrice")).doubleValue()));
			localQuotation.setCurAmount(Long.valueOf(((BigDecimal) localMap.get("CurAmount")).longValue()));
			localQuotation.setOpenAmount(Long.valueOf(((BigDecimal) localMap.get("OpenAmount")).longValue()));
			localQuotation.setBuyOpenAmount(Long.valueOf(((BigDecimal) localMap.get("BuyOpenAmount")).longValue()));
			localQuotation.setSellOpenAmount(Long.valueOf(((BigDecimal) localMap.get("SellOpenAmount")).longValue()));
			localQuotation.setCloseAmount(Long.valueOf(((BigDecimal) localMap.get("CloseAmount")).longValue()));
			localQuotation.setBuyCloseAmount(Long.valueOf(((BigDecimal) localMap.get("BuyCloseAmount")).longValue()));
			localQuotation.setSellCloseAmount(Long.valueOf(((BigDecimal) localMap.get("SellCloseAmount")).longValue()));
			localQuotation.setReserveCount(Long.valueOf(((BigDecimal) localMap.get("ReserveCount")).longValue()));
			localQuotation.setReserveChange(Long.valueOf(((BigDecimal) localMap.get("ReserveChange")).longValue()));
			localQuotation.setPrice(Double.valueOf(((BigDecimal) localMap.get("Price")).doubleValue()));
			localQuotation.setTotalMoney(Double.valueOf(((BigDecimal) localMap.get("TotalMoney")).doubleValue()));
			localQuotation.setTotalAmount(Long.valueOf(((BigDecimal) localMap.get("TotalAmount")).longValue()));
			localQuotation.setSpread(Double.valueOf(((BigDecimal) localMap.get("Spread")).doubleValue()));
			localQuotation.buy[0] = ((BigDecimal) localMap.get("BuyPrice1")).doubleValue();
			localQuotation.sell[0] = ((BigDecimal) localMap.get("SellPrice1")).doubleValue();
			localQuotation.buyqty[0] = ((BigDecimal) localMap.get("BuyAmount1")).longValue();
			localQuotation.sellqty[0] = ((BigDecimal) localMap.get("SellAmount1")).longValue();
			localQuotation.buy[1] = ((BigDecimal) localMap.get("BuyPrice2")).doubleValue();
			localQuotation.sell[1] = ((BigDecimal) localMap.get("SellPrice2")).doubleValue();
			localQuotation.buyqty[1] = ((BigDecimal) localMap.get("BuyAmount2")).longValue();
			localQuotation.sellqty[1] = ((BigDecimal) localMap.get("SellAmount2")).longValue();
			localQuotation.buy[2] = ((BigDecimal) localMap.get("BuyPrice3")).doubleValue();
			localQuotation.sell[2] = ((BigDecimal) localMap.get("SellPrice3")).doubleValue();
			localQuotation.buyqty[2] = ((BigDecimal) localMap.get("BuyAmount3")).longValue();
			localQuotation.sellqty[2] = ((BigDecimal) localMap.get("SellAmount3")).longValue();
			localQuotation.buy[3] = ((BigDecimal) localMap.get("BuyPrice4")).doubleValue();
			localQuotation.sell[3] = ((BigDecimal) localMap.get("SellPrice4")).doubleValue();
			localQuotation.buyqty[3] = ((BigDecimal) localMap.get("BuyAmount4")).longValue();
			localQuotation.sellqty[3] = ((BigDecimal) localMap.get("SellAmount4")).longValue();
			localQuotation.buy[4] = ((BigDecimal) localMap.get("BuyPrice5")).doubleValue();
			localQuotation.sell[4] = ((BigDecimal) localMap.get("SellPrice5")).doubleValue();
			localQuotation.buyqty[4] = ((BigDecimal) localMap.get("BuyAmount5")).longValue();
			localQuotation.sellqty[4] = ((BigDecimal) localMap.get("SellAmount5")).longValue();
			localQuotation.setOutAmount(Long.valueOf(((BigDecimal) localMap.get("OutAmount")).longValue()));
			localQuotation.setInAmount(Long.valueOf(((BigDecimal) localMap.get("InAmount")).longValue()));
			localQuotation.setTradeCue(Long.valueOf(((BigDecimal) localMap.get("TradeCue")).longValue()));
			localQuotation.setNo(Long.valueOf(((BigDecimal) localMap.get("NO")).longValue()));
			localQuotation.updateTime = ((Timestamp) localMap.get("CreateTime"));
			localArrayList.add(localQuotation);
		}
		return localArrayList;
	}

	public List getTradeTimes() {
		ArrayList localArrayList = new ArrayList();
		String str = "select * from T_A_TradeTime order by SectionID";
		this.log.debug("sql:" + str);
		List localList = getJdbcTemplate().queryForList(str);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			TradeTime localTradeTime = new TradeTime();
			localTradeTime.setSectionID(new Integer(((BigDecimal) localMap.get("SectionID")).intValue()));
			localTradeTime.setName((String) localMap.get("Name"));
			localTradeTime.setStartTime((String) localMap.get("StartTime"));
			localTradeTime.setEndTime((String) localMap.get("EndTime"));
			localTradeTime.setStatus(new Short(((BigDecimal) localMap.get("Status")).shortValue()));
			localTradeTime.setGatherBid(new Short(((BigDecimal) localMap.get("GatherBid")).shortValue()));
			localTradeTime.setBidStartTime((String) localMap.get("BidStartTime"));
			localTradeTime.setBidEndTime((String) localMap.get("BidEndTime"));
			localTradeTime.setModifyTime((Date) localMap.get("ModifyTime"));
			localArrayList.add(localTradeTime);
		}
		return localArrayList;
	}

	public Market getMarket() {
		Market localMarket = null;
		String str = "select * from T_A_Market ";
		this.log.debug("sql:" + str);
		List localList = getJdbcTemplate().queryForList(str);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap = (Map) localList.get(0);
			localMarket = new Market();
			localMarket.setMarketCode((String) localMap.get("MarketCode"));
			localMarket.setMarketName((String) localMap.get("MarketName"));
			localMarket.setStatus(new Short(((BigDecimal) localMap.get("Status")).shortValue()));
			localMarket.setMarginFBFlag(new Short(((BigDecimal) localMap.get("MarginFBFlag")).shortValue()));
			localMarket.setShortName((String) localMap.get("ShortName"));
			localMarket.setRunMode(new Short(((BigDecimal) localMap.get("RunMode")).shortValue()));
			localMarket.setTradePriceAlgr(new Short(((BigDecimal) localMap.get("TradePriceAlgr")).shortValue()));
			localMarket.setCloseAlgr(new Short(((BigDecimal) localMap.get("CloseAlgr")).shortValue()));
			localMarket.setFloatingLossComputeType(new Short(((BigDecimal) localMap.get("FloatingLossComputeType")).shortValue()));
			localMarket.setDelayQuoShowType(new Short(((BigDecimal) localMap.get("DelayQuoShowType")).shortValue()));
			localMarket.setDelayNeedBill(new Short(((BigDecimal) localMap.get("DelayNeedBill")).shortValue()));
			localMarket.setNeutralFlag(new Short(((BigDecimal) localMap.get("NeutralFlag")).shortValue()));
			localMarket.setNeutralMatchPriority(new Short(((BigDecimal) localMap.get("NeutralMatchPriority")).shortValue()));
			localMarket.setQuotationTwoSide(new Short(((BigDecimal) localMap.get("QuotationTwoSide")).shortValue()));
		}
		return localMarket;
	}

	public NotTradeDay getNotTradeDay() {
		NotTradeDay localNotTradeDay = null;
		String str1 = "select * from T_A_NotTradeDay";
		this.log.debug("sql:" + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap = (Map) localList.get(0);
			localNotTradeDay = new NotTradeDay();
			localNotTradeDay.setID(new Integer(((BigDecimal) localMap.get("ID")).intValue()));
			localNotTradeDay.setModifyTime((Date) localMap.get("ModifyTime"));
			String str2 = (String) localMap.get("Week");
			if (!StringUtils.isEmpty(str2)) {
				String[] localObject1 = str2.split(",");
				List localObject2 = new ArrayList();
				for (int i = 0; i < localObject1.length; i++) {
					((List) localObject2).add(localObject1[i]);
				}
				localNotTradeDay.setWeekList((List) localObject2);
			}
			Object localObject1 = (String) localMap.get("Day");
			if (!StringUtils.isEmpty((String) localObject1)) {
				String[] localObject2 = ((String) localObject1).split(",");
				ArrayList localArrayList = new ArrayList();
				for (int j = 0; j < localObject2.length; j++) {
					localArrayList.add(localObject2[j]);
				}
				localNotTradeDay.setDayList(localArrayList);
			}
		}
		return localNotTradeDay;
	}

	public SystemStatus getSystemStatus() {
		SystemStatus localSystemStatus = null;
		String str = "select * from T_SystemStatus";
		this.log.debug("sql:" + str);
		List localList = getJdbcTemplate().queryForList(str);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap = (Map) localList.get(0);
			localSystemStatus = new SystemStatus();
			localSystemStatus.setTradeDate((Date) localMap.get("TradeDate"));
			localSystemStatus.setStatus(((BigDecimal) localMap.get("Status")).intValue());
			localSystemStatus
					.setSectionID(localMap.get("SectionID") == null ? null : new Integer(((BigDecimal) localMap.get("SectionID")).intValue()));
			localSystemStatus.setNote((String) localMap.get("Note"));
			localSystemStatus.setRecoverTime((String) localMap.get("RecoverTime"));
		}
		return localSystemStatus;
	}

	public void updateSystemStatus(SystemStatus paramSystemStatus) {
		String str = "update T_SystemStatus set TradeDate=?,Status=?,SectionID=?,Note=?,RecoverTime=? ";
		Object[] arrayOfObject = { paramSystemStatus.getTradeDate(), new Integer(paramSystemStatus.getStatus()), paramSystemStatus.getSectionID(),
				paramSystemStatus.getNote(), paramSystemStatus.getRecoverTime() };
		int[] arrayOfInt = { 91, 5, 4, 12, 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public void updateSystemRecoverTime(String paramString) {
		String str = "update T_SystemStatus set RecoverTime=? ";
		Object[] arrayOfObject = { paramString };
		int[] arrayOfInt = { 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public void insertSysLog(SysLog paramSysLog) {
		String str = "insert into c_globallog_all(id,operator,operatetime,operatetype,operatecontent,operateresult,logtype)  values(SEQ_C_GLOBALLOG.Nextval,?, sysdate,?,?,?,?)";
		Object[] arrayOfObject = { paramSysLog.getUserID(), Integer.valueOf(paramSysLog.getOperateType()), paramSysLog.getNote(),
				Integer.valueOf(paramSysLog.getOperateResult()), Integer.valueOf(paramSysLog.getLogType()) };
		int[] arrayOfInt = { 12, 4, 12, 4, 4 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public Date getCurDbDate() {
		String str = "select sysdate from dual";
		this.log.debug("sql: " + str);
		return (Date) getJdbcTemplate().queryForObject(str, Date.class);
	}

	public int getTradeSecCount() {
		String str = "select count(*) from T_A_TradeTime";
		this.log.debug("sql: " + str);
		return getJdbcTemplate().queryForInt(str);
	}

	public int getComtyCount() {
		String str = "select count(*) from T_Commodity";
		this.log.debug("sql: " + str);
		return getJdbcTemplate().queryForInt(str);
	}

	public Trader getOneTrader(String paramString) {
		String str = " select a.TraderID,a.FirmID,b.OperateCode from M_Trader a,T_Trader b where a.TraderID=b.TraderID(+) and a.traderID=?";
		this.log.debug("query trader sql: " + str);
		RowMapper local1 = new RowMapper() {
			public Object mapRow(ResultSet paramAnonymousResultSet, int paramAnonymousInt) throws SQLException {
				Trader localTrader = new Trader();
				localTrader.setTraderID(paramAnonymousResultSet.getString("TraderID"));
				String str1 = paramAnonymousResultSet.getString("FirmID");
				localTrader.setFirmID(str1);
				String str2 = paramAnonymousResultSet.getString("OperateCode");
				if (!StringUtils.isEmpty(str2)) {
					String[] arrayOfString = str2.split(",");
					ArrayList localArrayList = new ArrayList();
					for (int i = 0; i < arrayOfString.length; i++) {
						localArrayList.add(str1 + arrayOfString[i]);
					}
					localTrader.setOperateCustomerList(localArrayList);
				}
				return localTrader;
			}
		};
		return (Trader) getJdbcTemplate().queryForObject(str, new Object[] { paramString }, local1);
	}

	public Firm getOneFirm(String paramString) {
		String str = "select FirmID,Status,MaxHoldQty,MinClearDeposit,MaxOverdraft,(select TariffID from M_Firm where FirmID=T_Firm.FirmID) as TariffID from T_Firm where Status <> 2 and firmid = ?";
		this.log.debug("query firm sql: " + str);
		RowMapper local2 = new RowMapper() {
			public Object mapRow(ResultSet paramAnonymousResultSet, int paramAnonymousInt) throws SQLException {
				Firm localFirm = new Firm();
				localFirm.setFirmID(paramAnonymousResultSet.getString("FirmID"));
				localFirm.setStatus(paramAnonymousResultSet.getShort("Status"));
				localFirm.setMaxHoldQty(paramAnonymousResultSet.getLong("MaxHoldQty"));
				localFirm.setMinClearDeposit(paramAnonymousResultSet.getDouble("MaxOverdraft"));
				localFirm.setMaxOverdraft(paramAnonymousResultSet.getDouble("MaxOverdraft"));
				localFirm.setTariffID(paramAnonymousResultSet.getString("TariffID"));
				return localFirm;
			}
		};
		return (Firm) getJdbcTemplate().queryForObject(str, new Object[] { paramString }, local2);
	}

	public Map getCusByFirm(String paramString) {
		HashMap localHashMap = new HashMap();
		String str1 = "select * from T_Customer where firmID = ?";
		this.log.debug("query customer sql:" + str1);
		List localList = getJdbcTemplate().queryForList(str1, new Object[] { paramString });
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			Customer localCustomer = new Customer();
			String str2 = (String) localMap.get("CustomerID");
			localCustomer.setCustomerID(str2);
			localCustomer.setName((String) localMap.get("Name"));
			localCustomer.setCode((String) localMap.get("Code"));
			localCustomer.setStatus(((BigDecimal) localMap.get("Status")).shortValue());
			localCustomer.setFirmID((String) localMap.get("FirmID"));
			localHashMap.put(str2, localCustomer);
		}
		return localHashMap;
	}

	public Consigner getConsigner(String paramString) {
		String str = "select AgentTraderID consignerID,Name,Type,Status,OperateFirm from M_AgentTrader where AgentTraderID=? ";
		this.log.debug("sql:" + str);
		RowMapper local3 = new RowMapper() {
			public Object mapRow(ResultSet paramAnonymousResultSet, int paramAnonymousInt) throws SQLException {
				Consigner localConsigner = new Consigner();
				String str1 = paramAnonymousResultSet.getString("ConsignerID");
				ServerDAOJdbc.this.log.debug("consignerID:" + str1);
				localConsigner.setConsignerID(str1);
				localConsigner.setName(paramAnonymousResultSet.getString("Name"));
				localConsigner.setType(paramAnonymousResultSet.getShort("Type"));
				localConsigner.setStatus(paramAnonymousResultSet.getShort("Status"));
				String str2 = paramAnonymousResultSet.getString("OperateFirm");
				if (!StringUtils.isEmpty(str2)) {
					String[] arrayOfString = str2.split(",");
					ArrayList localArrayList = new ArrayList();
					for (int i = 0; i < arrayOfString.length; i++) {
						localArrayList.add(arrayOfString[i]);
					}
					localConsigner.setOperateFirmList(localArrayList);
				}
				return localConsigner;
			}
		};
		return (Consigner) getJdbcTemplate().queryForObject(str, new Object[] { paramString }, local3);
	}

	public String getFirmByCustmID(String paramString) {
		String str = "select FirmID from T_Customer where CustomerID=?";
		this.log.debug("sql:" + str);
		return (String) getJdbcTemplate().queryForObject(str, new Object[] { paramString }, String.class);
	}

	public List getFirmIDList() {
		ArrayList localArrayList = new ArrayList();
		String str = "select FirmID from T_Firm where Status <> 2 order by FirmID";
		this.log.debug("sql:" + str);
		List localList = getJdbcTemplate().queryForList(str);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			localArrayList.add((String) localMap.get("FirmID"));
		}
		return localArrayList;
	}

	public Date getLastClearDate() {
		String str = "select nvl(max(ClearDate),sysdate) from T_H_Market";
		this.log.debug("sql: " + str);
		return (Date) getJdbcTemplate().queryForObject(str, Date.class);
	}

	public Map getDaySectionMap() {
		HashMap localHashMap1 = new HashMap();
		List localList1 = getJdbcTemplate().queryForList("select distinct WeekDay from T_A_DaySection order by WeekDay ");
		if ((localList1 != null) && (localList1.size() > 0)) {
			for (int i = 0; i < localList1.size(); i++) {
				Map localMap1 = (Map) localList1.get(i);
				int j = ((BigDecimal) localMap1.get("WeekDay")).shortValue();
				String str = "select * from T_A_DaySection where WeekDay=? order by SectionID ";
				HashMap localHashMap2 = new HashMap();
				List localList2 = getJdbcTemplate().queryForList(str, new Object[] { new Integer(j) });
				if ((localList2 != null) && (localList2.size() > 0)) {
					for (int k = 0; k < localList2.size(); k++) {
						Map localMap2 = (Map) localList2.get(k);
						int m = ((BigDecimal) localMap2.get("SectionID")).intValue();
						short s = ((BigDecimal) localMap2.get("Status")).shortValue();
						localHashMap2.put(Integer.valueOf(m), Short.valueOf(s));
					}
				}
				localHashMap1.put(Integer.valueOf(j), localHashMap2);
			}
		}
		return localHashMap1;
	}

	public void updateTradeSectionDateStatus(TradeTime paramTradeTime) {
		String str = "update T_A_TradeTime set StartDate=?,EndDate=?,BidStartDate=?,BidEndDate=?,Status=? where SectionID=? ";
		Object[] arrayOfObject = { paramTradeTime.getStartDate(), paramTradeTime.getEndDate(), paramTradeTime.getBidStartDate(),
				paramTradeTime.getBidEndDate(), paramTradeTime.getStatus(), paramTradeTime.getSectionID() };
		int[] arrayOfInt = { 12, 12, 12, 12, 5, 4 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public void adjustQuotationTime(Timestamp paramTimestamp1, Timestamp paramTimestamp2, Timestamp paramTimestamp3) {
		String str = "update T_Quotation set CreateTime=? where CreateTime > ? and CreateTime < ? ";
		Object[] arrayOfObject = { paramTimestamp3, paramTimestamp1, paramTimestamp2 };
		int[] arrayOfInt = { 93, 93, 93 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public List getQuotationsByTrade() {
		String str = "select quotationTwoSide from t_a_market ";
		int i = getJdbcTemplate().queryForInt(str);
		int j = 1;
		if (i == 2) {
			j = 2;
		}
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select ").append("q.commodityid, yesterbalanceprice, closeprice,")
				.append("openprice,highprice,lowprice,curprice,curamount,")
				.append("openamount,buyopenamount,sellopenamount,closeamount,buycloseamount,sellcloseamount,").append("decode(").append(j)
				.append(",2,(c.ReserveCount+q.openamount-q.closeamount),c.ReserveCount+(q.openamount-q.closeamount)/2").append(") reservecount,")
				.append("decode(").append(j).append(",2,(q.openamount-q.closeamount),(q.openamount-q.closeamount)/2").append(") reservechange,")
				.append("(case when totalAmount=0 then q.Price else round(totalMoney/(totalAmount*c.ContractFactor)/c.MinPriceMove)*c.MinPriceMove end) price,")
				.append("totalMoney ,totalAmount ,case when nvl(curprice,0)=0 then 0 else (curprice-yesterbalanceprice) end as spread,")
				.append("buyprice1, sellprice1, buyamount1, sellamount1, ").append("buyprice2, sellprice2, buyamount2, sellamount2, ")
				.append("buyprice3, sellprice3, buyamount3, sellamount3,").append("buyprice4, sellprice4, buyamount4, sellamount4,")
				.append("buyprice5, sellprice5, buyamount5, sellamount5,").append("outamount, inamount, tradecue, no, createtime ").append("from ")
				.append("(select ").append("commodityid, yesterbalanceprice, closeprice,")
				.append("nvl((select nvl(price,0) from t_trade where a_tradeno=(select min(a_tradeno) from t_trade where commodityid=qt.commodityid)),0) openprice,")
				.append("(select nvl(max(price),0) from t_trade where commodityid=qt.commodityid) highprice,")
				.append("(select nvl(min(price),0) from t_trade where commodityid=qt.commodityid) lowprice,")
				.append("nvl((select nvl(price,0) from t_trade where a_tradeno=(select max(a_tradeno) from t_trade where commodityid=qt.commodityid)),0) curprice,")
				.append("nvl((select nvl(quantity,0) from t_trade where a_tradeno=(select max(a_tradeno) from t_trade where commodityid=qt.commodityid)),0)*")
				.append(j).append(" curamount,")
				.append("(select nvl(sum(quantity), 0) from t_trade where ordertype = 1 and commodityid = qt.commodityid) openamount,")
				.append("(select nvl(sum(quantity), 0) from t_trade where ordertype = 1 and commodityid = qt.commodityid and BS_Flag=1) buyopenamount,")
				.append("(select nvl(sum(quantity), 0) from t_trade where ordertype = 1 and commodityid = qt.commodityid and BS_Flag=2) sellopenamount,")
				.append("(select nvl(sum(quantity), 0) from t_trade where ordertype = 2 and commodityid = qt.commodityid) closeamount,")
				.append("(select nvl(sum(quantity), 0) from t_trade where ordertype = 2 and commodityid = qt.commodityid and BS_Flag=1)  buycloseamount,")
				.append("(select nvl(sum(quantity), 0) from t_trade where ordertype = 2 and commodityid = qt.commodityid and BS_Flag=2)  sellcloseamount,")
				.append("(select nvl(sum(Price*Quantity*(select ContractFactor from t_commodity where commodityid=qt.commodityid)),0) from t_trade where commodityid = qt.commodityid)*decode(")
				.append(j).append(",2,1,0.5) totalMoney,")
				.append("(select nvl(sum(quantity),0) from t_trade where commodityid = qt.commodityid)*decode(").append(j)
				.append(",2,1,0.5) totalAmount,")
				.append("buyprice1, sellprice1, buyamount1, sellamount1,buyprice2, sellprice2, buyamount2, sellamount2,")
				.append("buyprice3, sellprice3, buyamount3, sellamount3,buyprice4, sellprice4, buyamount4, sellamount4,")
				.append("buyprice5, sellprice5, buyamount5, sellamount5,outamount, inamount, tradecue, no, createtime,Price ")
				.append("from t_quotation qt ").append(") q,(select commodityid,ContractFactor,ReserveCount,MinPriceMove from t_commodity) c ")
				.append("where q.commodityid=c.commodityid ");
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
		return dbList2QuotationList(localList);
	}

	public String getTraderIDByUserID(String paramString) {
		String str = "select traderID from M_Trader t  where t.userID=? ";
		this.log.debug("sql: " + str);
		Object[] arrayOfObject = { paramString };
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			return (String) ((Map) localList.get(0)).get("traderID");
		}
		return null;
	}
}
