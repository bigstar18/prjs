package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.announcement.dao.NoticeProDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractTypeHandler
  implements NoticeTypeHandler
{
  private final transient Log logger = LogFactory.getLog(AbstractTypeHandler.class);
  @Autowired
  @Qualifier("noticeProDao")
  protected NoticeProDao noticeProDao;
  protected NoticeTypeHandler nextHandle;
  
  public void setNextHandle(NoticeTypeHandler nextHandle)
  {
    this.nextHandle = nextHandle;
  }
  
  public abstract int handle(Long paramLong, Element paramElement);
  
  public int handleRequest(Long noticeId, Element range)
  {
    int result = -1;
    result = handle(noticeId, range);
    if ((result != 1) && 
      (this.nextHandle != null)) {
      result = this.nextHandle.handleRequest(noticeId, range);
    }
    return result;
  }
}
