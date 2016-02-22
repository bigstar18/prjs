<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/choSkin.jsp"%>
<link rel="stylesheet" href="<%=skinPath%>/mainstyle.css" type="text/css" />
<HTML>
<HEAD>
	<TITLE> 客户归属</TITLE>
	<link rel="stylesheet" href="<%=basePath %>/zTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath %>/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath %>/zTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/zTree/js/jquery.ztree.core-3.0.js"></script>
	<script type="text/javascript" src="<%=basePath %>/zTree/js/jquery.ztree.excheck-3.0.js"></script>
	<!--
	<script type="text/javascript" src="<%=basePath %>/zTree/js/jquery.ztree.exedit-3.0.js"></script>
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
		
		var zNodes =${treeString};  
		var code;
		
		function setCheck() {
			//alert('enter');
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			if($("#isRelated").attr("checked")){
				type = { "Y" : "s", "N" : "s" };
			}else{
				type = { "Y" : "", "N" : "" };
			}
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
			if(""=='${isRelated}'||'${isRelated}'=='true'){
				document.getElementById("isRelated").checked=true;
			}else{
				document.getElementById("isRelated").checked=false;
			}
			initReturn();
		});
	</SCRIPT>
</HEAD>

<BODY>
		<input type="checkbox" id="isRelated" name="isRelated" onchange="setCheck()">是否关联子项
		<div style="text-align:center;">
		<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="400"
				align="center">
				<tr><td height="5">&nbsp;</td></tr>
				<tr height="35">
					<td>
						<div align="center">
							<button class="btn_sec" onClick="sub()">
								确定
							</button>
					</td>
					<td>
						<div align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
							</button>
					</td>
				</tr>
			</table>
		</div>
		
</BODY>
</HTML>
<script type="text/javascript">
	function sub(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var memberNos="",orgNos="",broNos="",memberNames="",orgNames="",broNames="";
		for(var i=0;i<nodes.length;i++){
			node=nodes[i];
			var id=node.id;
			var newNode=id.substring(0,id.length-2);
			if(node.title=='M'){
				memberNos+="'"+newNode+"',";
				memberNames+=node.name+',';
			}
			else if(node.title=='O'){
				orgNos+="'"+newNode+"',";
				orgNames+=node.name+',';
			}
			else if(node.title=='B'){
				broNos+="'"+newNode+"',";
				broNames+=node.name+',';
			}
		}
		if(memberNos.length>0){
			memberNos=memberNos.substring(0,memberNos.length-1);
		}
		if(orgNos.length>0){
			orgNos=orgNos.substring(0,orgNos.length-1);
		}
		if(broNos.length>0){
			broNos=broNos.substring(0,broNos.length-1);
		}
		var strName=memberNames+orgNames+broNames;
		if(strName.length>0){
			strName=strName.substring(0,strName.length-1);
		}
		
		//对父页面的赋值操作
		var parWin = window.dialogArguments;
		parWin.document.getElementById('brokerageIds').value = broNos;
		parWin.document.getElementById('memberInfoIds').value = memberNos;
		parWin.document.getElementById('organizationIds').value = orgNos;
		var Related=document.getElementById('isRelated');
		var isRelated="";
		if(Related.checked){
			isRelated='true';
		}else{
			isRelated='false';
		}
		parWin.document.getElementById('isRelated').value = isRelated;
		parWin.document.getElementById('memberNames').value = strName;
		window.close();
	}
	function initReturn(){
		
		var parWin = window.dialogArguments;
		var brokerageIds = parWin.document.getElementById('brokerageIds').value;
		var brokerageId = brokerageIds.split(",");
		
		var organizationIds = parWin.document.getElementById('organizationIds').value;
		var organizationId = organizationIds.split(",");
		
		var memberInfoIds1 = parWin.document.getElementById('memberInfoIds').value;
		var memberInfoId = memberInfoIds1.split(",");
		 
		var ids=",";
		
		if (memberInfoIds1!="") {
			for ( var j = 0; j < memberInfoId.length; j++) {
				memberInfoId[j] = memberInfoId[j].substring(1,
						memberInfoId[j].length - 1);
				memberInfoId[j]=memberInfoId[j]+"_M";
				ids+=memberInfoId[j]+',';
			}
		}
		if (brokerageIds!="") {
			for ( var j = 0; j < brokerageId.length; j++) {
				brokerageId[j] = brokerageId[j].substring(1,
						brokerageId[j].length - 1);
				brokerageId[j]=brokerageId[j]+"_B";
				ids+=brokerageId[j]+',';
			}
		}
		if (organizationIds!="") {
			for ( var j = 0; j < organizationId.length; j++) {
				organizationId[j] = organizationId[j].substring(1,
						organizationId[j].length - 1);
				organizationId[j]=organizationId[j]+"_O";
				ids+=organizationId[j]+',';
			}
		}
		
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		if(nodes!=null&&nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				var node=nodes[i];
				var id=node.id;
				if(ids.indexOf(','+id+',')>=0){
					treeObj.checkNode(node,true,false);
				}
			}
			
		}
	}
</script>
