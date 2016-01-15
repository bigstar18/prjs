package gnnt.MEBS.xhzc.manage;

import java.io.PrintStream;
import java.util.Hashtable;

public class CommodityCodeList
{
  public static Hashtable ht = new Hashtable();
  public static int flag = 0;

  public void setValue(String commodityID, String status)
  {
    try {
      ht.put(commodityID, status);
    }
    catch (Exception e)
    {
      System.out.println(e.toString());
    }
  }

  public String getValue(String commodityID) {
    String i = null;
    try
    {
      i = (String)ht.get(commodityID);
    }
    catch (Exception e)
    {
      System.out.println(e.toString());
    }
    return i;
  }

  public void removeValue(String commodityID)
  {
    try {
      ht.remove(commodityID);
    }
    catch (Exception e)
    {
      System.out.println(e.toString());
    }
  }

  public void clearValue() {
    ht = new Hashtable();
  }

  public boolean isNull() {
    if (ht.isEmpty())
    {
      return true;
    }

    return false;
  }

  public int getFlag()
  {
    return flag;
  }

  public void setFlag(int flag) {
    flag = flag;
  }
}