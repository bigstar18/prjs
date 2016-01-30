package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class TradeList extends StandardModel
{
  private static final long serialVersionUID = 8861985392284267161L;
  private Double trade_money;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}