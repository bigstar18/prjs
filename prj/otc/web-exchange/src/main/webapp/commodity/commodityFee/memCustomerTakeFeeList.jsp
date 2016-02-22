<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>��Ա��Ͻ�ͻ���ȡ�������б�</title>
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
								action="${basePath}/commodity/memCustomerTakeFee/list.action?sortName=commodity.id"
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
															��Ʒ����:&nbsp;
															<label>
																<input type="text" class="input_text"
																	name="${GNNT_}commodity.name[like]" id="commodityName"
																	value="${oldParams['commodity.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															������Ա:&nbsp;
															<label>
																<input type="text" id="memberInfoName"
																	class="input_text" name="${GNNT_}memberInfo.name[like]"
																	value="${oldParams['memberInfo.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="submitCustomerTakeFee()">
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
							<button class="anniu_btn" onclick="addmemCustomerTakeFeePoint()" id="update">
								���
							</button>
							<button class="anniu_btn" onclick="deleteCustomerTakeFeePoint()"
								id="delete">
								ɾ��
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customerTakeFee"
											action="${basePath}/commodity/memCustomerTakeFee/list.action"
											minHeight="345" listWidth="100%" retrieveRowsCallback="limit"
											sortRowsCallback="limit" filterRowsCallback="limit" 
											csvFileName="�����б�.csv" 
											style="table-layout:fixed">

											<ec:row recordKey="${customerTakeFee.commodityId}">
												<ec:column width="4%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="commodity.name[like]" title="��Ʒ����"	width="15%" style="text-align:left; " ellipsis="true"> <a href="#" class="blank_a" onclick="return forwardUpdate('${customerTakeFee.commodityId}','${customerTakeFee.m_FirmId }');"><font color="#880000">${customerTakeFee.commodityName}</font> </a> </ec:column>
												<ec:column property="memberInfo.name[Like]" title="������Ա"
													width="15%" style="text-align:left;" ellipsis="true"
													value="${customerTakeFee.memberName}" />
												<ec:column property="feeAlgr[=][int]" title="�������㷨"
													width="15%" style="text-align:left;"
													editTemplate="ecs_feeAlgr">
													<c:set var="marginAlgrKey">
														<c:out value="${customerTakeFee.feeAlgr}"></c:out>
													</c:set>
											  		${feeAlgrMap[marginAlgrKey]}
									            </ec:column>
												<ec:column property="feeRate[like]" title="������ϵ��"
													width="10%" style="text-align:right; "
													value="${customerTakeFee.feeRate_log}" filterable="false" />
												<ec:column property="mkt_FeeRate[like]"
													title="��������ȡ������" width="15%"
													style="text-align:right; " filterable="false"
													value="${customerTakeFee.mkt_FeeRate_log}" />
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
		<!-- �༭�͹�����ʹ�õĻ�Ա��ȡ�����ѷ�ʽģ�� -->
		<textarea id="ecs_customerTakeFeeType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="feeMode[=]">
			<ec:options items="customerTakeFeeMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õ��������㷨ģ�� -->
		<textarea id="ecs_feeAlgr" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="feeAlgr[=][int]">
			<ec:options items="feeAlgrMap" />
		</select>
	    </textarea>
		<script type="text/javascript">

function submitCustomerTakeFee() {
	frm.submit();

}
function deleteCustomerTakeFeePoint() {
	var url = "${basePath}/commodity/memCustomerTakeFee/delete.action";
	deleteEcside(ec.ids, url);
}
function forwardUpdate(commodityId, firmId) {
	var url = "${basePath}/commodity/memCustomerTakeFee/forwardUpdate.action?obj.commodityId="
			+ commodityId + "&obj.m_FirmId=" + firmId;
	ecsideDialog(url, "", 580, 520);
}
function addmemCustomerTakeFeePoint() {
	var urlFee = "${basePath}/commodity/memCustomerTakeFee/forwardAdd.action";
	ecsideDialog(urlFee, "", 580, 415);
}
</script>
	</body>
</html>