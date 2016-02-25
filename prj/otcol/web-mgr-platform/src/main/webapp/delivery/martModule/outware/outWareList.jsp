<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWaresList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<INPUT TYPE="hidden" NAME="requestId" value="">
		<fieldset width="95%">
			<legend>���ⵥ��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="95%" height="35">
				<tr height="35">
					<td align="right">���ⵥ�ţ�</td>
					<td align="left">
						<input id="id" name="_Id[like]" value="<c:out value='${oldParams["Id[like]"]}'/>" type=text class="text" style="width: 120px"onkeypress="notSpace()">
					</td>
					<td align="right">${FIRMID}��</td>
					<td align="left">
						<input id="firmID" name="_firmID[like]" value="<c:out value='${oldParams["firmID[like]"]}'/>" type=text class="text" style="width: 120px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;���ⵥ״̬��
						<select id="ability" name="_ability[=]" class="normal" style="width: 120px">
							<OPTION value="">ȫ��</OPTION>
							<c:forEach items="${statusList}" var="result">
								<option value="${result.value}">${result.name}</option>
			                </c:forEach>
						</select>
						<script>
							frm.ability.value = "<c:out value='${oldParams["ability[=]"]}'/>";
					</script>
					</td>
					
				</tr>
				<tr height="35">
					<td align="right">&nbsp;�����ʱ�䣺</td>
					<td align="left">
						<MEBS:calendar eltID="planOutDate" eltName="_trunc(planOutDate)[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(planOutDate)[=][date]"]}'/>"/>
					</td>
					<td align="right">�ֿ����ƣ�</td>
					<td align="left">
						<select id="warehouseID" name="_warehouseID[=]" class="normal" style="width: 120px">
							<OPTION value="">ȫ��</OPTION>
							<c:forEach items="${warehouseList}" var="warehouseList">
								<option value="${warehouseList.id}">${warehouseList.name}</option>
			                </c:forEach>
						</select>
						<script>
							frm.warehouseID.value = "<c:out value='${oldParams["warehouseID[=]"]}'/>";
						</script>
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;Ʒ�����ƣ�
					<select id="commodityID" name="_commodityID[=]" style="width:120" class="form_k">
							<option value="">ȫ��</option>
							<c:forEach items="${commodityList}" var="commodityList">
								<option value="${commodityList.id}">${commodityList.name}</option>
			                </c:forEach>
					</select>&nbsp;
					<script>
							frm.commodityID.value = "<c:out value='${oldParams["commodityID[=]"]}'/>";
						</script>
					</td>
				</tr>
				<tr height="35">
					<td colspan="4">&nbsp;</td>
					<td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	    <%@include file="outWareTable.jsp" %>
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
		    <tr height="35">
	            <td><div align="right">
				  <button class="lgrbtn" type="button" onclick="add();">��ⵥת���ⵥ</button>
				</div></td>
	        </tr>
        </table>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery(){
		frm.submit();
	}
	function fAuditing(vid){
	
		frm.requestId.value=vid;
		frm.action = "<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWareInfoView";
		frm.submit();
	}
	
	function resetForm(){
		frm.id.value = "";
		frm.ability.value = "";
		frm.firmID.value = "";
		frm.planOutDate.value = "";
		frm.commodityID.options[0].selected = true;
		frm.warehouseID.options[0].selected = true;
		frm.submit();
	}
	
	function initQuery(){
	 	if(frm.pageSize.value == "0" || frm.pageSize.value == "" || frm.pageSize.value == "null")
	 	{
			doQuery();
		}
	}
	
	function add(){
		frm.action="<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWareForward";
		frm.submit();
	}
//-->
</SCRIPT>

