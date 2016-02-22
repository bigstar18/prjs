<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա������ֵ�б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/memberThreshold/list.action?sortName=memberInfo.id"
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
															<input type="text" class="input_text" id="memberNo" name="${GNNT_}memberNo[like]"
																value="${oldParams['memberNo[like]'] }">
														</label>
													</td>
													<td class="table3_td_1" align="left">
														��Ա����:&nbsp;
														<label>
															<input type="text" class="input_text" id="firmName" name="${GNNT_}name[like]"
																value="${oldParams['name[like]'] }">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">��ѯ</button>&nbsp;&nbsp;
														<button  class="btn_cz" onclick="myReset()">����</button>
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
											var="memberThreshold"
											action="${basePath}/account/memberThreshold/list.action?sortName=memberInfo.id"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">

											<ec:row recordKey="memberThreshold.memberNo">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.memberNo[like]" title="��Ա���"
													style="text-align:left;" width="14%" ellipsis="true" > 
													<a href="#" class="blank_a"
														onclick="return update('${memberThreshold.memberNo}');"><font
														color="#880000">${memberThreshold.memberNo}</font>
													</a>
												</ec:column>
												<ec:column property="memberInfo.name[like]" title="��Ա����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" width="14%" ellipsis="true"
													value="${memberThreshold.memberName}" tipTitle="${memberThreshold.memberName}"/>
												<ec:column property="primary.minRiskFund[like]" title="��Ա������ֵ"
													style="text-align:right;" width="14%">
													<fmt:formatNumber value="${memberThreshold.minRiskFund}"   pattern="###,##0.00"/>
												</ec:column>
												<ec:column property="primary.warnTh[like]" title="��ԱԤ��������"
													style="text-align:right;" width="14%"
													value="${memberThreshold.warnTh_log}" />
												<ec:column property="primary.frozenTh[like]" title="��Ա���������"
													style="text-align:right;" width="14%"
													value="${memberThreshold.frozenTh_v}%" />
												<ec:column property="primary.cu_F_WarnTh[like]" title="�ͻ��ʽ�Ԥ����ֵ"
													style="text-align:right;" width="14%">
												<fmt:formatNumber 	value="${memberThreshold.cu_F_WarnTh}"  pattern="###,##0.00"/>
												</ec:column>
												<ec:column property="primary.m_SelfTradeRate[like]" title="��Ա�ǿͻ�ͷ�罻�ױ���"
													style="text-align:right;" width="12%"
													value="${memberThreshold.m_SelfTradeRate_v}%" />
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
<script type="text/javascript">

function update(id) {
	var url = "${basePath}/account/memberThreshold/forwardUpdate.action?obj.memberNo="
			+ id;
	ecsideDialog(url,"",580,403);
}
function select1() {
	frm.submit();
}
</script>
	</body>
</html>