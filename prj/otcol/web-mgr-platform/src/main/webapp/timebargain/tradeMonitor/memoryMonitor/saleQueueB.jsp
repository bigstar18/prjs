<%@ include file="/timebargain/common/taglibs.jsp"%> 
<%@ page pageEncoding="GBK" %>
<%@ page import="gnnt.MEBS.timebargain.server.model.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.action.*"%>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>default</title>
	<link href="<c:url value="/timebargain/styles/maintest.css"/>" rel="stylesheet" type="text/css">
	<script language="javascript" src="<c:url value='/timebargain/scripts/monitorAjax3.js'/>"></script>
    <script type="text/javascript">
    
	   //设置数据源地址
		function winload() 
		{   
		
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();                   
			htFilter.Add("orderby",orderby);
			setRequestUrl("<c:url value='/timebargain/tradeMonitor/tradeMonitor.do?funcflg=memoryMonitor&queryOrderType=buyOrders'/>");
			
			//sendRequest();	
			//url /timebargain/tradeMonitor/tradeMonitor.do?funcflg=memoryMonitor&queryOrderType=buyOrders&parameter=scg1&pageIndex=2
			}
		
		function query(parameter)
		{

			clearTimeout(timer);//先停止以前的请求
						
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
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHeadB>
	    <tr>
	    	<td class="hq_bt" align="center"><div align="center">买</div></td>
	    </tr>
	  </table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="16%" class="hq_bt" align="center" abbr="customerID" style="cursor:hand">客户代码</td>
	    <td width="16%" class="hq_bt" align="center" abbr="orderType" style="cursor:hand">订立/转让</td>
	    <td width="16%" class="hq_bt" align="center" abbr="price" style="cursor:hand">委托价↓</td>
	    <td width="16%" class="hq_bt" align="center" abbr="quantity" style="cursor:hand">剩余量</td>
	    <td width="16%" class="hq_bt" align="center" abbr="orderNo" style="cursor:hand">委托号</td>
	    <td width="16%" class="hq_bt" align="center" abbr="orderTime" style="cursor:hand">入单时间</td>
	    </tr>
	  
	  </table>
	 
	  <table width="100%"  height="84%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
	  		 <tr valign="top">
	  			<td>
	  				<table width="100%"  valign="top" cellpadding="0" cellspacing="0" id=tb>  		
	  				</table>
	  			</td>
	  		</tr>  
	  		<tr>
	  		
	  		</tr>
	  </table>
	 
	  <table width="100%" height="3%" border="0" cellspacing="0" cellpadding="0" class="pagetype">
		  <tr>
		    <td align="right">
		         当前:<label id="pageIndex"></label>/<label id="totalPage"></label>页&nbsp;共<label id="totalCount"></label>条
				<label id="firstPage">第一页</label>
				<label id="prePage">上一页</label>
				<label id="nextPage" onclick="pageClick('nextPage')">下一页</label>
				<label id="lastPage">最末页</label>
				</td>
		  </tr>
	 </table>
	<label id="orderType" style="display:none">DESC,Ordertime ASC</label>
	<label id="orderName" style="display:none">price</label>
	<!-- 主键 根据主键控制当前选中行 主键使用列序号标识，如果是组合主键则使用";"分割-->
	<label id="PrimaryKey" style="display:none">5;</label>
   	</form>

	</body>
</html>
