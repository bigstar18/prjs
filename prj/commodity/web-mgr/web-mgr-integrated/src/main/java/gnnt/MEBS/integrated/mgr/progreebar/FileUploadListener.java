package gnnt.MEBS.integrated.mgr.progreebar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUploadListener
  implements ProgressListener
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  private HttpSession session;
  
  public FileUploadListener(HttpServletRequest paramHttpServletRequest)
  {
    this.session = paramHttpServletRequest.getSession();
    State localState = new State();
    this.session.setAttribute("state", localState);
  }
  
  public void update(long paramLong1, long paramLong2, int paramInt)
  {
    this.logger.debug("已上传：" + paramLong1 + ";总大小：" + paramLong2 + ";文件个数：" + paramInt);
    State localState = (State)this.session.getAttribute("state");
    localState.setReadedBytes(paramLong1);
    localState.setTotalBytes(paramLong2);
    localState.setCurrentItem(paramInt);
  }
}
