package gnnt.MEBS.firm.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.firm.common.SysData;
import gnnt.MEBS.firm.model.CustomerInfoAudit;
import gnnt.MEBS.firm.service.FirmService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class WeiXinAddZSFirm
  extends MultiActionController
{
  FirmService firmService = (FirmService)SysData.getBean("m_firmService");
  
  public ModelAndView eidtFirm(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String brokerage = request.getParameter("brokerage");
    Map map = this.firmService.getMemberForBrokerage(brokerage);
    if ((map == null) || (map.get("MEMBERNO") == null))
    {
      ModelAndView mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", "未查询到该居间商！");
      return mv;
    }
    String phonecode = request.getParameter("verifyCode");
    String str = (String)FirmManager.phoneCode.get(request.getParameter("phone"));
    if ((phonecode == null) || (phonecode.equals("")) || (!phonecode.equals(str)))
    {
      ModelAndView mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", "手机验证码错误！");
      return mv;
    }
    FirmManager.phoneCode.remove(request.getParameter("phone"));
    CustomerInfoAudit auditInfo = new CustomerInfoAudit();
    request.getSession().setAttribute("memberInfo", map);
    auditInfo.setBankAccount(request.getParameter("bankno"));
    auditInfo.setName(request.getParameter("fullName"));
    auditInfo.setMemberNo(map.get("MEMBERNO").toString());
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
    request.getSession().setAttribute("auditInfo", auditInfo);
    ModelAndView mv = new ModelAndView("./zswap/agreement1");
    return mv;
  }
  
  public ModelAndView hrefagreement2(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView("./zswap/agreement2");
    return mv;
  }
  
  public ModelAndView saveFrim(HttpServletRequest request, HttpServletResponse response)
    throws UnsupportedEncodingException
  {
    ModelAndView mv = null;
    String msg = "";
    String openid = (String)request.getSession().getAttribute("openid");
    String nikeName = (String)request.getSession().getAttribute("nikeName");
    if ((openid == null) || (openid.equals("")))
    {
      msg = "请从微信端打开！";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    List frimBindingList = this.firmService.getFrimBindingInfo(openid);
    if ((frimBindingList != null) && (frimBindingList.size() > 0))
    {
      msg = "您已绑定交易商账号，无需重复开户！";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    CustomerInfoAudit auditInfo = (CustomerInfoAudit)request.getSession().getAttribute("auditInfo");
    String firmId = this.firmService.getFirmId(auditInfo.getMemberNo(), "-1", "-1");
    int ret = 0;
    try
    {
      ret = this.firmService.insertCount("010", firmId, auditInfo.getBankAccount(), auditInfo.getName(), auditInfo.getPapersName(), auditInfo.getPapersType().toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ret = -1000;
    }
    if (ret == -1000)
    {
      msg = "校验异常，请通知市场人员！";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    if (ret != 0)
    {
      msg = "银行卡号校验失败！";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
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
    boolean exist = this.firmService.zscheckedCardNumber(customer.getPapersName(), customer.getMemberNo());
    boolean flag = this.firmService.checkedZSContacterPhoneNo(customer.getPhone());
    if ((exist) || (flag))
    {
      msg = "您已注册过，请不要重复提交";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
      return mv;
    }
    int num = this.firmService.addZS("", "", customer, customerMappingBroker, openid, nikeName);
    if (num == -600)
    {
      msg = "该会员暂时停止服务！";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
    }
    else if (num == 0)
    {
      msg = "没有找到该会员单位！";
      mv = new ModelAndView("./zswap/weixinInfo");
      mv.addObject("msg", msg);
    }
    else if (num == 2)
    {
      String customerNo = customer.getCustomerNo();
      FirmManager firmManager = new FirmManager();
      firmManager.sendCustomNo((String)request.getAttribute("name"), customer.getPhone(), customerNo);
      request.setAttribute("customer", customer);
      mv = new ModelAndView("./zswap/dispacther", "msg", "信息提交成功");
      request.getSession().removeAttribute("auditInfo");
    }
    return mv;
  }
  
  public ModelAndView savePice(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    ModelAndView mv = new ModelAndView("./zswap/first");
    String msg = "";
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    
    MultipartFile imgFile1 = multipartRequest.getFile("sfz");
    byte[] fileBytes = imgFile1.getBytes();
    System.out.println(imgFile1.getSize() + "============size");
    if (imgFile1.getSize() > 1256000L)
    {
      msg = "上传图片不能大于1M！，请压缩图片后再上传！";
      mv.addObject("resultMsg", msg);
      return mv;
    }
    String URL = System.getProperty("user.dir") + "/update/" + new Date().getTime() + ".jpg";
    File file = new File(URL);
    
    FileOutputStream fos = new FileOutputStream(file);
    
    fos.write(fileBytes, 0, fileBytes.length);
    
    fos.flush();
    
    fos.close();
    System.out.println(URL);
    mv.addObject("resultMsg", msg);
    return mv;
  }
}
