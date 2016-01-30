package gnnt.mebsv.hqservice.model.HQVO;

import java.io.DataInputStream;
import java.io.IOException;

public class CMDMarketSortVO extends CMDVO
{
  public int num;

  public CMDMarketSortVO()
  {
    this.cmd = 10;
  }

  public static MarketStatusVO[] getObj(DataInputStream paramDataInputStream)
    throws IOException
  {
    MarketStatusVO[] arrayOfMarketStatusVO = new MarketStatusVO[paramDataInputStream.readInt()];
    for (int i = 0; i < arrayOfMarketStatusVO.length; i++)
    {
      arrayOfMarketStatusVO[i] = new MarketStatusVO();
      arrayOfMarketStatusVO[i].marketID = paramDataInputStream.readUTF();
      arrayOfMarketStatusVO[i].code = paramDataInputStream.readUTF();
      arrayOfMarketStatusVO[i].cur = paramDataInputStream.readFloat();
      arrayOfMarketStatusVO[i].status = paramDataInputStream.readByte();
      arrayOfMarketStatusVO[i].value = paramDataInputStream.readFloat();
    }
    return arrayOfMarketStatusVO;
  }
}