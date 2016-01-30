package gnnt.mebsv.hqservice.model.HQVO;

import gnnt.mebsv.hqservice.model.TradeTimeVO;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class CMDTradeTimeVO extends CMDVO
{
  public CMDTradeTimeVO()
  {
    this.cmd = 8;
  }

  public static Hashtable getObj(DataInputStream paramDataInputStream)
    throws IOException
  {
    Hashtable localHashtable = new Hashtable();
    int i = paramDataInputStream.readInt();
    for (int j = 0; j < i; j++)
    {
      String str = paramDataInputStream.readUTF();
      MarketDataVO localMarketDataVO = new MarketDataVO();
      localMarketDataVO.marketID = str;
      int k = paramDataInputStream.readInt();
      TradeTimeVO[] arrayOfTradeTimeVO = new TradeTimeVO[k];
      for (int m = 0; m < arrayOfTradeTimeVO.length; m++)
      {
        arrayOfTradeTimeVO[m] = new TradeTimeVO();
        arrayOfTradeTimeVO[m].orderID = paramDataInputStream.readInt();
        arrayOfTradeTimeVO[m].beginDate = paramDataInputStream.readInt();
        arrayOfTradeTimeVO[m].beginTime = paramDataInputStream.readInt();
        arrayOfTradeTimeVO[m].endDate = paramDataInputStream.readInt();
        arrayOfTradeTimeVO[m].endTime = paramDataInputStream.readInt();
        arrayOfTradeTimeVO[m].tradeDate = paramDataInputStream.readInt();
        arrayOfTradeTimeVO[m].status = paramDataInputStream.readInt();
      }
      localMarketDataVO.m_timeRange = arrayOfTradeTimeVO;
      localHashtable.put(str, localMarketDataVO);
    }
    return localHashtable;
  }
}