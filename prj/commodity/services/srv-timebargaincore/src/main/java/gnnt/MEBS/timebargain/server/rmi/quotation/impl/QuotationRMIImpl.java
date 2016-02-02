package gnnt.MEBS.timebargain.server.rmi.quotation.impl;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.quotation.BillDataVO;
import gnnt.MEBS.timebargain.server.model.quotation.TradeTimeVO;
import gnnt.MEBS.timebargain.server.quotation.server.HQServer;
import gnnt.MEBS.timebargain.server.rmi.quotation.IQuotationRMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class QuotationRMIImpl
  extends UnicastRemoteObject
  implements IQuotationRMI
{
  private static final long serialVersionUID = 5625480562583805016L;
  HQServer hqServer = HQServer.getInstance();
  
  public QuotationRMIImpl()
    throws RemoteException
  {}
  
  public List<Quotation> getAllQuotation()
    throws RemoteException
  {
    return this.hqServer.queryQuotation(0L);
  }
  
  public List<BillDataVO> getBills(long paramLong)
    throws RemoteException
  {
    return null;
  }
  
  public List<Quotation> getQuotation(long paramLong)
    throws RemoteException
  {
    return this.hqServer.queryQuotation(paramLong);
  }
  
  public int querySystemStatus()
    throws RemoteException
  {
    return 0;
  }
  
  public Date querySystemTime()
    throws RemoteException
  {
    return this.hqServer.getTradeTime();
  }
  
  public Date queryTradeDate()
    throws RemoteException
  {
    return null;
  }
  
  public List<TradeTimeVO> queryTradeTimes()
    throws RemoteException
  {
    return null;
  }
}
