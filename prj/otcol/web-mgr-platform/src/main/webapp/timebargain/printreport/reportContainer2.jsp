<%@ page contentType="text/html;charset=GBK" %>

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ include file="marketName.jsp" %>
<html>
	<%
	String title = request.getParameter("title");
	
	String reportName = null;
	String sign = request.getParameter("sign");
	if(sign != null && !"".equals(sign)){
		reportName = sign;
	}else{
	%>
	<script type="text/javascript">
		alert("没有指定报表的查询内容！");
	</script>
	<%
	reportName = "[***]";//给定特殊str避免异常
	}
	%>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title><%=marketName%> - <%=title%></title>
</head>
<body>
	<table align="center" width="600px" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="打印">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="600px">
					<tr>
						<td>
					  </td>
					 </tr>
				</table>
			</td>
			</tr>
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
							<%//分加盟商成交统计表
							}else if("tradeBybroker".equals(reportName.trim())){
							%>
							<jsp:include page="tradeBybroker.jsp" />
							<%//分加盟商订货统计表
							}else if("holdBybroker".equals(reportName.trim())){ 
							%>
							<jsp:include page="holdBybroker.jsp" />
							<%//分加盟商综合统计表
							}else if("brokerallReport".equals(reportName.trim())){ 
							%>
							<jsp:include page="brokerallReport.jsp" />
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
		<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="打印"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function replaceHtml(replacedStr,repStr,endStr){   
    var replacedStrF = "";   
    var replacedStrB = "";   
    var repStrIndex = replacedStr.indexOf(repStr);   
    while(repStrIndex != -1){   
        replacedStrF = replacedStr.substring(0,repStrIndex);   
        replacedStrB = replacedStr.substring(repStrIndex,replacedStr.length);   
        replacedStrB = replacedStrB.substring(replacedStrB.indexOf(endStr)+1,replacedStrB.length);   
        replacedStr = replacedStrF + replacedStrB;   
        repStrIndex = replacedStr.indexOf(repStr);   
    }   
    return replacedStr;   
}   
//elTalbeOut 这个为导出内容的外层表格，主要是设置border之类的样式，elDiv则是整个导出的html部分   
function htmlToExcel(elTableOut,elDiv){   
    try{   
        //设置导出前的数据，为导出后返回格式而设置   
        var elDivStrBak = elDiv.innerHTML;   
        //设置table的border=1，这样到excel中就有表格线 ps:感谢双面提醒   
        elTableOut.border=1;   
        //过滤elDiv内容   
        var elDivStr = elDiv.innerHTML;   
        elDivStr = replaceHtml(elDivStr,"<A",">");   
        elDivStr = replaceHtml(elDivStr,"</A",">");   
        elDiv.innerHTML=elDivStr;      
           
        var oRangeRef = document.body.createTextRange();   
        oRangeRef.moveToElementText( elDiv );   
        oRangeRef.execCommand("Copy");   
           
        //返回格式变换以前的内容   
        elDiv.innerHTML = elDivStrBak;   
        //内容数据可能很大，所以赋空   
        elDivStrBak = "";   
        elDivStr = "";   
           
        var oXL = new ActiveXObject("Excel.Application")   
        var oWB = oXL.Workbooks.Add ;   
        var oSheet = oWB.ActiveSheet ;   
        oSheet.Paste();   
        oSheet.Cells.NumberFormatLocal = "@";   
        oSheet.Columns("D:D").Select   
        oXL.Selection.ColumnWidth = 20  
        oXL.Visible = true;        
        oSheet = null;   
        oWB = null;   
        appExcel = null;   
    }catch(e){   
        alert(e.description)   
    }   
}
function method1(tableid) {//整个表格拷贝到EXCEL中 
    var curTbl = document.getElementById(tableid); 
    var oXL = new ActiveXObject("Excel.Application"); 
    //创建AX对象excel 
    var oWB = oXL.Workbooks.Add(); 
    //获取workbook对象 
        var oSheet = oWB.ActiveSheet; 
    //激活当前sheet 
    var sel = document.body.createTextRange(); 
    sel.moveToElementText(curTbl); 
    //把表格中的内容移到TextRange中 
    sel.select(); 
    //全选TextRange中内容 
    sel.execCommand("Copy"); 
    //复制TextRange中内容  
    oSheet.Paste(); 
    //粘贴到活动的EXCEL中       
    oXL.Visible = true; 
    //设置excel可见属性 
} 
</SCRIPT>