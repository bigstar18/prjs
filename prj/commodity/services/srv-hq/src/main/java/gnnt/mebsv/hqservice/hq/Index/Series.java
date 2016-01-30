package gnnt.mebsv.hqservice.hq.Index;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.tools.HZPY;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;

public class Series
{
  private String[] seriesCode;
  private String[] seriesName;

  public Series()
  {
    getSeriesSetup();
  }

  private void getSeriesSetup()
  {
    Properties localProperties = new Configuration().getSection("MEBS.Series");
    if (localProperties == null)
      return;
    int i = Integer.parseInt(localProperties.getProperty("Num", "0"));
    this.seriesCode = new String[i];
    this.seriesName = new String[i];
    if (i > 0)
    {
      localProperties.list(System.out);
      System.out.println("");
    }
    for (int j = 0; j < i; j++)
    {
      this.seriesCode[j] = localProperties.getProperty("Series_" + (j + 1) + "_Code");
      this.seriesName[j] = localProperties.getProperty("Series_" + (j + 1) + "_Name");
    }
  }

  public ProductInfoVO[] getSeriesInfoVO()
  {
    if (this.seriesCode == null)
      return new ProductInfoVO[0];
    ProductInfoVO[] arrayOfProductInfoVO = new ProductInfoVO[this.seriesCode.length];
    for (int i = 0; i < this.seriesCode.length; i++)
    {
      arrayOfProductInfoVO[i] = new ProductInfoVO();
      arrayOfProductInfoVO[i].code = this.seriesCode[i];
      arrayOfProductInfoVO[i].name = this.seriesName[i];
      arrayOfProductInfoVO[i].modifyTime = new Date(0L);
      arrayOfProductInfoVO[i].status = 4;
      try
      {
        arrayOfProductInfoVO[i].pyName = HZPY.getPYJM(arrayOfProductInfoVO[i].name, "GBK");
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return arrayOfProductInfoVO;
  }
}