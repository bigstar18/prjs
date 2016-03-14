package gnnt.MEBS.delivery.action.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import gnnt.MEBS.base.query.OrderField;
import gnnt.MEBS.base.query.PageInfo;

public class ReturnCurrentPage extends HandlerInterceptorAdapter {
	private final transient Log logger = LogFactory.getLog(ReturnCurrentPage.class);

	public boolean preHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject)
			throws Exception {
		if (paramHttpServletRequest.getParameter("return") != null) {
			this.logger.debug("__________________");
			HttpSession localHttpSession = paramHttpServletRequest.getSession();
			PageInfo localPageInfo = (PageInfo) localHttpSession.getAttribute("pi");
			Map localMap = (Map) localHttpSession.getAttribute("op");

			if (localMap != null) {
				Iterator localObject1 = localMap.keySet().iterator();
				while (((Iterator) localObject1).hasNext()) {
					Object localObject2 = ((Iterator) localObject1).next();
					this.logger.debug("--:" + localObject2.toString() + ";" + localMap.get(localObject2).toString());
					paramHttpServletRequest.setAttribute("_" + localObject2.toString(), localMap.get(localObject2).toString());
				}
			}
			if (localPageInfo != null) {
				this.logger.debug("pageSize:" + localPageInfo.getPageSize());
				paramHttpServletRequest.setAttribute("pageSize", localPageInfo.getPageSize() + "");
				this.logger.debug("pageNo:" + localPageInfo.getPageNo());
				paramHttpServletRequest.setAttribute("pageNo", localPageInfo.getPageNo() + "");
				List localObject1 = localPageInfo.getOrderFields();
				if ((localObject1 != null) && (((List) localObject1).size() > 0)) {
					String[] localObject2 = new String[((List) localObject1).size()];
					String[] arrayOfString = new String[((List) localObject1).size()];
					for (int i = 0; i < ((List) localObject1).size(); i++) {
						OrderField localOrderField = (OrderField) ((List) localObject1).get(i);
						localObject2[i] = localOrderField.getOrderField();
						arrayOfString[i] = (localOrderField.isOrderDesc() + "");
					}
					this.logger.debug("orderField:" + localObject2.length);
					paramHttpServletRequest.setAttribute("orderField", localObject2);
					this.logger.debug("orderType:" + arrayOfString.length);
					paramHttpServletRequest.setAttribute("orderDesc", arrayOfString);
				}
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {
		this.logger.debug(Boolean.valueOf(paramModelAndView == null));
		if (paramModelAndView != null) {
			this.logger.debug(paramModelAndView.getViewName());
			String str = paramModelAndView.getViewName();
			Map localMap = paramModelAndView.getModel();
			if ((str.indexOf("redirect:") >= 0) && (str.indexOf(".jsp") < 0) && (str.indexOf("noReturn=1") < 0)) {
				str = str + "&return=1";
				paramModelAndView.setViewName(str);
			}
			if ((str.indexOf("noReturn=1") < 0) && (!localMap.containsKey("noReturn"))) {
				Object localObject;
				if (localMap.get("pageInfo") != null) {
					localObject = (PageInfo) localMap.get("pageInfo");
					paramHttpServletRequest.getSession().setAttribute("pi", localObject);
				}
				if (localMap.get("oldParams") != null) {
					localObject = (Map) localMap.get("oldParams");
					paramHttpServletRequest.getSession().setAttribute("op", localObject);
					this.logger.debug("viewName:" + str);
					Iterator localIterator = ((Map) localObject).entrySet().iterator();
					while (localIterator.hasNext()) {
						Map.Entry localEntry = (Map.Entry) localIterator.next();
						this.logger.debug("key:" + localEntry.getKey().toString());
					}
				}
			}
		}
	}
}
