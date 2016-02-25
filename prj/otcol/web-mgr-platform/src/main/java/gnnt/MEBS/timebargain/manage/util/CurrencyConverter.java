package gnnt.MEBS.timebargain.manage.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CurrencyConverter
  implements Converter
{
  protected final Log log = LogFactory.getLog(CurrencyConverter.class);
  protected final DecimalFormat formatter = new DecimalFormat("###,###.00");
  
  public final Object convert(Class paramClass, Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramObject instanceof String))
    {
      if (this.log.isDebugEnabled()) {
        this.log.debug("value (" + paramObject + ") instance of String");
      }
      try
      {
        if (StringUtils.isBlank(String.valueOf(paramObject))) {
          return null;
        }
        if (this.log.isDebugEnabled()) {
          this.log.debug("converting '" + paramObject + "' to a decimal");
        }
        Number localNumber = this.formatter.parse(String.valueOf(paramObject));
        return new Double(localNumber.doubleValue());
      }
      catch (ParseException localParseException)
      {
        localParseException.printStackTrace();
      }
    }
    else if ((paramObject instanceof Double))
    {
      if (this.log.isDebugEnabled())
      {
        this.log.debug("value (" + paramObject + ") instance of Double");
        this.log.debug("returning double: " + this.formatter.format(paramObject));
      }
      return this.formatter.format(paramObject);
    }
    throw new ConversionException("Could not convert " + paramObject + " to " + paramClass.getName() + "!");
  }
}
