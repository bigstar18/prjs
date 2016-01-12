package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.model.settleProps.SettlePropsP;
import java.util.List;

public abstract interface SettlePropsService
{
  public abstract void addSettleProps(SettlePropsP paramSettlePropsP, List<SettlePropsP> paramList)
    throws Exception;
}