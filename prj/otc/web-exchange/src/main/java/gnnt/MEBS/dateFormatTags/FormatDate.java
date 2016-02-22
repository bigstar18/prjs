package gnnt.MEBS.dateFormatTags;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FormatDate
{
  private static final transient Log logger = LogFactory.getLog(FormatDate.class);
  
  public static String formatdate(Object date)
  {
    SimpleDateFormat fmt = null;
    String result = null;
    try
    {
      if (date != null)
      {
        logger.debug("date:" + date.toString());
        logger.debug("date.class:" + date.getClass().getName());
        if ((date instanceof String))
        {
          result = (String)date;
        }
        else
        {
          if ((date instanceof Date))
          {
            logger.debug(date.getClass().getName());
            fmt = new SimpleDateFormat("yyyy-MM-dd");
          }
          if ((date instanceof Timestamp))
          {
            logger.debug(date.getClass().getName());
            fmt = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
          }
          logger.debug("===========" + fmt.format(date));
          result = fmt.format(date);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}
