<%@ page pageEncoding="GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
  <head>
	<title>�ʽ�Ԥ��</title>
	<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
	<script src="${mgrPath}/app/timebargain/tradeMonitor/js/monitorAjax2.js" type="text/javascript"></script>
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	   //��������Դ��ַ
		function winload() 
		{
			
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("${basePath}/timebargain/tradeMonitor/monitorFundsAnalysis.action");
		}
		
		function query()
		{
			//������ֵ ��Ʒ
			var parameter=frm.parameter.value;
			
			clearTimeout(timer);//��ֹͣ��ǰ������
						
			if(parameter!="")
			{
				if(htFilter.ContainsKey("pageIndex"))//
					htFilter.SetValue("pageIndex",1);
				else 
					htFilter.Add("pageIndex",1);
				if(htFilter.ContainsKey("parameter"))//�������parameter��ѯ�������������÷������
					htFilter.SetValue("parameter",parameter);
				else 
					htFilter.Add("parameter",parameter);
			}
			else
			{
				htFilter.Remove("parameter");
			}	
					
			//��������
			sendRequest();	
		}
		
		
	var oPopup = window.createPopup();
    function PopMenu(id)
    {
    	//alert(id);
        var oPopBody = oPopup.document.body;
        oPopBody.style.backgroundColor = "white";
        oPopBody.style.border = "solid black 1px";
        var TableBegin="<table style=\"border:0; width:100%; font-size: 12px;\" cellpadding=\"1\" cellspacing=\"1\">";
        var Tr1="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.firmMonitor('"+id+"');\">�����̶������</td></tr>";
        var TrBr1="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr2="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.tradeList('"+id+"')\">�ɽ���ϸ</td></tr>";
       	var TrBr2="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr3="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.orderMonitor('"+id+"')\">ί�м��</td></tr>";
        var TrBr3="<tr><td style=\"background-color: orange; height: 1px;\"></td></tr>";
        var Tr4="<tr><td valign=\"middle\" style=\"height:25px;cursor:hand;\" onmousemove=\"this.bgColor='highlight';this.style.color='highlighttext'\" onmouseout=\"this.bgColor='';this.style.color=''\" onclick=\"parent.firmInfo('"+id+"')\">��������Ϣ</td></tr>";
        var TableEnd="</table>";
        oPopBody.innerHTML =TableBegin + Tr1 + TrBr1 + Tr2 + TrBr2 + Tr3 + TrBr3 + Tr4 + TableEnd;
        //oPopBody.innerHTML =TableBegin + Tr1 + TableEnd;
        oPopup.show(event.x, event.y, 100, 107, document.body);
        return false;
    }
    function firmMonitor(firmID){
    	window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?firmID=" + firmID+"&type=firmMonitor";
    }
    
    function tradeList(firmID){
    	window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?firmID=" + firmID+"&type=tradeList";
    }
    
    function orderMonitor(firmID){
    	window.location = "${basePath}/timebargain/tradeMonitor/monitorGetCommodityList.action?firmID=" + firmID+"&type=orderMonitor";
    }
    
    function firmInfo(firmID){
    	var url="${basePath}/timebargain/tradeMonitor/monitorFundsAnalysisInfo.action?firmID=" + firmID;
    	showDialog(url, "", 550, 350);
    	//pTop("<c:url value="/timebargain/tradeMonitor/tradeMonitor.do?funcflg=fundsAnalysisInfo&firmID="/>" + firmID, 550, 350);
    }
	
	</script>
  </head>
  <body bgcolor="#000000" onLoad="winload()" >
  <form name="frm" METHOD=POST ACTION="">
  <table width="100%" height="5%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="84%">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText">�����̴���</label>��</span>
        		<input type="text" id="parameter"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" style="width:111" onkeypress="onlyNumberAndCharInput()"/>
        	</div>
		</td>
		<td width="6%"><div align="RIGHT"><input type="button" value="��ѯ" onclick="query()"></div></td>
  </tr>
</table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="12%" class="hq_bt" align="center" abbr="FirmID" style="cursor:hand">�����̴���</td>
	    <td width="14%" class="hq_bt" align="center" abbr="FundsSafeRate" style="cursor:hand">�ʽ�ȫ�ʡ�</td>
	    <td width="8%" class="hq_bt" align="center" abbr="Status" style="cursor:hand">״̬</td>
	    <td width="10%" class="hq_bt" align="center" abbr="HoldQty" style="cursor:hand">������</td>
	    <td width="14%" class="hq_bt" align="center" abbr="Runtimemargin" style="cursor:hand">��ʼ��֤��</td>
	    <td width="14%" class="hq_bt" align="center" abbr="Runtimefl" style="cursor:hand">��������</td>
	    <td width="14%" class="hq_bt" align="center" abbr="UsableFunds" style="cursor:hand">�����ʽ�</td>    
	    <td width="14%" class="hq_bt" align="center" abbr="UNQuantity" style="cursor:hand">δ�ɽ�����</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="81%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
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
	<label id="orderType" style="display:none">ASC</label>
	<label id="orderName" style="display:none">FundsSafeRate</label>
	<!-- ���� �����������Ƶ�ǰѡ���� ����ʹ������ű�ʶ����������������ʹ��";"�ָ�-->
	<label id="PrimaryKey" style="display:none">1</label>
   	</form>
	</body>
</html>
