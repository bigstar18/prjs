package gnnt.mebsv.hqservice.model.HQVO;

import gnnt.mebsv.hqservice.model.ProductInfoVO;

public class ProductInfoListVO
{
  public int date;
  public int time;
  public ProductInfoVO[] productInfos = new ProductInfoVO[0];

  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("date:" + this.date + str);
    localStringBuffer.append("time:" + this.time + str);
    for (int i = 0; i < this.productInfos.length; i++)
      localStringBuffer.append(this.productInfos[i].toString());
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}