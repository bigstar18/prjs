package gnnt.MEBS.integrated.mgr.action.usernamage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.ErrorLoginLog;
import gnnt.MEBS.integrated.mgr.service.writeopreatelog.WriteOperateLogService;

@Controller("errorLoginLogAction")
@Scope("request")
public class ErrorLoginLogAction extends EcsideAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("writeOperateLogService")
	private WriteOperateLogService writeOperateLogService;

	public String getList() throws Exception {
		List localList1 = getService().getListBySql("select * from l_dictionary l where l.key='WarehouseMaxErrorLogonTimes'");
		int i = Tools.strToInt(((Map) localList1.get(0)).get("VALUE") + "", -1000);
		List localList2 = getService().getListBySql("select trunc(sysdate) CRTDATE from dual");
		Date localDate = (Date) ((Map) localList2.get(0)).get("CRTDATE");
		PageRequest localPageRequest = getPageRequest(this.request);
		QueryConditions localQueryConditions = getQueryConditionsFromRequest(this.request);
		localQueryConditions.addCondition("primary.counts", ">=", Integer.valueOf(i));
		localQueryConditions.addCondition("primary.loginDate", "=", localDate);
		localPageRequest.setFilters(localQueryConditions);
		localPageRequest.setSortColumns(" order by primary.userID");
		listByLimit(localPageRequest);
		return "success";
	}

	public String getDetail() throws Exception {
		String str = this.request.getParameter("userId");
		PageRequest localPageRequest = new PageRequest(" and primary.userID='" + str + "'");
		localPageRequest.setSortColumns(" order by primary.userID");
		Page localPage = getService().getPage(localPageRequest, new ErrorLoginLog());
		this.request.setAttribute("pageInfo", localPage);
		return "success";
	}

	public String deleteTraders() {
		String[] arrayOfString = this.request.getParameterValues("ids");
		String str1 = "";
		for (String str2 : arrayOfString) {
			if (str1.trim().length() > 0) {
				str1 = str1 + ",";
			}
			str1 = str1 + "'" + str2 + "'";
		}
		getService().executeUpdateBySql("delete from W_ErrorLoginLog where userId in (" + str1 + ")");
		addReturnValue(1, 121005L, new Object[] { str1.replaceAll("'", "") });
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		this.writeOperateLogService.writeOperateLog(1202, (User) user, "用户" + str1.replaceAll("'", "") + "解锁成功", 1, "");
		return "success";
	}

	public String deleteAllActive() {
		getService().executeUpdateBySql("delete from W_ErrorLoginLog ");
		addReturnValue(1, 121006L);
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.writeOperateLogService.writeOperateLog(1202, localUser, "清空所有登陆异常记录", 1, "");
		return "success";
	}
}
