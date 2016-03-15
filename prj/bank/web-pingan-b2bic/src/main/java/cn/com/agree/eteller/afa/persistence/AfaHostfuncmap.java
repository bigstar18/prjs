package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaHostfuncmap
  implements Serializable
{
  private String hosttype;
  private String agentflag;
  private String channelcode;
  private String revtranf;
  private String acctype;
  private String tradefunc;
  private String comment;
  private String note1;
  private String note2;
  
  public AfaHostfuncmap(String hosttype, String agentflag, String channelcode, String revtranf, String acctype, String tradefunc, String comment, String note1, String note2)
  {
    this.hosttype = hosttype;
    this.agentflag = agentflag;
    this.channelcode = channelcode;
    this.revtranf = revtranf;
    this.acctype = acctype;
    this.tradefunc = tradefunc;
    this.comment = comment;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaHostfuncmap() {}
  
  public String getHosttype()
  {
    return this.hosttype;
  }
  
  public void setHosttype(String hosttype)
  {
    this.hosttype = hosttype;
  }
  
  public String getAgentflag()
  {
    return this.agentflag;
  }
  
  public void setAgentflag(String agentflag)
  {
    this.agentflag = agentflag;
  }
  
  public String getChannelcode()
  {
    return this.channelcode;
  }
  
  public void setChannelcode(String channelcode)
  {
    this.channelcode = channelcode;
  }
  
  public String getRevtranf()
  {
    return this.revtranf;
  }
  
  public void setRevtranf(String revtranf)
  {
    this.revtranf = revtranf;
  }
  
  public String getAcctype()
  {
    return this.acctype;
  }
  
  public void setAcctype(String acctype)
  {
    this.acctype = acctype;
  }
  
  public String getTradefunc()
  {
    return this.tradefunc;
  }
  
  public void setTradefunc(String tradefunc)
  {
    this.tradefunc = tradefunc;
  }
  
  public String getComment()
  {
    return this.comment;
  }
  
  public void setComment(String comment)
  {
    this.comment = comment;
  }
  
  public String getNote1()
  {
    return this.note1;
  }
  
  public void setNote1(String note1)
  {
    this.note1 = note1;
  }
  
  public String getNote2()
  {
    return this.note2;
  }
  
  public void setNote2(String note2)
  {
    this.note2 = note2;
  }
  
  public String toString()
  {
    return 
    








      new ToStringBuilder(this).append("hosttype", getHosttype()).append("agentflag", getAgentflag()).append("channelcode", getChannelcode()).append("revtranf", getRevtranf()).append("acctype", getAcctype()).append("tradefunc", getTradefunc()).append("comment", getComment()).append("note1", getNote1()).append("note2", getNote2()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaHostfuncmap)) {
      return false;
    }
    AfaHostfuncmap castOther = (AfaHostfuncmap)other;
    return new EqualsBuilder()
      .append(getHosttype(), castOther.getHosttype())
      .append(getAgentflag(), castOther.getAgentflag())
      .append(getChannelcode(), castOther.getChannelcode())
      .append(getRevtranf(), castOther.getRevtranf())
      .append(getAcctype(), castOther.getAcctype())
      .append(getTradefunc(), castOther.getTradefunc())
      .append(getComment(), castOther.getComment())
      .append(getNote1(), castOther.getNote1())
      .append(getNote2(), castOther.getNote2())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    








      new HashCodeBuilder().append(getHosttype()).append(getAgentflag()).append(getChannelcode()).append(getRevtranf()).append(getAcctype()).append(getTradefunc()).append(getComment()).append(getNote1()).append(getNote2()).toHashCode();
  }
}
