package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;

/**开销户记录	(客户资金台账开销户文档)*/
public class OpenOrDelFirmValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**业务流水号*/
	public long maketNum;
	/**交易商代码*/
	public String firmId;
	/**客户姓名*/
	public String firmName;
	/**开户或销户*/
	public String openordel;
	public String toString(){
		String str="\n";
		StringBuffer sb = new StringBuffer();
		sb.append("maketNum(业务流水号)["+maketNum+"]"+str);
		sb.append("firmId(交易商代码)["+firmId+"]"+str);
		sb.append("firmName(客户姓名)["+firmName+"]"+str);
		sb.append("openordel(开户或销户)["+openordel+"]"+str);
		sb.append(str);
		return sb.toString();
	}
	/**
	 * @return 业务流水号
	 */
	public long getMaketNum() {
		return maketNum;
	}
	/**
	 * @param 业务流水号
	 */
	public void setMaketNum(long maketNum) {
		this.maketNum = maketNum;
	}
	/**
	 * @return 交易商代码
	 */
	public String getFirmId() {
		return firmId;
	}
	/**
	 * @param 交易商代码
	 */
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	/**
	 * @return 客户姓名
	 */
	public String getFirmName() {
		return firmName;
	}
	/**
	 * @param 客户姓名
	 */
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	/**
	 * @return 开户或销户
	 */
	public String getOpenordel() {
		return openordel;
	}
	/**
	 * @param 开户或销户
	 */
	public void setOpenordel(String openordel) {
		this.openordel = openordel;
	}
}
