package gnnt.MEBS.integrated.mgr.action.noticeandmessage;

import gnnt.MEBS.common.core.kernel.IKernelService;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.integrated.mgr.model.noticeandmessage.Notice;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("noticeAction")
@Scope("request")
public class NoticeAction
  extends EcsideAction
{
  private static final long serialVersionUID = 5515114711492695790L;
  @Autowired
  @Qualifier("com_kernelService")
  private IKernelService kernelService;
  
  public IKernelService getKernelService()
  {
    return this.kernelService;
  }
  
  public void setKernelService(IKernelService paramIKernelService)
  {
    this.kernelService = paramIKernelService;
  }
  
  public String addNotice()
    throws Exception
  {
    this.logger.debug("add notice");
    Notice localNotice = (Notice)this.entity;
    localNotice.setCreateTime(getService().getSysDate());
    super.getService().add(localNotice);
    this.kernelService.sendMgrTopicMsg("你有新的公告,请注意查看！");
    this.kernelService.sendFrontTopicMsg("你有新的公告,请注意查看！");
    writeOperateLog(1041, "添加公告,公告标题为：" + localNotice.getTitle(), 1, "");
    addReturnValue(1, 119901L);
    return "success";
  }
}
