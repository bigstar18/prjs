<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>breedController.zcjs?funcflg=list" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>Ʒ���б�</legend>
			
		
	  <%@ include file="breedTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td align="center"><div >
			  <button class="lgrbtn" type="button" onclick="add();">���</button>&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="refurbish();">��ֿ�ͬ��ˢ��</button>
			</div></td>
			
        </tr>
    </table>
	</fieldset>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function fmod(breedId){
		frm.action = "<%=basePath%>breedController.zcjs?funcflg=updateForward&breedId="+breedId;
		frm.submit();
	}

	function add(){
		frm.action="<%=basePath%>breedController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function refurbish(){
		
		refurbishRec(frm,tableList,'delCheck');
	}
	
	function refurbishRec(frm_delete,tableList,checkName)
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
		frm.action="<%=basePath%>breedController.zcjs?funcflg=refurbish";
		frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>