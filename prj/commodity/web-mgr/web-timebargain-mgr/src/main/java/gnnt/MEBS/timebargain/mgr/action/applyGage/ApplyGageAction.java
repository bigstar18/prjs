package gnnt.MEBS.timebargain.mgr.action.applyGage;

import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.action.ECSideAction;
import gnnt.MEBS.timebargain.mgr.model.applyGage.ApplyGage;
import gnnt.MEBS.timebargain.mgr.model.applyGage.CustomerA;
import gnnt.MEBS.timebargain.mgr.service.ApplyGageService;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("applyGageAction")
@Scope("request")
public class ApplyGageAction extends ECSideAction
{

  @Resource(name="applyGage_typeMap")
  private Map<Integer, String> applyGage_typeMap;

  @Resource(name="applyGage_statusMap")
  private Map<Integer, String> applyGage_statusMap;

  @Autowired
  @Qualifier("applyGageService")
  private ApplyGageService applyGageService;

  public Map<Integer, String> getApplyGage_typeMap()
  {
    return this.applyGage_typeMap;
  }

  public void setApplyGage_typeMap(Map<Integer, String> paramMap)
  {
    this.applyGage_typeMap = paramMap;
  }

  public Map<Integer, String> getApplyGage_statusMap()
  {
    return this.applyGage_statusMap;
  }

  public void setApplyGage_statusMap(Map<Integer, String> paramMap)
  {
    this.applyGage_statusMap = paramMap;
  }

  public String forwardAddApplyGage()
  {
    this.logger.debug("------------forwardAddApplyGage 到抵顶申请页面--------------");
    String str = "select commodityId,name from t_commodity ";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
    return "success";
  }

  public String addApplyGage()
  {
    String str = "";
    int i = 0;
    this.logger.debug("------------addApplyGage 抵顶申请-------------");
    ApplyGage localApplyGage = (ApplyGage)this.entity;
    CustomerA localCustomerA = new CustomerA();
    localCustomerA.setCustomerId(localApplyGage.getCustomerId());
    localCustomerA = (CustomerA)getService().get(localCustomerA);
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    try
    {
      Date localDate = getService().getSysDate();
      localApplyGage.setFirmId(localCustomerA.getFirmId());
      localApplyGage.setStatus(Integer.valueOf(1));
      localApplyGage.setCreator(localUser.getUserId());
      localApplyGage.setCreateTime(localDate);
      localApplyGage.setModifyTime(localDate);
      i = this.applyGageService.addApplyGage(localApplyGage);
      if (i == 1)
      {
        addReturnValue(1, 119907L);
        writeOperateLog(1508, "抵顶申请成功,申请ID：" + localApplyGage.getApplyId(), 1, "");
        return "success";
      }
      if (i == -1)
      {
        addReturnValue(-1, 150503L);
        str = "抵顶时超出可抵顶数量！";
      }
      else if (i == -2)
      {
        addReturnValue(-1, 150505L);
        str = "抵顶数量大于可用数量！";
      }
      else if (i == -3)
      {
        addReturnValue(-1, 150507L);
        str = "取消抵顶时超出抵顶数量！";
      }
      else
      {
        addReturnValue(-1, 119908L);
        str = "操作失败！";
      }
      writeOperateLog(1508, str, 0, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String auditApplyGage()
  {
    this.logger.debug("------------auditApplyGage 抵顶审核--------------");
    ApplyGage localApplyGage1 = (ApplyGage)this.entity;
    ApplyGage localApplyGage2 = (ApplyGage)getService().get(localApplyGage1);
    SystemStatus localSystemStatus = null;
    List localList = getService().getListBySql("select status from T_SystemStatus");
    Object localObject;
    if ((localList != null) && (localList.size() > 0))
    {
      localObject = (Map)localList.get(0);
      localSystemStatus = new SystemStatus();
      localSystemStatus.setStatus(Integer.parseInt(((Map)localObject).get("STATUS").toString()));
      this.logger.debug("------------ 交易状态: " + localSystemStatus.getStatus() + "--------------");
    }
    if ((localSystemStatus == null) || (localSystemStatus.getStatus() != 1))
    {
      addReturnValue(-1, 150501L);
      writeOperateLog(1508, "只能在闭市状态下进行审核！", 0, "");
      return "success";
    }
    if (localApplyGage2 == null)
    {
      addReturnValue(0, 119901L);
    }
    else if (localApplyGage2.getStatus().intValue() != 1)
    {
      addReturnValue(0, 119901L);
    }
    else if (localApplyGage1.getStatus().intValue() == 2)
    {
      int j;
      if (localApplyGage2.getApplyType().intValue() == 1)
      {
        localObject = "";
        j = 0;
        try
        {
          j = this.applyGageService.auditApplyGage(localApplyGage2);
          if (j == 1)
          {
            addReturnValue(1, 119907L);
            writeOperateLog(1508, "抵顶审核通过处理成功,申请ID：" + localApplyGage2.getApplyId(), 1, "");
            return "success";
          }
          if (j == 2)
          {
            addReturnValue(-1, 150502L);
            localObject = "已处理的申请无需再次审核！";
          }
          else if (j == -1)
          {
            addReturnValue(-1, 150503L);
            localObject = "抵顶时超出可抵顶数量！";
          }
          else if (j == -2)
          {
            addReturnValue(-1, 150504L);
            localObject = "抵顶数量大于持仓数量！";
          }
          else if (j == -3)
          {
            addReturnValue(-1, 150505L);
            localObject = "抵顶数量大于可用数量！";
          }
          else if (j == -4)
          {
            addReturnValue(-1, 150506L);
            localObject = "没有对应的生效仓单抵顶记录！";
          }
          else if (j == -13)
          {
            addReturnValue(-1, 150513L);
            localObject = "没有对应持仓数量！";
          }
          else
          {
            addReturnValue(-1, 119908L);
            localObject = "操作失败！";
          }
          writeOperateLog(1508, (String)localObject, 0, "");
        }
        catch (Exception localException2)
        {
          addReturnValue(-1, 119908L);
          this.logger.error("抵顶审核失败！！！");
          writeOperateLog(1508, "抵顶审核失败！", 0, localException2.getMessage());
        }
      }
      else if ((localApplyGage2.getApplyType().intValue() == 2) || (localApplyGage2.getApplyType().intValue() == 3))
      {
        localObject = "";
        j = 0;
        try
        {
          j = this.applyGageService.cancelApplyGage(localApplyGage2);
          if (j == 1)
          {
            addReturnValue(1, 111501L);
            writeOperateLog(1508, "撤销抵顶审核通过处理成功,申请ID：" + localApplyGage2.getApplyId(), 1, "");
            return "success";
          }
          if (j == 2)
          {
            addReturnValue(-1, 150502L);
            localObject = "已处理的申请无需再次审核！";
          }
          else if (j == -11)
          {
            addReturnValue(-1, 150507L);
            localObject = "取消抵顶时超出抵顶数量！";
          }
          else if (j == -12)
          {
            addReturnValue(-1, 150508L);
            localObject = "取消抵顶数量大于抵顶数量！";
          }
          else if (j == -13)
          {
            addReturnValue(-1, 150509L);
            localObject = "资金余额不足！";
          }
          else
          {
            addReturnValue(-1, 119908L);
            localObject = "操作失败";
          }
          writeOperateLog(1508, (String)localObject, 0, "");
        }
        catch (Exception localException3)
        {
          addReturnValue(-1, 119908L);
          this.logger.error("撤销抵顶审核失败！！！");
          writeOperateLog(1508, "撤销抵顶审核失败！", 0, localException3.getMessage());
        }
      }
    }
    else if (localApplyGage1.getStatus().intValue() == 3)
    {
      int i = 0;
      localApplyGage2.setStatus(Integer.valueOf(3));
      try
      {
        i = this.applyGageService.failApplyGage(localApplyGage2);
        if (i == 1)
        {
          addReturnValue(1, 111501L);
          writeOperateLog(1508, "抵顶审核不通过处理成功,申请ID：" + localApplyGage2.getApplyId(), 1, "");
        }
        else
        {
          addReturnValue(-1, 119908L);
          writeOperateLog(1508, "抵顶审核不通过处理失败！", 0, "");
        }
      }
      catch (Exception localException1)
      {
        addReturnValue(-1, 119908L);
        this.logger.error("抵顶审核不通过处理失败！！！");
        writeOperateLog(1508, "抵顶审核不通过处理失败！", 0, localException1.getMessage());
      }
    }
    return "success";
  }
}