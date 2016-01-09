package gnnt.MEBS.bank.mgr.action;

import gnnt.MEBS.bank.mgr.core.BankCoreCode;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("capitalAction")
@Scope("request")
public class CapitalAction extends EcsideAction
{
  private static final long serialVersionUID = 6546653294844440462L;

  @Resource(name="capitalInfoType")
  private Map<Integer, String> capitalInfoType;

  @Resource(name="capitalInfoStatus")
  private Map<Integer, String> capitalInfoStatus;

  @Autowired
  @Qualifier("capitalProcessorRMI")
  private CapitalProcessorRMI capitalProcessorRMI;

  public Map<Integer, String> getCapitalInfoType()
  {
    return this.capitalInfoType;
  }

  public Map<Integer, String> getCapitalInfoStatus()
  {
    return this.capitalInfoStatus;
  }

  public String capitalInfoList()
    throws Exception
  {
    return listByLimit();
  }

  public String outMoneyAuditList()
    throws Exception
  {
    PageRequest pageRequest = super.getPageRequest(this.request);

    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    qc.addCondition("status", "=", Integer.valueOf(3));
    qc.addCondition("type", "=", Integer.valueOf(1));
    listByLimit(pageRequest);

    return "success";
  }

  public String outMoneyAuditPass()
  {
    String[] ids = this.request.getParameterValues("ids");

    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 133101L);
      return "success";
    }

    String errorIDs = "";
    String successIDS = "";

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());

    for (String id : ids)
    {
      try {
        long result = this.capitalProcessorRMI.outMoneyAudit(Integer.parseInt(id.trim()), true);

        if (result == 0L) {
          if (successIDS.trim().length() > 0) successIDS = successIDS + ",";
          successIDS = successIDS + id;
          log.setLogcontent("流水" + id + "一次审核通过成功，时间：" + Tools.fmtTime(log.getLogDate()));
        } else {
          if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
          errorIDs = errorIDs + "编号" + id + "：" + BankCoreCode.getCode(result);
          log.setLogcontent("流水" + id + "一次审核通过失败，" + BankCoreCode.getCode(result) + "，时间：" + Tools.fmtTime(log.getLogDate()));
        }
      } catch (Exception e) {
        if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
        errorIDs = errorIDs + "编号" + id + "：异常";
        log.setLogcontent("流水" + id + "一次审核通过发生异常，时间：" + Tools.fmtTime(log.getLogDate()));
        this.logger.error("一次审核通过编号为 " + id + "的出金流水时发生异常。", e);
      }
      getService().add(log);
    }

    String result = "出金一次审核通过，";
    if (successIDS.trim().length() > 0) {
      result = result + successIDS + "成功";
    }
    if (errorIDs.trim().length() > 0) {
      result = result + errorIDs;
    }
    addReturnValue(-1, 130000L, new Object[] { result });

    return "success";
  }

  public String outMoneyAuditRefuse()
  {
    String[] ids = this.request.getParameterValues("ids");

    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 133111L);
      return "success";
    }

    String errorIDs = "";
    String successIDS = "";

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());

    for (String id : ids)
    {
      try {
        long result = this.capitalProcessorRMI.outMoneyAudit(Integer.parseInt(id.trim()), false);

        if (result == 0L) {
          if (successIDS.trim().length() > 0) successIDS = successIDS + ",";
          successIDS = successIDS + id;
          log.setLogcontent("流水" + id + "一次审核拒绝成功，时间：" + Tools.fmtTime(log.getLogDate()));
        } else {
          if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
          errorIDs = errorIDs + BankCoreCode.getCode(result);
          log.setLogcontent("流水" + id + "一次审核拒绝失败，" + BankCoreCode.getCode(result) + "，时间：" + Tools.fmtTime(log.getLogDate()));
        }
      } catch (Exception e) {
        if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
        errorIDs = errorIDs + id;
        log.setLogcontent("流水" + id + "一次审核拒绝发生异常，时间：" + Tools.fmtTime(log.getLogDate()));
        this.logger.error("一次审核拒绝编号为 " + id + "的出金流水时发生异常。", e);
      }
      getService().add(log);
    }

    String result = "出金一次审核拒绝，";
    if (successIDS.trim().length() > 0) {
      result = result + successIDS + "成功";
    }
    if (errorIDs.trim().length() > 0) {
      result = result + errorIDs;
    }
    addReturnValue(-1, 130000L, new Object[] { result });

    return "success";
  }

  public String outMoneySecondAuditList()
    throws Exception
  {
    PageRequest pageRequest = super.getPageRequest(this.request);

    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    qc.addCondition("status", "=", Integer.valueOf(4));
    qc.addCondition("type", "=", Integer.valueOf(1));
    listByLimit(pageRequest);

    return "success";
  }

  public String outMoneySecondAuditPass()
  {
    String[] ids = this.request.getParameterValues("ids");

    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 133131L);
      return "success";
    }

    String errorIDs = "";
    String successIDS = "";

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());

    for (String id : ids)
    {
      try {
        long result = this.capitalProcessorRMI.outMoneySecondAudit(Integer.parseInt(id.trim()), true);

        if (result == 0L) {
          if (successIDS.trim().length() > 0) successIDS = successIDS + ",";
          successIDS = successIDS + id;
          log.setLogcontent("流水" + id + "二次审核通过成功，时间：" + Tools.fmtTime(log.getLogDate()));
        } else {
          if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
          errorIDs = errorIDs + "编号" + id + "：" + BankCoreCode.getCode(result);
          log.setLogcontent("流水" + id + "二次审核通过失败，" + BankCoreCode.getCode(result) + "，时间：" + Tools.fmtTime(log.getLogDate()));
        }
      } catch (Exception e) {
        if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
        errorIDs = errorIDs + "编号" + id + "：异常";
        log.setLogcontent("流水" + id + "二次审核通过发生异常，时间：" + Tools.fmtTime(log.getLogDate()));
        this.logger.error("二次审核通过编号为 " + id + "的出金流水时发生异常。", e);
      }
      getService().add(log);
    }

    String result = "出金二次审核通过，";
    if (successIDS.trim().length() > 0) {
      result = result + successIDS + "成功";
    }
    if (errorIDs.trim().length() > 0) {
      result = result + errorIDs;
    }
    addReturnValue(-1, 130000L, new Object[] { result });

    return "success";
  }

  public String outMoneySecondAuditRefuse()
  {
    String[] ids = this.request.getParameterValues("ids");

    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 133141L);
      return "success";
    }

    String errorIDs = "";
    String successIDS = "";

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());

    for (String id : ids)
    {
      try {
        long result = this.capitalProcessorRMI.outMoneySecondAudit(Integer.parseInt(id.trim()), false);

        if (result == 0L) {
          if (successIDS.trim().length() > 0) successIDS = successIDS + ",";
          successIDS = successIDS + id;
          log.setLogcontent("流水" + id + "二次审核拒绝成功，时间：" + Tools.fmtTime(log.getLogDate()));
        } else {
          if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
          errorIDs = errorIDs + "编号" + id + "：" + BankCoreCode.getCode(result);
          log.setLogcontent("流水" + id + "二次审核拒绝失败，" + BankCoreCode.getCode(result) + "，时间：" + Tools.fmtTime(log.getLogDate()));
        }
      } catch (Exception e) {
        if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
        errorIDs = errorIDs + "编号" + id + "：异常";
        log.setLogcontent("流水" + id + "二次审核拒绝发生异常，时间：" + Tools.fmtTime(log.getLogDate()));
        this.logger.error("二次审核拒绝编号为 " + id + "的出金流水时发生异常。", e);
      }
      getService().add(log);
    }

    String result = "出金二次审核拒绝，";
    if (successIDS.trim().length() > 0) {
      result = result + successIDS + "成功";
    }
    if (errorIDs.trim().length() > 0) {
      result = result + errorIDs;
    }
    addReturnValue(-1, 130000L, new Object[] { result });

    return "success";
  }

  public String capitalUnilateralList()
    throws Exception
  {
    PageRequest pageRequest = super.getPageRequest(this.request);

    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    qc.addCondition("status", "in", "(2,5,6)");
    qc.addCondition("type", "<>", Integer.valueOf(2));
    listByLimit(pageRequest);

    return "success";
  }

  public String capitalUnilateralPass()
  {
    String[] ids = this.request.getParameterValues("ids");

    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 133151L);
      return "success";
    }

    String errorIDs = "";
    String successIDS = "";

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());

    for (String id : ids)
    {
      try {
        long result = this.capitalProcessorRMI.moneyInAudit(Integer.parseInt(id.trim()), true);

        if (result == 0L) {
          if (successIDS.trim().length() > 0) successIDS = successIDS + ",";
          successIDS = successIDS + id;
          log.setLogcontent("流水" + id + "单边账审核通过成功，时间：" + Tools.fmtTime(log.getLogDate()));
        } else {
          if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
          errorIDs = errorIDs + "编号" + id + "：" + BankCoreCode.getCode(result);
          log.setLogcontent("流水" + id + "单边账审核通过失败，" + BankCoreCode.getCode(result) + "，时间：" + Tools.fmtTime(log.getLogDate()));
        }
      } catch (Exception e) {
        if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
        errorIDs = errorIDs + "编号" + id + "：异常";
        log.setLogcontent("流水" + id + "单边账审核通过发生异常，时间：" + Tools.fmtTime(log.getLogDate()));
        this.logger.error("单边账审核通过编号为 " + id + "的流水时发生异常。", e);
      }
      getService().add(log);
    }

    String result = "单边账审核通过，";
    if (successIDS.trim().length() > 0) {
      result = result + successIDS + "成功";
    }
    if (errorIDs.trim().length() > 0) {
      result = result + errorIDs;
    }
    addReturnValue(-1, 130000L, new Object[] { result });

    return "success";
  }

  public String capitalUnilateralRefuse()
  {
    String[] ids = this.request.getParameterValues("ids");

    if ((ids == null) || (ids.length <= 0))
    {
      addReturnValue(-1, 133161L);
      return "success";
    }

    String errorIDs = "";
    String successIDS = "";

    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
    log.setLogDate(new Date());
    log.setLogIP(user.getIpAddress());
    log.setLogopr(user.getUserId());

    for (String id : ids)
    {
      try {
        long result = this.capitalProcessorRMI.moneyInAudit(Integer.parseInt(id.trim()), false);

        if (result == 0L) {
          if (successIDS.trim().length() > 0) successIDS = successIDS + ",";
          successIDS = successIDS + id;
          log.setLogcontent("流水" + id + "单边账审核拒绝成功，时间：" + Tools.fmtTime(log.getLogDate()));
        } else {
          if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
          errorIDs = errorIDs + "编号" + id + "：" + BankCoreCode.getCode(result);
          log.setLogcontent("流水" + id + "单边账审核拒绝失败，" + BankCoreCode.getCode(result) + "，时间：" + Tools.fmtTime(log.getLogDate()));
        }
      } catch (Exception e) {
        if (errorIDs.trim().length() > 0) errorIDs = errorIDs + ",";
        errorIDs = errorIDs + id + "：异常";
        log.setLogcontent("流水" + id + "单边账审核拒绝发生异常，时间：" + Tools.fmtTime(log.getLogDate()));
        this.logger.error("单边账审核拒绝编号为 " + id + "的流水时发生异常。", e);
      }
      getService().add(log);
    }

    String result = "单边账审核拒绝，";
    if (successIDS.trim().length() > 0) {
      result = result + successIDS + "成功";
    }
    if (errorIDs.trim().length() > 0) {
      result = result + errorIDs;
    }
    addReturnValue(-1, 130000L, new Object[] { result });

    return "success";
  }
}