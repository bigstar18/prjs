package gnnt.MEBS.timebargain.mgr.action.deduct;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.deduct.Customer;
import gnnt.MEBS.timebargain.mgr.model.deduct.CustomerHoldSum;
import gnnt.MEBS.timebargain.mgr.model.deduct.Deduct;
import gnnt.MEBS.timebargain.mgr.model.deduct.DeductDetail;
import gnnt.MEBS.timebargain.mgr.model.deduct.DeductKeep;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("deductAction")
@Scope("request")
public class DeductAction extends EcsideAction
{
  private static final long serialVersionUID = -1L;

  @Autowired
  @Qualifier("TradeRMI")
  private TradeRMI tradeRMI;

  @Resource(name="loserBSFlagMap")
  private Map<Short, String> loserBSFlagMap;

  @Resource(name="loserModeMap")
  private Map<Short, String> loserModeMap;

  @Resource(name="deductStatusMap")
  private Map<String, String> deductStatusMap;

  @Resource(name="selfCounteractMap")
  private Map<Short, String> selfCounteractMap;

  public Map<Short, String> getLoserBSFlagMap()
  {
    return this.loserBSFlagMap;
  }

  public void setLoserBSFlagMap(Map<Short, String> paramMap)
  {
    this.loserBSFlagMap = paramMap;
  }

  public Map<Short, String> getLoserModeMap()
  {
    return this.loserModeMap;
  }

  public void setLoserModeMap(Map<Short, String> paramMap)
  {
    this.loserModeMap = paramMap;
  }

  public Map<String, String> getDeductStatusMap()
  {
    return this.deductStatusMap;
  }

  public void setDeductStatusMap(Map<String, String> paramMap)
  {
    this.deductStatusMap = paramMap;
  }

  public Map<Short, String> getSelfCounteractMap()
  {
    return this.selfCounteractMap;
  }

  public void setSelfCounteractMap(Map<Short, String> paramMap)
  {
    this.selfCounteractMap = paramMap;
  }

  public TradeRMI getTradeRMI()
  {
    return this.tradeRMI;
  }

  public void setTradeRMI(TradeRMI paramTradeRMI)
  {
    this.tradeRMI = paramTradeRMI;
  }

  public String toDeductPosition()
  {
    this.logger.debug("------------toDeductPosition 到强制减仓配置页面--------------");
    String str = "select commodityId,name from t_commodity ";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
    return "success";
  }

  public String addDeductPosition()
  {
    this.logger.debug("------------addDeductPosition 保存强制减仓配置--------------");
    Deduct localDeduct = (Deduct)this.entity;
    localDeduct.setLossRate(Double.valueOf(localDeduct.getLossRate().doubleValue() / 100.0D));
    localDeduct.setProfitLvl1(Double.valueOf(localDeduct.getProfitLvl1().doubleValue() / 100.0D));
    localDeduct.setProfitLvl2(Double.valueOf(localDeduct.getProfitLvl2().doubleValue() / 100.0D));
    localDeduct.setProfitLvl3(Double.valueOf(localDeduct.getProfitLvl3().doubleValue() / 100.0D));
    localDeduct.setProfitLvl4(Double.valueOf(localDeduct.getProfitLvl4().doubleValue() / 100.0D));
    localDeduct.setProfitLvl5(Double.valueOf(localDeduct.getProfitLvl5().doubleValue() / 100.0D));
    getService().add(this.entity);
    writeOperateLog(1511, "添加强制减仓" + localDeduct.getDeductId(), 1, "");
    deductKeepFirmForward(null, localDeduct);
    return "success";
  }

  public String updateDeductPositionForward()
  {
    this.logger.debug("------------updateDeductPositionForward 跳转到强制减仓配置修改页面--------------");
    String str = "select commodityId,name from t_commodity ";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
    Deduct localDeduct = (Deduct)getService().get(this.entity).clone();
    localDeduct.setLossRate(Double.valueOf(localDeduct.getLossRate().doubleValue() * 100.0D));
    localDeduct.setProfitLvl1(Double.valueOf(localDeduct.getProfitLvl1().doubleValue() * 100.0D));
    localDeduct.setProfitLvl2(Double.valueOf(localDeduct.getProfitLvl2().doubleValue() * 100.0D));
    localDeduct.setProfitLvl3(Double.valueOf(localDeduct.getProfitLvl3().doubleValue() * 100.0D));
    localDeduct.setProfitLvl4(Double.valueOf(localDeduct.getProfitLvl4().doubleValue() * 100.0D));
    localDeduct.setProfitLvl5(Double.valueOf(localDeduct.getProfitLvl5().doubleValue() * 100.0D));
    this.entity = localDeduct;
    return "success";
  }

  public String updateDeductPosition()
  {
    this.logger.debug("------------updateDeductPosition 修改强制减仓配置--------------");
    Deduct localDeduct = (Deduct)this.entity;
    localDeduct.setLossRate(Double.valueOf(localDeduct.getLossRate().doubleValue() / 100.0D));
    localDeduct.setProfitLvl1(Double.valueOf(localDeduct.getProfitLvl1().doubleValue() / 100.0D));
    localDeduct.setProfitLvl2(Double.valueOf(localDeduct.getProfitLvl2().doubleValue() / 100.0D));
    localDeduct.setProfitLvl3(Double.valueOf(localDeduct.getProfitLvl3().doubleValue() / 100.0D));
    localDeduct.setProfitLvl4(Double.valueOf(localDeduct.getProfitLvl4().doubleValue() / 100.0D));
    localDeduct.setProfitLvl5(Double.valueOf(localDeduct.getProfitLvl5().doubleValue() / 100.0D));
    getService().update(localDeduct);
    writeOperateLog(1511, "修改强制减仓" + localDeduct.getDeductId(), 1, "");
    deductKeepFirmForward(null, localDeduct);
    return "success";
  }

  public String addKeepFirm()
  {
    this.logger.debug("------------addKeepFirm 添加强减交易商配置--------------");
    String str = "";
    int i = 0;
    DeductKeep localDeductKeep = (DeductKeep)this.entity;
    CustomerHoldSum localCustomerHoldSum = new CustomerHoldSum();
    localCustomerHoldSum.setCustomerId(localDeductKeep.getCustomerId());
    localCustomerHoldSum.setCommodityId(this.request.getParameter("commodityId"));
    localCustomerHoldSum.setBs_Flag(localDeductKeep.getBs_Flag());
    localCustomerHoldSum = (CustomerHoldSum)getService().get(localCustomerHoldSum);
    if (localCustomerHoldSum == null)
    {
      str = "添加失败！交易客户" + localDeductKeep.getCustomerId() + "没有持仓！";
      addReturnValue(-1, 131506L);
    }
    else if (localDeductKeep.getKeepQty().longValue() > localCustomerHoldSum.getHoldQty().longValue())
    {
      str = "添加失败！交易客户" + localDeductKeep.getCustomerId() + "持仓小于保留量！";
      addReturnValue(-1, 131507L);
    }
    else
    {
      getService().add(localDeductKeep);
      str = "添加强减保留交易商记录" + localDeductKeep.getDeductId() + localDeductKeep.getCustomerId() + localDeductKeep.getBs_Flag();
      i = 1;
      addReturnValue(1, 131508L);
    }
    deductKeepFirmForward(localDeductKeep.getDeductId(), null);
    writeOperateLog(1511, str, i, "");
    return "success";
  }

  public void deductKeepFirmForward(Long paramLong, Deduct paramDeduct)
  {
    if (paramDeduct == null)
    {
      paramDeduct = new Deduct();
      paramDeduct.setDeductId(paramLong);
      paramDeduct = (Deduct)getService().get(paramDeduct);
    }
    this.request.setAttribute("deduct", paramDeduct);
    this.entity = new DeductKeep();
    List localList = getService().getListBySql("select * from T_CUSTOMER order by customerId ", new Customer());
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.deductId", "=", paramDeduct.getDeductId());
      listByLimit(localPageRequest);
    }
    catch (Exception localException)
    {
    }
    this.request.setAttribute("customer", localList);
  }

  public String deductKeepFirmForward()
  {
    Long localLong = Long.valueOf(Long.parseLong(this.request.getParameter("deductId")));
    deductKeepFirmForward(localLong, null);
    return "success";
  }

  public String operateDeductDetail()
  {
    Deduct localDeduct = (Deduct)getService().get(this.entity);
    Object[] arrayOfObject = { localDeduct.getDeductId() };
    String str1 = "";
    int i = 1;
    try
    {
      i = Integer.parseInt(getService().executeProcedure("{?=call FN_T_DeductData(?) }", arrayOfObject).toString());
      if (i == 1)
      {
        Long localLong = null;
        String str2 = "select sum(DeductQty) deductQty from T_E_DEDUCTDETAIL where DeductID =" + localDeduct.getDeductId();
        List localList = getService().getListBySql(str2);
        if ((localList == null) && (localList.size() > 0))
          localLong = Long.valueOf(Long.parseLong(((Map)localList.get(0)).get("DEDUCTQTY").toString()));
        if (localLong == null)
          localLong = new Long(0L);
        deductDetailForward(localDeduct.getDeductId(), null);
        this.request.setAttribute("deductQty", localLong);
        this.request.setAttribute("deduct", localDeduct);
        writeOperateLog(1511, "生成强减数据成功,强减Id：" + localDeduct.getDeductId(), 1, "");
        addReturnValue(-1, 111501L);
        return "success";
      }
      if (i == -1)
      {
        addReturnValue(-1, 131502L);
        str1 = "生成强减明细失败：只能闭市操作状态强减！强减Id：" + localDeduct.getDeductId();
      }
      else if (i == -2)
      {
        addReturnValue(-1, 131503L);
        str1 = "生成强减明细失败：没有符合条件的盈利方！强减Id：" + localDeduct.getDeductId();
      }
      else
      {
        addReturnValue(-1, 131504L);
        str1 = "生成强减明细失败！";
      }
      writeOperateLog(1511, str1, 0, "");
    }
    catch (Exception localException)
    {
      addReturnValue(-1, 131504L);
      this.logger.error("生成强减明细失败！！！");
      writeOperateLog(1511, "生成强减明细失败！", 0, localException.getMessage());
    }
    deductKeepFirmForward(localDeduct.getDeductId(), null);
    return "fail";
  }

  public void deductDetailForward(Long paramLong, DeductDetail paramDeductDetail)
  {
    this.entity = new DeductDetail();
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.deductId", "=", paramLong);
      listByLimit(localPageRequest);
    }
    catch (Exception localException)
    {
    }
  }

  public String delete()
    throws SecurityException, NoSuchFieldException
  {
    DeductKeep localDeductKeep = new DeductKeep();
    this.logger.debug("enter delete");
    String[] arrayOfString1 = this.request.getParameterValues("ids");
    if ((arrayOfString1 == null) || (arrayOfString1.length == 0))
      throw new IllegalArgumentException("删除主键数组不能为空！");
    Long localLong = null;
    String str = "";
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      str = arrayOfString1[i];
      String[] arrayOfString2 = str.split(",");
      localLong = Long.valueOf(Long.parseLong(arrayOfString2[0]));
      int j = Integer.parseInt(arrayOfString2[2]);
      localDeductKeep.setBs_Flag(Integer.valueOf(j));
      localDeductKeep.setDeductId(localLong);
      localDeductKeep.setCustomerId(arrayOfString2[1]);
      localDeductKeep.setBs_Flag(Integer.valueOf(j));
      getService().delete(localDeductKeep);
      writeOperateLog(1511, "成功删除强减保留记录：强减ID：" + localLong + "买卖标志：" + j + "客户代码" + arrayOfString2[1] + "！", 1, "");
    }
    addReturnValue(1, 119903L);
    deductKeepFirmForward(localLong, null);
    return "success";
  }

  public String deductGo()
  {
    Deduct localDeduct = (Deduct)this.entity;
    Object[] arrayOfObject = { localDeduct.getDeductId() };
    String str = "";
    int i = 0;
    try
    {
      i = Integer.parseInt(getService().executeProcedure("{?=call FN_T_DeductGo(?) }", arrayOfObject).toString());
      if (i == 1)
      {
        writeOperateLog(1511, "执行强减成功,强减Id：" + localDeduct.getDeductId(), 1, "");
        addReturnValue(-1, 111501L);
        return "success";
      }
      if (i == -1)
      {
        addReturnValue(-1, 131509L);
        str = "应在闭市之前，结算之后做强减！";
      }
      else if (i == -2)
      {
        addReturnValue(-1, 131510L);
        str = "强减日期应为当前日期！";
      }
      else
      {
        addReturnValue(-1, 131511L);
        str = "执行强减失败！";
      }
      writeOperateLog(1511, str, 0, "");
    }
    catch (Exception localException)
    {
      addReturnValue(-1, 131511L);
      this.logger.error("执行强减失败！！！");
      writeOperateLog(1511, "执行强减失败！", 0, localException.getMessage());
    }
    return "fail";
  }

  public String deductInfoByDeductId()
  {
    Long localLong1 = Long.valueOf(Long.parseLong(this.request.getParameter("deductId")));
    Deduct localDeduct = new Deduct();
    localDeduct.setDeductId(localLong1);
    localDeduct = (Deduct)getService().get(localDeduct).clone();
    localDeduct.setLossRate(Double.valueOf(localDeduct.getLossRate().doubleValue() * 100.0D));
    localDeduct.setProfitLvl1(Double.valueOf(localDeduct.getProfitLvl1().doubleValue() * 100.0D));
    localDeduct.setProfitLvl2(Double.valueOf(localDeduct.getProfitLvl2().doubleValue() * 100.0D));
    localDeduct.setProfitLvl3(Double.valueOf(localDeduct.getProfitLvl3().doubleValue() * 100.0D));
    localDeduct.setProfitLvl4(Double.valueOf(localDeduct.getProfitLvl4().doubleValue() * 100.0D));
    localDeduct.setProfitLvl5(Double.valueOf(localDeduct.getProfitLvl5().doubleValue() * 100.0D));
    this.entity = localDeduct;
    Long localLong2 = null;
    Long localLong3 = null;
    String str = "select sum(DeductedQty) deductedQty,sum(CounteractedQty) counteractedQty from T_E_DEDUCTDETAIL where deductID =" + localDeduct.getDeductId();
    List localList = getService().getListBySql(str);
    if ((localList == null) && (localList.size() > 0))
    {
      localLong2 = Long.valueOf(Long.parseLong(((Map)localList.get(0)).get("DEDUCTQTY").toString()));
      localLong3 = Long.valueOf(Long.parseLong(((Map)localList.get(0)).get("COUNTERACTEDQTY").toString()));
    }
    if (localLong2 == null)
      localLong2 = new Long(0L);
    if (localLong3 == null)
      localLong3 = new Long(0L);
    this.request.setAttribute("deductedQty", localLong2);
    this.request.setAttribute("counteractedQty", localLong3);
    return "success";
  }

  public String deductKeepFirmByDeductId()
  {
    Long localLong = Long.valueOf(Long.parseLong(this.request.getParameter("deductId")));
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.deductId", "=", localLong);
      listByLimit(localPageRequest);
      this.request.setAttribute("deductId", localLong);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String deductDetailByDeductId()
  {
    Long localLong = Long.valueOf(Long.parseLong(this.request.getParameter("deductId")));
    deductDetailForward(localLong, null);
    this.request.setAttribute("deductId", localLong);
    return "success";
  }

  public String deductOrderAddForward()
  {
    String str = "select commodityId,name from t_commodity ";
    List localList1 = getService().getListBySql(str);
    List localList2 = getService().getListBySql("select * from T_CUSTOMER ", new Customer());
    this.request.setAttribute("list", localList1);
    this.request.setAttribute("customer", localList2);
    return "success";
  }

  public String addDeductOrder()
  {
    String str1 = this.request.getParameter("customerId");
    String str2 = this.request.getParameter("commodityId");
    String str3 = this.request.getParameter("buyOrSell");
    String str4 = this.request.getParameter("price");
    String str5 = this.request.getParameter("quantity");
    try
    {
      int i = 1;
      Order localOrder = new Order();
      localOrder.setCustomerID(str1);
      localOrder.setCommodityID(str2);
      localOrder.setBuyOrSell(Short.valueOf(Short.parseShort(str3)));
      localOrder.setPrice(Double.valueOf(Double.parseDouble(str4)));
      localOrder.setQuantity(Long.valueOf(Long.parseLong(str5)));
      i = this.tradeRMI.deductCloseOrder(localOrder);
      if (i == 0)
      {
        addReturnValue(1, 111501L);
        writeOperateLog(1511, "添加强减委托信息" + str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5, 1, "");
      }
      else if (i == -1)
      {
        addReturnValue(-1, 131512L);
      }
      else if (i == -2)
      {
        addReturnValue(-1, 131513L);
      }
      else if (i == -3)
      {
        addReturnValue(-1, 131514L);
      }
      else if (i == -202)
      {
        addReturnValue(-1, 131515L);
      }
      return "success";
    }
    catch (Exception localException)
    {
      this.logger.error("RMI添加强减委托失败！！！");
      writeOperateLog(1511, "添加强减委托", 0, localException.getMessage());
      addReturnValue(-1, 131516L);
    }
    return "success";
  }
}