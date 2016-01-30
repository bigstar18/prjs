package gnnt.MEBS.bill.front.service;

import gnnt.MEBS.common.front.model.integrated.User;

public abstract interface StockConfirmService
{
  public abstract int stockConfirm(String paramString, User paramUser)
    throws Exception;
}
