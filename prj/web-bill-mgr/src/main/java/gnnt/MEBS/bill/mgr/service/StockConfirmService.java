package gnnt.MEBS.bill.mgr.service;

import gnnt.MEBS.common.mgr.model.User;

public abstract interface StockConfirmService
{
  public abstract int stockConfirm(String paramString, User paramUser)
    throws Exception;
}
