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
			alert("����Ա���벻��Ϊ�գ�");
			return false;
		} else {
			if (confirm("��ȷ��Ҫʹ�˽���Ա������")) {
				
				document.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=outLine&traderID="/>" + document.getElementById("traderID").value;
			}
		}
	}
	
	function firmOutline(){
		if (document.getElementById("firmID").value == "") {
			alert("�����̴��벻��Ϊ�գ�");
			return false;
		} else {
			if (confirm("��ȷ��Ҫʹ�˽�������������Ա������")) {
				
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
							<ec:column property="traderID" title="����Ա����" width="100"
								style="text-align:center;" />
							<ec:column property="loginTime" title="��¼ʱ��"
								style="text-align:center;" width="250" />
							<ec:column property="loginIP" title="��¼IP"
								style="text-align:center;" width="250" />
						</ec:row>
					</ec:table>
				</td>
			</tr>

			<tr>
				<td>
					����Ա���룺
					<input type="text" id="traderID" value="" size="10" />
					<html:button property="save" styleClass="button"
						onclick="javascript:return outline();">
				ǿ������
		</html:button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; �����̴��룺
					<input type="text" id="firmID" value="" size="10" />
					<html:button property="save" styleClass="button"
						onclick="javascript:return firmOutline();">
				��������Աǿ������
		</html:button>
				</td>


			</tr>

		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
	</textarea>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
