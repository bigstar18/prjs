<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.broker.common.Page"%>
<%@page import="gnnt.MEBS.integrated.broker.model.fundsQuery.ClientLedger"%>
<%@page import="gnnt.MEBS.integrated.broker.model.fundsQuery.LedgerField"%>
<%@page import="gnnt.MEBS.integrated.broker.model.fundsQuery.FirmBalance"%>
<%@page import="java.util.Set"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商总账单</title>
		<SCRIPT type="text/javascript">
		<!-- 
			
			//执行查询列表
			function dolistquery() {
				frm.submit();
			}
			function load(){
				  if( ${oldParams['primary.b_Date[>=][date]']==null}&& ${oldParams['primary.b_Date[<=][datetime]']==null}){
						frm.beginDate.value=new Date().format("yyyy-MM-dd");
						frm.endDate.value=new Date().format("yyyy-MM-dd");
				  }
			}
		//-->
		</SCRIPT>
	</head>
	<body onload="load();">
	
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/broker/clientLedger/clientLedger.action?sortColumns=order+by+b_Date" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 1000">
													<tr>
													<td class="table3_td_1" align="left" >
															&nbsp;&nbsp;&nbsp;开始日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[>=][date]"
																	value='${oldParams["primary.b_Date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
														&nbsp;&nbsp;&nbsp;结束日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[<=][datetime]"
																	value='${oldParams["primary.b_Date[<=][datetime]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left" >
														&nbsp;&nbsp;系统名称:&nbsp;
															<label>
																<select type="text" class="input_text" id="moduleId"
																	name="moduleId">
																	<option value="">全部</option>
																	<c:forEach var="list" items="${moduleList}">
																		<option value="${list.moduleId}">${list.name }</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("moduleId").value="${moduleId}";
														</script>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
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
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="firmBalance"
											action="${basePath}/broker/clientLedger/clientLedger.action?sortColumns=order+by+b_Date"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firmBalance.xls" csvFileName="firmBalance.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="b_Date" title="发生日期 "  style="text-align:center;">
													<fmt:formatDate value="${firmBalance.b_Date}" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="firmId" title="交易商代码" style="text-align:left;" ellipsis="true" width="110"/>
												<ec:column property="lastBalance" title="期初余额"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.lastBalance}" pattern="#,##0.00"/>
												</ec:column>
												<c:set var="moduleMoney" value="0"></c:set>
												<c:forEach items="${fieldList}" var="lf">
												<c:set var="flag" value="-1"></c:set>
												<c:if test="${lf.fieldSign==-1}"><c:set var="name" value="-${lf.name}"></c:set></c:if>
												<c:if test="${lf.fieldSign==1}"><c:set var="name" value="+${lf.name}"></c:set></c:if>
									 				<ec:column property="_" title="${name}" style="text-align:right;" width="120">
														<c:forEach items="${firmBalance.clientLedger}" var="cl"> 
															<c:choose> 
																<c:when test="${lf.code == cl.code }">   
													               <fmt:formatNumber value="${cl.value}" pattern="#,##0.00"/>
													               <c:set var="flag" value="0"></c:set>
													               <c:set var="moduleMoney" value="${cl.value*lf.fieldSign+moduleMoney}"></c:set>
													            </c:when>  
													        </c:choose> 
												        </c:forEach> 
												        <c:if test="${flag==-1}"><fmt:formatNumber value="0" pattern="#,##0.00"/></c:if> 
												     </ec:column>
									 			</c:forEach>
									 			<c:if test="${moduleId!=null&&moduleId!=''}">
									 				<ec:column property="_" title="+其他交易系统"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.todayBalance-firmBalance.lastBalance-moduleMoney}" pattern="#,##0.00"/>
												</ec:column>
									 			</c:if>
												<ec:column property="todayBalance" title="期末余额"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.todayBalance}" pattern="#,##0.00"/>
												</ec:column>
												<c:if test="${moduleId==null||moduleId==''||moduleId=='15'}">
												<ec:column property="lastWarranty" title="期初担保金"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.lastWarranty}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="todayWarranty" title="期末担保金" style="text-align:right;" width="120">
													<fmt:formatNumber value="${firmBalance.todayWarranty}" pattern="#,##0.00"/>
										  		</ec:column>
										  		</c:if>
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
	</body>
</html>