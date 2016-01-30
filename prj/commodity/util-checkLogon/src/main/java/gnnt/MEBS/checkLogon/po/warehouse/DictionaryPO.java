
package gnnt.MEBS.checkLogon.po.warehouse;

import gnnt.MEBS.checkLogon.po.Clone;

/**
 * <P>类说明：系统配置字典表
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-25上午10:36:03|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class DictionaryPO extends Clone{
	/** 字典 key 值 */
	private String key;

	/** 字典名称 */
	private String name;

	/** 字典值 */
	private String value;

	/** 说明信息 */
	private String note;

	/**
	 * 
	 * 字典 key 值
	 * <br/><br/>
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * 字典 key 值
	 * <br/><br/>
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 
	 * 字典名称
	 * <br/><br/>
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 字典名称
	 * <br/><br/>
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * 字典值
	 * <br/><br/>
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * 字典值
	 * <br/><br/>
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * 说明信息
	 * <br/><br/>
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 
	 * 说明信息
	 * <br/><br/>
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

}

