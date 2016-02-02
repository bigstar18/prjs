package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractTradeMatcher
  extends Thread
  implements TradeMatcher
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  protected List<Order> orders;
  protected TradeEngine te;
  protected String commodityCode;
  protected CommodityOrder commodityOrder;
  protected TradeCallback tradeback;
  protected volatile boolean stop = false;
  protected boolean quotationTwoSide;
  protected int tradePriceType;
  protected double preTradePrice;
  
  public void pleaseStop()
  {
    this.stop = true;
  }
  
  public void start()
  {
    super.start();
  }
  
  public void init(TradeEngine paramTradeEngine, String paramString)
  {
    this.te = paramTradeEngine;
    this.commodityCode = paramString;
    this.commodityOrder = ((CommodityOrder)paramTradeEngine.commodityOrders.get(paramString));
    this.orders = this.commodityOrder.waitOrders;
    this.tradeback = paramTradeEngine.getTradeCallback();
    if (this.te.getMarket().getQuotationTwoSide().shortValue() == 2) {
      this.quotationTwoSide = true;
    } else {
      this.quotationTwoSide = false;
    }
    this.tradePriceType = Integer.parseInt(DAOBeanFactory.getConfig("TradePriceType"));
  }
  
  public double getMiddleTradePrice(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double[] arrayOfDouble = new double[3];
    arrayOfDouble[0] = paramDouble1;
    arrayOfDouble[1] = paramDouble2;
    arrayOfDouble[2] = paramDouble3;
    arrayOfDouble = maoPao(arrayOfDouble);
    return arrayOfDouble[1];
  }
  
  public double[] maoPao(double[] paramArrayOfDouble)
  {
    for (int i = 0; i < paramArrayOfDouble.length; i++) {
      for (int j = i + 1; j < paramArrayOfDouble.length; j++) {
        if (paramArrayOfDouble[i] > paramArrayOfDouble[j])
        {
          double d = paramArrayOfDouble[i];
          paramArrayOfDouble[i] = paramArrayOfDouble[j];
          paramArrayOfDouble[j] = d;
        }
      }
    }
    return paramArrayOfDouble;
  }
}
