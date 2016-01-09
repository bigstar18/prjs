package gnnt.MEBS.bank.mgr.action;

import gnnt.MEBS.bank.mgr.model.Bank;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("bankAction")
@Scope("request")
public class BankAction extends EcsideAction
{
  private static final long serialVersionUID = 8319794578361432660L;

  @Resource(name="bankStatus")
  private Map<Integer, String> bankStatus;

  @Resource(name="bankControl")
  private Map<Integer, String> bankControl;

  @Autowired
  @Qualifier("capitalProcessorRMI")
  private CapitalProcessorRMI capitalProcessorRMI;

  public Map<Integer, String> getBankStatus()
  {
    return this.bankStatus;
  }

  public Map<Integer, String> getBankControl()
  {
    return this.bankControl;
  }

  public String bankList()
    throws Exception
  {
    return listByLimit();
  }

  public String bankDetails()
    throws Exception
  {
    return viewById();
  }

  public String updateBank()
  {
    Bank bank = (Bank)this.entity;
    if (bank.getMaxAuditMoney() == null) {
      bank.setMaxAuditMoney(Double.valueOf(0.0D));
    }
    if (bank.getMaxPersgltransMoney() == null) {
      bank.setMaxPersgltransMoney(Double.valueOf(0.0D));
    }
    if (bank.getMaxPertransCount() == null) {
      bank.setMaxPertransCount(Integer.valueOf(0));
    }
    if (bank.getMaxPertransMoney() == null) {
      bank.setMaxPertransMoney(Double.valueOf(0.0D));
    }
    update();

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());
    log.setLogcontent("修改银行 " + bank.getBankID() + " 信息");
    getService().add(log);
    return "success";
  }

  public String forbiddenBank()
  {
    String[] ids = this.request.getParameterValues("ids");
    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 132801L);
      return "success";
    }

    for (String bankID : ids) {
      Bank bank = new Bank();
      bank.setBankID(bankID);
      bank.setValidflag(Integer.valueOf(1));
      getService().update(bank);

      User user = (User)this.request.getSession().getAttribute("CurrentUser");
      gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
      log.setLogDate(new Date());
      log.setLogIP(user.getIpAddress());
      log.setLogopr(user.getUserId());
      log.setLogcontent("禁用银行 " + bank.getBankID());
      getService().add(log);
    }

    addReturnValue(1, 112801L);
    return "success";
  }

  public String startUsingBank()
  {
    String[] ids = this.request.getParameterValues("ids");
    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 132811L);
      return "success";
    }

    for (String bankID : ids) {
      Bank bank = new Bank();
      bank.setBankID(bankID);
      bank.setValidflag(Integer.valueOf(0));
      getService().update(bank);

      User user = (User)this.request.getSession().getAttribute("CurrentUser");
      gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
      log.setLogDate(new Date());
      log.setLogIP(user.getIpAddress());
      log.setLogopr(user.getUserId());
      log.setLogcontent("启用银行 " + bank.getBankID());
      getService().add(log);
    }

    addReturnValue(1, 112811L);
    return "success";
  }

  public String gotobankTransferMoneyGFB() {
    this.logger.info("进入国付宝收益划拨页面");
    return "success";
  }

  public String bankTransferMoneyGFB() {
    this.logger.info("国付宝收益划拨执行");
    double money = Double.parseDouble(this.request.getParameter("money"));
    String bankID = this.request.getParameter("bankID");
    String FIRMID = "MarketGSOut";
    int express = 0;
    try
    {
      String msg = null;
      ReturnValue returnValue = this.capitalProcessorRMI.outMoneyGS(bankID, money, FIRMID, "", null, "market_out", express, 0);
      if (returnValue.result >= 0L)
      {
        if (returnValue.result == 5L)
        {
          msg = "国付宝收益资金划转处理中！";
        } else if (returnValue.result == 1L)
        {
          msg = "国付宝收益资金划转已发送国付宝处理，请在资金流水中查看流水状态！";
        }
        else
        {
          msg = "国付宝收益资金划转成功！";
        }
      }
      else msg = returnValue.remark == null ? "系统异常" : returnValue.remark;

      this.logger.info(msg);
      addReturnValue(1, 130000L, new Object[] { msg });

      User user = (User)this.request.getSession().getAttribute("CurrentUser");
      gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
      log.setLogDate(new Date());
      log.setLogIP(user.getIpAddress());
      log.setLogopr(user.getUserId());
      log.setLogcontent("国付宝收益以划拨，金额：" + money);
      getService().add(log);
    }
    catch (RemoteException e)
    {
      this.logger.error("国付宝收益资金划转，连接处理器异常", e);
      addReturnValue(-1, 130000L, new Object[] { "国付宝收益资金划转，连接处理器异常" });
      return "success";
    } catch (Exception e) {
      this.logger.error("国付宝收益资金划转，系统异常", e);
      addReturnValue(-1, 130000L, new Object[] { "国付宝收益资金划转，系统异常" });
      return "success";
    }

    return "success";
  }
}