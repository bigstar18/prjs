<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<body onload="getFocus('TraderId');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
						<form name="frm" action="${basePath}/user/online/list.action"
								method="post">
						<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														管理员代码:&nbsp;
														<label>
															<input name="${GNNT_}TraderId[like]" id="TraderId" type="text"
																class="input_text" 
																value="<c:out value="${oldParams['TraderId[like]']}"/>"  maxLength="<fmt:message key='userID_q' bundle='${PropsFieldLength}'/>">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="queryInfo()">查询</button>&nbsp;&nbsp;&nbsp;
														
														<button class="btn_cz" onclick="myReset()">重置</button>
													</td>
													<td class="table3_td_1" align="left">
														&nbsp;
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
							<rightButton:rightButton name="强制下线" onclick="downUser()" className="anniu_btn" action="/user/online/downOnlineUser.action" id="down"></rightButton:rightButton>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="user"
											action="${basePath}/user/online/list.action"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="onlineuser.xls" csvFileName="onlineuser.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" filterable="true" sortable="true">
											<ec:row recordKey="${user.userId}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${user.userId}"
													width="10%" viewsAllowed="html" />
												<ec:column width="15%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="userId" width="25%" title="管理员代码"
													style="text-align:left; ">
												</ec:column>
												<ec:column property="logonTime" title="登录时间" width="25%"
													style="text-align:center;">
												</ec:column>
												<ec:column property="loginIp" title="登录IP" width="25%"
													style="text-align:center;">
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
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="isForbid[=]">
				<ec:options items="com_isForbidMap" />
			</select>
	    </textarea>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	
	function queryInfo(){
	  frm.submit();	
	}
	
	function downUser(){
	  var a=document.getElementById('down').action;
		var url="<%=basePath%>"+a+"?autoInc=false";
	  var collCheck=document.getElementsByName("ids");
	  for(var i=0;i < collCheck.length;i++ )
		{ 
			if( collCheck[i].checked == true)
			{
				if(collCheck[i].value=='${CurrentUser.userId }'){
					alert('不能强制自己下线！');
					return false;
				}
			}
		}
	  downOnline(ec.ids,url);
	}
</SCRIPT>