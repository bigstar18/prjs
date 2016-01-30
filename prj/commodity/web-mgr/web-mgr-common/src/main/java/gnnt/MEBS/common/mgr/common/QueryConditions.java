package gnnt.MEBS.common.mgr.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件集合
 * 
 * @author xuejt
 */
public class QueryConditions {
	private List<Condition> conditionList = new ArrayList<Condition>();

	/**
	 * 直接传入conditionList
	 * 
	 * @param conditionList
	 */
	public QueryConditions(List<Condition> conditionList) {
		this.conditionList = conditionList;
	}

	/**
	 * 查询条件集合
	 */
	public QueryConditions() {
	}

	/**
	 * 根据参数直接构造conditionList
	 * 
	 * @param field
	 *            字段名
	 * @param operator
	 *            操作符
	 * @param value
	 *            值
	 */
	public QueryConditions(String field, String operator, Object value) {
		addCondition(field, operator, value);
	}

	/**
	 * 添加一个查询条件到conditionList
	 * 
	 * @param field
	 *            字段名
	 * @param operator
	 *            操作符
	 * @param value
	 *            值
	 */
	public void addCondition(String field, String operator, Object value) {
		conditionList.add(new Condition(field, operator, value));
	}

	/**
	 * 根据字段名移除查询条件
	 * 
	 * @param field
	 *            字段名
	 */
	public void removeCondition(String field) {
		for (int i = 0; i < conditionList.size(); i++) {
			Condition cond = (Condition) conditionList.get(i);
			if (cond.getField().equals(field)) {
				conditionList.remove(i);
			}
		}
	}

	/**
	 * 移除指定字段和操作符的查询条件
	 * 
	 * @param field
	 *            字段名
	 * @param operator
	 *            操作符
	 */
	public void removeCondition(String field, String operator) {
		for (int i = 0; i < conditionList.size(); i++) {
			Condition cond = (Condition) conditionList.get(i);
			if (cond.getField().equals(field)
					&& cond.getOperator().equals(operator)) {
				conditionList.remove(i);
			}
		}
	}

	/**
	 * 遍历查询条件 将每个查询条件的子语句合并
	 * 
	 * @return
	 */
	public String getFieldsSqlClause() {
		String sqlclause = null;
		if (conditionList != null && conditionList.size() > 0) {
			Condition cond = null;
			for (int i = 0; i < conditionList.size(); i++) {
				cond = (Condition) conditionList.get(i);
				if (sqlclause != null) {
					sqlclause = sqlclause + " and " + cond.getSqlClause();
				} else {
					sqlclause = cond.getSqlClause();
				}
			}
		}
		return sqlclause;
	}

	/**
	 * 遍历查询条件列表 将使用占位符的查询条件value写入数组<br>
	 * 在查询条件中没有使用占位符的不添加到数组
	 * 
	 * @return
	 */
	public Object[] getValueArray() {
		Object[] params = null;

		if (conditionList != null && conditionList.size() > 0) {
			Condition cond = null;
			List<Object> lst = new ArrayList<Object>();
			for (int i = 0; i < conditionList.size(); i++) {
				cond = (Condition) conditionList.get(i);
				Object obj = cond.getSqlValue();
				if (obj != null)
					lst.add(obj);
			}
			params = lst.toArray();
		}
		return params;
	}

	

	/**
	 * 根据字段名获取对应值
	 * 
	 * @param field
	 *            字段名
	 * @return
	 */
	public Object getConditionValue(String field) {
		if (field == null)
			return null;
		for (int i = 0; i < conditionList.size(); i++) {
			Condition cond = (Condition) conditionList.get(i);
			if (field.equals(cond.getField()))
				return cond.getValue();
		}
		return null;
	}

	/**
	 * 根据字段名和操作符获取对应值
	 * 
	 * @param field
	 *            字段名
	 * @param operator
	 *            操作符
	 * @return
	 */
	public Object getConditionValue(String field, String operator) {
		if (field == null || operator == null)
			return null;
		for (int i = 0; i < conditionList.size(); i++) {
			Condition cond = (Condition) conditionList.get(i);
			if (field.equals(cond.getField())
					&& operator.equals(cond.getOperator()))
				return cond.getValue();
		}
		return null;
	}

	/**
	 * 返回查询条件列表
	 * 
	 * @return
	 */
	public List<Condition> getConditionList() {
		return conditionList;
	}

	/**
	 * 设置查询条件列表
	 * 
	 * @param conditionList
	 */
	public void setConditionList(List<Condition> conditionList) {
		this.conditionList = conditionList;
	}

	public static void main(String[] args) {
		QueryConditions qc = new QueryConditions("code", "=", "");
		qc.addCondition("code", "is", "null");
		qc.addCondition("code", "is", "not null");
		System.out.println(qc.getFieldsSqlClause());
		System.out.println(qc.getValueArray().length);
	}
}
