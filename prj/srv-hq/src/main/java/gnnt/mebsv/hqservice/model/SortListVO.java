package gnnt.mebsv.hqservice.model;

public class SortListVO
{
  public static final int SORT_KEY_MAX = 10;
  public static final int SORT_BY_CODE = 0;
  public static final int SORT_BY_PRICE = 1;
  public static final int SORT_BY_UP = 2;
  public static final int SORT_BY_UPRATE = 3;
  public static final int SORT_BY_SHAKERATE = 4;
  public static final int SORT_BY_AMOUNTRATE = 5;
  public static final int SORT_BY_MONEY = 6;
  public static final int SORT_BY_CONSIGNRATE = 7;
  public static final int SORT_BY_MINRATE = 8;
  public static final int SORT_BY_TOTALAMOUNT = 9;
  public int sortKey;
  public String[] codeList = new String[0];

  public Object clone()
  {
    SortListVO localSortListVO = new SortListVO();
    localSortListVO.sortKey = this.sortKey;
    if (this.codeList != null)
    {
      localSortListVO.codeList = new String[this.codeList.length];
      for (int i = 0; i < localSortListVO.codeList.length; i++)
        localSortListVO.codeList[i] = this.codeList[i];
    }
    return localSortListVO;
  }
}