package cn.com.pingan.b2bic.web;

public class FtpVo
{
  private String hostname;
  private int port;
  private String ftpname;
  private String ftppwd;
  private int soTimeout;
  private String defaultDir;
  
  public String getDefaultDir()
  {
    return this.defaultDir;
  }
  
  public void setDefaultDir(String defaultDir)
  {
    this.defaultDir = defaultDir;
  }
  
  public String getFtpname()
  {
    return this.ftpname;
  }
  
  public void setFtpname(String ftpname)
  {
    this.ftpname = ftpname;
  }
  
  public String getFtppwd()
  {
    return this.ftppwd;
  }
  
  public void setFtppwd(String ftppwd)
  {
    this.ftppwd = ftppwd;
  }
  
  public String getHostname()
  {
    return this.hostname;
  }
  
  public void setHostname(String hostname)
  {
    this.hostname = hostname;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public void setPort(int port)
  {
    this.port = port;
  }
  
  public int getSoTimeout()
  {
    return this.soTimeout;
  }
  
  public void setSoTimeout(int soTimeout)
  {
    this.soTimeout = soTimeout;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("hostname=").append(this.hostname).append(", ").append("port=")
      .append(this.port).append(", ").append("ftpname=").append(this.ftpname)
      .append(", ").append("ftppwd=").append(this.ftppwd).append(", ")
      .append("soTimeout=").append(this.soTimeout).append(", ").append(
      "defaultDir=").append(this.defaultDir);
    return sb.toString();
  }
}
