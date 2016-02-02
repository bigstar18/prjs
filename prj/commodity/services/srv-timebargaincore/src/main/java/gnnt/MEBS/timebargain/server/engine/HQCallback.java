package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.quotation.server.HQServer;

public class HQCallback
  implements QuotationCallback
{
  private HQServer hqServer = HQServer.getInstance();
  
  public void callback(Quotation paramQuotation)
  {
    this.hqServer.updateQuotation(paramQuotation);
  }
}
