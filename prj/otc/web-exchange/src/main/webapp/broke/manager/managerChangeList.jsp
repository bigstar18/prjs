<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�ͻ�����ת���б�</title>
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
								action="${basePath}/broke/managerChange/list.action"
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
														������������:
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="parentNo"
																name="${GNNT_}primary.parentOrganizationNO[like]"
																value="${oldParams['primary.parentOrganizationNO[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td" align="right"> 
														�ͻ��������:
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="managerNo"
																name="${GNNT_}primary.managerNo[like]"
																value="${oldParams['primary.managerNo[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="select1()">��ѯ</button>
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
											autoIncludeParameters="${empty param.autoInc}" var="manager"
											action="${basePath}/broke/managerChange/list.action"
											title="" minHeight="460" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">

											<ec:row recordKey="${manager.managerNo}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${manager.managerNo}" width="2%" viewsAllowed="html" />
												<ec:column property="primary.managerNo[like]" width="12%"
													title="�����˴���" style="text-align:left; "
													value="${manager.managerNo}" />
													<ec:column property="primary.parentOrganizationNO[like]" width="12%"
													title="������������" style="text-align:left; "
													value="${manager.parentOrganizationNO}" />
												<ec:column property="primary.name[like]" width="12%"
													title="����" style="text-align:left;"
													value="${manager.name}" />
												<ec:column property="primary.telephone[like]" width="12%"
													title="��ϵ�绰" style="text-align:left;"
													value="${manager.telephone}" />
												<ec:column property="primary.mobile[like]" width="12%"
													title="�ֻ�" style="text-align:left;"
													value="${manager.mobile}" />
												<ec:column property="primary.email[like]" width="12%"
													title="��������" style="text-align:left;"
													value="${manager.email}" />
												<ec:column property="primary.address[like]" width="12%"
													title="ͨѶ��ַ" style="text-align:left;"
													value="${manager.address}" />
												<ec:column property="_" title="������ת��"
													style="text-align:center" width="11%">
													<button onclick="forwardChange('${manager.managerNo}')">
														<font color="red">������ת��</font>
													</button>
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

		<SCRIPT type="text/javascript">

		function forwardChange(id){
			var url="${basePath}/broke/managerChange/forwardUpdate.action?obj.managerNo="+id;
			ecsideDialog(url,"",600,500);
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>