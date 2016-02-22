package gnnt.MEBS.firm.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.firm.common.MD5;
import gnnt.MEBS.firm.common.SysData;
import gnnt.MEBS.firm.model.CustomerInfoAudit;
import gnnt.MEBS.firm.service.FirmService;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Service("m_firmService")
public class FirmController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(FirmController.class);
  private FirmService firmService;
  
  public ModelAndView goNotice(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    
    String memberno = request.getParameter("memberno");
    System.out.println("memberno----------------------------------------------------==============<>" + memberno);
    String yzm = (String)request.getSession().getAttribute("RANDOMICITYNUM");
    String verifyCode = request.getParameter("verifyCode");
    String registeredPhoneNo = request.getParameter("phone");
    if (verifyCode.equals(FirmManager.phoneCode.get(registeredPhoneNo)))
    {
      request.getSession().removeAttribute("SescardNumber");
      
      FirmManager.phoneCode.put(registeredPhoneNo, "");
      request.getSession().setAttribute("PhoneNo", registeredPhoneNo);
      

      HttpSession session = request.getSession();
      session.setAttribute("memberno", memberno);
      
      session.setAttribute("name", request.getParameter("name"));
      
      session.setAttribute("password", request.getParameter("password1"));
      


      session.setAttribute("papersType", request.getParameter("papersType"));
      
      session.setAttribute("papersName", request.getParameter("papersName"));
      
      session.setAttribute("phone", request.getParameter("phone"));
      
      session.setAttribute("email", request.getParameter("email"));
      
      session.setAttribute("postCode", request.getParameter("postCode"));
      

      session.setAttribute("address", request.getParameter("address"));
      
      String unputyzm = request.getParameter("validateCode");
      String msg = "";
      if ((yzm != null) && (!"".equals(yzm.trim())))
      {
        if (yzm.equalsIgnoreCase(unputyzm))
        {
          mv = forward(request, response);
          request.getSession().removeAttribute("RANDOMICITYNUM");
          FirmService firmService = (FirmService)SysData.getBean("m_firmService");
          







          session.setAttribute("memberRevelation", firmService.getMemberClobById(request.getParameter("memNo")));
          Map map = firmService.getMemberInforName(memberno);
          String memberName = (String)map.get("NAME");
          request.setAttribute("memberName", memberName);
        }
        else
        {
          msg = "验证码错误！";
          mv = new ModelAndView("./first");
          mv.addObject("msg", msg);
        }
      }
      else
      {
        msg = "验证码已过期！";
        mv = new ModelAndView("./first");
        mv.addObject("msg", msg);
      }
    }
    else
    {
      mv = new ModelAndView("./first");
      mv.addObject("msg", "您输入的手机验证码有误。请重新获取！");
    }
    return mv;
  }
  
  public ModelAndView goSelMember(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    String msg = "";
    


    HttpSession session = request.getSession();
    

    String bankAccount = request.getParameter("bankAccount");
    if ((bankAccount != null) && (!"".equals(bankAccount.trim())) && 
      (bankAccount.indexOf(" ") > 0)) {
      bankAccount = bankAccount.replace(" ", "");
    }
    session.setAttribute("bankAccount", request.getParameter("bankAccount"));
    
    session.setAttribute("bank", request.getParameter("bank"));
    String bankId = request.getParameter("bank");
    
    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    String memNo = (String)session.getAttribute("memberno");
    String firmId = firmService.getFirmId(memNo, "-1", "-1");
    
    int ret = 0;
    try
    {
      ret = firmService.insertCount(bankId, firmId, bankAccount, (String)session.getAttribute("name"), (String)session.getAttribute("papersName"), (String)session.getAttribute("papersType"));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ret = -1000;
    }
    if (ret == 0)
    {
      mv = new ModelAndView("./memberInfor");
      String memberNo = (String)request.getSession().getAttribute("memberno");
      
      List ListOrg = firmService.getOrganization(memberNo);
      
      List listBroke = firmService.getBroker(memberNo);
      request.setAttribute("ListOrg", ListOrg);
      request.setAttribute("listBroke", listBroke);
    }
    else if (ret == -1000)
    {
      msg = "校验异常，请通知市场人员！";
      mv = new ModelAndView("./bankInfor");
      mv.addObject("msg", msg);
    }
    else
    {
      msg = "银行卡号校验失败！";
      mv = new ModelAndView("./bankInfor");
      mv.addObject("msg", msg);
    }
    return mv;
  }
  
  public String checkedContacterPhoneNo(String ContacterPhoneNo)
  {
    boolean flag = this.firmService.checkedZSContacterPhoneNo(ContacterPhoneNo);
    System.out.println("flag++++++++++++++++++++++++++>>>>>>>>>>>>" + flag);
    if (flag) {
      return "1";
    }
    return "0";
  }
  
  public String PersonCode(String memberNo, String personNo)
  {
    boolean flag = this.firmService.PersonCode(memberNo, personNo);
    if (flag) {
      return "1";
    }
    return "0";
  }
  
  public ModelAndView goBank(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    
    HttpSession session = request.getSession();
    String memberno = (String)session.getAttribute("memberno");
    
    session.setAttribute("name", request.getParameter("name"));
    
    session.setAttribute("password", request.getParameter("password1"));
    
    session.setAttribute("papersType", request.getParameter("papersType"));
    
    session.setAttribute("papersName", request.getParameter("papersName"));
    
    session.setAttribute("phone", request.getParameter("phone"));
    
    session.setAttribute("email", request.getParameter("email"));
    
    session.setAttribute("postCode", request.getParameter("postCode"));
    
    String address = request.getParameter("address");
    session.setAttribute("address", address);
    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    
    firmService.getAllBank();
    boolean ph = firmService.checkedZSContacterPhoneNo(request.getParameter("phone"));
    
    boolean pc = firmService.PersonCode(memberno, request.getParameter("papersName"));
    System.out.println("pc----------->" + pc);
    if (!pc)
    {
      mv = new ModelAndView("./customeInfor");
      mv.addObject("msg", "此身份证号已经在该会员下注册，请更换！");
    }
    if (!ph)
    {
      mv = new ModelAndView("./customeInfor");
      mv.addObject("msg", "此手机号码已被注册，请更换！");
    }
    if ((pc) && (ph)) {
      mv = new ModelAndView("./bankInfor");
    }
    return mv;
  }
  
  public ModelAndView goPromise(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    
    String customerno = request.getParameter("customerno");
    
    String passwords = request.getParameter("password");
    String msg = "";
    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    Map map = firmService.getCustomerinfor(customerno);
    if ((map.size() != 0) && (map != null))
    {
      String pwd = (String)map.get("password");
      request.setAttribute("CustomerName", (String)map.get("name"));
      Map map1 = firmService.getCustomInfo(customerno);
      String memberNo = (String)map1.get("memberno");
      
      Map map2 = firmService.getMemberInfors(memberNo);
      String memberName = (String)map2.get("name");
      String memberAddress = (String)map2.get("address");
      String memberPhone = (String)map2.get("phone");
      String password = MD5.getMD5(customerno, passwords);
      if (password.equalsIgnoreCase(pwd))
      {
        List list1 = firmService.checkMember(memberNo);
        if ((list1.size() > 0) && (list1 != null))
        {
          mv = new ModelAndView("./notice");
          
          HttpSession session = request.getSession();
          session.setAttribute("customerno", customerno);
          session.setAttribute("memberName", memberName);
          session.setAttribute("memberAddress", memberAddress);
          session.setAttribute("memberPhone", memberPhone);
          List list2 = firmService.checkCustomNo(customerno);
          if ((list2.size() > 0) && (list2 != null))
          {
            request.setAttribute("msg", "该模拟账号已经注册过,请联系市场！");
            mv = new ModelAndView("./noMember");
          }
          else if (firmService.checkMemberStatue(memberNo))
          {
            session.setAttribute("moNiCustomNo", customerno);
          }
          else
          {
            request.setAttribute("msg", "该会员暂时停止服务！");
            mv = new ModelAndView("./noMember");
          }
        }
        else
        {
          request.setAttribute("msg", "您的所属会员还未在正式环境注册！");
          mv = new ModelAndView("./noMember");
        }
      }
      else
      {
        msg = "账户或密码不正确！";
        mv = new ModelAndView("./first1");
        mv.addObject("msg", msg);
      }
    }
    else
    {
      msg = "账户或密码不正确！";
      mv = new ModelAndView("./first1");
      mv.addObject("msg", msg);
    }
    return mv;
  }
  
  public ModelAndView goWarning(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    
    String organization = request.getParameter("organization");
    
    String broker = request.getParameter("broker");
    request.getSession().setAttribute("broker", broker);
    request.getSession().setAttribute("organization", organization);
    mv = new ModelAndView("./warning");
    return mv;
  }
  
  public ModelAndView goAgreement(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    mv = new ModelAndView("./agreement");
    return mv;
  }
  
  public ModelAndView goRegister(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    







    mv = forward(request, response);
    return mv;
  }
  
  public ModelAndView goCustomInfor(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = null;
    








    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    String customerno = (String)request.getSession().getAttribute("customerno");
    
    Map map = firmService.getCustomInfo(customerno);
    request.getSession().setAttribute("memberno", map.get("memberno"));
    request.setAttribute("name", map.get("name"));
    request.setAttribute("papersType", map.get("papersType"));
    request.setAttribute("papersName", map.get("paperscode"));
    request.setAttribute("email", map.get("email"));
    request.setAttribute("phone", map.get("phone"));
    request.setAttribute("postCode", map.get("postCode"));
    request.setAttribute("address", map.get("address"));
    mv = new ModelAndView("./customeInfor");
    return mv;
  }
  
  public ModelAndView forward(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView("./register-personal");
    
    return mv;
  }
  
  public ModelAndView eidtFirm(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    this.logger.debug("enter eidtFirm method.....");
    ModelAndView mv = null;
    String msg = "";
    



    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    

















    String memberNo = request.getParameter("memberno");
    Map map = firmService.getMemberInfor(memberNo);
    String hrefaddress = (String)map.get("hrefaddress");
    request.getSession().setAttribute("hrefaddress", hrefaddress);
    

    HttpSession session = request.getSession();
    
    CustomerInfoAudit auditInfo = new CustomerInfoAudit();
    auditInfo.setName((String)session.getAttribute("name"));
    auditInfo.setMemberNo(request.getParameter("memberno"));
    auditInfo.setPapersType(Integer.valueOf(Integer.parseInt((String)session.getAttribute("papersType"))));
    auditInfo.setPapersName(request.getParameter("papersName"));
    auditInfo.setStatus("C");
    auditInfo.setPhonePwd((String)session.getAttribute("password"));
    auditInfo.setAddress((String)session.getAttribute("address"));
    auditInfo.setPhone((String)session.getAttribute("phone"));
    auditInfo.setPostCode((String)session.getAttribute("postCode"));
    auditInfo.setEmail((String)session.getAttribute("email"));
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
    customer.setPassword((String)session.getAttribute("password"));
    customer.setPhone(auditInfo.getPhone());
    customer.setPhonePWD(auditInfo.getPhonePwd());
    customer.setPostCode(auditInfo.getPostCode());
    
    CustomerMappingBroker customerMappingBroker = new CustomerMappingBroker();
    CopyObjectParamUtil.bindData(auditInfo, customerMappingBroker);
    

    boolean exist = firmService.checkedCardNumber((String)request.getSession().getAttribute("papersName"), (String)request.getSession().getAttribute("memberNos"));
    boolean flag = firmService.checkedContacterPhoneNo((String)session.getAttribute("phone"));
    if (!exist)
    {
      msg = "您已注册过，请不要重复提交";
      
      mv = new ModelAndView("./sameData");
      mv.addObject("msg", msg);
      return mv;
    }
    if (!flag)
    {
      msg = "您已注册过，请不要重复提交";
      
      mv = new ModelAndView("./sameData");
      mv.addObject("msg", msg);
      return mv;
    }
    int num = 0;
    if (num == -600)
    {
      msg = "该会员暂时停止服务！";
      mv = forward(request, response);
      mv.addObject("msg", msg);
    }
    else if (num == 0)
    {
      msg = "没有找到该会员单位！";
      mv = forward(request, response);
      mv.addObject("msg", msg);
    }
    else if (num == 2)
    {
      String customerNo = customer.getCustomerNo();
      FirmManager firmManager = new FirmManager();
      firmManager.sendCustomNo((String)session.getAttribute("name"), customer.getPhone(), customerNo);
      request.setAttribute("customerNo", customer.getCustomerNo());
      request.getSession().setAttribute("customerNo", customer.getCustomerNo());
      mv = new ModelAndView("./dispacther", "msg", "信息提交成功");
    }
    return mv;
  }
  
  public ModelAndView goZSSuccess(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    this.logger.debug("enter eidtZSFirm method.....");
    ModelAndView mv = null;
    String msg = "";
    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    HttpSession session = request.getSession();
    








    String memberNo = (String)request.getSession().getAttribute("memberno");
    Map map = firmService.getMemberInfor(memberNo);
    String zshrefaddress = (String)map.get("zshrefaddress");
    
    request.getSession().setAttribute("zshrefaddress", zshrefaddress);
    
    String organization = (String)request.getSession().getAttribute("organization");
    
    String broker = (String)request.getSession().getAttribute("broker");
    
    System.out.println("正式注册过程，，，，，，，，获得编号前");
    String customerNo = firmService.getFirmId(memberNo, organization, broker);
    System.out.println("正式注册过程，，，，，，，，获得编号后 ；  customerNo-------》 " + customerNo);
    String moNiCustomNo = (String)request.getSession().getAttribute("moNiCustomNo");
    if (!"-1".equals(customerNo))
    {
      request.getSession().setAttribute("customerNo", customerNo);
      

      CustomerInfoAudit auditInfo = new CustomerInfoAudit();
      auditInfo.setCustomerNo(customerNo);
      auditInfo.setBank((String)session.getAttribute("bank"));
      auditInfo.setBankAccount((String)session.getAttribute("bankAccount"));
      auditInfo.setName((String)session.getAttribute("name"));
      auditInfo.setMemberNo((String)session.getAttribute("memberno"));
      auditInfo.setPapersType(Integer.valueOf(Integer.parseInt((String)session.getAttribute("papersType"))));
      auditInfo.setPapersName((String)session.getAttribute("papersName"));
      auditInfo.setStatus("C");
      auditInfo.setPhonePwd((String)session.getAttribute("password"));
      auditInfo.setAddress((String)session.getAttribute("address"));
      auditInfo.setPhone((String)session.getAttribute("phone"));
      auditInfo.setPostCode((String)session.getAttribute("postCode"));
      auditInfo.setEmail((String)session.getAttribute("email"));
      auditInfo.setCreateTime(new Date());
      auditInfo.setModifyTime(new Date());
      

      Customer customer = new Customer();
      customer.setAddress(auditInfo.getAddress());
      customer.setCreateTime(auditInfo.getModifyTime());
      customer.setCustomerNo(auditInfo.getCustomerNo());
      CustomerStatus customerStatus = new CustomerStatus();
      customerStatus.setStatus(auditInfo.getStatus());
      customerStatus.setCustomerNo(auditInfo.getCustomerNo());
      customer.setCustomerStatus(customerStatus);
      customer.setEmail(auditInfo.getEmail());
      
      customer.setMemberNo(auditInfo.getMemberNo());
      customer.setName(auditInfo.getName());
      customer.setPapersName(auditInfo.getPapersName());
      customer.setPapersType(auditInfo.getPapersType());
      customer.setPassword((String)session.getAttribute("password"));
      customer.setPhone(auditInfo.getPhone());
      customer.setPhonePWD(auditInfo.getPhonePwd());
      customer.setPostCode(auditInfo.getPostCode());
      
      CustomerMappingBroker customerMappingBroker = new CustomerMappingBroker();
      CopyObjectParamUtil.bindData(auditInfo, customerMappingBroker);
      customerMappingBroker.setCustomerNo(customer.getCustomerNo());
      if ("-1".equals(broker)) {
        customerMappingBroker.setBrokerageNo("");
      } else {
        customerMappingBroker.setBrokerageNo(broker);
      }
      customerMappingBroker.setMemberNo(memberNo);
      if ("-1".equals(organization)) {
        customerMappingBroker.setOrganizationNo("");
      } else {
        customerMappingBroker.setOrganizationNo(organization);
      }
      int num = 0;
      boolean exist = firmService.zscheckedCardNumber((String)request.getSession().getAttribute("papersName"), (String)request.getSession().getAttribute("memberNos"));
      boolean flag = firmService.checkedZSContacterPhoneNo((String)session.getAttribute("phone"));
      if (!exist)
      {
        msg = "您已注册过，请不要重复提交";
        
        mv = new ModelAndView("./noMember");
        mv.addObject("msg", msg);
        return mv;
      }
      if (!flag)
      {
        msg = "您已注册过，请不要重复提交";
        
        mv = new ModelAndView("./noMember");
        mv.addObject("msg", msg);
        return mv;
      }
      num = firmService.goZSSuccess(moNiCustomNo, organization, broker, customer, customerMappingBroker);
      if (num == -600)
      {
        msg = "该会员暂时停止服务！";
        
        mv = new ModelAndView("./noMember");
        mv.addObject("msg", msg);
      }
      else if (num == 0)
      {
        msg = "没有找到该会员单位！";
        mv = new ModelAndView("./noMember");
        mv.addObject("msg", msg);
      }
      else if (num == 2)
      {
        String customerNos = customer.getCustomerNo();
        FirmManager firmManager = new FirmManager();
        firmManager.sendZSCustomNo((String)session.getAttribute("name"), customer.getPhone(), customerNos);
        request.setAttribute("customerNo", customer.getCustomerNo());
        request.getSession().setAttribute("customerNo", customer.getCustomerNo());
        mv = new ModelAndView("./zssuccess", "msg", "信息提交成功");
      }
    }
    else
    {
      msg = "请联系市场，维护后台数据！";
      mv = new ModelAndView("./error");
      mv.addObject("msg", msg);
    }
    return mv;
  }
  
  public static String getRandomCode()
  {
    Random random = new Random();
    String allChar = "0123456789";
    StringBuffer randomCode = new StringBuffer();
    for (int length = 0; length < 6; length++) {
      randomCode.append(allChar.charAt(random.nextInt(allChar.length())));
    }
    return randomCode.toString();
  }
  
  public static String IDCardValidate(String IDStr1)
    throws ParseException
  {
    String IDStr = IDStr1.replace("x", "X");
    String errorInfo = "";
    String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", 
      "3", "2" };
    String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", 
      "9", "10", "5", "8", "4", "2" };
    String Ai = "";
    if (IDStr.length() != 18)
    {
      errorInfo = "身份证号码长度应该为18位。";
      return errorInfo;
    }
    if (IDStr.length() == 18) {
      Ai = IDStr.substring(0, 17);
    } else if (IDStr.length() == 15) {
      Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
    }
    if (!isNumeric(Ai))
    {
      errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
      return errorInfo;
    }
    String strYear = Ai.substring(6, 10);
    String strMonth = Ai.substring(10, 12);
    String strDay = Ai.substring(12, 14);
    if (!isDate(strYear + "-" + strMonth + "-" + strDay))
    {
      errorInfo = "身份证生日无效。";
      return errorInfo;
    }
    GregorianCalendar gc = new GregorianCalendar();
    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      if ((gc.get(1) - Integer.parseInt(strYear) > 150) || 
      
        (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime() < 0L)) {
        return "身份证生日不在有效范围。";
      }
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    if ((Integer.parseInt(strMonth) > 12) || (Integer.parseInt(strMonth) == 0))
    {
      errorInfo = "身份证月份无效";
      return errorInfo;
    }
    if ((Integer.parseInt(strDay) > 31) || (Integer.parseInt(strDay) == 0))
    {
      errorInfo = "身份证日期无效";
      return errorInfo;
    }
    Hashtable h = GetAreaCode();
    if (h.get(Ai.substring(0, 2)) == null)
    {
      errorInfo = "身份证地区编码错误。";
      return errorInfo;
    }
    int TotalmulAiWi = 0;
    for (int i = 0; i < 17; i++) {
      TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * 
        Integer.parseInt(Wi[i]);
    }
    int modValue = TotalmulAiWi % 11;
    String strVerifyCode = ValCodeArr[modValue];
    Ai = Ai + strVerifyCode;
    if (IDStr.length() == 18)
    {
      if (!Ai.equals(IDStr))
      {
        errorInfo = "身份证无效，不是合法的身份证号码";
        return errorInfo;
      }
    }
    else {
      return "";
    }
    return "";
  }
  
  private static Hashtable GetAreaCode()
  {
    Hashtable hashtable = new Hashtable();
    hashtable.put("11", "北京");
    hashtable.put("12", "天津");
    hashtable.put("13", "河北");
    hashtable.put("14", "山西");
    hashtable.put("15", "内蒙古");
    hashtable.put("21", "辽宁");
    hashtable.put("22", "吉林");
    hashtable.put("23", "黑龙江");
    hashtable.put("31", "上海");
    hashtable.put("32", "江苏");
    hashtable.put("33", "浙江");
    hashtable.put("34", "安徽");
    hashtable.put("35", "福建");
    hashtable.put("36", "江西");
    hashtable.put("37", "山东");
    hashtable.put("41", "河南");
    hashtable.put("42", "湖北");
    hashtable.put("43", "湖南");
    hashtable.put("44", "广东");
    hashtable.put("45", "广西");
    hashtable.put("46", "海南");
    hashtable.put("50", "重庆");
    hashtable.put("51", "四川");
    hashtable.put("52", "贵州");
    hashtable.put("53", "云南");
    hashtable.put("54", "西藏");
    hashtable.put("61", "陕西");
    hashtable.put("62", "甘肃");
    hashtable.put("63", "青海");
    hashtable.put("64", "宁夏");
    hashtable.put("65", "新疆");
    hashtable.put("71", "台湾");
    hashtable.put("81", "香港");
    hashtable.put("82", "澳门");
    hashtable.put("91", "国外");
    return hashtable;
  }
  
  private static boolean isNumeric(String str)
  {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (isNum.matches()) {
      return true;
    }
    return false;
  }
  
  public static boolean isDate(String strDate)
  {
    Pattern pattern = 
      Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
    Matcher m = pattern.matcher(strDate);
    if (m.matches()) {
      return true;
    }
    return false;
  }
  
  public static String getTrace(Throwable t)
  {
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    t.printStackTrace(writer);
    StringBuffer buffer = stringWriter.getBuffer();
    return buffer.toString();
  }
}
