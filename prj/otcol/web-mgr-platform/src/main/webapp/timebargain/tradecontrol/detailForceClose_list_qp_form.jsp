
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
		
		/** 计算价格区间 */
			int spreadAlgr = commodity.getSpreadAlgr();//((BigDecimal) map.get("SpreadAlgr")).intValue();
			double SP_U = commodity.getSpreadUpLmt(); //((BigDecimal) map.get("SpreadUpLmt")).doubleValue();
			double SP_D = commodity.getSpreadDownLmt();// ((BigDecimal) map.get("SpreadDownLmt")).doubleValue();
			double PR_C = commodity.getLastPrice();// ((BigDecimal) map.get("LastPrice")).doubleValue();
			//BigDecimal minPriceMove = (BigDecimal) map.get("MinPriceMove");

		if(spreadAlgr == 1)//按百分比算法对幅度进行最小变动价位整数倍的计算
        {
			SP_U = Math.round(Arith.mul(PR_C,SP_U));
			SP_U = Arith.add(PR_C,SP_U);
			SP_D = Math.round(Arith.mul(PR_C,SP_D));
			SP_D = Arith.sub(PR_C,SP_D);
        }
        else if(spreadAlgr == 2)//固定值算法直接加减即可
        {
      	 	SP_U = Arith.add(PR_C,SP_U);
      	 	SP_D = Arith.sub(PR_C,SP_D);
        }
	%>
	
<html>
<head>
<title>
强制转让信息
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
		  alert("可平仓数量不足，请重新操作！");
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
    	alert("登录者为代为委托员，不能进行强制转让操作！");
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
  
  if (confirm("您确定要提交吗？")) {
	  	if(ordersForm.price.value=="")
	  {
	    alert("委托价格不能为空！");
	    ordersForm.price.focus();
	    return false;
	  }
	  if(ordersForm.quantity.value=="")
	  {
	    alert("委托数量不能为空！");
	    ordersForm.quantity.focus();
	    return false;
	  }
	  if(parseInt(ordersForm.quantity.value)<=0)
	  {
	    alert("强平数量必须大于0");
	    ordersForm.quantity.focus();
	    return false;
	  }
	  if (ordersForm.forceCloseType.value == "") {
	  	alert("强制转让方式不能为空！");
	  	ordersForm.forceCloseType.focus();
	    return false;
	  }
	  if (ordersForm.type.value == "0") {
	    	alert("登陆者为代为委托员，不能进行强制转让操作！");
	    	return false;
	  }
	  
	  startXML();
	  if(doFlag){
		  ordersForm.submit();
		  ordersForm.order.disabled = true;
	  }
  }
}
//交易员是否登陆后提交委托(统一回调函数)
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

//仅输入数字
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
        <caption><b>强制转让信息</b></caption>
        <table class="common"><tr><td>对应订货信息</td></tr></table> 
         <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="100%">            
        	<tr>
            	<td class="left">交易商代码</td>
            	<td class="right"><c:out value="${param['FirmID']}" escapeXml="false"/>&nbsp;</td>
            	<td class="left" bgcolor="#CCFFCC">商品代码</td>
            	<td class="right" ><c:out value="${param['CommodityID']}" escapeXml="false"/>&nbsp;</td>   
	        	</tr>
	        <tr>
	        	<td class="left">订货数量</td>
	            <td class="right"><c:out value="${param['openQty']}" escapeXml="false"/>&nbsp;</td>
	            <td class="left">可转让数量</td>
	            <td class="right"><c:out value="${param['HoldQty']}" escapeXml="false"/>&nbsp;</td>
	        </tr> 
        	<tr>
            	<td class="left" >买卖</td>
	            <td class="right">
	            	<c:choose>
	            		<c:when test="${param['BS_Flag'] eq '1'}">
	            			买进
	            		</c:when>
	            		<c:when test="${param['BS_Flag'] eq '2'}">
	            			卖出
	            		</c:when>
	            	</c:choose>
	            &nbsp;</td>
	            <%--
	            <td class="left" >订货价格</td>
            	<td class="right"><fmt:formatNumber value="<%=EvenPrice %>" pattern="#,##0.00"/>&nbsp;</td>        
        		--%>
        	</tr>
       
        <!-- /tradecontrol/forceClose.do?method=order -->
        </table>
        <table class="common"><tr><td>平仓数量</td></tr></table> 
         <table class="common" border="1" align="center" cellpadding="0" cellspacing="0" width="100%">    
        <html:form action="/timebargain/order/orders.do?funcflg=chkLogin" method="POST" target="HiddFrame">	
        <tr>
        	<%--
        	<td class="left" >二级代码</td>
            <td class="right">
            	<html:text property="customerID" maxlength="16" size="16" />
				<span class="req">*</span>
            </td>
            --%>
            <td class="left" >强制转让价格方式</td>
            <td class="right">
            	按市价:<input type="checkbox" name="priceType" value="1" onselect="" onclick="priceTypeSelect(this);">
            </td>
            <td class="left" >委托价格</td>
            <td class="right">
            	<html:text property="price" maxlength="16" size="16" style="ime-mode:disabled" onkeypress="return numberPass()" styleClass="Number"/>
				<span class="req">*</span>
            </td>
        	
        </tr>
        <tr>
            <td class="left">平仓数量</td>
            <td class="right">
            	<html:text property="quantity" maxlength="10" size="16" onkeypress="onlyNumberInput()" style="ime-mode:disabled" styleClass="Number" readonly=""/>
				<%--<span class="req">*</span>--%>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
        </tr>
        <tr>
            <td class="left" colspan="4">
            <font color="red">强转参考价格：<span id="">买强转默认价格为跌停版价格；卖强转默认价格为涨停板价格</span></font></td>
            
        </tr>
        <tr>			     							
			<td colspan="4" align="center">
			  <html:button  property="order" styleClass="button" onclick="javascript:return order_onclick();">强制转让</html:button>
			  <html:button  property="cancel" styleClass="button" onclick="close_close()">取消</html:button>			  			  
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