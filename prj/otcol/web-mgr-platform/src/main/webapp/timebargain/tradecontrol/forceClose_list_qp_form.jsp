<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.TradeCtlManager"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
	<%
		TradeCtlManager mgr = (TradeCtlManager)SysData.getBean("tradeCtlManager");
		String firmID = request.getParameter("FirmID");
		List list = mgr.getTraderInfo(firmID);
		String runtimeFL = "";
		String clearFL = "";
		String runtimeMargin = "";
		String clearMargin = "";
		String virtualFunds = "";
		String maxOverdraft = "";
		String usefulFunds = "";
		String minClearDeposit = "";
		if (list != null && list.size() > 0) {
			Map map = (Map)list.get(0);
			if (map.get("runtimeFL") != null) {
				runtimeFL = map.get("runtimeFL").toString();
			}
			if (map.get("clearFL") != null) {
				clearFL = map.get("clearFL").toString();
			}
			if (map.get("runtimeMargin") != null) {
				runtimeMargin = map.get("runtimeMargin").toString();
			}
			if (map.get("clearMargin") != null) {
				clearMargin = map.get("clearMargin").toString();
			}
			if (map.get("virtualFunds") != null) {
				virtualFunds = map.get("virtualFunds").toString();
			}
			if (map.get("maxOverdraft") != null) {
				maxOverdraft = map.get("maxOverdraft").toString();
			}
			if (map.get("usefulFunds") != null) {
				usefulFunds = map.get("usefulFunds").toString();
			}
			if (map.get("minClearDeposit") != null) {
				minClearDeposit = map.get("minClearDeposit").toString();
			}
		}
		
		String EvenPrice = request.getParameter("EvenPrice");
	%>
	<%
			
			String quantity = "1";
			String bS_Flag = request.getParameter("BS_Flag");
			String commodityID = request.getParameter("CommodityID");
			String holdQty = request.getParameter("HoldQty");
			System.out.println("======================================>>>>>>>:"+bS_Flag);
			
			String type = (String)request.getSession().getAttribute("type");
			String relType = "";
			if (type != null && !"".equals(type)) {
				relType = type;
			}
			
			StatQueryManager smgr = (StatQueryManager)SysData.getBean("statQueryManager");
			String lastPrice = smgr.getQuotationPrice(commodityID);
			//String lastPrice = com.getLastPrice()+"";
	%>
	
	
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
    ordersForm.customerID.value = "<c:out value="${param['CustomerID']}"/>";
    ordersForm.uni_Cmdty_Code.value = "<c:out value="${param['Uni_Cmdty_Code']}"/>";
   
    ordersForm.customerID.focus();
    
    if (ordersForm.type.value == "0") {
    	alert("��¼��Ϊ��Ϊί��Ա�����ܽ���ǿ��ת�ò�����");
    }
    
    ordersForm.forceCloseType.value = "1";
    ordersForm.customerID.value = '<%=firmID%>' + '00';
    ordersForm.price.value = '<%=lastPrice%>';
    startXML();
}

function order_onclick()
{
  
  if (confirm("��ȷ��Ҫ�ύ��")) {
  	if(ordersForm.price.value=="")
  {
    alert("ί�м۸���Ϊ�գ�");
    ordersForm.price.focus();
    return false;
  }
  if(ordersForm.quantity.value=="")
  {
    alert("ί����������Ϊ�գ�");
    ordersForm.quantity.focus();
    return false;
  }
  if (ordersForm.forceCloseType.value == "") {
  	alert("ǿ��ת�÷�ʽ����Ϊ�գ�");
  	ordersForm.forceCloseType.focus();
    return false;
  }
  if (ordersForm.type.value == "0") {
    	alert("��½��Ϊ��Ϊί��Ա�����ܽ���ǿ��ת�ò�����");
    	return false;
  }
  var qty = parseFloat(ordersForm.quantity.value);
  var holdQty = parseFloat('<%=holdQty%>'+"");
  if (qty > holdQty) {
	  alert("ί������ӦС�ڶ���������");
	  return false;
  }
  /*
  alert(ordersForm.orderType.value);
  alert(ordersForm.customerID.value);
  alert(ordersForm.BS_Flag.value);
  alert(ordersForm.uni_Cmdty_Code.value);
  alert(ordersForm.price.value);
  alert(ordersForm.quantity.value);
  alert(ordersForm.closeFlag.value);
  */
  ordersForm.submit();
  ordersForm.order.disabled = true;
  }
}
//����Ա�Ƿ��½���ύί��(ͳһ�ص�����)
function order()
{
	var firmID = ordersForm.firmID.value;
	//alert(firmID);
	ordersForm.action = "<c:url value="/timebargain/tradecontrol/forceClose.do?funcflg=forceClose&firmID="/>" + firmID;
	ordersForm.submit();
	ordersForm.order.disabled = true;
	ordersForm.action = "<c:url value="/timebargain/order/orders.do?funcflg=chkLogin"/>";
}

function close_close(){
	window.close();
}

function forceCloseType_onchange(){
	if (ordersForm.forceCloseType.value == "1") {
		ordersForm.price.value = "";
		setReadWrite(ordersForm.price);
	}
	if (ordersForm.forceCloseType.value == "2") {
		ordersForm.price.value = "0";
		setReadOnly(ordersForm.price);
	}
}

// -->
</script>    
</head>

<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
    <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="80%">
        <caption><b>ǿ��ת����Ϣ</b></caption>
        <table class="common"><tr><td>��������Ϣ</td></tr></table>
        <table class="common" border="1" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="left">�����̴���</td>
            <td class="right"><c:out value="${param['FirmID']}" escapeXml="false"/>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            
        </tr>
      
        <tr>
            <td class="left" >���㸡��</td>
            <td class="right"><fmt:formatNumber value="<%=clearFL%>" pattern="#,##0.00"/>&nbsp;</td>
            <td class="left">��ʱ����</td>
            <td class="right"><fmt:formatNumber value="<%=runtimeFL%>" pattern="#,##0.00"/>&nbsp;</td>
            
        </tr> 
        <tr>
            <td class="left" >���㱣֤��</td>
            <td class="right"><fmt:formatNumber value="<%=clearMargin%>" pattern="#,##0.00"/>&nbsp;</td>
            <td class="left">��ʱ��֤��</td>
            <td class="right"><fmt:formatNumber value="<%=runtimeMargin%>" pattern="#,##0.00"/>&nbsp;</td>
            
        </tr>
        <tr>
            <td class="left" >��Ѻ�ʽ�</td>
            <td class="right"><fmt:formatNumber value="<%=maxOverdraft%>" pattern="#,##0.00"/>&nbsp;</td>
            <!-- 
            <td class="left">�����ʽ�</td>
            <td class="right"><fmt:formatNumber value="<%=virtualFunds%>" pattern="#,##0.00"/>&nbsp;</td>
             -->
        </tr>  
        <tr>
        	<td class="left" >�����ʽ�</td>
            <td class="right"><fmt:formatNumber value="<%=usefulFunds%>" pattern="#,##0.00"/>&nbsp;</td>
            <td class="left" >��ͽ��㱣֤��</td>
            <td class="right"><fmt:formatNumber value="<%=minClearDeposit%>" pattern="#,##0.00"/>&nbsp;</td>            
        </tr> 
        </table> 
        <table class="common"><tr><td>��Ӧ������Ϣ</td></tr></table> 
         <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="100%">            
        <tr>
            <td class="left" bgcolor="#CCFFCC">��Ʒ����</td>
            <td class="right" ><c:out value="${param['CommodityID']}" escapeXml="false"/>&nbsp;</td>   
            <td class="left" >����</td>
            <td class="right">
            	<c:choose>
            		<c:when test="${param['BS_Flag'] eq '1'}">
            			���
            		</c:when>
            		<c:when test="${param['BS_Flag'] eq '2'}">
            			����
            		</c:when>
            	</c:choose>
            &nbsp;</td>       
        </tr>
        <tr>
            <td class="left">��������</td>
            <td class="right"><c:out value="${param['HoldQty']}" escapeXml="false"/>&nbsp;</td>
            <td class="left" >��������</td>
            <td class="right"><fmt:formatNumber value="<%=EvenPrice %>" pattern="#,##0.00"/>&nbsp;</td> 
        </tr> 
       
        <!-- /tradecontrol/forceClose.do?method=order -->
        </table>
        <table class="common"><tr><td>��������</td></tr></table> 
         <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="100%">    
        <html:form action="/timebargain/order/orders.do?funcflg=chkLogin" method="POST" target="HiddFrame">	
        <tr>
        	<td class="left" >��������</td>
            <td class="right">
            	<html:text property="customerID" maxlength="16" size="16" />
				<span class="req">*</span>
            </td>
            <td class="left" >ί�м۸�</td>
            <td class="right">
            	<html:text property="price" maxlength="16" size="16" style="ime-mode:disabled" onkeypress="return numberPass()" onchange="startXML()" styleClass="Number"/>
				<span class="req">*</span>
            </td>
        	
        </tr>
        <tr>
            <td class="left" >ǿ��ת�ü۸�ʽ</td>
            <td class="right">
            	<html:select property="forceCloseType" style="width:125" onchange="forceCloseType_onchange()">
					<html:option value=""></html:option>
				    <html:option value="1">ָ���۸�</html:option>
					<html:option value="2">���м�</html:option>
			   </html:select>
				<span class="req">*</span>
            </td>
            
            <td class="left">ί������</td>
            <td class="right">
            	
            	<html:text property="quantity" maxlength="10" size="16" onkeypress="return numberPass()" style="ime-mode:disabled" styleClass="Number"/>
				<span class="req">*</span>
			</td>
        </tr>
        <tr>
            <td class="left" colspan="4">
            <font color="red">ǿת�ο�������<span id="referenceQty"></span></font></td>
            
        </tr>
        <tr>			     							
			<td colspan="4" align="center">
			  <html:button  property="order" styleClass="button" onclick="javascript:return order_onclick();">ǿ��ת��</html:button>
			  <html:button  property="cancel" styleClass="button" onclick="close_close()">ȡ��</html:button>			  			  
			</td>
		</tr>
		<html:hidden property="orderType" value="2"/>
		<html:hidden property="BS_Flag"/><html:hidden property="uni_Cmdty_Code"/>
		<html:hidden property="closeFlag" value="2"/>
		<html:hidden property="closeAlgr" value="0"/>
		
		<html:hidden property="firmID" value="<%=firmID%>"/>
		
		<html:hidden property="bS_Flag" value="<%=bS_Flag%>"/>
		<html:hidden property="CommodityID" value="<%=commodityID%>"/>
		<html:hidden property="closeMode" value="1"/>
		<html:hidden property="type" value="<%=relType%>"/>
        </html:form>   
        </table>     
    </table>
</body>
</html>
<script type="text/javascript">
    	var xmlHttprequest;
    	function createXML(){
    		if (window.ActiveXObject) {
    			xmlHttprequest = new ActiveXObject("Microsoft.XMLHTTP");
    		}else if (window.XMLHttpRequest) {
    			xmlHttprequest = new XMLHttpRequest();
    		}
    	}
    	var url = "<c:url value="/timebargain/tradecontrol/tradeCtl.do?funcflg=searchForceCloseMoney&timestamp="/>" + new Date().getTime();
    	
    	function startXML(){
    		createXML();
    		var	queryStr = "firmID=" + '<%=firmID%>' + "&commodityID=" + '<%=commodityID%>' + "&bS_Flag=" + '<%=bS_Flag%>' + "&quantity=" + '<%=quantity%>' + "&evenPrice=" + '<%=EvenPrice%>' + "&price=" + ordersForm.price.value + "&holdQty=" + '<%=holdQty%>' + "&usefulFunds=" + '<%=usefulFunds%>';
    		xmlHttprequest.onreadystatechange = operateXML;
    		xmlHttprequest.open("POST", url, true);
    		xmlHttprequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
    		xmlHttprequest.send(queryStr);
    	}
    	
    	function operateXML(){
    		if (xmlHttprequest.readyState == 4) {
    			responseMoney();
    		}
    	}
    	
    	function responseMoney(){
    		if (xmlHttprequest.status == 200) {
    			ordersForm.quantity.value = xmlHttprequest.responseText;
    			document.getElementById("referenceQty").innerHTML = xmlHttprequest.responseText;
    			
    		}
    	}
    	
    </script>
