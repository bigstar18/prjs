<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>���</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0"
				width="100%">
				<tr>
					<td>
						<ec:table items="list"
							autoIncludeParameters="${empty param.autoInc}" var="obj"
							action="${basePath}/audit/baseAudit/baseAuditList.action"
							title="" minHeight="345" listWidth="100%" height="550px"
						    retrieveRowsCallback="limit" style="table-layout:fixed"
							sortRowsCallback="limit" filterRowsCallback="limit"
							csvFileName="�����б�.csv">
							<ec:row>
								<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
								<ec:column property="primary.applyType[like]" title="�������"
									width="22%" style="text-align:left;"
									editTemplate="ecs_t_applyTypeMap">
									<c:set var="applyTypeKey">
										<c:out value="${obj.applyType}"></c:out>
									</c:set>
									<a href="#" class="blank_a"
										onclick="doubleClick(${obj.id},'${obj.applyType}');"><font
										color="#880000">${auditOperateMap[applyTypeKey]}</font>
									</a>
          					  	</ec:column>
								<ec:column property="statusDiscribe[like]" title="����"  ellipsis="true" width="20%" style="text-align:left;" value="${obj.statusDiscribe}" />
								<ec:column property="proposer[like]" title="������"
									width="20%" style="text-align:left;"
									value="${obj.proposer}" />
								<ec:column property="modTime[=][timestemp]" title="����ʱ��"
									width="20%" style="text-align:left;"
									value="${datefn:formatdate(obj.modTime)}" />
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<!-- �༭�͹�����ʹ�õ���˷�ʽģ�� -->
		<textarea id="ecs_t_applyTypeMap" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.applyType[=][int]">
			<ec:options items="auditOperateMap" />
		</select>
	    </textarea>
	</body>
	<script type="text/javascript">
function doubleClick(id,type) {
	var url = "${basePath}/audit/"+type+"Audit/auditDetails.action?applyId="
			+ id;
	ecsideDialog(url, window, 600, 505);
}
function submitSelect() {
	myForm.submit();
}
</script>
</html>