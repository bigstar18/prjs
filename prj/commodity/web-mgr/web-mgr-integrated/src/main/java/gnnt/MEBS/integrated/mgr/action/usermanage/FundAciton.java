package gnnt.MEBS.integrated.mgr.action.usermanage;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import gnnt.MEBS.integrated.mgr.service.FundService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("fundAction")
@Scope("request")
public class FundAciton
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  @Autowired
  @Qualifier("fundService")
  private FundService fundService;
  private String firmId;
  
  public FundService getFundService()
  {
    return this.fundService;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String forwardfund()
  {
    return "success";
  }
  
  public String inmoneymodel()
  {
    String str1 = "模拟转入资金,转入金额:";
    double d1 = Tools.strToDouble(this.request.getParameter("money"), 10000.0D);
    str1 = str1 + Tools.fmtDouble2(d1);
    String str2 = this.request.getParameter("firmId");
    MFirm localMFirm = new MFirm();
    localMFirm.setFirmId(str2);
    localMFirm = (MFirm)getService().get(localMFirm);
    if (localMFirm == null)
    {
      addReturnValue(-1, 119907L, new Object[] { "交易商不存在" });
      writeOperateLog(1031, str1, 0, String.format(ApplicationContextInit.getErrorInfo("119907"), new Object[] { "交易商不存在" }));
      return "success";
    }
    if (d1 > 0.0D)
    {
      try
      {
        double d2 = this.fundService.inmoneymodel(str2, d1);
        if (d2 < 0.0D)
        {
          addReturnValue(-1, 999999L, new Object[] { "入金操作失败" });
          writeOperateLog(1031, str1, 0, String.format(ApplicationContextInit.getErrorInfo("999999"), new Object[] { "入金操作失败" }));
        }
        else
        {
          str1 = "对交易商：" + str2 + str1;
          addReturnValue(1, 119909L, new Object[] { "入金：" + Tools.fmtDouble2(d1) + " 成功，您的当前余额为：" + Tools.fmtDouble2(d2) });
          writeOperateLog(1031, str1, 1, "");
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    else
    {
      addReturnValue(-1, 230003L, new Object[] { "输入资金值为" + d1 });
      writeOperateLog(1031, str1, 0, String.format(ApplicationContextInit.getErrorInfo("230003"), new Object[] { "入金操作失败" }));
    }
    return "success";
  }
}
