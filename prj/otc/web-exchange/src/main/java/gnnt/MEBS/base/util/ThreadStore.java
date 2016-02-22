package gnnt.MEBS.base.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadStore
{
  private static ThreadLocal<Map<String, Object>> tl = new ThreadLocal();
  
  public static Map get()
  {
    return (Map)tl.get();
  }
  
  public static void set(Map map)
  {
    tl.set(map);
  }
  
  public static void put(String key, Object value)
  {
    Map map = get();
    if (map == null) {
      map = new HashMap();
    }
    map.put(key, value);
    set(map);
  }
  
  public static Object get(String key)
  {
    Object o = null;
    Map map = get();
    if (map != null) {
      o = map.get(key);
    }
    return o;
  }
  
  public static void clear()
  {
    set(null);
  }
}
