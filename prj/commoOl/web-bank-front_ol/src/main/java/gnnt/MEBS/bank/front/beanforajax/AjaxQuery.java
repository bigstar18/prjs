package gnnt.MEBS.bank.front.beanforajax;

import gnnt.MEBS.common.front.beanforajax.BaseAjax;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxQuery")
@Scope("request")
public class AjaxQuery
  extends BaseAjax
{
  public String getProCityCountyJson()
  {
    this.logger.debug("查询省市县信息");
    










    return "success";
  }
  
  public String getBusinessManJson()
  {
    return "success";
  }
}
