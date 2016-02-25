package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.services.RightService;
import gnnt.MEBS.common.util.SysDataTest;
import java.util.HashSet;
import java.util.Set;

public class TestAddRight
{
  public static void main(String[] paramArrayOfString)
  {
    RightService localRightService = (RightService)SysDataTest.getBean("rightService");
    Right localRight1 = localRightService.getRightById(0L);
    HashSet localHashSet1 = new HashSet();
    for (int i = 1; i < 4; i++)
    {
      Right localRight2 = new Right();
      localRight2.setIcon(i + "");
      localRight2.setModuleId(Integer.valueOf(i));
      localRight2.setIsLog(Integer.valueOf(0));
      localRight2.setName(i + "菜单");
      localRight2.setSeq(Integer.valueOf(i));
      localRight2.setType(Integer.valueOf(-1));
      localRight2.setUrl(i + "");
      localRight2.setVisible(Integer.valueOf(0));
      HashSet localHashSet2 = new HashSet();
      for (int j = 1; j < 4; j++)
      {
        Right localRight3 = new Right();
        localRight3.setIcon(i + "" + j);
        localRight3.setModuleId(Integer.valueOf(i));
        localRight3.setIsLog(Integer.valueOf(0));
        localRight3.setName(i + "_" + j + "菜单");
        localRight3.setSeq(Integer.valueOf(j));
        localRight3.setType(Integer.valueOf(0));
        localRight3.setUrl(i + "_" + j);
        localRight3.setVisible(Integer.valueOf(0));
        HashSet localHashSet3 = new HashSet();
        for (int k = 1; k < 4; k++)
        {
          Right localRight4 = new Right();
          localRight4.setModuleId(Integer.valueOf(i));
          localRight4.setIsLog(Integer.valueOf(0));
          localRight4.setName(i + "_" + j + "_" + k + "菜单");
          localRight4.setType(Integer.valueOf(0));
          localRight4.setUrl(i + "_" + j + "_" + k);
          HashSet localHashSet4 = new HashSet();
          for (int m = 1; m < 4; m++)
          {
            Right localRight5 = new Right();
            localRight5.setModuleId(Integer.valueOf(i));
            localRight5.setIsLog(Integer.valueOf(0));
            localRight5.setName(i + "_" + j + "_" + k + "_" + m + "权限");
            localRight5.setType(Integer.valueOf(1));
            localRight5.setUrl(i + "_" + j + "_" + k + "_" + m);
            localHashSet4.add(localRight5);
          }
          localRight4.setRightSet(localHashSet4);
          localHashSet3.add(localRight4);
        }
        localRight3.setRightSet(localHashSet3);
        localHashSet2.add(localRight3);
      }
      localRight2.setRightSet(localHashSet2);
      localHashSet1.add(localRight2);
    }
    localRight1.setRightSet(localHashSet1);
    localRightService.updateRight(localRight1);
  }
}
