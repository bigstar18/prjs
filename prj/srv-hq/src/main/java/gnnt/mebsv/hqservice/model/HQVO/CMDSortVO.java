package gnnt.mebsv.hqservice.model.HQVO;

import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.io.DataInputStream;
import java.io.IOException;

public class CMDSortVO extends CMDVO
{
  public byte sortBy;
  public int start;
  public int end;
  public byte isDescend;

  public CMDSortVO()
  {
    this.cmd = 5;
  }

  public static ProductDataVO[] getObj(DataInputStream paramDataInputStream)
    throws IOException
  {
    ProductDataVO[] arrayOfProductDataVO = new ProductDataVO[paramDataInputStream.readInt()];
    for (int i = 0; i < arrayOfProductDataVO.length; i++)
    {
      arrayOfProductDataVO[i] = new ProductDataVO();
      arrayOfProductDataVO[i].code = paramDataInputStream.readUTF();
      arrayOfProductDataVO[i].yesterBalancePrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].closePrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].openPrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].highPrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].lowPrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].curPrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].totalAmount = paramDataInputStream.readLong();
      arrayOfProductDataVO[i].totalMoney = paramDataInputStream.readDouble();
      arrayOfProductDataVO[i].curAmount = paramDataInputStream.readInt();
      arrayOfProductDataVO[i].amountRate = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].balancePrice = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].reserveCount = paramDataInputStream.readInt();
      arrayOfProductDataVO[i].buyAmount = new int[1];
      arrayOfProductDataVO[i].buyAmount[0] = paramDataInputStream.readInt();
      arrayOfProductDataVO[i].sellAmount = new int[1];
      arrayOfProductDataVO[i].sellAmount[0] = paramDataInputStream.readInt();
      arrayOfProductDataVO[i].buyPrice = new float[1];
      arrayOfProductDataVO[i].buyPrice[0] = paramDataInputStream.readFloat();
      arrayOfProductDataVO[i].sellPrice = new float[1];
      arrayOfProductDataVO[i].sellPrice[0] = paramDataInputStream.readFloat();
    }
    return arrayOfProductDataVO;
  }
}