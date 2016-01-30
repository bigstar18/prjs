package gnnt.MEBS.integrated.broker.action.brokerage;

import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.broker.action.EcsideAction;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.StandardService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("brokerageAction")
@Scope("request")
public class BrokerageAction extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  String brokerAgeStr = "";

  public String listByLimit()
  {
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.brokerId", "=", localUser.getUserId());
      listByLimit(localPageRequest);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String forwardAddBrokerage()
  {
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str1 = "select brokerAgeId,name from BR_Brokerage where brokerId= '" + localUser.getUserId() + "' order by brokerAgeId asc";
    List localList = getService().getListBySql(str1);
    this.request.setAttribute("list", localList);
    String str3 = "";
    String str2;
    if (localUser.getType().equals("0"))
    {
      str2 = localUser.getBroker().getBrokerId();
      str3 = localUser.getBroker().getName();
    }
    else
    {
      str2 = localUser.getBrokerAge().getBrokerAgeId();
      str3 = localUser.getBrokerAge().getName();
    }
    this.request.setAttribute("brokerId", str2);
    this.request.setAttribute("brokerName", str3);
    return "success";
  }

  public String viewByIdBrokerage()
  {
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    BrokerAge localBrokerAge = (BrokerAge)this.entity;
    this.brokerAgeStr = (this.brokerAgeStr + "'" + localBrokerAge.getBrokerAgeId() + "'");
    getList(localBrokerAge.getBrokerAgeId());
    String str = "select * from BR_Brokerage where brokerId= '" + localUser.getUserId() + "' and brokerageId not in (" + this.brokerAgeStr + ") order by brokerAgeId asc";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("brokerAgeList", localList);
    return viewById();
  }

  public void getList(String paramString)
  {
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str1 = "select * from BR_Brokerage where brokerId= '" + localUser.getUserId() + "' and pbrokerageId = '" + paramString + "' order by brokerAgeId asc";
    List localList = getService().getListBySql(str1);
    if (localList != null)
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        String str2 = (String)localMap.get("PBROKERAGEID");
        if (str2.equals(paramString))
        {
          String str3 = (String)localMap.get("BROKERAGEID");
          this.brokerAgeStr = (this.brokerAgeStr + ",'" + str3 + "'");
          getList(str3);
        }
      }
  }

  public String addBrokerage()
  {
    try
    {
      BrokerAge localBrokerAge = (BrokerAge)this.entity;
      String str1 = "select brokerAgeId,name from BR_Brokerage where brokerageId= '" + localBrokerAge.getBrokerAgeId() + "'";
      List localList1 = getService().getListBySql(str1);
      String str2 = "select brokerId from BR_Broker where brokerId= '" + localBrokerAge.getBrokerAgeId() + "'";
      List localList2 = getService().getListBySql(str2);
      if (((localList1 != null) && (localList1.size() > 0)) || ((localList2 != null) && (localList2.size() > 0)))
      {
        addReturnValue(0, 111401L);
      }
      else
      {
        String str3 = MD5.getMD5(localBrokerAge.getBrokerAgeId(), localBrokerAge.getPassword());
        localBrokerAge.setPassword(str3);
        localBrokerAge.setCreatTime(new Date());
        getService().add(localBrokerAge);
        addReturnValue(1, 119901L);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String updatePWD()
  {
    try
    {
      BrokerAge localBrokerAge = (BrokerAge)this.entity;
      String str = MD5.getMD5(localBrokerAge.getBrokerAgeId(), localBrokerAge.getPassword());
      localBrokerAge.setPassword(str);
      getService().update(localBrokerAge);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    addReturnValue(1, 111301L);
    return "success";
  }

  public String deleteBrokerage()
  {
    int i = 1;
    String[] arrayOfString = this.request.getParameterValues("ids");
    if ((arrayOfString != null) && (arrayOfString.length != 0))
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str1 = arrayOfString[j];
        String str2 = "select t.brokerageid from br_brokerage t where t.pbrokerageid= '" + str1 + "'";
        List localList1 = getService().getListBySql(str2);
        String str3 = "select * from br_brokerageandfirm t where t.brokerageid= '" + str1 + "'";
        List localList2 = getService().getListBySql(str3);
        if (((localList1 != null) && (localList1.size() > 0)) || ((localList2 != null) && (localList2.size() > 0)))
        {
          i = 0;
          break;
        }
      }
    if (i == 0)
      addReturnValue(0, 111402L);
    else
      try
      {
        deleteCollection();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return "success";
  }
}