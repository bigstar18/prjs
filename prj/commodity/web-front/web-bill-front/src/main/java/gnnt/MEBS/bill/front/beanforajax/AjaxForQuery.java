package gnnt.MEBS.bill.front.beanforajax;

import gnnt.MEBS.bill.front.model.commoditymanage.Breed;
import gnnt.MEBS.bill.front.model.commoditymanage.Category;
import gnnt.MEBS.bill.front.model.stockmanage.Stock;
import gnnt.MEBS.bill.front.model.stockmanage.StockGoodsProperty;
import gnnt.MEBS.common.front.beanforajax.BaseAjax;
import gnnt.MEBS.common.front.service.StandardService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxForQuery")
@Scope("request")
public class AjaxForQuery
  extends BaseAjax
{
  public String getStockJson()
  {
    this.logger.debug("通过仓单编号获取仓单信息，并封装成json对象");
    HttpServletRequest localHttpServletRequest = getRequest();
    String str = localHttpServletRequest.getParameter("stockId");
    if ((str != null) && (str.trim().length() > 0))
    {
      Stock localStock = new Stock();
      localStock.setStockId(str);
      localStock = (Stock)getService().get(localStock);
      HashMap localHashMap1 = new HashMap();
      localHashMap1.put("tradeMode", localStock.getBreed().getTradeMode());
      localHashMap1.put("breedId", "" + localStock.getBreed().getBreedId());
      localHashMap1.put("breedName", localStock.getBreed().getBreedName());
      localHashMap1.put("categoryId", "" + localStock.getBreed().getCategory().getCategoryId());
      localHashMap1.put("warehouseId", localStock.getWarehouseId());
      localHashMap1.put("unit", localStock.getUnit());
      localHashMap1.put("quantity", localStock.getQuantity());
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = localStock.getGoodsProperties().iterator();
      while (localIterator.hasNext())
      {
        StockGoodsProperty localStockGoodsProperty = (StockGoodsProperty)localIterator.next();
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("key", localStockGoodsProperty.getPropertyName());
        localHashMap2.put("value", localStockGoodsProperty.getPropertyValue());
        localJSONArray.add(localHashMap2);
      }
      localHashMap1.put("property", localJSONArray);
      this.jsonValidateReturn = createJSONArray(new Object[] { localHashMap1 });
    }
    return "success";
  }
}
