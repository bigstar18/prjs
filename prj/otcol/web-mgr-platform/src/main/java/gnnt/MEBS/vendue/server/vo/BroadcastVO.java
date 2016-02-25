package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class BroadcastVO
{
  public long iD;
  public String title;
  public String author;
  public String content;
  public int type;
  public Timestamp sendtime;
  public Timestamp endtime;
  public Timestamp createtime;
  public Timestamp updatetime;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("ID:" + this.iD + str);
    localStringBuffer.append("title:" + this.title + str);
    localStringBuffer.append("author:" + this.author + str);
    localStringBuffer.append("content:" + this.content + str);
    localStringBuffer.append("type:" + this.type + str);
    localStringBuffer.append("sendtime:" + this.sendtime + str);
    localStringBuffer.append("endtime:" + this.endtime + str);
    localStringBuffer.append("createtime:" + this.createtime + str);
    localStringBuffer.append("updatetime:" + this.updatetime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
