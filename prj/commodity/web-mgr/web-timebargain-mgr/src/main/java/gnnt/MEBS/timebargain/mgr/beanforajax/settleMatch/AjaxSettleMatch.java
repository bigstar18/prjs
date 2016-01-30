package gnnt.MEBS.timebargain.mgr.beanforajax.settleMatch;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.beanforajax.AjaxCheckDemo;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxSettleMatch")
@Scope("request")
public class AjaxSettleMatch extends AjaxCheckDemo
{
  public String getSettleWay()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();
    String commodityId = request.getParameter("commodityId");

    String sql = "select distinct(SettleProcessDate) settleDate from T_SettleHoldPosition where commodityId = '" + commodityId + "' and settleType<>2 and matchStatus!=2";
    List listSettleDate = getService().getListBySql(sql);
    String settleDate = "";
    if ((listSettleDate != null) && (listSettleDate.size() > 0)) {
      for (int i = 0; i < listSettleDate.size(); i++) {
        settleDate = ((Map)listSettleDate.get(i)).get("SETTLEDATE").toString();
        settleDate = settleDate.substring(0, 10);
        this.jsonValidateReturn.add(settleDate);
      }
    }
    return this.SUCCESS;
  }

  public String checkFirmId()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    int result = -1;
    String firmId = request.getParameter("firmId");
    String sql = "select * from m_firm where firmId = '" + firmId + "'";
    List list = getService().getListBySql(sql);

    if ((list != null) && (list.size() > 0)) {
      result = 1;
    }
    this.jsonValidateReturn = new JSONArray();
    this.jsonValidateReturn.add(Integer.valueOf(result));
    return this.SUCCESS;
  }
}