<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css" />

<html>
	<head>
		<title>会员列表</title>
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
								action="${basePath}/account/memberChangeOrder/list.action"
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
																id="memberInfoId"
																name="${GNNT_}primary.id[like]"
																value="${oldParams['primary.id[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														会员名称:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" class="input_text"
																id="memberInfoName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="select1()">查询</button>
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
											var="memberInfo"
											action="${basePath}/account/memberChangeOrder/list.action"
											title="" minHeight="460" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">

											<ec:row recordKey="${memberInfo.id}"
												ondblclick="return contactSMember('${memberInfo.id}')">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${memberInfo.id}" style="text-align:center; "
													width="2%" viewsAllowed="html" />
												<ec:column property="memberNo[like]" width="5%" title="会员编号"
													style="text-align:center;" value="${memberInfo.id}" />
												<ec:column property="name[like]" width="5%" title="会员名称"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${memberInfo.name}" tipTitle="${memberInfo.name}"/>
												<ec:column property="memberType[=][String]" width="5%"
													title="会员类型" style="text-align:center;"
													editTemplate="ecs_memberType">
													${accountMemberTypeMap[memberInfo.memberType]}
												</ec:column>
												<ec:column property="_" title="转单分配"
													style="text-align:center" width="5%">
													<button onclick="contactSMember('${memberInfo.id}')">
														<font color="red">转单分配</font>
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

		<SCRIPT type="text/javascript">
		
		function contactSMember(id){
			var url="${basePath}/account/memberChangeOrder/forwardUpdate.action?id="+id;
			var result=ecsideDialog(url,"",600,500);
			if(result>0)
			{
			   ec.submit();	
			}
		}
		function select1(){
			frm.submit();
		}
		function addMember(){
			var url="${basePath}/account/memberInfo/forwardAdd.action";
			var result=ecsideDialog(url,"",600,500);
			if(result>0)
			{
			   ec.submit();	
			}
		}
		function deleteByCheckBox(){
	 		var url="${basePath}/account/memberInfo/delete.action"
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>