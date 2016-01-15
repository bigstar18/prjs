package gnnt.MEBS.integrated.broker.action.firmManager;

import gnnt.MEBS.common.broker.action.EcsideAction;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.integrated.broker.model.firmManager.BRFirm;
import gnnt.MEBS.integrated.broker.model.firmManager.BRFirmApply;
import gnnt.MEBS.integrated.broker.model.firmManager.MFirmApply;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("firmManagerAction")
@Scope("request")
public class FirmManagerAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;

  @Resource(name="mfirmTypeMap")
  protected Map<Integer, String> mfirmTypeMap;

  @Resource(name="applyStatusMap")
  protected Map<Short, String> applyStatusMap;

  @Resource(name="certificateTypeMap")
  protected Map<Integer, String> certificateTypeMap;

  @Resource(name="firmStatusMap")
  protected Map<String, String> firmStatusMap;

  public Map<Integer, String> getMfirmTypeMap()
  {
    return this.mfirmTypeMap;
  }

  public Map<Short, String> getApplyStatusMap()
  {
    return this.applyStatusMap;
  }

  public Map<Integer, String> getCertificateTypeMap()
  {
    return this.certificateTypeMap;
  }

  public Map<String, String> getFirmStatusMap()
  {
    return this.firmStatusMap;
  }

  public String listFirmApply()
  {
    this.logger.debug("------------listFirmApply 开户申请列表--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.brFirmApply.brokerId", "=", localUser.getUserId());
      localQueryConditions.addCondition("primary.status", "!=", new Short("1"));
      listByLimit(localPageRequest);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String forwardAddFirmApply()
  {
    this.logger.debug("------------forwardAddFirmApply 开户申请添加页面--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = "select brokerAgeId,name from BR_Brokerage where brokerId= '" + localUser.getUserId() + "'";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
    return "success";
  }

  public String addFirmApply()
  {
    this.logger.debug("------------addFirmApply  添加开户申请--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    MFirmApply localMFirmApply = (MFirmApply)this.entity;
    try
    {
      localMFirmApply.setCreateTime(getService().getSysDate());
      localMFirmApply.setModifyTime(localMFirmApply.getCreateTime());
      localMFirmApply.setStatus(new Short("0").shortValue());
      BRFirmApply localBRFirmApply = localMFirmApply.getBrFirmApply();
      localBRFirmApply.setBrokerId(localUser.getUserId());
      localBRFirmApply.setUserId(localMFirmApply.getUserId());
      localBRFirmApply.setApplyDate(localMFirmApply.getCreateTime());
      localBRFirmApply.setmFirmApply(localMFirmApply);
      getService().add(this.entity);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    addReturnValue(1, 119901L);
    return "success";
  }

  public String viewByIdFirmApply()
  {
    this.logger.debug("------------viewByIdFirmApply 查看开户申请--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = "select brokerAgeId,name from BR_Brokerage where brokerId= '" + localUser.getUserId() + "'";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
    return viewById();
  }

  public String updateFirmApply()
  {
    this.logger.debug("------------updateFirmApply  修改开户申请--------------");
    MFirmApply localMFirmApply = (MFirmApply)this.entity;
    try
    {
      localMFirmApply.setModifyTime(getService().getSysDate());
      localMFirmApply.setStatus(new Short("0").shortValue());
      BRFirmApply localBRFirmApply = localMFirmApply.getBrFirmApply();
      localBRFirmApply.setUserId(localMFirmApply.getUserId());
      getService().update(this.entity);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    addReturnValue(1, 119902L);
    return "success";
  }

  public String listFirm()
  {
    this.logger.debug("------------listFirm 已审核交易商列表--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.firmAndBroker.brokerId", "=", localUser.getUserId());
      listByLimit(localPageRequest);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String viewByIdFirm()
  {
    BRFirm localBRFirm = (BRFirm)this.entity;
    this.logger.debug("------------viewByIdFirm 查看已审核交易商--------------");
    String str1 = "";
    String str2 = "select brokerAgeId,name from BR_Brokerage where brokerAgeID=(select brokerAgeID from BR_BrokerAgeAndFirm where bindType=0 and firmId='" + localBRFirm.getFirmId() + "')";
    List localList = getService().getListBySql(str2);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      str1 = localMap.get("NAME").toString();
    }
    this.request.setAttribute("brokerAge", str1);
    return viewById();
  }

  public String forwardAddFirm()
  {
    this.logger.debug("------------forwardAddFirmApply 开户申请添加页面--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str = "select brokerAgeId,name from BR_Brokerage where brokerId= '" + localUser.getUserId() + "'";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
    return "success";
  }

  public String addFirm()
  {
    this.logger.debug("------------addFirmApply  添加开户申请--------------");
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    MFirmApply localMFirmApply = (MFirmApply)this.entity;
    try
    {
      localMFirmApply.setCreateTime(getService().getSysDate());
      localMFirmApply.setModifyTime(localMFirmApply.getCreateTime());
      localMFirmApply.setStatus(new Short("0").shortValue());
      BRFirmApply localBRFirmApply = localMFirmApply.getBrFirmApply();
      localBRFirmApply.setBrokerId(localUser.getUserId());
      localBRFirmApply.setUserId(localMFirmApply.getUserId());
      localBRFirmApply.setApplyDate(localMFirmApply.getCreateTime());
      localBRFirmApply.setmFirmApply(localMFirmApply);
      getService().add(this.entity);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }
}