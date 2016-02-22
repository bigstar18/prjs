<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>������ֵ�����б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0"
				width="100%">
				<tr>
					<td>
						<ec:table items="resultList"
							autoIncludeParameters="${empty param.autoInc}"
							var="stepDictionary"
							action="${basePath}/commodity/fundsLadder/list.action"
							minHeight="345" listWidth="100%" retrieveRowsCallback="limit"
							sortRowsCallback="limit" filterRowsCallback="limit"
							 csvFileName="�����б�.csv" style="table-layout:fixed">

							<ec:row recordKey="${stepDictionary['memberNo'] }">
								<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
								<ec:column property="memberNo[like]" title="����"
									style="text-align:left;" filterable="false">
									<c:set var="memberName">
										<c:out value="${stepDictionary.memberNo}"></c:out>
									</c:set>
									<a href="#" class="blank_a"
										onclick="return update('${stepDictionary['memberNo'] }');"><font
										color="#880000">${firmDisMap[memberName]}</font> </a>
								</ec:column>
								<c:forEach var="ta" begin="1" end="${total}" step="1">
									<c:set var="step" value="stepNo${ta}"></c:set>
									<c:forEach var="fundder" items="${ladderList}">
										<c:if test="${fundder.stepNo==ta}">
											<c:set var="note" value="${fundder.note}"></c:set>
										</c:if>
									</c:forEach>
									<ec:column property="stepNo${ta}[=]" title="${note}�ĳ�����ֵ����"
										style="text-align:left;"
										filterable="false" sortable="false" value="${stepDictionary[step]}%">
									</ec:column>
								</c:forEach>
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
		<!-- �༭�͹�����ʹ�õĻ�Ա����ģ�� -->
		<textarea id="ecs_t_memberNameType" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="memberNo[like]">
			<ec:options items="firmDisMap" />
		</select>
	    </textarea>
		<script type="text/javascript">

function update(id) {
	var url = "${basePath}/commodity/fundsLadder/forwardUpdate.action?memberNo="
			+ id;
	ecsideDialog(url, "", 620, 235);
}
function select1() {
	frm.submit();
}
</script>
	</body>
</html>