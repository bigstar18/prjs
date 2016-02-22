package gnnt.MEBS.timebargain.tradeweb.model;

import java.util.List;

public class ResponseCDQ
  extends Response
{
  private List lst;
  
  public ResponseCDQ()
  {
    setCMD((short)1);
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
