package gnnt.MEBS.member.firm.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.firm.dao.SystemDao;
import gnnt.MEBS.member.firm.dao.TraderDao;
import gnnt.MEBS.member.firm.unit.Firm;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.Trader;
import gnnt.MEBS.member.firm.unit.TraderModule;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_traderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TraderService
{
  private final transient Log logger = LogFactory.getLog(TraderService.class);
  @Autowired
  @Qualifier("m_traderDao")
  private TraderDao traderDao;
  @Autowired
  @Qualifier("m_systemDao")
  private SystemDao systemDao;
  @Autowired
  @Qualifier("m_firmService")
  private FirmService firmService;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getTraders(QueryConditions paramQueryConditions)
  {
    return this.traderDao.getTraders(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getTraderList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.traderDao.getTraderList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Trader getTraderById(String paramString)
  {
    Trader localTrader = null;
    QueryConditions localQueryConditions = new QueryConditions("traderId", "=", paramString);
    List localList1 = this.traderDao.getTraders(localQueryConditions);
    if (localList1.size() > 0)
    {
      localTrader = (Trader)localList1.get(0);
      List localList2 = this.traderDao.getTraderModuleByTraderId(paramString);
      localTrader.addTraderModule(localList2);
    }
    return localTrader;
  }
  
  public int createTrader(Trader paramTrader)
  {
    int i = 1;
    List localList = paramTrader.getTraderModule();
    String str = "";
    for (int j = 0; j < localList.size(); j++) {
      str = str + ((TraderModule)localList.get(j)).getModuleId() + ",";
    }
    if ((str != null) && (str.length() > 0)) {
      str = str.substring(0, str.length() - 1);
    }
    j = 1;
    boolean bool;
    if ((localList != null) && (localList.size() > 0)) {
      bool = this.traderDao.querySign(paramTrader, str);
    }
    if (bool)
    {
      Firm localFirm = this.firmService.getFirmById(paramTrader.getFirmId());
      paramTrader.setStatus(localFirm.getStatus());
      this.traderDao.createTrader(paramTrader);
      addTraderModule(paramTrader);
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public void addTraderModule(Trader paramTrader)
  {
    List localList1 = this.systemDao.getTradeModuleList(null, null);
    TraderModule localTraderModule;
    if ((localList1 != null) && (localList1.size() > 0)) {
      for (int i = 0; i < localList1.size(); i++)
      {
        String str = (String)((Map)localList1.get(i)).get("MODULEID");
        localTraderModule = new TraderModule();
        localTraderModule.setTraderId(paramTrader.getTraderId());
        localTraderModule.setEnabled("N");
        localTraderModule.setModuleId(str);
        this.traderDao.addTraderModule(localTraderModule);
      }
    }
    List localList2 = paramTrader.getTraderModule();
    if ((localList2 != null) && (localList2.size() > 0)) {
      for (int j = 0; j < localList2.size(); j++)
      {
        localTraderModule = (TraderModule)localList2.get(j);
        this.traderDao.updateTraderModule(localTraderModule);
      }
    }
  }
  
  public int updateTrader(Trader paramTrader)
  {
    int i = 1;
    List localList = paramTrader.getTraderModule();
    String str = "";
    for (int j = 0; j < localList.size(); j++) {
      str = str + ((TraderModule)localList.get(j)).getModuleId() + ",";
    }
    if (str.length() > 0) {
      str = str.substring(0, str.length() - 1);
    }
    j = 1;
    boolean bool;
    if ((localList != null) && (localList.size() > 0)) {
      bool = this.traderDao.querySign(paramTrader, str);
    }
    if (bool)
    {
      this.traderDao.updateTrader(paramTrader);
      updateTraderModule(paramTrader);
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public void updateTraderModule(Trader paramTrader)
  {
    this.traderDao.initTraderModule(paramTrader.getTraderId());
    List localList = paramTrader.getTraderModule();
    if ((localList != null) && (localList.size() > 0))
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        TraderModule localTraderModule = (TraderModule)localIterator.next();
        this.traderDao.updateTraderModule(localTraderModule);
      }
    }
  }
  
  public int setStatusTrader(Trader paramTrader, FirmLog paramFirmLog)
  {
    int i = 1;
    Firm localFirm = this.firmService.getFirmById(paramTrader.getFirmId());
    if ((localFirm != null) && ("N".equals(localFirm.getStatus()))) {
      this.traderDao.setStatusTrader(paramTrader);
    } else {
      i = -1;
    }
    return i;
  }
  
  public int deleteTrader(String paramString, FirmLog paramFirmLog)
  {
    int i = 1;
    Trader localTrader = getTraderById(paramString);
    if (!paramString.equals(localTrader.getFirmId())) {
      this.traderDao.deleteTrader(paramString);
    } else {
      i = -1;
    }
    return i;
  }
  
  public void changePwdTrader(String paramString1, String paramString2)
  {
    this.traderDao.changePwdTrader(paramString1, paramString2);
  }
}
