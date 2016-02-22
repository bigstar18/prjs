<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�ر��Ա״̬�б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/account/specialMemberActiveStatus/list.action"
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
																<input type="text" class="input_text" id="sid"
																	name="${GNNT_}id[like]"
																	value="${oldParams['id[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1mid" align="left">
															�ر��Ա����:&nbsp;
															<label>
																<input type="text" class="input_text" id="sName"
																	name="${GNNT_}name[like]"
																	value="${oldParams['name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1()">
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
																����
															</button>
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
											action="${basePath}/account/specialMemberActiveStatus/list.action"
											minHeight="345" listWidth="100%" retrieveRowsCallback="limit"
											sortRowsCallback="limit" filterRowsCallback="limit"
											 csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${specialMember.id}">
												<ec:column width="4%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="id[Like]" title="�ر��Ա���"
													style="text-align:left; " width="6%">
													<a href="#" class="blank_a"
														onclick="return update('${specialMember.id}');"><font
														color="#880000">${specialMember.id}</font> </a>
												</ec:column>
												<ec:column property="name[Like]" title="�ر��Ա����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${specialMember.name}"
													width="20%" tipTitle="${specialMember.name}"/>
												<ec:column property="useFunds[=][bigdecimal]" width="10%"
													title="�����ʽ�" style="text-align:right;">
													<fmt:formatNumber value="${specialMember.useFunds}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="specialMemberStatus.status[like]"
													title="�ر��Ա״̬" style="text-align:left;"
													editTemplate="ecs_t_statusType" width="10%">
													${firmStatusMap[specialMember.specialMemberStatus.status]}
												</ec:column>
												<ec:column property="-1" width="15%" filterable="false" ellipsis="true"
													title="��ע" style="text-align:left;">
		  												<c:choose>
		  													<c:when test="${specialMember.useFunds < market[0].specialMemberOpenMinFunds}">
		  														<c:out value="�ʽ��㣬����������� "/>
		  														<fmt:formatNumber value="${market[0].specialMemberOpenMinFunds - specialMember.useFunds}" pattern="#,##0.00"/>
		  													</c:when>
		  													<c:otherwise>
		  														<c:out value="���Լ���"/>
		  													</c:otherwise>
		  												</c:choose>
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
		var url = "${basePath}/account/specialMemberActiveStatus/forwardUpdate.action?obj.id="
				+ id;
		ecsideDialog(url, "", 580, 220);
	}
	function select1() {
		frm.submit();
	}
</script>
	</body>
</html>