package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

public abstract interface CIBCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue saveZFPH(ZFPHValue paramZFPHValue)
    throws RemoteException;
  
  public abstract ReturnValue saveFFHD(FFHDValue paramFFHDValue)
    throws RemoteException;
  
  public abstract RZQSValue getXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;
  
  public abstract RZDZValue getXYDZValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue checkSigning(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract int modfirmuser(FirmValue paramFirmValue, String paramString)
    throws RemoteException;
  
  public abstract Vector<CorrespondValue> getCorrespondList(String paramString)
    throws RemoteException;
}
