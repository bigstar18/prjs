package gnnt.MEBS.timebargain.tradeweb.model;

import java.util.List;

public class ResponseMCDQ
  extends Response
{
  private List lst;
  
  public ResponseMCDQ()
  {
    setCMD((short)3);
  }
  
  public List getLst()
  {
    return this.lst;
  }
  
  public void setLst(List lst)
  {
    this.lst = lst;
  }
}
