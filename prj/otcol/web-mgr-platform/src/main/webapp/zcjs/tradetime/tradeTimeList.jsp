<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>tradeTimeController.zcjs?funcflg=tradeTimeList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>���׽��б�</legend>
			
		
	  <%@ include file="tradeTimeTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">���</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <!-- 
			  <button class="lgrbtn" type="button" onclick="settingSame();">ͬ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   -->
			    <button class="lgrbtn" type="button" onclick="del();">ɾ��</button>
			 
			</div></td>
        </tr>
    </table>
	</fieldset>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function fmod(serialNumber){
		frm.action = "<%=basePath%>tradeTimeController.zcjs?funcflg=tradeTimeView&serialNumber="+serialNumber;
		frm.submit();
	}

	function add(){
		frm.action="<%=basePath%>tradeTimeController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function del(){
		
		deleteRec(frm,tableList,'delCheck');
	}
	
	function deleteRec(frm_delete,tableList,checkName)
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
		frm.action="<%=basePath%>tradeTimeController.zcjs?funcflg=delForward";
		frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	
	function settingSame() {
		if(confirm("ȷ��ͬ����?")){
			frm.action = "<%=basePath%>/tradeTimeController.zcjs?funcflg=tradeTimeList";
			frm.submit();
		}
	}

</SCRIPT>