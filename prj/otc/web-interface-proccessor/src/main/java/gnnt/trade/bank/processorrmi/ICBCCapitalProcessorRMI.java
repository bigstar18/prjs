package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.data.icbc.vo.BankFirmRightValue;
import gnnt.trade.bank.data.icbc.vo.FirmRights;
import gnnt.trade.bank.data.icbc.vo.OpenOrDelFirmValue;
import gnnt.trade.bank.data.icbc.vo.ProperBalanceValue;
import gnnt.trade.bank.data.icbc.vo.TradingDetailsValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public abstract interface ICBCCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract List<TradingDetailsValue> getChangeBalance(Vector<FirmBalance> paramVector)
    throws RemoteException;
  
  public abstract List<FirmRights> getRightsList(Vector<FirmBalance> paramVector)
    throws RemoteException;
  
  public abstract List<OpenOrDelFirmValue> getOpenOrDropMaket(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract long setBankFirmRightValue(List<BankFirmRightValue> paramList)
    throws RemoteException;
  
  public abstract long setProperBalanceValue(ProperBalanceValue paramProperBalanceValue)
    throws RemoteException;
  
  public abstract int modfirmuser(FirmValue paramFirmValue, String paramString)
    throws RemoteException;
  
  public abstract ReturnValue bankTransfer(BankTransferValue paramBankTransferValue)
    throws RemoteException;
  
  public abstract Vector<CorrespondValue> getCorrespondList(String paramString)
    throws RemoteException;
  
  public abstract FirmValue getFirmValue(FirmValue paramFirmValue)
    throws RemoteException;
  
  public abstract ReturnValue ifQuitFirm(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract ReturnValue checkSigning(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract String getfirmid(String paramString)
    throws RemoteException;
  
  public abstract void send(Date paramDate)
    throws RemoteException;
}
