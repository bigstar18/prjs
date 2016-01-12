package gnnt.MEBS.timebargain.mgr.action.tradeparams;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.timebargain.mgr.exception.DeleteCheckException;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Breed;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class BreedAction extends EcsideAction
{
  private static final long serialVersionUID = 1571997273637816072L;

  @Autowired
  @Qualifier("com_tradeParamsService")
  private TradeParamsService tps;
  private String opr;

  public String getOpr()
  {
    return this.opr;
  }

  public void setOpr(String paramString)
  {
    this.opr = paramString;
  }

  public String listResults()
    throws Exception
  {
    this.logger.debug("enter listResults");
    List localList = this.tps.executeSelect(getOpr());
    this.request.setAttribute("resultList", localList);
    return listByLimit();
  }

  public String viewByIdBreed()
    throws Exception
  {
    this.logger.debug("enter viewByIdBreed");
    Breed localBreed = new Breed();
    String str1 = this.request.getParameter("breedID");
    localBreed.setBreedID(Long.valueOf(Long.parseLong(str1)));
    localBreed = (Breed)getService().get(localBreed);
    modifyBreedByMul(localBreed);
    String str2 = "1";
    String str3 = "1";
    String str4 = "1";
    String str5 = "1";
    String str6 = "1";
    if ((localBreed.getLimitBreedQty() != null) && ("-1".equals(localBreed.getLimitBreedQty().toString())))
      str2 = "2";
    if ((localBreed.getLimitCmdtyQty() != null) && ("-1".equals(localBreed.getLimitCmdtyQty().toString())))
      str3 = "2";
    if ((localBreed.getFirmCleanQty() != null) && ("-1".equals(localBreed.getFirmCleanQty().toString())))
      str4 = "2";
    if ((localBreed.getFirmMaxHoldQty() != null) && ("-1".equals(localBreed.getFirmMaxHoldQty().toString())))
      str5 = "2";
    if ((localBreed.getOneMaxHoldQty() != null) && ("-1".equals(localBreed.getOneMaxHoldQty().toString())))
      str6 = "2";
    this.request.setAttribute("type101", str2);
    this.request.setAttribute("type102", str3);
    this.request.setAttribute("type103", str4);
    this.request.setAttribute("type104", str5);
    this.request.setAttribute("type105", str6);
    this.request.setAttribute("typeFeeAlgr", localBreed.getFeeAlgr().toString());
    this.request.setAttribute("typeSettleFeeAlgr", localBreed.getSettleFeeAlgr().toString());
    this.entity = localBreed;
    return "success";
  }

  public String addBreed()
    throws Exception
  {
    this.logger.debug("enter BreedAction's addBreed");
    Breed localBreed = (Breed)this.entity;
    modifyBreedByDiv(localBreed);
    this.tps.add(this.entity);
    addReturnValue(1, 119901L);
    writeOperateLog(1504, "添加品种，代码：" + localBreed.getBreedID(), 1, "");
    return "success";
  }

  public String updateBreed()
    throws Exception
  {
    this.logger.debug("enter BreedAction's updateBreed");
    Breed localBreed = (Breed)this.entity;
    if (localBreed.getHoldDaysLimit().intValue() == 0)
      localBreed.setMaxHoldPositionDay(null);
    modifyBreedByDiv(localBreed);
    this.tps.update(this.entity);
    addReturnValue(1, 119902L);
    writeOperateLog(1504, "修改品种，代码：" + localBreed.getBreedID(), 1, "");
    return "success";
  }

  public String deleteBreed()
    throws Exception
  {
    this.logger.debug("enter BreedAction's deleteBreed");
    try
    {
      List localList = (List)this.request.getAttribute("deletesList");
      this.tps.deleteBreed(localList);
      addReturnValue(1, 119903L);
    }
    catch (Exception localException)
    {
      if ((localException instanceof DeleteCheckException))
        addReturnValue(-1, 151102L, new Object[] { localException.getMessage() });
      else
        throw localException;
    }
    return "success";
  }

  private void modifyBreedByMul(Breed paramBreed)
  {
    if (paramBreed.getSpreadAlgr().shortValue() == 1)
    {
      paramBreed.setSpreadUpLmt(Double.valueOf(Arith.mul(paramBreed.getSpreadUpLmt().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setSpreadDownLmt(Double.valueOf(Arith.mul(paramBreed.getSpreadDownLmt().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if ((paramBreed.getFirmMaxHoldQtyAlgr() != null) && (paramBreed.getFirmMaxHoldQtyAlgr().shortValue() == 1))
      paramBreed.setMaxPercentLimit(Double.valueOf(Arith.mul(paramBreed.getMaxPercentLimit().doubleValue(), 100.0F)));
    if (paramBreed.getFeeAlgr().shortValue() == 1)
    {
      paramBreed.setFeeRate_B(Double.valueOf(Arith.mul(paramBreed.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setFeeRate_S(Double.valueOf(Arith.mul(paramBreed.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setHistoryCloseFeeRate_B(Double.valueOf(Arith.mul(paramBreed.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setHistoryCloseFeeRate_S(Double.valueOf(Arith.mul(paramBreed.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setTodayCloseFeeRate_B(Double.valueOf(Arith.mul(paramBreed.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setTodayCloseFeeRate_S(Double.valueOf(Arith.mul(paramBreed.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setForceCloseFeeRate_B(Double.valueOf(Arith.mul(paramBreed.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setForceCloseFeeRate_S(Double.valueOf(Arith.mul(paramBreed.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (paramBreed.getSettleFeeAlgr().shortValue() == 1)
    {
      paramBreed.setSettleFeeRate_B(Double.valueOf(Arith.mul(paramBreed.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setSettleFeeRate_S(Double.valueOf(Arith.mul(paramBreed.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (paramBreed.getSettleMarginAlgr_B().shortValue() == 1)
      paramBreed.setSettleMarginRate_B(Double.valueOf(Arith.mul(paramBreed.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
    if (paramBreed.getSettleMarginAlgr_S().shortValue() == 1)
      paramBreed.setSettleMarginRate_S(Double.valueOf(Arith.mul(paramBreed.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    if (paramBreed.getPayoutAlgr().shortValue() == 1)
      paramBreed.setPayoutRate(Double.valueOf(Arith.mul(paramBreed.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
    if (paramBreed.getMarginAlgr().shortValue() == 1)
    {
      paramBreed.setMarginItem1(Double.valueOf(Arith.mul(paramBreed.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setMarginItem1_S(Double.valueOf(Arith.mul(paramBreed.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setMarginItemAssure1(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setMarginItemAssure1_S(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
      if (paramBreed.getMarginItem2() != null)
      {
        paramBreed.setMarginItem2(Double.valueOf(Arith.mul(paramBreed.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem2_S(Double.valueOf(Arith.mul(paramBreed.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure2(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure2_S(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (paramBreed.getMarginItem3() != null)
      {
        paramBreed.setMarginItem3(Double.valueOf(Arith.mul(paramBreed.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem3_S(Double.valueOf(Arith.mul(paramBreed.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure3(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure3_S(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (paramBreed.getMarginItem4() != null)
      {
        paramBreed.setMarginItem4(Double.valueOf(Arith.mul(paramBreed.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem4_S(Double.valueOf(Arith.mul(paramBreed.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure4(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure4_S(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (paramBreed.getMarginItem5() != null)
      {
        paramBreed.setMarginItem5(Double.valueOf(Arith.mul(paramBreed.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem5_S(Double.valueOf(Arith.mul(paramBreed.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure5(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure5_S(Double.valueOf(Arith.mul(paramBreed.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
    }
    paramBreed.setAddedTax(Double.valueOf(Arith.mul(paramBreed.getAddedTax().doubleValue(), new Double(100.0D).doubleValue())));
    paramBreed.setDelayRecoupRate(Double.valueOf(Arith.mul(paramBreed.getDelayRecoupRate().doubleValue(), new Double(100.0D).doubleValue())));
    paramBreed.setDelayRecoupRate_S(Double.valueOf(Arith.mul(paramBreed.getDelayRecoupRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    paramBreed.setMaxFeeRate(Double.valueOf(Arith.mul((paramBreed.getMaxFeeRate() == null ? new Double(0.0D) : paramBreed.getMaxFeeRate()).doubleValue(), new Double(100.0D).doubleValue())));
  }

  private void modifyBreedByDiv(Breed paramBreed)
  {
    if (paramBreed.getSpreadAlgr().shortValue() == 1)
    {
      paramBreed.setSpreadUpLmt(Double.valueOf(Arith.div(paramBreed.getSpreadUpLmt().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setSpreadDownLmt(Double.valueOf(Arith.div(paramBreed.getSpreadDownLmt().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if ((paramBreed.getFirmMaxHoldQtyAlgr() != null) && (paramBreed.getFirmMaxHoldQtyAlgr().shortValue() == 1))
      paramBreed.setMaxPercentLimit(Double.valueOf(Arith.div(paramBreed.getMaxPercentLimit().doubleValue(), 100.0F)));
    if (paramBreed.getFeeAlgr().shortValue() == 1)
    {
      paramBreed.setFeeRate_B(Double.valueOf(Arith.div(paramBreed.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setFeeRate_S(Double.valueOf(Arith.div(paramBreed.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setHistoryCloseFeeRate_B(Double.valueOf(Arith.div(paramBreed.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setHistoryCloseFeeRate_S(Double.valueOf(Arith.div(paramBreed.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setTodayCloseFeeRate_B(Double.valueOf(Arith.div(paramBreed.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setTodayCloseFeeRate_S(Double.valueOf(Arith.div(paramBreed.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setForceCloseFeeRate_B(Double.valueOf(Arith.div(paramBreed.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setForceCloseFeeRate_S(Double.valueOf(Arith.div(paramBreed.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (paramBreed.getSettleFeeAlgr().shortValue() == 1)
    {
      paramBreed.setSettleFeeRate_B(Double.valueOf(Arith.div(paramBreed.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setSettleFeeRate_S(Double.valueOf(Arith.div(paramBreed.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (paramBreed.getSettleMarginAlgr_B().shortValue() == 1)
      paramBreed.setSettleMarginRate_B(Double.valueOf(Arith.div(paramBreed.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
    if (paramBreed.getSettleMarginAlgr_S().shortValue() == 1)
      paramBreed.setSettleMarginRate_S(Double.valueOf(Arith.div(paramBreed.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    if (paramBreed.getPayoutAlgr().shortValue() == 1)
      paramBreed.setPayoutRate(Double.valueOf(Arith.div(paramBreed.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
    if (paramBreed.getMarginAlgr().shortValue() == 1)
    {
      paramBreed.setMarginItem1(Double.valueOf(Arith.div(paramBreed.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setMarginItem1_S(Double.valueOf(Arith.div(paramBreed.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setMarginItemAssure1(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
      paramBreed.setMarginItemAssure1_S(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
      if (paramBreed.getMarginItem2() != null)
      {
        paramBreed.setMarginItem2(Double.valueOf(Arith.div(paramBreed.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem2_S(Double.valueOf(Arith.div(paramBreed.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure2(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure2_S(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (paramBreed.getMarginItem3() != null)
      {
        paramBreed.setMarginItem3(Double.valueOf(Arith.div(paramBreed.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem3_S(Double.valueOf(Arith.div(paramBreed.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure3(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure3_S(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (paramBreed.getMarginItem4() != null)
      {
        paramBreed.setMarginItem4(Double.valueOf(Arith.div(paramBreed.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem4_S(Double.valueOf(Arith.div(paramBreed.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure4(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure4_S(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (paramBreed.getMarginItem5() != null)
      {
        paramBreed.setMarginItem5(Double.valueOf(Arith.div(paramBreed.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItem5_S(Double.valueOf(Arith.div(paramBreed.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure5(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
        paramBreed.setMarginItemAssure5_S(Double.valueOf(Arith.div(paramBreed.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
    }
    paramBreed.setAddedTax(Double.valueOf(Arith.div(paramBreed.getAddedTax().doubleValue(), new Double(100.0D).doubleValue())));
    paramBreed.setDelayRecoupRate(Double.valueOf(Arith.div(paramBreed.getDelayRecoupRate().doubleValue(), new Double(100.0D).doubleValue())));
    paramBreed.setDelayRecoupRate_S(Double.valueOf(Arith.div(paramBreed.getDelayRecoupRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    paramBreed.setMaxFeeRate(Double.valueOf(Arith.div((paramBreed.getMaxFeeRate() == null ? new Double(0.0D) : paramBreed.getMaxFeeRate()).doubleValue(), new Double(100.0D).doubleValue())));
  }
}