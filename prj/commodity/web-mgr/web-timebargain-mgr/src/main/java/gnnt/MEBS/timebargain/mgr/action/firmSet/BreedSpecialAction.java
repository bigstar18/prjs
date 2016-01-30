package gnnt.MEBS.timebargain.mgr.action.firmSet;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmBreedMaxHoldQty;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmBreedSettleFee;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmBreedSettleMargin;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmBreedTradeFee;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmBreedTradeMargin;
import gnnt.MEBS.timebargain.mgr.service.CommodityIdService;
import gnnt.MEBS.timebargain.mgr.service.FirmSpecialService;
import gnnt.MEBS.timebargain.mgr.service.TradePrivilegeService;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

@Controller("breedSpecialAction")
@Scope("request")
public class BreedSpecialAction extends EcsideAction
{
  private static final long serialVersionUID = 304221715594694424L;

  @Resource(name="algrMap")
  private Map<Integer, String> algrMap;

  @Autowired
  @Qualifier("tradePrivilegeService")
  private TradePrivilegeService tradePrivilegeService;

  @Autowired
  @Qualifier("firmSpecialService")
  private FirmSpecialService firmSpecialService;

  @Autowired
  @Qualifier("commodityIdService")
  private CommodityIdService commodityIdService;

  public String fowardAddBreedSpecial()
  {
    this.logger.debug("enter fowardAddBreedSpecial");

    List breedList = this.tradePrivilegeService.getBreed();

    this.request.setAttribute("breedList", breedList);

    return "success";
  }

  private boolean existBreedSpecial(String firmID, int breedID)
  {
    boolean result = false;

    PageRequest pageRequest = new PageRequest(" and primary.firmID='" + firmID + "' and primary.breedID=" + breedID);
    Page page = getService().getPage(pageRequest, this.entity);
    if ((page.getResult() != null) && (page.getResult().size() > 0)) {
      result = true;
    }
    return result;
  }

  public String addTradeMargin()
    throws Exception
  {
    this.logger.debug("enter addTradeMargin");

    FirmBreedTradeMargin tradeMargin = (FirmBreedTradeMargin)this.entity;
    boolean flag = existBreedSpecial(tradeMargin.getFirmID(), tradeMargin.getBreedID().intValue());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊品种交易保证金失败，已存在！" });
    }
    else {
      FirmBreedTradeMargin firmBreedTradeMargin = (FirmBreedTradeMargin)this.entity;

      if (firmBreedTradeMargin.getMarginAlgr().intValue() == 1) {
        firmBreedTradeMargin.setMarginItem1(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
        firmBreedTradeMargin.setMarginItem1_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmBreedTradeMargin.setMarginItemAssure1(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
        firmBreedTradeMargin.setMarginItemAssure1_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
        if (firmBreedTradeMargin.getMarginItem2() != null) {
          firmBreedTradeMargin.setMarginItem2(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItem2_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure2(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure2_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
        if (firmBreedTradeMargin.getMarginItem3() != null) {
          firmBreedTradeMargin.setMarginItem3(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItem3_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure3(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure3_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
        if (firmBreedTradeMargin.getMarginItem4() != null) {
          firmBreedTradeMargin.setMarginItem4(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItem4_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure4(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure4_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
        if (firmBreedTradeMargin.getMarginItem5() != null) {
          firmBreedTradeMargin.setMarginItem5(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItem5_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure5(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeMargin.setMarginItemAssure5_S(Double.valueOf(Arith.div(firmBreedTradeMargin.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
      }

      firmBreedTradeMargin.setModifyTime(getService().getSysDate());

      getService().add(firmBreedTradeMargin);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊品种交易保证金！交易商ID：" + firmBreedTradeMargin.getFirmID() + ",品种ID：" + firmBreedTradeMargin.getBreedID(), 1, "");
    }

    return "success";
  }

  public String addSettleMargin()
    throws Exception
  {
    this.logger.debug("enter addSettleMargin");

    FirmBreedSettleMargin settleMargin = (FirmBreedSettleMargin)this.entity;
    boolean flag = existBreedSpecial(settleMargin.getFirmID(), settleMargin.getBreedID().intValue());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊品种交收保证金失败，已存在！" });
    }
    else {
      FirmBreedSettleMargin firmBreedSettleMargin = (FirmBreedSettleMargin)this.entity;

      if (firmBreedSettleMargin.getSettleMarginAlgr_B().intValue() == 1) {
        firmBreedSettleMargin.setSettleMarginRate_B(Double.valueOf(Arith.div(firmBreedSettleMargin.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmBreedSettleMargin.getSettleMarginAlgr_S().intValue() == 1) {
        firmBreedSettleMargin.setSettleMarginRate_S(Double.valueOf(Arith.div(firmBreedSettleMargin.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmBreedSettleMargin.getPayoutAlgr().intValue() == 1) {
        firmBreedSettleMargin.setPayoutRate(Double.valueOf(Arith.div(firmBreedSettleMargin.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
      }

      firmBreedSettleMargin.setModifyTime(getService().getSysDate());

      getService().add(firmBreedSettleMargin);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊品种交收保证金！交易商ID：" + firmBreedSettleMargin.getFirmID() + ",品种ID：" + firmBreedSettleMargin.getBreedID(), 1, "");
    }

    return "success";
  }

  public String addMaxHoldQty()
    throws Exception
  {
    this.logger.debug("enter addMaxHoldQty");

    FirmBreedMaxHoldQty maxHoldQty = (FirmBreedMaxHoldQty)this.entity;
    boolean flag = existBreedSpecial(maxHoldQty.getFirmID(), maxHoldQty.getBreedID().intValue());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊品种订货量失败，已存在！" });
    }
    else {
      FirmBreedMaxHoldQty firmBreedMaxHoldQty = (FirmBreedMaxHoldQty)this.entity;

      firmBreedMaxHoldQty.setModifyTime(getService().getSysDate());

      getService().add(firmBreedMaxHoldQty);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊品种订货量！交易商ID：" + firmBreedMaxHoldQty.getFirmID() + ",品种ID：" + firmBreedMaxHoldQty.getBreedID(), 1, "");
    }

    return "success";
  }

  public String addTradeFee()
    throws Exception
  {
    this.logger.debug("enter addMaxHoldQty");

    FirmBreedTradeFee tradeFee = (FirmBreedTradeFee)this.entity;

    if ((tradeFee.getFirmID() != null) && (!"".equals(tradeFee.getFirmID()))) {
      boolean flag = existBreedSpecial(tradeFee.getFirmID(), tradeFee.getBreedID().intValue());
      if (flag) {
        addReturnValue(-1, 151103L, new Object[] { "添加特殊品种交易手续费失败，已存在！" });
      }
      else {
        FirmBreedTradeFee firmBreedTradeFee = (FirmBreedTradeFee)this.entity;

        if (firmBreedTradeFee.getFeeAlgr().intValue() == 1) {
          firmBreedTradeFee.setFeeRate_B(Double.valueOf(Arith.div(firmBreedTradeFee.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setFeeRate_S(Double.valueOf(Arith.div(firmBreedTradeFee.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setHistoryCloseFeeRate_B(Double.valueOf(Arith.div(firmBreedTradeFee.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setHistoryCloseFeeRate_S(Double.valueOf(Arith.div(firmBreedTradeFee.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setTodayCloseFeeRate_B(Double.valueOf(Arith.div(firmBreedTradeFee.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setTodayCloseFeeRate_S(Double.valueOf(Arith.div(firmBreedTradeFee.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setForceCloseFeeRate_B(Double.valueOf(Arith.div(firmBreedTradeFee.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmBreedTradeFee.setForceCloseFeeRate_S(Double.valueOf(Arith.div(firmBreedTradeFee.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
        }

        firmBreedTradeFee.setModifyTime(getService().getSysDate());

        getService().add(firmBreedTradeFee);

        addReturnValue(1, 119901L);

        writeOperateLog(1505, "添加特殊品种交易手续费！交易商ID：" + firmBreedTradeFee.getFirmID() + ",品种ID：" + firmBreedTradeFee.getBreedID(), 1, "");
      }
    }
    else {
      List list = this.commodityIdService.firmIdList("desc");
      for (int i = 0; i < list.size(); i++) {
        Map map = (Map)list.get(i);
        Collection coll = map.values();
        Iterator iter = coll.iterator();

        tradeFee.setFirmID((String)iter.next());
        boolean flag = existBreedSpecial(tradeFee.getFirmID(), tradeFee.getBreedID().intValue());
        if (flag) {
          System.out.println("nihao");
        }
        else
        {
          FirmBreedTradeFee firmTradeFee1 = new FirmBreedTradeFee();
          BeanUtils.copyProperties(tradeFee, firmTradeFee1);

          firmTradeFee1.setModifyTime(getService().getSysDate());

          getService().add(firmTradeFee1);

          addReturnValue(1, 119901L);

          writeOperateLog(1505, "添加特殊商品交易手续费！交易商ID：" + firmTradeFee1.getFirmID() + ",商品ID：" + firmTradeFee1.getBreedID(), 1, "");
        }
      }

    }

    return "success";
  }

  public String addSettleFee()
    throws Exception
  {
    this.logger.debug("enter addMaxHoldQty");

    FirmBreedSettleFee settleFee = (FirmBreedSettleFee)this.entity;
    boolean flag = existBreedSpecial(settleFee.getFirmID(), settleFee.getBreedID().intValue());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊品种交收手续费失败，已存在！" });
    }
    else {
      FirmBreedSettleFee firmBreedSettleFee = (FirmBreedSettleFee)this.entity;

      if (firmBreedSettleFee.getSettleFeeAlgr().intValue() == 1) {
        firmBreedSettleFee.setSettleFeeRate_B(Double.valueOf(Arith.div(firmBreedSettleFee.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
        firmBreedSettleFee.setSettleFeeRate_S(Double.valueOf(Arith.div(firmBreedSettleFee.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      }

      firmBreedSettleFee.setModifyTime(getService().getSysDate());

      getService().add(firmBreedSettleFee);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊品种交收手续费！交易商ID：" + firmBreedSettleFee.getFirmID() + ",品种ID：" + firmBreedSettleFee.getBreedID(), 1, "");
    }

    return "success";
  }

  public String deleteTradeMargin()
  {
    this.logger.debug("enter deleteTradeMargin");

    String[] ids = this.request.getParameterValues("ids");

    if (ids != null)
    {
      String prompt = "";
      int count = 0;
      for (int i = 0; i < ids.length; i++) {
        String[] idArr = ids[i].split(",");
        String firmID = idArr[0];
        String breedID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmBreedTradeMargin tradeMargin = new FirmBreedTradeMargin();
          tradeMargin.setFirmID(firmID);
          tradeMargin.setBreedID(Integer.valueOf(Integer.parseInt(breedID)));
          getService().delete(tradeMargin);

          writeOperateLog(1505, "删除特殊品种交易保证金! 交易商ID：" + tradeMargin.getFirmID() + ",品种ID：" + tradeMargin.getBreedID(), 1, "");
        } catch (DataIntegrityViolationException e) {
          e.getStackTrace();
          prompt = prompt + ids[i] + ",";
          addReturnValue(-1, 151103L, new Object[] { "[" + ids[i] + "]与其他数据关联，删除失败！" });
          count++;
        }
      }
      if (!prompt.equals(""))
      {
        prompt = prompt.substring(0, prompt.length() - 1);
        prompt = prompt + "与其他数据关联，不能删除！";
        addReturnValue(0, 151103L, new Object[] { prompt });
        count++;
      }
      if (count == 0)
      {
        addReturnValue(1, 119903L);
      }
    }
    else {
      addReturnValue(-1, 151103L, new Object[] { "删除主键为空或错误，请修改~！" });
    }

    return "success";
  }

  public String deleteSettleMargin()
  {
    this.logger.debug("enter deleteSettleMargin");

    String[] ids = this.request.getParameterValues("ids");

    if (ids != null)
    {
      String prompt = "";
      int count = 0;
      for (int i = 0; i < ids.length; i++) {
        String[] idArr = ids[i].split(",");
        String firmID = idArr[0];
        String breedID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmBreedSettleMargin settleMargin = new FirmBreedSettleMargin();
          settleMargin.setFirmID(firmID);
          settleMargin.setBreedID(Integer.valueOf(Integer.parseInt(breedID)));
          getService().delete(settleMargin);

          writeOperateLog(1505, "删除特殊品种交收保证金! 交易商ID：" + settleMargin.getFirmID() + ",品种ID：" + settleMargin.getBreedID(), 1, "");
        } catch (DataIntegrityViolationException e) {
          e.getStackTrace();
          prompt = prompt + ids[i] + ",";
          addReturnValue(-1, 151103L, new Object[] { "[" + ids[i] + "]与其他数据关联，删除失败！" });
          count++;
        }
      }
      if (!prompt.equals(""))
      {
        prompt = prompt.substring(0, prompt.length() - 1);
        prompt = prompt + "与其他数据关联，不能删除！";
        addReturnValue(0, 151103L, new Object[] { prompt });
        count++;
      }
      if (count == 0)
      {
        addReturnValue(1, 119903L);
      }
    }
    else {
      addReturnValue(-1, 151103L, new Object[] { "删除主键为空或错误，请修改~！" });
    }

    return "success";
  }

  public String deleteMaxHoldQty()
  {
    this.logger.debug("enter deleteMaxHoldQty");

    String[] ids = this.request.getParameterValues("ids");

    if (ids != null)
    {
      String prompt = "";
      int count = 0;
      for (int i = 0; i < ids.length; i++) {
        String[] idArr = ids[i].split(",");
        String firmID = idArr[0];
        String breedID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmBreedMaxHoldQty maxHoldQty = new FirmBreedMaxHoldQty();
          maxHoldQty.setFirmID(firmID);
          maxHoldQty.setBreedID(Integer.valueOf(Integer.parseInt(breedID)));
          getService().delete(maxHoldQty);

          writeOperateLog(1505, "删除特殊品种订货量! 交易商ID：" + maxHoldQty.getFirmID() + ",品种ID：" + maxHoldQty.getBreedID(), 1, "");
        } catch (DataIntegrityViolationException e) {
          e.getStackTrace();
          prompt = prompt + ids[i] + ",";
          addReturnValue(-1, 151103L, new Object[] { "[" + ids[i] + "]与其他数据关联，删除失败！" });
          count++;
        }
      }
      if (!prompt.equals(""))
      {
        prompt = prompt.substring(0, prompt.length() - 1);
        prompt = prompt + "与其他数据关联，不能删除！";
        addReturnValue(0, 151103L, new Object[] { prompt });
        count++;
      }
      if (count == 0)
      {
        addReturnValue(1, 119903L);
      }
    }
    else {
      addReturnValue(-1, 151103L, new Object[] { "删除主键为空或错误，请修改~！" });
    }

    return "success";
  }

  public String deleteTradeFee()
  {
    this.logger.debug("enter deleteTradeFee");

    String[] ids = this.request.getParameterValues("ids");

    if (ids != null)
    {
      String prompt = "";
      int count = 0;
      for (int i = 0; i < ids.length; i++) {
        String[] idArr = ids[i].split(",");
        String firmID = idArr[0];
        String breedID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmBreedTradeFee tradeFee = new FirmBreedTradeFee();
          tradeFee.setFirmID(firmID);
          tradeFee.setBreedID(Integer.valueOf(Integer.parseInt(breedID)));
          getService().delete(tradeFee);

          writeOperateLog(1505, "删除特殊品种交易手续费! 交易商ID：" + tradeFee.getFirmID() + ",品种ID：" + tradeFee.getBreedID(), 1, "");
        } catch (DataIntegrityViolationException e) {
          e.getStackTrace();
          prompt = prompt + ids[i] + ",";
          addReturnValue(-1, 151103L, new Object[] { "[" + ids[i] + "]与其他数据关联，删除失败！" });
          count++;
        }
      }
      if (!prompt.equals(""))
      {
        prompt = prompt.substring(0, prompt.length() - 1);
        prompt = prompt + "与其他数据关联，不能删除！";
        addReturnValue(0, 151103L, new Object[] { prompt });
        count++;
      }
      if (count == 0)
      {
        addReturnValue(1, 119903L);
      }
    }
    else {
      addReturnValue(-1, 151103L, new Object[] { "删除主键为空或错误，请修改~！" });
    }

    return "success";
  }

  public String deleteSettleFee()
  {
    this.logger.debug("enter deleteSettleFee");

    String[] ids = this.request.getParameterValues("ids");

    if (ids != null)
    {
      String prompt = "";
      int count = 0;
      for (int i = 0; i < ids.length; i++) {
        String[] idArr = ids[i].split(",");
        String firmID = idArr[0];
        String breedID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmBreedSettleFee settleFee = new FirmBreedSettleFee();
          settleFee.setFirmID(firmID);
          settleFee.setBreedID(Integer.valueOf(Integer.parseInt(breedID)));
          getService().delete(settleFee);

          writeOperateLog(1505, "删除特殊品种交收手续费! 交易商ID：" + settleFee.getFirmID() + ",品种ID：" + settleFee.getBreedID(), 1, "");
        } catch (DataIntegrityViolationException e) {
          e.getStackTrace();
          prompt = prompt + ids[i] + ",";
          addReturnValue(-1, 151103L, new Object[] { "[" + ids[i] + "]与其他数据关联，删除失败！" });
          count++;
        }
      }
      if (!prompt.equals(""))
      {
        prompt = prompt.substring(0, prompt.length() - 1);
        prompt = prompt + "与其他数据关联，不能删除！";
        addReturnValue(0, 151103L, new Object[] { prompt });
        count++;
      }
      if (count == 0)
      {
        addReturnValue(1, 119903L);
      }
    }
    else {
      addReturnValue(-1, 151103L, new Object[] { "删除主键为空或错误，请修改~！" });
    }

    return "success";
  }

  public String viewTradeMargin()
  {
    this.logger.debug("enter viewTradeMargin");

    FirmBreedTradeMargin tradeMargin = (FirmBreedTradeMargin)getService().get(this.entity);

    if (tradeMargin.getMarginAlgr().intValue() == 1) {
      tradeMargin.setMarginItem1(Double.valueOf(Arith.mul(tradeMargin.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
      tradeMargin.setMarginItem1_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeMargin.setMarginItemAssure1(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
      tradeMargin.setMarginItemAssure1_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
      if (tradeMargin.getMarginItem2() != null) {
        tradeMargin.setMarginItem2(Double.valueOf(Arith.mul(tradeMargin.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem2_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure2(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure2_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (tradeMargin.getMarginItem3() != null) {
        tradeMargin.setMarginItem3(Double.valueOf(Arith.mul(tradeMargin.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem3_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure3(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure3_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (tradeMargin.getMarginItem4() != null) {
        tradeMargin.setMarginItem4(Double.valueOf(Arith.mul(tradeMargin.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem4_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure4(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure4_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (tradeMargin.getMarginItem5() != null) {
        tradeMargin.setMarginItem5(Double.valueOf(Arith.mul(tradeMargin.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem5_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure5(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure5_S(Double.valueOf(Arith.mul(tradeMargin.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
    }

    this.entity = tradeMargin;

    return "success";
  }

  public String viewSettleMargin()
  {
    this.logger.debug("enter viewSettleMargin");

    FirmBreedSettleMargin settleMargin = (FirmBreedSettleMargin)getService().get(this.entity);

    if (settleMargin.getSettleMarginAlgr_B().intValue() == 1) {
      settleMargin.setSettleMarginRate_B(Double.valueOf(Arith.mul(settleMargin.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (settleMargin.getSettleMarginAlgr_S().intValue() == 1) {
      settleMargin.setSettleMarginRate_S(Double.valueOf(Arith.mul(settleMargin.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (settleMargin.getPayoutAlgr().intValue() == 1) {
      settleMargin.setPayoutRate(Double.valueOf(Arith.mul(settleMargin.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
    }

    this.entity = settleMargin;

    return "success";
  }

  public String viewTradeFee()
  {
    this.logger.debug("enter viewTradeFee");

    FirmBreedTradeFee tradeFee = (FirmBreedTradeFee)getService().get(this.entity);

    if (tradeFee.getFeeAlgr().intValue() == 1) {
      tradeFee.setFeeRate_B(Double.valueOf(Arith.mul(tradeFee.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setFeeRate_S(Double.valueOf(Arith.mul(tradeFee.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setHistoryCloseFeeRate_B(Double.valueOf(Arith.mul(tradeFee.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setHistoryCloseFeeRate_S(Double.valueOf(Arith.mul(tradeFee.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setTodayCloseFeeRate_B(Double.valueOf(Arith.mul(tradeFee.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setTodayCloseFeeRate_S(Double.valueOf(Arith.mul(tradeFee.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setForceCloseFeeRate_B(Double.valueOf(Arith.mul(tradeFee.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setForceCloseFeeRate_S(Double.valueOf(Arith.mul(tradeFee.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    this.entity = tradeFee;

    return "success";
  }

  public String viewSettleFee()
  {
    this.logger.debug("enter viewSettleFee");

    FirmBreedSettleFee settleFee = (FirmBreedSettleFee)getService().get(this.entity);

    if (settleFee.getSettleFeeAlgr().intValue() == 1) {
      settleFee.setSettleFeeRate_B(Double.valueOf(Arith.mul(settleFee.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      settleFee.setSettleFeeRate_S(Double.valueOf(Arith.mul(settleFee.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    this.entity = settleFee;

    return "success";
  }

  public String updateTradeMargin()
    throws Exception
  {
    this.logger.debug("enter updateTradeMargin");

    FirmBreedTradeMargin tradeMargin = (FirmBreedTradeMargin)this.entity;

    if (tradeMargin.getMarginAlgr().intValue() == 1) {
      tradeMargin.setMarginItem1(Double.valueOf(Arith.div(tradeMargin.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
      tradeMargin.setMarginItem1_S(Double.valueOf(Arith.div(tradeMargin.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeMargin.setMarginItemAssure1(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
      tradeMargin.setMarginItemAssure1_S(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
      if (tradeMargin.getMarginItem2() != null) {
        tradeMargin.setMarginItem2(Double.valueOf(Arith.div(tradeMargin.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem2_S(Double.valueOf(Arith.div(tradeMargin.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure2(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure2_S(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (tradeMargin.getMarginItem3() != null) {
        tradeMargin.setMarginItem3(Double.valueOf(Arith.div(tradeMargin.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem3_S(Double.valueOf(Arith.div(tradeMargin.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure3(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure3_S(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (tradeMargin.getMarginItem4() != null) {
        tradeMargin.setMarginItem4(Double.valueOf(Arith.div(tradeMargin.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem4_S(Double.valueOf(Arith.div(tradeMargin.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure4(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure4_S(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (tradeMargin.getMarginItem5() != null) {
        tradeMargin.setMarginItem5(Double.valueOf(Arith.div(tradeMargin.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItem5_S(Double.valueOf(Arith.div(tradeMargin.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure5(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
        tradeMargin.setMarginItemAssure5_S(Double.valueOf(Arith.div(tradeMargin.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
    }

    tradeMargin.setModifyTime(getService().getSysDate());

    getService().update(tradeMargin);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊品种交易保证金! 交易商ID：" + tradeMargin.getFirmID() + ",品种ID：" + tradeMargin.getBreedID(), 1, "");

    return "success";
  }

  public String updateSettleMargin()
    throws Exception
  {
    this.logger.debug("enter updateSettleMargin");

    FirmBreedSettleMargin settleMargin = (FirmBreedSettleMargin)this.entity;

    if (settleMargin.getSettleMarginAlgr_B().intValue() == 1) {
      settleMargin.setSettleMarginRate_B(Double.valueOf(Arith.div(settleMargin.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (settleMargin.getSettleMarginAlgr_S().intValue() == 1) {
      settleMargin.setSettleMarginRate_S(Double.valueOf(Arith.div(settleMargin.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (settleMargin.getPayoutAlgr().intValue() == 1) {
      settleMargin.setPayoutRate(Double.valueOf(Arith.div(settleMargin.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
    }

    settleMargin.setModifyTime(getService().getSysDate());

    getService().update(settleMargin);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊品种交收保证金! 交易商ID：" + settleMargin.getFirmID() + ",品种ID：" + settleMargin.getBreedID(), 1, "");

    return "success";
  }

  public String updateMaxHoldQty()
    throws Exception
  {
    this.logger.debug("enter updateMaxHoldQty");

    FirmBreedMaxHoldQty maxHoldQty = (FirmBreedMaxHoldQty)this.entity;

    maxHoldQty.setModifyTime(getService().getSysDate());

    getService().update(maxHoldQty);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊品种订货量! 交易商ID：" + maxHoldQty.getFirmID() + ",品种ID：" + maxHoldQty.getBreedID(), 1, "");

    return "success";
  }

  public String updateTradeFee()
    throws Exception
  {
    this.logger.debug("enter updateTradeFee");

    FirmBreedTradeFee tradeFee = (FirmBreedTradeFee)this.entity;

    if (tradeFee.getFeeAlgr().intValue() == 1) {
      tradeFee.setFeeRate_B(Double.valueOf(Arith.div(tradeFee.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setFeeRate_S(Double.valueOf(Arith.div(tradeFee.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setHistoryCloseFeeRate_B(Double.valueOf(Arith.div(tradeFee.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setHistoryCloseFeeRate_S(Double.valueOf(Arith.div(tradeFee.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setTodayCloseFeeRate_B(Double.valueOf(Arith.div(tradeFee.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setTodayCloseFeeRate_S(Double.valueOf(Arith.div(tradeFee.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setForceCloseFeeRate_B(Double.valueOf(Arith.div(tradeFee.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      tradeFee.setForceCloseFeeRate_S(Double.valueOf(Arith.div(tradeFee.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    tradeFee.setModifyTime(getService().getSysDate());

    getService().update(tradeFee);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊品种交易手续费! 交易商ID：" + tradeFee.getFirmID() + ",品种ID：" + tradeFee.getBreedID(), 1, "");

    return "success";
  }

  public String updateSettleFee()
    throws Exception
  {
    this.logger.debug("enter updateSettleFee");

    FirmBreedSettleFee settleFee = (FirmBreedSettleFee)this.entity;

    if (settleFee.getSettleFeeAlgr().intValue() == 1) {
      settleFee.setSettleFeeRate_B(Double.valueOf(Arith.div(settleFee.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      settleFee.setSettleFeeRate_S(Double.valueOf(Arith.div(settleFee.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    settleFee.setModifyTime(getService().getSysDate());

    getService().update(settleFee);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊品种交收手续费! 交易商ID：" + settleFee.getFirmID() + ",品种ID：" + settleFee.getBreedID(), 1, "");

    return "success";
  }

  public Map<Integer, String> getAlgrMap()
  {
    return this.algrMap;
  }

  public TradePrivilegeService getTradePrivilegeService() {
    return this.tradePrivilegeService;
  }

  public FirmSpecialService getFirmSpecialService() {
    return this.firmSpecialService;
  }
  public CommodityIdService getCommodityIdService() {
    return this.commodityIdService;
  }
}