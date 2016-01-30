package gnnt.mebsv.hqservice.hq.Index;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Index6 extends Index
{
  Hashtable htBase = new Hashtable();

  public Index6(HQDAO paramHQDAO)
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
          localProductDataForIndex.hisTotalAmount = localDayDataVO.hisTotalAmount;
        }
        else
        {
          localProductDataForIndex.fPrePrice = paramArrayOfProductDataVO[i].openPrice;
          localProductDataForIndex.preTotalAmount = 0L;
          localProductDataForIndex.hisTotalAmount = 0L;
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
    long l = 0L;
    int i = 0;
    double d1 = 0.0D;
    double d2;
    if (paramInt == 0)
    {
      d2 = 1.0D;
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
            d1 += localProductDataForIndex.totalMoney / 100.0D;
            float f;
            if (this.htBase.get(str) == null)
            {
              DayDataVO localDayDataVO = null;
              try
              {
                localDayDataVO = this.dao.getBaseDayData(str, this.baseDate.getTime());
              }
              catch (Exception localException2)
              {
                localException2.printStackTrace();
              }
              if (localDayDataVO == null)
                f = -1.0F;
              else
                f = localDayDataVO.balancePrice;
              this.htBase.put(str, new Float(f));
            }
            else
            {
              f = ((Float)this.htBase.get(str)).floatValue();
            }
            if (f < 0.0F)
              f = localProductDataForIndex.fPrice;
            d2 *= localProductDataForIndex.fPrice / f;
            j++;
          }
        }
      }
      this.indexData[paramInt].curPrice = ((float)Math.pow(d2, 1.0F / j) * 1000.0F);
    }
    else if (paramInt == 1)
    {
      l = productAmount("PV1") + productAmount("PV2");
      i = productReserveCount("PV1") + productReserveCount("PV2");
      d1 = (productMoney("PV1") + productMoney("PV2")) / 100.0F;
      try
      {
        this.indexData[paramInt].curPrice = ((productPrice("PV1") * (float)productHisAmount("PV1") + productPrice("PV2") * (float)productHisAmount("PV2")) / (productBasePrice("PV1") * (float)productHisAmount("PV1") + productBasePrice("PV2") * (float)productHisAmount("PV2")) * 1000.0F);
        d2 = Double.parseDouble(this.indexData[paramInt].curPrice + "");
        this.indexData[paramInt].curPrice = ((float)d2);
      }
      catch (Exception localException1)
      {
        System.out.println("curPrice error！");
        this.indexData[paramInt].curPrice = (this.indexData[paramInt].yesterBalancePrice - 123.0F);
      }
    }
    else if (paramInt == 2)
    {
      l = productAmount("AB") + productAmount("LL") + productAmount("PP") + productAmount("PS");
      i = productReserveCount("AB") + productReserveCount("LL") + productReserveCount("PP") + productReserveCount("PS");
      d1 = (productMoney("AB") + productMoney("LL") + productMoney("PP") + productMoney("PS")) / 100.0F;
      this.indexData[paramInt].curPrice = ((productPrice("AB") * (float)productHisAmount("AB") + productPrice("LL") * (float)productHisAmount("LL") + productPrice("PP") * (float)productHisAmount("PP") + productPrice("PS") * (float)productHisAmount("PS")) / (productBasePrice("AB") * (float)productHisAmount("AB") + productBasePrice("LL") * (float)productHisAmount("LL") + productBasePrice("PP") * (float)productHisAmount("PP") + productBasePrice("PS") * (float)productHisAmount("PS")) * 1000.0F);
    }
    if (this.indexData[paramInt].code.equals("PVC"))
      this.indexData[paramInt].curPrice += 123.0F;
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
    this.indexData[paramInt].totalMoney = d1;
    return this.indexData[paramInt].curPrice >= 0.01F;
  }

  private float productPrice(String paramString)
  {
    Enumeration localEnumeration = this.htProductData.keys();
    double d = 1.0D;
    int i = 0;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (str.startsWith(paramString))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
        {
          d *= localProductDataForIndex.fPrice;
          i++;
        }
      }
    }
    if (i == 0)
      throw new ArithmeticException();
    return (float)Math.pow(d, 1.0F / i);
  }

  private float productBasePrice(String paramString)
  {
    Enumeration localEnumeration = this.htProductData.keys();
    double d = 1.0D;
    int i = 0;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (str.startsWith(paramString))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
        {
          float f;
          if (this.htBase.get(str) == null)
          {
            DayDataVO localDayDataVO = null;
            try
            {
              localDayDataVO = this.dao.getBaseDayData(str, this.baseDate.getTime());
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
            if (localDayDataVO == null)
              f = -1.0F;
            else
              f = localDayDataVO.balancePrice;
            this.htBase.put(str, new Float(f));
          }
          else
          {
            f = ((Float)this.htBase.get(str)).floatValue();
          }
          if (f < 0.0F)
            f = localProductDataForIndex.fPrice;
          d *= f;
          i++;
        }
      }
    }
    return (float)Math.pow(d, 1.0F / i);
  }

  private long productHisAmount(String paramString)
  {
    Enumeration localEnumeration = this.htProductData.keys();
    long l = 0L;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (str.startsWith(paramString))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
          l += localProductDataForIndex.hisTotalAmount + localProductDataForIndex.totalAmount;
      }
    }
    return l;
  }

  private long productAmount(String paramString)
  {
    Enumeration localEnumeration = this.htProductData.keys();
    long l = 0L;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (str.startsWith(paramString))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
          l += localProductDataForIndex.totalAmount;
      }
    }
    return l;
  }

  private float productMoney(String paramString)
  {
    Enumeration localEnumeration = this.htProductData.keys();
    float f = 0.0F;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (str.startsWith(paramString))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
          f = (float)(f + localProductDataForIndex.totalMoney);
      }
    }
    return f;
  }

  private int productReserveCount(String paramString)
  {
    Enumeration localEnumeration = this.htProductData.keys();
    int i = 0;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (str.startsWith(paramString))
      {
        ProductDataForIndex localProductDataForIndex = (ProductDataForIndex)this.htProductData.get(str);
        if (localProductDataForIndex.fPrice >= 0.01F)
          i += localProductDataForIndex.reserveCount;
      }
    }
    return i;
  }
}