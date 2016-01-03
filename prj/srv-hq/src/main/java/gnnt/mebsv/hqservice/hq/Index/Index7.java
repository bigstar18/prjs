package gnnt.mebsv.hqservice.hq.Index;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class Index7 extends Index
{
  private Hashtable seriesCodeht = new Hashtable();
  private ArrayList commList = new ArrayList();

  public Index7()
  {
  }

  public Index7(HQDAO paramHQDAO)
  {
    super(paramHQDAO);
  }

  void init(Date paramDate)
  {
    super.init(paramDate);
    this.seriesCodeht.clear();
    this.commList.clear();
  }

  boolean calculate(int paramInt)
  {
    return false;
  }

  int getCalculateResult(int paramInt)
  {
    int i = 0;
    boolean bool = false;
    ProductDataVO localProductDataVO = null;
    Object localObject = (String)this.seriesCodeht.get(this.indexCode[paramInt]);
    if (localObject == null)
    {
      try
      {
        this.commList = ((ArrayList)this.dao.getAllCommID());
      }
      catch (SQLException localSQLException1)
      {
        localSQLException1.printStackTrace();
      }
      this.indexData[paramInt] = new ProductDataVO();
      Calendar localCalendar = Calendar.getInstance();
      int k = localCalendar.get(1) * 100 + (localCalendar.get(2) + 1);
      if (localCalendar.get(5) > this.baseDate.get(5))
        k = addMonth(k);
      int m = 0;
      for (int n = 0; n < this.seriesMonth; n++)
        k = addMonth(k);
      String str1 = null;
      do
      {
        str1 = getIndexMask(this.indexMask[paramInt]) + String.valueOf(k).substring(2) + "+\\w{0,}";
        System.out.println(str1);
        if (!this.commList.isEmpty())
          for (int i1 = 0; i1 < this.commList.size(); i1++)
          {
            String str2 = (String)this.commList.get(i1);
            if (str2.matches(str1))
            {
              localObject = str2;
              System.out.println((String)localObject);
              break;
            }
            localObject = str1;
          }
        if ((localObject != null) && (((String)localObject).length() != 0))
        {
          localProductDataVO = IndexCalculator.quotation.getProductData("00" + (String)localObject);
          if (localProductDataVO == null)
          {
            k = addMonth(k);
            m++;
          }
          else
          {
            this.indexData[paramInt] = ((ProductDataVO)localProductDataVO.clone());
            System.out.println("-----Use----" + this.indexData[paramInt].code);
            this.indexData[paramInt].code = this.indexCode[paramInt];
            System.out.println("----Seriea Code----" + this.indexData[paramInt].code);
            this.indexData[paramInt].name = this.indexName[paramInt];
            break;
          }
        }
        else
        {
          System.out.println("没有相应的连续指数代码!");
          break;
        }
      }
      while (m < 6);
      try
      {
        bool = this.dao.isDataExist(this.indexData[paramInt].code, this.indexData[paramInt].totalAmount, this.indexData[paramInt].totalMoney, this.indexData[paramInt].reserveCount);
      }
      catch (SQLException localSQLException2)
      {
        localSQLException2.printStackTrace();
      }
      this.seriesCodeht.put(this.indexCode[paramInt], localObject);
      return bool ? 0 : 1;
    }
    localProductDataVO = IndexCalculator.quotation.getProductData("00" + (String)localObject);
    int j = getDataState(localProductDataVO, this.indexData[paramInt]);
    if (j == 0)
      return 0;
    if (j == 1)
    {
      this.indexData[paramInt] = ((ProductDataVO)localProductDataVO.clone());
      this.indexData[paramInt].code = this.indexCode[paramInt];
      this.indexData[paramInt].name = this.indexName[paramInt];
      return 1;
    }
    this.indexData[paramInt] = ((ProductDataVO)localProductDataVO.clone());
    this.indexData[paramInt].code = this.indexCode[paramInt];
    this.indexData[paramInt].name = this.indexName[paramInt];
    return 2;
  }

  boolean input(ProductDataVO[] paramArrayOfProductDataVO)
  {
    return true;
  }

  private int addMonth(int paramInt)
  {
    paramInt += 1;
    if (paramInt % 100 > 12)
      paramInt = (paramInt / 100 + 1) * 100 + 1;
    return paramInt;
  }

  private String getIndexMask(String paramString)
  {
    String str = "";
    if ((paramString != null) && (paramString.length() > 0))
      for (int i = 0; i < paramString.length(); i++)
        if (paramString.charAt(i) == '?')
        {
          str = paramString.substring(0, i);
          break;
        }
    return str;
  }

  private int getDataState(ProductDataVO paramProductDataVO1, ProductDataVO paramProductDataVO2)
  {
    int i = 0;
    if ((paramProductDataVO1.totalAmount <= paramProductDataVO2.totalAmount) && (paramProductDataVO1.totalMoney <= paramProductDataVO2.totalMoney) && (paramProductDataVO1.buyPrice[0] == paramProductDataVO2.buyPrice[0]) && (paramProductDataVO1.buyAmount[0] == paramProductDataVO2.buyAmount[0]) && (paramProductDataVO1.buyAmount[1] == paramProductDataVO2.buyAmount[1]) && (paramProductDataVO1.buyAmount[2] == paramProductDataVO2.buyAmount[2]) && (paramProductDataVO1.buyAmount[3] == paramProductDataVO2.buyAmount[3]) && (paramProductDataVO1.buyAmount[4] == paramProductDataVO2.buyAmount[4]) && (paramProductDataVO1.buyPrice[1] == paramProductDataVO2.buyPrice[1]) && (paramProductDataVO1.buyPrice[2] == paramProductDataVO2.buyPrice[2]) && (paramProductDataVO1.buyPrice[3] == paramProductDataVO2.buyPrice[3]) && (paramProductDataVO1.buyPrice[4] == paramProductDataVO2.buyPrice[4]) && (paramProductDataVO1.sellAmount[0] == paramProductDataVO2.sellAmount[0]) && (paramProductDataVO1.sellPrice[0] == paramProductDataVO2.sellPrice[0]))
      i = 0;
    if ((paramProductDataVO1.totalAmount == paramProductDataVO2.totalAmount) && (paramProductDataVO1.reserveCount == paramProductDataVO2.reserveCount) && (paramProductDataVO1.totalMoney == paramProductDataVO2.totalMoney) && ((paramProductDataVO1.buyPrice[0] != paramProductDataVO2.buyPrice[0]) || (paramProductDataVO1.buyPrice[1] != paramProductDataVO2.buyPrice[1]) || (paramProductDataVO1.buyPrice[2] != paramProductDataVO2.buyPrice[2]) || (paramProductDataVO1.buyPrice[3] != paramProductDataVO2.buyPrice[3]) || (paramProductDataVO1.buyPrice[4] != paramProductDataVO2.buyPrice[4]) || (paramProductDataVO1.buyAmount[0] != paramProductDataVO2.buyAmount[0]) || (paramProductDataVO1.buyAmount[1] != paramProductDataVO2.buyAmount[1]) || (paramProductDataVO1.buyAmount[2] != paramProductDataVO2.buyAmount[2]) || (paramProductDataVO1.buyAmount[3] != paramProductDataVO2.buyAmount[3]) || (paramProductDataVO1.buyAmount[4] != paramProductDataVO2.buyAmount[4]) || (paramProductDataVO1.sellPrice[0] != paramProductDataVO2.sellPrice[0]) || (paramProductDataVO1.sellAmount[0] != paramProductDataVO2.sellAmount[0])))
      i = 1;
    if ((paramProductDataVO1.totalAmount > paramProductDataVO2.totalAmount) && (paramProductDataVO1.totalMoney > paramProductDataVO2.totalMoney))
      i = 2;
    return i;
  }

  public static void main(String[] paramArrayOfString)
  {
    Index7 localIndex7 = new Index7();
    System.out.println(localIndex7.getIndexMask("AFVF????"));
    String str1 = "TC0901";
    String str2 = "TC0901\\w{0,}";
    if (str1.matches(str2))
      System.out.print("yes");
    else
      System.out.print("NO");
  }
}