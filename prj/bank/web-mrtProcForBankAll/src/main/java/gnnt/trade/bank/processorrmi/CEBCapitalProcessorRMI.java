package gnnt.trade.bank.processorrmi;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract interface CEBCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract CEB_RSP CEB(CEB_param paramCEB_param, String paramString)
    throws RemoteException;
  
  public abstract ReturnValue chargeAgainstMaket_ceb(ChargeAgainstValue paramChargeAgainstValue)
    throws RemoteException;
  
  public abstract ReturnValue sendFile_CEB(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract List<TradingDetailsValue> getChangeBalance(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;
  
  public abstract List<FirmRights> getRightsList(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;
  
  public abstract List<FirmRightsValue> getTradeDataMsg(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;
  
  public abstract Map<String, FirmRights> getRightsMap(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;
  
  public abstract List<OpenOrDelFirmValue> getOpenOrDropMaket(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract FirmBalanceValue getMarketBalance(String paramString)
    throws RemoteException;
  
  public abstract String insertFcs(Vector<FCS_10_Result> paramVector, Vector<FCS_11_Result> paramVector1, Vector<FCS_13_Result> paramVector2, Vector<FCS_99> paramVector3)
    throws RemoteException;
  
  public abstract String insertFcs10(Vector<FCS_10_Result> paramVector)
    throws RemoteException;
  
  public abstract String insertFcs11(Vector<FCS_11_Result> paramVector)
    throws RemoteException;
  
  public abstract String insertFcs99(Vector<FCS_99> paramVector)
    throws RemoteException;
  
  public abstract String insertFcs13(Vector<FCS_13_Result> paramVector)
    throws RemoteException;
  
  public abstract ReturnValue synchroAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
}
