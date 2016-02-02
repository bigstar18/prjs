package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * f_b_FirmUser对应类
 * @author zhangxh
 *
 */
public class FirmUserValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 交易商ID */
	public String firmID;
	/** 交易账号和银行绑定号 */
	public String contact;	
	/** 交易账户名 */
	public String firmName;
	/**"交易商状态 0 可用,1 禁用,2 注销" 	 */
	public int status;
	/**"注册日期	 */
	public java.util.Date registerDate;
	/**注销日期  */
	public java.util.Date logoutDate;
	/** 交易商转账密码  */
	public String passWord;	
	/**
	 * 客户证件类型 1身份证, 2军官证,3国内护照,4户口本,5学员证,6退休证, 7临时身份证,8组织机构代码,9营业执照,
	 * A警官证,B解放军士兵证,C回乡证,D外国护照,E港澳台居民身份证,F台湾通行证及其他有效旅行证,G海外客户编号,
	 * H解放军文职干部证,I武警文职干部证,J武警士兵证,X其他有效证件,Z重号身份证
	 */
	public String cardType ;
	/** 证件号 */
	public String card;
	/**  银行编号 */
	public String bankID;
	/** f_b_firminfo 中的key */
	public String strkey;
	/**判断是否已经操作过华夏预签约  Y：是	 */
	public String checkSignHX;
	/**所属会员	 */
	public String belevemember;
	/**账号类型  交易商类型
	* C 客户
	* M 综合会员
	* S 特别会员
	*/
	public String firmType;
	/** 证件类型对照 */
	private Map<String,String> _cardTypeMap = new HashMap<String,String>();
	/**
	 * 构造函数
	 */
	public FirmUserValue() {
		init();
	}
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
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("firmID:" + this.firmID+sep);
		sb.append("contact:"+this.contact +sep);
		sb.append("firmName:" + this.firmName+sep);
		sb.append("status:" + this.status+sep);
		sb.append("registerDate:" + this.registerDate+sep);
		sb.append("logoutDate:" + this.logoutDate+sep);
		sb.append("passWord:" + passWord + sep);
		sb.append("CardType:" + this.cardType+sep);
		sb.append("Card:" + this.card+sep);
		sb.append("checkSignHX:" + this.checkSignHX+sep);
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
