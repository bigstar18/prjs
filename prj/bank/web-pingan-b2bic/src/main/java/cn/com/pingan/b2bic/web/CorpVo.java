package cn.com.pingan.b2bic.web;

public class CorpVo
{
  private ServerInVo corpIn;
  private ServerOutVo corpOut;
  private boolean useFtp;
  private FtpVo corpFtp;
  private boolean fileNotify;
  private int downMode;
  private boolean normalFormat;
  private String dataVersion;
  private String corpCode;
  private String tranCode;
  
  public FtpVo getCorpFtp()
  {
    return this.corpFtp;
  }
  
  public void setCorpFtp(FtpVo corpFtp)
  {
    this.corpFtp = corpFtp;
  }
  
  public ServerInVo getCorpIn()
  {
    return this.corpIn;
  }
  
  public void setCorpIn(ServerInVo corpIn)
  {
    this.corpIn = corpIn;
  }
  
  public ServerOutVo getCorpOut()
  {
    return this.corpOut;
  }
  
  public void setCorpOut(ServerOutVo corpOut)
  {
    this.corpOut = corpOut;
  }
  
  public boolean isFileNotify()
  {
    return this.fileNotify;
  }
  
  public void setFileNotify(boolean fileNotify)
  {
    this.fileNotify = fileNotify;
  }
  
  public boolean isUseFtp()
  {
    return this.useFtp;
  }
  
  public void setUseFtp(boolean useFtp)
  {
    this.useFtp = useFtp;
  }
  
  public int getDownMode()
  {
    return this.downMode;
  }
  
  public void setDownMode(int downMode)
  {
    this.downMode = downMode;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("In:  ").append(this.corpIn.toString()).append("\n");
    sb.append("Out: ").append(this.corpOut.toString()).append("\n");
    sb.append("Ftp: ").append("useFtp=").append(this.useFtp).append(", ")
      .append(this.corpFtp.toString()).append("\n");
    sb.append("fileNotify=").append(this.fileNotify).append(", ").append(
      "downMode=").append(this.downMode).append("\n");
    sb.append("normalFormat").append(this.normalFormat).append(", ").append(
      "dataVersion").append(this.dataVersion).append(", ").append(
      "corpCode").append(this.corpCode).append(",tranCode").append(this.tranCode);
    return sb.toString();
  }
  
  public String getCorpCode()
  {
    return this.corpCode;
  }
  
  public void setCorpCode(String corpCode)
  {
    this.corpCode = corpCode;
  }
  
  public String getDataVersion()
  {
    return this.dataVersion;
  }
  
  public void setDataVersion(String dataVersion)
  {
    this.dataVersion = dataVersion;
  }
  
  public boolean isNormalFormat()
  {
    return this.normalFormat;
  }
  
  public void setNormalFormat(boolean normalFormat)
  {
    this.normalFormat = normalFormat;
  }
  
  public String getTranCode()
  {
    return this.tranCode;
  }
  
  public void setTranCode(String tranCode)
  {
    this.tranCode = tranCode;
  }
}
