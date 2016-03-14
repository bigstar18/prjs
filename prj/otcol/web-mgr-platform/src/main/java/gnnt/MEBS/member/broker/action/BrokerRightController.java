package gnnt.MEBS.member.broker.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.model.BrokerRight;
import gnnt.MEBS.member.broker.services.BrokerRightService;
import gnnt.MEBS.member.broker.util.SysData;

public class BrokerRightController extends BaseController {
	private final transient Log logger = LogFactory.getLog(BrokerRightController.class);

	public ModelAndView brokerRightList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		this.logger.debug("action into brokerRightList~~~~");
		String str1 = paramHttpServletRequest.getParameter("brokerid");
		if (str1 != null) {
			paramHttpServletRequest.setAttribute("brokerid", str1);
		}
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("brokerid", "=", str1);
		BrokerRightService localBrokerRightService = (BrokerRightService) SysData.getBean("m_brokerRightService");
		List localList1 = localBrokerRightService.getBrokerMenu();
		List localList2 = localBrokerRightService.getBrokerRight(localQueryConditions);
		String str2 = "";
		Iterator localObject = localList2.iterator();
		while (((Iterator) localObject).hasNext()) {
			BrokerRight localBrokerRight = (BrokerRight) ((Iterator) localObject).next();
			str2 = str2 + localBrokerRight.getRightId() + ",";
		}

		return (new ModelAndView("member/broker/brokerDetail/setBrokerRight", "resultList", localList1)).addObject("rights", str2);
	}

	public ModelAndView saveBrokerRight(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		String str1 = paramHttpServletRequest.getParameter("brokerid");
		String str2 = paramHttpServletRequest.getParameter("rights");
		int[] arrayOfInt = null;
		String str3 = "";
		try {
			BrokerRightService localBrokerRightService = (BrokerRightService) SysData.getBean("m_brokerRightService");
			if ((str2 != null) && (!"".equals(str2))) {
				String[] arrayOfString = str2.split(",");
				arrayOfInt = new int[arrayOfString.length];
				for (int i = 0; i < arrayOfString.length; i++) {
					arrayOfInt[i] = Integer.parseInt(arrayOfString[i]);
				}
			}
			localBrokerRightService.saveBrokerRight(str1, arrayOfInt);
			str3 = "保存加盟商[" + str1 + "]权限成功!";
		} catch (Exception localException) {
			localException.printStackTrace();
			str3 = "处理异常！";
		}
		ModelAndView localModelAndView = new ModelAndView("member/broker/brokerDetail/setBrokerRight");
		localModelAndView.addObject("resultMsg", str3);
		localModelAndView.addObject("modSuccess", "modSuccess!");
		return localModelAndView;
	}
}
