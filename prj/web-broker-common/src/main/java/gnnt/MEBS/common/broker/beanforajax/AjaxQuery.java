package gnnt.MEBS.common.broker.beanforajax;

import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.common.broker.statictools.Tools;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_ajaxQuery")
@Scope("request")
public class AjaxQuery extends BaseAjax
{
  private static Long diffTime;

  public String getSystemTimeJson()
  {
    if (diffTime == null)
      synchronized (getClass())
      {
        if (diffTime == null)
          try
          {
            Date localDate = getService().getSysDate();
            if (localDate != null)
              diffTime = Long.valueOf(localDate.getTime() - System.currentTimeMillis());
          }
          catch (Exception localException)
          {
            this.logger.debug(Tools.getExceptionTrace(localException));
          }
      }
    long l = 0L;
    if (diffTime != null)
      l = diffTime.longValue();
    String str = Tools.fmtTime(new Date(System.currentTimeMillis() + l));
    this.jsonValidateReturn = createJSONArray(new Object[] { str });
    return "success";
  }
}