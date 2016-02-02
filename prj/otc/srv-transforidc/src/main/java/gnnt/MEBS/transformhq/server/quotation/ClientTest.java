package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientTest
  implements QuotationInterFace
{
  IPConfig ipConfig = new IPConfig("218.249.27.98", "7022", "WESTERNC", "WESTERNC", 1, 20L);
  InCommodity inCommodity = new InCommodity("C:PXAGUSDOZ\\SP", "XAG", 20L, 1, 3, 4, null, false, "4=691|");
  int i = 0;
  
  public IPConfig getUseIPConfig()
  {
    return this.ipConfig;
  }
  
  public void setHQBean(HQBean hqBean)
  {
    System.out.println("=============>" + hqBean.toString());
  }
  
  public void start() {}
  
  public Map<String, InCommodity> getInCommodity()
  {
    Map<String, InCommodity> cmdty = new HashMap();
    cmdty.put("C:PXAGUSDOZ\\SP", this.inCommodity);
    return cmdty;
  }
  
  public void setClientVersion(String version)
  {
    System.out.println("版本为：" + version);
  }
  
  public String getClientVersion()
  {
    return null;
  }
  
  public List<IPConfig> getIPConfigList()
  {
    List<IPConfig> ipconfigList = new ArrayList();
    ipconfigList.add(this.ipConfig);
    return ipconfigList;
  }
}
