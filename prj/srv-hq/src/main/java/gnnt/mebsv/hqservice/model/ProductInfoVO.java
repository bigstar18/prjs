package gnnt.mebsv.hqservice.model;

import java.util.Date;

public class ProductInfoVO
{
  public String marketID;
  public String code;
  public String name;
  public int type;
  public int openTime;
  public int[] tradeSecNo = new int[1];
  public int closeTime;
  public Date modifyTime;
  public int status;
  public String[] pyName = new String[0];
  public float fUnit = 1.0F;
  public float spread = 1.0F;
  public String addrCode;
  public String industryCode;
  public String coDescription;
  public String proUTF;
  public static final int TYPE_INVALID = -1;
  public static final int TYPE_COMMON = 0;
  public static final int TYPE_CANCEL = 1;
  public static final int TYPE_INDEX = 2;
  public static final int TYPE_INDEX_MAIN = 3;
  public static final int TYPE_SERIES = 4;
  public static final int TYPE_PAUSE = 5;
  public static final int TYPE_FINISHIED = 6;
  public static final int TYPE_DECIMAL = 10;

  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("MarketID : " + this.marketID + str);
    localStringBuffer.append("Code:" + this.code + str);
    localStringBuffer.append("Name:" + this.name + str);
    localStringBuffer.append("Type:" + this.type + str);
    localStringBuffer.append("CloseTime:" + this.closeTime + str);
    localStringBuffer.append("CreateTime:" + this.openTime + str);
    localStringBuffer.append("ModifyTime:" + this.modifyTime + str);
    localStringBuffer.append("Status:" + this.status + str);
    localStringBuffer.append("pyName.length:" + this.pyName.length + str);
    for (int i = 0; i < this.pyName.length; i++)
      localStringBuffer.append("pyName[" + i + "]:" + this.pyName[i] + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString() + super.toString();
  }
}