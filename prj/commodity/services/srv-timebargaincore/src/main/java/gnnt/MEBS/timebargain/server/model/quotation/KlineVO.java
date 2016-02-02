package gnnt.MEBS.timebargain.server.model.quotation;

public class KlineVO
{
  private DayDataVO[] minKLineList;
  private int type;
  private String hisMinTable;
  
  public KlineVO() {}
  
  public KlineVO(DayDataVO[] paramArrayOfDayDataVO, int paramInt)
  {
    this.minKLineList = paramArrayOfDayDataVO;
    this.type = paramInt;
    switch (this.type)
    {
    case 0: 
      this.hisMinTable = " HistoryDayData ";
      break;
    case 1: 
      this.hisMinTable = " HistoryMinData ";
      break;
    case 5: 
      this.hisMinTable = " History5MinData ";
      break;
    default: 
      this.hisMinTable = " History5MinData ";
    }
  }
  
  public DayDataVO[] getMinKLineList()
  {
    return this.minKLineList;
  }
  
  public void setMinKLineList(DayDataVO[] paramArrayOfDayDataVO)
  {
    this.minKLineList = paramArrayOfDayDataVO;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getHisMinTable()
  {
    return this.hisMinTable;
  }
}
