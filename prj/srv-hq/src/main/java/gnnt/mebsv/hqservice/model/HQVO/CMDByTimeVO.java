package gnnt.mebsv.hqservice.model.HQVO;

import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.io.DataInputStream;
import java.io.IOException;

public class CMDByTimeVO extends CMDVO
{
  public int time;

  public CMDByTimeVO()
  {
    this.cmd = 11;
  }

  public static ProductDataVO[] getObj(DataInputStream paramDataInputStream)
    throws IOException
  {
    return CMDSortVO.getObj(paramDataInputStream);
  }
}