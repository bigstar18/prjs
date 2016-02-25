<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/regStockApplyController.${POSTFIX}?funcflg=regStockApplyList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>ע��ֵ������ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�ֵ�����ţ�</td>
					<td align="left">
						<input type="text" name="_ApplyID[like]" id="applyId" value="<c:out value='${oldParams["ApplyID[like]"]}'/>" class="text" style="width: 120px"
onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">${FIRMID }��</td>
					<td align="left">
						<input type="text" name="_FirmID[like]" id="firmId" value="<c:out value='${oldParams["FirmID[like]"]}'/>" class="text" style="width: 120px"
onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�䣺
						<MEBS:calendar eltID="applyTime" eltName="_trunc(ApplyTime)[=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["trunc(ApplyTime)[=][date]"] }'/>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�ֵ�״̬��</td>
					<td align="left">
						<select name="_Status[=]" class="normal" id="regStockStatus" style="width: 120px">
							<option value="">ȫ��</option>
							<option value="1">δ���</option>
							<option value="2">�����</option>
							<option value="-2">����</option>
						</select>
						<script type="text/javascript">
							document.getElementById("regStockStatus").value="<c:out value='${oldParams["Status[=]"]}'/>";
						</script>
					</td>
					<td align="right">�ֿ����ƣ�</td>
					<td align="left">
						<select id="warehouseId" name="_WarehouseID[=]" class="normal" style="width: 120px">
							<OPTION value="">ȫ��</OPTION>
							<c:forEach items="${warehouseList }" var="warehouse">
								<OPTION value="${warehouse.id }">${warehouse.name }</OPTION>
							</c:forEach>
						</select>
						<script>
							frm.warehouseId.value = "<c:out value='${oldParams["WarehouseID[=]"]}'/>";
					   </script>						
					</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ʒ�����ƣ�
						<select id="breedId" name="_BreedID[=]" class="normal" style="width: 120px">
							<OPTION value="">ȫ��</OPTION>
							<c:forEach items="${breedList }" var="list">
								<OPTION value="${list.id }">${list.name }</OPTION>
							</c:forEach>
						</select>
						<script>
							frm.breedId.value = "<c:out value='${oldParams["BreedID[=]"]}'/>";
					   </script>						
					</td>
					</tr>
					<tr>
					<td colspan="4">&nbsp;</td>
					<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	  <%@include file="regStockApplyTable.jsp" %>
	<INPUT TYPE="hidden" NAME="tag" value="">
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function initQuery()
	{
		if( frm.pageSize.value == "" || frm.pageSize.value == "null"){
			doQuery();
		}
	}
	function doQuery(){
		frm.submit();
	}
	function viewOprLog(logid){
		frm.action = "<%=basePath%>servlet/regStockApplyController.${POSTFIX}?funcflg=regStockApplyView&id="+logid;
		frm.submit();
	}
	function resetForm(){
		frm.applyId.value = "";
		frm.firmId.value = "";
		frm.applyTime.value = "";
		frm.regStockStatus.options[0].selected = true;
		frm.warehouseId.options[0].selected = true;
		frm.breedId.options[0].selected = true;
		frm.submit();
	}
//-->
</SCRIPT>

