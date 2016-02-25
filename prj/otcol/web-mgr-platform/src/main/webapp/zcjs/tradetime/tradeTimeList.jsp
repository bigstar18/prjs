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
			<legend>交易节列表</legend>
			
		
	  <%@ include file="tradeTimeTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <!-- 
			  <button class="lgrbtn" type="button" onclick="settingSame();">同步</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   -->
			    <button class="lgrbtn" type="button" onclick="del();">删除</button>
			 
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
		alert ( "没有可以操作的数据！" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "请选择需要操作的数据！" );
		return false;
		}
		if(confirm("您确实要操作选中数据吗？"))
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
		if(confirm("确定同步吗?")){
			frm.action = "<%=basePath%>/tradeTimeController.zcjs?funcflg=tradeTimeList";
			frm.submit();
		}
	}

</SCRIPT>