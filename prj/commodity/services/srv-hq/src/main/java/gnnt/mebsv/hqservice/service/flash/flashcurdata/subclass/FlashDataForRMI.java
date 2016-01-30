package gnnt.mebsv.hqservice.service.flash.flashcurdata.subclass;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.quotation.PreData;
import gnnt.MEBS.timebargain.server.rmi.quotation.IQuotationRMI;
import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.BillDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.service.flash.flashcurdata.BFlashDataServer;
import gnnt.mebsv.hqservice.service.rmi.RMIManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class FlashDataForRMI extends BFlashDataServer
{
  protected String MarketID = "00";
  RMIManager rmiManager = null;
  IQuotationRMI quotationRMI = this.rmiManager.getQuotationRMI();
  private Map<String, PreData> amountMap = new HashMap();
  long currentNum;

  public FlashDataForRMI(QuotationServer paramQuotationServer)
  {
    super(paramQuotationServer);
  }

  public void initCurData()
  {
    try
    {
      if (quotationServer.bDataLoaded)
        this.currentNum = 0L;
      else
        this.currentNum = QuotationServer.getCurDataDAO().queryQuotationNO();
      QuotationServer.getCurDataDAO().getAllCurData(QuotationServer.htProductData, quotationServer.cclass_id, quotationServer.cclass_bq, quotationServer.buySell);
      initLastBillAmount();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }

  public void initLastBillAmount()
  {
    Set localSet = QuotationServer.htProductData.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (((ProductDataVO)localEntry.getValue()).billData.size() > 0)
      {
        BillDataVO localBillDataVO = (BillDataVO)((ProductDataVO)localEntry.getValue()).billData.lastElement();
        PreData localPreData = new PreData();
        localPreData.preCloseAmount = localBillDataVO.closeAmount;
        localPreData.preOpenAmount = localBillDataVO.openAmount;
        localPreData.preTotalAmount = localBillDataVO.totalAmount;
        this.amountMap.put(localBillDataVO.commodityID, localPreData);
      }
    }
  }

  public boolean flashCurData()
    throws SQLException
  {
    List localList = null;
    try
    {
      try
      {
        localList = this.quotationRMI.getQuotation(this.currentNum);
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        this.quotationRMI = null;
        this.rmiManager.lookup();
        this.quotationRMI = this.rmiManager.getQuotationRMI();
      }
      if ((localList == null) || (localList.size() == 0))
        return false;
      this.currentNum += localList.size();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Quotation localQuotation = (Quotation)localIterator.next();
        String str1 = localQuotation.getCommodityID();
        String str2 = this.MarketID + str1;
        ProductDataVO localProductDataVO = (ProductDataVO)QuotationServer.htProductData.get(str2);
        if (localProductDataVO == null)
        {
          localProductDataVO = new ProductDataVO();
          localProductDataVO.code = str1;
          localProductDataVO.marketID = this.MarketID;
          QuotationServer.htProductData.put(str2, localProductDataVO);
        }
        quotationServer.transProductDate(localQuotation, localProductDataVO);
        PreData localPreData;
        if (this.amountMap.get(str1) == null)
          localPreData = new PreData();
        else
          localPreData = (PreData)this.amountMap.get(str1);
        if (localQuotation.totalAmount.longValue() > localPreData.preTotalAmount)
        {
          BillDataVO localBillDataVO = quotationServer.calculateBill(localQuotation, localPreData);
          localProductDataVO.billData.add(localBillDataVO);
        }
        localPreData.preCloseAmount = localQuotation.closeAmount.longValue();
        localPreData.preOpenAmount = localQuotation.openAmount.longValue();
        localPreData.preTotalAmount = localQuotation.totalAmount.longValue();
        localProductDataVO.bUpdated = true;
        localProductDataVO.lUpdateTime = System.currentTimeMillis();
        this.amountMap.put(str1, localPreData);
      }
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
    calculateProductDataVO();
    return true;
  }

  public Date getHQTime()
  {
    Date localDate = null;
    try
    {
      localDate = this.quotationRMI.querySystemTime();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.quotationRMI = null;
      this.rmiManager.lookup();
      this.quotationRMI = this.rmiManager.getQuotationRMI();
    }
    return localDate;
  }
}