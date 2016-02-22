package gnnt.trade.bank.vo.bankdz.sfz.resave.child;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.Serializable;
/**
 * 对账不平记录文件【1007】
 * BatCustDzFail+交易网代码（4位）+时间（14位）.txt
 */
public class BatCustDzFailChild extends FileFathor implements Serializable {
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
	/**银行清算前可用余额*/
	public double BankBalance;
	private int BankBalancelength = 18;
	/**银行清算前冻结余额*/
	public double BankFrozen;
	private int BankFrozenlength = 18;
	/**交易网可用余额*/
	public double MaketBalance;
	private int MaketBalancelength = 18;
	/**交易网冻结余额*/
	public double MaketFrozen;
	private int MaketFrozenlength = 18;
	/**可用余额差额（银行可用余额-交易网可用余额）*/
	public double BalanceError;
	private int BalanceErrorlength = 18;
	/**冻结余额差额（银行冻结余额-交易网冻结余额）*/
	public double FrozenError;
	private int FrozenErrorlength=18;
	/**对账结果说明*/
	public String Note;
	private int Notelength = 120;
	/**查询的是哪天的交易信息*/
	public java.util.Date tradeDate;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("CustAcctId["+CustAcctId+"]"+str);
		sb.append("CustName["+CustName+"]"+str);
		sb.append("ThirdCustId["+ThirdCustId+"]"+str);
		sb.append("BankBalance["+BankBalance+"]"+str);
		sb.append("BankFrozen["+BankFrozen+"]"+str);
		sb.append("MaketBalance["+MaketBalance+"]"+str);
		sb.append("MaketFrozen["+MaketFrozen+"]"+str);
		sb.append("BalanceError["+BalanceError+"]"+str);
		sb.append("FrozenError["+FrozenError+"]"+str);
		sb.append("Note["+Note+"]"+str);
		if(tradeDate == null){
			sb.append("tradeDate is Null"+str);
		}else{
			sb.append("tradeDate["+this.dateToStr1(tradeDate)+"]"+str);
		}
		sb.append(str);
		return sb.toString();
	}
	/**解析报文信息，获取字段值*/
	public void getBody(String str)throws Exception{
		if(str==null || str.trim().length()<=0){
			throw new Exception("传入的信息为空");
		}
		String[] merthods = str.split("&",-1);
		if(merthods.length<preferencenum){
			throw new Exception("传入的信息个数小于需要信息个数");
		}else if(merthods.length>preferencenum+1){
			System.out.println("注意：当个传入的信息数量["+(preferencenum+1)+"]超出了所需的信息个数["+merthods.length+"]，是否为错误数据？首信息："+merthods[0]);
		}
		try{
			int n = 0;
			this.CustAcctId = this.overLong(merthods[n++], this.CustAcctIdlength);
			this.CustName = this.overLong(merthods[n++], this.CustNamelength);
			this.ThirdCustId = this.overLong(merthods[n++], this.ThirdCustIdlength);
			this.BankBalance = Double.valueOf(this.overLong(merthods[n++], this.BankBalancelength));
			this.BankFrozen = Double.valueOf(this.overLong(merthods[n++], this.BankFrozenlength));
			this.MaketBalance = Double.valueOf(this.overLong(merthods[n++], this.MaketBalancelength));
			this.MaketFrozen = Double.valueOf(this.overLong(merthods[n++], this.MaketFrozenlength));
			this.BalanceError = Double.valueOf(this.overLong(merthods[n++], this.BalanceErrorlength));
			this.FrozenError = Double.valueOf(this.overLong(merthods[n++], this.FrozenErrorlength));
			this.Note = this.overLong(merthods[n++], this.Notelength);
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
		sb.append(this.fmtDouble(this.BankBalance)+this.separator);
		sb.append(this.fmtDouble(this.BankFrozen)+this.separator);
		sb.append(this.fmtDouble(this.MaketBalance)+this.separator);
		sb.append(this.fmtDouble(this.MaketFrozen)+this.separator);
		sb.append(this.fmtDouble(this.BalanceError)+this.separator);
		sb.append(this.fmtDouble(this.FrozenError)+this.separator);
		sb.append(this.Note+this.separator);
		return sb.toString();
	}
	public static void main(String args[]){
		String str = "888800000012114&广州化工交易网&0003&636.00&0.00&9563039.00&290400.00&-9562403.00&-290400.00&已对账且账不平,银行可用余额小于交易网端,银行冻结余额小于交易网端&";
		//str = "3&20100810000000&&11000098301001&30&888800000012104&测试一&0002&2548&RMB&406560.00&0.00&0.00&0.00&9522368.86&0.00&0.00&0&9522368.86&406560.00&交易网清算信息&&ERR044&该会员清算数据有误,清算后会导致银行端余额与交易网的不等&";
		BatCustDzFailChild child = new BatCustDzFailChild();
		try{
			child.getBody(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(child.toString());
	}
}
