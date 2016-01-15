package gnnt.MEBS.finance.mgr.beanforajax;

import gnnt.MEBS.common.mgr.service.StandardService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxCheckDemo")
@Scope("request")
public class AjaxCheckDemo
{
  protected String SUCCESS = "success";

  @Autowired
  @Qualifier("com_standardService")
  protected StandardService standardService;
  protected JSONArray jsonValidateReturn;

  public StandardService getService()
  {
    return this.standardService;
  }

  public JSONArray getJsonValidateReturn()
  {
    return this.jsonValidateReturn;
  }

  protected JSONArray getJSONArray(Object[] paramArrayOfObject)
  {
    JSONArray localJSONArray = new JSONArray();
    for (Object localObject : paramArrayOfObject)
      localJSONArray.add(localObject);
    return localJSONArray;
  }

  public JSONArray getJSONArrayList(JSONArray[] paramArrayOfJSONArray)
  {
    JSONArray localJSONArray1 = new JSONArray();
    for (JSONArray localJSONArray2 : paramArrayOfJSONArray)
      localJSONArray1.add(localJSONArray2);
    return localJSONArray1;
  }
}