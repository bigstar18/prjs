package gnnt.MEBS.common.mgr.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 分页信息
 * 第一页从1开始
 * </pre>
 * 
 * @author xuejt
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 2984068735031118368L;

	/**
	 * 查询结果记录集
	 */
	protected List<T> result;

	/**
	 * 每一页显示的条目数
	 */
	protected int pageSize;

	/**
	 * 当前页码
	 */
	protected int pageNumber;

	/**
	 * 总的数据条目数量
	 */
	protected int totalCount = 0;

	/**
	 * 分页信息
	 * 
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            每一页显示的条目数
	 * @param totalCount
	 *            总的数据条目数量
	 */
	public Page(int pageNumber, int pageSize, int totalCount) {
		this(pageNumber, pageSize, totalCount, new ArrayList<T>(0));
	}

	/**
	 * 分页信息
	 * 
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            每一页显示的条目数
	 * @param totalCount
	 *            总的数据条目数量
	 * @param result
	 *            查询结果记录集
	 */
	public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {
		if (pageSize <= 0)
			throw new IllegalArgumentException(
					"[pageSize] must great than zero");
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.totalCount = totalCount;
		setResult(result);
	}

	/**
	 * 设置查询结果记录集
	 * 
	 * @param elements
	 */
	public void setResult(List<T> elements) {
		if (elements == null)
			throw new IllegalArgumentException("'result' must be not null");
		this.result = elements;
	}

	/**
	 * 返回查询结果记录集
	 * 
	 * @return
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 总的数据条目数量，0表示没有数据
	 * 
	 * @return 总数量
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总的数据条目数量
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取每一页显示的条目数
	 * 
	 * @return 每一页显示的条目数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每一页显示的条目数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前页的页码
	 * 
	 * @return 当前页的页码
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置当前页的页码
	 * 
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	public int getTotalPage() {
		int totalPage = 1;
		if (totalCount % pageSize == 0) {
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}
		return totalPage;
	}
}
