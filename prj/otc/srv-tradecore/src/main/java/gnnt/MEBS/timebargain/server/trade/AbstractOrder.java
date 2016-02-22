package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.FirmDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.DelayPrice;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.HoldQty_RT;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractOrder
{
  protected Log log = LogFactory.getLog(getClass());
  protected TradeEngine tradeEngine;
  public Map<String, List<DelayPrice>> delayPrice = new HashMap();
  
  public void init(TradeEngine tradeEngine)
  {
    this.tradeEngine = tradeEngine;
  }
  
  public int validateOrder(Order order)
  {
    String traderID = order.getTraderID();
    if ((traderID != null) && (!traderID.equals("")) && 
      (order.getConsignerID() != null) && 
      (!order.getConsignerID().equals(""))) {
      return 5;
    }
    if (this.tradeEngine.getTraderOrderStatus() == 1) {
      return 3;
    }
    String firmID = order.getFirmID();
    if ((traderID == null) || (traderID.equals("")))
    {
      FirmDAO firmDAO = Server.getFirmDAO();
      long result = firmDAO.checkDelegateInfo(order.getConsignFirmID(), 
        firmID);
      if (result == -1L) {
        return 38;
      }
      if (result == -2L) {
        return 37;
      }
      order.setTraderID(order.getConsignerID());
    }
    Firm firm = (Firm)ServerInit.getFirmQueue().get(firmID);
    if (firm == null) {
      return 34;
    }
    return 0;
  }
  
  public void addPrice(Double curPrice, String commodityId)
  {
    DelayPrice dp = new DelayPrice();
    List<DelayPrice> dList = new ArrayList();
    dp.setPrice(curPrice);
    dp.setPriceTime(System.currentTimeMillis());
    if (!this.delayPrice.containsKey(commodityId))
    {
      List<DelayPrice> list = new ArrayList();
      this.delayPrice.put(commodityId, list);
    }
    synchronized ((List)this.delayPrice.get(commodityId))
    {
      for (DelayPrice dPrice : (List)this.delayPrice.get(commodityId)) {
        if (System.currentTimeMillis() - dPrice.getPriceTime() > ServerInit.maxDelayTime) {
          dList.add(dPrice);
        }
      }
      ((List)this.delayPrice.get(commodityId)).removeAll(dList);
      
      ((List)this.delayPrice.get(commodityId)).add(0, dp);
    }
  }
  
  public int validateOrderQty(Order order)
  {
    Firm firm = (Firm)ServerInit.getFirmQueue().get(order.getFirmID());
    
    HoldQty_RT holdQty_RT = 
      (HoldQty_RT)firm.getHoldQtyMap().get(order.getCommodityID());
    this.log.debug("holdQty_RT:" + holdQty_RT);
    if (holdQty_RT.getOneMaxOrderQty() != -1L) {
      if (order.getQuantity().longValue() > holdQty_RT.getOneMaxOrderQty()) {
        return 50;
      }
    }
    if (holdQty_RT.getOneMinOrderQty() != -1L) {
      if (order.getQuantity().longValue() < holdQty_RT.getOneMinOrderQty()) {
        return 51;
      }
    }
    return 0;
  }
  
  public int validateCommodity(Order order)
  {
    Commodity commodity = 
      (Commodity)((Map)ServerInit.getCommodityQueue().get(Server.getSystemStatus().getSectionID())).get(
      order.getCommodityID());
    if (commodity == null) {
      return 11;
    }
    if (!commodity.getAcceptHQFlag()) {
      return 70;
    }
    if (commodity.getTradeStatus() == 'P') {
      return 10;
    }
    if (commodity.getSpreadAlgr() != 4)
    {
      if (order.getPrice().doubleValue() > commodity.getSpreadUpLmt()) {
        return 12;
      }
      if (order.getPrice().doubleValue() < commodity.getSpreadDownLmt()) {
        return 13;
      }
    }
    if (commodity.getMinPriceMove() != 0.0D)
    {
      if (!Arith.divideExactly(order.getPrice().doubleValue(), commodity
        .getMinPriceMove())) {
        return 14;
      }
      order.setPrice(
        Double.valueOf(Arith.priceFormat(order.getPrice().doubleValue())));
    }
    return 0;
  }
  
  public boolean judgeTrade(Order order)
  {
    boolean bRet = false;
    String m_FirmID = order.getOtherFirmID();
    Member member = (Member)ServerInit.getMemberQueue().get(m_FirmID);
    Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
      order.getCommodityID());
    if (order.getBuyOrSell().shortValue() == 1)
    {
      if (order.getPrice().doubleValue() >= quotation_RT.getCurPrice_B()) {
        bRet = true;
      }
    }
    else if ((order.getBuyOrSell().shortValue() == 2) && 
      (order.getPrice().doubleValue() <= quotation_RT.getCurPrice_S())) {
      bRet = true;
    }
    return bRet;
  }
  
  public abstract int openOrder(Order paramOrder);
  
  public abstract int closeOrder(Order paramOrder);
  
  public abstract int withdrawOrder(Order paramOrder);
}
