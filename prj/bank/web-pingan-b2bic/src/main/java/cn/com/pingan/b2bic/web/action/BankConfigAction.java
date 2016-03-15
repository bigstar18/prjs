package cn.com.pingan.b2bic.web.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.pingan.b2bic.web.BankVo;
import cn.com.pingan.b2bic.web.FtpVo;
import cn.com.pingan.b2bic.web.IConfigManager;
import cn.com.pingan.b2bic.web.ServerOutVo;
import javax.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class BankConfigAction
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource
  private IConfigManager configManager;
  private BankVo bankVo;
  
  @Action(value="B2Bi_BankConfig", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/pingan/BankConfig.jsp")})
  public String load()
    throws Exception
  {
    try
    {
      this.configManager.load(this.bankVo = new BankVo());
      logger.debug("银行配置信息：" + this.bankVo);
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward("加载配置失败");
      return "dwz";
    }
    return "success";
  }
  
  @Action("B2Bi_BankConfig_save")
  public String save()
    throws Exception
  {
    try
    {
      this.configManager.save(this.bankVo);
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
  
  @Action("BankSocketCheck")
  public String bankSocketCheck()
    throws Exception
  {
    try
    {
      this.configManager.commCheck(this.bankVo.getBankOut().getIps(), Integer.parseInt(this.bankVo.getBankOut().getPorts()));
      this.dwzResp.ajaxSuccessForward("检测通讯成功");
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
    }
    return "dwz";
  }
  
  @Action("BankSftpCheck")
  public String bankSftpCheck()
    throws Exception
  {
    try
    {
      this.configManager.commCheck(this.bankVo.getBankFtp().getHostname(), this.bankVo.getBankFtp().getPort());
      this.dwzResp.ajaxSuccessForward("检测通讯成功");
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
    }
    return "dwz";
  }
  
  public BankVo getBankVo()
  {
    return this.bankVo;
  }
  
  public void setBankVo(BankVo bankVo)
  {
    this.bankVo = bankVo;
  }
}
