<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�����̲�����־</title>
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
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left">
															�����̴���:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.operator[allLike]"
																	value="${oldParams['primary.operator[allLike]']}" maxLength="<fmt:message key='firmId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
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
														<td align="left" class="table3_td_1" style="display: none;">
															��������:&nbsp;
															<select id="catalogID"
																name="${GNNT_}primary.logCatalog.catalogID[=][int]" class="input_text">
																<option value="">
																	ȫ��
																</option>
															</select>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();
>
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=
	myReset();
>
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
											action="${basePath}/logquery/${logMethod}/mfirmlogList.action?sortColumns=order by occurTime desc&isQueryDB=true"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="firmLog.xls" csvFileName="firmLog.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="operator" title="�����̴���" width="10%" style="text-align:left;"/>
												<ec:column property="operateTime" title="����ʱ��" width="15%" style="text-align:center;">
													<fmt:formatDate value="${operateLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="logCatalog.catalogName" title="��������" width="12%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="logCatalog.tradeModule.cnName" title="����ģ��" width="10%" />
												<ec:column property="operateResult" title="�������" width="10%">
													<c:if test="${operateLog.operateResult ==1}">
														�����ɹ�
													</c:if>
													<c:if test="${operateLog.operateResult ==0}">
														����ʧ��
													</c:if>
												</ec:column>
												<ec:column property="operateContent" title="��������" width="32%" ellipsis="true" style="text-align:center;" alias="[allLike]"/>
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
	function select1() {
			checkQueryDate(frm.beginDate.value,frm.endDate.value);
	}
	refileselected();
	function refileselected(){
		$.ajaxSettings.async = false;
		getLogMoudles();
		//д��ԭ��ѯģ������
		document.getElementById("moudleId").value="${oldParams['primary.logCatalog.tradeModule.moduleId[=][int]'] }";
		getLogCatalogs();
		//д��ԭ��ѯ��������
		document.getElementById("catalogID").value="${oldParams['primary.logCatalog.catalogID[=][int]'] }";
	}
	/**
	 * ��ȡģ����Ϣͬ���������б���
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
	 * ����ģ�����޸Ĳ������������б�
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
	 * ��ղ������������б�
	 */
	function clearLogCatalogs(){
		var catalogID = document.getElementById("catalogID");
		catalogID.length = 0;
		var op = new Option("ȫ��",""); 
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