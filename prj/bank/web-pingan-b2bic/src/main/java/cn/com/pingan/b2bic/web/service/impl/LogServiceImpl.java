package cn.com.pingan.b2bic.web.service.impl;

import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.CompressUtil;
import cn.com.agree.eteller.generic.utils.ConfigUtil;
import cn.com.pingan.b2bic.web.service.LogService;
import cn.com.pingan.b2bic.web.vo.LogFileVo;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl
  implements LogService
{
  private static Logger logger = Logger.getLogger(LogService.class);
  
  public List<LogFileVo> getLogFiles(String logDir)
    throws Exception
  {
    File dir = new File(logDir);
    File[] logs = dir.listFiles(new FileFilter()
    {
      public boolean accept(File pathname)
      {
        return (pathname.isFile()) && (pathname.getName().contains(".log"));
      }
    });
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    List<LogFileVo> infos = new LinkedList();
    for (File f : logs) {
      infos.add(new LogFileVo(f.getName(), sdf.format(new Date(f.lastModified())), Long.valueOf(f.length()), f.getAbsolutePath()));
    }
    if (!infos.isEmpty()) {
      Collections.sort(infos);
    }
    return infos.isEmpty() ? null : infos;
  }
  
  public File getLogFile(String logDir, String logFileName)
    throws Exception
  {
    File logFile = new File(logDir, logFileName);
    

    File zipLogTempDir = null;
    try
    {
      ConfigUtil config = new ConfigUtil("config/pingan.properties");
      String zipLogTempDirPath = config.getString("zipLogTempDir");
      if ((zipLogTempDirPath == null) || (zipLogTempDirPath.length() == 0)) {
        throw new ServiceException();
      }
      zipLogTempDir = new File(zipLogTempDirPath);
      if (!zipLogTempDir.exists()) {
        zipLogTempDir.mkdirs();
      }
    }
    catch (Exception e)
    {
      logger.warn("未找到配置的压缩日志文件的临时目录, 在日志所在路径下生成临时目录tmp");
      zipLogTempDir = new File(logDir, "tmp");
      zipLogTempDir.mkdirs();
    }
    File zipLog = new File(zipLogTempDir.getAbsolutePath() + "/" + logFile.getName() + ".zip");
    CompressUtil compress = new CompressUtil(logFile);
    compress.packFiles(zipLog.getAbsolutePath(), false);
    if (!zipLog.exists()) {
      throw new ServiceException("压缩日志文件失败：");
    }
    return zipLog;
  }
  
  public List<LogFileVo> getLogFiles(String logDir, Map<String, String> condition)
    throws Exception
  {
    String starttime = null;
    String endtime = null;
    
    String timePattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
    if ((!ComFunction.isEmpty(condition.get("starttime"))) && (((String)condition.get("starttime")).matches(timePattern))) {
      starttime = ((String)condition.get("starttime")).trim();
    }
    if ((!ComFunction.isEmpty(condition.get("endtime"))) && (((String)condition.get("endtime")).matches(timePattern))) {
      endtime = ((String)condition.get("endtime")).trim();
    }
    List<LogFileVo> list = getLogFiles(logDir);
    if (!ComFunction.isEmpty(list)) {
      for (Iterator<LogFileVo> iterator = list.iterator(); iterator
            .hasNext();)
      {
        LogFileVo lf = (LogFileVo)iterator.next();
        if ((starttime != null) && (lf.getTime().compareTo(starttime) < 0)) {
          iterator.remove();
        }
        if ((endtime != null) && (lf.getTime().compareTo(endtime) > 0)) {
          iterator.remove();
        }
      }
    }
    return list;
  }
}
