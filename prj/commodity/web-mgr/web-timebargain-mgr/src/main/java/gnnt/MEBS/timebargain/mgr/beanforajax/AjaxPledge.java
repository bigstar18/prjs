package gnnt.MEBS.timebargain.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxPledge")
@Scope("request")
public class AjaxPledge extends AjaxCheckDemo
{

  @Autowired
  @Qualifier("billKernelService")
  private IKernelService kernelService;

  public IKernelService getKernelService()
  {
    return this.kernelService;
  }

  public void setKernelService(IKernelService kernelService)
  {
    this.kernelService = kernelService;
  }

  public String getBill()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String firmid = request.getParameter("firmid");
    String result = "";

    List billIdList = this.kernelService.getUnusedStocks(15, firmid);

    if ((billIdList != null) && (billIdList.size() > 0)) {
      for (int i = 0; i < billIdList.size(); i++) {
        this.jsonValidateReturn.add(billIdList.get(i));
      }
    }

    return this.SUCCESS;
  }

  public String getReBill()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String sql = "select BillID from T_E_Pledge where Status = 1 and type=0 and BillID not in (select BillID from T_E_Pledge where Status = 0 and type = 1)";
    List billIdList = getService().getListBySql(sql);

    if ((billIdList != null) && (billIdList.size() > 0)) {
      for (int i = 0; i < billIdList.size(); i++) {
        this.jsonValidateReturn.add(((Map)billIdList.get(i)).get("BILLID"));
      }
    }

    return this.SUCCESS;
  }

  public String getBillMessage()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String stockid = request.getParameter("billid");
    String sql = "select s.WarehouseID,b.breedname,s.Quantity,s.unit from bi_stock s,M_Breed b where s.breedid = b.breedid and s.stockid = '" + stockid + "'";
    List billIdList = getService().getListBySql(sql);
    if ((billIdList != null) && (billIdList.size() > 0)) {
      String warehouseID = ((Map)billIdList.get(0)).get("WAREHOUSEID").toString();
      String breedname = ((Map)billIdList.get(0)).get("BREEDNAME").toString();
      String quantity = ((Map)billIdList.get(0)).get("QUANTITY").toString();
      String unit = ((Map)billIdList.get(0)).get("UNIT").toString();
      this.jsonValidateReturn.add(warehouseID);
      this.jsonValidateReturn.add(breedname);
      this.jsonValidateReturn.add(quantity);
      this.jsonValidateReturn.add(unit);
    }

    return this.SUCCESS;
  }

  public String getReBillMessage()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    this.jsonValidateReturn = new JSONArray();

    String stockid = request.getParameter("billid");
    String sql = "select p.FirmID,p.BreedName,s.warehouseid,p.quantity,s.unit,p.billfund from T_E_Pledge p,bi_stock s where p.billid = s.stockid and p.billid = '" + stockid + "' and p.type = 0 and p.status = 1";
    List billIdList = getService().getListBySql(sql);
    if ((billIdList != null) && (billIdList.size() > 0)) {
      String firmid = ((Map)billIdList.get(0)).get("FIRMID").toString();
      String warehouseID = ((Map)billIdList.get(0)).get("WAREHOUSEID").toString();
      String breedname = ((Map)billIdList.get(0)).get("BREEDNAME").toString();
      String quantity = ((Map)billIdList.get(0)).get("QUANTITY").toString();
      String unit = ((Map)billIdList.get(0)).get("UNIT").toString();
      String billfund = ((Map)billIdList.get(0)).get("BILLFUND").toString();
      this.jsonValidateReturn.add(firmid);
      this.jsonValidateReturn.add(warehouseID);
      this.jsonValidateReturn.add(breedname);
      this.jsonValidateReturn.add(quantity);
      this.jsonValidateReturn.add(billfund);
      this.jsonValidateReturn.add(unit);
    }

    return this.SUCCESS;
  }
}