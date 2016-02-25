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
			<legend>品种列表</legend>
			
		
	  <%@ include file="breedTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td align="center"><div >
			  <button class="lgrbtn" type="button" onclick="add();">添加</button>&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="refurbish();">与仓库同步刷新</button>
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