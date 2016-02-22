package gnnt.MEBS.packaging.action.util;

import java.math.BigDecimal;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

public class BigDecimalConvert
  extends StrutsTypeConverter
{
  private final transient Log logger = LogFactory.getLog(BigDecimalConvert.class);
  
  public Object convertFromString(Map context, String[] values, Class toClass)
  {
    this.logger.debug(">>>>>>>>>>>BigDecimalConvert>>>>>>>>>>>>>调用类型转换");
    if (values == null) {
      return null;
    }
    if (BigDecimal.class == toClass)
    {
      String bigDecimalStr = values[0];
      if ((bigDecimalStr == null) || ("".equals(bigDecimalStr.trim())))
      {
        this.logger.debug("获取到的字符串 bigDecimalStr == null");
        return null;
      }
      this.logger.debug("获取到的字符串" + bigDecimalStr);
      bigDecimalStr = bigDecimalStr.replaceAll(",", "");
      this.logger.debug("转换后" + bigDecimalStr);
      BigDecimal b = new BigDecimal(bigDecimalStr);
      this.logger.debug(b.toString());
      return b;
    }
    return null;
  }
  
  public String convertToString(Map context, Object o)
  {
    this.logger.debug(">>>>>>>>BigDecimalConvert>>>>convertToString>>>>>>value   " + o);
    if (o == null) {
      return "";
    }
    String oTemp = o.toString();
    return oTemp;
  }
}
