package gnnt.MEBS.finance.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("voucherModelAction")
@Scope("request")
public class VoucherModelAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;

  @Resource(name="voucherModel_need")
  private Map<String, String> voucherModel_need;

  public Map<String, String> getVoucherModel_need()
  {
    return this.voucherModel_need;
  }

  public void setVoucherModel_need(Map<String, String> paramMap)
  {
    this.voucherModel_need = paramMap;
  }
}