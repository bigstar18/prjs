package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.BreedDAO;
import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class BreedDAOJdbc
  extends BaseDAOJdbc
  implements BreedDAO
{
  private Log log = LogFactory.getLog(BreedDAOJdbc.class);
  
  public Breed getBreedById(String paramString)
  {
    Assert.hasText(paramString);
    Breed localBreed = new Breed();
    String str1 = "select * from T_A_Breed b where b.breedID=?";
    Object[] arrayOfObject1 = { paramString };
    this.log.debug("sql: " + str1);
    this.log.debug("BreedID:" + arrayOfObject1[0]);
    List localList1 = getJdbcTemplate().queryForList(str1, arrayOfObject1);
    if ((localList1 == null) || (localList1.size() == 0)) {
      return null;
    }
    Map localMap1 = (Map)localList1.get(0);
    if (localMap1.get("breedID") != null) {
      localBreed.setBreedID(Long.valueOf(Long.parseLong(localMap1.get("breedID").toString())));
    }
    localBreed.setBreedName((String)localMap1.get("BreedName"));
    if (localMap1.get("SortID") != null) {
      localBreed.setSortID(Long.valueOf(Long.parseLong(localMap1.get("SortID").toString())));
    }
    if (localMap1.get("ContractFactor") != null) {
      localBreed.setContractFactor(Double.valueOf(Double.parseDouble(localMap1.get("ContractFactor").toString())));
    }
    localBreed.setMinPriceMove(Double.valueOf(Double.parseDouble(localMap1.get("MinPriceMove").toString())));
    localBreed.setSpreadAlgr(Short.valueOf(Short.parseShort(localMap1.get("SpreadAlgr").toString())));
    localBreed.setSpreadUpLmt(Double.valueOf(Double.parseDouble(localMap1.get("SpreadUpLmt").toString())));
    localBreed.setSpreadDownLmt(Double.valueOf(Double.parseDouble(localMap1.get("SpreadDownLmt").toString())));
    localBreed.setFeeAlgr(Short.valueOf(Short.parseShort(localMap1.get("FeeAlgr").toString())));
    localBreed.setFeeRate_B(Double.valueOf(Double.parseDouble(localMap1.get("FeeRate_B").toString())));
    localBreed.setFeeRate_S(Double.valueOf(Double.parseDouble(localMap1.get("FeeRate_S").toString())));
    localBreed.setMarginAlgr(Short.valueOf(Short.parseShort(localMap1.get("MarginAlgr").toString())));
    localBreed.setMarginRate_B(Double.valueOf(Double.parseDouble(localMap1.get("MarginRate_B").toString())));
    localBreed.setMarginRate_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginRate_S").toString())));
    localBreed.setMarginAssure_B(Double.valueOf(Double.parseDouble(localMap1.get("MarginAssure_B").toString())));
    localBreed.setMarginAssure_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginAssure_S").toString())));
    localBreed.setTodayCloseFeeRate_B(Double.valueOf(Double.parseDouble(localMap1.get("TodayCloseFeeRate_B").toString())));
    localBreed.setTodayCloseFeeRate_S(Double.valueOf(Double.parseDouble(localMap1.get("TodayCloseFeeRate_S").toString())));
    localBreed.setHistoryCloseFeeRate_B(Double.valueOf(Double.parseDouble(localMap1.get("HistoryCloseFeeRate_B").toString())));
    localBreed.setHistoryCloseFeeRate_S(Double.valueOf(Double.parseDouble(localMap1.get("HistoryCloseFeeRate_S").toString())));
    if (localMap1.get("LimitCmdtyQty") != null) {
      localBreed.setLimitCmdtyQty(Long.valueOf(Long.parseLong(localMap1.get("LimitCmdtyQty").toString())));
    }
    localBreed.setSettleFeeAlgr(Short.valueOf(Short.parseShort(localMap1.get("SettleFeeAlgr").toString())));
    if (localMap1.get("SettleFeeRate_B") != null) {
      localBreed.setSettleFeeRate_B(Double.valueOf(Double.parseDouble(localMap1.get("SettleFeeRate_B").toString())));
    }
    localBreed.setSettleFeeRate_S(Double.valueOf(Double.parseDouble(localMap1.get("SettleFeeRate_S").toString())));
    localBreed.setForceCloseFeeAlgr(Short.valueOf(Short.parseShort(localMap1.get("ForceCloseFeeAlgr").toString())));
    localBreed.setForceCloseFeeRate_B(Double.valueOf(Double.parseDouble(localMap1.get("ForceCloseFeeRate_B").toString())));
    localBreed.setForceCloseFeeRate_S(Double.valueOf(Double.parseDouble(localMap1.get("ForceCloseFeeRate_S").toString())));
    localBreed.setSettleMarginRate_B(Double.valueOf(Double.parseDouble(localMap1.get("SettleMarginRate_B").toString())));
    localBreed.setSettleMarginRate_S(Double.valueOf(Double.parseDouble(localMap1.get("SettleMarginRate_S").toString())));
    localBreed.setAddedTax(Double.valueOf(Double.parseDouble(localMap1.get("AddedTax").toString())));
    localBreed.setMarginPriceType(Short.valueOf(Short.parseShort(localMap1.get("MarginPriceType").toString())));
    localBreed.setLowestSettleFee(Double.valueOf(Double.parseDouble(localMap1.get("LowestSettleFee").toString())));
    localBreed.setLimitBreedQty(Long.valueOf(Long.parseLong(localMap1.get("LimitBreedQty").toString())));
    localBreed.setFirmCleanQty(Long.valueOf(Long.parseLong(localMap1.get("FirmCleanQty").toString())));
    if (localMap1.get("MarginItem1") != null) {
      localBreed.setMarginItem1(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem1").toString())));
    }
    if (localMap1.get("MarginItem2") != null) {
      localBreed.setMarginItem2(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem2").toString())));
    }
    if (localMap1.get("MarginItem3") != null) {
      localBreed.setMarginItem3(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem3").toString())));
    }
    if (localMap1.get("MarginItem4") != null) {
      localBreed.setMarginItem4(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem4").toString())));
    }
    if (localMap1.get("MarginItem5") != null) {
      localBreed.setMarginItem5(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem5").toString())));
    }
    if (localMap1.get("MarginItem1_S") != null) {
      localBreed.setMarginItem1_S(new Double(localMap1.get("MarginItem1_S").toString()));
    }
    if (localMap1.get("MarginItem2_S") != null) {
      localBreed.setMarginItem2_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem2_S").toString())));
    }
    if (localMap1.get("MarginItem3_S") != null) {
      localBreed.setMarginItem3_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem3_S").toString())));
    }
    if (localMap1.get("MarginItem4_S") != null) {
      localBreed.setMarginItem4_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem4_S").toString())));
    }
    if (localMap1.get("MarginItem5_S") != null) {
      localBreed.setMarginItem5_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItem5_S").toString())));
    }
    if (localMap1.get("MarginItemAssure1") != null) {
      localBreed.setMarginItemAssure1(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure1").toString())));
    }
    if (localMap1.get("MarginItemAssure2") != null) {
      localBreed.setMarginItemAssure2(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure2").toString())));
    }
    if (localMap1.get("MarginItemAssure3") != null) {
      localBreed.setMarginItemAssure3(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure3").toString())));
    }
    if (localMap1.get("MarginItemAssure4") != null) {
      localBreed.setMarginItemAssure4(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure4").toString())));
    }
    if (localMap1.get("MarginItemAssure5") != null) {
      localBreed.setMarginItemAssure5(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure5").toString())));
    }
    if (localMap1.get("MarginItemAssure1_S") != null) {
      localBreed.setMarginItemAssure1_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure1_S").toString())));
    }
    if (localMap1.get("MarginItemAssure2_S") != null) {
      localBreed.setMarginItemAssure2_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure2_S").toString())));
    }
    if (localMap1.get("MarginItemAssure3_S") != null) {
      localBreed.setMarginItemAssure3_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure3_S").toString())));
    }
    if (localMap1.get("MarginItemAssure4_S") != null) {
      localBreed.setMarginItemAssure4_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure4_S").toString())));
    }
    if (localMap1.get("MarginItemAssure5_S") != null) {
      localBreed.setMarginItemAssure5_S(Double.valueOf(Double.parseDouble(localMap1.get("MarginItemAssure5_S").toString())));
    }
    localBreed.setSettleMarginAlgr_B(Short.valueOf(Short.parseShort(localMap1.get("SettleMarginAlgr_B").toString())));
    localBreed.setSettleMarginAlgr_S(Short.valueOf(Short.parseShort(localMap1.get("SettleMarginAlgr_S").toString())));
    localBreed.setOrderPrivilege(Short.valueOf(Short.parseShort(localMap1.get("OrderPrivilege").toString())));
    localBreed.setPayoutAlgr(Short.valueOf(Short.parseShort(localMap1.get("PayoutAlgr").toString())));
    localBreed.setPayoutRate(Double.valueOf(Double.parseDouble(localMap1.get("PayoutRate").toString())));
    localBreed.setFirmMaxHoldQty(Long.valueOf(Long.parseLong(localMap1.get("FirmMaxHoldQty").toString())));
    localBreed.setAddedTaxFactor(Double.valueOf(Double.parseDouble(localMap1.get("AddedTaxFactor").toString())));
    localBreed.setSettlePriceType(Short.valueOf(Short.parseShort(localMap1.get("SettlePriceType").toString())));
    if (localMap1.get("BeforeDays") != null) {
      localBreed.setBeforeDays(Long.valueOf(Long.parseLong(localMap1.get("BeforeDays").toString())));
    }
    if (localMap1.get("SpecSettlePrice") != null) {
      localBreed.setSpecSettlePrice(Double.valueOf(Double.parseDouble(localMap1.get("SpecSettlePrice").toString())));
    }
    if (localMap1.get("FirmMaxHoldQtyAlgr") != null) {
      localBreed.setFirmMaxHoldQtyAlgr(Short.valueOf(Short.parseShort(localMap1.get("FirmMaxHoldQtyAlgr").toString())));
    }
    if (localMap1.get("StartPercentQty") != null) {
      localBreed.setStartPercentQty(Long.valueOf(Long.parseLong(localMap1.get("StartPercentQty").toString())));
    }
    if (localMap1.get("MaxPercentLimit") != null) {
      localBreed.setMaxPercentLimit(Double.valueOf(Double.parseDouble(localMap1.get("MaxPercentLimit").toString())));
    }
    localBreed.setOneMaxHoldQty(Long.valueOf(Long.parseLong(localMap1.get("OneMaxHoldQty").toString())));
    localBreed.setMinQuantityMove(Integer.parseInt(localMap1.get("MinQuantityMove").toString()));
    localBreed.setMinSettleMoveQty(Integer.parseInt(localMap1.get("MinSettleMoveQty").toString()));
    localBreed.setMinSettleQty(Integer.parseInt(localMap1.get("MinSettleQty").toString()));
    if (localMap1.get("ContractFactorName") != null) {
      localBreed.setContractFactorName(localMap1.get("ContractFactorName").toString());
    }
    localBreed.setDelayRecoupRate(Double.valueOf(Double.parseDouble(localMap1.get("DelayRecoupRate").toString())));
    localBreed.setSettleWay(Short.valueOf(Short.parseShort(localMap1.get("SettleWay").toString())));
    localBreed.setDelayFeeWay(Short.valueOf(Short.parseShort(localMap1.get("DelayFeeWay").toString())));
    localBreed.setStoreRecoupRate(Double.valueOf(Double.parseDouble(localMap1.get("StoreRecoupRate").toString())));
    if (localMap1.get("MaxFeeRate") != null) {
      localBreed.setMaxFeeRate(Double.valueOf(Double.parseDouble(localMap1.get("MaxFeeRate").toString())));
    }
    localBreed.setBreedTradeMode(Short.valueOf(Short.parseShort(localMap1.get("BreedTradeMode").toString())));
    localBreed.setDelayRecoupRate_S(Double.valueOf(Double.parseDouble(localMap1.get("DelayRecoupRate_S").toString())));
    localBreed.setDelaySettlePriceType(Integer.parseInt(localMap1.get("DelaySettlePriceType").toString()));
    localBreed.setSettleMarginType(Integer.parseInt(localMap1.get("SettleMarginType").toString()));
    if (localMap1.get("BeforeDays_M") != null) {
      localBreed.setBeforeDays_M(Integer.parseInt(localMap1.get("BeforeDays_M").toString()));
    }
    String str2 = localMap1.get("holdDaysLimit").toString();
    localBreed.setHoldDaysLimit(Integer.parseInt(str2));
    if ("1".equals(str2)) {
      localBreed.setMaxHoldPositionDay(Integer.valueOf(Integer.parseInt(localMap1.get("MaxHoldPositionDay").toString())));
    }
    HashSet localHashSet = new HashSet();
    str1 = "select t.sectionID sectionID,t.Name name from T_A_BREEDTRADEPROP btp,T_A_BREED b,T_A_TRADETIME t where b.breedID = btp.breedID and t.SectionID = btp.SectionID and b.breedID=?";
    Object[] arrayOfObject2 = { paramString };
    this.log.debug("sql: " + str1);
    this.log.debug("BreedID:" + arrayOfObject1[0]);
    List localList2 = getJdbcTemplate().queryForList(str1, arrayOfObject2);
    for (int i = 0; i < localList2.size(); i++)
    {
      Map localMap2 = (Map)localList2.get(i);
      Integer localInteger = Integer.valueOf(Integer.parseInt(localMap2.get("sectionID").toString()));
      String str3 = (String)localMap2.get("name");
      TradeTime localTradeTime = new TradeTime();
      localTradeTime.setSectionID(localInteger);
      localTradeTime.setName(str3);
      localHashSet.add(localTradeTime);
    }
    localBreed.setTradeTime(localHashSet);
    return localBreed;
  }
  
  public List getBreeds(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Object[] arrayOfObject = null;
    localStringBuffer.append("select a.*,c.SortName ").append("from T_A_BREED a,T_A_CMDTYSORT c where a.SortID=c.SortID ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public void insertBreed(Breed paramBreed)
  {
    String str = "insert into Breed(BreedID,MarketCode,BreedName,CmdtyPrefix,SortID,ContractFactor,MinPriceMove,SpreadAlgr,SpreadUpLmt,SpreadDownLmt,FeeAlgr,FeeRate_B,FeeRate_S,MarginAlgr,MarginRate_B,MarginRate_S,StartTime,EndTime) values(seq_Breed.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramBreed.getMarketCode(), paramBreed.getBreedName(), paramBreed.getCmdtyPrefix(), paramBreed.getSortID(), convertDoubleNull2Zero(paramBreed.getContractFactor()), convertDoubleNull2Zero(paramBreed.getMinPriceMove()), paramBreed.getSpreadAlgr(), convertDoubleNull2Zero(paramBreed.getSpreadUpLmt()), convertDoubleNull2Zero(paramBreed.getSpreadDownLmt()), paramBreed.getFeeAlgr(), convertDoubleNull2Zero(paramBreed.getFeeRate_B()), convertDoubleNull2Zero(paramBreed.getFeeRate_S()), paramBreed.getMarginAlgr(), convertDoubleNull2Zero(paramBreed.getMarginRate_B()), convertDoubleNull2Zero(paramBreed.getMarginRate_S()), paramBreed.getStartTime(), paramBreed.getEndTime() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("同一市场中的商品代码前缀应该唯一！");
    }
  }
  
  public void insertBreed1(Breed paramBreed)
  {
    String str1 = "insert into T_A_BREED(BreedID,BreedName,SortID,ContractFactor,MinPriceMove,SpreadAlgr,SpreadUpLmt,SpreadDownLmt,FeeAlgr,FeeRate_B,FeeRate_S,MarginAlgr,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B ,HistoryCloseFeeRate_S, LimitCmdtyQty,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,SettleMarginRate_B,SettleMarginRate_S,AddedTax,MarginPriceType,LowestSettleFee,LimitBreedQty,FirmCleanQty,MarginItem1,MarginItem2,MarginItem3,MarginItem4,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,settleMarginAlgr_B,settleMarginAlgr_S,OrderPrivilege,PayoutAlgr,PayoutRate,FirmMaxHoldQty,AddedTaxFactor,MarginItem5,MarginItem5_S,MarginItemAssure5,MarginItemAssure5_S,SettlePriceType,BeforeDays,SpecSettlePrice,FirmMaxHoldQtyAlgr,StartPercentQty,MaxPercentLimit,OneMaxHoldQty,MinQuantityMove,MinSettleMoveQty,MinSettleQty,ContractFactorName,DelayRecoupRate,DelayRecoupRate_S,StoreRecoupRate,SettleWay,DelayFeeWay,MaxFeeRate,BreedTradeMode,DelaySettlePriceType,SettleMarginType,BeforeDays_M,holdDaysLimit,MaxHoldPositionDay) values(SEQ_T_A_BREED.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject1 = { paramBreed.getBreedName(), paramBreed.getSortID(), convertDoubleNull2Zero(paramBreed.getContractFactor()), convertDoubleNull2Zero(paramBreed.getMinPriceMove()), paramBreed.getSpreadAlgr(), convertDoubleNull2Zero(paramBreed.getSpreadUpLmt()), convertDoubleNull2Zero(paramBreed.getSpreadDownLmt()), paramBreed.getFeeAlgr(), convertDoubleNull2Zero(paramBreed.getFeeRate_B()), convertDoubleNull2Zero(paramBreed.getFeeRate_S()), paramBreed.getMarginAlgr(), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_S()), convertLongNull2Zero(paramBreed.getLimitCmdtyQty()), paramBreed.getSettleFeeAlgr(), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_B()), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_S()), paramBreed.getForceCloseFeeAlgr(), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_B()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_S()), convertDoubleNull2Zero(paramBreed.getAddedTax()), paramBreed.getMarginPriceType(), convertDoubleNull2Zero(paramBreed.getLowestSettleFee()), convertLongNull2Zero(paramBreed.getLimitBreedQty()), convertLongNull2Zero(paramBreed.getFirmCleanQty()), convertDoubleNull2Zero(paramBreed.getMarginItem1()), convertDoubleNull2Zero(paramBreed.getMarginItem2()), convertDoubleNull2Zero(paramBreed.getMarginItem3()), convertDoubleNull2Zero(paramBreed.getMarginItem4()), convertDoubleNull2Zero(paramBreed.getMarginItem1_S()), convertDoubleNull2Zero(paramBreed.getMarginItem2_S()), convertDoubleNull2Zero(paramBreed.getMarginItem3_S()), convertDoubleNull2Zero(paramBreed.getMarginItem4_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4_S()), paramBreed.getSettleMarginAlgr_B(), paramBreed.getSettleMarginAlgr_S(), paramBreed.getOrderPrivilege(), paramBreed.getPayoutAlgr(), convertDoubleNull2Zero(paramBreed.getPayoutRate()), convertLongNull2Zero(paramBreed.getFirmMaxHoldQty()), convertDoubleNull2Zero(paramBreed.getAddedTaxFactor()), convertDoubleNull2Zero(paramBreed.getMarginItem5()), convertDoubleNull2Zero(paramBreed.getMarginItem5_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5_S()), paramBreed.getSettlePriceType(), convertLongNull2Zero(paramBreed.getBeforeDays()), convertDoubleNull2Zero(paramBreed.getSpecSettlePrice()), paramBreed.getFirmMaxHoldQtyAlgr(), convertLongNull2Zero(paramBreed.getStartPercentQty()), convertDoubleNull2Zero(paramBreed.getMaxPercentLimit()), convertLongNull2Zero(paramBreed.getOneMaxHoldQty()), Integer.valueOf(paramBreed.getMinQuantityMove()), Integer.valueOf(paramBreed.getMinSettleMoveQty()), Integer.valueOf(paramBreed.getMinSettleQty()), paramBreed.getContractFactorName(), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate()), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate_S()), paramBreed.getSettleWay(), paramBreed.getDelayFeeWay(), paramBreed.getMaxFeeRate(), convertDoubleNull2Zero(paramBreed.getStoreRecoupRate()), paramBreed.getBreedTradeMode(), Integer.valueOf(paramBreed.getDelaySettlePriceType()), Integer.valueOf(paramBreed.getSettleMarginType()), Integer.valueOf(paramBreed.getBeforeDays_M()), Integer.valueOf(paramBreed.getHoldDaysLimit()), paramBreed.getMaxHoldPositionDay() };
    this.log.debug("sql: " + str1);
    for (int i = 0; i < arrayOfObject1.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
    }
    try
    {
      getJdbcTemplate().update(str1, arrayOfObject1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
    String str2 = "insert into T_A_BREEDTRADEPROP values (SEQ_T_A_BREED.currval,?,sysdate)";
    Object[] arrayOfObject2 = { "" };
    Set localSet = paramBreed.getTradeTime();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      TradeTime localTradeTime = (TradeTime)localIterator.next();
      arrayOfObject2[0] = localTradeTime.getSectionID();
      this.log.debug("sectionID: " + arrayOfObject2[0]);
      getJdbcTemplate().update(str2, arrayOfObject2);
    }
  }
  
  public void updateBreed(Breed paramBreed)
  {
    String str = "update T_A_BREED set MarketCode=?,BreedName=?,CmdtyPrefix=?,SortID=?,ContractFactor=?,MinPriceMove=?,SpreadAlgr=?,SpreadUpLmt=?,SpreadDownLmt=?,FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,MarginAlgr=?,MarginRate_B=?,MarginRate_S=?,StartTime=?,EndTime=?,MarketFeeAlgr=?,MarketFeeRate_B=?,MarketFeeRate_S=? where BreedID=?";
    Object[] arrayOfObject = { paramBreed.getMarketCode(), paramBreed.getBreedName(), paramBreed.getCmdtyPrefix(), paramBreed.getSortID(), convertDoubleNull2Zero(paramBreed.getContractFactor()), convertDoubleNull2Zero(paramBreed.getMinPriceMove()), paramBreed.getSpreadAlgr(), convertDoubleNull2Zero(paramBreed.getSpreadUpLmt()), convertDoubleNull2Zero(paramBreed.getSpreadDownLmt()), paramBreed.getFeeAlgr(), convertDoubleNull2Zero(paramBreed.getFeeRate_B()), convertDoubleNull2Zero(paramBreed.getFeeRate_S()), paramBreed.getMarginAlgr(), convertDoubleNull2Zero(paramBreed.getMarginRate_B()), convertDoubleNull2Zero(paramBreed.getMarginRate_S()), paramBreed.getStartTime(), paramBreed.getEndTime(), paramBreed.getMarketFeeAlgr(), convertDoubleNull2Zero(paramBreed.getMarketFeeRate_B()), convertDoubleNull2Zero(paramBreed.getMarketFeeRate_S()), paramBreed.getBreedID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("同一市场中的商品代码前缀应该唯一！");
    }
  }
  
  public void updateBreed1(Breed paramBreed)
  {
    String str1 = "update T_A_BREED set BreedName=?,SortID=?,ContractFactor=?,MinPriceMove=?,SpreadAlgr=?,SpreadUpLmt=?,SpreadDownLmt=?,FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,MarginAlgr=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?, LimitCmdtyQty=?, SettleFeeAlgr=?, SettleFeeRate_B=?,SettleFeeRate_S=?,ForceCloseFeeAlgr=?,ForceCloseFeeRate_B=?,ForceCloseFeeRate_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,AddedTax=?,MarginPriceType=?,LowestSettleFee=?,LimitBreedQty=?,FirmCleanQty=?,MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,settleMarginAlgr_B=?,settleMarginAlgr_S=?,OrderPrivilege=?,PayoutAlgr=?,PayoutRate=?,FirmMaxHoldQty=?,AddedTaxFactor=?,MarginItem5=?,MarginItem5_S=?,MarginItemAssure5=?,MarginItemAssure5_S=?,SettlePriceType=?,BeforeDays=?,SpecSettlePrice=?,FirmMaxHoldQtyAlgr=?,StartPercentQty=?,MaxPercentLimit=?,OneMaxHoldQty=?,MinQuantityMove=?,MinSettleMoveQty=?,MinSettleQty=?,ContractFactorName=?,DelayRecoupRate=?,DelayRecoupRate_S=?,SettleWay=?,DelayFeeWay=?,MaxFeeRate=? ,breedtradeMode=?,StoreRecoupRate=?,DelaySettlePriceType=?,SettleMarginType=?, BeforeDays_M=?,holdDaysLimit=?,maxHoldPositionDay=? where BreedID=?";
    Object[] arrayOfObject1 = { paramBreed.getBreedName(), paramBreed.getSortID(), convertDoubleNull2Zero(paramBreed.getContractFactor()), convertDoubleNull2Zero(paramBreed.getMinPriceMove()), paramBreed.getSpreadAlgr(), convertDoubleNull2Zero(paramBreed.getSpreadUpLmt()), convertDoubleNull2Zero(paramBreed.getSpreadDownLmt()), paramBreed.getFeeAlgr(), convertDoubleNull2Zero(paramBreed.getFeeRate_B()), convertDoubleNull2Zero(paramBreed.getFeeRate_S()), paramBreed.getMarginAlgr(), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_S()), convertLongNull2Zero(paramBreed.getLimitCmdtyQty()), paramBreed.getSettleFeeAlgr(), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_B()), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_S()), paramBreed.getForceCloseFeeAlgr(), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_B()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_S()), convertDoubleNull2Zero(paramBreed.getAddedTax()), paramBreed.getMarginPriceType(), convertDoubleNull2Zero(paramBreed.getLowestSettleFee()), convertLongNull2Zero(paramBreed.getLimitBreedQty()), convertLongNull2Zero(paramBreed.getFirmCleanQty()), convertDoubleNull2Zero(paramBreed.getMarginItem1()), convertDoubleNull2Zero(paramBreed.getMarginItem2()), convertDoubleNull2Zero(paramBreed.getMarginItem3()), convertDoubleNull2Zero(paramBreed.getMarginItem4()), convertDoubleNull2Zero(paramBreed.getMarginItem1_S()), convertDoubleNull2Zero(paramBreed.getMarginItem2_S()), convertDoubleNull2Zero(paramBreed.getMarginItem3_S()), convertDoubleNull2Zero(paramBreed.getMarginItem4_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4_S()), paramBreed.getSettleMarginAlgr_B(), paramBreed.getSettleMarginAlgr_S(), paramBreed.getOrderPrivilege(), paramBreed.getPayoutAlgr(), convertDoubleNull2Zero(paramBreed.getPayoutRate()), convertLongNull2Zero(paramBreed.getFirmMaxHoldQty()), convertDoubleNull2Zero(paramBreed.getAddedTaxFactor()), convertDoubleNull2Zero(paramBreed.getMarginItem5()), convertDoubleNull2Zero(paramBreed.getMarginItem5_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5_S()), paramBreed.getSettlePriceType(), convertLongNull2Zero(paramBreed.getBeforeDays()), convertDoubleNull2Zero(paramBreed.getSpecSettlePrice()), paramBreed.getFirmMaxHoldQtyAlgr(), convertLongNull2Zero(paramBreed.getStartPercentQty()), convertDoubleNull2Zero(paramBreed.getMaxPercentLimit()), convertLongNull2Zero(paramBreed.getOneMaxHoldQty()), Integer.valueOf(paramBreed.getMinQuantityMove()), Integer.valueOf(paramBreed.getMinSettleMoveQty()), Integer.valueOf(paramBreed.getMinSettleQty()), paramBreed.getContractFactorName(), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate()), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate_S()), paramBreed.getSettleWay(), paramBreed.getDelayFeeWay(), paramBreed.getMaxFeeRate(), paramBreed.getBreedTradeMode(), convertDoubleNull2Zero(paramBreed.getStoreRecoupRate()), Integer.valueOf(paramBreed.getDelaySettlePriceType()), Integer.valueOf(paramBreed.getSettleMarginType()), Integer.valueOf(paramBreed.getBeforeDays_M()), Integer.valueOf(paramBreed.getHoldDaysLimit()), paramBreed.getMaxHoldPositionDay(), paramBreed.getBreedID() };
    this.log.debug("sql: " + str1);
    for (int i = 0; i < arrayOfObject1.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
    }
    try
    {
      getJdbcTemplate().update(str1, arrayOfObject1);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("同一市场中的商品代码前缀应该唯一！");
    }
    String str2 = "delete from T_A_BREEDTRADEPROP b where b.breedID=?";
    Object[] arrayOfObject2 = { paramBreed.getBreedID() };
    this.log.debug("sql: " + str2);
    getJdbcTemplate().update(str2, arrayOfObject2);
    String str3 = "insert into T_A_BREEDTRADEPROP values (?,?,sysdate)";
    Object[] arrayOfObject3 = { paramBreed.getBreedID(), "" };
    Iterator localIterator = paramBreed.getTradeTime().iterator();
    while (localIterator.hasNext())
    {
      TradeTime localTradeTime = (TradeTime)localIterator.next();
      arrayOfObject3[1] = localTradeTime.getSectionID();
      this.log.debug("sectionID: " + arrayOfObject3[1]);
      getJdbcTemplate().update(str3, arrayOfObject3);
    }
  }
  
  public void deleteBreedById(String paramString)
  {
    Assert.hasText(paramString);
    Object[] arrayOfObject1 = { paramString };
    String str1 = "select count(*) from T_COMMODITY where BreedID=?";
    if (getJdbcTemplate().queryForInt(str1, arrayOfObject1) > 0) {
      throw new RuntimeException("此品种还有商品，不能删除！");
    }
    str1 = "delete from T_A_BREED where BreedID=?";
    this.log.debug("sql: " + str1);
    this.logger.debug("BreedID: " + arrayOfObject1[0]);
    getJdbcTemplate().update(str1, arrayOfObject1);
    String str2 = "delete from T_A_BREEDTRADEPROP b where b.breedID=?";
    Object[] arrayOfObject2 = { paramString };
    this.log.debug("sql: " + str2);
    this.logger.debug("BreedID: " + arrayOfObject1[0]);
    getJdbcTemplate().update(str2, arrayOfObject2);
  }
  
  public String getIsSettleFlagByModuleID(String paramString)
  {
    String str1 = "select t.isSettle from m_trademodule t where t.moduleID = '2'";
    List localList = null;
    String str2 = "";
    try
    {
      localList = getJdbcTemplate().queryForList(str1);
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        str2 = localMap.get("isSettle").toString();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str2;
  }
  
  public void insertBreedAndSettle(Breed paramBreed)
  {
    String str1 = "insert into T_A_BREED(BreedID,BreedName,SortID,ContractFactor,MinPriceMove,SpreadAlgr,SpreadUpLmt,SpreadDownLmt,FeeAlgr,FeeRate_B,FeeRate_S,MarginAlgr,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B ,HistoryCloseFeeRate_S, LimitCmdtyQty,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,SettleMarginRate_B,SettleMarginRate_S,AddedTax,MarginPriceType,LowestSettleFee,LimitBreedQty,FirmCleanQty,MarginItem1,MarginItem2,MarginItem3,MarginItem4,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,settleMarginAlgr_B,settleMarginAlgr_S,OrderPrivilege,PayoutAlgr,PayoutRate,FirmMaxHoldQty,AddedTaxFactor,MarginItem5,MarginItem5_S,MarginItemAssure5,MarginItemAssure5_S,SettlePriceType,BeforeDays,SpecSettlePrice,FirmMaxHoldQtyAlgr,StartPercentQty,MaxPercentLimit,OneMaxHoldQty,MinQuantityMove,MinSettleMoveQty,MinSettleQty,ContractFactorName,DelayRecoupRate,DelayRecoupRate_S,SettleWay,DelayFeeWay,MaxFeeRate,StoreRecoupRate,BreedTradeMode,DelaySettlePriceType,SettleMarginType,BeforeDays_M,holdDaysLimit,maxHoldPositionDay) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject1 = { paramBreed.getBreedID(), paramBreed.getBreedName(), paramBreed.getSortID(), convertDoubleNull2Zero(paramBreed.getContractFactor()), convertDoubleNull2Zero(paramBreed.getMinPriceMove()), paramBreed.getSpreadAlgr(), convertDoubleNull2Zero(paramBreed.getSpreadUpLmt()), convertDoubleNull2Zero(paramBreed.getSpreadDownLmt()), paramBreed.getFeeAlgr(), convertDoubleNull2Zero(paramBreed.getFeeRate_B()), convertDoubleNull2Zero(paramBreed.getFeeRate_S()), paramBreed.getMarginAlgr(), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_S()), convertLongNull2Zero(paramBreed.getLimitCmdtyQty()), paramBreed.getSettleFeeAlgr(), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_B()), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_S()), paramBreed.getForceCloseFeeAlgr(), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_B()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_S()), convertDoubleNull2Zero(paramBreed.getAddedTax()), paramBreed.getMarginPriceType(), convertDoubleNull2Zero(paramBreed.getLowestSettleFee()), convertLongNull2Zero(paramBreed.getLimitBreedQty()), convertLongNull2Zero(paramBreed.getFirmCleanQty()), convertDoubleNull2Zero(paramBreed.getMarginItem1()), convertDoubleNull2Zero(paramBreed.getMarginItem2()), convertDoubleNull2Zero(paramBreed.getMarginItem3()), convertDoubleNull2Zero(paramBreed.getMarginItem4()), convertDoubleNull2Zero(paramBreed.getMarginItem1_S()), convertDoubleNull2Zero(paramBreed.getMarginItem2_S()), convertDoubleNull2Zero(paramBreed.getMarginItem3_S()), convertDoubleNull2Zero(paramBreed.getMarginItem4_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4_S()), paramBreed.getSettleMarginAlgr_B(), paramBreed.getSettleMarginAlgr_S(), paramBreed.getOrderPrivilege(), paramBreed.getPayoutAlgr(), convertDoubleNull2Zero(paramBreed.getPayoutRate()), convertLongNull2Zero(paramBreed.getFirmMaxHoldQty()), convertDoubleNull2Zero(paramBreed.getAddedTaxFactor()), convertDoubleNull2Zero(paramBreed.getMarginItem5()), convertDoubleNull2Zero(paramBreed.getMarginItem5_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5_S()), paramBreed.getSettlePriceType(), convertLongNull2Zero(paramBreed.getBeforeDays()), convertDoubleNull2Zero(paramBreed.getSpecSettlePrice()), paramBreed.getFirmMaxHoldQtyAlgr(), convertLongNull2Zero(paramBreed.getStartPercentQty()), convertDoubleNull2Zero(paramBreed.getMaxPercentLimit()), convertLongNull2Zero(paramBreed.getOneMaxHoldQty()), Integer.valueOf(paramBreed.getMinQuantityMove()), Integer.valueOf(paramBreed.getMinSettleMoveQty()), Integer.valueOf(paramBreed.getMinSettleQty()), paramBreed.getContractFactorName(), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate()), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate_S()), paramBreed.getSettleWay(), paramBreed.getDelayFeeWay(), paramBreed.getMaxFeeRate(), convertDoubleNull2Zero(paramBreed.getStoreRecoupRate()), paramBreed.getBreedTradeMode(), Integer.valueOf(paramBreed.getDelaySettlePriceType()), Integer.valueOf(paramBreed.getSettleMarginType()), Integer.valueOf(paramBreed.getBeforeDays_M()), Integer.valueOf(paramBreed.getHoldDaysLimit()), paramBreed.getMaxHoldPositionDay() };
    this.log.debug("sql: " + str1);
    for (int i = 0; i < arrayOfObject1.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
    }
    try
    {
      getJdbcTemplate().update(str1, arrayOfObject1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
    String str2 = "insert into T_A_BREEDTRADEPROP values (" + paramBreed.getBreedID() + ",?,sysdate)";
    Object[] arrayOfObject2 = { "" };
    Set localSet = paramBreed.getTradeTime();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      TradeTime localTradeTime = (TradeTime)localIterator.next();
      arrayOfObject2[0] = localTradeTime.getSectionID();
      this.log.debug("sectionID: " + arrayOfObject2[0]);
      getJdbcTemplate().update(str2, arrayOfObject2);
    }
  }
  
  public void updateBreedAndSettle(Breed paramBreed)
  {
    String str1 = "update T_A_BREED set BreedName=?,SortID=?,ContractFactor=?,MinPriceMove=?,SpreadAlgr=?,SpreadUpLmt=?,SpreadDownLmt=?,FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,MarginAlgr=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?, LimitCmdtyQty=?, SettleFeeAlgr=?, SettleFeeRate_B=?,SettleFeeRate_S=?,ForceCloseFeeAlgr=?,ForceCloseFeeRate_B=?,ForceCloseFeeRate_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,AddedTax=?,MarginPriceType=?,LowestSettleFee=?,LimitBreedQty=?,FirmCleanQty=?,MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,settleMarginAlgr_B=?,settleMarginAlgr_S=?,OrderPrivilege=?,PayoutAlgr=?,PayoutRate=?,FirmMaxHoldQty=?,AddedTaxFactor=?,MarginItem5=?,MarginItem5_S=?,MarginItemAssure5=?,MarginItemAssure5_S=?,SettlePriceType=?,BeforeDays=?,SpecSettlePrice=?,FirmMaxHoldQtyAlgr=?,StartPercentQty=?,MaxPercentLimit=?,OneMaxHoldQty=?,MinQuantityMove=?,MinSettleMoveQty=?,MinSettleQty=?,ContractFactorName=?,DelayRecoupRate=?,DelayRecoupRate_S=?,SettleWay=?,DelayFeeWay=?,MaxFeeRate=? ,StoreRecoupRate=?,BreedTradeMode=?,DelaySettlePriceType=?,holdDaysLimit=?,maxHoldPositionDay=? where BreedID=?";
    Object[] arrayOfObject1 = { paramBreed.getBreedName(), paramBreed.getSortID(), convertDoubleNull2Zero(paramBreed.getContractFactor()), convertDoubleNull2Zero(paramBreed.getMinPriceMove()), paramBreed.getSpreadAlgr(), convertDoubleNull2Zero(paramBreed.getSpreadUpLmt()), convertDoubleNull2Zero(paramBreed.getSpreadDownLmt()), paramBreed.getFeeAlgr(), convertDoubleNull2Zero(paramBreed.getFeeRate_B()), convertDoubleNull2Zero(paramBreed.getFeeRate_S()), paramBreed.getMarginAlgr(), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getTodayCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getHistoryCloseFeeRate_S()), convertLongNull2Zero(paramBreed.getLimitCmdtyQty()), paramBreed.getSettleFeeAlgr(), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_B()), convertDoubleNull2Zero(paramBreed.getSettleFeeRate_S()), paramBreed.getForceCloseFeeAlgr(), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_B()), convertDoubleNull2Zero(paramBreed.getForceCloseFeeRate_S()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_B()), convertDoubleNull2Zero(paramBreed.getSettleMarginRate_S()), convertDoubleNull2Zero(paramBreed.getAddedTax()), paramBreed.getMarginPriceType(), convertDoubleNull2Zero(paramBreed.getLowestSettleFee()), convertLongNull2Zero(paramBreed.getLimitBreedQty()), convertLongNull2Zero(paramBreed.getFirmCleanQty()), convertDoubleNull2Zero(paramBreed.getMarginItem1()), convertDoubleNull2Zero(paramBreed.getMarginItem2()), convertDoubleNull2Zero(paramBreed.getMarginItem3()), convertDoubleNull2Zero(paramBreed.getMarginItem4()), convertDoubleNull2Zero(paramBreed.getMarginItem1_S()), convertDoubleNull2Zero(paramBreed.getMarginItem2_S()), convertDoubleNull2Zero(paramBreed.getMarginItem3_S()), convertDoubleNull2Zero(paramBreed.getMarginItem4_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure1_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure2_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure3_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure4_S()), paramBreed.getSettleMarginAlgr_B(), paramBreed.getSettleMarginAlgr_S(), paramBreed.getOrderPrivilege(), paramBreed.getPayoutAlgr(), convertDoubleNull2Zero(paramBreed.getPayoutRate()), convertLongNull2Zero(paramBreed.getFirmMaxHoldQty()), convertDoubleNull2Zero(paramBreed.getAddedTaxFactor()), convertDoubleNull2Zero(paramBreed.getMarginItem5()), convertDoubleNull2Zero(paramBreed.getMarginItem5_S()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5()), convertDoubleNull2Zero(paramBreed.getMarginItemAssure5_S()), paramBreed.getSettlePriceType(), convertLongNull2Zero(paramBreed.getBeforeDays()), convertDoubleNull2Zero(paramBreed.getSpecSettlePrice()), paramBreed.getFirmMaxHoldQtyAlgr(), convertLongNull2Zero(paramBreed.getStartPercentQty()), convertDoubleNull2Zero(paramBreed.getMaxPercentLimit()), convertLongNull2Zero(paramBreed.getOneMaxHoldQty()), Integer.valueOf(paramBreed.getMinQuantityMove()), Integer.valueOf(paramBreed.getMinSettleMoveQty()), Integer.valueOf(paramBreed.getMinSettleQty()), paramBreed.getContractFactorName(), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate()), convertDoubleNull2Zero(paramBreed.getDelayRecoupRate_S()), paramBreed.getSettleWay(), paramBreed.getDelayFeeWay(), paramBreed.getMaxFeeRate(), paramBreed.getBreedTradeMode(), convertDoubleNull2Zero(paramBreed.getStoreRecoupRate()), Integer.valueOf(paramBreed.getDelaySettlePriceType()), Integer.valueOf(paramBreed.getHoldDaysLimit()), paramBreed.getMaxHoldPositionDay(), paramBreed.getBreedID() };
    this.log.debug("sql: " + str1);
    for (int i = 0; i < arrayOfObject1.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
    }
    try
    {
      getJdbcTemplate().update(str1, arrayOfObject1);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("同一市场中的商品代码前缀应该唯一！");
    }
    String str2 = "delete from T_A_BREEDTRADEPROP b where b.breedID=?";
    Object[] arrayOfObject2 = { paramBreed.getBreedID() };
    this.log.debug("sql: " + str2);
    getJdbcTemplate().update(str2, arrayOfObject2);
    String str3 = "insert into T_A_BREEDTRADEPROP values (?,?,sysdate)";
    Object[] arrayOfObject3 = { paramBreed.getBreedID(), "" };
    Iterator localIterator = paramBreed.getTradeTime().iterator();
    while (localIterator.hasNext())
    {
      TradeTime localTradeTime = (TradeTime)localIterator.next();
      arrayOfObject3[1] = localTradeTime.getSectionID();
      this.log.debug("sectionID: " + arrayOfObject3[1]);
      getJdbcTemplate().update(str3, arrayOfObject3);
    }
  }
  
  class BreedRowMapper
    implements RowMapper
  {
    BreedRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToBreed(paramResultSet);
    }
    
    private Breed rsToBreed(ResultSet paramResultSet)
      throws SQLException
    {
      Breed localBreed = new Breed();
      localBreed.setBreedID(new Long(paramResultSet.getLong("BreedID")));
      localBreed.setMarketCode(paramResultSet.getString("MarketCode"));
      localBreed.setBreedName(paramResultSet.getString("BreedName"));
      localBreed.setCmdtyPrefix(paramResultSet.getString("CmdtyPrefix"));
      localBreed.setSortID(new Long(paramResultSet.getLong("SortID")));
      localBreed.setContractFactor(new Double(paramResultSet.getDouble("ContractFactor")));
      localBreed.setMinPriceMove(new Double(paramResultSet.getDouble("MinPriceMove")));
      localBreed.setSpreadAlgr(new Short(paramResultSet.getShort("SpreadAlgr")));
      localBreed.setSpreadUpLmt(new Double(paramResultSet.getDouble("SpreadUpLmt")));
      localBreed.setSpreadDownLmt(new Double(paramResultSet.getDouble("SpreadDownLmt")));
      localBreed.setFeeAlgr(new Short(paramResultSet.getShort("FeeAlgr")));
      localBreed.setFeeRate_B(new Double(paramResultSet.getDouble("FeeRate_B")));
      localBreed.setFeeRate_S(new Double(paramResultSet.getDouble("FeeRate_S")));
      localBreed.setMarginAlgr(new Short(paramResultSet.getShort("MarginAlgr")));
      localBreed.setMarginRate_B(new Double(paramResultSet.getDouble("MarginRate_B")));
      localBreed.setMarginRate_S(new Double(paramResultSet.getDouble("MarginRate_S")));
      localBreed.setStartTime(paramResultSet.getString("StartTime"));
      localBreed.setEndTime(paramResultSet.getString("EndTime"));
      localBreed.setMarketFeeAlgr(new Short(paramResultSet.getShort("MarketFeeAlgr")));
      localBreed.setMarketFeeRate_B(new Double(paramResultSet.getDouble("MarketFeeRate_B")));
      localBreed.setMarketFeeRate_S(new Double(paramResultSet.getDouble("MarketFeeRate_S")));
      localBreed.setMarginAssure_B(new Double(paramResultSet.getDouble("marginAssure_B")));
      localBreed.setMarginAssure_S(new Double(paramResultSet.getDouble("marginAssure_S")));
      localBreed.setTodayCloseFeeRate_B(new Double(paramResultSet.getDouble("TodayCloseFeeRate_B")));
      localBreed.setTodayCloseFeeRate_S(new Double(paramResultSet.getDouble("TodayCloseFeeRate_S")));
      localBreed.setHistoryCloseFeeRate_B(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_B")));
      localBreed.setHistoryCloseFeeRate_S(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_S")));
      localBreed.setLimitCmdtyQty(new Long(paramResultSet.getString("LimitCmdtyQty")));
      localBreed.setSettleFeeAlgr(new Short(paramResultSet.getShort("SettleFeeAlgr")));
      localBreed.setForceCloseFeeAlgr(new Short(paramResultSet.getShort("ForceCloseFeeAlgr")));
      localBreed.setSettleFeeRate_B(new Double(paramResultSet.getDouble("SettleFeeRate_B")));
      localBreed.setSettleFeeRate_S(new Double(paramResultSet.getDouble("SettleFeeRate_S")));
      localBreed.setForceCloseFeeRate_B(new Double(paramResultSet.getDouble("ForceCloseFeeRate_B")));
      localBreed.setForceCloseFeeRate_S(new Double(paramResultSet.getDouble("ForceCloseFeeRate_S")));
      int i = paramResultSet.getInt("holdDaysLimit");
      localBreed.setHoldDaysLimit(i);
      if ("1".equals(Integer.valueOf(i))) {
        localBreed.setMaxHoldPositionDay(Integer.valueOf(paramResultSet.getInt("MaxHoldPositionDay")));
      }
      return localBreed;
    }
  }
}
