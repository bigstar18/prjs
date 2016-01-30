<%@ page pageEncoding="GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
  <style type="text/css">
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
  </style>
	<title>成交明细</title>
		<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
		<script src="${mgrPath}/app/timebargain/monitor/js/monitorAjax.js" type="text/javascript"></script>
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	
	   //设置数据源地址
	   var commodityID2="";
		function winload() 
		{
			commodityID2=document.getElementById("commodityID2").innerHTML;
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("${basePath}/ajaxcheck/monitor/monitorTradeList.action");
			//setRequestUrl("${basePath}/timebargain/monitor/monitorTradeList.action");
			var firmID = document.getElementById("firmID2").innerHTML;
			if (firmID != "null" && firmID != "") {
				sel('0');
				frm.parameter.value = firmID;
				query();
			}
			
			if (commodityID2 != "null" && commodityID2 != "") {
				sel('1');
				document.getElementsByName("queryType")[1].checked = true;
				frm.parameter2.value = commodityID2;
			}
		}	
		
		function sel(value)
		{
			if(value==0)
			{
				//document.getElementById("queryTypeText").innerHTML="交易商代码";
				document.getElementById("firmID").className = 'xian';
				document.getElementById("commodityID").className = 'yin';
			}
			else
			{
				//document.getElementById("queryTypeText").innerHTML="商品代码";
				document.getElementById("firmID").className = 'yin';
				document.getElementById("commodityID").className = 'xian';
			}
		}
		
		function query()
		{
			//查询类型0：按交易商查询 1：按商品查询
			var queryType="0";	
			var aa = document.getElementsByName("queryType");
    		for (var i=0; i<aa.length; i++)
    		{
     			if(aa[i].checked)
    			   queryType=aa[i].value;
			}
			
			//设置queryType
			if (commodityID2 != "null" && commodityID2 != "") {
				
				queryType="1";
			}
			//变量的值 变量代表交易商或者商品
			var parameter = "";
			if (queryType == "0") {
				parameter=frm.parameter.value;
			}else if (queryType == "1") {
				parameter=frm.parameter2.value;
			}
			
			clearTimeout(timer);//先停止以前的请求
					
			if(htFilter.ContainsKey("queryType"))//如果包含queryType查询条件则重新设置否则添加
				htFilter.SetValue("queryType",queryType);
			else 
				htFilter.Add("queryType",queryType);
			
			if(parameter!="")
			{
				if(htFilter.ContainsKey("parameter"))//如果包含parameter查询条件则重新设置否则添加
					htFilter.SetValue("parameter",parameter);
				else 
					htFilter.Add("parameter",parameter);
			}
			else
			{
				htFilter.Remove("parameter");
			}	
					
			//发送请求
			sendRequest();	
		}
	
	</script>
  </head>
 <body bgcolor="#000000" onLoad="winload()">
  <form name="frm" METHOD=POST ACTION="">
  <table width="100%" height="5%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
      	<td width="54%">
      		<div align="right">
      			<span class="pagetype"><INPUT TYPE="radio" name="queryType"  value="0" onclick="sel('0')" checked="checked"/>按交易商查询 
				<INPUT TYPE="radio" name="queryType"  value="1" onclick="sel('1')"/>按商品查询 </span>
			</div>
      	</td>
        <td width="20%" class="xian" id="firmID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText">交易商代码</label>：</span>
        		<input type="text" id="parameter"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" style="width:111" onkeypress=""/>
        	</div>
		</td>
		<td width="20%" class="yin" id="commodityID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText2">商品代码</label>：</span>
        		<select id="parameter2" style="width:111">
        			<option value="">请选择</option>
        			<c:forEach items="${commoditySelect}" var="result">
						<option value="${result.COMMODITYID}">${result.COMMODITYID}</option>
					</c:forEach>
        		</select>
        	</div>
		</td>
		<td width="6%"><div align="RIGHT"><input type="button" value="查询" onclick="query()"></div></td>
  </tr>
</table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="8%" class="hq_bt" align="center" abbr="TradeTime" style="cursor:hand">成交时间</td>
	    <td width="12%" class="hq_bt" align="center" abbr="a_tradeno" style="cursor:hand">成交号↓</td>
	    <td width="8%" class="hq_bt" align="center" abbr="CustomerID" style="cursor:hand">二级代码</td>
	    <td width="8%" class="hq_bt" align="center" abbr="CommodityID" style="cursor:hand">交易商品</td>
	    <td width="8%" class="hq_bt" align="center" abbr="BS_Flag" style="cursor:hand">买/卖</td>
	    <td width="8%" class="hq_bt" align="center" abbr="OrderType" style="cursor:hand">订立/转让</td>
	    <td width="8%" class="hq_bt" align="center" abbr="Quantity" style="cursor:hand">成交量</td>
	    <td width="8%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">成交价</td>     
	    <td width="8%" class="hq_bt" align="center" abbr="firmIdOop" style="cursor:hand">成交对方</td>
	    <td width="8%" class="hq_bt" align="center" abbr="ordertime" style="cursor:hand">委托时间</td>
	    <td width="8%" class="hq_bt" align="center" abbr="A_OrderNo" style="cursor:hand">委托号</td>
	    <td width="8%" class="hq_bt" align="center" abbr="orderPrice" style="cursor:hand">委托价</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="80%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
	  		<tr valign="top">
	  			<td>
	  				<table width="100%"  valign="top" cellpadding="0" cellspacing="0"  id=tb>  		
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
	<label id="orderName" style="display:none">a_tradeno</label>
	<!-- 主键 根据主键控制当前选中行 主键使用列序号标识，如果是组合主键则使用";"分割-->
	<label id="PrimaryKey" style="display:none">2;</label>
	<label id="firmID2" style="display:none">${firmID }</label>
	<label id="commodityID2" style="display:none">${commodityID2 }</label>
   	</form>
	</body>
</html>
