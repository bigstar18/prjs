<%@ page pageEncoding="GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
  <head>
	<title>行情信息</title>
	<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
	<script src="${mgrPath}/app/timebargain/tradeMonitor/js/monitorAjax2.js" type="text/javascript"></script>
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	//<!--
	   //设置数据源地址
		function winload() 
		{
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("${basePath}/timebargain/tradeMonitor/monitorQuotationInfo.action");
		}
		    
	var oPopup = window.createPopup();
    function PopMenu(id)
    {
    	//alert(id);
        var oPopBody = oPopup.document.body;
        oPopBody.style.backgroundColor = "white";
        oPopBody.style.border = "solid black 1px";
        var TableBegin="<table style=\"border:0; width:100%; font-size: 12px;\" cellpadding=\"1\" cellspacing=\"1\">";
        var Tr1="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.tradeList('"+id+"');\">成交明细</td></tr>";
        var TrBr1="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr2="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.tradeStatistic()\">成交综合统计</td></tr>";
       	var TrBr2="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr3="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.commodityMonitor('"+id+"')\">商品订货监控</td></tr>";
        var TrBr3="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr4="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.orderMonitor('"+id+"')\">委托监控</td></tr>";
        var TrBr4="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr5="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.unTradeInfo('"+id+"')\">未成交委托队列</td></tr>";
        var TableEnd="</table>";
        oPopBody.innerHTML =TableBegin + Tr1 + TrBr1 + Tr2 + TrBr2 + Tr3 + TrBr3 + Tr4 + TrBr4 + Tr5 +TableEnd;
        //oPopBody.innerHTML =TableBegin + Tr1 + TableEnd;
        oPopup.show(event.x, event.y, 140, 137, document.body);
        return false;
    }
	// -->
	
	function tradeList(commodityID){
		window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?commodityID2=" + commodityID+"&type=tradeList";
	}
	
	function tradeStatistic(){
		window.location = "${mgrPath}/app/timebargain/tradeMonitor/monitor_tradeStatistic.jsp";
	}
	
	function commodityMonitor(commodityID){
		window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?commodityID2=" + commodityID+"&type=commodityMonitor";
	}
	
	function orderMonitor(commodityID){
		window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?commodityID2=" + commodityID+"&type=orderMonitor";
	}
	
	function unTradeInfo(commodityID){
		window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?commodityID2=" + commodityID+"&type=unTradeInfo";
	}

	</script>
  </head>
  <body bgcolor="#000000" onLoad="winload()">
  <div class="content"> 
  <form name="frm" method="post" ACTION="">
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="8%" class="hq_bt" align="center" abbr="CommodityID" style="cursor:hand">商品代码↓</td>
	    <td width="6%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">平均价</td>
	    <td width="6%" class="hq_bt" align="center" abbr="CurPrice" style="cursor:hand">最新价</td>
	    <td width="6%" class="hq_bt" align="center" abbr="relSpread" style="cursor:hand">涨跌幅</td>
	    <td width="5%" class="hq_bt" align="center" abbr="BuyPrice1" style="cursor:hand">买价</td>
	    <td width="5%" class="hq_bt" align="center" abbr="SellPrice1" style="cursor:hand">卖价</td>
	    <td width="5%" class="hq_bt" align="center" abbr="HighPrice" style="cursor:hand">最高</td>
	    <td width="5%" class="hq_bt" align="center" abbr="LowPrice" style="cursor:hand">最低</td>
	    <td width="5%" class="hq_bt" align="center" abbr="BuyOpenAmount" style="cursor:hand">买订量</td>
	    <td width="5%" class="hq_bt" align="center" abbr="SellOpenAmount" style="cursor:hand">卖订量</td>
	    <td width="5%" class="hq_bt" align="center" abbr="BuyCloseAmount" style="cursor:hand">买转量</td>
	    <td width="5%" class="hq_bt" align="center" abbr="SellCloseAmount" style="cursor:hand">卖转量</td>     
	    <td width="5%" class="hq_bt" align="center" abbr="ordercnt" style="cursor:hand">委托单</td>
	    <td width="5%" class="hq_bt" align="center" abbr="tradecnt" style="cursor:hand">成交单</td>
	    <td width="5%" class="hq_bt" align="center" abbr="TotalAmount" style="cursor:hand">成交量</td>
	    <td width="11%" class="hq_bt" align="center" abbr="TotalMoney" style="cursor:hand">成交额</td>
	    <td width="9%" class="hq_bt" align="center" abbr="ReserveCount" style="cursor:hand">订货量</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="87%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
	  		<tr valign="top">
	  			<td>
	  				<table width="100%"  valign="top" cellpadding="0" cellspacing="0" id=tb>  		
	  				</table>
	  			</td>
	  		</tr>
	  </table>
	  <table width="100%" height="3%" border="0" cellspacing="0" cellpadding="4" class="pagetype">
		  <tr>
		    <td align="right">
		         当前:<label id="pageIndex"></label>/<label id="totalPage"></label>页&nbsp;共<label id="totalCount"></label>条
				<label id="firstPage">第一页</label>
				<label id="prePage">上一页</label>
				<label id="nextPage">下一页</label>
				<label id="lastPage">最末页</label>
				</td>
		  </tr>
	 </table>
	<label id="orderType" style="display:none">DESC</label>
	<label id="orderName" style="display:none">CommodityID</label>
	<!-- 主键 根据主键控制当前选中行 主键使用列序号标识，如果是组合主键则使用";"分割-->
	<label id="PrimaryKey" style="display:none">1;</label>
   	</form>
   	</div>
	</body>
</html>
