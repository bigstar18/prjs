package gnnt.MEBS.vendue.server.vo;

import java.util.Hashtable;

public class TradeCommodityList1
{
  public static Hashtable list = null;
  
  public TradeCommodityList1()
  {
    if (list == null) {
      list = new Hashtable();
    }
  }
}
