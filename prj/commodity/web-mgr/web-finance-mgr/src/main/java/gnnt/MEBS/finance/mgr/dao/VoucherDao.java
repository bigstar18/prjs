package gnnt.MEBS.finance.mgr.dao;

import gnnt.MEBS.finance.mgr.model.Voucher;

public abstract interface VoucherDao
{
  public abstract void voucherLock(String paramString);

  public abstract Voucher iSVoucherEditing(String paramString);
}