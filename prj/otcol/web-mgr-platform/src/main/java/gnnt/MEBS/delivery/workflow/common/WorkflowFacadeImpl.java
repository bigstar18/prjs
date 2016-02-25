package gnnt.MEBS.delivery.workflow.common;

import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.ProcessContext;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorkflowFacadeImpl
  implements WorkflowFacade
{
  public final transient Log logger = LogFactory.getLog(WorkflowFacadeImpl.class);
  private Map<String, ProcessContext> workflowMap;
  private Translate translate;
  
  public void setTranslate(Translate paramTranslate)
  {
    this.translate = paramTranslate;
  }
  
  public void setWorkflowMap(Map<String, ProcessContext> paramMap)
  {
    this.workflowMap = paramMap;
  }
  
  public Map dealWorkflow(OriginalModel paramOriginalModel, String paramString)
  {
    HashMap localHashMap = new HashMap();
    ProcessContext localProcessContext = (ProcessContext)this.workflowMap.get(paramString);
    int i = -1;
    try
    {
      i = localProcessContext.doActivities(paramOriginalModel);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -100;
    }
    localHashMap.put("result", Integer.valueOf(i));
    String str = this.translate.transResult(i + "");
    localHashMap.put("transResult", str);
    return localHashMap;
  }
  
  public Map getLastMsg(int paramInt1, int paramInt2, String paramString)
  {
    ProcessContext localProcessContext = (ProcessContext)this.workflowMap.get(paramString);
    Map localMap1 = localProcessContext.getNewest(paramInt1, paramInt2);
    this.logger.debug("map:::" + localMap1);
    int i = ((Integer)localMap1.get("result")).intValue();
    if (i == 1)
    {
      List localList = (List)localMap1.get("handleNameList");
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Map localMap2 = (Map)localIterator.next();
        String str = this.translate.transHandle((String)localMap2.get("value"));
        localMap2.put("name", str);
        boolean bool2 = ((Boolean)localMap2.get("isWriteNote")).booleanValue();
        localMap2.put("isWriteNote", Boolean.valueOf(bool2));
        localArrayList.add(localMap2);
      }
      localMap1.put("transHandleList", localArrayList);
      boolean bool1 = ((Boolean)localMap1.get("isExistNote")).booleanValue();
      localMap1.put("isNote", Boolean.valueOf(bool1));
    }
    return localMap1;
  }
  
  public String getLogContent(String paramString)
  {
    return this.translate.transLogContent(paramString);
  }
  
  public List<Integer> getFinalStatus(String paramString)
  {
    ProcessContext localProcessContext = (ProcessContext)this.workflowMap.get(paramString);
    return localProcessContext.getFinalStatus();
  }
}
