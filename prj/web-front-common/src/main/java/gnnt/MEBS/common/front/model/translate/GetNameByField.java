package gnnt.MEBS.common.front.model.translate;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public class GetNameByField
  implements IGetNameByField
{
  private List<IGetNameByField> getNameByFieldList;
  
  public List<IGetNameByField> getGetNameByFieldList()
  {
    return this.getNameByFieldList;
  }
  
  public void setGetNameByFieldList(List<IGetNameByField> paramList)
  {
    this.getNameByFieldList = paramList;
  }
  
  public String getName(Field paramField)
  {
    String str = "";
    if ((this.getNameByFieldList != null) && (this.getNameByFieldList.size() > 0))
    {
      Iterator localIterator = this.getNameByFieldList.iterator();
      while (localIterator.hasNext())
      {
        IGetNameByField localIGetNameByField = (IGetNameByField)localIterator.next();
        str = localIGetNameByField.getName(paramField);
        if ((str != null) && (str.length() > 0)) {
          break;
        }
      }
    }
    return str;
  }
}
