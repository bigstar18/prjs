package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DecimalsDigitVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(DecimalsDigitVerifyHandle.class);
  
  protected String verify(List<Map<String, String>> list, Map<String, String> map)
  {
    String result = null;
    for (Map<String, String> verifyMap : list)
    {
      String field = (String)verifyMap.get("field");
      this.logger.debug("verify_field:" + field);
      String maxDigitForString = (String)verifyMap.get("maxDigit");
      this.logger.debug("maxDigitForString:" + maxDigitForString);
      String maxIntString = (String)verifyMap.get("maxInt");
      this.logger.debug("maxIntString:" + maxIntString);
      if (map.containsKey(field))
      {
        String value = (String)map.get(field);
        this.logger.debug("verify_value:" + value);
        int digit = checkDigit(value);
        this.logger.debug("digit:" + digit);
        int ints = checkInt(value);
        this.logger.debug("ints:" + ints);
        if (digit > Integer.parseInt(maxDigitForString))
        {
          result = 
            (String)verifyMap.get("name") + "超过" + maxDigitForString + "位小数";
          break;
        }
        if (ints > Integer.parseInt(maxIntString))
        {
          result = 
            (String)verifyMap.get("name") + "超过" + maxIntString + "位整数";
          break;
        }
      }
    }
    return result;
  }
  
  private int checkDigit(String str)
  {
    int result = 0;
    Pattern p = Pattern.compile("\\d{1,}\\.\\d{1,}");
    Matcher m = p.matcher(str);
    boolean b = m.matches();
    if (b)
    {
      int bitPos = str.indexOf(".");
      

      int numOfBits = str.length() - bitPos - 1;
      result = numOfBits;
    }
    else
    {
      result = 0;
    }
    return result;
  }
  
  private int checkInt(String str)
  {
    int result = 0;
    Pattern p = Pattern.compile("\\d{1,}\\.\\d{1,}");
    Matcher m = p.matcher(str);
    boolean b = m.matches();
    this.logger.debug("b:" + b);
    if (b)
    {
      int bitPos = str.indexOf(".");
      

      result = bitPos;
    }
    else
    {
      result = str.length();
    }
    return result;
  }
}
