package gnnt.MEBS.common.test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LikeHashMap
  extends HashMap
{
  public Set keySet()
  {
    Set localSet = super.keySet();
    TreeSet localTreeSet = null;
    if (localSet != null) {
      localTreeSet = new TreeSet(localSet);
    }
    return localTreeSet;
  }
  
  public List<Object> get(String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList1 = new ArrayList();
    if (paramBoolean)
    {
      ArrayList localArrayList2 = new ArrayList();
      TreeSet localTreeSet = (TreeSet)keySet();
      Iterator localIterator = localTreeSet.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.indexOf(paramString) != -1)
        {
          localArrayList2.add(str);
          localArrayList1.add(get(str));
        }
        else
        {
          if ((str.indexOf(paramString) != -1) || (localArrayList2.size() != 0)) {
            break;
          }
        }
      }
      localArrayList2.clear();
      localArrayList2 = null;
    }
    else
    {
      localArrayList1.add(get(paramString));
    }
    return localArrayList1;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    LikeHashMap localLikeHashMap = new LikeHashMap();
    for (int i = 0; i < 100000; i++) {
      localLikeHashMap.put("A_" + i, "AAAAAA" + i);
    }
    long l = System.currentTimeMillis();
    System.out.println(localLikeHashMap.get("A", true).size());
    System.out.println(System.currentTimeMillis() - l);
  }
}
