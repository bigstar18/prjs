<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��Ʒ�������������б�</title>
	</head>
	<body  onload="getFocus('name');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/category/propertytype/list.action?sortColumns=order+by+sortNo"
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
															������������:&nbsp;
															<label>
																<input type="text" class="input_text" id="name" style="width: 115px"
																	name="${GNNT_}primary.name[allLike]" 
																	value="${oldParams['primary.name[allLike]'] }" maxLength="32' bundle='${PropsFieldLength}'/>"/>
															</label>
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
						<rightButton:rightButton name="���" onclick="PropertyType();"
								className="anniu_btn" action="/category/propertytype/addForwardPropertyType.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deletePropertyType();"
								className="anniu_btn" action="/category/propertytype/deletePropertyType.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="propertyType"
											action="${basePath}/category/propertytype/list.action?sortColumns=order+by+sortNo"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="propertyType.xls" csvFileName="propertyType.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${propertyType.propertyTypeID}"
													width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="propertyTypeID" width="10%" title="�������ͱ��"
													style="text-align:left;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/category/propertytype/updateForwardPropertyType.action" id="detail" text="<font color='#880000'>${propertyType.propertyTypeID}</font>" onclick="return update('${propertyType.propertyTypeID}');"/>
												</ec:column>
												<ec:column property="name" title="������������" width="10%"
													style="text-align:left;"  ellipsis="true">
													${propertyType.name}
												</ec:column>
												<ec:column property="sortNo" title="�����" width="10%"
													style="text-align:left;"  ellipsis="true">
													${propertyType.sortNo}
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
	function PropertyType(){
	var a=document.getElementById('add').action;
		var url = "${basePath}"+a;
		if(showDialog(url, "", 600, 400)){
			frm.submit();
		};
	}
	function update(id){
		var a=document.getElementById("detail").action;
		var url = "${basePath}"+a+"?entity.propertyTypeID="+id;
		if(showDialog(url, "", 600, 400)){
			frm.submit();
		};
	}
	function deletePropertyType(){
		var a=document.getElementById('delete').action;
		var url = "${basePath}"+a+"?autoInc=false";
		updateRMIEcside(ec.ids,url);
	}
	function select1() {
		frm.submit();
	}
</SCRIPT>
	</body>
</html>