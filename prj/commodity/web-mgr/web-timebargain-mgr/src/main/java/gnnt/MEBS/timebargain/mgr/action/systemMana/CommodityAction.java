package gnnt.MEBS.timebargain.mgr.action.systemMana;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.systemMana.CommodityModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("commodityAction")
@Scope("request")
public class CommodityAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;
  private List commodityList = new ArrayList();

  public List getCommodityList()
  {
    return this.commodityList;
  }

  public void setCommodityList(List paramList)
  {
    this.commodityList = paramList;
  }

  public String getCommodityListOrder()
    throws Exception
  {
    this.logger.debug("-----------getCommodityListOrder获取商品list----------------");
    PageRequest localPageRequest = getPageRequest(this.request);
    localPageRequest.setSortColumns(" and status <> 1 order by commodityID");
    try
    {
      Page localPage = getService().getPage(localPageRequest, this.entity);
      List localList = localPage.getResult();
      for (int i = 0; i < localList.size(); i++)
      {
        CommodityModel localCommodityModel = (CommodityModel)localList.get(i);
        this.commodityList.add(localCommodityModel);
      }
    }
    catch (Exception localException)
    {
      this.logger.debug("--------CommodityAction method getCommodityListOrder Error----");
      localException.printStackTrace();
    }
    return "success";
  }
}