package gnnt.MEBS.timebargain.mgr.action.delay;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.timebargain.mgr.action.ECSideAction;
import gnnt.MEBS.timebargain.mgr.model.delay.Delay;
import gnnt.MEBS.timebargain.mgr.model.delay.DelayOrders;
import gnnt.MEBS.timebargain.mgr.model.delay.DelayQuotation;
import gnnt.MEBS.timebargain.mgr.model.delay.DelayStatusLocal;
import gnnt.MEBS.timebargain.mgr.model.delay.DelayTrade;
import gnnt.MEBS.timebargain.mgr.model.delay.HistoryDelayOrders;
import gnnt.MEBS.timebargain.mgr.model.delay.HistoryDelayQuotation;
import gnnt.MEBS.timebargain.mgr.model.delay.HistoryDelayTrade;
import gnnt.MEBS.timebargain.mgr.model.delay.SettlePrivilege;
import gnnt.MEBS.timebargain.mgr.statictools.CommonDictionary;
import gnnt.MEBS.timebargain.mgr.statictools.Copy;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.rmi.DelayRMI;

@Controller
@Scope("request")
public class DelayAction extends ECSideAction {
	private String opr;
	private String sql;

	@Autowired
	@Qualifier("DelayRMI")
	private DelayRMI remoteRMI;

	public String getOpr() {
		return this.opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	private String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String addCommodity() {
		this.logger.debug("enter addCommodity");
		SettlePrivilege settlePrivilege = (SettlePrivilege) this.entity;
		String kindid = settlePrivilege.getKindId();
		if (!"all".equals(kindid)) {
			getService().add(settlePrivilege);
		} else {
			String sql = "select t.commodityid from t_commodity t where settleWay=1 ";
			List commodityList = getService().getListBySql(sql);
			int s = commodityList.size();
			if ((commodityList != null) && (commodityList.size() > 0)) {
				for (int i = 0; i < commodityList.size(); i++) {
					SettlePrivilege settlePrivileges = new SettlePrivilege();
					String commodityid = ((Map) commodityList.get(i)).get("COMMODITYID").toString();

					String sqls = "select * from T_A_SETTLEPRIVILEGE t where t.kindid='" + commodityid + "' and t.typeid= '"
							+ settlePrivilege.getTypeId() + "'";
					List relList = getService().getListBySql(sqls);

					if ((relList == null) || (relList.size() == 0)) {
						settlePrivileges.setType(settlePrivilege.getType());
						settlePrivileges.setTypeId(settlePrivilege.getTypeId());
						settlePrivileges.setKind(settlePrivilege.getKind());
						settlePrivileges.setKindId(commodityid);
						settlePrivileges.setPrivilegecodeb(settlePrivilege.getPrivilegecodeb());
						settlePrivileges.setPrivilegecodes(settlePrivilege.getPrivilegecodes());

						getService().add(settlePrivileges);
					}
				}
			}
		}

		addReturnValue(1, 119901L);
		return "success";
	}

	public String editDelaySection() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'editDelaySection' method");
		}

		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, this.entity);
		List list = page.getResult();

		List neutralflagList = getService().getListBySql("select t.neutralflag from T_A_Market t where t.marketcode='99'");
		Map map = (Map) neutralflagList.get(0);
		String neutralflag = map.get("NEUTRALFLAG").toString();
		this.request.setAttribute("neutralflag", neutralflag);

		Delay delay = new Delay();
		delay.setSectionID(new Long("1"));
		delay.setType(new Short("0"));
		delay.setStatus(new Short("1"));
		if ((list != null) && (list.size() > 0)) {
			Iterator it = list.iterator();
			boolean flag = false;
			while (it.hasNext()) {
				Delay temp = (Delay) it.next();
				if (temp.getType().shortValue() == 0) {
					delay.setStartTime(temp.getStartTime());
					delay.setEndTime(temp.getEndTime());
					delay.setStatus(temp.getStatus());
					delay.setModifyTime(temp.getModifyTime());
				} else {
					delay.setType(temp.getType());
					delay.setStartMiddleTime(temp.getStartTime());
					delay.setEndMiddleTime(temp.getEndTime());
					flag = true;
				}
			}
			if ((flag) && (list.size() == 2)) {
				setOpr("middle");
			} else if ((flag) && (list.size() == 1)) {
				setOpr("single");
			} else if (!flag) {
				setOpr("delay");
			}
		}
		this.entity = delay;

		return "success";
	}

	public String saveDelaySection() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'saveDelaySection' method");
		}
		Delay delay = (Delay) this.entity;
		delay.setModifyTime(getService().getSysDate());
		Short s0 = new Short("0");
		Short s1 = new Short("1");
		if (delay.getType().shortValue() == 0) {
			Delay delay1 = new Delay();
			delay1.setModifyTime(getService().getSysDate());
			delay1.setStatus(new Short("1"));
			if ("delay".equals(getOpr())) {
				getService().update(this.entity);
			} else if ("single".equals(getOpr())) {
				getService().add(this.entity);
				getService().deleteBYBulk(new Delay(), new Long[] { new Long("2") });
			} else if ("middle".equals(getOpr())) {
				getService().update(this.entity);
				getService().deleteBYBulk(new Delay(), new Long[] { new Long("2") });
			} else {
				getService().add(this.entity);
			}
		} else {
			Delay delay1 = new Delay();
			delay1.setModifyTime(getService().getSysDate());
			delay1.setStatus(new Short("1"));
			if ("delay".equals(getOpr())) {
				delay.setType(s0);
				getService().update(delay);
				delay1.setSectionID(new Long("2"));
				delay1.setType(s1);
				delay1.setStartTime(delay.getStartMiddleTime());
				delay1.setEndTime(delay.getEndMiddleTime());
				getService().add(delay1);
			} else if ("single".equals(getOpr())) {
				delay1.setSectionID(new Long("2"));
				delay1.setType(s1);
				delay1.setStartTime(delay.getStartMiddleTime());
				delay1.setEndTime(delay.getEndMiddleTime());
				getService().update(delay1);

				Delay delay2 = new Delay();
				Copy.copy(delay, delay2);
				delay2.setType(s0);
				getService().add(delay2);
			} else if ("middle".equals(getOpr())) {
				delay1.setSectionID(new Long("2"));
				delay1.setType(s1);
				delay1.setStartTime(delay.getStartMiddleTime());
				delay1.setEndTime(delay.getEndMiddleTime());
				getService().update(delay1);
				delay.setType(s0);
				getService().update(delay);
			} else {
				delay1.setSectionID(new Long("2"));
				delay1.setType(s1);
				delay1.setStartTime(delay.getStartMiddleTime());
				delay1.setEndTime(delay.getEndMiddleTime());
				getService().add(delay1);
				delay1.setType(s0);
				getService().add(delay);
			}
		}

		addReturnValue(1, 119902L);
		writeOperateLog(1513, "修改延期交收节", 1, "");
		return "success";
	}

	public String deleteDelaySection() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'deleteDelaySection' method");
		}

		getService().deleteBYBulk(new Delay(), new Long[] { new Long("1") });
		addReturnValue(1, 151103L, new Object[] { "成功删除延期交收节" });
		writeOperateLog(1513, "修改延期交收节", 1, "");
		return "success";
	}

	public String commoditySettlePropEditForward() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'commoditySettlePropEditForward' method");
		}
		getSelectAttribute(this.request);

		List listSettle = getService().getListBySql(getSql().replace("?", "1"));

		List listMiddle = getService().getListBySql(getSql().replace("?", "2"));
		this.request.setAttribute("listSettle", listSettle);
		this.request.setAttribute("listMiddle", listMiddle);
		return "success";
	}

	public String commoditySettlePropEdit() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'commoditySettlePropEdit' method");
		}
		String[] settle = this.request.getParameterValues("settle");
		String[] middle = this.request.getParameterValues("middle");
		Date sysdate = getService().getSysDate();
		this.tps.commoditySettlePropAdd(settle, middle, sysdate);
		addReturnValue(1, 119902L);
		writeOperateLog(1513, "修改商品交收权限", 1, "");
		return "success";
	}

	private void getSelectAttribute(HttpServletRequest request) throws Exception {
		request.setAttribute("commoditySelect",
				this.tps.getSelectLabelValueByTable("T_commodity", "NAME", "COMMODITYID", " where settleWay=1 order by name "));
	}

	public String listSettlePrivilege() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'commoditySettlePropEdit' method");
		}
		this.request.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
		this.request.setAttribute("DELAYORDERTYPE", CommonDictionary.DELAYORDERTYPE);
		this.request.setAttribute("PRIVILEGECODE_B", CommonDictionary.PRIVILEGECODE_B);
		this.request.setAttribute("PRIVILEGECODE_S", CommonDictionary.PRIVILEGECODE_S);

		PageRequest pageRequest = getPageRequest(this.request);
		return listByLimit(pageRequest, this.entity);
	}

	public String listDelayStatus() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'listDelayStatus' method");
		}
		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, this.entity);
		DelayStatusLocal dsl = (DelayStatusLocal) page.getResult().get(0);

		this.request.setAttribute("tradeTime", dsl.getTradeDate() == null ? "" : Tools.fmtDate(dsl.getTradeDate()));
		this.request.setAttribute("sectionID", dsl.getSectionID());
		this.request.setAttribute("status", dsl.getStatus() == null ? "" : dsl.getStatus());
		this.request.setAttribute("note", dsl.getNote() == null ? "" : dsl.getNote());

		this.request.setAttribute("sysdate", Tools.fmtDate(getService().getSysDate()));

		DelayStatus dsRmi = new DelayStatus();
		if ("0".equals(dsl.getStatus())) {
			this.request.setAttribute("result2", DelayStatus.DELAY_STATUS[0]);
		}
		if ("1".equals(dsl.getStatus())) {
			this.request.setAttribute("result2", DelayStatus.DELAY_STATUS[1]);
		}
		if ("2".equals(dsl.getStatus())) {
			this.request.setAttribute("result2", DelayStatus.DELAY_STATUS[2]);
		}
		if ("3".equals(dsl.getStatus())) {
			this.request.setAttribute("result2", DelayStatus.DELAY_STATUS[3]);
		}
		if ("4".equals(dsl.getStatus())) {
			this.request.setAttribute("result2", DelayStatus.DELAY_STATUS[4]);
		}
		if ("5".equals(dsl.getStatus())) {
			this.request.setAttribute("result2", DelayStatus.DELAY_STATUS[5]);
		}

		String recoverTime2 = dsl.getRecoverTime();
		this.request.setAttribute("recoverTime2", recoverTime2);
		return "success";
	}

	public String updateDelayStatus() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'updateDelayStatus' method");
		}
		String type = this.request.getParameter("type");
		try {
			if ("99".equals(type)) {
				return "recoverTime";
			}

			if ("01".equals(type)) {
				this.remoteRMI.ctlTrade(0);
			}

			if ("02".equals(type)) {
				this.remoteRMI.ctlTrade(1);
			}

			if ("03".equals(type)) {
				this.remoteRMI.tradeEnd();
			}

			addReturnValue(1, 119907L);
		} catch (Exception e) {
			this.logger.debug("出错：" + e.getMessage());
			e.printStackTrace();
			addReturnValue(-1, 151103L, new Object[] { "操作失败，原因：" + e.getMessage() });
		}
		return "success";
	}

	public String recoverTime() throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'updateDelayStatusRecoverTime' method");
		}
		String recoverTime = this.request.getParameter("recoverTime");
		try {
			this.remoteRMI.timingContinueTrade(recoverTime);
			addReturnValue(1, 151103L, new Object[] { "修改延期交易节恢复时间[" + recoverTime + "]" });
			writeOperateLog(1513, "修改延期交易节恢复时间[" + recoverTime + "]", 1, "");
		} catch (Exception e) {
			this.logger.debug("出错：" + e.getMessage());
			e.printStackTrace();
			addReturnValue(-1, 151103L, new Object[] { "操作失败，原因：" + e.getMessage() });
			writeOperateLog(1513, "修改延期交易节恢复时间[" + recoverTime + "]", 0, "");
		}
		return "success";
	}

	public String getSettleprivilegeByFirmIdAndCommId() {
		int mgs = 0;
		String firmId = this.request.getParameter("firmId");
		String kindId = this.request.getParameter("kindId");
		PageRequest pageRequest = new PageRequest(" and primary.typeId='" + firmId + "' and kindId='" + kindId + "'");
		Page page = getService().getPage(pageRequest, new SettlePrivilege());

		if (page.getResult().size() > 0) {
			mgs = 1;
		}
		setOpr(String.valueOf(mgs));
		return "success";
	}

	public String delayOrdersList() throws Exception {
		String isQryHisHidd = this.request.getParameter("isQryHisHidd");
		this.request.setAttribute("isQryHisHidd", isQryHisHidd);

		if ((isQryHisHidd == null) || (isQryHisHidd.equals("no"))) {
			this.entity = new DelayOrders();
			this.request.setAttribute("isHiss", "no");
		} else {
			this.entity = new HistoryDelayOrders();
			this.request.setAttribute("isHis", "yes");
		}

		this.logger.debug("enter delayOrdersList");
		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, this.entity);
		this.request.setAttribute("pageInfo", page);

		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		return "success";
	}

	public String delayTradeList() throws Exception {
		String isQryHisHidd = this.request.getParameter("isQryHisHidd");
		this.request.setAttribute("isQryHisHidd", isQryHisHidd);

		if ((isQryHisHidd == null) || (isQryHisHidd.equals("no"))) {
			this.entity = new DelayTrade();
			this.request.setAttribute("isHiss", "no");
		} else {
			this.entity = new HistoryDelayTrade();
			this.request.setAttribute("isHis", "yes");
		}

		this.logger.debug("enter delayTradeList");
		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, this.entity);
		this.request.setAttribute("pageInfo", page);

		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		return "success";
	}

	public String delayQuotationList() throws Exception {
		String isQryHisHidd = this.request.getParameter("isQryHisHidd");
		this.request.setAttribute("isQryHisHidd", isQryHisHidd);

		if ((isQryHisHidd == null) || (isQryHisHidd.equals("no"))) {
			this.entity = new DelayQuotation();
			this.request.setAttribute("isHiss", "no");
		} else {
			this.entity = new HistoryDelayQuotation();
			this.request.setAttribute("isHis", "yes");
		}

		this.logger.debug("enter delayQuotationList");
		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, this.entity);
		this.request.setAttribute("pageInfo", page);

		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		return "success";
	}
}