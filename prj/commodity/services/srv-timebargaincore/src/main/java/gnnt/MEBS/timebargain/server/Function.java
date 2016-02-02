package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Tariff;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Function
{
  private static final Log log = LogFactory.getLog(Function.class);
  
  public static double computeMargin(Order paramOrder, Firm paramFirm, Commodity paramCommodity)
  {
    String str = paramOrder.getCommodityID();
    long l = paramOrder.getQuantity().longValue();
    double d1;
    if (paramCommodity.getMarginPriceType() == 1) {
      d1 = paramCommodity.getLastPrice();
    } else {
      d1 = paramOrder.getPrice().doubleValue();
    }
    int i = paramOrder.getBuyOrSell().shortValue();
    double d2 = paramCommodity.getContractFactor();
    double d3 = paramCommodity.getMarginRate_B();
    double d4 = paramCommodity.getMarginRate_S();
    int j = paramCommodity.getMarginAlgr();
    Commodity localCommodity = (Commodity)paramFirm.getFirmMarginMap().get(str);
    if (localCommodity != null)
    {
      d3 = localCommodity.getMarginRate_B();
      d4 = localCommodity.getMarginRate_S();
      j = localCommodity.getMarginAlgr();
    }
    double d5 = 0.0D;
    log.debug("quantity:" + l);
    log.debug("v_contractFactor:" + d2);
    log.debug("price:" + d1);
    log.debug("v_marginAlgr:" + j);
    log.debug("v_marginRate_b:" + d3);
    log.debug("v_marginRate_s:" + d4);
    if (j == 1)
    {
      if (i == 1)
      {
        if (d3 == -1.0D) {
          d5 = l * Arith.mul(d2, d1);
        } else {
          d5 = l * Arith.mul(d2, Arith.mul(d1, d3));
        }
      }
      else if (i == 2) {
        if (d4 == -1.0D) {
          d5 = l * Arith.mul(d2, d1);
        } else {
          d5 = l * Arith.mul(d2, Arith.mul(d1, d4));
        }
      }
    }
    else if (j == 2) {
      if (i == 1)
      {
        if (d3 == -1.0D) {
          d5 = l * Arith.mul(d2, d1);
        } else {
          d5 = l * d3;
        }
      }
      else if (i == 2) {
        if (d4 == -1.0D) {
          d5 = l * Arith.mul(d2, d1);
        } else {
          d5 = l * d4;
        }
      }
    }
    return d5;
  }
  
  public static double computeFee(Order paramOrder, Firm paramFirm, Commodity paramCommodity)
  {
    String str = paramOrder.getCommodityID();
    long l = paramOrder.getQuantity().longValue();
    double d1;
    if (paramCommodity.getMarginPriceType() == 1) {
      d1 = paramCommodity.getLastPrice();
    } else {
      d1 = paramOrder.getPrice().doubleValue();
    }
    int i = paramOrder.getBuyOrSell().shortValue();
    double d2 = paramCommodity.getContractFactor();
    double d3 = paramCommodity.getFeeRate_B();
    double d4 = paramCommodity.getFeeRate_S();
    int j = paramCommodity.getFeeAlgr();
    Tariff localTariff = (Tariff)ServerInit.getInstance().tariffQueue.get(paramFirm.getTariffID() + "_" + str);
    if (localTariff != null)
    {
      d3 = localTariff.getFeeRate_B();
      d4 = localTariff.getFeeRate_S();
      j = localTariff.getFeeAlgr();
    }
    Commodity localCommodity = (Commodity)paramFirm.getFirmFeeMap().get(str);
    if (localCommodity != null)
    {
      d3 = localCommodity.getFeeRate_B();
      d4 = localCommodity.getFeeRate_S();
      j = localCommodity.getFeeAlgr();
    }
    double d5 = 0.0D;
    log.debug("quantity:" + l);
    log.debug("v_contractFactor:" + d2);
    log.debug("price:" + d1);
    log.debug("v_feeAlgr:" + j);
    log.debug("v_feeRate_b:" + d3);
    log.debug("v_feeRate_s:" + d4);
    if (j == 1)
    {
      if (i == 1) {
        d5 = l * Arith.mul(d2, Arith.mul(d1, d3));
      } else if (i == 2) {
        d5 = l * Arith.mul(d2, Arith.mul(d1, d4));
      }
    }
    else if (j == 2) {
      if (i == 1) {
        d5 = l * d3;
      } else if (i == 2) {
        d5 = l * d4;
      }
    }
    return d5;
  }
}
