<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	if("do".equals(request.getParameter("submitFlag"))){
		String funID = request.getParameter("funID");
		if(funID == null){
			funID = "";
		}
		if(funID.trim().length()>0){
%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				if(confirm('<%="��ȷ������������ˮ��Ϊ["+funID+"]��" %>')){
					window.returnValue="<%=funID%>";
					window.close();
				}
				//-->
			</SCRIPT>
<%
		}else{
%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('������ˮ�Ų���Ϊ��');
				//-->
			</SCRIPT>
<%
		}
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>����������������ˮ��</title>
  </head>
  
  <body style="overflow-y:hidden">
  	<form id="frm" action="" method="post">
	<table width="100%">
	<tr>
	<td align="center" width="90%">
		<table border="0" cellspacing="0" cellpadding="0" width="80%" align="center" height="35" class="st_bor">
			<tr height="35">
				<td align="right">������ˮ�ţ�&nbsp;</td>
				<td align="left">
					<input name="funID" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
				</td>
			</tr>
		</table>
	</td>
	</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
		<tr height="35">					
			<td align="center" colspan=2>
				<button  class="btn_sec" onclick="doAdd();">ȷ��</button>&nbsp;
				<button class="btn_cz" onclick="window.close();">ȡ��</button>&nbsp;
				<input type=hidden name=submitFlag value="">
			</td>
		</tr>
	</table>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function doAdd() {
	if(!ismyStr(frm.funID.value)){
		alert("��������Ч��������ˮ��");
	}else if(frm.funID.value.trim().length>50){
		alert("������ˮ���50λ");
	}else if(frm.funID.value.trim().length<=0){
		alert("������ˮ�Ų���Ϊ��");
	}else{
		frm.submitFlag.value = "do";
		frm.submit();
	}
}
function ismyStr(str){
	var patrn=/^\w*$/;
	if (patrn.exec(str)) {
		return true ;
	}
	return false ;
}
//-->
</SCRIPT>