package gnnt.MEBS.security.a;

public final class a
{
  public static boolean a(String paramString)
  {
    if (paramString == null) {
      return true;
    }
    return ((paramString = (paramString = (paramString = (paramString = (paramString = (paramString = (paramString = (paramString = (paramString = paramString.replace('\t', ' ')).replace('\r', ' ')).replace('\n', ' ')).replace('\f', ' ')).replace('(', ' ')).replace(')', ' ')).replace('/', ' ')).replace('\\', ' ')).toLowerCase()).indexOf("processbuilder") < 0) && (paramString.indexOf("dbms_xmlquery") < 0) && (paramString.indexOf("newcontext") < 0) && (paramString.indexOf("declare") < 0) && (paramString.indexOf("pragma") < 0) && (paramString.indexOf("autonomous_transaction") < 0) && (paramString.indexOf(" begin ") < 0) && (paramString.indexOf("execute") < 0) && (paramString.indexOf("commit") < 0) && (paramString.indexOf(" and ") < 0) && (paramString.indexOf(" or ") < 0) && (paramString.indexOf(" select ") < 0) && (paramString.indexOf(" update ") < 0) && (paramString.indexOf(" delete ") < 0) && (paramString.indexOf(" drop ") < 0) && (paramString.indexOf(" alter ") < 0) && (paramString.indexOf("--") < 0) && (paramString.indexOf("/*") < 0) && (paramString.indexOf("*/") < 0) && (paramString.indexOf("chr(") < 0) && (paramString.indexOf("'") < 0) && (paramString.indexOf("|") < 0);
  }
  
  public static boolean b(String paramString)
  {
    if (paramString == null) {
      return true;
    }
    return (!(paramString = paramString.toLowerCase()).startsWith("%25")) && (paramString.indexOf("redirect:") < 0) && (paramString.indexOf("redirectaction:") < 0) && (paramString.indexOf("action:") < 0) && (paramString.indexOf("java.lang.") < 0) && (paramString.indexOf("java.io.") < 0);
  }
}
