package gnnt.MEBS.common.security.util;

import gnnt.MEBS.common.model.Right;
import java.util.ArrayList;
import java.util.Comparator;
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
    TreeSet tSet = new TreeSet(new StringLengthComparator(null));
    if (set != null) {
      for (Object object : set) {
        tSet.add(object);
      }
    }
    return tSet;
  }
  
  public long checkRight(String key, boolean like)
  {
    long result = -1L;
    List<Object> value = new ArrayList();
    String rightKey = null;
    if (like)
    {
      TreeSet<String> treeSet = (TreeSet)keySet();
      for (String string : treeSet)
      {
        String stringMatch = key;
        String s = string.replaceAll("\\*", "");
        if (stringMatch.indexOf(s) != -1)
        {
          rightKey = string;
          break;
        }
      }
    }
    else if (get(key) != null)
    {
      rightKey = key;
    }
    if (rightKey != null)
    {
      Right right = (Right)get(rightKey);
      result = right.getId().longValue();
    }
    return result;
  }
  
  private class StringLengthComparator
    implements Comparator<String>
  {
    private StringLengthComparator() {}
    
    public int compare(String str1, String str2)
    {
      int result = str1.length() - str2.length();
      result = -result;
      if (result == 0) {
        result = str1.length();
      }
      return result;
    }
  }
}
