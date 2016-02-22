package gnnt.bank.adapter.rmi.Impl;

import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class BankAdapterRMIImpl extends UnicastRemoteObject
  implements BankAdapterRMI
{
  private BankAdapter adapter = null;
  private static final long serialVersionUID = 3800004680225048743L;

  public BankAdapterRMIImpl()
    throws RemoteException
  {
    this.adapter = BankAdapter.getInstance();
  }

  public double accountQuery(CorrespondValue correspondValue, String password)
    throws RemoteException
  {
    return this.adapter.accountQuery(correspondValue, password);
  }

  public ReturnValue delAccountQuery(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.adapter.delAccountQuery(correspondValue);
  }

  public Vector<MoneyInfoValue> getBankMoneyInfo(Date date, Vector v)
    throws RemoteException
  {
    return this.adapter.getBankMoneyInfo(date, v);
  }

  public ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO)
    throws RemoteException
  {
    return this.adapter.inMoneyQueryBank(inMoneyVO);
  }

  public ReturnValue loginBank(String bankID) throws RemoteException
  {
    return this.adapter.loginBank(bankID);
  }

  public ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO)
    throws RemoteException
  {
    return this.adapter.outMoneyMarketDone(outMoneyVO);
  }

  public ReturnValue quitBank(String bankID) throws RemoteException
  {
    return this.adapter.quitBank(bankID);
  }

  public ReturnValue rgstAccountQuery(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.adapter.rgstAccountQuery(correspondValue);
  }
  public ReturnValue checkAccount(CorrespondValue correspondValue) throws RemoteException {
    return this.adapter.checkAccount(correspondValue);
  }

  public int setBankMoneyInfo(Date date) throws RemoteException
  {
    return this.adapter.setBankMoneyInfo(date);
  }

  public ReturnValue transferMoney(TransferMoneyVO transferMoneyVO)
    throws RemoteException
  {
    return this.adapter.transferMoney(transferMoneyVO);
  }

  public ReturnValue outMoneyBackBank(OutMoneyVO outMoneyVO, boolean funFlag)
    throws RemoteException
  {
    return this.adapter.outMoneyBackBank(outMoneyVO, funFlag);
  }

  public ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> ht)
    throws RemoteException
  {
    return this.adapter.setBankMoneyInfo(ht);
  }

  public ReturnValue setTotalMoneyInfo(Hashtable<String, Double> ht)
    throws RemoteException
  {
    return this.adapter.setTotalMoneyInfo(ht);
  }

  public String testRmi() throws RemoteException
  {
    return "message come from adapter!";
  }

  public void sendTradeData() throws RemoteException
  {
    this.adapter.sendTradeData();
  }

  public ReturnValue sendTradeData(List<FirmRights> frs, List<TradingDetailsValue> tds, List<OpenOrDelFirmValue> opdf, Date tradeDate)
    throws RemoteException
  {
    return this.adapter.sendTradeData(frs, tds, opdf, tradeDate);
  }

  public void getTradeDataRst(Date date) throws RemoteException
  {
    this.adapter.getTradeDataRst(date);
  }

  public Vector<BankFirmRightValue> getBankFirmRightValue(String bankID, Date date)
    throws RemoteException
  {
    return this.adapter.getBankFirmRightValue(bankID, date);
  }

  public ProperBalanceValue getProperBalanceValue(String bankID, Date date)
    throws RemoteException
  {
    return this.adapter.getProperBalanceValue(bankID, date);
  }

  public ReturnValue bankTransfer(BankTransferValue bankTransferValue)
    throws RemoteException
  {
    return this.adapter.bankTransfer(bankTransferValue);
  }

  public ReturnValue matketOutMoney(BankTransferValue bankTransferValue) throws RemoteException
  {
    return this.adapter.matketOutMoney(bankTransferValue);
  }

  public ReturnValue modAccount(CorrespondValue corrOld, CorrespondValue corrNew) throws RemoteException
  {
    ReturnValue result = new ReturnValue();
    result.result = 5L;
    result.remark = "不支持市场端改约";
    return result;
  }
}