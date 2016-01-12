package gnnt.MEBS.timebargain.mgr.beanforajax.agency;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.beanforajax.AjaxCheckDemo;
import gnnt.MEBS.timebargain.mgr.model.printReport.FirmModel;
import gnnt.MEBS.timebargain.mgr.model.systemMana.CommodityModel;
import gnnt.MEBS.timebargain.mgr.model.systemMana.FirmMargin;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxAgency")
@Scope("request")
public class AjaxAgency extends AjaxCheckDemo
{
  private static final int DEF_DIV_SCALE = 10;
  private static final int RESULT_DIV_SCALE = 10;

  private static BigDecimal result(BigDecimal b)
  {
    return b.setScale(10, 4);
  }

  private boolean existModelId(Object value, StandardModel model) {
    boolean result = false;

    List page = getService().getListByBulk(model, 
      new Object[] { value });
    if ((page != null) && (page.size() > 0)) {
      result = true;
    }
    return result;
  }

  public String getCommodity()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String comStr = "";
    CommodityModel commodity = new CommodityModel();
    String id = request.getParameter("id");
    commodity.setCommodityId(id);
    commodity = (CommodityModel)getService().get(commodity);
    String marginAlgr = "";
    if ((commodity != null) && (commodity.getMarginAlgr() != null)) {
      marginAlgr = commodity.getMarginAlgr().toString();
    }
    if (marginAlgr.equals("1")) {
      comStr = comStr + mul((commodity.getMarginRate_B() == null ? new Double(0.0D) : 
        commodity.getMarginRate_B()).doubleValue(), 100.0F) + 
        ",";
      comStr = comStr + mul((commodity.getMarginRate_S() == null ? new Double(0.0D) : 
        commodity.getMarginRate_S()).doubleValue(), 100.0F) + 
        ",";
      comStr = comStr + mul((commodity.getMarginAssure_B() == null ? new Double(0.0D) : 
        commodity.getMarginAssure_B()).doubleValue(), 100.0F) + 
        ",";
      comStr = comStr + mul((commodity.getMarginAssure_S() == null ? new Double(0.0D) : 
        commodity.getMarginAssure_S()).doubleValue(), 100.0F) + 
        ",";
      comStr = comStr + commodity.getMarginAlgr() + ",";
    } else {
      comStr = comStr + commodity.getMarginRate_B() + ",";
      comStr = comStr + commodity.getMarginRate_S() + ",";
      comStr = comStr + commodity.getMarginAssure_B() + ",";
      comStr = comStr + commodity.getMarginAssure_S() + ",";
      comStr = comStr + commodity.getMarginAlgr() + ",";
    }
    comStr = comStr + commodity.getLastPrice();
    this.jsonValidateReturn = new JSONArray();
    this.jsonValidateReturn.add(comStr);
    return this.SUCCESS;
  }

  public String getFirmMargin()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String id = request.getParameter("id");
    FirmMargin firmMargin = new FirmMargin();
    firmMargin.setCommodityId(id);
    firmMargin = (FirmMargin)getService().get(firmMargin);
    String marginAlgr = "";
    if ((firmMargin != null) && (firmMargin.getMarginAlgr() != null)) {
      marginAlgr = firmMargin.getMarginAlgr().toString();
    }
    String comStr = "";
    if (marginAlgr.equals("1")) {
      comStr = comStr + mul((firmMargin.getMarginRate_B() == null ? new Double(0.0D) : 
        firmMargin.getMarginRate_B()).doubleValue(), 100.0F) + 
        ",";
      comStr = comStr + mul((firmMargin.getMarginRate_S() == null ? new Double(0.0D) : 
        firmMargin.getMarginRate_S()).doubleValue(), 100.0F) + 
        ",";
      comStr = comStr + mul(
        (firmMargin.getMarginAssure_B() == null ? new Double(0.0D) : 
        firmMargin.getMarginAssure_B()).doubleValue(), 
        100.0F) + 
        ",";
      comStr = comStr + mul(
        (firmMargin.getMarginAssure_S() == null ? new Double(0.0D) : 
        firmMargin.getMarginAssure_S()).doubleValue(), 
        100.0F) + 
        ",";
      comStr = comStr + firmMargin.getMarginAlgr();
    } else {
      comStr = comStr + firmMargin.getMarginRate_B() + ",";
      comStr = comStr + firmMargin.getMarginRate_S() + ",";
      comStr = comStr + firmMargin.getMarginAssure_B() + ",";
      comStr = comStr + firmMargin.getMarginAssure_S() + ",";
      comStr = comStr + firmMargin.getMarginAlgr();
    }

    this.jsonValidateReturn = new JSONArray();
    this.jsonValidateReturn.add(comStr);
    return this.SUCCESS;
  }

  public static double mul(double v1, float v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);

    return result(result(b1.multiply(b2))).doubleValue();
  }

  public String checkFirmId()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String firmId = request.getParameter("firmId");
    FirmModel firm = new FirmModel();
    firm.setFirmId(firmId);
    boolean flag = existModelId(firmId, firm);
    if (!flag) {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("false");
    } else {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return this.SUCCESS;
  }
}