package gnnt.MEBS.trade.action;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.model.Commoditytradeprop;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.CommoditytradepropService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.vo.InfoForDisplay;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import gnnt.MEBS.trade.service.TradeTimeService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class TradeTimeAction
  extends BaseAction
{
  private String[] del;
  private String[] save;
  private int tradeTimeSequenct;
  @Autowired
  @Qualifier("commoditytradepropService")
  private CommoditytradepropService commoditytradepropService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  
  public String[] getSave()
  {
    return this.save;
  }
  
  public void setSave(String[] save)
  {
    this.save = save;
  }
  
  public void handleCommodityChange()
    throws IOException
  {
    PrintWriter out = null;
    ServletActionContext.getResponse().setContentType("text/plain; charset=GBK");
    out = ServletActionContext.getResponse().getWriter();
    
    List<Commoditytradeprop> saveObj = getSaveObj();
    List<Commoditytradeprop> delObj = getDelObj();
    int bla = this.commoditytradepropService.handleSaveDel(saveObj, delObj);
    if (bla == 1) {
      out.write("操作成功");
    } else {
      out.write("操作失败");
    }
    out.flush();
    out.close();
  }
  
  private List<Commoditytradeprop> getDelObj()
  {
    if (this.del == null) {
      return null;
    }
    List<Commoditytradeprop> l = new ArrayList();
    for (String id : this.del) {
      l.add(new Commoditytradeprop(id, this.tradeTimeSequenct, new Date()));
    }
    return l;
  }
  
  private List<Commoditytradeprop> getSaveObj()
  {
    if (this.save == null) {
      return null;
    }
    List<Commoditytradeprop> l = new ArrayList();
    for (String id : this.save) {
      l.add(new Commoditytradeprop(id, this.tradeTimeSequenct, new Date()));
    }
    return l;
  }
  
  public String[] getDel()
  {
    return this.del;
  }
  
  public void setDel(String[] de)
  {
    this.del = de;
  }
  
  public String displayCommodity()
  {
    Set<String> l = this.commoditytradepropService.getListBySectionId(this.tradeTimeSequenct);
    List<Commodity> cList = this.commodityService.getAllCommodity();
    List<InfoForDisplay> info = new ArrayList();
    String nameStr = null;
    try
    {
      nameStr = new String(this.request.getParameter("name").getBytes("ISO8859-1"), "GBK");
    }
    catch (UnsupportedEncodingException e)
    {
      nameStr = "";
      e.printStackTrace();
    }
    for (Commodity c : cList)
    {
      InfoForDisplay aa = new InfoForDisplay();
      aa.setId(c.getId());
      aa.setName(c.getName());
      aa.setFlag(l.contains(c.getId()));
      info.add(aa);
    }
    ActionContext ac = ActionContext.getContext();
    ac.put("info", info);
    ac.put("day", Integer.valueOf(this.tradeTimeSequenct));
    ac.put("name", nameStr);
    return "success";
  }
  
  public CommodityService getCommodityService()
  {
    return this.commodityService;
  }
  
  public void setCommodityService(CommodityService commodityService)
  {
    this.commodityService = commodityService;
  }
  
  private final transient Log logger = LogFactory.getLog(TradeTimeAction.class);
  @Autowired
  @Qualifier("tradeTimeService")
  private TradeTimeService tradeTimeService;
  @Resource(name="tradeStatusMap")
  private Map tradeStatusMap;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  private int tradeTimeType;
  
  public int getTradeTimeSequenct()
  {
    return this.tradeTimeSequenct;
  }
  
  public void setTradeTimeSequenct(int tradeTimeSequenct)
  {
    this.tradeTimeSequenct = tradeTimeSequenct;
  }
  
  public int getTradeTimeType()
  {
    return this.tradeTimeType;
  }
  
  public void setTradeTimeType(int tradeTimeType)
  {
    this.tradeTimeType = tradeTimeType;
  }
  
  public Map getTradeStatusMap()
  {
    return this.tradeStatusMap;
  }
  
  public String forwardAdd()
  {
    this.request.setAttribute("tradeTimeSize", Integer.valueOf(this.tradeTimeService.getList(null, null).size()));
    return getReturnValue();
  }
  
  public String updateRMI()
  {
    String operateContent = "";
    int returnValue = 1;
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      remObject.refreshTradeTime();
      addResultSessionMsg(this.request, returnValue);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      returnValue = -1;
      if (e.getCause().getMessage() != null)
      {
        addResultSessionMsg(this.request, returnValue);
        this.request.getSession().setAttribute(ActionConstant.RESULTMSG, e.getCause().getMessage());
      }
    }
    if (returnValue == 1) {
      operateContent = "交易节设置实时生效成功！";
    } else if (returnValue == -1) {
      operateContent = "交易节设置实时生效失败！";
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
  
  public InService getService()
  {
    return this.tradeTimeService;
  }
}
