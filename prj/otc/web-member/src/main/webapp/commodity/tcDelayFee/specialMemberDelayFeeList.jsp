<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>特别会员延期费设置列表</title>
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
								action="${basePath}/commodity/specialMemberDelayFee/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1mid" align="left">
															特别会员名称:&nbsp;
															<label>
																<input type="text" class="input_text"
																	id="memberName" name="${GNNT_}specialMember.name[like]"
																	value="${oldParams['specialMember.name[like]'] }">
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1()">
																查询
															</button>&nbsp;&nbsp;
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
						<div class="div_gn">
							<button class="anniu_btn" onclick="addDelayFee()" id="update">
								添加
							</button>
							<button class="anniu_btn" onclick="deleteDelayFee()" id="delete">
								删除
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="tcDelayFee"
											action="${basePath}/commodity/specialMemberDelayFee/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="tcDelayFee.memberNo">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" title="商品名称" width="24%" filterable="false" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return update('${tcDelayFee.memberNo}','${tcDelayFee.commodityId}');"><font color="#880000">${tcDelayFee.commodityName }</font> </a> </ec:column>
												<ec:column property="specialMember.name[like]" title="特别会员名称"
													style="text-align:left;" filterable="false" ellipsis="true" width="24%"
													value="${tcDelayFee.firmName}" />
												<c:forEach var="ta" begin="1" end="${total}" step="1">
													<c:set var="step" value="stepNO${ta}"></c:set>
													<c:forEach var="fundder" items="${ladderList}">
														<c:if test="${fundder.stepNo==ta}">
															<c:set var="note" value="${fundder.note}"></c:set>
														</c:if>
												</c:forEach>
													<ec:column property="stepNO${ta}[=]"  title="${note}的延期费率"
														style="text-align:right;" filterable="false" width="24%"
														sortable="false" value="${tcDelayFee[step]}%" />
												</c:forEach>
											</ec:row>
										</ec:table>
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
					</td>
				</tr>
			</table>
		</div>

<script type="text/javascript">
function update(memberNo, commodityId) {
	var url = "${basePath}/commodity/specialMemberDelayFee/forwardUpdate.action?obj.firmId="
			+ memberNo + "&obj.commodityId=" + commodityId;
	ecsideDialog(url, "", 580, 358);
}
function select1() {
	frm.submit();
}
function addDelayFee(){
	var url = "${basePath}/commodity/specialMemberDelayFee/forwardAdd.action";
	ecsideDialog(url, "", 580, 330);
}

function deleteDelayFee(){
	var url="${basePath}/commodity/specialMemberDelayFee/delete.action";
	deleteEcside(ec.ids,url);
}
</script>
	</body>
</html>