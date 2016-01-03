package gnnt.mebsv.hqservice.hq.Index;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Index1 extends Index
{
  public Index1(HQDAO paramHQDAO)
  {
    super(paramHQDAO);
  }

  protected void init(Date paramDate)
  {
    super.init(paramDate);
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
        DayDataVO localDayDataVO = null;
        try
        {
          localDayDataVO = this.dao.getPreDayData(paramArrayOfProductDataVO[i].code, paramArrayOfProductDataVO[i].time);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        localProductDataForIndex = new ProductDataForIndex();
        if (localDayDataVO != null)
        {
          localProductDataForIndex.fPrePrice = localDayDataVO.balancePrice;
          localProductDataForIndex.preTotalAmount = localDayDataVO.totalAmount;
        }
        else
        {
          localProductDataForIndex.fPrePrice = 0.0F;
          localProductDataForIndex.preTotalAmount = 0L;
        }
        localProductDataForIndex.fPrice = paramArrayOfProductDataVO[i].balancePrice;
        localProductDataForIndex.totalAmount = paramArrayOfProductDataVO[i].totalAmount;
        localProductDataForIndex.reserveCount = paramArrayOfProductDataVO[i].reserveCount;
        localProductDataForIndex.totalMoney = paramArrayOfProductDataVO[i].totalMoney;
        this.htProductData.put(paramArrayOfProductDataVO[i].code, localProductDataForIndex);
      }
      else if (localProductDataForIndex.totalAmount < paramArrayOfProductDataVO[i].totalAmount)
      {
        bool = true;
        localProductDataForIndex.totalAmount = paramArrayOfProductDataVO[i].totalAmount;
        localProductDataForIndex.fPrice = paramArrayOfProductDataVO[i].balancePrice;
        localProductDataForIndex.reserveCount = paramArrayOfProductDataVO[i].reserveCount;
        localProductDataForIndex.totalMoney = paramArrayOfProductDataVO[i].totalMoney;
      }
    }
    return bool;
  }

  boolean calculate(int paramInt)
  {
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = 0.0D;
    double d4 = 0.0D;
    long l = 0L;
    int i = 0;
    double d5 = 0.0D;
    Enumeration localEnumeration = this.htProductData.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (codeMatchMask(str, this.indexMask[paramInt], this.indexMaskExclude[paramInt]))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        l += localProductDataForIndex.totalAmount;
        i += localProductDataForIndex.reserveCount;
        d5 += localProductDataForIndex.totalMoney / 100.0D;
        if ((localProductDataForIndex.fPrice >= 0.01F) && (localProductDataForIndex.fPrePrice >= 0.01F))
        {
          d1 += localProductDataForIndex.fPrice * (float)localProductDataForIndex.totalAmount;
          d2 += localProductDataForIndex.fPrice * (float)localProductDataForIndex.preTotalAmount;
          d3 += localProductDataForIndex.fPrePrice * (float)localProductDataForIndex.totalAmount;
          d4 += localProductDataForIndex.fPrePrice * (float)localProductDataForIndex.preTotalAmount;
        }
      }
    }
    if (l <= this.indexData[paramInt].totalAmount)
      return false;
    if ((d3 == 0.0D) || (d4 == 0.0D))
      this.indexData[paramInt].curPrice = this.indexData[paramInt].yesterBalancePrice;
    else
      this.indexData[paramInt].curPrice = ((float)(this.indexData[paramInt].yesterBalancePrice * Math.sqrt(d1 / d3 * (d2 / d4))));
    this.indexData[paramInt].balancePrice = this.indexData[paramInt].curPrice;
    if (this.indexData[paramInt].openPrice == 0.0F)
      this.indexData[paramInt].openPrice = this.indexData[paramInt].curPrice;
    if (this.indexData[paramInt].curPrice > this.indexData[paramInt].highPrice)
      this.indexData[paramInt].highPrice = this.indexData[paramInt].curPrice;
    if ((this.indexData[paramInt].curPrice > 0.0F) && ((this.indexData[paramInt].curPrice < this.indexData[paramInt].lowPrice) || (this.indexData[paramInt].lowPrice == 0.0F)))
      this.indexData[paramInt].lowPrice = this.indexData[paramInt].curPrice;
    this.indexData[paramInt].curAmount = ((int)(l - this.indexData[paramInt].totalAmount));
    this.indexData[paramInt].totalAmount = l;
    this.indexData[paramInt].reserveChange = (i - this.indexData[paramInt].reserveCount);
    this.indexData[paramInt].reserveCount = i;
    this.indexData[paramInt].totalMoney = d5;
    return this.indexData[paramInt].curPrice != 0.0F;
  }
}