package gnnt.MEBS.integrated.broker.action.brokerage;

import gnnt.MEBS.common.broker.action.EcsideAction;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.integrated.broker.model.brokerage.BrokerageAndFirm;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("brokerageAndFirmAction")
@Scope("request")
public class BrokerageAndFirmAction extends EcsideAction
{
  private static final long serialVersionUID = 1L;

  @Resource(name="mfirmTypeMap")
  protected Map<Integer, String> mfirmTypeMap;

  public Map<Integer, String> getMfirmTypeMap()
  {
    return this.mfirmTypeMap;
  }

  public String listBroAndFirm()
  {
    try
    {
      PageRequest localPageRequest = super.getPageRequest(this.request);
      String str = this.request.getParameter("brokerAgeId");
      QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
      localQueryConditions.addCondition("primary.brokerAgeId", "=", str);
      localQueryConditions.addCondition("primary.bindType", "=", "0");
      listByLimit(localPageRequest);
      this.request.setAttribute("brokerAgeId", str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String deleteBroAndFirm()
  {
    try
    {
      String[] arrayOfString = this.request.getParameterValues("ids");
      if ((arrayOfString != null) && (arrayOfString.length != 0))
        for (int i = 0; i < arrayOfString.length; i++)
        {
          String str1 = arrayOfString[i];
          if ((str1 != null) && (!str1.equals("")))
          {
            String str2 = "delete from br_brokerageandfirm t where t.firmid='" + str1 + "'";
            getService().executeUpdateBySql(str2);
          }
        }
      addReturnValue(1, 111403L);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String forwardManageFirmAdd()
  {
    String str = this.request.getParameter("brokerAgeId");
    this.request.setAttribute("brokerAgeId", str);
    return "success";
  }

  public String addManagerFirm()
  {
    int i = 0;
    int j = 0;
    String str1 = "";
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    BrokerageAndFirm localBrokerageAndFirm = (BrokerageAndFirm)this.entity;
    if ((localBrokerageAndFirm != null) && (localBrokerageAndFirm.getFirmId() != null) && (!localBrokerageAndFirm.getFirmId().equals("")))
    {
      str1 = localBrokerageAndFirm.getFirmId();
      String str2 = localUser.getSql() + " and firmid='" + str1 + "'";
      List localList1 = getService().getListBySql(str2);
      i = localList1.size();
      if (i != 0)
      {
        String str3 = "select * from br_brokerageandfirm t where t.firmid=(" + str2 + ")";
        List localList2 = getService().getListBySql(str3);
        j = localList2.size();
      }
    }
    if (i == 0)
      addReturnValue(0, 111404L);
    else if (j > 0)
      addReturnValue(0, 111405L);
    else
      try
      {
        addBroAndFirm(localBrokerageAndFirm);
        addReturnValue(1, 119901L);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return "success";
  }

  public void addBroAndFirm(BrokerageAndFirm paramBrokerageAndFirm)
  {
    paramBrokerageAndFirm.setBindTime(new Date());
    paramBrokerageAndFirm.setBindType("0");
    getService().add(paramBrokerageAndFirm);
    addPBroAndFirm(paramBrokerageAndFirm.getBrokerAgeId(), paramBrokerageAndFirm.getFirmId());
  }

  public void addPBroAndFirm(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (!paramString1.equals("")) && (paramString2 != null) && (!paramString2.equals("")))
    {
      BrokerAge localBrokerAge = new BrokerAge();
      localBrokerAge.setBrokerAgeId(paramString1);
      localBrokerAge = (BrokerAge)getService().get(localBrokerAge);
      if ((localBrokerAge != null) && (localBrokerAge.getPbrokerAgeId() != null))
      {
        BrokerageAndFirm localBrokerageAndFirm = new BrokerageAndFirm();
        localBrokerageAndFirm.setBrokerAgeId(localBrokerAge.getPbrokerAgeId());
        localBrokerageAndFirm.setFirmId(paramString2);
        localBrokerageAndFirm.setBindTime(new Date());
        localBrokerageAndFirm.setBindType("1");
        getService().add(localBrokerageAndFirm);
        addPBroAndFirm(localBrokerAge.getPbrokerAgeId(), paramString2);
      }
    }
  }
}