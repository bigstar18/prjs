<%@ page pageEncoding="GBK" %>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
  <head>
	<title>排行分析</title>
	<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
	<script src="${mgrPath}/app/timebargain/monitor/js/monitorAjax.js" type="text/javascript"></script>
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	//<!--
	   //设置数据源地址
		function winload() 
		{
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("${basePath}/ajaxcheck/monitor/monitorAnalyseInfo.action");
		}
		
	// -->
	</script>
  </head>
  <body bgcolor="#000000" onLoad="winload()">
  <form name="frm" METHOD=POST ACTION="">
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="12%" class="hq_bt" align="center" abbr="Firmid" style="cursor:hand">交易商代码↓</td>
	    <td width="12%" class="hq_bt" align="center" abbr="Orderscnt" style="cursor:hand">委托指令</td>
	    <td width="12%" class="hq_bt" align="center" abbr="Tradecnt" style="cursor:hand">成交单</td>
	    <td width="12%" class="hq_bt" align="center" abbr="TotalTradeQuantity" style="cursor:hand">总成交量</td>
	    <td width="12%" class="hq_bt" align="center" abbr="TotalTradePrice" style="cursor:hand">成交额</td>
	    <td width="12%" class="hq_bt" align="center" abbr="HoldQtyCnt" style="cursor:hand">订货量</td>
	    <td width="13%" class="hq_bt" align="center" abbr="O_quantity" style="cursor:hand">订立量</td>     
	    <td width="12%" class="hq_bt" align="center" abbr="L_quantity" style="cursor:hand">转让量</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="86%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
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
	<label id="orderName" style="display:none">Firmid</label>
	<!-- 主键 根据主键控制当前选中行 主键使用列序号标识，如果是组合主键则使用";"分割-->
	<label id="PrimaryKey" style="display:none">1;</label>
   	</form>
	</body>
</html>
