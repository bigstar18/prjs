<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@page import="util.UtilCommon"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			int count=8;
		String a=UtilCommon.get("a1","b","a",true,count);
	String str="["+a.substring(0,a.length()-1)+"]";
	System.out.println(UtilCommon.c);

%>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - checkbox</TITLE>
	<link rel="stylesheet" href="<%=basePath %>tree1/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath %>tree1/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath %>tree1/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>tree1/js/jquery.ztree.core-3.0.js"></script>
	<script type="text/javascript" src="<%=basePath %>tree1/js/jquery.ztree.excheck-3.0.js"></script>
	<!--
	<script type="text/javascript" src="<%=basePath %>tree1/js/jquery.ztree.exedit-3.0.js"></script>
	-->
	<SCRIPT LANGUAGE="JavaScript">
		<!--
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =<%=str%>;
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = type;
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
		function aa(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			node=nodes[i];
			alert(node.id);
		}
		document.getElementById("spa").innerText=nodes;
		var node = treeObj.getNodeByParam("id", "c10", null);
		//var node = treeObj.getNodeByTId("c10");
		alert(node);
		treeObj.checkNode(node,true,true);
		}
		
		//-->
	</SCRIPT>
</HEAD>

<BODY>
<h1>checkbox ¹´Ñ¡²Ù×÷</h1>
		<ul id="treeDemo" class="ztree"></ul>
		<button onclick="aa()">²éÑ¯</button>
		<span id="spa"></span>
</BODY>
</HTML>

