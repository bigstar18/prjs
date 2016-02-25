<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<%@ include file="/timebargain/common/ecside_head.jsp"%>
		<title>default</title>
		<script type="text/javascript">
<!--
	function outline(){
		if (document.getElementById("traderID").value == "") {
			alert("交易员代码不能为空！");
			return false;
		} else {
			if (confirm("您确定要使此交易员下线吗？")) {
				
				document.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=outLine&traderID="/>" + document.getElementById("traderID").value;
			}
		}
	}
	
	function firmOutline(){
		if (document.getElementById("firmID").value == "") {
			alert("交易商代码不能为空！");
			return false;
		} else {
			if (confirm("您确定要使此交易商所属交易员下线吗？")) {
				
				document.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=outLine&firmID="/>" + document.getElementById("firmID").value;
			}
		}
		document.location.href = "<c:url value='/timebargain/order/orders.do?funcflg=trader_list'/>"
	}
// -->
</script>
	</head>
	<body>
		<table width="100%">
			<tr>
				<td>
					<ec:table items="traderList" var="trader"
						action="${pageContext.request.contextPath}/timebargain/order/orders.do?funcflg=trader_list"
						xlsFileName="TraderList.xls" csvFileName="TraderList.csv"
						showPrint="true" listWidth="100%" rowsDisplayed="20"
						minHeight="300" title="">
						<ec:row>
							<ec:column property="traderID" title="交易员代码" width="100"
								style="text-align:center;" />
							<ec:column property="loginTime" title="登录时间"
								style="text-align:center;" width="250" />
							<ec:column property="loginIP" title="登录IP"
								style="text-align:center;" width="250" />
						</ec:row>
					</ec:table>
				</td>
			</tr>

			<tr>
				<td>
					交易员代码：
					<input type="text" id="traderID" value="" size="10" />
					<html:button property="save" styleClass="button"
						onclick="javascript:return outline();">
				强制下线
		</html:button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 交易商代码：
					<input type="text" id="firmID" value="" size="10" />
					<html:button property="save" styleClass="button"
						onclick="javascript:return firmOutline();">
				所属交易员强制下线
		</html:button>
				</td>


			</tr>

		</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
	</textarea>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
