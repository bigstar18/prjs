<%@ page contentType="text/html;charset=GBK" %>
<html>
	<head>
		<%@ include file="public/headInc.jsp" %>
		<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
		<script language="javascript" src="<%=basePath%>/public/jstools/frameCtrl.js"></script>
		<script language="javascript" src="<%=basePath%>/public/jstools/excelTools.js"></script>
		<title>财务系统</title>
		<style type="text/css">
		<!--
		.top_bt {
			font-size: 14px;
			color: #FFFFFF;
			text-decoration: none;
			font-weight: bold;
			line-height: 30px;
		}
		-->
		</style>
	</head>
<script>
function logout()
{
	parent.window.location="<%=basePath%>/userLogout.spr";
}
function changePwd(){
	openDialog("<%=basePath%>/user/changePwd.jsp","_blank",500,320);
}
function goMain()
{
	parent.window.location="<%=basePath%>/index.jsp";
}
function printMainTable()
{
	var mainFrame = oMainFrame();
	var tablePrint = mainFrame.tableList;
	if(tablePrint)
		pagePrint(tablePrint);
	else
		alert('此打印功能只用于结果列表，主窗口中未发现结果列表！');
}
function exportMainTable()
{
	var mainFrame = oMainFrame();
	var tablePrint = mainFrame.tableList;
	if(tablePrint)
		exportToExcel(mainFrame.tableList);
	else
		alert('此导出功能只用于结果列表，主窗口中未发现结果列表！');
}
</script>
	<body class="topframe">
		<div style="width: 100%; height: 55px; overflow: hidden;">
			<div style="width: 100%; height: 50px; overflow: hidden; background-color: #6A7EA8;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%"  height="100%">
					<tr>
					    <td width="205" bgcolor="6B7EA9"><img src="<%=basePath + "/skin/" + userSkinName%>/images/top_bt.gif" width="205" height="38" onclick="goMain()" style="cursor:hand"></td>
					    <td valign="bottom" bgcolor="6B7EA9"><span class="top_bt">财务</span></td>
					    <td width="80" valign="bottom" bgcolor="6B7EA9"><div align="center"><img src="<%=basePath + "/skin/" + userSkinName%>/images/print.gif" width="78" height="19" border="0" onclick="printMainTable();" style="cursor:hand" title="打印查询结果列表"></div></td>
					    <td width="80" valign="bottom" bgcolor="6B7EA9"><div align="center"><img src="<%=basePath + "/skin/" + userSkinName%>/images/export.gif" width="78" height="19" border="0" onclick="exportMainTable();" style="cursor:hand" title="导出查询结果列表到Excel"></div></td>
					    <td width="69" valign="bottom" bgcolor="6B7EA9"><div align="center"><img src="<%=basePath + "/skin/" + userSkinName%>/images/modify.gif" width="69" height="19" border="0" onclick="changePwd();" style="cursor:hand" title="修改用户口令"></div></td>
						<td width="44" valign="bottom" bgcolor="6B7EA9"><div align="center"><img src="<%=basePath + "/skin/" + userSkinName%>/images/out.gif" width="44" height="19" border="0" onclick="logout();" style="cursor:hand" title="退出本系统"></div></td>
					</tr>
				</table>
			</div>
			<div style="width: 100%; height: 1px; overflow: hidden; background-color: #7F8EAD;">
			</div>
			<div style="widht: 100%; height: 4px; overflow: hidden; background-color: #929DB4;">
			</div>
		</div>
	</body>
</html>