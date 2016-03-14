package gnnt.MEBS.zcjs.memory.show;

import gnnt.MEBS.zcjs.model.CommodityProperty;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import gnnt.MEBS.zcjs.services.CommodityPropertyService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowFields
{
  private static ShowFields showFields;
  private Map<String, List<ProsceniumShow>> applicationMap = new HashMap();
  
  private ShowFields()
  {
    initData();
  }
  
  public static ShowFields createInstance()
  {
    if (showFields == null) {
      synchronized (ShowFields.class)
      {
        if (showFields == null) {
          showFields = new ShowFields();
        }
      }
    }
    return showFields;
  }
  
  public List<ProsceniumShow> getApplicationProperties(String key)
  {
    List<ProsceniumShow> list = (List)this.applicationMap.get(key);
    return list;
  }
  
  public void initData()
  {
    initH_Trade();
    initSubmit();
    initTrade();
    initH_GoodsOrder();
    initDailyDiscussion();
  }
  
  public List<CommodityProperty> getCommodityProperty()
  {
    CommodityPropertyService commodityPropertyService = (CommodityPropertyService)
      SysData.getBean("z_commodityPropertyService");
    List<CommodityProperty> list = commodityPropertyService.getObjectList(
      null, null);
    return list;
  }
  
  public void initGoodsOrder()
  {
    List<ProsceniumShow> GoodsOrderlist = new ArrayList();
    ProsceniumShow o0 = new ProsceniumShow();
    o0.setNodeKey("GoodsOrderNo");
    o0.setShowProperty("GoodsOrderNo");
    o0.setShowName("挂单号");
    o0.setType("String");
    o0.setRenderer("renderer:modOrder");
    GoodsOrderlist.add(o0);
    
    ProsceniumShow o1 = new ProsceniumShow();
    o1.setNodeKey("breedname");
    o1.setShowProperty("breedname");
    o1.setShowName("品种");
    o1.setType("String");
    o1.setRenderer("textalign:'right'");
    GoodsOrderlist.add(o1);
    
    ProsceniumShow o5 = new ProsceniumShow();
    o5.setNodeKey("Price");
    o5.setShowProperty("Price");
    o5.setShowName("单位价格");
    o5.setType("double");
    o5.setRenderer("renderer:changeMoney");
    GoodsOrderlist.add(o5);
    
    ProsceniumShow o6 = new ProsceniumShow();
    o6.setNodeKey("Quantity-PartBargainQuantity");
    o6.setShowProperty("CanQuantity");
    o6.setShowName("可成交数量");
    o6.setType("double");
    o6.setRenderer("textalign:'right'");
    GoodsOrderlist.add(o6);
    
    ProsceniumShow o8 = new ProsceniumShow();
    o8.setNodeKey("PartBargainQuantity");
    o8.setShowProperty("PartBargainQuantity");
    o8.setShowName("已成交数量");
    o8.setType("double");
    o8.setRenderer("textalign:'right'");
    GoodsOrderlist.add(o8);
    
    ProsceniumShow o10 = new ProsceniumShow();
    o10.setNodeKey("Status");
    o10.setShowProperty("Status");
    o10.setShowName("状态");
    o10.setType("int");
    o10.setRenderer("textalign:'center',disable:true,renderer:getStatus");
    GoodsOrderlist.add(o10);
    
    ProsceniumShow o3 = new ProsceniumShow();
    o3.setNodeKey("nvl(deliveryPlace,'不限')");
    o3.setShowProperty("deliveryPlace");
    o3.setShowName("交货地");
    o3.setType("String");
    o3.setRenderer("textalign:'center'");
    GoodsOrderlist.add(o3);
    
    ProsceniumShow o7 = new ProsceniumShow();
    o7.setNodeKey("EffectiveDays");
    o7.setShowProperty("EffectiveDays");
    o7.setShowName("有效期（天）");
    o7.setType("long");
    o7.setRenderer("textalign:'center'");
    GoodsOrderlist.add(o7);
    
    ProsceniumShow o9 = new ProsceniumShow();
    o9.setNodeKey("BusinessDirection||','||ISREGSTOCK");
    o9.setShowProperty("BusinessDirection");
    o9.setShowName("买卖方向");
    o9.setType("String");
    o9.setRenderer("textalign:'center',renderer:renderFal");
    GoodsOrderlist.add(o9);
    
    List<CommodityProperty> list = getCommodityProperty();
    for (CommodityProperty l : list)
    {
      ProsceniumShow pro = new ProsceniumShow();
      pro
        .setNodeKey("extractValue(xmlType(t.commodityproperties),'/root/property[@key=\"" + 
        l.getKey() + "\"]/value') ");
      pro.setShowProperty(l.getKey());
      pro.setShowName(l.getPropertyName());
      pro.setType("String");
      pro.setRenderer("textalign:'center'");
      GoodsOrderlist.add(pro);
    }
    ProsceniumShow o11 = new ProsceniumShow();
    o11.setNodeKey("GoodsOrderId||','||ISREGSTOCK");
    o11.setShowProperty("Id");
    o11.setShowName("");
    o11.setType("String");
    o11.setRenderer("textalign:'center',renderer:showXiangXi");
    GoodsOrderlist.add(o11);
    
    ProsceniumShow o12 = new ProsceniumShow();
    o12.setNodeKey("goodsOrderId");
    o12.setShowProperty("goodsOrderId");
    o12.setShowName("");
    o12.setType("long");
    o12.setRenderer("textalign:'center',renderer:renderSubmit");
    GoodsOrderlist.add(o12);
    
    ProsceniumShow o13 = new ProsceniumShow();
    o13.setNodeKey("commonSqlOne_old");
    o13.setShowProperty("Tourists");
    o13.setShowName("");
    o13.setType("String");
    o13.setRenderer("textalign:'center',renderer:renderDiscussion");
    GoodsOrderlist.add(o13);
    
    ProsceniumShow o14 = new ProsceniumShow();
    o14.setNodeKey("sortId");
    o14.setShowProperty("sortId");
    o14.setShowName("");
    o14.setType("String");
    o14.setIsShow("N");
    GoodsOrderlist.add(o14);
    
    ProsceniumShow o15 = new ProsceniumShow();
    o15.setNodeKey("commonSqlTwo_old");
    o15.setShowProperty("TouristsFirm");
    o15.setShowName("");
    o15.setType("String");
    o15.setIsShow("N");
    GoodsOrderlist.add(o15);
    
    this.applicationMap.put("GoodsOrder", GoodsOrderlist);
  }
  
  public void initH_GoodsOrder()
  {
    List<ProsceniumShow> H_GoodsOrderList = new ArrayList();
    
    ProsceniumShow o0 = new ProsceniumShow();
    o0.setNodeKey("GoodsOrderNo");
    o0.setShowProperty("GoodsOrderNO");
    o0.setShowName("委托号");
    o0.setType("String");
    H_GoodsOrderList.add(o0);
    
    ProsceniumShow o1 = new ProsceniumShow();
    o1.setNodeKey("breedname");
    o1.setShowProperty("breedname");
    o1.setShowName("商品名称");
    o1.setType("String");
    o1.setRenderer("textalign:'right'");
    H_GoodsOrderList.add(o1);
    
    ProsceniumShow o5 = new ProsceniumShow();
    o5.setNodeKey("Price");
    o5.setShowProperty("Price");
    o5.setShowName("单位价格");
    o5.setType("double");
    o5.setRenderer("renderer:changeMoney");
    H_GoodsOrderList.add(o5);
    
    ProsceniumShow o6 = new ProsceniumShow();
    o6.setNodeKey("Quantity-PartBargainQuantity");
    o6.setShowProperty("Quantity");
    o6.setShowName("可成交数量");
    o6.setType("double");
    o6.setRenderer("textalign:'right'");
    H_GoodsOrderList.add(o6);
    
    ProsceniumShow o8 = new ProsceniumShow();
    o8.setNodeKey("PartBargainQuantity");
    o8.setShowProperty("PartBargainQuantity");
    o8.setShowName("已成交数量");
    o8.setType("double");
    o8.setRenderer("textalign:'right'");
    H_GoodsOrderList.add(o8);
    

    ProsceniumShow o2 = new ProsceniumShow();
    o2.setNodeKey("Status");
    o2.setShowProperty("Status");
    o2.setShowName("状态");
    o2.setType("int");
    o2.setRenderer("textalign:'center',disable:true,renderer:getStatus");
    H_GoodsOrderList.add(o2);
    
    ProsceniumShow o3 = new ProsceniumShow();
    o3.setNodeKey("nvl(deliveryPlace,'不限')");
    o3.setShowProperty("deliveryPlace");
    o3.setShowName("交货地");
    o3.setType("String");
    o3.setRenderer("textalign:'center'");
    H_GoodsOrderList.add(o3);
    
    ProsceniumShow o7 = new ProsceniumShow();
    o7.setNodeKey("EffectiveDays");
    o7.setShowProperty("EffectiveDays");
    o7.setShowName("有效期(天)");
    o7.setType("long");
    o7.setRenderer("textalign:'center'");
    H_GoodsOrderList.add(o7);
    
    ProsceniumShow o9 = new ProsceniumShow();
    o9.setNodeKey("BusinessDirection");
    o9.setShowProperty("BusinessDirection");
    o9.setShowName("买卖方向");
    o9.setType("String");
    o9.setRenderer("textalign:'center',renderer:renderFal_BS");
    H_GoodsOrderList.add(o9);
    


















    this.applicationMap.put("H_GoodsOrder", H_GoodsOrderList);
  }
  
  public void initH_Trade()
  {
    List<ProsceniumShow> H_TradeList = new ArrayList();
    ProsceniumShow o0 = new ProsceniumShow();
    o0.setNodeKey("tradeNo");
    o0.setShowProperty("tradeNo");
    o0.setShowName("合同号");
    o0.setType("String");
    o0.setRenderer("textalign:'center'");
    H_TradeList.add(o0);
    
    ProsceniumShow o1 = new ProsceniumShow();
    o1.setNodeKey("breedname");
    o1.setShowProperty("breedname");
    o1.setShowName("商品名称");
    o1.setType("String");
    o1.setRenderer("textalign:'center'");
    H_TradeList.add(o1);
    
    ProsceniumShow o6 = new ProsceniumShow();
    o6.setNodeKey("Price");
    o6.setShowProperty("Price");
    o6.setShowName("单位价格");
    o6.setType("double");
    o6.setRenderer("renderer:changeMoney");
    H_TradeList.add(o6);
    
    ProsceniumShow o7 = new ProsceniumShow();
    o7.setNodeKey("Quantity");
    o7.setShowProperty("Quantity");
    o7.setShowName("已成交数量");
    o7.setType("double");
    o7.setRenderer("textalign:'center'");
    H_TradeList.add(o7);
    
    ProsceniumShow o4 = new ProsceniumShow();
    o4.setNodeKey("nvl(deliveryPlace,'不限')");
    o4.setShowProperty("deliveryPlace");
    o4.setShowName("交货地");
    o4.setType("String");
    o4.setRenderer("textalign:'center'");
    H_TradeList.add(o4);
    
    ProsceniumShow o2 = new ProsceniumShow();
    o2.setNodeKey("case isregstock when 'Y' then '是' else '否' end ");
    o2.setShowProperty("isregstock");
    o2.setShowName("是否卖仓单");
    o2.setType("String");
    o1.setRenderer("textalign:'center'");
    H_TradeList.add(o2);
    





















    ProsceniumShow o8 = new ProsceniumShow();
    o8.setNodeKey("TradeDate");
    o8.setShowProperty("TradeDate");
    o8.setShowName("成交时间");
    o8.setRenderer("renderer:formatDate");
    o8.setType("Date");
    H_TradeList.add(o8);
    ProsceniumShow o9 = new ProsceniumShow();
    o9.setNodeKey("ht.firmId_B");
    o9.setShowProperty("firmId_B");
    o9.setShowName("查看单据");
    o9.setType("String");
    o9.setRenderer("renderer:isNotInvoiceDelivery");
    H_TradeList.add(o9);
    
    ProsceniumShow o10 = new ProsceniumShow();
    o10.setNodeKey("ht.firmId_S");
    o10.setShowProperty("firmId_S");
    o10.setShowName("");
    o10.setType("String");
    o10.setIsShow("N");
    H_TradeList.add(o10);
    
    ProsceniumShow o11 = new ProsceniumShow();
    o11.setNodeKey("grade");
    o11.setShowProperty("grade");
    o11.setShowName("");
    o11.setType("String");
    o11.setIsShow("N");
    H_TradeList.add(o11);
    
    ProsceniumShow o12 = new ProsceniumShow();
    o12.setNodeKey("settleStatus");
    o12.setShowProperty("settleStatus");
    o12.setShowName("");
    o12.setType("String");
    o12.setIsShow("N");
    H_TradeList.add(o12);
    
    ProsceniumShow o13 = new ProsceniumShow();
    o13.setNodeKey("schedule");
    o13.setShowProperty("schedule");
    o13.setShowName("");
    o13.setType("String");
    o13.setIsShow("N");
    H_TradeList.add(o13);
    
    this.applicationMap.put("H_Trade", H_TradeList);
  }
  
  public void initSubmit()
  {
    List<ProsceniumShow> SubmitList = new ArrayList();
    ProsceniumShow o1 = new ProsceniumShow();
    o1.setNodeKey("GoodsOrderNo");
    o1.setShowProperty("GoodsOrderNo");
    o1.setShowName("挂单号");
    o1.setType("String");
    
    SubmitList.add(o1);
    
    ProsceniumShow o2 = new ProsceniumShow();
    o2.setNodeKey("breedname");
    o2.setShowProperty("breedname");
    o2.setShowName("商品名称");
    o2.setType("String");
    SubmitList.add(o2);
    
    List<CommodityProperty> list = getCommodityProperty();
    for (CommodityProperty l : list)
    {
      ProsceniumShow pro = new ProsceniumShow();
      pro
        .setNodeKey("extractValue(xmlType(t.commodityproperties),'/root/property[@key=\"" + 
        l.getKey() + "\"]/value') ");
      pro.setShowProperty(l.getKey());
      pro.setShowName(l.getPropertyName());
      pro.setType("String");
      pro.setRenderer("textalign:'center'");
      SubmitList.add(pro);
    }
    ProsceniumShow o6 = new ProsceniumShow();
    o6.setNodeKey("Price");
    o6.setShowProperty("Price");
    o6.setShowName("单位价格");
    o6.setType("double");
    o6.setRenderer("renderer:changeMoney");
    
    SubmitList.add(o6);
    
    ProsceniumShow o7 = new ProsceniumShow();
    o7.setNodeKey("Quantity");
    o7.setShowProperty("Quantity");
    o7.setShowName("数量");
    o7.setType("double");
    o7.setRenderer("textalign:'center'");
    SubmitList.add(o7);
    
    ProsceniumShow o8 = new ProsceniumShow();
    o8.setNodeKey("PartBargainQuantity");
    o8.setShowProperty("PartBargainQuantity");
    o8.setShowName("已成交数量");
    o8.setType("double");
    o8.setRenderer("textalign:'right'");
    SubmitList.add(o8);
    
    ProsceniumShow o9 = new ProsceniumShow();
    o9.setNodeKey("BusinessDirection");
    o9.setShowProperty("BusinessDirection");
    o9.setShowName("买卖方向");
    o9.setType("String");
    o9.setRenderer("textalign:'center',renderer:renderFal_BS");
    


    SubmitList.add(o9);
    
    ProsceniumShow o10 = new ProsceniumShow();
    o10.setNodeKey("goodsOrderId||','||status");
    o10.setShowProperty("gs");
    o10.setShowName("");
    o10.setType("long");
    o10.setRenderer("textalign:'center',renderer:renderRes_daily_submit");
    SubmitList.add(o10);
    
    this.applicationMap.put("Submit", SubmitList);
  }
  
  public void initDailyDiscussion()
  {
    List<ProsceniumShow> dailyDiscussionList = new ArrayList();
    
    ProsceniumShow o0 = new ProsceniumShow();
    o0.setNodeKey("GoodsOrderid");
    o0.setShowProperty("GoodsOrderid");
    o0.setShowName("委托号");
    o0.setType("String");
    dailyDiscussionList.add(o0);
    
    ProsceniumShow o1 = new ProsceniumShow();
    o1.setNodeKey("breedname");
    o1.setShowProperty("breedname");
    o1.setShowName("商品名称");
    o1.setType("String");
    dailyDiscussionList.add(o1);
    
    List<CommodityProperty> list = getCommodityProperty();
    for (CommodityProperty l : list)
    {
      ProsceniumShow pro = new ProsceniumShow();
      pro
        .setNodeKey("extractValue(xmlType(t.commodityproperties),'/root/property[@key=\"" + 
        l.getKey() + "\"]/value') ");
      pro.setShowProperty(l.getKey());
      pro.setShowName(l.getPropertyName());
      pro.setType("String");
      pro.setRenderer("textalign:'center'");
      dailyDiscussionList.add(pro);
    }
    ProsceniumShow o5 = new ProsceniumShow();
    o5.setNodeKey("DiscussPrice");
    o5.setShowProperty("DiscussPrice");
    o5.setShowName("单位价格");
    o5.setType("double");
    o5.setRenderer("renderer:changeMoney");
    dailyDiscussionList.add(o5);
    
    ProsceniumShow o6 = new ProsceniumShow();
    o6.setNodeKey("Quantity");
    o6.setShowProperty("Quantity");
    o6.setShowName("数量");
    o6.setType("double");
    o6.setRenderer("textalign:'right'");
    dailyDiscussionList.add(o6);
    
    ProsceniumShow o7 = new ProsceniumShow();
    o7
      .setNodeKey("case status when 1 then '等待确认' when 2 then '已成交' when 3 then '撤单' when 4 then '废除' when 5 then '系统撤单' when 6 then '挂单撤销' else '异常状态' end");
    o7.setShowProperty("status");
    o7.setShowName("状态");
    o7.setType("double");
    dailyDiscussionList.add(o7);
    
    ProsceniumShow o8 = new ProsceniumShow();
    o8.setNodeKey("BusinessDirection");
    o8.setShowProperty("BusinessDirection");
    o8.setShowName("买卖方向");
    o8.setType("String");
    o8.setRenderer("textalign:'center',renderer:renderFal_BS");
    dailyDiscussionList.add(o8);
    
    ProsceniumShow o9 = new ProsceniumShow();
    o9.setNodeKey("goodsOrderId||','||discusspriceid||','||status");
    o9.setShowProperty("gs");
    o9.setShowName("");
    o9.setType("long");
    o9.setRenderer("textalign:'center',renderer:renderRes_daily_disscuss");
    dailyDiscussionList.add(o9);
    
    this.applicationMap.put("dailyDiscussionList", dailyDiscussionList);
  }
  
  public void initTrade()
  {
    List<ProsceniumShow> TradeList = new ArrayList();
    
    ProsceniumShow o0 = new ProsceniumShow();
    o0.setNodeKey("tradeNo");
    o0.setShowProperty("tradeNo");
    o0.setShowName("合同号");
    o0.setType("String");
    o0.setRenderer("textalign:'center'");
    TradeList.add(o0);
    
    ProsceniumShow o1 = new ProsceniumShow();
    o1.setNodeKey("breedname");
    o1.setShowProperty("breedname");
    o1.setShowName("商品名称");
    o1.setType("String");
    o1.setRenderer("textalign:'center'");
    TradeList.add(o1);
    
    ProsceniumShow o6 = new ProsceniumShow();
    o6.setNodeKey("Price");
    o6.setShowProperty("Price");
    o6.setShowName("单位价格");
    o6.setType("double");
    o6.setRenderer("textalign:'center',renderer:changeMoney");
    TradeList.add(o6);
    
    ProsceniumShow o7 = new ProsceniumShow();
    o7.setNodeKey("Quantity");
    o7.setShowProperty("Quantity");
    o7.setShowName("数量");
    o7.setType("double");
    o7.setRenderer("textalign:'center'");
    TradeList.add(o7);
    
    ProsceniumShow o2 = new ProsceniumShow();
    o2.setNodeKey("case isregstock when 'Y' then '是' else '否' end");
    o2.setShowProperty("isregstock");
    o2.setShowName("是否卖仓单");
    o2.setType("String");
    o2.setRenderer("textalign:'center'");
    TradeList.add(o2);
    
    ProsceniumShow o4 = new ProsceniumShow();
    o4.setNodeKey("nvl(deliveryPlace,'不限')");
    o4.setShowProperty("deliveryPlace");
    o4.setShowName("交货地");
    o4.setType("String");
    o4.setRenderer("textalign:'center'");
    TradeList.add(o4);
    




















    ProsceniumShow o8 = new ProsceniumShow();
    o8.setNodeKey("TradeDate");
    o8.setShowProperty("TradeDate");
    o8.setShowName("成交时间");
    o8.setType("Date");
    o8.setRenderer("renderer:formatDate");
    TradeList.add(o8);
    
    this.applicationMap.put("Trade", TradeList);
  }
  
  public Map<String, List<ProsceniumShow>> getApplicationMap()
  {
    return this.applicationMap;
  }
  
  public void setApplicationMap(Map<String, List<ProsceniumShow>> applicationMap)
  {
    this.applicationMap = applicationMap;
  }
}
