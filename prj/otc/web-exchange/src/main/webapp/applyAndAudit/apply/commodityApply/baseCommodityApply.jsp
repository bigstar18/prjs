<%@ page contentType="text/html;charset=GBK"%><%@page import="gnnt.MEBS.commodity.model.Commodity"%>

<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head>
	<title>商品信息</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="myForm" action=""
			method="post" targetType="hidden">
			<div class="div_scromax">
			<table border="0" width="90%" align="center">
				<tr height="30"></tr>
				<tr>
					<td>
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;详细信息</div>
				<%@ include file="/commodity/baseInfo/baseInfoTable.jsp"%>
				</td>
				</tr>
				<tr>
				<td>
				<%@ include file="/applyAndAudit/public/applyedNote.jsp"%>
				</td>
				</tr>
				</table>
				</div>
				<% 
					request.setAttribute("isOpenOperationButton","N");	
				%>
				
				<%@ include file="/applyAndAudit/public/buttonList.jsp"%>
		</form>
	</body>
</html>
<script language="javascript">
	function audit(aa){
		var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
		myForm.action="${basePath}/commodity/baseInfo/update.action";
		myForm.buttonClick.value=aa;		
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
			myForm.submit();
	    }else{
           return false;
	    }
	}
</script>
<%@ include file="/public/footInc.jsp"%>