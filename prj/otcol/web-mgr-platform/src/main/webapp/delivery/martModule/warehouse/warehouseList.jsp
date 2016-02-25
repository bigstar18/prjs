<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html>
  <head>
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
    	<input type="hidden" name="tag" value="">
    	<input type="hidden" id="warehouseId" name="warehouseId" value="">
		<fieldset width="95%">
			<legend>�ֿ��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">�ֿ��ţ�</td>
					<td align="left">
						<input id="id" name="_id[like]" value="<c:out value='${oldParams["id[like]"]}'/>" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">�ֿ����ƣ�</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					
					<%-- 
					<td align="right">�ֿ�״̬&nbsp;</td>
					<td align="left">
						<select id="ability" name="_ability[=]" class="normal" style="width: 100px">
							<OPTION value="">ȫ��</OPTION>
							<c:forEach items="${statusList}" var="result">
			                <option value="${result.value}">${result.name}</option>
			                </c:forEach>
						</select>
						<script>
							frm.ability.value = "<c:out value='${oldParams["ability[=]"]}'/>";
						</script>
					</td>
					--%>	
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	
	<%@ include file="warehouseTable.jsp"%>
   	
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="fadd()">���</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="disposeDel(frm,tableList,'delCheck','ɾ��')">ɾ��</button>&nbsp;&nbsp;
			  <!-- <button class="smlbtn" type="button" onclick="document.frm.tag.value='resume';disposeRecForbidOrResume(frm,tableList,'delCheck','�ָ�')">�ָ�</button> -->
            </div></td>
        </tr>
    </table>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function initQuery()
	{
	 	if( frm.pageSize.value == "" || frm.pageSize.value == "null")
		{
			frm.submit();
		}
	}
	function doQuery(){
		frm.submit();
	}
	function resetForm(){
		frm.id.value = "";
		frm.name.value = "";
		frm.ability.options[0].selected = true;
		frm.submit();
	}
	function fmod(vid){
		frm.warehouseId.value = vid;
		frm.action ="<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseView";
		frm.submit();
	}
	function relate(vid){
		frm.warehouseId.value = vid;
		frm.action ="<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=commodityWarehouseView";
		frm.submit();
	}
	function fadd(){
		frm.action = "<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseAddForward";
		frm.submit();
	}
	
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
			frm.action = "<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=commodityWarehouseForbidOrResume";
			frm_delete.submit();
		}
    }
    
    // ɾ�� 
    function disposeDel(frm_delete,tableList,checkName,dispose){
        if(isSelNothing(tableList,checkName) == -1){
			alert ( "û�п���"+dispose+"�����ݣ�" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "��ѡ����Ҫ"+dispose+"�����ݣ�" );
			return;
		}
		if(confirm("��ȷʵҪ"+dispose+"ѡ��������")){
			frm.action = "<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseDel";
			frm_delete.submit();
		}
    }
    
//-->
</SCRIPT>
