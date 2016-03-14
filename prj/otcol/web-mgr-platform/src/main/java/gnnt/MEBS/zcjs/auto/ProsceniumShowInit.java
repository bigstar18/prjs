package gnnt.MEBS.zcjs.auto;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.memory.show.operate.OperateShow;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import gnnt.MEBS.zcjs.services.ProsceniumShowService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProsceniumShowInit
{
  private ProsceniumShowService prosceniumShowService;
  
  public void setProsceniumShowService(ProsceniumShowService prosceniumShowService)
  {
    this.prosceniumShowService = prosceniumShowService;
  }
  
  public void init()
  {
    Map<String, List<ProsceniumShow>> proMap = new HashMap();
    QueryConditions qc = new QueryConditions();
    qc.addCondition("prosceniumapplication", "like", "QT_");
    List listkeyList = this.prosceniumShowService.getProsceniumapplicationList(qc);
    for (Object object : listkeyList)
    {
      Map map = (Map)object;
      String prosceniumapplication = (String)map.get("prosceniumapplication");
      QueryConditions proQc = new QueryConditions();
      proQc.addCondition("prosceniumapplication", "=", prosceniumapplication);
      List list = this.prosceniumShowService.getObjectList(proQc);
      
      proMap.put(prosceniumapplication, list);
    }
    OperateShow.setApplicationMap(proMap);
  }
}
