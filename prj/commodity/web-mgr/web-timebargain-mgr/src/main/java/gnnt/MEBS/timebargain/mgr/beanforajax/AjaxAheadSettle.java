package gnnt.MEBS.timebargain.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxAheadSettle")
@Scope("request")
public class AjaxAheadSettle extends AjaxCheckDemo
{
  public String getSettlePriceType()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    int result = -1;
    String commodityId = request.getParameter("commodityId");
    String sql = "select aheadsettlepricetype from t_commodity where commodityId = '" + commodityId + "'";
    List list = getService().getListBySql(sql);

    if ((list != null) && (list.size() > 0)) {
      result = Integer.parseInt(((Map)list.get(0)).get("AHEADSETTLEPRICETYPE").toString());
    }
    this.jsonValidateReturn = new JSONArray();
    this.jsonValidateReturn.add(Integer.valueOf(result));
    return this.SUCCESS;
  }
}