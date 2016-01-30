package gnnt.MEBS.integrated.mgr.service;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.integrated.mgr.model.usermanage.Trader;
import gnnt.MEBS.integrated.mgr.model.usermanage.TraderModule;
import java.util.Iterator;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("traderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class TraderService
  extends StandardService
{
  public void addTrader(Trader paramTrader)
  {
    if (paramTrader != null)
    {
      Set localSet = paramTrader.getTraderModuleSet();
      paramTrader.setTraderModuleSet(null);
      add(paramTrader);
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        TraderModule localTraderModule = (TraderModule)localIterator.next();
        localTraderModule.setTrader(paramTrader);
        add(localTraderModule);
      }
    }
  }
  
  public void updateTrader(Trader paramTrader)
  {
    if (paramTrader != null)
    {
      Set localSet = paramTrader.getTraderModuleSet();
      paramTrader.setTraderModuleSet(null);
      update(paramTrader);
      if (localSet != null)
      {
        QueryConditions localQueryConditions = new QueryConditions();
        localQueryConditions.addCondition("trader.traderId", "=", paramTrader.getTraderId());
        PageRequest localPageRequest = new PageRequest(1, 100, localQueryConditions, "");
        Page localPage = getPage(localPageRequest, new TraderModule());
        deleteBYBulk(localPage.getResult());
        Iterator localIterator = localSet.iterator();
        while (localIterator.hasNext())
        {
          TraderModule localTraderModule = (TraderModule)localIterator.next();
          localTraderModule.setTrader(paramTrader);
          add(localTraderModule);
        }
      }
    }
  }
}
