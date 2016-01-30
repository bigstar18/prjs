package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.CustomerFundsDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("customerFundsDao")
public class CustomerFundsDaoImpl extends StandardDao
  implements CustomerFundsDao
{
  public List customerFundsTable(String firmId)
  {
    String sql = "select nvl(a.lastbalance,0) lastbalance,  nvl(a.balance,0) balance, nvl((select sum(amount) from f_fundflow where firmid = b.firmid and oprcode in ('11001', '11003')),  0) inAmount, nvl((select sum(amount) from f_fundflow where firmid = b.firmid and oprcode in ('11002', '11004')), 0) outAmount, nvl((select sum(close_pl) from t_trade where firmid = b.firmid and close_pl is not null), 0) close_pl, nvl((select sum(tradefee) from t_trade where firmid = b.firmid), 0) tradefee, nvl(b.ClearMargin,0) ClearMargin, nvl(b.clearfl,0) clearfl, nvl(b.runtimemargin,0) runtimemargin, nvl(b.runtimefl,0) runtimefl, nvl(b.ClearAssure,0) ClearAssure, nvl(b.MaxOverdraft,0) MaxOverdraft, nvl(b.RuntimeSettleMargin,0) RuntimeSettleMargin, nvl((select sum(frozenfunds - unfrozenfunds) from t_orders where firmid = b.firmid),  0) orderFrozen, (a.frozenfunds - nvl(c.frozenfunds, 0)) otherFrozen, (a.balance - a.frozenfunds) usefulFund, b.virtualfunds, nvl((select sum(floatingloss) from t_Firmholdsum where firmid = b.firmid), 0) PL from F_FIRMFUNDS a, T_Firm b, (select firmid, frozenfunds from f_Frozenfunds where moduleid = '15' and firmID = '" + 
      firmId + "') c " + 
      "where a.firmid = b.firmid " + 
      "and a.firmid = c.firmid(+) " + 
      "and b.firmID = '" + firmId + "'";

    return queryBySql(sql);
  }
}