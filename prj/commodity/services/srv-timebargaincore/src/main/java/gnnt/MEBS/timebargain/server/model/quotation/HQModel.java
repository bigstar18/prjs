package gnnt.MEBS.timebargain.server.model.quotation;

import gnnt.MEBS.timebargain.server.model.Quotation;

public class HQModel
{
  private String code;
  private long mark;
  private Quotation quotation;
  
  public long getMark()
  {
    return this.mark;
  }
  
  public void setMark(long paramLong)
  {
    this.mark = paramLong;
  }
  
  public Quotation getQuotation()
  {
    return this.quotation;
  }
  
  public void setQuotation(Quotation paramQuotation)
  {
    this.quotation = paramQuotation;
    this.code = paramQuotation.getCommodityID();
  }
}
