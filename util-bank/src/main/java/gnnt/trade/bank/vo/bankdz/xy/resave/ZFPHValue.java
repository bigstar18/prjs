package gnnt.trade.bank.vo.bankdz.xy.resave;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**总分平衡*/
public class ZFPHValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**文件名称*/
	public String fileName = "zongfenpingheng";
	/**银行代码*/
	public String bankID;
	/**市场代码*/
	public String maketID;
	/**交易日期（格式：YYYYMMDD）*/
	public Date tradeDate;
	/**币别 001：人民币 013：港币 014：美元*/
	public String currency="001";
	/**钞汇标识（0：钞 1：汇）*/
	public int type;
	/**资金存管明细账户余额汇总*/
	public BigDecimal lastAccountBalance;
	/**资金汇总账号金额*/
	public BigDecimal accountBalance;
	/**总分对账结果(0：平 1：不平)*/
	public int result;
	/**返回本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("fileName:"+this.fileName+str);
		sb.append("bankID:"+this.bankID+str);
		sb.append("maketID:"+this.maketID+str);
		sb.append("tradeDate:"+this.tradeDate+str);
		sb.append("currency:"+this.currency+str);
		sb.append("type:"+this.type+str);
		sb.append("lastAccountBalance:"+this.lastAccountBalance+str);
		sb.append("accountBalance:"+this.accountBalance+str);
		sb.append("result:"+this.result+str);
		sb.append(str);
		return sb.toString();
	}
}
