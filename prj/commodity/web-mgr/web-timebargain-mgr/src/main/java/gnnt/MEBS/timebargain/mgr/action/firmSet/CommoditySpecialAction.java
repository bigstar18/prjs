package gnnt.MEBS.timebargain.mgr.action.firmSet;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmMaxHoldQty;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmSettleFee;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmSettleMargin;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmTradeFee;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmTradeMargin;
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

@Controller("commoditySpecialAction")
@Scope("request")
public class CommoditySpecialAction extends EcsideAction
{
  private static final long serialVersionUID = -2119787762302411598L;

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

  public String fowardAddCommoditySpecial()
  {
    this.logger.debug("enter fowardAddCommoditySpecial");

    List commodityList = this.tradePrivilegeService.getCommodity();

    this.request.setAttribute("commodityList", commodityList);

    return "success";
  }

  private boolean existCommoditySpecial(String firmID, String commodityID)
  {
    boolean result = false;

    PageRequest pageRequest = new PageRequest(" and primary.firmID='" + firmID + "' and primary.commodityID='" + commodityID + "'");
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

    FirmTradeMargin tradeMargin = (FirmTradeMargin)this.entity;
    boolean flag = existCommoditySpecial(tradeMargin.getFirmID(), tradeMargin.getCommodityID());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊商品交易保证金失败，已存在！" });
    }
    else {
      FirmTradeMargin firmTradeMargin = (FirmTradeMargin)this.entity;

      if (firmTradeMargin.getMarginAlgr().intValue() == 1) {
        firmTradeMargin.setMarginItem1(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem1_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure1(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure1_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
        if (firmTradeMargin.getMarginItem2() != null) {
          firmTradeMargin.setMarginItem2(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItem2_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure2(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure2_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
        if (firmTradeMargin.getMarginItem3() != null) {
          firmTradeMargin.setMarginItem3(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItem3_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure3(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure3_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
        if (firmTradeMargin.getMarginItem4() != null) {
          firmTradeMargin.setMarginItem4(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItem4_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure4(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure4_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
        }
        if (firmTradeMargin.getMarginItem5() != null) {
          firmTradeMargin.setMarginItem5(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItem5_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure5(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeMargin.setMarginItemAssure5_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
        }

      }

      firmTradeMargin.setModifyTime(getService().getSysDate());

      getService().add(firmTradeMargin);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊商品交易保证金！交易商ID：" + firmTradeMargin.getFirmID() + ",商品ID：" + firmTradeMargin.getCommodityID(), 1, "");
    }

    return "success";
  }

  public String addSettleMargin()
    throws Exception
  {
    this.logger.debug("enter addSettleMargin");

    FirmSettleMargin settleMargin = (FirmSettleMargin)this.entity;
    boolean flag = existCommoditySpecial(settleMargin.getFirmID(), settleMargin.getCommodityID());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊商品交收保证金失败，已存在！" });
    }
    else {
      FirmSettleMargin firmSettleMargin = (FirmSettleMargin)this.entity;

      if (firmSettleMargin.getSettleMarginAlgr_B().intValue() == 1) {
        firmSettleMargin.setSettleMarginRate_B(Double.valueOf(Arith.div(firmSettleMargin.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmSettleMargin.getSettleMarginAlgr_S().intValue() == 1) {
        firmSettleMargin.setSettleMarginRate_S(Double.valueOf(Arith.div(firmSettleMargin.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmSettleMargin.getPayoutAlgr().intValue() == 1) {
        firmSettleMargin.setPayoutRate(Double.valueOf(Arith.div(firmSettleMargin.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
      }

      firmSettleMargin.setModifyTime(getService().getSysDate());

      getService().add(firmSettleMargin);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊商品交收保证金！交易商ID：" + firmSettleMargin.getFirmID() + ",商品ID：" + firmSettleMargin.getCommodityID(), 1, "");
    }

    return "success";
  }

  public String addMaxHoldQty()
    throws Exception
  {
    this.logger.debug("enter addMaxHoldQty");

    FirmMaxHoldQty maxHoldQty = (FirmMaxHoldQty)this.entity;
    boolean flag = existCommoditySpecial(maxHoldQty.getFirmID(), maxHoldQty.getCommodityID());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊商品订货量失败，已存在！" });
    }
    else {
      FirmMaxHoldQty firmMaxHoldQty = (FirmMaxHoldQty)this.entity;

      firmMaxHoldQty.setModifyTime(getService().getSysDate());

      getService().add(firmMaxHoldQty);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊商品订货量！交易商ID：" + firmMaxHoldQty.getFirmID() + ",商品ID：" + firmMaxHoldQty.getCommodityID(), 1, "");
    }

    return "success";
  }

  public String addTradeFee()
    throws Exception
  {
    this.logger.debug("enter addMaxHoldQty");

    FirmTradeFee tradeFee = (FirmTradeFee)this.entity;

    if ((tradeFee.getFirmID() != null) && (!"".equals(tradeFee.getFirmID()))) {
      boolean flag = existCommoditySpecial(tradeFee.getFirmID(), tradeFee.getCommodityID());
      if (flag) {
        addReturnValue(-1, 151103L, new Object[] { "添加特殊商品交易手续费失败，已存在！" });
      }
      else {
        FirmTradeFee firmTradeFee = (FirmTradeFee)this.entity;

        if (firmTradeFee.getFeeAlgr().intValue() == 1) {
          firmTradeFee.setFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setHistoryCloseFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setHistoryCloseFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setTodayCloseFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setTodayCloseFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setForceCloseFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
          firmTradeFee.setForceCloseFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
        }

        firmTradeFee.setModifyTime(getService().getSysDate());

        getService().add(firmTradeFee);

        addReturnValue(1, 119901L);

        writeOperateLog(1505, "添加特殊商品交易手续费！交易商ID：" + firmTradeFee.getFirmID() + ",商品ID：" + firmTradeFee.getCommodityID(), 1, "");
      }
    }
    else
    {
      List list = this.commodityIdService.firmIdList("desc");
      for (int i = 0; i < list.size(); i++) {
        Map map = (Map)list.get(i);
        Collection coll = map.values();
        Iterator iter = coll.iterator();

        tradeFee.setFirmID((String)iter.next());
        boolean flag = existCommoditySpecial(tradeFee.getFirmID(), tradeFee.getCommodityID());
        if (flag) {
          System.out.println("nihao");
        }
        else
        {
          FirmTradeFee firmTradeFee1 = new FirmTradeFee();
          BeanUtils.copyProperties(tradeFee, firmTradeFee1);

          firmTradeFee1.setModifyTime(getService().getSysDate());

          getService().add(firmTradeFee1);

          addReturnValue(1, 119901L);

          writeOperateLog(1505, "添加特殊商品交易手续费！交易商ID：" + firmTradeFee1.getFirmID() + ",商品ID：" + firmTradeFee1.getCommodityID(), 1, "");
        }
      }

    }

    return "success";
  }

  public String addSettleFee()
    throws Exception
  {
    this.logger.debug("enter addMaxHoldQty");

    FirmSettleFee settleFee = (FirmSettleFee)this.entity;
    boolean flag = existCommoditySpecial(settleFee.getFirmID(), settleFee.getCommodityID());
    if (flag) {
      addReturnValue(-1, 151103L, new Object[] { "添加特殊商品交收手续费失败，已存在！" });
    }
    else {
      FirmSettleFee firmSettleFee = (FirmSettleFee)this.entity;

      if (firmSettleFee.getSettleFeeAlgr().intValue() == 1) {
        firmSettleFee.setSettleFeeRate_B(Double.valueOf(Arith.div(firmSettleFee.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
        firmSettleFee.setSettleFeeRate_S(Double.valueOf(Arith.div(firmSettleFee.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      }

      firmSettleFee.setModifyTime(getService().getSysDate());

      getService().add(firmSettleFee);

      addReturnValue(1, 119901L);

      writeOperateLog(1505, "添加特殊商品交收手续费！交易商ID：" + firmSettleFee.getFirmID() + ",商品ID：" + firmSettleFee.getCommodityID(), 1, "");
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
        String commodityID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmTradeMargin tradeMargin = new FirmTradeMargin();
          tradeMargin.setFirmID(firmID);
          tradeMargin.setCommodityID(commodityID);
          getService().delete(tradeMargin);

          writeOperateLog(1505, "删除特殊商品交易保证金! 交易商ID：" + tradeMargin.getFirmID() + ",商品ID：" + tradeMargin.getCommodityID(), 1, "");
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
        String commodityID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmSettleMargin settleMargin = new FirmSettleMargin();
          settleMargin.setFirmID(firmID);
          settleMargin.setCommodityID(commodityID);
          getService().delete(settleMargin);

          writeOperateLog(1505, "删除特殊商品交收保证金! 交易商ID：" + settleMargin.getFirmID() + ",商品ID：" + settleMargin.getCommodityID(), 1, "");
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
        String commodityID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmMaxHoldQty maxHoldQty = new FirmMaxHoldQty();
          maxHoldQty.setFirmID(firmID);
          maxHoldQty.setCommodityID(commodityID);
          getService().delete(maxHoldQty);

          writeOperateLog(1505, "删除特殊商品订货量! 交易商ID：" + maxHoldQty.getFirmID() + ",商品ID：" + maxHoldQty.getCommodityID(), 1, "");
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
        String commodityID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmTradeFee tradeFee = new FirmTradeFee();
          tradeFee.setFirmID(firmID);
          tradeFee.setCommodityID(commodityID);
          getService().delete(tradeFee);

          writeOperateLog(1505, "删除特殊商品交易手续费! 交易商ID：" + tradeFee.getFirmID() + ",商品ID：" + tradeFee.getCommodityID(), 1, "");
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
        String commodityID = idArr[1];
        try
        {
          String sysStatus = this.firmSpecialService.getSystemStatus();
          if (("5".equals(sysStatus)) || ("6".equals(sysStatus)))
          {
            addReturnValue(-1, 151103L, new Object[] { "此时市场状态不允许删除！" });
            count++;
            break;
          }

          FirmSettleFee settleFee = new FirmSettleFee();
          settleFee.setFirmID(firmID);
          settleFee.setCommodityID(commodityID);
          getService().delete(settleFee);

          writeOperateLog(1505, "删除特殊商品交收手续费! 交易商ID：" + settleFee.getFirmID() + ",商品ID：" + settleFee.getCommodityID(), 1, "");
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

    FirmTradeMargin firmTradeMargin = (FirmTradeMargin)getService().get(this.entity);

    if (firmTradeMargin.getMarginAlgr().intValue() == 1) {
      firmTradeMargin.setMarginItem1(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeMargin.setMarginItem1_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeMargin.setMarginItemAssure1(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeMargin.setMarginItemAssure1_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
      if (firmTradeMargin.getMarginItem2() != null) {
        firmTradeMargin.setMarginItem2(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem2_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure2(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure2_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmTradeMargin.getMarginItem3() != null) {
        firmTradeMargin.setMarginItem3(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem3_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure3(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure3_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmTradeMargin.getMarginItem4() != null) {
        firmTradeMargin.setMarginItem4(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem4_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure4(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure4_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmTradeMargin.getMarginItem5() != null) {
        firmTradeMargin.setMarginItem5(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem5_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure5(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure5_S(Double.valueOf(Arith.mul(firmTradeMargin.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
    }

    this.entity = firmTradeMargin;

    return "success";
  }

  public String viewSettleMargin()
  {
    this.logger.debug("enter viewSettleMargin");

    FirmSettleMargin firmSettleMargin = (FirmSettleMargin)getService().get(this.entity);

    if (firmSettleMargin.getSettleMarginAlgr_B().intValue() == 1) {
      firmSettleMargin.setSettleMarginRate_B(Double.valueOf(Arith.mul(firmSettleMargin.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (firmSettleMargin.getSettleMarginAlgr_S().intValue() == 1) {
      firmSettleMargin.setSettleMarginRate_S(Double.valueOf(Arith.mul(firmSettleMargin.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (firmSettleMargin.getPayoutAlgr().intValue() == 1) {
      firmSettleMargin.setPayoutRate(Double.valueOf(Arith.mul(firmSettleMargin.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
    }

    this.entity = firmSettleMargin;

    return "success";
  }

  public String viewTradeFee()
  {
    this.logger.debug("enter viewTradeFee");

    FirmTradeFee firmTradeFee = (FirmTradeFee)getService().get(this.entity);

    if (firmTradeFee.getFeeAlgr().intValue() == 1) {
      firmTradeFee.setFeeRate_B(Double.valueOf(Arith.mul(firmTradeFee.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setFeeRate_S(Double.valueOf(Arith.mul(firmTradeFee.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setHistoryCloseFeeRate_B(Double.valueOf(Arith.mul(firmTradeFee.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setHistoryCloseFeeRate_S(Double.valueOf(Arith.mul(firmTradeFee.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setTodayCloseFeeRate_B(Double.valueOf(Arith.mul(firmTradeFee.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setTodayCloseFeeRate_S(Double.valueOf(Arith.mul(firmTradeFee.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setForceCloseFeeRate_B(Double.valueOf(Arith.mul(firmTradeFee.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setForceCloseFeeRate_S(Double.valueOf(Arith.mul(firmTradeFee.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    this.entity = firmTradeFee;

    return "success";
  }

  public String viewSettleFee()
  {
    this.logger.debug("enter viewSettleFee");

    FirmSettleFee firmSettleFee = (FirmSettleFee)getService().get(this.entity);

    if (firmSettleFee.getSettleFeeAlgr().intValue() == 1) {
      firmSettleFee.setSettleFeeRate_B(Double.valueOf(Arith.mul(firmSettleFee.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmSettleFee.setSettleFeeRate_S(Double.valueOf(Arith.mul(firmSettleFee.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    this.entity = firmSettleFee;

    return "success";
  }

  public String updateTradeMargin()
    throws Exception
  {
    this.logger.debug("enter updateTradeMargin");

    FirmTradeMargin firmTradeMargin = (FirmTradeMargin)this.entity;

    if (firmTradeMargin.getMarginAlgr().intValue() == 1) {
      firmTradeMargin.setMarginItem1(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeMargin.setMarginItem1_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeMargin.setMarginItemAssure1(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeMargin.setMarginItemAssure1_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
      if (firmTradeMargin.getMarginItem2() != null) {
        firmTradeMargin.setMarginItem2(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem2_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure2(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure2_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmTradeMargin.getMarginItem3() != null) {
        firmTradeMargin.setMarginItem3(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem3_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure3(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure3_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmTradeMargin.getMarginItem4() != null) {
        firmTradeMargin.setMarginItem4(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem4_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure4(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure4_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
      if (firmTradeMargin.getMarginItem5() != null) {
        firmTradeMargin.setMarginItem5(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItem5_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure5(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
        firmTradeMargin.setMarginItemAssure5_S(Double.valueOf(Arith.div(firmTradeMargin.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
      }
    }

    firmTradeMargin.setModifyTime(getService().getSysDate());

    getService().update(firmTradeMargin);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊商品交易保证金! 交易商ID：" + firmTradeMargin.getFirmID() + ",商品ID：" + firmTradeMargin.getCommodityID(), 1, "");

    return "success";
  }

  public String updateSettleMargin()
    throws Exception
  {
    this.logger.debug("enter updateSettleMargin");

    FirmSettleMargin firmSettleMargin = (FirmSettleMargin)this.entity;

    if (firmSettleMargin.getSettleMarginAlgr_B().intValue() == 1) {
      firmSettleMargin.setSettleMarginRate_B(Double.valueOf(Arith.div(firmSettleMargin.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (firmSettleMargin.getSettleMarginAlgr_S().intValue() == 1) {
      firmSettleMargin.setSettleMarginRate_S(Double.valueOf(Arith.div(firmSettleMargin.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }
    if (firmSettleMargin.getPayoutAlgr().intValue() == 1) {
      firmSettleMargin.setPayoutRate(Double.valueOf(Arith.div(firmSettleMargin.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
    }

    firmSettleMargin.setModifyTime(getService().getSysDate());

    getService().update(firmSettleMargin);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊商品交收保证金! 交易商ID：" + firmSettleMargin.getFirmID() + ",商品ID：" + firmSettleMargin.getCommodityID(), 1, "");

    return "success";
  }

  public String updateMaxHoldQty()
    throws Exception
  {
    this.logger.debug("enter updateMaxHoldQty");

    FirmMaxHoldQty firmMaxHoldQty = (FirmMaxHoldQty)this.entity;

    firmMaxHoldQty.setModifyTime(getService().getSysDate());

    getService().update(firmMaxHoldQty);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊商品订货量! 交易商ID：" + firmMaxHoldQty.getFirmID() + ",商品ID：" + firmMaxHoldQty.getCommodityID(), 1, "");

    return "success";
  }

  public String updateTradeFee()
    throws Exception
  {
    this.logger.debug("enter updateTradeFee");

    FirmTradeFee firmTradeFee = (FirmTradeFee)this.entity;

    if (firmTradeFee.getFeeAlgr().intValue() == 1) {
      firmTradeFee.setFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setHistoryCloseFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setHistoryCloseFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setTodayCloseFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setTodayCloseFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setForceCloseFeeRate_B(Double.valueOf(Arith.div(firmTradeFee.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmTradeFee.setForceCloseFeeRate_S(Double.valueOf(Arith.div(firmTradeFee.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    firmTradeFee.setModifyTime(getService().getSysDate());

    getService().update(firmTradeFee);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊商品交易手续费! 交易商ID：" + firmTradeFee.getFirmID() + ",商品ID：" + firmTradeFee.getCommodityID(), 1, "");

    return "success";
  }

  public String updateSettleFee()
    throws Exception
  {
    this.logger.debug("enter updateSettleFee");

    FirmSettleFee firmSettleFee = (FirmSettleFee)this.entity;

    if (firmSettleFee.getSettleFeeAlgr().intValue() == 1) {
      firmSettleFee.setSettleFeeRate_B(Double.valueOf(Arith.div(firmSettleFee.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
      firmSettleFee.setSettleFeeRate_S(Double.valueOf(Arith.div(firmSettleFee.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
    }

    firmSettleFee.setModifyTime(getService().getSysDate());

    getService().update(firmSettleFee);

    addReturnValue(1, 119902L);

    writeOperateLog(1505, "修改特殊商品交收手续费! 交易商ID：" + firmSettleFee.getFirmID() + ",商品ID：" + firmSettleFee.getCommodityID(), 1, "");

    return "success";
  }

  public Map<Integer, String> getAlgrMap() {
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