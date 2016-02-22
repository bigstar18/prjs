package gnnt.MEBS.commodity.test;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.service.SingleReportService;
import java.io.PrintStream;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRAM
{
  public static void main(String[] args)
    throws Exception
  {
    ApplicationContext factory = new ClassPathXmlApplicationContext("report_default.xml");
    SingleReportService infoService = (SingleReportService)factory.getBean("customerFundsReportService");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("to_char(primary.cleardate,'yyyy-MM-dd')", "=", "2011-08-01");
    



    usedMemory();
    

    long heap1 = 0L;
    
    heap1 = usedMemory();
    
    List list = infoService.getTraderList(qc);
    long heap2 = usedMemory();
    
    float size = Math.round((float)(heap2 - heap1) / 1024.0F / 1024.0F);
    System.out.println("'before' heap: " + heap1 + 
      ", 'after' heap: " + heap2);
    System.out.println("heap delta: " + (heap2 - heap1) + 
      " size = " + size + " M");
  }
  
  private static void runGC()
    throws Exception
  {
    for (int r = 0; r < 4; r++) {
      _runGC();
    }
  }
  
  private static void _runGC()
    throws Exception
  {
    long usedMem1 = usedMemory();long usedMem2 = 9223372036854775807L;
    for (int i = 0; (usedMem1 < usedMem2) && (i < 500); i++)
    {
      s_runtime.runFinalization();
      s_runtime.gc();
      Thread.currentThread();Thread.yield();
      
      usedMem2 = usedMem1;
      usedMem1 = usedMemory();
    }
  }
  
  private static long usedMemory()
  {
    return s_runtime.totalMemory() - s_runtime.freeMemory();
  }
  
  private static final Runtime s_runtime = ;
}
