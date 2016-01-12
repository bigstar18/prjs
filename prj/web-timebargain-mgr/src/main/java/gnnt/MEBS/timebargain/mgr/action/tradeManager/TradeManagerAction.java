package gnnt.MEBS.timebargain.mgr.action.tradeManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;

@Controller("tradeManagerAction")
@Scope("request")
public class TradeManagerAction extends EcsideAction {
	private static final long serialVersionUID = -1L;

	@Autowired
	@Qualifier("TradeRMI")
	private TradeRMI tradeRMI;

	@Autowired
	@Qualifier("ServerRMI")
	private ServerRMI serverRMI;

	@Autowired
	@Qualifier("billTradeService")
	private ITradeService iTradeService;

	@Resource(name = "tradeRunModeMap")
	private Map<String, String> tradeRunModeMap;

	@Resource(name = "tradeStatusMap")
	private Map<String, String> tradeStatusMap;

	@Resource(name = "clearStatus_statusMap")
	private Map<String, String> clearStatus_statusMap;

	public Map<String, String> getClearStatus_statusMap() {
		return this.clearStatus_statusMap;
	}

	public void setClearStatus_statusMap(Map<String, String> paramMap) {
		this.clearStatus_statusMap = paramMap;
	}

	public Map<String, String> getTradeRunModeMap() {
		return this.tradeRunModeMap;
	}

	public void setTradeRunModeMap(Map<String, String> paramMap) {
		this.tradeRunModeMap = paramMap;
	}

	public Map<String, String> getTradeStatusMap() {
		return this.tradeStatusMap;
	}

	public void setTradeStatusMap(Map<String, String> paramMap) {
		this.tradeStatusMap = paramMap;
	}

	public TradeRMI getTradeRMI() {
		return this.tradeRMI;
	}

	public void setTradeRMI(TradeRMI paramTradeRMI) {
		this.tradeRMI = paramTradeRMI;
	}

	public ServerRMI getServerRMI() {
		return this.serverRMI;
	}

	public void setServerRMI(ServerRMI paramServerRMI) {
		this.serverRMI = paramServerRMI;
	}

	public String toTradeStatus() {
		this.logger.debug("------------toTradeStatus 交易状态管理--------------");
		SystemStatus localSystemStatus = null;
		List localList1 = getService().getListBySql("select * from T_SystemStatus");
		if ((localList1 != null) && (localList1.size() > 0)) {
			Map localObject = (Map) localList1.get(0);
			localSystemStatus = new SystemStatus();
			localSystemStatus.setTradeDate((Date) ((Map) localObject).get("TRADEDATE"));
			localSystemStatus.setStatus(((BigDecimal) ((Map) localObject).get("STATUS")).intValue());
			localSystemStatus.setSectionID(((Map) localObject).get("SECTIONID") == null ? null
					: new Integer(((BigDecimal) ((Map) localObject).get("SECTIONID")).intValue()));
			localSystemStatus.setNote((String) ((Map) localObject).get("NOTE"));
			localSystemStatus.setRecoverTime((String) ((Map) localObject).get("RECOVERTIME"));
			this.logger.debug("------------ 交易状态: " + localSystemStatus.getStatus() + "--------------");
		}
		this.request.setAttribute("systemStatus", localSystemStatus);
		Object localObject = "select RunMode,(select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual) systime from T_A_Market";
		List localList2 = getService().getListBySql((String) localObject);
		if ((localList2 != null) && (localList2.size() > 0)) {
			Map localMap = (Map) localList2.get(0);
			if (localMap.get("RUNMODE") != null) {
				String str1 = "";
				String str2 = "";
				str1 = localMap.get("RUNMODE").toString();
				str2 = localMap.get("SYSTIME").toString();
				this.request.setAttribute("runMode", str1);
				this.request.setAttribute("sysTime", str2);
			}
		}
		return "success";
	}

	public String updateTradeStatus() {
		String str1 = this.request.getParameter("marketStatus");
		try {
			String str2 = "";
			if ((str1 != null) && (str1.length() > 0)) {
				if (str1.equals("05")) {
					this.serverRMI.ctlTrade(0);
					this.logger.debug("updateTradeStatus ===pauseTrade");
					str2 = str2 + "修改交易状态-暂停";
				} else if (str1.equals("06")) {
					this.serverRMI.ctlTrade(1);
					this.logger.debug("updateTradeStatus ===resumeTrade");
					str2 = str2 + "修改交易状态-恢复";
				} else if (str1.equals("07")) {
					this.serverRMI.close();
					this.logger.debug("updateTradeStatus ===closeTrade");
					str2 = str2 + "修改交易状态-闭市";
				} else if (str1.equals("08")) {
					this.serverRMI.start();
					this.logger.debug("updateTradeStatus ===openTrade");
					str2 = str2 + "修改交易状态-开市";
				} else if (str1.equals("09")) {
					this.serverRMI.tradeEnd();
					this.logger.debug("updateTradeStatus ===endTrade");
					str2 = str2 + "修改交易状态-交易结束";
				}
				addReturnValue(1, 111501L);
				this.logger.debug("RMI" + str2);
				writeOperateLog(1503, str2, 1, "");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			String[] arrayOfString = localException.getMessage().split(":");
			String str3 = arrayOfString[(arrayOfString.length - 1)];
			this.logger.error("RMI修改交易状态失败！！！" + str3);
			writeOperateLog(1503, "修改市场交易状态", 0, localException.getMessage());
			addReturnValue(-1, 131520L, new Object[] { str3 });
		}
		return "success";
	}

	public String updateRecoverTime() {
		String str = this.request.getParameter("recoverTime");
		this.logger.debug("=============" + str);
		try {
			if ((str != null) && (str.length() > 0)) {
				this.serverRMI.timingContinueTrade(str);
				addReturnValue(1, 111501L);
				writeOperateLog(1503, "修改系统暂停恢复时间,恢复时间为：" + str, 1, "");
				return "success";
			}
		} catch (Exception localException) {
			this.logger.error("RMI修改暂停恢复时间失败！！！");
			writeOperateLog(1503, "修改系统暂停恢复时间", 0, localException.getMessage());
		}
		addReturnValue(-1, 131501L);
		return "SysException";
	}

	public String tradeEnd() {
		int i = -100;
		ResultVO localResultVO = null;
		try {
			List localList = getService().getListBySql(
					"select f.billid from T_billFrozen f, T_E_GageBill g where f.operation = g.id and Operationtype = 0 and g.commodityid in (select CommodityID from T_Commodity where SettleDate <= (select trunc(tradedate) from t_systemstatus))");
			String[] arrayOfString = new String[9999999];
			for (int j = 0; j < localList.size(); j++) {
				Map localMap = (Map) localList.get(j);
				String str2 = localMap.get("BILLID").toString();
				arrayOfString[j] = str2;
			}
			if ((localList != null) && (localList.size() > 0))
				localResultVO = this.iTradeService.unFrozenStocks(15, arrayOfString);
			if ((localResultVO == null) || (localResultVO.getResult() >= 0L) || (localResultVO.getResult() == -2L)) {
				i = this.tradeRMI.balance();
				if (i == 1) {
					addReturnValue(1, 111502L);
					writeOperateLog(1503, "交易结算成功", 1, "");
				} else if (i == -2) {
					addReturnValue(-1, 131517L);
					writeOperateLog(1503, "交收处理出错", 0, "");
				} else {
					addReturnValue(-1, 131518L);
					writeOperateLog(1503, "交易结算失败", 0, "");
				}
			}
		} catch (Exception localException) {
			String[] arrayOfString = localException.getMessage().split(":");
			String str1 = arrayOfString[(arrayOfString.length - 1)];
			addReturnValue(-1, 131519L, new Object[] { str1 });
			writeOperateLog(1503, "RMI交易结算失败！" + localException.getMessage(), 0, "");
		}
		return "success";
	}
}