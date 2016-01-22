package gnnt.bank.adapter;

import gnnt.bank.adapter.util.Configuration;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
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
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.log4j.Logger;

public abstract class BankAdapter extends Thread
{
  protected CapitalProcessorRMI PROCESSOR;
  public String BANKID;
  public String MARKETCODE;
  public String BANKCODE;

  public void setMarketCode(String marketCode)
  {
    this.MARKETCODE = marketCode;
  }

  public String getMarketCode()
  {
    return this.MARKETCODE;
  }

  public void setBankCode(String BankCode)
  {
    this.BANKCODE = BankCode;
  }

  public String getBankCode()
  {
    return this.BANKCODE;
  }

  public void setProcessor(CapitalProcessorRMI processor)
  {
    this.PROCESSOR = processor;
  }

  public void setBankID(String bankID)
  {
    this.BANKID = bankID;
  }

  public String getBankID()
  {
    return this.BANKID;
  }

  public static void log(String content)
  {
    Logger alog = Logger.getLogger("Adapterlog");
    alog.debug(content);
  }

  public static String getConfig(String key)
  {
    Properties props = new Configuration().getSection("BANK.Adapter");
    return props.getProperty(key);
  }

  public static BankAdapter getInstance()
  {
    return Factory.getInstance().getBankAdapter();
  }

  public abstract ReturnValue inMoneyQueryBank(InMoneyVO paramInMoneyVO);

  public abstract ReturnValue outMoneyMarketDone(OutMoneyVO paramOutMoneyVO);

  public abstract ReturnValue outMoneyBackBank(OutMoneyVO paramOutMoneyVO, boolean paramBoolean);

  public abstract ReturnValue transferMoney(TransferMoneyVO paramTransferMoneyVO);

  public abstract ReturnValue rgstAccountQuery(CorrespondValue paramCorrespondValue);

  public abstract ReturnValue checkAccount(CorrespondValue paramCorrespondValue);

  public abstract ReturnValue delAccountQuery(CorrespondValue paramCorrespondValue);

  public abstract ReturnValue loginBank(String paramString);

  public abstract ReturnValue quitBank(String paramString);

  public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date paramDate);

  public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date paramDate, Vector paramVector);

  public abstract int setBankMoneyInfo(Date paramDate);

  public abstract ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> paramHashtable);

  public abstract ReturnValue setTotalMoneyInfo(Hashtable<String, Double> paramHashtable);

  public abstract double accountQuery(CorrespondValue paramCorrespondValue, String paramString);

  public abstract void sendTradeData();

  public abstract ReturnValue sendTradeData(List<FirmRights> paramList, List<TradingDetailsValue> paramList1, List<OpenOrDelFirmValue> paramList2, Date paramDate);

  public abstract void getTradeDataRst(Date paramDate);

  public abstract Vector<BankFirmRightValue> getBankFirmRightValue(String paramString, Date paramDate);

  public abstract ProperBalanceValue getProperBalanceValue(String paramString, Date paramDate);

  public abstract ReturnValue bankTransfer(BankTransferValue paramBankTransferValue);

  public abstract ReturnValue matketOutMoney(BankTransferValue paramBankTransferValue);
}