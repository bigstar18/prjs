package gnnt.MEBS.bill.core;

import gnnt.MEBS.bill.core.vo.StockOperation;
import java.io.PrintStream;

public class Test
{
  public static void main(String[] paramArrayOfString)
  {
    StockOperation localStockOperation = StockOperation.DELIVERY;
    switch (localStockOperation)
    {
    case DELIVERY: 
      System.out.println("dddd");
    }
  }
}
