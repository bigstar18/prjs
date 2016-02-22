package gnnt.MEBS.firm.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.firm.common.SysData;
import gnnt.MEBS.firm.model.CustomerInfoAudit;
import gnnt.MEBS.firm.service.FirmService;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class WeiXinAddFirm
  extends MultiActionController
{
  FirmService firmService = (FirmService)SysData.getBean("m_firmService");
  
  public ModelAndView intiFirmInfo(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, UnsupportedEncodingException
  {
    ModelAndView mv = null;
    String msg = "";
    String openid = request.getParameter("openid");
    String redirect = request.getParameter("credirect");
    String nikeName = request.getParameter("nikeName");
    String regionType = request.getParameter("regionType");
    request.getSession().removeAttribute("regionType");
    openid = nikeName = "4211214";
    request.getSession().setAttribute("openid", openid);
    request.getSession().setAttribute("nikeName", nikeName);
    System.out.println(openid + "=====" + nikeName + "=====" + redirect + "=====" + regionType);
    if ((openid == null) || (openid.equals("")) || (nikeName == null) || (nikeName.equals("")))
    {
      msg = "请在微信端打开!";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    List frimBindingList = this.firmService.getFrimBindingInfo(openid);
    if ((frimBindingList != null) && (frimBindingList.size() > 0))
    {
      msg = "您已绑定交易商账号，无需重复开户！";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    List<Map<Object, Object>> agreementlist = this.firmService.getAgreements();
    if ((agreementlist != null) && (agreementlist.size() > 0))
    {
      Map<Object, Object> agreementMap = (Map)agreementlist.get(0);
      
      request.getSession().setAttribute("agreementMap", agreementMap);
    }
    if ((regionType == null) || (regionType.equals("")))
    {
      mv = new ModelAndView("./zswap/registered");
      return mv;
    }
    List<Map<Object, Object>> list = this.firmService.getMemberIdAndName();
    String str = "";
    for (int i = 0; i < list.size(); i++)
    {
      Map map = (Map)list.get(i);
      str = str + "<option value='" + map.get("memberno") + "'>" + map.get("name") + "</option>";
    }
    request.setAttribute("members", str);
    mv = new ModelAndView("./wap/registered");
    
    return mv;
  }
  
  public ModelAndView eidtFirm(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    String msg = "";
    String openid = (String)request.getSession().getAttribute("openid");
    String nikeName = (String)request.getSession().getAttribute("nikeName");
    if ((openid == null) || (openid.equals("")))
    {
      msg = "请从微信端打开！";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    List frimBindingList = this.firmService.getFrimBindingInfo(openid);
    if ((frimBindingList != null) && (frimBindingList.size() > 0))
    {
      msg = "您已绑定交易商账号，无需重复开户！";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    CustomerInfoAudit auditInfo = new CustomerInfoAudit();
    String memberNo = request.getParameter("memberNo");
    Map map = this.firmService.getMemberInfor(memberNo);
    request.getSession().setAttribute("memberInfo", map);
    auditInfo.setName(request.getParameter("fullName"));
    auditInfo.setMemberNo(memberNo);
    auditInfo.setPapersType(Integer.valueOf(Integer.parseInt(request.getParameter("papersType"))));
    auditInfo.setPapersName(request.getParameter("papersName"));
    auditInfo.setStatus("C");
    auditInfo.setPhonePwd("111111");
    auditInfo.setAddress(request.getParameter("address"));
    auditInfo.setPhone(request.getParameter("phone"));
    auditInfo.setPostCode(request.getParameter("postCode"));
    auditInfo.setEmail(request.getParameter("email"));
    auditInfo.setCreateTime(new Date());
    auditInfo.setModifyTime(new Date());
    
    Customer customer = new Customer();
    customer.setAddress(auditInfo.getAddress());
    customer.setCreateTime(auditInfo.getModifyTime());
    
    CustomerStatus customerStatus = new CustomerStatus();
    customerStatus.setStatus(auditInfo.getStatus());
    customerStatus.setCustomerNo(auditInfo.getCustomerNo());
    customer.setCustomerStatus(customerStatus);
    customer.setEmail(auditInfo.getEmail());
    
    customer.setMemberNo(auditInfo.getMemberNo());
    customer.setName(auditInfo.getName());
    customer.setPapersName(auditInfo.getPapersName());
    customer.setPapersType(auditInfo.getPapersType());
    customer.setPassword("111111");
    customer.setPhone(auditInfo.getPhone());
    customer.setPhonePWD(auditInfo.getPhonePwd());
    customer.setPostCode(auditInfo.getPostCode());
    CustomerMappingBroker customerMappingBroker = new CustomerMappingBroker();
    CopyObjectParamUtil.bindData(auditInfo, customerMappingBroker);
    boolean exist = this.firmService.checkedCardNumber(customer.getPapersName(), customer.getMemberNo());
    boolean flag = this.firmService.checkedContacterPhoneNo(customer.getPhone());
    if ((!exist) || (!flag))
    {
      msg = "您已注册过，请不要重复提交";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    int num = this.firmService.add(customer, customerMappingBroker, openid, nikeName);
    if (num == -600)
    {
      msg = "该会员暂时停止服务！";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
    }
    else if (num == 0)
    {
      msg = "没有找到该会员单位！";
      mv = new ModelAndView("./wap/weixinInfo");
      mv.addObject("msg", msg);
    }
    else if (num == 2)
    {
      request.setAttribute("customer", customer);
      mv = new ModelAndView("./wap/dispacther", "msg", "信息提交成功");
    }
    return mv;
  }
}
