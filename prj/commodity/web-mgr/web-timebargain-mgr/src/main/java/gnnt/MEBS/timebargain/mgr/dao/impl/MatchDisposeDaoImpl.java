package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.timebargain.mgr.dao.MatchDisposeDao;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatch;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatchLog;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("matchDisposeDao")
public class MatchDisposeDaoImpl extends StandardDao
  implements MatchDisposeDao
{
  public Object getFirmFunds(String procedureSql, Object[] params)
    throws Exception
  {
    return executeProcedure(procedureSql, params);
  }

  public List<StandardModel> getSettleMatchLog(String matchID)
  {
    String sql = "select * from t_settlematchlog where matchid = '" + 
      matchID + "' " + 
      "order by updatetime desc";

    return queryBySql(sql, new SettleMatchLog());
  }

  public SettleMatch getSettleMatchLock(String matchID)
  {
    SettleMatch settleMatch = null;
    String sql = "select * from t_settlematch where matchid = '" + matchID + "' for update ";
    List list = queryBySql(sql, new SettleMatch());
    if ((list != null) && (list.size() > 0)) {
      settleMatch = (SettleMatch)list.get(0);
    }
    return settleMatch;
  }

  public void updateSettleMatch(SettleMatch settleMatch)
  {
    update(settleMatch);
  }

  public void updateSettleMargin(String firmId, double thisPayMent)
  {
    String sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin -" + thisPayMent + " where FirmID='" + firmId + "'";
    executeUpdateBySql(sql);
  }

  public void deleteBillFrozenByMatchId(String matchId)
  {
    String sql = "delete from T_billFrozen where operation='" + matchId + "'";
    executeUpdateBySql(sql);
  }
}