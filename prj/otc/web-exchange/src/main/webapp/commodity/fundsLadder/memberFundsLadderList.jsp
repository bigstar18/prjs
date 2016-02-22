<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员出金阈值阶梯列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/memberFundsLadder/list.action"
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
														会员名称:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_}memberInfo.name[like]"
																id="memberName"
																value="${oldParams['memberInfo.name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
															<button  class="btn_sec" onclick="select1()">查询</button>&nbsp;&nbsp;
                                                           <button  class="btn_cz" onclick="myReset()">重置</button>
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
							<button class="anniu_btn" onclick="addfundsLadder()" id="add">
								添加
							</button>
							<button class="anniu_btn" onclick="deletefundsLadder()" id="add">
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
											var="stepDictionary"
											action="${basePath}/commodity/memberFundsLadder/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="${stepDictionary['memberNo'] }">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${stepDictionary['memberNo'] }" style="text-align:center; "
													width="2%" viewsAllowed="html" />
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="memberInfo.name[like]" title="会员名称" ellipsis="true" width="33%"  style="text-align:center;" filterable="false"> <a href="#" class="blank_a" onclick="return update('${stepDictionary['memberNo'] }');"><font color="#880000">${stepDictionary.memberName }</font> </a> </ec:column>
												<c:forEach var="ta" begin="1" end="${total}" step="1">
												<c:set var="step" value="stepNo${ta}"></c:set>
													<c:forEach var="fundder" items="${ladderList}">
														<c:if test="${fundder.stepNo==ta}">
														<c:set var="note" value="${fundder.note}"></c:set>
														</c:if>
													</c:forEach>
													<ec:column property="stepNo${ta}[=]" title="${note}的出金阈值比例"
														style="text-align:right;"  width="33%" 
														filterable="false" sortable="false" value="${stepDictionary[step]}%">
														</ec:column>
												</c:forEach>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
						<!-- 编辑和过滤所使用的会员类型模板 -->
						<textarea id="ecs_t_memberNameType" rows="" cols=""
							style="display: none">
							<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
									name="memberNo[like]">
								<ec:options items="firmDisMap" />
							</select>
					    </textarea>
					</td>
				</tr>
			</table>
		</div>
<script type="text/javascript">

function update(id) {
	var url = "${basePath}/commodity/memberFundsLadder/forwardUpdate.action?memberNo="+id;
	ecsideDialog(url,"",580,300);
}
function select1() {
	frm.submit();
}
function addfundsLadder(){
var url="${basePath}/commodity/memberFundsLadder/forwardAdd.action";
ecsideDialog(url,"",600,300);
}
function deletefundsLadder(){
var url="${basePath}/commodity/memberFundsLadder/delete.action";
deleteEcside(ec.ids,url);
}

</script>
	</body>
</html>