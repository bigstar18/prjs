<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css" />

<html>
	<head>
		<title>��Ա�б�</title>
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
								action="${basePath}/account/memberInfo/list.action?sortName=compMember.id"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															��Ա���:&nbsp;
															<label>
																<input name="${GNNT_}id[like]" type="text"
																	class="input_text" id="memberInfoId" size="14"
																	value="${oldParams['id[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��Ա����:&nbsp;
															<label>
																<input type="text" id="memberInfoName"
																	class="input_text" name="${GNNT_}name[like]"
																	value="${oldParams['name[like]'] }" size="14" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��Ա״̬:&nbsp;
															<label>
																<select id="memberStatus" style="width: 120px;"
																	name="${GNNT_}compMember.status[=]">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${memberStatusMap}" var="maps">
																		<option value="${maps.key}">
																			${maps.value}
																		</option>
																	</c:forEach>
																</select>
																<script type="text/javascript">
	document.getElementById('memberStatus').value = "${oldParams['compMember.status[=]']}";
</script>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="submitMember()">
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
						<div class="div_gn">
							<button id="addMember" class="anniu_btn" onclick="addMember()">
								���
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr valign="top">
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="memberInfo" width="100%"
											action="${basePath}/account/memberInfo/list.action?sortName=compMember.id"
											listWidth="190%" minHeight="345" retrieveRowsCallback="limit"
											sortRowsCallback="limit" filterRowsCallback="limit"
											 csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${memberInfo.id}">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="primary.id[like]" width="3%"
													title="��Ա���" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return forwardUpdate('${memberInfo.id}');"><font
														color="#880000">${memberInfo.id}</font> </a>
												</ec:column>
												<ec:column property="name[like]" width="10%" title="��Ա����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.name}" tipTitle="${memberInfo.name}"/>
												<ec:column property="memberType[=][String]" width="3%"
													title="��Ա����" style="text-align:left;"
													editTemplate="ecs_memberType">
													${accountMemberTypeMap[memberInfo.memberType]}
												</ec:column>
												<ec:column property="compMember.status[=]" title="��Ա״̬"
													width="3%" style="text-align:left;"
													editTemplate="ecs_t_statusType">
													${memberStatusMap[memberInfo.compMember.status]}
												</ec:column>
												<ec:column property="createTime[=][Date]"
													width="6%" title="����ʱ��" style="text-align:left;" value="${datefn:formatdate(memberInfo.createTime)}" filterable="false">
												</ec:column>
												<ec:column property="signTime[=][Date]"
													width="6%" title="ǩԼʱ��" style="text-align:left;" value="${datefn:formatdate(memberInfo.signTime)}" filterable="false">
												</ec:column>
												<ec:column property="activateTime[=][Date]"
													width="6%" title="����ʱ��" style="text-align:left;" value="${datefn:formatdate(memberInfo.activateTime)}" filterable="false">
												</ec:column>
												<ec:column property="freezeTime[=][Date]"
													width="6%" title="����ʱ��" style="text-align:left;" value="${datefn:formatdate(memberInfo.freezeTime)}" filterable="false">
												</ec:column>
												<ec:column property="demiseTime[=][Date]"
													width="6%" title="��ֹʱ��" style="text-align:left;" value="${datefn:formatdate(memberInfo.demiseTime)}" filterable="false">
												</ec:column>
												<ec:column property="useFunds[=][bigdecimal]" width="6%"
													title="�����ʽ�" style="text-align:right;">
													<fmt:formatNumber value="${memberInfo.useFunds}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="signNo[like]" width="4%"
													title="�����˺�" style="text-align:left;">
													${memberInfo.signNo}
												</ec:column>
												<ec:column property="papersType[=][int]" width="3%"
													title="֤������" style="text-align:left;"
													editTemplate="ecs_papersType">
													<c:set var="typeKey">
														<c:out value="${memberInfo.papersType}"></c:out>
													</c:set>
							  						${accountPapersTypeMap[typeKey]}
												</ec:column>
												<ec:column property="papersName[like]" width="6%"
													title="֤������" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${memberInfo.papersName}" tipTitle="${memberInfo.papersName}"/>
												<ec:column property="email[like]" width="6%" title="��������"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.email}" filterable="false" tipTitle="${memberInfo.email}"/>
												<ec:column property="fax[like]" width="4%" title="�������"
													style="text-align:left;" value="${memberInfo.fax}" filterable="false"/>
												<ec:column property="postCode[like]" width="4%" title="�ʱ�"
													style="text-align:left;" value="${memberInfo.postCode}" filterable="false"/>
												<ec:column property="phone[like]" width="4%" title="��ϵ�绰"
													style="text-align:left;" value="${memberInfo.phone}" filterable="false"/>
												<ec:column property="address[like]" width="7%" title="ͨѶ��ַ"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.address}" tipTitle="${memberInfo.address}" filterable="false"/>
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
		<!-- �༭�͹�����ʹ�õĻ�Ա����ģ�� -->
		<textarea id="ecs_memberType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="memberType[=][String]">
			<ec:options items="accountMemberTypeMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õĻ�Ա֤������ ģ�� -->
		<textarea id="ecs_papersType" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="papersType[=][String]">
				<ec:options items="accountPapersTypeMap" />
			</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õĻ�Ա״̬ģ�� -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="compMember.Status[=]">
			<ec:options items="memberStatusMap" />
		</select>
	    </textarea>
		<textarea id="ecs_t_openstatus" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="openstatus[=][int]">
			<ec:options items="openstatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
	function contactSMember(id) {
		var url = "${basePath}/account/memberRelation/updateForward.action?id="
				+ id;
		ecsideDialog(url, "", 600, 460);
	}
	function forwardUpdate(id) {
		var url = "${basePath}/account/memberInfo/forwardUpdate.action?obj.id="
				+ id;
		ecsideDialog(url, "", 650, 515);
	}

	function addMember() {
		var url = "${basePath}/account/memberInfo/forwardAdd.action";
		ecsideDialog(url, "", 650, 650);
	}
	function deleteByCheckBox() {
		var url = "${basePath}/account/memberInfo/delete.action"
		deleteEcside(ec.ids, url);
	}
</SCRIPT>
	</body>
</html>
<script type="text/javascript">
function submitMember() {
	frm.submit();
}
function forwardUpdatePW(id) {///account/memberInfo/
	var url = "${basePath}/account/memberInfo/forwardUpdatePassward.action?obj.traderID="
			+ id;
	ecsideDialog(url, "", 580, 260);
}
</script>