package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.TradePrivilegeDao;
import gnnt.MEBS.timebargain.mgr.model.firmSet.FirmInfo;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TradePrivilege;
import gnnt.MEBS.timebargain.mgr.service.TradePrivilegeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("tradePrivilegeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradePrivilegeServiceImpl extends StandardService
  implements TradePrivilegeService
{

  @Autowired
  @Qualifier("tradePrivilegeDao")
  private TradePrivilegeDao tradePrivilegeDao;

  public List getFirmPrivilege(String firmID)
  {
    return this.tradePrivilegeDao.getFirmPrivilege(firmID);
  }

  public List getCustomerPrivilege(String customerID)
  {
    return this.tradePrivilegeDao.getCustomerPrivilege(customerID);
  }

  public List getTraderPrivilege(String traderID)
  {
    return this.tradePrivilegeDao.getTraderPrivilege(traderID);
  }

  public List getBreed()
  {
    return this.tradePrivilegeDao.getBreed();
  }

  public List getCommodity()
  {
    return this.tradePrivilegeDao.getCommodity();
  }

  public String getOperateCode(String traderID)
  {
    StringBuffer sb = new StringBuffer();

    String str = this.tradePrivilegeDao.getOperateCode(traderID);

    if ((str != null) && (!"".equals(str))) {
      String[] ops = str.split(",");
      for (int i = 0; i < ops.length; i++)
      {
        sb.append("<option value=\"" + 
          ops[i] + 
          "\">" + 
          ops[i] + 
          "</option>");
      }
    }
    return sb.toString();
  }

  public List getCodeNotChoose(String firmID, String traderID)
  {
    return this.tradePrivilegeDao.getCodeNotChoose(firmID, traderID);
  }

  public TradePrivilegeDao getTradePrivilegeDao() {
    return this.tradePrivilegeDao;
  }

  public void batchSetInsertFirmPrivilege(int type, String typeIdString, int kind, String kindIdString, int pb, int ps)
  {
    this.tradePrivilegeDao.batchEmptyDeleteFirmPrivilege(type, typeIdString, kind, kindIdString);

    this.tradePrivilegeDao.batchSetInsertFirmPrivilege(type, typeIdString, kind, kindIdString, pb, ps);
  }

  public void batchEmptyDeleteFirmPrivilege(int type, String typeIdString, int kind, String kindIdString)
  {
    this.tradePrivilegeDao.batchEmptyDeleteFirmPrivilege(type, typeIdString, kind, kindIdString);
  }

  public void addTradePrivilege(TradePrivilege tradePrivilege)
    throws Exception
  {
    this.tradePrivilegeDao.deleteTradePrivilege(tradePrivilege);

    FirmInfo firm = new FirmInfo();
    firm.setFirmID(tradePrivilege.getTypeID());
    FirmInfo firmInfo = (FirmInfo)get(firm);
    if (firmInfo != null)
    {
      add(tradePrivilege);
    }
  }

  public void deleteTradePrivilege(TradePrivilege tradePrivilege)
    throws Exception
  {
    this.tradePrivilegeDao.deleteTradePrivilege(tradePrivilege);
  }
}