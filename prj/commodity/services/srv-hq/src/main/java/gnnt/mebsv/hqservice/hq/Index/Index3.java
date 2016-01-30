package gnnt.mebsv.hqservice.hq.Index;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public class Index3 extends Index
{
  public Index3(HQDAO paramHQDAO)
  {
    super(paramHQDAO);
  }

  protected void init(Date paramDate)
  {
    super.init(paramDate);
    initIndexData(paramDate);
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
    long l = 0L;
    int i = 0;
    double d = 0.0D;
    Enumeration localEnumeration = this.htProductData.keys();
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
          d += localProductDataForIndex.totalMoney / 100.0D;
        }
      }
    }
    if (l <= this.indexData[paramInt].totalAmount)
      return false;
    if ((l == 0L) || (d == 0.0D))
      this.indexData[paramInt].curPrice = (this.indexData[paramInt].balancePrice = this.indexData[paramInt].yesterBalancePrice);
    else
      this.indexData[paramInt].curPrice = (this.indexData[paramInt].balancePrice = (float)(d / l * 20.0D));
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
    this.indexData[paramInt].totalMoney = d;
    return this.indexData[paramInt].curPrice != 0.0F;
  }

  /** @deprecated */
  private void initIndexData(Date paramDate)
  {
    DayDataVO localDayDataVO = null;
    for (int i = 0; i < this.indexData.length; i++)
      try
      {
        localDayDataVO = this.dao.getPreDayData(this.indexCode[i], paramDate);
        if (localDayDataVO == null)
        {
          this.indexData[i].time = ((Date)this.dao.getHqTime().get(Integer.valueOf(0)));
          this.indexData[i].curPrice = 1000.0F;
          this.indexData[i].yesterBalancePrice = 1000.0F;
        }
        else
        {
          this.indexData[i].time = ((Date)this.dao.getHqTime().get(Integer.valueOf(0)));
          this.indexData[i].curPrice = this.indexData[i].yesterBalancePrice;
          this.indexData[i].openPrice = this.indexData[i].curPrice;
          this.indexData[i].reserveCount = localDayDataVO.reserveCount;
        }
        System.out.println("New Day..init index data");
        this.dao.initIndexData(this.indexData[i]);
      }
      catch (SQLException localSQLException)
      {
        localSQLException.printStackTrace();
      }
  }
}