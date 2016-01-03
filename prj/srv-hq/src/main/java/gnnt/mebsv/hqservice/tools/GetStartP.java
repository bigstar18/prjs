package gnnt.mebsv.hqservice.tools;

public class GetStartP
{
  public static String getParameterName(String paramString)
  {
    int i = paramString.indexOf("=");
    if (i == -1)
      return "ERROR";
    return paramString.substring(0, i);
  }

  public static int getParameterNum(String paramString)
  {
    int i = paramString.lastIndexOf("=");
    if (i == -1)
      return -1;
    int j = Integer.parseInt(paramString.substring(i + 1, paramString.length()));
    if (j > 0)
      return j;
    return -1;
  }
}