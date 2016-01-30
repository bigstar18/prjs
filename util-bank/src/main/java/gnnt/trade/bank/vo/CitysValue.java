/**
 * 
 */
package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 开户行所在城市 	---------华夏银行他行签约定制
 * @author YANGPC
 *
 */
public class CitysValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 城市ID */
	public String ID;
	
	/** 城市名称 */
	public String cityName;
	
	/** 上一级城市ID */
	public String parentID;
	
	public String toString(){
		String sep = "\n";
		StringBuilder sb = new StringBuilder();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("ID:" + ID + sep);
		sb.append("cityName:" + cityName + sep);
		sb.append("parentID:" + parentID + sep);
		return sb.toString();
	}
}
