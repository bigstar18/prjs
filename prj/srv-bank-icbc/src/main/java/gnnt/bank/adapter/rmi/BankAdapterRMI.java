package gnnt.bank.adapter.rmi;

import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public abstract interface BankAdapterRMI extends Remote
{
  public abstract String testRmi()
    throws RemoteException;

  public abstract ReturnValue inMoneyQueryBank(InMoneyVO paramInMoneyVO)
    throws RemoteException;

  public abstract ReturnValue outMoneyMarketDone(OutMoneyVO paramOutMoneyVO)
    throws RemoteException;

  public abstract ReturnValue outMoneyBackBank(OutMoneyVO paramOutMoneyVO, boolean paramBoolean)
    throws RemoteException;

  public abstract ReturnValue transferMoney(TransferMoneyVO paramTransferMoneyVO)
    throws RemoteException;

  public abstract ReturnValue rgstAccountQuery(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue checkAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue delAccountQuery(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue loginBank(String paramString)
    throws RemoteException;

  public abstract ReturnValue quitBank(String paramString)
    throws RemoteException;

  public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date paramDate, Vector paramVector)
    throws RemoteException;

  public abstract int setBankMoneyInfo(Date paramDate)
    throws RemoteException;

  public abstract double accountQuery(CorrespondValue paramCorrespondValue, String paramString)
    throws RemoteException;

  public abstract ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> paramHashtable)
    throws RemoteException;

  public abstract ReturnValue setTotalMoneyInfo(Hashtable<String, Double> paramHashtable)
    throws RemoteException;

  public abstract void sendTradeData()
    throws RemoteException;

  public abstract ReturnValue sendTradeData(List<FirmRights> paramList, List<TradingDetailsValue> paramList1, List<OpenOrDelFirmValue> paramList2, Date paramDate)
    throws RemoteException;

  public abstract void getTradeDataRst(Date paramDate)
    throws RemoteException;

  public abstract Vector<BankFirmRightValue> getBankFirmRightValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ProperBalanceValue getProperBalanceValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue bankTransfer(BankTransferValue paramBankTransferValue)
    throws RemoteException;

  public abstract ReturnValue matketOutMoney(BankTransferValue paramBankTransferValue)
    throws RemoteException;

  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2)
    throws RemoteException;
}