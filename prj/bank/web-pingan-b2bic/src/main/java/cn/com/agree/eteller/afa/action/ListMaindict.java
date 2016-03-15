package cn.com.agree.eteller.afa.action;

import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.afa.spring.IAfaManager;
import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class ListMaindict
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="afaManagerTarget")
  private IAfaManager afaManager;
  private AfapMaindict item;
  private List<AfapMaindict> items;
  private Map<String, String> condition = new HashMap();
  
  @Action(value="ListMaindict", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/afa/ListMaindict.jsp")})
  public String list()
    throws Exception
  {
    if (this.rollPage.booleanValue())
    {
      this.items = ((List)this.session.getAttribute("list"));
    }
    else
    {
      this.items = Arrays.asList(this.afaManager.getAfapMaindictByMap(this.condition));
      this.session.setAttribute("list", this.items);
    }
    this.items = Pagination.splitPageWithAllRecords(this.items, this.page);
    
    return "success";
  }
  
  @Action("CreateMaindict")
  public String add()
    throws Exception
  {
    try
    {
      this.item.setItem(this.afaManager.getNextItem());
      this.afaManager.addEntity(this.item);
      this.dwzResp.successForward("添加成功");
    }
    catch (Exception e)
    {
      logger.error(e);
      this.dwzResp.errorForward("添加失败");
    }
    return "dwz";
  }
  
  @Action(value="ModifyMaindict", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/afa/ModifyMaindict.jsp")})
  public String modifyDisplay()
    throws Exception
  {
    this.item = ((AfapMaindict)this.afaManager.getEntity(AfapMaindict.class, this.item.getItem()));
    return "success";
  }
  
  @Action("ModifyMaindict_real")
  public String modify()
    throws Exception
  {
    try
    {
      this.afaManager.updateEntity(this.item);
      this.dwzResp.successForward("修改成功");
    }
    catch (Exception e)
    {
      logger.error(e);
      this.dwzResp.errorForward("修改失败");
    }
    return "dwz";
  }
  
  @Action("RemoveMaindict")
  public String remove()
    throws Exception
  {
    try
    {
      for (AfapMaindict m : this.items)
      {
        m = (AfapMaindict)this.afaManager.getEntity(AfapMaindict.class, m.getItem());
        boolean delete = this.afaManager.deleteEntity(m);
        if (!delete)
        {
          this.dwzResp.errorForward("删除失败");
          return "dwz";
        }
      }
      this.dwzResp.ajaxSuccessForward("删除成功");
    }
    catch (Exception e)
    {
      logger.error(e);
      this.dwzResp.errorForward("删除失败");
    }
    return "dwz";
  }
  
  public AfapMaindict getItem()
  {
    return this.item;
  }
  
  public void setItem(AfapMaindict item)
  {
    this.item = item;
  }
  
  public List<AfapMaindict> getItems()
  {
    return this.items;
  }
  
  public void setItems(List<AfapMaindict> items)
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
}
