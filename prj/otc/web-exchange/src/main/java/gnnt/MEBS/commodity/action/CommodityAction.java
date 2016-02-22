package gnnt.MEBS.commodity.action;

import gnnt.MEBS.audit.service.ParmaLogServiceJDBC;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commodityAction")
@Scope("request")
public class CommodityAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommodityAction.class);
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Resource(name="commodityStatusMap")
  private Map commodityStatusMap;
  @Resource(name="commodityTradeModeMap")
  private Map commodityTradeModeMap;
  @Resource(name="commoditySpreadAlgrMap")
  private Map<Integer, String> commoditySpreadAlgrMap;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  private String status;
  @Resource(name="delayFeeAlgrMap")
  private Map delayFeeAlgrMap;
  @Resource(name="tradeStatusMap")
  private Map tradeStatusMap;
  @Resource(name="pauseTypeMap")
  private Map pauseTypeMap;
  @Resource(name="contractUnitMap")
  private Map contractUnitMap;
  @Autowired
  @Qualifier("parmaLogServiceJDBC")
  private ParmaLogServiceJDBC parmaLogServiceJDBC;
  private String operateGflag = "commodity_baseCommodity";
  
  public Map getContractUnitMap()
  {
    return this.contractUnitMap;
  }
  
  public void setContractUnitMap(Map contractUnitMap)
  {
    this.contractUnitMap = contractUnitMap;
  }
  
  public Map getDelayFeeAlgrMap()
  {
    return this.delayFeeAlgrMap;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Map getCommodityStatusMap()
  {
    return this.commodityStatusMap;
  }
  
  public Map getCommodityTradeModeMap()
  {
    return this.commodityTradeModeMap;
  }
  
  public Map getCommoditySpreadAlgrMap()
  {
    return this.commoditySpreadAlgrMap;
  }
  
  public Map getTradeStatusMap()
  {
    return this.tradeStatusMap;
  }
  
  public Map getPauseTypeMap()
  {
    return this.pauseTypeMap;
  }
  
  public InService getService()
  {
    return this.commodityService;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter commodityParameters");
    this.request.setAttribute("commodityStatusMap", this.commodityStatusMap);
    this.request.setAttribute("commodityTradeModeMap", this.commodityTradeModeMap);
    this.request.setAttribute("commoditySpreadAlgrMap", this.commoditySpreadAlgrMap);
    this.request.setAttribute("delayFeeAlgrMap", this.delayFeeAlgrMap);
    this.request.setAttribute("pauseTypeMap", this.pauseTypeMap);
    this.request.setAttribute("contractUnitMap", this.contractUnitMap);
  }
  
  public String updateIn()
  {
    int resultValue = this.commodityService.updateIn(this.obj);
    if (resultValue == -1) {
      resultValue = -500;
    } else if (resultValue == -2) {
      resultValue = -502;
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updateOut()
  {
    int resultValue = this.commodityService.updateOut(this.obj);
    if (resultValue == -1) {
      resultValue = -500;
    } else if (resultValue == -2) {
      resultValue = -501;
    } else if (resultValue == -3) {
      resultValue = -505;
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updateRMI()
  {
    String[] commodityIds = this.request.getParameterValues("ids");
    int resultValue = 1;
    String operateContent = "";
    String commodityNames = "";
    if ((commodityIds != null) && (commodityIds.length > 0))
    {
      for (String string : commodityIds) {
        try
        {
          Commodity commodity = (Commodity)getService().get(this.commodityService.getById(string));
          commodityNames = commodityNames + commodity.getName() + ",";
          String mysql = " primary.OPERATEGFLAG='" + this.operateGflag + "' and  extractValue(xmlType(primary.currentvalue), '/root/id') = '" + commodity.getId() + "'";
          AgencyRMIBean remObject = new AgencyRMIBean(this.request);
          remObject.setCommodityStatus(commodity.getId(), commodity.getTradeMode().charAt(0));
          this.parmaLogServiceJDBC.delete(mysql);
          addResultSessionMsg(this.request, resultValue);
        }
        catch (Exception e)
        {
          e.printStackTrace();
          if (e.getCause().getMessage() != null)
          {
            resultValue = -1;
            addResultSessionMsg(this.request, resultValue);
            this.request.getSession().setAttribute(ActionConstant.RESULTMSG, e.getCause().getMessage());
          }
        }
      }
      if (resultValue == 1) {
        operateContent = "商品" + commodityNames + "实时生效成功";
      } else if (resultValue == -1) {
        operateContent = "商品" + commodityNames + "实时生效失败";
      }
    }
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      OperateLog operateLog = new OperateLog();
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateContent(operateContent);
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      this.operateLogService.add(operateLog);
    }
    return "success";
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] idString = id.split(",");
        this.obj.setPrimary(idString[0]);
        Clone clone = getService().get(this.obj);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        
        resultValue = getService().delete(this.obj);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    Commodity commodity = (Commodity)this.obj;
    BigDecimal minPriceMove = commodity.getMinPriceMove();
    if (minPriceMove != null)
    {
      commodity.setMinHQMove(minPriceMove);
      commodity.setStepMove(minPriceMove);
    }
    int resultValue = getService().add(commodity);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    Commodity commodity = (Commodity)this.obj;
    BigDecimal minPriceMove = commodity.getMinPriceMove();
    if (minPriceMove != null)
    {
      commodity.setMinHQMove(minPriceMove);
      commodity.setStepMove(minPriceMove);
    }
    int resultValue = getService().update(commodity);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
