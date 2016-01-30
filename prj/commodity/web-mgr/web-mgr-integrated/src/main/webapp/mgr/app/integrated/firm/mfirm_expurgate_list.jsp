<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>已注销交易商列表</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/trade/mfirm/expurgateFirmList.action?sortColumns=order+by+firmId&isQueryDB=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table4_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															<fmt:message key="MFirm.firmId" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.firmId[allLike]"
																	value="${oldParams['primary.firmId[allLike]']}" maxLength="<fmt:message key='firmId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<fmt:message key="MFirm.name" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<input type="text" class="input_text" id="name"
																	name="${GNNT_}primary.name[allLike]" 
																	value="${oldParams['primary.name[allLike]'] }" maxLength="<fmt:message key='firmName_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<fmt:message key="MFirm.type" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<select id="type" name="${GNNT_}primary.type[=][int]"  class="normal" style="width: 100px">
																	<option value="">全部</option>
																	<c:forEach items="${mfirmTypeMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																</select>
															</label>
															<script >
																frm.type.value="<c:out value='${oldParams["primary.type[=][int]"]}' />"
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
						&nbsp;<br/>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="firm"
											action="${basePath}/trade/mfirm/expurgateFirmList.action?sortColumns=order+by+firmId&isQueryDB=true"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firm.xls" csvFileName="firm.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												<ec:column width="5%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="firmId" title="MFirm.firmId" width="10%"
													style="text-align:left;" ellipsis="true">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/mfirm/updateForwardMfirmDetails.action" id="detail" text="<font color='#880000'>${firm.firmId}</font>" onclick="return query('${firm.firmId}');"/>	
												</ec:column>
												<ec:column property="name" title="MFirm.name" width="10%"
													style="text-align:left;" ellipsis="true">
													${firm.name}
												</ec:column>
												<ec:column property="fullName" title="MFirm.fullName" width="10%"
													style="text-align:left;" ellipsis="true"/>
												<ec:column property="type" title="MFirm.type" width="6%"
													style="text-align:center;">
													${mfirmTypeMap[firm.type] }
												</ec:column>
												<ec:column property="createTime" title="MFirm.createTime" width="10%"
													style="text-align:center;">
													<fmt:formatDate value="${firm.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="modifyTime" title="MFirm.modifyTime" width="10%"
													style="text-align:center;">
													<fmt:formatDate value="${firm.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
		function query(id){
			var a=document.getElementById("detail").action;
			var url = "${basePath}"+a+"?isQueryDB=true&entity.firmId="+id;
			if(showDialog(url, "", 550, 490)){
				frm.submit();
		};
	}
     </SCRIPT>
	</body>
</html>