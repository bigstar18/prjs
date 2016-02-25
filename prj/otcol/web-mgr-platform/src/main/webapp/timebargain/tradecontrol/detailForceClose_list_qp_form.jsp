
<%@page import="gnnt.MEBS.timebargain.manage.util.Arith"%><%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.TradeCtlManager"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
	<%
		String firmID = request.getParameter("FirmID");
		//String EvenPrice = request.getParameter("EvenPrice");
		String quantity = "1";
		String bS_Flag = request.getParameter("BS_Flag");
		String commodityID = request.getParameter("CommodityID");
		String holdQty = request.getParameter("HoldQty");
		
		String type = (String)request.getSession().getAttribute("type");
		String relType = "";
		if (type != null && !"".equals(type)) {
			relType = type;
		}
		
		StatQueryManager smgr = (StatQueryManager)SysData.getBean("statQueryManager");
		CommodityManager mgr = (CommodityManager) SysData.getBean("commodityManager");
		String lastPrice = smgr.getQuotationPrice(commodityID);
		
		Commodity commodity = mgr.getCommodityById(commodityID);
		
		/** ����۸����� */
			int spreadAlgr = commodity.getSpreadAlgr();//((BigDecimal) map.get("SpreadAlgr")).intValue();
			double SP_U = commodity.getSpreadUpLmt(); //((BigDecimal) map.get("SpreadUpLmt")).doubleValue();
			double SP_D = commodity.getSpreadDownLmt();// ((BigDecimal) map.get("SpreadDownLmt")).doubleValue();
			double PR_C = commodity.getLastPrice();// ((BigDecimal) map.get("LastPrice")).doubleValue();
			//BigDecimal minPriceMove = (BigDecimal) map.get("MinPriceMove");

		if(spreadAlgr == 1)//���ٷֱ��㷨�Է��Ƚ�����С�䶯��λ�������ļ���
        {
			SP_U = Math.round(Arith.mul(PR_C,SP_U));
			SP_U = Arith.add(PR_C,SP_U);
			SP_D = Math.round(Arith.mul(PR_C,SP_D));
			SP_D = Arith.sub(PR_C,SP_D);
        }
        else if(spreadAlgr == 2)//�̶�ֵ�㷨ֱ�ӼӼ�����
        {
      	 	SP_U = Arith.add(PR_C,SP_U);
      	 	SP_D = Arith.sub(PR_C,SP_D);
        }
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
var xmlHttprequest;
var doFlag = false;
var lprice = 0;
function createXML(){
	if (window.ActiveXObject) {
		xmlHttprequest = new ActiveXObject("Microsoft.XMLHTTP");
	}else if (window.XMLHttpRequest) {
		xmlHttprequest = new XMLHttpRequest();
	}
}

function startXML(){
	var url = "<c:url value="/timebargain/tradecontrol/tradeCtl.do?funcflg=searchMayForceCloseHQty&timestamp="/>" + new Date().getTime();
	var queryStr = "firmID=<%=firmID%>&commodityID=<%=commodityID%>&bS_Flag=<%=bS_Flag%>";
	createXML();
	xmlHttprequest.onreadystatechange = operateXML;
	xmlHttprequest.open("POST", url, false);
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
		var quantityStr = xmlHttprequest.responseText;// maycloseQty,holdqty
		var quantitys = quantityStr.split(",");
		var maycloseQty =  parseInt(quantitys[0]);
		var holdqty =  parseInt(quantitys[1]);		
		
		var qty = parseFloat(ordersForm.quantity.value);
	 	var closeQty = holdqty<=maycloseQty?holdqty:maycloseQty;
	  if (qty > closeQty) {
		  alert("��ƽ���������㣬�����²�����");
		  close_close();
	  }else {
		  doFlag = true;
	  }
	} 
}
    
function window_onload()
{
    highlightFormElements();
    //ordersForm.customerID.value = "<c:out value="${param['CustomerID']}"/>";
    ordersForm.uni_Cmdty_Code.value = "<c:out value="${param['Uni_Cmdty_Code']}"/>";

    //ordersForm.customerID.focus();
    
    if (ordersForm.type.value == "0") {
    	alert("��¼��Ϊ��Ϊί��Ա�����ܽ���ǿ��ת�ò�����");
    }
    
    ordersForm.forceCloseType.value = "1";
    ordersForm.customerID.value = '<%=firmID%>' + '00';
    <c:choose>
   		<c:when test="${param['BS_Flag'] eq '1'}">
   			ordersForm.price.value = '<%=SP_D%>';
   			lprice = '<%=SP_D%>';
   		</c:when>
   		<c:when test="${param['BS_Flag'] eq '2'}">
   			ordersForm.price.value = '<%=SP_U%>';
   			lprice = '<%=SP_U%>';
   		</c:when>
   	</c:choose>
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
	  if(parseInt(ordersForm.quantity.value)<=0)
	  {
	    alert("ǿƽ�����������0");
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
	  
	  startXML();
	  if(doFlag){
		  ordersForm.submit();
		  ordersForm.order.disabled = true;
	  }
  }
}
//����Ա�Ƿ��½���ύί��(ͳһ�ص�����)
function order()
{
	var firmID = ordersForm.firmID.value;
	//ordersForm.action = "<c:url value="/timebargain/tradecontrol/forceClose.do?funcflg=forceClose&firmID="/>" + firmID;
	ordersForm.action = "<c:url value="/timebargain/tradecontrol/forceClose.do?funcflg=forceCloseExpire&firmID="/>" + firmID;
	ordersForm.submit();
	ordersForm.order.disabled = true;
	ordersForm.action = "<c:url value="/timebargain/order/orders.do?funcflg=chkLogin"/>";
}

function close_close(){
	window.returnValue = true;
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

function priceTypeSelect(obj) {
	if(!obj.checked) {
		ordersForm.forceCloseType.value = "1";
		ordersForm.price.value = lprice;
		setReadWrite(ordersForm.price);
	}else {
		ordersForm.forceCloseType.value = "2";
		ordersForm.price.value = "0";
		setReadOnly(ordersForm.price);
	}
}

//����������
function onlyNumberInput() {
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}
// -->
</script>    
</head>

<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
    <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="80%">
        <caption><b>ǿ��ת����Ϣ</b></caption>
        <table class="common"><tr><td>��Ӧ������Ϣ</td></tr></table> 
         <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="100%">            
        	<tr>
            	<td class="left">�����̴���</td>
            	<td class="right"><c:out value="${param['FirmID']}" escapeXml="false"/>&nbsp;</td>
            	<td class="left" bgcolor="#CCFFCC">��Ʒ����</td>
            	<td class="right" ><c:out value="${param['CommodityID']}" escapeXml="false"/>&nbsp;</td>   
	        	</tr>
	        <tr>
	        	<td class="left">��������</td>
	            <td class="right"><c:out value="${param['openQty']}" escapeXml="false"/>&nbsp;</td>
	            <td class="left">��ת������</td>
	            <td class="right"><c:out value="${param['HoldQty']}" escapeXml="false"/>&nbsp;</td>
	        </tr> 
        	<tr>
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
	            <%--
	            <td class="left" >�����۸�</td>
            	<td class="right"><fmt:formatNumber value="<%=EvenPrice %>" pattern="#,##0.00"/>&nbsp;</td>        
        		--%>
        	</tr>
       
        <!-- /tradecontrol/forceClose.do?method=order -->
        </table>
        <table class="common"><tr><td>ƽ������</td></tr></table> 
         <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="100%">    
        <html:form action="/timebargain/order/orders.do?funcflg=chkLogin" method="POST" target="HiddFrame">	
        <tr>
        	<%--
        	<td class="left" >��������</td>
            <td class="right">
            	<html:text property="customerID" maxlength="16" size="16" />
				<span class="req">*</span>
            </td>
            --%>
            <td class="left" >ǿ��ת�ü۸�ʽ</td>
            <td class="right">
            	���м�:<input type="checkbox" name="priceType" value="1" onselect="" onclick="priceTypeSelect(this);">
            </td>
            <td class="left" >ί�м۸�</td>
            <td class="right">
            	<html:text property="price" maxlength="16" size="16" style="ime-mode:disabled" onkeypress="return numberPass()" styleClass="Number"/>
				<span class="req">*</span>
            </td>
        	
        </tr>
        <tr>
            <td class="left">ƽ������</td>
            <td class="right">
            	<html:text property="quantity" maxlength="10" size="16" onkeypress="onlyNumberInput()" style="ime-mode:disabled" styleClass="Number" readonly=""/>
				<%--<span class="req">*</span>--%>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
        </tr>
        <tr>
            <td class="left" colspan="4">
            <font color="red">ǿת�ο��۸�<span id="">��ǿתĬ�ϼ۸�Ϊ��ͣ��۸���ǿתĬ�ϼ۸�Ϊ��ͣ��۸�</span></font></td>
            
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
		<html:hidden property="forceCloseType" value=""/>
		<html:hidden property="customerID" />
        </html:form>   
        
        </table>     
    </table>
    <script type="text/javascript">
    	document.getElementById("quantity").value = <%=holdQty %>
    </script>
</body>
</html>
<script type="text/javascript">
	
</script>