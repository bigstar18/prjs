package gnnt.MEBS.util;

import java.io.PrintStream;
import java.util.Vector;

public class ObjSetUtil
{
  public static ObjSet getObjSet(Object[] array, int pageSize, int pageIndex, boolean isDescend)
  {
    Vector vTemp = new Vector();

    if (!isDescend) {
      Object[] temp = new Object[array.length];
      for (int i = 0; i < array.length; i++)
        temp[i] = array[(array.length - 1 - i)];
      array = temp;
    }

    if ((pageIndex == 0) || (pageSize == 0)) {
      for (int i = 0; i < array.length; i++)
        vTemp.add(array[i]);
      return ObjSet.getInstance(vTemp.toArray(new Object[vTemp.size()]), 
        vTemp.size(), vTemp.size(), 1);
    }

    int start = (pageIndex - 1) * pageSize + 1;
    int end = pageIndex * pageSize;
    int curCount = 0;

    for (int i = 0; i < array.length; i++)
      if (i + 1 >= start)
      {
        vTemp.add(array[i]);
        curCount++;
        if (i + 1 == end)
          break;
      }
    int pageCount = 0;
    if (pageSize != 0) {
      pageCount = array.length / pageSize;
      if (array.length % pageSize != 0)
        pageCount++;
    }
    return ObjSet.getInstance(vTemp.toArray(new Object[vTemp.size()]), 
      array.length, pageSize, 
      pageIndex);
  }

  public static void main(String[] args) {
    Long[] l = new Long[10];
    for (int i = 0; i < 10; i++) {
      l[i] = new Long(i + 1);
    }
    ObjSet objSet = getObjSet(l, 2, 3, true);
    System.out.println("getCurNum:" + objSet.getCurNum() + "<br>");
    System.out.println("getPageCount:" + objSet.getPageCount() + "<br>");
    System.out.println("getPageIndex:" + objSet.getPageIndex() + "<br>");
    System.out.println("getPageSize:" + objSet.getPageSize() + "<br>");
    System.out.println("getTotalCount:" + objSet.getTotalCount() + "<br>");

    for (int i = 0; i < objSet.size(); i++)
      System.out.println((Long)objSet.get(i));
  }
}