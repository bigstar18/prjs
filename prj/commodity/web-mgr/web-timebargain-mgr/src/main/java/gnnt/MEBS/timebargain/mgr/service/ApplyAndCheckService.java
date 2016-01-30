package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.dao.ApplyAndCheckDao;
import gnnt.MEBS.timebargain.mgr.dao.TradeParamsDao;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply;
import gnnt.MEBS.timebargain.mgr.model.apply.ApplyModel;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityFee;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityMargin;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_PledgeMoney;
import gnnt.MEBS.timebargain.mgr.model.apply.PledgeApply;
import gnnt.MEBS.timebargain.mgr.statictools.DaoHelper;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_applyAndCheckService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class ApplyAndCheckService
{
  private final transient Log logger = LogFactory.getLog(ApplyAndCheckService.class);

  @Autowired
  @Qualifier("com_tradeParamsDao")
  private TradeParamsDao dao;

  @Autowired
  @Qualifier("com_applyAndCheckDao")
  private ApplyAndCheckDao acDao;

  @Autowired
  @Qualifier("com_ajaxHelper")
  private DaoHelper daoHelper;

  public void addPledgeApp(Apply apply) { this.dao.executeFunctionApply(apply); }

  public Apply_T_PledgeMoney getApply_T_PledgeMoneyByBillID(Apply_T_PledgeMoney apply)
  {
    String sql = "select t.* from T_E_Pledge t where t.billID = '" + apply.getBillID() + "'";
    this.logger.debug("sql: " + sql);
    List list = this.dao.executeSqlQuery(sql);
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      if (map.get("FIRMID") != null) {
        apply.setFirmId(map.get("FIRMID").toString());
      }
      if (map.get("BILLID") != null) {
        apply.setBillID(map.get("BILLID").toString());
      }
      if (map.get("BILLFUND") != null) {
        apply.setBillFund(Double.parseDouble(map.get("BILLFUND").toString()));
      }
      if (map.get("COMMODITYNAME") != null) {
        apply.setCommodityName(map.get("COMMODITYNAME").toString());
      }
      if (map.get("QUANTITY") != null) {
        apply.setQuantity(Double.parseDouble(map.get("QUANTITY").toString()));
      }
    }
    return apply;
  }

  public int CheckFirmAndBillFund(ApplyModel apply, PledgeApply pledge)
  {
    String sql = "select t.MaxOverdraft MaxOverdraft from T_Firm t where t.firmID = '" + pledge.getFirmId() + "'";
    int cnt = 0;

    List list = this.dao.executeSqlQuery(sql);
    if ((list != null) && (list.size() > 0)) {
      Map map = (Map)list.get(0);
      if (map.get("MAXOVERDRAFT") != null) {
        double maxOverdraft = Double.parseDouble(map.get("MAXOVERDRAFT").toString());
        if (maxOverdraft + pledge.getBillFund() < 0.0D)
          cnt = 2;
      }
    }
    else {
      cnt = 1;
    }
    return cnt;
  }

  public int updatePledgeCheck(Apply_T_PledgeMoney apply)
  {
    int result = -1;
    int status = apply.getStatus();
    String approver = apply.getApprover();
    String note = apply.getNote();
    if ((apply != null) && (apply.getId() != 0L))
    {
      apply = (Apply_T_PledgeMoney)this.acDao.getApplyById(apply, true);
      System.out.println("Approver: " + apply.getApprover());
      int s = apply.getStatus();
      if ((s != 2) && (s != 3))
      {
        apply.setApprover(approver);
        apply.setNote(note);
        if (status == 2)
        {
          if (apply.getType() == 2) {
            result = this.acDao.deletePledgeChe(apply);
          }
          if (apply.getType() == 1) {
            result = this.acDao.CheckFirmAndBillFund(apply);
            if (result == 0) {
              this.acDao.updatePledge(apply);
            }
          }

        }
        else
        {
          result = 0;
        }

        if (result == 0)
        {
          apply.setStatus(status);
          this.acDao.updateApply(apply);
        }
      }
    }

    return result;
  }

  public int updateCommodityFeeCheck(Apply_T_CommodityFee app)
  {
    int result = -1;
    int status = app.getStatus();
    String approver = app.getApprover();
    String note = app.getNote();
    if ((app != null) && (app.getId() != 0L))
    {
      app = (Apply_T_CommodityFee)this.acDao.getApplyById(app, true);
      if ((app != null) && (app.getFeeAlgr().shortValue() == 1)) {
        app.setFeeRate_B(Double.valueOf(app.getFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        app.setFeeRate_S(Double.valueOf(app.getFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        app.setHistoryCloseFeeRate_B(Double.valueOf(app.getHistoryCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        app.setHistoryCloseFeeRate_S(Double.valueOf(app.getHistoryCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        app.setTodayCloseFeeRate_B(Double.valueOf(app.getTodayCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        app.setTodayCloseFeeRate_S(Double.valueOf(app.getTodayCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        app.setForceCloseFeeRate_B(Double.valueOf(app.getForceCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        app.setForceCloseFeeRate_S(Double.valueOf(app.getForceCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }

      if ((app != null) && (app.getSettleFeeAlgr().shortValue() == 1)) {
        app.setSettleFeeRate_B(Double.valueOf(app.getSettleFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        app.setSettleFeeRate_S(Double.valueOf(app.getSettleFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }

      int s = app.getStatus();
      if ((s != 2) && (s != 3)) {
        app.setApprover(approver);
        app.setNote(note);
        if (status == 2) {
          result = this.acDao.checkCommodityID(app);
          if (result == 0)
            this.acDao.updateCommodityFee(app);
        }
        else {
          result = 0;
        }

        if (result == 0)
        {
          app.setStatus(status);
          this.acDao.updateApply(app);
        }
      }
    }
    return result;
  }

  public int updateCommodityMarginCheck(Apply_T_CommodityMargin app)
  {
    int result = -1;
    int status = app.getStatus();
    String approver = app.getApprover();
    String note = app.getNote();
    if ((app != null) && (app.getId() != 0L))
    {
      app = (Apply_T_CommodityMargin)this.acDao.getApplyById(app, true);
      if (app != null) {
        if ((app.getMarginAlgr() != null) && ("1".equals(app.getMarginAlgr().toString()))) {
          if (app.getMarginItem1() != null) {
            app.setMarginItem1(Double.valueOf(app.getMarginItem1().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem2() != null) {
            app.setMarginItem2(Double.valueOf(app.getMarginItem2().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem3() != null) {
            app.setMarginItem3(Double.valueOf(app.getMarginItem3().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem4() != null) {
            app.setMarginItem4(Double.valueOf(app.getMarginItem4().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem5() != null) {
            app.setMarginItem5(Double.valueOf(app.getMarginItem5().doubleValue() / new Double(100.0D).doubleValue()));
          }

          if (app.getMarginItem1_S() != null) {
            app.setMarginItem1_S(Double.valueOf(app.getMarginItem1_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem2_S() != null) {
            app.setMarginItem2_S(Double.valueOf(app.getMarginItem2_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem3_S() != null) {
            app.setMarginItem3_S(Double.valueOf(app.getMarginItem3_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem4_S() != null) {
            app.setMarginItem4_S(Double.valueOf(app.getMarginItem4_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItem5_S() != null) {
            app.setMarginItem5_S(Double.valueOf(app.getMarginItem5_S().doubleValue() / new Double(100.0D).doubleValue()));
          }

          if (app.getMarginItemAssure1() != null) {
            app.setMarginItemAssure1(Double.valueOf(app.getMarginItemAssure1().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure2() != null) {
            app.setMarginItemAssure2(Double.valueOf(app.getMarginItemAssure2().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure3() != null) {
            app.setMarginItemAssure3(Double.valueOf(app.getMarginItemAssure3().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure4() != null) {
            app.setMarginItemAssure4(Double.valueOf(app.getMarginItemAssure4().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure5() != null) {
            app.setMarginItemAssure5(Double.valueOf(app.getMarginItemAssure5().doubleValue() / new Double(100.0D).doubleValue()));
          }

          if (app.getMarginItemAssure1_S() != null) {
            app.setMarginItemAssure1_S(Double.valueOf(app.getMarginItemAssure1_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure2_S() != null) {
            app.setMarginItemAssure2_S(Double.valueOf(app.getMarginItemAssure2_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure3_S() != null) {
            app.setMarginItemAssure3_S(Double.valueOf(app.getMarginItemAssure3_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure4_S() != null) {
            app.setMarginItemAssure4_S(Double.valueOf(app.getMarginItemAssure4_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (app.getMarginItemAssure5_S() != null) {
            app.setMarginItemAssure5_S(Double.valueOf(app.getMarginItemAssure5_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
        }

        if ((app.getSettleMarginAlgr_B() != null) && ("1".equals(app.getSettleMarginAlgr_B().toString()))) {
          app.setSettleMarginRate_B(Double.valueOf(app.getSettleMarginRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if ((app.getSettleMarginAlgr_S() != null) && ("1".equals(app.getSettleMarginAlgr_S().toString()))) {
          app.setSettleMarginRate_S(Double.valueOf(app.getSettleMarginRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if ((app.getPayoutAlgr() != null) && ("1".equals(app.getPayoutAlgr().toString()))) {
          app.setPayoutRate(Double.valueOf(app.getPayoutRate().doubleValue() / new Double(100.0D).doubleValue()));
        }
      }

      int s = app.getStatus();
      if ((s != 2) && (s != 3)) {
        app.setApprover(approver);
        app.setNote(note);
        if (status == 2) {
          result = this.acDao.checkMarginCommodityID(app);
          System.out.println("result im: " + result);
          if (result == 0)
            this.acDao.updateCommodityMargin(app);
        }
        else {
          result = 0;
        }

        if (result == 0)
        {
          app.setStatus(status);
          this.acDao.updateApply(app);
        }
      }
    }
    return result;
  }

  public List queryBySQL(String sql) throws Exception {
    return this.daoHelper.queryBySQL(sql);
  }
}