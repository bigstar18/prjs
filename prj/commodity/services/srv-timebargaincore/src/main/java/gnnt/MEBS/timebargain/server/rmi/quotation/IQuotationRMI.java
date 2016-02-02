package gnnt.MEBS.timebargain.server.rmi.quotation;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.quotation.BillDataVO;
import gnnt.MEBS.timebargain.server.model.quotation.TradeTimeVO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public abstract interface IQuotationRMI
  extends Remote
{
  public abstract List<Quotation> getAllQuotation()
    throws RemoteException;
  
  public abstract List<Quotation> getQuotation(long paramLong)
    throws RemoteException;
  
  public abstract List<BillDataVO> getBills(long paramLong)
    throws RemoteException;
  
  public abstract int querySystemStatus()
    throws RemoteException;
  
  public abstract List<TradeTimeVO> queryTradeTimes()
    throws RemoteException;
  
  public abstract Date querySystemTime()
    throws RemoteException;
  
  public abstract Date queryTradeDate()
    throws RemoteException;
}
