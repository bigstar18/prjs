package gnnt.MEBS.common.manage.service.impl;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.dao.CommodityDAO;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityFee;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityMargin;
import gnnt.MEBS.common.manage.service.CommodityManage;
import java.io.PrintStream;

public class CommodityManageImpl
  implements CommodityManage
{
  private ApplyDAO applyDAO;
  private CommodityDAO commodityDAO;
  
  public void setApplyDAO(ApplyDAO paramApplyDAO)
  {
    this.applyDAO = paramApplyDAO;
  }
  
  public void setCommodityDAO(CommodityDAO paramCommodityDAO)
  {
    this.commodityDAO = paramCommodityDAO;
  }
  
  public int updateCommodityFeeCheck(Apply_T_CommodityFee paramApply_T_CommodityFee)
  {
    int i = -1;
    int j = paramApply_T_CommodityFee.getStatus();
    String str1 = paramApply_T_CommodityFee.getApprover();
    String str2 = paramApply_T_CommodityFee.getNote();
    if ((paramApply_T_CommodityFee != null) && (paramApply_T_CommodityFee.getId() != 0L))
    {
      paramApply_T_CommodityFee = (Apply_T_CommodityFee)this.applyDAO.getApplyById(paramApply_T_CommodityFee, true);
      if ((paramApply_T_CommodityFee != null) && (paramApply_T_CommodityFee.getFeeAlgr().shortValue() == 1))
      {
        paramApply_T_CommodityFee.setFeeRate_B(Double.valueOf(paramApply_T_CommodityFee.getFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setFeeRate_S(Double.valueOf(paramApply_T_CommodityFee.getFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setHistoryCloseFeeRate_B(Double.valueOf(paramApply_T_CommodityFee.getHistoryCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setHistoryCloseFeeRate_S(Double.valueOf(paramApply_T_CommodityFee.getHistoryCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setTodayCloseFeeRate_B(Double.valueOf(paramApply_T_CommodityFee.getTodayCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setTodayCloseFeeRate_S(Double.valueOf(paramApply_T_CommodityFee.getTodayCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setForceCloseFeeRate_B(Double.valueOf(paramApply_T_CommodityFee.getForceCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setForceCloseFeeRate_S(Double.valueOf(paramApply_T_CommodityFee.getForceCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }
      if ((paramApply_T_CommodityFee != null) && (paramApply_T_CommodityFee.getSettleFeeAlgr().shortValue() == 1))
      {
        paramApply_T_CommodityFee.setSettleFeeRate_B(Double.valueOf(paramApply_T_CommodityFee.getSettleFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        paramApply_T_CommodityFee.setSettleFeeRate_S(Double.valueOf(paramApply_T_CommodityFee.getSettleFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }
      int k = paramApply_T_CommodityFee.getStatus();
      if ((k != 2) && (k != 3))
      {
        paramApply_T_CommodityFee.setApprover(str1);
        paramApply_T_CommodityFee.setNote(str2);
        if (j == 2)
        {
          i = this.commodityDAO.checkCommodityID(paramApply_T_CommodityFee);
          if (i == 0) {
            this.commodityDAO.updateCommodityFee(paramApply_T_CommodityFee);
          }
        }
        else
        {
          i = 0;
        }
        if (i == 0)
        {
          paramApply_T_CommodityFee.setStatus(j);
          this.applyDAO.updateApply(paramApply_T_CommodityFee);
        }
      }
    }
    return i;
  }
  
  public int updateCommodityMarginCheck(Apply_T_CommodityMargin paramApply_T_CommodityMargin)
  {
    int i = -1;
    int j = paramApply_T_CommodityMargin.getStatus();
    String str1 = paramApply_T_CommodityMargin.getApprover();
    String str2 = paramApply_T_CommodityMargin.getNote();
    if ((paramApply_T_CommodityMargin != null) && (paramApply_T_CommodityMargin.getId() != 0L))
    {
      paramApply_T_CommodityMargin = (Apply_T_CommodityMargin)this.applyDAO.getApplyById(paramApply_T_CommodityMargin, true);
      if (paramApply_T_CommodityMargin != null)
      {
        if ((paramApply_T_CommodityMargin.getMarginAlgr() != null) && ("1".equals(paramApply_T_CommodityMargin.getMarginAlgr().toString())))
        {
          if (paramApply_T_CommodityMargin.getMarginItem1() != null) {
            paramApply_T_CommodityMargin.setMarginItem1(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem1().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem2() != null) {
            paramApply_T_CommodityMargin.setMarginItem2(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem2().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem3() != null) {
            paramApply_T_CommodityMargin.setMarginItem3(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem3().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem4() != null) {
            paramApply_T_CommodityMargin.setMarginItem4(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem4().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem5() != null) {
            paramApply_T_CommodityMargin.setMarginItem5(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem5().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem1_S() != null) {
            paramApply_T_CommodityMargin.setMarginItem1_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem1_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem2_S() != null) {
            paramApply_T_CommodityMargin.setMarginItem2_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem2_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem3_S() != null) {
            paramApply_T_CommodityMargin.setMarginItem3_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem3_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem4_S() != null) {
            paramApply_T_CommodityMargin.setMarginItem4_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem4_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItem5_S() != null) {
            paramApply_T_CommodityMargin.setMarginItem5_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItem5_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure1() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure1(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure1().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure2() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure2(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure2().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure3() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure3(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure3().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure4() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure4(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure4().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure5() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure5(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure5().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure1_S() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure1_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure1_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure2_S() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure2_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure2_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure3_S() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure3_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure3_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure4_S() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure4_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure4_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
          if (paramApply_T_CommodityMargin.getMarginItemAssure5_S() != null) {
            paramApply_T_CommodityMargin.setMarginItemAssure5_S(Double.valueOf(paramApply_T_CommodityMargin.getMarginItemAssure5_S().doubleValue() / new Double(100.0D).doubleValue()));
          }
        }
        if ((paramApply_T_CommodityMargin.getSettleMarginAlgr_B() != null) && ("1".equals(paramApply_T_CommodityMargin.getSettleMarginAlgr_B().toString()))) {
          paramApply_T_CommodityMargin.setSettleMarginRate_B(Double.valueOf(paramApply_T_CommodityMargin.getSettleMarginRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if ((paramApply_T_CommodityMargin.getSettleMarginAlgr_S() != null) && ("1".equals(paramApply_T_CommodityMargin.getSettleMarginAlgr_S().toString()))) {
          paramApply_T_CommodityMargin.setSettleMarginRate_S(Double.valueOf(paramApply_T_CommodityMargin.getSettleMarginRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if ((paramApply_T_CommodityMargin.getPayoutAlgr() != null) && ("1".equals(paramApply_T_CommodityMargin.getPayoutAlgr().toString()))) {
          paramApply_T_CommodityMargin.setPayoutRate(Double.valueOf(paramApply_T_CommodityMargin.getPayoutRate().doubleValue() / new Double(100.0D).doubleValue()));
        }
      }
      int k = paramApply_T_CommodityMargin.getStatus();
      if ((k != 2) && (k != 3))
      {
        paramApply_T_CommodityMargin.setApprover(str1);
        paramApply_T_CommodityMargin.setNote(str2);
        if (j == 2)
        {
          i = this.commodityDAO.checkMarginCommodityID(paramApply_T_CommodityMargin);
          System.out.println("result im: " + i);
          if (i == 0) {
            this.commodityDAO.updateCommodityMargin(paramApply_T_CommodityMargin);
          }
        }
        else
        {
          i = 0;
        }
        if (i == 0)
        {
          paramApply_T_CommodityMargin.setStatus(j);
          this.applyDAO.updateApply(paramApply_T_CommodityMargin);
        }
      }
    }
    return i;
  }
}
