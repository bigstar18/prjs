package cn.com.agree.eteller.generic.utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class WebUtil
{
  public static void printTitle(String info, int fontSize)
    throws Exception
  {
    ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
    PrintWriter out = ServletActionContext.getResponse().getWriter();
    out.print("<center><h" + fontSize + ">" + info + "</h" + fontSize + "></center>");
  }
  
  public static File saveTempFile(File tempFile, String suffix)
    throws Exception
  {
    UUID uuid = UUID.randomUUID();
    String savePath = ServletActionContext.getServletContext().getRealPath("/") + "WEB-INF/data/upload/" + uuid.toString() + "." + suffix;
    File saveFile = new File(savePath);
    if (!saveFile.getParentFile().exists()) {
      saveFile.mkdirs();
    }
    FileUtils.copyFile(tempFile, saveFile);
    
    return saveFile;
  }
}
