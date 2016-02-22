package gnnt.MEBS.broke.action;

import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.MBrokerage;
import gnnt.MEBS.broke.service.MBrokerageService;
import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
public class MBrokerageAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MBrokerageAction.class);
  private InService inService;
  @Autowired
  @Qualifier("mBrokerageService")
  public MBrokerageService mBrokerageService;
  
  public InService getInService()
  {
    return this.inService;
  }
  
  public InService getService()
  {
    return this.mBrokerageService;
  }
  
  public String updateBroker()
  {
    this.logger.debug("updateBroker....");
    String memberNumber = (String)this.request.getSession().getAttribute("CURRENUSERID");
    String[] subMemberNo = memberNumber.split("_");
    String memberNo = subMemberNo[0];
    
    String[] str2 = this.request.getParameterValues("brokerageNo");
    System.out.println("str2---->" + str2);
    List<MBrokerage> list2 = new ArrayList();
    if ((str2 != null) && (str2.length > 0)) {
      for (int i = 0; i < str2.length; i++)
      {
        Brokerage brokerage = this.mBrokerageService.queryByNo(str2[i], memberNo);
        MBrokerage mBrokerage = this.mBrokerageService.change(brokerage);
        list2.add(mBrokerage);
      }
    }
    this.mBrokerageService.updateBroker(memberNo, list2);
    this.request.getSession().setAttribute("msg", "success");
    return getReturnValue();
  }
  
  public String queryMBroker()
  {
    this.logger.debug("queryMBroker....");
    String memberNumber = (String)this.request.getSession().getAttribute("CURRENUSERID");
    String[] subMemberNo = memberNumber.split("_");
    String memberNo = subMemberNo[0];
    List<Brokerage> list = this.mBrokerageService.queryBroker(memberNo);
    List<MBrokerage> list1 = this.mBrokerageService.queryMBroker(memberNo);
    System.out.println("list size----->" + list.size());
    list.removeAll(list1);
    
    System.out.println("list size----->" + list.size());
    String selFlag = "";
    if ((list1 != null) && (list1.size() > 0)) {
      for (int i = 0; i < list1.size(); i++)
      {
        System.out.println("list1.get(i).getBrokerageNo() : " + ((MBrokerage)list1.get(i)).getBrokerageNo());
        selFlag = selFlag + ((MBrokerage)list1.get(i)).getBrokerageNo() + ";";
      }
    }
    String msg = (String)this.request.getSession().getAttribute("msg");
    System.out.println("msg------>" + msg);
    if ("success".equals(msg))
    {
      this.request.setAttribute("msg", "success");
      this.request.getSession().removeAttribute("msg");
    }
    this.request.setAttribute("list", list);
    this.request.setAttribute("selFlag", selFlag);
    
    return getReturnValue();
  }
}
