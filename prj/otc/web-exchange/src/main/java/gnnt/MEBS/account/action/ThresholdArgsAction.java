package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.ThresholdArgs;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.ThresholdArgsService;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ThresholdArgsAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(ThresholdArgsAction.class);
  @Autowired
  @Qualifier("thresholdArgsService")
  private ThresholdArgsService thresholdArgsService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Resource(name="returnForceCloseMap")
  protected Map<Integer, Integer> returnForceCloseMap;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  
  public InService getService()
  {
    return this.thresholdArgsService;
  }
  
  public String forwardUpdate()
  {
    this.obj = this.thresholdArgsService.getById(null);
    return getReturnValue();
  }
  
  public String update()
  {
    int resultValue = this.thresholdArgsService.update((ThresholdArgs)this.obj);
    this.obj = this.thresholdArgsService.get((ThresholdArgs)this.obj);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String forceClose()
  {
    String operateContent = "";
    String customerNo = this.request.getParameter("customerNo");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("customerNo", "=", customerNo);
    List list = this.customerService.getList(qc, null);
    int resultValue = 1;
    if (list.size() > 0)
    {
      resultValue = this.thresholdArgsService.forceClose(this.request.getParameter("customerNo"), AclCtrl.getLogonID(this.request));
      if (resultValue == 1) {
        operateContent = "客户" + this.request.getParameter("customerNo") + "强平成功";
      } else if (resultValue == -1) {
        operateContent = "客户" + this.request.getParameter("customerNo") + "非交易状态,不能强平";
      } else {
        operateContent = "客户" + this.request.getParameter("customerNo") + "强平失败";
      }
    }
    else
    {
      operateContent = "无" + this.request.getParameter("customerNo") + "客户,强平失败";
      resultValue = -222;
    }
    addResultMsg(this.request, ((Integer)this.returnForceCloseMap.get(Integer.valueOf(resultValue))).intValue());
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      OperateLog operateLog = new OperateLog();
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateContent(operateContent);
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      this.operateLogService.add(operateLog);
    }
    return getReturnValue();
  }
  
  public String memberForceClose()
  {
    String operateContent = "";
    String memberNo = this.request.getParameter("memberNo");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("id", "=", memberNo);
    List list = this.memberInfoService.getList(qc, null);
    int resultValue = 1;
    if (list.size() > 0)
    {
      resultValue = this.thresholdArgsService.forceClose(this.request.getParameter("memberNo"), AclCtrl.getLogonID(this.request));
      if (resultValue == 1) {
        operateContent = "综合会员" + this.request.getParameter("memberNo") + "强平成功";
      } else if (resultValue == -1) {
        operateContent = "综合会员" + this.request.getParameter("memberNo") + "非交易状态,不能强平";
      } else {
        operateContent = "综合会员" + this.request.getParameter("memberNo") + "强平失败";
      }
    }
    else
    {
      resultValue = -222;
    }
    addResultMsg(this.request, ((Integer)this.returnForceCloseMap.get(Integer.valueOf(resultValue))).intValue());
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      OperateLog operateLog = new OperateLog();
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateContent(operateContent);
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      this.operateLogService.add(operateLog);
    }
    return getReturnValue();
  }
}
