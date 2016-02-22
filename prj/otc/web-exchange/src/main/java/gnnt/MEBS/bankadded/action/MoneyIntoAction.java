package gnnt.MEBS.bankadded.action;

import gnnt.MEBS.bankadded.service.MoneyIntoService;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
public class MoneyIntoAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MoneyIntoAction.class);
  @Autowired
  @Qualifier("moneyIntoService")
  private MoneyIntoService moneyIntoService;
  @Autowired
  @Qualifier("applyService")
  protected InService applyService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  
  public InService getService()
  {
    return this.moneyIntoService;
  }
  
  public String add()
  {
    String inOrout = this.request.getParameter("inOrout");
    if ("0".equals(inOrout))
    {
      String frimId = this.request.getParameter("firmId");
      String bankId = this.request.getParameter("bankId");
      String money = this.request.getParameter("money");
      
      Map map = new HashMap();
      map.put("firmId", frimId);
      map.put("money", money);
      if ((bankId == null) || ("".equals(bankId))) {
        map.put("bankId", null);
      } else {
        map.put("bankId", bankId);
      }
      int resultValue = 1;
      try
      {
        this.moneyIntoService.add(map, 0);
      }
      catch (Exception e)
      {
        resultValue = -1;
      }
      String operateContent = "";
      operateContent = "交易商" + frimId + ",向银行" + bankId + ",虚拟资金入金" + money;
      if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
      {
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(null);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        operateLog.setOperateDate(new Date());
        operateLog.setOperatorType("E");
        operateLog.setOperateIp(this.request.getRemoteAddr());
        if (resultValue == 1) {
          operateContent = operateContent + ",成功";
        } else {
          operateContent = operateContent + ",失败";
        }
        operateLog.setOperateContent(operateContent);
        this.operateLogService.add(operateLog);
      }
      addResultMsg(this.request, resultValue);
    }
    else
    {
      String frimId = this.request.getParameter("firmId");
      String bankId = this.request.getParameter("bankId");
      String money = this.request.getParameter("money");
      
      Map map = new HashMap();
      map.put("firmId", frimId);
      map.put("money", money);
      if ((bankId == null) || ("".equals(bankId))) {
        map.put("bankId", null);
      } else {
        map.put("bankId", bankId);
      }
      int num = 1;
      try
      {
        num = this.moneyIntoService.outMoney(map, 0);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      String operateContent = "";
      operateContent = "交易商" + frimId + ",从银行" + bankId + ",虚拟资金出金" + money;
      if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
      {
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(null);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        operateLog.setOperateDate(new Date());
        operateLog.setOperatorType("E");
        operateLog.setOperateIp(this.request.getRemoteAddr());
        if (num >= 0) {
          operateContent = operateContent + ",成功";
        } else if (num == -1) {
          operateContent = "资金不足,虚拟资金出金失败";
        } else if (num == -2) {
          operateContent = "可出虚拟资金不足,虚拟资金出金失败";
        } else {
          operateContent = operateContent + ",失败";
        }
        operateLog.setOperateContent(operateContent);
        this.operateLogService.add(operateLog);
      }
      if (num >= 0) {
        this.request.setAttribute(ActionConstant.RESULTMSG, "虚拟资金出金成功");
      } else if (num == -1) {
        this.request.setAttribute(ActionConstant.RESULTMSG, "资金不足,虚拟资金出金失败");
      } else if (num == -2) {
        this.request.setAttribute(ActionConstant.RESULTMSG, "可出虚拟资金不足,虚拟资金出金失败");
      } else {
        this.request.setAttribute(ActionConstant.RESULTMSG, "虚拟资金出金失败");
      }
      this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    }
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.logger.debug("enter viewById");
    return getReturnValue();
  }
  
  public String addByManual()
  {
    String inOrout = this.request.getParameter("inOrout");
    if ("0".equals(inOrout))
    {
      String frimId = this.request.getParameter("firmId");
      String bankId = this.request.getParameter("bankId");
      String money = this.request.getParameter("money");
      
      Map map = new HashMap();
      map.put("firmId", frimId);
      map.put("money", money);
      if ((bankId == null) || ("".equals(bankId))) {
        map.put("bankId", null);
      } else {
        map.put("bankId", bankId);
      }
      int resultValue = 1;
      try
      {
        this.moneyIntoService.add(map, 0);
      }
      catch (Exception e)
      {
        resultValue = -1;
      }
      String operateContent = "";
      operateContent = "交易商" + frimId + ",向银行" + bankId + ",手动入金" + money;
      if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
      {
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(null);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        operateLog.setOperateDate(new Date());
        operateLog.setOperatorType("E");
        operateLog.setOperateIp(this.request.getRemoteAddr());
        if (resultValue == 1) {
          operateContent = operateContent + ",成功";
        } else {
          operateContent = operateContent + ",失败";
        }
        operateLog.setOperateContent(operateContent);
        this.operateLogService.add(operateLog);
      }
      addResultMsg(this.request, resultValue);
    }
    else
    {
      String frimId = this.request.getParameter("firmId");
      String bankId = this.request.getParameter("bankId");
      String money = this.request.getParameter("money");
      
      Map map = new HashMap();
      map.put("firmId", frimId);
      map.put("money", money);
      if ((bankId == null) || ("".equals(bankId))) {
        map.put("bankId", null);
      } else {
        map.put("bankId", bankId);
      }
      int num = 1;
      try
      {
        num = this.moneyIntoService.outMoney(map, 0);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      String operateContent = "";
      operateContent = "交易商" + frimId + ",从银行" + bankId + ",手动出金" + money;
      if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
      {
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(null);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        operateLog.setOperateDate(new Date());
        operateLog.setOperatorType("E");
        operateLog.setOperateIp(this.request.getRemoteAddr());
        if (num >= 0) {
          operateContent = operateContent + ",成功";
        } else if (num == -1) {
          operateContent = "资金不足,手动出金失败";
        } else {
          operateContent = operateContent + ",失败";
        }
        operateLog.setOperateContent(operateContent);
        this.operateLogService.add(operateLog);
      }
      if (num >= 0) {
        this.request.setAttribute(ActionConstant.RESULTMSG, "手动出金成功");
      } else if (num == -1) {
        this.request.setAttribute(ActionConstant.RESULTMSG, "资金不足,手动出金失败");
      } else {
        this.request.setAttribute(ActionConstant.RESULTMSG, "手动出金失败");
      }
      this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    }
    return getReturnValue();
  }
}
