package gnnt.MEBS.finance.front.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.finance.front.model.LedgerField;
import gnnt.MEBS.finance.front.model.SystemModule;

@Controller("clientLedgerSumAction")
@Scope("request")
public class ClientLedgerSumAction extends StandardAction {
	private static final long serialVersionUID = -1580217480584285099L;

	public String clientLedgerSum() throws Exception {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		String firmId = localUser.getBelongtoFirm().getFirmID();
		String moduleId = this.request.getParameter("gnnt_moduleId");
		String condition = "where 1=1  and firmId = '" + firmId + "'";

		String beginDate = this.request.getParameter("beginDate");
		String endDate = this.request.getParameter("endDate");
		if ((beginDate == null) || ("".equals(beginDate)))
			beginDate = Tools.fmtDate(new Date());
		if ((endDate == null) || ("".equals(endDate)))
			endDate = Tools.fmtDate(new Date());
		if ((beginDate != null) && (!"".equals(beginDate)))
			condition = condition + " and b_date >=to_date('" + beginDate + "','yyyy-MM-dd')";
		if ((endDate != null) && (!"".equals(endDate)))
			condition = condition + " and b_date <=to_date('" + endDate + "','yyyy-MM-dd')";

		String moduleIdStr = "";
		if ((moduleId != null) && (!"".equals(moduleId)))
			moduleIdStr = " and moduleId=" + moduleId;
		String ledgerSql = "select t.* from f_ledgerfield t where 1=1 " + moduleIdStr + " order by t.ordernum";

		List ledgerFieldList = getService().getListBySql(ledgerSql, new LedgerField());
		String[] signs = null;
		String[] codes = null;
		if ((ledgerFieldList != null) && (ledgerFieldList.size() > 0)) {
			codes = new String[ledgerFieldList.size()];
			signs = new String[ledgerFieldList.size()];
			for (int i = 0; i < ledgerFieldList.size(); i++) {
				String code = ((LedgerField) ledgerFieldList.get(i)).getCode();
				String sign = ((LedgerField) ledgerFieldList.get(i)).getFieldSign().toString();
				signs[i] = sign;
				codes[i] = code;
			}
		}
		// 转大写？
		if ((ledgerFieldList != null) && (ledgerFieldList.size() > 0))
			for (int j = 0; j < ledgerFieldList.size(); j++) {
				LedgerField ledgerField = (LedgerField) ledgerFieldList.get(j);
				((LedgerField) ledgerField).setCode(((LedgerField) ledgerField).getCode().toUpperCase());
			}

		List listValue = new ArrayList();
		if (codes != null) {
			String localObject2 = "todayBalance-lastBalance-(";
			String localObject3 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";
			String select = "select lastBalance,todayBalance,lastWarranty,todayWarranty,lastData.firmid ";
			for (int k = 0; k < codes.length; k++) {
				String code = codes[k];
				select = select + " ,nvl(ledgerSum." + code + ",0) " + code + " ";
			}
			select = select + " from (select lastBalance,lastWarranty,firmId,b_date from f_firmbalance " + condition
					+ ") lastData,(select todayBalance,todayWarranty,firmId,b_date from f_firmbalance " + condition
					+ ") todayData,(select  firmId,max(b_date) maxDate,min(b_date) minDate from f_firmbalance " + condition
					+ " group by firmId) firmDate,(select firmid lfirmid";
			for (int k = 0; k < codes.length; k++) {
				String code = codes[k];
				String sign = signs[k];
				String str11 = ((String) localObject3).replaceAll("e_x_t", code);
				select = select + str11;
				localObject2 = (String) localObject2 + "+(" + sign + ")*" + code;
			}
			select = select + " from f_clientledger f ";
			select = select + condition;
			select = select
					+ " group by firmid) ledgerSum where firmDate.firmid=ledgerSum.lfirmid(+)  and lastData.firmId=firmDate.firmId and firmDate.minDate=lastData.b_Date and firmDate.firmId=todayData.Firmid and firmDate.maxDate=todayData.b_Date";
			String sql = "select a.*";
			if ((moduleIdStr != null) && (!"".equals(moduleIdStr)))
				sql = (String) sql + ",(" + (String) localObject2 + ")) other";
			sql = (String) sql + " from (" + select + ") a order by firmId";

			this.logger.debug("sql-------" + (String) sql);
			listValue = getService().getListBySql((String) sql);
		}

		PageRequest pageRequest = new PageRequest(" ");
		Page systemModule = getService().getPage((PageRequest) pageRequest, new SystemModule());

		this.request.setAttribute("beginDate", beginDate);
		this.request.setAttribute("endDate", endDate);
		this.request.setAttribute("moduleList", ((Page) systemModule).getResult());
		this.request.setAttribute("listValue",
				(listValue != null) && (((List) listValue).size() > 0) ? (Map) ((List) listValue).get(0) : new HashMap());
		this.request.setAttribute("fieldList", ledgerFieldList);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));

		return "success";
	}

	//
	// select a.*
	// from (select lastBalance,
	// todayBalance,
	// lastWarranty,
	// todayWarranty,
	// lastData.firmid,
	// nvl(ledgerSum.Deposit, 0) Deposit,
	// nvl(ledgerSum.Fetch, 0) Fetch,
	// nvl(ledgerSum.BankFee, 0) BankFee,
	// nvl(ledgerSum.OtherItem, 0) OtherItem,
	// nvl(ledgerSum.Margin_T, 0) Margin_T,
	// nvl(ledgerSum.FL_T, 0) FL_T,
	// nvl(ledgerSum.SettleMargin_T, 0) SettleMargin_T,
	// nvl(ledgerSum.TradePL_T, 0) TradePL_T,
	// nvl(ledgerSum.SettlePL_T, 0) SettlePL_T,
	// nvl(ledgerSum.Income_T, 0) Income_T,
	// nvl(ledgerSum.Payout_T, 0) Payout_T,
	// nvl(ledgerSum.TradeFee_T, 0) TradeFee_T,
	// nvl(ledgerSum.SettleFee_T, 0) SettleFee_T,
	// nvl(ledgerSum.SettleCompens_T, 0) SettleCompens_T,
	// nvl(ledgerSum.OtherItem_T, 0) OtherItem_T,
	// nvl(ledgerSum.Income_V, 0) Income_V,
	// nvl(ledgerSum.Payout_V, 0) Payout_V,
	// nvl(ledgerSum.TradeFee_V, 0) TradeFee_V,
	// nvl(ledgerSum.Margin_V, 0) Margin_V,
	// nvl(ledgerSum.TradeFee_E, 0) TradeFee_E,
	// nvl(ledgerSum.Margin_E, 0) Margin_E,
	// nvl(ledgerSum.MarginBack_E, 0) MarginBack_E,
	// nvl(ledgerSum.SettleFee_E, 0) SettleFee_E,
	// nvl(ledgerSum.Payout_E, 0) Payout_E,
	// nvl(ledgerSum.Income_E, 0) Income_E,
	// nvl(ledgerSum.Subscirption_E, 0) Subscirption_E,
	// nvl(ledgerSum.OtherItem_E, 0) OtherItem_E,
	// nvl(ledgerSum.Payout_I, 0) Payout_I,
	// nvl(ledgerSum.Income_I, 0) Income_I,
	// nvl(ledgerSum.TradeFee_I, 0) TradeFee_I
	// from (select lastBalance, lastWarranty, firmId, b_date
	// from f_firmbalance
	// where 1 = 1
	// and firmId = '6'
	// and b_date >= to_date('2016-01-07', 'yyyy-MM-dd')
	// and b_date <= to_date('2016-01-07', 'yyyy-MM-dd')) lastData,
	// (select todayBalance, todayWarranty, firmId, b_date
	// from f_firmbalance
	// where 1 = 1
	// and firmId = '6'
	// and b_date >= to_date('2016-01-07', 'yyyy-MM-dd')
	// and b_date <= to_date('2016-01-07', 'yyyy-MM-dd')) todayData,
	// (select firmId, max(b_date) maxDate, min(b_date) minDate
	// from f_firmbalance
	// where 1 = 1
	// and firmId = '6'
	// and b_date >= to_date('2016-01-07', 'yyyy-MM-dd')
	// and b_date <= to_date('2016-01-07', 'yyyy-MM-dd')
	// group by firmId) firmDate,
	// (select firmid lfirmid,
	// sum(decode(code, 'Deposit', value, 0)) Deposit,
	// sum(decode(code, 'Fetch', value, 0)) Fetch,
	// sum(decode(code, 'BankFee', value, 0)) BankFee,
	// sum(decode(code, 'OtherItem', value, 0)) OtherItem,
	// sum(decode(code, 'Margin_T', value, 0)) Margin_T,
	// sum(decode(code, 'FL_T', value, 0)) FL_T,
	// sum(decode(code, 'SettleMargin_T', value, 0)) SettleMargin_T,
	// sum(decode(code, 'TradePL_T', value, 0)) TradePL_T,
	// sum(decode(code, 'SettlePL_T', value, 0)) SettlePL_T,
	// sum(decode(code, 'Income_T', value, 0)) Income_T,
	// sum(decode(code, 'Payout_T', value, 0)) Payout_T,
	// sum(decode(code, 'TradeFee_T', value, 0)) TradeFee_T,
	// sum(decode(code, 'SettleFee_T', value, 0)) SettleFee_T,
	// sum(decode(code, 'SettleCompens_T', value, 0)) SettleCompens_T,
	// sum(decode(code, 'OtherItem_T', value, 0)) OtherItem_T,
	// sum(decode(code, 'Income_V', value, 0)) Income_V,
	// sum(decode(code, 'Payout_V', value, 0)) Payout_V,
	// sum(decode(code, 'TradeFee_V', value, 0)) TradeFee_V,
	// sum(decode(code, 'Margin_V', value, 0)) Margin_V,
	// sum(decode(code, 'TradeFee_E', value, 0)) TradeFee_E,
	// sum(decode(code, 'Margin_E', value, 0)) Margin_E,
	// sum(decode(code, 'MarginBack_E', value, 0)) MarginBack_E,
	// sum(decode(code, 'SettleFee_E', value, 0)) SettleFee_E,
	// sum(decode(code, 'Payout_E', value, 0)) Payout_E,
	// sum(decode(code, 'Income_E', value, 0)) Income_E,
	// sum(decode(code, 'Subscirption_E', value, 0)) Subscirption_E,
	// sum(decode(code, 'OtherItem_E', value, 0)) OtherItem_E,
	// sum(decode(code, 'Payout_I', value, 0)) Payout_I,
	// sum(decode(code, 'Income_I', value, 0)) Income_I,
	// sum(decode(code, 'TradeFee_I', value, 0)) TradeFee_I
	// from f_clientledger f
	// where 1 = 1
	// and firmId = '6'
	// and b_date >= to_date('2016-01-07', 'yyyy-MM-dd')
	// and b_date <= to_date('2016-01-07', 'yyyy-MM-dd')
	// group by firmid) ledgerSum
	// where firmDate.firmid = ledgerSum.lfirmid(+)
	// and lastData.firmId = firmDate.firmId
	// and firmDate.minDate = lastData.b_Date
	// and firmDate.firmId = todayData.Firmid
	// and firmDate.maxDate = todayData.b_Date) a
	// order by firmId

}