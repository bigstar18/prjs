package gnnt.MEBS.member.firm.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.firm.dao.FirmDao;
import gnnt.MEBS.member.firm.dao.SystemDao;
import gnnt.MEBS.member.firm.unit.Firm;
import gnnt.MEBS.member.firm.unit.FirmCategory;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.FirmModule;
import gnnt.MEBS.member.firm.unit.TradeModule;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_firmService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmService
{
  private final transient Log logger = LogFactory.getLog(FirmService.class);
  @Autowired
  @Qualifier("m_firmDao")
  private FirmDao firmDao;
  @Autowired
  @Qualifier("m_systemDao")
  private SystemDao systemDao;
  
  public void setFirmDao(FirmDao firmDao)
  {
    this.firmDao = firmDao;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Firm> getFirms(QueryConditions conditions)
  {
    return this.firmDao.getFirms(conditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getFirmList(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.firmDao.getFirmList(conditions, pageInfo);
  }
  
  public List getFirmLists(QueryConditions conditions, PageInfo pageInfo, String brokerId)
  {
    return this.firmDao.getFirmLists(conditions, pageInfo, brokerId);
  }
  
  public int createFirm(Firm firm, FirmLog firmLog)
  {
    int result = 1;
    int count = this.firmDao.verifyRepeat(firm.getFirmId());
    if (count != 0)
    {
      result = -2;
    }
    else
    {
      this.firmDao.createFirm(firm);
      addFirmModule(firm);
      this.firmDao.addFirm(firm);
    }
    return result;
  }
  
  public int createFirm(Firm firm, FirmLog firmLog, Map map)
  {
    int result = 1;
    int count = this.firmDao.verifyRepeat(firm.getFirmId());
    if (count != 0)
    {
      result = -2;
    }
    else
    {
      this.firmDao.createFirm(firm);
      addFirmModule(firm);
      this.firmDao.addFirm(firm);
      if ((firm.getBrokerId() != null) && (!"".equals(firm.getBrokerId().trim()))) {
        this.firmDao.createBrokerAndFirm(firm);
      }
    }
    return result;
  }
  
  public void addFirmModule(Firm firm)
  {
    List list = this.systemDao.getTradeModuleList1(null, null);
    if ((list != null) && (list.size() > 0)) {
      for (int i = 0; i < list.size(); i++)
      {
        String moduleId = (String)((Map)list.get(i)).get("MODULEID");
        FirmModule firmModule = new FirmModule();
        firmModule.setFirmId(firm.getFirmId());
        firmModule.setEnabled("N");
        firmModule.setModuleId(moduleId);
        this.firmDao.addFirmModule(firmModule);
      }
    }
    List<FirmModule> moduleList = firm.getFirmModule();
    if ((moduleList != null) && (moduleList.size() > 0)) {
      for (int i = 0; i < moduleList.size(); i++)
      {
        FirmModule firmModule = (FirmModule)moduleList.get(i);
        this.firmDao.updateFirmModule(firmModule);
      }
    }
  }
  
  public void addFirmLog(FirmLog firmLog)
  {
    this.firmDao.addFirmLog(firmLog);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Firm getFirmById(String firmId)
  {
    Firm firm = null;
    QueryConditions qc = new QueryConditions("firmId", "=", firmId);
    List list = this.firmDao.getFirms(qc);
    if (list.size() > 0)
    {
      firm = (Firm)list.get(0);
      List<FirmModule> moduleList = this.firmDao.getFirmModuleByFirmId(firmId);
      firm.addFirmModule(moduleList);
    }
    return firm;
  }
  
  public int updateFirm(Firm firm)
  {
    int result = 1;
    
    String per = "";
    List<FirmModule> moduleList = firm.getFirmModule();
    if ((moduleList != null) && (moduleList.size() > 0)) {
      for (FirmModule module : moduleList) {
        per = per + module.getModuleId() + ",";
      }
    }
    if (per.length() > 0) {
      per = per.substring(0, per.length() - 1);
    }
    result = this.firmDao.updateFirm(firm);
    if (result == 1)
    {
      updateFirmModule(firm);
      this.firmDao.updateTraderModule(per, firm.getFirmId());
    }
    return result;
  }
  
  public void updateFirmModule(Firm firm)
  {
    this.firmDao.initFirmModule(firm.getFirmId());
    List<FirmModule> moduleList = firm.getFirmModule();
    if ((moduleList != null) && (moduleList.size() > 0)) {
      for (FirmModule module : moduleList) {
        this.firmDao.updateFirmModule(module);
      }
    }
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public Firm getFirmForUpdate(String id)
  {
    return this.firmDao.getFirmLock(id);
  }
  
  public int setStatusFirm(Firm firm, FirmLog firmLog)
  {
    String remark = "";
    int result = 1;
    if (!"E".equals(firm.getStatus()))
    {
      this.firmDao.setStatusFirm(firm);
      if ("D".equals(firm.getStatus())) {
        this.firmDao.setStatusTrader(firm);
      }
    }
    else
    {
      int r = this.firmDao.callStored(firm.getFirmId());
      if (r == 1)
      {
        this.firmDao.setStatusFirm(firm);
        this.firmDao.setStatusTrader(firm);
      }
      else
      {
        result = -2;
      }
    }
    return result;
  }
  
  public int deleteFirm(Firm firm, FirmLog firmLog)
  {
    int result = 1;
    String status = firm.getStatus();
    if ("E".equals(status)) {
      this.firmDao.deleteFirm(firm);
    } else {
      result = -2;
    }
    return result;
  }
  
  public boolean checkTariffStatus(Firm user)
  {
    return this.firmDao.checkTariffStatus(user);
  }
  
  public int verifyFirm(String firmId, String status, FirmLog log)
  {
    int result = 1;
    Firm firm = getFirmForUpdate(firmId);
    FirmModule firmModule = new FirmModule();
    firmModule.setFirmId(firmId);
    if ("W".equals(firm.getStatus()))
    {
      if ("N".equals(status))
      {
        QueryConditions conditions = new QueryConditions();
        conditions.addCondition("Enabled", "=", "Y");
        List list = this.firmDao.getTradeModules(conditions);
        if ((list != null) && (list.size() > 0)) {
          for (int i = 0; i < list.size(); i++)
          {
            String moduleId = ((TradeModule)list.get(i)).getModuleId();
            
            firmModule.setModuleId(moduleId);
            firmModule.setEnabled(status);
            this.firmDao.addFirmModule(firmModule);
          }
        }
        this.firmDao.addFirm(firm);
      }
      this.firmDao.updateFirmStatus(firmId, status);
      this.firmDao.addFirmLog(log);
    }
    else
    {
      result = -2;
    }
    return result;
  }
  
  public int rejectFirm(String firmId, String note, FirmLog log)
  {
    int result = 1;
    Firm firm = getFirmForUpdate(firmId);
    if ("W".equals(firm.getStatus()))
    {
      firm.setNote(note);
      this.firmDao.updateFirm(firm);
      this.firmDao.updateFirmStatus(firmId, "R");
      this.firmDao.addFirmLog(log);
    }
    else
    {
      result = -2;
    }
    return result;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getFirms(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.firmDao.getFirms(conditions, pageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public int setStatusFirm(String firmId, String firmStatus, String logonUser)
  {
    return this.firmDao.setStatusFirm(firmId, firmStatus, logonUser);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public int updatefirmbankMaxPertransMoney(String firmId, String firmStatus, String logonUser)
  {
    int result = this.firmDao.updatefirmbankMaxPertransMoney(firmId, firmStatus, logonUser);
    return result;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getFirmCategoryList(String firmId, String name, PageInfo pageInfo)
  {
    return this.firmDao.getFirmCategoryList(firmId, name, pageInfo);
  }
  
  public int createFirmCategory(FirmCategory firmCategory)
  {
    int result = 1;
    int count = this.firmDao.verifyRepeatCategory(firmCategory.getId());
    if (count != 0) {
      result = -2;
    } else {
      this.firmDao.createFirmCategory(firmCategory);
    }
    return result;
  }
  
  public List getFirmCategoryById(String firmId)
  {
    return this.firmDao.getFirmCategoryById(firmId);
  }
  
  public int updateFirmCategory(FirmCategory firmCategory, String oldId)
  {
    int result = 1;
    if (firmCategory.getId().toString().equals(oldId))
    {
      this.firmDao.updateFirmCategory(firmCategory, oldId);
    }
    else
    {
      int count = this.firmDao.verifyRepeatCategory(firmCategory.getId());
      if (count != 0) {
        result = -2;
      } else {
        this.firmDao.updateFirmCategory(firmCategory, oldId);
      }
    }
    return result;
  }
  
  public int deleteFirmCategory(String firmId)
  {
    int result = 1;
    if ((firmId != null) && (!"".equals(firmId))) {
      this.firmDao.deleteFirmCategory(firmId);
    } else {
      result = -2;
    }
    return result;
  }
  
  public List getBankList()
  {
    return this.firmDao.getBankList();
  }
  
  public List getBrokerByFirmId(String firmId)
  {
    return this.firmDao.getBrokerByFirmId(firmId);
  }
}
