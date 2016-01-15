package gnnt.MEBS.finance.mgr.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.finance.mgr.model.FundFlowF;
import gnnt.MEBS.finance.mgr.model.FundFlowHisF;
import gnnt.MEBS.finance.mgr.model.LedgerField;
import gnnt.MEBS.finance.mgr.model.SummaryF;
import gnnt.MEBS.finance.mgr.model.SystemModule;

@Controller("firmFundsAction")
@Scope("request")
public class FirmFundsAction extends EcsideAction {
	private static final long serialVersionUID = 1L;

	public String firmCurfundsList() throws Exception {
		this.logger.debug("enter firmCurfundsList");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		listByLimit(localPageRequest);
		return "success";
	}

	public String getFundsDetail() {
		String str1 = this.request.getParameter("firmId");
		ArrayList localArrayList = null;
		String str2 = "select firmId,lastBalance,balance from f_firmfunds t where firmId='" + str1 + "'";
		List localList1 = getService().getListBySql(str2);
		String str3 = "select (case when s.funddcflag='C' then '+' when s.funddcflag='D' then '-' end)||s.summary name,a.money value from (select t.oprcode,sum(t.amount) money from f_fundflow t where firmID='"
				+ str1 + "' group by t.oprcode ) a,f_summary s where a.oprcode=SummaryNo";
		List localList2 = getService().getListBySql(str3);
		Map localMap = null;
		if ((localList1 != null) && (localList1.size() > 0))
			localMap = (Map) localList1.get(0);
		localArrayList = new ArrayList();
		localArrayList.add(localMap);
		localArrayList.add(localList2);
		this.request.setAttribute("list", localArrayList);
		this.request.setAttribute("firmId", str1);
		return "success";
	}

	public String fundFlowList() throws Exception {
		String str = this.request.getParameter("type");
		if ((str != null) && (str.equals("H"))) {
			this.entity = new FundFlowHisF();
			str = "H";
		} else {
			this.entity = new FundFlowF();
			str = "D";
		}
		PageRequest localPageRequest1 = super.getPageRequest(this.request);
		listByLimit(localPageRequest1);
		this.request.setAttribute("type", str);
		PageRequest localPageRequest2 = new PageRequest(" ");
		Page localPage1 = getService().getPage(localPageRequest2, new SystemModule());
		PageRequest localPageRequest3 = new PageRequest(" ");
		Page localPage2 = getService().getPage(localPageRequest3, new SummaryF());
		this.request.setAttribute("summaryList", localPage2.getResult());
		this.request.setAttribute("moduleList", localPage1.getResult());
		return "success";
	}

	public String clientLedger() throws Exception {
		PageRequest localPageRequest1 = super.getPageRequest(this.request);
		QueryConditions localQueryConditions = (QueryConditions) localPageRequest1.getFilters();
		Map localMap = getParametersStartingWith(this.request, "gnnt_");
		if ((localMap != null) && (localMap.size() == 0)) {
			String str1 = Tools.fmtDate(new Date());
			String str2 = Tools.combineDateTime(str1);
			String str3 = Tools.combineDateTime(str1, 1);
			localQueryConditions.addCondition("primary.b_Date", ">=", Tools.strToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), str2));
			localQueryConditions.addCondition("primary.b_Date", "<=", Tools.strToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), str3));
		}
		String str1 = "";
		String str2 = this.request.getParameter("moduleId");
		if ((str2 != null) && (!"".equals(str2)))
			str1 = " and moduleId=" + str2;
		listByLimit(localPageRequest1);
		String str3 = "select t.* from f_ledgerfield t where 1=1 " + str1 + " order by t.ordernum";
		List localList = getService().getListBySql(str3, new LedgerField());
		this.request.setAttribute("fieldList", localList);
		PageRequest localPageRequest2 = new PageRequest(" ");
		Page localPage = getService().getPage(localPageRequest2, new SystemModule());
		this.request.setAttribute("moduleList", localPage.getResult());
		this.request.setAttribute("moduleId", str2);
		return "success";
	}

	public String clientLedgerSum() throws Exception {
		String str1 = this.request.getParameter("beginDate");
		String str2 = this.request.getParameter("endDate");
		String str3 = this.request.getParameter("firmId");
		String str4 = this.request.getParameter("moduleId");
		String str5 = "";
		String str6 = "where 1=1 ";
		if (str1 == null)
			str1 = Tools.fmtDate(new Date());
		if (str2 == null)
			str2 = Tools.fmtDate(new Date());
		if ((str1 != null) && (!"".equals(str1)))
			str6 = str6 + " and b_date >=to_date('" + str1 + "','yyyy-MM-dd')";
		if ((str2 != null) && (!"".equals(str2)))
			str6 = str6 + " and b_date <=to_date('" + str2 + "','yyyy-MM-dd')";
		if ((str3 != null) && (!"".equals(str3)))
			str6 = str6 + " and firmId ='" + str3 + "'";
		if ((str4 != null) && (!"".equals(str4)))
			str5 = " and moduleId=" + str4;
		String str7 = "select t.* from f_ledgerfield t where 1=1 " + str5 + " order by t.ordernum";
		List localList = getService().getListBySql(str7, new LedgerField());
		String[] arrayOfString1 = null;
		String[] arrayOfString2 = null;
		String str8;
		if ((localList != null) && (localList.size() > 0)) {
			arrayOfString2 = new String[localList.size()];
			arrayOfString1 = new String[localList.size()];
			for (int i = 0; i < localList.size(); i++) {
				String localObject2 = ((LedgerField) localList.get(i)).getCode();
				String localObject3 = ((LedgerField) localList.get(i)).getName();
				str8 = ((LedgerField) localList.get(i)).getFieldSign().toString();
				arrayOfString2[i] = localObject2;
				arrayOfString1[i] = str8;
			}
		}
		Object localObject1 = new ArrayList();
		Object localObject4;
		if (arrayOfString2 != null) {
			String localObject2 = "";
			String localObject3 = "todayBalance-lastBalance-(";
			str8 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";
			localObject4 = "select lastBalance,todayBalance,lastWarranty,todayWarranty,lastData.firmid ";
			String str10;
			for (int k = 0; k < arrayOfString2.length; k++) {
				str10 = arrayOfString2[k];
				localObject4 = (String) localObject4 + " ,nvl(ledgerSum." + str10 + ",0) " + str10 + " ";
			}
			localObject4 = (String) localObject4 + " from (select lastBalance,lastWarranty,firmId,b_date from f_firmbalance " + str6
					+ ") lastData,(select todayBalance,todayWarranty,firmId,b_date from f_firmbalance " + str6
					+ ") todayData,(select  firmId,max(b_date) maxDate,min(b_date) minDate from f_firmbalance " + str6
					+ " group by firmId) firmDate,(select firmid lfirmid";
			for (int k = 0; k < arrayOfString2.length; k++) {
				str10 = arrayOfString2[k];
				String str11 = arrayOfString1[k];
				String str12 = str8.replaceAll("e_x_t", str10);
				localObject4 = (String) localObject4 + str12;
				localObject3 = (String) localObject3 + "+(" + str11 + ")*" + str10;
			}
			localObject4 = (String) localObject4 + " from f_clientledger f ";
			localObject4 = (String) localObject4 + str6;
			localObject4 = (String) localObject4
					+ " group by firmid) ledgerSum where firmDate.firmid=ledgerSum.lfirmid(+)  and lastData.firmId=firmDate.firmId and firmDate.minDate=lastData.b_Date and firmDate.firmId=todayData.Firmid and firmDate.maxDate=todayData.b_Date";
			String str9 = "select a.*";
			if ((str5 != null) && (!"".equals(str5)))
				str9 = str9 + ",(" + (String) localObject3 + ")) other";
			str9 = str9 + " from (" + (String) localObject4 + ") a order by firmId";
			localObject1 = getService().getListBySql(str9);
		}
		Object localObject2 = new PageRequest(" ");
		Object localObject3 = getService().getPage((PageRequest) localObject2, new SystemModule());
		this.request.setAttribute("moduleList", ((Page) localObject3).getResult());
		this.request.setAttribute("listValue", localObject1);
		this.request.setAttribute("moduleId", str4);
		this.request.setAttribute("firmId", str3);
		this.request.setAttribute("beginDate", str1);
		this.request.setAttribute("endDate", str2);
		if ((localList != null) && (localList.size() > 0))
			for (int j = 0; j < localList.size(); j++) {
				localObject4 = (LedgerField) localList.get(j);
				((LedgerField) localObject4).setCode(((LedgerField) localObject4).getCode().toUpperCase());
			}
		this.request.setAttribute("fieldList", localList);
		return "success";
	}
}