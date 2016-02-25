package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.dao.BrokerRewardPropsDao;
import gnnt.MEBS.member.broker.model.BrokerRewardProps;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_brokerRewardPropsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerRewardPropsService
{
  @Autowired
  @Qualifier("m_brokerRewardPropsDao")
  private BrokerRewardPropsDao brokerRewardPropsDao;
  
  public BrokerRewardProps getBrokerRewardProps(String paramString1, String paramString2, String paramString3)
  {
    BrokerRewardProps localBrokerRewardProps = null;
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("breedid", "=", paramString1);
    localQueryConditions.addCondition("brokerid", "=", paramString2);
    localQueryConditions.addCondition("moduleId", "=", paramString3);
    List localList = this.brokerRewardPropsDao.getBrokerRewardPropsList(localQueryConditions, null);
    if (localList.size() > 0) {
      localBrokerRewardProps = (BrokerRewardProps)localList.get(0);
    }
    return localBrokerRewardProps;
  }
  
  public List getBrokerRewardPropsList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.brokerRewardPropsDao.getBrokerRewardPropsTableList(paramQueryConditions, paramPageInfo);
  }
  
  public int BrokerRewardPropsAdd(BrokerRewardProps paramBrokerRewardProps)
  {
    int i = 0;
    if ((paramBrokerRewardProps.getBrokerId().equals("-1")) && (paramBrokerRewardProps.getBreedId().equals("-1")))
    {
      i = -2;
      return i;
    }
    BrokerRewardProps localBrokerRewardProps = getBrokerRewardProps(paramBrokerRewardProps.getBreedId(), paramBrokerRewardProps.getBrokerId(), paramBrokerRewardProps.getModuleId());
    if (localBrokerRewardProps != null)
    {
      i = -1;
    }
    else
    {
      this.brokerRewardPropsDao.insertBrokerRewardProps(paramBrokerRewardProps);
      i = 1;
    }
    return i;
  }
  
  public int BrokerRewardPropsMod(BrokerRewardProps paramBrokerRewardProps)
  {
    int i = 1;
    this.brokerRewardPropsDao.updateBrokerRewardProps(paramBrokerRewardProps);
    return i;
  }
  
  public int BrokerRewardPropsDel(BrokerRewardProps paramBrokerRewardProps)
  {
    int i = 1;
    this.brokerRewardPropsDao.deleteBrokerRewardProps(paramBrokerRewardProps);
    return i;
  }
  
  public BrokerRewardProps getBrokerRewardProps()
  {
    return getBrokerRewardProps("-1", "-1", "-1");
  }
  
  public void updateBrokerRewardPropsByPrimaryKey(BrokerRewardProps paramBrokerRewardProps)
  {
    this.brokerRewardPropsDao.updateBrokerRewardProps(paramBrokerRewardProps);
  }
  
  public void updateBrokerRewardProps(BrokerRewardProps paramBrokerRewardProps)
  {
    this.brokerRewardPropsDao.updateBrokerRewardProps(paramBrokerRewardProps);
  }
  
  public List getTbBreeds()
  {
    return this.brokerRewardPropsDao.getTbBreeds();
  }
  
  public List getZcjsBreeds()
  {
    return this.brokerRewardPropsDao.getZcjsBreeds();
  }
  
  public List getBrokers()
  {
    return this.brokerRewardPropsDao.getBrokers();
  }
  
  public List getBreeds()
  {
    return this.brokerRewardPropsDao.getBreeds();
  }
  
  public void insertBrokerRewardPropsAll(BrokerRewardProps paramBrokerRewardProps)
  {
    this.brokerRewardPropsDao.insertBrokerRewardProps(paramBrokerRewardProps);
  }
  
  public List getBrokerRewardPropsBreedList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.brokerRewardPropsDao.getBrokerRewardPropsBreedList(paramQueryConditions, paramPageInfo);
  }
  
  public void insertBrokerRewardPropsBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    this.brokerRewardPropsDao.insertBrokerRewardPropsBreed(paramBrokerRewardProps);
  }
  
  public int BrokerRewardPropsAddByBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    int i = 0;
    this.brokerRewardPropsDao.insertBrokerRewardPropsByBreed(paramBrokerRewardProps);
    return i;
  }
  
  public List getBrokerRewardPropsBreedByID(int paramInt)
  {
    return this.brokerRewardPropsDao.getBrokerRewardPropsBreedByID(paramInt);
  }
  
  public void updateBrokerRewardPropsByBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    this.brokerRewardPropsDao.updateBrokerRewardPropsByBreed(paramBrokerRewardProps);
  }
  
  public void updateBrokerRewardPropsBreed(BrokerRewardProps paramBrokerRewardProps)
  {
    this.brokerRewardPropsDao.updateBrokerRewardPropsBreed(paramBrokerRewardProps);
  }
  
  public List getBrokerIDList()
  {
    return this.brokerRewardPropsDao.getBrokerIDList();
  }
  
  public int BrokerRewardPropsDel(String paramString1, String paramString2)
  {
    int i = 1;
    this.brokerRewardPropsDao.deleteBrokerRewardProps(paramString1, paramString2);
    return i;
  }
  
  public void deleteBrokerRewardPropsBreed(int paramInt)
  {
    this.brokerRewardPropsDao.deleteBrokerRewardPropsBreed(paramInt);
  }
}
