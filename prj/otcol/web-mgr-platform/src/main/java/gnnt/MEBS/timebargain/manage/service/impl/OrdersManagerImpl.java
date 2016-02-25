package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.OrdersDAO;
import gnnt.MEBS.timebargain.manage.dao.TradeCtlDAO;
import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.service.OrdersManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.action.AgencyRMIBean;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.List;
import java.util.Map;

public class OrdersManagerImpl
  extends BaseManager
  implements OrdersManager
{
  private OrdersDAO dao;
  private TradeCtlDAO tradeCtlDao;
  
  public void setOrdersDAO(OrdersDAO dao)
  {
    this.dao = dao;
  }
  
  public void setTradeCtlDAO(TradeCtlDAO tradeCtlDao)
  {
    this.tradeCtlDao = tradeCtlDao;
  }
  
  public List noTradeList(Orders orders, String sectionId, String traderId)
  {
    return this.dao.noTradeList(orders, sectionId, traderId);
  }
  
  public List getNotMarketCodeOrders()
  {
    return this.dao.getNotMarketCodeOrders();
  }
  
  public void updateMarketOrderNo(Long a_orderNo, Long m_orderNo)
  {
    this.dao.updateMarketOrderNo(a_orderNo, m_orderNo);
  }
  
  public List queryWithdrawReason()
  {
    return this.dao.queryWithdrawReason();
  }
  
  public List holdMonitor(long holdQty)
  {
    return this.dao.holdMonitor(holdQty);
  }
  
  public List fundMonitor(double monFund)
  {
    return this.dao.fundMonitor(monFund);
  }
  
  public Map performMonitor()
  {
    return this.dao.performMonitor();
  }
  
  public List procLog(QueryConditions conditions)
  {
    return this.dao.procLog(conditions);
  }
  
  public void deleteProcLog(QueryConditions conditions)
  {
    this.dao.deleteProcLog(conditions);
  }
  
  public List sysLog(QueryConditions conditions, String querytype)
  {
    return this.dao.sysLog(conditions, querytype);
  }
  
  public void deleteSysLog(QueryConditions conditions)
  {
    this.dao.deleteSysLog(conditions);
  }
  
  public List getTradeResponds()
  {
    return this.dao.getTradeResponds();
  }
  
  public String getMarketCodeByTradeRespondID(Long id)
  {
    return this.dao.getMarketCodeByTradeRespondID(id);
  }
  
  public void deleteTradeRespondById(Long id)
  {
    this.dao.deleteTradeRespondById(id);
  }
  
  public void deleteTradeRespond()
  {
    this.dao.deleteTradeRespond();
  }
  
  public List getTradeRespondsByStatus(Short status)
  {
    return this.dao.getTradeRespondsByStatus(status);
  }
  
  public void updateTradeRespond(String M_TradeNo, int status)
  {
    this.dao.updateTradeRespond(M_TradeNo, status);
  }
  
  public String getFirmIDToDefine(String customerID)
  {
    return this.dao.getFirmIDToDefine(customerID);
  }
  
  public List traderLog(QueryConditions conditions)
  {
    return this.dao.traderLog(conditions);
  }
  
  public int insertOrder(AgencyRMIBean remObject, Long sessionID, Order ov)
    throws Exception
  {
    return remObject.consignerOrder(sessionID.longValue(), ov);
  }
  
  public int insertCloseOrder(AgencyRMIBean remObject, Long sessionID, Order ov)
    throws Exception
  {
    int ret = 0;
    

    this.dao.lockHoldPosition(ov);
    
    QueryConditions qc = new QueryConditions();
    qc.addCondition("firmid", "=", ov.getFirmID());
    qc.addCondition("commodityID", "=", ov.getCommodityID());
    qc.addCondition("bS_Flag", "!=", ov.getBuyOrSell());
    qc.addCondition("remainday", "=", Integer.valueOf(0));
    List<Map> list = this.tradeCtlDao.getHoldPositionsDetail(qc, 0, 2147483647);
    long maycloseQty = 0L;
    long holdqty = 0L;
    for (Map map : list)
    {
      maycloseQty += Long.parseLong(map.get("maycloseQty").toString());
      holdqty += Long.parseLong(map.get("holdqty").toString());
    }
    long closeQty = holdqty <= maycloseQty ? holdqty : maycloseQty;
    if (ov.getQuantity().longValue() > closeQty) {
      ret = -500;
    } else {
      ret = remObject.consignerOrder(sessionID.longValue(), ov);
    }
    return ret;
  }
  
  public String resultOrder(int ret)
    throws Exception
  {
    String result = "";
    switch (ret)
    {
    case 0: 
      break;
    case 1: 
      result = "校验异常！";
      break;
    case 2: 
      result = "市场未启用！";
      break;
    case 3: 
      result = "不是交易时间！";
      break;
    case 4: 
      result = "不是代为委托员交易时间！";
      break;
    case 5: 
      result = "交易员和代为委托员不能同时存在！";
      break;
    case 10: 
      result = "商品处于禁止交易状态！";
      break;
    case 11: 
      result = "商品不属于当前交易节！";
      break;
    case 12: 
      result = "委托价格超出涨幅上限！";
      break;
    case 13: 
      result = "委托价格低于跌幅下限！";
      break;
    case 14: 
      result = "委托价格不在此商品最小价位变动范围内！";
      break;
    case 15: 
      result = "不存在此商品！";
      break;
    case 16: 
      result = "委托数量不在此商品最小变动数量范围内！";
      break;
    case 30: 
      result = "此交易员不存在！";
      break;
    case 31: 
      result = "此交易员没有操作该客户的权限！";
      break;
    case 32: 
      result = "此交易客户不存在！";
      break;
    case 33: 
      result = "此交易客户为禁止交易状态！";
      break;
    case 34: 
      result = "此交易商不存在！";
      break;
    case 35: 
      result = "此交易商为禁止交易状态！";
      break;
    case 37: 
      result = "此代为委托员没有操作该交易商的权限！";
      break;
    case 38: 
      result = "此代为委托员不存在！";
      break;
    case 40: 
      result = "计算交易保证金错误！";
      break;
    case 41: 
      result = "计算交易手续费错误！";
      break;
    case 42: 
      result = "此委托已成交或已撤单！";
      break;
    case 199: 
      result = "通信故障！";
      break;
    case 200: 
      result = "代码异常而失败！";
      break;
    case -1: 
      result = "资金余额不足！";
      break;
    case -2: 
      result = "超过交易商对此商品的最大订货量！";
      break;
    case -3: 
      result = "超过客户总的最大订货量！";
      break;
    case -4: 
      result = "超过品种最大订货量！";
      break;
    case -5: 
      result = "超过商品最大订货量！";
      break;
    case -6: 
      result = "超过交易商对此商品的最大净订货量！";
      break;
    case -7: 
      result = "超过单笔最大委托量！";
      break;
    case -11: 
      result = "此委托已处于正在撤单状态！";
      break;
    case -12: 
      result = "此委托已全部成交了！";
      break;
    case -13: 
      result = "此委托已完成撤单了！";
      break;
    case -21: 
      result = "订货不足！";
      break;
    case -22: 
      result = "指定仓不足！";
      break;
    case -99: 
      result = "执行存储时未找到相关数据！";
      break;
    case -100: 
      result = "执行存储失败！";
      break;
    case -204: 
      result = "交易服务器已关闭！";
      break;
    case -206: 
      result = "委托信息不能为空！";
      break;
    default: 
      result = "委托失败！";
    }
    return result;
  }
}
