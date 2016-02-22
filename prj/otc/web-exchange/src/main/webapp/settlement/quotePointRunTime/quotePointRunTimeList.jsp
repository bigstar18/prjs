<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>��Ʒ���۵�����ʱ�б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/settlement/quotePointRunTime/list.action?sortName=primary.commodityId"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmin">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														��Ʒ����:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_ }commodity.name[like]"
																id="commodityName"
																value="${oldParams['commodity.name[like]'] }"/>
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="submitQuote()">��ѯ</button>&nbsp;&nbsp;
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
											var="quotePointRunTime"
											action="${basePath}/settlement/quotePointRunTime/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"  style="table-layout:fixed">
											<ec:row recordKey="${quotePointRunTime.m_FirmId}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="��Ʒ����" width="10%"
													style="text-align:left;">
													<a href="#" class="blank_a"
														onclick="return update('${quotePointRunTime.m_FirmId}','${quotePointRunTime.commodityId}');"><font
														color="#880000">${quotePointRunTime.commodityName }</font>
													</a>
												</ec:column>
											   <ec:column property="firm.firmName[Like]" title="��Ա����" width="10%"
													style="text-align:left;"
													value="${quotePointRunTime.firmName}" />
											    <ec:column property="firm.firmType[=]" title="��Ա����" width="10%"
													style="text-align:left;"
												   editTemplate="ecs_status">
												   ${memberTypeMap[quotePointRunTime.firmType] }   
												</ec:column>
												<ec:column property="primary.quotePoint_B[=][int]" title="�򱨼۵��"
													width="10%" style="text-align:right;"
													value="${quotePointRunTime.quotePoint_B}" />
												<ec:column property="primary.quotePoint_S[=][int]" title="�����۵��"
													width="10%" style="text-align:right;"
													value="${quotePointRunTime.quotePoint_S}" />
												<ec:column property="primary.quotePoint_B_RMB[=][bigdecimal]" title="�򱨼۵����"
													width="10%" style="text-align:right;"
													value="${quotePointRunTime.quotePoint_B_RMB}" />
												<ec:column property="primary.quotePoint_S_RMB[=][bigdecimal]" title="�����۵����"
													width="10%" style="text-align:right;"
													value="${quotePointRunTime.quotePoint_S_RMB}" />
												
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
		<textarea id="ecs_status" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="firm.firmType[=][String]">
			<ec:options items="memberTypeMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function update(firmId,commodityId){
			var url="${basePath}/settlement/quotePointRunTime/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.m_FirmId="+firmId;
			ecsideDialog(url,"",580,240);
		}
		function submitQuote(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>