<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html>
  <head>
    <title>操作记录</title>
      <base target="_self" /><!--这句话非常重要，只要加上才能保证在弹出窗口中调用服务端代码而不会再弹出一个新窗口-->
  </head>
  <body onload="initBody('${returnRefresh}')">
	<form name="frm" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		
	<fieldset width="95%">
			<legend>具体操作列表</legend>
			
		
	  <%@ include file="settleViewOperatorTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="100%">
	    <tr height="30">
          <td align="center">
            <div >
			  <button class="lgrbtn" type="button" onclick="doReturn();">关闭</button>
			</div> 
	      </td>	
        </tr>
    </table>
	</fieldset>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function doReturn(){
		window.close();
	}


</Script>	