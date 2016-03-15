package cn.com.agree.eteller.usermanager.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import java.util.List;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;

public class ListApplication
  extends GenericAction
{
  private static final long serialVersionUID = 7928748389576542994L;
  @Resource(name="userManagerTarget")
  private IUserManager userMg;
  private Appinfo app;
  private List<Appinfo> apps;
  
  @Action(value="ListApplication", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ListApplication.jsp")})
  public String list()
    throws Exception
  {
    this.apps = this.userMg.getAppinfosByCondition(this.app, this.page);
    return "success";
  }
  
  @Action("CreateApplication")
  public String add()
    throws Exception
  {
    try
    {
      this.app.setAppid(String.valueOf(this.userMg.getMaxAppid()));
      this.userMg.addAppinfo(this.app);
      
      this.dwzResp.successForward("添加应用成功");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.dwzResp.exceptionForward(e);
    }
    return "dwz";
  }
  
  @Action(value="ModifyInfoApplication", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ModifyApplication.jsp")})
  public String info()
    throws Exception
  {
    this.app = this.userMg.getAppinfo(this.app.getAppid());
    return "success";
  }
  
  @Action("RemoveApplication")
  public String remove()
    throws Exception
  {
    for (Appinfo app : this.apps) {
      this.userMg.deleteAppinfo(app);
    }
    this.dwzResp.successForward("删除应用成功");
    this.dwzResp.setCallbackType(null);
    return "dwz";
  }
  
  @Action("ModifyApplication")
  public String modify()
    throws Exception
  {
    this.userMg.updateAppinfo(this.app);
    this.dwzResp.successForward("修改应用成功");
    return "dwz";
  }
  
  public void setApps(List<Appinfo> apps)
  {
    this.apps = apps;
  }
  
  public List<Appinfo> getApps()
  {
    return this.apps;
  }
  
  public Appinfo getApp()
  {
    return this.app;
  }
  
  public void setApp(Appinfo app)
  {
    this.app = app;
  }
}
