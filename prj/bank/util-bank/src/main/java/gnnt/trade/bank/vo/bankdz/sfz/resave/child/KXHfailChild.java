package gnnt.trade.bank.vo.bankdz.sfz.resave.child;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.Serializable;
/**
 * 会员开销户流水文件
 * KXH+ ThirdLogNo CustLog+交易网代码（4位）+时间（14位）.txt
 */
public class KXHfailChild extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**当个类的属性个数*/
	protected int preferencenum = 9;
	/**序号*/
	public int number;
	private int numberlength = 5;
	/**银行前置流水号*/
	public String funID;
	private int funIDlength = 20;
	/**交易状态(1：开户 2：销户 3：待确认)*/
	public int status;
	private int statuslength=1;
	/**子账户账号*/
	public String account1;
	private int account1length=32;
	/**子账户性质*/
	public int type;
	private int typelength=1;
	/**子账户名称*/
	public String account1Name;
	private int account1Namelength=120;
	/**交易网会员代码*/
	public String firmID;
	private int firmIDlength = 32;
	/**交易日期*/
	public java.util.Date tradeDate;
	private int tradeDatelength = 8;
	/**操作柜员号*/
	public String counterId;
	private int counterIdlength = 12;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("number["+number+"]"+str);
		sb.append("funID["+funID+"]"+str);
		sb.append("status["+status+"]"+str);
		sb.append("account1["+account1+"]"+str);
		sb.append("type["+type+"]"+str);
		sb.append("account1Name["+account1Name+"]"+str);
		sb.append("firmID["+firmID+"]"+str);
		sb.append("tradeDate["+tradeDate+"]"+str);
		sb.append("counterId["+counterId+"]"+str);
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
			int n = 0;
			this.number = Integer.parseInt(this.overLong(merthods[n++], this.numberlength));
			this.funID = this.overLong(merthods[n++], this.funIDlength);
			this.status = Integer.parseInt(this.overLong(merthods[n++], this.statuslength));
			this.account1 = this.overLong(merthods[n++], this.account1length);
			this.type = Integer.parseInt(this.overLong(merthods[n++], this.typelength));
			this.account1Name = this.overLong(merthods[n++], this.account1Namelength);
			this.firmID = this.overLong(merthods[n++], this.firmIDlength);
			this.tradeDate = this.strToDate1(this.overLong(merthods[n++], this.tradeDatelength));
			this.counterId = this.overLong(merthods[n++], this.counterIdlength);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**生成流水信息*/
	public String setBody()throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(number+this.separator);
		sb.append(funID+this.separator);
		sb.append(status+this.separator);
		sb.append(account1+this.separator);
		sb.append(type+this.separator);
		sb.append(account1Name+this.separator);
		sb.append(firmID+this.separator);
		sb.append(this.dateToStr1(tradeDate)+this.separator);
		sb.append(counterId+this.separator);
		return sb.toString();
	}
	/**测试方法*/
	public static void main(String args[]){
		KXHfailChild kc = new KXHfailChild();
		kc.number=2;
		kc.funID = "funID";
		kc.status = 1;
		kc.account1 = "account1";
		kc.type = 1;
		kc.account1Name="account1Name";
		kc.firmID = "firmID";
		kc.tradeDate = kc.strToDate1("20100202");
		kc.counterId = "counterId";
		try{
			System.out.println(kc.toString());
			String str = kc.setBody();
			System.out.println(str);
			kc.getBody(str);
			System.out.println(kc.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
