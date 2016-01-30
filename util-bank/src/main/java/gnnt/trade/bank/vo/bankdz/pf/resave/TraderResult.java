package gnnt.trade.bank.vo.bankdz.pf.resave;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import java.util.Vector;
public class TraderResult {

	/**
	 * 冻结解冻数据返回信息
	 */
	public Vector<Margins> mVe= new Vector<Margins>();
	
	/**
	 * 交易明细数据返回信息
	 */
	public Vector<TradeList> tVe = new Vector<TradeList>();
	/**
	 * 连接超时次数
	 */
	public int timeOutCount = 0;
	/**
	 * 银行处理失败数据记录数
	 */
	public int faileCount = 0;
	/**
	 * 是否存在交易数据
	 */
	public int flag = 0;
}
