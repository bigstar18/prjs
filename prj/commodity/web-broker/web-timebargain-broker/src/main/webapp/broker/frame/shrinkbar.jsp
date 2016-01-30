<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>框架</title>
<meta http-equiv=Content-Type content=text/html; charset=GBK>
<script language="javascript">
function switchSysBar(){
	if(parent.document.getElementById('middle').cols == "185,10,*")
	{
		parent.document.getElementById('middle').cols = "0,10,*";
		document.getElementById('leftbar').style.display = "";
	}
	else
	{
		parent.document.getElementById('middle').cols = "185,10,*";
		document.getElementById('leftbar').style.display = "none";
	}
}
</script>
</head>
<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 >
<center>
<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>
<tr>
<td width="1"><img src="${skinPath }/image/frame/shrinkbar/bar_bg.gif" width=1 height=1></td>
<td id="leftbar" style="display:none"><a href="javascript:void(0);" onClick="switchSysBar()"><img src="${skinPath }/image/frame/shrinkbar/bar_right.gif" width="9" height="90" alt='展开左侧菜单' border="0"></a></td>
<td id="rightbar"><a href="javascript:void(0);" onClick="switchSysBar()"><img src="${skinPath }/image/frame/shrinkbar/bar_left.gif" width="9" height="90" alt='隐藏左侧菜单' border="0"></a></td>
</tr>
</table>
</center>
</body>
</html>


