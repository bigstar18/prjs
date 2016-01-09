package gnnt.MEBS.bank.mgr.action;

import gnnt.MEBS.bank.mgr.model.Bank;
import gnnt.MEBS.bank.mgr.model.Dictionary;
import gnnt.MEBS.bank.mgr.model.FeeInfo;
import gnnt.MEBS.bank.mgr.model.FirmUser;
import gnnt.MEBS.bank.mgr.model.Log;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("feeAction")
@Scope("request")
public class FeeAction extends EcsideAction
{
  private static final long serialVersionUID = 1616441543210750501L;
  private List<FeeInfo> feeInfoList;

  public List<FeeInfo> getFeeInfoList()
  {
    return this.feeInfoList;
  }

  public void setFeeInfoList(List<FeeInfo> feeInfoList)
  {
    this.feeInfoList = feeInfoList;
  }

  public String setUpBankFeeForward()
  {
    String bankID = this.request.getParameter("bankID");
    if ((bankID == null) || (bankID.trim().length() <= 0)) {
      return "success";
    }

    Bank bank = new Bank();
    bank.setBankID(bankID);
    bank = (Bank)getService().get(bank);
    if (bank == null) {
      return "success";
    }
    this.request.setAttribute("bank", bank);

    QueryConditions dicqc = new QueryConditions();
    dicqc.addCondition("type", "=", Integer.valueOf(3));
    dicqc.addCondition("bankID", "=", bankID);
    PageRequest dicpageRequest = new PageRequest(1, 100, dicqc, " order by id ");
    Page dicPage = getService().getPage(dicpageRequest, new Dictionary());
    if ((dicPage == null) || (dicPage.getResult().size() <= 0)) {
      return "success";
    }

    this.request.setAttribute("diclist", dicPage.getResult());

    String type = this.request.getParameter("type");
    if ((type == null) || (type.trim().length() <= 0)) {
      Dictionary dictionary = (Dictionary)dicPage.getResult().get(0);
      type = dictionary.getValue();
    }
    this.request.setAttribute("type", type);

    QueryConditions feeqc = new QueryConditions();
    feeqc.addCondition("type", "=", type);
    feeqc.addCondition("userID", "=", bankID);
    PageRequest feepageRequest = new PageRequest(1, 100, feeqc, " order by id ");
    Page feePage = getService().getPage(feepageRequest, new FeeInfo());
    this.request.setAttribute("pageInfo", feePage);

    return "success";
  }

  public String setUpBankFee()
  {
    String bankID = this.request.getParameter("bankID");
    if ((bankID == null) || (bankID.trim().length() <= 0))
    {
      addReturnValue(-1, 133201L);
      return "success";
    }

    String type = this.request.getParameter("type");
    if ((type == null) || (type.trim().length() <= 0))
    {
      addReturnValue(-1, 133202L);
      return "success";
    }

    QueryConditions feeqc = new QueryConditions();
    feeqc.addCondition("type", "=", type);
    feeqc.addCondition("userID", "=", bankID);
    PageRequest feepageRequest = new PageRequest(1, 100, feeqc);
    Page feePage = getService().getPage(feepageRequest, new FeeInfo());

    getService().deleteBYBulk(feePage.getResult());

    if (this.feeInfoList != null) {
      for (FeeInfo fee : this.feeInfoList) {
        if (fee != null) {
          fee.setUserID(bankID);
          fee.setType(type);
          fee.setUpdateTime(new Date());
          if (fee.getTmode().intValue() == 0) {
            fee.setRate(Double.valueOf(fee.getRate().doubleValue() / 100.0D));
          }
          getService().add(fee.clone());
        }
      }
    }

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    Log log = new Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());
    log.setLogcontent("修改银行 " + bankID + " 手续费设置");
    getService().add(log);

    addReturnValue(1, 113201L);

    return "success";
  }

  public String setUpFirmFeeForward()
  {
    String firmID = this.request.getParameter("firmID");
    if ((firmID == null) || (firmID.trim().length() <= 0)) {
      return "success";
    }

    FirmUser firm = new FirmUser();
    firm.setFirmID(firmID);
    firm = (FirmUser)getService().get(firm);
    if (firm == null) {
      return "success";
    }
    this.request.setAttribute("firm", firm);

    QueryConditions dicqc = new QueryConditions();
    dicqc.addCondition("type", "=", Integer.valueOf(3));
    dicqc.addCondition("bankID", "is", "null");
    PageRequest dicpageRequest = new PageRequest(1, 100, dicqc, " order by id ");
    Page dicPage = getService().getPage(dicpageRequest, new Dictionary());
    if ((dicPage == null) || (dicPage.getResult().size() <= 0)) {
      return "success";
    }

    this.request.setAttribute("diclist", dicPage.getResult());

    String type = this.request.getParameter("type");
    if ((type == null) || (type.trim().length() <= 0)) {
      Dictionary dictionary = (Dictionary)dicPage.getResult().get(0);
      type = dictionary.getValue();
    }
    this.request.setAttribute("type", type);

    QueryConditions feeqc = new QueryConditions();
    feeqc.addCondition("type", "=", type);
    feeqc.addCondition("userID", "=", firmID);
    PageRequest feepageRequest = new PageRequest(1, 100, feeqc, " order by id ");
    Page feePage = getService().getPage(feepageRequest, new FeeInfo());
    this.request.setAttribute("pageInfo", feePage);

    return "success";
  }

  public String setUpFirmFee()
  {
    String firmID = this.request.getParameter("firmID");
    if ((firmID == null) || (firmID.trim().length() <= 0))
    {
      addReturnValue(-1, 133203L);
      return "success";
    }

    String type = this.request.getParameter("type");
    if ((type == null) || (type.trim().length() <= 0))
    {
      addReturnValue(-1, 133204L);
      return "success";
    }

    QueryConditions feeqc = new QueryConditions();
    feeqc.addCondition("type", "=", type);
    feeqc.addCondition("userID", "=", firmID);
    PageRequest feepageRequest = new PageRequest(1, 100, feeqc);
    Page feePage = getService().getPage(feepageRequest, new FeeInfo());

    getService().deleteBYBulk(feePage.getResult());

    if (this.feeInfoList != null) {
      for (FeeInfo fee : this.feeInfoList) {
        if (fee != null) {
          fee.setUserID(firmID);
          fee.setType(type);
          fee.setUpdateTime(new Date());
          if (fee.getTmode().intValue() == 0) {
            fee.setRate(Double.valueOf(fee.getRate().doubleValue() / 100.0D));
          }
          getService().add(fee.clone());
        }
      }
    }

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    Log log = new Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());
    log.setLogcontent("修改交易商 " + firmID + " 手续费设置");
    getService().add(log);

    addReturnValue(1, 113202L);

    return "success";
  }
}