package gnnt.MEBS.commodity.action;

import gnnt.MEBS.audit.service.ParmaLogServiceJDBC;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.service.ArticleParitiesAgioService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("articleParitiesAgioAction")
@Scope("request")
public class ArticleParitiesAgioAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(ArticleParitiesAgioAction.class);
  @Autowired
  @Qualifier("articleParitiesAgioService")
  private ArticleParitiesAgioService articleParitiesAgioService;
  @Autowired
  @Qualifier("parmaLogServiceJDBC")
  private ParmaLogServiceJDBC parmaLogServiceJDBC;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  private String operateGflag = "commodity_articleParitiesAgio";
  
  public InService getService()
  {
    return this.articleParitiesAgioService;
  }
  
  public String updateRMI()
  {
    int returnValue = 1;
    String operateContent = "";
    try
    {
      String mysql = " primary.OPERATEGFLAG='" + this.operateGflag + "'";
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      remObject.refreshExchageRate();
      this.parmaLogServiceJDBC.delete(mysql);
      addResultSessionMsg(this.request, returnValue);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      returnValue = -1;
      if (e.getCause().getMessage() != null)
      {
        addResultSessionMsg(this.request, returnValue);
        this.request.getSession().setAttribute(ActionConstant.RESULTMSG, e.getCause().getMessage());
      }
    }
    if (returnValue == 1) {
      operateContent = "商品汇率设置实时生效成功！";
    } else if (returnValue == -1) {
      operateContent = "商品汇率设置实时生效失败！";
    }
    this.obj = null;
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      OperateLog operateLog = new OperateLog();
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      operateLog.setOperateContent(operateContent);
      this.operateLogService.add(operateLog);
    }
    return "success";
  }
}
