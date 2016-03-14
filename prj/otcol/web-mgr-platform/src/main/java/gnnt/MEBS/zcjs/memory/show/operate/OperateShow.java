package gnnt.MEBS.zcjs.memory.show.operate;

import gnnt.MEBS.zcjs.memory.show.ShowFields;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import java.util.List;
import java.util.Map;

public class OperateShow
{
  public static void setApplicationMap(Map<String, List<ProsceniumShow>> applicationMap)
  {
    ShowFields showFields = ShowFields.createInstance();
    showFields.setApplicationMap(applicationMap);
  }
}
