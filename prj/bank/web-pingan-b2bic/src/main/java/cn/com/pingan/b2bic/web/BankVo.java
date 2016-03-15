package cn.com.pingan.b2bic.web;

public class BankVo
{
  private ServerInVo bankIn;
  private ServerOutVo bankOut;
  private FtpVo bankFtp;
  
  public FtpVo getBankFtp()
  {
    return this.bankFtp;
  }
  
  public void setBankFtp(FtpVo bankFtp)
  {
    this.bankFtp = bankFtp;
  }
  
  public ServerInVo getBankIn()
  {
    return this.bankIn;
  }
  
  public void setBankIn(ServerInVo bankIn)
  {
    this.bankIn = bankIn;
  }
  
  public ServerOutVo getBankOut()
  {
    return this.bankOut;
  }
  
  public void setBankOut(ServerOutVo bankOut)
  {
    this.bankOut = bankOut;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("In:  ").append(this.bankIn.toString()).append("\n");
    sb.append("Out: ").append(this.bankOut.toString()).append("\n");
    sb.append("Ftp: ").append(this.bankFtp.toString());
    return sb.toString();
  }
}
