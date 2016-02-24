package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import java.rmi.RemoteException;
import java.util.Vector;

public abstract interface CMBCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue checkSigning(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract long insertFirmTradeStatus(Vector<FirmTradeStatus> paramVector, int paramInt)
    throws RemoteException;
  
  public abstract long insertTradeDetailAccount(Vector<TradeDetailAccount> paramVector, int paramInt)
    throws RemoteException;
  
  public abstract long tradeDate(String paramString, int paramInt)
    throws RemoteException;
}
