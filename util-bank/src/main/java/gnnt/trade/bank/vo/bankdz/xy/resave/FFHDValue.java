package gnnt.trade.bank.vo.bankdz.xy.resave;

import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
/**分分和对类*/
public class FFHDValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**文件名称*/
	public String fileName = "fenfenhedui";
	/**银行代码*/
	public String bankID;
	/**市场代码*/
	public String maketID;
	/**交易日期（格式：YYYYMMDD）*/
	public Date tradeDate;
	/**交易商分分核对*/
	private Vector<FirmDateValue> fdv;
	/**交易商分分核对*/
	public void setFdv(Vector<FirmDateValue> fdv){
		this.fdv = fdv;
	}
	/**交易商分分核对*/
	public Vector<FirmDateValue> getFdv(){
		if(this.fdv==null){
			this.fdv = new Vector<FirmDateValue>();
		}
		return fdv;
	}
	/**交易商分分核对*/
	public void putFdv(FirmDateValue fd){
		if(this.fdv==null){
			this.fdv = new Vector<FirmDateValue>();
		}
		fdv.add(fd);
	}
}