package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

public abstract interface CMBCCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract Vector<FirmBalance> sendCMBCQS(String paramString, Date paramDate)
    throws RemoteException;
}
