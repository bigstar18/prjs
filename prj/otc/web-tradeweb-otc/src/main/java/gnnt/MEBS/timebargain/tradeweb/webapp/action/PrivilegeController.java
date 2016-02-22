package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.timebargain.server.model.WithdrawOrder;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import java.math.BigDecimal;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrivilegeController
{
  private static final Log log = LogFactory.getLog(PrivilegeController.class);
  
  public static ResponseResult checkMarketOrder(Privilege prvlg, MarketOrder ov, ResponseResult rr)
  {
    Map<String, Map<String, BigDecimal>> firmTradePrivilege = prvlg.getFirmTradePrivilege();
    if (firmTradePrivilege == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + "交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    Map<String, BigDecimal> map = (Map)firmTradePrivilege.get(
      ov.getCommodityID());
    if (map == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + " 商品代码=" + ov.getCommodityID() + "的交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    if (ov.getBuyOrSell().shortValue() == 1)
    {
      if (ov.getOC_Flag() == 'O')
      {
        if (((BigDecimal)map.get("M_B_Open")).intValue() == 0)
        {
          rr.setRetCode(-1);
          rr.setMessage("对不起，交易商没有买入市价建仓的权限！");
          return rr;
        }
      }
      else if (((BigDecimal)map.get("M_B_Close")).intValue() == 0)
      {
        rr.setRetCode(-2);
        rr.setMessage("对不起，交易商没有买入市价平仓的权限！");
        return rr;
      }
    }
    else if (ov.getOC_Flag() == 'O')
    {
      if (((BigDecimal)map.get("M_S_Open")).intValue() == 0)
      {
        rr.setRetCode(-3);
        rr.setMessage("对不起，交易商没有卖出市价建仓的权限！");
        return rr;
      }
    }
    else if (((BigDecimal)map.get("M_S_Close")).intValue() == 0)
    {
      rr.setRetCode(-4);
      rr.setMessage("对不起，交易商没有卖出市价平仓的权限！");
      return rr;
    }
    return rr;
  }
  
  public static ResponseResult checkMarketOrder(Privilege prvlg, LimitOrder ov, ResponseResult rr)
  {
    Map<String, Map<String, BigDecimal>> firmTradePrivilege = prvlg.getFirmTradePrivilege();
    if (firmTradePrivilege == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + "交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    Map<String, BigDecimal> map = (Map)firmTradePrivilege.get(
      ov.getCommodityID());
    if (map == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + " 商品代码=" + ov.getCommodityID() + "的交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    if (ov.getBuyOrSell().shortValue() == 1)
    {
      if (((BigDecimal)map.get("L_B_Open")).intValue() == 0)
      {
        rr.setRetCode(-1);
        rr.setMessage("对不起，交易商没有买入限价建仓的权限！");
        return rr;
      }
      if ((ov.getStopLossPrice().doubleValue() > 0.0D) && 
        (((BigDecimal)map.get("S_StopLoss")).intValue() == 0))
      {
        rr.setRetCode(-3);
        rr.setMessage("交易商没有设置卖出止损权限！");
        return rr;
      }
      if ((ov.getStopProfitPrice().doubleValue() > 0.0D) && 
        (((BigDecimal)map.get("S_StopProfit")).intValue() == 0))
      {
        rr.setRetCode(-4);
        rr.setMessage("交易商没有设置卖出止盈权限！");
        return rr;
      }
    }
    else
    {
      if (((BigDecimal)map.get("L_S_Open")).intValue() == 0)
      {
        rr.setRetCode(-2);
        rr.setMessage("对不起，交易商没有卖出限价建仓的权限！");
        return rr;
      }
      if ((ov.getStopLossPrice().doubleValue() > 0.0D) && 
        (((BigDecimal)map.get("B_StopLoss")).intValue() == 0))
      {
        rr.setRetCode(-1);
        rr.setMessage("对不起，交易商没有设置买入止损权限！");
        return rr;
      }
      if ((ov.getStopProfitPrice().doubleValue() > 0.0D) && 
        (((BigDecimal)map.get("B_StopProfit")).intValue() == 0))
      {
        rr.setRetCode(-2);
        rr.setMessage("对不起，交易商没有设置买入止盈权限！");
        return rr;
      }
    }
    return rr;
  }
  
  public static ResponseResult checkWithdrawOrder(Privilege prvlg, WithdrawOrder ov, String commodityID, ResponseResult rr)
  {
    Map<String, Map<String, BigDecimal>> firmTradePrivilege = prvlg.getFirmTradePrivilege();
    if (firmTradePrivilege == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + "交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    Map<String, BigDecimal> map = (Map)firmTradePrivilege.get(
      commodityID);
    if (map == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + " 商品代码=" + commodityID + "的交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    if (((BigDecimal)map.get("Cancel_L_Open")).intValue() == 0)
    {
      rr.setRetCode(-1);
      rr.setMessage("对不起，交易商没有撤销限价建仓的权限！");
      return rr;
    }
    return rr;
  }
  
  public static ResponseResult checkSetLossProfit(Privilege prvlg, String commodityID, int TY, double STOP_LOSS, double STOP_PROFIT, ResponseResult rr)
  {
    Map<String, Map<String, BigDecimal>> firmTradePrivilege = prvlg.getFirmTradePrivilege();
    if (firmTradePrivilege == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + "交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    Map<String, BigDecimal> map = (Map)firmTradePrivilege.get(
      commodityID);
    if (map == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + " 商品代码=" + commodityID + "的交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    if (TY == 1)
    {
      if ((STOP_LOSS > 0.0D) && 
        (((BigDecimal)map.get("B_StopLoss")).intValue() == 0))
      {
        rr.setRetCode(-1);
        rr.setMessage("对不起，交易商没有设置买入止损权限！");
        return rr;
      }
      if ((STOP_PROFIT > 0.0D) && 
        (((BigDecimal)map.get("B_StopProfit")).intValue() == 0))
      {
        rr.setRetCode(-2);
        rr.setMessage("对不起，交易商没有设置买入止盈权限！");
        return rr;
      }
    }
    else
    {
      if ((STOP_LOSS > 0.0D) && 
        (((BigDecimal)map.get("S_StopLoss")).intValue() == 0))
      {
        rr.setRetCode(-3);
        rr.setMessage("交易商没有设置卖出止损权限！");
        return rr;
      }
      if ((STOP_PROFIT > 0.0D) && 
        (((BigDecimal)map.get("S_StopProfit")).intValue() == 0))
      {
        rr.setRetCode(-4);
        rr.setMessage("交易商没有设置卖出止盈权限！");
        return rr;
      }
    }
    return rr;
  }
  
  public static ResponseResult checkWithdrawLossProfit(Privilege prvlg, String commodityID, int TYPE, ResponseResult rr)
  {
    Map<String, Map<String, BigDecimal>> firmTradePrivilege = prvlg.getFirmTradePrivilege();
    if (firmTradePrivilege == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + "交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    Map<String, BigDecimal> map = (Map)firmTradePrivilege.get(
      commodityID);
    if (map == null)
    {
      log.warn("userID=" + prvlg.getFirmId() + " 商品代码=" + commodityID + "的交易权限为空！");
      
      rr.setRetCode(0);
      return rr;
    }
    if (TYPE == 3)
    {
      if ((((BigDecimal)map.get("Cancel_StopLoss")).intValue() == 0) || 
        (((BigDecimal)map.get("Cancel_StopProfit")).intValue() == 0))
      {
        rr.setRetCode(-1);
        rr.setMessage("对不起，交易商没有撤销止损止盈权限！");
        return rr;
      }
    }
    else if (TYPE == 2)
    {
      if (((BigDecimal)map.get("Cancel_StopProfit")).intValue() == 0)
      {
        rr.setRetCode(-2);
        rr.setMessage("对不起，交易商没有撤销止盈权限！");
        return rr;
      }
    }
    else if (((BigDecimal)map.get("Cancel_StopLoss")).intValue() == 0)
    {
      rr.setRetCode(-3);
      rr.setMessage("对不起，交易商没有撤销止损权限！");
      return rr;
    }
    return rr;
  }
}
