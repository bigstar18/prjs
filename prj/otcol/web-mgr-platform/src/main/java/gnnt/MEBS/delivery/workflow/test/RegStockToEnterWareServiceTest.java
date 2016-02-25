package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegStockToEnterWareServiceTest
  extends AbstractTest
{
  public void testNewStatus1()
  {
    Map localMap = this.workflow.getLastMsg(0, 9, "regStockToEnterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
  }
  
  public void testNewStatus2()
  {
    Map localMap1 = this.workflow.getLastMsg(1, 0, "regStockToEnterWare");
    int i = ((Integer)localMap1.get("result")).intValue();
    assertEquals(1, i);
    List localList = (List)localMap1.get("transHandleList");
    assertEquals(2, localList.size());
    Map localMap2 = (Map)localList.get(0);
    String str1 = (String)localMap2.get("value");
    assertEquals(str1, "approve");
    String str2 = (String)localMap2.get("name");
    assertEquals(str2, "审核");
    localMap2 = (Map)localList.get(1);
    str1 = (String)localMap2.get("value");
    assertEquals(str1, "overrule");
    str2 = (String)localMap2.get("name");
    assertEquals(str2, "驳回");
  }
  
  public void testFinalStatus()
  {
    List localList = this.workflow.getFinalStatus("regStockToEnterWare");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(2));
    assertEquals(localArrayList, localList);
  }
  
  public void testNote()
  {
    Map localMap = this.workflow.getLastMsg(0, 9, "regStockToEnterWare");
    System.out.println("size= " + localMap.isEmpty());
    boolean bool = ((Boolean)localMap.get("isNote")).booleanValue();
    assertEquals(false, bool);
  }
  
  public void testNote2()
  {
    Map localMap1 = this.workflow.getLastMsg(1, 0, "regStockToEnterWare");
    boolean bool1 = ((Boolean)localMap1.get("isNote")).booleanValue();
    assertEquals(true, bool1);
    List localList = (List)localMap1.get("transHandleList");
    assertEquals(2, localList.size());
    Map localMap2 = (Map)localList.get(1);
    boolean bool2 = ((Boolean)localMap2.get("isWriteNote")).booleanValue();
    assertEquals(true, bool2);
    localMap2 = (Map)localList.get(0);
    bool2 = ((Boolean)localMap2.get("isWriteNote")).booleanValue();
    assertEquals(false, bool2);
  }
}
