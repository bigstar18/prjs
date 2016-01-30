package gnnt.MEBS.integrated.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.beanforajax.BaseAjax;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxCheck")
@Scope("request")
public class AjaxCheck
  extends BaseAjax
{
  private JSONArray genJSON(ArrayList<AjaxValidationFormResponse> errors)
  {
    JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < errors.size(); i++)
    {
      AjaxValidationFormResponse err = (AjaxValidationFormResponse)errors.get(i);
      jsonArray.add(err.toJSONArray());
    }
    return jsonArray;
  }
  
  private boolean existUserByUserId(String userId)
  {
    boolean exit = false;
    if (!"".equals(userId))
    {
      PageRequest<String> pageRequest = new PageRequest(
        " and primary.userId='" + userId + "'");
      Page<StandardModel> page = getService().getPage(pageRequest, 
        new User());
      if ((page.getResult() != null) && (page.getResult().size() > 0)) {
        exit = true;
      }
    }
    return exit;
  }
  
  public String checkKey()
  {
    HttpServletRequest request = getRequest();
    this.logger.debug("-----------密钥验证-------------");
    
    String fieldId = request.getParameter("fieldId");
    String fieldValue = request.getParameter("fieldValue");
    String stockId = request.getParameter("stockId");
    this.logger.debug("----------------" + fieldValue);
    this.logger.debug("----------------" + stockId);
    String sql = "select t.key from bi_outstock t where t.status=0 and t.stockid=" + stockId;
    List<Map<Object, Object>> keyList = getService().getDao().queryBySql(sql);
    System.out.println(((Map)keyList.get(0)).get("KEY"));
    if ((keyList != null) && (keyList.size() > 0))
    {
      boolean flag = false;
      if (fieldValue.equals(((Map)keyList.get(0)).get("KEY"))) {
        flag = true;
      }
      AjaxValidationFieldResponse result = new AjaxValidationFieldResponse(
        fieldId, Boolean.valueOf(flag));
      this.jsonValidateReturn = result.toJSONArray();
      return "success";
    }
    return "success";
  }
  
  public String checkUserByUserId()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    
    String fieldId = request.getParameter("fieldId");
    String fieldValue = request.getParameter("fieldValue");
    AjaxValidationFieldResponse result = new AjaxValidationFieldResponse(
      fieldId, Boolean.valueOf(!existUserByUserId(fieldValue)));
    
    this.jsonValidateReturn = result.toJSONArray();
    
    return "success";
  }
  
  public String checkUserForm()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    
    ArrayList<AjaxValidationFormResponse> errors = new ArrayList();
    
    String userId = request.getParameter("entity.userId");
    if (existUserByUserId(userId)) {
      errors.add(new AjaxValidationFormResponse("userId", 
        Boolean.valueOf(false), "输入的用户代码已存在"));
    }
    if (errors.size() != 0)
    {
      this.jsonValidateReturn = genJSON(errors);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean exitRoleName(String name, String oldName)
  {
    boolean exit = false;
    if (!"".equals(name))
    {
      if ((oldName != null) && (oldName.trim().equals(name))) {
        return false;
      }
      PageRequest<String> pageRequest = new PageRequest(
        " and primary.name= '" + name + "'");
      Page<StandardModel> page = getService().getPage(pageRequest, 
        new Role());
      if ((page.getResult() != null) && (page.getResult().size() > 0)) {
        exit = true;
      }
    }
    return exit;
  }
  
  public String checkRoleByName()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    
    String fieldId = request.getParameter("fieldId");
    String fieldValue = request.getParameter("fieldValue");
    String oldName = request.getParameter("oldName");
    
    AjaxValidationFieldResponse result = new AjaxValidationFieldResponse(
      fieldId, Boolean.valueOf(!exitRoleName(fieldValue, oldName)));
    
    this.jsonValidateReturn = result.toJSONArray();
    
    return "success";
  }
  
  public String checkRoleForm()
  {
    ActionContext ac = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest)ac
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    
    ArrayList<AjaxValidationFormResponse> errors = new ArrayList();
    
    String name = request.getParameter("entity.name");
    String oldName = request.getParameter("oldName");
    if (exitRoleName(name, oldName)) {
      errors.add(new AjaxValidationFormResponse("name", Boolean.valueOf(false), 
        "输入的角色名称已存在"));
    }
    if (errors.size() != 0)
    {
      this.jsonValidateReturn = genJSON(errors);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existFirmFirmId(String firmId, String oldFirmId)
  {
    boolean exit = false;
    if (!"".equals(firmId)) {
      if (firmId.equals(oldFirmId))
      {
        exit = false;
      }
      else
      {
        PageRequest<String> pageRequest = new PageRequest(
          " and primary.firmId='" + firmId + "' and primary.status != 'E' ");
        Page<StandardModel> page = getService().getPage(pageRequest, 
          new MFirm());
        if ((page.getResult() != null) && (page.getResult().size() > 0)) {
          exit = true;
        }
      }
    }
    return exit;
  }
  
  public String checkStockByFirmId()
  {
    HttpServletRequest request = getRequest();
    
    String fieldId = request.getParameter("fieldId");
    String fieldValue = request.getParameter("fieldValue");
    String oldFirmId = request.getParameter("oldFirmId");
    AjaxValidationFieldResponse result = new AjaxValidationFieldResponse(
      fieldId, Boolean.valueOf(existFirmFirmId(fieldValue, oldFirmId)));
    
    this.jsonValidateReturn = result.toJSONArray();
    
    return "success";
  }
  
  public String checkStockFirmIdForm()
  {
    HttpServletRequest request = getRequest();
    
    ArrayList<AjaxValidationFormResponse> errors = new ArrayList();
    
    String firmId = request.getParameter("entity.ownerFirm");
    String oldFirmId = request.getParameter("oldFirmId");
    boolean flag = existFirmFirmId(firmId, oldFirmId);
    if (!flag) {
      errors.add(new AjaxValidationFormResponse("ownerFirm", 
        Boolean.valueOf(flag), "此交易商不存在或已注销"));
    }
    if (errors.size() != 0)
    {
      this.jsonValidateReturn = genJSON(errors);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public class AjaxValidationFieldResponse
  {
    private String id;
    private Boolean status;
    
    public AjaxValidationFieldResponse(String paramString, Boolean paramBoolean)
    {
      this.id = paramString;
      this.status = paramBoolean;
    }
    
    public AjaxValidationFieldResponse(String paramString1, boolean paramBoolean, String paramString2) {}
    
    public JSONArray toJSONArray()
    {
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.add(this.id);
      localJSONArray.add(this.status);
      return localJSONArray;
    }
  }
  
  public class AjaxValidationFormResponse
  {
    private String id;
    private Boolean status;
    private String error;
    
    public AjaxValidationFormResponse(String paramString1, Boolean paramBoolean, String paramString2)
    {
      this.id = paramString1;
      this.status = paramBoolean;
      this.error = paramString2;
    }
    
    public JSONArray toJSONArray()
    {
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.add(this.id);
      localJSONArray.add(this.status);
      localJSONArray.add(this.error);
      return localJSONArray;
    }
  }
}
