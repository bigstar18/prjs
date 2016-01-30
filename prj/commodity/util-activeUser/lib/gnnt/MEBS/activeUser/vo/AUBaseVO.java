package gnnt.MEBS.activeUser.vo;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AUBaseVO
  implements Serializable
{
  private static final long serialVersionUID = 6773666333589225909L;
  protected final transient Log logger = LogFactory.getLog(getClass());
}
