package gnnt.MEBS.packaging.action.util;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

public class DoubleConvert
  extends StrutsTypeConverter
{
  private final transient Log logger = LogFactory.getLog(DoubleConvert.class);
  
  public Object convertFromString(Map arg0, String[] values, Class toClass)
  {
    this.logger.debug(">>>>>>>>>>>DoubleConvert>>>>>>>>>>>>>调用类型转换");
    if (values == null) {
      return null;
    }
    if (Double.class == toClass)
    {
      String doubleStr = values[0];
      if ((doubleStr == null) || ("".equals(doubleStr.trim())))
      {
        this.logger.debug("获取到的字符串 doubleStr == null");
        return null;
      }
      this.logger.debug("获取到的字符串" + doubleStr);
      Double d = Double.valueOf(Double.parseDouble(doubleStr));
      return d;
    }
    return null;
  }
  
  public String convertToString(Map arg0, Object o)
  {
    this.logger.debug(">>>>>>>>DoubleConvert>>>>convertToString>>>>>>value   " + o);
    if (o == null) {
      return "";
    }
    String oTemp = o.toString();
    return oTemp;
  }
}
