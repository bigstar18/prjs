package gnnt.MEBS.finance.front.common;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
	private static final long serialVersionUID = -153741706163332879L;
	private int pageSize;
	private int pageNumber;
	private int totalCount = 0;
	private int startCount;
	private int totalPage;
	private List result;

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(int paramInt) {
		this.totalPage = paramInt;
	}

	public List getResult() {
		return this.result;
	}

	public void setResult(List paramList) {
		this.result = paramList;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int paramInt) {
		this.pageSize = paramInt;
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int paramInt) {
		this.pageNumber = paramInt;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int paramInt) {
		this.totalCount = paramInt;
	}

	public int getStartCount() {
		return this.startCount;
	}

	public void setStartCount(int paramInt) {
		this.startCount = paramInt;
	}

	public Page(int paramInt1, int paramInt2, int paramInt3) {
		if (paramInt2 <= 0)
			throw new IllegalArgumentException("[pageSize] must great than zero");
		this.pageSize = paramInt2;
		this.pageNumber = paramInt1;
		this.totalCount = paramInt3;
		this.startCount = ((paramInt1 - 1) * paramInt2);
	}
}