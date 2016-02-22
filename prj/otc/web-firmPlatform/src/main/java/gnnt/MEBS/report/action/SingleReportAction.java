package gnnt.MEBS.report.action;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.report.service.SingleReportService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class SingleReportAction
  extends BaseReportAction
{
  private SingleReportService singleReportService;
  @Resource(name="flagMap")
  private Map flagMap;
  @Resource(name="bankMap")
  private Map bankMap;
  @Resource(name="customerStatusMap")
  private Map customerStatusMap;
  
  public Map getCustomerStatusMap()
  {
    return this.customerStatusMap;
  }
  
  public Map getBankMap()
  {
    return this.bankMap;
  }
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public SingleReportService getSingleReportService()
  {
    return this.singleReportService;
  }
  
  public void setSingleReportService(SingleReportService singleReportService)
  {
    this.singleReportService = singleReportService;
  }
  
  public String getTraderList()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    this.dataList = this.singleReportService.getTraderList(qc);
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    return getReturnValue();
  }
}
