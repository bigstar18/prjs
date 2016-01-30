<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<jsp:include page="commodity_head.jsp"></jsp:include>
<html>
<head>
	<c:set var="target" value=""/>
	<c:set var="orderBy" value="commodityID+asc"/>
	<c:if test="${opr == 'his'}">
	<c:set var="target" value="His"/>
	<c:set var="orderBy" value="settleProcessDate+desc"/>	
	</c:if>
<title>��Ʒ�����б�</title>
<script type="text/javascript">
//�����Ϣ��ת
function addForward(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('add').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl+"?breedID=${breedID}";

	document.location.href = url;
	
}
function back(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('back').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl+"?sortColumns=order+by+breedID+asc";

	document.location.href = url;	
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
	var url = "${basePath}"+deleteUrl+"?breedID=${breedID}";
	//ִ��ɾ������
	updateRMIEcside(ec.ids,url);
}

function updateForward(id, date) {
	//��ȡ����Ȩ�޵� URL
	var updateUrl = "/timebargain/tradeparams/detailCommodity${target}.action";
	//��ȡ������תURL
	var url = "${basePath}"+updateUrl;
	//�� URL ��Ӳ���
	url += "?commodityID="+id+"&opr="+opr.value;

	if ("${target}" == "His") {
		url += "&d="+date;
	}
	
	document.location.href = url;
}
function status2(){
	var status = document.getElementById("sta").value;
	var updateStatusUrl = "/timebargain/tradeparams/updateCommodityStatus.action";
	//��ȡ������תURL
	var url = "${basePath}"+updateStatusUrl+"?breedID=${breedID}&status="+status;
	
	updateRMIEcside(ec.ids,url);
}
</script>
</head>
<body>
	<input type="hidden" name="opr" value="${opr }"/>
<br />
<div class="div_gn">
    <c:if test="${opr == 'cur'}">
	<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addCommodityforward.action" id="add"></rightButton:rightButton>
	&nbsp;&nbsp;
	<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteCommodity.action?autoInc=false" id="delete"></rightButton:rightButton>
	&nbsp;&nbsp;
	</c:if>
	<rightButton:rightButton name="����Ʒ��" onclick="back();" className="anniu_btn" action="/timebargain/tradeparams/breedsList.action" id="back"></rightButton:rightButton>
</div>
<div class="div_list">
<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
	  <td>
		<ec:table items="pageInfo.result" var="commodity"
			action="${basePath}/timebargain/tradeparams/detailToCommodity${target }.action?sortColumns=order+by+${orderBy }"											
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
			showPrint="true" listWidth="100%"
			minHeight="345"  style="table-layout:fixed;">
			<ec:row>
				<c:if test="${opr == 'cur'}">
					<ec:column cell="checkbox" headerCell="checkbox" alias="ids" value="${commodity.commodityID}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:5%;"/>		
				</c:if>
				<ec:column property="name" title="��Ʒ����" width="10%" style="text-align:center;">	
					<c:if test="${opr == 'cur'}">
					   <rightHyperlink:rightHyperlink text="${commodity.name}" className="blank_a" onclick="return updateForward('${commodity.commodityID}');" title="�޸�" action="/timebargain/tradeparams/detailCommodity${target}.action" />
				  		<%-- <a href="#" class="blank_a" onclick="return updateForward('${commodity.commodityID}');" title="�޸�"><c:out value="${commodity.name}"/></a> --%>
	           		</c:if>
	           		<c:if test="${opr == 'his'}">	           		    
	           		    <rightHyperlink:rightHyperlink text="${commodity.name}" className="blank_a" onclick="return updateForward('${commodity.commodityID}','${commodity.settleProcessDate }')" title="�޸�" action="/timebargain/tradeparams/detailCommodity${target}.action" />
	           			<%-- <a href="#" class="blank_a" onclick="return updateForward('${commodity.commodityID}', '<fmt:formatDate pattern="yyyy-MM-dd" value="${commodity.clearDate }" />');" title="�޸�"><c:out value="${commodity.name}"/></a> --%>
	           		</c:if>
	            </ec:column>	
	            <ec:column property="commodityID" title="��Ʒ����" width="10%" style="text-align:center;"/>              								
				<c:if test="${opr == 'cur'}">
				<ec:column property="status"  title="״̬" width="15%" style="text-align:center;">${commodity_statusMap[commodity.status]}</ec:column>			
				</c:if>
				<ec:column property="marketDate" title="��������" cell="date" format="date" width="20%" style="text-align:center;"/>
				<ec:column property="settleDate" title="�������" cell="date" format="date" width="20%" style="text-align:center;"/>
				<%-- 
				<c:if test="${opr == 'his'}">
					<ec:column property="clearDate" title="��������" cell="date" format="date" width="20%" style="text-align:center;"/>
				</c:if>	
				--%>	
					
			</ec:row>
			
		</ec:table>
	</td>
 </tr>

<c:if test="${opr == 'cur'}">
	<tr>
		<td>
			<select id="sta">
				<option value="0">��Ч</option>
				<option value="2">��ͣ����</option>
			</select>
			<input type="button" class="anniu_btn" value="�޸�״̬"  onclick="status2()">
		</td>
	</tr>
</c:if>
</table>
</div>	
</body>

</html>
