<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>
<title>�������ײ��б�</title>
<script type="text/javascript">
<!--
//�����Ϣ��ת
function addForward(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('add').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl;

	var selTariff=document.getElementById("selTariffID");
	
	var selTariffid=selTariff.value;

	var selTariffName=selTariff.options[selTariff.selectedIndex].text;
	
    document.location.href = url + "?tariffID=" + selTariffid + "&tariffName=" + selTariffName;	

}
//�޸���Ϣ��ת
function detailForward(id){
	//��ȡ����Ȩ�޵� URL
	var detailUrl = "${basePath}/timebargain/tradeparams/detailTariff.action?entity.sectionID=";
	//��ȡ������תURL
	var url = detailUrl + id;
	//�����޸�ҳ��
	if(showDialog(url, "", 800, 550)){
		//����޸ĳɹ�����ˢ���б�
		ECSideUtil.reload("ec");
	};
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
//ִ�в�ѯ�б�
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
					<form name="frm" action="${basePath}/timebargain/tradeparams/tariffList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="table3_td_1" align="right">
												�ײʹ���:&nbsp;
												<input id="tariffID" name="${GNNT_}primary.tariffID" type="text" value="${oldParams['primary.tariffID'] }" class="input_text"/>
												</td>
												<!-- <td align="center">�ײ�״̬��
													<select id="oldDate" name="oldDate" style="width:80" >											    
													<option value="0" <c:if test="${oldStatus==0 }">selected</c:if>>�����ײ�</option>
													<option value="1" <c:if test="${oldStatus==1 }">selected</c:if>>�����ײ�</option>
											  	    </select> 
												</td>
												-->
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
			        <select id="selTariffID" class="normal" style="height:40px; width: 80px">
					  <c:forEach items="${allMap}" var="map">
						  <option value="${map.key }">${map.value }</option>
					  </c:forEach>
					</select>
					<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addTariff.action" id="add"></rightButton:rightButton>
					&nbsp;&nbsp;
					<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteTariff.action" id="delete"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="tariff"
									action="${basePath}/timebargain/tradeparams/tariffList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column cell="checkbox" headerCell="checkbox" alias="ids" value="${tariff.tariffID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:3%;"/>				            	
							            <ec:column property="tariffID" title="�ײʹ���" width="20%" style="text-align:center;">
							               <rightHyperlink:rightHyperlink text="${tariff.tariffID}" className="blank_a" href="<c:out value='${basePath}'/>/timebargain/tradeparams/detailTariff.action?tariffID=${tariff.tariffID}&sortColumns=order+by+commodityID+asc" title="�鿴" action="/timebargain/tradeparams/detailTariff.action" />
							              <%-- <a href="<c:out value="${basePath}"/>/timebargain/tradeparams/detailTariff.action?tariffID=${tariff.tariffID}&sortColumns=order+by+commodityID+asc" title="�鿴"><c:out value="${tariff.tariffID}"/></a> --%>
							            </ec:column>
							            <ec:column property="tariffName" title="�ײ�����" width="20%" style="text-align:center;"/>
							            <ec:column property="createTime" title="����ʱ��" width="30%" style="text-align:center;">
							            <fmt:formatDate pattern="yyyy-MM-dd" value="${tariff.createTime}"/>
							            </ec:column>
							            <ec:column property="createUser" title="������" width="20%" style="text-align:center;"/>  		
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
</body>

</html>
  
