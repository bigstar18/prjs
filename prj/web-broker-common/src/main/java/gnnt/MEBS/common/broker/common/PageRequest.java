package gnnt.MEBS.common.broker.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageRequest<T> implements Serializable {
	private static final long serialVersionUID = -3399934325189446016L;
	public static final int DEFAULT_PAGE_SIZE = 1000;
	public static final int DEFAULT_PAGE_NUMBER = 1;
	private T filters;
	private int pageNumber;
	private int pageSize = 1000;
	private String sortColumns;

	public PageRequest() {
	}

	public PageRequest(T paramT) {
		this(1, 1000, paramT);
	}

	public PageRequest(int paramInt1, int paramInt2) {
		this(paramInt1, paramInt2, (T) null);
	}

	public PageRequest(int paramInt1, int paramInt2, T paramT) {
		this(paramInt1, paramInt2, paramT, null);
	}

	public PageRequest(int paramInt1, int paramInt2, String paramString) {
		this(paramInt1, paramInt2, null, paramString);
	}

	public PageRequest(int paramInt1, int paramInt2, T paramT, String paramString) {
		this.pageNumber = paramInt1;
		this.pageSize = paramInt2;
		setFilters(paramT);
		setSortColumns(paramString);
	}

	public T getFilters() {
		return this.filters;
	}

	public void setFilters(T paramT) {
		if ((!(paramT instanceof String)) && (!(paramT instanceof QueryConditions)))
			throw new IllegalArgumentException("只支持String和QueryConditions类型");
		this.filters = paramT;
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int paramInt) {
		this.pageNumber = paramInt;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int paramInt) {
		this.pageSize = paramInt;
	}

	public String getSortColumns() {
		if (this.sortColumns == null)
			return "";
		return this.sortColumns;
	}

	public void setSortColumns(String paramString) {
		checkSortColumnsSqlInjection(paramString);
		if ((paramString != null) && (paramString.length() > 200))
			throw new IllegalArgumentException("sortColumns.length() <= 200 must be true");
		this.sortColumns = paramString;
	}

	public List<SortInfo> getSortInfos() {
		return Collections.unmodifiableList(SortInfo.parseSortColumns(this.sortColumns));
	}

	private void checkSortColumnsSqlInjection(String paramString) {
		if (paramString == null)
			return;
		if ((paramString.indexOf("'") >= 0) || (paramString.indexOf("\\") >= 0))
			throw new IllegalArgumentException("sortColumns:" + paramString + " has SQL Injection risk");
	}
}