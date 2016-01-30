package gnnt.trade.bank.hx;

import java.sql.Connection;
import java.util.List;

/**
 * 资金清算服务类
 * @author Administrator
 *
 */
public interface ExchangeData {

	/**
	 * 得到清算文件
	 * @param Date
	 * @return
	 */
	public List<QS> getQS(String bankId,String Date,Connection conn);
	/**
	 * 得到清算文件
	 * @param Date
	 * @return
	 */
	public List<DZ> getDZ(String bankId,String Date,Connection conn);
	/**
	 * 得到清算文件
	 * @param Date
	 * @return
	 */
	public List<ZZH> getZZH(String bankId,String Date,Connection conn);	

}
