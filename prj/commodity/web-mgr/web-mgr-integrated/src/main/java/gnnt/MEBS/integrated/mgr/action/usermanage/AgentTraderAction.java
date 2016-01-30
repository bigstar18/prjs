package gnnt.MEBS.integrated.mgr.action.usermanage;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.AgentTrader;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;

@Controller("agentTraderAction")
@Scope("request")
public class AgentTraderAction extends EcsideAction {
	private static final long serialVersionUID = -4628426875505296088L;
	@Autowired
	@Resource(name = "agentTraderStatusMap")
	protected Map<Integer, String> agentTraderStatusMap;
	@Resource(name = "agentTraderTypeMap")
	protected Map<Integer, String> agentTraderTypeMap;

	public Map<Integer, String> getAgentTraderStatusMap() {
		return this.agentTraderStatusMap;
	}

	public Map<Integer, String> getAgentTraderTypeMap() {
		return this.agentTraderTypeMap;
	}

	public String agentTraderList() throws Exception {
		PageRequest localPageRequest = super.getPageRequest(this.request);
		QueryConditions localQueryConditions = (QueryConditions) localPageRequest.getFilters();
		String str1 = ApplicationContextInit.getConfig("unListAgentTrader");
		if ((str1 != null) && (str1.trim().length() > 0)) {
			String[] arrayOfString1 = str1.split(",");
			String str2 = "";
			for (String str3 : arrayOfString1) {
				if ((str3 != null) && (str3.trim().length() > 0)) {
					if (str2.trim().length() > 0) {
						str2 = str2 + ",";
					}
					str2 = str2 + "'" + str3.trim() + "'";
				}
			}
			if (str2.trim().length() > 0) {
				localQueryConditions.addCondition(" ", " ", "agentTraderId not in (" + str2 + ")");
			}
		}
		listByLimit(localPageRequest);
		return "success";
	}

	public String addAgentTrader() {
		this.logger.debug("enter addAgentTrader");
		AgentTrader localAgentTrader = (AgentTrader) this.entity;
		localAgentTrader.setPassword(MD5.getMD5(localAgentTrader.getAgentTraderId(), ((AgentTrader) this.entity).getPassword()));
		getService().add(localAgentTrader);
		addReturnValue(1, 111502L, new Object[] { localAgentTrader.getAgentTraderId() });
		return "success";
	}

	public String updateAgentTraderForward() {
		entity = getService().get(entity);
		AgentTrader agenttrader = (AgentTrader) entity.clone();
		request.setAttribute("entity", entity);
		if (agenttrader != null)
			if (agenttrader.getOperateFirm() != null && agenttrader.getOperateFirm().trim().length() > 0) {
				String as[] = agenttrader.getOperateFirm().split(",");
				String s = "";
				String as1[] = as;
				int i = as1.length;
				for (int j = 0; j < i; j++) {
					String s1 = as1[j];
					if (s1.trim().length() <= 0)
						continue;
					if (s.length() > 0)
						s = (new StringBuilder()).append(s).append(",").toString();
					s = (new StringBuilder()).append(s).append("'").append(s1).append("'").toString();
				}

				QueryConditions queryconditions1 = new QueryConditions();
				queryconditions1.addCondition("firmId", "in", (new StringBuilder()).append("(").append(s).append(")").toString());
				queryconditions1.addCondition("status", "in", "('N')");
				PageRequest pagerequest1 = new PageRequest(1, 0x186a0, queryconditions1, " order by firmId ");
				Page page1 = getService().getPage(pagerequest1, new MFirm());
				request.setAttribute("operateFirm", page1.getResult());
				QueryConditions queryconditions2 = new QueryConditions();
				queryconditions2.addCondition("firmId", "not in", (new StringBuilder()).append("(").append(s).append(")").toString());
				queryconditions2.addCondition("status", "in", "('N')");
				PageRequest pagerequest2 = new PageRequest(1, 0x186a0, queryconditions2, " order by firmId ");
				Page page2 = getService().getPage(pagerequest2, new MFirm());
				request.setAttribute("unoperateFirm", page2.getResult());
			} else {
				QueryConditions queryconditions = new QueryConditions();
				queryconditions.addCondition("status", "in", "('N')");
				PageRequest pagerequest = new PageRequest(1, 0x186a0, queryconditions, " order by firmId ");
				Page page = getService().getPage(pagerequest, new MFirm());
				request.setAttribute("unoperateFirm", page.getResult());
			}
		return "success";
	}

	public String updateAgentTrader() {
		AgentTrader localAgentTrader = (AgentTrader) this.entity;
		try {
			localAgentTrader.setModifyTime(getService().getSysDate());
		} catch (Exception localException) {
			this.logger.debug(Tools.getExceptionTrace(localException));
		}
		getService().update(localAgentTrader);
		addReturnValue(1, 119902L, new Object[] { localAgentTrader.getAgentTraderId() });
		return "success";
	}

	public String updatePassword() {
		String str1 = "修改代为交易员密码";
		this.logger.debug(str1);
		this.logger.debug("enter passwordSelfSave");
		String str2 = this.request.getParameter("password");
		AgentTrader localAgentTrader = (AgentTrader) this.entity;
		localAgentTrader.setPassword(MD5.getMD5(localAgentTrader.getAgentTraderId(), str2));
		getService().update(localAgentTrader);
		addReturnValue(1, 111501L, new Object[] { localAgentTrader.getAgentTraderId() });
		return "success";
	}

	public String deleteAgentTrader() throws SecurityException, NoSuchFieldException {
		this.logger.debug("enter deleteAgentTrader");
		String[] arrayOfString = this.request.getParameterValues("ids");
		System.out.println("ids: " + arrayOfString[0]);
		if (arrayOfString.length <= 1) {
			AgentTrader localAgentTrader = new AgentTrader();
			localAgentTrader.setAgentTraderId(arrayOfString[0]);
			localAgentTrader = (AgentTrader) getService().get(localAgentTrader);
			getService().delete(localAgentTrader);
			addReturnValue(1, 111503L, new Object[] { arrayOfString[0] });
		} else {
			delete();
		}
		return "success";
	}
}
