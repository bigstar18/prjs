<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>

<script type="text/javascript" src="<%=serverPath %>/public/jslib/xtree.js"></script>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">	
    <title></title>
	<script language="JavaScript">
		function clickMenu(i) {
			var t2 = document.getElementById("bt2");
			var t3 = document.getElementById("bt3");
			if(i == 1) {
				t2.className = "top_b2";
				t3.className = "top_b1";
				
				window.parent.frames('leftFrame').location='<%=basePath %>/tradeManage/lookAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}';
				
			} else if(i == 2) {
				t3.className = "top_b2";
				t2.className = "top_b1";
				window.parent.frames('leftFrame').location='<%=basePath %>/tradeManage/lookHisAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}';
			}
		}
	</script>
  </head>
  
  <body >
  	<form id="frm_query" action="voucherBaseList.htm" method="post">
		<table width="50%" border="0" cellpadding="4" cellspacing="4">
			<tr>
		  		<td align="left" width="50"><button id="bt2" onclick="javascript:clickMenu(1);" style="width:100px;" class="top_b1">有效公告</button></td>
				<td align="left"><button id="bt3" onclick="javascript:clickMenu(2);" style="width:110px;" class="top_b1">过期公告</button></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
		   	</tr>
		</table>		
	</form>	
  </body>
</html>
