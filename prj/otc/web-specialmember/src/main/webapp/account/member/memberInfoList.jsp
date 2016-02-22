<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>会员列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/account/memberInfo/list.action?sortName=compMember.id"
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
																<input name="${GNNT_}id[like]" type="text"
																	class="input_text" id="memberInfoId" size="14"
																	value="${oldParams['memberInfo.id[like]'] }" />
															</label>
														</td>
														<td class="table3_td" align="right">
															会员名称:&nbsp;
														</td>
														<td class="table3_td2">
															<label>
																<input type="text" id="memberInfoName"
																	class="input_text" name="${GNNT_}name[like]"
																	value="${oldParams['memberInfo.name[like]'] }"
																	size="14" />
															</label>
														</td>
														<td class="table3_td" align="right">
															<button class="btn_sec" onclick="submitMember()">
																查询
															</button>
														</td>
														<td class="table3_td2" align="left">
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
							<button id="addMember" class="anniu_btn" onclick="addMember()">
								添加
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr valign="top">
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="memberInfo" width="100%"
											action="${basePath}/account/memberInfo/list.action?sortName=compMember.id"
											title="" listWidth="150%" height="460" minHeight="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">

											<ec:row recordKey="${memberInfo.id}"
												ondblclick="return forwardUpdate('${memberInfo.id}');">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${memberInfo.id}" style="text-align:center; "
													width="2%" viewsAllowed="html" />
												<ec:column property="memberInfo.id[like]" width="10%"
													title="会员编号" style="text-align:center; "
													value="${memberInfo.id}" />
												<ec:column property="name[like]" width="8%" title="会员名称"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.name}" tipTitle="${memberInfo.name}"/>
												<ec:column property="memberType[=][String]" width="8%"
													title="会员类型" style="text-align:center;"
													editTemplate="ecs_memberType">
													${accountMemberTypeMap[memberInfo.memberType]}
												</ec:column>
												<ec:column property="papersType[=][int]" width="8%"
													title="证件类型" style="text-align:center;"
													editTemplate="ecs_papersType">
													<c:set var="typeKey">
														<c:out value="${memberInfo.papersType}"></c:out>
													</c:set>
							  						${accountPapersTypeMap[typeKey]}
												</ec:column>
												<ec:column property="papersName[like]" width="10%"
													title="证件号码" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${memberInfo.papersName}" tipTitle="${memberInfo.papersName}"/>
												<ec:column property="address[like]" width="11%" title="地址"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.address}" tipTitle="${memberInfo.address}"/>
												<ec:column property="phone[like]" width="7%" title="电话"
													style="text-align:center;" value="${memberInfo.phone}" />
												<ec:column property="fax[like]" width="7%" title="传真"
													style="text-align:center;" value="${memberInfo.fax}" />
												<ec:column property="postCode[like]" width="7%" title="邮编"
													style="text-align:center;" value="${memberInfo.postCode}" />
												<ec:column property="email[like]" width="8%" title="电子邮箱"
													style="text-align:center;" value="${memberInfo.email}" />
												<%--<ec:column property="_" title="关联特别会员"
													style="text-align:center" width="8%">
													<button onclick="contactSMember('${memberInfo.id}')">
														关联
													</button>
												</ec:column>
											--%>
												<ec:column property="compMember.status[=]" title="会员状态"
													width="11%" style="text-align:center;"
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
			<!-- 编辑和过滤所使用的会员类型模板 -->
			<textarea id="ecs_memberType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="memberType[=][String]">
			<ec:options items="accountMemberTypeMap" />
		</select>
	    </textarea>
			<!-- 编辑和过滤所使用的会员证件类型 模板 -->
			<textarea id="ecs_papersType" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="papersType[=][String]">
				<ec:options items="accountPapersTypeMap" />
			</select>
	    </textarea>
			<!-- 编辑和过滤所使用的会员状态模板 -->
			<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="compMember.Status[=]">
			<ec:options items="memberStatusMap" />
		</select>
	    </textarea>
			<SCRIPT type="text/javascript">
		function contactSMember(id){
			var url="${basePath}/account/memberRelation/updateForward.action?id="+id;
			var result=ecsideDialog(url,"",600,500);
			if(result>0)
			{
			   ec.submit();	
			}
		}
		function forwardUpdate(id){
			var url="${basePath}/account/memberInfo/forwardUpdate.action?obj.id="+id;
			var result=ecsideDialog(url,"",650,600);
			if(result>0)
			{
			   ec.submit();	
			}
		}
		
		function addMember(){
			var url="${basePath}/account/memberInfo/forwardAdd.action";
			var result=ecsideDialog(url,"",650,500);
			if(result>0){
			   frm.submit();	
			}
		}
		function deleteByCheckBox(){
	 		var url="${basePath}/account/memberInfo/delete.action"
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>
<script type="text/javascript">
function submitMember() {
	frm.submit();
}
function resetSelect() {
	frm.memberId.value = "";
	frm.memberName.value = "";
	frm.submit();
}
</script>