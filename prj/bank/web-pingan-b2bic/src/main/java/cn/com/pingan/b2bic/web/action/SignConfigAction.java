package cn.com.pingan.b2bic.web.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.generic.utils.CommonType;
import cn.com.agree.eteller.generic.utils.ConfigUtil;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.pingan.b2bic.web.IConfigManager;
import cn.com.pingan.b2bic.web.SignVo;
import cn.com.pingan.b2bic.web.vo.CommunicationVo;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class SignConfigAction
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource
  private IConfigManager configManager;
  private SignVo signVo;
  private List<CommonType> signModes;
  private String signTradeCodes;
  
  @Action(value="B2Bi_SignConfig", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/pingan/SignConfig.jsp")})
  public String load()
    throws Exception
  {
    try
    {
      this.configManager.load(this.signVo = new SignVo());
      this.signTradeCodes = StringUtils.join(this.signVo.getSignTradeCode(), "\n");
      logger.debug("signVo:" + this.signVo);
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward("加载配置失败");
      return "dwz";
    }
    return "success";
  }
  
  @Action("B2Bi_SignConfig_save")
  public String save()
    throws Exception
  {
    try
    {
      this.signVo.setSignTradeCode(Arrays.asList(this.signTradeCodes.trim().split("\\s+")));
      this.configManager.save(this.signVo);
      this.dwzResp.successForward("保存配置成功");
      this.dwzResp.setCallbackType(null);
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
    }
    return "dwz";
  }
  
  @Action("SignModeCheck")
  public String signModeCheck()
    throws Exception
  {
    try
    {
      HttpClient client = new HttpClient();
      String url = "http://127.0.0.1:" + getServerPort() + "/service/";
      logger.debug("url: " + url);
      

      CommunicationVo requestVo = new CommunicationVo();
      requestVo.setTradeCode("000001");
      requestVo.putParam("signMode", this.signVo.getSignMode());
      requestVo.putParam("certDn", this.signVo.getCertDn());
      PostMethod postMethod = new PostMethod(url);
      postMethod.addParameter("json", requestVo.toJson());
      

      client.executeMethod(postMethod);
      
      String resp = postMethod.getResponseBodyAsString().trim();
      
      postMethod.releaseConnection();
      logger.info("resp: " + resp);
      

      CommunicationVo respVo = CommunicationVo.getInstancefromJson(resp);
      if (!"0000".equals(respVo.getStatus())) {
        throw new ServiceException(respVo.getMessage());
      }
      this.dwzResp.ajaxSuccessForward(respVo.getMessage());
      this.dwzResp.setAlertClose(Boolean.valueOf(false));
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward(e.getMessage());
      return "dwz";
    }
    return "dwz";
  }
  
  @Action(value="FindDnList", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/pingan/SignDnList.jsp")})
  public String findDnList()
  {
    try
    {
      HttpClient client = new HttpClient();
      String url = "http://127.0.0.1:" + getServerPort() + "/service/";
      logger.debug("url: " + url);
      

      CommunicationVo requestVo = new CommunicationVo();
      requestVo.setTradeCode("000002");
      requestVo.putParam("signMode", this.signVo.getSignMode());
      
      PostMethod postMethod = new PostMethod(url);
      postMethod.addParameter("json", requestVo.toJson());
      

      client.executeMethod(postMethod);
      
      String resp = postMethod.getResponseBodyAsString().trim();
      
      postMethod.releaseConnection();
      logger.info("resp: " + resp);
      

      CommunicationVo respVo = CommunicationVo.getInstancefromJson(resp);
      if (!"0000".equals(respVo.getStatus())) {
        throw new ServiceException(respVo.getMessage());
      }
      this.req.setAttribute("dnList", respVo.getListParam("dnList"));
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward("获取证书DN列表失败");
      return "dwz";
    }
    return "success";
  }
  
  private String getServerPort()
    throws Exception
  {
    String configFile = new ConfigUtil("config/pingan.properties").getString("webserverPortConfigFile");
    Properties prop = new Properties();
    try
    {
      prop.load(new FileInputStream(new File(configFile)));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new ServiceException("获取http服务端口号失败，请检查pingan.properties是否配置webserverPortConfigFile属性");
    }
    return prop.getProperty("server_port");
  }
  
  public SignVo getSignVo()
  {
    return this.signVo;
  }
  
  public void setSignVo(SignVo signVo)
  {
    this.signVo = signVo;
  }
  
  public List<CommonType> getSignModes()
  {
    ConfigUtil config = new ConfigUtil("config/pingan.properties");
    String modes = config.getString("signMode");
    String[] modeArray = modes.split("\\s*;\\s*");
    List<CommonType> modeList = new ArrayList();
    for (String m : modeArray) {
      modeList.add(new CommonType(m.split("-")[0], m));
    }
    this.signModes = modeList;
    
    return this.signModes;
  }
  
  public void setSignModes(List<CommonType> signModes)
  {
    this.signModes = signModes;
  }
  
  public String getSignTradeCodes()
  {
    return this.signTradeCodes;
  }
  
  public void setSignTradeCodes(String signTradeCodes)
  {
    this.signTradeCodes = signTradeCodes;
  }
}
