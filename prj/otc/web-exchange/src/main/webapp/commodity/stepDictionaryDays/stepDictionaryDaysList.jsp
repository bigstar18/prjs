<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա���ڷѽ����б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/stepDictionary/list.action"
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
														���ݽ׺�:&nbsp;
														<label>
															<input type="text" class="input_text" id="stepNo" name="${GNNT_}stepNo[=][int]"
																value="${oldParams['stepNo[=][int]'] }">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">��ѯ</button>
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
											var="stepDictionary"
											action="${basePath}/commodity/stepDictionary/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">

											<ec:row recordKey="stepDictionary.ladderCode">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.stepNo[=][int]" title="���ݽ׺�" width="49%" 
													style="text-align:left;">
													<a href="#" class="blank_a"
														onclick="return update('${stepDictionary.ladderCode}','${stepDictionary.stepNo}');"><font
														color="#880000">${stepDictionary.stepNo }</font>
													</a>
												</ec:column>
												<ec:column property="primary.stepValue[=][bigdecimal]" title="����ֵ(��)"
													style="text-align:right;" width="49%" 
													value="${stepDictionary.stepValue}" />
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
function update(id1,id2) {
	var url = "${basePath}/commodity/stepDictionary/forwardUpdate.action?obj.ladderCode="+id1+"&obj.stepNo="+id2;
	ecsideDialog(url,"",580,222);
}
function select1() {
	frm.submit();
}
function addStepDictionary(){
	var url="${basePath}/commodity/stepDictionary/forwardAdd.action";
	ecsideDialog(url);
}
function deleteByCheckBox(){
	 		var url="${basePath}/commodity/stepDictionary/delete.action"
			deleteEcside(ec.ids,url);
		}
</script>
	</body>
</html>