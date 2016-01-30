package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.server.model.OrderReturnValue;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.TotalDate;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.util.Arith;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseXml
{
  private static final Log log = LogFactory.getLog(ResponseXml.class);
  private static final String cmdSuff = "99";
  private static boolean debugcommodity = false;
  
  public static String responseXml(String name, int retCode, String message)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(name).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(retCode)
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(message)
      .append("</MESSAGE>")
      .append("</RESULT>")
      .append("</REP>")
      .append("</GNNT>");
    log.debug("responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String responseXml(String userid, String name, int retCode, String message)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(name).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(retCode)
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(message)
      .append("</MESSAGE>")
      .append("</RESULT>")
      .append("</REP>")
      .append("</GNNT>");
    log.debug("responseXml:userid=" + userid + sb.toString());
    return sb.toString();
  }
  
  public static String order(String userid, ResponseResult rr, OrderReturnValue orv)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<OR_N>")
      .append(orv.getOrderNo())
      .append("</OR_N>")
      .append("<TIME>")
      .append(orv.getOrderTime())
      .append("</TIME>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>")
      .append("</REP>")
      .append("</GNNT>");
    log.debug("responseXml:userid=" + userid + sb.toString());
    return sb.toString();
  }
  
  public static String logon(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    List list = rr.getResultList();
    TraderLogonInfo info = null;
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getLongRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<MODULE_ID>")
      .append("15")
      .append("</MODULE_ID>")
      .append("<TYPE>")
      .append("</TYPE>");
    if ((list != null) && (list.size() > 0))
    {
      info = (TraderLogonInfo)list.get(0);
      sb.append("<LAST_TIME>")
        .append(info.getLastTime() == null ? "" : info.getLastTime().substring(0, 19))
        .append("</LAST_TIME>")
        .append("<LAST_IP>")
        .append(info.getLastIP() == null ? "" : info.getLastIP())
        .append("</LAST_IP>")
        .append("<CHG_PWD>");
      if (info.getForceChangePwd() == 0) {
        sb.append(2);
      } else if (info.getForceChangePwd() == 1) {
        sb.append(1);
      }
      sb.append("</CHG_PWD>");
      sb.append("<NAME>")
        .append(info.getTraderName())
        .append("</NAME>");
      sb.append("<RANDOM_KEY>")
        .append(info.getTrustKey())
        .append("</RANDOM_KEY>");
      sb.append("<USERID>")
        .append(info.getTraderId())
        .append("</USERID>");
    }
    sb.append("</RESULT>").append("</REP>").append("</GNNT>");
    log.debug("logon's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String check_user(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getLongRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<MODULE_ID>")
      .append("15")
      .append("</MODULE_ID>")
      .append("</RESULT>")
      .append("</REP>")
      .append("</GNNT>");
    log.debug("check_user's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String firm_info(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        BigDecimal in_out_Amount = ((BigDecimal)map.get("inAmount")).subtract((BigDecimal)map.get("outAmount"));
        
        BigDecimal right = ((BigDecimal)map.get("balance")).add((BigDecimal)map.get("ClearMargin")).subtract((BigDecimal)map.get("ClearAssure"))
          .add((BigDecimal)map.get("RuntimeSettleMargin")).add((BigDecimal)map.get("clearfl")).add((BigDecimal)map.get("PL")).add((BigDecimal)map.get("close_pl")).subtract((BigDecimal)map.get("tradefee"));
        




        BigDecimal ot_change = ((BigDecimal)map.get("usefulFund")).subtract((BigDecimal)map.get("lastbalance"))
          .subtract(((BigDecimal)map.get("ClearMargin")).add((BigDecimal)map.get("ClearFL")).add(in_out_Amount).add((BigDecimal)map.get("close_pl"))
          .subtract((BigDecimal)map.get("runtimemargin")).subtract((BigDecimal)map.get("runtimefl")).subtract((BigDecimal)map.get("orderFrozen"))
          .subtract((BigDecimal)map.get("tradefee")).subtract((BigDecimal)map.get("otherFrozen")));
        


        sb.append("<REC>")
          .append("<FI>")
          .append((String)map.get("FirmID"))
          .append("</FI>")
          .append("<FN>")
          .append((String)map.get("Name"))
          .append("</FN>")
          .append("<IF>")
          .append(((BigDecimal)map.get("lastbalance")).toString())
          .append("</IF>")
          .append("<CM>")
          .append(((BigDecimal)map.get("ClearMargin")).toString())
          .append("</CM>")
          .append("<CF>")
          .append(((BigDecimal)map.get("clearfl")).toString())
          .append("</CF>")
          .append("<RM>")
          .append(((BigDecimal)map.get("runtimemargin")).toString())
          .append("</RM>")
          .append("<RF>")
          .append(((BigDecimal)map.get("runtimefl")).toString())
          .append("</RF>")
          .append("<OR_F>")
          .append(((BigDecimal)map.get("orderFrozen")).toString())
          .append("</OR_F>")
          .append("<OT_F>")
          .append(((BigDecimal)map.get("otherFrozen")).toString())
          .append("</OT_F>")
          .append("<UF>")
          .append(map.get("usefulFund") == null ? "" : ((BigDecimal)map.get("usefulFund")).toString())
          .append("</UF>")
          .append("<EQT>")
          .append(right.toString())
          .append("</EQT>")
          .append("<FEE>")
          .append(((BigDecimal)map.get("tradefee")).toString())
          .append("</FEE>")
          .append("<PL>")
          .append(((BigDecimal)map.get("close_pl")).toString())
          .append("</PL>")
          .append("<IOF>")
          .append(in_out_Amount.toString())
          .append("</IOF>")
          .append("<OC>")
          .append(ot_change.toString())
          .append("</OC>")
          .append("<HPF>")
          .append((BigDecimal)map.get("PL"))
          .append("</HPF>")
          .append("<PGF>")
          .append((BigDecimal)map.get("MaxOverdraft"))
          .append("</PGF>")
          .append("<CDLST>");
        String OperateCode = (String)map.get("OperateCode") == null ? "" : (String)map.get("OperateCode");
        String[] codeArr = OperateCode.split(",");
        for (int j = 0; j < codeArr.length; j++) {
          sb.append("<CD>").append(codeArr[j]).append("</CD>");
        }
        sb.append("</CDLST>");
        
        sb.append("<CDRS>");
        for (int j = 0; j < codeArr.length; j++) {
          sb.append("<CDS><CD>").append(codeArr[j]).append("</CD></CDS>");
        }
        sb.append("</CDRS>").append("<RE_F>>").append(map.get("usefulFund") == null ? "" : ((BigDecimal)map.get("usefulFund")).toString()).append("</RE_F>>").append("<MA_A>").append(((BigDecimal)map.get("ClearMargin")).toString()).append("</MA_A>").append("<C_M>").append(((BigDecimal)map.get("runtimemargin")).toString()).append("</C_M>").append("<TT_M>").append(((BigDecimal)map.get("runtimemargin")).toString()).append("</TT_M>").append("<LFPL>").append(((BigDecimal)map.get("runtimefl")).toString()).append("</LFPL>").append("<EQT>").append(right.toString()).append("</EQT>").append("<CODE>");
        for (int j = 0; j < codeArr.length; j++) {
          sb.append(codeArr[j]);
        }
        sb.append("</CODE>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("Firm_info's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String tradequery(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = records.size() - 1; i >= 0; i--)
      {
        Trade trade = (Trade)records.get(i);
        sb.append("<REC>")
          .append("<TR_N>")
          .append(trade.getA_TradeNo().toString())
          .append("</TR_N>");
        sb.append("<OR_N>")
          .append(trade.getA_OrderNo() == null ? "0" : trade.getA_OrderNo().toString())
          .append("</OR_N>");
        if (trade.getTradeTime() != null) {
          sb.append("<TI>").append(DateUtil.getDateTime(trade.getTradeTime())).append("</TI>");
        }
        sb.append("<TY>").append(trade.getBS_Flag().toString()).append("</TY>").append("<SE_F>").append(trade.getOrderType().toString()).append("</SE_F>").append("<FI_I>").append(trade.getFirmID()).append("</FI_I>").append("<CU_I>").append(trade.getCustomerID()).append("</CU_I>").append("<CO_I>").append("99" + trade.getCommodityID()).append("</CO_I>").append("<PR>").append(trade.getPrice().toString()).append("</PR>").append("<QTY>").append(trade.getQuantity().toString()).append("</QTY>");
        if (trade.getHoldPrice() != null) {
          sb.append("<O_PR>").append(trade.getHoldPrice().toString()).append("</O_PR>");
        }
        if (trade.getClose_PL() != null) {
          sb.append("<LIQPL>").append(trade.getClose_PL().toString()).append("</LIQPL>");
        }
        if (trade.getTradeFee() != null) {
          sb.append("<COMM>").append(trade.getTradeFee().toString()).append("</COMM>");
        }
        if (trade.getA_TradeNo_Closed() != null) {
          sb.append("<S_TR_N>").append(trade.getA_TradeNo_Closed().toString()).append("</S_TR_N>");
        }
        sb.append("<A_TR_N>").append(trade.getM_TradeNo().toString()).append("</A_TR_N>").append("<TR_T>").append(trade.getTradeType().toString()).append("</TR_T>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("Tradequery's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String tradepagingquery(ResponseResult rr)
  {
    TotalDate totalDate = rr.getTotalDate();
    if (totalDate == null) {
      totalDate = new TotalDate();
    }
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TOTALROW>")
      .append("<TOTALNUM>")
      .append(String.valueOf(totalDate.getTotalNum()))
      .append("</TOTALNUM>")
      .append("<TOTALQTY>")
      .append(String.valueOf(totalDate.getTotalQty()))
      .append("</TOTALQTY>")
      .append("<TOTALLIQPL>")
      .append(String.valueOf(totalDate.getTotalLiqpl()))
      .append("</TOTALLIQPL>")
      .append("<TOTALCOMM>")
      .append(String.valueOf(totalDate.getTotalComm()))
      .append("</TOTALCOMM>")
      .append("</TOTALROW>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = records.size() - 1; i >= 0; i--)
      {
        Trade trade = (Trade)records.get(i);
        sb.append("<REC>")
          .append("<TR_N>")
          .append(trade.getA_TradeNo().toString())
          .append("</TR_N>");
        sb.append("<OR_N>")
          .append(trade.getA_OrderNo() == null ? "0" : trade.getA_OrderNo().toString())
          .append("</OR_N>");
        if (trade.getTradeTime() != null) {
          sb.append("<TI>").append(DateUtil.getDateTime(trade.getTradeTime())).append("</TI>");
        }
        sb.append("<TY>").append(trade.getBS_Flag().toString()).append("</TY>").append("<SE_F>").append(trade.getOrderType().toString()).append("</SE_F>").append("<FI_I>").append(trade.getFirmID()).append("</FI_I>").append("<CU_I>").append(trade.getCustomerID()).append("</CU_I>").append("<CO_I>").append("99" + trade.getCommodityID()).append("</CO_I>").append("<PR>").append(trade.getPrice().toString()).append("</PR>").append("<QTY>").append(trade.getQuantity().toString()).append("</QTY>");
        if (trade.getHoldPrice() != null) {
          sb.append("<O_PR>").append(trade.getHoldPrice().toString()).append("</O_PR>");
        }
        if (trade.getClose_PL() != null) {
          sb.append("<LIQPL>").append(trade.getClose_PL().toString()).append("</LIQPL>");
        }
        if (trade.getTradeFee() != null) {
          sb.append("<COMM>").append(trade.getTradeFee().toString()).append("</COMM>");
        }
        if (trade.getA_TradeNo_Closed() != null) {
          sb.append("<S_TR_N>").append(trade.getA_TradeNo_Closed().toString()).append("</S_TR_N>");
        }
        sb.append("<A_TR_N>").append(trade.getM_TradeNo().toString()).append("</A_TR_N>").append("<TR_T>").append(trade.getTradeType().toString()).append("</TR_T>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("Tradequery's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String sys_time_query(ResponseResult rr, String dd, boolean showTDRP, int count)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    try
    {
      sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append("\">").append("<RESULT>").append("<RETCODE>").append(rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>").append(rr.getMessage()).append("</MESSAGE>");
      List records = rr.getResultList();
      if ((records != null) && (records.size() != 0))
      {
        Map map = (Map)records.get(0);
        sb.append("<CU_T>")
          .append((String)map.get("CUR_TIME"))
          .append("</CU_T>")
          .append("<CU_D>")
          .append((String)map.get("CUR_DATE"))
          .append("</CU_D>")
          .append("<TV_U>")
          .append((String)map.get("TV_USEC"))
          .append("</TV_U>");
        

        sb.append("<MARK>").append(count).append("</MARK>");
        if (map.get("L_ID") != null) {
          sb.append("<L_ID>").append((String)map.get("L_ID")).append("</L_ID>");
        }
        sb.append("<NEW_T>").append(map.get("NEW_T")).append("</NEW_T>");
        sb.append("<TD_TTL>")
          .append(map.get("TD_TTL"))
          .append("</TD_TTL>");
        if (!map.get("NEW_T").toString().equals("0")) {
          debugcommodity = true;
        }
        if (showTDRP)
        {
          sb.append("<TDRP>");
          ArrayList list = (ArrayList)map.get("Trades");
          if ((list != null) && (list.size() > 0)) {
            for (int i = 0; i < list.size(); i++)
            {
              Trade t = (Trade)list.get(i);
              log.debug("OR_N : " + t.getA_OrderNo() + "TDQTY:" + t.getQuantity());
              sb.append("<TDS>")
                .append("<OR_N>" + t.getA_OrderNo() + "</OR_N>" + 
                
                "<CO_I>" + "99" + t.getCommodityID() + "</CO_I>" + 
                "<TDQTY>" + t.getQuantity() + "</TDQTY>")
                .append("</TDS>");
            }
          }
          sb.append("</TDRP>");
        }
      }
      if (dd != null) {
        sb.append("<DAY>").append(dd).append("</DAY>");
      }
      sb.append("</RESULT>");
      sb.append("</REP>")
        .append("</GNNT>");
      if (debugcommodity) {
        log.debug("Sys_time_query's responseXml:userid=" + rr.getUserID() + sb.toString());
      }
      debugcommodity = false;
    }
    catch (Exception e)
    {
      log.error("ResponseXml出错:" + e);
      errorException(e);
      e.printStackTrace();
    }
    return sb.toString();
  }
  
  public static String queryDateQty(ResponseResult rr)
  {
    TotalDate totalDate = rr.getTotalDate();
    
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TTLREC>");
    if (totalDate != null) {
      sb.append(totalDate.getTotalNum());
    }
    sb.append("</TTLREC>").append("</RESULT>").append("<RESULTLIST>");
    List list = rr.getResultList();
    for (int i = 0; i < list.size(); i++)
    {
      TotalDate totalDates = (TotalDate)list.get(i);
      sb.append("<REC>")
        .append("<TOTALROW>")
        .append("<RESPONSENAME>")
        .append(totalDates.getResponseName())
        .append("</RESPONSENAME>")
        .append("<TOTALNUM>")
        .append(totalDates.getTotalNum())
        .append("</TOTALNUM>")
        .append("<TOTALQTY>")
        .append(totalDates.getTotalQty())
        .append("</TOTALQTY>")
        .append("<UNTRADEQTY>")
        .append(totalDates.getTotalUnTradeQty())
        .append("</UNTRADEQTY>")
        .append("<TOTALLIQPL>")
        .append(totalDates.getTotalLiqpl())
        .append("</TOTALLIQPL>")
        .append("<TOTALCOMM>")
        .append(totalDates.getTotalComm())
        .append("</TOTALCOMM>")
        .append("</TOTALROW>")
        .append("</REC>");
    }
    sb.append("</RESULTLIST>").append("</REP>").append("</GNNT>");
    log.debug(rr.getName() + "'s responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String my_order_query(ResponseResult rr, long lastestUpdateTime)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size())
      .append("</TTLREC>")
      .append("<UT>")
      .append(lastestUpdateTime)
      .append("</UT>")
      .append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<OR_N>")
          .append(((BigDecimal)map.get("A_OrderNo")).toString())
          .append("</OR_N>")
          .append("<TIME>")
          .append(map.get("OrderTime") == null ? "" : DateUtil.getDateTime((Date)map.get("OrderTime")))
          .append("</TIME>")
          .append("<STA>")
          .append(((BigDecimal)map.get("Status")).toString())
          .append("</STA>")
          .append("<TYPE>")
          .append(map.get("BS_Flag") == null ? "0" : ((BigDecimal)map.get("BS_Flag")).toString())
          .append("</TYPE>")
          .append("<SE_F>")
          .append(((BigDecimal)map.get("OrderType")).toString())
          .append("</SE_F>")
          .append("<TR_I>")
          .append(map.get("TraderID") == null ? "" : (String)map.get("TraderID"))
          .append("</TR_I>")
          .append("<FI_I>")
          .append(map.get("FirmID") == null ? "" : (String)map.get("FirmID"))
          .append("</FI_I>")
          .append("<CU_I>")
          .append(map.get("CustomerID") == null ? "" : (String)map.get("CustomerID"))
          .append("</CU_I>")
          .append("<CO_I>")
          .append("99" + (String)map.get("COMMODITYID"))
          .append("</CO_I>")
          .append("<PRI>")
          .append(map.get("Price") == null ? "0" : ((BigDecimal)map.get("Price")).toString())
          .append("</PRI>")
          .append("<QTY>")
          .append(map.get("Quantity") == null ? "0" : ((BigDecimal)map.get("Quantity")).toString())
          .append("</QTY>")
          .append("<BAL>")
          .append(map.get("notTradeQty") == null ? "0" : ((BigDecimal)map.get("notTradeQty")).toString())
          .append("</BAL>")
          .append("<L_P>")
          .append(map.get("SpecPrice") == null ? "0" : ((BigDecimal)map.get("SpecPrice")).toString())
          .append("</L_P>")
          .append("<WD_T>")
          .append(map.get("WithdrawTime") == null ? "" : DateUtil.getDateTime((Date)map.get("WithdrawTime")))
          .append("</WD_T>")
          



          .append("<C_F>")
          .append(map.get("CloseFlag") == null ? "" : ((BigDecimal)map.get("CloseFlag")).toString())
          .append("</C_F>")
          .append("<B_T_T>")
          .append(map.get("BillTradeType") == null ? "" : ((BigDecimal)map.get("BillTradeType")).toString())
          .append("</B_T_T>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug(rr.getName() + "'s responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String my_order_pagingquery(ResponseResult rr, long lastestUpdateTime)
  {
    List records = rr.getResultList();
    TotalDate totalDate = rr.getTotalDate();
    if (totalDate == null) {
      totalDate = new TotalDate();
    }
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size())
      .append("</TTLREC>")
      .append("<UT>")
      .append(lastestUpdateTime)
      .append("</UT>")
      .append("<TOTALROW>")
      .append("<TOTALNUM>")
      .append(String.valueOf(totalDate.getTotalNum()))
      .append("</TOTALNUM>")
      .append("<TOTALQTY>")
      .append(String.valueOf(totalDate.getTotalQty()))
      .append("</TOTALQTY>")
      .append("<UNTRADEQTY>")
      .append(String.valueOf(totalDate.getTotalUnTradeQty()))
      .append("</UNTRADEQTY>")
      .append("</TOTALROW>")
      .append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<OR_N>")
          .append(((BigDecimal)map.get("A_OrderNo")).toString())
          .append("</OR_N>")
          .append("<TIME>")
          .append(map.get("OrderTime") == null ? "" : DateUtil.getDateTime((Date)map.get("OrderTime")))
          .append("</TIME>")
          .append("<STA>")
          .append(((BigDecimal)map.get("Status")).toString())
          .append("</STA>")
          .append("<TYPE>")
          .append(map.get("BS_Flag") == null ? "0" : ((BigDecimal)map.get("BS_Flag")).toString())
          .append("</TYPE>")
          .append("<SE_F>")
          .append(((BigDecimal)map.get("OrderType")).toString())
          .append("</SE_F>")
          .append("<TR_I>")
          .append(map.get("TraderID") == null ? "" : (String)map.get("TraderID"))
          .append("</TR_I>")
          .append("<FI_I>")
          .append(map.get("FirmID") == null ? "" : (String)map.get("FirmID"))
          .append("</FI_I>")
          .append("<CU_I>")
          .append(map.get("CustomerID") == null ? "" : (String)map.get("CustomerID"))
          .append("</CU_I>")
          .append("<CO_I>")
          .append("99" + (String)map.get("COMMODITYID"))
          .append("</CO_I>")
          .append("<PRI>")
          .append(map.get("Price") == null ? "0" : ((BigDecimal)map.get("Price")).toString())
          .append("</PRI>")
          .append("<QTY>")
          .append(map.get("Quantity") == null ? "0" : ((BigDecimal)map.get("Quantity")).toString())
          .append("</QTY>")
          .append("<BAL>")
          .append(map.get("notTradeQty") == null ? "0" : ((BigDecimal)map.get("notTradeQty")).toString())
          .append("</BAL>")
          .append("<L_P>")
          .append(map.get("SpecPrice") == null ? "0" : ((BigDecimal)map.get("SpecPrice")).toString())
          .append("</L_P>")
          .append("<WD_T>")
          .append(map.get("WithdrawTime") == null ? "" : DateUtil.getDateTime((Date)map.get("WithdrawTime")))
          .append("</WD_T>")
          



          .append("<C_F>")
          .append(map.get("CloseFlag") == null ? "" : ((BigDecimal)map.get("CloseFlag")).toString())
          .append("</C_F>")
          .append("<B_T_T>")
          .append(map.get("BillTradeType") == null ? "" : ((BigDecimal)map.get("BillTradeType")).toString())
          .append("</B_T_T>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug(rr.getName() + "'s responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String holding_query(ResponseResult rr)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size())
      .append("</TTLREC>")
      .append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<CO_I>")
          .append("99" + (String)map.get("commodityid"))
          .append("</CO_I>")
          .append("<CU_I>")
          .append((String)map.get("customerid"))
          .append("</CU_I>")
          .append("<BU_H>")
          .append(map.get("buyQty") == null ? "0" : ((BigDecimal)map.get("buyQty")).toString())
          .append("</BU_H>")
          .append("<SE_H>")
          .append(map.get("sellQty") == null ? "0" : ((BigDecimal)map.get("sellQty")).toString())
          .append("</SE_H>")
          .append("<B_V_H>")
          .append(map.get("buyAvailQty") == null ? "0" : ((BigDecimal)map.get("buyAvailQty")).toString())
          .append("</B_V_H>")
          .append("<S_V_H>")
          .append(map.get("sellAvailQty") == null ? "0" : ((BigDecimal)map.get("sellAvailQty")).toString())
          .append("</S_V_H>")
          .append("<BU_A>")
          .append(map.get("buyEvenPrice") == null ? "0" : ((BigDecimal)map.get("buyEvenPrice")).toString())
          .append("</BU_A>")
          .append("<SE_A>")
          .append(map.get("sellEvenPrice") == null ? "0" : ((BigDecimal)map.get("sellEvenPrice")).toString())
          .append("</SE_A>")
          .append("<GO_Q>")
          .append(map.get("gageqty") == null ? "0" : ((BigDecimal)map.get("gageqty")).toString())
          .append("</GO_Q>")
          .append("<FL_P>")
          .append(map.get("bsPL") == null ? "0" : ((BigDecimal)map.get("bsPL")).toString())
          .append("</FL_P>")
          .append("<MAR>")
          .append(map.get("holdmargin") == null ? "0" : ((BigDecimal)map.get("holdmargin")).toString())
          .append("</MAR>")
          

          .append("<NP_PF>")
          .append(map.get("lastestPL") == null ? "0" : ((BigDecimal)map.get("lastestPL")).toString())
          .append("</NP_PF>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("holding_query's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String commodity_query(ResponseResult rr, Privilege prvg)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<MA_I>")
          .append((String)map.get("MarketCode"))
          .append("</MA_I>")
          .append("<CO_I>")
          .append((String)map.get("CommodityID"))
          .append("</CO_I>")
          .append("<CO_N>")
          .append((String)map.get("Name"))
          .append("</CO_N>")
          .append("<L_SET>")
          .append(DateUtil.getDate((Date)map.get("SettleDate")))
          .append("</L_SET>")
          .append("<STA>")
          .append(((BigDecimal)map.get("Status")).toString())
          .append("</STA>")
          .append("<CT_S>")
          .append(((BigDecimal)map.get("ContractFactor")).toString())
          .append("</CT_S>");
        

        int spreadAlgr = ((BigDecimal)map.get("SpreadAlgr")).intValue();
        double SP_U = ((BigDecimal)map.get("SpreadUpLmt")).doubleValue();
        double SP_D = ((BigDecimal)map.get("SpreadDownLmt")).doubleValue();
        double PR_C = ((BigDecimal)map.get("LastPrice")).doubleValue();
        BigDecimal minPriceMove = (BigDecimal)map.get("MinPriceMove");
        if (spreadAlgr == 1)
        {
          SP_U = Math.round(Arith.mul(PR_C, SP_U) / minPriceMove.doubleValue()) * minPriceMove.doubleValue();
          SP_U = Arith.add(PR_C, SP_U);
          SP_D = Math.round(Arith.mul(PR_C, SP_D) / minPriceMove.doubleValue()) * minPriceMove.doubleValue();
          SP_D = Arith.sub(PR_C, SP_D);
        }
        else if (spreadAlgr == 2)
        {
          SP_U = Arith.add(PR_C, SP_U);
          SP_D = Arith.sub(PR_C, SP_D);
        }
        sb.append("<SPREAD>").append(minPriceMove.toString()).append("</SPREAD>").append("<MQ>").append(map.get("MinQuantityMove").toString()).append("</MQ>").append("<SP_U>").append(SP_U).append("</SP_U>").append("<SP_D>").append(SP_D).append("</SP_D>");
        




        int tax = 0;
        int taxInclusive = ((BigDecimal)map.get("TaxInclusive")).intValue();
        if (taxInclusive == 0) {
          sb.append("<TR>").append("0").append("</TR>");
        } else {
          sb.append("<TR>").append(map.get("addedTax")).append("</TR>");
        }
        int MarginAlgr = ((BigDecimal)map.get("MarginAlgr")).intValue();
        double MarginRate_B = ((BigDecimal)map.get("MarginRate_B")).doubleValue();
        double MarginRate_S = ((BigDecimal)map.get("MarginRate_S")).doubleValue();
        double MarginAssure_B = ((BigDecimal)map.get("MarginAssure_B")).doubleValue();
        double MarginAssure_S = ((BigDecimal)map.get("MarginAssure_S")).doubleValue();
        if (prvg != null)
        {
          Map mg_Map = prvg.getFirm_MarginRate();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get((String)map.get("CommodityID"));
            if (v_map != null)
            {
              MarginAlgr = ((BigDecimal)v_map.get("MarginAlgr")).intValue();
              MarginRate_B = ((BigDecimal)v_map.get("MarginRate_B")).doubleValue();
              MarginRate_S = ((BigDecimal)v_map.get("MarginRate_S")).doubleValue();
              MarginAssure_B = ((BigDecimal)v_map.get("MarginAssure_B")).doubleValue();
              MarginAssure_S = ((BigDecimal)v_map.get("MarginAssure_S")).doubleValue();
              

              MarginRate_B += MarginAssure_B;
              MarginRate_S += MarginAssure_S;
            }
          }
        }
        if (MarginAlgr == 1)
        {
          MarginRate_B *= 100.0D;
          MarginRate_S *= 100.0D;
          MarginAssure_B *= 100.0D;
          MarginAssure_S *= 100.0D;
        }
        sb.append("<MA_A>").append(((BigDecimal)map.get("MarginAlgr")).toString()).append("</MA_A>");
        
        sb.append("<BMA>")
          .append(String.valueOf(MarginRate_B))
          .append("</BMA>")
          .append("<SMA>")
          .append(String.valueOf(MarginRate_S))
          .append("</SMA>")
          .append("<BAS>")
          .append(String.valueOf(MarginAssure_B))
          .append("</BAS>")
          .append("<SAS>")
          .append(String.valueOf(MarginAssure_S))
          .append("</SAS>")
          .append("<PR_C>")
          .append(((BigDecimal)map.get("LastPrice")).toString())
          .append("</PR_C>");
        double FeeRate_B = ((BigDecimal)map.get("FeeRate_B")).doubleValue();
        double FeeRate_S = ((BigDecimal)map.get("FeeRate_S")).doubleValue();
        
        double todayclosefeerate_b = ((BigDecimal)map.get("TodayCloseFeeRate_B")).doubleValue();
        double todayclosefeerate_s = ((BigDecimal)map.get("TodayCloseFeeRate_S")).doubleValue();
        double historyclosefeerate_b = ((BigDecimal)map.get("HistoryCloseFeeRate_B")).doubleValue();
        double historyclosefeerate_s = ((BigDecimal)map.get("HistoryCloseFeeRate_S")).doubleValue();
        double forceclosefeerate_b = ((BigDecimal)map.get("ForceCloseFeeRate_B")).doubleValue();
        double forceclosefeerate_s = ((BigDecimal)map.get("ForceCloseFeeRate_S")).doubleValue();
        
        int feeAlgr = ((BigDecimal)map.get("FeeAlgr")).intValue();
        double SettleFeeRate_B = ((BigDecimal)map.get("SETTLEFEERATE_B")).doubleValue();
        double SettleFeeRate_S = ((BigDecimal)map.get("SETTLEFEERATE_S")).doubleValue();
        int SettleFeeAlgr = ((BigDecimal)map.get("SETTLEFEEALGR")).intValue();
        Object maxHoldDay = map.get("MAXHOLDPOSITIONDAY");
        if (prvg != null)
        {
          Map fee_Map = prvg.getFirm_FeeRate();
          if ((fee_Map != null) && (fee_Map.size() > 0))
          {
            Map v_map = (Map)fee_Map.get((String)map.get("CommodityID"));
            if ((v_map != null) && (v_map.size() > 0))
            {
              feeAlgr = ((BigDecimal)v_map.get("FeeAlgr")).intValue();
              FeeRate_B = ((BigDecimal)v_map.get("FeeRate_B")).doubleValue();
              FeeRate_S = ((BigDecimal)v_map.get("FeeRate_S")).doubleValue();
              
              todayclosefeerate_b = ((BigDecimal)v_map.get("todayclosefeerate_b")).doubleValue();
              todayclosefeerate_s = ((BigDecimal)v_map.get("todayclosefeerate_s")).doubleValue();
              historyclosefeerate_b = ((BigDecimal)v_map.get("historyclosefeerate_b")).doubleValue();
              historyclosefeerate_s = ((BigDecimal)v_map.get("historyclosefeerate_s")).doubleValue();
              forceclosefeerate_b = ((BigDecimal)v_map.get("forceclosefeerate_b")).doubleValue();
              forceclosefeerate_s = ((BigDecimal)v_map.get("forceclosefeerate_s")).doubleValue();
            }
          }
          Map settleFee_Map = prvg.getFirm_SettleFeeRate();
          if ((settleFee_Map != null) && (settleFee_Map.size() > 0))
          {
            Map v_map = (Map)settleFee_Map.get((String)map.get("CommodityID"));
            if ((v_map != null) && (v_map.size() > 0))
            {
              SettleFeeAlgr = ((BigDecimal)v_map.get("settlefeealgr")).intValue();
              SettleFeeRate_B = ((BigDecimal)v_map.get("settlefeerate_b")).doubleValue();
              SettleFeeRate_S = ((BigDecimal)v_map.get("settlefeerate_s")).doubleValue();
            }
          }
        }
        if (feeAlgr == 1)
        {
          FeeRate_B *= 100.0D;
          FeeRate_S *= 100.0D;
        }
        if (SettleFeeAlgr == 1)
        {
          SettleFeeRate_B *= 100.0D;
          SettleFeeRate_S *= 100.0D;
        }
        sb.append("<FE_A>").append(String.valueOf(feeAlgr)).append("</FE_A>").append("<TE_T>").append(String.valueOf(FeeRate_B)).append("</TE_T>").append("<STE_T>").append(String.valueOf(FeeRate_S)).append("</STE_T>").append("<BCHFE>").append(String.valueOf(historyclosefeerate_b)).append("</BCHFE>").append("<SCHFE>").append(String.valueOf(historyclosefeerate_s)).append("</SCHFE>").append("<BCTFE>").append(String.valueOf(todayclosefeerate_b)).append("</BCTFE>").append("<SCTFE>").append(String.valueOf(todayclosefeerate_s)).append("</SCTFE>").append("<BCFFE>").append(String.valueOf(forceclosefeerate_b)).append("</BCFFE>").append("<SCFFE>").append(String.valueOf(forceclosefeerate_s)).append("</SCFFE>");
        
        sb.append("<SFE_A>")
          .append(String.valueOf(SettleFeeAlgr))
          .append("</SFE_A>");
        sb.append("<TM_SET>")
          .append(String.valueOf(SettleFeeRate_B))
          .append("</TM_SET>")
          
          .append("<STM_SET>")
          .append(String.valueOf(SettleFeeRate_S))
          .append("</STM_SET>")
          
          .append("<BRDID>")
          .append((BigDecimal)map.get("BREEDID"))
          .append("</BRDID>")
          .append("<B_T_M>")
          .append((BigDecimal)map.get("BREEDTRADEMODE"))
          .append("</B_T_M>")
          



          .append("<MAXHOLDDAYS>")
          .append(maxHoldDay == null ? "" : maxHoldDay)
          .append("</MAXHOLDDAYS>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("commodity_query's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String market_query(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<MA_I>")
          .append((String)map.get("MarketCode"))
          .append("</MA_I>")
          .append("<MA_N>")
          .append(map.get("MarketName") == null ? "" : (String)map.get("MarketName"))
          .append("</MA_N>")
          .append("<STA>")
          .append(((BigDecimal)map.get("Status")).toString())
          .append("</STA>")
          .append("<FI_I>")
          .append(map.get("FirmID") == null ? "" : (String)map.get("FirmID"))
          .append("</FI_I>")
          .append("<MAR>")
          .append(((BigDecimal)map.get("MarginFBFlag")).toString())
          .append("</MAR>")
          .append("<SH_N>")
          .append(map.get("ShortName") == null ? "" : (String)map.get("ShortName"))
          .append("</SH_N>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("market_query's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String commodity_data_query(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<CO_I>")
          .append("99" + (String)map.get("COMMODITYID"))
          .append("</CO_I>")
          .append("<CO_N>")
          .append((String)map.get("Name"))
          .append("</CO_N>")
          .append("<L_SET>")
          .append(DateUtil.getDate((Date)map.get("SettleDate")))
          .append("</L_SET>")
          .append("<PR_C>")
          .append(((BigDecimal)map.get("YesterBalancePrice")).toString())
          .append("</PR_C>")
          .append("<BID>")
          .append(((BigDecimal)map.get("BuyPrice1")).toString())
          .append("</BID>")
          .append("<BI_D>")
          .append(((BigDecimal)map.get("BuyAmount1")).toString())
          .append("</BI_D>")
          .append("<OFFER>")
          .append(((BigDecimal)map.get("SellPrice1")).toString())
          .append("</OFFER>")
          .append("<OF_D>")
          .append(((BigDecimal)map.get("SellAmount1")).toString())
          .append("</OF_D>")
          .append("<HIGH>")
          .append(((BigDecimal)map.get("HighPrice")).toString())
          .append("</HIGH>")
          .append("<LOW>")
          .append(((BigDecimal)map.get("LowPrice")).toString())
          .append("</LOW>")
          .append("<LAST>")
          .append(((BigDecimal)map.get("CurPrice")).toString())
          .append("</LAST>")
          .append("<AVG>")
          .append(((BigDecimal)map.get("Price")).toString())
          .append("</AVG>")
          .append("<CHA>")
          .append(((BigDecimal)map.get("Spread")).toString())
          .append("</CHA>")
          .append("<VO_T>")
          .append(((BigDecimal)map.get("TotalAmount")).toString())
          .append("</VO_T>")
          .append("<TT_O>")
          .append(((BigDecimal)map.get("ReserveCount")).toString())
          .append("</TT_O>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    if (debugcommodity) {
      log.debug("commodity_data_query's responseXml:userid=" + rr.getUserID() + sb.toString());
    }
    return sb.toString();
  }
  
  public static String directfirmbreed_query(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<BRDID>")
          .append((BigDecimal)map.get("BREEDID"))
          .append("</BRDID>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("directfirmbreed_query responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String holdpositionbyprice(ResponseResult rr)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size())
      .append("</TTLREC>")
      .append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<HOLDDATE>")
          .append(map.get("HOLDDATE").toString())
          .append("</HOLDDATE>")
          .append("<DEADLINE>")
          .append(map.get("DEADLINE") == null ? "" : map.get("DEADLINE").toString())
          .append("</DEADLINE>")
          .append("<REMAINDAY>")
          .append(map.get("REMAINDAY") == null ? "" : ((BigDecimal)map.get("REMAINDAY")).toString())
          .append("</REMAINDAY>")
          .append("<CO_I>")
          .append("99" + (String)map.get("commodityid"))
          .append("</CO_I>")
          .append("<CU_I>")
          .append((String)map.get("customerid"))
          .append("</CU_I>")
          .append("<BS>")
          .append(map.get("BS_Flag").toString())
          .append("</BS>")
          .append("<PRC>")
          .append(map.get("Price") == null ? "0" : ((BigDecimal)map.get("Price")).toString())
          .append("</PRC>")
          .append("<QTY>")
          .append(map.get("QTY") == null ? "0" : ((BigDecimal)map.get("QTY")).toString())
          .append("</QTY>")
          .append("<GO_Q>")
          .append(map.get("GageQty") == null ? "0" : ((BigDecimal)map.get("GageQty")).toString())
          .append("</GO_Q>")
          .append("<MAR>")
          .append(map.get("HoldMargin") == null ? "0" : ((BigDecimal)map.get("HoldMargin")).toString())
          .append("</MAR>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("holdpositionbyprice's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String verify_version(ResponseResult rr, String moduleId, String serverVersion)
  {
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<MODULE_ID>")
      .append(moduleId)
      .append("</MODULE_ID>")
      .append("<SERVERVERSION>")
      .append(serverVersion)
      .append("</SERVERVERSION>")
      .append("</RESULT>")
      .append("</REP>")
      .append("</GNNT>");
    log.debug("verify_version responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String conditionOrderQuery(ResponseResult rr, long lastestUpdateTime)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size())
      .append("</TTLREC>")
      .append("<UT>")
      .append(lastestUpdateTime)
      .append("</UT>")
      .append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<OR_N>")
          .append(((BigDecimal)map.get("A_OrderNo")).toString())
          .append("</OR_N>")
          .append("<TIME>")
          .append(map.get("SuccessTime") == null ? "" : map.get("SuccessTime").toString())
          .append("</TIME>")
          .append("<STA>")
          .append(map.get("SendStatus").toString())
          .append("</STA>")
          .append("<TYPE>")
          .append(((BigDecimal)map.get("BS_Flag")).toString())
          .append("</TYPE>")
          .append("<SE_F>")
          .append(((BigDecimal)map.get("OrderType")).toString())
          .append("</SE_F>")
          .append("<CU_I>")
          .append(map.get("CustomerID") == null ? "" : (String)map.get("CustomerID"))
          .append("</CU_I>")
          .append("<CO_I>")
          .append("99" + (String)map.get("CommodityID"))
          .append("</CO_I>")
          .append("<PRI>")
          .append(map.get("Price") == null ? "0" : ((BigDecimal)map.get("Price")).toString())
          .append("</PRI>")
          .append("<QTY>")
          .append(map.get("Quantity") == null ? "0" : ((BigDecimal)map.get("Quantity")).toString())
          .append("</QTY>")
          
          .append("<EMDATE>")
          .append(map.get("UpdateTime") == null ? "" : map.get("UpdateTime").toString())
          .append("</EMDATE>")
          .append("<CONEXPIRE>")
          .append(map.get("ValidDate") == null ? "" : map.get("ValidDate").toString())
          .append("</CONEXPIRE>")
          .append("<CONCO_I>")
          .append(map.get("ConditionCommodityID") == null ? "" : map.get("ConditionCommodityID").toString())
          .append("</CONCO_I>")
          .append("<CONPRICE>")
          .append(map.get("ConditionPrice") == null ? "" : ((BigDecimal)map.get("ConditionPrice")).toString())
          .append("</CONPRICE>")
          .append("<CONTYPE>")
          .append(((BigDecimal)map.get("ConditionType")).toString())
          .append("</CONTYPE>")
          .append("<CONOPERATOR>")
          .append(((BigDecimal)map.get("ConditionOperation")).toString())
          .append("</CONOPERATOR>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("conditionOrderQuery's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  public static String conditionOrderPageQuery(ResponseResult rr, long lastestUpdateTime)
  {
    List records = rr.getResultList();
    
    TotalDate totalDate = rr.getTotalDate();
    if (totalDate == null) {
      totalDate = new TotalDate();
    }
    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>")
      .append("<REP name=\"").append(rr.getName()).append("\">")
      .append("<RESULT>")
      .append("<RETCODE>")
      .append(rr.getRetCode())
      .append("</RETCODE>")
      .append("<MESSAGE>")
      .append(rr.getMessage())
      .append("</MESSAGE>")
      .append("<UT>")
      .append(lastestUpdateTime)
      .append("</UT>")
      .append("<TOTALROW>")
      .append("<TOTALNUM>")
      .append(String.valueOf(totalDate.getTotalNum()))
      .append("</TOTALNUM>")
      .append("<TOTALQTY>")
      .append(String.valueOf(totalDate.getTotalQty()))
      .append("</TOTALQTY>")
      .append("</TOTALROW>")
      .append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>")
          .append("<OR_N>")
          .append(((BigDecimal)map.get("A_OrderNo")).toString())
          .append("</OR_N>")
          .append("<TIME>")
          .append(map.get("SuccessTime") == null ? "" : map.get("SuccessTime").toString())
          .append("</TIME>")
          .append("<STA>")
          .append(map.get("SendStatus").toString())
          .append("</STA>")
          .append("<TYPE>")
          .append(((BigDecimal)map.get("BS_Flag")).toString())
          .append("</TYPE>")
          .append("<SE_F>")
          .append(((BigDecimal)map.get("OrderType")).toString())
          .append("</SE_F>")
          .append("<CU_I>")
          .append(map.get("CustomerID") == null ? "" : (String)map.get("CustomerID"))
          .append("</CU_I>")
          .append("<CO_I>")
          .append("99" + (String)map.get("CommodityID"))
          .append("</CO_I>")
          .append("<PRI>")
          .append(map.get("Price") == null ? "0" : ((BigDecimal)map.get("Price")).toString())
          .append("</PRI>")
          .append("<QTY>")
          .append(map.get("Quantity") == null ? "0" : ((BigDecimal)map.get("Quantity")).toString())
          .append("</QTY>")
          
          .append("<EMDATE>")
          .append(map.get("UpdateTime") == null ? "" : map.get("UpdateTime").toString())
          .append("</EMDATE>")
          .append("<CONEXPIRE>")
          .append(map.get("ValidDate") == null ? "" : map.get("ValidDate").toString())
          .append("</CONEXPIRE>")
          .append("<CONCO_I>")
          .append(map.get("ConditionCommodityID") == null ? "" : map.get("ConditionCommodityID").toString())
          .append("</CONCO_I>")
          .append("<CONPRICE>")
          .append(map.get("ConditionPrice") == null ? "" : ((BigDecimal)map.get("ConditionPrice")).toString())
          .append("</CONPRICE>")
          .append("<CONTYPE>")
          .append(((BigDecimal)map.get("ConditionType")).toString())
          .append("</CONTYPE>")
          .append("<CONOPERATOR>")
          .append(((BigDecimal)map.get("ConditionOperation")).toString())
          .append("</CONOPERATOR>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("conditionOrderPageQuery's responseXml:userid=" + rr.getUserID() + sb.toString());
    return sb.toString();
  }
  
  private static void errorException(Exception e)
  {
    StackTraceElement[] ste = e.getStackTrace();
    log.error(e.getMessage());
    for (int i = 0; i < ste.length; i++) {
      log.error(ste[i].toString());
    }
  }
}
