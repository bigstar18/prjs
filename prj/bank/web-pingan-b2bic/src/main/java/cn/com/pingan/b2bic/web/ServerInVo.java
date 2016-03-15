package cn.com.pingan.b2bic.web;

public class ServerInVo
{
  private String protocol;
  private int port;
  private int soTimeout;
  private int maximumPoolSize;
  
  public int getMaximumPoolSize()
  {
    return this.maximumPoolSize;
  }
  
  public void setMaximumPoolSize(int maximumPoolSize)
  {
    this.maximumPoolSize = maximumPoolSize;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public void setPort(int port)
  {
    this.port = port;
  }
  
  public String getProtocol()
  {
    return this.protocol;
  }
  
  public void setProtocol(String protocol)
  {
    this.protocol = protocol;
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
    sb.append("protocol=").append(this.protocol).append(", ").append("port=")
      .append(this.port).append(", ").append("soTimeout=")
      .append(this.soTimeout).append(", ").append("maximumPoolSize=")
      .append(this.maximumPoolSize);
    return sb.toString();
  }
}
