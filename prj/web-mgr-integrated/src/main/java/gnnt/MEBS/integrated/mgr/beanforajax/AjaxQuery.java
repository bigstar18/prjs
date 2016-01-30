package gnnt.MEBS.integrated.mgr.beanforajax;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.beanforajax.BaseAjax;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmModule;
import net.sf.json.JSONArray;

@Controller("ajaxQuery")
@Scope("request")
public class AjaxQuery extends BaseAjax {
	public String getfirmListJson() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("firmId");
		int i = Tools.strToInt(localHttpServletRequest.getParameter("type"), -1);
		String str2 = localHttpServletRequest.getParameter("status");
		QueryConditions localQueryConditions = new QueryConditions();
		if ((str1 != null) && (str1.trim().length() > 0)) {
			localQueryConditions.addCondition("firmId", "like", str1);
		}
		if (i >= 0) {
			localQueryConditions.addCondition("type", "=", Integer.valueOf(i));
		}
		if ((str2 != null) && (str2.trim().length() > 0)) {
			localQueryConditions.addCondition("status", "=", str2);
		}
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

	public String getfirmModuleIdJson() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str = localHttpServletRequest.getParameter("firmId");
		MFirm localMFirm = new MFirm();
		localMFirm.setFirmId(str);
		localMFirm = (MFirm) getService().get(localMFirm);
		if ((localMFirm != null) && ("N".equalsIgnoreCase(localMFirm.getStatus()))) {
			QueryConditions localQueryConditions = new QueryConditions();
			localQueryConditions.addCondition("firmId", "=", str);
			localQueryConditions.addCondition("enabled", "=", "Y");
			PageRequest localPageRequest = new PageRequest(1, 100, localQueryConditions, " order by primary.moduleId ");
			Page localPage = getService().getPage(localPageRequest, new MFirmModule());
			BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
			if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0) && (Global.modelContextMap != null)
					&& (Global.modelContextMap.size() > 0)) {
				Set localSet = Global.modelContextMap.keySet();
				Iterator localIterator1 = localSet.iterator();
				while (localIterator1.hasNext()) {
					Integer localInteger = (Integer) localIterator1.next();
					Iterator localIterator2 = localPage.getResult().iterator();
					while (localIterator2.hasNext()) {
						StandardModel localStandardModel = (StandardModel) localIterator2.next();
						if (localInteger.equals(((MFirmModule) localStandardModel).getModuleId())) {
							localAjaxJSONArrayResponse.addJSON(new Object[] { ((MFirmModule) localStandardModel).getModuleId() });
							break;
						}
					}
				}
			}
			this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		} else {
			this.jsonValidateReturn = new JSONArray();
		}
		return "success";
	}
}
