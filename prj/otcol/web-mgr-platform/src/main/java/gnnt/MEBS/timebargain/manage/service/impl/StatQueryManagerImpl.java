package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.StatQueryDAO;
import gnnt.MEBS.timebargain.manage.dao.StatQueryUseMainDSDao;
import gnnt.MEBS.timebargain.manage.model.Apply;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.util.List;

public class StatQueryManagerImpl
  extends BaseManager
  implements StatQueryManager
{
  private StatQueryDAO dao;
  private StatQueryUseMainDSDao statQueryUseMainDSDao;
  
  public void setStatQueryUseMainDSDao(StatQueryUseMainDSDao paramStatQueryUseMainDSDao)
  {
    this.statQueryUseMainDSDao = paramStatQueryUseMainDSDao;
  }
  
  public void setStatQueryDAO(StatQueryDAO paramStatQueryDAO)
  {
    this.dao = paramStatQueryDAO;
  }
  
  public int getOrderCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getOrderCount(paramQueryConditions);
  }
  
  public List getOrderSum(QueryConditions paramQueryConditions)
  {
    return this.dao.getOrderSum(paramQueryConditions);
  }
  
  public List getOrderSums(QueryConditions paramQueryConditions)
  {
    return this.dao.getOrderSums(paramQueryConditions);
  }
  
  public List getOrders(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getOrders(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int getHoldPositionCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getHoldPositionCount(paramQueryConditions);
  }
  
  public List getHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getHoldPositions(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public List getTrades(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getTrades(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public List getTradesSums(QueryConditions paramQueryConditions)
  {
    return this.dao.getTradesSums(paramQueryConditions);
  }
  
  public int getTradesCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getTradesCount(paramQueryConditions);
  }
  
  public List getTradesSum(QueryConditions paramQueryConditions)
  {
    return this.dao.getTradesSum(paramQueryConditions);
  }
  
  public List getCustomerFundss(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getCustomerFundss(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public List getCustomerFund(QueryConditions paramQueryConditions, String paramString1, String paramString2)
  {
    return this.statQueryUseMainDSDao.getCustomerFund(paramQueryConditions, paramString1, paramString2);
  }
  
  public List getDailyMoneys(QueryConditions paramQueryConditions)
  {
    return this.statQueryUseMainDSDao.getDailyMoneys(paramQueryConditions);
  }
  
  public List getQuotations(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getQuotations(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int getQuotationCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getQuotationCount(paramQueryConditions);
  }
  
  public List getFundTransfer(QueryConditions paramQueryConditions)
  {
    return this.statQueryUseMainDSDao.getFundTransfer(paramQueryConditions);
  }
  
  public void updateFundTransferStatusById(String paramString)
  {
    this.statQueryUseMainDSDao.updateFundTransferStatusById(paramString);
  }
  
  public List getFirmHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getFirmHoldPositions(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public List getFirmHoldPositionsSum(QueryConditions paramQueryConditions)
  {
    return this.dao.getFirmHoldPositionsSum(paramQueryConditions);
  }
  
  public List getHandSettle(QueryConditions paramQueryConditions)
  {
    return this.statQueryUseMainDSDao.getHandSettle(paramQueryConditions);
  }
  
  public List getSettle(StatQuery paramStatQuery)
  {
    return this.statQueryUseMainDSDao.getSettle(paramStatQuery);
  }
  
  public void updateSettle(StatQuery paramStatQuery)
  {
    this.statQueryUseMainDSDao.updateSettle(paramStatQuery);
  }
  
  public List getSettleSum()
  {
    return this.statQueryUseMainDSDao.getSettleSum();
  }
  
  public String getConferClose(Settle paramSettle)
  {
    return this.statQueryUseMainDSDao.getConferClose(paramSettle);
  }
  
  public String getConferClose2(Settle paramSettle)
  {
    return this.statQueryUseMainDSDao.getConferClose2(paramSettle);
  }
  
  public String getConferClose3(Settle paramSettle)
  {
    return this.statQueryUseMainDSDao.getConferClose3(paramSettle);
  }
  
  public List getMarketCode()
  {
    return this.statQueryUseMainDSDao.getMarketCode();
  }
  
  public void insertSettle(StatQuery paramStatQuery)
  {
    this.statQueryUseMainDSDao.insertSettle(paramStatQuery);
  }
  
  public List getAheadSettleLog()
  {
    return this.statQueryUseMainDSDao.getAheadSettleLog();
  }
  
  public List getConferCloseLog()
  {
    return this.statQueryUseMainDSDao.getConferCloseLog();
  }
  
  public List getHoldPositions1(QueryConditions paramQueryConditions)
  {
    return this.statQueryUseMainDSDao.getHoldPositions1(paramQueryConditions);
  }
  
  public List getSettleTogether(String paramString)
  {
    return this.statQueryUseMainDSDao.getSettleTogether(paramString);
  }
  
  public List getApplyWaitCD(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getApplyWaitCD(paramApply);
  }
  
  public List getApplyAlreadyCD(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getApplyAlreadyCD(paramApply);
  }
  
  public Apply getGiveUpDD(String paramString)
  {
    return this.statQueryUseMainDSDao.getGiveUpDD(paramString);
  }
  
  public Apply getAlreadySettle(String paramString)
  {
    return this.statQueryUseMainDSDao.getAlreadySettle(paramString);
  }
  
  public void insertApplyGiveUpDD(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertApplyGiveUpDD(paramApply);
  }
  
  public void insertApplyAlreadySettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertApplyAlreadySettle(paramApply);
  }
  
  public void insertWaitSettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertWaitSettle(paramApply);
  }
  
  public void insertDD(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertDD(paramApply);
  }
  
  public void insertBeforeSettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertBeforeSettle(paramApply);
  }
  
  public Apply getApplyBillById(String paramString)
  {
    return this.statQueryUseMainDSDao.getApplyBillById(paramString);
  }
  
  public void updateApplyCheckFail(Apply paramApply)
  {
    this.statQueryUseMainDSDao.updateApplyCheckFail(paramApply);
  }
  
  public Apply getGiveUpWaitSettle(String paramString)
  {
    return this.statQueryUseMainDSDao.getGiveUpWaitSettle(paramString);
  }
  
  public void inserApplyGiveUpWaitSettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.inserApplyGiveUpWaitSettle(paramApply);
  }
  
  public List getApplyLiveInfo(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getApplyLiveInfo(paramApply);
  }
  
  public Apply getValidIDByApplyID(String paramString)
  {
    return this.statQueryUseMainDSDao.getValidIDByApplyID(paramString);
  }
  
  public List getQuantity(String paramString1, String paramString2)
  {
    return this.statQueryUseMainDSDao.getQuantity(paramString1, paramString2);
  }
  
  public void updateApplyCheckSuccess(Apply paramApply)
  {
    this.statQueryUseMainDSDao.updateApplyCheckSuccess(paramApply);
  }
  
  public List getHoldQtyDD(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getHoldQtyDD(paramApply);
  }
  
  public List getHoldQtyAheadSettleB(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getHoldQtyAheadSettleB(paramApply);
  }
  
  public List getHoldQtyAheadSettleS(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getHoldQtyAheadSettleS(paramApply);
  }
  
  public List getHoldQtyWaitSettle(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getHoldQtyWaitSettle(paramApply);
  }
  
  public List getGageQty(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.getGageQty(paramApply);
  }
  
  public Apply getValidIDByApplyID2(String paramString)
  {
    return this.statQueryUseMainDSDao.getValidIDByApplyID2(paramString);
  }
  
  public List getSettlePairsB(StatQuery paramStatQuery)
  {
    return this.statQueryUseMainDSDao.getSettlePairsB(paramStatQuery);
  }
  
  public List getSettlePairS(StatQuery paramStatQuery)
  {
    return this.statQueryUseMainDSDao.getSettlePairS(paramStatQuery);
  }
  
  public Apply getValidIDByApplyIDGiveup(String paramString)
  {
    return this.statQueryUseMainDSDao.getValidIDByApplyIDGiveup(paramString);
  }
  
  public String getSysdate()
  {
    return this.statQueryUseMainDSDao.getSysdate();
  }
  
  public void deleteApplyGiveupDD(Apply paramApply)
  {
    this.statQueryUseMainDSDao.deleteApplyGiveupDD(paramApply);
  }
  
  public void deleteApplyWaitSettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.deleteApplyWaitSettle(paramApply);
  }
  
  public Apply getValidIDByApplyID2LiveinfoBefore(String paramString)
  {
    return this.statQueryUseMainDSDao.getValidIDByApplyID2LiveinfoBefore(paramString);
  }
  
  public List getCustomerFundsTable(StatQuery paramStatQuery)
  {
    return this.dao.getCustomerFundsTable(paramStatQuery);
  }
  
  public String validatorCustomer_S(Apply paramApply)
  {
    return this.statQueryUseMainDSDao.validatorCustomer_S(paramApply);
  }
  
  public List getPledgeList(StatQuery paramStatQuery)
  {
    return this.statQueryUseMainDSDao.getPledgeList(paramStatQuery);
  }
  
  public void insertPledge(StatQuery paramStatQuery)
  {
    this.statQueryUseMainDSDao.insertPledge(paramStatQuery);
  }
  
  public StatQuery getPledgeById(String paramString)
  {
    return this.statQueryUseMainDSDao.getPledgeById(paramString);
  }
  
  public void updatePledge(StatQuery paramStatQuery)
  {
    this.statQueryUseMainDSDao.updatePledge(paramStatQuery);
  }
  
  public void deletePledgeById(String paramString)
  {
    this.statQueryUseMainDSDao.deletePledgeById(paramString);
  }
  
  public String getSystemStatus()
  {
    return this.statQueryUseMainDSDao.getSystemStatus();
  }
  
  public SystemStatus getSystemStatusObject()
  {
    return this.statQueryUseMainDSDao.getSystemStatusObject();
  }
  
  public Boolean getHandSettleCnt(String paramString)
  {
    return this.statQueryUseMainDSDao.getHandSettleCnt(paramString);
  }
  
  public String getQuotationPrice(String paramString)
  {
    return this.statQueryUseMainDSDao.getQuotationPrice(paramString);
  }
  
  public void insertDelaySettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertDelaySettle(paramApply);
  }
  
  public Apply getGiveUpDelaySettle(String paramString)
  {
    return this.statQueryUseMainDSDao.getGiveUpDelaySettle(paramString);
  }
  
  public void insertApplyGiveUpDelaySettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.insertApplyGiveUpDelaySettle(paramApply);
  }
  
  public Apply getValidIDByApplyIDGiveupDelaySettle(String paramString)
  {
    return this.statQueryUseMainDSDao.getValidIDByApplyIDGiveupDelaySettle(paramString);
  }
  
  public void deleteApplyGiveupDelaySettle(Apply paramApply)
  {
    this.statQueryUseMainDSDao.deleteApplyGiveupDelaySettle(paramApply);
  }
  
  public int getFirmHoldPositionsCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getFirmHoldPositionsCount(paramQueryConditions);
  }
  
  public int getCustomerFundssCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getCustomerFundssCount(paramQueryConditions);
  }
  
  public String getSysdate1()
  {
    return this.dao.getSysdate1();
  }
  
  public List getBrokerHoldPositions(QueryConditions paramQueryConditions)
  {
    return this.dao.getBrokerHoldPositions(paramQueryConditions);
  }
  
  public List getBrokerFunds(QueryConditions paramQueryConditions)
  {
    return this.dao.getBrokerFunds(paramQueryConditions);
  }
  
  public List getBrokerTrades(QueryConditions paramQueryConditions)
  {
    return this.dao.getBrokerTrades(paramQueryConditions);
  }
  
  public List getFirmCategory()
  {
    return this.dao.getFirmCategory();
  }
}
