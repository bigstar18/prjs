<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>���������������б�</title>
<script type="text/javascript">
<!--
	//�����Ϣ��ת
	function addForward(){
		//��ȡ����Ȩ�޵� URL
		var addUrl=document.getElementById('add').action;
		//��ȡ������תURL
		var url = "${basePath}"+addUrl;

		if(showDialog(url, "", 500, 350)){
			//�����ӳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		}
		
	}
	//�޸���Ϣ��ת
	function detailForward(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerAreaforward.action?entity.areaId=";
		//��ȡ������תURL
		var url = detailUrl + id;
		//�����޸�ҳ��
		if(showDialog(url, "", 500, 350)){
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

// -->
</script>
</head>
<body>
	<div id="main_body">
	    
	    <div class="div_gn">
			<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/broker/brokerManagement/addBrokerAreaforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/broker/brokerManagement/deleteBrokerArea.action" id="delete"></rightButton:rightButton>
		</div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="brokerArea"
							action="${basePath}/broker/brokerManagement/listBrokerArea.action?sortColumns=order+by+areaId+asc"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${brokerArea.areaId}" width="5%" viewsAllowed="html" />
								<ec:column property="areaId" width="20%" title="�������" style="text-align:center;">
								    <a href="#" class="blank_a" onclick="detailForward('${brokerArea.areaId}')" title="�޸�"><font color="#880000">${brokerArea.areaId}</font></a> 	
								</ec:column>
								
								<ec:column property="name" title="��������" width="20%" style="text-align:center;"/>
								
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
