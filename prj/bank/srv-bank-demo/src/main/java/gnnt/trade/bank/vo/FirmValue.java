package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>Title: 交易账号对象类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 * @author zhangzhongli
 * @version 1.0
 */
public class FirmValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 交易账号代码
	 */
	public String firmID;
	/**
	 * 银行签约号
	 */
	public String contact;
	/**
	 * 交易账号名称
	 */
	public String firmName;
	/**
	 * 状态：0：已注册1：禁用2：已注销
	 */
	public int status;
	/**
	 * 注册日期
	 */
	public Date registerDate;
	/**
	 * 注销日期
	 */
	public Date logoutDate;
	/**
	 * 市场交易密码
	 */
	public String password;
	/**
	 * 证件类型
	 */
	public String cardType;
	/**
	 * 证件号码
	 */
	public String card;
	/**
	 * 交易账号签约状态
	 */
	public int isOpen;
	/**
	 * 交易账号银行账号
	 */
	public String account;
/**
	 * 扩展表字段
	 */
	public FirmInfo firminfo;
	
	/**
	 * 构造函数
	 */
	public FirmValue(){
		init();
	}
	private Map<String,String> _cardTypeMap = new HashMap<String,String>();
	public String getCardType(){
		return getCardType(this.cardType);
	}
	public String getCardType(String cardType){
		if(cardType != null && cardType.trim().length()>0){
			if(this._cardTypeMap.get(cardType.trim())!=null){
				return this._cardTypeMap.get(cardType.trim());
			}
		}
		return "";
	}
	
	/**
	 * 返回对象属性值
	 */
	public String toString() {
		String sep = "\n";
		StringBuilder sb = new StringBuilder();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("firmID:" + this.firmID+sep);
		sb.append("firmName:" + firmName + sep);
		sb.append("Status:" + this.status+sep);
		sb.append("registerDate:" + this.registerDate+sep);
		sb.append("logoutDate:" + this.logoutDate+sep);
		sb.append("cardType:" + this.cardType+sep);
		sb.append("card:" + this.card+sep);
		sb.append("isOpen:" + this.isOpen +sep);
		sb.append("account"+this.account+sep);
		sb.append("contact:"+this.contact+sep);
		sb.append("firmInfo:"+this.firminfo.toString()+sep);
		sb.append(sep);
		return sb.toString();
	}
	private void init(){
		_cardTypeMap.put("1", "身份证");
		_cardTypeMap.put("2", "军官证");
		_cardTypeMap.put("3", "国内护照");
		_cardTypeMap.put("4", "户口本");
		_cardTypeMap.put("5", "学员证");
		_cardTypeMap.put("6", "退休证");
		_cardTypeMap.put("7", "临时身份证");
		_cardTypeMap.put("8", "机构代码");
		_cardTypeMap.put("9", "营业执照");
		_cardTypeMap.put("A", "警官证");
		_cardTypeMap.put("B", "解放军士兵证");
		_cardTypeMap.put("C", "回乡证");
		_cardTypeMap.put("D", "外国护照");
		_cardTypeMap.put("E", "港澳台居民身份证");
		_cardTypeMap.put("F", "台湾通行证及其他有效旅行证");
		_cardTypeMap.put("G", "海外客户编号");
		_cardTypeMap.put("H", "解放军文职干部证");
		_cardTypeMap.put("I", "武警文职干部证");
		_cardTypeMap.put("J", "武警士兵证");
		_cardTypeMap.put("X", "其他有效证件");
		_cardTypeMap.put("Z", "重号身份证");
		_cardTypeMap.put("a", "法人代码");
	}
}
