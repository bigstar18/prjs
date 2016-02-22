package gnnt.MEBS.packaging.action.util;

import gnnt.MEBS.config.constant.LogConstant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHashMap<K extends String, V>
  extends HashMap<K, V>
{
  private final transient Log logger = LogFactory.getLog(LogHashMap.class);
  
  public Set<K> keySet()
  {
    Set set = super.keySet();
    TreeSet tSet = new TreeSet(new TimeComparator(null));
    if (set != null) {
      for (Object object : set) {
        tSet.add(object);
      }
    }
    return tSet;
  }
  
  public V remove(Object key)
  {
    this.logger.debug("LogHashMap remove");
    V value = super.remove(key);
    return value;
  }
  
  public V put(K key, V value)
  {
    this.logger.debug("enter put");
    V v = super.put(key, value);
    int expireCount = LogConstant.EXPIRECOUNT;
    this.logger.debug("expireCount:" + expireCount);
    this.logger.debug("mapSize:" + size());
    if (size() > expireCount)
    {
      int count = size();
      Iterator<K> it = keySet().iterator();
      while (it.hasNext())
      {
        String str = (String)it.next();
        if (count <= expireCount) {
          break;
        }
        it.remove();
        count--;
      }
    }
    return v;
  }
  
  private class TimeComparator
    implements Comparator<String>
  {
    private TimeComparator() {}
    
    public int compare(String str1, String str2)
    {
      String[] str1Spilt = str1.split(LogConstant.MARK);
      String str1TimeMillsString = str1Spilt[1];
      long str1TimeMills = Long.parseLong(str1TimeMillsString);
      String[] str2Spilt = str2.split(LogConstant.MARK);
      String str2TimeMillsString = str2Spilt[1];
      long str2TimeMills = Long.parseLong(str2TimeMillsString);
      long timeMills = str1TimeMills - str2TimeMills;
      int result = 0;
      if (timeMills <= 0L) {
        result = 1;
      } else if (timeMills > 0L) {
        result = -1;
      }
      return result;
    }
  }
}
