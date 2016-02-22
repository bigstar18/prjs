package gnnt.MEBS.trade.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.common.filter.HQServerInfoFilter;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.Commodity;
import gnnt.MEBS.trade.model.HQServerInfo;
import gnnt.MEBS.trade.model.vo.HQServerInfoVO;
import gnnt.MEBS.trade.service.HQServerInfoService;
import gnnt.MEBS.trade.service.TQLogService;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("hqServerMonitorAction")
@Scope("request")
public class HQServerMonitorAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(HQServerMonitorAction.class);
  @Autowired
  @Qualifier("hqServerInfoService")
  private HQServerInfoService hqServerInfoService;
  
  public InService getService()
  {
    return this.hqServerInfoService;
  }
  
  private JSONArray json = new JSONArray();
  
  public JSONArray getJson()
  {
    return this.json;
  }
  
  public String serverInfo()
  {
    QueryConditions qc = new QueryConditions();
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.serverRank", false);
    List<HQServerInfo> hqServerInfoList = this.hqServerInfoService.getList(qc, 
      pageInfo);
    
    TQLogService tqLogService = (TQLogService)
      SpringContextHelper.getBean("tqLogService");
    List<Commodity> tcExchangeRateList = tqLogService.getExList();
    this.request.setAttribute("serverInfoList", hqServerInfoList);
    this.request.setAttribute("commodityList", tcExchangeRateList);
    return "success";
  }
  
  public String getServerInfo()
  {
    QueryConditions qc = new QueryConditions();
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.serverRank", false);
    List<HQServerInfo> list = this.hqServerInfoService.getList(qc, pageInfo);
    for (int i = 0; i < list.size(); i++)
    {
      HQServerInfo hqServerInfo = new HQServerInfo();
      hqServerInfo = (HQServerInfo)list.get(i);
      HQServerInfoVO hqServerInfoVO = (HQServerInfoVO)HQServerInfoFilter.serverInfoTable.get(hqServerInfo.getId());
      hqServerInfoVO.setIsUsed(hqServerInfo.getIsUsed());
    }
    this.json.add(HQServerInfoFilter.serverInfoTable);
    

    return "success";
  }
}
