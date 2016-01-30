<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>仓单日志列表</title>
	</head>
	<body onload="change('${type }');getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/stock/log/list.action?sortColumns=order+by+createTime&isQueryDB=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" >
									<tr>
										<td class="table2_td_width" style="width: 100%">
											<div class="div2_top" style="margin-left: 0px">
												<table class="table3_style" border="0" cellspacing="0" 
													cellpadding="0" width="100%">
													<tr >
													<td class="table3_td_1" align="right">
															查询范围：&nbsp;
															<label>
																<select name="type" size="1" id="type" style="width: 110px;"
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
																	name="${GNNT_}primary.operateTime[>=][date]" style="width: 110px;"
																	value="${oldParams['primary.operateTime[>=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="endDate"
																	name="${GNNT_}primary.operateTime[<=][datetime]" style="width: 110px;"
																	value="${oldParams['primary.operateTime[<=][datetime]'] }" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="right">
															操作人：
															<label>
																<input id="id" type="text" class="input_text" style="width: 110px;"
																maxLength="<fmt:message key='operator_q' bundle='${PropsFieldLength}'/>" 
																	name="${GNNT_}primary.operator[allLike]"
																	value="${oldParams['primary.operator[allLike]']}" />
															</label>
														</td>
														
														<td class="table3_td_anniu" align="right">
															<button class="btn_sec" onclick=select1();>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
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
						&nbsp;<br/>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="operateLog"
											action="${basePath}/stock/log/list.action?sortColumns=order+by+createTime&isQueryDB=true"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="stock.xls" csvFileName="stock.csv" 
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="8%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="operator" title="操作人" width="15%" style="text-align:left;"/>
												<ec:column property="operateTime" title="操作时间" width="17%" style="text-align:center;" sortable="true" filterable="false" >
													<fmt:formatDate value="${operateLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="logCatalog.catalogName" title="操作类型" width="15%" style="text-align:center;" />
												<ec:column property="operateResult" title="操作结果" width="15%" style="text-align:center;" >
													<c:if test="${operateLog.operateResult==1}">操作成功</c:if>
													<c:if test="${operateLog.operateResult==0}">操作失败</c:if>
												</ec:column>
												<ec:column property="operateContent" title="操作内容" width="30%" ellipsis="true" style="text-align:left;" alias="[allLike]"/>
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
					name="" /></textarea>

<SCRIPT type="text/javascript">

function select1() {
		checkQueryDate(frm.beginDate.value,frm.endDate.value);
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
	  frm.type.value="D";
	}
	else if(value=='H')
	{
	   frm.type.value="H";
	}
}

</SCRIPT>
	</body>
</html>