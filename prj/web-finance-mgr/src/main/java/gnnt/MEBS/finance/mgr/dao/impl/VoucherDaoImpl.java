package gnnt.MEBS.finance.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.finance.mgr.dao.VoucherDao;
import gnnt.MEBS.finance.mgr.model.Voucher;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("voucherDao")
public class VoucherDaoImpl extends StandardDao
  implements VoucherDao
{
  public void voucherLock(String voucherNo)
  {
    String sql = "select * from f_voucher where voucherNo = " + voucherNo + " for update";

    executeUpdateBySql(sql);
  }

  public Voucher iSVoucherEditing(String voucherNo)
  {
    boolean result = false;

    String sql = "select * from f_voucher where status = 'editing' and voucherNo = " + voucherNo;

    List list = queryBySql(sql, new Voucher());

    if ((list != null) && (list.size() > 0)) {
      return (Voucher)list.get(0);
    }

    return null;
  }
}