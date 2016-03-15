package cn.com.agree.eteller.usermanager.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfoPK;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;

public class ListSubappinfo
  extends GenericAction
{
  private static final long serialVersionUID = -1738325305816455907L;
  @Resource(name="userManagerTarget")
  private IUserManager userMg;
  private EtellerSubappinfo subApp;
  private List<EtellerSubappinfo> subApps;
  private List<String> subappId;
  private List<Appinfo> apps;
  
  @Action(value="ListSubappinfo", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ListSubappinfo.jsp")})
  public String list()
    throws Exception
  {
    this.apps = Arrays.asList(this.userMg.getAllAppinfo());
    if ((this.subApp == null) || ((this.subApp.getComp_id() != null) && (ComFunction.isEmpty(this.subApp.getComp_id().getAppid())) && (ComFunction.isEmpty(this.subApp.getAppname()))))
    {
      this.subApp = new EtellerSubappinfo(new EtellerSubappinfoPK("", ""), "");
      this.subApps = this.userMg.getAllEntity(EtellerSubappinfo.class, this.page);
    }
    else
    {
      this.subApps = this.userMg.getBySubAppCondition(this.subApp, this.page);
    }
    for (EtellerSubappinfo sa : this.subApps) {
      sa.setApp(this.userMg.getAppinfo(sa.getComp_id().getAppid()));
    }
    return "success";
  }
  
  @Action(value="CreateInfoSubappinfo", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/CreateSubappinfo.jsp")})
  public String addInfo()
    throws Exception
  {
    this.apps = Arrays.asList(this.userMg.getAllAppinfo());
    this.subApp = new EtellerSubappinfo();
    this.subApp.setComp_id(new EtellerSubappinfoPK(null, String.valueOf(this.userMg.getMaxSubappid())));
    return "success";
  }
  
  @Action(value="ModifyInfoSubappinfo", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ModifySubappinfo.jsp")})
  public String modifyInfo()
    throws Exception
  {
    this.apps = Arrays.asList(this.userMg.getAllAppinfo());
    this.subApp = this.userMg.getSubAppinfo(this.subApp.getComp_id().getSubappid());
    this.subApp.setApp(this.userMg.getAppinfo(this.subApp.getComp_id().getAppid()));
    return "success";
  }
  
  @Action(value="CreateSubappinfo", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String add()
    throws Exception
  {
    try
    {
      if (this.userMg.getSubAppinfo(this.subApp.getComp_id().getSubappid()) != null)
      {
        addFieldError("id_err", "该子应用ID已存在");
        return "input";
      }
      this.userMg.addSubAppinfo(this.subApp);
      
      this.dwzResp.successForward("添加子应用成功");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.dwzResp.exceptionForward(e);
    }
    return "success";
  }
  
  @Action(value="ModiySubappinfo", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String modify()
    throws Exception
  {
    try
    {
      this.userMg.deleteSubAppinfo(this.userMg.getSubAppinfo(this.subApp.getComp_id().getSubappid()));
      this.userMg.addSubAppinfo(this.subApp);
      this.dwzResp.successForward("修改子应用成功");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.dwzResp.exceptionForward(e);
    }
    return "success";
  }
  
  @Action(value="RemoveSubappinfo", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String remove()
    throws Exception
  {
    try
    {
      for (String id : this.subappId)
      {
        EtellerSubappinfo app = new EtellerSubappinfo();
        EtellerSubappinfoPK pk = new EtellerSubappinfoPK();
        pk.setSubappid(id);
        app.setComp_id(pk);
        this.userMg.deleteSubAppinfo(app);
      }
      this.dwzResp.ajaxSuccessForward("删除子应用成功");
    }
    catch (Exception e)
    {
      this.dwzResp.ajaxErrorForward(e.getMessage());
    }
    return "success";
  }
  
  public EtellerSubappinfo getSubApp()
  {
    return this.subApp;
  }
  
  public void setSubApp(EtellerSubappinfo subApp)
  {
    this.subApp = subApp;
  }
  
  public List<EtellerSubappinfo> getSubApps()
  {
    return this.subApps;
  }
  
  public void setSubApps(List<EtellerSubappinfo> subApps)
  {
    this.subApps = subApps;
  }
  
  public void setApps(List<Appinfo> apps)
  {
    this.apps = apps;
  }
  
  public List<Appinfo> getApps()
  {
    return this.apps;
  }
  
  public void setSubappId(List<String> subappId)
  {
    this.subappId = subappId;
  }
  
  public List<String> getSubappId()
  {
    return this.subappId;
  }
}
