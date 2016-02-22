package gnnt.trade.bank.vo.bankdz.xy.sent;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
/**日终清算类*/
public class RZQSValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**服务文件名称*/
	public String fileName = "rizhongqingsuan";
	/**银行代码*/
	public String bankID;
	/**市场代码*/
	public String maketID;
	/**交易日期（格式：YYYYMMDD）*/
	public Date tradeDate;
	/**市场清算类*/
	private MarketRightValue mr;
	/**交易商清算类*/
	private Vector<FirmRightValue> frv = new Vector<FirmRightValue>();
	/**市场清算信息*/
	public void setMarketRight(MarketRightValue mr){
		this.mr = mr;
	}
	/**市场清算信息*/
	public MarketRightValue getMarketRight(){
		if(this.mr==null){
			mr = new MarketRightValue();
		}
		return mr;
	}
	/**交易商清算信息*/
	public void setFrv(Vector<FirmRightValue> frv){
		this.frv = frv;
	}
	/**交易商清算信息*/
	public Vector<FirmRightValue> getFrv (){
		if(this.frv==null){
			frv = new Vector<FirmRightValue>();
		}
		return frv;
	}
	/**添加交易商清算信息*/
	public void putFrv(FirmRightValue fr){
		if(this.frv==null){
			frv = new Vector<FirmRightValue>();
		}
		frv.add(fr);
	}
}