<%@ page pageEncoding="GBK" %>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
  <head>
	<title>���з���</title>
	<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
	<script src="${mgrPath}/app/timebargain/monitor/js/monitorAjax.js" type="text/javascript"></script>
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	//<!--
	   //��������Դ��ַ
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
	    <td width="12%" class="hq_bt" align="center" abbr="Firmid" style="cursor:hand">�����̴����</td>
	    <td width="12%" class="hq_bt" align="center" abbr="Orderscnt" style="cursor:hand">ί��ָ��</td>
	    <td width="12%" class="hq_bt" align="center" abbr="Tradecnt" style="cursor:hand">�ɽ���</td>
	    <td width="12%" class="hq_bt" align="center" abbr="TotalTradeQuantity" style="cursor:hand">�ܳɽ���</td>
	    <td width="12%" class="hq_bt" align="center" abbr="TotalTradePrice" style="cursor:hand">�ɽ���</td>
	    <td width="12%" class="hq_bt" align="center" abbr="HoldQtyCnt" style="cursor:hand">������</td>
	    <td width="13%" class="hq_bt" align="center" abbr="O_quantity" style="cursor:hand">������</td>     
	    <td width="12%" class="hq_bt" align="center" abbr="L_quantity" style="cursor:hand">ת����</td>
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
		         ��ǰ:<label id="pageIndex"></label>/<label id="totalPage"></label>ҳ&nbsp;��<label id="totalCount"></label>��
				<label id="firstPage">��һҳ</label>
				<label id="prePage">��һҳ</label>
				<label id="nextPage">��һҳ</label>
				<label id="lastPage">��ĩҳ</label>
				</td>
		  </tr>
	 </table>
	<label id="orderType" style="display:none">DESC</label>
	<label id="orderName" style="display:none">Firmid</label>
	<!-- ���� �����������Ƶ�ǰѡ���� ����ʹ������ű�ʶ����������������ʹ��";"�ָ�-->
	<label id="PrimaryKey" style="display:none">1;</label>
   	</form>
	</body>
</html>
