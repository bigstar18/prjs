<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>机构列表</title>
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
								action="${basePath}/broke/organizationChange/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															机构名称:&nbsp;
															<label>
																<input type="text" id="organizationNo"
																	name="${GNNT_}primary.name[like]"
																	value="${oldParams['primary.name[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1()">
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
																重置
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
											var="organization"
											action="${basePath}/broke/organizationChange/list.action"
											title="" minHeight="345" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"  
											style="table-layout:fixed">

											<ec:row recordKey="${organization.organizationNO}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<%-- 
												<ec:column property="primary.organizationNO[like]"
													width="11%" title="机构代码" style="text-align:left; "
													value="${organization.organizationNO}" />
												--%>
												<ec:column property="primary.name[Like]" width="12%"
													title="机构名称" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${organization.name}" tipTitle="${organization.name}"/>
												<ec:column property="primary.parentOrganization.name[like]"
													width="11%" title="上级机构名称" style="text-align:left;overflow:hidden;text-overflow:ellipsis" tipTitle="${parentOrganizationName}"  sortable="false">
													${organization.parentOrganization.name}
													</ec:column>
												<ec:column property="primary.telephone[like]" width="11%"
													title="联系电话" style="text-align:left;"
													value="${organization.telephone}" />
												<ec:column property="primary.mobile[like]" width="12%"
													title="手机" style="text-align:left;"
													value="${organization.mobile}" />
												<ec:column property="primary.email[like]" width="12%"
													title="电子邮箱" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${organization.email}" tipTitle="${organization.email}"/>
												<ec:column property="primary.address[like]" width="13%"
													title="通讯地址" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${organization.address}" tipTitle="${organization.address}"/>
												<ec:column property="_" title="机构转移"
													style="text-align:center" width="10%">
													<a href="#" class="blank_a"
														onclick="changeOrg('${organization.organizationNO}')"><font
														color="#880000">机构转移</font> </a>
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<SCRIPT type="text/javascript">

		function changeOrg(id){
			var url="${basePath}/broke/organizationChange/forwardUpdate.action?obj.organizationNO="+id;
			ecsideDialog(url,"",580,275);
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>