package gnnt.trade.bank.vo.bankdz.sfz.resave.child;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.Serializable;
/**
 * 会员余额文件【1002】
 * BatCustDz+交易网代码（4位）+时间（14位）.txt
 */
public class BatCustDzBChild extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**当个类的属性个数*/
	protected int preferencenum = 10;
	/**子账号*/
	public String CustAcctId;
	private int CustAcctIdlength = 32;
	/**名称*/
	public String CustName;
	private int CustNamelength = 120;
	/**会员代码*/
	public String ThirdCustId;
	private int ThirdCustIdlength = 32;
	/**状态(1：正常  2：退出 3：待确定)*/
	public int Status;
	private int Statuslength=1;
	/**账号属性（1：普通会员子账号 2：挂账子账号  3：手续费子账号 4：利息子账号 6:清收子账户）*/
	public int Type;
	private int Typelength=1;
	/**是否实子账号(默认为1：虚子账户)*/
	public int IsTrueCont;
	private int IsTrueContlength = 1;
	/**总余额*/
	public double Balance;
	private int Balancelength=18;
	/**可用余额*/
	public double UsrBalance;
	private int UsrBalancelength=18;
	/**冻结余额*/
	public double FrozenBalance;
	private int FrozenBalancelength = 18;
	/**利息基数*/
	public double Interest;
	private int Interestlength = 18;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("CustAcctId["+CustAcctId+"]"+str);
		sb.append("CustName["+CustName+"]"+str);
		sb.append("ThirdCustId["+ThirdCustId+"]"+str);
		sb.append("Status["+Status+"]"+str);
		sb.append("Type["+Type+"]"+str);
		sb.append("IsTrueCont["+IsTrueCont+"]"+str);
		sb.append("Balance["+Balance+"]"+str);
		sb.append("UsrBalance["+UsrBalance+"]"+str);
		sb.append("FrozenBalance["+FrozenBalance+"]"+str);
		sb.append("Interest["+Interest+"]"+str);
		return sb.toString();
	}
	/**解析报文信息，获取字段值*/
	public void getBody(String str)throws Exception{
		if(str==null || str.trim().length()<=0){
			throw new Exception("传入的信息为空");
		}
		String[] merthods = str.split(this.separator,-1);
		if(merthods.length<preferencenum){
			throw new Exception("传入的信息个数小于需要信息个数");
		}else if(merthods.length>preferencenum+1){
			System.out.println("注意：当个传入的信息数量超出了所需的信息个数，是否为错误数据？序号："+merthods[0]);
		}
		try{
			int n = 0 ;
			this.CustAcctId = this.overLong(merthods[n++], this.CustAcctIdlength);
			this.CustName = this.overLong(merthods[n++], this.CustNamelength);
			this.ThirdCustId = this.overLong(merthods[n++], this.ThirdCustIdlength);
			this.Status = Integer.parseInt(this.overLong(merthods[n++], this.Statuslength));
			this.Type = Integer.parseInt(this.overLong(merthods[n++], this.Typelength));
			this.IsTrueCont = Integer.parseInt(this.overLong(merthods[n++], this.IsTrueContlength));
			this.Balance = Double.valueOf(this.overLong(merthods[n++], this.Balancelength));
			this.UsrBalance = Double.valueOf(this.overLong(merthods[n++], this.UsrBalancelength));
			this.FrozenBalance = Double.valueOf(this.overLong(merthods[n++], this.FrozenBalancelength));
			this.Interest = Double.valueOf(this.overLong(merthods[n++], this.Interestlength));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**生成流水信息*/
	public String setBody()throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(this.CustAcctId+this.separator);
		sb.append(this.CustName+this.separator);
		sb.append(this.ThirdCustId+this.separator);
		sb.append(this.Status+this.separator);
		sb.append(this.Type+this.separator);
		sb.append(this.IsTrueCont+this.separator);
		sb.append(this.Balance+this.separator);
		sb.append(this.UsrBalance+this.separator);
		sb.append(this.FrozenBalance+this.separator);
		return sb.toString();
	}
}
