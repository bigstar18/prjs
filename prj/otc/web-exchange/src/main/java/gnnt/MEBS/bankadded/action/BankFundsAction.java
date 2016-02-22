package gnnt.MEBS.bankadded.action;

import gnnt.MEBS.bankadded.model.BankFundTrans;
import gnnt.MEBS.bankadded.model.BankFunds;
import gnnt.MEBS.bankadded.service.BankFundTransService;
import gnnt.MEBS.bankadded.service.BankFundsService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class BankFundsAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BankFundsAction.class);
  @Autowired
  @Qualifier("bankFundsService")
  private BankFundsService bankFundsService;
  @Autowired
  @Qualifier("bankFundTransService")
  private BankFundTransService bankFundTransService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  
  public InService getService()
  {
    return this.bankFundsService;
  }
  
  public String update()
  {
    int resultValue = 0;
    String operateContent = "";
    double funds = Double.parseDouble(this.request.getParameter("funds"));
    this.logger.debug("id:" + this.obj.getId());
    BankFunds bankFunds = (BankFunds)getService().get(this.obj);
    bankFunds.setMarketFeeBalance(Double.valueOf(bankFunds.getMarketFeeBalance().doubleValue() - funds));
    resultValue = getService().update(bankFunds);
    BankFundTrans bankFundTrans = new BankFundTrans();
    bankFundTrans.setBankCode(((BankFunds)this.obj).getBankCode());
    bankFundTrans.setTransType("F");
    bankFundTrans.setiOFlag("O");
    bankFundTrans.setAmount(Double.valueOf(funds));
    bankFundTrans.setCreateTime(new Date());
    if (resultValue == 3)
    {
      this.bankFundTransService.add(bankFundTrans);
      operateContent = "从" + ((BankFunds)this.obj).getBankName() + "划转出手续费：" + bankFundTrans.getAmount();
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
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String viewById()
  {
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      boolean isTradeDate = remObject.isTradeDate();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
      int hour = Integer.parseInt(simpleDateFormat.format(new Date()).substring(0, 2));
      if ((hour <= 7) && (hour >= 0)) {
        isTradeDate = false;
      }
      this.request.setAttribute("isTradeDate", Boolean.valueOf(isTradeDate));
      this.obj = ((BankFunds)getService().getList(new QueryConditions("primary.bankCode", "=", this.request.getParameter("bankCode")), null).get(0));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return getReturnValue();
  }
}
