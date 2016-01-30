package gnnt.trade.bank.vo.bankdz.xy.sent;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
/**日终对账类*/
public class RZDZValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**文件名称*/
	public String fileName="rizhongduizhang";
	/**银行代码*/
	public String bankID;
	/**市场代码*/
	public String maketID;
	/**交易日期（格式：YYYYMMDD）*/
	public Date tradeDate;
	/**交易商对账*/
	private Vector<FirmDZValue> fdv;
	/**交易商对账*/
	public void setFdv(Vector<FirmDZValue> fdv){
		this.fdv = fdv;
	}
	/**交易商对账*/
	public Vector<FirmDZValue> getFdv(){
		if(this.fdv==null){
			this.fdv = new Vector<FirmDZValue>();
		}
		return this.fdv;
	}
	/**添加交易商对账*/
	public void putFdv(FirmDZValue fd){
		if(this.fdv==null){
			this.fdv = new Vector<FirmDZValue>();
		}
		fdv.add(fd);
	}
}
