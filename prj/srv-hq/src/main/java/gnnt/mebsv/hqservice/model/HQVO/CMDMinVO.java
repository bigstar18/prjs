package gnnt.mebsv.hqservice.model.HQVO;

import gnnt.mebsv.hqservice.model.MinDataVO;
import java.io.DataInputStream;
import java.io.IOException;

public class CMDMinVO extends CMDVO
{
  public String marketID;
  public String code;
  public byte type;
  public int date = 0;
  public int time;

  public CMDMinVO()
  {
    this.cmd = 6;
  }

  public static MinDataVO[] getObj(DataInputStream paramDataInputStream)
    throws IOException
  {
    int i = paramDataInputStream.readInt();
    MinDataVO[] arrayOfMinDataVO = new MinDataVO[i];
    for (int j = 0; j < arrayOfMinDataVO.length; j++)
    {
      arrayOfMinDataVO[j] = new MinDataVO();
      arrayOfMinDataVO[j].tradeDate = paramDataInputStream.readInt();
      arrayOfMinDataVO[j].time = paramDataInputStream.readInt();
      arrayOfMinDataVO[j].curPrice = paramDataInputStream.readFloat();
      arrayOfMinDataVO[j].totalAmount = paramDataInputStream.readLong();
      arrayOfMinDataVO[j].averPrice = paramDataInputStream.readFloat();
      arrayOfMinDataVO[j].reserveCount = paramDataInputStream.readInt();
    }
    return arrayOfMinDataVO;
  }
}