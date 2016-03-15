package cn.com.agree.eteller.generic.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NatpGenericAction
  extends GenericAction
{
  private static final long serialVersionUID = 8210902667581802474L;
  protected List<Map<String, String>> list;
  protected List<String> removeList;
  protected Map<String, String> condition;
  
  public NatpGenericAction()
  {
    this.list = new ArrayList();
    this.condition = new HashMap();
    this.removeList = new ArrayList();
    this.firstQueryKey = new HashMap();
    this.lastQueryKey = new HashMap();
  }
  
  public NatpGenericAction(String connect, String templateCode)
  {
    this();
    this.condition.put("connect", connect);
    this.condition.put("templateCode", templateCode);
  }
  
  protected String actionResult = "success";
  protected int oldPage;
  protected Map<String, String> firstQueryKey;
  protected Map<String, String> lastQueryKey;
  
  public List<Map<String, String>> getList()
  {
    return this.list;
  }
  
  public void setList(List<Map<String, String>> list)
  {
    this.list = list;
  }
  
  public List<String> getRemoveList()
  {
    return this.removeList;
  }
  
  public void setRemoveList(List<String> removeList)
  {
    this.removeList = removeList;
  }
  
  public Map<String, String> getCondition()
  {
    return this.condition;
  }
  
  public void setCondition(Map<String, String> condition)
  {
    this.condition = condition;
  }
  
  public String getActionResult()
  {
    return this.actionResult;
  }
  
  public void setActionResult(String actionResult)
  {
    this.actionResult = actionResult;
  }
  
  public int getOldPage()
  {
    return this.oldPage;
  }
  
  public void setOldPage(int oldPage)
  {
    this.oldPage = oldPage;
  }
  
  public Map<String, String> getFirstQueryKey()
  {
    return this.firstQueryKey;
  }
  
  public void setFirstQueryKey(Map<String, String> firstQueryKey)
  {
    this.firstQueryKey = firstQueryKey;
  }
  
  public Map<String, String> getLastQueryKey()
  {
    return this.lastQueryKey;
  }
  
  public void setLastQueryKey(Map<String, String> lastQueryKey)
  {
    this.lastQueryKey = lastQueryKey;
  }
}
