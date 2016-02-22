<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css" />

<html>
	<head>
		<title>��Ա״̬�б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/memberFreezeStatus/list.action?sortName=primary.id"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														��Ա���:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="memberId"
																name="${GNNT_}id[like]" value="${oldParams['id[like]'] }" />
														</label>
													</td>
													<td class="table3_td_1" align="left">
														��Ա����:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="memberName"
																name="${GNNT_}name[like]"
																value="${oldParams['name[like]'] }" size="14" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="submitMember()">��ѯ</button>&nbsp;&nbsp;
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
											var="memberInfo" width="100%"
											action="${basePath}/account/memberFreezeStatus/list.action?sortName=primary.id"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row recordKey="${memberInfo.id}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="memberNo[like]" title="��Ա���"
													width="6%" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return update('${memberInfo.id}');"><font
														color="#880000">${memberInfo.id}</font>
													</a>
												</ec:column>
												<ec:column property="name[like]" title="��Ա����" width="20%"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.name}" tipTitle="${memberInfo.name}"/>
												<ec:column property="useFunds[=][bigdecimal]" width="10%"
													title="�����ʽ�" style="text-align:right;">
													<fmt:formatNumber value="${memberInfo.useFunds}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="compMember.status[=]" title="��Ա״̬"
													width="10%" style="text-align:left;"
													editTemplate="ecs_t_statusType">
													${memberStatusMap[memberInfo.compMember.status]}
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
		</div>

		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- �༭�͹�����ʹ�õĻ�Ա״̬ģ�� -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="compMember.Status[=]">
			<ec:options items="memberStatusMap" />
		</select>
	    </textarea>
	</body>
</html>
	    
		<script type="text/javascript">
function submitMember() {
	frm.submit();
}
function update(id) {
	var url = "${basePath}/account/memberFreezeStatus/forwardUpdateFreeze.action?obj.id="
			+ id;
	ecsideDialog(url,"",580,220);
}
</script>