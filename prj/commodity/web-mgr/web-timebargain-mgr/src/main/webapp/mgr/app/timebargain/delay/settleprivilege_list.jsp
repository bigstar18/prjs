<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
        <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<title></title>
<script type="text/javascript">

jQuery(document).ready(function() {
	jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
});
//�����Ϣ��ת
function addForward(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('add').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl;

	if(showDialog(url, "", 800, 550)){
		//�����ӳɹ�����ˢ���б�
		ECSideUtil.reload("ec");
	}	
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
	var updateUrl = "/timebargain/delay/detailPrivilegeforward.action";
	//��ȡ������תURL
	var url = "${basePath}"+updateUrl;
	//�� URL ��Ӳ���
	url += "?entity.id="+id;

	if(showDialog(url, "", 800, 550)){
		ECSideUtil.reload("ec");
	}	
}

function dolistquery() {
	if(jQuery("#frm").validationEngine('validateform')){
		frm.submit();
	}
}
</script>
</head>
<body leftmargin="2" topmargin="0">
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/delay/privilegeList.action?sortColumns=order+by+ID+asc" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="table3_td_1" align="right">
														&nbsp;&nbsp;�����̴��룺
												</td>
												<td>
													<input type="text" id="typeId" name="${GNNT_}primary.typeId[=]" style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.typeId[=]']}"/>
												</td>
						                        <td align="right">
														��Ʒ���룺
												</td>
												<td>
													<input type="text" id="kindId" name="${GNNT_}primary.kindId[=]"  style="width:111;ime-mode:disabled" maxlength="18"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.kindId[=]']}"/>
												</td>
												<td class="table3_td_1" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>	
												    &nbsp;&nbsp;
												    <button class="btn_cz" onclick="myReset();">����</button>
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
					<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/delay/addPrivilegeforward.action" id="add"></rightButton:rightButton>
					&nbsp;&nbsp;
					<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/timebargain/delay/deletePrivilege.action" id="delete"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="settleprivilege"
									action="${basePath}/timebargain/delay/privilegeList.action?sortColumns=order+by+ID+asc"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column cell="checkbox" headerCell="checkbox" alias="ids" value="${settleprivilege.id}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:20%;"/>		
									    <ec:column property="typeId" title="�����̴���" width="20%" ellipsis="true" style="text-align:center;">
									       <%-- <a onclick="updateForward('<c:out value="${settleprivilege.id}"/>');" style="cursor:hand" title="�鿴"><c:out value="${settleprivilege.typeId}"/></a> --%>
									       <rightHyperlink:rightHyperlink text="${settleprivilege.typeId}" title='�鿴' onclick="updateForward('${settleprivilege.id}');" action="/timebargain/delay/detailPrivilegeforward.action" />
									    </ec:column>
							            <ec:column property="kindId" title="��Ʒ����" width="20%" style="text-align:center;"/>
							            <ec:column property="privilegecodeb" title="��Ȩ��" width="20%" style="text-align:center;" mappingItem="PRIVILEGECODE_B"/>
										<ec:column property="privilegecodes" title="����Ȩ��" width="20%" style="text-align:center;" mappingItem="PRIVILEGECODE_S"/>
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
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
</body>
</html>