package cn.com.pingan.b2bic.web.vo;

import java.io.Serializable;

public class LogFileVo
  implements Serializable, Comparable<LogFileVo>
{
  private static final long serialVersionUID = 1L;
  private String name;
  private String time;
  private String dir;
  private Long size;
  
  public LogFileVo() {}
  
  public LogFileVo(String name, String time, Long size, String dir)
  {
    this.name = name;
    this.time = time;
    this.dir = dir;
    this.size = size;
  }
  
  public int compareTo(LogFileVo o)
  {
    return o.getTime().compareTo(this.time);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  public String getDir()
  {
    return this.dir;
  }
  
  public void setDir(String dir)
  {
    this.dir = dir;
  }
  
  public Long getSize()
  {
    return this.size;
  }
  
  public void setSize(Long size)
  {
    this.size = size;
  }
}
