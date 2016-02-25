package gnnt.MEBS.vendue.server.vo;

import java.util.Hashtable;

public class FundInfoList
{
  public static Hashtable list = null;
  
  public FundInfoList()
  {
    if (list == null) {
      list = new Hashtable();
    }
  }
}
