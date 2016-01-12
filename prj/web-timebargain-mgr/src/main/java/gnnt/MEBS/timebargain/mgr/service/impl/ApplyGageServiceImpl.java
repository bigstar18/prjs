package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.model.applyGage.ApplyGage;
import gnnt.MEBS.timebargain.mgr.model.settle.HoldFrozen;
import gnnt.MEBS.timebargain.mgr.service.ApplyGageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("applyGageService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ApplyGageServiceImpl
  implements ApplyGageService
{

  @Autowired
  @Qualifier("com_standardDao")
  private StandardDao standardDao;

  public int addApplyGage(ApplyGage applyGage)
    throws Exception
  {
    int result = 0;
    Long availableQuantity = new Long(0L);
    Long canUseQuantity = new Long(0L);
    Long canUseGageQty = new Long(0L);
    String sql = "";
    if (applyGage.getApplyType().intValue() > 1) {
      sql = "select GageQty-GageFrozenQty canUseGageQty from T_CustomerHoldSum where CustomerID = '" + applyGage.getCustomerId() + "' and CommodityID='" + applyGage.getCommodityId() + "' and bs_flag=2";
      List list = this.standardDao.queryBySql(sql);
      if ((list != null) && (list.size() > 0)) {
        canUseGageQty = Long.valueOf(Long.parseLong(((Map)list.get(0)).get("CANUSEGAGEQTY").toString()));
      }
      if (canUseGageQty.longValue() < applyGage.getQuantity().longValue()) {
        result = -3;
      }
      else {
        this.standardDao.add(applyGage);
        result = 1;
      }
    }
    else {
      sql = "select holdqty-frozenqty canUseQuantity from T_CustomerHoldSum where CustomerID = '" + applyGage.getCustomerId() + "' and CommodityID='" + applyGage.getCommodityId() + "' and bs_flag=2 for update ";
      List list = this.standardDao.queryBySql(sql);
      if ((list != null) && (list.size() > 0)) {
        canUseQuantity = Long.valueOf(Long.parseLong(((Map)list.get(0)).get("CANUSEQUANTITY").toString()));
      }
      if (canUseQuantity.longValue() < applyGage.getQuantity().longValue()) {
        result = -1;
      }
      else {
        sql = "select Quantity-FrozenQty availableQuantity from T_ValidGageBill where FirmID = '" + applyGage.getFirmId() + "' and CommodityID='" + applyGage.getCommodityId() + "' for update ";
        list = this.standardDao.queryBySql(sql);
        if ((list != null) && (list.size() > 0)) {
          availableQuantity = Long.valueOf(Long.parseLong(((Map)list.get(0)).get("AVAILABLEQUANTITY").toString()));
        }
        if (availableQuantity.longValue() < applyGage.getQuantity().longValue()) {
          result = -2;
        }
        else {
          sql = "update T_CustomerHoldSum set frozenQty = frozenQty + " + applyGage.getQuantity() + " where CommodityID = '" + applyGage.getCommodityId() + "' and customerid = '" + applyGage.getCustomerId() + "' and bs_flag = 2";
          this.standardDao.executeUpdateBySql(sql);

          sql = "update T_ValidGageBill set frozenQty = frozenQty + " + applyGage.getQuantity() + " where CommodityID = '" + applyGage.getCommodityId() + "' and FirmID = '" + applyGage.getFirmId() + "' ";
          this.standardDao.executeUpdateBySql(sql);

          this.standardDao.add(applyGage);

          HoldFrozen holdFrozen = new HoldFrozen();
          holdFrozen.setOperation(applyGage.getApplyId().toString());
          holdFrozen.setFirmId(applyGage.getFirmId());
          holdFrozen.setCommodityId(applyGage.getCommodityId());
          holdFrozen.setBs_Flag(2);
          holdFrozen.setFrozentype(Integer.valueOf(3));
          holdFrozen.setFrozenQty(applyGage.getQuantity().longValue());
          holdFrozen.setFrozenTime(applyGage.getCreateTime());
          holdFrozen.setCustomerId(applyGage.getCustomerId());
          this.standardDao.add(holdFrozen);
          result = 1;
        }
      }
    }
    return result;
  }

  public int failApplyGage(ApplyGage applyGage)
    throws Exception
  {
    int result = 0;
    if (applyGage.getApplyType().intValue() > 1)
    {
      this.standardDao.update(applyGage);
      result = 1;
    }
    else {
      String sql = "update T_CustomerHoldSum set frozenQty = frozenQty - " + applyGage.getQuantity() + " where CommodityID = '" + applyGage.getCommodityId() + "' and customerid = '" + applyGage.getCustomerId() + "' and bs_flag = 2";
      this.standardDao.executeUpdateBySql(sql);

      sql = "update T_ValidGageBill set frozenQty = frozenQty - " + applyGage.getQuantity() + " where CommodityID = '" + applyGage.getCommodityId() + "' and FirmID = '" + applyGage.getFirmId() + "' ";
      this.standardDao.executeUpdateBySql(sql);

      this.standardDao.update(applyGage);

      sql = "delete T_Holdfrozen where Operation = '" + applyGage.getApplyId() + "' and frozentype=3 ";
      this.standardDao.executeUpdateBySql(sql);
      result = 1;
    }
    return result;
  }

  public int auditApplyGage(ApplyGage applyGageDB)
    throws Exception
  {
    String procedureName = "{?=call FN_T_GageQty(?,?,?,?) }";
    Object[] params = { applyGageDB.getCommodityId(), Integer.valueOf(2), applyGageDB.getCustomerId(), applyGageDB.getQuantity() };
    int result = 0;
    result = Integer.parseInt(this.standardDao.executeProcedure("{?=call FN_T_GageQty(?,?,?,?) }", params).toString());
    if (result == 1) {
      applyGageDB.setStatus(Integer.valueOf(2));
      this.standardDao.update(applyGageDB);

      String sql = "delete T_Holdfrozen where Operation = '" + applyGageDB.getApplyId() + "' and frozentype=3 ";
      this.standardDao.executeUpdateBySql(sql);
    }
    return result;
  }

  public int cancelApplyGage(ApplyGage applyGageDB)
    throws Exception
  {
    String procedureNameCancel = "{?=call FN_T_GageQtyCancel(?,?,?,?) }";
    Object[] params = { applyGageDB.getCustomerId(), applyGageDB.getCommodityId(), applyGageDB.getQuantity(), applyGageDB.getApplyType() };
    int result = 0;
    result = Integer.parseInt(this.standardDao.executeProcedure("{?=call FN_T_GageQtyCancel(?,?,?,?) }", params).toString());
    if (result == 1) {
      applyGageDB.setStatus(Integer.valueOf(2));
      this.standardDao.update(applyGageDB);
    }
    return result;
  }
}