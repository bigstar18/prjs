package gnnt.MEBS.common.broker.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T>
  implements Serializable
{
  private static final long serialVersionUID = 2984068735031118368L;
  protected List<T> result;
  protected int pageSize;
  protected int pageNumber;
  protected int totalCount = 0;

  public Page(int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramInt1, paramInt2, paramInt3, new ArrayList(0));
  }

  public Page(int paramInt1, int paramInt2, int paramInt3, List<T> paramList)
  {
    if (paramInt2 <= 0)
      throw new IllegalArgumentException("[pageSize] must great than zero");
    this.pageSize = paramInt2;
    this.pageNumber = paramInt1;
    this.totalCount = paramInt3;
    setResult(paramList);
  }

  public void setResult(List<T> paramList)
  {
    if (paramList == null)
      throw new IllegalArgumentException("'result' must be not null");
    this.result = paramList;
  }

  public List<T> getResult()
  {
    return this.result;
  }

  public int getTotalCount()
  {
    return this.totalCount;
  }

  public void setTotalCount(int paramInt)
  {
    this.totalCount = paramInt;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(int paramInt)
  {
    this.pageSize = paramInt;
  }

  public int getPageNumber()
  {
    return this.pageNumber;
  }

  public void setPageNumber(int paramInt)
  {
    this.pageNumber = paramInt;
  }

  public int getTotalPage()
  {
    int i = 1;
    if (this.totalCount % this.pageSize == 0)
      i = this.totalCount / this.pageSize;
    else
      i = this.totalCount / this.pageSize + 1;
    return i;
  }
}