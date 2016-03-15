package cn.com.agree.eteller.afa.action;

import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.afa.persistence.AfapSubdict;
import cn.com.agree.eteller.afa.persistence.AfapSubdictPK;
import cn.com.agree.eteller.afa.spring.IAfaManager;
import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.CommonType;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class ListSubdict
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="afaManagerTarget")
  private IAfaManager afaManager;
  private AfapSubdict item;
  private List<AfapSubdict> items;
  private Map<String, String> condition = new HashMap();
  private List<String> removeList;
  private List<CommonType> mainItems;
  
  @Action(value="ListSubdict", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/afa/ListSubdict.jsp")})
  public String list()
    throws Exception
  {
    if (this.rollPage.booleanValue())
    {
      this.items = ((List)this.session.getAttribute("list"));
    }
    else
    {
      this.items = Arrays.asList(this.afaManager.getAfapSubdictBymap(this.condition));
      this.session.setAttribute("list", this.items);
    }
    this.items = Pagination.splitPageWithAllRecords(this.items, this.page);
    
    return "success";
  }
  
  @Action(value="CreateSubdict", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/afa/CreateSubdict.jsp")})
  public String addDisplay()
    throws Exception
  {
    return "success";
  }
  
  @Action("CreateSubdict_real")
  public String add()
    throws Exception
  {
    try
    {
      this.afaManager.addEntity(this.item);
      this.dwzResp.successForward("添加成功");
    }
    catch (Exception e)
    {
      logger.error(e);
      this.dwzResp.errorForward("添加失败 ");
    }
    return "dwz";
  }
  
  @Action(value="ModifySubdict", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/afa/ModifySubdict.jsp")})
  public String modifyDisplay()
    throws Exception
  {
    Gson g = new Gson();
    AfapSubdictPK pk = (AfapSubdictPK)g.fromJson(((String)this.removeList.get(0)).replaceAll("_", ","), AfapSubdictPK.class);
    this.item = ((AfapSubdict)this.afaManager.getEntity(AfapSubdict.class, pk));
    return "success";
  }
  
  @Action("ModifySubdict_real")
  public String modify()
    throws Exception
  {
    try
    {
      this.afaManager.updateAfapSubdict(this.item);
      this.dwzResp.successForward("修改成功");
    }
    catch (Exception e)
    {
      this.dwzResp.errorForward("修改失败");
    }
    return "dwz";
  }
  
  @Action("RemoveSubdict")
  public String remove()
    throws Exception
  {
    Gson g = new Gson();
    try
    {
      for (String s : this.removeList)
      {
        AfapSubdictPK pk = (AfapSubdictPK)g.fromJson(s.replaceAll("_", ","), AfapSubdictPK.class);
        AfapSubdict item = (AfapSubdict)this.afaManager.getEntity(AfapSubdict.class, pk);
        this.afaManager.deleteEntity(item);
      }
      this.dwzResp.ajaxSuccessForward("删除成功");
    }
    catch (Exception e)
    {
      this.dwzResp.errorForward("删除失败");
    }
    return "dwz";
  }
  
  public AfapSubdict getItem()
  {
    return this.item;
  }
  
  public void setItem(AfapSubdict item)
  {
    this.item = item;
  }
  
  public List<AfapSubdict> getItems()
  {
    return this.items;
  }
  
  public void setItems(List<AfapSubdict> items)
  {
    this.items = items;
  }
  
  public Map<String, String> getCondition()
  {
    return this.condition;
  }
  
  public void setCondition(Map<String, String> condition)
  {
    this.condition = condition;
  }
  
  public List<CommonType> getMainItems()
  {
    IAfaManager afa = this.afaManager;
    AfapMaindict[] afamdict = (AfapMaindict[])null;
    afamdict = afa.getAllAfapMaindict();
    CommonType[] ct = new CommonType[afamdict.length];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = new CommonType(afamdict[i].getItem(), afamdict[i]
        .getItem() + 
        "-" + afamdict[i].getItemcname());
    }
    return this.mainItems = Arrays.asList(ct);
  }
  
  public void setMainItems(List<CommonType> mainItems)
  {
    this.mainItems = mainItems;
  }
  
  public List<String> getRemoveList()
  {
    return this.removeList;
  }
  
  public void setRemoveList(List<String> removeList)
  {
    this.removeList = removeList;
  }
}
