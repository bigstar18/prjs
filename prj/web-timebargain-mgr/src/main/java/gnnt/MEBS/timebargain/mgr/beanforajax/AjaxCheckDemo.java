package gnnt.MEBS.timebargain.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Breed;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Commodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.TradeTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxCheckDemo")
@Scope("request")
public class AjaxCheckDemo
{
  protected String SUCCESS = "success";

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;
  protected JSONArray jsonValidateReturn;
  private String opr;

  public StandardService getService()
  {
    return this.standardService;
  }

  public String getOpr()
  {
    return this.opr;
  }

  public JSONArray getJsonValidateReturn()
  {
    return this.jsonValidateReturn;
  }

  private JSONArray getJSONArray(Object[] values)
  {
    JSONArray result = new JSONArray();
    for (Object value : values) {
      result.add(value);
    }
    return result;
  }

  private JSONArray getJSONArrayList(JSONArray[] values)
  {
    JSONArray result = new JSONArray();
    for (Object value : values) {
      result.add(value);
    }
    return result;
  }

  private boolean existModelId(Object value, StandardModel model)
  {
    boolean result = false;

    List page = getService().getListByBulk(model, new Object[] { value });
    if ((page != null) && (page.size() > 0)) {
      result = true;
    }
    return result;
  }

  public String formCheckBreedById() {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    Long breedID = new Long(request.getParameter("entity.breedID"));
    Breed breed = new Breed();
    breed.setBreedID(breedID);
    boolean flag = existModelId(breedID, breed);
    if (!flag)
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    else {
      this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "breedID", Boolean.valueOf(false), "该品种代码已存在" }) });
    }
    return this.SUCCESS;
  }

  public String formCheckCommodityById()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    Map mgs = new HashMap();
    mgs.put("opr", "0");
    String commodityID = request.getParameter("commodityID");
    PageRequest pageRequest = new PageRequest(" and primary.commodityID='" + commodityID + "'");
    Page page = getService().getPage(pageRequest, new Commodity());

    if (page.getResult().size() > 0) {
      mgs.put("opr", "1");
    }
    this.opr = JSONObject.fromObject(mgs).toString();
    return this.SUCCESS;
  }

  public String formCheckTradeTimeById()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    Integer sectionID = new Integer(request.getParameter("entity.sectionID"));
    TradeTime tt = new TradeTime();
    tt.setSectionID(sectionID);
    boolean flag = existModelId(sectionID, tt);
    if (!flag)
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    else {
      this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "sectionID", Boolean.valueOf(false), "该交易节编号已存在" }) });
    }
    return this.SUCCESS;
  }
}