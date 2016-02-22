package gnnt.MEBS.trade.action;

import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.vo.TradeManageVO;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import gnnt.MEBS.trade.service.AgencyService;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
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

@Component
@Scope("request")
public class AgencyAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(AgencyAction.class);
  @Autowired
  @Qualifier("agencyService")
  private AgencyService agencyService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  @Resource(name="trStatusMap")
  private Map<Integer, String> statusMap;
  @Resource(name="tradeLogMap")
  private Map<Integer, String> tradeLogMap;
  @Resource(name="tradeRunModeMap")
  private Map tradeRunModeMap;
  private TradeManageVO tradeManageVo;
  
  public TradeManageVO getTradeManageVo()
  {
    return this.tradeManageVo;
  }
  
  public void setTradeManageVo(TradeManageVO tradeManageVo)
  {
    this.tradeManageVo = tradeManageVo;
  }
  
  public Map getTradeRunModeMap()
  {
    return this.tradeRunModeMap;
  }
  
  public Map<Integer, String> getStatusMap()
  {
    return this.statusMap;
  }
  
  public InService getService()
  {
    return null;
  }
  
  public String update()
  {
    this.obj = null;
    int result = 0;
    boolean flagLog = false;
    OperateLog operateLog = null;
    String type = this.tradeManageVo.getStatus();
    String operateContent = "";
    this.logger.debug("type:" + type);
    this.logger.debug("time:" + this.tradeManageVo.getRecoverTime());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    operateContent = "手工点击" + (String)this.tradeLogMap.get(type);
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      flagLog = true;
      operateLog = new OperateLog();
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateContent(operateContent);
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      this.operateLogService.add(operateLog);
    }
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      if ("99".equals(type)) {
        remObject.timingContinueTrade(this.tradeManageVo.getRecoverTime());
      } else if (type.equals("5")) {
        remObject.ctlTrade(0);
      } else if (type.equals("6")) {
        remObject.ctlTrade(1);
      } else if (type.equals("7")) {
        remObject.close();
      } else if (type.equals("8")) {
        remObject.start();
      } else if (type.equals("9")) {
        remObject.tradeEnd();
      } else if ("online".equals(type)) {
        remObject.refreshTradeTime();
      }
      if ("99".equals(type)) {
        addResultMsg(this.request, 1);
      } else {
        addResultSessionMsg(this.request, 1);
      }
    }
    catch (RemoteException e)
    {
      result = -1;
      e.printStackTrace();
      if (e.getCause().getMessage() != null)
      {
        this.request.getSession().setAttribute(ActionConstant.RESULTMSG, e.getCause().getMessage());
        this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(800));
      }
    }
    catch (Exception e)
    {
      result = -2;
      e.printStackTrace();
      this.request.getSession().setAttribute(ActionConstant.RESULTMSG, "操作失败");
      this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(800));
    }
    if ("99".equals(type)) {
      operateContent = "设定恢复时间:" + this.tradeManageVo.getRecoverTime();
    } else if (result < 0) {
      operateContent = "交易日期：" + simpleDateFormat.format(this.agencyService.getTradeManageVO(null, null).getTradeDate()) + "手工点击" + (String)this.tradeLogMap.get(type) + this.request.getSession().getAttribute(ActionConstant.RESULTMSG);
    } else {
      operateContent = 
        "交易日期：" + simpleDateFormat.format(this.agencyService.getTradeManageVO(null, null).getTradeDate()) + "的状态从：" + (String)this.statusMap.get(Integer.valueOf(Integer.parseInt(this.request.getParameter("systemStatusLog")))) + "改为" + (String)this.statusMap.get(this.agencyService.getTradeManageVO(null, null).getStatus());
    }
    if (flagLog)
    {
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
  
  public String viewById()
  {
    this.logger.debug("Entering 'viewById' method");
    Thread thread = new Thread();
    try
    {
      Thread.sleep(1000L);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    this.tradeManageVo = this.agencyService.getTradeManageVO(null, null);
    this.logger.debug("sysDate:" + this.tradeManageVo.getSysDate());
    this.logger.debug("RunMode:" + this.tradeManageVo.getRunMode());
    this.logger.debug("Status:" + this.tradeManageVo.getStatus());
    this.logger.debug("TradeDate:" + this.tradeManageVo.getTradeDate());
    this.obj = this.tradeManageVo;
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      this.request.setAttribute("dateFlag", Boolean.valueOf(remObject.isTradeDate()));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "success";
  }
}
