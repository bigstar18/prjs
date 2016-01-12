package gnnt.MEBS.timebargain.mgr.action.printReport;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.timebargain.mgr.service.BrokerRewardService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("brokerAction")
@Scope("request")
public class BrokerAction extends EcsideAction
{
  private static final long serialVersionUID = 3833030598333460111L;
  private List listASC = new ArrayList();
  private List listDesc = new ArrayList();
  private List listBrokerIdDesc = new ArrayList();
  private List listBrokerIdAsc = new ArrayList();

  @Autowired
  @Qualifier("brokerRewardService")
  private BrokerRewardService brokerRewardService;

  public String getBrokerListOrder()
    throws Exception
  {
    List localList1 = this.brokerRewardService.getBrokerEndList();
    List localList2 = this.brokerRewardService.getBrokerStartList();
    this.request.setAttribute("listDesc", localList1);
    this.request.setAttribute("listAsc", localList2);
    return "success";
  }

  public BrokerRewardService getBrokerRewardService()
  {
    return this.brokerRewardService;
  }

  public void setBrokerRewardService(BrokerRewardService paramBrokerRewardService)
  {
    this.brokerRewardService = paramBrokerRewardService;
  }

  public List getListASC()
  {
    return this.listASC;
  }

  public void setListASC(List paramList)
  {
    this.listASC = paramList;
  }

  public List getListDesc()
  {
    return this.listDesc;
  }

  public void setListDesc(List paramList)
  {
    this.listDesc = paramList;
  }

  public List getListBrokerIdDesc()
  {
    return this.listBrokerIdDesc;
  }

  public void setListBrokerIdDesc(List paramList)
  {
    this.listBrokerIdDesc = paramList;
  }

  public List getListBrokerIdAsc()
  {
    return this.listBrokerIdAsc;
  }

  public void setListBrokerIdAsc(List paramList)
  {
    this.listBrokerIdAsc = paramList;
  }
}