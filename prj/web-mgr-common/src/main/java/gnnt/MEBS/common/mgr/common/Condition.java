package gnnt.MEBS.common.mgr.common;

import java.math.BigDecimal;
import java.sql.Types;

/**
 * 查询条件对象
 * 
 * @author xuejt
 * 
 */
public class Condition {
	/**
	 * 字段
	 */
	private String field;
	/**
	 * 查询操作符：= like <= >= is in
	 */
	private String operator;
	/**
	 * 值
	 */
	private Object value;

	/**
	 * 构造器
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 */
	public Condition() {
	}

	/**
	 * 构造器
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 */
	public Condition(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * 获取字段名称
	 * 
	 * @return
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设置字段名称
	 * 
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 获取操作符
	 * 
	 * @return
	 */
	public String getOperator() {
		if("allLike".equals(operator)){
			return "like";
		}else{
			return operator;
		}
	}

	/**
	 * 设置操作符
	 * 
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取字段对应的值
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置字段对应的值
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 获取sql子句
	 * 
	 * @return
	 */
	public String getSqlClause() {
		if (this.getField() == null || getField().length() == 0
				|| operator == null || operator.length() == 0 || value == null)
			return "";
		String clause = null;
		if (isPlaceholder()) {
			clause = getField() + " " + getOperator() + " ?";
		} else {
			clause = getField() + " " + operator + " " + getValue();
		}
		return clause;
	}

	/**
	 * 获取 value对应的sql类型
	 * 
	 * @return
	 */
	public Integer getSqlDataType() {
		if (this.getField() == null || getField().length() == 0
				|| operator == null || operator.length() == 0 || value == null)
			return null;
		if (isPlaceholder()) {
			if (value instanceof String)
				return new Integer(Types.VARCHAR);
			else if (value instanceof Long)
				return new Integer(Types.BIGINT);
			else if (value instanceof java.util.Date)
				return new Integer(Types.DATE);
			else if (value instanceof java.sql.Date)
				return new Integer(Types.DATE);
			else if (value instanceof java.sql.Timestamp)
				return new Integer(Types.TIMESTAMP);
			else if (value instanceof BigDecimal)
				return new Integer(Types.NUMERIC);
			else
				return null;
		} else {
			return null;
		}
	}

	/**
	 * 获取值 用于替换占位符
	 * 
	 * @return
	 */
	public Object getSqlValue() {
		if (this.getField() == null || getField().length() == 0
				|| operator == null || operator.length() == 0 || value == null)
			return null;
		if (isPlaceholder()) {
			// 如果为like系列操作 则加上%；并将value中 %和_字符分别用 /%和/_替换 表示不属于sql中的匹配符号
			if ("like".equals(operator))
				return ((String) getValue()).replaceAll("%", "/%").replaceAll(
						"_", "/_")
						+ "%";
			else if ("allLike".equals(operator))
				return "%"
						+ ((String) getValue()).replaceAll("%", "/%")
								.replaceAll("_", "/_") + "%";
			else
				return value;
		} else {
			return null;
		}
	}

	/**
	 * 根据操作符判断是否使用占位符
	 * 
	 * @return true:使用占位符拼接sql false：不使用占位符 直接将值拼接到操作符后
	 */
	private boolean isPlaceholder() {
		if ("is".equals(operator))
			return false;
		if ("in".equals(operator))
			return false;
		if ("not in".equals(operator))
			return false;
		if(operator.trim().length()==0)
			return false;
		return true;
	}
}
