package gnnt.MEBS.finance.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HomeController
  implements Controller
{
  private final transient Log log = LogFactory.getLog(HomeController.class);
  
  public ModelAndView handleRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.log.debug("entering 'handleRequest' method...");
    return new ModelAndView("home", "message", "welcome");
  }
}
