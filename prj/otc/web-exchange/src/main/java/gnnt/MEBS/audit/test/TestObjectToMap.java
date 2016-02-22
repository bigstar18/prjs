package gnnt.MEBS.audit.test;

import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.base.copy.MapToXml;
import gnnt.MEBS.base.copy.ObjectToMap;
import java.io.PrintStream;
import java.util.Date;

public class TestObjectToMap
{
  public static void main(String[] args)
  {
    try
    {
      AuditStatus log = new AuditStatus();
      log.setModTime(new Date());
      log.setProposer("admin");
      System.out.println(MapToXml.mapToXml(ObjectToMap.bindData(log)));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
