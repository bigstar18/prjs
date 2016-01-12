package gnnt.MEBS.broker.mgr.beanforajax;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.broker.mgr.model.MFirm;
import gnnt.MEBS.broker.mgr.model.brokerManagement.Broker;
import gnnt.MEBS.broker.mgr.model.brokerManagement.Firm;
import gnnt.MEBS.common.mgr.beanforajax.BaseAjax;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;

@Controller("ajaxCheckBroker")
@Scope("request")
public class AjaxCheckBroker extends BaseAjax {
	private boolean existFirmFirmId(String paramString) {
		boolean bool = false;
		if ((paramString == null) || (paramString.trim().length() <= 0))
			return bool;
		PageRequest localPageRequest = new PageRequest(" and primary.firmId='" + paramString + "'");
		Page localPage = getService().getPage(localPageRequest, new Firm());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
			bool = true;
		return bool;
	}

	private boolean existBrokerBrokerId(String paramString) {
		boolean bool = false;
		if ((paramString == null) || (paramString.trim().length() <= 0))
			return bool;
		PageRequest localPageRequest = new PageRequest(" and primary.brokerId='" + paramString + "'");
		Page localPage = getService().getPage(localPageRequest, new Broker());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
			bool = true;
		return bool;
	}

	public String mouseCheckFirmByFirmId() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("fieldId");
		String str2 = localHttpServletRequest.getParameter("fieldValue");
		this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(existFirmFirmId(str2)) });
		return "success";
	}

	public String mouseCheckBrokerBrokerId() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("fieldId");
		String str2 = localHttpServletRequest.getParameter("fieldValue");
		this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(!existBrokerBrokerId(str2)) });
		return "success";
	}

	public void formCheckFirmByFirmId(BaseAjax.AjaxJSONArrayResponse paramAjaxJSONArrayResponse) {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str = localHttpServletRequest.getParameter("entity.firmId");
		boolean bool = existFirmFirmId(str);
		if (!bool)
			paramAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "firmId", Boolean.valueOf(false), "交易商不存在" }) });
	}

	public String formCheckFirmByFirmId() {
		BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		formCheckFirmByFirmId(localAjaxJSONArrayResponse);
		if (localAjaxJSONArrayResponse.size() <= 0)
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "true" }) });
		this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		return "success";
	}

	public void formCheckBrokerBrokerId(BaseAjax.AjaxJSONArrayResponse paramAjaxJSONArrayResponse) {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("entity.brokerId");
		String str2 = localHttpServletRequest.getParameter("entity.firmId");
		boolean bool1 = existBrokerBrokerId(str1);
		boolean bool2 = existFirmFirmId(str2);
		if (bool1)
			paramAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "brokerId", Boolean.valueOf(false), "此加盟商已存在" }) });
		if (!bool2)
			paramAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "firmId", Boolean.valueOf(false), "此交易商不存在" }) });
	}

	public String formCheckBrokerBrokerId() {
		BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		formCheckBrokerBrokerId(localAjaxJSONArrayResponse);
		if (localAjaxJSONArrayResponse.size() <= 0)
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "true" }) });
		this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		return "success";
	}

	public String getfirmListJson() {
		this.logger.debug("------getfirmListJson------查询交易商代码，将其封装成 json 串返回---------");
		HttpServletRequest localHttpServletRequest = getRequest();
		String str = localHttpServletRequest.getParameter("firmId");
		QueryConditions localQueryConditions = new QueryConditions();
		if ((str != null) && (str.trim().length() > 0))
			localQueryConditions.addCondition("firmId", "like", str);
		PageRequest localPageRequest = new PageRequest(1, 100000, localQueryConditions, " order by primary.firmId ");
		Page localPage = getService().getPage(localPageRequest, new MFirm());
		if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
			Iterator localIterator = localPage.getResult().iterator();
			while (localIterator.hasNext()) {
				StandardModel localStandardModel = (StandardModel) localIterator.next();
				MFirm localMFirm = (MFirm) localStandardModel;
				localAjaxJSONArrayResponse.addJSON(new Object[] { localMFirm.getFirmId() });
			}
			this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		}
		return "success";
	}

	public String getCMDByModuleID() {
		try {
			this.logger.debug("------ajax--getCMDByModuleID根据模块号查询该模块下的商品---------");
			HttpServletRequest localHttpServletRequest = getRequest();
			String str1 = localHttpServletRequest.getParameter("moduleId").trim();
			String str2 = "";
			if (str1.equals("18"))
				str2 = "select t.commodityid,t.name from k_commodity t";
			else
				str2 = "select t.commodityid,t.name from t_commodity t";
			List localList = getService().getListBySql(str2);
			this.jsonValidateReturn = createJSONArray(new Object[] { localList });
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "success";
	}
}