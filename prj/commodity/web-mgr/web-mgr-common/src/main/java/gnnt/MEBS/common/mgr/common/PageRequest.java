package gnnt.MEBS.common.mgr.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页请求信息
 * 
 * @author Administrator
 * 
 * @param <T>
 *            目前支持String和QueryConditions
 */
public class PageRequest<T> implements Serializable {

	private static final long serialVersionUID = -3399934325189446016L;

	/**
	 * 默认每页1000条
	 */
	public static final int DEFAULT_PAGE_SIZE = 1000;

	/**
	 * 默认第一页
	 */
	public static final int DEFAULT_PAGE_NUMBER = 1;

	/**
	 * 过滤参数
	 */
	private T filters;

	/**
	 * 页号码,页码从1开始
	 */
	private int pageNumber;

	/**
	 * 分页大小
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 排序的多个列,如: username desc,age asc
	 */
	private String sortColumns;

	/**
	 * 使用查询库进行查询
	 */
	private boolean isQueryDB;

	/**
	 * 分页请求信息
	 */
	public PageRequest() {
	}

	/**
	 * 分页请求信息
	 * 
	 * @param filters
	 *            过滤参数
	 */
	public PageRequest(T filters) {
		this(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, filters);
	}

	/**
	 * 分页请求信息
	 * 
	 * @param pageNumber
	 *            页码号
	 * @param pageSize
	 *            分页大小
	 */
	public PageRequest(int pageNumber, int pageSize) {
		this(pageNumber, pageSize, (T) null);
	}

	/**
	 * 分页请求信息
	 * 
	 * @param pageNumber
	 *            页码号
	 * @param pageSize
	 *            分页大小
	 * @param filters
	 *            过滤参数
	 */
	public PageRequest(int pageNumber, int pageSize, T filters) {
		this(pageNumber, pageSize, filters, null);
	}

	/**
	 * 分页请求信息
	 * 
	 * @param pageNumber
	 *            页码号
	 * @param pageSize
	 *            分页大小
	 * @param sortColumns
	 *            排序的多个列 ,如: username desc,age asc
	 */
	public PageRequest(int pageNumber, int pageSize, String sortColumns) {
		this(pageNumber, pageSize, null, sortColumns);
	}

	/**
	 * 分页请求信息
	 * 
	 * @param pageNumber
	 *            页码号
	 * @param pageSize
	 *            分页大小
	 * @param filters
	 *            过滤参数
	 * @param sortColumns
	 *            ,如: username desc,age asc
	 */
	public PageRequest(int pageNumber, int pageSize, T filters,
			String sortColumns) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		setFilters(filters);
		setSortColumns(sortColumns);
	}

	/**
	 * 返回过滤参数
	 * 
	 * @return
	 */
	public T getFilters() {
		return filters;
	}

	/**
	 * 设置过滤参数
	 * 
	 * @param filters
	 */
	public void setFilters(T filters) {
		// 只支持String和QueryConditions类型
		if (!(filters instanceof String)
				&& !(filters instanceof QueryConditions)) {
			throw new IllegalArgumentException("只支持String和QueryConditions类型");
		}
		this.filters = filters;
	}

	/**
	 * 获取页码
	 * 
	 * @return
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取分页大小
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置分页大小
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取排序列
	 * 
	 * @return
	 */
	public String getSortColumns() {
		if (sortColumns == null)
			return "";
		return sortColumns;
	}

	/**
	 * 排序的列,可以同时多列,使用逗号分隔,如 username desc,age asc
	 * 
	 * @return
	 */
	public void setSortColumns(String sortColumns) {
		checkSortColumnsSqlInjection(sortColumns);
		if (sortColumns != null && sortColumns.length() > 200) {
			throw new IllegalArgumentException(
					"sortColumns.length() <= 200 must be true");
		}
		this.sortColumns = sortColumns;
	}

	/**
	 * @return 是否使用查询库进行查询
	 */
	public boolean isQueryDB() {
		return isQueryDB;
	}

	/**
	 * @param isQueryDB
	 *            是否使用查询库进行查询
	 */
	public void setQueryDB(boolean isQueryDB) {
		this.isQueryDB = isQueryDB;
	}

	/**
	 * 将sortColumns进行解析，返回只读的SortInfo列表
	 * 
	 * @return
	 */
	public List<SortInfo> getSortInfos() {
		return Collections.unmodifiableList(SortInfo
				.parseSortColumns(sortColumns));
	}

	/**
	 * 检查排序列字符串 防止sql注入，检查内容为 "'"和"\\"
	 * 
	 * @param sortColumns
	 */
	private void checkSortColumnsSqlInjection(String sortColumns) {
		if (sortColumns == null)
			return;
		if (sortColumns.indexOf("'") >= 0 || sortColumns.indexOf("\\") >= 0) {
			throw new IllegalArgumentException("sortColumns:" + sortColumns
					+ " has SQL Injection risk");
		}
	}
}
