package gnnt.MEBS.integrated.mgr.progreebar;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("fileProgressAction")
@Scope("request")
public class FileProgressAction
  extends ActionSupport
{
  private static final long serialVersionUID = -5890149846680229181L;
  protected final transient Log logger = LogFactory.getLog(getClass());
  private State state;
  
  public State getState()
  {
    return this.state;
  }
  
  public void setState(State paramState)
  {
    this.state = paramState;
  }
  
  public String execute()
    throws Exception
  {
    HttpSession localHttpSession = ServletActionContext.getRequest().getSession();
    this.state = ((State)localHttpSession.getAttribute("state"));
    if (this.state == null)
    {
      this.logger.debug("action is null");
      this.state = new State();
      this.state.setCurrentItem(0);
    }
    else
    {
      Double localDouble1 = Double.valueOf(Double.parseDouble(this.state.getReadedBytes() + ""));
      Double localDouble2 = Double.valueOf(Double.parseDouble(this.state.getTotalBytes() + ""));
      double d = localDouble1.doubleValue() / localDouble2.doubleValue() * 100.0D;
      this.state.setRate((int)d);
      this.logger.debug("上传百分比：" + this.state.getRate());
    }
    return "success";
  }
}
