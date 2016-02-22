package gnnt.MEBS.trade.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.HQServerInfo;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import gnnt.MEBS.trade.service.HQServerInfoService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("hqServerInfoAction")
@Scope("request")
public class HQServerInfoAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(HQServerInfoAction.class);
  @Autowired
  @Qualifier("hqServerInfoService")
  private HQServerInfoService hqServerInfoService;
  
  public InService getService()
  {
    return this.hqServerInfoService;
  }
  
  public String serverInfolist()
  {
    this.logger.debug("enter HQServerInfoAction serverInfolist");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.serverRank";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    if (this.request.getAttribute("sortString") != null)
    {
      String orderString = (String)this.request.getAttribute("sortString");
      ThreadStore.put("orderString", orderString);
    }
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    

    QueryConditions qc = getQueryConditions(map);
    this.resultList = getService().getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter HQServerInfoAction---update");
    String idStr = this.request.getParameter("idStr");
    int resultValue = 0;
    if ((idStr != null) && (!"".equals(idStr)))
    {
      String[] ids = idStr.split("_");
      if ((ids != null) && (ids.length > 0)) {
        for (int i = 0; i < ids.length; i++)
        {
          HQServerInfo hqServerInfo = new HQServerInfo();
          hqServerInfo.setId(Integer.valueOf(Integer.parseInt(ids[i])));
          hqServerInfo.setServerRank(Integer.valueOf(i));
          resultValue = getService().update(hqServerInfo);
        }
      }
    }
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      remObject.refreshHQserverinfo();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      this.logger.error("updateRMI:调用出错" + ex.getMessage());
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
