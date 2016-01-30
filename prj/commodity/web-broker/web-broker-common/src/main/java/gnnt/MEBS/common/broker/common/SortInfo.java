package gnnt.MEBS.common.broker.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortInfo
  implements Serializable
{
  private static final long serialVersionUID = 6959974032209696722L;
  private String columnName;
  private String sortOrder;

  public SortInfo()
  {
  }

  public SortInfo(String paramString1, String paramString2)
  {
    this.columnName = paramString1;
    this.sortOrder = paramString2;
  }

  public String getColumnName()
  {
    return this.columnName;
  }

  public void setColumnName(String paramString)
  {
    this.columnName = paramString;
  }

  public String getSortOrder()
  {
    return this.sortOrder;
  }

  public void setSortOrder(String paramString)
  {
    this.sortOrder = paramString;
  }

  public static List<SortInfo> parseSortColumns(String paramString)
  {
    if (paramString == null)
      return new ArrayList(0);
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString1 = paramString.trim().split(",");
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      String str = arrayOfString1[i];
      String[] arrayOfString2 = str.split("\\s+");
      SortInfo localSortInfo = new SortInfo();
      localSortInfo.setColumnName(arrayOfString2[0]);
      localSortInfo.setSortOrder(arrayOfString2.length == 2 ? arrayOfString2[1] : null);
      localArrayList.add(localSortInfo);
    }
    return localArrayList;
  }

  public String toString()
  {
    return new StringBuilder().append(this.columnName).append(this.sortOrder == null ? "" : new StringBuilder().append(" ").append(this.sortOrder).toString()).toString();
  }
}