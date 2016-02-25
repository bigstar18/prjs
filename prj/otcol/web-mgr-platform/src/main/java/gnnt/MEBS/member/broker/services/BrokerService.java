package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.finance.base.util.SysData;
import gnnt.MEBS.member.broker.dao.BrokerDao;
import gnnt.MEBS.member.broker.dao.BrokerGroupDao;
import gnnt.MEBS.member.broker.dao.BrokerLogDao;
import gnnt.MEBS.member.broker.dao.BrokerRewardDao;
import gnnt.MEBS.member.broker.model.Broker;
import gnnt.MEBS.member.broker.model.BrokerGroup;
import gnnt.MEBS.member.broker.model.BrokerLog;
import gnnt.MEBS.member.broker.model.Firm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_brokerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerService
{
  @Autowired
  @Qualifier("m_brokerDao")
  private BrokerDao brokerDao;
  @Autowired
  @Qualifier("m_brokerRewardDao")
  private BrokerRewardDao brokerRewardDao;
  @Autowired
  @Qualifier("m_brokerLogDao")
  private BrokerLogDao brokerLogDao;
  @Autowired
  @Qualifier("m_brokerGroupDao")
  private BrokerGroupDao brokerGroupDao;
  
  public List<Broker> getBrokerList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.brokerDao.getBrokerList(paramQueryConditions, paramPageInfo);
  }
  
  public List<Broker> getBrokerLists(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.brokerDao.getBrokerLists(paramQueryConditions, paramPageInfo);
  }
  
  public List<Broker> getfirmList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.brokerDao.getfirmList(paramQueryConditions, paramPageInfo);
  }
  
  public boolean checkFirmandbroke(String paramString)
  {
    return this.brokerDao.checkFirmandbroke(paramString);
  }
  
  public boolean checkFirmById(String paramString)
  {
    return this.brokerDao.checkFirmById(paramString);
  }
  
  public int brokerAdd(Broker paramBroker)
  {
    int i = 1;
    if (checkFirmandbroke(paramBroker.getFirmId())) {
      i = -2;
    } else if (checkFirmById(paramBroker.getFirmId())) {
      this.brokerDao.insertBroker(paramBroker);
    } else {
      i = -1;
    }
    return i;
  }
  
  public int brokerAdd(Broker paramBroker, String paramString)
  {
    int i = 1;
    if (checkFirmandbroke(paramBroker.getFirmId()))
    {
      i = -2;
    }
    else if (checkFirmById(paramBroker.getFirmId()))
    {
      this.brokerDao.insertBroker(paramBroker);
      BrokerLog localBrokerLog = new BrokerLog();
      localBrokerLog.setBrokerId(paramBroker.getBrokerid());
      localBrokerLog.setOperatorId(paramString);
      localBrokerLog.setType(1);
      localBrokerLog.setAction("添加" + SysData.getConfig("brokerName"));
      this.brokerLogDao.insertBroker(localBrokerLog);
      BrokerGroup localBrokerGroup = new BrokerGroup();
      localBrokerGroup.setBrokerId(paramBroker.getBrokerid());
      localBrokerGroup.setGroupName(paramBroker.getBrokerid());
      this.brokerGroupDao.insertBrokerGroup(localBrokerGroup);
      this.brokerDao.insertBrokerRewardpropsbreed(paramBroker);
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public int brokerDelete(String[] paramArrayOfString)
  {
    int i = 1;
    for (String str : paramArrayOfString)
    {
      QueryConditions localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("brokerId", "=", str);
      List localList1 = this.brokerRewardDao.getBrokerRewardList(localQueryConditions, null);
      if (localList1.size() == 0)
      {
        List localList2 = this.brokerDao.getFirmById(null, null, str);
        if ((localList2 == null) || (localList2.size() == 0))
        {
          this.brokerDao.deleteFirmRelatedByBrokerId(this.brokerDao.getBrokerById(str).getBrokerid());
          this.brokerDao.deleteBroker(str);
        }
        else
        {
          i = -2;
          break;
        }
      }
      else
      {
        i = -1;
        break;
      }
    }
    return i;
  }
  
  public int brokerDelete(String[] paramArrayOfString, String paramString)
  {
    int i = 1;
    for (String str : paramArrayOfString)
    {
      QueryConditions localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("brokerId", "=", str);
      List localList1 = this.brokerRewardDao.getBrokerRewardList(localQueryConditions, null);
      if (localList1.size() == 0)
      {
        List localList2 = this.brokerDao.getFirmById(null, null, str);
        if ((localList2 == null) || (localList2.size() == 0))
        {
          this.brokerDao.deleteFirmRelatedByBrokerId(this.brokerDao.getBrokerById(str).getBrokerid());
          this.brokerDao.deleteBrokerOperatorMenu(str);
          this.brokerDao.deleteBrokerOperatorGroup(str);
          this.brokerDao.deleteBrokerGroup(str);
          this.brokerDao.deleteBrokerOperator(str);
          this.brokerDao.deleteBrokerRight(str);
          this.brokerDao.deleteBroker(str);
          this.brokerDao.deleteBrokerRewardProps(str);
          BrokerLog localBrokerLog = new BrokerLog();
          localBrokerLog.setBrokerId(str);
          localBrokerLog.setOperatorId(paramString);
          localBrokerLog.setType(1);
          localBrokerLog.setAction("删除" + SysData.getConfig("brokerName"));
          this.brokerLogDao.insertBroker(localBrokerLog);
        }
        else
        {
          i = -2;
          break;
        }
      }
      else
      {
        i = -1;
        break;
      }
    }
    return i;
  }
  
  public int brokerMod(Broker paramBroker)
  {
    int i = 1;
    if (checkFirmById(paramBroker.getFirmId())) {
      this.brokerDao.updateBroker(paramBroker);
    } else {
      i = -1;
    }
    return i;
  }
  
  public int brokerMod(Broker paramBroker, String paramString)
  {
    int i = 1;
    if (checkFirmById(paramBroker.getFirmId()))
    {
      this.brokerDao.updateBroker(paramBroker);
      BrokerLog localBrokerLog = new BrokerLog();
      localBrokerLog.setBrokerId(paramBroker.getBrokerid());
      localBrokerLog.setOperatorId(paramString);
      localBrokerLog.setType(1);
      localBrokerLog.setAction("修改" + SysData.getConfig("brokerName") + "信息");
      this.brokerLogDao.insertBroker(localBrokerLog);
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public int setBrokerPwd(String paramString1, String paramString2)
  {
    int i = 1;
    this.brokerDao.setBrokerPwd(paramString1, paramString2);
    return i;
  }
  
  public int setBrokerPwd(String paramString1, String paramString2, String paramString3)
  {
    int i = 1;
    this.brokerDao.setBrokerPwd(paramString1, paramString2);
    BrokerLog localBrokerLog = new BrokerLog();
    localBrokerLog.setBrokerId(paramString1);
    localBrokerLog.setOperatorId(paramString3);
    localBrokerLog.setType(1);
    localBrokerLog.setAction("修改" + SysData.getConfig("brokerName") + "密码");
    this.brokerLogDao.insertBroker(localBrokerLog);
    return i;
  }
  
  public Broker getBrokerById(String paramString)
  {
    Broker localBroker = this.brokerDao.getBrokerById(paramString);
    return localBroker;
  }
  
  public List getFirmById(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.brokerDao.getFirmById(paramQueryConditions, paramPageInfo, paramString);
  }
  
  public Firm getFirmById(String paramString)
  {
    Firm localFirm = null;
    QueryConditions localQueryConditions = new QueryConditions("firmId", "=", paramString);
    List localList = this.brokerDao.getFirms(localQueryConditions);
    if ((localList.size() > 0) && (localList != null)) {
      localFirm = (Firm)localList.get(0);
    }
    return localFirm;
  }
  
  public int addFirmRelated(String paramString1, String paramString2)
  {
    int i = 1;
    if (checkFirmById(paramString2))
    {
      if (checkFirmandbroke(paramString2)) {
        i = -2;
      } else {
        this.brokerDao.addFirmRelated(paramString1, paramString2);
      }
    }
    else {
      i = -1;
    }
    return i;
  }
  
  public int addFirmRelated(String paramString1, String paramString2, String paramString3)
  {
    int i = 1;
    if (checkFirmById(paramString2))
    {
      if (checkFirmandbroke(paramString2))
      {
        i = -2;
      }
      else
      {
        this.brokerDao.addFirmRelated(paramString1, paramString2);
        this.brokerDao.addFirmRelatedGroup(paramString1, paramString2);
        BrokerLog localBrokerLog = new BrokerLog();
        localBrokerLog.setBrokerId(paramString1);
        localBrokerLog.setOperatorId(paramString3);
        localBrokerLog.setType(1);
        localBrokerLog.setAction(SysData.getConfig("brokerName") + "增加所属交易商" + paramString2);
        this.brokerLogDao.insertBroker(localBrokerLog);
      }
    }
    else {
      i = -1;
    }
    return i;
  }
  
  public int deleteFirmRelated(String[] paramArrayOfString)
  {
    int i = 1;
    for (String str : paramArrayOfString) {
      this.brokerDao.deleteFirmRelated(str);
    }
    return i;
  }
  
  public int deleteFirmRelated(String[] paramArrayOfString, String paramString1, String paramString2)
  {
    int i = 1;
    for (String str : paramArrayOfString)
    {
      this.brokerDao.deleteFirmRelatedGroup(paramString1, str);
      this.brokerDao.deleteFirmRelated(str);
      BrokerLog localBrokerLog = new BrokerLog();
      localBrokerLog.setBrokerId(paramString1);
      localBrokerLog.setOperatorId(paramString2);
      localBrokerLog.setType(1);
      localBrokerLog.setAction(SysData.getConfig("brokerName") + "删除所属交易商" + str);
      this.brokerLogDao.insertBroker(localBrokerLog);
    }
    return i;
  }
  
  public void addLoginLog(String paramString1, String paramString2, String paramString3)
  {
    this.brokerDao.addLoginLog(paramString1, paramString2, paramString3);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Dealer getDealerByfirmId(String paramString)
  {
    return this.brokerDao.getDealerByfirmId(paramString);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public int getDealerByBrokerId(String paramString)
  {
    return this.brokerDao.getDealerByBrokerId(paramString);
  }
}
