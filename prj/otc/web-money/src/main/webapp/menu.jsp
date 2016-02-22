<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.trade.bank.util.Tool"%>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%
	String menuid = (String)request.getParameter("menuid");
	if(menuid==null||menuid.equals("")||menuid.equals("null")){
		menuid="0";
	}

%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
    <title></title>
	<script language="javascript" src="public/jstools/common.js"></script>
	<script language="JavaScript">
		function clickMenu(i) {
			var t1 = document.getElementById("bt1");
			var t2 = document.getElementById("bt2");
			var t3 = document.getElementById("bt3");
			if(i == 0) {
				window.parent.frames('list').location='Money.jsp?fid=<%=fid%>&sid=<%=sid%>';
			} else if(i == 1) {
				window.parent.frames('list').location='list.jsp?fid=<%=fid%>&sid=<%=sid%>';
			} else if(i == 2) {
				window.parent.frames('list').location='firmBalance.jsp?fid=<%=fid%>&sid=<%=sid%>';
			}
			window.parent.frames('menu').location='menu.jsp?fid=<%=fid%>&sid=<%=sid%>&menuid='+i;
		}
		function setMenufocus(i) {
			var t1 = document.getElementById("bt1");
			var t2 = document.getElementById("bt2");
			var t3 = document.getElementById("bt3");
			if(i == 0) {
				t1.className = "top_b2";
				t2.className = "top_b1";
				t3.className = "top_b1";
			} else if(i == 1) {
				t2.className = "top_b2";
				t1.className = "top_b1";
				t3.className = "top_b1";
			} else if(i == 2) {
				t3.className = "top_b2";
				t1.className = "top_b1";
				t2.className = "top_b1";
			}
		}
		function logOut() {
			window.parent.location = "index.jsp";
		}
		function changePassword(){
			var result = window.showModalDialog("changePassword.jsp?fid=<%=fid%>&sid=<%=sid%>","","dialogWidth=280px; dialogHeight=200px; status=yes;scroll=yes;help=no;");
		}
	</script>
  </head>
  
  <body oncontextmenu="return false" onload="setMenufocus(<%=menuid%>)">
  	<form id="frm_query" action="voucherBaseList.htm" method="post">
		<table width="90%" border="0" cellpadding="4" cellspacing="4">
			<tr>
		  		<td width="100" align="center"><button id="bt1" onclick="javascript:clickMenu(0);" style="width:100px;" class="top_b2">出入金操作</button></td>
				<td width="100" align="center"><button id="bt2" onclick="javascript:clickMenu(1);" style="width:100px;" class="top_b1">出入金查询</button></td>
				<td width="100" align="center"><button id="bt3" onclick="javascript:clickMenu(2);" style="width:100px;" class="top_b1">银行余额查询</button></td>
				
				  <td width='100' align="right">
				  <button class="top_b1" style="width:120px;" id=chpwd onclick="changePassword();">修改<%=marketpwd%></button>
			  </td>
			  <td>&nbsp;
			  </td>
		   	</tr>
		</table>		
	</form>	
  </body>
</html>
