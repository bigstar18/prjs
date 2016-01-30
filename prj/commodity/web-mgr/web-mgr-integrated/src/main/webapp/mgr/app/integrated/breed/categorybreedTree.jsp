<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<TITLE></TITLE>
	<meta http-equiv="content-type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="<%=skinPath%>/css/xtree.css" type="text/css"/>
	<link rel="stylesheet" href="${basePath }/mgr/app/integrated/xtree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${basePath }/mgr/app/integrated/xtree/css/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${basePath }/mgr/app/integrated/xtree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${basePath }/mgr/app/integrated/xtree/js/jquery.ztree.core-3.0.js"></script>
	<script type="text/javascript" src="${basePath }/mgr/app/integrated/xtree/js/jquery.ztree.excheck-3.0.js"></script>
	<script type="text/javascript" src="${basePath }/mgr/app/integrated/xtree/js/jquery.ztree.exedit-3.0.js"></script>
	<SCRIPT type="text/javascript">
		//此处引用的是www.baby666.cn下的ztree下的exedit下的edit_super.html
		<!--
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeClick: beforeClick,
				onClick:onClick
			}
		};
		var zNodes =${json};
		var log, className = "dark";
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}
		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
			alert;
			if(treeNode.type=="leaf"){
			var addStr = "<button type='button' class='add' id='addBtn_" + treeNode.id
				+ "' title='添加品名' onfocus='this.blur();'></button>";
			sObj.append(addStr);
			}
			var btn = $("#addBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){
				frm.action="<%=basePath%>/category/breed/addForwardBreed.action?categoryId="+treeNode.id;
				frm.submit();
				return false;
			});
		};
		function dele(id,name){
			if(confirm("你确定要删除（"+name+"）吗？")){
				frm.action="${basePath}/category/breed/deleteBreed.action?entity.breedId="+id;
				frm.submit();
				a=1;
				}
		}
		var a=0;
		function beforeClick(treeId, treeNode, clickFlag) {
			if(a==1||treeNode.type!="pro"){
				return false;
			}else{
				return true;
			}
		};
		function onClick(event, treeId, treeNode, clickFlag){
			if(treeNode.type=="pro"){
				frm.action="<%=basePath%>/category/breed/updateForwardBreed.action?entity.breedId="+treeNode.id;
				frm.submit();
			}
		}
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();
		};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			
		});
		function stop(){
			return false;
			}
		document.oncontextmenu=stop;
		//-->
	</SCRIPT>
</head>

<body style="overflow-y:auto">
<form target="workspace"  name="frm" method="post">
		</form>
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</body>
</html>




