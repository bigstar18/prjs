package gnnt.MEBS.timebargain.mgr.beanforajax;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import gnnt.MEBS.timebargain.mgr.model.Orders;
import net.sf.json.JSONArray;

@Controller("ajaxOrder")
@Scope("request")
public class AjaxOrder extends AjaxCheckDemo {
	public String searchForceCloseMoney() {
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

		String firmID = request.getParameter("firmID");
		String commodityID = request.getParameter("commodityID");
		String bS_Flag = request.getParameter("bS_Flag");
		String quantity = request.getParameter("quantity");
		String evenPrice = request.getParameter("evenPrice");
		String orderPrice = request.getParameter("price");
		String holdQty = request.getParameter("holdQty");
		String usefulFunds = request.getParameter("usefulFunds");

		Orders order = new Orders();
		order.setFirmId(firmID);
		order.setCommodityId(commodityID);
		if ((bS_Flag != null) && (!"".equals(bS_Flag))) {
			order.setBS_Flag(Short.valueOf(Short.parseShort(bS_Flag)));
		}
		if ((quantity != null) && (!"".equals(quantity))) {
			order.setQuantity(Long.valueOf(Long.parseLong(quantity)));
		}
		if ((evenPrice != null) && (!"".equals(evenPrice))) {
			order.setSpecPrice(Double.valueOf(Double.parseDouble(evenPrice)));
		}
		if ((orderPrice != null) && (!"".equals(orderPrice))) {
			order.setPrice(Double.valueOf(Double.parseDouble(orderPrice)));
		}
		double money = 0.0D;
		String sql = "select FN_T_ComputeForceCloseQty('" + order.getFirmId() + "','" + order.getCommodityId() + "'," + order.getBS_Flag() + "," + 1
				+ "," + order.getSpecPrice() + "," + order.getPrice() + ") money from dual";
		List list = getService().getListBySql(sql);
		if ((list != null) && (list.size() > 0)) {
			money = Double.parseDouble(((Map) list.get(0)).get("MONEY").toString());
		}
		String result = "";
		long compareQuantity = 0L;
		long relHoldQty = 0L;
		double relUsefulFunds = 0.0D;
		if ((holdQty != null) && (!"".equals(holdQty))) {
			relHoldQty = Long.parseLong(holdQty);
		}
		if ((usefulFunds != null) && (!"".equals(usefulFunds))) {
			relUsefulFunds = Double.parseDouble(usefulFunds);
		}
		if ((money > 0.0D) && (relUsefulFunds < 0.0D)) {
			compareQuantity = (long) Math.ceil(relUsefulFunds * -1.0D / money);
		}
		if (compareQuantity > relHoldQty)
			result = relHoldQty + "";
		else {
			result = compareQuantity + "";
		}
		this.jsonValidateReturn = new JSONArray();
		this.jsonValidateReturn.add(result);
		return this.SUCCESS;
	}
}