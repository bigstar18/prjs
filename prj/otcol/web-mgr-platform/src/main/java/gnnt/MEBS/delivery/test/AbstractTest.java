package gnnt.MEBS.delivery.test;

import java.io.File;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractTest
  extends AbstractTransactionalDataSourceSpringContextTests
{
  protected String[] getConfigLocations()
  {
    File localFile = new File(".");
    String str = "file:" + localFile.getAbsolutePath().substring(0, localFile.getAbsolutePath().length() - 1) + "warehouse-src/wareHouseBeansTest.xml";
    setAutowireMode(1);
    return new String[] { str };
  }
}
