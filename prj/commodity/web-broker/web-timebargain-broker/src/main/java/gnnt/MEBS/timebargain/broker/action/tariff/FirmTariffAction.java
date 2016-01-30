package gnnt.MEBS.timebargain.broker.action.tariff;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.model.MFirm;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.statictools.Tools;

@Controller
@Scope("request")
public class FirmTariffAction extends ECSideAction {

	@Resource(name = "unauditedFirm_StatusMap")
	private HashMap<Character, String> unauditedFirm_StatusMap;

	public HashMap<Character, String> getUnauditedFirm_StatusMap() {
		return this.unauditedFirm_StatusMap;
	}

	public void setUnauditedFirm_StatusMap(HashMap<Character, String> paramHashMap) {
		this.unauditedFirm_StatusMap = paramHashMap;
	}

	public String listFirmTariff() throws Exception {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter listFirmTariff method");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		String str = "('N','D','E')";
		StringBuffer localStringBuffer = new StringBuffer("(");
		localStringBuffer
				.append("select m.firmId,m.tariffid,mm.name,mm.contactMan,mm.createTime,mm.status,mm.type,(select distinct tariffname from T_A_tariff t where t.tariffid=m.tariffid) as tariffname from t_firm m, m_firm mm where m.firmId=mm.firmId and m.firmId in ("
						+ localUser.getSql());
		localStringBuffer.append(") and mm.status in  " + str);
		localStringBuffer.append(")");
		return listByLimit(localPageRequest, localStringBuffer.toString());
	}

	public String updateTariff() throws Exception {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter updateTariff method");
		MFirm localMFirm = (MFirm) this.entity;
		String str1 = this.request.getParameter("tariffID");
		String str2 = "update t_firm set tariffid='" + str1 + "' where firmid='" + localMFirm.getFirmID() + "'";
		getService().executeUpdateBySql(str2);
		addReturnValue(1, 119902L);
		return "success";
	}

	public String getTariffCommodityList() throws Exception {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter getTariffCommodityList method");
		String str1 = this.request.getParameter("tariffID");
		this.request.setAttribute("tariffID", str1);
		PageRequest localPageRequest = super.getPageRequest(this.request);
		if ((str1 != null) && (!str1.equals(""))) {
			String str2 = "(select TariffID,case t.FEEALGR when 1 then  to_char(t.FEERATE_B*100,'fm9999999990.009999')||'%'  else to_char(t.FEERATE_B,'fm9999999990.009999') end newFeeRate,case c.FEEALGR when 1 then   to_char(c.FEERATE_B*100,'fm9999999990.009999')||'%'  else  to_char(c.FEERATE_B,'fm9999999990.009999') end oldFeeRate,t.MODIFYTIME, t.TariffName,TariffRate*100||'%' TARIFFRATE,c.name,t.commodityID from T_A_Tariff t,T_COMMODITY c where tariffID='"
					+ str1 + "' and t.commodityid=c.commodityid order by t.commodityid)";
			this.request.setAttribute("tariffID", str1);
			this.request.setAttribute("tariffName", "加收" + str1.substring(1) + "%");
			return listByLimit(localPageRequest, str2);
		}
		return "success";
	}

	public String listTradeFeeFirm() throws Exception {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter listTradeFeeFirm method");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		String str1 = this.request.getParameter("beginDate");
		String str2 = this.request.getParameter("endDate");
		if ((str1 == null) && (str2 == null)) {
			Date localObject = new Date();
			str1 = Tools.fmtDate((Date) localObject);
			str2 = Tools.fmtDate((Date) localObject);
		}
		((QueryConditions) localPageRequest.getFilters()).addCondition("to_char(trunc(a.occurdate),'yyyy-MM-dd')", ">=", str1);
		((QueryConditions) localPageRequest.getFilters()).addCondition("to_char(trunc(a.occurdate),'yyyy-MM-dd')", "<=", str2);
		this.request.setAttribute("beginDate", Tools.strToDate(str1));
		this.request.setAttribute("endDate", Tools.strToDate(str2));
		Object localObject = " select a.brokerid brokerid,  (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select  t.brokerid brokerid,t.firmid firmid,t.cleardate occurdate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.reward + t.marketreward,0)) sTradeFee from t_br_firmrewarddeail t where t.firmid in ("
				+ localUser.getSql() + ") group by t.firmid, t.brokerid,t.cleardate) a where 1=1 ";
		String str3 = " order by a.brokerid,a.firmid,a.occurdate ";
		String str4 = "";
		String str5 = "N";
		if ("N".equals(str5))
			str4 = "select d.brokerid BROKERID,d.name NAME,d.firmid FIRMID,d.firmname FIRMNAME,sum(d.sumTradeFee) SUMTRADEFEE,sum(d.sumBrokereachdivide) SUMBROKEREACHDIVIDE,sum(d.sumReward) SUMREWARD,sum(d.sumSelfGain) SUMSELFGAIN,sum(d.sumMarhetGain) SUMMARHETGAIN,sum(d.sumFinalMarhetGain) SUMFINALMARHETGAIN from (?) d group by d.brokerid,d.name,d.firmid,d.firmname order by d.brokerid,d.firmid";
		else if ("Y".equals(str5))
			str4 = "select d.brokerid BROKERID,d.name NAME,d.firmid FIRMID,d.firmname FIRMNAME,d.breedid breedid,sum(d.sumTradeFee) SUMTRADEFEE,sum(d.sumBrokereachdivide) SUMBROKEREACHDIVIDE,sum(d.sumReward) SUMREWARD,sum(d.sumSelfGain) SUMSELFGAIN,sum(d.sumMarhetGain) SUMMARHETGAIN,sum(d.sumFinalMarhetGain) SUMFINALMARHETGAIN  from (?) d group by d.brokerid,d.name,d.firmid,d.firmname,d.breedid order by d.brokerid,d.firmid";
		return listByLimit(localPageRequest, (String) localObject, str3, str4);
	}

	public String listTradeFeeFirmCommodityList() throws Exception {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter listTradeFeeFirmCommodityList method");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		String str1 = this.request.getParameter("beginDate");
		String str2 = this.request.getParameter("endDate");
		if ((str1 == null) && (str2 == null)) {
			Date localObject = new Date();
			str1 = Tools.fmtDate((Date) localObject);
			str2 = Tools.fmtDate((Date) localObject);
		}
		((QueryConditions) localPageRequest.getFilters()).addCondition("to_char(trunc(a.occurdate),'yyyy-MM-dd')", ">=", str1);
		((QueryConditions) localPageRequest.getFilters()).addCondition("to_char(trunc(a.occurdate),'yyyy-MM-dd')", "<=", str2);
		this.request.setAttribute("beginDate", Tools.strToDate(str1));
		this.request.setAttribute("endDate", Tools.strToDate(str2));
		Object localObject = " select tb.breedname , tb.breedID breedid, sum(a.sTradeFee) sumTradeFee, sum(a.sReward) sumReward, sum(a.sBrokereachdivide) sumBrokereachdivide, sum((a.sReward-a.sBrokereachdivide)) sumSelfGain, sum((a.sTradeFee-(a.sReward-a.sBrokereachdivide))) sumMarhetGain, sum((a.sTradeFee-a.sReward)) sumFinalMarhetGain  from  (select t.brokerid brokerid,t.cleardate occurdate,t.commodityid commodityid,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.reward + t.marketreward,0)) sTradeFee from t_br_firmrewarddeail t where t.firmid in ("
				+ localUser.getSql() + ") group by t.brokerid,t.cleardate,t.commodityid) a,t_a_breed tb,t_commodity c "
				+ " where a.commodityid=c.commodityid and c.breedid=tb.breedid ";
		String str3 = " group by tb.breedid,tb.breedname order by tb.breedid ";
		String str4 = " ?";
		return listByLimit(localPageRequest, (String) localObject, str3, str4);
	}
}