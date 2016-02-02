package gnnt.MEBS.transformhq.server.clientManager;

import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.quotation.ClientTest;
import gnnt.MEBS.transformhq.server.quotation.QuotationInterFace;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClientManager
{
  protected final transient Log logger = LogFactory.getLog(ClientManager.class);
  private final String version = "IDC_1.0.0";
  private List<IPConfig> ipConfigList;
  private QuotationInterFace quoInterFace;
  
  public ClientManager(QuotationInterFace quoInterFace)
  {
    this.quoInterFace = quoInterFace;
  }
  
  public void init()
  {
    this.quoInterFace.setClientVersion("IDC_1.0.0");
    this.ipConfigList = this.quoInterFace.getIPConfigList();
    if ((this.ipConfigList != null) && (this.ipConfigList.size() > 0)) {
      reClientConn(this.ipConfigList);
    }
  }
  
  public void reClientConn(List<IPConfig> ipConfigList)
  {
    for (IPConfig ipConfig : ipConfigList)
    {
      SocketClient socketClient = new SocketClient(ipConfig, this.quoInterFace);
      socketClient.start();
    }
  }
  
  public static void main(String[] args)
  {
    ClientManager manager = new ClientManager(new ClientTest());
    manager.init();
  }
}
