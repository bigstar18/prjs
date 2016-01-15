package gnnt.MEBS.integrated.broker.beanforajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.broker.beanforajax.BaseAjax;
import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.integrated.broker.model.MFirm;

@Controller("ajaxCheckDemo")
@Scope("request")
public class AjaxCheckDemo extends BaseAjax {
	private boolean existFirmFirmId(String paramString) {
		boolean bool = false;
		if ((paramString == null) || (paramString.trim().length() <= 0))
			return bool;
		PageRequest localPageRequest = new PageRequest(" and primary.firmId='" + paramString + "'");
		Page localPage = getService().getPage(localPageRequest, new MFirm());
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

	public void formCheckFirmByFirmId(BaseAjax.AjaxJSONArrayResponse paramAjaxJSONArrayResponse) {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str = localHttpServletRequest.getParameter("entity.firm.firmId");
		boolean bool = existFirmFirmId(str);
		if (!bool)
			paramAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "firm_firmId", Boolean.valueOf(false), "交易商不存在" }) });
	}

	public String formCheckFirmByFirmId() {
		BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		if (localAjaxJSONArrayResponse.size() <= 0)
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "true" }) });
		this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		return "success";
	}
}