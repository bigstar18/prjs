package cn.com.pingan.b2bic.web.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.ConfigUtil;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.pingan.b2bic.web.IConfigManager;
import cn.com.pingan.b2bic.web.LogLvLVo;
import cn.com.pingan.b2bic.web.service.LogService;
import cn.com.pingan.b2bic.web.vo.LogFileVo;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

public class LogConfigAction
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource
  private IConfigManager configManager;
  @Resource
  private LogService logService;
  private LogLvLVo logLvLVo;
  private InputStream download;
  private List<LogFileVo> list;
  private Map<String, String> condition = new HashMap();
  private DecimalFormat fileSizeFmt = new DecimalFormat("#0.00");
  
  @Action(value="B2Bi_LogConfig", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/pingan/LogConfig.jsp")})
  public String load()
    throws Exception
  {
    try
    {
      this.configManager.load(this.logLvLVo = new LogLvLVo());
      logger.debug("logLvLVo:" + this.logLvLVo);
      try
      {
        if (this.rollPage.booleanValue())
        {
          this.list = ((List)this.session.getAttribute("list"));
        }
        else
        {
          String logDir = new ConfigUtil("config/pingan.properties").getString("logDir");
          
          this.list = this.logService.getLogFiles(logDir, this.condition);
          this.session.setAttribute("list", this.list);
        }
        this.list = Pagination.splitPageWithAllRecords(this.list, this.page);
      }
      catch (Exception e)
      {
        logger.warn("获取日志列表失败：" + ExceptionUtils.getStackTrace(e));
      }
      return "success";
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      this.dwzResp.errorForward("加载配置失败");
      return "dwz";
    }
  }
  
  @Action("B2Bi_LogConfig_save")
  public String save()
    throws Exception
  {
    try
    {
      this.configManager.save(this.logLvLVo);
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
  
  @Action(value="B2Bi_LogConfig_downloadLog", results={@org.apache.struts2.convention.annotation.Result(type="stream", params={"inputName", "download", "contentDisposition", "attachment;filename=${#request.filename}"})})
  public String downloadLog()
    throws Exception
  {
    String logFileName = this.req.getParameter("logFileName");
    logger.debug("logFileName:" + logFileName);
    

    String logDir = new ConfigUtil("config/pingan.properties").getString("logDir");
    logger.debug("logPath:" + logDir);
    
    this.download = new FileInputStream(this.logService.getLogFile(logDir, logFileName));
    this.req.setAttribute("filename", new String((logFileName.replaceAll(" ", "") + ".zip").getBytes(), "ISO8859-1"));
    
    return "success";
  }
  
  public String formatFileSize(long length)
  {
    if (length < 1048576L)
    {
      System.out.println(length);
      return String.valueOf(this.fileSizeFmt.format(length / 1024.0D)) + " KB";
    }
    return String.valueOf(this.fileSizeFmt.format(length / 1024.0D / 1024.0D)) + " MB";
  }
  
  public LogLvLVo getLogLvLVo()
  {
    return this.logLvLVo;
  }
  
  public void setLogLvLVo(LogLvLVo logLvLVo)
  {
    this.logLvLVo = logLvLVo;
  }
  
  public InputStream getDownload()
  {
    return this.download;
  }
  
  public void setDownload(InputStream download)
  {
    this.download = download;
  }
  
  public List<LogFileVo> getList()
  {
    return this.list;
  }
  
  public void setList(List<LogFileVo> list)
  {
    this.list = list;
  }
  
  public Map<String, String> getCondition()
  {
    return this.condition;
  }
  
  public void setCondition(Map<String, String> condition)
  {
    this.condition = condition;
  }
}
