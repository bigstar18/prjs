package gnnt.MEBS.vendue.server.beans.busbeans;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEquals
{
  public static void main(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    int i = 1000000;
    for (int j = 0; j < i; j++)
    {
      localArrayList.add(new Long(j));
      localHashMap.put(new Long(j), new Long(j));
    }
    Long localLong = new Long(2L);
    long l1 = System.currentTimeMillis();
    for (int k = localArrayList.size() - 1; k >= 0; k--) {
      if (localLong.longValue() <= ((Long)localArrayList.get(k)).longValue()) {}
    }
    long l2 = System.currentTimeMillis();
    System.out.println("1: " + (l2 - l1));
    for (int m = 0; m < i; m++) {
      Object localObject = localHashMap.get(new Long(200L));
    }
    long l3 = System.currentTimeMillis();
    System.out.println(l3 - l2);
  }
}
