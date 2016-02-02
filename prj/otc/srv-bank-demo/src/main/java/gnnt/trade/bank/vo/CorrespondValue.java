package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>Title: 交易账号代码银行账户对应关系对象类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 * @author zhangzhongli
 * @version 1.0
 */
public class CorrespondValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 编号 */
	public long id;
	/** 银行代码 */
	public String bankID;
	/** 交易账号代码 */
	public String firmID;
	/** 交易账号和银行绑定号 */
	public String contact;
	/** 状态：0：可用1：禁用 */
	public int status ;
	/** 银行账号(正式账号) */
	public String account;
	/** 银行账号(内部账号) */
	public String account1;
	/** 账户名 */
	public String accountName;
	/** 银行内部账号名称 */
	public String accountName1;
	/** 开户行名字 */
	public String bankName;
	/** 开户行省份 */
	public String bankProvince;
	/** 开户行市 */
	public String bankCity;
	/** 电话 */
	public String mobile;
	/** 电子邮件 */
	public String email;
	/** 签约 状态：0：签约失败1：签约成功 */
	public int isOpen ;
	/**
	 * 客户证件类型 1身份证, 2军官证,3国内护照,4户口本,5学员证,6退休证, 7临时身份证,8组织机构代码,9营业执照,
	 * A警官证,B解放军士兵证,C回乡证,D外国护照,E港澳台居民身份证,F台湾通行证及其他有效旅行证,G海外客户编号,
	 * H解放军文职干部证,I武警文职干部证,J武警士兵证,X其他有效证件,Z重号身份证
	 */
	public String cardType ;
	/** 证件号 */
	public String card;
	/** 交易账号冻结资金 */
	public double frozenFuns;
	/** 签约时间 */
	public java.util.Date opentime;
	/** 解约时间 */
	public java.util.Date deltime;
	/** 改约时间 */
	public java.util.Date modtime;
	/** 银行卡密码 */
	public String bankCardPassword;
	/** 银行名称 */
	public String name;
	/** 交易账号类型 */
	public String firmType;
	/** 银行是否为主银行 */
	public String mainBank;
	/** 所属会员 */
	public String belevemember;
	/** 证件类型对照 */
	private Map<String,String> _cardTypeMap = new HashMap<String,String>();
	/**
	 * 构造函数
	 */
	public CorrespondValue() {
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
		sb.append("bankID:" + this.bankID+sep);
		sb.append("firmID:" + this.firmID+sep);
		sb.append("contact:"+this.contact +sep);
		sb.append("status:" + this.status+sep);
		sb.append("account:" + this.account+sep);
		sb.append("account1:" + this.account1+sep);
		sb.append("accountName:" + this.accountName+sep);
		sb.append("accountName1:" + accountName1 + sep);
		sb.append("bankName:" + this.bankName+sep);
		sb.append("bankProvince:" + this.bankProvince+sep);
		sb.append("bankCity:" + this.bankCity+sep);
		sb.append("mobile:" + this.mobile+sep);
		sb.append("Email:" + this.email+sep);
		sb.append("IsOpen:" + this.isOpen+sep);
		sb.append("CardType:" + this.cardType+sep);
		sb.append("Card:" + this.card+sep);
		sb.append("frozenFuns:" + this.frozenFuns+sep);
		sb.append("opentime:" + this.opentime+sep);
		sb.append("deltime:" + this.deltime +sep);
		sb.append("modtime:" + this.modtime + sep);
		sb.append("mainBank:" + this.mainBank +sep);
		sb.append("belevemember:"+this.belevemember+sep);
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
