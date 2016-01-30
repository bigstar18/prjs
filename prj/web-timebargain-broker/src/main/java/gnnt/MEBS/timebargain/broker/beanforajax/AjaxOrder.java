package gnnt.MEBS.timebargain.broker.beanforajax;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import gnnt.MEBS.timebargain.broker.model.Orders;
import net.sf.json.JSONArray;

@Controller("ajaxOrder")
@Scope("request")
public class AjaxOrder extends AjaxCheckDemo {
	public String searchForceCloseMoney() {
		ActionContext localActionContext = ActionContext.getContext();
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) localActionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String str1 = localHttpServletRequest.getParameter("firmID");
		String str2 = localHttpServletRequest.getParameter("commodityID");
		String str3 = localHttpServletRequest.getParameter("bS_Flag");
		String str4 = localHttpServletRequest.getParameter("quantity");
		String str5 = localHttpServletRequest.getParameter("evenPrice");
		String str6 = localHttpServletRequest.getParameter("price");
		String str7 = localHttpServletRequest.getParameter("holdQty");
		String str8 = localHttpServletRequest.getParameter("usefulFunds");
		Orders localOrders = new Orders();
		localOrders.setFirmId(str1);
		localOrders.setCommodityId(str2);
		if ((str3 != null) && (!"".equals(str3)))
			localOrders.setBS_Flag(Short.valueOf(Short.parseShort(str3)));
		if ((str4 != null) && (!"".equals(str4)))
			localOrders.setQuantity(Long.valueOf(Long.parseLong(str4)));
		if ((str5 != null) && (!"".equals(str5)))
			localOrders.setSpecPrice(Double.valueOf(Double.parseDouble(str5)));
		if ((str6 != null) && (!"".equals(str6)))
			localOrders.setPrice(Double.valueOf(Double.parseDouble(str6)));
		double d1 = 0.0D;
		String str9 = "select FN_T_ComputeForceCloseQty('" + localOrders.getFirmId() + "','" + localOrders.getCommodityId() + "',"
				+ localOrders.getBS_Flag() + "," + 1 + "," + localOrders.getSpecPrice() + "," + localOrders.getPrice() + ") money from dual";
		List localList = getService().getListBySql(str9);
		if ((localList != null) && (localList.size() > 0))
			d1 = Double.parseDouble(((Map) localList.get(0)).get("MONEY").toString());
		String str10 = "";
		long l1 = 0L;
		long l2 = 0L;
		double d2 = 0.0D;
		if ((str7 != null) && (!"".equals(str7)))
			l2 = Long.parseLong(str7);
		if ((str8 != null) && (!"".equals(str8)))
			d2 = Double.parseDouble(str8);
		if ((d1 > 0.0D) && (d2 < 0.0D))
			l1 = (long) Math.ceil(d2 * -1.0D / d1);
		if (l1 > l2)
			str10 = l2 + "";
		else
			str10 = l1 + "";
		this.jsonValidateReturn = new JSONArray();
		this.jsonValidateReturn.add(str10);
		return "success";
	}
}