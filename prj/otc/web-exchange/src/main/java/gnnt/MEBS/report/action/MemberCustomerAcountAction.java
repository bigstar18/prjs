package gnnt.MEBS.report.action;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.report.service.MemberCustomerAcountService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberCustomerAcountAction
  extends BaseReportAction
{
  @Autowired
  @Qualifier("memberCustomerAcountService")
  private MemberCustomerAcountService memberCustomerAcountService;
  
  public QueryConditions commonQueryConditions()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    if (qc == null)
    {
      qc = new QueryConditions();
      qc.addCondition("1", "=", Integer.valueOf(1));
    }
    return qc;
  }
  
  public List memberCustomerAllList(QueryConditions qc)
  {
    List list = this.memberCustomerAcountService.getAllList(qc);
    return list;
  }
  
  public String memberCustomerDistinctList()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    this.dataList = this.memberCustomerAcountService.getDistinctList(qc);
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    return getReturnValue();
  }
}
