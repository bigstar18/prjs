<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>���׽ڹ����б�</title>
<script type="text/javascript">
<!--
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
	//�޸���Ϣ��ת
	function detailForward(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/timebargain/tradeparams/detailTradeTimeforward.action?entity.sectionID=";
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

// -->
</script>
</head>
<body>
	<div id="main_body">
	    <div class="warning">
			<div class="content">
				��ܰ��ʾ :��ӡ�ɾ�����޸Ľ��׽�
			</div>
		</div>	
	    <div class="div_gn">
			<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addTradeTimeforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteTradeTime.action" id="delete"></rightButton:rightButton>
		</div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="tradeTime"
							action="${basePath}/timebargain/tradeparams/tradeTimeList.action?sortColumns=order+by+sectionID+asc"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${tradeTime.sectionID}" width="5%" viewsAllowed="html" />
								<ec:column property="sectionID" width="20%" title="���" style="text-align:center;">
								   <rightHyperlink:rightHyperlink text="<font color='#880000'>${tradeTime.sectionID}</font>" className="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="�޸�" action="/timebargain/tradeparams/detailTradeTimeforward.action" />
								    <%-- <a href="#" class="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="�޸�"><font color="#880000">${tradeTime.sectionID}</font></a> 	--%>
								</ec:column>
								<ec:column property="name" title="���׽�����" width="20%" style="text-align:center;">
								    <rightHyperlink:rightHyperlink text="<font color='#880000'>${tradeTime.name}</font>" className="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="�޸�" action="/timebargain/tradeparams/detailTradeTimeforward.action" />
									<%-- <a href="#" class="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="�޸�"><font color="#880000">${tradeTime.name}</font></a> 	--%>
								</ec:column>
								<ec:column property="startTime" title="���׿�ʼʱ��" width="20%" style="text-align:center;"/>
								<ec:column property="endTime" title="���׽���ʱ��" width="20%" style="text-align:center;"/>
								<ec:column property="status" title="״̬" width="20%" style="text-align:center;">${tradetime_statusMap[tradeTime.status]}</ec:column>
								
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
