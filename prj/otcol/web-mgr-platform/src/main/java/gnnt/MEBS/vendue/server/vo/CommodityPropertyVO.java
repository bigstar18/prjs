package gnnt.MEBS.vendue.server.vo;

public class CommodityPropertyVO
{
  public int id;
  public int cpid;
  public String name;
  public int type;
  public int isnull;
  public String charvartext;
  public String str1;
  public String str2;
  public String str3;
  public String str4;
  public String str5;
  
  public void setValues(int paramInt1, int paramInt2, String paramString1, int paramInt3, int paramInt4, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.id = paramInt1;
    this.name = paramString1;
    this.cpid = paramInt2;
    this.type = paramInt3;
    this.isnull = paramInt4;
    this.charvartext = paramString2;
    this.str1 = paramString3;
    this.str2 = paramString4;
    this.str3 = paramString5;
    this.str4 = paramString6;
    this.str5 = this.str5;
  }
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("id:" + this.id + str);
    localStringBuffer.append("cpid:" + this.cpid + str);
    localStringBuffer.append("name:" + this.name + str);
    localStringBuffer.append("type:" + this.type + str);
    localStringBuffer.append("isnull:" + this.isnull + str);
    localStringBuffer.append("charvartext:" + this.charvartext + str);
    localStringBuffer.append("str1:" + this.str1 + str);
    localStringBuffer.append("str2:" + this.str2 + str);
    localStringBuffer.append("str3:" + this.str3 + str);
    localStringBuffer.append("str4:" + this.str4 + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
