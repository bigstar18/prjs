package cn.com.agree.eteller.usermanager.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.CommonType;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfoPK;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;

public class ListFunction
  extends GenericAction
{
  private static final long serialVersionUID = -7786444015311967576L;
  @Resource(name="userManagerTarget")
  private IUserManager userMg;
  private List<Funclist> funcList;
  private List<Appinfo> apps;
  private List<EtellerSubappinfo> subApps;
  private Funclist func;
  private List<CommonType> appidList;
  private List<CommonType> subappidList;
  
  @Action(value="ListFunction", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ListFunction.jsp")})
  public String list()
    throws Exception
  {
    Map<String, String> map = new HashMap();
    if (this.func != null)
    {
      if (StringUtils.isNotBlank(this.func.getAppid())) {
        map.put("appid", this.func.getAppid());
      }
      if (StringUtils.isNotBlank(this.func.getSubappid())) {
        map.put("subappid", this.func.getSubappid());
      }
      if (StringUtils.isNotBlank(this.func.getFuncName())) {
        map.put("funcName", this.func.getFuncName().trim());
      }
      if (StringUtils.isNotBlank(this.func.getFuncAddress())) {
        map.put("funcAddress", this.func.getFuncAddress().trim());
      }
    }
    this.funcList = this.userMg.getFunclistByMap2(map, this.page);
    for (int i = 0; i < this.funcList.size(); i++)
    {
      Funclist f = (Funclist)this.funcList.get(i);
      f.setApp(this.userMg.getAppinfo(f.getAppid()));
      f.setSubApp(this.userMg.getSubAppinfo(f.getSubappid()));
      this.funcList.set(i, f);
    }
    initSelectList();
    
    return "success";
  }
  
  @Action(value="QuerySelectList_ListFunction", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"excludeProperties", "funcList, apps, subApps, func"})})
  public String querySelectList()
    throws Exception
  {
    this.subappidList = getSubappidCommonTypes();
    return "success";
  }
  
  @Action(value="CreateInfoFunction", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/CreateFunction.jsp")})
  public String addInfo()
    throws Exception
  {
    initSelectList();
    return "success";
  }
  
  @Action(value="ModifyInfoFunction", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ModifyFunction.jsp")})
  public String modifyInfo()
    throws Exception
  {
    Funclist tempFunc = 
      tempFunc = (Funclist)this.userMg.getEntityByZHPK(Funclist.class, 
      Long.valueOf(this.func.getFuncId().longValue()));
    if (tempFunc != null) {
      this.func = tempFunc;
    }
    initSelectList();
    return "success";
  }
  
  @Action(value="CreateFunction", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String add()
    throws Exception
  {
    try
    {
      if (ComFunction.isEmpty(this.func.getSubappid())) {
        this.func.setSubappid("00000");
      }
      this.func.setFuncId(getMaxId());
      this.userMg.addFunction(this.func);
      
      this.dwzResp.successForward("添加功能成功");
    }
    catch (Exception e)
    {
      this.dwzResp.exceptionForward(e);
    }
    return "success";
  }
  
  @Action(value="ModifyFunction", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String modify()
    throws Exception
  {
    try
    {
      if (ComFunction.isEmpty(this.func.getSubappid())) {
        this.func.setSubappid("00000");
      }
      this.userMg.updateFunction(this.func);
      
      this.dwzResp.successForward("修改功能成功");
    }
    catch (Exception e)
    {
      this.dwzResp.exceptionForward(e);
    }
    return "success";
  }
  
  @Action(value="RemoveFunction", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String remove()
    throws Exception
  {
    try
    {
      for (Funclist f : this.funcList) {
        this.userMg.deleteFunction(f.getFuncId().longValue());
      }
      this.dwzResp.ajaxSuccessForward("删除功能成功");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.dwzResp.ajaxErrorForward(e.getMessage());
    }
    return "success";
  }
  
  public String selectListInfo()
    throws Exception
  {
    this.subApps = Arrays.asList(this.userMg.getSubAppinfoByappid(this.req.getParameter("appid")));
    return "success";
  }
  
  private void initSelectList()
  {
    this.appidList = getAppidCommonTypes();
    this.subappidList = getSubappidCommonTypes();
  }
  
  private Long getMaxId()
  {
    Object[][] result = this.userMg.getSearchResultBySQL("SELECT max(func_id) FROM eteller_funclist");
    if ((result == null) || (result.length == 0)) {
      return Long.valueOf(1L);
    }
    return Long.valueOf(Long.parseLong((String)result[0][0]) + 1L);
  }
  
  private List<CommonType> getAppidCommonTypes()
  {
    Appinfo[] appinfos = this.userMg.getAllAppinfo();
    CommonType[] ct = new CommonType[appinfos.length + 1];
    ct[0] = new CommonType("", "全部");
    for (int i = 1; i < ct.length; i++) {
      ct[i] = new CommonType(appinfos[(i - 1)].getAppid(), appinfos[(i - 1)].getAppname());
    }
    return Arrays.asList(ct);
  }
  
  private List<CommonType> getSubappidCommonTypes()
  {
    int ctLength = 1;
    EtellerSubappinfo[] subappinfos = (EtellerSubappinfo[])null;
    if ((this.func != null) && (StringUtils.isNotBlank(this.func.getAppid()))) {
      subappinfos = this.userMg.getSubAppinfoByappid(this.func.getAppid());
    }
    if (subappinfos != null) {
      ctLength += subappinfos.length;
    }
    CommonType[] ct = new CommonType[ctLength];
    ct[0] = new CommonType("", "全部");
    for (int i = 1; i < ct.length; i++) {
      ct[i] = new CommonType(subappinfos[(i - 1)].getComp_id().getSubappid(), 
        subappinfos[(i - 1)].getAppname());
    }
    return Arrays.asList(ct);
  }
  
  public List<Funclist> getFuncList()
  {
    return this.funcList;
  }
  
  public void setFuncList(List<Funclist> funcList)
  {
    this.funcList = funcList;
  }
  
  public List<Appinfo> getApps()
  {
    return this.apps;
  }
  
  public void setApps(List<Appinfo> apps)
  {
    this.apps = apps;
  }
  
  public List<EtellerSubappinfo> getSubApps()
  {
    return this.subApps;
  }
  
  public void setSubApps(List<EtellerSubappinfo> subApps)
  {
    this.subApps = subApps;
  }
  
  public void setFunc(Funclist func)
  {
    this.func = func;
  }
  
  public Funclist getFunc()
  {
    return this.func;
  }
  
  public List<CommonType> getAppidList()
  {
    return this.appidList;
  }
  
  public void setAppidList(List<CommonType> appidList)
  {
    this.appidList = appidList;
  }
  
  public List<CommonType> getSubappidList()
  {
    return this.subappidList;
  }
  
  public void setSubappidList(List<CommonType> subappidList)
  {
    this.subappidList = subappidList;
  }
}
