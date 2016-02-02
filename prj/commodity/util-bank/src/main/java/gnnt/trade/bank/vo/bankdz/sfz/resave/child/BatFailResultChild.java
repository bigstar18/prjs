package gnnt.trade.bank.vo.bankdz.sfz.resave.child;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.Serializable;
/**
 * 清算失败文件【1001】
 * BatFailResult+交易网代码（4位）+时间（14位）.txt
 */
public class BatFailResultChild extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int preferencenum = 24;
	/**序号*/
	public long QueryId;
	private int QueryIdlength = 10;
	/**交易时间*/
	public String TranDateTime;
	private int TranDateTimelength = 14;
	/**操作员号*/
	public String CounterId;
	private int CounterIdlength = 12;
	/**资金汇总帐号*/
	public String SupAcctId;
	private int SupAcctIdlength = 32;
	/**功能代码*/
	public String FuncCode;
	private int FuncCodelength = 4;
	/**子账户帐号*/
	public String CustAcctId;
	private int CustAcctIdlength = 32;
	/**子账户名称*/
	public String CustName;
	private int CustNamelength = 120;
	/**交易网会员代码*/
	public String ThirdCustId;
	private int ThirdCustIdlength = 32;
	/**交易网流水号*/
	public String ThirdLogNo;
	private int ThirdLogNolength = 20;
	/**币种*/
	public String CcyCode = "RMB";
	private int CcyCodelength = 3;
	/**当天总冻结金额*/
	public double FreezeAmount;
	private int FreezeAmountlength = 18;
	/**当天总解冻金额*/
	public double UnfreezeAmount;
	private int UnfreezeAmountlength = 18;
	/**当天成交的总货款(卖方)*/
	public double AddTranAmount;
	private int AddTranAmountlength = 18;
	/**当天成交的总货款(买方)*/
	public double CutTranAmount;
	private int CutTranAmountlength = 18;
	/**盈利总金额*/
	public double ProfitAmount;
	private int ProfitAmountlength = 18;
	/**亏损总金额*/
	public double LossAmount;
	private int LossAmountlength = 18;
	/**当天应该付给交易网的手续费总额*/
	public double TranHandFee;
	private int TranHandFeelength = 18;
	/**当天交易总比数*/
	public int TranCount;
	private int TranCountlength = 30;
	/**交易网端最新的交易商可用余额*/
	public double NewBalance;
	private int NewBalancelength =18;
	/**交易网端最新的冻结资金*/
	public double NewFreezeAmount;
	private int NewFreezeAmountlength = 18;
	/**说明*/
	public String Note;
	private int Notelength = 120;
	/**保留域*/
	public String Reserve;
	private int Reservelength = 120;
	/**错误码*/
	public String RspCode;
	private final int RspCodelength=6;
	/**应答描述*/
	public String RspMsg;
	private final int RspMsglength=120;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("QueryId["+QueryId+"]"+str);
		sb.append("TranDateTime["+TranDateTime+"]"+str);
		sb.append("CounterId["+CounterId+"]"+str);
		sb.append("SupAcctId["+SupAcctId+"]"+str);
		sb.append("FuncCode["+FuncCode+"]"+str);
		sb.append("CustAcctId["+CustAcctId+"]"+str);
		sb.append("CustName["+CustName+"]"+str);
		sb.append("ThirdCustId["+ThirdCustId+"]"+str);
		sb.append("ThirdLogNo["+ThirdLogNo+"]"+str);
		sb.append("FreezeAmount["+FreezeAmount+"]"+str);
		sb.append("UnfreezeAmount["+UnfreezeAmount+"]"+str);
		sb.append("AddTranAmount["+AddTranAmount+"]"+str);
		sb.append("CutTranAmount["+CutTranAmount+"]"+str);
		sb.append("ProfitAmount["+ProfitAmount+"]"+str);
		sb.append("LossAmount["+LossAmount+"]"+str);
		sb.append("TranHandFee["+TranHandFee+"]"+str);
		sb.append("TranCount["+TranCount+"]"+str);
		sb.append("NewBalance["+NewBalance+"]"+str);
		sb.append("NewFreezeAmount["+NewFreezeAmount+"]"+str);
		sb.append("Note["+Note+"]"+str);
		sb.append("Reserve["+Reserve+"]"+str);
		sb.append("RspCode["+RspCode+"]"+str);
		sb.append("RspMsg["+RspMsg+"]"+str);
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
			throw new Exception("传入的信息个数["+merthods.length+"]小于需要信息个数["+preferencenum+"]。数据信息["+str+"]");
		}else if(merthods.length>preferencenum+1){
			System.out.println("注意：当个传入的信息数量["+merthods.length+"]超出了所需的信息个数["+(preferencenum+1)+"]，是否为错误数据。数据信息["+str+"]");
		}
		try{
			int n = 0 ;
			this.QueryId = Long.parseLong(this.overLong(merthods[n++],this.QueryIdlength));
			this.TranDateTime = this.overLong(merthods[n++], this.TranDateTimelength);
			this.CounterId = this.overLong(merthods[n++], this.CounterIdlength);
			this.SupAcctId = this.overLong(merthods[n++], this.SupAcctIdlength);
			this.FuncCode = this.overLong(merthods[n++], this.FuncCodelength);
			this.CustAcctId = this.overLong(merthods[n++], this.CustAcctIdlength);
			this.CustName = this.overLong(merthods[n++], this.CustNamelength);
			this.ThirdCustId = this.overLong(merthods[n++], this.ThirdCustIdlength);
			this.ThirdLogNo = this.overLong(merthods[n++], this.ThirdLogNolength);
			this.CcyCode = this.overLong(merthods[n++], this.CcyCodelength);
			this.FreezeAmount = Double.valueOf(this.overLong(merthods[n++], this.FreezeAmountlength));
			this.UnfreezeAmount = Double.valueOf(this.overLong(merthods[n++], this.UnfreezeAmountlength));
			this.AddTranAmount = Double.valueOf(this.overLong(merthods[n++], this.AddTranAmountlength));
			this.CutTranAmount = Double.valueOf(this.overLong(merthods[n++], this.CutTranAmountlength));
			this.ProfitAmount = Double.valueOf(this.overLong(merthods[n++], this.ProfitAmountlength));
			this.LossAmount = Double.valueOf(this.overLong(merthods[n++], this.LossAmountlength));
			this.TranHandFee = Double.valueOf(this.overLong(merthods[n++], this.TranHandFeelength));
			this.TranCount = Integer.parseInt(this.overLong(merthods[n++], this.TranCountlength));
			this.NewBalance = Double.valueOf(this.overLong(merthods[n++], this.NewBalancelength));
			this.NewFreezeAmount = Double.valueOf(this.overLong(merthods[n++], this.NewFreezeAmountlength));
			this.Note = this.overLong(merthods[n++], this.Notelength);
			this.Reserve = this.overLong(merthods[n++], this.Reservelength);
			this.RspCode = this.overLong(merthods[n++], this.RspCodelength);
			this.RspMsg = this.overLong(merthods[n++], this.RspMsglength);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**生成流水信息*/
	public String setBody()throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(QueryId+this.separator);
		sb.append(TranDateTime+this.separator);
		sb.append(CounterId + this.separator);
		sb.append(SupAcctId + this.separator);
		sb.append(FuncCode + this.separator);
		sb.append(CustAcctId+this.separator);
		sb.append(CustName+this.separator);
		sb.append(ThirdCustId+this.separator);
		sb.append(ThirdLogNo+this.separator);
		sb.append(CcyCode+this.separator);
		sb.append(this.fmtDouble(FreezeAmount)+this.separator);
		sb.append(this.fmtDouble(UnfreezeAmount)+this.separator);
		sb.append(this.fmtDouble(AddTranAmount)+this.separator);
		sb.append(this.fmtDouble(CutTranAmount)+this.separator);
		sb.append(this.fmtDouble(ProfitAmount)+this.separator);
		sb.append(this.fmtDouble(LossAmount)+this.separator);
		sb.append(this.fmtDouble(TranHandFee)+this.separator);
		sb.append(TranCount+this.separator);
		sb.append(this.fmtDouble(NewBalance)+this.separator);
		sb.append(this.fmtDouble(NewFreezeAmount)+this.separator);
		sb.append(Note+this.separator);
		sb.append(Reserve+this.separator);
		sb.append(RspCode+this.separator);
		sb.append(RspMsg+this.separator);
		return sb.toString();
	}
	/**测试方法*/
	public static void main(String args[]){
		String str = "3&20100810000000&&11000098301001&30&888800000012104&测试一&0002&2548&RMB&406560.00&0.00&0.00&0.00&9522368.86&0.00&0.00&0&9522368.86&406560.00&交易网清算信息&&ERR044&该会员清算数据有误,清算后会导致银行端余额与交易网的不等&";
		BatFailResultChild bqc = new BatFailResultChild();
		try{
			bqc.getBody(str);
			System.out.println();
			System.out.println(bqc.toString());
			System.out.println();
			String str2 = bqc.setBody();
			System.out.println(str);
			System.out.println(str2);
			System.out.println();
			System.out.println(str.equals(str2));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}