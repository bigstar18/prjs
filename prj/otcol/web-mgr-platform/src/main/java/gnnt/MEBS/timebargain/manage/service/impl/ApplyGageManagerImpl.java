package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.ApplyGageDAO;
import gnnt.MEBS.timebargain.manage.dao.CommodityDAO;
import gnnt.MEBS.timebargain.manage.dao.CustomerDAO;
import gnnt.MEBS.timebargain.manage.model.ApplyGage;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.service.ApplyGageManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplyGageManagerImpl
  extends BaseManager
  implements ApplyGageManager
{
  private final Log logger = LogFactory.getLog(ApplyGageManagerImpl.class);
  private ApplyGageDAO applyGageDAO;
  private CustomerDAO customerDAO;
  private CommodityDAO commodityDAO;
  
  public void setApplyGageDAO(ApplyGageDAO paramApplyGageDAO)
  {
    this.applyGageDAO = paramApplyGageDAO;
  }
  
  public void setCommodityDAO(CommodityDAO paramCommodityDAO)
  {
    this.commodityDAO = paramCommodityDAO;
  }
  
  public void setCustomerDAO(CustomerDAO paramCustomerDAO)
  {
    this.customerDAO = paramCustomerDAO;
  }
  
  public List listApplyGage(QueryConditions paramQueryConditions)
  {
    return this.applyGageDAO.listApplyGage(paramQueryConditions);
  }
  
  public ApplyGage getApplyGage(long paramLong)
  {
    return this.applyGageDAO.getApplyGage(paramLong);
  }
  
  public int saveApplyGage(ApplyGage paramApplyGage)
  {
    int i = 0;
    Customer localCustomer = this.customerDAO.getKHCustomerById(paramApplyGage.getCustomerID());
    this.logger.debug("customer:" + localCustomer);
    if (localCustomer == null)
    {
      this.logger.debug("CustomerID:" + localCustomer.getCustomerID());
      return 1;
    }
    Commodity localCommodity = this.commodityDAO.getCommodityById(paramApplyGage.getCommodityID());
    if (localCommodity == null) {
      return 2;
    }
    if (paramApplyGage.getApplyType() > 1)
    {
      List localList = this.applyGageDAO.getResultListBySQL(paramApplyGage);
      if (localList.size() > 0)
      {
        Map localMap = (Map)localList.get(0);
        long l1 = ((BigDecimal)localMap.get("GAGEQTY")).longValue();
        long l2 = ((BigDecimal)localMap.get("GAGEFROZENQTY")).longValue();
        if (l1 - l2 < paramApplyGage.getQuantity()) {
          return 4;
        }
      }
      else
      {
        return 4;
      }
    }
    try
    {
      paramApplyGage.setFirmID(localCustomer.getFirmID());
      paramApplyGage.setApplyId(this.applyGageDAO.getApplyGageId());
      this.logger.debug("applyGage:" + paramApplyGage.toString());
      this.applyGageDAO.saveApplyGage(paramApplyGage);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = 3;
    }
    this.logger.debug("----enter saveApplyGage()-----处理结果:" + i);
    return i;
  }
  
  public synchronized int gageProcessorAudit(ApplyGage paramApplyGage, String paramString)
  {
    this.logger.debug("----gageProcessorAudit()----auditType:" + paramString);
    int i = 1;
    ApplyGage localApplyGage = getApplyGage(paramApplyGage.getApplyId());
    this.logger.debug("----applyGageInDB状态----stutas(1_ok):" + localApplyGage.getStatus());
    if (localApplyGage == null) {
      return 3;
    }
    if (localApplyGage.getStatus() != 1) {
      return 2;
    }
    if (i == 1) {
      if ("2".equals(paramString)) {
        i = gageProcessorAuditPass(paramApplyGage);
      } else if ("3".equals(paramString)) {
        i = updateApplyGage(paramApplyGage, 3);
      } else {
        i = -5;
      }
    }
    return i;
  }
  
  public int gageProcessorAuditPass(ApplyGage paramApplyGage)
  {
    this.logger.debug("----ApplyGage状态----stutas(2_ok):" + paramApplyGage.getStatus());
    int i = 1;
    if (paramApplyGage.getApplyType() == 1) {
      i = this.applyGageDAO.auditApplyGage(paramApplyGage);
    } else if ((paramApplyGage.getApplyType() == 2) || (paramApplyGage.getApplyType() == 3)) {
      i = this.applyGageDAO.auditCancleGage(paramApplyGage);
    } else {
      return -5;
    }
    if (i == 1) {
      try
      {
        i = updateApplyGage(paramApplyGage, 2);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        i = -6;
      }
    }
    return i;
  }
  
  public int updateApplyGage(ApplyGage paramApplyGage, int paramInt)
  {
    this.logger.debug("----want2Status状态----stutas(3_ok;2pass;3refuse):" + paramInt);
    int i = 1;
    try
    {
      paramApplyGage.setStatus(paramInt);
      this.applyGageDAO.updateApplyGage(paramApplyGage);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -4;
    }
    return i;
  }
}
