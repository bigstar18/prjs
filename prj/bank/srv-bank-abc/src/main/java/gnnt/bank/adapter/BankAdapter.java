package gnnt.bank.adapter;

import gnnt.bank.adapter.util.Configuration;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import org.apache.log4j.Logger;

public abstract class BankAdapter extends Thread
  implements Serializable
{
  public CapitalProcessorRMI PROCESSOR;
  public String BANKID;
  public String MARKETCODE;
  public static BankAdapter adpter = null;

  public void setMarketCode(String marketCode)
  {
    this.MARKETCODE = marketCode;
  }

  public String getMarketCode()
  {
    return this.MARKETCODE;
  }

  public BankAdapter()
  {
  }

  public static BankAdapter getInstance()
  {
    if (adpter == null) {
      try {
        adpter = (BankAdapter)Class.forName("gnnt.bank.adapter." + getConfig("AdapterImpl")).newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return adpter;
  }

  public BankAdapter(CapitalProcessorRMI processor, String bankID)
  {
    this.PROCESSOR = processor;
    this.BANKID = bankID;
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

  public void reUpCapitalProcessorRMI()
  {
    String processorIP = getConfig("ProcessorIP");
    String processorPort = getConfig("ProcessorPort");
    String serviceName = getConfig("ServiceName");
    try {
      this.PROCESSOR = ((CapitalProcessorRMI)Naming.lookup("//" + processorIP + ":" + processorPort + "/" + serviceName));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (NotBoundException e) {
      e.printStackTrace();
    }
  }

  public abstract ReturnValue inMoneyQueryBank(InMoneyVO paramInMoneyVO);

  public abstract ReturnValue inMoneyQueryBankenregister(InMoneyVO paramInMoneyVO);

  public abstract ReturnValue outMoneyMarketDone(OutMoneyVO paramOutMoneyVO);

  public abstract ReturnValue rgstAccountQuery(CorrespondValue paramCorrespondValue);

  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2);

  public abstract ReturnValue delAccountQuery(CorrespondValue paramCorrespondValue);

  public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date paramDate, Vector paramVector);

  public abstract int setBankMoneyInfo(Date paramDate);

  public abstract ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> paramHashtable);

  public abstract ReturnValue setTotalMoneyInfo(Hashtable<String, Double> paramHashtable);

  public abstract double accountQuery(CorrespondValue paramCorrespondValue, String paramString);

  public abstract boolean dayDataReady(Date paramDate);

  public abstract ReturnValue hxSentDZ(Vector<HXSentQSMsgValue> paramVector, Date paramDate);

  public abstract void pay1();

  public abstract void pay2();

  public abstract void pay3();
}