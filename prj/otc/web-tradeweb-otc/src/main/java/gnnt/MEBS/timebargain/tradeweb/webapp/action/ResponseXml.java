package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
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
  private static final String cmdSuff = "";
  
  public static String responseXml(String name, int retCode, String message)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(name).append("\">")
      .append("<RESULT>").append("<RETCODE>").append(retCode).append(
      "</RETCODE>").append("<MESSAGE>").append(message)
      .append("</MESSAGE>").append("</RESULT>").append("</REP>")
      .append("</GNNT>");
    log.debug("responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String logon(ResponseResult rr, Privilege prvg)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    List list = rr.getResultList();
    TraderInfo info = null;
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getLongRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "<MODULE_ID>").append("2").append(
      "</MODULE_ID>").append("<TYPE>").append("</TYPE>");
    if ((list != null) && (list.size() > 0))
    {
      info = (TraderInfo)list.get(0);
      sb.append("<LAST_TIME>")
        .append(
        info.lastTime == null ? "" : info.lastTime
        .substring(0, 19)).append("</LAST_TIME>")
        .append("<LAST_IP>").append(
        info.lastIP == null ? "" : info.lastIP).append(
        "</LAST_IP>").append("<CHG_PWD>");
      if (info.forceChangePwd == 0) {
        sb.append(2);
      } else if (info.forceChangePwd == 1) {
        sb.append(1);
      }
      sb.append("</CHG_PWD>");
      sb.append("<NAME>").append(info.traderName).append("</NAME>");
      if (prvg != null) {
        sb.append("<IDENTITY>").append(prvg.getFirmType()).append(
          "</IDENTITY>");
      }
      sb.append("<RANDOM_KEY>").append(info.keyCode).append(
        "</RANDOM_KEY>");
    }
    sb.append("</RESULT>").append("</REP>").append("</GNNT>");
    log.debug("logon's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String check_user(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getLongRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "<MODULE_ID>").append("2").append(
      "</MODULE_ID>").append("</RESULT>").append("</REP>")
      .append("</GNNT>");
    log.debug("check_user's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String firm_info(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        BigDecimal in_out_Amount = 
          (BigDecimal)map.get("in_out_Amount");
        



        BigDecimal right = ((BigDecimal)map.get("usefulFund")).add(
          (BigDecimal)map.get("runtimemargin")).add(
          (BigDecimal)map.get("frozenMargin")).add(
          (BigDecimal)map.get("frozenFee")).add(
          (BigDecimal)map.get("RuntimeSettleMargin"));
        








        BigDecimal otherFrozen = ((BigDecimal)map.get("frozenfunds"))
          .subtract((BigDecimal)map.get("frozenMargin"))
          .subtract((BigDecimal)map.get("frozenFee")).subtract(
          (BigDecimal)map.get("runtimemargin"))
          .subtract((BigDecimal)map.get("tradefee")).add(
          (BigDecimal)map.get("PL")).add(
          (BigDecimal)map.get("close_pl")).add(
          (BigDecimal)map.get("in_out_Amount"));
        


        BigDecimal ot_change = ((BigDecimal)map.get("usefulFund"))
          .subtract((BigDecimal)map.get("lastbalance"))
          .subtract(((BigDecimal)map.get("ClearMargin")).add(
          (BigDecimal)map.get("ClearFL")).add(
          in_out_Amount).add(
          (BigDecimal)map.get("close_pl")).subtract(
          (BigDecimal)map.get("runtimemargin"))
          .subtract((BigDecimal)map.get("runtimefl"))
          .subtract((BigDecimal)map.get("frozenMargin"))
          .subtract((BigDecimal)map.get("frozenFee"))
          .subtract((BigDecimal)map.get("tradefee"))
          .subtract(otherFrozen));
        








        int status = 0;
        if (((String)map.get("Status")).equals("D")) {
          status = 1;
        }
        sb.append("<REC>").append("<FI>").append((String)map.get("FirmID")).append("</FI>").append("<FN>").append((String)map.get("Name")).append("</FN>").append("<IF>").append(((BigDecimal)map.get("lastbalance")).toString()).append("</IF>").append("<CM>").append(((BigDecimal)map.get("ClearMargin")).toString()).append("</CM>").append("<CF>").append(((BigDecimal)map.get("clearfl")).toString()).append("</CF>").append("<RM>").append(((BigDecimal)map.get("runtimemargin")).toString()).append("</RM>").append("<CD>").append(map.get("cleardelayfee") == null ? 0.0D : ((BigDecimal)map.get("cleardelayfee")).doubleValue()).append("</CD>").append("<RF>").append(((BigDecimal)map.get("runtimefl")).toString()).append("</RF>").append("<OR_F>").append(((BigDecimal)map.get("frozenMargin")).add((BigDecimal)map.get("frozenFee")).toString()).append("</OR_F>").append("<OR_F_M>").append(((BigDecimal)map.get("frozenMargin")).toString()).append("</OR_F_M>").append("<OR_F_F>").append(((BigDecimal)map.get("frozenFee")).toString()).append("</OR_F_F>").append("<OT_F>").append(otherFrozen.toString()).append("</OT_F>").append("<UF>").append(map.get("usefulFund") == null ? "" : ((BigDecimal)map.get("usefulFund")).toString()).append("</UF>").append("<FEE>").append(((BigDecimal)map.get("tradefee")).toString()).append("</FEE>").append("<PL>").append(((BigDecimal)map.get("close_pl")).toString()).append("</PL>").append("<EQT>").append(right.toString()).append("</EQT>").append("<IOF>").append(in_out_Amount.toString()).append("</IOF>").append("<HPF>").append((BigDecimal)map.get("PL")).append("</HPF>").append("<ZT_IOF>").append(map.get("ztIOF") == null ? Integer.valueOf(0) : ((BigDecimal)map.get("ztIOF")).toString()).append("</ZT_IOF>").append("<FUNDS_RISK>").append(map.get("RiskRate")).append("</FUNDS_RISK>").append("<OC>").append(ot_change.toString()).append("</OC>").append("<STA>").append(status).append("</STA>").append("<C_STA>").append(map.get("c_Status")).append("</C_STA>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("Firm_info's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String firm_funds_info(ResponseResult rr, Privilege prvg)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        sb
          .append("<REC>")
          
          .append("<PURE_F>")
          .append(((BigDecimal)map.get("NetFL")).toString())
          .append("</PURE_F>")
          
          .append("<TRADE_F>")
          .append(((BigDecimal)map.get("FL_Accept")).toString())
          .append("</TRADE_F>")
          
          .append("<TRADE_CF>")
          .append(
          ((BigDecimal)map.get("ClosePL_Accept"))
          .toString())
          .append("</TRADE_CF>")
          
          .append("<D_F>")
          .append(((BigDecimal)map.get("FL_Hedge")).toString())
          .append("</D_F>")
          
          .append("<R_M>")
          .append(((BigDecimal)map.get("Balance")).toString())
          .append("</R_M>")
          
          .append("<JTC>")
          .append(((BigDecimal)map.get("NetHold")).toString())
          .append("</JTC>")
          
          .append("<T_F_F>")
          .append(((BigDecimal)map.get("WarnTh")).toString())
          .append("</T_F_F>")
          
          .append("<C_F_F>")
          .append(
          ((BigDecimal)map.get("CU_F_WarnTh"))
          .toString())
          .append("</C_F_F>")
          
          .append("<T_T_F>")
          .append(((BigDecimal)map.get("FrozenTh")).toString())
          .append("</T_T_F>")
          
          .append("<M_R_F>")
          .append(((BigDecimal)map.get("MinRiskFund")).toString())
          .append("</M_R_F>")
          
          .append("<T_JTC_F>")
          .append(
          ((BigDecimal)map.get("NetHoldWarnTh"))
          .toString())
          .append("</T_JTC_F>")
          
          .append("<T_M_JTC_F>")
          .append(((BigDecimal)map.get("MaxNetHold")).toString())
          .append("</T_M_JTC_F>")
          
          .append("<M_T>").append(prvg.getMemberType()).append(
          "</M_T>")
          
          .append("<STA>").append((String)map.get("Status"))
          .append("</STA>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("firm_funds_info's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String customer_order_query(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        long BHoldQty = 0L;
        long SHoldQty = 0L;
        
        double BFloatingLoss = 0.0D;
        double SFloatingLoss = 0.0D;
        if (map.get("BHoldQty") != null) {
          BHoldQty = ((BigDecimal)map.get("BHoldQty")).longValue();
        }
        if (map.get("SHoldQty") != null) {
          SHoldQty = ((BigDecimal)map.get("SHoldQty")).longValue();
        }
        if (map.get("BFloatingLoss") != null) {
          BFloatingLoss = 
            ((BigDecimal)map.get("BFloatingLoss")).doubleValue();
        }
        if (map.get("SFloatingLoss") != null) {
          SFloatingLoss = 
            ((BigDecimal)map.get("SFloatingLoss")).doubleValue();
        }
        sb.append("<REC>").append("<CO_I>").append((String)map.get("CommodityID")).append("</CO_I>").append("<BU_A>").append(((BigDecimal)map.get("BEvenPrice")).toString()).append("</BU_A>").append("<BU_H>").append(((BigDecimal)map.get("BHoldFunds")).toString()).append("</BU_H>").append("<BU_Q>").append(((BigDecimal)map.get("BHoldQty")).toString()).append("</BU_Q>").append("<BU_F>").append(((BigDecimal)map.get("BFloatingLoss")).toString()).append("</BU_F>").append("<SE_A>").append(((BigDecimal)map.get("SEvenPrice")).toString()).append("</SE_A>").append("<SE_H>").append(((BigDecimal)map.get("SHoldFunds")).toString()).append("</SE_H>").append("<SE_Q>").append(((BigDecimal)map.get("SHoldQty")).toString()).append("</SE_Q>").append("<SE_F>").append(((BigDecimal)map.get("SFloatingLoss")).toString()).append("</SE_F>").append("<JTC>").append(BHoldQty - SHoldQty).append("</JTC>").append("<F>").append(BFloatingLoss + SFloatingLoss).append("</F>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("customer_order_query's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String other_firm_query(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        sb.append("<REC>")
        
          .append("<E_M_ID>").append((String)map.get("MemberNo"))
          .append("</E_M_ID>")
          
          .append("<E_M_N>").append((String)map.get("Name"))
          .append("</E_M_N>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("other_firm_query's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String firm_holdsum(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        sb
          .append("<REC>")
          
          .append("<CO_I>")
          .append((String)map.get("CommodityID"))
          .append("</CO_I>")
          
          .append("<M_H>")
          .append(((BigDecimal)map.get("MaxNetHold")).toString())
          .append("</M_H>")
          
          .append("<F_H>")
          .append(((BigDecimal)map.get("NetHold")).toString())
          .append("</F_H>")
          
          .append("<C_H>")
          .append(((BigDecimal)map.get("CU_NetHold")).toString())
          .append("</C_H>")
          
          .append("<H_F_H>").append(
          ((BigDecimal)map.get("HedgeNetHold"))
          .toString()).append("</H_F_H>")
          
          .append("<F_F>").append(
          ((BigDecimal)map.get("NetFL")).toString())
          .append("</F_F>")
          
          .append("<C_F>").append(
          ((BigDecimal)map.get("FL_Accept")).toString())
          .append("</C_F>")
          
          .append("<H_F>").append(
          ((BigDecimal)map.get("FL_Hedge")).toString())
          .append("</H_F>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("firm_holdsum's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String tradequery(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = records.size() - 1; i >= 0; i--)
      {
        Trade trade = (Trade)records.get(i);
        sb.append("<REC>")
        
          .append("<TR_N>").append(trade.getTradeNo().toString()).append(
          "</TR_N>");
        
        sb.append("<OR_N>").append(
          trade.getOrderNo() == null ? "0" : trade.getOrderNo()
          .toString()).append("</OR_N>");
        if (trade.getTradeTime() != null) {
          sb.append("<TI>").append(DateUtil.getDateTime(trade.getTradeTime())).append(
            "</TI>");
        }
        sb.append("<TY>").append(trade.getBS_Flag().toString()).append("</TY>").append("<SE_F>").append(trade.getOrderType().toString()).append("</SE_F>").append("<FI_I>").append(trade.getFirmID()).append("</FI_I>").append("<TR_I>").append(trade.getFirmID()).append("</TR_I>").append("<CO_I>").append(trade.getCommodityID()).append("</CO_I>").append("<O_PR>").append(trade.getOpenPrice().toString()).append("</O_PR>").append("<PR>").append(trade.getPrice().toString()).append("</PR>").append("<QTY>").append(trade.getQuantity().toString()).append("</QTY>");
        if (trade.getClose_PL() != null) {
          sb.append("<LIQPL>").append(trade.getClose_PL().toString()).append("</LIQPL>");
        }
        if (trade.getTradeFee() != null) {
          sb.append("<COMM>").append(trade.getTradeFee().toString()).append("</COMM>");
        }
        if (trade.getHoldPrice() != null) {
          sb.append("<H_P>").append(trade.getHoldPrice().toString()).append("</H_P>");
        }
        if (trade.getHoldTime() != null) {
          sb.append("<OR_T>").append(DateUtil.getDateTime(trade.getHoldTime())).append(
            "</OR_T>");
        }
        sb.append("<TR_T>").append(trade.getTradeType().toString()).append("</TR_T>");
        
        sb.append("<HL_N>").append(
          trade.getHoldNO() == null ? "0" : trade.getHoldNO()
          .toString()).append("</HL_N>");
        
        sb.append("<OTHER_ID>").append(
          trade.getHoldNO() == null ? "" : trade.getOtherFirmID()
          .toString()).append("</OTHER_ID>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("Tradequery's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String sys_time_query(ResponseResult rr, String dd, boolean showTDRP)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    try
    {
      sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append("\">").append("<RESULT>").append("<RETCODE>").append(rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>").append(rr.getMessage()).append(
        "</MESSAGE>");
      List records = rr.getResultList();
      if ((records != null) && (records.size() != 0))
      {
        Map map = (Map)records.get(0);
        sb.append("<CU_T>").append((String)map.get("CUR_TIME"))
          .append("</CU_T>").append("<CU_D>").append(
          (String)map.get("CUR_DATE")).append("</CU_D>")
          .append("<TV_U>").append((String)map.get("TV_USEC"))
          .append("</TV_U>");
        if (map.get("L_ID") != null) {
          sb.append("<L_ID>").append(map.get("L_ID")).append(
            "</L_ID>");
        }
        sb.append("<NEW_T>").append(map.get("NEW_T")).append("</NEW_T>");
        sb.append("<TD_TTL>").append(map.get("TD_TTL")).append(
          "</TD_TTL>");
        if (showTDRP)
        {
          sb.append("<TDRP>");
          ArrayList list = (ArrayList)map.get("Trades");
          if ((list != null) && (list.size() > 0)) {
            for (int i = 0; i < list.size(); i++)
            {
              Trade t = (Trade)list.get(i);
              
              log.debug("OR_N : " + t.getOrderNo() + "TDQTY:" + 
                t.getQuantity());
              sb.append("<TDS>").append(
                "<OR_N>" + t.getOrderNo() + "</OR_N>" + 
                
                "<CO_I>" + t.getCommodityID() + 
                "</CO_I>" + "<TDQTY>" + 
                t.getQuantity() + "</TDQTY>")
                .append("</TDS>");
            }
          }
          sb.append("</TDRP>");
        }
        sb.append("<S_S>").append(map.get("SysStatus") == null ? "" : map.get("SysStatus")).append("</S_S>");
      }
      if (dd != null) {
        sb.append("<DAY>").append(dd).append("</DAY>");
      }
      sb.append("<U_D>").append("服务器端暂不支持").append("</U_D>");
      
      sb.append("</RESULT>");
      sb.append("</REP>").append("</GNNT>");
      log.debug("Sys_time_query's responseXml:" + sb.toString());
    }
    catch (Exception e)
    {
      log.error("ResponseXml出错:" + e);
      errorException(e);
      e.printStackTrace();
    }
    return sb.toString();
  }
  
  public static String my_order_query(ResponseResult rr, long lastestUpdateTime)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size()).append(
      "</TTLREC>").append("<UT>").append(lastestUpdateTime)
      .append("</UT>").append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        int OC_Flag = 1;
        
        String ocStr = (String)map.get("OC_Flag");
        if (ocStr.equals("C")) {
          OC_Flag = 2;
        }
        sb.append("<REC>").append("<OR_N>").append(((BigDecimal)map.get("OrderNo")).toString()).append("</OR_N>").append("<TIME>").append(map.get("OrderTime") == null ? "" : DateUtil.getDateTime((Date)map.get("OrderTime"))).append("</TIME>").append("<STA>").append(((BigDecimal)map.get("Status")).toString()).append("</STA>").append("<TYPE>").append(map.get("BS_Flag") == null ? "0" : ((BigDecimal)map.get("BS_Flag")).toString()).append("</TYPE>").append("<SE_F>").append(OC_Flag).append("</SE_F>").append("<O_T>").append(((BigDecimal)map.get("OrderType")).toString()).append("</O_T>").append("<TR_I>").append(map.get("TraderID") == null ? "" : (String)map.get("TraderID")).append("</TR_I>").append("<FI_I>").append(map.get("FirmID") == null ? "" : (String)map.get("FirmID")).append("</FI_I>").append("<CO_I>").append((String)map.get("COMMODITYID")).append("</CO_I>").append("<PRI>").append(map.get("Price") == null ? "0" : ((BigDecimal)map.get("Price")).toString()).append("</PRI>").append("<F_MAR>").append(map.get("FrozenMargin") == null ? "0" : ((BigDecimal)map.get("FrozenMargin")).toString()).append("</F_MAR>").append("<F_FEE>").append(map.get("FrozenFee") == null ? "0" : ((BigDecimal)map.get("FrozenFee")).toString()).append("</F_FEE>").append("<QTY>").append(map.get("Quantity") == null ? "0" : ((BigDecimal)map.get("Quantity")).toString()).append("</QTY>").append("<WD_T>").append(map.get("WithdrawTime") == null ? "" : DateUtil.getDateTime((Date)map.get("WithdrawTime"))).append("</WD_T>").append("<STOP_LOSS>").append(map.get("StopLossPrice") == null ? "0" : ((BigDecimal)map.get("StopLossPrice")).toString()).append("</STOP_LOSS>").append("<STOP_PROFIT>").append(map.get("StopProfitPrice") == null ? "0" : ((BigDecimal)map.get("StopProfitPrice")).toString()).append("</STOP_PROFIT>").append("<O_F>").append((String)map.get("O_FirmID")).append("</O_F>").append("<H_NO>").append(map.get("HoldNO") == null ? "" : ((BigDecimal)map.get("HoldNO")).toString()).append("</H_NO>").append("<CO_ID>").append(map.get("CONSIGNERID") == null ? "" : map.get("CONSIGNERID").toString()).append("</CO_ID>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug(rr.getName() + "'s responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String holding_query(ResponseResult rr)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size()).append(
      "</TTLREC>").append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        
        sb.append("<REC>")
        
          .append("<CO_I>").append(
          (String)map.get("commodityid")).append(
          "</CO_I>")
          
          .append("<FL_P>").append(
          map.get("floatingloss") == null ? "0" : 
          ((BigDecimal)map.get("floatingloss"))
          .toString()).append("</FL_P>")
          
          .append("<MAR>").append(
          map.get("holdmargin") == null ? "0" : 
          ((BigDecimal)map.get("holdmargin")).toString())
          .append("</MAR>")
          
          .append("<TY>").append(
          map.get("BS_Flag") == null ? "1" : 
          ((BigDecimal)map.get("BS_Flag"))
          .toString()).append("</TY>")
          
          .append("<QTY>").append(
          map.get("HoldQty") == null ? "0" : 
          ((BigDecimal)map.get("HoldQty"))
          .toString()).append("</QTY>")
          
          .append("<O_A>").append(
          map.get("eventprice") == null ? "0" : 
          
          ((BigDecimal)map.get("OpenEvenPrice"))
          .toString()).append("</O_A>")
          
          .append("<A_H>").append(
          map.get("eventprice") == null ? "0" : 
          ((BigDecimal)map.get("eventprice"))
          .toString()).append("</A_H>")
          
          .append("<F_QTY>").append(
          map.get("frozenqty") == null ? "0" : 
          ((BigDecimal)map.get("frozenqty"))
          .toString()).append("</F_QTY>")
          
          .append("<COMM>").append("暂不支持").append("</COMM>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("holding_query's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String holding_detail_query(ResponseResult rr)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size()).append(
      "</TTLREC>").append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb
          .append("<REC>")
          
          .append("<H_ID>")
          .append(map.get("HoldNo"))
          .append("</H_ID>")
          
          .append("<T_N>")
          .append(map.get("TradeNo"))
          .append("</T_N>")
          
          .append("<CO_I>")
          .append((String)map.get("commodityid"))
          .append("</CO_I>")
          
          .append("<TY>")
          .append(
          map.get("BS_Flag") == null ? "1" : 
          ((BigDecimal)map.get("BS_Flag"))
          .toString())
          .append("</TY>")
          
          .append("<O_QTY>")
          .append(
          map.get("OpenQty") == null ? "0" : 
          ((BigDecimal)map.get("OpenQty"))
          .toString())
          .append("</O_QTY>")
          
          .append("<C_QTY>")
          .append(
          map.get("HoldQty") == null ? "0" : 
          ((BigDecimal)map.get("HoldQty"))
          .toString())
          .append("</C_QTY>")
          
          .append("<PR>")
          .append(
          map.get("OpenPrice") == null ? "0" : 
          ((BigDecimal)map.get("OpenPrice"))
          .toString())
          .append("</PR>")
          
          .append("<H_P>")
          .append(
          map.get("HoldPrice") == null ? "0" : 
          ((BigDecimal)map.get("HoldPrice"))
          .toString())
          .append("</H_P>")
          
          .append("<OR_T>")
          .append(
          map.get("HoldTime") == null ? "" : 
          DateUtil.getDateTime(
          (Date)map.get("HoldTime")))
          .append("</OR_T>")
          
          .append("<FL_P>")
          .append(
          map.get("FloatingLoss") == null ? "0" : 
          ((BigDecimal)map.get("FloatingLoss"))
          .toString()).append("</FL_P>")
          
          .append("<MAR>").append(
          map.get("HoldMargin") == null ? "0" : 
          ((BigDecimal)map.get("HoldMargin"))
          .toString()).append("</MAR>")
          
          .append("<COMM>").append("不支持").append("</COMM>")
          
          .append("<STOP_LOSS>").append(
          map.get("StopLossPrice") == null ? "0" : 
          
          ((BigDecimal)map.get("StopLossPrice"))
          .toString()).append(
          "</STOP_LOSS>")
          
          .append("<STOP_PROFIT>").append(
          map.get("StopProfitPrice") == null ? "0" : 
          
          ((BigDecimal)map.get("StopProfitPrice"))
          .toString()).append(
          "</STOP_PROFIT>")
          
          .append("<OTHER_ID>").append(
          map.get("O_FirmID") == null ? "" : 
          (String)map.get("O_FirmID")).append("</OTHER_ID>")
          
          .append("<CO_ID>").append(
          map.get("CONSIGNERID") == null ? "" : 
          (String)map.get("CONSIGNERID"))
          .append("</CO_ID>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("holding_query's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String commodity_query(ResponseResult rr, Privilege prvg)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int k = 0; k < records.size(); k++)
      {
        Map map = (Map)records.get(k);
        sb
          .append("<REC>")
          
          .append("<CO_I>")
          .append((String)map.get("CommodityID"))
          .append("</CO_I>")
          
          .append("<CO_N>")
          .append((String)map.get("Name"))
          .append("</CO_N>")
          
          .append("<STA>")
          .append(((BigDecimal)map.get("Status")).toString())
          .append("</STA>")
          
          .append("<CT_S>")
          .append(
          ((BigDecimal)map.get("ContractFactor"))
          .toString())
          .append("</CT_S>")
          
          .append("<SPREAD>")
          .append(
          ((BigDecimal)map.get("MinPriceMove"))
          .toString())
          .append("</SPREAD>")
          
          .append("<SP_U>")
          .append(
          ((BigDecimal)map.get("SpreadUpLmt"))
          .toString())
          .append("</SP_U>")
          
          .append("<SP_D>")
          .append(
          ((BigDecimal)map.get("SpreadDownLmt"))
          .toString())
          .append("</SP_D>")
          
          .append("<CON_U>")
          .append(
          map.get("Contractunit") == null ? "" : map.get(
          "Contractunit").toString())
          .append("</CON_U>")
          
          .append("<PR_C>")
          .append(
          map.get("LastPrice") == null ? "0" : 
          ((BigDecimal)map.get("LastPrice"))
          .toString())
          .append("</PR_C>")
          
          .append("<ORDER_NUM>")
          .append(((BigDecimal)map.get("DisplayNum")).toString())
          .append("</ORDER_NUM>")
          
          .append("<B_J_H>").append(
          ((BigDecimal)map.get("QuoteExchangeRate"))
          .toString()).append("</B_J_H>");
        

        int OneMinOrderQty = 0;
        
        int OneMaxOrderQty = 0;
        
        int MaxQty = 0;
        if (prvg != null)
        {
          Map mg_Map = prvg.getHoldQty();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              OneMinOrderQty = 
                ((BigDecimal)v_map.get("OneMinOrderQty")).intValue();
              OneMaxOrderQty = 
                ((BigDecimal)v_map.get("OneMaxOrderQty")).intValue();
              MaxQty = ((BigDecimal)v_map.get("MaxHOldQty"))
                .intValue();
            }
          }
        }
        sb.append("<P_MIN_H>").append(String.valueOf(OneMinOrderQty)).append("</P_MIN_H>").append("<P_M_H>").append(String.valueOf(OneMaxOrderQty)).append("</P_M_H>").append("<M_H>").append(String.valueOf(MaxQty)).append(
          "</M_H>");
        

        int MarginAlgr = 0;
        
        double TradeMargin = 0.0D;
        if (prvg != null)
        {
          Map mg_Map = prvg.getFirm_MarginRate();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              MarginAlgr = 
                ((BigDecimal)v_map.get("MarginAlgr")).intValue();
              TradeMargin = 
                ((BigDecimal)v_map.get("TradeMargin")).doubleValue();
            }
          }
        }
        sb.append("<MA_A>").append(String.valueOf(MarginAlgr)).append("</MA_A>").append("<MA_V>").append(String.valueOf(TradeMargin)).append(
          "</MA_V>");
        

        int FeeAlgr = 0;
        
        double FeeRate = 0.0D;
        




        int FeeMode = 0;
        if (prvg != null)
        {
          Map mg_Map = prvg.getFirm_FeeRate();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              FeeAlgr = 
                ((BigDecimal)v_map.get("FeeAlgr")).intValue();
              FeeRate = ((BigDecimal)v_map.get("FeeRate"))
                .doubleValue();
              FeeMode = ((BigDecimal)v_map.get("FeeMode"))
                .intValue();
            }
          }
        }
        sb.append("<FE_A>").append(String.valueOf(FeeAlgr)).append("</FE_A>").append("<FEE_V>").append(String.valueOf(FeeRate)).append("</FEE_V>").append("<FEE_T>").append(String.valueOf(FeeMode)).append(
          "</FEE_T>");
        

        int DelayFeeAlgr = 0;
        
        List StepList = null;
        if (prvg != null)
        {
          Map mg_Map = prvg.getFirm_DelayFee();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              if (v_map.get("DelayFeeAlgr") != null) {
                DelayFeeAlgr = 
                  ((BigDecimal)v_map.get("DelayFeeAlgr")).intValue();
              }
              if (v_map.get("StepList") != null) {
                StepList = (List)v_map.get("StepList");
              }
            }
          }
        }
        if ((StepList != null) && (StepList.size() > 0))
        {
          Map feeAlgr = (Map)StepList.get(0);
          DelayFeeAlgr = 
            ((BigDecimal)feeAlgr.get("DelayFeeAlgr")).intValue();
          sb.append("<YA_A>").append(String.valueOf(DelayFeeAlgr))
            .append("</YA_A>");
        }
        if ((StepList != null) && (StepList.size() > 0))
        {
          sb.append("<YARS>");
          for (int j = 0; j < StepList.size(); j++)
          {
            Map sub_map = (Map)StepList.get(j);
            
            int StepValue = ((BigDecimal)sub_map.get("StepValue"))
              .intValue();
            
            int LowValue = ((BigDecimal)sub_map.get("lowvalue"))
              .intValue();
            

            double DelayFee = ((BigDecimal)sub_map.get("DelayFee"))
              .doubleValue();
            
            sb.append("<YAS>")
            
              .append("<STEP_V>").append(String.valueOf(StepValue))
              .append("</STEP_V>")
              
              .append("<STEP_L>").append(
              String.valueOf(LowValue)).append(
              "</STEP_L>")
              
              .append("<YA_V>").append(
              String.valueOf(DelayFee)).append(
              "</YA_V>")
              
              .append("</YAS>");
          }
          sb.append("</YARS>");
        }
        int Cancel_L_Open = 1;
        
        int Cancel_StopLoss = 1;
        
        int Cancel_StopProfit = 1;
        
        int M_B_Open = 1;
        
        int M_B_Close = 1;
        
        int L_B_Open = 1;
        
        int B_StopLoss = 1;
        
        int B_StopProfit = 1;
        
        int M_S_Open = 1;
        
        int M_S_Close = 1;
        
        int L_S_Open = 1;
        
        int S_StopLoss = 1;
        
        int S_StopProfit = 1;
        if (prvg != null)
        {
          Map mg_Map = prvg.getFirmTradePrivilege();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              Cancel_L_Open = 
                ((BigDecimal)v_map.get("Cancel_L_Open")).intValue();
              Cancel_StopLoss = 
                ((BigDecimal)v_map.get("Cancel_StopLoss")).intValue();
              Cancel_StopProfit = 
                ((BigDecimal)v_map.get("Cancel_StopProfit")).intValue();
              M_B_Open = ((BigDecimal)v_map.get("M_B_Open"))
                .intValue();
              M_B_Close = ((BigDecimal)v_map.get("M_B_Close"))
                .intValue();
              L_B_Open = ((BigDecimal)v_map.get("L_B_Open"))
                .intValue();
              B_StopLoss = ((BigDecimal)v_map.get("B_StopLoss"))
                .intValue();
              B_StopProfit = 
                ((BigDecimal)v_map.get("B_StopProfit")).intValue();
              M_S_Open = ((BigDecimal)v_map.get("M_S_Open"))
                .intValue();
              M_S_Close = ((BigDecimal)v_map.get("M_S_Close"))
                .intValue();
              L_S_Open = ((BigDecimal)v_map.get("L_S_Open"))
                .intValue();
              S_StopLoss = ((BigDecimal)v_map.get("S_StopLoss"))
                .intValue();
              S_StopProfit = 
                ((BigDecimal)v_map.get("S_StopProfit")).intValue();
            }
          }
        }
        sb.append("<W_D_T_P>").append(String.valueOf(Cancel_L_Open)).append("</W_D_T_P>").append("<W_D_S_L_P>").append(String.valueOf(Cancel_StopLoss)).append("</W_D_S_L_P>").append("<W_D_S_P_P>").append(String.valueOf(Cancel_StopProfit)).append("</W_D_S_P_P>").append("<B_O_P>").append(String.valueOf(M_B_Open)).append("</B_O_P>").append("<B_L_P>").append(String.valueOf(M_B_Close)).append("</B_L_P>").append("<B_X_O_P>").append(String.valueOf(L_B_Open)).append("</B_X_O_P>").append("<B_S_L>").append(String.valueOf(B_StopLoss)).append("</B_S_L>").append("<B_S_P>").append(String.valueOf(B_StopProfit)).append("</B_S_P>").append("<S_O_P>").append(String.valueOf(M_S_Open)).append("</S_O_P>").append("<S_L_P>").append(String.valueOf(M_S_Close)).append("</S_L_P>").append("<S_X_O_P>").append(String.valueOf(L_S_Open)).append("</S_X_O_P>").append("<S_S_L>").append(String.valueOf(S_StopLoss)).append("</S_S_L>").append("<S_S_P>").append(String.valueOf(S_StopProfit)).append("</S_S_P>");
        

        double QuotePoint_B = 0.0D;
        
        double QuotePoint_S = 0.0D;
        if (prvg != null)
        {
          Map mg_Map = prvg.getQuotePoint();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              QuotePoint_B = 
                ((BigDecimal)v_map.get("QuotePoint_B")).doubleValue();
              QuotePoint_S = 
                ((BigDecimal)v_map.get("QuotePoint_S")).doubleValue();
            }
          }
        }
        sb.append("<B_P_D_D>").append(String.valueOf(QuotePoint_B)).append("</B_P_D_D>").append("<S_P_D_D>").append(String.valueOf(QuotePoint_S)).append(
          "</S_P_D_D>");
        

        double L_Open_Point = 0.0D;
        
        double StopLossPoint = 0.0D;
        
        double StopProfitPoint = 0.0D;
        
        double Min_M_OrderPoint = 0.0D;
        
        double Max_M_OrderPoint = 0.0D;
        
        double M_OrderPoint = 0.0D;
        if (prvg != null)
        {
          Map mg_Map = prvg.getOrderPoint();
          if ((mg_Map != null) && (mg_Map.size() > 0))
          {
            Map v_map = (Map)mg_Map.get(
              (String)map.get("CommodityID"));
            if (v_map != null)
            {
              L_Open_Point = 
                ((BigDecimal)v_map.get("L_Open_Point")).doubleValue();
              StopLossPoint = 
                ((BigDecimal)v_map.get("StopLossPoint")).doubleValue();
              StopProfitPoint = 
                ((BigDecimal)v_map.get("StopProfitPoint")).doubleValue();
              Min_M_OrderPoint = 
                ((BigDecimal)v_map.get("Min_M_OrderPoint")).doubleValue();
              Max_M_OrderPoint = 
                ((BigDecimal)v_map.get("Max_M_OrderPoint")).doubleValue();
              M_OrderPoint = 
                ((BigDecimal)v_map.get("M_OrderPoint")).doubleValue();
            }
          }
        }
        sb.append("<X_O_B_D_D>").append(String.valueOf(L_Open_Point)).append("</X_O_B_D_D>").append("<X_O_S_D_D>").append(String.valueOf(L_Open_Point)).append("</X_O_S_D_D>").append("<STOP_L_P>").append(String.valueOf(StopLossPoint)).append("</STOP_L_P>").append("<STOP_P_P>").append(String.valueOf(StopProfitPoint)).append("</STOP_P_P>").append("<U_O_D_D_MIN>").append(String.valueOf(Min_M_OrderPoint)).append("</U_O_D_D_MIN>").append("<U_O_D_D_MAX>").append(String.valueOf(Max_M_OrderPoint)).append("</U_O_D_D_MAX>").append("<U_O_D_D_DF>").append(String.valueOf(M_OrderPoint)).append("</U_O_D_D_DF>").append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("commodity_query's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String commodity_data_query(ResponseResult rr)
  {
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>").append(
      "</RESULT>");
    List records = rr.getResultList();
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        double curPrice = ((BigDecimal)map.get("CurPrice"))
          .doubleValue();
        double quotePoint_B_RMB = 
          ((BigDecimal)map.get("QuotePoint_B_RMB")).doubleValue();
        double quotePoint_S_RMB = 
          ((BigDecimal)map.get("QuotePoint_S_RMB")).doubleValue();
        quotePoint_B_RMB += curPrice;
        quotePoint_S_RMB = curPrice - quotePoint_S_RMB;
        int scale = Arith.getScale(
          ((BigDecimal)map.get("MinPriceMove")).doubleValue());
        
        sb.append("<REC>").append("<C>").append(
          (String)map.get("COMMODITYID")).append(
          "</C>")
          
          .append("<H>").append(
          ((BigDecimal)map.get("HighPrice")).toString()).append(
          "</H>")
          
          .append("<L>").append(
          ((BigDecimal)map.get("LowPrice")).toString()).append(
          "</L>")
          
          .append("<LT>").append(Arith.format(curPrice, scale)).append(
          "</LT>")
          
          .append("<B>").append(Arith.format(quotePoint_B_RMB, scale))
          .append("</B>")
          
          .append("<S>").append(
          Arith.format(quotePoint_S_RMB, scale)).append(
          "</S>")
          






          .append("<T>").append(
          map.get("CreateTime") == null ? "" : 
          DateUtil.getDateTime(
          (Date)map.get("CreateTime"))).append(
          "</T>")
          
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("commodity_data_query's responseXml:" + sb.toString());
    return sb.toString();
  }
  
  public static String holdpositionbyprice(ResponseResult rr)
  {
    List records = rr.getResultList();
    StringBuffer sb = new StringBuffer(
      "<?xml version=\"1.0\" encoding=\"GBK\"?>");
    sb.append("<GNNT>").append("<REP name=\"").append(rr.getName()).append(
      "\">").append("<RESULT>").append("<RETCODE>").append(
      rr.getRetCode()).append("</RETCODE>").append("<MESSAGE>")
      .append(rr.getMessage()).append("</MESSAGE>")
      .append("<TTLREC>")
      .append(records == null ? 0 : records.size()).append(
      "</TTLREC>").append("</RESULT>");
    if ((records != null) && (records.size() != 0))
    {
      sb.append("<RESULTLIST>");
      for (int i = 0; i < records.size(); i++)
      {
        Map map = (Map)records.get(i);
        sb.append("<REC>").append("<CO_I>").append(
          (String)map.get("commodityid")).append(
          "</CO_I>").append("<CU_I>").append(
          (String)map.get("customerid")).append("</CU_I>")
          .append("<BS>").append(map.get("BS_Flag").toString())
          .append("</BS>").append("<PRC>").append(
          map.get("Price") == null ? "0" : 
          ((BigDecimal)map.get("Price"))
          .toString()).append("</PRC>")
          .append("<QTY>").append(
          map.get("QTY") == null ? "0" : 
          ((BigDecimal)map.get("QTY"))
          .toString()).append("</QTY>")
          .append("<GO_Q>").append(
          map.get("GageQty") == null ? "0" : 
          ((BigDecimal)map.get("GageQty"))
          .toString()).append("</GO_Q>")
          .append("<MAR>").append(
          map.get("HoldMargin") == null ? "0" : 
          ((BigDecimal)map.get("HoldMargin"))
          .toString()).append("</MAR>")
          .append("</REC>");
      }
      sb.append("</RESULTLIST>");
    }
    sb.append("</REP>").append("</GNNT>");
    log.debug("holdpositionbyprice's responseXml:" + sb.toString());
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
