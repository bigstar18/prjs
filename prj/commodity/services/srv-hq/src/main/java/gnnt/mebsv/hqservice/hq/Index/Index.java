package gnnt.mebsv.hqservice.hq.Index;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public abstract class Index
{
  protected HQDAO dao;
  String[] indexCode;
  String[] indexMask;
  String[] indexMaskExclude;
  String[] indexName;
  ProductDataVO[] indexData;
  Calendar baseDate = Calendar.getInstance();
  int seriesMonth;
  String marketID;
  Hashtable htProductData = new Hashtable();

  public Index()
  {
  }

  public Index(HQDAO paramHQDAO)
  {
    this.dao = paramHQDAO;
  }

  void init(Date paramDate)
  {
    this.htProductData.clear();
    this.indexData = new ProductDataVO[this.indexCode.length];
    for (int i = 0; i < this.indexCode.length; i++)
      initOneIndex(i, paramDate);
  }

  void initOneIndex(int paramInt, Date paramDate)
  {
    this.indexData[paramInt] = new ProductDataVO();
    this.indexData[paramInt].code = this.indexCode[paramInt];
    DayDataVO localDayDataVO = null;
    try
    {
      localDayDataVO = this.dao.getPreDayData(this.indexCode[paramInt], paramDate);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (localDayDataVO == null)
    {
      this.indexData[paramInt].yesterBalancePrice = (this.indexData[paramInt].closePrice = 10000.0F);
    }
    else
    {
      this.indexData[paramInt].yesterBalancePrice = localDayDataVO.balancePrice;
      this.indexData[paramInt].closePrice = localDayDataVO.closePrice;
    }
  }

  abstract boolean input(ProductDataVO[] paramArrayOfProductDataVO);

  abstract boolean calculate(int paramInt);

  int getCalculateResult(int paramInt)
  {
    return 0;
  }

  public static boolean codeMatchMask(String paramString1, String paramString2, String paramString3)
  {
    int i;
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      if (paramString1.length() != paramString2.length())
        return false;
      for (i = 0; i < paramString1.length(); i++)
        if ((paramString2.charAt(i) != '?') && (paramString1.charAt(i) != paramString2.charAt(i)))
          return false;
    }
    if ((paramString3 != null) && (paramString3.length() > 0))
    {
      if (paramString1.length() != paramString3.length())
        return true;
      for (i = 0; i < paramString1.length(); i++)
        if ((paramString3.charAt(i) != '?') && (paramString1.charAt(i) != paramString3.charAt(i)))
          return true;
      return false;
    }
    return true;
  }
}