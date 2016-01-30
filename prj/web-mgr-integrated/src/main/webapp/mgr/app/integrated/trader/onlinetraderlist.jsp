<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>在线交易员信息列表</title>
		<style type="text/css">
			.style1{
				text-align:center;
			}
		</style>
	</head>
	<body onload="getFocus('TraderId');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
						<form name="frm" action="${basePath}/trade/trader/online/onlineList.action"
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
														交易员代码:&nbsp;
														<label>
															<input name="${GNNT_}TraderId[allLike]" id="TraderId" type="text"
																class="input_text" 
																value="<c:out value="${oldParams['TraderId[allLike]']}"/>"  maxLength="<fmt:message key='traderId_q' bundle='${PropsFieldLength}'/>">
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
							<rightButton:rightButton name="强制下线" onclick="downTrader()" className="anniu_btn" action="/trade/trader/online/downOnlineTrader.action" id="downline"></rightButton:rightButton>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="trader"
											action="${basePath}/trade/trader/online/onlineList.action"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="onlinetrader.xls" csvFileName="onlinetrader.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row recordKey="${trader.traderId}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${trader.traderId}"
													width="5%" viewsAllowed="html" />
												<ec:column width="8%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="traderId" width="23%" title="交易员代码"
													style="text-align:left; ">
												</ec:column>
												
												<ec:column property="logonTime" title="登录时间" width="25%"
													style="text-align:center;">
												</ec:column>
												<ec:column property="logonIp" title="登录IP" width="25%"
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
	function downTrader(){
	  var a=document.getElementById('downline').action;
		var url="${basePath}" +a+"?autoInc=false";
	 downOnline(ec.ids,url);
	}
</SCRIPT>