package cn.com.agree.eteller.usermanager.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.CommonType;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.utils.TellerUtil;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.struts2.convention.annotation.Action;

public class ListDepartment
  extends GenericAction
{
  private static final long serialVersionUID = -5451772726024541252L;
  @Resource(name="userManagerTarget")
  private IUserManager userMg;
  private Department dept;
  private List<Department> deptList;
  private List<CommonType> deptTypeList;
  private List<Department> highLevelDeptList;
  private List<Rolelist> toSelectRoleList;
  private List<Rolelist> selectedRoleList;
  private Department superiorDept;
  
  @Action(value="ListDepartment", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ListDepartment.jsp")})
  public String list()
    throws Exception
  {
    LoginUser user = (LoginUser)this.session.getAttribute("user");
    Map condition = new HashMap();
    if (this.dept != null)
    {
      condition.put("id", this.dept.getId());
      condition.put("name", this.dept.getName());
    }
    if ("root".equals(user.getUserId()))
    {
      this.deptList = this.userMg.getDeptListByMap(condition, this.page);
    }
    else
    {
      if (this.rollPage.booleanValue())
      {
        this.deptList = ((List)this.session.getAttribute("list"));
      }
      else
      {
        this.deptList = this.userMg.getSubDepartmentList(user.getDept().getId(), condition);
        this.session.setAttribute("list", this.deptList);
      }
      this.page.setAllRecords(Integer.valueOf(this.deptList.size()));
      int begin = this.page.getFirstRecord().intValue();
      int end = this.page.getCurPage().intValue() * this.page.getPerPageRecords().intValue();
      this.deptList = this.deptList.subList(begin, end > this.deptList.size() ? this.deptList.size() : end);
    }
    return "success";
  }
  
  @Action(value="CreateInfoDepartment", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/CreateDepartment.jsp")})
  public String addInfo()
    throws Exception
  {
    CommonType[] ct = new CommonType[4];
    ct[0] = new CommonType("0", "总行");
    ct[1] = new CommonType("1", "分行");
    ct[2] = new CommonType("2", "支行");
    ct[3] = new CommonType("3", "网点");
    this.deptTypeList = Arrays.asList(ct);
    if ((this.dept != null) && (this.dept.getId() != null) && (!"".equals(this.dept.getId())))
    {
      this.highLevelDeptList = this.userMg.getSubDepartmentWithCurrentList_final(this.dept.getId());
      this.toSelectRoleList = this.userMg.getRoleList(this.dept.getId());
    }
    else
    {
      LoginUser user = (LoginUser)this.session.getAttribute("user");
      if ("root".equals(user.getUserId()))
      {
        this.highLevelDeptList = this.userMg.getDep_finalFlag();
        this.toSelectRoleList = Arrays.asList(this.userMg.getAllRole());
      }
      else
      {
        this.highLevelDeptList = this.userMg.getSubDepartmentWithCurrentList_final(user.getDept().getId());
        this.toSelectRoleList = this.userMg.getRoleList(user.getDept().getId());
      }
    }
    return "success";
  }
  
  @Action(value="ModifyInfoDepartment", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/usermanager/ModifyDepartment.jsp")})
  public String modifyInfo()
    throws Exception
  {
    LoginUser user = (LoginUser)this.session.getAttribute("user");
    Department tempDept = null;
    if (this.dept.getId() != null) {
      tempDept = this.userMg.getDepartment(this.dept.getId());
    }
    if ("root".equals(user.getUserId())) {
      this.toSelectRoleList = Arrays.asList(this.userMg.getAllRole());
    } else if ((this.dept.getId() != null) && (!"".equals(this.dept.getId()))) {
      this.toSelectRoleList = this.userMg.getRoleList(tempDept.getSuperiorDepartmentId());
    }
    if ((!"".equals(this.dept.getId())) && (tempDept != null))
    {
      String selectedId = this.dept.getSuperiorDepartmentId();
      this.dept = tempDept;
      if (selectedId != null) {
        this.dept.setSuperiorDepartmentId(selectedId);
      }
      this.selectedRoleList = this.userMg.getRoleListInDepartment(this.dept.getId());
    }
    CommonType[] ct = new CommonType[4];
    ct[0] = new CommonType("0", "总行");
    ct[1] = new CommonType("1", "分行");
    ct[2] = new CommonType("2", "支行");
    ct[3] = new CommonType("3", "网点");
    this.deptTypeList = Arrays.asList(ct);
    if ("root".equals(user.getUserId())) {
      this.highLevelDeptList = this.userMg.getDep_finalFlag();
    } else {
      this.highLevelDeptList = this.userMg.getSubDepartmentWithCurrentList_final(user.getDept().getId());
    }
    return "success";
  }
  
  @Action(value="CreateDepartment", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String add()
    throws Exception
  {
    if (this.userMg.getDepartment(this.dept.getId()) != null)
    {
      this.dwzResp.errorForward("此机构号已存在");
      return "success";
    }
    try
    {
      this.userMg.checkCycleSuperior(this.dept);
      this.dept.setRoles(new HashSet(this.selectedRoleList));
      this.userMg.addDepartment(this.dept);
      this.dwzResp.successForward("添加机构成功");
    }
    catch (ServiceException e)
    {
      this.dwzResp.exceptionForward(e);
    }
    return "success";
  }
  
  @Action(value="ModifyDepartment", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String modify()
    throws Exception
  {
    try
    {
      this.userMg.checkCycleSuperior(this.dept);
      Department[] depold = this.userMg.getDepartmentByDepId(this.dept.getId());
      String supdepNo = depold[0].getSuperiorDepartmentId();
      if (!this.dept.getSuperiorDepartmentId().equals(supdepNo))
      {
        if (this.userMg.isHaveSubDep(this.dept.getId()))
        {
          this.dwzResp.errorForward("该机构尚有下属机构，上级机构修改失败！");
          return "success";
        }
        if (this.userMg.isHaveTeller(this.dept.getId()))
        {
          this.dwzResp.errorForward("该机构尚有员工，上级机构修改失败！");
          return "success";
        }
      }
      if ((this.userMg.isHaveSubDep(this.dept.getId())) && (this.dept.getFinalFlag() == '1'))
      {
        this.dwzResp.errorForward("该机构尚有下属机构,是否能有下属机构修改有误!");
        return "success";
      }
      this.userMg.checkRoleUserList(this.dept.getId(), this.toSelectRoleList, 
        this.selectedRoleList);
      this.dwzResp.successForward("修改机构成功");
    }
    catch (ServiceException e)
    {
      this.dwzResp.exceptionForward(e);
      return "success";
    }
    catch (Exception e)
    {
      this.dwzResp.exceptionForward(e);
      e.printStackTrace();
      return "success";
    }
    this.dept.setRoles(new HashSet(this.selectedRoleList));
    this.userMg.updateDepartment(this.dept);
    return "success";
  }
  
  @Action(value="RemoveDepartment", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
  public String remove()
    throws Exception
  {
    for (Department d : this.deptList) {
      this.userMg.deleteDepartment(d);
    }
    this.dwzResp.ajaxSuccessForward("删除机构成功");
    return "success";
  }
  
  @Action(value="SelectListInfoDepartment", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"excludeProperties", "dept,deptList,deptTypeList,highLevelDeptList,selectedRoleList"})})
  public String selectListInfo()
    throws Exception
  {
    if ((this.dept != null) && (this.dept.getSuperiorDepartmentId() != null) && (!"".equals(this.dept.getSuperiorDepartmentId()))) {
      this.toSelectRoleList = this.userMg.getRoleList(this.dept.getSuperiorDepartmentId());
    } else if ("root".equals(TellerUtil.getLoginTeller().getUserId())) {
      this.toSelectRoleList = Arrays.asList(this.userMg.getAllRole());
    } else {
      this.toSelectRoleList = new ArrayList();
    }
    if (!ComFunction.isEmpty(this.toSelectRoleList)) {
      for (Rolelist r : this.toSelectRoleList)
      {
        r.setDepartments(null);
        r.setFunctions(null);
      }
    }
    return "success";
  }
  
  private void commonValidate()
  {
    LoginUser user = (LoginUser)this.session.getAttribute("user");
    if (this.dept.getId() == null) {
      addFieldError("id_err", "机构号不能为空");
    }
    if (this.dept.getId().length() != 6) {
      addFieldError("id_err", "机构号必须为6位");
    }
    if (this.dept.getName() == null) {
      addFieldError("name_err", "机构名称不能为空");
    }
    if ((this.dept.getSuperiorDepartmentId().equals("")) && (!user.getUserId().equals("root"))) {
      addFieldError("superDepid", "上级机构不能为空");
    }
  }
  
  public String superiorDeptName(String deptId)
  {
    if (ComFunction.isEmpty(deptId)) {
      return "";
    }
    if ((this.superiorDept == null) || (!deptId.equals(this.superiorDept.getId())))
    {
      this.superiorDept = this.userMg.getDepartment(deptId);
      return this.superiorDept.getName();
    }
    return this.superiorDept.getName();
  }
  
  public Department getDept()
  {
    return this.dept;
  }
  
  public void setDept(Department dept)
  {
    this.dept = dept;
  }
  
  public List<Department> getDeptList()
  {
    return this.deptList;
  }
  
  public void setDeptList(List<Department> deptList)
  {
    this.deptList = deptList;
  }
  
  public List<CommonType> getDeptTypeList()
  {
    return this.deptTypeList;
  }
  
  public void setDeptTypeList(List<CommonType> deptTypeList)
  {
    this.deptTypeList = deptTypeList;
  }
  
  public List<Department> getHighLevelDeptList()
  {
    return this.highLevelDeptList;
  }
  
  public void setHighLevelDeptList(List<Department> highLevelDeptList)
  {
    this.highLevelDeptList = highLevelDeptList;
  }
  
  public List<Rolelist> getToSelectRoleList()
  {
    return this.toSelectRoleList;
  }
  
  public void setToSelectRoleList(List<Rolelist> toSelectRoleList)
  {
    this.toSelectRoleList = toSelectRoleList;
  }
  
  public List<Rolelist> getSelectedRoleList()
  {
    return this.selectedRoleList;
  }
  
  public void setSelectedRoleList(List<Rolelist> selectedRoleList)
  {
    this.selectedRoleList = selectedRoleList;
  }
  
  public Department getSuperiorDept()
  {
    return this.superiorDept;
  }
  
  public void setSuperiorDept(Department superiorDept)
  {
    this.superiorDept = superiorDept;
  }
}
