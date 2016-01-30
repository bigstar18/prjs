<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>�ύ�б�</title>
	</head>
	<body>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0"
				width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="apply"
							action="${basePath}/audit/baseAudit/waitAuditList.action?isQueryDB=true"
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="apply.xls" csvFileName="apply.csv" showPrint="true"
							listWidth="100%" minHeight="345"  style="table-layout:fixed;">
							<ec:row>
								<ec:column width="3%" property="_0" title="���"
									value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
								<ec:column property="applyType" title="�������" width="22%"
									style="text-align:center;" editTemplate="ecs_t_applyTypeMap">
									<c:set var="applyTypeKey">
										<c:out value="${apply.applyType}"></c:out>
									</c:set>
									<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/audit/baseAudit/waitAuditDetails.action" id="detail" text="<font color='#880000'>${auditOperateMap[applyTypeKey].name}</font>" onclick="applyDetail(${apply.id});"/>
								</ec:column>
								<ec:column property="discribe" title="����" ellipsis="true"
									width="20%" style="text-align:center;" value="${apply.discribe}" />
								<ec:column property="createTime" title="����ʱ��"
									width="20%" style="text-align:center;">
									<fmt:formatDate value="${apply.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</ec:column>
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
function applyDetail(id) {
	var a=document.getElementById("detail").action;
	var url = "${basePath}"+a+"?entity.id="+ id+"&date="+Date();
	ecsideDialog(url, window, 600, 400);
}
</script>
</html>