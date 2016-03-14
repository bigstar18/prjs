package gnnt.MEBS.zcjs.memory.scheduling;

import java.util.ArrayList;
import java.util.List;

public class GoodsOrderShow
{
  private static GoodsOrderShow goodsOrderShow;
  private List list = new ArrayList();
  
  public static GoodsOrderShow createInstance()
  {
    if (goodsOrderShow == null) {
      synchronized (GoodsOrderShow.class)
      {
        if (goodsOrderShow == null) {
          goodsOrderShow = new GoodsOrderShow();
        }
      }
    }
    return goodsOrderShow;
  }
  
  public List getList()
  {
    return this.list;
  }
  
  public void setList(List list)
  {
    this.list = list;
  }
}
