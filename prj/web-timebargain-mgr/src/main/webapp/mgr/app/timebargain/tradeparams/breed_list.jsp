<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>

<title>Ʒ�ֹ����б�</title>
<script type="text/javascript">
<!--
//�����Ϣ��ת
function addForward(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('add').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl;

	document.location.href = url;
	
}
function addSortForward(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('addBC').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl;

	if(window.open(url, "", "width=450,height=400")){
		//����޸ĳɹ�����ˢ���б�
		ECSideUtil.reload("ec");
	};
	
}
//�޸���Ϣ��ת
function detailForward(id){
	//��ȡ����Ȩ�޵� URL
	var detailUrl = "${basePath}/timebargain/tradeparams/detailBreed.action?breedID=";
	//��ȡ������תURL
	var url = detailUrl + id;

	document.location.href = url;
	
}
//����ɾ����Ϣ
function deleteList(){
	//��ȡ����Ȩ�޵� URL
	var deleteUrl = document.getElementById('delete').action;
	//��ȡ������תURL
	var url = "${basePath}"+deleteUrl;
	//ִ��ɾ������
	updateRMIEcside(ec.ids,url);
}

function updateForward(id) {
	//��ȡ����Ȩ�޵� URL
	var updateUrl = "/timebargain/tradeparams/detailToCommodity.action";
	//��ȡ������תURL
	var url = "${basePath}"+updateUrl;
	//�� URL ��Ӳ���
	url += "?breedID="+id+"&sortColumns=order+by+commodityID+asc";

	document.location.href = url;
}

function dolistquery() {
	frm.submit();
}
// -->
</script>
</head>
<body>
<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/tradeparams/breedsList.action?sortColumns=order+by+breedID+asc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															Ʒ������:&nbsp;
															<label>
															    <!-- 
																<select id="sortID" name="${GNNT_}primary.sortID[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ��Ʒ��</option>
																	<c:forEach items="${resultList}" var="sort">
																		<option value="${sort.sortID}">${sort.sortName}</option>
																	</c:forEach>	
																</select> -->
																<input id="tariffID" name="${GNNT_}primary.breedName[allLike]" type="text" value="${oldParams['primary.breedName[allLike]'] }" class="input_text"/>	
															</label>
															
														</td>
														<td class="table3_td_1" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>	
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<br />
						<div class="div_gn">
							<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addBreedforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteBreed.action?autoInc=false" id="delete"></rightButton:rightButton>
							&nbsp;&nbsp;<!--  
							<rightButton:rightButton name="Ʒ�ַ���" onclick="addSortForward();" className="anniu_btn" action="/timebargain/tradeparams/addBCategoryList.action" id="addBC"></rightButton:rightButton>
						-->
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="breed"
											action="${basePath}/timebargain/tradeparams/breedsList.action?sortColumns=order+by+breedID+asc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${breed.breedID}" width="5%" viewsAllowed="html" />
												<ec:column property="breedID" width="35%" title="Ʒ��ID" style="text-align:center;">
												   <rightHyperlink:rightHyperlink text="<font color='#880000'>${breed.breedID}</font>" className="blank_a" onclick="return detailForward('${breed.breedID}');" action="/timebargain/tradeparams/detailBreed.action" />
													<%-- <a href="#" class="blank_a" onclick="return detailForward('${breed.breedID}');"><font color="#880000">${breed.breedID}</font></a> --%>
												</ec:column>
												<ec:column property="breedName" title="Ʒ������" width="40%" sortable="false" style="text-align:center;"/>
												<ec:column property="prop" title="��Ӧ��Ʒ" resizeColWidth="false" tipTitle="������Ʒ��Ϣ" sortable="false" width="20%" style="text-align:center;">
												   <rightHyperlink:rightHyperlink text="<img src='${skinPath }/image/app/timebargain/commodity.gif'/>" href="#" onclick="updateForward('${breed.breedID}')" action="/timebargain/tradeparams/detailToCommodity.action"/>
													<%-- <a href="#" onclick="updateForward('<c:out value="${breed.breedID}"/>')"><img src="<c:url value="${skinPath }/image/app/timebargain/commodity.gif"/>"></a> --%>
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
</body>

</html>
