<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html>
  <head>
    <title>������¼</title>
      <base target="_self" /><!--��仰�ǳ���Ҫ��ֻҪ���ϲ��ܱ�֤�ڵ��������е��÷���˴���������ٵ���һ���´���-->
  </head>
  <body onload="initBody('${returnRefresh}')">
	<form name="frm" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		
	<fieldset width="95%">
			<legend>��������б�</legend>
			
		
	  <%@ include file="settleViewOperatorTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="100%">
	    <tr height="30">
          <td align="center">
            <div >
			  <button class="lgrbtn" type="button" onclick="doReturn();">�ر�</button>
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