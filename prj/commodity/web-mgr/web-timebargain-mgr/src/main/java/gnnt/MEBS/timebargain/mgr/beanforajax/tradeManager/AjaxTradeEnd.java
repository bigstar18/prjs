package gnnt.MEBS.timebargain.mgr.beanforajax.tradeManager;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.beanforajax.AjaxCheckDemo;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxTradeEnd")
@Scope("request")
public class AjaxTradeEnd extends AjaxCheckDemo
{
  public String getStatusJson()
  {
    List list = getService().getListBySql(
      "select status from T_SystemStatus");

    int i = 0;

    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      i = Integer.parseInt(map.get("STATUS").toString());
    }

    this.jsonValidateReturn = new JSONArray();

    this.jsonValidateReturn.add(Integer.valueOf(i));
    return this.SUCCESS;
  }
}