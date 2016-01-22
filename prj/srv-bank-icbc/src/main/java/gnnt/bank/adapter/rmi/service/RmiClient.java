package gnnt.bank.adapter.rmi.service;

import gnnt.bank.adapter.BankAdapter;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient
{
  private String rimIp_Processor = null;
  private int rimPort_Processor;
  private String rimName_Processor = null;
  public CapitalProcessorRMI capitalProcessorRMI = null;

  public RmiClient()
  {
    this.rimIp_Processor = BankAdapter.getConfig("rimIp_Processor");
    this.rimPort_Processor = Integer.parseInt(BankAdapter.getConfig("rimPort_Processor"));
    this.rimName_Processor = BankAdapter.getConfig("rimName_Processor");
  }

  public boolean connProcessor() {
    boolean result = true;
    String fullRmiPath = "//" + this.rimIp_Processor + ":" + this.rimPort_Processor + "/" + this.rimName_Processor;
    try {
      this.capitalProcessorRMI = ((CapitalProcessorRMI)Naming.lookup(fullRmiPath));
      result = true;
    } catch (MalformedURLException e) {
      BankAdapter.log("连接处理器失败,处理器地址不正确");

      result = false;
    } catch (RemoteException e) {
      BankAdapter.log("连接处理器失败,处理器远程服务未打开");

      result = false;
    } catch (NotBoundException e) {
      BankAdapter.log("连接处理器失败,处理器远程服务未绑定");

      result = false;
    }
    return result;
  }
}