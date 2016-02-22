package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.ExchageRate;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.util.Map;

public class PriceInterfaceImpl
  implements PriceInterface
{
  public Double convertPrice(String commodityID, Double price)
  {
    ExchageRate exchageRate = (ExchageRate)QuotationEngine.getExchageRateMap().get(commodityID);
    double minPriceMove = ((Commodity)ServerInit.getCommodityMap().get(commodityID)).getMinPriceMove();
    int scale = Arith.getDecimalDigits(minPriceMove);
    
    Double retPrice = Double.valueOf(Arith.add(exchageRate.getQuoteAgio().doubleValue(), Arith.mul(exchageRate.getQuoteExchangeRate().doubleValue(), Arith.mul(price.doubleValue(), exchageRate.getQuoteRate().doubleValue()))));
    
    retPrice = Double.valueOf(Math.round(retPrice.doubleValue() / minPriceMove) * minPriceMove);
    return Double.valueOf(Arith.format(retPrice.doubleValue(), scale));
  }
}
