<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>֤������ά���б�</title>
	</head>
	<body  onload="getFocus('code');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/mfirmAttribute/mainTenance/certificateTypeList.action?sortColumns=order+by+sortNo"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table3_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															֤�����ͱ��:&nbsp;
															<label>
																<input type="text" class="input_text" id="code" style="width: 115px;"
																	name="${GNNT_}primary.code[allLike]" 
																	value="${oldParams['primary.code[allLike]'] }" maxLength="<fmt:message key='code_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															�Ƿ��ѡ��:&nbsp;
															<label>
																<select id="isvisibal" name="${GNNT_}primary.isvisibal[=][String]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${isvisibalMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.isvisibal.value = "<c:out value='${oldParams["primary.isvisibal[=][String]"] }'/>"
					  										</script>
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
						<div class="div_gn">
						<rightButton:rightButton name="���" onclick="addCertificateType();"
								className="anniu_btn" action="/mfirmAttribute/mainTenance/addForwardCertificateType.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deleteCertificateTypes()" className="anniu_btn" action="/mfirmAttribute/mainTenance/deleteCertificateTypes.action" id="deletes"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="certificateType"
											action="${basePath}/mfirmAttribute/mainTenance/certificateTypeList.action?sortColumns=order+by+sortNo"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="certificateType.xls" csvFileName="certificateType.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${certificateType.code}"
													width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="code" width="10%" title="֤�����ͱ��"
													style="text-align:left;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/mfirmAttribute/mainTenance/updateForwardCertificateType.action" id="detail" text="<font color='#880000'>${certificateType.code}</font>" onclick="return update('${certificateType.code}');"/>
												</ec:column>
												<ec:column property="name" title="֤����������" width="10%"
													style="text-align:left;"  ellipsis="true">
												</ec:column>
												<ec:column property="isvisibal" title="�Ƿ��ѡ��" width="10%"
													style="text-align:left;"  ellipsis="true">
													<c:set var="key">
														<c:out value="${certificateType.isvisibal}" />
													</c:set>
													${isvisibalMap[key]}
												</ec:column>
												<ec:column property="sortNo" title="�����" width="10%"
													style="text-align:left;"  ellipsis="true">
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


			<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>


<SCRIPT type="text/javascript">
	function addCertificateType(){
	var a=document.getElementById('add').action;
		var url = "${basePath}"+a;
		if(showDialog(url, "", 650, 350)){
			frm.submit();
		};
	}
	//�޸�֤��������Ϣ
	function update(id){
		var a=document.getElementById("detail").action;
		var url = "${basePath}"+a+"?entity.code="+id;
		if(showDialog(url, "", 650, 350)){
			frm.submit();
		};
	}
	//�޸�֤�������Ƿ��ѡ��
	function updateIsvisibal(id,value){
		var valid =affirm("��ȷ��Ҫ����֤�����͡�"+value+"���Ĳ�����")
		if(valid){
			var url = "${basePath}/mfirmAttribute/mainTenance/updateIsvisibalCertificateType.action?entity.code="+id;
			frm.action =url;
			frm.submit();
		}
	}
	function deleteCertificateType(id){
		var  valid=affirm("��ȷ��Ҫɾ����");
		if(valid){
			var a=document.getElementById('delete').action;
			var url = "${basePath}"+a+"?entity.code="+id;
			frm.action=url;
			frm.submit();
		}
	}
	function deleteCertificateTypes(){
		var a =document.getElementById('deletes').action;
	  	var url="${basePath}"+a+"?autoInc=false";
	  	var collCheck=document.getElementsByName("ids");
	  	deleteEcside(collCheck,url);
	}
	function select1() {
		frm.submit();
	}
</SCRIPT>
	</body>
</html>