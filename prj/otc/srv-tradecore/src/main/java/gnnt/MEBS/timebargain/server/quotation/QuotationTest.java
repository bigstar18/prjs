package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.model.Quotation;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class QuotationTest
  implements QuotationInterface
{
  public void init()
  {
    System.out.println("init");
  }
  
  public void setQuotation(Quotation quotation)
  {
    System.out.println(quotation.toString());
  }
  
  public List<HQServerInfo> getHQServerInfoList()
  {
    List<HQServerInfo> serverList = new ArrayList();
    
    HQServerInfo hqServerInfo1 = new HQServerInfo(0, "电信服务器", "172.16.2.10", 
      16894, 16895, 0);
    serverList.add(hqServerInfo1);
    

    HQServerInfo hqServerInfo2 = new HQServerInfo(1, "网通服务器", "172.16.2.10", 
      16896, 16897, 1);
    serverList.add(hqServerInfo2);
    
    return serverList;
  }
  
  public void dispose() {}
  
  public boolean getTraderOrderStatus()
  {
    return false;
  }
  
  public void setCurServerInfo(HQServerInfo curServerInfo) {}
}
