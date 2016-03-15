package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaTradeadm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaTradeadmPK comp_id;
  private String busimode;
  private String trxname;
  private String starttime;
  private String stoptime;
  private String trademode;
  private String status;
  private String bankreq;
  private String unitreq;
  private String accpwdflag;
  private String tradepwdflag;
  private String channelcode;
  private String tradedesc;
  private String errtype;
  private String note1;
  private String note2;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位");
    hm.put("Subunitno", "商户分支");
    hm.put("Trxcode", "交易代码");
    
    hm.put("Busimode", "业务模式");
    hm.put("Trxname", "交易名称");
    hm.put("Starttime", "开始时间");
    hm.put("Stoptime", "结束时间");
    hm.put("Trademode", "交易模式");
    hm.put("Status", "交易状态");
    hm.put("Bankreq", "银行发起标志");
    hm.put("Unitreq", "商户发起标志");
    hm.put("Accpwdflag", "卡密码校验标志");
    hm.put("Tradepwdflag", "交易密码校验标志");
    hm.put("Channelcode", "渠道掩码");
    hm.put("Tradedesc", "交易描述");
    hm.put("Errtype", "错误处理类型");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaTradeadm t where ";
    sb.append(sql).append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and  t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and  t.comp_id.subunitno='" + getComp_id().getSubunitno() + "'")
      .append(" and  t.comp_id.trxcode='" + getComp_id().getTrxcode() + "'");
    return sb.toString();
  }
  
  public String toChinese(String value)
  {
    if (value == null) {
      return null;
    }
    if (value.equals("")) {
      return "";
    }
    String[] str1InArray = value.split("\\Q|\\E");
    String str = "";
    for (int i = 0; i < str1InArray.length; i++)
    {
      String[] str1InArray2 = str1InArray[i].split("\\Q=\\E");
      if (hm.containsKey(str1InArray2[0])) {
        str1InArray2[0] = ((String)hm.get(str1InArray2[0]));
      }
      if (str1InArray2.length == 1) {
        str = str + str1InArray2[0] + "=|";
      } else {
        str = str + str1InArray2[0] + "=" + str1InArray2[1] + "|";
      }
    }
    return str;
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getComp_id().getSysid() + "|")
      .append("Unitno=" + getComp_id().getUnitno() + "|")
      .append("Subunitno=" + getComp_id().getSubunitno() + "|")
      .append("Trxcode=" + getComp_id().getTrxcode() + "|")
      
      .append("Busimode=" + getBusimode() + "|")
      .append("Trxname=" + getTrxname() + "|")
      .append("Starttime=" + getStarttime() + "|")
      .append("Stoptime=" + getStoptime() + "|")
      .append("Trademode=" + getTrademode() + "|")
      .append("Status=" + getStatus() + "|")
      .append("Bankreq=" + getBankreq() + "|")
      .append("Unitreq=" + getUnitreq() + "|")
      .append("Accpwdflag=" + getAccpwdflag() + "|")
      .append("Tradepwdflag=" + getTradepwdflag() + "|")
      .append("Channelcode=" + getChannelcode() + "|")
      .append("Accpwdflag=" + getAccpwdflag() + "|")
      .append("Tradedesc=" + getTradedesc() + "|")
      .append("Errtype=" + getErrtype() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBusimode() == null) {
      setBusimode("");
    }
    if (getTrxname() == null) {
      setTrxname("");
    }
    if (getStarttime() == null) {
      setStarttime("");
    }
    if (getStoptime() == null) {
      setStoptime("");
    }
    if (getTrademode() == null) {
      setTrademode("");
    }
    if (getStatus() == null) {
      setStatus("");
    }
    if (getBankreq() == null) {
      setBankreq("");
    }
    if (getUnitreq() == null) {
      setUnitreq("");
    }
    if (getAccpwdflag() == null) {
      setAccpwdflag("");
    }
    if (getTradepwdflag() == null) {
      setTradepwdflag("");
    }
    if (getChannelcode() == null) {
      setChannelcode("");
    }
    if (getAccpwdflag() == null) {
      setAccpwdflag("");
    }
    if (getTradedesc() == null) {
      setTradedesc("");
    }
    if (getErrtype() == null) {
      setErrtype("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
  }
  
  public AfaTradeadm(AfaTradeadmPK comp_id, String busimode, String trxname, String starttime, String stoptime, String trademode, String status, String bankreq, String unitreq, String accpwdflag, String tradepwdflag, String channelcode, String tradedesc, String errtype, String note1, String note2)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
    this.trxname = trxname;
    this.starttime = starttime;
    this.stoptime = stoptime;
    this.trademode = trademode;
    this.status = status;
    this.bankreq = bankreq;
    this.unitreq = unitreq;
    this.accpwdflag = accpwdflag;
    this.tradepwdflag = tradepwdflag;
    this.channelcode = channelcode;
    this.tradedesc = tradedesc;
    this.errtype = errtype;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaTradeadm() {}
  
  public AfaTradeadm(AfaTradeadmPK comp_id, String busimode, String status)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
    this.status = status;
  }
  
  public AfaTradeadmPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaTradeadmPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getBusimode()
  {
    return this.busimode;
  }
  
  public void setBusimode(String busimode)
  {
    this.busimode = busimode;
  }
  
  public String getTrxname()
  {
    return this.trxname;
  }
  
  public void setTrxname(String trxname)
  {
    this.trxname = trxname;
  }
  
  public String getStarttime()
  {
    return this.starttime;
  }
  
  public void setStarttime(String starttime)
  {
    this.starttime = starttime;
  }
  
  public String getStoptime()
  {
    return this.stoptime;
  }
  
  public void setStoptime(String stoptime)
  {
    this.stoptime = stoptime;
  }
  
  public String getTrademode()
  {
    return this.trademode;
  }
  
  public void setTrademode(String trademode)
  {
    this.trademode = trademode;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getBankreq()
  {
    return this.bankreq;
  }
  
  public void setBankreq(String bankreq)
  {
    this.bankreq = bankreq;
  }
  
  public String getUnitreq()
  {
    return this.unitreq;
  }
  
  public void setUnitreq(String unitreq)
  {
    this.unitreq = unitreq;
  }
  
  public String getAccpwdflag()
  {
    return this.accpwdflag;
  }
  
  public void setAccpwdflag(String accpwdflag)
  {
    this.accpwdflag = accpwdflag;
  }
  
  public String getTradepwdflag()
  {
    return this.tradepwdflag;
  }
  
  public void setTradepwdflag(String tradepwdflag)
  {
    this.tradepwdflag = tradepwdflag;
  }
  
  public String getChannelcode()
  {
    return this.channelcode;
  }
  
  public void setChannelcode(String channelcode)
  {
    this.channelcode = channelcode;
  }
  
  public String getTradedesc()
  {
    return this.tradedesc;
  }
  
  public void setTradedesc(String tradedesc)
  {
    this.tradedesc = tradedesc;
  }
  
  public String getErrtype()
  {
    return this.errtype;
  }
  
  public void setErrtype(String errtype)
  {
    this.errtype = errtype;
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
  
  public String getSysid()
  {
    return getComp_id().getSysid();
  }
  
  public void setSysid(String sysid)
  {
    getComp_id().setSysid(sysid);
  }
  
  public String getUnitno()
  {
    return getComp_id().getUnitno();
  }
  
  public void setUnitno(String unitno)
  {
    getComp_id().setUnitno(unitno);
  }
  
  public String getSubunitno()
  {
    return getComp_id().getSubunitno();
  }
  
  public void setSubunitno(String subunitno)
  {
    getComp_id().setSubunitno(subunitno);
  }
  
  public String getTrxcode()
  {
    return getComp_id().getTrxcode();
  }
  
  public void setTrxcode(String trxcode)
  {
    getComp_id().setTrxcode(trxcode);
  }
  
  public String toString()
  {
    return 
    













      new ToStringBuilder(this).append("comp_id", getComp_id()).append("busimode", getBusimode()).append("trxname", getTrxname()).append("starttime", getStarttime()).append("stoptime", getStoptime()).append("trademode", getTrademode()).append("status", getStatus()).append("bankreq", getBankreq()).append("unitreq", getUnitreq()).append("accpwdflag", getAccpwdflag()).append("tradepwdflag", getTradepwdflag()).append("channelcode", getChannelcode()).append("tradedesc", getTradedesc()).append("errtype", getErrtype()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaTradeadm)) {
      return false;
    }
    AfaTradeadm castOther = (AfaTradeadm)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
}
