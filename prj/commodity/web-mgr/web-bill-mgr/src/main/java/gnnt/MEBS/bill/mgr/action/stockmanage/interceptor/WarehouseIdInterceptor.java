package gnnt.MEBS.bill.mgr.action.stockmanage.interceptor;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gnnt.MEBS.bill.mgr.model.stockmanage.Warehouse;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;

public class WarehouseIdInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	private final transient Log logger = LogFactory.getLog(WarehouseIdInterceptor.class);
	@Autowired
	@Qualifier("standardService")
	private StandardService standardService;

	public StandardService getStandardService() {
		return this.standardService;
	}

	public String intercept(ActionInvocation paramActionInvocation) throws Exception {
		this.logger.debug("enter   WarehouseIdInterceptor");
		ActionContext localActionContext = paramActionInvocation.getInvocationContext();
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) localActionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		PageRequest localPageRequest = new PageRequest(" order by primary.id");
		localPageRequest.setPageSize(100000);
		Page localPage = this.standardService.getPage(localPageRequest, new Warehouse());
		String str = "";
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			Iterator localObject = localPage.getResult().iterator();
			while (((Iterator) localObject).hasNext()) {
				StandardModel localStandardModel = (StandardModel) ((Iterator) localObject).next();
				Warehouse localWarehouse = (Warehouse) localStandardModel;
				str = str + "\"" + localWarehouse.getWarehouseId() + "-" + localWarehouse.getWarehouseName() + "\",";
			}
			str = "[" + str.substring(0, str.length() - 1) + "]";
		}
		localHttpServletRequest.setAttribute("warehouseJson", str);
		Object localObject = paramActionInvocation.invoke();
		return (String) localObject;
	}
}
