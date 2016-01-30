package gnnt.MEBS.common.mgr.common;

/**
 * 存储过程传入参数
 * 
 * @author xuejt
 * 
 */
public class ProcedureParameter {
	/**
	 * 输出参数
	 */
	public final static int OUTPARAMETER = 1;
	/**
	 * 输入参数
	 */
	public final static int INPARAMETER = 2;

	private int parameterType;
	private int sqlType;
	private String name;
	private Object value;

	/**
	 * @return 参数类型（OUTPARAMETER/INPARAMETER）
	 */
	public int getParameterType() {
		return parameterType;
	}

	/**
	 * @param 参数类型
	 *            （OUTPARAMETER/INPARAMETER）
	 */
	public void setParameterType(int parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * @return 参数数据类型
	 */
	public int getSqlType() {
		return sqlType;
	}

	/**
	 * @param 参数数据类型
	 *            例如Types.NUMERIC
	 */
	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

	/**
	 * @return 参数名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param 参数名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 参数值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param 参数值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
