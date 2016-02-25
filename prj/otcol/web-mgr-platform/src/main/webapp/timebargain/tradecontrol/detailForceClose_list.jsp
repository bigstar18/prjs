<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--

var xmlHttprequest;
var doFlag = false;
function createXML(){
	if (window.ActiveXObject) {
		xmlHttprequest = new ActiveXObject("Microsoft.XMLHTTP");
	}else if (window.XMLHttpRequest) {
		xmlHttprequest = new XMLHttpRequest();
	}
}

function startXML(url, queryStr){
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
		var maycloseQty = parseInt(quantitys[0]);
		var holdqty = parseInt(quantitys[1]);
		if(maycloseQty < holdqty) {
			doFlag = false;
			alert("��ƽ���������㣡");
		} else {
			doFlag = true;
		}
	} 
}
function onclick_FC( firmID, commodityID, bs_Flag, holdQty, openQty, maycloseQty)
{
	//var url = "<c:url value="/timebargain/tradecontrol/tradeCtl.do?funcflg=searchMayForceCloseHQty&timestamp="/>" + new Date().getTime();
	//var params = "firmID=" + firmID + "&commodityID=" + commodityID + "&bS_Flag=" + bs_Flag;
	//startXML( url, params);
	//if(doFlag) {
		var fcFlag = 1;//������ϸ ƽ��
		holdQty=parseInt(holdQty)<=parseInt(maycloseQty)?holdQty:maycloseQty;
	    if(pTop("<c:url value="/timebargain/tradecontrol/forceClose_list_qp.jsp?CommodityID="/>" + commodityID + 
	    	"&BS_Flag=" + bs_Flag + "&HoldQty=" + holdQty + "&FirmID=" + firmID + 
	    	"&fcFlag=" + fcFlag + "&openQty=" + openQty,600,400))
	    {
	    	// ˢ���б�
		    ECSideUtil.reload("ec");
	    }
	//}
}

//logoff
function logoff_onclick()
{
	parent.HiddFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=logoff&mkName=forceClose"/>";
}
// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="holdList" var="hold" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/tradeCtl.do?funcflg=searchDetailForceClose"	
			xlsFileName="DetailForceCloseList.xls" 
			csvFileName="DetailForceCloseList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="20"	
			minHeight="300"
			sortable="true"
			retrieveRowsCallback="limit"
	>
		<ec:row>
		  
		    
			<ec:column property="firmID" title="�����̴���" width="80" style="text-align:center;"/>	
			<ec:column property="commodityid" title="��Ʒ����" width="90" style="text-align:center;"/>
			<ec:column property="bS_Flag" title="����" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
			<%--<ec:column property="price" title="�۸�" cell="currency" width="100" style="text-align:right;"/>			
			<ec:column property="holdTime" title="��������" cell="date" format="yyyy-MM-dd hh:mm" width="115" style="text-align:center;"/>
			<ec:column property="gageQty" title="�ֶ�����" width="100" style="text-align:right;"/>--%>
			<ec:column property="openQty" title="��������" width="80" style="text-align:right;"/>
			<ec:column property="HoldQty" title="�ֲ�����" width="80" style="text-align:right;"/>
			<ec:column property="maycloseQty" title="��ת������" width="80" style="text-align:right;">
				<c:choose>
					<c:when test="${hold.HoldQty <= hold.maycloseQty}">${hold.HoldQty }</c:when>
					<c:otherwise>${hold.maycloseQty }</c:otherwise> 
				</c:choose> 
			</ec:column>
			<ec:column sortable="false" filterable="false" property="_1" title="����" width="60" style="text-align:center;">
				<a href="#" onclick="javascript:onclick_FC( '${hold.firmID}', '${hold.commodityid}', '${hold.bS_Flag}', '${hold.HoldQty}',  '${hold.openQty }','${hold.maycloseQty}')" style="color:blue; ">ǿƽ</a>
			</ec:column>
		</ec:row>
		<ec:extend>
	       <c:if test="${not empty sessionID}">
			  <a href="#" onclick="logoff_onclick()">ע����¼</a>
			  </c:if>	
	    </ec:extend>
	</ec:table>
</td></tr>
</table>	
	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ��
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	 �༭״̬����ģ�� 
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	-->
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDERTYPE" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADETYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<%
  if(request.getAttribute("holdList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.tradeCtlForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
<!--
  var cll;
  for (var i=0;i<ec_table.rows.length;i++) 
  {
    /*cll = ec_table.rows(i).cells(4);
    var bs_Flag = cll.innerHTML;
    if (bs_Flag == "1") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.BS_Flag.option.b1"/>";
    }
    else if (bs_Flag == "2") 
    {
       cll.innerHTML = "<fmt:message key="ordersForm.BS_Flag.option.s1"/>";
    }
    else
    {
    	cll.innerHTML = "";
    }
    cl2 = ec_table.rows(i).cells(3);
    var uni_Cmdty_Code=cl2.innerHTML;
    cl2.innerHTML =uni_Cmdty_Code.substring(2,uni_Cmdty_Code.length);*/
  }
// -->
</script>
</body>


</html>
