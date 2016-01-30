<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<html>
	<head>
		<title>交易商列表</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/trade/mfirm/mfirmApplyList.action?sortColumns=order+by+createTime+desc"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															注册申请号:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.applyID[=][Long]"
																	value="${oldParams['primary.applyID[=][Long]']}" maxLength="<fmt:message key='firmApplyId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">
															交易商名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="name"
																	name="${GNNT_}primary.name[allLike]" 
																	value="${oldParams['primary.name[allLike]'] }" maxLength="<fmt:message key='firmName_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">
															审核状态:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${applyStatusMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>"
					  										</script>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="right">
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
															<button class="btn_sec" onclick=
	select1();
>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=
	myReset();
>
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
										<ec:table items="pageInfo.result" var="firmapply"
											action="${basePath}/trade/mfirm/mfirmApplyList.action?sortColumns=order+by+createTime+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firm.xls" csvFileName="firm.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="applyID" width="5%" title="注册申请号" 
													style="text-align:left;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/mfirm/forwordFirmApplyDetail.action" id="getFirm" text="<font color='#880000'>${firmapply.applyID}</font>" onclick="getFirm(${firmapply.applyID});"/>
												</ec:column>
												<ec:column property="name" title="MFirmApply.name" width="7%"
													style="text-align:left;" ellipsis="true">
													${firmapply.name}
												</ec:column>
												<ec:column property="fullName" title="MFirmApply.fullName" width="8%"
													style="text-align:left;" ellipsis="true"/>
												<ec:column property="type" title="MFirmApply.type" width="7%" style="text-align:center;">
													${mfirmTypeMap[firmapply.type]}
												</ec:column>
												<ec:column property="status" title="MFirmApply.status" width="7%" style="text-align:left;">
													<c:set var="key">
														<c:out value="${firmapply.status}"></c:out>
													</c:set>
													${applyStatusMap[key]}
												</ec:column>
												<ec:column property="createTime" title="MFirmApply.createTime" width="10%"
													style="text-align:center;">
													<fmt:formatDate value="${firmapply.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="modifyTime" title="MFirmApply.modifyTime" width="10%"
													style="text-align:center;">
													<fmt:formatDate value="${firmapply.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="operate" width="6%" title="数据操作"
													style="text-align:center; " sortable="false">
													<c:if test="${firmapply.status==0}">
														<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/mfirm/forwordFirmApplyDetail.action" id="detail" text="<font color='#880000'>审核操作</font>" onclick="getFirm(${firmapply.applyID});"/>
													</c:if>
													<c:if test="${firmapply.status !=0}">
														<button class="anniu_btn" disabled="disabled">已审核</button>
													</c:if>
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
						onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
			</textarea>
	 <SCRIPT type="text/javascript">
			function select1() {
				frm.submit();
			}
			function getFirm(id){
				var random = Math.floor(Math.random()*10000);
				var a=document.getElementById("getFirm").action;
				var url = "${basePath}"+a+"?entity.applyID="+id+ "&a="+random;
				if(showDialog(url, "", 650, 570)){
					frm.action="${basePath}/trade/mfirm/mfirmApplyList.action?sortColumns=order+by+applyID";
					frm.submit();
				};
			}
     </SCRIPT>
	</body>
</html>