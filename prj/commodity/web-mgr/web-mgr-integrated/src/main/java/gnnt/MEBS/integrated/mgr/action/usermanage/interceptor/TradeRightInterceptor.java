package gnnt.MEBS.integrated.mgr.action.usermanage.interceptor;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;

public class TradeRightInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	private final transient Log logger = LogFactory.getLog(TradeRightInterceptor.class);
	@Autowired
	@Qualifier("com_standardService")
	private StandardService standardService;

	public StandardService getStandardService() {
		return this.standardService;
	}

	public String intercept(ActionInvocation paramActionInvocation) throws Exception {
		this.logger.debug("enter   TradeRightInterceptor");
		ActionContext localActionContext = paramActionInvocation.getInvocationContext();
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) localActionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		PageRequest localPageRequest = new PageRequest(" and primary.status!='E' order by primary.firmId");
		localPageRequest.setPageSize(100000);
		Page localPage = this.standardService.getPage(localPageRequest, new MFirm());
		String str = "";
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			Iterator localObject = localPage.getResult().iterator();
			while (((Iterator) localObject).hasNext()) {
				StandardModel localStandardModel = (StandardModel) ((Iterator) localObject).next();
				MFirm localMFirm = (MFirm) localStandardModel;
				str = str + "\"" + localMFirm.getFirmId() + "\",";
			}
			str = "[" + str.substring(0, str.length() - 1) + "]";
		}
		localHttpServletRequest.setAttribute("json", str);
		Object localObject = paramActionInvocation.invoke();
		return (String) localObject;
	}
}
