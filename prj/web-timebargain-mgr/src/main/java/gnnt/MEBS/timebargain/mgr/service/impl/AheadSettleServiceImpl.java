package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.timebargain.mgr.dao.AheadSettleDao;
import gnnt.MEBS.timebargain.mgr.model.settle.ApplyAheadSettle;
import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.HoldFrozen;
import gnnt.MEBS.timebargain.mgr.service.AheadSettleService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("aheadSettleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AheadSettleServiceImpl
  implements AheadSettleService
{
  private final int moduleId = 15;

  @Autowired
  @Qualifier("billTradeService")
  private ITradeService tradeService;

  @Autowired
  @Qualifier("aheadSettleDao")
  private AheadSettleDao aheadSettleDao;

  public ITradeService getTradeService() { return this.tradeService; }

  public void setTradeService(ITradeService tradeService)
  {
    this.tradeService = tradeService;
  }

  public AheadSettleDao getAheadSettleDao()
  {
    return this.aheadSettleDao;
  }

  public void setAheadSettleDao(AheadSettleDao aheadSettleDao)
  {
    this.aheadSettleDao = aheadSettleDao;
  }

  public List<?> getCustomerList() {
    return this.aheadSettleDao.getCustomerList();
  }

  public int addApplyAheadSettle(String[] ids, ApplyAheadSettle applyAheadSettle, String firmB, String firmS) throws Exception {
    int result = 0;

    List bHoldQtyList = this.aheadSettleDao.queryBySql("select (holdqty-frozenqty) qty from t_customerholdsum where CustomerID ='" + applyAheadSettle.getCustomerId_B() + "' and CommodityID= '" + applyAheadSettle.getCommodityId() + "' and bs_flag = 1");
    if ((bHoldQtyList != null) && (bHoldQtyList.size() > 0)) {
      long qty = Long.parseLong(((Map)bHoldQtyList.get(0)).get("QTY").toString());
      if (qty < applyAheadSettle.getQuantity().intValue()) {
        result = -5;
        return result;
      }
    } else {
      result = -5;
      return result;
    }

    List sHoldQtyList = this.aheadSettleDao.queryBySql("select (holdqty-frozenqty) qty from t_customerholdsum where CustomerID ='" + applyAheadSettle.getCustomerId_S() + "' and CommodityID= '" + applyAheadSettle.getCommodityId() + "' and bs_flag = 2");
    if ((sHoldQtyList != null) && (sHoldQtyList.size() > 0)) {
      long qty = Long.parseLong(((Map)sHoldQtyList.get(0)).get("QTY").toString());
      if (qty < applyAheadSettle.getQuantity().intValue()) {
        result = -6;
        return result;
      }
    } else {
      result = -6;
      return result;
    }
    String applyId = "";

    String sql = "select seq_t_e_applyaheadsettle.nextval id from dual";
    List list = this.aheadSettleDao.queryBySql(sql);
    applyId = "ATS_" + ((Map)list.get(0)).get("ID").toString();

    applyAheadSettle.setApplyId(applyId);
    this.aheadSettleDao.addApplyAheadSettle(applyAheadSettle);

    HoldFrozen holdFrozenB = new HoldFrozen();
    holdFrozenB.setOperation(applyId);
    holdFrozenB.setFirmId(firmB);
    holdFrozenB.setCommodityId(applyAheadSettle.getCommodityId());
    holdFrozenB.setBs_Flag(1);
    holdFrozenB.setFrozentype(Integer.valueOf(0));
    holdFrozenB.setFrozenQty(applyAheadSettle.getQuantity().intValue());
    holdFrozenB.setFrozenTime(new Date());
    holdFrozenB.setCustomerId(applyAheadSettle.getCustomerId_B());
    this.aheadSettleDao.addHoldFrozen(holdFrozenB);

    String sqlb = "update t_customerholdsum set frozenQty = frozenQty +" + applyAheadSettle.getQuantity() + " where CustomerID = '" + applyAheadSettle.getCustomerId_B() + "' and bs_flag = 1 and commodityId ='" + applyAheadSettle.getCommodityId() + "'";
    this.aheadSettleDao.executeUpdateBySql(sqlb);

    HoldFrozen holdFrozenS = new HoldFrozen();
    holdFrozenS.setOperation(applyId);
    holdFrozenS.setFirmId(firmS);
    holdFrozenS.setCommodityId(applyAheadSettle.getCommodityId());
    holdFrozenS.setBs_Flag(2);
    holdFrozenS.setFrozentype(Integer.valueOf(0));
    holdFrozenS.setFrozenQty(applyAheadSettle.getQuantity().intValue());
    holdFrozenS.setFrozenTime(new Date());
    holdFrozenS.setCustomerId(applyAheadSettle.getCustomerId_S());
    this.aheadSettleDao.addHoldFrozen(holdFrozenS);

    String sqls = "update t_customerholdsum set frozenQty = frozenQty +" + applyAheadSettle.getQuantity() + " where CustomerID = '" + applyAheadSettle.getCustomerId_S() + "' and bs_flag = 2 and commodityId ='" + applyAheadSettle.getCommodityId() + "'";
    this.aheadSettleDao.executeUpdateBySql(sqls);

    for (int i = 0; i < ids.length; i++) {
      BillFrozen billFrozen = new BillFrozen();
      billFrozen.setOperation(applyId);
      billFrozen.setBillID(ids[i]);
      billFrozen.setOperationType(Integer.valueOf(1));
      billFrozen.setModifyTime(new Date());
      this.aheadSettleDao.addBillFrozen(billFrozen);
    }

    ResultVO resultVO = this.tradeService.performTransferGoods(15, applyId, ids, firmS);
    if (resultVO.getResult() == -1L) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      result = -2;
      return result;
    }
    return result;
  }

  public int auditApplyAheadSettlePass(ApplyAheadSettle applyAheadSettle) throws Exception
  {
    int result = 0;

    String procedureName = "{?=call FN_T_AheadSettleQty(?,?,?,?,?,?,?,?,?,?) }";
    Object[] params = { applyAheadSettle.getApplyId(), applyAheadSettle.getCommodityId(), applyAheadSettle.getQuantity(), applyAheadSettle.getPrice(), applyAheadSettle.getCustomerId_S(), Integer.valueOf(0), applyAheadSettle.getCustomerId_B(), Integer.valueOf(0), applyAheadSettle.getCreator(), Integer.valueOf(1) };
    result = Integer.parseInt(this.aheadSettleDao.executeProcedure("{?=call FN_T_AheadSettleQty(?,?,?,?,?,?,?,?,?,?) }", params).toString());
    if (result == 1)
    {
      this.aheadSettleDao.updateApplyAheadSettle(applyAheadSettle);
    }
    return result;
  }

  public int auditApplyAheadSettleFail(ApplyAheadSettle applyAheadSettle) throws Exception
  {
    int result = 1;

    this.aheadSettleDao.updateApplyAheadSettle(applyAheadSettle);

    String billFrozenSql = "delete T_billFrozen where Operation = '" + applyAheadSettle.getApplyId() + "'";
    this.aheadSettleDao.executeUpdateBySql(billFrozenSql);

    String sql = "select * from t_holdfrozen where Operation = '" + applyAheadSettle.getApplyId() + "' and frozentype = 0";
    List list = this.aheadSettleDao.queryBySql(sql);
    for (int i = 0; i < list.size(); i++) {
      Map map = (Map)list.get(i);
      String customerId = map.get("CUSTOMERID").toString();
      long qty = Long.parseLong(map.get("FROZENQTY").toString());
      String commodityId = map.get("COMMODITYID").toString();
      int bs_flag = Integer.parseInt(map.get("BS_FLAG").toString());

      String updateFrozenSql = "update t_customerholdsum set frozenQty = frozenQty -" + qty + " where CustomerID = '" + customerId + "' and bs_flag = " + bs_flag + " and commodityId ='" + commodityId + "'";
      this.aheadSettleDao.executeUpdateBySql(updateFrozenSql);
    }

    String holdFrozenSql = "delete t_holdfrozen where Operation = '" + applyAheadSettle.getApplyId() + "' and frozentype = 0";
    this.aheadSettleDao.executeUpdateBySql(holdFrozenSql);

    ResultVO resultVO = this.tradeService.performRealeseGoods(15, applyAheadSettle.getApplyId());
    if (resultVO.getResult() == -1L) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      result = -6;
    }
    return result;
  }
}