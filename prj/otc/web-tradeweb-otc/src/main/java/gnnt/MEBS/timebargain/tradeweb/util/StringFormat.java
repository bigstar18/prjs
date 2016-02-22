package gnnt.MEBS.timebargain.tradeweb.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormat
{
  private static SimpleDateFormat dateFormat = new SimpleDateFormat();
  private static DecimalFormat numberFormat = new DecimalFormat();
  
  public static boolean isValidDate(String dateString, String dateFormatPattern)
  {
    Date validDate = null;
    synchronized (dateFormat)
    {
      try
      {
        dateFormat.applyPattern(dateFormatPattern);
        dateFormat.setLenient(false);
        validDate = dateFormat.parse(dateString);
      }
      catch (ParseException localParseException) {}
    }
    return validDate != null;
  }
  
  public static boolean isValidInteger(String numberString, int min, int max)
  {
    Integer validInteger = null;
    try
    {
      Number aNumber = numberFormat.parse(numberString);
      int anInt = aNumber.intValue();
      if ((anInt >= min) && (anInt <= max)) {
        validInteger = new Integer(anInt);
      }
    }
    catch (ParseException localParseException) {}
    return validInteger != null;
  }
  
  public static boolean isValidEmailAddr(String emailAddrString)
  {
    boolean isValid = false;
    if ((emailAddrString != null) && 
      (emailAddrString.indexOf("@") != -1) && 
      (emailAddrString.indexOf(".") != -1)) {
      isValid = true;
    }
    return isValid;
  }
  
  public static boolean isValidString(String value, String[] validStrings, boolean ignoreCase)
  {
    boolean isValid = false;
    for (int i = 0; (validStrings != null) && (i < validStrings.length); i++) {
      if (ignoreCase)
      {
        if (validStrings[i].equalsIgnoreCase(value))
        {
          isValid = true;
          break;
        }
      }
      else if (validStrings[i].equals(value))
      {
        isValid = true;
        break;
      }
    }
    return isValid;
  }
  
  public static String toHTMLString(String in)
  {
    StringBuffer out = new StringBuffer();
    for (int i = 0; (in != null) && (i < in.length()); i++)
    {
      char c = in.charAt(i);
      if (c == '\'') {
        out.append("&#39;");
      } else if (c == '"') {
        out.append("&#34;");
      } else if (c == '<') {
        out.append("&lt;");
      } else if (c == '>') {
        out.append("&gt;");
      } else if (c == '&') {
        out.append("&amp;");
      } else if (c == ' ') {
        out.append("&nbsp;");
      } else if (c == '\n') {
        out.append("<br/>");
      } else {
        out.append(c);
      }
    }
    return out.toString();
  }
  
  public static Date toDate(String dateString, String dateFormatPattern)
    throws ParseException
  {
    Date date = null;
    if (dateFormatPattern == null) {
      dateFormatPattern = "yyyy-MM-dd";
    }
    synchronized (dateFormat)
    {
      dateFormat.applyPattern(dateFormatPattern);
      dateFormat.setLenient(false);
      date = dateFormat.parse(dateString);
    }
    return date;
  }
  
  public static String getDate(String strDateTime)
  {
    return strDateTime.substring(0, strDateTime.indexOf(" "));
  }
  
  public static Number toNumber(String numString, String numFormatPattern)
    throws ParseException
  {
    Number number = null;
    if (numFormatPattern == null) {
      numFormatPattern = "######.##";
    }
    synchronized (numberFormat)
    {
      numberFormat.applyPattern(numFormatPattern);
      number = numberFormat.parse(numString);
    }
    return number;
  }
  
  public static String replaceInString(String in, String from, String to)
  {
    if ((in == null) || (from == null) || (to == null)) {
      return in;
    }
    StringBuffer newValue = new StringBuffer();
    char[] inChars = in.toCharArray();
    int inLen = inChars.length;
    char[] fromChars = from.toCharArray();
    int fromLen = fromChars.length;
    for (int i = 0; i < inLen; i++) {
      if ((inChars[i] == fromChars[0]) && (i + fromLen <= inLen))
      {
        boolean isEqual = true;
        for (int j = 1; j < fromLen; j++) {
          if (inChars[(i + j)] != fromChars[j])
          {
            isEqual = false;
            break;
          }
        }
        if (isEqual)
        {
          newValue.append(to);
          i += fromLen - 1;
        }
        else
        {
          newValue.append(inChars[i]);
        }
      }
      else
      {
        newValue.append(inChars[i]);
      }
    }
    return newValue.toString();
  }
  
  public static String LTrim(String source)
  {
    if ((source == null) || (source.length() == 0)) {
      return "";
    }
    if (source.charAt(0) != ' ') {
      return source;
    }
    for (int i = 0; i < source.length(); i++) {
      if (source.charAt(i) != ' ') {
        break;
      }
    }
    return source.substring(i);
  }
  
  public static String RTrim(String source)
  {
    if ((source == null) || (source.length() == 0)) {
      return "";
    }
    if (source.charAt(source.length() - 1) != ' ') {
      return source;
    }
    for (int i = source.length() - 1; i > 0; i--) {
      if (source.charAt(i) != ' ') {
        break;
      }
    }
    return source.substring(0, i + 1);
  }
  
  public static String Trim(String source)
  {
    return LTrim(RTrim(source));
  }
  
  public static String repeatString(String str, int number)
  {
    str = getNullString(str);
    if (number < 0) {
      return "";
    }
    StringBuffer sb = new StringBuffer(str.length() * number);
    for (int i = 0; i < number; i++) {
      sb.append(str);
    }
    return sb.toString();
  }
  
  public static String getAlertString(String str)
  {
    str = getNullString(str);
    return str.replace('\n', ' ').replace('\r', ' ').replace('"', '\'');
  }
  
  public static String getNullString(String str)
  {
    return str == null ? "" : str;
  }
  
  public static final String encodeHex(byte[] bytes)
  {
    StringBuffer buff = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++)
    {
      String b = Integer.toHexString(bytes[i]);
      
      buff.append(b.length() > 2 ? b.substring(6, 8) : b);
      buff.append(" ");
    }
    return buff.toString();
  }
  
  public static final String arrayToString(byte[] bytes)
  {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) {
      buff.append(bytes[i] + " ");
    }
    return buff.toString();
  }
}
