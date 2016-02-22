package gnnt.MEBS.report.action;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.util.EcsideJDBCUtil;
import gnnt.MEBS.report.service.SingleReportService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SingleReportAction
  extends BaseReportAction
{
  private final transient Log logger = LogFactory.getLog(SingleReportAction.class);
  private SingleReportService singleReportService;
  private List reportList;
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
  
  public List getReportList()
  {
    return this.reportList;
  }
  
  public void setReportList(List reportList)
  {
    this.reportList = reportList;
  }
  
  public String getTraderList()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    this.dataList = this.singleReportService.getTraderList(qc);
    this.request.setAttribute("list", this.dataList);
    this.logger.debug("list:" + this.dataList.getClass().getName());
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    return getReturnValue();
  }
  
  public String getList()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "clearDate";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    Map<String, Object> map = EcsideJDBCUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.reportList = this.singleReportService.getJDBCList(qc, pageInfo);
    this.request.setAttribute("list", this.reportList);
    this.logger.debug("list:" + this.reportList.getClass().getName());
    EcsideJDBCUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    String date = strDate();
    this.request.setAttribute("date", date);
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    return getReturnValue();
  }
  
  protected PageInfo getPageInfo(Map<String, Object> map)
  {
    return (PageInfo)map.get(ActionConstant.PAGEINFO);
  }
  
  protected QueryConditions getQueryConditions(Map<String, Object> map)
  {
    return (QueryConditions)map.get(ActionConstant.QUERYCONDITIONS);
  }
  
  protected void returnBaseMsg(PageInfo pageInfo)
  {
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    this.request.setAttribute(ActionConstant.PAGEINFO, pageInfo);
  }
}
