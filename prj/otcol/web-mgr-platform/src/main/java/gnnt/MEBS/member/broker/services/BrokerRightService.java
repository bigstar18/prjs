package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.dao.BrokerRightDao;
import gnnt.MEBS.member.broker.model.BrokerMenu;
import gnnt.MEBS.member.broker.model.BrokerRight;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_brokerRightService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerRightService
{
  @Autowired
  @Qualifier("m_brokerRightDao")
  private BrokerRightDao brokerRightDao;
  
  public List<BrokerMenu> getBrokerMenu()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("menuId", "=", Integer.valueOf(-1));
    List localList1 = this.brokerRightDao.getBrokerMenu(localQueryConditions);
    Iterator localIterator = localList1.iterator();
    while (localIterator.hasNext())
    {
      BrokerMenu localBrokerMenu = (BrokerMenu)localIterator.next();
      localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("parentId", "=", localBrokerMenu.getMenuId());
      List localList2 = this.brokerRightDao.getBrokerMenu(localQueryConditions);
      for (int i = 0; i < localList2.size(); i++)
      {
        localQueryConditions = new QueryConditions();
        localQueryConditions.addCondition("parentId", "=", ((BrokerMenu)localList2.get(i)).getMenuId());
        List localList3 = this.brokerRightDao.getBrokerMenu(localQueryConditions);
        ((BrokerMenu)localList2.get(i)).setBrokerMenuList(localList3);
      }
      localBrokerMenu.setBrokerMenuList(localList2);
    }
    return localList1;
  }
  
  public List<BrokerRight> getBrokerRight(QueryConditions paramQueryConditions)
  {
    return this.brokerRightDao.getBrokerRight(paramQueryConditions);
  }
  
  public void saveBrokerRight(String paramString, int[] paramArrayOfInt)
  {
    int i = this.brokerRightDao.getBrokerRightCount(paramString);
    if (i > 0) {
      this.brokerRightDao.deleteBrokerRight(paramString);
    }
    if (paramArrayOfInt != null) {
      for (int m : paramArrayOfInt) {
        this.brokerRightDao.addBrokerRight(paramString, Integer.valueOf(m));
      }
    }
  }
}
