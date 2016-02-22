<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>����ת���б�</title>
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
								action="${basePath}/broke/organizationChange/list.action"
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
														�ϼ���������:&nbsp;
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
														��������:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="organizationNo"
																name="${GNNT_}primary.organizationNO[like]"
																value="${oldParams['primary.organizationNO[like]'] }"
																class="input_text" />
														</label>
													</td>
												</tr>
												<tr>
													<td class="table3_td" align="right">
														&nbsp;
													</td>
													<td class="table3_td2">
													&nbsp;
													</td>
													<td class="table3_td" align="right">
														&nbsp;
													</td>
													<td class="table3_td2">
														&nbsp;
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
											autoIncludeParameters="${empty param.autoInc}" var="organization"
											action="${basePath}/broke/organizationChange/list.action"
											title="" minHeight="460" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">

											<ec:row recordKey="${organization.organizationNO}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${organization.organizationNO}" width="2%" viewsAllowed="html" />
												<ec:column property="primary.organizationNO[like]" width="12%"
													title="��������" style="text-align:left; "
													value="${organization.organizationNO}" />
												<ec:column property="primary.parentOrganizationNO[like]" width="12%"
													title="�ϼ���������" style="text-align:left; "
													value="${organization.parentOrganizationNO}" />
												<ec:column property="primary.name[Like]" width="12%"
													title="��������" style="text-align:left;"
													value="${organization.name}" />
												<ec:column property="primary.telephone[like]" width="12%"
													title="�绰" style="text-align:left;"
													value="${organization.telephone}" />
												<ec:column property="primary.mobile[like]" width="12%"
													title="�ֻ�" style="text-align:left;"
													value="${organization.mobile}" />
												<ec:column property="primary.email[like]" width="12%"
													title="��������" style="text-align:left;"
													value="${organization.email}" />
												<ec:column property="primary.address[like]" width="12%"
													title="��ַ" style="text-align:left;"
													value="${organization.address}" />
												<ec:column property="_" title="ת�ƻ���"
													style="text-align:center" width="11%">
													<button onclick="changeOrg('${organization.organizationNO}')">
														<font color="red">ת�ƻ���</font>
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

		function changeOrg(id){
			var url="${basePath}/broke/organizationChange/forwardUpdate.action?obj.organizationNO="+id;
			ecsideDialog(url,"",600,500);
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>