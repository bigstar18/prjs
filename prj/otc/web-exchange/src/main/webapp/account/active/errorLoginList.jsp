<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>交易商激活</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/active/list.action"
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
														交易账号:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="traderId" name="${GNNT_}traderid[like]"
																value="${oldParams['traderid[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">查询</button>&nbsp;&nbsp;
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
						<div class="div_gn">
							<button class="anniu_btn" onclick="activeMany()" id="activeMany">
								解锁
							</button>
							<button class="anniu_btn" onclick="activeAll()" id="activeAll">
								全部解锁
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="list"
											autoIncludeParameters="${empty param.autoInc}" var="errorLogin"
											action="${basePath}/account/active/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">
											<ec:row recordKey="${traderId}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${errorLogin.traderid}" width="2%"
													viewsAllowed="html" />
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="traderId[Like]" width="10%" title="交易账号"
													style="text-align:left;" value="${errorLogin.traderid}"/>
												<ec:column property="loginDate[Like]" width="8%" title="登录日期"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" filterable="false">
													<fmt:formatDate value="${errorLogin.loginDate}" pattern="yyyy-MM-dd"/>
												</ec:column>
												<ec:column property="1" width="10%"
													title="展示详情" style="text-align:center;" filterable="false">
													<a href="#" class="blank_a"
														onclick="return detail('${errorLogin.traderid}');"><font color="#880000">详情</font>
													</a>
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
		<SCRIPT type="text/javascript">
		function detail(id){
			var url = "${basePath}/account/active/detail.action?traderid="+id;
			ecsideDialog(url,"",500,500);
		}
		
		function activeMany(){
			var collCheck=document.getElementsByName("ids");
			var url = "${basePath}/account/active/active.action?manyOrAll=many";
			if(isSelNothing(collCheck) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return false;
			}
			if(isSelNothing(collCheck))
			{
				alert ( "请选择需要操作的数据！" );
				return false;
			}
			if(confirm("你确定要解锁吗？"))
			{
				ec.action=url;
				ec.submit();
			}
		}
		function activeAll(){
			var url = "${basePath}/account/active/active.action?manyOrAll=all";
			if(${GLOBALROWCOUNT}>0){
				if(confirm("你确定要解锁吗？"))
				{
					ec.action=url;
					ec.submit();
				}
			}else{
				alert('没有可操作数据');
			}
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>