package gnnt.trade.bank.vo.bankdz.sfz.sent.child;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.Serializable;
/**
 * 清算文件子类【1000】
 * BatQs+交易网代码（4位）+时间（14位）.txt
 */
public class BatQsChild extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int preferencenum = 22;
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
	/**功能代码（30: 扎差清算）*/
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
	/**当日订货盈亏(取数据时业务需求要加的)*/
	public double toDhykAmount;
	/**上日订货盈亏(取数据时业务需求要加的)*/
	public double yeDhykAmount;
	/**当日欠款(取数据时业务需求要加的)*/
	public double toQianAmount;
	/**上日欠款(取数据时业务需求需要加的)*/
	public double yeQianAmount;
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
		sb.append("当天总冻结金额FreezeAmount["+fmtDouble(FreezeAmount)+"]"+str);
		sb.append("当天总解冻金额UnfreezeAmount["+fmtDouble(UnfreezeAmount)+"]"+str);
		sb.append("当天成交的总货款(卖方)AddTranAmount["+fmtDouble(AddTranAmount)+"]"+str);
		sb.append("当天成交的总货款(买方)CutTranAmount["+fmtDouble(CutTranAmount)+"]"+str);
		sb.append("盈利总金额ProfitAmount["+fmtDouble(ProfitAmount)+"]"+str);
		sb.append("亏损总金额LossAmount["+fmtDouble(LossAmount)+"]"+str);
		sb.append("当天应该付给交易网的手续费总额TranHandFee["+fmtDouble(TranHandFee)+"]"+str);
		sb.append("当天交易总比数TranCount["+TranCount+"]"+str);
		sb.append("交易网端最新的交易商可用余额NewBalance["+fmtDouble(NewBalance)+"]"+str);
		sb.append("交易网端最新的冻结资金NewFreezeAmount["+fmtDouble(NewFreezeAmount)+"]"+str);
		sb.append("Note["+Note+"]"+str);
		sb.append("Reserve["+Reserve+"]"+str);
		sb.append("当日订货盈亏["+toDhykAmount+"]"+str);
		sb.append("上日订货盈亏["+toDhykAmount+"]"+str);
		sb.append("当日欠款["+toQianAmount+"]"+str);
		sb.append("上日欠款["+yeQianAmount+"]"+str);
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
			System.out.println("注意：当个传入的信息数量超出了所需的信息个数，是否为错误数据？序号："+merthods[0]);
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
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**生成流水信息*/
	public String setBody()throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(QueryId+this.separator);
		sb.append(overLong(TranDateTime,TranDateTimelength)+this.separator);
		sb.append(overLong(CounterId,CounterIdlength) + this.separator);
		sb.append(overLong(SupAcctId,SupAcctIdlength) + this.separator);
		sb.append(overLong(FuncCode,FuncCodelength) + this.separator);
		sb.append(overLong(CustAcctId,CustAcctIdlength)+this.separator);
		sb.append(overLong(CustName,CustNamelength)+this.separator);
		sb.append(overLong(ThirdCustId,ThirdCustIdlength)+this.separator);
		sb.append(overLong(ThirdLogNo,ThirdLogNolength)+this.separator);
		sb.append(overLong(CcyCode,CcyCodelength)+this.separator);
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
		sb.append(overLong(Note,Notelength)+this.separator);
		sb.append(overLong(Reserve,Reservelength)+this.separator);
		return sb.toString();
	}
	/**测试方法*/
	public static void main(String args[]){
		String str = "3&20100818000000&1&11000098301001&30&888800000012104&测试一&0002&2683&RMB&4331021.54&4331021.54&0.00&0.00&0.00&0.00&0.00&0&5521000.02&4331021.54&交易网清算信息&&";
		String str2 = "1&20100818000000&1&11000098301001&30&888800000012134&测试二&0004&2679&RMB&1401000.00&1401000.00&0.00&0.00&0.00&0.00&0.00&0&8224004.28&1401000.00&交易网清算信息&&";
		String str3 = "2&20100818000000&1&11000098301001&30&888800000012114&广州化工交易网&0003&2684&RMB&7061891.03&7061891.03&0.00&0.00&0.00&0.00&0.00&0&2785978.21&7061891.03&交易网清算信息&&";
		System.out.println("--------------------------------测试一--------------------------------");
		showMsg(5521000.02,2792560,str);
		System.out.println("--------------------------------测试二--------------------------------");
		showMsg(8224004.28,1520658.12,str2);
		System.out.println("--------------------------------广州化工测试--------------------------------");
		showMsg(2785978.21,8827061.97,str3);
	}
	public static void showMsg(double bankky,double bankdj,String str){
		BatQsChild bqc = new BatQsChild();
		try{
			bqc.getBody(str);
//			bqc.FreezeAmount = 1008015.98+3950679.66;
//			bqc.UnfreezeAmount = 406560+2166135.64;
//			bqc.AddTranAmount = 0;
//			bqc.CutTranAmount = 0;
//			bqc.ProfitAmount = 107905.98;
//			bqc.LossAmount = 0+1335897.44;
//			bqc.TranHandFee = 1000+6000;
//			bqc.NewBalance = 5901341.9;
//			bqc.NewFreezeAmount = 2684654.02;
//			System.out.println();
//			System.out.println(bqc.toString());
			System.out.println();
			System.out.println("银行记录上日银行可用["+bankky+"]");
			System.out.println("银行记录上日银行冻结["+bankdj+"]");
			System.out.println("当天冻结["+bqc.FreezeAmount+"]-当天解冻["+bqc.UnfreezeAmount+"]=["+(bqc.FreezeAmount-bqc.UnfreezeAmount)+"]");
			System.out.println("当前冻结资金["+bqc.NewFreezeAmount+"]");
			System.out.println("卖方货款["+bqc.AddTranAmount+"]-买方货款["+bqc.CutTranAmount+"]+盈利["+bqc.ProfitAmount+"]-亏损["+bqc.LossAmount+"]-手续费["+bqc.TranHandFee+"]-冻结资金["+bqc.FreezeAmount+"]+解冻资金["+bqc.UnfreezeAmount+"]=["+(bqc.AddTranAmount-bqc.CutTranAmount+bqc.ProfitAmount-bqc.LossAmount-bqc.TranHandFee-bqc.FreezeAmount+bqc.UnfreezeAmount)+"]");
			System.out.println("当前可用资金["+bqc.NewBalance+"]");
			System.out.println("根据市场信息计算上日银行冻结["+(bqc.NewFreezeAmount-(bqc.FreezeAmount-bqc.UnfreezeAmount))+"]");
			System.out.println("根据市场信息计算上日银行可用["+(bqc.NewBalance-(bqc.AddTranAmount-bqc.CutTranAmount+bqc.ProfitAmount-bqc.LossAmount-bqc.TranHandFee-bqc.FreezeAmount+bqc.UnfreezeAmount))+"]");
			System.out.println("可用扎差["+((bqc.NewBalance-(bqc.AddTranAmount-bqc.CutTranAmount+bqc.ProfitAmount-bqc.LossAmount-bqc.TranHandFee-bqc.FreezeAmount+bqc.UnfreezeAmount))-bankky)+"]");
			System.out.println("冻结扎差["+((bqc.NewFreezeAmount-(bqc.FreezeAmount-bqc.UnfreezeAmount))-bankdj)+"]");
			System.out.println("银行计算市场可用：卖方货款["+bqc.AddTranAmount+"]-买方货款["+bqc.CutTranAmount+"]+盈利["+bqc.ProfitAmount+"]-亏损["+bqc.LossAmount+"]-手续费["+bqc.TranHandFee+"]-冻结资金["+bqc.FreezeAmount+"]+解冻资金["+bqc.UnfreezeAmount+"]+上日可用资金["+bankky+"]=["+(bqc.AddTranAmount-bqc.CutTranAmount+bqc.ProfitAmount-bqc.LossAmount-bqc.TranHandFee-bqc.FreezeAmount+bqc.UnfreezeAmount+bankky)+"]");
			System.out.println("银行计算市场冻结：当天冻结["+bqc.FreezeAmount+"]-当天解冻["+bqc.UnfreezeAmount+"]+银行冻结["+bankdj+"]=["+(bqc.FreezeAmount-bqc.UnfreezeAmount+bankdj)+"]");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
