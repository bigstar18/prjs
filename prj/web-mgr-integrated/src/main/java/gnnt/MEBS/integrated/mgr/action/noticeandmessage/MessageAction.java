package gnnt.MEBS.integrated.mgr.action.noticeandmessage;

import gnnt.MEBS.common.core.kernel.IKernelService;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.integrated.mgr.model.noticeandmessage.Message;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("messageAction")
@Scope("request")
public class MessageAction
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="recieverMap")
  protected Map<String, String> recieverMap;
  @Autowired
  @Qualifier("com_kernelService")
  private IKernelService kernelService;
  
  public Map<String, String> getRecieverMap()
  {
    return this.recieverMap;
  }
  
  public IKernelService getKernelService()
  {
    return this.kernelService;
  }
  
  public void setKernelService(IKernelService paramIKernelService)
  {
    this.kernelService = paramIKernelService;
  }
  
  public String addMessage()
    throws Exception
  {
    Message localMessage = (Message)this.entity;
    localMessage.setCreateTime(getService().getSysDate());
    String str = "";
    if (localMessage.getRecieverType().intValue() == 1)
    {
      this.kernelService.sendFrontTopicMsg(localMessage.getMessage());
      str = "添加消息,接收人类型为：在线交易员";
    }
    else if (localMessage.getRecieverType().intValue() == 2)
    {
      this.kernelService.sendMgrTopicMsg(localMessage.getMessage());
      str = "添加消息,接收人类型为：在线管理员";
    }
    else if (localMessage.getRecieverType().intValue() == 3)
    {
      this.kernelService.sendMgrTopicMsg(localMessage.getMessage());
      this.kernelService.sendFrontTopicMsg(localMessage.getMessage());
      str = "添加消息,接收人类型为：在线用户";
    }
    else if (localMessage.getRecieverType().intValue() == 4)
    {
      this.kernelService.sendFrontQueueMsg(localMessage.getTraderId(), localMessage.getMessage());
      str = "添加消息,接收人类型为：指定交易员(" + localMessage.getTraderId() + ")";
    }
    else if (localMessage.getRecieverType().intValue() == 5)
    {
      this.kernelService.sendMgrQueueMsg(localMessage.getTraderId(), localMessage.getMessage());
      str = "添加消息,接收人类型为：指定管理员(" + localMessage.getTraderId() + ")";
    }
    getService().add(localMessage);
    addReturnValue(1, 119901L);
    writeOperateLog(1041, str, 1, "");
    return "success";
  }
}
