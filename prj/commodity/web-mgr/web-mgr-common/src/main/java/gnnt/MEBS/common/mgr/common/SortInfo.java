package gnnt.MEBS.common.mgr.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 排序的列 for PageRequest.getSortInfos()
 * 
 * @author xuejt
 */
public class SortInfo implements Serializable {

	private static final long serialVersionUID = 6959974032209696722L;

	private String columnName;
	private String sortOrder;

	/**
	 * 排序的列
	 */
	public SortInfo() {
	}

	/**
	 * 排序的列
	 * 
	 * @param columnName
	 *            列名
	 * @param sortOrder
	 *            排序方式
	 */
	public SortInfo(String columnName, String sortOrder) {
		super();
		this.columnName = columnName;
		this.sortOrder = sortOrder;
	}

	/**
	 * 获取列名
	 * 
	 * @return
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 设置列名
	 * 
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * 获取排序方式
	 * 
	 * @return
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * 设置排序方式
	 * 
	 * @param sortOrder
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 将多个排序列解析为 SortInfo的List
	 * 
	 * @param sortColumns
	 *            排序字符串
	 * @return List<SortInfo>
	 */
	public static List<SortInfo> parseSortColumns(String sortColumns) {
		if (sortColumns == null) {
			return new ArrayList<SortInfo>(0);
		}

		List<SortInfo> results = new ArrayList<SortInfo>();
		String[] sortSegments = sortColumns.trim().split(",");
		for (int i = 0; i < sortSegments.length; i++) {
			String sortSegment = sortSegments[i];
			// \\s表示 空格,回车,换行等空白符,
			// +号表示一个或多个的意思
			String[] array = sortSegment.split("\\s+");

			SortInfo sortInfo = new SortInfo();
			sortInfo.setColumnName(array[0]);
			sortInfo.setSortOrder(array.length == 2 ? array[1] : null);
			results.add(sortInfo);
		}
		return results;
	}

	public String toString() {
		return columnName + (sortOrder == null ? "" : " " + sortOrder);
	}
}
