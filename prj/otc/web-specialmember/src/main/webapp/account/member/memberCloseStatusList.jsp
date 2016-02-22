<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>会员状态列表</title>
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
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/memberCloseStatus/list.action?sortName=memberInfo.memberType"
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
														会员编号:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" class="input_text"
																id="memberId"
																name="${GNNT_}id[like]" value="${oldParams['id[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														会员名称:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" class="input_text"
																id="memberName"
																name="${GNNT_}name[like]"
																value="${oldParams['name[like]'] }" size="14" />
														</label>
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="submitMember()">查询</button>
													</td>
													<td class="table3_td2" align="left">
														<button class="btn_cz" onclick="myReset()">重置</button>
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
											var="memberInfo" width="100%"
											action="${basePath}/account/memberCloseStatus/list.action?sortName=memberInfo.memberType"
											minHeight="460" listWidth="100%" height="460px"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row recordKey="${memberInfo.id}"
												ondblclick="return update('${memberInfo.id}');">
												<ec:column property="memberNo[like]" title="会员编号"
													width="20%" style="text-align:center; "
													value="${memberInfo.id}" />
												<ec:column property="name[like]" title="会员名称" width="20%"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.name}" tipTitle="${memberInfo.name}"/>
												<ec:column property="address[like]" title="地址" width="20%"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.address}" tipTitle="${memberInfo.address}"/>
												<ec:column property="compMember.status[=]" title="会员状态"
													width="20%" style="text-align:center;"
													editTemplate="ecs_t_statusType">
													${memberStatusMap[memberInfo.compMember.status]}
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
		<!-- 编辑和过滤所使用的会员状态模板 -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="compMember.Status[=]">
			<ec:options items="memberStatusMap" />
		</select>
	    </textarea>
	</body>
</html>
	    
		<script type="text/javascript">
function submitMember() {
	frm.submit();
}
function update(id) {
	var url = "${basePath}/account/memberCloseStatus/forwardUpdateClose.action?obj.id="
			+ id;
	ecsideDialog(url);
}
</script>