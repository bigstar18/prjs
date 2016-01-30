package gnnt.mebsv.hqservice.model.HQVO;

import gnnt.mebsv.hqservice.model.BillDataVO;
import java.io.DataInputStream;
import java.io.IOException;

public class CMDBillVO extends CMDVO
{
  public String marketID;
  public String code;
  public byte type;
  public int date = 0;
  public int time;

  public CMDBillVO()
  {
    this.cmd = 7;
  }

  public static BillDataVO[] getObj(DataInputStream paramDataInputStream)
    throws IOException
  {
    int i = paramDataInputStream.readInt();
    BillDataVO[] arrayOfBillDataVO = new BillDataVO[i];
    for (int j = 0; j < arrayOfBillDataVO.length; j++)
    {
      arrayOfBillDataVO[j] = new BillDataVO();
      arrayOfBillDataVO[j].tradeDate = paramDataInputStream.readInt();
      arrayOfBillDataVO[j].time = paramDataInputStream.readInt();
      arrayOfBillDataVO[j].reserveCount = paramDataInputStream.readInt();
      arrayOfBillDataVO[j].buyPrice = paramDataInputStream.readFloat();
      arrayOfBillDataVO[j].curPrice = paramDataInputStream.readFloat();
      arrayOfBillDataVO[j].sellPrice = paramDataInputStream.readFloat();
      arrayOfBillDataVO[j].totalAmount = paramDataInputStream.readLong();
      arrayOfBillDataVO[j].totalMoney = paramDataInputStream.readDouble();
      arrayOfBillDataVO[j].openAmount = paramDataInputStream.readInt();
      arrayOfBillDataVO[j].closeAmount = paramDataInputStream.readInt();
      arrayOfBillDataVO[j].balancePrice = paramDataInputStream.readFloat();
    }
    return arrayOfBillDataVO;
  }
}