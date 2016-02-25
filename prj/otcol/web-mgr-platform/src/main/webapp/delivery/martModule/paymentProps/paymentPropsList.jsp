<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>


<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" name="requestModuleID" value="">
		<input type="hidden" name="requestBreedID" value="">
		<input type="hidden" name="requestSettleDayNo" value="">
		<fieldset width="95%">
			<legend>�����ո����ò�ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">����ģ�飺</td>
					<td align="left">
						<select id="moduleID" name="_moduleID[=]" class="normal" style="width: 120px">
							<OPTION value="">ȫ��</OPTION>
							<c:forEach items="${moduleNameMap}" var="moduleNameMap">
								<option value="${moduleNameMap.key}">${moduleNameMap.value}</option>
			                </c:forEach>
						</select>
						<script>
							document.getElementById("moduleID").value = "<c:out value='${oldParams["moduleID[=]"]}'/>";
						</script>
					</td>
					
					<td align="right">Ʒ�����ƣ�</td>
					<td align="left">
					<select id="breedID" name="_breedID[=]" style="width:120" class="normal">
							<option value="">ȫ��</option>
							<c:forEach items="${PayCommoditysList}" var="payCommoditysList">
								<option value="${payCommoditysList.id}">${payCommoditysList.name}</option>
			                </c:forEach>
						</select>&nbsp;
						<script>
							document.getElementById("breedID").value = "<c:out value='${oldParams["breedID[=]"]}'/>" ;
						</script>
					</td>
					
					<td align="right">�ڼ������գ�</td>
					<td align="left">
						<input id="settleDayNo" name="_settleDayNo[=]" value="<c:out value='${oldParams["settleDayNo[=]"]}'/>" type=text class="text" style="width: 120px" onkeypress="notSpace()">
					</td>
					
					
					<td colspan="4">&nbsp;</td>
					<td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onClick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onClick="resetForm();">����</button>&nbsp;
					</td>
				</tr>
					<script>
							//frm.enterDate.value = "<c:out value='${oldParams["enterDate[=]"]}'/>";
					</script>
				
				
				
			</table>
		</fieldset>
	  <%@include file="paymentPropsTable.jsp" %>
		<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
            
			 </div></td>
			 <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">����ո�����</button>&nbsp;&nbsp;
             <button class="lgrbtn" type="button" onclick="disposeRecForbidOrResume(frm,tableList,'delCheck','ɾ��')">ɾ��</button>&nbsp;&nbsp;
			 </div></td>
        </tr>
    	</table>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--

	function initQuery(){
		if(frm.pageSize.value == "" || frm.pageSize.value == "null"){
			doQuery();
		}
	}
	function doQuery(){
		//var num = 1;
		//if (frm.settleDayNo.value==""){
		//	return true;
		//}else if(isNaN(frm.settleDayNo.value)){
		//	alert("���������ֽ�����");
		//	return false;
		//}
		frm.submit();
	}

	function resetForm(){
		frm.moduleID.value = "";
		frm.breedID.value = "";
		frm.settleDayNo.value = "";
		frm.submit();
	}
	
	//�鿴����
	function fAuditing(moduleID, breedID, settleDayNo){
		frm.requestModuleID.value = moduleID;
		frm.requestBreedID.value = breedID;
		frm.requestSettleDayNo.value = settleDayNo;
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsView";
		frm.submit();
	}
	
	//���
	function add(){
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsAddForward";
		frm.submit();
	}
	//�޸�
	function fupdate(moduleID, breedID, settleDayNo){
		frm.requestModuleID.value = moduleID;
		frm.requestBreedID.value = breedID;
		frm.requestSettleDayNo.value = settleDayNo;
		frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsUpdateForward";
		frm.submit();
	}
	//ɾ��
		function disposeRecForbidOrResume(frm_delete,tableList,checkName,dispose){
		if(isSelNothing(tableList,checkName) == -1){
			alert ( "û�п���"+dispose+"�����ݣ�" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "��ѡ����Ҫ"+dispose+"�����ݣ�" );
			return;
		}
		if(confirm("��ȷʵҪ"+dispose+"ѡ��������")){
			frm.action = "<%=basePath%>servlet/paymentPropsController.${POSTFIX}?funcflg=paymentPropsDelete";
			frm_delete.submit();
		}
		
}
	
//-->
</SCRIPT>

