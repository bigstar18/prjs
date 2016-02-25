<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html>
  <head>
    <title>配对持仓信息</title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  <base target="_self" /><!--这句话非常重要，只要加上才能保证在弹出窗口中调用服务端代码而不会再弹出一个新窗口-->
  	<form name="frm" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		
	<fieldset width="95%">
	  <legend class="common">配对持仓信息</legend>
	  
	  <%@ include file="settleViewStockTable.jsp"%>
	  
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="30">
          <td align="center">
            <div >
			  &nbsp;&nbsp;&nbsp;<button class="lgrbtn" type="button" onclick="doReturn();">关闭</button>
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