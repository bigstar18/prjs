<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
	
	
<html>
<head>
<title>
ǿ��ת����Ϣ
</title>
    <link href="<c:url value="/timebargain/styles/common.css"/>" type="text/css" rel=stylesheet>    
    <script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script type="text/javascript">
<!--
function window_onload()
{
    highlightFormElements();
    
}

function cancle(){
	window.close();
}

		var xmlHttprequest;
    	function createXML(){
    		if (window.ActiveXObject) {
    			xmlHttprequest = new ActiveXObject("Microsoft.XMLHTTP");
    		}else if (window.XMLHttpRequest) {
    			xmlHttprequest = new XMLHttpRequest();
    		}
    	}
    	var url = "<c:url value="/timebargain/tradeMonitor/tradeMonitor.do?funcflg=ajaxFundsAnalysis&timestamp="/>" + new Date().getTime();
    	
    	function startXML(){
    		createXML();
    		xmlHttprequest.onreadystatechange = operateXML;
    		xmlHttprequest.open("POST",url,true);
    		xmlHttprequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
    		var queryStr = "id=" + document.getElementById("temp").value;
    		xmlHttprequest.send(queryStr);
    	}
    	
    	function operateXML(){
    		if (xmlHttprequest.readyState == 4) {
    			if (xmlHttprequest.status == 200) {
    				send_info();
    				document.getElementsByName("order")[0].disabled = true;
    			}
    		}
    	}
    	
    	function send_info(){
		    	//���ɱ���
		    	var tdTitle = document.createElement("td");
		    	tdTitle.setAttribute("align","right");
		    	tdTitle.appendChild(document.createTextNode("��Ϣ���ݣ�"));
		    	//var trTitle = document.createElement("tr");
		    	//trTitle.appendChild(tdTitle);
		    	
		    	//�����ı���
		    	var text = document.createElement("textarea");
		    	text.setAttribute("id","content");
		    	text.setAttribute("value",xmlHttprequest.responseText);
		    	text.setAttribute("maxlength","70");
		    	text.setAttribute("style","width:550");
		    	text.setAttribute("rows","5");
		    	text.setAttribute("cols","75");
		    	var tdText = document.createElement("td");
		    	tdText.setAttribute("align","left");
		    	tdText.appendChild(text);
		    	var trContent = document.createElement("tr");
		    	trContent.appendChild(tdTitle);
		    	trContent.appendChild(tdText);
		    	
		    	//���ɰ�ť
		    	var subButton = document.createElement("input");
			    subButton.setAttribute("type", "button");
			    subButton.setAttribute("value", "�ύ��Ϣ");
			    subButton.setAttribute("class", "button");
			    subButton.onclick = function () { submitInfo(); };
		    	var buttonTr = document.createElement("tr");
		    	var buttonTd = document.createElement("td");
		    	var buttonTdpsi = document.createElement("td");
		    	buttonTd.setAttribute("align","center");
		    	//buttonTdpsi.appendChild(document.createTextNode("&nbsp;"));
		    	//buttonTd.setAttribute("colspan","2");
		    	buttonTd.appendChild(subButton);
		    	buttonTr.appendChild(buttonTdpsi);
		    	buttonTr.appendChild(buttonTd);
		    	
		    	//��ӵ�table
		    	//document.getElementById("tbo").appendChild(trTitle);
		    	document.getElementById("tbo").appendChild(trContent);
		    	document.getElementById("tbo").appendChild(buttonTr);
		    
		}
		
		function submitInfo(){
			if (confirm("��ȷ��Ҫ�ύ��")) {
				var objValue = document.getElementById("content").value;
				if (objValue == "") {
					alert("��Ϣ���ݲ���Ϊ�գ�");
					return false;
				}else {
					if(objValue.length > 70){
					 	alert("�����������ƣ�ֻ��������70���ַ���");
				        return false;
				    }
				}
				document.getElementsByName("order")[0].disabled = false;
				}
		}
		
		function sendMessage(){
			var firmID = document.getElementById("firmID").innerHTML
			var templateid = document.getElementById("temp").value;
			//pTop("../member/message/messageSmsForward.spr?firmid=" + firmID + "&templateid=" + templateid, 650, 550);
			//alert('<%=request.getContextPath()%>');
			window.showModalDialog("<%=request.getContextPath()%>/member/message/messageSmsForward.spr?firmid=" + firmID + "&templateid=" + templateid,"", "dialogWidth=550px; dialogHeight=350px; status=no;scroll=yes;help=no;resizable=no");
		}
		
// -->
</script>    
</head>

<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
    <table class="common" border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
        <caption><b>�����̻�����Ϣ</b></caption>
        <table class="common" border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="right" class="right" >�����̴��룺</td>
            <td class="left" id="firmID">${mapInfo.firmID}</td>
            <td align="right" class="right">���������ƣ�</td>
            <td  class="left">${mapInfo.firmName}</td>
        </tr>
      
        <tr>
            <td align="right" class="right">��ϵ��ʽ��</td>
            <td class="left">${mapInfo.phone}</td>
            <td align="right" class="right" >�ʽ�ȫ�ʣ�</td>
            <td class="left"><fmt:formatNumber value="${mapInfo.fundsSafeRate}" pattern="#,##0.00"/>&nbsp;</td>
        </tr> 
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr>
        	<td valign="top" colspan="4">
        								
										<table valign="top" border="1"  width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
											<caption><b>�����ֲ̳���Ϣ</b></caption>
											<tr height="20"><td bgcolor="C0C0C0" align="center">��Ʒ����</td><td bgcolor="C0C0C0" align="center">�ֲ�����</td><td bgcolor="C0C0C0" align="center">��������</td><td bgcolor="C0C0C0" align="center">����</td></tr>
											<%
												List listHold = (List)request.getAttribute("listHold");
												System.out.println("listHold: "+listHold.size());
												if (listHold != null && listHold.size() > 0) {
												 	for (int i = 0; i < listHold.size(); i++) {
												 		Map map = (Map)listHold.get(i);
												 		String commodityID = "";
												 		String holdQty = "";
												 		String evenPrice = "";
												 		String bs = "";
												 		if (map.get("commodityID") != null) {
												 			commodityID = map.get("commodityID").toString();
												 		}
												 		if (map.get("evenPrice") != null) {
												 			evenPrice = map.get("evenPrice").toString();
												 		}
												 		if (map.get("BS_Flag") != null) {
												 			bs = map.get("BS_Flag").toString();
												 			if ("1".equals(bs)) {
												 				bs = "��";
												 			}else if ("2".equals(bs)) {
												 				bs = "��";
												 			}
												 		}
												 		if (map.get("HoldQty") != null) {
												 			 holdQty = map.get("HoldQty").toString();
												 		}
												 		
											%>
												<tr valign="top" height="20">
													<td align="center">
														<%=commodityID%>
													</td>
													<td align="right">
														<%=holdQty%>
													</td>
													<td align="right">
														<%=evenPrice%>
													</td>
													<td align="center">
														<%=bs%>
													</td>
												</tr>
											<%
												 	}
													
												}
											%>
										</table>
									</td>
        </tr>
       	
		
        </table>
        <table height="50">
        	<tr>
        		<td>
        			&nbsp;
        		</td>
        	</tr>
        </table>
        <table id="tab" class="common" border="0" align="center" cellpadding="1" cellspacing="1" width="100%">
        	
        	<tbody id="tbo" align="center">
        		
        
        	</tbody>
        </table>
    </table>
</body>
</html>

