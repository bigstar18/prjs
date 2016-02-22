package gnnt.MEBS.common.test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LikeHashMap
  extends HashMap
{
  public Set keySet()
  {
    Set set = super.keySet();
    TreeSet tSet = null;
    if (set != null) {
      tSet = new TreeSet(set);
    }
    return tSet;
  }
  
  public List<Object> get(String key, boolean like)
  {
    List<Object> value = new ArrayList();
    if (like)
    {
      List<String> keyList = new ArrayList();
      TreeSet<String> treeSet = (TreeSet)keySet();
      for (String string : treeSet) {
        if (string.indexOf(key) != -1)
        {
          keyList.add(string);
          value.add(get(string));
        }
        else
        {
          if ((string.indexOf(key) != -1) || (keyList.size() != 0)) {
            break;
          }
        }
      }
      keyList.clear();
      keyList = null;
    }
    else
    {
      value.add(get(key));
    }
    return value;
  }
  
  public static void main(String[] args)
  {
    LikeHashMap hMap = new LikeHashMap();
    for (int i = 0; i < 100000; i++) {
      hMap.put("A_" + i, "AAAAAA" + i);
    }
    long time = System.currentTimeMillis();
    System.out.println(hMap.get("A", true).size());
    System.out.println(System.currentTimeMillis() - time);
  }
}
