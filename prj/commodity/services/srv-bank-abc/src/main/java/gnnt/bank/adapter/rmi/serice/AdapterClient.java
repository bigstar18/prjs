package gnnt.bank.adapter.rmi.serice;

import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.util.Configuration;
import java.io.PrintStream;
import java.util.Properties;

public class AdapterClient
{
  public BankAdapter adapter = null;

  public String getConfig(String key)
  {
    Properties props = new Configuration().getSection("BANK.Adapter");
    return props.getProperty(key);
  }

  public void initAdapter()
  {
    System.out.println("初始化适配器...");
    this.adapter = BankAdapter.getInstance();
    System.out.println(getConfig("BankID"));
    this.adapter.setBankID(getConfig("BankID"));
    System.out.println(this.adapter.getBankID());
    this.adapter.setMarketCode(getConfig("MarketCode"));
    this.adapter.start();
  }

  public static void main(String[] args) {
    AdapterClient ac = new AdapterClient();
    System.out.println(ac.getConfig("BankID"));
  }
}