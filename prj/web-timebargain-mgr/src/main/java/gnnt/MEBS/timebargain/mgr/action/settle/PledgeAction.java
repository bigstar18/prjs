package gnnt.MEBS.timebargain.mgr.action.settle;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.settle.PledgeModel;
import gnnt.MEBS.timebargain.mgr.service.PledgeService;
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

@Controller("pledgeAction")
@Scope("request")
public class PledgeAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;

  @Resource(name="Pledge_typeMap")
  private Map<String, String> Pledge_typeMap;

  @Resource(name="Pledge_statusMap")
  private Map<String, String> Pledge_statusMap;

  @Autowired
  @Qualifier("com_pledgeService")
  private PledgeService pledgeService;

  public Map<String, String> getPledge_typeMap()
  {
    return this.Pledge_typeMap;
  }

  public void setPledge_typeMap(Map<String, String> paramMap)
  {
    this.Pledge_typeMap = paramMap;
  }

  public Map<String, String> getPledge_statusMap()
  {
    return this.Pledge_statusMap;
  }

  public void setPledge_statusMap(Map<String, String> paramMap)
  {
    this.Pledge_statusMap = paramMap;
  }

  public PledgeService getPledgeService()
  {
    return this.pledgeService;
  }

  public void setPledgeService(PledgeService paramPledgeService)
  {
    this.pledgeService = paramPledgeService;
  }

  public String addPledge()
  {
    PledgeModel localPledgeModel = (PledgeModel)this.entity;
    String str1 = ((User)this.request.getSession().getAttribute("CurrentUser")).getUserId();
    localPledgeModel.setCreator(str1);
    localPledgeModel.setStatus(Integer.valueOf(0));
    localPledgeModel.setCreateTime(new Date());
    String str2 = localPledgeModel.getBillID();
    int i = localPledgeModel.getType().intValue();
    if (i == 1)
    {
      String str3 = "select * from T_E_Pledge where status = 0 and type = 1 and BillID = '" + str2 + "'";
      List localList = getService().getListBySql(str3);
      if (localList.size() > 0)
      {
        addReturnValue(-1, 150817L);
        return "success";
      }
    }
    int j = this.pledgeService.addPledge(str2, this.entity);
    if (j == -2)
      addReturnValue(1, 150819L);
    else if (j < 0)
      addReturnValue(1, 150818L);
    else
      addReturnValue(1, 119901L);
    return "success";
  }

  public String viewByIdPledge()
    throws Exception
  {
    PledgeModel localPledgeModel = (PledgeModel)getService().get(this.entity);
    this.entity = localPledgeModel;
    return "success";
  }

  public String pledgeAudit()
  {
    String str1 = this.request.getParameter("pledgeID");
    int i = Integer.parseInt(this.request.getParameter("status"));
    PledgeModel localPledgeModel = new PledgeModel();
    localPledgeModel.setPledgeID(Long.valueOf(Long.parseLong(str1)));
    localPledgeModel = (PledgeModel)getService().get(localPledgeModel);
    long l = localPledgeModel.getType().intValue();
    double d = localPledgeModel.getBillFund();
    String str2 = localPledgeModel.getFirmID();
    String str3 = localPledgeModel.getBillID();
    String str4 = ((User)this.request.getSession().getAttribute("CurrentUser")).getUserId();
    int j;
    if (i == 1)
    {
      j = this.pledgeService.auditSuccess(str1, str2, str3, l, d, str4);
      if (j == 1)
        addReturnValue(1, 119907L);
      else if (j == -1)
        addReturnValue(1, 150818L);
    }
    else if (i == 2)
    {
      j = this.pledgeService.auditFail(str1, str3, l, str4);
      if (j == 1)
        addReturnValue(1, 119907L);
      else if (j == -1)
        addReturnValue(1, 150818L);
    }
    return "success";
  }

  public String getBillListByBillID()
    throws Exception
  {
    this.logger.debug("------------getBillListByBillID 根据质押资金仓单号查询仓单--------------");
    PledgeModel localPledgeModel = (PledgeModel)this.entity;
    String str = "select bs.stockid stockid,bs.warehouseid warehouseid,mb.breedname breedname,bs.quantity,bs.unit,bs.lasttime lasttime from BI_Stock bs,m_breed mb where mb.breedid = bs.breedId and stockid = '" + localPledgeModel.getBillID() + "' order by to_number(stockid)";
    List localList = getService().getListBySql(str);
    PageRequest localPageRequest = super.getPageRequest(this.request);
    Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
    this.request.setAttribute("pledgeID", localPledgeModel.getBillID());
    this.request.setAttribute("pageInfo", localPage);
    return "success";
  }
}