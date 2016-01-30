package gnnt.mebsv.hqservice.hq.Index;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Index4 extends Index
{
  Hashtable htBase = new Hashtable();

  public Index4(HQDAO paramHQDAO)
  {
    super(paramHQDAO);
  }

  protected void init(Date paramDate)
  {
    super.init(paramDate);
    this.htBase.clear();
  }

  protected boolean input(ProductDataVO[] paramArrayOfProductDataVO)
  {
    boolean bool = false;
    for (int i = 0; i < paramArrayOfProductDataVO.length; i++)
    {
      ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(paramArrayOfProductDataVO[i].code);
      if (localProductDataForIndex == null)
      {
        bool = true;
        localProductDataForIndex = new ProductDataForIndex();
        DayDataVO localDayDataVO = null;
        try
        {
          localDayDataVO = this.dao.getPreDayData(paramArrayOfProductDataVO[i].code, paramArrayOfProductDataVO[i].time);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        if (localDayDataVO != null)
        {
          localProductDataForIndex.fPrePrice = localDayDataVO.balancePrice;
          localProductDataForIndex.preTotalAmount = localDayDataVO.totalAmount;
        }
        else
        {
          localProductDataForIndex.fPrePrice = paramArrayOfProductDataVO[i].openPrice;
          localProductDataForIndex.preTotalAmount = 0L;
        }
        localProductDataForIndex.fPrice = paramArrayOfProductDataVO[i].balancePrice;
        if (localProductDataForIndex.fPrice < 0.01D)
          localProductDataForIndex.fPrice = localProductDataForIndex.fPrePrice;
        localProductDataForIndex.totalAmount = paramArrayOfProductDataVO[i].totalAmount;
        localProductDataForIndex.reserveCount = paramArrayOfProductDataVO[i].reserveCount;
        localProductDataForIndex.totalMoney = paramArrayOfProductDataVO[i].totalMoney;
        this.htProductData.put(paramArrayOfProductDataVO[i].code, localProductDataForIndex);
      }
      else if (localProductDataForIndex.totalAmount < paramArrayOfProductDataVO[i].totalAmount)
      {
        bool = true;
        localProductDataForIndex.totalAmount = paramArrayOfProductDataVO[i].totalAmount;
        if (paramArrayOfProductDataVO[i].balancePrice > 0.0F)
          localProductDataForIndex.fPrice = paramArrayOfProductDataVO[i].balancePrice;
        localProductDataForIndex.reserveCount = paramArrayOfProductDataVO[i].reserveCount;
        localProductDataForIndex.totalMoney = paramArrayOfProductDataVO[i].totalMoney;
      }
    }
    return bool;
  }

  boolean calculate(int paramInt)
  {
    double d1 = 1.0D;
    long l = 0L;
    int i = 0;
    double d2 = 0.0D;
    Enumeration localEnumeration = this.htProductData.keys();
    int j = 0;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (codeMatchMask(str, this.indexMask[paramInt], this.indexMaskExclude[paramInt]))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
        {
          l += localProductDataForIndex.totalAmount;
          i += localProductDataForIndex.reserveCount;
          d2 += localProductDataForIndex.totalMoney / 100.0D;
          float f;
          if (this.htBase.get(str) == null)
          {
            if ((str.charAt(0) == 'L') || (str.charAt(1) == 'L'))
              f = 3429.0F;
            else if (str.charAt(0) == 'B')
              f = 5400.0F;
            else
              f = 4500.0F;
            this.htBase.put(str, new Float(f));
          }
          else
          {
            f = ((Float)this.htBase.get(str)).floatValue();
          }
          if (f < 0.0F)
            f = localProductDataForIndex.fPrice;
          d1 *= localProductDataForIndex.fPrice / f;
          j++;
        }
      }
    }
    this.indexData[paramInt].curPrice = ((float)Math.pow(d1, 1.0F / j) * 1000.0F);
    this.indexData[paramInt].balancePrice = this.indexData[paramInt].curPrice;
    if ((this.indexData[paramInt].openPrice == 0.0F) || ("NaN".equals(String.valueOf(this.indexData[paramInt].openPrice))))
      this.indexData[paramInt].openPrice = this.indexData[paramInt].curPrice;
    if (this.indexData[paramInt].curPrice > this.indexData[paramInt].highPrice)
      this.indexData[paramInt].highPrice = this.indexData[paramInt].curPrice;
    if ((this.indexData[paramInt].curPrice > 0.0F) && ((this.indexData[paramInt].curPrice < this.indexData[paramInt].lowPrice) || (this.indexData[paramInt].lowPrice == 0.0F)))
      this.indexData[paramInt].lowPrice = this.indexData[paramInt].curPrice;
    this.indexData[paramInt].curAmount = ((int)(l - this.indexData[paramInt].totalAmount));
    this.indexData[paramInt].totalAmount = l;
    this.indexData[paramInt].reserveChange = (i - this.indexData[paramInt].reserveCount);
    this.indexData[paramInt].reserveCount = i;
    this.indexData[paramInt].totalMoney = d2;
    return this.indexData[paramInt].curPrice >= 0.01F;
  }
}