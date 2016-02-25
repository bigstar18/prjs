<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%
	
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		
		request.setAttribute("commoditySelect", lookupMgr
				.getSelectLabelValueByTableDistinctCommodityID("t_orders", "commodityID",
						"commodityID"," order by commodityID "));
	String firmID = request.getParameter("firmID");
	String commodityID2 = request.getParameter("commodityID2");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
	<title>default</title>
	<link href="<c:url value="/timebargain/styles/maintest.css"/>" rel="stylesheet" type="text/css">
	<script language="javascript" src="<c:url value='/timebargain/scripts/monitorAjax.js'/>"></script>
    <script type="text/javascript">
	<!--
	   //设置数据源地址
	   var commodityID2 = '<%=commodityID2%>';
		function winload() 
		{
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("<c:url value='/timebargain/tradeMonitor/tradeMonitor.do?funcflg=listOrderMonitor'/>");
			var firmID = '<%=firmID%>';
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
			
			//状态
			var status=frm.status.value;
			
			clearTimeout(timer);//先停止以前的请求
					
			if(htFilter.ContainsKey("queryType"))//如果包含queryType查询条件则重新设置否则添加
				htFilter.SetValue("queryType",queryType);
			else 
				htFilter.Add("queryType",queryType);
			
			if(parameter!="")
			{
				if(htFilter.ContainsKey("pageIndex"))//
					htFilter.SetValue("pageIndex",1);
				else 
					htFilter.Add("pageIndex",1);
				if(htFilter.ContainsKey("parameter"))//如果包含parameter查询条件则重新设置否则添加
					htFilter.SetValue("parameter",parameter);
				else 
					htFilter.Add("parameter",parameter);
			}
			else
			{
				htFilter.Remove("parameter");
			}	
			
			if(status!="")
			{
				if(htFilter.ContainsKey("status"))//如果包含status查询条件则重新设置否则添加
					htFilter.SetValue("status",status);
				else 
					htFilter.Add("status",status);
			}
			else
			{
				htFilter.Remove("status");
			}	
			
					
			//发送请求
			sendRequest();	
		}
	// -->
	</script>
  </head>
  <body bgcolor="#000000" onLoad="winload()">
  <form name="frm" METHOD=POST ACTION="">
  <table width="100%" height="5%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
      	<td width="54%" >
      		<div align="right">
      			<span class="pagetype"><INPUT TYPE="radio" name="queryType"  value="0" onclick="sel('0')" checked="checked"/>按交易商查询 
				<INPUT TYPE="radio" name="queryType"  value="1" onclick="sel('1')"/>按商品查询 </span>
			</div>
      	</td>
        <td width="20%" class="xian" id="firmID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText">交易商代码</label>：</span>
        		<input type="text" id="parameter"  maxlength="16" style="width:111"onkeypress="onlyNumberAndCharInput()" />
        	</div>
		</td>
		<td width="20%" class="yin" id="commodityID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText2">商品代码</label>：</span>
        		<select id="parameter2" style="width:111">
        			<%
        				String commodityID = "";
						List list = (List)request.getAttribute("commoditySelect");
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								LabelValue lv = (LabelValue)list.get(i);
								if (lv != null) {
									commodityID = lv.getValue();
					%>
					<option value="<%=commodityID%>"><%=commodityID%></option>
					<%
								}
							}
							
						}
        			%>
        		</select>
        	</div>
		</td>
		<td width="20%"><div align="RIGHT"><span class="pagetype">委托单状态：</span>
			<select id="status" name="_a.Status[=]" style="width:111">
				                            <option value="">全部状态</option>
					                        <option value="1">已委托</option>
					                        <option value="2">部分成交</option>
					                        <option value="3">全部成交</option>
					                        <option value="5">全部撤单</option>
					                        <option value="6">部分成交后撤单</option>
			    </select>	
			</div>
		</td>
		<td width="6%"><div align="RIGHT"><input type="button" value="查询" onclick="query()"></div></td>
  </tr>
</table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="11%" class="hq_bt" align="center" abbr="CommodityID" style="cursor:hand">商品代码</td>
	    <td width="10%" class="hq_bt" align="center" abbr="FirmID" style="cursor:hand">交易商代码</td>
	    <td width="9%" class="hq_bt" align="center" abbr="BS_Flag" style="cursor:hand">买/卖</td>
	    <td width="10%" class="hq_bt" align="center" abbr="OrderType" style="cursor:hand">订立/转让</td>
	    <td width="10%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">委托价</td>
	    <td width="9%" class="hq_bt" align="center" abbr="Quantity" style="cursor:hand">委托量</td>
	    <td width="8%" class="hq_bt" align="center" abbr="A_OrderNo" style="cursor:hand">委托号↓</td>
	    <td width="11%" class="hq_bt" align="center" abbr="OrderTime" style="cursor:hand">入单时间</td>     
	    <td width="11%" class="hq_bt" align="center" abbr="WithdrawTime" style="cursor:hand">撤单时间</td>
	    <td width="8%" class="hq_bt" align="center" abbr="Status" style="cursor:hand">委托单状态</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="81%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
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
	<label id="orderName" style="display:none">A_OrderNo</label>
	<!-- 主键 根据主键控制当前选中行 主键使用列序号标识，如果是组合主键则使用";"分割-->
	<label id="PrimaryKey" style="display:none">7;</label>
   	</form>
	</body>
</html>
