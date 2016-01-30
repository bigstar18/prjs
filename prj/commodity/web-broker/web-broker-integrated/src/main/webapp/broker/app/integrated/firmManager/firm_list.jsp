<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商列表</title>
		<SCRIPT type="text/javascript">
			
			function update(id){
				var url = "${basePath}/broker/firmManager/viewByIdFirm.action?entity.firmId="+id;
				if(showDialog(url, "", 650, 530)){
					frm.submit();
				};
			}
		
			function select1() {
				frm.submit();
			}
		</SCRIPT>
	</head>
	<body  onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/broker/firmManager/listFirm.action?sortColumns=order+by+createtime+desc"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															交易商代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked"
																	name="${GNNT_}primary.firmId[allLike]"
																	value="${oldParams['primary.firmId[allLike]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易商名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="name"
																	name="${GNNT_}primary.name[allLike]" 
																	value="${oldParams['primary.name[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易商类型:&nbsp;
															<label>
																<select id="type" name="${GNNT_}primary.type[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${mfirmTypeMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.type.value = "<c:out value='${oldParams["primary.type[=][int]"] }'/>"
					  										</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>重置</button>
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
										<ec:table items="pageInfo.result" var="firm"
											action="${basePath}/broker/firmManager/listFirm.action?sortColumns=order+by+createtime+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firmApply.xls" csvFileName="firmApply.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="firmId" width="10%" title="交易商代码"
													style="text-align:left;" ellipsis="true">
													<a href="#" class="blank_a"
														onclick="return update('${firm.firmId}');"><font
														color="#880000">${firm.firmId}</font> </a>
												</ec:column>
												<ec:column property="name" title="交易商名称" width="10%"
													style="text-align:left;"  ellipsis="true">
													${firmApply.name}
												</ec:column>
												<ec:column property="fullName" title="交易商全称" width="10%"
													style="text-align:left;"  ellipsis="true"/>
												<ec:column property="contactMan" title="联系人" width="10%"
													style="text-align:left;"  ellipsis="true"/>
												<ec:column property="type" title="交易商类型" width="10%"
													style="text-align:center;">
													${mfirmTypeMap[firm.type]}
												</ec:column>
												<ec:column property="createTime" title="创建时间" width="10%"
													style="text-align:center;">
													<fmt:formatDate value="${firmApply.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="type" title="状态" width="10%"
													style="text-align:center;">
													${firmStatusMap[firm.status]}
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


			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>



	</body>
</html>