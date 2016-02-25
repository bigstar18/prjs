<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
    <title><%=TITLE%></title>
  </head>
  
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityList" method="post">
  		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>品种查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">品种编号：</td>
					<td align="left">
						<input id="id" name="_id[like]" value="<c:out value='${oldParams["id[like]"]}'/>" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">品种名称：</td>
					<td align="left">
						<input id="name" name="_c.name[like]" value="<c:out value='${oldParams["c.name[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
						<!-- <input type="reset" class="smlbtn" value="重置" /> -->
					</td>
				</tr>
			</table>
		</fieldset>
	  
	 <%@ include file="commodityTable.jsp"%>
	 
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
            <!-- 
              <button class="smlbtn" type="button" onclick="fadd()">添加</button>&nbsp;&nbsp;
             -->
  			  <button class="smlbtn" type="button" onclick="disposeRec(frm,tableList,'delCheck','同步')">同步</button>
            </div></td>
        </tr>
    </table>
	<INPUT TYPE="hidden" NAME="tag" value="">
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery(){
		frm.tag.value = "query";
		frm.submit();
	}
	function fmod(vid){
		frm.action = "<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityUpdateForward&id="+vid;
		frm.submit();
	}
	function fadd(){
		frm.action = "<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityAddForward";
		frm.submit();
	}
	function resetForm(){
		frm.id.value = "";
		frm.name.value = "";
		frm.submit();
	}

	function disposeRec(frm_delete,tableList,checkName,dispose){
		if(isSelNothing(tableList,checkName) == -1){
			alert ( "没有可以"+dispose+"的数据！" );
			return;
		}
		if(isSelNothing(tableList,checkName)){
			alert ( "请选择需要"+dispose+"的数据！" );
			return;
		}
		if(confirm("您确实要"+dispose+"选中数据吗？")){
			frm.action = "<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=refurbish";
			frm_delete.submit();
		}
	}
//-->
</SCRIPT>
