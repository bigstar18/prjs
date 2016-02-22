package gnnt.MEBS.announcement.promptHandler;

import gnnt.MEBS.announcement.model.Trade;
import gnnt.MEBS.announcement.service.TradeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
    HttpServletRequest request = (HttpServletRequest)ThreadStore.get(ThreadStoreConstant.REQUEST);
    int result = 0;
    QueryConditions queryConditions = new QueryConditions("tradeNo", ">", oldTradeNo);
    PageInfo pageInfo = new PageInfo(1, 1, "tradeNo", true);
    
    List<Trade> tradeList = this.tradeService.getList(queryConditions, pageInfo);
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
