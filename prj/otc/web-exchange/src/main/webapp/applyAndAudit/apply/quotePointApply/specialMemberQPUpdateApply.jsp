<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�ر��Ա���۵��</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm" action="" method="post" targetType="hidden">
			<div class="div_scromidmin">
				<table border="0" width="90%" align="center">
					<tr height="30"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;��ϸ��Ϣ
							</div>
							<%@ include
								file="/commodity/quotePoint/specialMemberQuotePointUpdateTable.jsp"%>
						</td>
					</tr>
				<tr>
				<td>
			       <%@ include file="/applyAndAudit/public/applyedNote.jsp"%>
			</td>
			</tr>
			</table>
				</div>
			<%@ include file="/applyAndAudit/public/buttonList.jsp"%>
		</form>
	</body>
</html>
<script type="text/javascript">

function audit(aa) {
			var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("û���޸�����");
			return false;}
		frm.action = "${basePath}/apply/specialMemberQuotePointApply/update.action";
		frm.buttonClick.value = aa;	
		var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
			frm.submit();
	    }else{
           return false;
	    }
}
</script>
<%@ include file="/public/footInc.jsp"%>