package gnnt.MEBS.timebargain.server.dao.quotation;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.quotation.BillDataVO;
import gnnt.MEBS.timebargain.server.model.quotation.KlineVO;
import gnnt.MEBS.timebargain.server.model.quotation.TradeTimeVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract interface IDBTransfer
{
  public abstract void transferComodity(List paramList, long paramLong, HashMap<String, String> paramHashMap);
  
  public abstract void transferProductData(List paramList);
  
  public abstract void transferProductData(List paramList, int paramInt);
  
  public abstract void transferTradeTime(List<TradeTimeVO> paramList, String paramString);
  
  public abstract boolean clear();
  
  public abstract void updateMaketStatus(int paramInt1, int paramInt2);
  
  public abstract void initCurrentdata(List paramList, long paramLong);
  
  public abstract void updateSysdate(long paramLong);
  
  public abstract Date getHQSysTime();
  
  public abstract void transBidData(List paramList, String paramString);
  
  public abstract String getHQDate();
  
  public abstract void addOneData(Quotation paramQuotation);
  
  public abstract void addOneComty(Map paramMap, long paramLong, String paramString);
  
  public abstract void loadCodeSet(HashSet paramHashSet);
  
  public abstract void transBreadData();
  
  public abstract boolean checkTradeSec(List<TradeTimeVO> paramList, String paramString);
  
  public abstract boolean checkSYSInfo(long paramLong);
  
  public abstract void updateCommoditySec(HashMap<String, String> paramHashMap);
  
  public abstract void insertHisDayData(String paramString, Map paramMap);
  
  public abstract void insertHisBillData();
  
  public abstract boolean isBackUp();
  
  public abstract float queryProductUnit(String paramString);
  
  public abstract void insertHisMinKLine(String paramString, KlineVO paramKlineVO);
  
  public abstract ArrayList<BillDataVO> queryBillData(String paramString);
  
  public abstract long getPreReserveCount(String paramString);
}
