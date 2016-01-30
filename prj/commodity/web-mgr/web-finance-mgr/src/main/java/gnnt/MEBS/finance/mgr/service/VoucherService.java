package gnnt.MEBS.finance.mgr.service;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.finance.mgr.dao.VoucherDao;
import gnnt.MEBS.finance.mgr.model.Voucher;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("voucherService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VoucherService extends StandardService
{

  @Resource(name="voucherDao")
  private VoucherDao voucherDao;

  public VoucherDao getVoucherDao()
  {
    return this.voucherDao;
  }

  public void setVoucherDao(VoucherDao voucherDao) {
    this.voucherDao = voucherDao;
  }

  public double addVoucherFast(String summaryNo, String summary, String debitCode, String creditCode, String contractno, String inputUser, String money)
    throws Exception
  {
    double result = -1.0D;
    Object[] objects = { summaryNo, summary, debitCode, 
      creditCode, money, contractno, inputUser };
    Object obj = getDao().executeProcedure(
      "{?=call FN_F_CreateVoucher(?,?,?,?,?,?,?)}", objects);
    result = new Double(obj.toString()).doubleValue();
    return result;
  }

  public int auditVoucher(Long voucherNo, boolean isPass)
    throws Exception
  {
    int result = -1;
    if (voucherNo != null) {
      if (isPass) {
        Object[] objects = { voucherNo };
        Object obj = getDao().executeProcedure(
          "{?=call FN_F_auditVoucherPass(?)}", objects);
        result = new Integer(obj.toString()).intValue();
      } else {
        result = 1;
      }
    }
    return result;
  }

  public String deleteVoucher(StandardModel entity, Object[] ids)
    throws Exception
  {
    String result = "";

    this.logger.debug("enter deleteBYBulk ids");
    if (ids == null) {
      throw new IllegalArgumentException("删除主键数组不能为空！");
    }

    if (entity == null) {
      throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
    }

    if ((entity.fetchPKey() == null) || (entity.fetchPKey().getKey() == null) || 
      (entity.fetchPKey().getKey().length() == 0)) {
      throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
    }

    for (Object id : ids)
    {
      String voucherNo = id.toString();

      this.voucherDao.voucherLock(voucherNo);

      Voucher voucher = this.voucherDao.iSVoucherEditing(voucherNo);
      if (voucher != null) {
        voucher.setVoucherNo(Long.valueOf(Long.parseLong(voucherNo)));

        getDao().delete(voucher);
      } else {
        result = result + voucherNo + ",";
      }

    }

    if ((result != null) && (!result.equals(""))) {
      result = result.substring(0, result.length() - 1);
    }

    return result;
  }
}