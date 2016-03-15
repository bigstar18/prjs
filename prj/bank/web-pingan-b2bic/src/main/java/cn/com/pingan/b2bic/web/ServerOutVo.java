package cn.com.pingan.b2bic.web;

public class ServerOutVo
{
  private String protocol;
  private String ips;
  private String ports;
  private int timeout;
  private String url;
  
  public String getIps()
  {
    return this.ips;
  }
  
  public void setIps(String ips)
  {
    this.ips = ips;
  }
  
  public String getPorts()
  {
    return this.ports;
  }
  
  public void setPorts(String ports)
  {
    this.ports = ports;
  }
  
  public String getProtocol()
  {
    return this.protocol;
  }
  
  public void setProtocol(String protocol)
  {
    this.protocol = protocol;
  }
  
  public int getTimeout()
  {
    return this.timeout;
  }
  
  public void setTimeout(int timeout)
  {
    this.timeout = timeout;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("protocol=").append(this.protocol).append(", ").append("ips=")
      .append(this.ips).append(", ").append("ports=").append(this.ports)
      .append(", ").append("timeout=").append(this.timeout);
    if (this.url != null) {
      sb.append(", ").append("url=").append(this.url);
    }
    return sb.toString();
  }
}
