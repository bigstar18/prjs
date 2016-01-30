<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商操作日志</title>
		<script type="text/javascript" src="<%=publicPath%>/js/jquery-1.6.min.js"></script>
	</head>
	<body  onload="getFocus('id');change('${type }')">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/logquery/${logMethod}/mfirmlogList.action?sortColumns=order by occurTime desc&isQueryDB=true"	
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table4_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;查询范围:&nbsp;
															<label>
																<select name="type" size="1" id="type"
																	style="width: 120" onchange="changeOn()">
																	<option value="D">
																		当前
																	</option>
																	<option value="H">
																		历史
																	</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="beginDate"
																	name="${GNNT_}primary.operateTime[>=][date]"
																	value="${oldParams['primary.operateTime[>=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="endDate"
																	name="${GNNT_}primary.operateTime[<=][datetime]"
																	value="${oldParams['primary.operateTime[<=][datetime]'] }" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left">
															交易商代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.operator[allLike]"
																	value="${oldParams['primary.operator[allLike]']}" maxLength="<fmt:message key='firmId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td align="left" class="table3_td_1">
															操作模块:&nbsp;
															<select id="moudleId"
																name="${GNNT_}primary.logCatalog.tradeModule.moduleId[=][int]"
																class="input_text" onchange="getLogCatalogs()">
																<option value="">
																	全部
																</option>
															</select>
															<script type="text/javascript">
																document.getElementById('moudleId').value="${oldParams['primary.logCatalog.logMoudle.moudleID[=][int]'] }";
															</script>
														</td>
														<td align="left" class="table3_td_1" style="display: none;">
															操作类型:&nbsp;
															<select id="catalogID"
																name="${GNNT_}primary.logCatalog.catalogID[=][int]" class="input_text">
																<option value="">
																	全部
																</option>
															</select>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();
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
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="operateLog"
											action="${basePath}/logquery/${logMethod}/mfirmlogList.action?sortColumns=order by occurTime desc&isQueryDB=true"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firmLog.xls" csvFileName="firmLog.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="operator" title="交易商代码" width="10%" style="text-align:left;"/>
												<ec:column property="operateTime" title="操作时间" width="15%" style="text-align:center;">
													<fmt:formatDate value="${operateLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="logCatalog.catalogName" title="操作类型" width="12%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="logCatalog.tradeModule.cnName" title="操作模块" width="10%" />
												<ec:column property="operateResult" title="操作结果" width="10%">
													<c:if test="${operateLog.operateResult ==1}">
														操作成功
													</c:if>
													<c:if test="${operateLog.operateResult ==0}">
														操作失败
													</c:if>
												</ec:column>
												<ec:column property="operateContent" title="操作内容" width="32%" ellipsis="true" style="text-align:center;" alias="[allLike]"/>
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
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
			</textarea>
			<!-- 编辑状态所用模板 -->
			<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="status">
				<ec:options items="CUSTOMER_STATUS" />
			</select>
		</textarea>
<SCRIPT type="text/javascript">
	function select1() {
			checkQueryDate(frm.beginDate.value,frm.endDate.value);
	}
	refileselected();
	function refileselected(){
		$.ajaxSettings.async = false;
		getLogMoudles();
		//写入原查询模块条件
		document.getElementById("moudleId").value="${oldParams['primary.logCatalog.tradeModule.moduleId[=][int]'] }";
		getLogCatalogs();
		//写入原查询类型条件
		document.getElementById("catalogID").value="${oldParams['primary.logCatalog.catalogID[=][int]'] }";
	}
	/**
	 * 获取模块信息同步到下拉列表中
	 */
	function getLogMoudles(){
		var moudleId = document.getElementById("moudleId");
		if(moudleId.length<=1){
			var len = moudleId.length;
			var url = "${basePath}/ajaxcheck/logquery/getLogMoudleList.action";
			url += "?logType="+${logtype};
			$.getJSON(url,null,function call(result){
				$.each(result,function(i,field){
					//if(result[i].moduleId !=11 && result[i].moduleId !=32 && result[i].moduleId !=98 && result[i].moduleId !=99 ){
						var op = new Option(field.cnName,field.moduleId);
						moudleId.options[len++]=op;
						op.title = field.cnName;
					//}
				});
			});
		}
	}
	/**
	 * 根据模块编号修改操作类型下拉列表
	 */
	function getLogCatalogs(){
		var moudleId = document.getElementById("moudleId").value;
		clearLogCatalogs();
		if(moudleId != ""){
			var catalogID = document.getElementById("catalogID");
			var len = catalogID.length;
			var url = "${basePath}/ajaxcheck/logquery/getLogCateLogByMoudleID.action";
			url += "?moudleId="+moudleId+"&logType="+${logtype};
			$.getJSON(url,null,function call(result){
				$.each(result,function(i,field){
					var op = new Option(field[0],field[1]);
					catalogID.options[len++]=op;
					op.title = field[0];
				});
			});
		}
	}
	/**
	 * 清空操作类型下拉列表
	 */
	function clearLogCatalogs(){
		var catalogID = document.getElementById("catalogID");
		catalogID.length = 0;
		var op = new Option("全部",""); 
		catalogID.options[0]=op;
	}
	function changeOn(){
		var todayHis=document.getElementById("type").value;
		change(todayHis);
	}
	function change(value){
		if( ${oldParams['primary.operateTime[>=][date]']==null}&& ${oldParams['primary.operateTime[<=][datetime]']==null}){
			frm.beginDate.value=new Date().format("yyyy-MM-dd");
			frm.endDate.value=new Date().format("yyyy-MM-dd");
		}
		if(value=='D')
		{
			frm.beginDate.disabled = true;
			frm.beginDate.style.backgroundColor = "#C0C0C0";
			frm.endDate.disabled = true;
			frm.endDate.style.backgroundColor = "#C0C0C0";
		  frm.type.value="D";
		}
		else if(value=='H')
		{
			frm.beginDate.disabled = false;
			frm.beginDate.style.backgroundColor = "white";
			frm.endDate.disabled = false;
			frm.endDate.style.backgroundColor = "white";
		   frm.type.value="H";
		}
	}

</SCRIPT>
	</body>
</html>