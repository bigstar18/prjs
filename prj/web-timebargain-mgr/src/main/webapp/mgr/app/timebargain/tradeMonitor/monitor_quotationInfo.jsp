<%@ page pageEncoding="GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
  <head>
	<title>������Ϣ</title>
	<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
	<script src="${mgrPath}/app/timebargain/tradeMonitor/js/monitorAjax2.js" type="text/javascript"></script>
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	//<!--
	   //��������Դ��ַ
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
        var Tr1="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.tradeList('"+id+"');\">�ɽ���ϸ</td></tr>";
        var TrBr1="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr2="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.tradeStatistic()\">�ɽ��ۺ�ͳ��</td></tr>";
       	var TrBr2="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr3="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.commodityMonitor('"+id+"')\">��Ʒ�������</td></tr>";
        var TrBr3="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr4="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.orderMonitor('"+id+"')\">ί�м��</td></tr>";
        var TrBr4="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr5="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.unTradeInfo('"+id+"')\">δ�ɽ�ί�ж���</td></tr>";
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
	    <td width="8%" class="hq_bt" align="center" abbr="CommodityID" style="cursor:hand">��Ʒ�����</td>
	    <td width="6%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">ƽ����</td>
	    <td width="6%" class="hq_bt" align="center" abbr="CurPrice" style="cursor:hand">���¼�</td>
	    <td width="6%" class="hq_bt" align="center" abbr="relSpread" style="cursor:hand">�ǵ���</td>
	    <td width="5%" class="hq_bt" align="center" abbr="BuyPrice1" style="cursor:hand">���</td>
	    <td width="5%" class="hq_bt" align="center" abbr="SellPrice1" style="cursor:hand">����</td>
	    <td width="5%" class="hq_bt" align="center" abbr="HighPrice" style="cursor:hand">���</td>
	    <td width="5%" class="hq_bt" align="center" abbr="LowPrice" style="cursor:hand">���</td>
	    <td width="5%" class="hq_bt" align="center" abbr="BuyOpenAmount" style="cursor:hand">����</td>
	    <td width="5%" class="hq_bt" align="center" abbr="SellOpenAmount" style="cursor:hand">������</td>
	    <td width="5%" class="hq_bt" align="center" abbr="BuyCloseAmount" style="cursor:hand">��ת��</td>
	    <td width="5%" class="hq_bt" align="center" abbr="SellCloseAmount" style="cursor:hand">��ת��</td>     
	    <td width="5%" class="hq_bt" align="center" abbr="ordercnt" style="cursor:hand">ί�е�</td>
	    <td width="5%" class="hq_bt" align="center" abbr="tradecnt" style="cursor:hand">�ɽ���</td>
	    <td width="5%" class="hq_bt" align="center" abbr="TotalAmount" style="cursor:hand">�ɽ���</td>
	    <td width="11%" class="hq_bt" align="center" abbr="TotalMoney" style="cursor:hand">�ɽ���</td>
	    <td width="9%" class="hq_bt" align="center" abbr="ReserveCount" style="cursor:hand">������</td>
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
		         ��ǰ:<label id="pageIndex"></label>/<label id="totalPage"></label>ҳ&nbsp;��<label id="totalCount"></label>��
				<label id="firstPage">��һҳ</label>
				<label id="prePage">��һҳ</label>
				<label id="nextPage">��һҳ</label>
				<label id="lastPage">��ĩҳ</label>
				</td>
		  </tr>
	 </table>
	<label id="orderType" style="display:none">DESC</label>
	<label id="orderName" style="display:none">CommodityID</label>
	<!-- ���� �����������Ƶ�ǰѡ���� ����ʹ������ű�ʶ����������������ʹ��";"�ָ�-->
	<label id="PrimaryKey" style="display:none">1;</label>
   	</form>
   	</div>
	</body>
</html>
