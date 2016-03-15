package cn.com.agree.eteller.generic.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage("eteller-default")
@Namespace("/")
@Action(value="linkPage", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/${path}")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef("loginInterceptor"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
public class LinkPageAction
  extends GenericAction
{
  private static final long serialVersionUID = 3427550579606971526L;
  private String path;
  
  public String getPath()
  {
    return this.path;
  }
  
  public void setPath(String path)
  {
    this.path = path;
  }
}
