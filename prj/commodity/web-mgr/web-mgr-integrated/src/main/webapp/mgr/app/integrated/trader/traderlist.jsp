<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>交易员信息列表</title>
		<style type="text/css">
			.style1{
				text-align:center;
			}
		</style>
	</head>
	<body onload="getFocus('traderId');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/trade/trader/list.action?sortColumns=order+by+createTime+desc"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
													<td class="table3_td_1" align="left">
														交易员代码:&nbsp;
														<label>
															<input name="${GNNT_}traderId[allLike]" id="traderId" type="text"
																class="input_text"
																value="${oldParams['traderId[allLike]']}" maxLength="<fmt:message key='traderId_q' bundle='${PropsFieldLength}'/>">
														</label>
													</td>
													<td class="table3_td_1" align="left">
														交易员名称:&nbsp;
														<label>
															<input name="${GNNT_}name[allLike]" id="name" type="text"
																class="input_text" maxlength="32"
																value="${oldParams['name[allLike]']}" maxLength="<fmt:message key='traderName_q' bundle='${PropsFieldLength}'/>">
														</label>
													</td>
													<td class="table3_td_1" align="left">
															交易员类型:&nbsp;
															<label>
																<select id="type" name="${GNNT_}primary.type[=][String]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${traderTypeMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.type.value = "<c:out value='${oldParams["primary.type[=][String]"] }'/>"
					  										</script>
													</td>
												</tr>
												<tr>
													<td class="table3_td_1" align="left">
															交易员状态:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][String]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${traderStatusMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][String]"] }'/>"
					  										</script>
														</td>
													<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名:&nbsp;
														<label>
															<input name="${GNNT_}primary.userId[allLike]" id="userId" type="text"
																class="input_text" maxLength="<fmt:message key='userId' bundle='${PropsFieldLength}'/>"
																value="${oldParams['primary.userId[allLike]']}">
														</label>
													</td>
													<td class="table3_td_1" align="left">
														所属交易商:&nbsp;
														<label>
															<input name="${GNNT_}primary.mfirm.firmId[allLike]" id="firmId" type="text"
																class="input_text" maxLength="<fmt:message key='firmId_q' bundle='${PropsFieldLength}'/>"
																value="${oldParams['primary.mfirm.firmId[allLike]']}">
														</label>
													</td>		
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=
	queryInfo();
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
						<div class="div_gn">
							<rightButton:rightButton name="添加" onclick="addTrader()" className="anniu_btn" action="/trade/trader/addTraderForward.action" id="addTraderBtn"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;
							<rightButton:rightButton name="禁止登录" onclick="forbideTrader()" className="anniu_btn" action="/trade/trader/forbideTrader.action" id="forbideTrader"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;
							<rightButton:rightButton name="恢复登录" onclick="recoverTrader()" className="anniu_btn"  action="/trade/trader/recoverTrader.action" id="recoverTrader"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="trader"
											action="${basePath}/trade/trader/list.action?sortColumns=order+by+createTime+desc"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="trader.xls" csvFileName="trader.csv"
											showPrint="true" listWidth="160%"
											minHeight="345" width="100%">
											<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${trader.traderId}"
													width="1%" viewsAllowed="html" />
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" style="text-align:center;"/>
											<ec:row recordKey="${trader.traderId}">
												<ec:column property="traderId" width="5%" title="交易员代码"
													style="text-align:left; " ellipsis="true">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/trader/updateForwardTrader.action" id="detail" text="<font color='#880000'>${trader.traderId}</font>" onclick="return updateTrader('${trader.traderId}');"/>
												</ec:column>
												<ec:column property="password" width="3%" title="修改密码"
													style="text-align:center; " sortable="false"
													filterable="false">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/trader/updateForwardTraderCodes.action" id="updateTraderCodes" text="<font color='#880000'>修  改</font>" onclick="return updateTraderCodes('${trader.traderId}');"/>
												</ec:column>												
												<ec:column property="userId" width="4%" title="用户名" style="text-align:left;" tipTitle="${trader.userId}"/>
												<ec:column property="name" width="5%" title="交易员名称" style="text-align:left;overflow:hidden;text-overflow:ellipsis"/>
												<ec:column property="mfirm.firmId" width="5%" title="所属交易商" style="text-align:left;overflow:hidden;text-overflow:ellipsis"  tipTitle="${trader.mfirm.firmId}"/>
												<ec:column property="type" width="4%" title="交易员类型" style="text-align:left;overflow:hidden;text-overflow:ellipsis">
													<c:set var="key">
														<c:out value="${trader.type}"></c:out>
													</c:set>
													${traderTypeMap[key] }
												</ec:column>
												<ec:column property="status" width="3%" title="状态" 
												style="text-align:center;overflow:hidden;text-overflow:ellipsis">
													<c:set var="key">
														<c:out value="${trader.status}"></c:out>
													</c:set>
													${traderStatusMap[key]}
												</ec:column>
												<ec:column property="enableKey" width="5%" title="是否启用Key" 
												style="text-align:left;overflow:hidden;text-overflow:ellipsis">
													<c:set var="key">
														<c:out value="${trader.enableKey}"></c:out>
													</c:set>
													${traderKeyMap[key]}
												</ec:column>
												<ec:column property="keyCode" width="4%" title="key值" 
												style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${trader.keyCode}" tipTitle="${trader.keyCode}"/>
												<ec:column property="createTime" width="5%" title="创建时间" 
												style="text-align:center;overflow:hidden;text-overflow:ellipsis" tipTitle="${trader.createTime}">
													<fmt:formatDate value="${trader.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="modifyTime" width="5%" title="修改时间" 
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" tipTitle="${trader.modifyTime}">
													<fmt:formatDate value="${trader.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
								<center>${message }</center>
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
			<!-- 编辑状态所用模板 -->
			<textarea id="ecs_t_status" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="status">
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>


<SCRIPT LANGUAGE="JavaScript">
<%--var ids = document.all("ids");
for(var i=0;i<ids.length;i++){
	if(ids[i].type=="hidden"){
			ids[i].value="";
		}
}--%>
	//修改交易员信息
	function updateTrader(traderid){
		var a=document.getElementById("detail").action;
		var url="<%=basePath%>"+a+"?entity.traderId="+traderid;
		ecsideDialog(url,"",650,380);
		
	}
	//修改交易员口令
	function updateTraderCodes(traderids){
		var a=document.getElementById("updateTraderCodes").action;
		var url="<%=basePath%>"+a+"?entity.traderId="+traderids;
		ecsideDialog(url,"",500,350);
	}
	//添加交易员
	function addTrader(){
		var a=document.getElementById('addTraderBtn').action;
		var url = "${basePath}"+a+"?autoInc=false";
		ecsideDialog(url,"",650,450);
	}
	//禁止登录
	function forbideTrader(){
		var a=document.getElementById('forbideTrader').action;
		var url = "${basePath}"+a+"?autoInc=false";
		updateRMIEcside(ec.ids,url);
		
	}
	//恢复登录
	function recoverTrader(){
		var a=document.getElementById('recoverTrader').action;
		var url = "${basePath}"+a+"?autoInc=false";
		updateRMIEcside(ec.ids,url);
	}
	
	function queryInfo(){
	  frm.submit();	
	}
</SCRIPT>
	</body>
</html>