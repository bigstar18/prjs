package gnnt.MEBS.common.security.util;

import gnnt.MEBS.common.model.Right;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
  
  public long checkRight(String paramString, boolean paramBoolean)
  {
    long l = -1L;
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = null;
    Object localObject2;
    if (paramBoolean)
    {
      localObject2 = (TreeSet)keySet();
      Iterator localIterator = ((TreeSet)localObject2).iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = paramString;
        String str3 = str1.replaceAll("\\*", "");
        if (str2.indexOf(str3) != -1) {
          localObject1 = str1;
        }
      }
    }
    else if (get(paramString) != null)
    {
      localObject1 = paramString;
    }
    if (localObject1 != null)
    {
      localObject2 = (Right)get(localObject1);
      l = ((Right)localObject2).getId().longValue();
    }
    return l;
  }
}
