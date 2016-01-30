package gnnt.MEBS.timebargain.mgr.action.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gnnt.MEBS.timebargain.mgr.model.tradeparams.Market;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;

public class FloatingTypeInterceptor extends AbstractInterceptor {
	private final transient Log logger = LogFactory.getLog(FloatingTypeInterceptor.class);

	@Autowired
	@Qualifier("com_tradeParamsService")
	private TradeParamsService tps;
	private Short floatingType = Short.valueOf((short) 0);

	public String intercept(ActionInvocation paramActionInvocation) throws Exception {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter FloatingTypeInterceptor's intercept");
		HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
		List localList = this.tps.executeSelect("findMarket");
		if (localList.size() > 0) {
			Market localObject = (Market) localList.get(0);
			this.floatingType = ((Market) localObject).getFloatingLossComputeType();
		}
		localHttpServletRequest.setAttribute("floatingType", this.floatingType);
		String localObject = paramActionInvocation.invoke();
		return localObject;
	}
}