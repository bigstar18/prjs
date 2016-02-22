package gnnt.MEBS.announcement.promptHandler;

import gnnt.MEBS.announcement.model.Trade;
import gnnt.MEBS.announcement.service.TradeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TradeHandler
  extends AbstractTypeHandler
{
  @Autowired
  @Qualifier("tradeService")
  private TradeService tradeService;
  
  public int handle(Map map, Long oldTradeNo)
  {
    List<Trade> tradeList = null;
    HttpServletRequest request = (HttpServletRequest)ThreadStore.get(ThreadStoreConstant.REQUEST);
    int result = 0;
    QueryConditions queryConditions = new QueryConditions("tradeNo", ">", oldTradeNo);
    PageInfo pageInfo = new PageInfo(1, 1, "tradeNo", true);
    if ((request.getSession().getAttribute(ActionConstant.ORGANIZATIONID) != null) && (!"".equals(request.getSession().getAttribute(ActionConstant.ORGANIZATIONID))))
    {
      queryConditions.addCondition("customerRelateOrg.organizationNo", "=", AclCtrl.getUser(request).getOrganization().getId());
      tradeList = this.tradeService.getTradeList(queryConditions, pageInfo);
    }
    else
    {
      queryConditions.addCondition("o_firmId", "=", AclCtrl.getUser(request).getMemberNo());
      tradeList = this.tradeService.getList(queryConditions, pageInfo);
    }
    if (tradeList.size() > 0)
    {
      map.put("tradeSize", Integer.valueOf(pageInfo.getTotalRecords()));
      map.put("maxTradeNo", ((Trade)tradeList.get(0)).getTradeNo());
    }
    else
    {
      map.put("tradeSize", Integer.valueOf(0));
      map.put("maxTradeNo", Integer.valueOf(-1));
    }
    return result;
  }
}
