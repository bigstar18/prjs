package cn.com.agree.eteller.generic.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUtil
{
  private String configFile;
  private String configSection;
  private String hostUser;
  private String hostPwd;
  private String hostIp;
  private int hostPort = 21;
  private FTPClient ftpClient;
  private String coding = "UTF-8";
  private int sleepTime = 2000;
  private int DEFAULT_TIMEOUT = 30000;
  private int retryTimes = 3;
  private int leftRetryTime = this.retryTimes;
  
  public FtpUtil(String configFile, String configSection)
    throws InterruptedException
  {
    ftpUtilInit(configFile, configSection);
  }
  
  private void ftpUtilInit(String configFile, String configSection)
    throws InterruptedException
  {
    this.configFile = configFile;
    this.configSection = configSection;
    InputStream confFileInputStream = ConfigUtil.getResource(this.configFile);
    ConfigParser configParser = new ConfigParser(confFileInputStream);
    this.hostUser = configParser.find_KeyToValue(this.configSection, "hostUser");
    this.hostPwd = configParser.find_KeyToValue(this.configSection, "hostPwd");
    this.hostIp = configParser.find_KeyToValue(this.configSection, "hostIp");
    this.DEFAULT_TIMEOUT = Integer.parseInt(configParser.find_KeyToValue(this.configSection, "timeOut"));
    System.out.println("ftp login user :" + this.hostUser);
  }
  
  public void connect()
    throws InterruptedException
  {
    close();
    this.ftpClient = new FTPClient();
    boolean success = false;
    try
    {
      this.ftpClient.connect(this.hostIp);
      this.ftpClient.login(this.hostUser, this.hostPwd);
      this.ftpClient.setDefaultPort(this.hostPort);
      this.ftpClient.setFileType(0);
      this.ftpClient.enterLocalPassiveMode();
      this.ftpClient.setControlEncoding(this.coding);
      this.ftpClient.setDefaultTimeout(this.DEFAULT_TIMEOUT);
      System.out.println("ftp connect success!");
      success = true;
    }
    catch (SocketException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if ((!success) && (this.leftRetryTime > 0))
      {
        Thread.sleep(this.sleepTime);
        System.out.println("重连次数共：[" + this.retryTimes + "]次，剩余:[" + this.leftRetryTime + "]次");
        this.leftRetryTime -= 1;
        connect();
      }
    }
  }
  
  public void close()
  {
    if ((this.ftpClient != null) && (this.ftpClient.isConnected())) {
      try
      {
        this.ftpClient.logout();
        this.ftpClient.disconnect();
        System.out.println("ftp close success!");
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public boolean existFile(String remoteFilePath, String remoteFileName)
    throws IOException
  {
    remoteFilePath = StringUtils.trim(remoteFilePath);
    boolean existRet = false;
    for (String fileName : this.ftpClient.listNames(remoteFilePath))
    {
      fileName = StringUtils.trim(fileName.substring(fileName.lastIndexOf("/") + 1));
      if (fileName.equals(remoteFileName))
      {
        existRet = true;
        break;
      }
    }
    return existRet;
  }
  
  public void writeDataToRemoteFile(String remoteFilePath, String remoteFileName, InputStream localInputStream)
    throws Exception
  {
    remoteFilePath = StringUtils.trim(remoteFilePath);
    this.ftpClient.changeWorkingDirectory(remoteFilePath);
    this.ftpClient.storeFile(remoteFileName, localInputStream);
  }
  
  public void writeDataToRemoteFile(String remoteFilePath, String remoteFileName, String localFilePath, String localFileName)
    throws Exception
  {
    remoteFilePath = StringUtils.trim(remoteFilePath);
    localFilePath = StringUtils.trim(localFilePath);
    if (localFilePath.endsWith("/")) {
      localFileName = localFilePath + localFileName;
    } else {
      localFileName = localFilePath + "/" + localFileName;
    }
    InputStream localInputStream = new BufferedInputStream(
      new FileInputStream(new File(localFileName)));
    
    this.ftpClient.changeWorkingDirectory(remoteFilePath);
    this.ftpClient.storeFile(remoteFileName, localInputStream);
  }
  
  public boolean remoteFileBak(String remoteFilePath, String remoteFileName, String remoteFilePathBak, String remoteFileNameBak)
    throws Exception
  {
    remoteFilePath = StringUtils.trim(remoteFilePath);
    remoteFilePathBak = StringUtils.trim(remoteFilePathBak);
    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    this.ftpClient.changeWorkingDirectory(remoteFilePath);
    this.ftpClient.retrieveFile(remoteFileName, outputStream);
    
    InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    this.ftpClient.changeWorkingDirectory(remoteFilePathBak);
    return this.ftpClient.storeUniqueFile(remoteFileNameBak, inputStream);
  }
  
  public int getHostPort()
  {
    return this.hostPort;
  }
  
  public void setHostPort(int hostPort)
  {
    this.hostPort = hostPort;
  }
  
  public String getCoding()
  {
    return this.coding;
  }
  
  public void setCoding(String coding)
  {
    this.coding = coding;
  }
  
  public int getSleepTime()
  {
    return this.sleepTime;
  }
  
  public void setSleepTime(int sleepTime)
  {
    this.sleepTime = sleepTime;
  }
  
  public int getDEFAULT_TIMEOUT()
  {
    return this.DEFAULT_TIMEOUT;
  }
  
  public void setDEFAULT_TIMEOUT(int dEFAULT_TIMEOUT)
  {
    this.DEFAULT_TIMEOUT = dEFAULT_TIMEOUT;
  }
  
  public int getRetryTimes()
  {
    return this.retryTimes;
  }
  
  public void setRetryTimes(int retryTimes)
  {
    this.retryTimes = retryTimes;
  }
  
  public static void main(String[] argv)
    throws Exception
  {
    FtpUtil ftpUtil = new FtpUtil("cn/com/agree/eteller/gwmgr/conf/gwHost.conf", "gwHostOuter");
    ftpUtil.connect();
    ftpUtil.remoteFileBak("/home/gateway/", "text.txt", "/home/gateway/tmp", "text_bak.txt");
    System.out.println("existRet:" + ftpUtil.existFile("/home/gateway/tmp", "text_bak.txt"));
    ftpUtil.close();
  }
}
