<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>sortController.zcjs?funcflg=getTableList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>�����б�</legend>
			
		
	  <%@ include file="sortTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td align="center"><div >
			  <button class="lgrbtn" type="button" onclick="add();">���</button>&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="delete1();">ɾ��</button>
			</div></td>
			
        </tr>
    </table>
	</fieldset>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function fmod(sortId){
		frm.action = "<%=basePath%>sortController.zcjs?funcflg=updateForward&sortId="+sortId;
		frm.submit();
	}

	function add(){
		frm.action="<%=basePath%>sortController.zcjs?funcflg=insertForward";
		frm.submit();
	}
	 function delete1(){
		
		deleteN(frm,tableList,'delCheck');
	}
	
	function deleteN(frm_delete,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
		{
		frm.action="<%=basePath%>sortController.zcjs?funcflg=delete";
		frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>