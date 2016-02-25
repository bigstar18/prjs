package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.AheadSettleDAO;
import gnnt.MEBS.timebargain.manage.dao.CommodityDAO;
import gnnt.MEBS.timebargain.manage.dao.CustomerDAO;
import gnnt.MEBS.timebargain.manage.model.AheadSettle;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.service.AheadSettleManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AheadSettleManagerImpl
  extends BaseManager
  implements AheadSettleManager
{
  private static final Log logger = LogFactory.getLog(AheadSettleManagerImpl.class);
  private CommodityDAO commodityDAO;
  private CustomerDAO customerDAO;
  private AheadSettleDAO aheadSettleDAO;
  
  public void setAheadSettleDAO(AheadSettleDAO paramAheadSettleDAO)
  {
    this.aheadSettleDAO = paramAheadSettleDAO;
  }
  
  public void setCommodityDAO(CommodityDAO paramCommodityDAO)
  {
    this.commodityDAO = paramCommodityDAO;
  }
  
  public void setCustomerDAO(CustomerDAO paramCustomerDAO)
  {
    this.customerDAO = paramCustomerDAO;
  }
  
  public synchronized int auditAheadSettle(AheadSettle paramAheadSettle)
  {
    int i = 1;
    AheadSettle localAheadSettle = this.aheadSettleDAO.getAheadSettle(paramAheadSettle.getApplyID() + "");
    if ((localAheadSettle == null) || (localAheadSettle.getStatus() != 1)) {
      return 2;
    }
    if ((localAheadSettle == null) || ("".equals(Integer.valueOf(localAheadSettle.getStatus())))) {
      return 3;
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" UPDATE T_E_APPLYAHEADSETTLE SET STATUS = ");
      switch (paramAheadSettle.getStatus())
      {
      case 2: 
        i = this.aheadSettleDAO.auditAheadSettle(paramAheadSettle);
        if (i == 1)
        {
          localStringBuilder.append(2);
          localStringBuilder.append(" ,MODIFIER='" + paramAheadSettle.getModifier() + "', " + " REMARK2='" + paramAheadSettle.getRemark2() + "', MODIFYTIME=SYSDATE " + " WHERE APPLYID=" + paramAheadSettle.getApplyID() + " ");
          this.aheadSettleDAO.updateDataByYourSQL(localStringBuilder.toString());
          updateFrozenQtyWhenApplyAndAudit(paramAheadSettle, -1);
          localStringBuilder = null;
        }
        break;
      case 3: 
        localStringBuilder.append(3);
        localStringBuilder.append(" ,MODIFIER='" + paramAheadSettle.getModifier() + "', " + " REMARK2='" + paramAheadSettle.getRemark2() + "', MODIFYTIME=SYSDATE " + " WHERE APPLYID=" + paramAheadSettle.getApplyID() + " ");
        this.aheadSettleDAO.updateDataByYourSQL(localStringBuilder.toString());
        updateFrozenQtyWhenApplyAndAudit(paramAheadSettle, -1);
        localStringBuilder = null;
      }
    }
    catch (Exception localException)
    {
      logger.debug(" service 审核提前交收申请异常，信息：" + localException.getMessage());
      localException.printStackTrace();
      i = -99;
    }
    return i;
  }
  
  public AheadSettle getAheadSettle(String paramString)
  {
    return this.aheadSettleDAO.getAheadSettle(paramString);
  }
  
  public int saveAheadSettle(AheadSettle paramAheadSettle)
  {
    int i = 0;
    Customer localCustomer1 = this.customerDAO.getKHCustomerById(paramAheadSettle.getCustomerID_B());
    if (localCustomer1 == null) {
      return -1;
    }
    Customer localCustomer2 = this.customerDAO.getKHCustomerById(paramAheadSettle.getCustomerID_B());
    if (localCustomer2 == null) {
      return -2;
    }
    Commodity localCommodity = this.commodityDAO.getCommodityById(paramAheadSettle.getCommodityID());
    if (localCommodity == null) {
      return -3;
    }
    try
    {
      this.aheadSettleDAO.saveAheadSettle(paramAheadSettle);
      updateFrozenQtyWhenApplyAndAudit(paramAheadSettle, 1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      logger.debug("error：保存新增提前交收申请出现异常；信息" + localException.getMessage());
      i = -100;
    }
    return i;
  }
  
  public List listAheadSettle(QueryConditions paramQueryConditions)
  {
    return this.aheadSettleDAO.listAheadSettle(paramQueryConditions);
  }
  
  private void updateFrozenQtyWhenApplyAndAudit(AheadSettle paramAheadSettle, int paramInt)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" UPDATE T_CUSTOMERHOLDSUM SET FROZENQTY=FROZENQTY+" + paramInt * paramAheadSettle.getQuantity() + " WHERE CUSTOMERID='" + paramAheadSettle.getCustomerID_B() + "' AND COMMODITYID='" + paramAheadSettle.getCommodityID() + "' AND BS_FLAG=1 ");
    this.aheadSettleDAO.updateDataByYourSQL(localStringBuilder.toString());
    localStringBuilder = null;
    localStringBuilder = new StringBuilder();
    double d = paramInt * paramAheadSettle.getQuantity() - paramInt * paramAheadSettle.getGageQty();
    localStringBuilder.append(" UPDATE T_CUSTOMERHOLDSUM SET FROZENQTY=FROZENQTY+" + d + ",GAGEFROZENQTY=GAGEFROZENQTY+" + paramInt * paramAheadSettle.getGageQty() + " WHERE CUSTOMERID='" + paramAheadSettle.getCustomerID_S() + "' AND COMMODITYID='" + paramAheadSettle.getCommodityID() + "' AND BS_FLAG=2 ");
    this.aheadSettleDAO.updateDataByYourSQL(localStringBuilder.toString());
    localStringBuilder = null;
  }
  
  public long getHoldQty(String paramString1, String paramString2, int paramInt)
  {
    return this.aheadSettleDAO.getHoldQty(paramString1, paramString2, paramInt);
  }
  
  public long getGageQty(String paramString1, String paramString2, int paramInt)
  {
    return this.aheadSettleDAO.getGageQty(paramString1, paramString2, paramInt);
  }
}
