package gnnt.MEBS.timebargain.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxTransfer")
@Scope("request")
public class AjaxTransfer extends AjaxCheckDemo
{
  public String getCommodity()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String customerid_s = request.getParameter("customerid_s");
    String bs_flag = request.getParameter("bs_flag");
    String result = "";
    String sql = "select distinct commodityid from t_customerholdsum where customerid = '" + customerid_s + "' and bs_flag = " + bs_flag;
    List list = getService().getListBySql(sql);

    if ((list != null) && (list.size() > 0)) {
      for (int i = 0; i < list.size(); i++) {
        this.jsonValidateReturn.add(((Map)list.get(i)).get("COMMODITYID").toString());
      }
    }
    return this.SUCCESS;
  }

  public String getQuantity()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String customerid_s = request.getParameter("customerid_s");
    String bs_flag = request.getParameter("bs_flag");
    String commodityid = request.getParameter("commodityid");
    String type = request.getParameter("type");

    int result = 0;
    if (type.equals("0")) {
      String sql = "select c.holdqty-c.frozenqty as qty from t_customerholdsum c where c.commodityid = '" + commodityid + "' and c.customerid = '" + customerid_s + "' and c.bs_flag = " + bs_flag;
      List list = getService().getListBySql(sql);
      if ((list != null) && (list.size() > 0)) {
        result = Integer.parseInt(((Map)list.get(0)).get("QTY").toString());
      }
    }
    this.jsonValidateReturn.add(Integer.valueOf(result));
    return this.SUCCESS;
  }

  public String getHoldQty()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String customerid_s = request.getParameter("customerid_s");
    String bs_flag = request.getParameter("bs_flag");
    String commodityid = request.getParameter("commodityid");

    int result = 0;
    String sql = "select c.holdqty-c.frozenqty as qty from t_customerholdsum c where c.commodityid = '" + commodityid + "' and c.customerid = '" + customerid_s + "' and c.bs_flag = " + bs_flag;
    List list = getService().getListBySql(sql);
    if ((list != null) && (list.size() > 0)) {
      result = Integer.parseInt(((Map)list.get(0)).get("QTY").toString());
    }
    this.jsonValidateReturn.add(Integer.valueOf(result));
    return this.SUCCESS;
  }
}