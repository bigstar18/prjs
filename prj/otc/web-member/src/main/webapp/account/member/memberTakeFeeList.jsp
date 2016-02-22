<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>��Ա��ȡ�������б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;��ѯ����
						</div>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/takeFee/list.action?sortName=commodity.id"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td height="60">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td" align="right">
														��Ʒ����:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
														<input type="text" id="commodityId" class="input_text"
																name="${GNNT_}takeFee.commodityId[like]"
																value="${oldParams['takeFee.commodityId[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														��Ա����:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="memberInfoName" class="input_text"
																name="${GNNT_}memberInfo.name[like]"
																value="${oldParams['memberInfo.name[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="submitMemberTakeFee()">��ѯ</button>
													</td>
													<td class="table3_td2" align="left">
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
											autoIncludeParameters="${empty param.autoInc}" var="takeFee"
											action="${basePath}/account/takeFee/list.action"
											minHeight="345" listWidth="120%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">
											<ec:row recordKey="${takeFee.commodityId}">
												<ec:column property="commodity.id[like]" title="��Ʒ����"
													width="14%" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${takeFee.commodityId}','${takeFee.m_FirmId }');"><font
														color="#880000">${takeFee.commodityId}</font>
													</a>
												</ec:column>
												<ec:column property="commodity.name[like]" title="��Ʒ����"
													width="14%" style="text-align:left; "
													value="${takeFee.commodityName}" />
												<ec:column property="memberInfo.name[Like]" title="��Ա����"
													width="14%" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${takeFee.memberName}" tipTitle="${takeFee.memberName}"/>
												<ec:column property="feeAlgr[=][int]" title="�������㷨"
													width="14%" style="text-align:left;"
													editTemplate="ecs_feeAlgr">
													<c:set var="marginAlgrKey">
														<c:out value="${takeFee.feeAlgr}"></c:out>
													</c:set>
											  		${feeAlgrMap[marginAlgrKey]}
									            </ec:column>
												<ec:column property="feeRate[like]" title="������ϵ��"
													width="14%" style="text-align:right; "
													value="${takeFee.feeRate_v}" />
												<ec:column property="feeMode[=]" title="��ȡ��ʽ" width="14%"
													style="text-align:left;" editTemplate="ecs_takeFeeType">
													<c:set var="takeFeeMode">
														<c:out value="${takeFee.feeMode}"></c:out>
													</c:set>
													${takeFeeMap[takeFeeMode]}
												</ec:column>
												<ec:column property="mkt_FeeRate[like]" title="������ϵ��(���н�������ȡ������)"
													width="16%" style="text-align:left; "
													value="${takeFee.mkt_FeeRate_v}" />
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
		<textarea id="ecs_takeFeeType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="feeMode[=]">
			<ec:options items="takeFeeMap" />
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

function submitMemberTakeFee(){
	frm.submit();

}
function forwardUpdate(commodityId, firmId) {
	var url = "${basePath}/account/takeFee/forwardUpdate.action?obj.commodityId="
			+ commodityId + "&obj.m_FirmId=" + firmId;
	ecsideDialog(url);
}
</script>
	</body>
</html>