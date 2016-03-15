package cn.com.pingan.b2bic.web.service;

import cn.com.pingan.b2bic.web.vo.LogFileVo;
import java.io.File;
import java.util.List;
import java.util.Map;

public abstract interface LogService
{
  public abstract List<LogFileVo> getLogFiles(String paramString, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<LogFileVo> getLogFiles(String paramString)
    throws Exception;
  
  public abstract File getLogFile(String paramString1, String paramString2)
    throws Exception;
}
