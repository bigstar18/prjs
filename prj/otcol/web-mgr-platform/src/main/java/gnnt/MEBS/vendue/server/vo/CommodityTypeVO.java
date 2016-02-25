package gnnt.MEBS.vendue.server.vo;

public class CommodityTypeVO
{
  public int id;
  public String name;
  public String str1;
  public String str2;
  public String str3;
  public String str4;
  
  public void setValues(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.id = paramInt;
    this.name = paramString1;
    this.str1 = paramString2;
    this.str2 = paramString3;
    this.str3 = paramString4;
    this.str4 = paramString5;
  }
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("id:" + this.id + str);
    localStringBuffer.append("name:" + this.name + str);
    localStringBuffer.append("str1:" + this.str1 + str);
    localStringBuffer.append("str2:" + this.str2 + str);
    localStringBuffer.append("str3:" + this.str3 + str);
    localStringBuffer.append("str4:" + this.str4 + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
