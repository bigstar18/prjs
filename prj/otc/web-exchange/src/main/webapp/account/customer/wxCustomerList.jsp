<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�ͻ��б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<IMPORT namespace="MEBS"
		implementation="${basePath}/common/jslib/calendar.htc">

	<body xmlns:MEBS>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/account/wxCustomer/list.action"
								method="post">
								<input type="hidden" name="tree" id="tree" value="${tree}"
									class="input_text">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="40" width="775">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<%-- <td width="220" align="left">
															�ͻ�����:&nbsp;
															<label>
																<input type="text" id="memberNames"
																	name="${GNNT_}selectIds"
																	value="${oldParams['selectIds']}" readonly=true
																	size="8" class="input_text">
																<a href="javascript:clickText();"><img
																		align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
																</a>
															</label>
														</td> --%>
														<td class="table3_td_1tjcx" align="left">
															�ۺϻ�Ա���ƣ�
															<input type="text" id="memberNames"
																name="${GNNT_}memberNames"
																value="${oldParams['memberNames']}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${GNNT_}memberIds"
																id="memberIds" value="${oldParams['memberIds']}"
																class="input_text">
														</td>
														<td class="table3_td_1" align="left">
															�����˺�:&nbsp;
															<label>
																<input type="text" id="customerId"
																	name="${GNNT_}primary.customerno[like]"
																	value="${oldParams['primary.customerno[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															�ͻ�����:&nbsp;
															<label>
																<input type="text" id="customerName"
																	name="${GNNT_}primary.name[like]"
																	value="${oldParams['primary.name[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															�ͻ�״̬:&nbsp;
															<label>
																<select id="customerStatus"
																	name="${GNNT_}primary.customerStatus.status[=]"
																	style="width: 100px;">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${firmStatusMap}" var="maps">
																		<option value="${maps.key}">
																			${maps.value}
																		</option>
																	</c:forEach>
																</select>
																<script type="text/javascript">
																	document.getElementById('customerStatus').value = "${oldParams['primary.customerStatus.status[=]']}";
																</script>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.activatetime[=][date]"
																	value='${oldParams["primary.activatetime[=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1();">
																��ѯ
															</button>
															&nbsp;
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
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/wxCustomer/list.action"
											title="" minHeight="345" listWidth="150%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit"
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">

											<ec:row recordKey="${customer.customerno}">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="primary.customerno[like]" width="5%"
													title="�����˺�" style="text-align:left; "
													value="${customer.customerno}"/>
												<ec:column property="primary.name[Like]" width="4%"
													title="�ͻ�����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													tipTitle="${customer.name}">
													<nobr>
														${customer.name}
													</nobr>
												</ec:column>
												<ec:column property="memberInfo.name[like]" width="4%"
													title="������Ա"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}"
													tipTitle="${customer.memberName}" />
												<ec:column property="primary.customerStatus.status[=]" width="3%"
													title="�ͻ�״̬" style="text-align:center;"
													editTemplate="ecs_t_statusType">
		  												${firmStatusMap[customer.status]}
												</ec:column>
												<ec:column property="primary.createtime[=][Date]" width="5%"
													title="����ʱ��" style="text-align:left;"
													value="${datefn:formatdate(customer.createtime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.activatetime[=][Date]"
													width="5%" title="����ʱ��" style="text-align:left;"
													value="${datefn:formatdate(customer.activatetime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.paperstype[=][int]" width="3%"
													title="֤������" style="text-align:left;"
													editTemplate="ecs_t_papersType">
													<c:set var="typeKey">
														<c:out value="${customer.paperstype}"></c:out>
													</c:set>
							  						${papersTypeMap[typeKey]}
							  						</ec:column>
												<ec:column property="primary.paperscode[like]" width="5%"
													title="֤������"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.paperscode}"
													tipTitle="${customer.paperscode}" />
												<ec:column property="primary.email[like]" width="5%"
													title="��������"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.email}" filterable="false"
													tipTitle="${customer.email}" />
												<ec:column property="primary.fax[like]" width="3%"
													title="Э��1" style="text-align:center; ">
													<a href="#" onclick="toAgreement('${customer.customerno}','1')">�鿴</a>
													</ec:column>
												<ec:column property="primary.fax[like]" width="3%"
													title="Э��2" style="text-align:center; ">
													<a href="#" onclick="toAgreement('${customer.customerno}','2')">�鿴</a>
													</ec:column>
												<ec:column property="primary.fax[like]" width="3%"
													title="Э��3" style="text-align:center; ">
													<a href="#" onclick="toAgreement('${customer.customerno}','3')">�鿴</a>
													</ec:column>
												<ec:column property="primary.fax[like]" width="3%"
													title="Э��4" style="text-align:center; ">
													<a href="#" onclick="toAgreement('${customer.customerno}','4')">�鿴</a>
													</ec:column>
												<ec:column property="primary.fax[like]" width="3%"
													title="Э��5" style="text-align:center; ">
													<a href="#" onclick="toAgreement('${customer.customerno}','5')">�鿴</a>
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
		<!-- �༭�͹�����ʹ�õ�֤������ģ�� -->
		<textarea id="ecs_t_papersType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.papersType[=][int]">
			<ec:options items="papersTypeMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õĿͻ�״̬ģ�� -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.status[=][int]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
		<textarea id="ecs_t_openstatus" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="openstatus[=][int]">
			<ec:options items="openstatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
	function clickText() {
		var memberIds = frm.memberIds.value;
		var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
				+ memberIds;
		var result = window.showModalDialog(url, '',
				"dialogWidth=350px;dialogHeight=520px");
		if (result != null && result != '') {
			var result1 = result.split('####');
			frm.memberIds.value = result1[0];
			frm.memberNames.value = result1[1];
		}
	}
	function select1() {
		frm.action="${basePath}/account/wxCustomer/list.action";
		frm.submit();
	}
	function toinformation(id){
		var url="${basePath}/account/netCustomer/toinformation.action?obj.customerno="+id;
		ecsideDialog(url, "", 600, 570);
	}
	function tojsp(name){
		var url="${basePath}/account/customer/"+name+".jsp";
		ecsideDialog(url, "", 600, 570);
	}
	function toAgreement(value,index){
		var url="${basePath}/account/wxCustomer/showAgreement.action?obj.customerno="+value+"&&agreementturn="+index;
		ecsideDialog(url, "", 600, 570);
	}
</SCRIPT>
	</body>
</html>