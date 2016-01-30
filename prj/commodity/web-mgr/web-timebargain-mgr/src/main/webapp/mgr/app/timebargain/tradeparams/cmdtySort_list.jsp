<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<base target="_self" />
<title>
品种分类列表
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    window.location = "${basePath}/timebargain/tradeparams/addBCategoryforward.action?crud=create";
}

function deleteList() {

	var url = "${basePath}/timebargain/tradeparams/deleteBCategory.action"
	updateRMIEcside(ec.ids,url);
}
function detailForward(sortID) {
	var url = "${basePath}/timebargain/tradeparams/updateBCategoryforward.action?crud=update&entity.sortID=" + sortID;
	window.location = url;
	
}
// -->
</script>
</head>
<body>
<div align="center">
	<table>
	  <tr><td>
	  <ec:table items="pageInfo.result" var="cmdtySort"
			action="${basePath}/timebargain/tradeparams/addBCategoryList.action"
			autoIncludeParameters="${empty param.autoInc}"
			toolbarContent="save add del|extend"
			minHeight="240"
			width="450"
			title="品种分类">
			<ec:row>
				<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${cmdtySort.sortID}" width="30" viewsAllowed="html" /> 
				<ec:column property="sortName" title="品种分类名称" width="415" style="text-align:center">
				    <a href="#" class="blank_a" onclick="detailForward('${cmdtySort.sortID}')" title="修改">
				    	<font color="#880000">${cmdtySort.sortName}</font>
				    </a>  
				</ec:column>
			</ec:row>
		</ec:table>
	</td></tr>
	</table>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
		<tr>
			<td align="center">
				<rightButton:rightButton name="添加" onclick="add_onclick()" className="btn_sec" action="/timebargain/tradeparams/addBCategoryforward.action" id="add"></rightButton:rightButton>
				&nbsp;&nbsp;
				<rightButton:rightButton name="删除" onclick="deleteList()" className="btn_sec" action="/timebargain/tradeparams/deleteBCategory.action" id="delete"></rightButton:rightButton>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
