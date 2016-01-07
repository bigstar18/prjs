package gnnt.MEBS.finance.front.action;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("firmFundDetailAction")
@Scope("request")
public class firmFundDetailAction extends StandardAction {
	private static final long serialVersionUID = -1451901121979465031L;

	public String firmFundDetail() throws Exception {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		String str = localUser.getBelongtoFirm().getFirmID();
		StringBuffer localStringBuffer1 = new StringBuffer();
		localStringBuffer1
				.append("select a.settleFee,a.settlePL,a.t_goodmoney,a.settleMargain,a.penal,cast(b.moduleid as varchar(2)) moduleId, b.firmid,b.frozenfunds")
				.append(" from (select max(firmid) firmid,").append(" sum(case when oprcode = '15001' then amount else 0 end) settleFee,")
				.append(" (sum(case when oprcode = '15011' then amount else 0 end) - sum(case when oprcode = '15012' then amount else 0 end)) settlePL,")
				.append(" (sum(case when oprcode = '15009' then amount else 0 end) - sum(case when oprcode = '15008' then amount else 0 end)) t_goodmoney,")
				.append(" (sum(case when oprcode = '15014' then amount else 0 end) - sum(case when oprcode = '15013' then amount else 0 end)) settleMargain,")
				.append(" (sum(case when oprcode = '15017' then amount else 0 end) - sum(case when oprcode = '15018' then amount else 0 end)) penal")
				.append(" from f_fundflow where firmid= '" + str + "') a,").append(" f_frozenfunds b where a.firmid = b.firmid");
		this.logger.debug("fundDetailSql : ============ :" + localStringBuffer1.toString());
		List localList1 = getService().getListBySql(localStringBuffer1.toString());
		this.request.setAttribute("fundDetail", (localList1 != null) && (localList1.size() > 0) ? (Map) localList1.get(0) : new HashMap());
		StringBuffer localStringBuffer2 = new StringBuffer();
		localStringBuffer2.append("select a.lastbalance,a.balance,")
				.append(" nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('11001','11003')),0) inAmount,")
				.append(" nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('11002','11004')),0) outAmount,")
				.append(" nvl((select sum(close_pl) from t_trade where firmid=b.firmid and close_pl is not null),0) close_pl,")
				.append(" nvl((select sum(tradefee) from t_trade where firmid=b.firmid),0) tradefee,")
				.append(" b.ClearMargin,b.clearfl,b.runtimemargin,b.runtimefl,b.ClearAssure,b.runtimeAssure ,b.MaxOverdraft,b.RuntimeSettleMargin,")
				.append(" nvl((select sum(frozenfunds - unfrozenfunds) from t_orders where firmid = b.firmid),0) orderFrozen,")
				.append(" (a.frozenfunds-nvl(c.frozenfunds,0)) otherFrozen,(a.balance - a.frozenfunds) usefulFund,b.virtualfunds,")
				.append(" nvl((select sum(floatingloss) from t_Firmholdsum where firmid = b.firmid),0) PL ")
				.append(" from F_FIRMFUNDS a,T_Firm b, (select firmid,frozenfunds from f_Frozenfunds where moduleid='15' and firmID='" + str
						+ "') c ")
				.append(" where a.firmid = b.firmid and a.firmid = c.firmid(+) and b.firmID = '" + str + "'");
		this.logger.debug("fundDetailSql : ============ :" + localStringBuffer2.toString());
		List localList2 = getService().getListBySql(localStringBuffer2.toString());
		this.request.setAttribute("firmInfo", (localList2 != null) && (localList2.size() > 0) ? (Map) localList2.get(0) : new HashMap());
		return "success";
	}
}