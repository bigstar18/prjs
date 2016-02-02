package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Quotation;

public abstract interface QuotationCallback
{
  public static final int interval = 300;
  
  public abstract void callback(Quotation paramQuotation);
}
