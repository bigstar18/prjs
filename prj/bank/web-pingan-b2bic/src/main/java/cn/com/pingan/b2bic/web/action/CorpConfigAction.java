package cn.com.pingan.b2bic.web.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.pingan.b2bic.web.CorpVo;
import cn.com.pingan.b2bic.web.FtpVo;
import cn.com.pingan.b2bic.web.IConfigManager;
import cn.com.pingan.b2bic.web.ServerOutVo;
import javax.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class CorpConfigAction
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource
  private IConfigManager configManager;
  private CorpVo corpVo;
  
  @Action(value="B2Bi_CorpConfig", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/pingan/CorpConfig.jsp")})
  public String load()
    throws Exception
  {
    try
    {
      this.configManager.load(this.corpVo = new CorpVo());
      logger.debug("企业配置信息：" + this.corpVo);
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward("加载配置失败");
      return "dwz";
    }
    return "success";
  }
  
  @Action("B2Bi_CorpConfig_save")
  public String save()
    throws Exception
  {
    try
    {
      this.configManager.save(this.corpVo);
      this.dwzResp.successForward("保存配置成功");
      this.dwzResp.setCallbackType(null);
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
    }
    return "dwz";
  }
  
  @Action("CorpSocketCheck")
  public String corpSocketCheck()
    throws Exception
  {
    try
    {
      this.configManager.commCheck(this.corpVo.getCorpOut().getIps(), Integer.parseInt(this.corpVo.getCorpOut().getPorts()));
      this.dwzResp.ajaxSuccessForward("通讯成功");
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
    }
    return "dwz";
  }
  
  @Action("CorpFtpCheck")
  public String ftpCheck()
    throws Exception
  {
    try
    {
      this.configManager.commCheck(this.corpVo.getCorpFtp().getHostname(), this.corpVo.getCorpFtp().getPort());
      this.dwzResp.ajaxSuccessForward("检测通讯成功");
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
    }
    return "dwz";
  }
  
  public CorpVo getCorpVo()
  {
    return this.corpVo;
  }
  
  public void setCorpVo(CorpVo corpVo)
  {
    this.corpVo = corpVo;
  }
}
