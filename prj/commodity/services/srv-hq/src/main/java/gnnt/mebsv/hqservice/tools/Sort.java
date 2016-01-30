package gnnt.mebsv.hqservice.tools;

import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.util.Comparator;

public class Sort
  implements Comparator
{
  int sortKey;

  public Sort(int paramInt)
  {
    this.sortKey = paramInt;
  }

  public int compare(Object paramObject1, Object paramObject2)
  {
    ProductDataVO localProductDataVO1 = (ProductDataVO)paramObject1;
    ProductDataVO localProductDataVO2 = (ProductDataVO)paramObject2;
    switch (this.sortKey)
    {
    case 0:
      return localProductDataVO1.code.compareTo(localProductDataVO2.code);
    case 1:
      return new Float(localProductDataVO1.curPrice).compareTo(new Float(localProductDataVO2.curPrice));
    case 2:
      return new Float(localProductDataVO1.yesterBalancePrice <= 0.0F ? 0.0F : localProductDataVO1.curPrice - localProductDataVO1.yesterBalancePrice).compareTo(new Float(localProductDataVO2.yesterBalancePrice <= 0.0F ? 0.0F : localProductDataVO2.curPrice - localProductDataVO2.yesterBalancePrice));
    case 3:
      return new Float(localProductDataVO1.upRate).compareTo(new Float(localProductDataVO2.upRate));
    case 4:
      return new Float(localProductDataVO1.closePrice > 0.0F ? (localProductDataVO1.highPrice - localProductDataVO1.lowPrice) / localProductDataVO1.closePrice : 0.0F).compareTo(new Float(localProductDataVO2.closePrice > 0.0F ? (localProductDataVO2.highPrice - localProductDataVO2.lowPrice) / localProductDataVO2.closePrice : 0.0F));
    case 5:
      return new Float(localProductDataVO1.amountRate).compareTo(new Float(localProductDataVO2.amountRate));
    case 6:
      return new Float(localProductDataVO1.totalMoney).compareTo(new Float(localProductDataVO2.totalMoney));
    case 7:
      return new Float(localProductDataVO1.consignRate).compareTo(new Float(localProductDataVO2.consignRate));
    case 8:
      return new Float(localProductDataVO1.upRate5min).compareTo(new Float(localProductDataVO2.upRate5min));
    case 9:
      return new Float((float)localProductDataVO1.totalAmount).compareTo(new Float((float)localProductDataVO2.totalAmount));
    }
    return 0;
  }
}