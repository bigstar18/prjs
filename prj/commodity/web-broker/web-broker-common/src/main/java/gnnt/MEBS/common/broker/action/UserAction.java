package gnnt.MEBS.common.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.checkLogon.vo.broker.BrokerAgeLogonResultVO;
import gnnt.MEBS.checkLogon.vo.broker.BrokerLogonResultVO;
import gnnt.MEBS.common.broker.common.ActiveUserManager;
import gnnt.MEBS.common.broker.common.Global;
import gnnt.MEBS.common.broker.common.ReturnValue;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.BrokerAgeService;
import gnnt.MEBS.common.broker.service.BrokerService;
import gnnt.MEBS.common.broker.service.StandardService;

@Controller("com_userAction")
@Scope("request")
public class UserAction extends EcsideAction {
	private static final long serialVersionUID = -5214089712349011935L;

	@Autowired
	@Qualifier("com_brokerService")
	private BrokerService brokerService;

	@Autowired
	@Qualifier("com_brokerAgeService")
	private BrokerAgeService brokerAgeService;

	public void setEntityName(String paramString) {
	}

	public StandardService getBrokerService() {
		return this.brokerService;
	}

	public StandardService getBrokerAgeService() {
		return this.brokerAgeService;
	}

	public String logon() throws Exception {
		String str1 = (String) this.request.getSession().getAttribute("RANDOMICITYNUM");
		String str2 = this.request.getParameter("randNumInput");
		String str3 = this.request.getParameter("userId");
		String str4 = this.request.getParameter("password");
		if (str2 == null) {
			addReturnValue(-1, 131201L);
			return "error";
		}
		if ((str1 == null) || ((str1 != null) && (!str1.toUpperCase().equals(str2.toUpperCase())))) {
			addReturnValue(-1, 131202L);
			return "error";
		}
		Object localObject1 = null;
		Object localObject2 = null;
		String str5 = "";
		String str6 = "";
		long l = 0L;
		String str7 = Global.getSelfLogonType();
		if ((str7 == null) || (str7.length() <= 0))
			str7 = Global.getSelfLogonType();
		BrokerLogonResultVO localBrokerLogonResultVO = ActiveUserManager.brokerLogon(str3, str4, this.request.getRemoteAddr(),
				Global.getSelfModuleID(), str7);
		l = localBrokerLogonResultVO.getSessionID();
		int i = localBrokerLogonResultVO.getResult();
		Object localObject3;
		if (i != -1) {
			ActiveUserManager.brokerLogonSuccess(str3, this.request, l, str7);
		} else {
			localObject3 = ActiveUserManager.brokerAgeLogon(str3, str4, this.request.getRemoteAddr(), Global.getSelfModuleID(), str7);
			l = ((BrokerAgeLogonResultVO) localObject3).getSessionID();
			i = ((BrokerAgeLogonResultVO) localObject3).getResult();
			if (i != -1)
				ActiveUserManager.brokerAgeLogonSuccess(str3, this.request, l, str7);
		}
		if (i == -1) {
			localObject3 = localBrokerLogonResultVO.getRecode().trim();
			if (("-1".equals(localObject3)) || ("-12".equals(localObject3)))
				addReturnValue(-1, 131203L);
			else if ("-3".equals(localObject3))
				addReturnValue(-1, 131205L);
			else if ("-4".equals(localObject3))
				addReturnValue(-1, 131204L);
			else if ("-6".equals(localObject3))
				addReturnValue(-1, 131207L);
			else
				addReturnValue(-1, 131206L, new Object[] { Long.valueOf(l) });
			return "error";
		}
		addReturnValue(1, 111201L);
		return "success";
	}

	public String updatePasswordSelfSave() {
		this.logger.debug("enter passwordSelfSave");
		String str1 = this.request.getParameter("oldPassword");
		String str2 = this.request.getParameter("password");
		int i = -1;
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		if (str2.equals(str1)) {
			addReturnValue(-1, 131301L);
			return "success";
		}
		if (localUser.getType().equals("0")) {
			Broker localObject = localUser.getBroker();
			i = ActiveUserManager.BrokerChangePassword(((Broker) localObject).getBrokerId(), str1, str2, this.request.getRemoteAddr());
		} else {
			BrokerAge localObject = localUser.getBrokerAge();
			i = ActiveUserManager.BrokerAgeChangePassword(((BrokerAge) localObject).getBrokerAgeId(), str1, str2, this.request.getRemoteAddr());
		}
		if (i != 0)
			addReturnValue(-1, 131302L);
		else
			addReturnValue(1, 111301L);
		Object localObject = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localObject != null)
			if (((ReturnValue) localObject).getResult() == 1) {
				if (localUser.getType().equals("0"))
					writeOperateLog(9901, "会员" + localUser.getBroker().getBrokerId() + "修改密码", 1, "");
				else
					writeOperateLog(9901, "居间商" + localUser.getBrokerAge().getBrokerAgeId() + "修改密码", 1, "");
			} else if (localUser.getType().equals("0"))
				writeOperateLog(9901, "会员" + localUser.getBroker().getBrokerId() + "修改密码", 0, ((ReturnValue) localObject).getInfo());
			else
				writeOperateLog(9901, "居间商" + localUser.getBrokerAge().getBrokerAgeId() + "修改密码", 0, ((ReturnValue) localObject).getInfo());
		return "success";
	}

	public String logout() throws Exception {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		if (localUser != null) {
			String str = "4".equals(localUser.getType()) ? "居间商" : "会员";
			if (localUser != null)
				writeOperateLog(9901, str + localUser.getUserId() + "退出", 1, "");
			ActiveUserManager.logoff(this.request);
		}
		this.request.getSession().invalidate();
		return "success";
	}

	public String saveShinStyle() {
		this.logger.debug("enter modShinStyle");
		return "success";
	}
}