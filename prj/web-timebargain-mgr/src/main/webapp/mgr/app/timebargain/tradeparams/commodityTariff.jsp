<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>

<title>
�ײ�����
</title>
<script type="text/javascript">
function back(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('back').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl;
	
    document.location.href = url;	

}
</script>
</head>
<body>
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
												${tariffID}
												</td>
												<td class="table3_td_1" align="right">
												�ײ�����:&nbsp;${tariffName}</td>
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
					<rightButton:rightButton name="����" onclick="back();" className="anniu_btn" action="/timebargain/tradeparams/tariffList.action" id="back"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="tariff"
									action="${basePath}/timebargain/tradeparams/detailTariff.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%" sortable="false" filterable="false"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column property="commodityID" title="��Ʒ����" width="100" style="text-align:center;"/>
							            <ec:column property="name" title="��Ʒ����" width="110" style="text-align:center;"/>
							            <ec:column property="oldFeeRate_B" title="��������������" width="130"  style="text-align:center;"/> 
							            <ec:column property="newFeeRate_B" title="�ײ���������" width="130"  style="text-align:center;"/>  
							            <ec:column property="oldFeeRate_S" title="����������������" width="130"  style="text-align:center;"/> 
							            <ec:column property="newFeeRate_S" title="�ײ�����������" width="130"  style="text-align:center;"/>  
							            <ec:column property="oldTodayCloseFeeRate_B" title="��������ת�ý񶩻�����" width="160"  style="text-align:center;"/> 
							            <ec:column property="newTodayCloseFeeRate_B" title="�ײ���ת�ý񶩻�����" width="160"  style="text-align:center;"/>  
							            <ec:column property="oldTodayCloseFeeRate_S" title="��������ת�ý񶩻�����" width="160"  style="text-align:center;"/> 
							            <ec:column property="newTodayCloseFeeRate_S" title="�ײ���ת�ý񶩻�����" width="160"  style="text-align:center;"/>  
							            <ec:column property="oldHistoryCloseFeeRate_B" title="��������ת����ʷ��������" width="170"  style="text-align:center;"/> 
							            <ec:column property="newHistoryCloseFeeRate_B" title="�ײ���ת����ʷ��������" width="160"  style="text-align:center;"/>   
							            <ec:column property="oldHistoryCloseFeeRate_S" title="��������ת����ʷ��������" width="170"  style="text-align:center;"/> 
							            <ec:column property="newHistoryCloseFeeRate_S" title="�ײ���ת����ʷ��������" width="160"  style="text-align:center;"/>   
							            <ec:column property="oldForceCloseFeeRate_B" title="��������ǿ��ת�÷���" width="160"  style="text-align:center;"/> 
							            <ec:column property="newForceCloseFeeRate_B" title="�ײ���ǿ��ת�÷���" width="160"  style="text-align:center;"/>   
							            <ec:column property="oldForceCloseFeeRate_S" title="��������ǿ��ת�÷���" width="160"  style="text-align:center;"/> 
							            <ec:column property="newForceCloseFeeRate_S" title="�ײ���ǿ��ת�÷���" width="160"  style="text-align:center;"/>                        
							            <ec:column property="modifyTime" title="�޸�ʱ��" width="160" cell="date" format="date" style="text-align:center;"/>
							            <ec:column property="createUser" title="�޸���" width="100" style="text-align:center;"/>
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
</body>

</html>
