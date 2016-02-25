<meta http-equiv="content-type" content="text/html;charset=gbk">
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ include file="marketName.jsp" %>

<%
	String title = request.getParameter("title");
	String reportName = null;
	String sign = request.getParameter("sign");

 SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMdd HHmm");
	        String datetime = tempDate.format(new java.util.Date());	 
	if(sign != null && !"".equals(sign)){
		reportName = sign;
	}else{
	reportName = "[***]";//给定特殊str避免异常
	}
	StringBuffer sb=new StringBuffer();
	for(int i=0;i<title.length();i++){
		char c=title.charAt(i);
		if(c>=0 && c<=255){
			sb.append(c);
		}
		byte []b;
		try{
			b=Character.toString(c).getBytes("utf-8");
		}catch(Exception e){
			b=new byte[0];
		}
		for(int j=0;j<b.length;j++){
			int k=b[j];
			if(k<0){
				k+=256;
			}
			sb.append("%"+Integer.toHexString(k).toUpperCase());
		}
	}
	title=sb.toString();
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=GBK");
	response.setHeader("Content-disposition","attachment; filename="+title+datetime +".xls");
%>

<table align="center">
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
							<%//成交记录表
							if("bargainResult".equals(reportName.trim())){
							%>
							<jsp:include page="bargainResult.jsp" />
							<%//转让盈亏明细表
							}else if("transferProfitAndLoss".equals(reportName.trim())){
							%>
							<jsp:include page="transferProfitAndLoss.jsp" />
							<%//订货汇总表
							}else if("indentCollect".equals(reportName.trim())){
							%>
							<jsp:include page="indentCollect.jsp" />
							<%//成交量统计表
							}else if("tradeMonth".equals(reportName.trim())){
							%>
							<jsp:include page="tradeMonth.jsp" />
							<%//交易商入金出金记录表
							}else if("cushInAndOut".equals(reportName.trim())){
							%>
							<jsp:include page="cushInAndOut.jsp" />
							<%//持有订货汇总表
							}else if("ownGoodsCollect".equals(reportName.trim())){
							%>
							<jsp:include page="ownGoodsCollect.jsp" />
							<%//订货明细表
							}else if("indentDetail".equals(reportName.trim())){
							%>
							<jsp:include page="indentDetail.jsp" />
							<%//转让和交收盈亏统计表
							}else if("tAPLStatistic".equals(reportName.trim())){
							%>
							<jsp:include page="tAPLStatistic.jsp" />
							<%//手续费统计表
							}else if("serviceFee".equals(reportName.trim())){
							%>
							<jsp:include page="serviceFee.jsp" />
							<%//订货统计表
							}else if("indentStatistic".equals(reportName.trim())){
							%>
							<jsp:include page="indentStatistic.jsp" />
							<%//分商品成交量统计表
							}else if("comTrade".equals(reportName.trim())){
							%>
							<jsp:include page="comTrade.jsp" />
							<%//当日分商品成交量统计表
							}else if("comTradeday".equals(reportName.trim())){
							%>
							<jsp:include page="comTradeday.jsp" />
							<%//分交易商成交量统计表
							}else if("comTradeDayRecord".equals(reportName.trim())){
							%>
							<jsp:include page="comTradeDayRecord.jsp" />
							<%//每日收市行情表
							}else if("dayHQ".equals(reportName.trim())){
							%>
							<jsp:include page="dayHQ.jsp" />
							<%//当日分商品成交记录表
							}else if("commodityTradeDayRecord".equals(reportName.trim())){
							%>
							<jsp:include page="commodityTradeDayRecord.jsp" />
							<%//当日成交记录表
							}else if("tradeDayResult".equals(reportName.trim())){
							%>
							<jsp:include page="tradeDayResult.jsp" />
							<%//交收记录表
							}else if("tradeResult".equals(reportName.trim())){
							%>
							<jsp:include page="tradeResult.jsp" />
							<%//抵顶记录表
							}else if("diDingResult".equals(reportName.trim())){
							%>
							<jsp:include page="diDingResult.jsp" />
							<%//行情统计表
							}else if("HQStatistic".equals(reportName.trim())){
							%>
							<jsp:include page="HQStatistic.jsp" />
							<%//协议转让记录表
							}else if("agreePCRecord".equals(reportName.trim())){
							%>
							<jsp:include page="agreePCRecord.jsp" />
							<%//代为转让记录表
							}else if("repTransRecord".equals(reportName.trim())){
							%>
							<jsp:include page="repTransRecord.jsp" />
							<%//资金不足交易商情况表
							}else if("tradeUserNotEnoughMoney".equals(reportName.trim())){
							%>
							<jsp:include page="tradeUserNotEnoughMoney.jsp" />
							<%//资金结算表
							}else if("zijinjiesuan".equals(reportName.trim())){
							%>
							<jsp:include page="zijinjiesuan.jsp" />
							<%//资金日报表
							}else if("delayFinance".equals(reportName.trim())){
							%>
							<jsp:include page="delayFinance.jsp" />
							<%//交收汇总表
							}else if("bargainOnCollect".equals(reportName.trim())){
							%>
							<jsp:include page="bargainOnCollect.jsp" />
							<%//浮动亏损统计表
							}else if("superAddBail".equals(reportName.trim())){
							%>
							<jsp:include page="superAddBail.jsp" />
							<%//资金月报表
							}else if("firmSettle".equals(reportName.trim())){
							%>
							<jsp:include page="firmSettle.jsp" />
							<%//委托情况表
							}else if("orders".equals(reportName.trim())){
							%>
							<jsp:include page="orders.jsp" />
							<%//综合日报表
							}else if("todayTogether".equals(reportName.trim())){
							%>
							<jsp:include page="todayTogether.jsp" />
							<%//资金月报表
							}else if("monthFinance".equals(reportName.trim())){
							%>
							<jsp:include page="monthFinance.jsp" />
							<%// 加盟商资金报表
							}else if("brokerFundflow".equals(reportName.trim())){
							%>
							<jsp:include page="brokerFundflow.jsp" />
							<%// 交易商分商品盈亏表
							}else if("firmCommodityTradePL".equals(reportName.trim())){
							%>
							<jsp:include page="firmCommodityTradePL.jsp" />
							<%// 交易商分商品成交表
							}else if("firmCommodityTrade".equals(reportName.trim())){
							%>
							<jsp:include page="firmCommodityTrade.jsp" />
							<%// 主力合约汇总表
							}else if("contractCollect".equals(reportName.trim())){
							%>
							<jsp:include page="contractCollect.jsp" />
							<%//分加盟商综合统计表
							}else if("brokerallReport".equals(reportName.trim())){
							%>
							<jsp:include page="brokerallReport.jsp" />
							<%//分加盟商订货统计表
							}else if("holdBybroker".equals(reportName.trim())){ 
							%>
							<jsp:include page="holdBybroker.jsp" />
							<%//分加盟商成交统计表
							}else if("tradeBybroker".equals(reportName.trim())){ 
							%>
							<jsp:include page="tradeBybroker.jsp" />
							<%
							}// 交易商成交情况表
						    else if("brokerFirmTrade".equals(reportName.trim())){
							%>
							<jsp:include page="brokerFirmTrade.jsp" />
							<%
						    }else{
							%>
							<center>没有可查询数据...</center>
							<%
							}
							%>
					  </td>
					 </tr>
					 <tr><td></td></tr>
				</table>
				</div>
			</td>	
		</tr>
	</table>