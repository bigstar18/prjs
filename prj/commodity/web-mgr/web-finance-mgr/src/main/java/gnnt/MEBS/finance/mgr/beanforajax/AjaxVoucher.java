package gnnt.MEBS.finance.mgr.beanforajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.beanforajax.BaseAjax;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.finance.mgr.model.Firm;

@Controller("ajaxVoucher")
@Scope("request")
public class AjaxVoucher extends BaseAjax {
	private boolean existFirm(String paramString) {
		boolean bool = false;
		if ((paramString == null) || (paramString.trim().length() <= 0))
			return bool;
		PageRequest localPageRequest = new PageRequest(" and primary.firmID='" + paramString + "'");
		Page localPage = getService().getPage(localPageRequest, new Firm());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
			bool = true;
		return bool;
	}

	private int checkFirm(String paramString) {
		int i = 0;
		PageRequest localPageRequest = new PageRequest(" and primary.firmID='" + paramString + "'");
		Page localPage = getService().getPage(localPageRequest, new Firm());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			Firm localFirm = (Firm) localPage.getResult().get(0);
			if (localFirm.getStatus().equals("D"))
				i = 2;
			else if (localFirm.getStatus().equals("E"))
				i = 3;
		}
		return i;
	}

	public String mouseCheckFirmByFirmId() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("fieldId");
		String str2 = localHttpServletRequest.getParameter("fieldValue");
		this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(existFirm(str2)) });
		return "success";
	}

	public void formCheckFirmByFirmId(BaseAjax.AjaxJSONArrayResponse paramAjaxJSONArrayResponse) {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str = localHttpServletRequest.getParameter("firmId");
		boolean bool = existFirm(str);
		if (!bool) {
			paramAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "firmId", Boolean.valueOf(false), "此交易商不存在" }) });
		} else {
			int i = checkFirm(str);
			if (i == 2)
				paramAjaxJSONArrayResponse
						.addJSON(new Object[] { createJSONArray(new Object[] { "firmId", Boolean.valueOf(false), "此交易商已冻结，请重新输入！" }) });
			else if (i == 3)
				paramAjaxJSONArrayResponse
						.addJSON(new Object[] { createJSONArray(new Object[] { "firmId", Boolean.valueOf(false), "此交易商已注销，请重新输入！" }) });
		}
	}

	public String formCheckFirmByFirmId() {
		BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		formCheckFirmByFirmId(localAjaxJSONArrayResponse);
		if (localAjaxJSONArrayResponse.size() <= 0)
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "true" }) });
		this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		return "success";
	}
}