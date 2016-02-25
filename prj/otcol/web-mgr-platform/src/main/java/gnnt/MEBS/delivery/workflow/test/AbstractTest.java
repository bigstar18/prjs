package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractTest
  extends AbstractTransactionalDataSourceSpringContextTests
{
  @Autowired
  @Qualifier("w_workflowFacade")
  protected WorkflowFacade workflow;
  
  protected String[] getConfigLocations()
  {
    File localFile = new File(".");
    String str = "classpath:wareHouseBeansTest.xml";
    setAutowireMode(1);
    return new String[] { str };
  }
}
