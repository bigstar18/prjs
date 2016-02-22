<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�ر��Ա״̬�б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/specialMemberStatus/list.action"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmid">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1mid" align="left">
														�ر��Ա���:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="sid" name="${GNNT_}id[like]"
																value="${oldParams['id[like]'] }" />
														</label>
													</td>
													<td class="table3_td_1mid" align="left">
														�ر��Ա����:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="sName" name="${GNNT_}name[like]"
																value="${oldParams['name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">��ѯ</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset()">����</button>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
							</form>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="specialMember"
											action="${basePath}/account/specialMemberStatus/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">


											<ec:row recordKey="${specialMember.id}"
												ondblclick="return update('${specialMember.id}');">
												<ec:column property="id[Like]" title="�ر��Ա���"
													style="text-align:left; " value="${specialMember.id}" />
												<ec:column property="name[Like]" title="�ر��Ա����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${specialMember.name}" tipTitle="${specialMember.name}"/>
												<ec:column property="specialMemberStatus.status[like]"
													title="�ر��Ա״̬" style="text-align:left;"
													editTemplate="ecs_t_statusType">
													${firmStatusMap[specialMember.specialMemberStatus.status]}
												</ec:column>
											</ec:row>

										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- �༭�͹�����ʹ�õĻ�Ա״̬ģ�� -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="specialMemberStatus.status[like]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
<script type="text/javascript">
function update(id) {
	var url = "${basePath}/account/specialMemberStatus/forwardUpdate.action?obj.id="
			+ id;
	ecsideDialog(url,"",580,260);
}
function select1() {
	frm.submit();
}
</script>
	</body>
</html>