<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��־�б�</title>
		<script type="text/javascript" src="<%=publicPath%>/js/jquery-1.6.min.js"></script>
	</head>
	<body  onload="change('${type }');getFocus('operator');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/log/list.action?sortColumns=order+by+id&isQueryDB=true"
								method="post">
								<input type="hidden" name="pageSize" value="${pageInfo.pageSize}">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
													<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��ѯ��Χ:&nbsp;
															<label>
																<select name="type" size="1" id="type"
																	style="width: 120" onchange="changeOn()">
																	<option value="D">
																		��ǰ
																	</option>
																	<option value="H">
																		��ʷ
																	</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="beginDate"
																	name="${GNNT_}primary.operateTime[>=][date]"
																	value="${oldParams['primary.operateTime[>=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="endDate"
																	name="${GNNT_}primary.operateTime[<=][datetime]"
																	value="${oldParams['primary.operateTime[<=][datetime]'] }" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������:&nbsp;
															<label>
																<input type="text" class="input_text" id="operator"
																	name="${GNNT_}primary.operator[allLike]"
																	value="${oldParams['primary.operator[allLike]'] }"  maxLength="<fmt:message key='operator_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<!-- 
														<td align="left" class="table3_td_1">
															����ģ��:&nbsp;
															<select id="moudleId"
																name="${GNNT_}primary.logCatalog.tradeModule.moduleId[=][int]"
																class="input_text" onchange="getLogCatalogs()">
																<option value="">
																	ȫ��
																</option>
															</select>
															<script type="text/javascript">
																document.getElementById('moudleId').value="${oldParams['primary.logCatalog.logMoudle.moudleID[=][int]'] }";
															</script>
														</td>
														 -->
														<td align="left" class="table3_td_1" id="opetype">
															��������:&nbsp;
															<select id="catalogID"
																name="${GNNT_}primary.logCatalog.catalogID[=][int]" value="${oldParams['primary.logCatalog.catalogID[=][int]'] }" class="input_text">
																<option value="">
																	ȫ��
																</option>
																<c:forEach items="${logList}" var="opetype">
																	<c:if test="${oldParams['primary.logCatalog.catalogID[=][int]']== opetype.catalogID}">
																		<option value="${opetype.catalogID }" selected="selected">${opetype.catalogName }</option>
																	</c:if>
																	<c:if test="${oldParams['primary.logCatalog.catalogID[=][int]']!= opetype.catalogID}">
																		<option value="${opetype.catalogID }">${opetype.catalogName }</option>
																	</c:if>
																</c:forEach>
															</select>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																����
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
											action="${basePath}/log/list.action?sortColumns=order+by+id&isQueryDB=true"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="operateLog.xls" csvFileName="operateLog.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="operator" title="������" width="10%" ellipsis="true"  style="text-align:left;"/>
												<ec:column property="operateIp" title="������Ip" width="10%" style="text-align:left;"/>
												<ec:column property="operateTime" title="����ʱ��" width="13%" style="text-align:left;" sortable="true" filterable="false" >
													<fmt:formatDate value="${operateLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="logCatalog.tradeModule.cnName" title="����ģ��" width="10%" >
												</ec:column>
												 <ec:column property="operatorType" title="����������" width="10%">
													${com_operatorTypeMap[operateLog.operatorType]}
												</ec:column>
												<ec:column property="logCatalog.catalogName" title="��������" width="10%" style="text-align:center;" ellipsis="true" />
												<ec:column property="operateResult" title="�������" width="6%">
													<c:if test="${operateLog.operateResult ==1}">
														�����ɹ�
													</c:if>
													<c:if test="${operateLog.operateResult ==0}">
														����ʧ��
													</c:if>
												</ec:column>
												<ec:column property="operateContent" title="��������" width="30%" ellipsis="true" style="text-align:center;"/>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
			</textarea>
			<!-- �༭״̬����ģ�� -->
			<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="status">
				<ec:options items="CUSTOMER_STATUS" />
			</select>
		</textarea>
		<SCRIPT type="text/javascript">
			
			function select1(){
				frm.submit();
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