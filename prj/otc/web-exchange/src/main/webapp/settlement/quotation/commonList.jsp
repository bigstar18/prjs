<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/settleStatus.js'></script>
<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<style type="text/css">
</style>
	</head>
	<body>
		<div id="main_body">
			<table>
				<tr>
					<td width="150">
						<form name="frm"
							action="${basePath}/settlement/quotation/updateSettlement.action"
							method="post" target="quotation">
							<button class="btn_sec" onclick="settle1();" id="update">
								手工结算
							</button>
						</form>
					</td>
					<td width="75">
						<font style="font-size: 15">结算状态：</font>
					</td>
					<td>
						<font style="font-size: 15" id="status"></font>
					</td>
				</tr>
			</table>
		</div>
		<table id="tb1" border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td width="45%" height="560">
					<iframe name="clearStatus"
						src="${basePath}/settlement/clearStatus/list.action"
						frameborder="0" width="100%" height="100%"></iframe>
				</td>

				<td width="55%">
					<iframe name="quotation"
						src="${basePath}/settlement/quotation/list.action" frameborder="0"
						width="100%" height="100%"></iframe>
				</td>
			</tr>
		</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
<SCRIPT type="text/javascript">
	function settle1() {
		frm.submit();
	}
	setInterval("settleSta()",1000);
	function settleSta(){
		settleStatus.getSettleStatus(function(data){
			if(data == 1){
				document.getElementById('status').innerHTML = "已完成";
			} else if(data == 0){
				document.getElementById('status').innerHTML = "未执行";
			} else {
				document.getElementById('status').innerHTML = "结算中";
			}
		});
	}
</SCRIPT>
	</body>
</html>
