package gnnt.MEBS.bank.mgr.action;

import gnnt.MEBS.bank.mgr.model.FirmUser;
import gnnt.MEBS.bank.mgr.model.Log;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("firmAction")
@Scope("request")
public class FirmAction extends EcsideAction
{
  private static final long serialVersionUID = 943238824138457549L;

  public String firmList()
    throws Exception
  {
    return listByLimit();
  }

  public String firmDetails()
    throws Exception
  {
    return viewById();
  }

  public String updateFirm()
  {
    FirmUser firm = (FirmUser)this.entity;
    if (firm.getMaxAuditMoney() == null) {
      firm.setMaxAuditMoney(Double.valueOf(0.0D));
    }
    if (firm.getMaxPersgltransMoney() == null) {
      firm.setMaxPersgltransMoney(Double.valueOf(0.0D));
    }
    if (firm.getMaxPertranscount() == null) {
      firm.setMaxPertranscount(Long.valueOf(0L));
    }
    if (firm.getMaxPertransMoney() == null) {
      firm.setMaxPertransMoney(Double.valueOf(0.0D));
    }

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    Log log = new Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());
    log.setLogcontent("修改交易商 " + firm.getFirmID() + " 信息");
    getService().add(log);
    return update();
  }
}