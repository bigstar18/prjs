/**
 * 
 */
package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 签约银行信息类 	---------华夏银行他行签约定制
 * @author YANGPC
 *
 */
public class BanksInfoValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 节点号码 */
	public String nbkcode;
	
	/** 清算行号 */
	public String sabkcode;
	
	/** 所在城市(新增) */
	public String bnkcity;
	
	/** 节点名称 */
	public String nbkname;
	
	/** 节点简称 */
	public String nbksname;
	
	/** 节点地址 */
	public String nbkaddrss;
	
	/** 联系电话 */
	public String cnttel;
	
	/** 联系人 */
	public String cntper;
	
	/** 邮编 */
	public String postcode;
	
	/** 节点状态 */
	public String nbkstate;
	
	/** email地址 */
	public String bkemail;
	
	/** 备注 */
	public String content;
	
	
	public String toString(){
		String sep = "\n";
		StringBuilder sb = new StringBuilder();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("nbkcode:" + nbkcode + sep);
		sb.append("sabkcode:" + sabkcode + sep);
		sb.append("bnkcity:" + bnkcity + sep);
		sb.append("nbkname:" + nbkname + sep);
		sb.append("nbksname:" + nbksname + sep);
		sb.append("nbkaddrss:" + nbkaddrss + sep);
		sb.append("cnttel:" + cnttel + sep);
		sb.append("cntper:" + cntper + sep);
		sb.append("postcode:" + postcode + sep);
		sb.append("nbkstate:" + nbkstate + sep);
		sb.append("bkemail:" + bkemail + sep);
		sb.append("content:" + content + sep);
		sb.append(sep);
		return sb.toString();
	}

}
