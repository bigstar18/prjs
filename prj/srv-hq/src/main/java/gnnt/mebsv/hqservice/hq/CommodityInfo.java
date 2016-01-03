package gnnt.mebsv.hqservice.hq;

public class CommodityInfo
{
  public String marketID = "";
  public String commodityCode = "";

  public CommodityInfo(String paramString1, String paramString2)
  {
    this.marketID = paramString1;
    this.commodityCode = paramString2;
  }

  public boolean Compare(CommodityInfo paramCommodityInfo)
  {
    return (paramCommodityInfo.marketID.equals(this.marketID)) && (paramCommodityInfo.commodityCode.equals(this.commodityCode));
  }

  public boolean Compare(Object paramObject)
  {
    CommodityInfo localCommodityInfo = (CommodityInfo)paramObject;
    return Compare(localCommodityInfo);
  }

  public static CommodityInfo DealCode(String paramString)
  {
    String str1 = paramString;
    String str2 = "00";
    String str3 = str1;
    int i = str1.indexOf("_");
    if (i != -1)
    {
      str2 = str1.substring(0, i);
      str3 = str1.substring(i + 1);
    }
    if (str2.length() == 0)
      str2 = "00";
    CommodityInfo localCommodityInfo = new CommodityInfo(str2, str3);
    return localCommodityInfo;
  }
}