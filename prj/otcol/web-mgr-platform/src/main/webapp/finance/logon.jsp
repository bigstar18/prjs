<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.base.util.SysData'%>
<html>  
	<head>   
		<%@ page contentType="text/html;charset=GBK" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/finance";
		String userSkinName = "default";
		String skinPath = basePath + "/skin/" + userSkinName;
		pageContext.setAttribute("validateCode",SysData.getConfig( "validateCode" ));
		%>
			<base href="<%=basePath%>">
			<meta http-equiv="Content-Type" content="text/html; charset=GBK">
			<script language="javascript" src="<%=basePath%>/public/jstools/formInit.js"></script>
			<script language="javascript">
				var basePath = "<%=basePath%>";
				var skinPath = "<%=skinPath%>";
				<c:if test='${not empty resultMsg}'>
					alert('<c:out value="${resultMsg}"/>');
				</c:if>
			</script>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>财务系统</title>
		<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
		<script>
		<!--
			function logon()
			{
				if (!logonForm.userId.value||logonForm.userId.value==""
				||!logonForm.password.value||logonForm.password.value==""
				<c:if test='${validateCode=="yes"}'>
				||!logonForm.randNumInput.value||logonForm.randNumInput.value==""
				</c:if>
				){
					alert("请输入用户名、密码或验证码！");
					return;
				}
				logonForm.submit();
			}
			function keyEnter()
			{
				if ( event.keyCode == 13 )
					logon();
			}
			-->
		</script>
        <style type="text/css">
        	<!--
        	.style1 {color: #224d8d}
        	a:link {
				color: #224d8d;
        	}
        	a:hover {
				color: #0033FF;
        	}
        	body {
        	margin-top: 130px;
				background-image: url(<%=basePath%>/skin/default/images/bg.gif);
        	}
        	body,td,th {
				font-size: 12px;
        	}
			button.smlbtn
            {
            	background-image: url(<%=basePath%>/skin/default/images/btn_sml.gif); 
            	background-repeat: no-repeat;
            	background-position: middle center;
            	background-color: transparent; 
            	border-left: medium none; 
            	border-right: medium none; 
            	border-top: medium none; 
            	border-bottom: medium none; 
            	padding-top: 3px;
            	font-size: 9pt; 
            	font-family: simsun; 
            	width: 55px;
            	height: 20px; 
            	overflow: hidden; 
            	cursor: hand; 
            	color: #333333; 
            	text-align: center;
            }
        -->
        </style>
	</head>

<body bgcolor="#FFFFFF" leftmargin="0" marginwidth="0">
	<form id="logonForm" name="logonForm" action="<%=basePath%>/userLogon.spr" method="post">
		<table width="582" height="333" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
			<tr>
				<td><img src="<%=skinPath%>/images/log_top1.gif" width="582" height="91" alt=""></td>
			</tr>
			<tr>
				<td height="222" valign="top" align="center" background="<%=skinPath%>/images/log_top2.gif">
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><table width="50%"  border="0" align="center" cellpadding="0" cellspacing="4">
									<tr height="40">
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td width="28%">用户名：</td>
										<td width="72%"><input type="text" name="userId" reqfv="required;用户名" value="<c:out value='${userId}'/>" style="width:150px" onkeydown="keyEnter();"></td>
									</tr>
									<tr>
										<td>密　码：</td>
										<td><input type="password" name="password" reqfv="required;密码" style="width:150px" onkeydown="keyEnter();"></td>
									</tr>
									<c:if test='${validateCode=="yes"}'>
									<tr>
							            <td>验证码：</td>
							            <td><input name="randNumInput" type="text" reqfv="required;验证码" style="width=88px" onkeydown="keyEnter();"><img src="<%=basePath%>/image.jsp" align="absmiddle"></td>
							        </tr>
							        </c:if>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
							</table></td>
						</tr>
						<tr>
                            <td valign="bottom" align="center" colspan=2>&nbsp;
                              <button class="smlbtn" onclick="logon()">登录</button>&nbsp;&nbsp;&nbsp;
                              <button class="smlbtn" type="reset">重置</button>
                            </td>
                          </tr>
					</table></td>
			</tr>
			<tr>
				<td>
					<img src="<%=skinPath%>/images/log_top3.gif" width="582" height="20" alt=""></td>
			</tr>
		</table>
	</form>
</body>
</html>
