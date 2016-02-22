<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>特别会员列表</title>
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
								action="${basePath}/account/specialMemberInfo/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1mid" align="left">
															特别会员编号:&nbsp;
															<label>
																<input type="text" class="input_text"
																	id="SpecialMemberInfoId" name="${GNNT_}id[like]"
																	value="${oldParams['id[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1mid" align="left">
															特别会员名称:&nbsp;
															<label>
																<input type="text" class="input_text"
																	id="SpecialMemberInfoName" name="${GNNT_}name[like]"
																	value="${oldParams['name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="submitMember()">
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
							<button class="anniu_btn" onClick="addSpecialMember()" id="add">
								添加
							</button>
							&nbsp;&nbsp;
							<!-- <button  class="anniu_btn" onClick="deleteByCheckBox()" id="delete">删除</button> -->
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="specialMemberInfo"
											action="${basePath}/account/specialMemberInfo/list.action"
											title="" minHeight="345" listWidth="150%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">

											<ec:row recordKey="${specialMemberInfo.id}">
												<ec:column property="id[like]" width="5%" title="特别会员编号"
													style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return forwardUpdate('${specialMemberInfo.id}');"><font
														color="#880000">${specialMemberInfo.id}</font>
													</a>
												</ec:column>
												<ec:column property="name[Like]" width="7%" title="特别会员名称"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${specialMemberInfo.name}" tipTitle="${specialMemberInfo.name}"/>
												<ec:column property="papersType[=][int]" width="7%"
													title="证件类型" style="text-align:left;"
													editTemplate="ecs_papersType">
													<c:set var="typeKey">
														<c:out value="${specialMemberInfo.papersType}"></c:out>
													</c:set>
							  						${accountPapersTypeMap[typeKey]}
												</ec:column>
												<ec:column property="papersName[like]" width="7%"
													title="证件号码" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${specialMemberInfo.papersName}" tipTitle="${specialMemberInfo.papersName}"/>
												
												
												<ec:column property="email[like]" width="6%" title="电子邮箱"
													style="text-align:left;" value="${specialMemberInfo.email}" />
												<ec:column property="fax[like]" width="6%" title="传真机号"
													style="text-align:left;" value="${specialMemberInfo.fax}" />
												<ec:column property="postCode[like]" width="5%" title="邮政编码"
													style="text-align:left;"
													value="${specialMemberInfo.postCode}" />
												<ec:column property="phone[like]" width="6%" title="联系电话"
													style="text-align:left;" value="${specialMemberInfo.phone}" />
												<ec:column property="address[like]" width="6%" title="通讯地址"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${specialMemberInfo.address}" tipTitle="${specialMemberInfo.address}"/>
												<ec:column property="_" title="修改交易密码"
													style="text-align:center" width="6%">
													<a href="#" class="blank_a"
														onclick="forwardUpdatePW('${specialMemberInfo.id}')"><font color="#880000">修改密码</font></a>
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
			<!-- 编辑和过滤所使用的会员证件类型 模板 -->
			<textarea id="ecs_papersType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="papersType[=][int]">
			<ec:options items="accountPapersTypeMap" />
		</select>
	    </textarea>
			<textarea id="ecs_t_openstatus" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="openstatus[=][int]">
			<ec:options items="openstatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function clickText(){
		var url="";
		ecsideDialog(url);
	
		}
		function click1(){
			document.getElementById('name2').readOnly=true;
			document.getElementById('name2').style.background="#bebebe";
			document.getElementById('name2').value="";
			document.getElementById('name1').readOnly=false;
			document.getElementById('name1').style.background= "#FFFFFF";
		
		}
		function click2(){
			document.getElementById('name1').readOnly=true;
			document.getElementById('name1').style.background="#bebebe";
			document.getElementById('name1').value="";
			document.getElementById('name2').readOnly=false;
			document.getElementById('name2').style.background= "#FFFFFF";
	
		
		}
	
		function forwardUpdate(id){
			var url="${basePath}/account/specialMemberInfo/forwardUpdate.action?obj.s_memberNo="+id;
		    ecsideDialog(url,"",600,445);
		}
		
		function addSpecialMember(){
			var url="${basePath}/account/specialMemberInfo/forwardAdd.action";
		    ecsideDialog(url,"",600,445);
		}
			
		function deleteByCheckBox(){
	 		var url="${basePath}/account/specialMemberInfo/delete.action"
			deleteEcside(ec.ids,url);
		}
		function submitMember(){
			frm.submit();
		}
		function forwardUpdatePW(id){
			var url="${basePath}/account/specialMemberInfo/forwardUpdatePassward.action?obj.id="+id;
		    ecsideDialog(url,"",580,260);
		}
		</SCRIPT>
	</body>
</html>